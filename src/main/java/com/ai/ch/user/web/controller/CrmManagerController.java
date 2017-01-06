package com.ai.ch.user.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 商户管理controller
 * @author Zh
 *
 */
@RestController
@RequestMapping("/crm")
public class CrmManagerController {
	
	/**
	 * 供应商状态页面
	 * @return
	 */
	 @RequestMapping("/supplierStatePager")
	    public ModelAndView supplierStatePager() {
	        return new ModelAndView("/jsp/crm/supplierStateList");
	    }
	 
	 /**
	  * 店铺状态页面
	  * @return
	  */
	 @RequestMapping("/shopStatePager")
	    public ModelAndView shopStatePager() {
		    return new ModelAndView("/jsp/crm/shopStateList");
	    }
	 
}
