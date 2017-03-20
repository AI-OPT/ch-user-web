
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.score.interfaces.IScoreSV;
import com.ai.ch.user.api.score.param.CountScoreAvgRequest;
import com.ai.ch.user.api.score.param.CountScoreAvgResponse;
import com.ai.ch.user.api.score.param.InsertCurrentScoreRequest;
import com.ai.ch.user.api.score.param.QueryScoreKpiResponse;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.OperateCode;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.util.PropertiesUtil;
import com.ai.ch.user.web.vo.SupplierScoreVo;
import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.ParseO2pDataUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 供应商评分controller
 * @author Zh
 *
 */
@RestController
@RequestMapping("/score")
public class ScoreController {
	
	private static final Log LOG = LogFactory.getLog(ScoreController.class);
	
	/**
	 * 跳转供货商评价管理
	 * @return
	 */
	@RequestMapping("/scorelist")
	public ModelAndView scoreList() {
		ModelAndView model = new ModelAndView("/jsp/crm/scorelist"); 
		return model;
	}
	
	/**
	 * 评价供货商页面
	 * @param username
	 * @param userId
	 * @param shopName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/scorepage")
	public ModelAndView scorePage(String username,String userId,String shopName) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("/jsp/crm/scorepage"); 
		model.addObject("supplier_name", URLDecoder.decode(username,"utf-8"));
		model.addObject("company_name", URLDecoder.decode(shopName,"utf-8"));
		model.addObject("userId", userId);
		//调dubbo服务
		IScoreSV scoreSV = DubboConsumerFactory.getService("iScoreSV");
		QueryScoreKpiRequest queryScoreKpiRequest = new QueryScoreKpiRequest();
		queryScoreKpiRequest.setTenantId("changhong");
		QueryScoreKpiResponse queryScoreKpiResponse = scoreSV.queryScoreKpi(queryScoreKpiRequest);
		model.addObject("scoreKpiList", queryScoreKpiResponse.getList());
		return model;
	}
	
	/**
	 * 供货商评价历史记录	
	 * @return
	 */
	@RequestMapping("/scorelog")
    public ModelAndView getScoreLog() {
        return new ModelAndView("/jsp/crm/scorelog");
    }
	
	/**
	 * 提交供货商评价
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping("/savescore")
	public ResponseData<String> saveScore(HttpServletRequest request,String userId) {
		ResponseData<String> response = new ResponseData<String>(null, null);
		ResponseHeader responseHeader = null;
		InsertCurrentScoreRequest currentScoreRequest = new InsertCurrentScoreRequest();
		//调dubbo服务
		//tenantId
		String tenantId =ChWebConstants.COM_TENANT_ID;
		//操作员ID
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		LOG.error("++++++++获取封装用户信息"+JSON.toJSONString(user));
		String operId = user.getUserId();
		//总分
		Integer totalScore =0;
		for(int i=1;i<=4;i++){
			totalScore+=Integer.valueOf(request.getParameter(String.valueOf(i)).toString());
		}
		//评分指标
		IScoreSV scoreSV = DubboConsumerFactory.getService("iScoreSV");
		currentScoreRequest.setTenantId(tenantId);
		currentScoreRequest.setOperId(Long.valueOf(operId));
		currentScoreRequest.setUserId(userId);
		currentScoreRequest.setTotalScore(totalScore);
		currentScoreRequest.setScore1(Integer.valueOf(request.getParameter(String.valueOf(1)).toString()));
		currentScoreRequest.setScore2(Integer.valueOf(request.getParameter(String.valueOf(2)).toString()));
		currentScoreRequest.setScore3(Integer.valueOf(request.getParameter(String.valueOf(3)).toString()));
		currentScoreRequest.setScore4(Integer.valueOf(request.getParameter(String.valueOf(4)).toString()));
		try{
			scoreSV.insertCurrentScore(currentScoreRequest);
			response.setStatusCode(ChWebConstants.OperateCode.SUCCESS);
			response.setStatusInfo("操作成功");
			responseHeader = new ResponseHeader(true,ChWebConstants.OperateCode.SUCCESS,"操作成功");
		}catch(BusinessException e){
		response.setStatusCode(ChWebConstants.OperateCode.Fail);
		response.setStatusInfo("操作失败");
		responseHeader = new ResponseHeader(false,ChWebConstants.OperateCode.Fail,"操作失败");
		LOG.error("保存失败",e);
		}
		response.setResponseHeader(responseHeader);
		return response;
	}
	
	/**
	 * 获取列表信息
	 * @param request
	 * @param companyName
	 * @param username
	 * @param companyType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public ResponseData<PageInfo<SupplierScoreVo>> getList(HttpServletRequest request,String companyName,String username,String companyType)throws Exception{
		ResponseData<PageInfo<SupplierScoreVo>> response = null;
		PageInfo<SupplierScoreVo> pageInfo =null;
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
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("searchCompanyList_http_url"), JSON.toJSONString(map),mapHeader);
		} catch (IOException | URISyntaxException e) {
			LOG.error("操作失败"+JSON.toJSONString(e));
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
				pageInfo.setPageNo(pageNo);
				pageInfo.setPageSize(pageSize);
				List<SupplierScoreVo> responseList = new ArrayList<>();
				JSONArray list =(JSONArray) JSON.parseArray(data.getString("list"));
				Iterator<Object> iterator = list.iterator();
				while(iterator.hasNext()){
					SupplierScoreVo supplierScoreVo = new SupplierScoreVo(); 
					
					 JSONObject object = (JSONObject) iterator.next();
					 //查询综合分
					 IScoreSV scoreSV = DubboConsumerFactory.getService("iScoreSV");
					 GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
					 	CountScoreAvgRequest scoreAvgRequest = new CountScoreAvgRequest();
					 	scoreAvgRequest.setTenantId(user.getTenantId());
					 	scoreAvgRequest.setUserId(object.getString("companyId"));
						CountScoreAvgResponse avgScore = scoreSV.countScoreAvg(scoreAvgRequest);
					 supplierScoreVo.setUserId(object.getString("companyId"));
					 supplierScoreVo.setUserName(object.getString("username"));
					 supplierScoreVo.setGroupName(object.getString("name"));
					 supplierScoreVo.setTotalScore((int)avgScore.getScoreAvg());
					 responseList.add(supplierScoreVo);
				}
				pageInfo.setResult(responseList);
				response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
			}
			response.setResponseHeader(header);
			response.setData(pageInfo);
		}catch(BusinessException e){
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "查询失败");
				header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "查询失败"); 
				response.setResponseHeader(header);
			}
		return response;
	}
	
}
