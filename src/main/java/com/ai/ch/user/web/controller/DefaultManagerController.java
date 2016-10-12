package com.ai.ch.user.web.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.ai.ch.user.web.constants.TranType;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.util.PayUtil;
import com.ai.ch.user.web.util.PropertiesUtil;
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
import com.upp.docking.covn.MsgString;
import com.ylink.itfin.certificate.SecurityUtil;
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
		//包装数据
		GrpHdr hdr = new GrpHdr();
		PayUtil payUtil = new PayUtil();
		hdr.setMerNo("CO20160700000004");//设置一级平台商户号
		hdr.setTranType(TranType.PAY_GUARANTEE_MONEY_QUERY.getValue());//设置交易类型(保证金支付订单查询)
		
		hdr.setCreDtTm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		GrpBody body = new GrpBody();
		body.setSonMerNo("CO20160800000008");//设置要查询的二级商户编号
		body.setResv("test");//设置保留域
		
		ReqsInfo reqsInfo = new ReqsInfo();
		reqsInfo.setGrpHdr(hdr);
		reqsInfo.setGrpBody(body);
		
		// 发送消息
		String xmlMsg = null;
		try {
			xmlMsg = oxmHandler.marshal(reqsInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, String> param = new TreeMap<String, String>();
		// 加签
		String sign = null;
		try {
			sign = payUtil.sign(xmlMsg);
			param.put("signMsg", sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拼装报文头
		String msgHeader = payUtil.initMsgHeader(hdr.getMerNo(), TranType.PAY_GUARANTEE_MONEY_QUERY.getValue());
		param.put("msgHeader", msgHeader);
		param.put("xmlBody", xmlMsg);
		String result = null;
		try {
			String url = PropertiesUtil.getStringByKey("balance_http_url");
			result = payUtil.sendHttpPost(url, param, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		MsgString msgString = MsgUtils.patch(result);
		String rh = msgString.getHeaderMsg();
        String rb = msgString.getXmlBody();
        String rs = msgString.getDigitalSign();

        String balance = "0";//查询的金额
		com.ylink.upp.base.oxm.XmlBodyEntity resultMsg = this.receiveMsg(rh, rb, rs);
        com.ylink.upp.oxm.entity.upp_712_001_01.RespInfo receive = (com.ylink.upp.oxm.entity.upp_712_001_01.RespInfo)resultMsg;
        if(receive == null){
        	com.ylink.upp.oxm.entity.upp_599_001_01.RespInfo receive2 = (com.ylink.upp.oxm.entity.upp_599_001_01.RespInfo) resultMsg;
            if(!"90000".equals(receive2.getGrpBody().getStsRsn().getRespCode())){
            	throw new RuntimeException("系统异常.");
            }
        }else{
            if(!"90000".equals(receive.getGrpBody().getStsRsn().getRespCode())){
            	throw new RuntimeException("系统异常.");
            }
            balance=receive.getGrpBody().getStsRsnInf().getBalance();
        }
		Map<String, Object> model = new HashMap<String, Object>();
 		model.put("userId", userId);
 		try {
			model.put("userName", URLDecoder.decode(userName,"utf-8"));
			model.put("custName", URLDecoder.decode(custName,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
 		model.put("balance", Float.parseFloat(balance)/100);
 		//model.put("balance", 2000);
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
			
			/*//调用长虹扣款
			//包装数据
			PayUtil payUtil = new PayUtil();
			com.ylink.upp.oxm.entity.upp_100_001_01.GrpHdr hdr = new com.ylink.upp.oxm.entity.upp_100_001_01.GrpHdr();
			hdr.setMerNo("CO20160700000004");//设置一级平台商户号
			hdr.setTranType("100.001.01");//设置交易类型(保证金)
			hdr.setCreDtTm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			
			com.ylink.upp.oxm.entity.upp_100_001_01.GrpBody body = new com.ylink.upp.oxm.entity.upp_100_001_01.GrpBody();
			String orderId = UUID.randomUUID().toString();
			body.setMerOrderId(orderId);//设置要查询的二级商户编号
			body.setUserName("uname");
			body.setToken("token");
			body.setOpenId("1");
			body.setCustType("02");//企业
			body.setPayCustNo("CB2016083000000004");
			
			BigDecimal orderAmt = new BigDecimal("0");
			List<PayOrderDetail> details = new ArrayList<PayOrderDetail>();
			PayOrderDetail detail = new PayOrderDetail();
			detail.setProductAmt("1");//扣保证金金额（分）
			detail.setProductName("productName1");//扣保证金原因
			detail.setSonMerNo("CO20160700000006");//收取保证金商户号
			detail.setMerSeqId(orderId);//商户主订单号
			orderAmt = BigDecimal.valueOf(Long.parseLong(detail.getProductAmt()));//订单总金额(当前与扣款金额相同)
			details.add(detail);

			body.setOrderAmt(orderAmt.toString());
			body.setOrderNum(details.size() + "");
			body.setOrderDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			body.setPayOrderDetail(details);
			body.setRemark("margindeposittest");

			String paymentOrderurl = PropertiesUtil.getStringByKey("paymentOrder_http_url", "httpUrl.properties");
			
			body.setNotifyUrl(paymentOrderurl);
			body.setReturnUrl(paymentOrderurl);
			
			body.setProductTypeName("margindeposit");
			body.setResv("test");
			body.setBizType("BAIL_BALANCE_PAY");// (保证金余额支付)
			body.setResv("test");//设置保留域

			
			com.ylink.upp.oxm.entity.upp_100_001_01.ReqsInfo reqsInfo = new com.ylink.upp.oxm.entity.upp_100_001_01.ReqsInfo();
			reqsInfo.setGrpHdr(hdr);
			reqsInfo.setGrpBody(body);
			
			// 发送消息
			String xmlMsg = null;
			try {
				xmlMsg = oxmHandler.marshal(reqsInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Map<String, String> param = new TreeMap<String, String>();
			// 加签
			String sign = null;
			try {
				ResourceLoader resourceLoader = new DefaultResourceLoader();
				Resource pfxResource = resourceLoader.getResource("classpath:CO20160700000018.pfx"); 
				InputStream in = new FileInputStream(pfxResource.getFile());
				byte[] pfxByte = IOUtils.toByteArray(in);
			    sign = SecurityUtil.pfxWithSign(pfxByte,xmlMsg, "111111");
				param.put("signMsg", sign);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 拼装报文头
			String msgHeader = payUtil.initMsgHeader(hdr.getMerNo(), "100.001.01");
			param.put("msgHeader", msgHeader);
			param.put("xmlBody", xmlMsg);
			String result = null;
			try {
				String paymentApplication = PropertiesUtil.getStringByKey("paymentApplication_http_url", "httpUrl.properties");
				result = payUtil.sendHttpPost(paymentApplication, param, "UTF-8");
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
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
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("searchCompanyList_http_url"), JSON.toJSONString(map),mapHeader);
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
					 businessListInfo.setBusinessCategory(object.getString("commodityType"));
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
	
	@RequestMapping("/paymentNotifications")
	public String paymentNotifications( @RequestParam("msgHeader") String msgHead,@RequestParam("xmlBody") String xmlBody,@RequestParam("signMsg") String signMsg){
		System.out.println("验签开始---------------------");
		boolean flag = false;
		try{
			//验签
			System.out.println("msgHead=====================:"+msgHead);
			System.out.println("xmlBody=====================:"+xmlBody);
			System.out.println("signMsg====================="+signMsg);
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
	        if("01".equals(receive.getGrpBody().getPayStatus())){
	        	//defaultLogBusiSV.insertDefaultLog(request);
	        }
	        
	        System.out.println("结束---------------------");
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
	
}
