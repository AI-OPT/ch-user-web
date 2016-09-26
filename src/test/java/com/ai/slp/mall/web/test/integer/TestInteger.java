package com.ai.slp.mall.web.test.integer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TestInteger {

	@Test
	public void testQueryInteger(){
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", "3a83ed361ebce978731b736328a97ea8");
		map.put("pageNo","2");
		map.put("pageSize", "5");
		String str ="";
		try {
			str = HttpClientUtil.sendPost("http://10.19.13.16:28151/opaas/http/srv_up_user_searchcompanylist_qry", JSON.toJSONString(map),mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		JSONObject json = JSON.parseObject(str);
		System.out.println(JSONObject.toJSONString(json));
	}
	
	@Test
	public void testUpdateInteger(){
		Map<String, String> map = new HashMap<>();
		Map<String, String> mapHeader = new HashMap<>();
		mapHeader.put("appkey", "3a83ed361ebce978731b736328a97ea8");
		map.put("openId", "1");
		map.put("auditState","1");
		map.put("companyId","e3c25734c079468b");
		String str ="";
		try {
			str = HttpClientUtil.sendPost("http://10.19.13.16:28151/opaas/http/srv_up_user_updateauditstate_update", JSON.toJSONString(map), mapHeader);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		System.out.println(JSON.parseObject(str).toJSONString());
	}
}
