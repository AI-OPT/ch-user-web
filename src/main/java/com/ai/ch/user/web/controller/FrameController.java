package com.ai.ch.user.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FrameController {
	private static final Log LOG = LogFactory.getLog(FrameController.class);

	@RequestMapping("/frame")
    public ModelAndView view(String path) {
        return new ModelAndView("/inc/frame");
    }
    
    

}
