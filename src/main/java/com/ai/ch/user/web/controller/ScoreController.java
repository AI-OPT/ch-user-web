package com.ai.ch.user.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.score.interfaces.IScoreSV;
import com.ai.ch.user.api.score.param.CountScoreAvgRequest;
import com.ai.ch.user.api.score.param.InsertCurrentScoreRequest;
import com.ai.ch.user.api.score.param.InsertScoreLogRequest;
import com.ai.ch.user.api.score.param.QueryScoreKpiRequest;
import com.ai.ch.user.api.score.param.QueryScoreKpiResponse;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.vo.SupplierScoreVo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;

@RestController
@RequestMapping("/score")
public class ScoreController {
	
	private static final Log LOG = LogFactory.getLog(ScoreController.class);
	
	//跳转供货商评价管理
	@RequestMapping("/scorelist")
	public ModelAndView scoreList() {
		ModelAndView model = new ModelAndView("/jsp/score/scorelist"); 
		return model;
	}
	
	//获取供货商列表
	@RequestMapping("/getscorelist")
	@ResponseBody
	public ResponseData<PageInfo<SupplierScoreVo>> getScoreList(HttpServletRequest request) {
		PageInfo<SupplierScoreVo> pageInfo = new PageInfo<SupplierScoreVo>();
		pageInfo.setCount(20);
		pageInfo.setPageCount(4);
		pageInfo.setPageNo(1);
		pageInfo.setPageSize(5);
		IScoreSV scoreSV = DubboConsumerFactory.getService("iScoreSV");
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		CountScoreAvgRequest scoreAvgRequest = new CountScoreAvgRequest();
		scoreAvgRequest.setTenantId(user.getTenantId());
		scoreAvgRequest.setUserId("1");
		float avgScore = scoreSV.countScoreAvg(scoreAvgRequest);
		List<SupplierScoreVo> list = new ArrayList<SupplierScoreVo>();
		for(int i=0;i<5;i++){
			SupplierScoreVo sl = new SupplierScoreVo();
			sl.setGroupName("yaxin");
			sl.setTenantId("changhong");
			sl.setUserName("ww");
			sl.setTotalScore(Integer.valueOf((int)avgScore));
			sl.setUserId("1234567");
			list.add(sl);
		}
		pageInfo.setResult(list);
		ResponseData<PageInfo<SupplierScoreVo>> response = new ResponseData<PageInfo<SupplierScoreVo>>("000000", "1");
		response.setData(pageInfo);
		return response;
	}
	
	//评价供货商页面
	@RequestMapping("/scorepage")
	public ModelAndView scorePage(HttpServletRequest request,String userId) {
		ModelAndView model = new ModelAndView("/jsp/score/scorepage"); 
		model.addObject("supplier_name", "wuda");
		model.addObject("company_name", "wuwer");
		
		//调dubbo服务
		IScoreSV scoreSV = DubboConsumerFactory.getService("iScoreSV");
		QueryScoreKpiRequest queryScoreKpiRequest = new QueryScoreKpiRequest();
		queryScoreKpiRequest.setTenantId("changhong");
		QueryScoreKpiResponse queryScoreKpiResponse = scoreSV.queryScoreKpi(queryScoreKpiRequest);
		model.addObject("scoreKpiList", queryScoreKpiResponse.getList());
		return model;
	}
	
	//供货商评价历史记录	
	@RequestMapping("/scorelog")
    public ModelAndView getScoreLog() {
        return new ModelAndView("/jsp/score/scorelog");
    }
	
	//提交供货商评价
	@RequestMapping("/savescore")
	public ResponseData<String> saveScore(HttpServletRequest request) {
		ResponseData<String> response = new ResponseData<String>(null, null);
		ResponseHeader responseHeader = null;
		InsertCurrentScoreRequest currentScoreRequest = new InsertCurrentScoreRequest();
		InsertScoreLogRequest scoreLogRequest = new InsertScoreLogRequest();
		//调dubbo服务
		//tenantId
		String tenantId ="changhong";
		//供货商ID
		String userId = "1";
		//操作员ID
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		String operId = user.getUserId();
		//总分
		Integer totalScore =0;
		for(int i=1;i<=4;i++)
			totalScore+=Integer.valueOf(request.getParameter(String.valueOf(i)).toString());
		//评分指标
		scoreLogRequest.setScore1(Integer.valueOf(request.getParameter(String.valueOf(1)).toString()));
		scoreLogRequest.setScore2(Integer.valueOf(request.getParameter(String.valueOf(2)).toString()));
		scoreLogRequest.setScore3(Integer.valueOf(request.getParameter(String.valueOf(3)).toString()));
		scoreLogRequest.setScore4(Integer.valueOf(request.getParameter(String.valueOf(4)).toString()));
		IScoreSV scoreSV = DubboConsumerFactory.getService("iScoreSV");
		currentScoreRequest.setTenantId(tenantId);
		currentScoreRequest.setOperId(Long.valueOf(operId));
		currentScoreRequest.setUserId(userId);
		currentScoreRequest.setTotalScore(totalScore);
		scoreLogRequest.setTenantId(tenantId);
		scoreLogRequest.setOperId(Long.valueOf(operId));
		scoreLogRequest.setUserId(userId);
		scoreLogRequest.setTotalScore(totalScore);
		try{
			scoreSV.insertCurrentScore(currentScoreRequest);
			scoreSV.insertScoreLog(scoreLogRequest);
			response.setStatusCode(ChWebConstants.OperateCode.SUCCESS);
			response.setStatusInfo("操作成功");
			responseHeader = new ResponseHeader(true,ChWebConstants.OperateCode.SUCCESS,"操作成功");
		}catch(Exception e){
		response.setStatusCode(ChWebConstants.OperateCode.Fail);
		response.setStatusInfo("操作失败");
		responseHeader = new ResponseHeader(false,ChWebConstants.OperateCode.Fail,"操作失败");
		LOG.error("保存失败");
		}
		response.setResponseHeader(responseHeader);
		return response;
	}
	
}
