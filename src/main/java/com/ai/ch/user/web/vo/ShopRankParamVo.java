package com.ai.ch.user.web.vo;

import java.util.List;
import java.util.Map;

import com.ai.ch.user.api.rank.params.ShopRankRuleVo;

/**
 * 规则表参数 Date: 2016年10月12日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class ShopRankParamVo {
	private ShopRankListVo shopRankListVo;
	private Map<String, String> urlMap;
	private Map<String, String> nameMap;
	private Map<String, String> idpsMap;
	private List<ShopRankRuleVo> result;
	private List<ShopRankRuleVo> middleData;

	public List<ShopRankRuleVo> getMiddleData() {
		return middleData;
	}

	public void setMiddleData(List<ShopRankRuleVo> middleData) {
		this.middleData = middleData;
	}

	public ShopRankListVo getShopRankListVo() {
		return shopRankListVo;
	}

	public void setShopRankListVo(ShopRankListVo shopRankListVo) {
		this.shopRankListVo = shopRankListVo;
	}

	public Map<String, String> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}

	public Map<String, String> getNameMap() {
		return nameMap;
	}

	public void setNameMap(Map<String, String> nameMap) {
		this.nameMap = nameMap;
	}

	public Map<String, String> getIdpsMap() {
		return idpsMap;
	}

	public void setIdpsMap(Map<String, String> idpsMap) {
		this.idpsMap = idpsMap;
	}

	public List<ShopRankRuleVo> getResult() {
		return result;
	}

	public void setResult(List<ShopRankRuleVo> result) {
		this.result = result;
	}

}
