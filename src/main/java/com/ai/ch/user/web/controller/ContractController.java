package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.ExceptionCode;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.image.IImageClient;
import com.alibaba.fastjson.JSON;

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
		 	contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SUPPLIER);
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
	 
	 @RequestMapping("/contractDetailPager")
	 public ModelAndView contractDetail(HttpServletRequest request,String userId) {
		 IContractSV contract = DubboConsumerFactory.getService("iContractSV");
		 	ContactInfoRequest contactInfo = new ContactInfoRequest();
		 	contactInfo.setContractType(ChWebConstants.CONTRACT_TYPE_SUPPLIER);
		 	contactInfo.setTenantId(ChWebConstants.COM_TENANT_ID);
		 	contactInfo.setUserId(userId);
	 		ContactInfoResponse response = contract.queryContractInfo(contactInfo);
	 		Map<String, Object> model = new HashMap<String, Object>();
	 		model.put("contactInfo", response);
	        return new ModelAndView("/jsp/contract/supplier/contractDetail",model);
	 }
	 /**
	  * 店铺添加合同信息
	  * @param request
	  * @param contactInfo
	  * @return
	  */
	 @RequestMapping("/addShopContractInfo")
	 @ResponseBody
	 public ResponseData<String> addShopContractInfo(HttpServletRequest request,ContactInfoRequest contactInfo) {

	        ResponseData<String> responseData = null;
	        ResponseHeader responseHeader = null;
	        try{
	        	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
	 	        contract.insertContractInfo(contactInfo);
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
	 public ResponseData<String> addContractInfo(HttpServletRequest request,ContactInfoRequest contactInfo) {

	        ResponseData<String> responseData = null;
	        ResponseHeader responseHeader = null;
	        try{
	        	IContractSV contract = DubboConsumerFactory.getService("iContractSV");
	 	        contract.insertContractInfo(contactInfo);
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
	 
	 
 	// 上传图片
    @RequestMapping(value = "/uploadImg", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> uploadImg(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        String imageId = request.getParameter("imageId");
        MultipartHttpServletRequest file = (MultipartHttpServletRequest) request;
        MultipartFile image = file.getFile(imageId);
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
        // 获取图片信息
        try {
            String idpsId = im.upLoadImage(image.getBytes(), UUIDUtil.genId32() + ".png");
            String url = im.getImageUrl(idpsId, ".jpg", "80x80");
            map.put("isTrue", true);
            map.put("idpsId", idpsId);
            map.put("url", url);
        } catch (IOException e) {
            map.put("isTrue", false);
        }
        LOGGER.info("Map:---->>" + JSON.toJSONString(map));
        return map;
    }
}
