package com.ai.ch.user.web.vo;

public class ShopManageVo {

	/**
	 * tenantId
	 */
	private String tenantId;

	/**
	 * 用戶Id
	 */
	private String userId;

	/**
	 * 用戶姓名
	 */
	private String userName;

	/**
	 * 保证金
	 */
	private Long deposit;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 商品品类
	 */
	private String brandNameType;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getDeposit() {
		return deposit;
	}

	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getBrandNameType() {
		return brandNameType;
	}

	public void setBrandNameType(String brandNameType) {
		this.brandNameType = brandNameType;
	}

}
