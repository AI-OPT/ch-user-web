package com.ai.ch.user.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
	 
}
