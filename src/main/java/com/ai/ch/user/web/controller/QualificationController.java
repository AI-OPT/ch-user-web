package com.ai.ch.user.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/qualification")
public class QualificationController {
	/**
	 * 供应商审核列表页面
	 */
	@RequestMapping("/toCheckedSupplierPager")
	public ModelAndView toCheckedSupplierPager() {
		return new ModelAndView("/jsp/qualification/supplier/checkedPagerList");
	}
	/**
	 * 供应商未审核列表页面
	 */
	@RequestMapping("/toNoCheckedSupplierPager")
	public ModelAndView toNoCheckedSupplierPager() {
		return new ModelAndView("/jsp/qualification/supplier/noCheckedPagerList");
	}
	/**
	 * 店铺审核列表
	 * @return
	 */
	@RequestMapping("/toCheckedShopPager")
	public ModelAndView toCheckedShopPager() {
		return new ModelAndView("/jsp/qualification/shop/checkedPagerList");
	}
	/**
	 * 店铺未审核列表
	 * @return
	 */
	@RequestMapping("/toNoCheckedShopPager")
	public ModelAndView toNoCheckedShopPager() {
		return new ModelAndView("/jsp/qualification/shop/noCheckedPagerList");
	}
	/**
	 * 供应商审核页面
	 * @return
	 */
	@RequestMapping("/toSuplierCheckPager")
	public ModelAndView toSuplierCheckPager() {
		return new ModelAndView("/jsp/qualification/supplier/auditeQualification");
	}
	/**
	 * 店铺审核页面
	 * @return
	 */
	@RequestMapping("/toShopCheckPager")
	public ModelAndView toShopCheckDetailPager() {
		return new ModelAndView("/jsp/qualification/shop/auditeQualification");
	}
	/**
	 * 供应商详情页面
	 * @return
	 */
	@RequestMapping("/toSuplierDetailPager")
	public ModelAndView toSuplierDetailPager() {
		return new ModelAndView("/jsp/qualification/supplier/checkedDetail");
	}
	/**
	 * 店铺详情页面
	 * @return
	 */
	@RequestMapping("/toShopDetailPager")
	public ModelAndView toShopDetailPager() {
		return new ModelAndView("/jsp/qualification/shop/checkedDetail");
	}
}
