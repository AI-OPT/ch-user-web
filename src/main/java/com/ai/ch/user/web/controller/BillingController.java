package com.ai.ch.user.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

@RestController
@RequestMapping("/billing")
public class BillingController {
	
	private static final Logger log = LoggerFactory.getLogger(BillingController.class);
	
	 @RequestMapping("/billingPager")
	    public ModelAndView billingPager() {
	        return new ModelAndView("/jsp/billing/billingList");
	    }
	 
	 @RequestMapping("/marginSetting")
	    public ModelAndView marginSetting() {
	        return new ModelAndView("/jsp/billing/marginSetting");
	    }
	 
	 @RequestMapping("/serviceFeeSetting")
	    public ModelAndView serviceFeeSetting() {
	        return new ModelAndView("/jsp/billing/serviceFeeSetting");
	    }
}
