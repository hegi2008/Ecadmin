package com.yinhai.ec.common.domain;

import java.io.Serializable;
import java.util.Date;

public class UserDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;

    private String userName;

    private Integer userSex;

    private String userLogin;

    private String userPwd;

    private Integer userPwdWrongTimes;

    private Date userPwdUpdateTime;

    private Integer userLock;

    private Integer userStatus;

    private String userPhone;

    private String userEmail;

    private String userHeaderPic;

    private Integer userCreater;

    private Date userCreateTime;
    
    private Integer orgId;

    private String orgName;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin == null ? null : userLogin.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public Integer getUserPwdWrongTimes() {
        return userPwdWrongTimes;
    }

    public void setUserPwdWrongTimes(Integer userPwdWrongTimes) {
        this.userPwdWrongTimes = userPwdWrongTimes;
    }

    public Date getUserPwdUpdateTime() {
        return userPwdUpdateTime;
    }

    public void setUserPwdUpdateTime(Date userPwdUpdateTime) {
        this.userPwdUpdateTime = userPwdUpdateTime;
    }

    public Integer getUserLock() {
        return userLock;
    }

    public void setUserLock(Integer userLock) {
        this.userLock = userLock;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserHeaderPic() {
        return userHeaderPic;
    }

    public void setUserHeaderPic(String userHeaderPic) {
        this.userHeaderPic = userHeaderPic == null ? null : userHeaderPic.trim();
    }

    public Integer getUserCreater() {
        return userCreater;
    }

    public void setUserCreater(Integer userCreater) {
        this.userCreater = userCreater;
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }
    
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }
}