package com.ai.ch.user.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.shopinfo.interfaces.IShopInfoSV;
import com.ai.ch.user.api.shopinfo.params.InsertShopInfoRequst;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoRequest;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoResponse;
import com.ai.ch.user.api.shopinfo.params.UpdateShopInfoRequest;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.vo.ShopManageVo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;

@RestController
@RequestMapping("/billing")
public class BillingController {

	//private static final Logger log = LoggerFactory.getLogger(BillingController.class);

	@RequestMapping("/billingpager")
	public ModelAndView billingPager() {
		return new ModelAndView("/jsp/billing/billingList");
	}

	@RequestMapping("/marginsetting")
	public ModelAndView marginSetting(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/jsp/billing/marginSetting");
		String url=request.getQueryString();
		String userId=url.substring(url.lastIndexOf("=")+1);
		QueryShopInfoRequest shopInfoRequest = new QueryShopInfoRequest();
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequest.setTenantId(user.getTenantId());
		shopInfoRequest.setUserId(userId);
		QueryShopInfoResponse shopInfoResponse = shopInfoSV.queryShopInfo(shopInfoRequest);
		Long deposit=0L;
		if(shopInfoResponse.getDepositBalance()!=null)
			deposit=shopInfoResponse.getDepositBalance();
		else{
			deposit=2000L;
		}
		model.addObject("userName", "长虹");
		model.addObject("shopName", "亚信");
		model.addObject("deposit", deposit);
		model.addObject("userId", userId);
		return model;
	}

	@RequestMapping("/servicefeesetting")
	public ModelAndView serviceFeeSetting(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/jsp/billing/serviceFeeSetting");
		String url=request.getQueryString();
		String userId=url.substring(url.lastIndexOf("=")+1);
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest shopInfoRequest = new QueryShopInfoRequest();
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequest.setTenantId(user.getUserId());
		shopInfoRequest.setUserId(userId);
		QueryShopInfoResponse shopInfoResponse = shopInfoSV.queryShopInfo(shopInfoRequest);
		String rentFeeStr="";
		String ratioStr="";
		if(shopInfoResponse.getRentFee()==null||shopInfoResponse.getRentFee()==0)
		{
			rentFeeStr="未设置";
		}else{
			if("Y".equals(shopInfoResponse.getRentCycleType()))
			rentFeeStr=shopInfoResponse.getRentFee()+"元/年";
			if("Q".equals(shopInfoResponse.getRentCycleType()))
				rentFeeStr=shopInfoResponse.getRentFee()+"元/季度";
			if("M".equals(shopInfoResponse.getRentCycleType()))
				rentFeeStr=shopInfoResponse.getRentFee()+"元/月";
		}
		if(shopInfoResponse.getRatio()==null||shopInfoResponse.getRatio()==0)
			ratioStr="未设置";
		else{
			ratioStr=shopInfoResponse.getRatio()+"%";
		}
		model.addObject("userName", "长虹");
		model.addObject("shopName", "亚信");
		model.addObject("rentFeeStr", rentFeeStr);
		model.addObject("ratioStr", ratioStr);
		model.addObject("userId", userId);
		return model;
	}
	
	@RequestMapping("/servicefee")
	public ModelAndView serviceFee(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/jsp/billing/serviceFee");
		String url=request.getQueryString();
		String userId=url.substring(url.lastIndexOf("=")+1);
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest shopInfoRequest = new QueryShopInfoRequest();
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequest.setTenantId(user.getTenantId());
		shopInfoRequest.setUserId(userId);
		QueryShopInfoResponse shopInfoResponse = shopInfoSV.queryShopInfo(shopInfoRequest);
		String rentFeeStr="";
		String ratioStr="";
		String deposit="";
		if(shopInfoResponse.getRentFee()==null||shopInfoResponse.getRentFee()==0){
			rentFeeStr="未设置";
		}else{
			if("Y".equals(shopInfoResponse.getRentCycleType()))
			rentFeeStr=shopInfoResponse.getRentFee()+"元/年";
			if("Q".equals(shopInfoResponse.getRentCycleType()))
				rentFeeStr=shopInfoResponse.getRentFee()+"元/季度";
			if("M".equals(shopInfoResponse.getRentCycleType()))
				rentFeeStr=shopInfoResponse.getRentFee()+"元/月";
		}
		if(shopInfoResponse.getRatio()==null||shopInfoResponse.getRatio()==0)
			ratioStr="未设置";
		else{
			ratioStr=shopInfoResponse.getRatio()+"%";
		}
		if(shopInfoResponse.getDepositBalance()!=null)
			deposit =shopInfoResponse.getDepositBalance()+"元";
		else
			deposit="1234元";
		model.addObject("rentFeeStr", rentFeeStr);
		model.addObject("ratioStr", ratioStr);
		model.addObject("deposit", deposit);
		model.addObject("userName", "长虹");
		model.addObject("shopName", "亚信");
		return model;
	}

	@RequestMapping("/getbilllist")
	@ResponseBody
	public ResponseData<PageInfo<ShopManageVo>> getbillList(HttpServletRequest request) {
		ResponseData<PageInfo<ShopManageVo>> response = new ResponseData<PageInfo<ShopManageVo>>(
				ChWebConstants.OperateCode.SUCCESS, "成功");
		PageInfo<ShopManageVo> pageInfo = new PageInfo<ShopManageVo>();
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest shopInfoRequest = new QueryShopInfoRequest();
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequest.setTenantId(user.getTenantId());
		shopInfoRequest.setUserId("1");
		QueryShopInfoResponse shopInfoResponse = shopInfoSV.queryShopInfo(shopInfoRequest);
		pageInfo.setCount(20);
		pageInfo.setPageCount(4);
		pageInfo.setPageNo(1);
		pageInfo.setPageSize(5);
		List<ShopManageVo> list = new ArrayList<ShopManageVo>();
		for (int i = 0; i < 5; i++) {
			ShopManageVo shopManageVo = new ShopManageVo();
			shopManageVo.setShopName("亚信");
			shopManageVo.setDeposit(shopInfoResponse.getDepositBalance());
			shopManageVo.setUserId("1");
			shopManageVo.setUserName("长虹");
			shopManageVo.setBusiType("家电");
			list.add(shopManageVo);
		}
		pageInfo.setResult(list);
		response.setData(pageInfo);
		return response;
	}
	
	@RequestMapping("/savemarginsetting")
	@ResponseBody
	public ResponseData<String> saveMarginSetting(HttpServletRequest request) {
		ResponseData<String> response = new ResponseData<String>(ChWebConstants.OperateCode.SUCCESS, "成功");
		ResponseHeader responseHeader = null;
		UpdateShopInfoRequest shopInfoRequst = new UpdateShopInfoRequest();
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequst.setTenantId(user.getTenantId());
		if(request.getParameter("userId")==null||"".equals(request.getParameter("userId")));
		shopInfoRequst.setUserId(request.getParameter("userId"));
		shopInfoRequst.setDepositBalance(Long.valueOf(request.getParameter("deposit")));
		try{
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		shopInfoSV.updateShopInfo(shopInfoRequst);
		responseHeader = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
		}catch(Exception e){
			responseHeader = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "操作失败");
		}
		response.setResponseHeader(responseHeader);
		return response;
	}
	
	@RequestMapping("/saveservicesetting")
	@ResponseBody
	public ResponseData<String> saveServiceSetting(HttpServletRequest request,UpdateShopInfoRequest shopInfoRequst) {
		ResponseData<String> response = new ResponseData<String>(ChWebConstants.OperateCode.SUCCESS, "成功");
		ResponseHeader responseHeader = null;
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequst.setTenantId(user.getTenantId());
		if(request.getParameter("userId")==null||"".equals(request.getParameter("userId")));
		shopInfoRequst.setUserId(request.getParameter("userId"));
		if(shopInfoRequst.getRentFee()==null)
			shopInfoRequst.setRentFee(0L);
		if(shopInfoRequst.getRatio()==null)
			shopInfoRequst.setRatio(0);
		try{
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		shopInfoSV.updateShopInfo(shopInfoRequst);
		responseHeader = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
		}catch(Exception e){
			responseHeader = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "操作失败");
		}
		response.setResponseHeader(responseHeader);
		return response;
	}
	
	@RequestMapping("/billingCyclePager")
	public ModelAndView billingCyclePager() {
		return new ModelAndView("/jsp/billing/billingCycleList");
	}
	
	@RequestMapping("/billingCycleSetting")
	public ModelAndView billingCycleSetting(HttpServletRequest request,String userId,String userName,String custName) {
		IShopInfoSV shopInfo = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest shopInfoRequest = new QueryShopInfoRequest();
		shopInfoRequest.setUserId(userId);
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequest.setTenantId(user.getTenantId());
		QueryShopInfoResponse response = shopInfo.queryShopInfo(shopInfoRequest);
		Map<String, Object> model = new HashMap<String, Object>();
 		model.put("shopInfo", response);
 		model.put("userName", userName);
 		model.put("custName", custName);
 		model.put("userId", userId);
		return new ModelAndView("/jsp/billing/billingCycle",model);
	}
	
	@RequestMapping("/saveCycleSetting")
	public ModelAndView saveCycleSetting(HttpServletRequest request,String userId,String periodType,String userName,String custName) {
		IShopInfoSV shopInfo = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest shopInfoRequest = new QueryShopInfoRequest();
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		String tenantId = user.getTenantId();
		shopInfoRequest.setUserId(userId);
		shopInfoRequest.setTenantId(tenantId);
		QueryShopInfoResponse response = shopInfo.queryShopInfo(shopInfoRequest);
		if("".equals(response.getUserId())||response.getUserId()==null){
			InsertShopInfoRequst insertShopInfo = new InsertShopInfoRequst();
			insertShopInfo.setTenantId(tenantId);
			insertShopInfo.setUserId(userId);
			insertShopInfo.setPeriodType(periodType);
			shopInfo.insertShopInfo(insertShopInfo);
		}else{
			UpdateShopInfoRequest updateShopInfoRequest = new UpdateShopInfoRequest();
			BeanUtils.copyProperties(updateShopInfoRequest, response);
			updateShopInfoRequest.setPeriodType(periodType);
			shopInfo.updateShopInfo(updateShopInfoRequest);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("periodType", periodType);
		model.put("userName", userName);
		model.put("custName", custName);
		return new ModelAndView("/jsp/billing/billingCycleDetail",model);
	}
	
	@RequestMapping("/billingCycleDetail")
	public ModelAndView billingCycleDetail(HttpServletRequest request,String userId,String userName,String custName) {
		IShopInfoSV shopInfo = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest shopInfoRequest = new QueryShopInfoRequest();
		shopInfoRequest.setUserId(userId);
		 GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequest.setTenantId(user.getTenantId());
		QueryShopInfoResponse response = shopInfo.queryShopInfo(shopInfoRequest);
		Map<String, Object> model = new HashMap<String, Object>();
 		model.put("shopInfo", response);
 		model.put("userName", userName);
 		model.put("custName", custName);
		return new ModelAndView("/jsp/billing/billingCycleDetail",model);
	}
}
