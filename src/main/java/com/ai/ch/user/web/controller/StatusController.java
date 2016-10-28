package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.OperateCode;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.util.PropertiesUtil;
import com.ai.ch.user.web.vo.StatusListVo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.ParseO2pDataUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/status")
public class StatusController {

	private static final Log log = LogFactory.getLog(ScoreController.class);
	
	@RequestMapping("/updateStatus")
	@ResponseBody
	public ResponseData<String> updateStatus(HttpServletRequest request,String companyState,String companyId){
		ResponseData<String> response = null;
		ResponseHeader header = null;
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", PropertiesUtil.getStringByKey("appkey"));
		map.put("companyState", companyState);
		map.put("companyId", companyId);
		String str ="";
		try {
			Long beginTime = System.currentTimeMillis();
			log.info("长虹修改账户状态服务开始"+beginTime);
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("updateCompanyState_http_url"), JSON.toJSONString(map), mapHeader);
			JSONObject data = ParseO2pDataUtil.getData(str);
			String resultCode = data.getString("resultCode");
			if (resultCode!=null&&!OperateCode.SUCCESS.equals(resultCode)){
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败"); 
			}
			else {
				//获取返回操作码
				String result = data.getString("result");
				if ("success".equals(result)){
					response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
					header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
				}
				else{
					response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "操作失败");
					header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败");
				}
				response.setResponseHeader(header);

			}
			log.info("长虹修改账户状态服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		} catch (IOException | URISyntaxException e) {
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
			header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败");
			response.setResponseHeader(header);
			e.printStackTrace();
		}
		
		return response;
	}
	
	@RequestMapping("/updateAudit")
	@ResponseBody
	public ResponseData<String> updateAudit(HttpServletRequest request,String companyId,String auditState){
		ResponseData<String> response = null;
		ResponseHeader header = null;
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", PropertiesUtil.getStringByKey("appkey"));
		map.put("openId", user.getUserId());
		map.put("auditState",auditState);
		map.put("companyId",companyId);
		String str ="";
		try {
			Long beginTime = System.currentTimeMillis();
			log.info("长虹修改审核状态服务开始"+beginTime);
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("updateCompanyState_http_url"), JSON.toJSONString(map), mapHeader);
			log.info("长虹修改审核状态服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("updateAuditState_http_url"), JSON.toJSONString(map), mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		JSONObject data = ParseO2pDataUtil.getData(str);
		String resultCode = data.getString("resultCode");
		if (resultCode!=null&&!OperateCode.SUCCESS.equals(resultCode)){
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
			header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败"); 
		}else {
			String result = data.getString("result");
			if ("success".equals(result)){
				response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
			}
			else{
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "操作失败");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败");
			}
			response.setResponseHeader(header);

		}
		return response;
	}
	
	@RequestMapping("/getList")
	@ResponseBody
	public ResponseData<PageInfo<StatusListVo>> getList(HttpServletRequest request,String companyName,String username, String companyType){
		ResponseData<PageInfo<StatusListVo>> response = null;
		PageInfo<StatusListVo> pageInfo =null;
		ResponseHeader header = null;
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", PropertiesUtil.getStringByKey("appkey"));
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
			Long beginTime = System.currentTimeMillis();
			log.info("长虹获取列表服务开始"+beginTime);
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("updateCompanyState_http_url"), JSON.toJSONString(map), mapHeader);
			log.info("长虹获取列表服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("searchCompanyList_http_url"), JSON.toJSONString(map),mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		try{
		JSONObject data = ParseO2pDataUtil.getData(str);
		String resultCode = data.getString("resultCode");
		if (resultCode!=null&&!OperateCode.SUCCESS.equals(resultCode)){
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
			header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败"); 
		}else {
				Integer pageNo = Integer.valueOf(data.getString("pages"));
				Integer pageSize = Integer.valueOf(data.getString("pageSize"));
				Integer total = Integer.valueOf(data.getString("total"));
				Integer pageCount = Integer.valueOf(data.getString("pageNum"));
				pageInfo = new PageInfo<>();
				pageInfo.setCount(total);
				pageInfo.setPageCount(pageCount);
				pageInfo.setPageNo(Integer.valueOf(pageNo));
				pageInfo.setPageSize(Integer.valueOf(pageSize));
				List<StatusListVo> responseList = new ArrayList<>();
				JSONArray list =(JSONArray) JSON.parseArray(data.getString("list"));
				Iterator<Object> iterator = list.iterator();
				while(iterator.hasNext()){
					 StatusListVo statusListVo = new StatusListVo(); 
					 JSONObject object = (JSONObject) iterator.next();
					 String stateValue = "";
					 if("0".equals(object.getString("companyState")))
						 stateValue = "正常";
					 else if("1".equals(object.getString("companyState")))
						 stateValue = "冻结";
					 else if("2".equals(object.getString("companyState")))
						 stateValue = "注销";
					 statusListVo.setUserId(object.getString("companyId"));
					 statusListVo.setUserName(object.getString("username"));
					 statusListVo.setGroupName(object.getString("name"));
					 statusListVo.setStateValue(stateValue);
					 statusListVo.setState(object.getString("companyState"));
					 responseList.add(statusListVo);
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
			//System.out.println(JSON.toJSONString(response));
		return response;
	}
}
