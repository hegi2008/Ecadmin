package com.yinhai.ec.common.domain;

import java.io.Serializable;
import java.util.Date;

public class AlbumDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer albumId;

    private String albumName;
    
    private String createUserName;

    private Integer albumCover;

    private Integer albumType;

    private Date createTime;

    private Integer createUser;

    private Integer albumStatus;

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName == null ? null : albumName.trim();
    }

    public Integer getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(Integer albumCover) {
        this.albumCover = albumCover;
    }

    public Integer getAlbumType() {
        return albumType;
    }

    public void setAlbumType(Integer albumType) {
        this.albumType = albumType;
    }

    public Date getCreateTime() {
    	
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getAlbumStatus() {
        return albumStatus;
    }

    public void setAlbumStatus(Integer albumStatus) {
        this.albumStatus = albumStatus;
    }

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}