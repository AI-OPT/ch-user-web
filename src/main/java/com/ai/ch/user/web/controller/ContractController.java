package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.contract.interfaces.IContractSV;
import com.ai.ch.user.api.contract.param.ContactInfoRequest;
import com.ai.ch.user.api.contract.param.ContractInfoResponse;
import com.ai.ch.user.api.custfile.interfaces.ICustFileSV;
import com.ai.ch.user.api.custfile.params.CmCustFileExtVo;
import com.ai.ch.user.api.custfile.params.QueryCustFileExtRequest;
import com.ai.ch.user.api.custfile.params.QueryCustFileExtResponse;
import com.ai.ch.user.api.custfile.params.UpdateCustFileExtRequest;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.ExceptionCode;
import com.ai.ch.user.web.constants.ChWebConstants.OperateCode;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.model.user.CustFileListVo;
import com.ai.ch.user.web.util.PropertiesUtil;
import com.ai.ch.user.web.vo.BusinessListInfo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.dss.DSSClientFactory;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.dss.base.interfaces.IDSSClient;
import com.ai.paas.ipaas.image.IImageClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/contract")
public class ContractController {

	 private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);
	/**
	 * 合同供应商列表信息
	 * 
	 */
	 @RequestMapping("/contractSupplierPager")
	 public ModelAndView contractSupplierPager() {
        return new ModelAndView("/jsp/contract/supplier/contractList");
	 }
	 
	 
 	/**
	 * 合同店铺列表信息
	 * 
	 */
	 @RequestMapping("/contractShopPager")
	 public ModelAndView contractShopPager() {
        return new ModelAndView("/jsp/contract/shop/contractList");
	 }
	
	 /**
	  * 供应商管理页面信息
	  * @param request
	  * @param userId
	  * @return
	  */
	 @RequestMapping("/contractSupplierManagerPager")
	 public ModelAndView contractManagerPager(HttpServletRequest request,String userId,String userName,String custName) {
		    long startTime = System.currentTimeMillis();
		    LOGGER.info("合同管理，供应商管理页面请求开始----------"+startTime);
		 	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
		 	ContactInfoRequest contactInfo = new ContactInfoRequest();
		 	contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SUPPLIER);
		 	GeneralSSOClientUser userClient = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		 	contactInfo.setTenantId(userClient.getTenantId());
		 	contactInfo.setUserId(userId);
		 	
		 	long queryTime = System.currentTimeMillis();
	 		ContractInfoResponse response = contract.queryContractInfo(contactInfo);
	 		long ctContractInfoTime = System.currentTimeMillis();
	 		LOGGER.info("查询ct_contract_info耗时----------"+(ctContractInfoTime-queryTime));
	 		
	 		response.setUserId(userId);
	 		Map<String, Object> model = new HashMap<String, Object>();
	 		model.put("contactInfo", response);
	 		if(response.getActiveTime()!=null){
	 			model.put("startTime", DateUtil.getDateString(response.getActiveTime(),"yyyy-MM-dd"));
	 		}
	 		if(response.getInactiveTime()!=null){
	 		  model.put("endTime", DateUtil.getDateString(response.getInactiveTime(),"yyyy-MM-dd"));
	 		}
	 		model.put("userId", userId);
	 		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
	 		custFileExtRequest.setTenantId(userClient.getTenantId());
	 		custFileExtRequest.setUsreId(userId);
	 		custFileExtRequest.setInfoType(ChWebConstants.INFOTYPE_SUPPLIER);
	 		ICustFileSV custFileSV = DubboConsumerFactory.getService("iCustfileSV");
	 		long queryCustFileTime = System.currentTimeMillis();
	 		QueryCustFileExtResponse custFileExtResponse = custFileSV.queryCustFileExt(custFileExtRequest);
	 		long cmcustFileTime = System.currentTimeMillis();
	 		LOGGER.info("查询cm_cust_file_ext耗时----------"+(cmcustFileTime-queryCustFileTime));
	 		
	 		List<CmCustFileExtVo> list = custFileExtResponse.getList();
	 		
	 		for(int i=0;i<list.size();i++){
	 			CmCustFileExtVo extVp = list.get(i);
	 			/**
	 			 * 扫描件合同
	 			 */
	 			String infoName = extVp.getInfoName();
	 			String attrValue = extVp.getAttrValue();
	 			String infoItem = extVp.getInfoItem();
	 			String infoExtId = extVp.getInfoExtId();
	 			if(ChWebConstants.SCAN_CONTRACT_SUPPLIER.equals(extVp.getInfoItem())){
		 			model.put("scanContractInfoName",infoName);
		 			model.put("scanContractAttrValue",attrValue);
		 			model.put("scanContractInfoItem",infoItem);
		 			model.put("scanContractInfoExtId", infoExtId);
	 			}else{
	 				model.put("electronicContractInfoName",infoName);
		 			model.put("electronicContractAttrValue",attrValue);
		 			model.put("electronicContractInfoItem",infoItem);
		 			model.put("electronicInfoExtId", infoExtId);
	 			}
	 		}
	 		try {
				model.put("userName", URLDecoder.decode(userName,"utf-8"));
				model.put("custName", URLDecoder.decode(custName,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	 		long endTime = System.currentTimeMillis();
		    LOGGER.info("合同管理，供应商管理页面请求结束----------"+endTime);
		    LOGGER.info("供应商管理页面一共耗时"+(endTime-startTime));
	        return new ModelAndView("/jsp/contract/supplier/contractManager",model);
	 }
	 /**
	  * 店铺管理页面信息
	  * @param request
	  * @param userId
	  * @return
	  */
	 @RequestMapping("/contractShopManagerPager")
	 public ModelAndView contractShopManagerPager(HttpServletRequest request,String userId,String userName,String custName) {
		 	long startTime = System.currentTimeMillis();
		    LOGGER.info("合同管理，店铺管理页面请求开始----------"+startTime);
		    IContractSV contract = DubboConsumerFactory.getService("iContractSV");
		 	ContactInfoRequest contactInfo = new ContactInfoRequest();
		 	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		 	contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SHOP);
		 	contactInfo.setTenantId(user.getTenantId());
		 	contactInfo.setUserId(userId);
		 	long querycontracttime = System.currentTimeMillis();
	 		ContractInfoResponse response = contract.queryContractInfo(contactInfo);
	 		long ctContractInfoTime = System.currentTimeMillis();
	 		LOGGER.info("查询ct_contract_info耗时----------"+(ctContractInfoTime-querycontracttime));
	 		
	 		Map<String, Object> model = new HashMap<String, Object>();
	 		model.put("contactInfo", response);
	 		if(response.getActiveTime()!=null){
	 			model.put("startTime", DateUtil.getDateString(response.getActiveTime(),"yyyy-MM-dd"));
	 		}
	 		if(response.getInactiveTime()!=null){
	 		  model.put("endTime", DateUtil.getDateString(response.getInactiveTime(),"yyyy-MM-dd"));
	 		}
	 		model.put("userId", userId);
	 		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
	 		custFileExtRequest.setTenantId(user.getTenantId());
	 		custFileExtRequest.setUsreId(userId);
	 		custFileExtRequest.setInfoType(ChWebConstants.INFOTYPE_SHOP);
	 		ICustFileSV custFileSV = DubboConsumerFactory.getService("iCustfileSV");
	 		
	 		long querycustTime = System.currentTimeMillis();
	 		QueryCustFileExtResponse custFileExtResponse = custFileSV.queryCustFileExt(custFileExtRequest);
	 		long cmcustFileTime = System.currentTimeMillis();
	 		LOGGER.info("查询cm_cust_file_ext耗时----------"+(cmcustFileTime-querycustTime));
	 		
	 		List<CmCustFileExtVo> list = custFileExtResponse.getList();
	 		
	 		for(int i=0;i<list.size();i++){
	 			CmCustFileExtVo extVp = list.get(i);
	 			/**
	 			 * 扫描件合同
	 			 */
	 			String infoName = extVp.getInfoName();
	 			String attrValue = extVp.getAttrValue();
	 			String infoItem = extVp.getInfoItem();
	 			String infoExtId = extVp.getInfoExtId();
	 			if(ChWebConstants.SCAN_CONTRACT_SHOP.equals(extVp.getInfoItem())){
		 			model.put("scanContractInfoName",infoName);
		 			model.put("scanContractAttrValue",attrValue);
		 			model.put("scanContractInfoItem",infoItem);
		 			model.put("scanContractInfoExtId", infoExtId);
	 			}else{
	 				model.put("electronicContractInfoName",infoName);
		 			model.put("electronicContractAttrValue",attrValue);
		 			model.put("electronicContractInfoItem",infoItem);
		 			model.put("electronicInfoExtId", infoExtId);
	 			}
	 		}
	 		try {
				model.put("userName", URLDecoder.decode(userName,"utf-8"));
				model.put("custName", URLDecoder.decode(custName,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	 		
	 		long endTime = System.currentTimeMillis();
		    LOGGER.info("合同管理，店铺管理页面请求结束----------"+endTime);
		    LOGGER.info("店铺管理页面一共耗时"+(endTime-startTime));
	 		
	        return new ModelAndView("/jsp/contract/shop/contractManager",model);
	 }
	 
	 /**
	  * 合同供应商查看页面信息
	  * @param request
	  * @param userId
	  * @return
	  */
	 
	 @RequestMapping("/contractSupplierDetailPager")
	 public ModelAndView contractSupplierDetailPager(HttpServletRequest request,String userId,String userName,String custName) {
		 	
		    long startTime = System.currentTimeMillis();
		    LOGGER.info("合同管理，供应商合同详情页面请求开始----------"+startTime);
		 
		 	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
		    ICustFileSV custFileSV = DubboConsumerFactory.getService("iCustfileSV");
		    
		 	ContactInfoRequest contactInfo = new ContactInfoRequest();
		 	contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SUPPLIER);
		 	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		 	contactInfo.setTenantId(user.getTenantId());
		 	contactInfo.setUserId(userId);
		 	
		 	long querycontracttime = System.currentTimeMillis();
	 		ContractInfoResponse response = contract.queryContractInfo(contactInfo);
	 		long ctContractInfoTime = System.currentTimeMillis();
	 		LOGGER.info("查询ct_contract_info耗时----------"+(ctContractInfoTime-querycontracttime));
	 		
	 		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
	 		custFileExtRequest.setTenantId(user.getTenantId());
	 		custFileExtRequest.setUsreId(userId);
	 		custFileExtRequest.setInfoType(ChWebConstants.INFOTYPE_SUPPLIER);
	 		
	 		long querycusttime = System.currentTimeMillis();
	 		QueryCustFileExtResponse custFileExtResponse = custFileSV.queryCustFileExt(custFileExtRequest);
	 		long cmcustFileTime = System.currentTimeMillis();
	 		LOGGER.info("查询cm_cust_file_ext耗时----------"+(cmcustFileTime-querycusttime));
	 		
	 		List<CmCustFileExtVo> list = custFileExtResponse.getList();
	 		
	 		Map<String, Object> model = new HashMap<String, Object>();
	 		
	 		for(int i=0;i<list.size();i++){
	 			CmCustFileExtVo extVp = list.get(i);
	 			/**
	 			 * 扫描件合同
	 			 */
	 			String infoName = extVp.getInfoName();
	 			String attrValue = extVp.getAttrValue();
	 			String infoItem = extVp.getInfoItem();
	 			
	 			if(ChWebConstants.SCAN_CONTRACT_SUPPLIER.equals(extVp.getInfoItem())){
		 			model.put("scanContractInfoName",infoName);
		 			model.put("scanDownLoadName", new Date().getTime()+infoName.substring(infoName.lastIndexOf("."),infoName.length()));
		 			model.put("scanContractAttrValue",attrValue);
		 			model.put("scanContractInfoItem",infoItem);
	 			}else{
	 				
	 				model.put("electronicContractInfoName",infoName);
	 				if(!"".equals(infoName)){
	 					model.put("electronicDownLoadName", new Date().getTime()+infoName.substring(infoName.lastIndexOf("."),infoName.length()));
	 				}
		 			model.put("electronicContractAttrValue",attrValue);
		 			model.put("electronicContractInfoItem",infoItem);
	 			}
	 		}
	 		model.put("contactInfo", response);
	 		int remarkLength = 0;
	 		if(response.getRemark()!=null){
	 			remarkLength = response.getRemark().length();
	 			if(remarkLength>100){
	 				model.put("remarkLength", true);
	 			}else{
	 				model.put("remarkLength", false);
	 			}
	 		}
	 		if(response.getActiveTime()!=null){
	 			model.put("startTime", DateUtil.getDateString(response.getActiveTime(),"yyyy-MM-dd"));
	 		}
	 		if(response.getInactiveTime()!=null){
	 		  model.put("endTime", DateUtil.getDateString(response.getInactiveTime(),"yyyy-MM-dd"));
	 		}
	 		try {
				model.put("userName", URLDecoder.decode(userName,"utf-8"));
				model.put("custName", URLDecoder.decode(custName,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	 		
	 		long endTime = System.currentTimeMillis();
		    LOGGER.info("合同管理，供应商合同页面请求结束----------"+endTime);
		    LOGGER.info("供应商合同详情一共耗时"+(endTime-startTime));
	 		
	 		return new ModelAndView("/jsp/contract/supplier/contractDetail",model);
	 }
	 
	 /**
	  * 合同店铺查看页面信息
	  * @param request
	  * @param userId
	  * @return
	  */
	 
	 @RequestMapping("/contractShopDetailPager")
	 public ModelAndView contractShopDetailPager(HttpServletRequest request,String userId,String userName,String custName) {
		 	
		 	long startTime = System.currentTimeMillis();
		    LOGGER.info("合同管理，店铺合同详情页面请求开始----------"+startTime);
		    
		    IContractSV contract = DubboConsumerFactory.getService("iContractSV");
		    ICustFileSV custFileSV = DubboConsumerFactory.getService("iCustfileSV");
		 	ContactInfoRequest contactInfo = new ContactInfoRequest();
		 	GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		 	contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SHOP);
		 	contactInfo.setTenantId(user.getTenantId());
		 	contactInfo.setUserId(userId);
		 	
		 	long querycontract = System.currentTimeMillis();
	 		ContractInfoResponse response = contract.queryContractInfo(contactInfo);
	 		long ctContractInfoTime = System.currentTimeMillis();
	 		LOGGER.info("查询ct_contract_info耗时----------"+(ctContractInfoTime-querycontract));
	 		
	 		/**
	 		 * 附件信息
	 		 */
	 		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
	 		custFileExtRequest.setTenantId(user.getTenantId());
	 		custFileExtRequest.setUsreId(userId);
	 		custFileExtRequest.setInfoType(ChWebConstants.INFOTYPE_SHOP);
	 		
	 		long querycusttime = System.currentTimeMillis();
	 		QueryCustFileExtResponse custFileExtResponse = custFileSV.queryCustFileExt(custFileExtRequest);
	 		long cmcustFileTime = System.currentTimeMillis();
	 		LOGGER.info("查询cm_cust_file_ext耗时----------"+(cmcustFileTime-querycusttime));
	 		
	 		List<CmCustFileExtVo> list = custFileExtResponse.getList();
	 		
	 		Map<String, Object> model = new HashMap<String, Object>();
	 		
	 		for(int i=0;i<list.size();i++){
	 			CmCustFileExtVo extVp = list.get(i);
	 			/**
	 			 * 扫描件合同
	 			 */
	 			String infoName = extVp.getInfoName();
	 			String attrValue = extVp.getAttrValue();
	 			String infoItem = extVp.getInfoItem();
	 			
	 			if(ChWebConstants.SCAN_CONTRACT_SHOP.equals(extVp.getInfoItem())){
		 			model.put("scanContractInfoName",infoName);
		 			model.put("scanDownLoadName", new Date().getTime()+infoName.substring(infoName.lastIndexOf("."),infoName.length()));
		 			model.put("scanContractAttrValue",attrValue);
		 			model.put("scanContractInfoItem",infoItem);
	 			}else{
	 				model.put("electronicContractInfoName",infoName);
	 				if(!"".equals(infoName)){
	 					model.put("electronicDownLoadName", new Date().getTime()+infoName.substring(infoName.lastIndexOf("."),infoName.length()));
	 				}
	 				model.put("electronicContractAttrValue",attrValue);
		 			model.put("electronicContractInfoItem",infoItem);
	 			}
	 		}
	 		
	 		model.put("contactInfo", response);
	 		int remarkLength = 0;
	 		if(response.getRemark()!=null){
	 			remarkLength = response.getRemark().length();
	 			if(remarkLength>100){
	 				model.put("remarkLength", true);
	 			}else{
	 				model.put("remarkLength", false);
	 			}
	 		}
	 		if(response.getActiveTime()!=null){
	 			model.put("startTime", DateUtil.getDateString(response.getActiveTime(),"yyyy-MM-dd"));
	 		}
	 		if(response.getInactiveTime()!=null){
	 		  model.put("endTime", DateUtil.getDateString(response.getInactiveTime(),"yyyy-MM-dd"));
	 		}
	 		try {
				model.put("userName", URLDecoder.decode(userName,"utf-8"));
				model.put("custName", URLDecoder.decode(custName,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	 		long endTime = System.currentTimeMillis();
		    LOGGER.info("合同管理，店铺合同页面请求结束----------"+endTime);
		    LOGGER.info("店铺合同详情一共耗时"+(endTime-startTime));
	        return new ModelAndView("/jsp/contract/shop/contractDetail",model);
	 }
	 /**
	  * 店铺添加合同信息
	  * @param request
	  * @param contactInfo
	  * @return
	  */
	 @RequestMapping("/addShopContractInfo")
	 @ResponseBody
	 public ModelAndView addShopContractInfo(HttpServletRequest request,ContactInfoRequest contractInfo,CustFileListVo custFileListVo) {
	       	long start = System.currentTimeMillis();
		 	LOGGER.info("添加店铺合同信息开始----------"+start);
		    GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
	        try{
	        	String startTime = request.getParameter("startTime");
	 	        String endTime = request.getParameter("endTime");
	 	        contractInfo.setActiveTime(DateUtil.getTimestamp(startTime));
	        	contractInfo.setInactiveTime(DateUtil.getTimestamp(endTime));
	        	contractInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SHOP);
	        	contractInfo.setTenantId(user.getTenantId());
	        	contractInfo.setUserId(contractInfo.getUserId());
	        	
	        	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
	        	ICustFileSV custFileSV = DubboConsumerFactory.getService("iCustfileSV");
	        	
	        	long insertcontracttime = System.currentTimeMillis();
	 	        contract.insertContractInfo(contractInfo);
	 	        long insertContractTime = System.currentTimeMillis();
	 	        LOGGER.info("添加店铺合同信息表ct_contract_info耗时----------"+(insertContractTime-insertcontracttime));
	 	        
	 	        long uploadtime = System.currentTimeMillis();
	 	        MultipartHttpServletRequest file = (MultipartHttpServletRequest) request;
	 	        MultipartFile multiScanFile = file.getFile("scanFile");
	 	        MultipartFile multiElectronicFile = file.getFile("electronicFile");
	 	        //保存附件信息
	 	        UpdateCustFileExtRequest updateCustFileExtRequest = new UpdateCustFileExtRequest();
	 	        List<CmCustFileExtVo> list = custFileListVo.getList();
	 	        List<CmCustFileExtVo> fileList = new ArrayList<CmCustFileExtVo>();
	 	        for(CmCustFileExtVo extVo : list){
	 	        	if(multiScanFile.getSize()>0&&!"".equals(extVo.getInfoName())&&ChWebConstants.SCAN_CONTRACT_SHOP.equals(extVo.getInfoItem())){
	 	        		if(extVo.getInfoName().contains(".PNG")||extVo.getInfoName().contains(".JPG")||extVo.getInfoName().contains(".png")||extVo.getInfoName().contains(".jpg")){
			 	           String idpsns = "ch-user-web-idps";
		 	          	   IImageClient im = IDPSClientFactory.getImageClient(idpsns);
		 	          	   String idpsId = im.upLoadImage(multiScanFile.getBytes(), UUIDUtil.genId32() + ".png");
		 	          	   extVo.setAttrValue(idpsId);
		 	          	   fileList.add(extVo);
			 	         }else{
			 	        	String dssns = "ch-user-detail-dss";
		 	 	        	IDSSClient client=DSSClientFactory.getDSSClient(dssns);
		 	 	    		String fileId=client.save(multiScanFile.getBytes(), "remark");
		 	 	    		extVo.setAttrValue(fileId);
			 	          	fileList.add(extVo);
			 	         }
	 	        	 }
	 	        	if(multiElectronicFile.getSize()>0&&!"".equals(extVo.getInfoName())&&ChWebConstants.ELECTRONIC_CONTRACT_SHOP.equals(extVo.getInfoItem())){
	 	        		if(extVo.getInfoName().contains(".PNG")||extVo.getInfoName().contains(".JPG")||extVo.getInfoName().contains(".png")||extVo.getInfoName().contains(".jpg")){
			 	           String idpsns = "ch-user-web-idps";
			 	           IImageClient im = IDPSClientFactory.getImageClient(idpsns);
			 	           String idpsId = im.upLoadImage(multiElectronicFile.getBytes(), UUIDUtil.genId32() + ".png");
			 	           extVo.setAttrValue(idpsId);
			 	           fileList.add(extVo);
			 	         
	 	        		}else{
	 	        			String dssns = "ch-user-detail-dss";
		 	 	        	IDSSClient client=DSSClientFactory.getDSSClient(dssns);
		 	 	    		String fileId=client.save(multiElectronicFile.getBytes(), "remark");
		 	 	    		extVo.setAttrValue(fileId);
			 	          	fileList.add(extVo);
			 	         }
	 	        	 }
	 	        	if("".equals(extVo.getInfoName())&&ChWebConstants.ELECTRONIC_CONTRACT_SHOP.equals(extVo.getInfoItem())){
	 	        		fileList.add(extVo);
	 	        	}
	 	        }
	 	        
	 	       long uploadTimeEnd = System.currentTimeMillis();
	 	       LOGGER.info("上传合同附件耗时----------"+(uploadTimeEnd-uploadtime));
	 	       
	 	       long updatecustfilestart = System.currentTimeMillis();
	 	        if(!CollectionUtil.isEmpty(fileList)&&fileList.size()>0){
		 	    	  updateCustFileExtRequest.setList(fileList);
			          custFileSV.updateCustFileExtBycondition(updateCustFileExtRequest);
		 	     }
	 	      long insertCustFileTime = System.currentTimeMillis();
	 	      LOGGER.info("添加附件信息表耗时----------"+(insertCustFileTime-updatecustfilestart));
	 	      
	        }catch(Exception e){
	        	LOGGER.error("操作失败");
	        	return new ModelAndView("/jsp/contract/shop/fail");
	        }
	        long end = System.currentTimeMillis();
	        LOGGER.info("添加供应商合同信息共耗时----------"+(end-start));
	        return new ModelAndView("/jsp/contract/shop/success");
	        
	 }
	 /**
	  * 供应商添加合同信息
	  * @param request
	  * @param contactInfo
	  * @return
	  */
	 @RequestMapping("/addSupplierContractInfo")
	 @ResponseBody
	 public ModelAndView addSupplierContractInfo(HttpServletRequest request,ContactInfoRequest contractInfo,CustFileListVo custFileListVo) {
		 	
		 	long start = System.currentTimeMillis();
		 	LOGGER.info("添加供应商合同信息开始----------"+start);
		 	
	        String startTime = request.getParameter("startTime");
	        String endTime = request.getParameter("endTime");
	        GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
	        try{
	        	//保存合同信息
	        	contractInfo.setActiveTime(DateUtil.getTimestamp(startTime));
	        	contractInfo.setInactiveTime(DateUtil.getTimestamp(endTime));
	        	contractInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SUPPLIER);
	        	contractInfo.setTenantId(user.getTenantId());
	        	contractInfo.setUserId(contractInfo.getUserId());
	        	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
	        	ICustFileSV custFileSV = DubboConsumerFactory.getService("iCustfileSV");
	        	
	        	long insertcontract = System.currentTimeMillis();
	 	        contract.insertContractInfo(contractInfo);
	 	        long insertContractTime = System.currentTimeMillis();
	 	        LOGGER.info("添加店铺合同信息表ct_contract_info耗时----------"+(insertContractTime-insertcontract));
	 	        
	 	        long uploadfiletime = System.currentTimeMillis();
	 	        MultipartHttpServletRequest file = (MultipartHttpServletRequest) request;
	 	        MultipartFile multiScanFile = file.getFile("scanFile");
	 	        MultipartFile multiElectronicFile = file.getFile("electronicFile");
	 	        //保存附件信息
	 	        UpdateCustFileExtRequest updateCustFileExtRequest = new UpdateCustFileExtRequest();
	 	        List<CmCustFileExtVo> list = custFileListVo.getList();
	 	        List<CmCustFileExtVo> fileList = new ArrayList<CmCustFileExtVo>();
	 	        for(CmCustFileExtVo extVo : list){
	 	        	if(multiScanFile.getSize()>0&&!"".equals(extVo.getInfoName())&&ChWebConstants.SCAN_CONTRACT_SUPPLIER.equals(extVo.getInfoItem())){
	 	        		if(extVo.getInfoName().contains(".PNG")||extVo.getInfoName().contains(".JPG")||extVo.getInfoName().contains(".png")||extVo.getInfoName().contains(".jpg")){
			 	           String idpsns = "ch-user-web-idps";
		 	          	   IImageClient im = IDPSClientFactory.getImageClient(idpsns);
		 	          	   String idpsId = im.upLoadImage(multiScanFile.getBytes(), UUIDUtil.genId32() + ".png");
		 	          	   extVo.setAttrValue(idpsId);
		 	          	   fileList.add(extVo);
			 	         }else{
			 	        	String dssns = "ch-user-detail-dss";
		 	 	        	IDSSClient client=DSSClientFactory.getDSSClient(dssns);
		 	 	    		String fileId=client.save(multiScanFile.getBytes(), "remark");
		 	 	    		extVo.setAttrValue(fileId);
			 	          	fileList.add(extVo);
			 	         }
	 	        	 }
	 	        	if(multiElectronicFile.getSize()>0&&!"".equals(extVo.getInfoName())&&ChWebConstants.ELECTRONIC_CONTRACT_SUPPLIER.equals(extVo.getInfoItem())){
	 	        		if(extVo.getInfoName().contains(".PNG")||extVo.getInfoName().contains(".JPG")||extVo.getInfoName().contains(".png")||extVo.getInfoName().contains(".jpg")){
			 	           String idpsns = "ch-user-web-idps";
			 	           IImageClient im = IDPSClientFactory.getImageClient(idpsns);
			 	           String idpsId = im.upLoadImage(multiElectronicFile.getBytes(), UUIDUtil.genId32() + ".png");
			 	           extVo.setAttrValue(idpsId);
			 	           fileList.add(extVo);
			 	         
	 	        		}else{
	 	        			String dssns = "ch-user-detail-dss";
		 	 	        	IDSSClient client=DSSClientFactory.getDSSClient(dssns);
		 	 	    		String fileId=client.save(multiElectronicFile.getBytes(), "remark");
		 	 	    		extVo.setAttrValue(fileId);
			 	          	fileList.add(extVo);
			 	         }
	 	        	 }
	 	        	if("".equals(extVo.getInfoName())&&ChWebConstants.ELECTRONIC_CONTRACT_SUPPLIER.equals(extVo.getInfoItem())){
	 	        		fileList.add(extVo);
	 	        	}
	 	        }
	 	       long uploadTime = System.currentTimeMillis();
	 	       LOGGER.info("上传合同附件耗时----------"+(uploadTime-uploadfiletime));	
	 	        if(!CollectionUtil.isEmpty(fileList)&&fileList.size()>0){
		 	    	  updateCustFileExtRequest.setList(fileList);
			          custFileSV.updateCustFileExtBycondition(updateCustFileExtRequest);
		 	     }
	 	       long insertCustFileTime = System.currentTimeMillis();
		 	      LOGGER.info("添加附件信息表耗时----------"+(insertCustFileTime-uploadTime));
	        }catch(Exception e){
	        	LOGGER.error("操作失败");
	        	return new ModelAndView("/jsp/contract/supplier/fail");
	        }
	        long end = System.currentTimeMillis();
	        LOGGER.info("添加供应商合同信息共耗时----------"+(end-start));
	        return new ModelAndView("/jsp/contract/supplier/success");
	        
	 }
	 
	 
 	// 上传文件
    @RequestMapping(value = "/uploadFile", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String uploadFile(HttpServletRequest request,String contractFileId) {
    	
    	 Map<String, Object> map = new HashMap<String, Object>();
         MultipartHttpServletRequest file = (MultipartHttpServletRequest) request;
         MultipartFile multiFile = file.getFile(contractFileId);
         System.out.println(file.getLocalName());
        String dssns = "ch-user-detail-dss";
        try{
        	IDSSClient client=DSSClientFactory.getDSSClient(dssns);
    		String fileId=client.save(multiFile.getBytes(), "remark");
    		map.put("isTrue", true);
    		map.put("dssId", fileId);
        }catch(Exception e){
        	 LOGGER.error("上传失败");
             map.put("isTrue", false);
        }
        return JSON.toJSONString(map);
       }
    
 // 上传文件
    @RequestMapping(value = "/uploadImage", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String uploadImage(HttpServletRequest request,String contractFileId) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
         MultipartHttpServletRequest file = (MultipartHttpServletRequest) request;
         MultipartFile multiFile = file.getFile(contractFileId);
        String idpsns = "ch-user-web-idps";
        try{
        	IImageClient im = IDPSClientFactory.getImageClient(idpsns);
        	String idpsId = im.upLoadImage(multiFile.getBytes(), UUIDUtil.genId32() + ".png");
    		map.put("isTrue", true);
    		map.put("dssId", idpsId);
        }catch(Exception e){
        	 LOGGER.error("上传失败");
             map.put("isTrue", false);
        }
        return JSON.toJSONString(map);
       }
    
    
    @RequestMapping("/download")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,String fileName, String attrValue) {
		long startTime = System.currentTimeMillis();
    	LOGGER.info("下载文件开始---------------"+startTime);
    	OutputStream os = null;
		try {
			LOGGER.info("response-------"+response);
			os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setContentType("application/pdf");// 定义输出类型
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);// 设定输出文件头
			String dssns = "ch-user-detail-dss";
			IDSSClient client=DSSClientFactory.getDSSClient(dssns);
			LOGGER.info("client-------"+client);
	    	byte[] b=client.read(attrValue);
	    	LOGGER.info("os-------"+os);
	        os.write(b); 
	        os.flush(); 
			os.close();
		} catch (Exception e) {
			LOGGER.error("下载文件失败",e);
			if(os!=null){
				try {
					os.close();
				} catch (IOException e1) {
					LOGGER.error("操作异常",e1);
				}
			}
		}
		long endTime = System.currentTimeMillis();
    	LOGGER.info("下载文件结束---------------"+(endTime-startTime));
	}
    
    @RequestMapping("/downloadImage/{fileName}")
	public void downloadImage(HttpServletRequest request, HttpServletResponse response,String fileName, String attrValue) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();// 取得输出流
			String exportFileName = fileName;
			response.reset();// 清空输出流
			response.setContentType("application/pdf");// 定义输出类型
			response.setHeader("Content-disposition", "attachment; filename=" + exportFileName);// 设定输出文件头
			 String idpsns = "ch-user-web-idps";
			 IImageClient client=IDPSClientFactory.getImageClient(idpsns);
	    	 //byte[] b=client.getImage(imageId, imageType, imageScale)
	         //os.write(b); 
	         os.flush(); 
			 os.close();
		} catch (Exception e) {
			LOGGER.error("下载文件失败",e);
			if(os!=null){
				try {
					os.close();
				} catch (IOException e1) {
					LOGGER.error("操作异常",e1);
				}
			}
		}

	}
    
   public Map<String,ContractInfoResponse> getContractList(String tenantId){
    	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
	 	ContactInfoRequest contactInfo = new ContactInfoRequest();
	    contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SUPPLIER);
	 	contactInfo.setTenantId(tenantId);
	 	Map<String,ContractInfoResponse> map = new HashMap<String,ContractInfoResponse>();
	 	List<ContractInfoResponse> responseList = contract.queryAllContractInfo(contactInfo);
	 	if(responseList!=null&&responseList.size()!=0){
	 	for(int i=0;i<responseList.size();i++){
	 		ContractInfoResponse contractInfo = responseList.get(i);
	 		map.put(contractInfo.getUserId()+contractInfo.getContractType(), contractInfo);
	 	}
	 	}
	 	return map;
    }
    
    @RequestMapping("/checkContractName")
    @ResponseBody
    public ResponseData<String> checkContractName(HttpServletRequest request,ContactInfoRequest contractRequest){
    	ResponseData<String> responseData = null;
        ResponseHeader header = null;
        
        try {
            IContractSV contractSV = DubboConsumerFactory.getService("iContractSV");
            String userId = contractRequest.getUserId();
            contractRequest.setUserId(null);
            ContractInfoResponse accountQueryResponse = contractSV.queryContractInfo(contractRequest);
            if (accountQueryResponse != null) {
                String resultCode = accountQueryResponse.getResponseHeader().getResultCode();
                if (resultCode.equals(ExceptionCode.SUCCESS_CODE)) {
                    if(accountQueryResponse.getUserId()!=null&&!userId.equals(accountQueryResponse.getUserId())){
                    	header = new ResponseHeader(false, ExceptionCode.CONTRACT_NAME_ERROR, "该合同名称已经注册");
                        responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "该合同名称已经注册", null);
                        responseData.setResponseHeader(header);
                    }else{
                    	  header = new ResponseHeader(false, ExceptionCode.SUCCESS_CODE, "成功");
                          responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功", null);
                          responseData.setResponseHeader(header);
                    }
                	
                } else {
                    header = new ResponseHeader(false, ExceptionCode.SUCCESS_CODE, "成功");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功", null);
                    responseData.setResponseHeader(header);
                }
            }
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "合同名称校验失败", null);
        }
        return responseData;
    }
    
    @RequestMapping("/checkContractCode")
    @ResponseBody
    public ResponseData<String> checkContractCode(HttpServletRequest request,ContactInfoRequest contractRequest){
    	ResponseData<String> responseData = null;
        ResponseHeader header = null;
        
        try {
            IContractSV contractSV = DubboConsumerFactory.getService("iContractSV");
            String userId = contractRequest.getUserId();
            contractRequest.setUserId(null);
            ContractInfoResponse accountQueryResponse = contractSV.queryContractInfo(contractRequest);
            if (accountQueryResponse != null) {
                String resultCode = accountQueryResponse.getResponseHeader().getResultCode();
                if (resultCode.equals(ExceptionCode.SUCCESS_CODE)) {
                    if(accountQueryResponse.getUserId()!=null&&!userId.equals(accountQueryResponse.getUserId())){
                    	header = new ResponseHeader(false, ExceptionCode.CONTRACT_NAME_ERROR, "该合同编号已经注册");
                        responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "该合同编号已经注册", null);
                        responseData.setResponseHeader(header);
                    }else{
                    	 header = new ResponseHeader(false, ExceptionCode.SUCCESS_CODE, "成功");
                         responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功", null);
                         responseData.setResponseHeader(header);
                    }
                	
                } else {
                    header = new ResponseHeader(false, ExceptionCode.SUCCESS_CODE, "成功");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功", null);
                    responseData.setResponseHeader(header);
                }
            }
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "合同编号校验失败", null);
        }
        return responseData;
    }
    
    
	//查询列表
	@RequestMapping("/getList")
	@ResponseBody
	public ResponseData<PageInfo<BusinessListInfo>> getList(HttpServletRequest request,String companyName,String username,String companyType){
		long startTime = System.currentTimeMillis();
		LOGGER.info("查询列表开始-----------------"+startTime);
		ResponseData<PageInfo<BusinessListInfo>> response = null;
		PageInfo<BusinessListInfo> pageInfo =new PageInfo<>();
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
		long sendPostTime = System.currentTimeMillis();
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("searchCompanyList_http_url"), JSON.toJSONString(map),mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		long sendPostEndTime = System.currentTimeMillis();
		LOGGER.info("通过o2p获取列表信息耗时----------"+(sendPostEndTime-sendPostTime));
		List<BusinessListInfo> responseList = new ArrayList<>();
		long resultstarttime = System.currentTimeMillis();
		try{
			JSONObject data = ParseO2pDataUtil.getData(str);
			String resultCode = data.getString("resultCode");
			/**
			 * 如果resultCode不为空说明获取结果是正确的
			 */
			if (resultCode!=null&&!OperateCode.SUCCESS.equals(resultCode)){
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
				header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "操作失败"); 
				response.setResponseHeader(header);
			}else{
					Integer pageNo = Integer.valueOf(data.getString("pages"));
					Integer pageSize = Integer.valueOf(data.getString("pageSize"));
					Integer total = Integer.valueOf(data.getString("total"));
					Integer pageCount = Integer.valueOf(data.getString("pageNum"));
					pageInfo.setCount(total);
					pageInfo.setPageCount(pageCount);
					pageInfo.setPageNo(pageNo);
					pageInfo.setPageSize(pageSize);
					JSONArray list =(JSONArray) JSON.parseArray(data.getString("list"));
					Iterator<Object> iterator = list.iterator();
					GeneralSSOClientUser userClient = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
					Map<String,ContractInfoResponse> contractMap = getContractList(userClient.getTenantId());
					while(iterator.hasNext()){
						BusinessListInfo businessInfo = new BusinessListInfo(); 
						 JSONObject object = (JSONObject) iterator.next();
						 businessInfo.setUserId(object.getString("companyId"));
						 businessInfo.setUserName(object.getString("username"));
						 businessInfo.setCustName(object.getString("name"));
						 if(contractMap.get(businessInfo.getUserId()+companyType)!=null){
								businessInfo.setUploadStatus("已上传");
							}else{
								businessInfo.setUploadStatus("未上传");
							}
						 responseList.add(businessInfo);
					}
					pageInfo.setResult(responseList);
					response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "查询成功");
					header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "查询成功");
			}
			response.setResponseHeader(header);
			response.setData(pageInfo);
		}catch(Exception e){
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
			header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "操作失败"); 
			response.setResponseHeader(header);
		}
		long listTime = System.currentTimeMillis();
		LOGGER.info("处理返回结果耗时--------------"+(listTime-resultstarttime));
		long endTime = System.currentTimeMillis();
		LOGGER.info("查询列表结束-----------------"+(endTime-startTime));
		return response;
	}
	
	@RequestMapping("/deleteExtFile")
    @ResponseBody
    public ResponseData<Object> deleteExtFile(HttpServletRequest request,String contractFileId,String infoExtId){
    	ResponseData<Object> responseData = null;
        ResponseHeader header = null;
        ICustFileSV custFileSv = DubboConsumerFactory.getService(ICustFileSV.class);
        try {
        	
            String dssns = "ch-user-detail-dss";
            IDSSClient client=DSSClientFactory.getDSSClient(dssns);
            if(!StringUtil.isBlank(contractFileId)){
            	client.delete(contractFileId);
            }
    		
    		
    		GeneralSSOClientUser userClient = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
    		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
    		
    		if(!StringUtil.isBlank(infoExtId)){
	    		custFileExtRequest.setInfoExtId(infoExtId);
	    		custFileExtRequest.setTenantId(userClient.getTenantId());
	    		custFileSv.deleteCustFileExtBycondition(custFileExtRequest);
    		}
			header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
			responseData = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
        } catch (Exception e) {
        	responseData = new ResponseData<Object>(ResponseData.AJAX_STATUS_FAILURE, "图片删除失败", null);
        }
        responseData.setResponseHeader(header);
        return responseData;
    }
}