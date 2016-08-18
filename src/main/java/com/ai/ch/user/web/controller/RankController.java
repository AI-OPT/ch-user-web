package com.ai.ch.user.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.rank.interfaces.IRankSV;
import com.ai.ch.user.api.rank.params.InsertRankRuleRequest;
import com.ai.ch.user.api.rank.params.QueryRankRuleRequest;
import com.ai.ch.user.api.rank.params.QueryRankRuleResponse;
import com.ai.ch.user.api.rank.params.ShopRankRuleVo;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;

@RestController
@RequestMapping("/rank")
public class RankController {

	private static final Log LOG = LogFactory.getLog(RankController.class);

	@RequestMapping("/rankrule")
	public ModelAndView rankRule() {
	    //调dubbo服务
	    IRankSV rankSV = DubboConsumerFactory.getService("iRankSV");
	    QueryRankRuleRequest queryRankRuleRequest = new QueryRankRuleRequest();
	    queryRankRuleRequest.setTenantId(ChWebConstants.Tenant.TENANT_ID);
	    QueryRankRuleResponse response = rankSV.queryRankRule(queryRankRuleRequest);
	    LOG.info("判断是否存在记录");
		if(response.getList().isEmpty())
			return new ModelAndView("/jsp/rank/rankrule");
		else
			return new ModelAndView("/jsp/rank/rankrule-edit");
	    }
	
		@RequestMapping("/saverule")
		public ResponseData<String> saveRule(HttpServletRequest request,InsertRankRuleRequest rankRuleRequest) {
		ResponseData<String> responseData = null;
		ResponseHeader responseHeader = null;
		//调dubbo服务
		try{
		IRankSV rankSV = DubboConsumerFactory.getService("iRankSV");
		//设置最大最小值
		rankRuleRequest.getList().get(0).setMinFee(Long.valueOf(request.getParameter("minFee")));
		rankRuleRequest.getList().get(rankRuleRequest.getList().size()-1).setMaxFee(Long.valueOf(request.getParameter("maxFee")));
		for (ShopRankRuleVo shopRankRuleVo : rankRuleRequest.getList()) {
			shopRankRuleVo.setTenantId(ChWebConstants.Tenant.TENANT_ID);
			shopRankRuleVo.setPeriodType(request.getParameter("periodType_"));
		}
		rankSV.insertRankRule(rankRuleRequest);
		responseHeader=new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
		responseData = new ResponseData<String>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
		}catch(Exception e){
			LOG.error("保存失败");
			responseHeader=new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "操作失败");
			responseData = new ResponseData<String>(ChWebConstants.OperateCode.Fail, "操作失败");
		}
		responseData.setResponseHeader(responseHeader);
		return responseData;
	}
	
	
}
