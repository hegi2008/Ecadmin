package com.yinhai.ec.system.controller;  

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.BaseConfigUtil;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AlbumDomain;
import com.yinhai.ec.common.domain.AlbumImgDomain;
import com.yinhai.ec.common.util.FTPUtil;
import com.yinhai.ec.system.service.AlbumManagerService;
  
/**
* @package com.yinhai.ec.base.system.controller
* <p>Title: AlbumManagerController.java</p>
* <p>Description: 相册管理</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 陈瓜瓜软件股份有限公司</p>
* @author cjh
* @date 2016年3月21日 下午4:28:29
* @version 1.0
 */
@Controller
@RequestMapping("system/album/albumManager")
public class AlbumManagerController extends BaseController{
	@Autowired
	private AlbumManagerService albumManagerService;
	
	@RequestMapping("/default")
	public String execute(ModelMap map) throws Exception{
		List<AlbumDomain> albums = albumManagerService.queryListAlbums();
		map.put("albums", albums);
		map.put("imgUrl", BaseConfigUtil.getFileServerDomain());
		return "/system/albumManager.jsp";
	}
	
	
	@RequestMapping("/viewAlbumImgs")
	public String viewAlbumImgs(ModelMap map, HttpServletRequest request) throws Exception{
		String album_id = request.getParameter("album_id");
		if(StringUtils.isEmpty(album_id)){
			return HYConst.PAGE_500;
		}
		AlbumDomain album = albumManagerService.getAlbumById(Integer.valueOf(album_id));
		List<AlbumImgDomain> imgs = albumManagerService.getAlbumImgs(album);
		
		map.put("album", album);
		map.put("imgUrl", BaseConfigUtil.getFileServerDomain());
		map.put("imgs", imgs);
		map.put("imgsCount", imgs.size());
		return "/system/albumImgs.jsp";
	}
	
	@RequestMapping("/saveAlbum")
	@ResponseBody
	public Object saveAlbum(HttpServletRequest request) throws Exception {
		PageParam pageParam = getPageParam(request);
		ResultBean bean = getResultBean();
		albumManagerService.addAlbum(pageParam,bean);
		return bean;
	}
	
	@RequestMapping("/deleteAlbum")
	@ResponseBody
	public Object deleteAlbum(Integer album_id) throws Exception {
		ResultBean bean = getResultBean();
		albumManagerService.deleteAlbum(album_id,bean);
		return bean;
	}
	
	@RequestMapping("/uploadImgs")
	@ResponseBody
	public Object uploadImgs(HttpServletRequest request,@RequestParam(value="imgs")MultipartFile file) throws Exception {
		String album_id = request.getParameter("album_id");
		ResultBean bean = getResultBean();
		// 图片类型验证
		String fileType = file.getContentType();
		if(FTPUtil.isImage(fileType)){
			String type = "."+fileType.substring(fileType.lastIndexOf("/")+1, fileType.length());
			String fileName = System.currentTimeMillis()+"_original"+type;
			String path = BaseConfigUtil.getEcAlbumUploadPath()+album_id+"/";
			if(FTPUtil.uploadFTPFile(file,fileName,path)){
				AlbumImgDomain imgDomain = new AlbumImgDomain();
				imgDomain.setAlbumId(Integer.valueOf(album_id));
				imgDomain.setImgName(fileName);
				imgDomain.setImgPath(BaseConfigUtil.getEcAlbumVisitPath()+album_id+"/"+fileName);
				imgDomain.setImgSize((int) (file.getSize()/1000));
				imgDomain.setUploadTime(albumManagerService.getSystemTimestamp());
				imgDomain.setUploadUser(getUserInfo().getUserId());
				albumManagerService.saveAlbumImg(imgDomain);
			}else{
				bean.setError(true);
				bean.setError_msg("上传的文件:"+file.getOriginalFilename()+"失败");
			}
		}else{
			bean.setError(true);
			bean.setError_msg("上传的文件:"+file.getOriginalFilename()+"类型不符合要求");
		}
		return bean;
	}
	
	@RequestMapping("/setAlbumCover")
	@ResponseBody
	public Object setAlbumCover(HttpServletRequest request) throws Exception {
		String album_id = request.getParameter("album_id");
		String img_id = request.getParameter("img_id");
		ResultBean bean = getResultBean();
		albumManagerService.setAlbumCover(Integer.valueOf(album_id),Integer.valueOf(img_id),bean);
		return bean;
	}
	
	@RequestMapping("/deleteImg")
	@ResponseBody
	public Object deleteImg(HttpServletRequest request) throws Exception {
		String img_id = request.getParameter("img_id");
		ResultBean bean = getResultBean();
		AlbumImgDomain imgDomain = new AlbumImgDomain();
		imgDomain.setImgId(Integer.valueOf(img_id));
		imgDomain = albumManagerService.selectAlbumImgByPrimary(imgDomain);
		if(imgDomain.getImgPath() != null){
			deleteImgIfHas(BaseConfigUtil.getEcAlbumUploadPath()+imgDomain.getAlbumId()+"/", imgDomain.getImgPath());
		}
		albumManagerService.deleteImg(Integer.valueOf(img_id),bean);
		return bean;
	}
	
	/**
	  * @package com.yinhai.ec.base.system.controller
	  * @method deleteImgIfHas 方法 
	  * @describe <p>方法说明:删除FTP服务器上的图片</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年3月23日 上午9:30:08
	 */
	private void deleteImgIfHas(String path, String fileName) {
		try {
			if(fileName.contains("/")){
				fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
			}
			if(FTPUtil.connectServer()){
				if(FTPUtil.makeAndEnterDirectory(path)){
					if(FTPUtil.isExistFile(fileName)){
						FTPUtil.deleteFile(fileName);
					}
				}
			}
		} finally {
			FTPUtil.closeConnect();
		}
	}
	
 }
 