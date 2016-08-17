package com.ai.ch.user.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/rank")
public class RankController {

	private static final Log LOG = LogFactory.getLog(RankController.class);

	@RequestMapping("/rankrule")
	public ModelAndView rankRule() {
	    //调dubbo服务
	    //IScoreSV gradeSV = DubboConsumerFactory.getService("iGradeSV");
	    
	    return new ModelAndView("/jsp/rank/rankrule");
	    }
}
