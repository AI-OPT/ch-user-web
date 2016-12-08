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
	/**
	 * 店铺评级列表
	 */
	private ShopRankListVo shopRankListVo;
	/**
	 * 图片urlMap
	 */
	private Map<String, String> urlMap;
	/**
	 * 图片名称map
	 */
	private Map<String, String> nameMap;
	/**
	 * 图片idpsIdMap
	 */
	private Map<String, String> idpsMap;
	/**
	 * 评级对象集合
	 */
	private List<ShopRankRuleVo> result;
	/**
	 * 评级对象集合
	 */
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
