package com.ai.ch.user.web.vo;

public class StatusListVo {

	// 用户id
	private String userId;

	/**
	 *  用户名
	 */
	private String userName;

	/**
	 *  企业名称
	 */
	private String groupName;

	/**
	 * 状态
	 */
	private String state;

	/**
	 *  状态码
	 */
	private String stateValue;

	public String getStateValue() {
		return stateValue;
	}

	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
