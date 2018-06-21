package com.yinhai.ec.system.service.impl;  

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AlbumDomain;
import com.yinhai.ec.common.domain.AlbumImgDomain;
import com.yinhai.ec.system.service.AlbumManagerService;
  
@Service
@Transactional
public class AlbumManagerServiceImpl extends BaseServiceImpl implements AlbumManagerService{

	@Override
	public void addAlbum(PageParam pageParam, ResultBean bean) throws Exception {
		if(StringUtils.isEmpty(pageParam.get("album_name"))){
			bean.setError(true);
			bean.setError_msg("相册名称不能为空");
		}
		if(!bean.isError() && StringUtils.isEmpty(bean.getError_msg())){
			AlbumDomain album = new AlbumDomain();
			album.setAlbumName(pageParam.getString("album_name"));
			if(StringUtils.isEmpty(pageParam.get("album_id"))){
				album.setCreateTime(getSystemTimestamp());
				album.setCreateUser(pageParam.getUserInfo().getUserId());
				album.setCreateUserName(pageParam.getUserInfo().getUserName());
				sqlSession.insert("hy.album.manager.insert", album);
				bean.setSuccess_msg("相册【"+pageParam.getString("album_name")+"】添加成功");
			}else{
				album.setAlbumId(Integer.valueOf(pageParam.getString("album_id")));
				int count = sqlSession.update("hy.album.manager.updateAlbumSelective", album);
				if (count != 1) {
					throw new BaseUpdateException(count, "更新相册出错");
				}
				bean.setSuccess_msg("相册【"+pageParam.getString("album_name")+"】修改成功");
			}
		}
	}

	@Override
	public List<AlbumDomain> queryListAlbums() throws Exception {
		return sqlSession.selectList("hy.album.manager.selectAlbumList");
	}

	@Override
	public AlbumDomain getAlbumById(Integer albumId) throws Exception {
		return sqlSession.selectOne("hy.album.manager.selectByPrimaryKey", albumId);
	}

	@Override
	public List<AlbumImgDomain> getAlbumImgs(AlbumDomain album) throws Exception {
		return sqlSession.selectList("hy.album.manager.selectAlbumImgsList", album);
	}

	@Override
	public void saveAlbumImg(AlbumImgDomain imgDomain) throws Exception {
		sqlSession.insert("hy.album.manager.insertAlbumImg", imgDomain);
	}

	@Override
	public void setAlbumCover(Integer album_id, Integer img_id, ResultBean bean) throws Exception {
		AlbumDomain album = new AlbumDomain();
		album.setAlbumId(album_id);
		album.setAlbumCover(img_id);
		int count = sqlSession.update("hy.album.manager.updateAlbumSelective", album);
		if (count != 1) {
			throw new BaseUpdateException(count, "更新相册出错");
		}
		bean.setSuccess_msg("设置封面成功");
	}

	@Override
	public void deleteImg(Integer img_id, ResultBean bean) throws Exception {
		AlbumImgDomain albumImgDomain = new AlbumImgDomain();
		albumImgDomain.setImgId(img_id);
		albumImgDomain.setImgStatus(HYConst.STATUS_NO);
		int count = sqlSession.update("hy.album.manager.updateAlbumImgByPrimaryKeySelective", albumImgDomain);
		if (count != 1) {
			throw new BaseUpdateException(count, "删除相册图片出错");
		}
	}

	@Override
	public AlbumImgDomain selectAlbumImgByPrimary(AlbumImgDomain imgDomain) throws Exception {
		
		return sqlSession.selectOne("hy.album.manager.selectAlbumImgByPrimary", imgDomain);
	}

	@Override
	public void deleteAlbum(Integer album_id, ResultBean bean) throws Exception {
		if(album_id == null){
			bean.setError(true);
			bean.setError_msg("删除相册失败");
		}else{
			AlbumDomain album = getAlbumById(album_id);
			if(HYConst.STATUS_YES.equals(album.getAlbumStatus())){
				// 先删除相册图片
				int imgCount =sqlSession.update("hy.album.manager.deleteAlbumImgs", album);
				AlbumDomain albumDomain = new AlbumDomain();
				albumDomain.setAlbumId(album.getAlbumId());
				albumDomain.setAlbumStatus(HYConst.STATUS_NO);
				int count = sqlSession.update("hy.album.manager.updateAlbumSelective", albumDomain);
				if (count != 1) {
					throw new BaseUpdateException(count, "更新相册出错");
				}
				bean.setSuccess_msg("相册【"+album.getAlbumName()+"】删除成功,共删除了"+imgCount+"张图片");
			}
		}
		
	}
	
}
 