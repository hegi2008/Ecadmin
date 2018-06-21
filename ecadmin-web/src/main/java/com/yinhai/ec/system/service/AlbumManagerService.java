package com.yinhai.ec.system.service;  

import java.util.List;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.AlbumDomain;
import com.yinhai.ec.common.domain.AlbumImgDomain;
  
public interface AlbumManagerService extends BaseService{

	void addAlbum(PageParam pageParam, ResultBean bean) throws Exception;

	List<AlbumDomain> queryListAlbums() throws Exception;

	AlbumDomain getAlbumById(Integer albumId) throws Exception;

	List<AlbumImgDomain> getAlbumImgs(AlbumDomain album) throws Exception;

	void saveAlbumImg(AlbumImgDomain imgDomain) throws Exception;

	void setAlbumCover(Integer album_id, Integer img_id, ResultBean bean) throws Exception;

	void deleteImg(Integer img_id, ResultBean bean) throws Exception;

	AlbumImgDomain selectAlbumImgByPrimary(AlbumImgDomain imgDomain) throws Exception;

	void deleteAlbum(Integer album_id, ResultBean bean) throws Exception;
}
 