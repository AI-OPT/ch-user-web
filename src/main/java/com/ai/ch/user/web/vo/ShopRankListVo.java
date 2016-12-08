package com.ai.ch.user.web.vo;

public class ShopRankListVo {

	/**
	 * 租户id
	 */
	private String tenantId;
	/**
	 * 店铺评级
	 */
	private Integer rank;
	/**
	 * 结算周期
	 */
	private String periodType;
	/**
	 * 最小分数
	 */
	private Long minScore;
	/**
	 * 最大分数
	 */
	private Long maxScore;
	/**
	 * 评级名称
	 */
	private String rankName;
	/**
	 * 评级图片
	 */
	private String rankLogo;
	/**
	 * 操作员id
	 */
	private Long operId;
	/**
	 * 操作员名称
	 */
	private String operName;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public Long getMinScore() {
		return minScore;
	}

	public void setMinScore(Long minScore) {
		this.minScore = minScore;
	}

	public Long getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Long maxScore) {
		this.maxScore = maxScore;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getRankLogo() {
		return rankLogo;
	}

	public void setRankLogo(String rankLogo) {
		this.rankLogo = rankLogo;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

}
