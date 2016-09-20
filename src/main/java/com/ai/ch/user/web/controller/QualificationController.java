package com.ai.ch.user.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;
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

import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.vo.BusinessListInfo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/qualification")
public class QualificationController {
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
	public ModelAndView toCheckedShopPager() {
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
	public ModelAndView toSuplierCheckPager() {
		return new ModelAndView("/jsp/qualification/supplier/auditeQualification");
	}
	/**
	 * 店铺审核页面
	 * @return
	 */
	@RequestMapping("/toShopCheckPager")
	public ModelAndView toShopCheckDetailPager() {
		return new ModelAndView("/jsp/qualification/shop/auditeQualification");
	}
	/**
	 * 供应商详情页面
	 * @return
	 */
	@RequestMapping("/toSuplierDetailPager")
	public ModelAndView toSuplierDetailPager() {
		return new ModelAndView("/jsp/qualification/supplier/checkedDetail");
	}
	/**
	 * 店铺详情页面
	 * @return
	 */
	@RequestMapping("/toShopDetailPager")
	public ModelAndView toShopDetailPager() {
		return new ModelAndView("/jsp/qualification/shop/checkedDetail");
	}
	
	//查询未审核列表
	@RequestMapping("/getUncheckList")
	@ResponseBody
	public ResponseData<PageInfo<BusinessListInfo>> getUncheckList(HttpServletRequest request,String companyName,String username,String companyType){
		ResponseData<PageInfo<BusinessListInfo>> response = null;
		PageInfo<BusinessListInfo> pageInfo =null;
		ResponseHeader header = null;
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", "3a83ed361ebce978731b736328a97ea8");
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
			str = HttpClientUtil.sendPost("http://10.19.13.16:28151/opaas/http/srv_up_user_searchcompanylist_qry", JSON.toJSONString(map),mapHeader);
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
		public ResponseData<PageInfo<BusinessListInfo>> getCheckedList(HttpServletRequest request,String companyName,String username,String companyType){
			ResponseData<PageInfo<BusinessListInfo>> response = null;
			PageInfo<BusinessListInfo> pageInfo =null;
			ResponseHeader header = null;
			Map<String, String> map = new HashMap<>();
			Map<String, String> mapHeader = new HashMap<>();
			mapHeader.put("appkey", "3a83ed361ebce978731b736328a97ea8");
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
				str = HttpClientUtil.sendPost("http://10.19.13.16:28151/opaas/http/srv_up_user_searchcompanylist_qry", JSON.toJSONString(map),mapHeader);
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
						 businessInfo.setCreateTime(object.getString("createTime"));
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
}
