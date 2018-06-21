package com.yinhai.plugin.fs;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件 基础 domain
 * */
public abstract class FSBaseDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2176663745912469095L;
	private String id;
	private String name;
	private InputStream inputStream;
	@Deprecated
	private String contentType;
	private long size;
	private Date updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Deprecated
	public String getContentType() {
		return contentType;
	}

	public long getSize() {
		return size;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Deprecated
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
