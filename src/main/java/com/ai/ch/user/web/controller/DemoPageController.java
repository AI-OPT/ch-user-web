package com.ai.ch.user.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * it is just a demo
 * @author Zh
 *
 */
@RestController
@RequestMapping("/demo")
public class DemoPageController {
	private static final Log LOG = LogFactory.getLog(DemoPageController.class);
    
    @RequestMapping("/demopage")
    public ModelAndView viewclc() {
        return new ModelAndView("/demo/demopage");
    }
    

}
