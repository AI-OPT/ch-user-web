package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.api.audit.interfaces.IAuditSV;
import com.ai.ch.user.api.audit.params.AuditLogVo;
import com.ai.ch.user.api.audit.params.InsertAuditInfoRequest;
import com.ai.ch.user.api.audit.params.QueryAuditInfoRequest;
import com.ai.ch.user.api.audit.params.QueryAuditInfoResponse;
import com.ai.ch.user.api.audit.params.QueryAuditLogInfoRequest;
import com.ai.ch.user.api.audit.params.QueryAuditLogInfoResponse;
import com.ai.ch.user.api.shopinfo.interfaces.IShopInfoSV;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoRequest;
import com.ai.ch.user.api.shopinfo.params.QueryShopInfoResponse;
import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.constants.ChWebConstants.OperateCode;
import com.ai.ch.user.web.model.sso.client.GeneralSSOClientUser;
import com.ai.ch.user.web.util.PropertiesUtil;
import com.ai.ch.user.web.vo.AuditInfoVo;
import com.ai.ch.user.web.vo.BusinessListInfo;
import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.util.ParseO2pDataUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 资质审核controller
 * @author Zh
 *
 */
@RestController
@RequestMapping("/qualification")
public class QualificationController {

	private static final Logger LOG = LoggerFactory.getLogger(QualificationController.class);

	
	static private String[] shopOwner = { "京东", "天猫", "淘宝", "苏宁", "一号店", "自有电商平台" };

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
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/toCheckedShopPager")
	public ModelAndView toCheckedShopPager(HttpServletRequest request) {
		return new ModelAndView("/jsp/qualification/shop/checkedPagerList");
	}

	/**
	 * 店铺审核日志
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/toShopAuditLogPager")
	public ModelAndView toShopAuditLogPager(HttpServletRequest request, String userId, String username, String custname)
			throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("/jsp/qualification/shop/auditlog");
		model.addObject("username", URLDecoder.decode(username, "utf-8"));
		model.addObject("custname", URLDecoder.decode(custname, "utf-8"));
		model.addObject("userId", userId);
		return model;
	}

	/**
	 * 供应商审核日志
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/toSuplierAuditLogPager")
	public ModelAndView toSuplierAuditLogPager(HttpServletRequest request, String userId, String username,
			String custname) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("/jsp/qualification/supplier/auditlog");
		model.addObject("username", URLDecoder.decode(username, "utf-8"));
		model.addObject("custname", URLDecoder.decode(custname, "utf-8"));
		model.addObject("userId", userId);
		return model;
	}

	/**
	 * 店铺未审核列表
	 * 
	 * @return
	 */
	@RequestMapping("/toNoCheckedShopPager")
	public ModelAndView toNoCheckedShopPager() {
		return new ModelAndView("/jsp/qualification/shop/noCheckedPagerList");
	}

	/**
	 * 供应商审核页面
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/toSuplierCheckPager", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView toSuplierCheckPager(String username, String userId) throws Exception {
		ModelAndView model = new ModelAndView("/jsp/qualification/supplier/auditeQualification");
		// 查询账户信息
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put(ChWebConstants.Audit.APPKEY, PropertiesUtil.getStringByKey(ChWebConstants.Audit.APPKEY));
		map.put(ChWebConstants.Audit.COMPANY_ID, userId);
		String str = "";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey(ChWebConstants.Audit.FINDBYCOMPANYID), JSON.toJSONString(map),
					mapHeader);
		} catch (IOException | URISyntaxException e) {
			LOG.error("查询失败,失败信息" + JSON.toJSONString(e));
		}
		JSONObject data0 = (JSONObject) JSON.parse(str);
		JSONObject data1 = (JSONObject) JSON.parse(data0.getString("data"));
		JSONObject data2 = (JSONObject) JSON.parse(data1.getString("data"));

		// 转换时间
		String createTime = "";
		if (data2.getString(ChWebConstants.Audit.CREATETIME) != null && data2.getString(ChWebConstants.Audit.CREATETIME).length() != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			createTime = sdf.format(Long.parseLong(data2.getString(ChWebConstants.Audit.CREATETIME)));
		}
		String taxpayerType = "";
		if ("1".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.GENERAL;
		} else if ("2".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.TAXPAYER;
		} else if ("3".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.NONVALUE;
		}
		String legalRepresentative = "";
		String phone = "";
		String email = "";
		String idNumber = "";
		String bankAccount = "";
		String businessAddress = "";
		String location = "";
		// 安全处理信息
		if (data2.getString(ChWebConstants.Audit.LEGALREPRESENTATIVE) != null) {
			legalRepresentative = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LEGALREPRESENTATIVE), 1);
		}
		if (data2.getString(ChWebConstants.Audit.PHONE) != null) {
			phone = getStarString(data2.getString(ChWebConstants.Audit.PHONE), 4, 7);
		}
		if (data2.getString(ChWebConstants.Audit.EMAIL) != null) {
			email = getStarString(data2.getString(ChWebConstants.Audit.EMAIL), 2, data2.getString(ChWebConstants.Audit.EMAIL).lastIndexOf("@"));
		}
		if (data2.getString(ChWebConstants.Audit.IDNUMBER) != null) {
			idNumber = getStarString(data2.getString(ChWebConstants.Audit.IDNUMBER), data2.getString(ChWebConstants.Audit.IDNUMBER).length() - 10,
					data2.getString(ChWebConstants.Audit.IDNUMBER).length() - 4);
		}
		if (data2.getString(ChWebConstants.Audit.BANKACCOUNT) != null) {
			bankAccount = getStarString(data2.getString(ChWebConstants.Audit.BANKACCOUNT), data2.getString(ChWebConstants.Audit.BANKACCOUNT).length() - 7,
					data2.getString(ChWebConstants.Audit.BANKACCOUNT).length() - 3);
		}
		if (data2.getString(ChWebConstants.Audit.BUSINESSADDRESS) != null) {
			if (data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() < 4) {
				businessAddress = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.BUSINESSADDRESS),
						data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() - 1);
			} else {
				businessAddress = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.BUSINESSADDRESS),
						data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() - 4);
			}
		}
		if (data2.getString(ChWebConstants.Audit.LOCATION) != null) {
			if (data2.getString(ChWebConstants.Audit.LOCATION).length() < 4) {
				location = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LOCATION), data2.getString(ChWebConstants.Audit.LOCATION).length() - 1);
			} else {
				location = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LOCATION), data2.getString(ChWebConstants.Audit.LOCATION).length() - 4);
			}
		}
		// System.out.println(JSON.toJSONString(data2));
		model.addObject(ChWebConstants.Audit.USERID, userId);
		model.addObject(ChWebConstants.Audit.USERNAME, username);
		model.addObject(ChWebConstants.Audit.SHOPNAME, data2.getString("name"));
		model.addObject(ChWebConstants.Audit.CREATETIME, createTime);
		model.addObject(ChWebConstants.Audit.INDUSTRYTYPE, data2.getString(ChWebConstants.Audit.INDUSTRYTYPE));
		model.addObject(ChWebConstants.Audit.OFFICIALWEBSITE, data2.getString(ChWebConstants.Audit.OFFICIALWEBSITE));
		model.addObject(ChWebConstants.Audit.COMPANIESNUMBER, data2.getString(ChWebConstants.Audit.COMPANIESNUMBER));
		model.addObject(ChWebConstants.Audit.COMPANYNATURE, data2.getString(ChWebConstants.Audit.COMPANYNATURE));
		model.addObject(ChWebConstants.Audit.LOCATION, location);
		model.addObject(ChWebConstants.Audit.ANNUALTURNOVER, data2.getString(ChWebConstants.Audit.ANNUALTURNOVER));
		model.addObject(ChWebConstants.Audit.AREACOVER, data2.getString(ChWebConstants.Audit.AREACOVER));
		model.addObject(ChWebConstants.Audit.PHONE, phone);
		model.addObject(ChWebConstants.Audit.FAX, data2.getString(ChWebConstants.Audit.FAX));
		model.addObject(ChWebConstants.Audit.EMAIL, email);
		model.addObject(ChWebConstants.Audit.BUSINESSADDRESS, businessAddress);
		model.addObject(ChWebConstants.Audit.BUSINESSLICENSEREGISTRATIONNUMBER, data2.getString(ChWebConstants.Audit.BUSINESSLICENSEREGISTRATIONNUMBER));
		model.addObject(ChWebConstants.Audit.ESTABLISHDATE, data2.getString(ChWebConstants.Audit.ESTABLISHDATE));
		model.addObject(ChWebConstants.Audit.BUSINESSSCOPE, data2.getString(ChWebConstants.Audit.BUSINESSSCOPE));
		model.addObject(ChWebConstants.Audit.LEGALREPRESENTATIVE, legalRepresentative);
		model.addObject(ChWebConstants.Audit.IDNUMBER, idNumber);
		model.addObject(ChWebConstants.Audit.TAXPAYERNUMBER, data2.getString(ChWebConstants.Audit.TAXPAYERNUMBER));
		model.addObject(ChWebConstants.Audit.TAXPAYERTYPE, taxpayerType);
		model.addObject(ChWebConstants.Audit.TAXCODE, data2.getString(ChWebConstants.Audit.TAXCODE));
		model.addObject(ChWebConstants.Audit.ORGANIZATIONCODE, data2.getString(ChWebConstants.Audit.ORGANIZATIONCODE));
		model.addObject(ChWebConstants.Audit.BANKNAME, data2.getString(ChWebConstants.Audit.BANKNAME));
		model.addObject(ChWebConstants.Audit.BANKACCOUNT, bankAccount);
		model.addObject(ChWebConstants.Audit.COMMODITYTYPE, data2.getString(ChWebConstants.Audit.COMMODITYTYPE));
		model.addObject(ChWebConstants.Audit.BRANDNAMECH, data2.getString(ChWebConstants.Audit.BRANDNAMECH));
		model.addObject(ChWebConstants.Audit.BRANDNAMEEN, data2.getString(ChWebConstants.Audit.BRANDNAMEEN));
		model.addObject(ChWebConstants.Audit.REGISTERCAPITAL, data2.getString(ChWebConstants.Audit.REGISTERCAPITAL));
		return model;
	}

	/**
	 * 店铺审核页面
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/toShopCheckPager", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView toShopCheckDetailPager(String username, String userId) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("/jsp/qualification/shop/auditeQualification");
		// 查询账户信息
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put(ChWebConstants.Audit.APPKEY, PropertiesUtil.getStringByKey(ChWebConstants.Audit.APPKEY));
		map.put(ChWebConstants.Audit.COMPANY_ID, userId);
		String str = "";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey(ChWebConstants.Audit.FINDBYCOMPANYID), JSON.toJSONString(map),
					mapHeader);
		} catch (IOException | URISyntaxException e) {
			LOG.error("查询失败,失败信息" + JSON.toJSONString(e));
		}
		JSONObject data0 = (JSONObject) JSON.parse(str);
		JSONObject data1 = (JSONObject) JSON.parse(data0.getString("data"));
		JSONObject data2 = (JSONObject) JSON.parse(data1.getString("data"));
		// 转换时间
		String createTime = "";
		// 查询商户信息
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest queryShopInfoRequest = new QueryShopInfoRequest();
		queryShopInfoRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
		queryShopInfoRequest.setUserId(userId);
		QueryShopInfoResponse response = shopInfoSV.queryShopInfo(queryShopInfoRequest);
		if (data2.getString(ChWebConstants.Audit.CREATETIME) != null && data2.getString(ChWebConstants.Audit.CREATETIME).length() != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			createTime = sdf.format(Long.parseLong(data2.getString(ChWebConstants.Audit.CREATETIME)));
		}
		String taxpayerType = "";
		if ("1".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.GENERAL;
		} else if ("2".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.TAXPAYER;
		} else if ("3".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.NONVALUE;
		}

		String legalRepresentative = "";
		String phone = "";
		String email = "";
		String idNumber = "";
		String bankAccount = "";
		String businessAddress = "";
		String location = "";
		// 安全处理信息
		if (data2.getString(ChWebConstants.Audit.LEGALREPRESENTATIVE) != null) {
			legalRepresentative = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LEGALREPRESENTATIVE), 1);
		}
		if (data2.getString(ChWebConstants.Audit.PHONE) != null) {
			phone = getStarString(data2.getString(ChWebConstants.Audit.PHONE), 4, 7);
		}
		if (data2.getString(ChWebConstants.Audit.EMAIL) != null) {
			email = getStarString(data2.getString(ChWebConstants.Audit.EMAIL), 2, data2.getString(ChWebConstants.Audit.EMAIL).lastIndexOf("@"));
		}
		if (data2.getString(ChWebConstants.Audit.IDNUMBER) != null) {
			idNumber = getStarString(data2.getString(ChWebConstants.Audit.IDNUMBER), data2.getString(ChWebConstants.Audit.IDNUMBER).length() - 10,
					data2.getString(ChWebConstants.Audit.IDNUMBER).length() - 4);
		}
		if (data2.getString(ChWebConstants.Audit.BANKACCOUNT) != null) {
			bankAccount = getStarString(data2.getString(ChWebConstants.Audit.BANKACCOUNT), data2.getString(ChWebConstants.Audit.BANKACCOUNT).length() - 7,
					data2.getString(ChWebConstants.Audit.BANKACCOUNT).length() - 3);
		}
		if (data2.getString(ChWebConstants.Audit.BUSINESSADDRESS) != null) {
			if (data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() < 4) {
				businessAddress = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.BUSINESSADDRESS),
						data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() - 1);
			} else {
				businessAddress = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.BUSINESSADDRESS),
						data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() - 4);
			}
		}
		if (data2.getString(ChWebConstants.Audit.LOCATION) != null) {
			if (data2.getString(ChWebConstants.Audit.LOCATION).length() < 4) {
				location = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LOCATION), data2.getString(ChWebConstants.Audit.LOCATION).length() - 1);
			} else {
				location = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LOCATION), data2.getString(ChWebConstants.Audit.LOCATION).length() - 4);
			}
		}
		model.addObject(ChWebConstants.Audit.USERID, userId);
		model.addObject(ChWebConstants.Audit.USERNAME, username);
		model.addObject(ChWebConstants.Audit.SHOPNAME, data2.getString("name"));
		model.addObject(ChWebConstants.Audit.CREATETIME, createTime);
		model.addObject(ChWebConstants.Audit.INDUSTRYTYPE, data2.getString(ChWebConstants.Audit.INDUSTRYTYPE));
		model.addObject(ChWebConstants.Audit.OFFICIALWEBSITE, data2.getString(ChWebConstants.Audit.OFFICIALWEBSITE));
		model.addObject(ChWebConstants.Audit.COMPANIESNUMBER, data2.getString(ChWebConstants.Audit.COMPANIESNUMBER));
		model.addObject(ChWebConstants.Audit.COMPANYNATURE, data2.getString(ChWebConstants.Audit.COMPANYNATURE));
		model.addObject(ChWebConstants.Audit.LOCATION, location);
		model.addObject(ChWebConstants.Audit.ANNUALTURNOVER, data2.getString(ChWebConstants.Audit.ANNUALTURNOVER));
		model.addObject(ChWebConstants.Audit.AREACOVER, data2.getString(ChWebConstants.Audit.AREACOVER));
		model.addObject(ChWebConstants.Audit.PHONE, phone);
		model.addObject(ChWebConstants.Audit.FAX, data2.getString(ChWebConstants.Audit.FAX));
		model.addObject(ChWebConstants.Audit.EMAIL, email);
		model.addObject(ChWebConstants.Audit.BUSINESSADDRESS, businessAddress);
		model.addObject(ChWebConstants.Audit.BUSINESSLICENSEREGISTRATIONNUMBER, data2.getString(ChWebConstants.Audit.BUSINESSLICENSEREGISTRATIONNUMBER));
		model.addObject(ChWebConstants.Audit.ESTABLISHDATE, data2.getString(ChWebConstants.Audit.ESTABLISHDATE));
		model.addObject(ChWebConstants.Audit.BUSINESSSCOPE, data2.getString(ChWebConstants.Audit.BUSINESSSCOPE));
		model.addObject(ChWebConstants.Audit.LEGALREPRESENTATIVE, legalRepresentative);
		model.addObject(ChWebConstants.Audit.IDNUMBER, idNumber);
		model.addObject(ChWebConstants.Audit.TAXPAYERNUMBER, data2.getString(ChWebConstants.Audit.TAXPAYERNUMBER));
		model.addObject(ChWebConstants.Audit.TAXPAYERTYPE, taxpayerType);
		model.addObject(ChWebConstants.Audit.TAXCODE, data2.getString(ChWebConstants.Audit.TAXCODE));
		model.addObject(ChWebConstants.Audit.ORGANIZATIONCODE, data2.getString(ChWebConstants.Audit.ORGANIZATIONCODE));
		model.addObject(ChWebConstants.Audit.BANKNAME, data2.getString(ChWebConstants.Audit.BANKNAME));
		model.addObject(ChWebConstants.Audit.BANKACCOUNT, bankAccount);
		model.addObject(ChWebConstants.Audit.COMMODITYTYPE, data2.getString(ChWebConstants.Audit.COMMODITYTYPE));
		model.addObject(ChWebConstants.Audit.BRANDNAMECH, data2.getString(ChWebConstants.Audit.BRANDNAMECH));
		model.addObject(ChWebConstants.Audit.BRANDNAMEEN, data2.getString(ChWebConstants.Audit.BRANDNAMEEN));
		if (response != null) {
			String busiType = "";
			if ("1".equals(response.getBusiType())) {
				busiType = "生产厂商";
			} else if ("2".equals(response.getBusiType())) {
				busiType = "品牌代理商";
			}
			String hasExperi = "";
			if ("0".equals(response.getHasExperi() + "")) {
				hasExperi = "无";
			} else if ("1".equals(response.getHasExperi() + "")) {
				hasExperi = "有";
			}

			model.addObject("wantShopName", response.getShopName());
			model.addObject("goodsNum", response.getGoodsNum());
			model.addObject("busiType", busiType);
			model.addObject("hasExperi", hasExperi);
			model.addObject("shopDesc", response.getShopDesc());
			StringBuffer ecomm = new StringBuffer();
			String ecommOwner = "";
			if (response.getEcommOwner() != null) {
				for (int index = 0; index < response.getEcommOwner().length(); index++) {
					if ('1' == response.getEcommOwner().charAt(index)) {
						ecomm.append(shopOwner[index]);
						ecomm.append("/");
					}
				}
				if (ecommOwner.length() > 1) {
					ecommOwner = ecomm.substring(0, ecommOwner.length() - 1);
				} else {
					ecommOwner = "无平台";
				}
			}
			model.addObject("ecommOwner", ecommOwner);
		}
		return model;
	}

	/**
	 * 供应商详情页面
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/toSuplierDetailPager", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView toSuplierDetailPager(String userId, String username) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("/jsp/qualification/supplier/checkedDetail");
		// 查询账户信息
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put(ChWebConstants.Audit.APPKEY, PropertiesUtil.getStringByKey(ChWebConstants.Audit.APPKEY));
		map.put(ChWebConstants.Audit.COMPANY_ID, userId);
		String str = "";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey(ChWebConstants.Audit.FINDBYCOMPANYID), JSON.toJSONString(map),
					mapHeader);
		} catch (IOException | URISyntaxException e) {
			LOG.error("查询失败,失败信息" + JSON.toJSONString(e));
		}
		JSONObject data0 = (JSONObject) JSON.parse(str);
		JSONObject data1 = (JSONObject) JSON.parse(data0.getString("data"));
		JSONObject data2 = (JSONObject) JSON.parse(data1.getString("data"));
		// 转换时间
		String createTime = "";
		if (data2.getString(ChWebConstants.Audit.CREATETIME) != null && data2.getString(ChWebConstants.Audit.CREATETIME).length() != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			createTime = sdf.format(Long.parseLong(data2.getString(ChWebConstants.Audit.CREATETIME)));
		}
		String taxpayerType = "";
		if ("1".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.GENERAL;
		} else if ("2".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.TAXPAYER;
		} else if ("3".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.NONVALUE;
		}

		String legalRepresentative = "";
		String phone = "";
		String email = "";
		String idNumber = "";
		String bankAccount = "";
		String businessAddress = "";
		String location = "";
		// 安全处理信息
		if (data2.getString(ChWebConstants.Audit.LEGALREPRESENTATIVE) != null) {
			legalRepresentative = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LEGALREPRESENTATIVE), 1);
		}
		if (data2.getString(ChWebConstants.Audit.PHONE) != null) {
			phone = getStarString(data2.getString(ChWebConstants.Audit.PHONE), 4, 7);
		}
		if (data2.getString(ChWebConstants.Audit.EMAIL) != null) {
			email = getStarString(data2.getString(ChWebConstants.Audit.EMAIL), 2, data2.getString(ChWebConstants.Audit.EMAIL).lastIndexOf("@"));
		}
		if (data2.getString(ChWebConstants.Audit.IDNUMBER) != null) {
			idNumber = getStarString(data2.getString(ChWebConstants.Audit.IDNUMBER), data2.getString(ChWebConstants.Audit.IDNUMBER).length() - 10,
					data2.getString(ChWebConstants.Audit.IDNUMBER).length() - 4);
		}
		if (data2.getString(ChWebConstants.Audit.BANKACCOUNT) != null) {
			bankAccount = getStarString(data2.getString(ChWebConstants.Audit.BANKACCOUNT), data2.getString(ChWebConstants.Audit.BANKACCOUNT).length() - 7,
					data2.getString(ChWebConstants.Audit.BANKACCOUNT).length() - 3);
		}
		if (data2.getString(ChWebConstants.Audit.BUSINESSADDRESS) != null) {
			if (data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() < 4) {
				businessAddress = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.BUSINESSADDRESS),
						data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() - 1);
			} else {
				businessAddress = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.BUSINESSADDRESS),
						data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() - 4);
			}
		}
		if (data2.getString(ChWebConstants.Audit.LOCATION) != null) {
			if (data2.getString(ChWebConstants.Audit.LOCATION).length() < 4) {
				location = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LOCATION), data2.getString(ChWebConstants.Audit.LOCATION).length() - 1);
			} else {
				location = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LOCATION), data2.getString(ChWebConstants.Audit.LOCATION).length() - 4);
			}
		}
		model.addObject(ChWebConstants.Audit.USERID, userId);
		model.addObject(ChWebConstants.Audit.USERNAME, username);
		model.addObject(ChWebConstants.Audit.SHOPNAME, data2.getString("name"));
		model.addObject(ChWebConstants.Audit.CREATETIME, createTime);
		model.addObject(ChWebConstants.Audit.INDUSTRYTYPE, data2.getString(ChWebConstants.Audit.INDUSTRYTYPE));
		model.addObject(ChWebConstants.Audit.OFFICIALWEBSITE, data2.getString(ChWebConstants.Audit.OFFICIALWEBSITE));
		model.addObject(ChWebConstants.Audit.COMPANIESNUMBER, data2.getString(ChWebConstants.Audit.COMPANIESNUMBER));
		model.addObject(ChWebConstants.Audit.COMPANYNATURE, data2.getString(ChWebConstants.Audit.COMPANYNATURE));
		model.addObject(ChWebConstants.Audit.LOCATION, location);
		model.addObject(ChWebConstants.Audit.ANNUALTURNOVER, data2.getString(ChWebConstants.Audit.ANNUALTURNOVER));
		model.addObject(ChWebConstants.Audit.AREACOVER, data2.getString(ChWebConstants.Audit.AREACOVER));
		model.addObject(ChWebConstants.Audit.PHONE, phone);
		model.addObject(ChWebConstants.Audit.FAX, data2.getString(ChWebConstants.Audit.FAX));
		model.addObject(ChWebConstants.Audit.EMAIL, email);
		model.addObject(ChWebConstants.Audit.BUSINESSADDRESS, businessAddress);
		model.addObject(ChWebConstants.Audit.BUSINESSLICENSEREGISTRATIONNUMBER, data2.getString(ChWebConstants.Audit.BUSINESSLICENSEREGISTRATIONNUMBER));
		model.addObject(ChWebConstants.Audit.ESTABLISHDATE, data2.getString(ChWebConstants.Audit.ESTABLISHDATE));
		model.addObject(ChWebConstants.Audit.BUSINESSSCOPE, data2.getString(ChWebConstants.Audit.BUSINESSSCOPE));
		model.addObject(ChWebConstants.Audit.LEGALREPRESENTATIVE, legalRepresentative);
		model.addObject(ChWebConstants.Audit.IDNUMBER, idNumber);
		model.addObject(ChWebConstants.Audit.TAXPAYERNUMBER, data2.getString(ChWebConstants.Audit.TAXPAYERNUMBER));
		model.addObject(ChWebConstants.Audit.TAXPAYERTYPE, taxpayerType);
		model.addObject(ChWebConstants.Audit.TAXCODE, data2.getString(ChWebConstants.Audit.TAXCODE));
		model.addObject(ChWebConstants.Audit.ORGANIZATIONCODE, data2.getString(ChWebConstants.Audit.ORGANIZATIONCODE));
		model.addObject(ChWebConstants.Audit.BANKNAME, data2.getString(ChWebConstants.Audit.BANKNAME));
		model.addObject(ChWebConstants.Audit.BANKACCOUNT, bankAccount);
		model.addObject(ChWebConstants.Audit.COMMODITYTYPE, data2.getString(ChWebConstants.Audit.COMMODITYTYPE));
		model.addObject(ChWebConstants.Audit.BRANDNAMECH, data2.getString(ChWebConstants.Audit.BRANDNAMECH));
		model.addObject(ChWebConstants.Audit.BRANDNAMEEN, data2.getString(ChWebConstants.Audit.BRANDNAMEEN));
		model.addObject(ChWebConstants.Audit.REGISTERCAPITAL, data2.getString(ChWebConstants.Audit.REGISTERCAPITAL));
		
		IAuditSV auditSV = DubboConsumerFactory.getService("iAuditSV");
		QueryAuditInfoRequest queryAuditInfoRequest = new QueryAuditInfoRequest();
		queryAuditInfoRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
		queryAuditInfoRequest.setUserId(userId);
		QueryAuditInfoResponse queryAuditInfoResponse = auditSV.queryAuditInfo(queryAuditInfoRequest);
		if(!StringUtil.isBlank(queryAuditInfoResponse.getAuditStatus())){
			if("2".equals(queryAuditInfoResponse.getAuditStatus())){
				queryAuditInfoResponse.setAuditStatus("审核已通过");
			}else if("3".equals(queryAuditInfoResponse.getAuditStatus())){
				queryAuditInfoResponse.setAuditStatus("审核已拒绝");
			}else{
				queryAuditInfoResponse.setAuditStatus("");
			}
			int remarkLength = 0;
			if (queryAuditInfoResponse.getAuditDesc() != null) {
				remarkLength = queryAuditInfoResponse.getAuditDesc().length();
				if (remarkLength > 60) {
					model.addObject("remarkLength", true);
				} else {
					model.addObject("remarkLength", false);
				}
			}
		}
		model.addObject("auditResponse", queryAuditInfoResponse);
		return model;
	}

	/**
	 * 店铺详情页面
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/toShopDetailPager", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView toShopDetailPager(String userId, String username) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("/jsp/qualification/shop/checkedDetail");
		// 查询账户信息
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put(ChWebConstants.Audit.APPKEY, PropertiesUtil.getStringByKey(ChWebConstants.Audit.APPKEY));
		map.put(ChWebConstants.Audit.COMPANY_ID, userId);
		// 查询商户信息
		IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
		QueryShopInfoRequest queryShopInfoRequest = new QueryShopInfoRequest();
		queryShopInfoRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
		queryShopInfoRequest.setUserId(userId);
		QueryShopInfoResponse response = shopInfoSV.queryShopInfo(queryShopInfoRequest);
		String str = "";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey(ChWebConstants.Audit.FINDBYCOMPANYID), JSON.toJSONString(map),
					mapHeader);
		} catch (IOException | URISyntaxException e) {
			LOG.error("查询失败,失败信息" + JSON.toJSONString(e));
		}
		JSONObject data0 = (JSONObject) JSON.parse(str);
		JSONObject data1 = (JSONObject) JSON.parse(data0.getString("data"));
		JSONObject data2 = (JSONObject) JSON.parse(data1.getString("data"));
		// 转换时间
		String createTime = "";
		if (data2.getString(ChWebConstants.Audit.CREATETIME) != null && data2.getString(ChWebConstants.Audit.CREATETIME).length() != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			createTime = sdf.format(Long.parseLong(data2.getString(ChWebConstants.Audit.CREATETIME)));
		}
		String taxpayerType = "";
		if ("1".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.GENERAL;
		} else if ("2".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.TAXPAYER;
		} else if ("3".equals(data2.getString(ChWebConstants.Audit.TAXPAYERTYPE))) {
			taxpayerType = ChWebConstants.Audit.NONVALUE;
		}

		String legalRepresentative = "";
		String phone = "";
		String email = "";
		String idNumber = "";
		String bankAccount = "";
		String businessAddress = "";
		String location = "";
		// 安全处理信息
		if (data2.getString(ChWebConstants.Audit.LEGALREPRESENTATIVE) != null) {
			legalRepresentative = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LEGALREPRESENTATIVE), 1);
		}
		if (data2.getString(ChWebConstants.Audit.PHONE) != null) {
			phone = getStarString(data2.getString(ChWebConstants.Audit.PHONE), 4, 7);
		}
		if (data2.getString(ChWebConstants.Audit.EMAIL) != null) {
			email = getStarString(data2.getString(ChWebConstants.Audit.EMAIL), 2, data2.getString(ChWebConstants.Audit.EMAIL).lastIndexOf("@"));
		}
		if (data2.getString(ChWebConstants.Audit.IDNUMBER) != null) {
			idNumber = getStarString(data2.getString(ChWebConstants.Audit.IDNUMBER), data2.getString(ChWebConstants.Audit.IDNUMBER).length() - 10,
					data2.getString(ChWebConstants.Audit.IDNUMBER).length() - 4);
		}
		if (data2.getString(ChWebConstants.Audit.BANKACCOUNT) != null) {
			bankAccount = getStarString(data2.getString(ChWebConstants.Audit.BANKACCOUNT), data2.getString(ChWebConstants.Audit.BANKACCOUNT).length() - 7,
					data2.getString(ChWebConstants.Audit.BANKACCOUNT).length() - 3);
		}
		if (data2.getString(ChWebConstants.Audit.BUSINESSADDRESS) != null) {
			if (data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() < 4) {
				businessAddress = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.BUSINESSADDRESS),
						data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() - 1);
			} else {
				businessAddress = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.BUSINESSADDRESS),
						data2.getString(ChWebConstants.Audit.BUSINESSADDRESS).length() - 4);
			}
		}
		if (data2.getString(ChWebConstants.Audit.LOCATION) != null) {
			if (data2.getString(ChWebConstants.Audit.LOCATION).length() < 4) {
				location = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LOCATION), data2.getString(ChWebConstants.Audit.LOCATION).length() - 1);
			} else {
				location = getStarStringNoEnd(data2.getString(ChWebConstants.Audit.LOCATION), data2.getString(ChWebConstants.Audit.LOCATION).length() - 4);
			}
		}
		model.addObject(ChWebConstants.Audit.USERID, userId);
		model.addObject(ChWebConstants.Audit.USERNAME, username);
		model.addObject(ChWebConstants.Audit.SHOPNAME, data2.getString("name"));
		model.addObject(ChWebConstants.Audit.CREATETIME, createTime);
		model.addObject(ChWebConstants.Audit.INDUSTRYTYPE, data2.getString(ChWebConstants.Audit.INDUSTRYTYPE));
		model.addObject(ChWebConstants.Audit.OFFICIALWEBSITE, data2.getString(ChWebConstants.Audit.OFFICIALWEBSITE));
		model.addObject(ChWebConstants.Audit.COMPANIESNUMBER, data2.getString(ChWebConstants.Audit.COMPANIESNUMBER));
		model.addObject(ChWebConstants.Audit.COMPANYNATURE, data2.getString(ChWebConstants.Audit.COMPANYNATURE));
		model.addObject(ChWebConstants.Audit.LOCATION, location);
		model.addObject(ChWebConstants.Audit.ANNUALTURNOVER, data2.getString(ChWebConstants.Audit.ANNUALTURNOVER));
		model.addObject(ChWebConstants.Audit.AREACOVER, data2.getString(ChWebConstants.Audit.AREACOVER));
		model.addObject(ChWebConstants.Audit.PHONE, phone);
		model.addObject(ChWebConstants.Audit.FAX, data2.getString(ChWebConstants.Audit.FAX));
		model.addObject(ChWebConstants.Audit.EMAIL, email);
		model.addObject(ChWebConstants.Audit.BUSINESSADDRESS, businessAddress);
		model.addObject(ChWebConstants.Audit.BUSINESSLICENSEREGISTRATIONNUMBER, data2.getString(ChWebConstants.Audit.BUSINESSLICENSEREGISTRATIONNUMBER));
		model.addObject(ChWebConstants.Audit.ESTABLISHDATE, data2.getString(ChWebConstants.Audit.ESTABLISHDATE));
		model.addObject(ChWebConstants.Audit.BUSINESSSCOPE, data2.getString(ChWebConstants.Audit.BUSINESSSCOPE));
		model.addObject(ChWebConstants.Audit.LEGALREPRESENTATIVE, legalRepresentative);
		model.addObject(ChWebConstants.Audit.IDNUMBER, idNumber);
		model.addObject(ChWebConstants.Audit.TAXPAYERNUMBER, data2.getString(ChWebConstants.Audit.TAXPAYERNUMBER));
		model.addObject(ChWebConstants.Audit.TAXPAYERTYPE, taxpayerType);
		model.addObject(ChWebConstants.Audit.TAXCODE, data2.getString(ChWebConstants.Audit.TAXCODE));
		model.addObject(ChWebConstants.Audit.ORGANIZATIONCODE, data2.getString(ChWebConstants.Audit.ORGANIZATIONCODE));
		model.addObject(ChWebConstants.Audit.BANKNAME, data2.getString(ChWebConstants.Audit.BANKNAME));
		model.addObject(ChWebConstants.Audit.BANKACCOUNT, bankAccount);
		model.addObject(ChWebConstants.Audit.COMMODITYTYPE, data2.getString(ChWebConstants.Audit.COMMODITYTYPE));
		model.addObject(ChWebConstants.Audit.BRANDNAMECH, data2.getString(ChWebConstants.Audit.BRANDNAMECH));
		model.addObject(ChWebConstants.Audit.BRANDNAMEEN, data2.getString(ChWebConstants.Audit.BRANDNAMEEN));
		model.addObject(ChWebConstants.Audit.REGISTERCAPITAL, data2.getString(ChWebConstants.Audit.REGISTERCAPITAL));
		if (response != null) {
			String busiType = "";
			if ("1".equals(response.getBusiType())) {
				busiType = "生产厂商";
			} else if ("2".equals(response.getBusiType())) {
				busiType = "品牌代理商";
			}
			String hasExperi = "";
			if ("0".equals(response.getHasExperi() + "")) {
				hasExperi = "无";
			} else if ("1".equals(response.getHasExperi() + "")) {
				hasExperi = "有";
			}
			model.addObject("wantShopName", response.getShopName());
			model.addObject("goodsNum", response.getGoodsNum());
			model.addObject("busiType", busiType);
			model.addObject("hasExperi", hasExperi);
			model.addObject("shopDesc", response.getShopDesc());
			int shopDescLength = 0;
			if (response.getShopDesc() != null) {
				shopDescLength = response.getShopDesc().length();
				if (shopDescLength > 60) {
					model.addObject("shopDescLength", true);
				} else {
					model.addObject("shopDescLength", false);
				}
			}
			StringBuffer ecomm = new StringBuffer();
			String ecommOwner = "";
			if (response.getEcommOwner() != null) {
				for (int index = 0; index < response.getEcommOwner().length(); index++) {
					if ('1' == response.getEcommOwner().charAt(index)) {
						ecomm.append(shopOwner[index]);
						ecomm.append("/");
					}
				}
				if (ecomm.length() > 1) {
					ecommOwner = ecomm.substring(0, ecomm.length() - 1);
				} else {
					ecommOwner = "无平台";
				}
			}
			model.addObject("ecommOwner", ecommOwner);
			IAuditSV auditSV = DubboConsumerFactory.getService("iAuditSV");
			QueryAuditInfoRequest queryAuditInfoRequest = new QueryAuditInfoRequest();
			queryAuditInfoRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
			queryAuditInfoRequest.setUserId(userId);
			QueryAuditInfoResponse queryAuditInfoResponse = auditSV.queryAuditInfo(queryAuditInfoRequest);
			if(!StringUtil.isBlank(queryAuditInfoResponse.getAuditStatus())){
				if("1".equals(queryAuditInfoResponse.getAuditStatus())){
					queryAuditInfoResponse.setAuditStatus("审核已通过");
				}else if("2".equals(queryAuditInfoResponse.getAuditStatus())){
					queryAuditInfoResponse.setAuditStatus("审核已拒绝");
				}else{
					queryAuditInfoResponse.setAuditStatus("");
				}
			}
			int remarkLength = 0;
			if (queryAuditInfoResponse.getAuditDesc() != null) {
				remarkLength = queryAuditInfoResponse.getAuditDesc().length();
				if (remarkLength > 60) {
					model.addObject("remarkLength", true);
				} else {
					model.addObject("remarkLength", false);
				}
			}
			model.addObject("auditResponse", queryAuditInfoResponse);
		}
		return model;
	}

	/**
	 * 查询未审核列表
	 * @param request
	 * @param auditState
	 * @param companyName
	 * @param username
	 * @param companyType
	 * @return
	 */
	@RequestMapping("/getUncheckList")
	@ResponseBody
	public ResponseData<PageInfo<BusinessListInfo>> getUncheckList(HttpServletRequest request, String auditState,
			String companyName, String username, String companyType) {
		ResponseData<PageInfo<BusinessListInfo>> response = null;
		PageInfo<BusinessListInfo> pageInfo = null;
		ResponseHeader header = null;
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put(ChWebConstants.Audit.APPKEY, PropertiesUtil.getStringByKey(ChWebConstants.Audit.APPKEY));
		map.put(ChWebConstants.Audit.PAGENO, request.getParameter(ChWebConstants.Audit.PAGENO));
		map.put(ChWebConstants.Audit.PAGESIZE, request.getParameter(ChWebConstants.Audit.PAGESIZE));
		if (username != null && username.length() != 0) {
			map.put("username", username);
		}
		if (companyName != null && companyName.length() != 0) {
			map.put("companyName", companyName);
		}
		if (companyType != null && companyType.length() != 0) {
			map.put("companyType", companyType);
		}
		if (auditState != null && auditState.length() != 0) {
			map.put("auditState", auditState);
		}
		String str = "";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("searchCompanyList_http_url"),
					JSON.toJSONString(map), mapHeader);
		} catch (IOException | URISyntaxException e) {
			LOG.error("查询失败,失败信息" + JSON.toJSONString(e));
		}
		try {
			JSONObject data = ParseO2pDataUtil.getData(str);
			String resultCode = data.getString("resultCode");
			if (resultCode != null && !OperateCode.SUCCESS.equals(resultCode)) {
				LOG.error("调用失败,resultCode" + resultCode);
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败");
			} else {
				Integer pageNo = Integer.valueOf(data.getString("pages"));
				Integer pageSize = Integer.valueOf(data.getString(ChWebConstants.Audit.PAGESIZE));
				Integer total = Integer.valueOf(data.getString("total"));
				Integer pageCount = Integer.valueOf(data.getString("pageNum"));
				pageInfo = new PageInfo<>();
				pageInfo.setCount(total);
				pageInfo.setPageCount(pageCount);
				pageInfo.setPageNo(pageNo);
				pageInfo.setPageSize(pageSize);
				List<BusinessListInfo> responseList = new ArrayList<>();
				JSONArray list = (JSONArray) JSON.parseArray(data.getString("list"));
				Iterator<Object> iterator = list.iterator();
				while (iterator.hasNext()) {
					BusinessListInfo businessInfo = new BusinessListInfo();
					JSONObject object = (JSONObject) iterator.next();
					businessInfo.setUserId(object.getString(ChWebConstants.Audit.COMPANY_ID));
					businessInfo.setUserName(object.getString("username"));
					businessInfo.setCustName(object.getString("name"));
					responseList.add(businessInfo);
				}
				pageInfo.setResult(responseList);
				response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
			}
		} catch (BusinessException e) {
			LOG.error("操作失败,原因:" + JSON.toJSONString(e));
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "查询失败");
			header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "查询失败");
			response.setResponseHeader(header);
		}
		response.setResponseHeader(header);
		response.setData(pageInfo);
		return response;
	}

	/**
	 * 查询审核日志
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getAuditLog")
	@ResponseBody
	public ResponseData<PageInfo<AuditInfoVo>> getAuditLog(HttpServletRequest request, String userId) {
		ResponseData<PageInfo<AuditInfoVo>> response = null;
		PageInfo<AuditInfoVo> pageInfo = new PageInfo<>();
		ResponseHeader header = null;
		IAuditSV auditSV = DubboConsumerFactory.getService("iAuditSV");
		QueryAuditLogInfoRequest queryAuditLogInfoRequest = new QueryAuditLogInfoRequest();
		QueryAuditLogInfoResponse queryAuditLogInfoResponse = null;
		try {
			queryAuditLogInfoRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
			queryAuditLogInfoRequest.setUserId(userId);
			queryAuditLogInfoRequest.setPageNo(Integer.valueOf(request.getParameter("pageNo")));
			queryAuditLogInfoRequest.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
			queryAuditLogInfoResponse = auditSV.queryAuditLogInfo(queryAuditLogInfoRequest);
			response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
			header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
		} catch (Exception e) {
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "操作失败");
			header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败");
			LOG.error("操作失败,原因" + JSON.toJSONString(e));
		}
		List<AuditInfoVo> result = new ArrayList<>();
			PageInfo<AuditLogVo> pageInfoVo = queryAuditLogInfoResponse.getPageInfo();
			if (pageInfoVo != null) {
				for (AuditLogVo auditLogVo : pageInfoVo.getResult()) {
					AuditInfoVo auditInfoVo = new AuditInfoVo();
					BeanUtils.copyProperties(auditLogVo, auditInfoVo);
					if ("1".equals(auditLogVo.getAuditStatus())) {
						auditInfoVo.setAuditStatus("审核已通过");
					} else if ("2".equals(auditLogVo.getAuditStatus())) {
						auditInfoVo.setAuditStatus("审核已拒绝");
					} else {
						auditInfoVo.setAuditStatus("");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if (auditLogVo.getAuditTime() != null) {
						auditInfoVo.setAuditTime(sdf.format(auditLogVo.getAuditTime()));
					}
					result.add(auditInfoVo);
				}
				BeanUtils.copyProperties(pageInfoVo, pageInfo);
				pageInfo.setResult(result);
		}
		response.setResponseHeader(header);
		response.setData(pageInfo);
		return response;
	}

	/**
	 * 查询已审核列表
	 * @param request
	 * @param companyName
	 * @param username
	 * @param auditState
	 * @param companyType
	 * @return
	 */
	@RequestMapping("/getCheckedList")
	@ResponseBody
	public ResponseData<PageInfo<BusinessListInfo>> getCheckedList(HttpServletRequest request, String companyName,
			String username, String auditState, String companyType) {
		ResponseData<PageInfo<BusinessListInfo>> response = null;
		PageInfo<BusinessListInfo> pageInfo = null;
		ResponseHeader header = null;
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put(ChWebConstants.Audit.APPKEY, PropertiesUtil.getStringByKey(ChWebConstants.Audit.APPKEY));
		map.put(ChWebConstants.Audit.PAGENO, request.getParameter(ChWebConstants.Audit.PAGENO));
		map.put(ChWebConstants.Audit.PAGESIZE, request.getParameter(ChWebConstants.Audit.PAGESIZE));
		if (username != null && username.length() != 0) {
			map.put("username", username);
		}
		if (companyName != null && companyName.length() != 0) {
			map.put("companyName", companyName);
		}
		if (companyType != null && companyType.length() != 0) {
			map.put("companyType", companyType);
		}
		if (auditState != null && auditState.length() != 0) {
			map.put("auditState", auditState);
		}
		String str = "";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("searchCompanyList_http_url"),
					JSON.toJSONString(map), mapHeader);
		} catch (IOException | URISyntaxException e) {
			LOG.error("查询失败,失败信息" + JSON.toJSONString(e));
		}
		try {
			JSONObject data = ParseO2pDataUtil.getData(str);
			String resultCode = data.getString("resultCode");
			if (resultCode != null && !OperateCode.SUCCESS.equals(resultCode)) {
				LOG.error("调用失败,resultCode" + resultCode);
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败");
			} else {
				Integer pageNo = Integer.valueOf(data.getString("pages"));
				Integer pageSize = Integer.valueOf(data.getString(ChWebConstants.Audit.PAGESIZE));
				Integer total = Integer.valueOf(data.getString("total"));
				Integer pageCount = Integer.valueOf(data.getString("pageNum"));
				pageInfo = new PageInfo<>();
				pageInfo.setCount(total);
				pageInfo.setPageCount(pageCount);
				pageInfo.setPageNo(pageNo);
				pageInfo.setPageSize(pageSize);
				List<BusinessListInfo> responseList = new ArrayList<>();
				JSONArray list = (JSONArray) JSON.parseArray(data.getString("list"));
				Iterator<Object> iterator = list.iterator();
				while (iterator.hasNext()) {
					BusinessListInfo businessInfo = new BusinessListInfo();
					JSONObject object = (JSONObject) iterator.next();
					String date = "";
					if (object.getString(ChWebConstants.Audit.CREATETIME) != null && object.getString(ChWebConstants.Audit.CREATETIME).length() != 0) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						date = sdf.format(Long.parseLong(object.getString(ChWebConstants.Audit.CREATETIME)));
					}
					String auditTime = "";
					if (object.getString("auditStateTime") != null
							&& object.getString("auditStateTime").length() != 0) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						auditTime = sdf.format(Long.parseLong(object.getString("auditStateTime")));
					}
					businessInfo.setUserId(object.getString(ChWebConstants.Audit.COMPANY_ID));
					businessInfo.setUserName(object.getString("username"));
					businessInfo.setCustName(object.getString("name"));
					businessInfo.setCreateTime(date);
					businessInfo.setAuditTime(auditTime);
					responseList.add(businessInfo);
				}
				pageInfo.setResult(responseList);
				// System.out.println(JSON.toJSONString(responseList));
				response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
			}
		} catch (BusinessException e) {
			LOG.error("操作失败,原因:" + JSON.toJSONString(e));
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "查询失败");
			header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "查询失败");
		}
		response.setResponseHeader(header);
		response.setData(pageInfo);
		return response;
	}

	/**
	 * 更新资质审核状态
	 * @param request
	 * @param companyId
	 * @param auditState
	 * @param reason
	 * @param ctType
	 * @return
	 */
	@RequestMapping("/updateAudit")
	@ResponseBody
	public ResponseData<String> updateAudit(HttpServletRequest request, String companyId, String auditState,
			String reason, String ctType,String username,String companyname) {
		ResponseData<String> response = null;
		ResponseHeader header = null;
		GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession()
				.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put(ChWebConstants.Audit.APPKEY, PropertiesUtil.getStringByKey(ChWebConstants.Audit.APPKEY));
		map.put("openId", user.getUserId());
		map.put("auditState", auditState);
		map.put(ChWebConstants.Audit.COMPANY_ID, companyId);
		String str = "";
		try {
			str = HttpClientUtil.sendPost(PropertiesUtil.getStringByKey("updateAuditState_http_url"),
					JSON.toJSONString(map), mapHeader);
		} catch (IOException | URISyntaxException e) {
			LOG.error("查询失败,失败信息" + JSON.toJSONString(e));
		}
		JSONObject data = ParseO2pDataUtil.getData(str);
		String resultCode = data.getString("resultCode");
		if (resultCode != null && !OperateCode.SUCCESS.equals(resultCode)) {
			LOG.error("调用失败,resultCode" + resultCode);
			response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "调用API失败");
			header = new ResponseHeader(true, ChWebConstants.OperateCode.Fail, "操作失败");
		} else {
			String result = data.getString("result");
			if ("success".equals(result)) {
				response = new ResponseData<>(ChWebConstants.OperateCode.SUCCESS, "操作成功");
				header = new ResponseHeader(true, ChWebConstants.OperateCode.SUCCESS, "操作成功");
				try{
				IAuditSV auditSV = DubboConsumerFactory.getService("iAuditSV");
				InsertAuditInfoRequest insertAuditInfoRequest = new InsertAuditInfoRequest();
				insertAuditInfoRequest.setTenantId(ChWebConstants.COM_TENANT_ID);
				insertAuditInfoRequest.setAuditDesc(reason);
				insertAuditInfoRequest.setAuditStatus(auditState);
				insertAuditInfoRequest.setOperId(user.getUserId());
				insertAuditInfoRequest.setOperName(user.getUsername());
				insertAuditInfoRequest.setCtType(ctType);
				insertAuditInfoRequest.setUserName(username);
				insertAuditInfoRequest.setCompanyName(companyname);
				insertAuditInfoRequest.setUserId(companyId);
				LOG.error("保存资质审核信息:"+JSON.toJSONString(insertAuditInfoRequest));
				auditSV.insertAuditInfo(insertAuditInfoRequest);
				}catch(Exception e){
					LOG.error("保存资质信息失败,原因"+JSON.toJSONString(e));
				}
			} else {
				response = new ResponseData<>(ChWebConstants.OperateCode.Fail, "操作失败");
				header = new ResponseHeader(false, ChWebConstants.OperateCode.Fail, "操作失败");
			}
		}
		response.setResponseHeader(header);
		LOG.error("返回报文体:"+JSON.toJSONString(response));
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
		String str = "";
		if (content != null) {
			if (begin >= content.length() || begin < 0) {
				return content;
			}
			if (end >= content.length() || end < 0) {
				return content;
			}
			if (begin >= end) {
				return content;
			}
			StringBuffer starStr = new StringBuffer();
			for (int i = begin; i < end; i++) {
				starStr.append("*");
			}
			str = content.substring(0, begin) + starStr + content.substring(end, content.length());
		}
		return str;

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

		String str = "";
		if (content != null) {
			if (begin >= content.length() || begin < 0) {
				return content;
			}
			if (begin >= content.length()) {
				return content;
			}
			StringBuffer starStr = new StringBuffer();
			for (int i = begin; i < content.length(); i++) {
				starStr.append("*");
			}
			str = content.substring(0, begin) + starStr;
		}
		return str;

	}

}
