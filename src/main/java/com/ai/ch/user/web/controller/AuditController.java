package com.ai.ch.user.web.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.audit.interfaces.IAuditSV;
import com.ai.ch.user.api.audit.params.AuditLogVo;
import com.ai.ch.user.api.audit.params.QueryAuditLogInfoRequest;
import com.ai.ch.user.api.audit.params.QueryAuditLogInfoResponse;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.vo.AuditLogInfoVo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.alibaba.fastjson.JSON;
import com.esotericsoftware.minlog.Log;

/**
 * 审核日志controller
 * @author Zh
 *
 */
@RestController
@RequestMapping("/audit")
public class AuditController {
	
	@RequestMapping("/shopauditlog")
	public ModelAndView shopAuditLogPager() {
		return new ModelAndView("/jsp/auditlog/shopAuditLog");
	}
	
	@RequestMapping("supplierauditlog")
	public ModelAndView supplierAuditLogPager() {
		return new ModelAndView("/jsp/auditlog/supplierAuditLog");
	}
	
	@RequestMapping("getAuditList")
	public ResponseData<PageInfo<AuditLogInfoVo>> getAuditList(HttpServletRequest request,String ctType){
		ResponseData<PageInfo<AuditLogInfoVo>> responseData= null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		IAuditSV auditSV = DubboConsumerFactory.getService(IAuditSV.class);
		QueryAuditLogInfoRequest queryAuditLogInfoRequest = new QueryAuditLogInfoRequest();
		queryAuditLogInfoRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
		queryAuditLogInfoRequest.setPageNo(Integer.valueOf(request.getParameter("pageNo")));
		queryAuditLogInfoRequest.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
		queryAuditLogInfoRequest.setCtType(ctType);
		if(request.getParameter("startTime")!=null&&!(StringUtil.isBlank(request.getParameter("startTime")))){
			queryAuditLogInfoRequest.setBeginTime(Timestamp.valueOf(request.getParameter("startTime")+" 00:00:00"));
		}
		if(request.getParameter("endTime")!=null&&(!StringUtil.isBlank(request.getParameter("endTime")))){
			queryAuditLogInfoRequest.setEndTime(Timestamp.valueOf(request.getParameter("endTime")+" 23:59:59"));
		}
		if(request.getParameter("username")!=null&&!(StringUtil.isBlank(request.getParameter("username")))){
			queryAuditLogInfoRequest.setUserName(request.getParameter("username"));
		}
		try{
		QueryAuditLogInfoResponse queryAuditLogInfoResponse = auditSV.queryAuditLogInfo(queryAuditLogInfoRequest);
		responseData = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
		if(queryAuditLogInfoResponse.getPageInfo()!=null){
			PageInfo<AuditLogVo> pageInfo = queryAuditLogInfoResponse.getPageInfo();
			PageInfo<AuditLogInfoVo> pageLogInfo = new PageInfo<>();
			List<AuditLogInfoVo> list= new ArrayList<>();
			if(pageInfo.getResult()!=null&&pageInfo.getResult().size()>0){
				for (AuditLogVo auditLogVo  : pageInfo.getResult()) {
					AuditLogInfoVo auditLogInfoVo = new AuditLogInfoVo();
					BeanUtils.copyProperties(auditLogVo, auditLogInfoVo);
					if(auditLogVo.getAuditTime()!=null){
						auditLogInfoVo.setAuditTime(sdf.format(auditLogVo.getAuditTime().getTime()));
					}
					if(auditLogVo.getAuditStatus()!=null){
						if("1".equals(auditLogVo.getAuditStatus())){
							auditLogInfoVo.setAuditStatus("审核已通过");
						}else if("2".equals(auditLogVo.getAuditStatus())){
							auditLogInfoVo.setAuditStatus("审核已拒绝");
						}
					}
					list.add(auditLogInfoVo);
				}
			}
			BeanUtils.copyProperties(pageInfo, pageLogInfo);
			pageLogInfo.setResult(list);
			responseData.setData(pageLogInfo);
		}
		}catch(Exception e){
			responseData = new ResponseData<>(ChWebConstants.OperateCode.Fail, "操作失败");
			Log.error("操作失败,原因:"+JSON.toJSONString(e));
		}
		return responseData;
	}
	
	
}
