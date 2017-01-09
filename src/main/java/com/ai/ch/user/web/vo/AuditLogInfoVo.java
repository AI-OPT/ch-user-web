package com.ai.ch.user.web.vo;

/**
 * 资质审核日志 Date: 2017年1月4日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class AuditLogInfoVo {
	/**
	 * 日志id
	 */
	private String logId;

	/**
	 * 类型
	 */
	private String ctType;

	/**
	 * 租户id
	 */
	private String tenantId;
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 审核状态
	 */
	private String auditStatus;

	/**
	 * 审核时间
	 */
	private String auditTime;

	/**
	 * 操作员id
	 */
	private String operId;

	/**
	 * 操作员姓名
	 */
	private String operName;

	/**
	 * 审核描述
	 */
	private String auditDesc;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 企业名
	 */
	private String companyName;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getCtType() {
		return ctType;
	}

	public void setCtType(String ctType) {
		this.ctType = ctType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
