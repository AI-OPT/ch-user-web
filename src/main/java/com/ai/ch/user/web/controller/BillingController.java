package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.shopinfo.interfaces.IShopInfoSV;
import com.ai.ch.user.api.shopinfo.params.InsertShopInfoRequst;
import com.ai.ch.user.api.shopinfo.params.QueryShopDepositRequest;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoRequest;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoResponse;
import com.ai.ch.user.api.shopinfo.params.UpdateShopInfoRequest;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.ExceptionCode;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.vo.BusinessListInfo;
import com.ai.ch.user.web.vo.ShopManageVo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopDepositRequest queryShopDepositRequest = new QueryShopDepositRequest();
		queryShopDepositRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
		queryShopDepositRequest.setUserId(userId);
		Long deposit=shopInfoSV.queryShopDeposit(queryShopDepositRequest);
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
		shopInfoRequest.setTenantId(user.getTenantId());
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
		else{
			QueryShopDepositRequest queryShopDepositRequest = new QueryShopDepositRequest();
			queryShopDepositRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
			queryShopDepositRequest.setUserId(userId);
			Long depositBalance = shopInfoSV.queryShopDeposit(queryShopDepositRequest);
			deposit = depositBalance.toString()+"元";
		}
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
		QueryShopDepositRequest queryShopDepositRequest = new QueryShopDepositRequest();
		queryShopDepositRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
		queryShopDepositRequest.setUserId("00002");
		Long deposit = shopInfoSV.queryShopDeposit(queryShopDepositRequest);
		pageInfo.setCount(20);
		pageInfo.setPageCount(4);
		pageInfo.setPageNo(1);
		pageInfo.setPageSize(5);
		List<ShopManageVo> list = new ArrayList<ShopManageVo>();
		for (int i = 0; i < 5; i++) {
			ShopManageVo shopManageVo = new ShopManageVo();
			shopManageVo.setShopName("亚信");
			shopManageVo.setDeposit(deposit);
			shopManageVo.setUserId("00002");
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
		shopInfoRequst.setDepositBalance(Long.valueOf(request.getParameter("depositBalance")));
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
			shopInfoRequst.setRatio(0.0F);
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
	
	@RequestMapping("/getBillingCycleList")
	 @ResponseBody
	 public ResponseData<PageInfo<BusinessListInfo>> getBillingCycleList(HttpServletRequest request) {
		 ResponseData<PageInfo<BusinessListInfo>> responseData = null;
		 try {
			 PageInfo<BusinessListInfo> pageInfo = new PageInfo<BusinessListInfo>();
			 pageInfo.setCount(5);
			 pageInfo.setPageCount(1);
			 pageInfo.setPageNo(1);
			 pageInfo.setPageSize(5);
			 List<BusinessListInfo> list = new ArrayList<BusinessListInfo>();
			 for(int i=0;i<5;i++){
				BusinessListInfo businessInfo = new BusinessListInfo();
				businessInfo.setUserId(i+"");
				businessInfo.setUserName("cycleTest_"+i);
				businessInfo.setCustName("custNameTest_"+i);
				businessInfo.setUserType(ChWebConstants.CONTRACT_TYPE_SHOP);
				businessInfo.setBusinessCategory("usiness"+i);
				list.add(businessInfo);
			 }
			 pageInfo.setResult(list);
			 responseData = new ResponseData<PageInfo<BusinessListInfo>>(ChWebConstants.OperateCode.SUCCESS, "查询成功", pageInfo);
			} catch (Exception e) {
				e.printStackTrace();
				responseData = new ResponseData<PageInfo<BusinessListInfo>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
			}
      return responseData;
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
			insertShopInfo.setStatus(0);
			shopInfo.insertShopInfo(insertShopInfo);
		}else{
			UpdateShopInfoRequest updateShopInfoRequest = new UpdateShopInfoRequest();
			BeanUtils.copyProperties(updateShopInfoRequest, response);
			updateShopInfoRequest.setPeriodType(periodType);
			shopInfo.updateShopInfo(updateShopInfoRequest);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("periodType", periodType);
		model.put("shopInfo", response);
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
	
	//查询结算周期和违约管理列表
	@RequestMapping("/getList")
	@ResponseBody
	public ResponseData<PageInfo<BusinessListInfo>> getList(HttpServletRequest request){
		ResponseData<PageInfo<BusinessListInfo>> response = null;
		PageInfo<BusinessListInfo> pageInfo =null;
		ResponseHeader header = null;
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", "3a83ed361ebce978731b736328a97ea8");
		map.put("pageNo", "1");
		map.put("pageSize", "10");
		//map.put("companyType", "1");
		//map.put("auditState", "1");
		//map.put("username", "ac_PgU9g");
		map.put("companyName", "长");
		String str ="";
		try {
			str = HttpClientUtil.sendPost("http://10.19.13.16:28151/opaas/http/srv_up_user_searchcompanylist_qry", JSON.toJSONString(map),mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		JSONObject json = JSON.parseObject(str);
		if (!"000000".equals(json.getString("resultCode"))){
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
			header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败"); 
		}
		else {
			//获取返回操作码
			JSONObject data = (JSONObject) JSON.parse(json.getString("data"));
			String result = data.getString("success");
			if ("false".equals(result)){
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "操作失败");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败");
			}
			else{
				Integer pageNo = Integer.valueOf(data.getString("pages"));
				Integer pageSize = Integer.valueOf(data.getString("pageSize"));
				Integer total = Integer.valueOf(data.getString("total"));
				Integer pageCount = Integer.valueOf(data.getString("pageNum"));
				pageInfo = new PageInfo<>();
				pageInfo.setCount(total);
				pageInfo.setPageCount(pageCount);
				pageInfo.setPageNo(pageNo);
				pageInfo.setPageSize(pageSize);
				List<BusinessListInfo> responseList = new ArrayList<>();
				JSONArray list =(JSONArray) JSON.parseArray(data.getString("list"));
				Iterator<Object> iterator = list.iterator();
				while(iterator.hasNext()){
					BusinessListInfo businessListInfo = new BusinessListInfo(); 
					 JSONObject object = (JSONObject) iterator.next();
					 businessListInfo.setUserId(object.getString("uid"));
					 businessListInfo.setUserName(object.getString("username"));
					 businessListInfo.setCustName(object.getString("name"));
					 businessListInfo.setBusinessCategory("无数据");
					 responseList.add(businessListInfo);
				}
				pageInfo.setResult(responseList);
				response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
			}
			response.setResponseHeader(header);
			response.setData(pageInfo);
		}
		return response;
	}
	
	//查询保证金/服务费列表
	@RequestMapping("/getBillingList")
	@ResponseBody
	public ResponseData<PageInfo<ShopManageVo>> getBillingList(HttpServletRequest request){
		ResponseData<PageInfo<ShopManageVo>> response = null;
		PageInfo<ShopManageVo> pageInfo =null;
		ResponseHeader header = null;
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", "3a83ed361ebce978731b736328a97ea8");
		map.put("pageNo", "1");
		map.put("pageSize", "10");
		//map.put("companyType", "1");
		//map.put("auditState", "1");
		//map.put("username", "ac_PgU9g");
		map.put("companyName", "长");
		String str ="";
		try {
			str = HttpClientUtil.sendPost("http://10.19.13.16:28151/opaas/http/srv_up_user_searchcompanylist_qry", JSON.toJSONString(map),mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		JSONObject json = JSON.parseObject(str);
		if (!"000000".equals(json.getString("resultCode"))){
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
			header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败"); 
		}
		else {
			//获取返回操作码
			JSONObject data = (JSONObject) JSON.parse(json.getString("data"));
			String result = data.getString("success");
			if ("false".equals(result)){
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "操作失败");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败");
			}
			else{
				Integer pageNo = Integer.valueOf(data.getString("pages"));
				Integer pageSize = Integer.valueOf(data.getString("pageSize"));
				Integer total = Integer.valueOf(data.getString("total"));
				Integer pageCount = Integer.valueOf(data.getString("pageNum"));
				pageInfo = new PageInfo<>();
				pageInfo.setCount(total);
				pageInfo.setPageCount(pageCount);
				pageInfo.setPageNo(pageNo);
				pageInfo.setPageSize(pageSize);
				List<ShopManageVo> responseList = new ArrayList<>();
				JSONArray list =(JSONArray) JSON.parseArray(data.getString("list"));
				Iterator<Object> iterator = list.iterator();
				while(iterator.hasNext()){
					ShopManageVo shopManageVo = new ShopManageVo(); 
					 JSONObject object = (JSONObject) iterator.next();
					 shopManageVo.setUserId(object.getString("uid"));
					 shopManageVo.setBusiType("无数据");
					 shopManageVo.setShopName(object.getString("name"));
					 shopManageVo.setUserName(object.getString("username"));
					 shopManageVo.setDeposit(0L);
					 responseList.add(shopManageVo);
				}
				pageInfo.setResult(responseList);
				response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
			}
			response.setResponseHeader(header);
			response.setData(pageInfo);
		}
		return response;
	}
}
