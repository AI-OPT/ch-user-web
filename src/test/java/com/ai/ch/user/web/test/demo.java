package com.ai.ch.user.web.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;


public class demo {
	@Test
	public void testDemo(){
		String msgHeader = "{H:01CO20160700000004100000000000000020160929142018103.001.01}";
		String xmlMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<RespInfo xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>"+
			    "<GrpHdr>"+
			        "<MerNo>CO20160700000004</MerNo>"+
			        "<CreDtTm>20160929142017</CreDtTm>"+
			        "<TranType>103.001.01</TranType>"+
			    "</GrpHdr>"+
			    "<GrpBody>"+
			        "<MerOrderId>CO20160800000008</MerOrderId>"+
			        "<PayTranSn>2</PayTranSn>"+
			        "<OrderAmt>40</OrderAmt>"+
			        "<OrderDate>1475130017902</OrderDate>"+
			        "<PayStatus>00</PayStatus>"+
			        "<Remark>11</Remark>"+
			        "<Resv>11</Resv>"+
			    "</GrpBody>"+
			"</RespInfo>";
		String sign = "TRePCKTz9bzMnQ8qJicBQe8K3nW5fKCoxnV0OLSPDlOQkqDgg5d2eDpL+PTQhX6l9drIcgzx7krA36oLcMjvGGVXyG6iIUKip+yxYoQt5Dg81rZ5S85pRyNnf6ZWFvIp9vLyYQsuUHQykzSc7Nvq5eX4dBAmeke/HMP+a7n4Ygf9UHbPMR9QrKEZI9ti1K32Ja0V9wMPdIHHZrpRbEa9ReIEteUhDFUNcVhaR3f7rmu+pLK45ZRqD0Ax/tPdGoT33Zcb6+/4IsnI8hGwXxL0RofNhSahQcQHb8aeV3kGXHJjuYXmW0MzY7ZPo0h5gd9y+5iW9cjWuFgQ0ebKBfEsA==";
		Map<String, String> param = new TreeMap<String, String>();
		param.put("msgHeader", msgHeader);
		param.put("xmlBody", xmlMsg);
		param.put("signMsg", sign);
		String result = null;
		try {
			result = sendHttpPost("http://124.207.3.100:8083/ch-user-web/defaultManager/paymentNotifications", param, "UTF-8");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String sendHttpPost(String url, Map<String, String> param, String charset) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
		try {
			HttpPost post = new HttpPost(url);
			post.setConfig(requestConfig);
			
			if (!CollectionUtils.isEmpty(param)) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					params.add(new BasicNameValuePair(key, param.get(key)));
				}
				HttpEntity fromEntity = new UrlEncodedFormEntity(params, charset);
				post.setEntity(fromEntity);
			}
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), charset);
			} else if (302 == response.getStatusLine().getStatusCode()) {
				Header hs = response.getFirstHeader("Location");
				if (hs != null) {
					String lo = hs.getValue();
					if (!checkUrl(lo)) {
						lo = findUrl(post.getURI().toASCIIString()) + lo;
					}
					return sendHttpPostGet(lo, null, charset);
				}
				String result = EntityUtils.toString(response.getEntity(), charset);
				return result;
			} else {
				throw new Exception("调用URL地址通讯失败,失败状态：" + response.getStatusLine().getStatusCode());
			}
		} finally {
			if (null != httpClient) {
				httpClient.close();
			}
		}
	}
	
	private static boolean checkUrl(String url) {
		Pattern p = Pattern.compile("^(http|https)://.+?(?=/)");
		Matcher m = p.matcher(url);
		return m.find();
	}
	
	protected static String findUrl(String url) {
		Pattern p = Pattern.compile("^(http|https)://.+?(?=/)");
		Matcher m = p.matcher(url);
		if (m.find()) {
			return m.group();
		}
		return null;
	}
	
	private static String sendHttpPostGet(String url, Map<String, String> data, String charset) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
		try {
			url = url.replaceFirst("^http://|^http://", "");
			URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost(url);
			if (!CollectionUtils.isEmpty(data)) {
				for (String key : data.keySet()) {
					uriBuilder.setParameter(key, data.get(key));
				}
			}
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			httpGet.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), charset);
			} else if (302 == response.getStatusLine().getStatusCode()) {
				Header hs = response.getFirstHeader("Location");
				if (hs != null) {
					String lo = hs.getValue();
					if (!checkUrl(lo)) {
						lo = findUrl(httpGet.getURI().toASCIIString()) + lo;
					}
					return sendHttpPostGet(lo, null, charset);
				}
				String result = EntityUtils.toString(response.getEntity(), charset);
				return result;
			} else {
				throw new Exception("调用URL地址通讯失败,失败状态：" + response.getStatusLine().getStatusCode());
			}
		} finally {
			if (null != httpClient) {
				httpClient.close();
			}
		}
	}
}
