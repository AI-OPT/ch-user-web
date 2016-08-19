package com.ai.ch.user.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/defaultManager")
public class DefaultManagerController {
	
	@RequestMapping("/defaultPager")
    public ModelAndView billingPager() {
        return new ModelAndView("/jsp/defaultManager/defaultManagerList");
    }
	
}
