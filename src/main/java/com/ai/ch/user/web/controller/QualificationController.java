package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.shopinfo.interfaces.IShopInfoSV;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoRequest;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoResponse;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.util.PropertiesUtil;
import com.ai.ch.user.web.vo.BusinessListInfo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/qualification")
public class QualificationController {
	

	//电商平台位置
	static private String[] shopOwner = {"京东","天猫","淘宝","苏宁","一号店","自有电商平台"};
	
	/**
	 * 供应商审核列表页面
	 */
	@RequestMapping("/toCheckedSupplierPager")
	public ModelAndView toCheckedSupplierPager() {
		return new ModelAndView("/jsp/qualification/supplier/checkedPagerList");
	}
	/**
	 * 供应商未审核列表页面
	 */
	@RequestMapping("/toNoCheckedSupplierPager")
	public ModelAndView toNoCheckedSupplierPager() {
		return new ModelAndView("/jsp/qualification/supplier/noCheckedPagerList");
	}
	/**
	 * 店铺审核列表
	 * @return
	 */
	@RequestMapping("/toCheckedShopPager")
	public ModelAndView toCheckedShopPager(HttpServletRequest request) {
		return new ModelAndView("/jsp/qualification/shop/checkedPagerList");
	}
	/**
	 * 店铺未审核列表
	 * @return
	 */
	@RequestMapping("/toNoCheckedShopPager")
	public ModelAndView toNoCheckedShopPager() {
		return new ModelAndView("/jsp/qualification/shop/noCheckedPagerList");
	}
	/**
	 * 供应商审核页面
	 * @return
	 */
	@RequestMapping("/toSuplierCheckPager")
	public ModelAndView toSuplierCheckPager(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/jsp/qualification/supplier/auditeQualification");
		String url=request.getQueryString();
		String userId = url.substring(url.lastIndexOf("userId=")+7, url.lastIndexOf("username=")-1);
		String username = url.substring(url.lastIndexOf("username=")+9);
		//查询账户信息
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", PropertiesUtil.getStringByKey("appkey"));
		map.put("companyId", userId);
		String str ="";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("findByCompanyId_http_url"), JSON.toJSONString(map), mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		JSONObject data = (JSONObject) JSON.parse(str);
		JSONObject data2 = (JSONObject) JSON.parse(data.getString("data"));
		//转换时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String createTime = sdf.format(Long.parseLong(data2.getString("createTime")));
        String taxpayerType = "";
        if("1".equals(data2.getString("taxpayerType"))){
        	taxpayerType = "一般纳税人";
        }else if("2".equals(data2.getString("taxpayerType"))){
        	taxpayerType = "小规模纳税人";
        }else if("3".equals(data2.getString("taxpayerType")))
        	taxpayerType = "非增值税纳税人";
        String legalRepresentative="";
        String phone="";
        String email = "";
        String idNumber="";
        String bankAccount = "";
        String businessAddress = "";
        String location = "";
        //安全处理信息
        if(data2.getString("legalRepresentative")!=null)
        	legalRepresentative = getStarStringNoEnd(data2.getString("legalRepresentative"),1);
        phone = getStarString(data2.getString("phone"),4,7);
        email = getStarString(data2.getString("email"), 2, data2.getString("email").lastIndexOf("@"));
        idNumber = getStarString(data2.getString("idNumber"), data2.getString("idNumber").length()-10, data2.getString("idNumber").length()-4);
        bankAccount = getStarString(data2.getString("bankAccount"), data2.getString("bankAccount").length()-7, data2.getString("bankAccount").length()-3);
        if(data2.getString("businessAddress")!=null){
        	if(data2.getString("businessAddress").length()<4)
        		businessAddress = getStarStringNoEnd(data2.getString("businessAddress"), data2.getString("businessAddress").length()-1);
        	else
        		businessAddress = getStarStringNoEnd(data2.getString("businessAddress"), data2.getString("businessAddress").length()-4);		
        }
        if(data2.getString("location")!=null){
        	if(data2.getString("location").length()<4)
        		location = getStarStringNoEnd(data2.getString("location"), data2.getString("location").length()-1);
        	else
        		location = getStarStringNoEnd(data2.getString("location"), data2.getString("location").length()-4);		
        }
        //System.out.println(JSON.toJSONString(data2));
		model.addObject("userId", userId);
		model.addObject("userName", username);
		model.addObject("shopName", data2.getString("name"));
		model.addObject("createTime",createTime);
		model.addObject("industryType", data2.getString("industryType"));
		model.addObject("officialWebsite", data2.getString("officialWebsite"));
		model.addObject("companiesNumber", data2.getString("companiesNumber"));
		model.addObject("companyNature", data2.getString("companyNature"));
		model.addObject("location", location);
		model.addObject("annualTurnover", data2.getString("annualTurnover"));
		model.addObject("areaCover", data2.getString("areaCover"));
		model.addObject("phone", phone);
		model.addObject("fax", data2.getString("fax"));
		model.addObject("email", email);
		model.addObject("businessAddress", businessAddress);
		model.addObject("businessLicenseRegistrationNumber", data2.getString("businessLicenseRegistrationNumber"));
		model.addObject("establishDate", data2.getString("establishDate"));
		model.addObject("businessScope", data2.getString("businessScope"));
		model.addObject("legalRepresentative", legalRepresentative);
		model.addObject("idNumber", idNumber);
		model.addObject("taxpayerNumber", data2.getString("taxpayerNumber"));
		model.addObject("taxpayerType", taxpayerType);
		model.addObject("taxCode", data2.getString("taxCode"));
		model.addObject("organizationCode", data2.getString("organizationCode"));
		model.addObject("bankName", data2.getString("bankName"));
		model.addObject("bankAccount", bankAccount);
		model.addObject("commodityType", data2.getString("commodityType"));
		model.addObject("brandNameCh", data2.getString("brandNameCh"));
		model.addObject("brandNameEn", data2.getString("brandNameEn"));
		model.addObject("registerCapital", data2.getString("registerCapital"));
		return model;
	}
	/**
	 * 店铺审核页面
	 * @return
	 */
	@RequestMapping("/toShopCheckPager")
	public ModelAndView toShopCheckDetailPager(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/jsp/qualification/shop/auditeQualification");
		String url=request.getQueryString();
		String userId = url.substring(url.lastIndexOf("userId=")+7, url.lastIndexOf("username=")-1);
		String username = url.substring(url.lastIndexOf("username=")+9);
		//查询账户信息
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", PropertiesUtil.getStringByKey("appkey"));
		map.put("companyId", userId);
		String str ="";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("findByCompanyId_http_url"), JSON.toJSONString(map), mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		JSONObject data = (JSONObject) JSON.parse(str);
		JSONObject data2 = (JSONObject) JSON.parse(data.getString("data"));
		//转换时间
		String createTime="";
		//查询商户信息
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest queryShopInfoRequest = new QueryShopInfoRequest();
		queryShopInfoRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
		queryShopInfoRequest.setUserId(userId);
		QueryShopInfoResponse response=shopInfoSV.queryShopInfo(queryShopInfoRequest);
		if(data2.getString("createTime")!=null&&data2.getString("createTime").length()!=0){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		createTime = sdf.format(Long.parseLong(data2.getString("createTime")));
		}
		 String taxpayerType = "";
		 if("1".equals(data2.getString("taxpayerType"))){
	        	taxpayerType = "一般纳税人";
	        }else if("2".equals(data2.getString("taxpayerType"))){
	        	taxpayerType = "小规模纳税人";
	        }else if("3".equals(data2.getString("taxpayerType")))
	        	taxpayerType = "非增值税纳税人";
	        
	        String legalRepresentative="";
	        String phone="";
	        String email = "";
	        String idNumber="";
	        String bankAccount = "";
	        String businessAddress = "";
	        String location = "";
	        //安全处理信息
	        if(data2.getString("legalRepresentative")!=null)
	        	legalRepresentative = getStarStringNoEnd(data2.getString("legalRepresentative"),1);
	        phone = getStarString(data2.getString("phone"),4,7);
	        email = getStarString(data2.getString("email"), 2, data2.getString("email").lastIndexOf("@"));
	        idNumber = getStarString(data2.getString("idNumber"), data2.getString("idNumber").length()-10, data2.getString("idNumber").length()-4);
	        bankAccount = getStarString(data2.getString("bankAccount"), data2.getString("bankAccount").length()-7, data2.getString("bankAccount").length()-3);
	        if(data2.getString("businessAddress")!=null){
	        	if(data2.getString("businessAddress").length()<4)
	        		businessAddress = getStarStringNoEnd(data2.getString("businessAddress"), data2.getString("businessAddress").length()-1);
	        	else
	        		businessAddress = getStarStringNoEnd(data2.getString("businessAddress"), data2.getString("businessAddress").length()-4);		
	        }
	        if(data2.getString("location")!=null){
	        	if(data2.getString("location").length()<4)
	        		location = getStarStringNoEnd(data2.getString("location"), data2.getString("location").length()-1);
	        	else
	        		location = getStarStringNoEnd(data2.getString("location"), data2.getString("location").length()-4);		
	        }
		model.addObject("userId", userId);
		model.addObject("userName", username);
		model.addObject("shopName", data2.getString("name"));
		model.addObject("createTime",createTime);
		model.addObject("industryType", data2.getString("industryType"));
		model.addObject("officialWebsite", data2.getString("officialWebsite"));
		model.addObject("companiesNumber", data2.getString("companiesNumber"));
		model.addObject("companyNature", data2.getString("companyNature"));
		model.addObject("location", location);
		model.addObject("annualTurnover", data2.getString("annualTurnover"));
		model.addObject("areaCover", data2.getString("areaCover"));
		model.addObject("phone", phone);
		model.addObject("fax", data2.getString("fax"));
		model.addObject("email", email);
		model.addObject("businessAddress", businessAddress);
		model.addObject("businessLicenseRegistrationNumber", data2.getString("businessLicenseRegistrationNumber"));
		model.addObject("establishDate", data2.getString("establishDate"));
		model.addObject("businessScope", data2.getString("businessScope"));
		model.addObject("legalRepresentative", legalRepresentative);
		model.addObject("idNumber", idNumber);
		model.addObject("taxpayerNumber", data2.getString("taxpayerNumber"));
		model.addObject("taxpayerType", taxpayerType);
		model.addObject("taxCode", data2.getString("taxCode"));
		model.addObject("organizationCode", data2.getString("organizationCode"));
		model.addObject("bankName", data2.getString("bankName"));
		model.addObject("bankAccount", bankAccount);
		model.addObject("commodityType", data2.getString("commodityType"));
		model.addObject("brandNameCh", data2.getString("brandNameCh"));
		model.addObject("brandNameEn", data2.getString("brandNameEn"));
		if(response!=null){
			String busiType="";
			if("1".equals(response.getBusiType())){
				busiType="生产厂商";
			}else if("2".equals(response.getBusiType())){
				busiType="品牌代理商";
			}
			String hasExperi ="";
			if("0".equals(response.getHasExperi()+"")){
				hasExperi = "无";
			}else if("1".equals(response.getHasExperi()+""))
				hasExperi = "有";
				
			model.addObject("wantShopName", response.getShopName());
			model.addObject("goodsNum", response.getGoodsNum());
			model.addObject("busiType", busiType);
			model.addObject("hasExperi", hasExperi);
			model.addObject("shopDesc", response.getShopDesc());
			String ecommOwner = "";
			if(!response.getEcommOwner().isEmpty()){
				for (int index=0;index<response.getEcommOwner().length();index++) {
					if('1'==response.getEcommOwner().charAt(index))
						ecommOwner+=shopOwner[index]+"/";
				}
			ecommOwner = ecommOwner.substring(0,ecommOwner.length()-1);
			}
			model.addObject("ecommOwner", ecommOwner);
		}
		return model;
	}
	/**
	 * 供应商详情页面
	 * @return
	 */
	@RequestMapping("/toSuplierDetailPager")
	public ModelAndView toSuplierDetailPager(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/jsp/qualification/supplier/checkedDetail");
		String url=request.getQueryString();
		String userId = url.substring(url.lastIndexOf("userId=")+7, url.lastIndexOf("username=")-1);
		String username = url.substring(url.lastIndexOf("username=")+9);
		//查询账户信息
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", PropertiesUtil.getStringByKey("appkey"));
		map.put("companyId", userId);
		String str ="";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("findByCompanyId_http_url"), JSON.toJSONString(map), mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		JSONObject data = (JSONObject) JSON.parse(str);
		JSONObject data2 = (JSONObject) JSON.parse(data.getString("data"));
		//转换时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String createTime = sdf.format(Long.parseLong(data2.getString("createTime")));
        String taxpayerType = "";
        if("1".equals(data2.getString("taxpayerType"))){
        	taxpayerType = "一般纳税人";
        }else if("2".equals(data2.getString("taxpayerType"))){
        	taxpayerType = "小规模纳税人";
        }else if("3".equals(data2.getString("taxpayerType")))
        	taxpayerType = "非增值税纳税人";
        
        String legalRepresentative="";
        String phone="";
        String email = "";
        String idNumber="";
        String bankAccount = "";
        String businessAddress = "";
        String location = "";
        //安全处理信息
        if(data2.getString("legalRepresentative")!=null)
        	legalRepresentative = getStarStringNoEnd(data2.getString("legalRepresentative"),1);
        phone = getStarString(data2.getString("phone"),4,7);
        email = getStarString(data2.getString("email"), 2, data2.getString("email").lastIndexOf("@"));
        idNumber = getStarString(data2.getString("idNumber"), data2.getString("idNumber").length()-10, data2.getString("idNumber").length()-4);
        bankAccount = getStarString(data2.getString("bankAccount"), data2.getString("bankAccount").length()-7, data2.getString("bankAccount").length()-3);
        if(data2.getString("businessAddress")!=null){
        	if(data2.getString("businessAddress").length()<4)
        		businessAddress = getStarStringNoEnd(data2.getString("businessAddress"), data2.getString("businessAddress").length()-1);
        	else
        		businessAddress = getStarStringNoEnd(data2.getString("businessAddress"), data2.getString("businessAddress").length()-4);		
        }
        if(data2.getString("location")!=null){
        	if(data2.getString("location").length()<4)
        		location = getStarStringNoEnd(data2.getString("location"), data2.getString("location").length()-1);
        	else
        		location = getStarStringNoEnd(data2.getString("location"), data2.getString("location").length()-4);		
        }
		model.addObject("userId", userId);
		model.addObject("userName", username);
		model.addObject("shopName", data2.getString("name"));
		model.addObject("createTime",createTime);
		model.addObject("industryType", data2.getString("industryType"));
		model.addObject("officialWebsite", data2.getString("officialWebsite"));
		model.addObject("companiesNumber", data2.getString("companiesNumber"));
		model.addObject("companyNature", data2.getString("companyNature"));
		model.addObject("location", location);
		model.addObject("annualTurnover", data2.getString("annualTurnover"));
		model.addObject("areaCover", data2.getString("areaCover"));
		model.addObject("phone", phone);
		model.addObject("fax", data2.getString("fax"));
		model.addObject("email", email);
		model.addObject("businessAddress", businessAddress);
		model.addObject("businessLicenseRegistrationNumber", data2.getString("businessLicenseRegistrationNumber"));
		model.addObject("establishDate", data2.getString("establishDate"));
		model.addObject("businessScope", data2.getString("businessScope"));
		model.addObject("legalRepresentative", legalRepresentative);
		model.addObject("idNumber", idNumber);
		model.addObject("taxpayerNumber", data2.getString("taxpayerNumber"));
		model.addObject("taxpayerType", taxpayerType);
		model.addObject("taxCode", data2.getString("taxCode"));
		model.addObject("organizationCode", data2.getString("organizationCode"));
		model.addObject("bankName", data2.getString("bankName"));
		model.addObject("bankAccount", bankAccount);
		model.addObject("commodityType", data2.getString("commodityType"));
		model.addObject("brandNameCh", data2.getString("brandNameCh"));
		model.addObject("brandNameEn", data2.getString("brandNameEn"));
		model.addObject("registerCapital", data2.getString("registerCapital"));
		return model;
	}
	/**
	 * 店铺详情页面
	 * @return
	 */
	@RequestMapping("/toShopDetailPager")
	public ModelAndView toShopDetailPager(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/jsp/qualification/shop/checkedDetail");
		String url=request.getQueryString();
		String userId = url.substring(url.lastIndexOf("userId=")+7, url.lastIndexOf("username=")-1);
		String username = url.substring(url.lastIndexOf("username=")+9);
		//查询账户信息
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", PropertiesUtil.getStringByKey("appkey"));
		map.put("companyId", userId);
		//查询商户信息
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest queryShopInfoRequest = new QueryShopInfoRequest();
		queryShopInfoRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
		queryShopInfoRequest.setUserId(userId);
		QueryShopInfoResponse response=shopInfoSV.queryShopInfo(queryShopInfoRequest);
		String str ="";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("findByCompanyId_http_url"), JSON.toJSONString(map), mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		JSONObject data = (JSONObject) JSON.parse(str);
		JSONObject data2 = (JSONObject) JSON.parse(data.getString("data"));
		//转换时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String createTime = sdf.format(Long.parseLong(data2.getString("createTime")));
        String taxpayerType = "";
        if("1".equals(data2.getString("taxpayerType"))){
        	taxpayerType = "一般纳税人";
        }else if("2".equals(data2.getString("taxpayerType"))){
        	taxpayerType = "小规模纳税人";
        }else if("3".equals(data2.getString("taxpayerType")))
        	taxpayerType = "非增值税纳税人";
        
        String legalRepresentative="";
        String phone="";
        String email = "";
        String idNumber="";
        String bankAccount = "";
        String businessAddress = "";
        String location = "";
        //安全处理信息
        if(data2.getString("legalRepresentative")!=null)
        	legalRepresentative = getStarStringNoEnd(data2.getString("legalRepresentative"),1);
        phone = getStarString(data2.getString("phone"),4,7);
        email = getStarString(data2.getString("email"), 2, data2.getString("email").lastIndexOf("@"));
        idNumber = getStarString(data2.getString("idNumber"), data2.getString("idNumber").length()-10, data2.getString("idNumber").length()-4);
        bankAccount = getStarString(data2.getString("bankAccount"), data2.getString("bankAccount").length()-7, data2.getString("bankAccount").length()-3);
        if(data2.getString("businessAddress")!=null){
        	if(data2.getString("businessAddress").length()<4)
        		businessAddress = getStarStringNoEnd(data2.getString("businessAddress"), data2.getString("businessAddress").length()-1);
        	else
        		businessAddress = getStarStringNoEnd(data2.getString("businessAddress"), data2.getString("businessAddress").length()-4);		
        }
        if(data2.getString("location")!=null){
        	if(data2.getString("location").length()<4)
        		location = getStarStringNoEnd(data2.getString("location"), data2.getString("location").length()-1);
        	else
        		location = getStarStringNoEnd(data2.getString("location"), data2.getString("location").length()-4);		
        }
		model.addObject("userId", userId);
		model.addObject("userName", username);
		model.addObject("shopName", data2.getString("name"));
		model.addObject("createTime",createTime);
		model.addObject("industryType", data2.getString("industryType"));
		model.addObject("officialWebsite", data2.getString("officialWebsite"));
		model.addObject("companiesNumber", data2.getString("companiesNumber"));
		model.addObject("companyNature", data2.getString("companyNature"));
		model.addObject("location", location);
		model.addObject("annualTurnover", data2.getString("annualTurnover"));
		model.addObject("areaCover", data2.getString("areaCover"));
		model.addObject("phone", phone);
		model.addObject("fax", data2.getString("fax"));
		model.addObject("email", email);
		model.addObject("businessAddress", businessAddress);
		model.addObject("businessLicenseRegistrationNumber", data2.getString("businessLicenseRegistrationNumber"));
		model.addObject("establishDate", data2.getString("establishDate"));
		model.addObject("businessScope", data2.getString("businessScope"));
		model.addObject("legalRepresentative", legalRepresentative);
		model.addObject("idNumber", idNumber);
		model.addObject("taxpayerNumber", data2.getString("taxpayerNumber"));
		model.addObject("taxpayerType", taxpayerType);
		model.addObject("taxCode", data2.getString("taxCode"));
		model.addObject("organizationCode", data2.getString("organizationCode"));
		model.addObject("bankName", data2.getString("bankName"));
		model.addObject("bankAccount", bankAccount);
		model.addObject("commodityType", data2.getString("commodityType"));
		model.addObject("brandNameCh", data2.getString("brandNameCh"));
		model.addObject("brandNameEn", data2.getString("brandNameEn"));
		model.addObject("registerCapital", data2.getString("registerCapital"));
		if(response!=null){
			String busiType="";
			if("1".equals(response.getBusiType())){
				busiType="生产厂商";
			}else if("2".equals(response.getBusiType())){
				busiType="品牌代理商";
			}
			String hasExperi ="";
			if("0".equals(response.getHasExperi()+"")){
				hasExperi = "无";
			}else if("1".equals(response.getHasExperi()+""))
				hasExperi = "有";
			model.addObject("wantShopName", response.getShopName());
			model.addObject("goodsNum", response.getGoodsNum());
			model.addObject("busiType", busiType);
			model.addObject("hasExperi", hasExperi);
			model.addObject("shopDesc", response.getShopDesc());
			String ecommOwner = "";
			if(!response.getEcommOwner().isEmpty()){
				for (int index=0;index<response.getEcommOwner().length();index++) {
					if('1'==response.getEcommOwner().charAt(index))
						ecommOwner+=shopOwner[index]+"/";
				}
			ecommOwner = ecommOwner.substring(0,ecommOwner.length()-1);
			}
			model.addObject("ecommOwner", ecommOwner);
		}
		return model;
	}
	
	//查询未审核列表
	@RequestMapping("/getUncheckList")
	@ResponseBody
	public ResponseData<PageInfo<BusinessListInfo>> getUncheckList(HttpServletRequest request,String auditState,String companyName,String username,String companyType){
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
		if(auditState!=null&&auditState.length()!=0)
			map.put("auditState", auditState);
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
					BusinessListInfo businessInfo = new BusinessListInfo(); 
					 JSONObject object = (JSONObject) iterator.next();
					 businessInfo.setUserId(object.getString("companyId"));
					 businessInfo.setUserName(object.getString("username"));
					 businessInfo.setCustName(object.getString("name"));
					 responseList.add(businessInfo);
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
		
		//查询已审核列表
		@RequestMapping("/getCheckedList")
		@ResponseBody
		public ResponseData<PageInfo<BusinessListInfo>> getCheckedList(HttpServletRequest request,String companyName,String username,String auditState,String companyType){
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
			if(auditState!=null&&auditState.length()!=0)
				map.put("auditState", auditState);
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
						BusinessListInfo businessInfo = new BusinessListInfo(); 
						 JSONObject object = (JSONObject) iterator.next();
						 String date = "";
						 if(object.getString("createTime")!=null&&object.getString("createTime").length()!=0){
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								date = sdf.format(Long.parseLong(object.getString("createTime")));
							}
						 String auditTime = "";
						 if(object.getString("auditStateTime")!=null&&object.getString("auditStateTime").length()!=0){
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								auditTime = sdf.format(Long.parseLong(object.getString("auditStateTime")));
							}
						 businessInfo.setUserId(object.getString("companyId"));
						 businessInfo.setUserName(object.getString("username"));
						 businessInfo.setCustName(object.getString("name"));
						 businessInfo.setCreateTime(date);
						 businessInfo.setAuditTime(auditTime);
						 responseList.add(businessInfo);
					}
					pageInfo.setResult(responseList);
					//System.out.println(JSON.toJSONString(responseList));
					response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
					header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
				}
				response.setResponseHeader(header);
				response.setData(pageInfo);
			}
			return response;
	}
		
		/** 
	     * 对字符串处理:将指定位置到指定位置的字符以星号代替 
	     *  
	     * @param content 
	     *            传入的字符串 
	     * @param begin 
	     *            开始位置 
	     * @param end 
	     *            结束位置 
	     * @return 
	     */  
	    private static String getStarString(String content, int begin, int end) {  
	  
	        if (begin >= content.length() || begin < 0) {  
	            return content;  
	        }  
	        if (end >= content.length() || end < 0) {  
	            return content;  
	        }  
	        if (begin >= end) {  
	            return content;  
	        }  
	        String starStr = "";  
	        for (int i = begin; i < end; i++) {  
	            starStr = starStr + "*";  
	        }  
	        return content.substring(0, begin) + starStr + content.substring(end, content.length());  
	  
	    }  
	    /** 
	     * 对字符串处理:将指定位置到指定位置的字符以星号代替 
	     *  
	     * @param content 
	     *            传入的字符串 
	     * @param begin 
	     *            开始位置 
	     * @param end 
	     *            结束位置 
	     * @return 
	     */  
	    private static String getStarStringNoEnd(String content, int begin) {  
	  
	        if (begin >= content.length() || begin < 0) {  
	            return content;  
	        }  
	        if (begin >= content.length()) {  
	            return content;  
	        }  
	        String starStr = "";  
	        for (int i = begin; i < content.length(); i++) {  
	            starStr = starStr + "*";  
	        }  
	        return content.substring(0, begin) + starStr;  
	  
	    }  
		
}
