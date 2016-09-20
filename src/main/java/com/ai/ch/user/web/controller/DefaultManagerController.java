package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.ExceptionCode;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.vo.BusinessListInfo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.platform.common.api.sysuser.interfaces.ISysUserQuerySV;
import com.ai.platform.common.api.sysuser.param.SysUserQueryRequest;
import com.ai.platform.common.api.sysuser.param.SysUserQueryResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/defaultManager")
public class DefaultManagerController {
	
	@RequestMapping("/defaultPager")
    public ModelAndView billingPager() {
        return new ModelAndView("/jsp/defaultManager/defaultManagerList");
    }
	
	@RequestMapping("/defaultManagerPager")
	public ModelAndView defaultManager() {
		return new ModelAndView("/jsp/defaultManager/defaultManagerList");
	}
	
	@RequestMapping("/getDefaultManagerList")
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
				businessInfo.setUserName("defaultTest_"+i);
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
			defaultLogRequest.setOperName(userQueryResponse.getLoginName());
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
	
	//查询列表
	@RequestMapping("/getList")
	@ResponseBody
	public ResponseData<PageInfo<BusinessListInfo>> getList(HttpServletRequest request,String companyName,String username,String companyType){
		ResponseData<PageInfo<BusinessListInfo>> response = null;
		PageInfo<BusinessListInfo> pageInfo =null;
		ResponseHeader header = null;
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", "3a83ed361ebce978731b736328a97ea8");
		map.put("pageNo", request.getParameter("pageNo"));
		map.put("pageSize", request.getParameter("pageSize"));
		if(username!=null&&username.length()!=0)
			map.put("username", username);
		if(companyName!=null&&companyName.length()!=0)
			map.put("companyName", companyName);
		if(companyType!=null&&companyType.length()!=0)
			map.put("companyType", companyType);
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
			JSONObject responseHeader = (JSONObject) JSON.parse(data.getString("responseHeader"));
			//"SCORE02003".equals(responseHeader.getString("resultCode"))
			if(responseHeader!=null){
				response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.ISNULL, "查询为空");
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
	
}
