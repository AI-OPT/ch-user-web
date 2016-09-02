package com.ai.ch.user.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.ch.user.web.constants.ChWebConstants;
import com.ai.ch.user.web.vo.GroupStateVo;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.web.model.ResponseData;

@RestController
@RequestMapping("/crm")
public class CrmManagerController {
	
	 @RequestMapping("/supplierStatePager")
	    public ModelAndView supplierStatePager() {
	        return new ModelAndView("/jsp/crm/supplierStateList");
	    }
	 
	 @RequestMapping("/shopStatePager")
	    public ModelAndView shopStatePager() {
		    return new ModelAndView("/jsp/crm/shopStateList");
	    }
	 

		@RequestMapping("/getGroupStateList")
		@ResponseBody
		public ResponseData<PageInfo<GroupStateVo>> getGroupStateList(HttpServletRequest request) {
			ResponseData<PageInfo<GroupStateVo>> response = new ResponseData<PageInfo<GroupStateVo>>(
					ChWebConstants.OperateCode.SUCCESS, "成功");
			PageInfo<GroupStateVo> pageInfo = new PageInfo<GroupStateVo>();
			//IShopInfoSV shopInfoSV = DubboConsumerFactory.getService("iShopInfoSV");
			//GeneralSSOClientUser user = (GeneralSSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
			pageInfo.setCount(20);
			pageInfo.setPageCount(4);
			pageInfo.setPageNo(1);
			pageInfo.setPageSize(5);
			List<GroupStateVo> list = new ArrayList<GroupStateVo>();
			for(int i=0;i<3;i++){
				 GroupStateVo groupStateVo = new GroupStateVo();
				 groupStateVo.setUserId(i+"");
				 groupStateVo.setGroupName("亚信");
				 groupStateVo.setUserName("长虹");
				 groupStateVo.setState("10");
				 groupStateVo.setStateValue("正常");
				 list.add(groupStateVo);
			 }
			 for(int j=3;j<5;j++){
				 GroupStateVo groupStateVo = new GroupStateVo();
				 groupStateVo.setUserId(j+"");
				 groupStateVo.setGroupName("亚信");
				 groupStateVo.setUserName("长虹");
				 groupStateVo.setState("11");
				 groupStateVo.setStateValue("冻结");
				 list.add(groupStateVo);
			 }
			pageInfo.setResult(list);
			response.setData(pageInfo);
			return response;
		}
		
	 
}
