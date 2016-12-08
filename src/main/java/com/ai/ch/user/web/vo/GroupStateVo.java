package com.ai.ch.user.web.vo;

/**
 * 企业状态 Date: 2016年9月1日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class GroupStateVo {

	/**用户id
	 * 
	 */
	private String userId;

	/**
	 * 状态
	 */
	private String state;

	/**
	 * 状态值
	 */
	private String stateValue;

	/**
	 * 企业名称
	 */
	private String groupName;

	/**
	 * 用户名
	 */
	private String userName;

	public String getStateValue() {
		return stateValue;
	}

	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
