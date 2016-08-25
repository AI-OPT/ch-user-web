package com.ai.ch.user.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.custfile.interfaces.ICustFileSV;
import com.ai.ch.user.api.custfile.params.CmCustFileExtVo;
import com.ai.ch.user.api.custfile.params.InsertCustFileExtRequest;
import com.ai.ch.user.api.custfile.params.QueryCustFileExtRequest;
import com.ai.ch.user.api.custfile.params.QueryCustFileExtResponse;
import com.ai.ch.user.api.custfile.params.UpdateCustFileExtRequest;
import com.ai.ch.user.api.rank.interfaces.IRankSV;
import com.ai.ch.user.api.rank.params.InsertRankRuleRequest;
import com.ai.ch.user.api.rank.params.QueryRankRuleRequest;
import com.ai.ch.user.api.rank.params.QueryRankRuleResponse;
import com.ai.ch.user.api.rank.params.ShopRankRuleVo;
import com.ai.ch.user.api.rank.params.UpdateRankRuleRequest;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.paas.ipaas.image.IImageClient;
import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/rank")
public class RankController {

	private static final Log LOG = LogFactory.getLog(RankController.class);

	@RequestMapping("/rankrule")
	public ModelAndView rankRule() {
		// 调dubbo服务
		IRankSV rankSV = DubboConsumerFactory.getService("iRankSV");
		QueryRankRuleRequest queryRankRuleRequest = new QueryRankRuleRequest();
		queryRankRuleRequest.setTenantId(ChWebConstants.Tenant.TENANT_ID);
		QueryRankRuleResponse response = rankSV.queryRankRule(queryRankRuleRequest);
		LOG.info("判断是否存在记录");
		if (response.getList().isEmpty())
			return new ModelAndView("/jsp/rank/rankrule");
		else {
			String periodType_ = "";
 			if ("Y".equals(response.getList().get(0).getPeriodType()))
				periodType_ = "年";
			if ("Q".equals(response.getList().get(0).getPeriodType()))
				periodType_ = "季度";
			if ("M".equals(response.getList().get(0).getPeriodType()))
				periodType_ = "月";
			ModelAndView model = new ModelAndView("/jsp/rank/rankrule-edit");
			List<String> urlList=getUrlList();
			model.addObject("periodType", periodType_);
			model.addObject("urlList", JSON.toJSONString(urlList));
			model.addObject("rank", response.getList().get(response.getList().size()-1).getRank());
			model.addObject("result", JSON.toJSONString(response.getList()));
			return model;
		}

	}

	@RequestMapping("/saverule")
	public ModelAndView saveRule(HttpServletRequest request, InsertRankRuleRequest rankRuleRequest) {
		ModelAndView view=null;
		try {
			String idpsns = "ch-user-web-idps";
	        // 获取imageClient
	        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
			Integer rank = rankRuleRequest.getList().get(rankRuleRequest.getList().size()-1).getRank();
			IRankSV rankSV = DubboConsumerFactory.getService("iRankSV");
			ICustFileSV custfileSV = DubboConsumerFactory.getService("iCustfileSV");
			MultipartHttpServletRequest file = (MultipartHttpServletRequest) request;
			List<CmCustFileExtVo> list = new ArrayList<CmCustFileExtVo>();
			InsertCustFileExtRequest custFileExtRequest = new InsertCustFileExtRequest();
			for(int i=1;i<=rank;i++){
				 CmCustFileExtVo cmCustFileExtVo = new CmCustFileExtVo(); 
			     MultipartFile image = file.getFile("img"+i);
			     String idpsId = im.upLoadImage(image.getBytes(), UUIDUtil.genId32() + ".png");
			     cmCustFileExtVo.setAttrValue(idpsId);
			     cmCustFileExtVo.setTenantId(ChWebConstants.COM_TENANT_ID);
			     cmCustFileExtVo.setAttrId(String.valueOf(i));
			     list.add(cmCustFileExtVo);
			}
			custFileExtRequest.setList(list);
			// 设置最大最小值
			rankRuleRequest.getList().get(0).setMinFee(Long.valueOf(request.getParameter("minFee")));
			rankRuleRequest.getList().get(rankRuleRequest.getList().size() - 1).setMaxFee(Long.valueOf(request.getParameter("maxFee")));
			for (ShopRankRuleVo shopRankRuleVo : rankRuleRequest.getList()) {
				shopRankRuleVo.setTenantId(ChWebConstants.Tenant.TENANT_ID);
				shopRankRuleVo.setPeriodType(request.getParameter("periodType_"));
			}
			// 调dubbo服务
			rankSV.insertRankRule(rankRuleRequest);
			custfileSV.insertCustFileExt(custFileExtRequest);
			view = new ModelAndView("/jsp/rank/saverulesuc");
		} catch (Exception e) {
			LOG.error("保存失败");
			view = new ModelAndView("/jsp/rank/saverulefai");
		}
		return view;
	}
	
	@RequestMapping("/updaterule")
	public ModelAndView updateRule(HttpServletRequest request, UpdateRankRuleRequest rankRuleRequest) {
		ModelAndView view=null;
		try {
			String idpsns = "ch-user-web-idps";
	        // 获取imageClient
	        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
			Integer rank = rankRuleRequest.getList().get(rankRuleRequest.getList().size()-1).getRank();
			IRankSV rankSV = DubboConsumerFactory.getService("iRankSV");
			ICustFileSV custfileSV = DubboConsumerFactory.getService("iCustfileSV");
			MultipartHttpServletRequest file = (MultipartHttpServletRequest) request;
			List<CmCustFileExtVo> list = new ArrayList<CmCustFileExtVo>();
			UpdateCustFileExtRequest custFileExtRequest = new UpdateCustFileExtRequest();
			for(int i=1;i<=rank;i++){
				 CmCustFileExtVo cmCustFileExtVo = new CmCustFileExtVo(); 
			     MultipartFile image = file.getFile("img"+i);
			     if(image.getSize()!=0){
			     String idpsId= im.upLoadImage(image.getBytes(), UUIDUtil.genId32() + ".png");
			     cmCustFileExtVo.setAttrValue(idpsId);
			     cmCustFileExtVo.setTenantId(ChWebConstants.COM_TENANT_ID);
			     cmCustFileExtVo.setAttrId(rank.toString());
			     list.add(cmCustFileExtVo);
			     }
			}
			custFileExtRequest.setList(list);
			// 设置最大最小值
			rankRuleRequest.getList().get(0).setMinFee(Long.valueOf(request.getParameter("minFee")));
			rankRuleRequest.getList().get(rankRuleRequest.getList().size() - 1).setMaxFee(Long.valueOf(request.getParameter("maxFee")));
			for (ShopRankRuleVo shopRankRuleVo : rankRuleRequest.getList()) {
				shopRankRuleVo.setTenantId(ChWebConstants.Tenant.TENANT_ID);
				shopRankRuleVo.setPeriodType(request.getParameter("periodType_"));
			}
			// 调dubbo服务
			rankRuleRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
			custFileExtRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
			rankSV.updateRankRule(rankRuleRequest);
			custfileSV.updateCustFileExt(custFileExtRequest);
			view = new ModelAndView("/jsp/rank/saverulesuc");
		} catch (Exception e) {
			LOG.error("更新失败");
			view = new ModelAndView("/jsp/rank/saverulefai");
		}
		return view;
	}
	
	
	//获取url
	public List<String> getUrlList(){
		String idpsns = "ch-user-web-idps";
	    // 获取imageClient
	    IImageClient im = IDPSClientFactory.getImageClient(idpsns);
		ICustFileSV custfileSV = DubboConsumerFactory.getService("iCustfileSV");
		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
		custFileExtRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
		QueryCustFileExtResponse response = custfileSV.queryCustFileExt(custFileExtRequest);
		List<String> list = new ArrayList<String>();
		if(!response.getList().isEmpty()){
			for (CmCustFileExtVo cmCustFileExtVo : response.getList()) {
				String url = im.getImageUrl(cmCustFileExtVo.getAttrValue(), ".jpg");
				list.add(url);
			}
		}
			return list;
	}
	
}
