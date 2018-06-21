package com.yinhai.plugin.fs.mongo.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bson.Document;
import org.bson.assertions.Assertions;
import org.bson.types.ObjectId;

import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.yinhai.plugin.fs.FSBaseDomain;
import com.yinhai.plugin.fs.mongo.IFSRepositoryForMongo;
import com.yinhai.plugin.fs.mongo.domain.FSDomainForMongo;
import com.yinhai.plugin.fs.mongo.template.GridFSTemplate;

public class FSRepositoryForMongo implements IFSRepositoryForMongo {
	private GridFSTemplate gridFSTemplate;
	
	public GridFSTemplate getGridFSTemplate() {
		return gridFSTemplate;
	}

	public void setGridFSTemplate(GridFSTemplate gridFSTemplate) {
		this.gridFSTemplate = gridFSTemplate;
	}
	
	@Override
	public FSBaseDomain get(String businessFileId) {
		Assertions.notNull("id", businessFileId);
		GridFSFile file = gridFSTemplate.find(businessFileId);
		if(file != null){
			FSDomainForMongo fs = new FSDomainForMongo();
			fs.setId(file.getObjectId().toString());
			fs.setName(file.getFilename());
			fs.setSize(file.getLength());
			fs.setUpdateDate(file.getUploadDate());
			fs.setMeta(file.getMetadata());
			fs.setMD5(file.getMD5());
			return fs;
		}
		return null;
	}

	@Override
	public FSBaseDomain storeFile(String businessFileId, FSBaseDomain fs) {
		return storeFile(businessFileId, fs, new Document());
	}

	@Override
	public FSBaseDomain storeFile(String businessFileId, FSBaseDomain fs, Document metadata) {
		Assertions.notNull("业务文件ID", businessFileId);
		Assertions.notNull("文件", fs);
		Assertions.notNull("文件内容", fs.getInputStream());
		Assertions.notNull("文件名称", fs.getName());
		InputStream ins = fs.getInputStream();
		try {
			ObjectId id = gridFSTemplate.store(businessFileId, ins, fs.getName(), metadata);
			fs.setId(id.toString());
			return fs;
		} finally {
			try {
				ins.close();
			} catch (IOException e) {
				throw new RuntimeException("输入流文件没有正常关闭");
			}
		}
	}
	
	@Override
	public void delete(String businessFileId) {
		Assertions.notNull("businessFileId", businessFileId);
		gridFSTemplate.delete(businessFileId);
	}

	@Override
	@Deprecated
	public void removeByFileName(String fileName) {
		Assertions.notNull("fileName", fileName);
		gridFSTemplate.deleteByName(fileName);
	}

	@Override
	public FSBaseDomain uploadTempFile(FSBaseDomain fs, Document metadata) {
		Assertions.notNull("文件", fs);
		Assertions.notNull("文件内容", fs.getInputStream());
		Assertions.notNull("文件名称", fs.getName());
		InputStream ins = fs.getInputStream();
		try {
			ObjectId id = gridFSTemplate.storeTempFile(fs.getInputStream(), fs.getName(), metadata);
			fs.setId(id.toString());
			return fs;
		} finally {
			try {
				ins.close();
			} catch (IOException e) {
				throw new RuntimeException("输入流文件没有正常关闭");
			}
		}
	}

	@Override
	public FSBaseDomain moveAndRemoveTemp2Master(String businessFileId, String tmpid) {
		GridFSDownloadStream downloadStream = null;
		FSDomainForMongo fs = null;
		try {
			fs = (FSDomainForMongo) getTempFile(tmpid);
			downloadStream = gridFSTemplate.openTempDownloadStream(fs.getId());
			fs.setInputStream(downloadStream);
			storeFile(businessFileId, fs, fs.getMeta());
			if(!fs.getId().equals(tmpid)){
				// id不一样 移动成功 删除temp文件
				gridFSTemplate.deleteTempFile(tmpid);
			}
		} catch (Exception e) {
			throw new RuntimeException("移动临时文件失败");
		} finally {
			if(downloadStream != null){
				downloadStream.close();
			}
		}
		return fs; 
	}

	@Override
	public FSBaseDomain getTempFile(String tmpid) {
		Assertions.notNull("tmpid", tmpid);
		GridFSFile file = gridFSTemplate.findTempFile(tmpid);
		if(file != null){
			FSDomainForMongo fs = new FSDomainForMongo();
			fs.setId(file.getObjectId().toString());
			fs.setName(file.getFilename());
			fs.setSize(file.getLength());
			fs.setUpdateDate(file.getUploadDate());
			fs.setMeta(file.getMetadata());
			fs.setMD5(file.getMD5());
			return fs;
		}
		return null;
	}

	@Override
	public void downloadFileToStream(String businessFileId, OutputStream out) {
		Assertions.notNull("businessFileId", businessFileId);
		Assertions.notNull("out", out);
		gridFSTemplate.downloadFileToStream(businessFileId, out);
	}
}
