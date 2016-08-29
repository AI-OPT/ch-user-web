package com.ai.ch.user.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.defaultlog.interfaces.IDefaultLogSV;
import com.ai.ch.user.api.defaultlog.params.DefaultLogVo;
import com.ai.ch.user.api.defaultlog.params.InsertDefaultLogRequest;
import com.ai.ch.user.api.defaultlog.params.QueryDefaultLogRequest;
import com.ai.ch.user.api.defaultlog.params.QueryDefaultLogResponse;
import com.ai.ch.user.api.shopinfo.interfaces.IShopInfoSV;
import com.ai.ch.user.api.shopinfo.params.InsertShopInfoRequst;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoRequest;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoResponse;
import com.ai.ch.user.api.shopinfo.params.UpdateShopInfoRequest;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.ExceptionCode;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.vo.ShopManageVo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.platform.common.api.sysuser.interfaces.ISysUserQuerySV;
import com.ai.platform.common.api.sysuser.param.SysUserQueryRequest;
import com.ai.platform.common.api.sysuser.param.SysUserQueryResponse;
import com.alibaba.fastjson.JSON;

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
	
	@RequestMapping("/defaultManagerPager")
	public ModelAndView defaultManager() {
		return new ModelAndView("/jsp/defaultManager/defaultManagerList");
	}
	
	@RequestMapping("/addDefaultInfo")
	public ModelAndView addDefaultInfo(HttpServletRequest request,String userId,String userName,String custName) {
		Map<String, Object> model = new HashMap<String, Object>();
 		model.put("userId", userId);
 		model.put("userName", userName);
 		model.put("custName", custName);
		return new ModelAndView("/jsp/defaultManager/addDefault",model);
	}
	
	@RequestMapping("/saveDefaultInfo")
	@ResponseBody
	public ResponseData<String> saveDefaultInfo(HttpServletRequest request,DefaultLogVo defaultLogInfo) {
		IDefaultLogSV defaultLog = DubboConsumerFactory.getService("iDefaultLogSV");
		ISysUserQuerySV sysUserQuery = DubboConsumerFactory.getService("iSysUserQuerySV");
		ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;
        HttpSession session = request.getSession();
        GeneralSSOClientUser user = (GeneralSSOClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        InsertDefaultLogRequest defaultLogRequest = new InsertDefaultLogRequest();
        try{
        	SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        	sysUserQueryRequest.setTenantId(user.getTenantId());
        	sysUserQueryRequest.setLoginName(user.getLoginName());
        	SysUserQueryResponse  userQueryResponse = sysUserQuery.queryUserInfo(sysUserQueryRequest);
			BeanUtils.copyProperties(defaultLogRequest,defaultLogInfo);
			defaultLogRequest.setDeductDate(new Timestamp(new Date().getTime()));
			defaultLogRequest.setOperId(Long.parseLong(userQueryResponse.getNo()));
		    GeneralSSOClientUser userClient = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
			defaultLogRequest.setTenantId(userClient.getTenantId());
			defaultLog.insertDefaultLog(defaultLogRequest);
			responseData = new ResponseData<String>(ExceptionCode.SUCCESS_CODE, "操作成功", null);
            responseHeader = new ResponseHeader(true,ExceptionCode.SUCCESS_CODE, "操作成功");
        }catch(Exception e){
        	responseData = new ResponseData<String>(ExceptionCode.ERROR_CODE, "操作失败", null);
            responseHeader = new ResponseHeader(false,ExceptionCode.ERROR_CODE, "操作失败");
        }
        responseData.setResponseHeader(responseHeader);
        responseData.setData(JSON.toJSONString(defaultLogRequest));
        return responseData;
	}
	
	@RequestMapping("/defaultHistoryPager")
	public ModelAndView defaultHistoryPager(HttpServletRequest request,String userId,String userName,String custName) {
		Map<String, Object> model = new HashMap<String, Object>();
 		model.put("userId", userId);
 		model.put("userName", userName);
 		model.put("custName", custName);
		return new ModelAndView("/jsp/defaultManager/defaultHistoryList",model);
	}
	
	
	//获取供货商列表
	@RequestMapping("/getDefaultHistoryList")
	@ResponseBody
	public ResponseData<PageInfo<DefaultLogVo>> getDefaultHistoryList(HttpServletRequest request,QueryDefaultLogRequest defaultLogRequest) {
		ResponseData<PageInfo<DefaultLogVo>> responseData = null;
		try {
			IDefaultLogSV defaultLog = DubboConsumerFactory.getService("iDefaultLogSV");
			QueryDefaultLogResponse defaultLogResponse = defaultLog.queryDefaultLog(defaultLogRequest);
			if (defaultLogResponse != null && defaultLogResponse.getResponseHeader().isSuccess()) {
				PageInfo<DefaultLogVo> pageInfo = defaultLogResponse.getPageInfo();
				responseData = new ResponseData<PageInfo<DefaultLogVo>>(ChWebConstants.OperateCode.SUCCESS, "查询成功", pageInfo);
			} else {
				responseData = new ResponseData<PageInfo<DefaultLogVo>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<DefaultLogVo>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
		}
		return responseData;
	}
	
}
