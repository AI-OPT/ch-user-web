package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.defaultlog.interfaces.IDefaultLogSV;
import com.ai.ch.user.api.defaultlog.params.DefaultLogVo;
import com.ai.ch.user.api.defaultlog.params.InsertDefaultLogRequest;
import com.ai.ch.user.api.defaultlog.params.InsertDefaultLogResponse;
import com.ai.ch.user.api.defaultlog.params.QueryDefaultLogRequest;
import com.ai.ch.user.api.defaultlog.params.QueryDefaultLogResponse;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.ExceptionCode;
import com.ai.ch.user.web.constants.ChWebConstants.OperateCode;
import com.ai.ch.user.web.constants.TranType;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.util.PayUtil;
import com.ai.ch.user.web.util.PropertiesUtil;
import com.ai.ch.user.web.vo.BusinessListInfo;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.ParseO2pDataUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.platform.common.api.sysuser.interfaces.ISysUserQuerySV;
import com.ai.platform.common.api.sysuser.param.SysUserQueryRequest;
import com.ai.platform.common.api.sysuser.param.SysUserQueryResponse;
import com.ai.platform.common.api.tenant.interfaces.IGnTenantQuerySV;
import com.ai.platform.common.api.tenant.param.GnTenantConditon;
import com.ai.platform.common.api.tenant.param.GnTenantVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upp.docking.covn.MsgString;
import com.ylink.upp.base.oxm.XmlBodyEntity;
import com.ylink.upp.base.oxm.util.Dom4jHelper;
import com.ylink.upp.base.oxm.util.HandlerMsgUtil;
import com.ylink.upp.base.oxm.util.HeaderBean;
import com.ylink.upp.base.oxm.util.MsgUtils;
import com.ylink.upp.base.oxm.util.OxmHandler;
import com.ylink.upp.oxm.entity.upp_100_001_01.PayOrderDetail;
import com.ylink.upp.oxm.entity.upp_711_001_01.GrpBody;
import com.ylink.upp.oxm.entity.upp_711_001_01.GrpHdr;
import com.ylink.upp.oxm.entity.upp_711_001_01.ReqsInfo;

@RestController
@RequestMapping("/defaultManager")
public class DefaultManagerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultManagerController.class);
	
	@Autowired
	private OxmHandler oxmHandler;
	
	@RequestMapping("/defaultPager")
    public ModelAndView billingPager() {
        return new ModelAndView("/jsp/defaultManager/defaultManagerList");
    }
	
	@RequestMapping("/defaultManagerPager")
	public ModelAndView defaultManager() {
		return new ModelAndView("/jsp/defaultManager/defaultManagerList");
	}
	
	
	@RequestMapping("/addDefaultInfo")
	public ModelAndView addDefaultInfo(HttpServletRequest request,String userId,String userName,String custName) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("扣款请求开始----------"+startTime);
		ReqsInfo reqsInfo = new ReqsInfo();
		PayUtil payUtil = new PayUtil();
		Map<String, String> param = new TreeMap<String, String>();
		GrpHdr hdr = new GrpHdr();
		try{
			/**
			 * 组装数据
			 */
			//获取租户信息
			GnTenantVo gntenatVo = getTenantInfo(request);
			hdr.setMerNo(gntenatVo.getMerNo());//设置一级平台商户号
			hdr.setTranType(TranType.PAY_GUARANTEE_MONEY_QUERY.getValue());//设置交易类型(保证金支付订单查询)
			hdr.setCreDtTm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			GrpBody body = new GrpBody();
			body.setSonMerNo(gntenatVo.getDebitSide());//设置要查询的二级商户编号
			body.setResv("test");//设置保留域
			reqsInfo.setGrpHdr(hdr);
			reqsInfo.setGrpBody(body);
			
			/**
			 *  解析对象成字符串类型
			 */
			String xmlMsg = null;
			xmlMsg = oxmHandler.marshal(reqsInfo);
			
			/**
			 *  加签
			 */
			String sign = null;
			sign = payUtil.sign(xmlMsg);
			param.put("signMsg", sign);
			
			/**
			 *拼装报文头
			 */
			String msgHeader = payUtil.initMsgHeader(hdr.getMerNo(), TranType.PAY_GUARANTEE_MONEY_QUERY.getValue());
			param.put("msgHeader", msgHeader);
			param.put("xmlBody", xmlMsg);
			LOGGER.info("msgHeader================="+msgHeader);
			LOGGER.info("msgXmlMsg================="+msgHeader);
			LOGGER.info("msgHeader================="+msgHeader);
			
		}catch(Exception e){
			LOGGER.error("组装数据出错",e);
			return new ModelAndView("/jsp/defaultManager/fail");
		}
		
		/**
		 * 访问保证金余额查询
		 */
		String result = null;
		try {
			String url = PropertiesUtil.getStringByKey("balance_http_url");
			Long beginTime = System.currentTimeMillis();
			LOGGER.info("查询保证金余额服务开始"+beginTime);
			result = payUtil.sendHttpPost(url, param, "UTF-8");
			LOGGER.info("查询保证金余额服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		} catch (Exception e) {
			LOGGER.error("获取保证金余额出错",e);
			return new ModelAndView("/jsp/defaultManager/fail");
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		if(result!=null){
			try{
				MsgString msgString = MsgUtils.patch(result);
				String rh = msgString.getHeaderMsg();
		        String rb = msgString.getXmlBody();
		        String rs = msgString.getDigitalSign();

		        LOGGER.info("rh================="+rh);
				LOGGER.info("rb================="+rb);
				LOGGER.info("rs================="+rs);
		        
		        String balance = "0";//查询的金额
				com.ylink.upp.base.oxm.XmlBodyEntity resultMsg = this.receiveMsg(rh, rb, rs);
				com.ylink.upp.oxm.entity.upp_712_001_01.RespInfo receive = null;
				
				if(resultMsg instanceof com.ylink.upp.oxm.entity.upp_712_001_01.RespInfo){
					 receive = (com.ylink.upp.oxm.entity.upp_712_001_01.RespInfo)resultMsg;
				}
		        
		        if(receive == null){
		        	com.ylink.upp.oxm.entity.upp_599_001_01.RespInfo receive2 = (com.ylink.upp.oxm.entity.upp_599_001_01.RespInfo) resultMsg;
		            if(!"90000".equals(receive2.getGrpBody().getStsRsn().getRespCode())){
		            	LOGGER.error("获取保证金余额出错:resultMsg======="+resultMsg);
		            	throw new SystemException("系统异常.");
		            }
		        }else{
		            if(!"90000".equals(receive.getGrpBody().getStsRsn().getRespCode())){
		            	throw new SystemException("系统异常.");
		            }
		            balance=receive.getGrpBody().getStsRsnInf().getBalance();
		        }
		 		model.put("userId", userId);
		 		try {
					model.put("userName", URLDecoder.decode(userName,"utf-8"));
					model.put("custName", URLDecoder.decode(custName,"utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		 		model.put("balance", Double.parseDouble(balance)/100);
			}catch(Exception e){
				return new ModelAndView("/jsp/defaultManager/fail");
			}
		}else{
			return new ModelAndView("/jsp/defaultManager/fail");
		}
		
		long endTime = System.currentTimeMillis();
		LOGGER.info("扣款请求结束,扣款共耗时----------"+(endTime-startTime));
		
		return new ModelAndView("/jsp/defaultManager/addDefault",model);
	}
	
	public XmlBodyEntity receiveMsg(String msgHeader, String xmlMsg, String sign) {
		try {
			PayUtil payUtil = new PayUtil();
			boolean verify = payUtil.verify(xmlMsg, sign);
			if (!verify) {
				 throw new Exception("验签失败.");
			}
			HeaderBean headerBean = new HeaderBean();
			HandlerMsgUtil.conversion(msgHeader, headerBean);
			xmlMsg = Dom4jHelper.addNamespace(xmlMsg, headerBean.getMesgType(), "UTF-8");
			return (XmlBodyEntity) oxmHandler.unmarshaller(xmlMsg);
		} catch (Exception e) {
			System.out.println("接收数据时发生异常，错误信息为:" + e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@RequestMapping("/saveDefaultInfo")
	@ResponseBody
	public ResponseData<String> saveDefaultInfo(HttpServletRequest request,DefaultLogVo defaultLogInfo) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("保存扣款记录开始---------"+startTime);
		ISysUserQuerySV sysUserQuery = DubboConsumerFactory.getService("iSysUserQuerySV");
		ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;
        HttpSession session = request.getSession();
        GeneralSSOClientUser user = (GeneralSSOClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        InsertDefaultLogRequest defaultLogRequest = new InsertDefaultLogRequest();
        PayUtil payUtil = new PayUtil();
        Map<String, String> param = new TreeMap<String, String>();
        IDefaultLogSV defaultLog = DubboConsumerFactory.getService("iDefaultLogSV");
        InsertDefaultLogResponse defaultLogResponse = new InsertDefaultLogResponse();
        try{
        	/**
        	 * 通过登录名称获取登录用户信息
        	 */
        	 GeneralSSOClientUser userClient = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        	 
        	 SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        	 sysUserQueryRequest.setLoginName(user.getLoginName());
        	 sysUserQueryRequest.setTenantId(userClient.getTenantId());
        	 SysUserQueryResponse  userQueryResponse = sysUserQuery.queryUserInfo(sysUserQueryRequest);
        	 
			 BeanUtils.copyProperties(defaultLogRequest,defaultLogInfo);
			
			 defaultLogRequest.setDeductDate(new Timestamp(new Date().getTime()));
			 /**
			  * 在长虹侧是以分为单位，所以由元转换成分
			  */
			 defaultLogRequest.setDeductBalance(defaultLogInfo.getDeductBalance()*100);
			 defaultLogRequest.setOperId(Long.parseLong(userQueryResponse.getNo()));
			 defaultLogRequest.setOperName(userQueryResponse.getLoginName());
			 defaultLogRequest.setTenantId(userClient.getTenantId());
			
			 	Long beginTime = System.currentTimeMillis();
				LOGGER.info("保存扣款信息服务开始"+beginTime);
				defaultLogResponse = defaultLog.insertDefaultLog(defaultLogRequest);
				LOGGER.info("保存扣款信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
        	
        	/**
        	 * 组装数据
        	 */
        	GnTenantVo gnTenantVo = getTenantInfo(request);
			//调用长虹扣款
			//包装数据
			com.ylink.upp.oxm.entity.upp_100_001_01.GrpHdr hdr = new com.ylink.upp.oxm.entity.upp_100_001_01.GrpHdr();
			hdr.setMerNo(gnTenantVo.getMerNo());//设置一级平台商户号
			//支付类型
			hdr.setTranType(TranType.PAY_APPLY.getValue());//设置交易类型(保证金)
			hdr.setCreDtTm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			com.ylink.upp.oxm.entity.upp_100_001_01.GrpBody body = new com.ylink.upp.oxm.entity.upp_100_001_01.GrpBody();
			//保证金扣款没有订单id，所以把扣款记录记录在属性里
			body.setMerOrderId(defaultLogResponse.getSerialCode());
			body.setUserName("uname");
			body.setToken("token");
			body.setOpenId("1");
			body.setCustType("02");//企业
			//扣保证金的企业
			body.setPayCustNo(gnTenantVo.getDebitSide());
			BigDecimal orderAmt = new BigDecimal("0");
			List<PayOrderDetail> details = new ArrayList<PayOrderDetail>();
			PayOrderDetail detail = new PayOrderDetail();
			detail.setProductAmt("1");//扣保证金金额（分）
			detail.setProductName("productName1");//扣保证金原因
			detail.setSonMerNo(gnTenantVo.getReceivingSide());//收取保证金商户号
			detail.setMerSeqId(UUIDUtil.genId32());//商户主订单号
			orderAmt = BigDecimal.valueOf(Long.parseLong(detail.getProductAmt()));//订单总金额(当前与扣款金额相同)
			details.add(detail);
			body.setOrderAmt(orderAmt.toString());
			body.setOrderNum(details.size() + "");
			body.setOrderDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			body.setPayOrderDetail(details);
			body.setRemark("margindeposittest");
			//获取支付通知的url
			Long payBeginTime = System.currentTimeMillis();
			LOGGER.info("支付申请服务开始"+payBeginTime);
			String paymentOrderurl = PropertiesUtil.getStringByKey("paymentOrder_http_url");
			LOGGER.info("支付申请服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-payBeginTime)+"毫秒");
			body.setNotifyUrl(paymentOrderurl);
			body.setReturnUrl(paymentOrderurl);
			body.setProductTypeName("margindeposit");
			body.setResv("test");
			body.setBizType("BAIL_BALANCE_PAY");// (保证金余额支付)
			body.setResv("test");//设置保留域
			com.ylink.upp.oxm.entity.upp_100_001_01.ReqsInfo reqsInfo = new com.ylink.upp.oxm.entity.upp_100_001_01.ReqsInfo();
			reqsInfo.setGrpHdr(hdr);
			reqsInfo.setGrpBody(body);
			
			/**
			 *  解析对象
			 */
			String xmlMsg = null;
			xmlMsg = oxmHandler.marshal(reqsInfo);
			
			/**
			 *  加签
			 */
			String sign = payUtil.sign(xmlMsg);
			param.put("signMsg", sign);
			
			/**
			 *  拼装报文头
			 */
			String msgHeader = payUtil.initMsgHeader(hdr.getMerNo(), TranType.PAY_APPLY.getValue());
			param.put("msgHeader", msgHeader);
			param.put("xmlBody", xmlMsg);
			String result = null;
			String paymentApplication = PropertiesUtil.getStringByKey("paymentApplication_http_url", "httpUrl.properties");
			result = payUtil.sendHttpPost(paymentApplication, param, "UTF-8");
			if(result!=null){
				MsgString msgString = MsgUtils.patch(result);
				String rh = msgString.getHeaderMsg();
		        String rb = msgString.getXmlBody();
		        String rs = msgString.getDigitalSign();
		        
		        com.ylink.upp.oxm.entity.upp_100_001_01.ReqsInfo receive = null;
		        
		        com.ylink.upp.base.oxm.XmlBodyEntity resultMsg = this.receiveMsg(rh, rb, rs);
		        
				if(resultMsg instanceof com.ylink.upp.oxm.entity.upp_100_001_01.ReqsInfo){
					 receive = (com.ylink.upp.oxm.entity.upp_100_001_01.ReqsInfo)resultMsg;
				}
		        if(receive == null){
		        	com.ylink.upp.oxm.entity.upp_599_001_01.RespInfo receive2 = (com.ylink.upp.oxm.entity.upp_599_001_01.RespInfo) resultMsg;
		            if(!"90000".equals(receive2.getGrpBody().getStsRsn().getRespCode())){
		            	responseData = new ResponseData<String>(ExceptionCode.ERROR_CODE, "操作失败", null);
			            responseHeader = new ResponseHeader(false,ExceptionCode.ERROR_CODE, "操作失败");
			            responseData.setResponseHeader(responseHeader);
			            responseData.setData(JSON.toJSONString(defaultLogRequest));
			            return responseData;
		            }
		        }
				responseData = new ResponseData<String>(ExceptionCode.SUCCESS_CODE, "操作成功", null);
	            responseHeader = new ResponseHeader(true,ExceptionCode.SUCCESS_CODE, "操作成功");
			}else{
				defaultLog.deleteDefaultLog(defaultLogResponse.getSerialCode());
				responseData = new ResponseData<String>(ExceptionCode.ERROR_CODE, "操作失败", null);
	            responseHeader = new ResponseHeader(false,ExceptionCode.ERROR_CODE, "操作失败");
	            LOGGER.error("返回结果result为空");
			}
        }catch(Exception e){
        	defaultLog.deleteDefaultLog(defaultLogResponse.getSerialCode());
        	responseData = new ResponseData<String>(ExceptionCode.ERROR_CODE, "操作失败", null);
            responseHeader = new ResponseHeader(false,ExceptionCode.ERROR_CODE, "操作失败");
            LOGGER.error("扣款失败",e);
        }
        responseData.setResponseHeader(responseHeader);
        responseData.setData(JSON.toJSONString(defaultLogRequest));
        long endTime = System.currentTimeMillis();
        LOGGER.info("保存扣款记录结束,共耗时"+(endTime-startTime));
        return responseData;
	}
	
	@RequestMapping("/defaultHistoryPager")
	public ModelAndView defaultHistoryPager(HttpServletRequest request,String userId,String userName,String custName) {
		Map<String, Object> model = new HashMap<String, Object>();
 		model.put("userId", userId);
 		try {
			model.put("userName", URLDecoder.decode(userName,"utf-8"));
			model.put("custName", URLDecoder.decode(custName,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ModelAndView("/jsp/defaultManager/defaultHistoryList",model);
	}
	
	
	//获取供货商列表
	@RequestMapping("/getDefaultHistoryList")
	@ResponseBody
	public ResponseData<PageInfo<DefaultLogVo>> getDefaultHistoryList(HttpServletRequest request,QueryDefaultLogRequest defaultLogRequest) {
		long startTime = System.currentTimeMillis();
		LOGGER.info("获取扣款历史记录开始--------"+startTime);
		ResponseData<PageInfo<DefaultLogVo>> responseData = null;
		try {
			IDefaultLogSV defaultLog = DubboConsumerFactory.getService("iDefaultLogSV");
			QueryDefaultLogResponse defaultLogResponse = defaultLog.queryDefaultLog(defaultLogRequest);
			if (defaultLogResponse != null && defaultLogResponse.getResponseHeader().isSuccess()) {
				Long beginTime = System.currentTimeMillis();
				LOGGER.info("获取供货商列表信息服务开始"+beginTime);
				PageInfo<DefaultLogVo> pageInfo = defaultLogResponse.getPageInfo();
				LOGGER.info("获取供货商列表信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
				responseData = new ResponseData<PageInfo<DefaultLogVo>>(ChWebConstants.OperateCode.SUCCESS, "查询成功", pageInfo);
			} else {
				responseData = new ResponseData<PageInfo<DefaultLogVo>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<DefaultLogVo>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
		}
		long endTime = System.currentTimeMillis();
		LOGGER.info("获取扣款历史记录结束，共耗时--------"+(endTime-startTime));
		return responseData;
	}
	
	//查询列表
	@RequestMapping("/getList")
	@ResponseBody
	public ResponseData<PageInfo<BusinessListInfo>> getList(HttpServletRequest request,String companyName,String username,String companyType){
		long startTime = System.currentTimeMillis();
		LOGGER.info("获取用户列表开始"+startTime);
		ResponseData<PageInfo<BusinessListInfo>> response = null;
		PageInfo<BusinessListInfo> pageInfo =null;
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
			LOGGER.info("长虹查询商户列表信息服务开始"+beginTime);
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("searchCompanyList_http_url"), JSON.toJSONString(map),mapHeader);
			LOGGER.info("长虹查询商户列表信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		long resultTime = System.currentTimeMillis();
		try{
			JSONObject data = ParseO2pDataUtil.getData(str);
			String resultCode = data.getString("resultCode");
			if (resultCode!=null&&!OperateCode.SUCCESS.equals(resultCode)){
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
				header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "操作失败"); 
				response.setResponseHeader(header);
			}else {
					if(data == null){
						response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
						header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
						response.setResponseHeader(header);
						return response;
					}
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
		}catch(Exception e){
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
			header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "操作失败"); 
		}
			response.setResponseHeader(header);
			response.setData(pageInfo);
		long resultEndtime = System.currentTimeMillis();
		LOGGER.info("处理结果耗时"+(resultEndtime-resultTime));
		long endTime = System.currentTimeMillis();
		LOGGER.info("获取用户列表结束"+(endTime-startTime));
		return response;
	}
	
	@RequestMapping("/paymentNotifications")
	public String paymentNotifications( @RequestParam("msgHeader") String msgHead,@RequestParam("xmlBody") String xmlBody,@RequestParam("signMsg") String signMsg){
		LOGGER.info("支付通知开始");
		boolean flag = false;
		try{
			//验签
			com.ylink.upp.base.oxm.XmlBodyEntity resultMsg =  receiveMsg(msgHead, xmlBody, signMsg);
	        com.ylink.upp.oxm.entity.upp_103_001_01.RespInfo receive = (com.ylink.upp.oxm.entity.upp_103_001_01.RespInfo)resultMsg;
	        if(receive == null){
	        	com.ylink.upp.oxm.entity.upp_599_001_01.RespInfo receive2 = (com.ylink.upp.oxm.entity.upp_599_001_01.RespInfo) resultMsg;
	            if(!"90000".equals(receive2.getGrpBody().getStsRsn().getRespCode())){
	            	flag = false;
	            	throw new RuntimeException("系统异常.");
	            }
	        }
	        
	        //00表示支付成功，01表示支付失败
	        LOGGER.info("扣款开始==================");
	        if("01".equals(receive.getGrpBody().getPayStatus())){
	        	LOGGER.info("扣款失败=============");
	        	//net.sf.json.JSONObject conditionObject = net.sf.json.JSONObject.fromObject(receive.getGrpBody().getMerOrderId());
	        	//InsertDefaultLogRequest defaultLogRequest = (InsertDefaultLogRequest)conditionObject.toBean(conditionObject, InsertDefaultLogRequest.class);
	        	IDefaultLogSV defaultLog = DubboConsumerFactory.getService("iDefaultLogSV");
	        	Long beginTime = System.currentTimeMillis();
				LOGGER.info("删除扣款信息服务开始"+beginTime);
				defaultLog.deleteDefaultLog(receive.getGrpBody().getMerOrderId());
				LOGGER.info("删除扣款信息服务结束"+System.currentTimeMillis()+"耗时:"+(System.currentTimeMillis()-beginTime)+"毫秒");
	        }
	        LOGGER.info("扣款结束===================");
	        flag = true;
		}catch(Exception e){
			flag = false;
			e.printStackTrace();
		}
		if(flag){
			return "SUCCESS";
		}else{
			return "ERROR";
		}
	}
	
	public GnTenantVo getTenantInfo (HttpServletRequest request){
		GnTenantVo gntenatVo = null;
		try{
			GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
			IGnTenantQuerySV GnTenantQuerySV = DubboConsumerFactory.getService(IGnTenantQuerySV.class);
			GnTenantConditon condition = new GnTenantConditon();
			condition.setTenantId(user.getTenantId());
			gntenatVo = GnTenantQuerySV.getTenant(condition);
		}catch(Exception e){
			LOGGER.error("获取租户信息出错",e);
		}
		return gntenatVo;
	}
}
