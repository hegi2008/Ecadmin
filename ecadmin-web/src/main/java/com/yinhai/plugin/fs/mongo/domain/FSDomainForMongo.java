package com.yinhai.plugin.fs.mongo.domain;

import org.bson.Document;

import com.yinhai.plugin.fs.FSBaseDomain;

/**
 * mongoDB 基础domain
 * */
public class FSDomainForMongo extends FSBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3361879822679676566L;
	
	private Document meta;
	
	private String MD5;

	public Document getMeta() {
		return meta;
	}

	public void setMeta(Document meta) {
		this.meta = meta;
	}

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String md5) {
		this.MD5 = md5;
	}
}
