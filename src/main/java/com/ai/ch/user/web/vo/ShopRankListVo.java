package com.ai.ch.user.web.vo;

public class ShopRankListVo {

	private String tenantId;
	private Integer rank;
	private String periodType;
	private Long minScore;
	private Long maxScore;
	private String rankName;
	private String rankLogo;
	private Long operId;
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
