package com.ai.ch.user.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.image.IImageClient;
import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/rank")
public class RankController {

	private static final Log LOG = LogFactory.getLog(RankController.class);

	@RequestMapping("/rankrule")
	public ModelAndView rankRule(HttpServletRequest request) {
		// 调dubbo服务
		IRankSV rankSV = DubboConsumerFactory.getService("iRankSV");
		QueryRankRuleRequest queryRankRuleRequest = new QueryRankRuleRequest();
		queryRankRuleRequest.setTenantId(ChWebConstants.Tenant.TENANT_ID);
		QueryRankRuleResponse response = rankSV.queryRankRule(queryRankRuleRequest);
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		LOG.info("判断是否存在记录");
		if (response.getList().isEmpty())
			return new ModelAndView("/jsp/crm/rankrule");
		else {
			String periodType_ = "";
 			if ("Y".equals(response.getList().get(0).getPeriodType()))
				periodType_ = "年";
			if ("Q".equals(response.getList().get(0).getPeriodType()))
				periodType_ = "季度";
			if ("M".equals(response.getList().get(0).getPeriodType()))
				periodType_ = "月";
			ModelAndView model = new ModelAndView("/jsp/crm/rankrule-edit");
			Map<String,String> urlMap=getUrlMap(user.getTenantId());
			Map<String,String> nameMap=getNameMap(user.getTenantId());
			Map<String,String> idpsMap=getIdpsMap(user.getTenantId());
			model.addObject("periodType", periodType_);
			model.addObject("urlMap", JSON.toJSONString(urlMap));
			model.addObject("nameMap", JSON.toJSONString(nameMap));
			model.addObject("idpsMap", JSON.toJSONString(idpsMap));
			model.addObject("result", JSON.toJSONString(response.getList()));
			model.addObject("rank", response.getList().get(response.getList().size()-1).getRank());
			//截取中间数据
			StringBuilder middle=new StringBuilder("{\"result\":[");
			if(response.getList().size()>2){
				for (int index = 1; index <= response.getList().size()-2; index++) {
					middle.append(JSON.toJSONString(response.getList().get(index)));
					middle.append(",");
				}
				String str= middle.substring(0, middle.length()-1);
				str+="]}";
				model.addObject("data",str);
				//System.out.println(JSON.toJSONString(str));
			}else
				model.addObject("data","[{\"result\":[{}]}");
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
			//获取登陆用户信息
			GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
			for(int i=1;i<=rank;i++){
				 CmCustFileExtVo cmCustFileExtVo = new CmCustFileExtVo(); 
			     MultipartFile image = file.getFile("img"+i);
			     String idpsId = im.upLoadImage(image.getBytes(), UUIDUtil.genId32() + ".png");
			     cmCustFileExtVo.setAttrValue(idpsId);
			     cmCustFileExtVo.setTenantId(user.getTenantId());
			     cmCustFileExtVo.setAttrId(String.valueOf(i));
			     cmCustFileExtVo.setInfoName(request.getParameter("rankName"+i));
			     rankRuleRequest.getList().get(i-1).setRankLogo(idpsId);
			     list.add(cmCustFileExtVo);
			}
			custFileExtRequest.setList(list);
			// 设置最大最小值
			rankRuleRequest.getList().get(rankRuleRequest.getList().size() - 1).setMaxScore(Long.valueOf(request.getParameter("maxScore")));
			for (ShopRankRuleVo shopRankRuleVo : rankRuleRequest.getList()) {
				shopRankRuleVo.setTenantId(ChWebConstants.Tenant.TENANT_ID);
				shopRankRuleVo.setPeriodType(request.getParameter("periodType_"));
				shopRankRuleVo.setOperId(Long.valueOf(user.getUserId()));
				shopRankRuleVo.setRankName(shopRankRuleVo.getRankName().trim());
			}
			rankRuleRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
			// 调dubbo服务
			rankSV.insertRankRule(rankRuleRequest);
			custfileSV.insertCustFileExt(custFileExtRequest);
			view = new ModelAndView("/jsp/crm/success");
		} catch (Exception e) {
			LOG.error("保存失败");
			view = new ModelAndView("/jsp/crm/fail");
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
			GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
			for(int i=1;i<=rank;i++){
				 CmCustFileExtVo cmCustFileExtVo = new CmCustFileExtVo(); 
			     MultipartFile image = file.getFile("img"+i);
			     if(image.getSize()!=0){
			     String idpsId= im.upLoadImage(image.getBytes(), UUIDUtil.genId32() + ".png");
			     cmCustFileExtVo.setAttrValue(idpsId);
			     cmCustFileExtVo.setTenantId(user.getTenantId());
			     cmCustFileExtVo.setInfoName(request.getParameter("rankName"+i));
			     cmCustFileExtVo.setAttrId(String.valueOf(i));
			     rankRuleRequest.getList().get(i-1).setRankLogo(idpsId);
			     list.add(cmCustFileExtVo);
			     }
			}
			custFileExtRequest.setList(list);
			//设置最大值
			rankRuleRequest.getList().get(rankRuleRequest.getList().size() - 1).setMaxScore(Long.valueOf(request.getParameter("maxScore")));
			for (ShopRankRuleVo shopRankRuleVo : rankRuleRequest.getList()) {
				shopRankRuleVo.setTenantId(ChWebConstants.Tenant.TENANT_ID);
				shopRankRuleVo.setPeriodType(request.getParameter("periodType_"));
				shopRankRuleVo.setOperId(Long.valueOf(user.getUserId()));
				shopRankRuleVo.setRankName(shopRankRuleVo.getRankName().trim());
			}
			// 调dubbo服务
			rankRuleRequest.setTenantId(user.getTenantId());
			custFileExtRequest.setTenantId(user.getTenantId());
			rankSV.updateRankRule(rankRuleRequest);
			custfileSV.updateCustFileExt(custFileExtRequest);
			view = new ModelAndView("/jsp/crm/success");
		} catch (Exception e) {
			LOG.error("更新失败");
			view = new ModelAndView("/jsp/crm/fail");
		}
		return view;
	}
	
	
	//获取url的Map
	public Map<String,String> getUrlMap(String tenantId){
		String idpsns = "ch-user-web-idps";
	    // 获取imageClient
	    IImageClient im = IDPSClientFactory.getImageClient(idpsns);
		ICustFileSV custfileSV = DubboConsumerFactory.getService("iCustfileSV");
		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
		
		custFileExtRequest.setTenantId(tenantId);
		QueryCustFileExtResponse response = custfileSV.queryCustFileExt(custFileExtRequest);
		Map<String,String> urlMap = new HashMap<String,String>();
		if(!response.getList().isEmpty()){
			for (CmCustFileExtVo cmCustFileExtVo : response.getList()) {
				String url = im.getImageUrl(cmCustFileExtVo.getAttrValue(), ".jpg");
				urlMap.put(cmCustFileExtVo.getAttrId(), url);
			}
		}
			return urlMap;
	}
	//获取图片name的Map
	public Map<String,String> getNameMap(String tenantId){
		ICustFileSV custfileSV = DubboConsumerFactory.getService("iCustfileSV");
		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
		custFileExtRequest.setTenantId(tenantId);
		QueryCustFileExtResponse response = custfileSV.queryCustFileExt(custFileExtRequest);
		Map<String,String> nameMap = new HashMap<String,String>();
		if(!response.getList().isEmpty()){
			for (CmCustFileExtVo cmCustFileExtVo : response.getList()) {
				nameMap.put(cmCustFileExtVo.getAttrId(), cmCustFileExtVo.getInfoName());
			}
		}
		return nameMap;
	}
	//获取图片url的Map
	public Map<String,String> getIdpsMap(String tenantId){
		ICustFileSV custfileSV = DubboConsumerFactory.getService("iCustfileSV");
		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
		custFileExtRequest.setTenantId(tenantId);
		QueryCustFileExtResponse response = custfileSV.queryCustFileExt(custFileExtRequest);
		Map<String,String> nameMap = new HashMap<String,String>();
		if(!response.getList().isEmpty()){
			for (CmCustFileExtVo cmCustFileExtVo : response.getList()) {
				nameMap.put(cmCustFileExtVo.getAttrId(), cmCustFileExtVo.getAttrValue());
			}
		}
		return nameMap;
	}
	
}
