package com.yinhai.hyman.weixin.api.response;

import com.yinhai.hyman.weixin.api.entity.Group;

/**
 * @author peiyu
 */
public class CreateGroupResponse extends BaseResponse {

//    private String id;
//    private String name;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Group group;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	
}
