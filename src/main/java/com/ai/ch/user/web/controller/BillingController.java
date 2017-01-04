package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
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
import com.ai.ch.user.api.shopinfo.params.QueryShopDepositRequest;
import com.ai.ch.user.api.shopinfo.params.QueryShopDepositResponse;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoRequest;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoResponse;
import com.ai.ch.user.api.shopinfo.params.UpdateShopInfoRequest;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.OperateCode;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.util.PropertiesUtil;
import com.ai.ch.user.web.vo.BusinessListInfo;
import com.ai.ch.user.web.vo.ShopManageVo;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.ParseO2pDataUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.esotericsoftware.minlog.Log;
@RestController
@RequestMapping("/billing")
public class BillingController {

	private static final Logger log = LoggerFactory.getLogger(BillingController.class);

	@RequestMapping("/billingpager")
	public ModelAndView billingPager() {
		return new ModelAndView("/jsp/billing/billingList");
	}

	@RequestMapping("/marginsetting")
	public ModelAndView marginSetting(String userId, String username,String shopName) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("/jsp/billing/marginSetting");
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopDepositRequest queryShopDepositRequest = new QueryShopDepositRequest();
		queryShopDepositRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
		queryShopDepositRequest.setUserId(userId);
		
		Long beginTime = System.currentTimeMillis();
		log.info("查询店铺保证金服务开始"+beginTime);
		QueryShopDepositResponse response=shopInfoSV.queryShopDeposit(queryShopDepositRequest);
		log.info("查询店铺保证金服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		model.addObject("userName", URLDecoder.decode(username,"utf-8"));
		model.addObject("shopName", URLDecoder.decode(shopName,"utf-8"));
		model.addObject("deposit", response.getDepositBalance());
		model.addObject("userId", userId);
		
		return model;
	}

	@RequestMapping("/servicefeesetting")
	public ModelAndView serviceFeeSetting(String userId,String username,String shopName,HttpServletRequest request) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("/jsp/billing/serviceFeeSetting");
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest shopInfoRequest = new QueryShopInfoRequest();
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequest.setTenantId(user.getTenantId());
		shopInfoRequest.setUserId(userId);
		Long beginTime = System.currentTimeMillis();
		log.info("查询店铺信息服务开始"+beginTime);
		QueryShopInfoResponse shopInfoResponse = shopInfoSV.queryShopInfo(shopInfoRequest);
		log.info("查询店铺信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		
		String rentFeeStr="";
		String ratioStr="";
		
		if(shopInfoResponse.getRentFee()==null||shopInfoResponse.getRentFee()==0)
		{
			rentFeeStr="未设置";
		}else{
			if("Y".equals(shopInfoResponse.getRentCycleType())){
				rentFeeStr=shopInfoResponse.getRentFee()+"元/年";
			}
			if("Q".equals(shopInfoResponse.getRentCycleType())){
				rentFeeStr=shopInfoResponse.getRentFee()+"元/季度";
			}
			if("M".equals(shopInfoResponse.getRentCycleType())){
				rentFeeStr=shopInfoResponse.getRentFee()+"元/月";
			}
		}
		if(shopInfoResponse.getRatio()==null||shopInfoResponse.getRatio()==0){
			ratioStr="未设置";
		}
		else{
			ratioStr=shopInfoResponse.getRatio()+"%";
		}
		model.addObject("userName", URLDecoder.decode(username,"utf-8"));
		model.addObject("shopName", URLDecoder.decode(shopName,"utf-8"));
		model.addObject("rentFeeStr", rentFeeStr);
		model.addObject("ratioStr", ratioStr);
		model.addObject("userId", userId);
		return model;
	}
	
	@RequestMapping("/servicefee")
	public ModelAndView serviceFee(String userId,String username,String shopName,HttpServletRequest request) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("/jsp/billing/serviceFee");
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest shopInfoRequest = new QueryShopInfoRequest();
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequest.setTenantId(user.getTenantId());
		shopInfoRequest.setUserId(userId);
		long beginTime = System.currentTimeMillis();
		log.info("查询店铺信息服务开始"+beginTime);
		QueryShopInfoResponse shopInfoResponse = shopInfoSV.queryShopInfo(shopInfoRequest);
		log.info("查询店铺信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		
		String rentFeeStr="";
		String ratioStr="";
		String deposit="";
		if(shopInfoResponse.getRentFee()==null||shopInfoResponse.getRentFee()==0){
			rentFeeStr="未设置";
		}else{
			if("Y".equals(shopInfoResponse.getRentCycleType())){
			rentFeeStr=shopInfoResponse.getRentFee()+"元/年";
			}
			if("Q".equals(shopInfoResponse.getRentCycleType())){
				rentFeeStr=shopInfoResponse.getRentFee()+"元/季度";
			}
			if("M".equals(shopInfoResponse.getRentCycleType())){
				rentFeeStr=shopInfoResponse.getRentFee()+"元/月";
			}
		}
		if(shopInfoResponse.getRatio()==null||shopInfoResponse.getRatio()==0){
			ratioStr="未设置";
		}
		else{
			ratioStr=shopInfoResponse.getRatio()+"%";
		}
		if(shopInfoResponse.getDepositBalance()!=null){
			deposit =shopInfoResponse.getDepositBalance()+"元";
		}
		else{
			QueryShopDepositRequest queryShopDepositRequest = new QueryShopDepositRequest();
			queryShopDepositRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
			queryShopDepositRequest.setUserId(userId);
			
			QueryShopDepositResponse depositBalance = shopInfoSV.queryShopDeposit(queryShopDepositRequest);
			deposit = depositBalance.getDepositBalance()+"元";
		}
		
			model.addObject("userName", URLDecoder.decode(username,"utf-8"));
			model.addObject("shopName", URLDecoder.decode(shopName,"utf-8"));
			model.addObject("rentFeeStr", rentFeeStr);
			model.addObject("ratioStr", ratioStr);
			model.addObject("deposit", deposit);
			return model;
	}

	@RequestMapping("/savemarginsetting")
	@ResponseBody
	public ResponseData<String> saveMarginSetting(HttpServletRequest request) {
		ResponseData<String> response = new ResponseData<String>(ChWebConstants.OperateCode.SUCCESS, "成功");
		ResponseHeader responseHeader = null;
		BaseResponse updateResponse = null;
		UpdateShopInfoRequest shopInfoRequst = new UpdateShopInfoRequest();
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequst.setTenantId(user.getTenantId());
		//if(request.getParameter("userId")==null||"".equals(request.getParameter("userId")));
		shopInfoRequst.setUserId(request.getParameter("userId"));
		shopInfoRequst.setDepositBalance(Long.valueOf(request.getParameter("depositBalance")));
		try{
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		Long beginTime = System.currentTimeMillis();
		log.info("更新店铺信息服务开始"+beginTime);
		updateResponse = shopInfoSV.updateShopInfo(shopInfoRequst);
		log.info("更新店铺信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		responseHeader = updateResponse.getResponseHeader();
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
		BaseResponse updateResponse = null;
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequst.setTenantId(user.getTenantId());
		//if(request.getParameter("userId")==null||"".equals(request.getParameter("userId")));
		shopInfoRequst.setUserId(request.getParameter("userId"));
		if(shopInfoRequst.getRentFee()==null){
			shopInfoRequst.setRentFee(0L);
		}
		if(shopInfoRequst.getRatio()==null){
			shopInfoRequst.setRatio(0.0F);
		}
		try{
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		Long beginTime = System.currentTimeMillis();
		log.info("更新店铺信息服务开始"+beginTime);
		updateResponse = shopInfoSV.updateShopInfo(shopInfoRequst);
		log.info("更新店铺信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		responseHeader = updateResponse.getResponseHeader();
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
		Long beginTime = System.currentTimeMillis();
		log.info("查询店铺信息服务开始"+beginTime);
		QueryShopInfoResponse response = shopInfo.queryShopInfo(shopInfoRequest);
		log.info("查询店铺信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		Map<String, Object> model = new HashMap<String, Object>();
 		model.put("shopInfo", response);
 		try {
			model.put("userName", URLDecoder.decode(userName,"utf-8"));
			model.put("custName", URLDecoder.decode(custName,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			Log.error("操作发生错误,原因"+JSON.toJSONString(e));
		}
 		model.put("userId", userId);
		return new ModelAndView("/jsp/billing/billingCycle",model);
	}
	
	@RequestMapping("/saveCycleSetting")
	public ModelAndView saveCycleSetting(HttpServletRequest request,String userId,String periodType,String userName,String custName) {
		IShopInfoSV shopInfo = DubboConsumerFactory.getService("iShopInfoSV");
		/*String tenantId = user.getTenantId();
		shopInfoRequest.setUserId(userId);
		shopInfoRequest.setTenantId(tenantId);
		Long beginTime = System.currentTimeMillis();
		log.info("查询店铺信息服务开始"+beginTime);
		QueryShopInfoResponse response = shopInfo.queryShopInfo(shopInfoRequest);
		log.info("查询店铺信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		BaseResponse baseResponse=null;
		if("".equals(response.getUserId())||response.getUserId()==null){
			InsertShopInfoRequst insertShopInfo = new InsertShopInfoRequst();
			insertShopInfo.setTenantId(tenantId);
			insertShopInfo.setUserId(userId);
			insertShopInfo.setPeriodType(periodType);
			insertShopInfo.setStatus(0);
			Long insertBeginTime = System.currentTimeMillis();
			log.info("保存店铺信息服务开始"+insertBeginTime);
			baseResponse = shopInfo.insertShopInfo(insertShopInfo);
			log.info("保存店铺信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		}else{
		**/
		    BaseResponse baseResponse = null;
			UpdateShopInfoRequest updateShopInfoRequest = new UpdateShopInfoRequest();
			updateShopInfoRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
			updateShopInfoRequest.setUserId(userId);
			updateShopInfoRequest.setPeriodType(periodType);
			Long updateBeginTime = System.currentTimeMillis();
			log.info("更新店铺信息服务开始"+updateBeginTime);
			baseResponse = shopInfo.updateShopInfo(updateShopInfoRequest);
			log.info("更新店铺信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-updateBeginTime)+"毫秒");
		if(ExceptCodeConstants.Special.SUCCESS.equals(baseResponse.getResponseHeader().getResultCode())){
			return new ModelAndView("/jsp/billing/billingCyclesSccess");
		}else{
			return new ModelAndView("/jsp/billing/billingCyclesFail");
		}
	}
	
	@RequestMapping("/billingCycleDetail")
	public ModelAndView billingCycleDetail(HttpServletRequest request,String userId,String userName,String custName) {
		IShopInfoSV shopInfo = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest shopInfoRequest = new QueryShopInfoRequest();
		shopInfoRequest.setUserId(userId);
		 GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		shopInfoRequest.setTenantId(user.getTenantId());
		Long beginTime = System.currentTimeMillis();
		log.info("查询店铺信息服务开始"+beginTime);
		QueryShopInfoResponse response = shopInfo.queryShopInfo(shopInfoRequest);
		log.info("查询店铺信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		Map<String, Object> model = new HashMap<String, Object>();
 		model.put("shopInfo", response);
 		try {
			model.put("userName", URLDecoder.decode(userName,"utf-8"));
			model.put("custName", URLDecoder.decode(custName,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			Log.error("操作失败,错误原因"+JSON.toJSONString(e));
		}
		return new ModelAndView("/jsp/billing/billingCycleDetail",model);
	}
	
	//查询结算周期和违约管理列表
	@RequestMapping("/getList")
	@ResponseBody
	public ResponseData<PageInfo<BusinessListInfo>> getList(HttpServletRequest request,String companyName,String username,String companyType){
		ResponseData<PageInfo<BusinessListInfo>> response = null;
		PageInfo<BusinessListInfo> pageInfo =null;
		ResponseHeader header = null;
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", PropertiesUtil.getStringByKey("appkey"));
		map.put("pageNo", request.getParameter("pageNo"));
		map.put("pageSize", request.getParameter("pageSize"));
		if(username!=null&&username.length()!=0){
			map.put("username", username);
		}
		if(companyName!=null&&companyName.length()!=0){
			map.put("companyName", companyName);
		}
		if(companyType!=null&&companyType.length()!=0){
			map.put("companyType", companyType);
		}
		String str ="";
		try {
			Long beginTime = System.currentTimeMillis();
			log.info("长虹接口查询列表服务开始"+beginTime);
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("searchCompanyList_http_url"), JSON.toJSONString(map),mapHeader);
			log.info("长虹接口查询列表服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
			
		} catch (IOException | URISyntaxException e) {
			Log.error("操作失败,错误原因"+JSON.toJSONString(e));
		}
		try{
			JSONObject data = ParseO2pDataUtil.getData(str);
			String resultCode = data.getString("resultCode");
			if (resultCode!=null&&!OperateCode.SUCCESS.equals(resultCode)){
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
				header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "操作失败"); 
				response.setResponseHeader(header);
			}else{
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
						 businessListInfo.setUserId(object.getString("companyId"));
						 businessListInfo.setUserName(object.getString("username"));
						 businessListInfo.setCustName(object.getString("name"));
						 businessListInfo.setBusinessCategory(object.getString("commodityType"));
						 responseList.add(businessListInfo);
					}
					pageInfo.setResult(responseList);
					response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
					header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
				}
				response.setResponseHeader(header);
				response.setData(pageInfo);
		}catch(Exception e){
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "查询失败");
			header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "查询失败"); 
			response.setResponseHeader(header);
		}
		return response;
	}
	
	//查询保证金/服务费列表
	@RequestMapping("/getBillingList")
	@ResponseBody
	public ResponseData<PageInfo<ShopManageVo>> getBillingList(HttpServletRequest request,String companyName,String username,String companyType){
		ResponseData<PageInfo<ShopManageVo>> response = null;
		PageInfo<ShopManageVo> pageInfo =null;
		ResponseHeader header = null;
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", PropertiesUtil.getStringByKey("appkey"));
		map.put("pageNo", request.getParameter("pageNo"));
		map.put("pageSize", request.getParameter("pageSize"));
		if(username!=null&&username.length()!=0){
			map.put("username", username);
		}
		if(companyName!=null&&companyName.length()!=0){
			map.put("companyName", companyName);
		}
		if(companyType!=null&&companyType.length()!=0){
			map.put("companyType", companyType);
		}
		String str ="";
		try {
			Long beginTime = System.currentTimeMillis();
			log.info("长虹接口查询列表服务开始"+beginTime);
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("searchCompanyList_http_url"), JSON.toJSONString(map),mapHeader);
			log.info("长虹接口查询列表服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
			
		} catch (IOException | URISyntaxException e) {
			Log.error("操作失败,错误原因"+JSON.toJSONString(e));
		}
		try{
			JSONObject data = ParseO2pDataUtil.getData(str);
			String resultCode = data.getString("resultCode");
			if (resultCode!=null&&!OperateCode.SUCCESS.equals(resultCode)){
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
				header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "操作失败"); 
				response.setResponseHeader(header);
			}else {
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
						JSONObject object = (JSONObject) iterator.next();
						//查询应缴保证金
						IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
						QueryShopDepositRequest queryShopDepositRequest = new QueryShopDepositRequest();
						queryShopDepositRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
						queryShopDepositRequest.setUserId(object.getString("companyId"));
						QueryShopDepositResponse deposit=shopInfoSV.queryShopDeposit(queryShopDepositRequest);
						ShopManageVo shopManageVo = new ShopManageVo(); 
						shopManageVo.setUserId(object.getString("companyId"));
						shopManageVo.setCommodityType(object.getString("commodityType"));
						shopManageVo.setShopName(object.getString("name"));
						shopManageVo.setUserName(object.getString("username"));
						shopManageVo.setDeposit(deposit.getDepositBalance());
						responseList.add(shopManageVo);
					}
					pageInfo.setResult(responseList);
					response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
					header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
				}
				response.setResponseHeader(header);
				response.setData(pageInfo);
		}catch(Exception e){
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "查询失败");
			header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "查询失败"); 
			response.setResponseHeader(header);
		}
		return response;
	}
	
}
