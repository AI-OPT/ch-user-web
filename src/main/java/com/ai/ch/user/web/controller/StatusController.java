package com.ai.ch.user.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.ch.user.web.util.HttpURLClient;

@RestController
@RequestMapping("/status")
public class StatusController {

	@RequestMapping("/changeShopStatus")
	public String changeShopStatus() {
		Map<String,String> map = new HashMap<>();
		map.put("companyState", "1");
		map.put("companyId", "832c736ddad34172");
		map.put("appkey", "3a83ed361ebce978731b736328a97ea8");
		String str = HttpURLClient.getAPIData("http://10.19.13.16:28151/opaas/http/srv_up_user_updatecompanystate_update", map);
		return str;
	}
}
