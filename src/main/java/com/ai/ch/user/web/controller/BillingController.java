package com.ai.ch.user.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.vo.ShopManageVo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.web.model.ResponseData;

@RestController
@RequestMapping("/billing")
public class BillingController {

	//private static final Logger log = LoggerFactory.getLogger(BillingController.class);

	@RequestMapping("/billingpager")
	public ModelAndView billingPager() {
		return new ModelAndView("/jsp/billing/billingList");
	}

	@RequestMapping("/marginsetting")
	public ModelAndView marginSetting() {
		ModelAndView model = new ModelAndView("/jsp/billing/marginSetting");
		model.addObject("userName", "长虹");
		model.addObject("shopName", "亚信");
		model.addObject("deposit", "66666");
		return model;
	}

	@RequestMapping("/servicefeesetting")
	public ModelAndView serviceFeeSetting() {
		ModelAndView model = new ModelAndView("/jsp/billing/serviceFeeSetting");
		model.addObject("userName", "长虹");
		model.addObject("shopName", "亚信");
		model.addObject("rentFee", "10000");
		model.addObject("rentFee", "10000");
		model.addObject("rentCycle", "年");
		model.addObject("ratio","50");
		return model;
	}
	
	@RequestMapping("/servicefee")
	public ModelAndView serviceFee() {
		return new ModelAndView("/jsp/billing/serviceFee");
	}

	@RequestMapping("/getbilllist")
	@ResponseBody
	public ResponseData<PageInfo<ShopManageVo>> getbillList() {
		ResponseData<PageInfo<ShopManageVo>> response = new ResponseData<PageInfo<ShopManageVo>>(
				ChWebConstants.OperateCode.SUCCESS, "成功");
		PageInfo<ShopManageVo> pageInfo = new PageInfo<ShopManageVo>();
		pageInfo.setCount(20);
		pageInfo.setPageCount(4);
		pageInfo.setPageNo(1);
		pageInfo.setPageSize(5);
		List<ShopManageVo> list = new ArrayList<ShopManageVo>();
		for (int i = 0; i < 5; i++) {
			ShopManageVo shopManageVo = new ShopManageVo();
			shopManageVo.setShopName("亚信");
			shopManageVo.setDeposit(1000L);
			shopManageVo.setUserId("111");
			shopManageVo.setUserName("长虹");
			shopManageVo.setBusiType("家电");
			list.add(shopManageVo);
		}
		pageInfo.setResult(list);
		response.setData(pageInfo);
		return response;
	}
}
