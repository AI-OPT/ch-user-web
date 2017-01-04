package com.ai.ch.user.web.vo;

/**
 * 资质审核日志 Date: 2017年1月4日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class AuditInfoVo {

	/**
	 * 用户Id
	 */
	private String userId;

	/**
	 * 审核结果
	 */
	private String auditStatus;

	/**
	 * 审核时间
	 */
	private String auditTime;

	/**
	 * 审核原因
	 */
	private String auditDesc;

	/**
	 * 操作员
	 */
	private String operName;

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

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

}
