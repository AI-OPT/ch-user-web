package com.ai.ch.user.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("jsp/login");

		return view;
	}

	@RequestMapping("/logincheck")
	public void logincheck(HttpServletRequest request, HttpServletResponse response) {
	}

}
