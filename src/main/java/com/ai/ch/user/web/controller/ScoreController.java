package com.ai.ch.user.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.lexer.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.score.param.CtScoreKpiVo;
import com.ai.ch.user.web.vo.SupplierScoreVo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.web.model.ResponseData;

@RestController
@RequestMapping("/score")
public class ScoreController {
	
	private static final Log LOG = LogFactory.getLog(ScoreController.class);
	
	//跳转供货商评价管理
	@RequestMapping("/scorelist")
	public ModelAndView scorelist() {
		ModelAndView model = new ModelAndView("/jsp/score/scorelist"); 
		return model;
	}
	
	//获取供货商列表
	@RequestMapping("/getscorelist")
	@ResponseBody
	public ResponseData<PageInfo<SupplierScoreVo>> getscorelist(String tenentId) {
		PageInfo<SupplierScoreVo> pageInfo = new PageInfo<SupplierScoreVo>();
		pageInfo.setCount(5);
		pageInfo.setPageCount(5);
		pageInfo.setPageNo(1);
		pageInfo.setPageSize(5);
		List<SupplierScoreVo> list = new ArrayList<SupplierScoreVo>();
		for(int i=0;i<5;i++){
			SupplierScoreVo sl = new SupplierScoreVo();
			sl.setGroupName("yaxin");
			sl.setTenantId("ch");
			sl.setUserName("ww");
			sl.setTotalScore(60);
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
	public ModelAndView scorepage() {
		ModelAndView model = new ModelAndView("/jsp/score/scorepage"); 
		model.addObject("supplier_name", "wuda");
		model.addObject("company_name", "wuwer");
		List<CtScoreKpiVo> scoreKpiList = new ArrayList<CtScoreKpiVo>();
		for(int i=0;i<4;i++){
			CtScoreKpiVo ctScoreKpiVo = new CtScoreKpiVo();
			ctScoreKpiVo.setKpiName("商品质量");
			ctScoreKpiVo.setMinScore(0);
			ctScoreKpiVo.setMaxScore(40);
			ctScoreKpiVo.setKpiDesc("针对供货商的供货品质量进行评分");
			scoreKpiList.add(ctScoreKpiVo );
		}
		model.addObject("scoreKpiList", scoreKpiList);
		
		//调dubbo服务
		/*IScoreSV scoreSV = DubboConsumerFactory.getService("iScoreSV");
		QueryScoreKpiRequest queryScoreKpiRequest = new QueryScoreKpiRequest();
		queryScoreKpiRequest.setTenantId("ch");
		QueryScoreKpiResponse queryScoreKpiResponse = scoreSV.queryScoreKpi(queryScoreKpiRequest);
		model.addObject("scoreKpiList", queryScoreKpiResponse.getList());*/
		return model;
	}
	
	//供货商评价历史记录	
	@RequestMapping("/scorelog")
    public ModelAndView getScoreLog() {
        return new ModelAndView("/jsp/score/scorelog");
    }
}
