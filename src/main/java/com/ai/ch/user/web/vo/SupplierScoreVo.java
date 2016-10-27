package com.ai.ch.user.web.vo;

/**
 * 供货商评价列表
 */
public class SupplierScoreVo {

	/**
	 * 租户Id
	 */
	private String tenantId;

	/**
	 * 用户Id
	 */
	private String userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 企业名
	 */
	private String groupName;

	/**
	 * 综合评分
	 */
	private Integer totalScore;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
