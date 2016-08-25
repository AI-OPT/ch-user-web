package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
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
import com.ai.ch.user.api.contract.param.ContactInfoResponse;
import com.ai.ch.user.api.custfile.interfaces.ICustFileSV;
import com.ai.ch.user.api.custfile.params.CmCustFileExtVo;
import com.ai.ch.user.api.custfile.params.InsertCustFileExtRequest;
import com.ai.ch.user.api.custfile.params.QueryCustFileExtRequest;
import com.ai.ch.user.api.custfile.params.QueryCustFileExtResponse;
import com.ai.ch.user.api.custfile.params.UpdateCustFileExtRequest;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.ExceptionCode;
import com.ai.ch.user.web.model.user.CustFileListVo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.dss.DSSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.dss.base.interfaces.IDSSClient;

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
	 public ModelAndView contractManagerPager(HttpServletRequest request,String userId) {
		 	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
		 	ContactInfoRequest contactInfo = new ContactInfoRequest();
		 	contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SUPPLIER);
		 	contactInfo.setTenantId(ChWebConstants.COM_TENANT_ID);
		 	contactInfo.setUserId(userId);
	 		ContactInfoResponse response = contract.queryContractInfo(contactInfo);
	 		response.setUserId(userId);
	 		Map<String, Object> model = new HashMap<String, Object>();
	 		model.put("contactInfo", response);
	        return new ModelAndView("/jsp/contract/supplier/contractManager",model);
	 }
	 /**
	  * 店铺管理页面信息
	  * @param request
	  * @param userId
	  * @return
	  */
	 @RequestMapping("/contractShopManagerPager")
	 public ModelAndView contractShopManagerPager(HttpServletRequest request,String userId) {
		 	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
		 	ContactInfoRequest contactInfo = new ContactInfoRequest();
		 	contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SHOP);
		 	contactInfo.setTenantId(ChWebConstants.COM_TENANT_ID);
		 	contactInfo.setUserId(userId);
	 		ContactInfoResponse response = contract.queryContractInfo(contactInfo);
	 		Map<String, Object> model = new HashMap<String, Object>();
	 		model.put("contactInfo", response);
	        return new ModelAndView("/jsp/contract/shop/contractManager",model);
	 }
	 
	 /**
	  * 合同供应商查看页面信息
	  * @param request
	  * @param userId
	  * @return
	  */
	 
	 @RequestMapping("/contractSupplierDetailPager")
	 public ModelAndView contractSupplierDetailPager(HttpServletRequest request,String userId) {
		    IContractSV contract = DubboConsumerFactory.getService("iContractSV");
		    ICustFileSV custFileSV = DubboConsumerFactory.getService("iCustfileSV");
		    
		 	ContactInfoRequest contactInfo = new ContactInfoRequest();
		 	contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SUPPLIER);
		 	contactInfo.setTenantId(ChWebConstants.COM_TENANT_ID);
		 	contactInfo.setUserId(userId);
		 	
	 		ContactInfoResponse response = contract.queryContractInfo(contactInfo);
	 		
	 		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
	 		custFileExtRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
	 		custFileExtRequest.setUsreId(userId);
	 		QueryCustFileExtResponse custFileExtResponse = custFileSV.queryCustFileExt(custFileExtRequest);
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
		 			model.put("scanContractAttrValue",attrValue);
		 			model.put("scanContractInfoItem",infoItem);
	 			}else{
	 				model.put("electronicContractInfoName",infoName);
		 			model.put("electronicContractAttrValue",attrValue);
		 			model.put("electronicContractInfoItem",infoItem);
	 			}
	 		}
	 			
	 		model.put("contactInfo", response);
	 		model.put("startTime", DateUtil.getDateString(response.getActiveTime(),"yyyy-MM-dd"));
	 		model.put("endTime", DateUtil.getDateString(response.getInactiveTime(),"yyyy-MM-dd"));
	        return new ModelAndView("/jsp/contract/supplier/contractDetail",model);
	 }
	 
	 /**
	  * 合同店铺查看页面信息
	  * @param request
	  * @param userId
	  * @return
	  */
	 
	 @RequestMapping("/contractShopDetailPager")
	 public ModelAndView contractShopDetailPager(HttpServletRequest request,String userId) {
		    IContractSV contract = DubboConsumerFactory.getService("iContractSV");
		    ICustFileSV custFileSV = DubboConsumerFactory.getService("iCustfileSV");
		 	ContactInfoRequest contactInfo = new ContactInfoRequest();
		 	contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SHOP);
		 	contactInfo.setTenantId(ChWebConstants.COM_TENANT_ID);
		 	contactInfo.setUserId(userId);
	 		ContactInfoResponse response = contract.queryContractInfo(contactInfo);
	 		/**
	 		 * 附件信息
	 		 */
	 		QueryCustFileExtRequest custFileExtRequest = new QueryCustFileExtRequest();
	 		custFileExtRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
	 		custFileExtRequest.setUsreId(userId);
	 		QueryCustFileExtResponse custFileExtResponse = custFileSV.queryCustFileExt(custFileExtRequest);
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
		 			model.put("scanContractAttrValue",attrValue);
		 			model.put("scanContractInfoItem",infoItem);
	 			}else{
	 				model.put("electronicContractInfoName",infoName);
		 			model.put("electronicContractAttrValue",attrValue);
		 			model.put("electronicContractInfoItem",infoItem);
	 			}
	 		}
	 		
	 		
	 		model.put("contactInfo", response);
	 		model.put("startTime", DateUtil.getDateString(response.getActiveTime(),"yyyy-MM-dd"));
	 		model.put("endTime", DateUtil.getDateString(response.getInactiveTime(),"yyyy-MM-dd"));
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
	 public ResponseData<String> addShopContractInfo(HttpServletRequest request,ContactInfoRequest contractInfo,CustFileListVo custFileListVo) {
	        ResponseData<String> responseData = null;
	        ResponseHeader responseHeader = null;
	        try{
	        	String startTime = request.getParameter("startTime");
	 	        String endTime = request.getParameter("endTime");
	 	        contractInfo.setActiveTime(DateUtil.getTimestamp(startTime));
	        	contractInfo.setInactiveTime(DateUtil.getTimestamp(endTime));
	        	contractInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SHOP);
	        	contractInfo.setTenantId(ChWebConstants.COM_TENANT_ID);
	        	contractInfo.setUserId(contractInfo.getUserId());
	        	
	        	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
	        	ICustFileSV custFileSV = DubboConsumerFactory.getService("iCustfileSV");
	        	
	 	        contract.insertContractInfo(contractInfo);
	 	        
	 	       //保存附件信息
	 	        UpdateCustFileExtRequest updateCustFileExtRequest = new UpdateCustFileExtRequest();
	 	        updateCustFileExtRequest.setList(custFileListVo.getList());
	            custFileSV.updateCustFileExtBycondition(updateCustFileExtRequest);
	 	        
	 	        
	        	responseData = new ResponseData<String>(ExceptionCode.SUCCESS_CODE, "操作成功", null);
                responseHeader = new ResponseHeader(true,ExceptionCode.SUCCESS_CODE, "操作成功");
	        }catch(Exception e){
	        	LOGGER.error("操作失败");
	        	responseData = new ResponseData<String>(ExceptionCode.ERROR_CODE, "操作成功", null);
                responseHeader = new ResponseHeader(true,ExceptionCode.ERROR_CODE, "操作成功");
	        }
	        responseData.setResponseHeader(responseHeader);
	        return responseData;
	 }
	 /**
	  * 供应商添加合同信息
	  * @param request
	  * @param contactInfo
	  * @return
	  */
	 @RequestMapping("/addSupplierContractInfo")
	 @ResponseBody
	 public ResponseData<String> addSupplierContractInfo(HttpServletRequest request,ContactInfoRequest contractInfo,CustFileListVo custFileListVo) {

	        ResponseData<String> responseData = null;
	        ResponseHeader responseHeader = null;
	        String startTime = request.getParameter("startTime");
	        String endTime = request.getParameter("endTime");
	
	        try{
	        	
	        	//保存合同信息
	        	contractInfo.setActiveTime(DateUtil.getTimestamp(startTime));
	        	contractInfo.setInactiveTime(DateUtil.getTimestamp(endTime));
	        	contractInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SUPPLIER);
	        	contractInfo.setTenantId(ChWebConstants.COM_TENANT_ID);
	        	contractInfo.setUserId(contractInfo.getUserId());
	        	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
	        	ICustFileSV custFileSV = DubboConsumerFactory.getService("iCustfileSV");
	 	        contract.insertContractInfo(contractInfo);
	 	        
	 	        //保存附件信息
	 	        UpdateCustFileExtRequest updateCustFileExtRequest = new UpdateCustFileExtRequest();
	 	        updateCustFileExtRequest.setList(custFileListVo.getList());
	            custFileSV.updateCustFileExtBycondition(updateCustFileExtRequest);
	 	        
	        	responseData = new ResponseData<String>(ExceptionCode.SUCCESS_CODE, "操作成功", null);
                responseHeader = new ResponseHeader(true,ExceptionCode.SUCCESS_CODE, "操作成功");
	        }catch(Exception e){
	        	LOGGER.error("操作失败");
	        	responseData = new ResponseData<String>(ExceptionCode.ERROR_CODE, "操作成功", null);
                responseHeader = new ResponseHeader(true,ExceptionCode.ERROR_CODE, "操作成功");
	        }
	        responseData.setResponseHeader(responseHeader);
	        return responseData;
	 }
	 
	 
 	// 上传文件
    @RequestMapping(value = "/uploadFile", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> uploadFile(HttpServletRequest request,String contractFileId) {
    	
    	 Map<String, Object> map = new HashMap<String, Object>();
         MultipartHttpServletRequest file = (MultipartHttpServletRequest) request;
         MultipartFile multiFile = file.getFile(contractFileId);
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
        return map;
       }
    
    @RequestMapping("/download/{fileName}")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,String fileName, String attrValue) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();// 取得输出流
			String exportFileName = fileName;
			response.reset();// 清空输出流
			response.setContentType("application/pdf");// 定义输出类型
			response.setHeader("Content-disposition", "attachment; filename=" + exportFileName);// 设定输出文件头
			 String dssns = "ch-user-detail-dss";
			 IDSSClient client=DSSClientFactory.getDSSClient(dssns);
	    	 byte[] b=client.read(attrValue);
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

	}
}