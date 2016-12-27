package com.ai.ch.user.web.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;

import com.ai.opt.base.exception.BusinessException;
import com.ylink.itfin.certificate.SecurityUtil;
import com.ylink.upp.base.oxm.util.OxmHandler;

public class PayUtil {

	@Autowired
	private OxmHandler oxmHandler;

	/**
	 * 加签
	 * 
	 * @param xmlMsg
	 * @return
	 * @throws Exception
	 */
	public String sign(String xmlMsg) throws Exception {
		InputStream in = null;
		String sign="";
		try {
			ResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource pfxResource = resourceLoader.getResource(PropertiesUtil.getStringByKey("sigh_classpath"));
			in = new FileInputStream(pfxResource.getFile());
			byte[] pfxByte = IOUtils.toByteArray(in);
			sign = SecurityUtil.pfxWithSign(pfxByte, xmlMsg, "111111");
		}finally {
			if (in != null) {
				in.close();
			}
		}
	return sign;
	}

	/**
	 * 拼装报文头
	 * 
	 * @return
	 */
	public String initMsgHeader(String merNo, String tranType) {
		StringBuilder buffer = new StringBuilder("{H:01");
		buffer.append(merNo);
		buffer.append("1000000000000000");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		buffer.append(dateFormat.format(new Date(System.currentTimeMillis())));
		buffer.append(tranType);
		buffer.append("}");
		return buffer.toString();
	}

	/**
	 * 验签
	 * 
	 * @param xmlMsg
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public boolean verify(String xmlMsg, String sign) throws Exception {
		InputStream in=null;
		byte[] cerByte;
		try{
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource pfxResource = resourceLoader.getResource(PropertiesUtil.getStringByKey("check_sign_classpath"));
		in = new FileInputStream(pfxResource.getFile());
		cerByte = IOUtils.toByteArray(in);
		}finally {
			if (in != null) {
				in.close();
			}
		}
		return SecurityUtil.verify(cerByte, xmlMsg, sign);
	}

	public boolean checkUrl(String url) {
		Pattern p = Pattern.compile("^(http|https)://.+?(?=/)");
		Matcher m = p.matcher(url);
		return m.find();
	}

	/**
	 * 发送请求
	 * @param url
	 * @param param
	 * @param charset
	 * @return
	 * @throws Exception
	 * @author zhangqiang7
	 * @UCUSER
	 */
	public String sendHttpPost(String url, Map<String, String> param, String charset) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
		try {
			HttpPost post = new HttpPost(url);
			post.setConfig(requestConfig);
			if (!CollectionUtils.isEmpty(param)) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				//修改entryset
				for (Entry<String, String> key : param.entrySet()) {
					params.add(new BasicNameValuePair(String.valueOf(key.getKey()), String.valueOf(param.get(key))));
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
					if (!this.checkUrl(lo)) {
						lo = findUrl(post.getURI().toASCIIString()) + lo;
					}
					return sendHttpPostGet(lo, null, charset);
				}
				String result = EntityUtils.toString(response.getEntity(), charset);
				return result;
			} else {
				throw new BusinessException("调用URL地址通讯失败,失败状态：" + response.getStatusLine().getStatusCode());
			}
		} finally {
			if (null != httpClient) {
				httpClient.close();
			}
		}
	}

	/**
	 * 匹配url
	 * @param url
	 * @return
	 * @author zhangqiang7
	 * @UCUSER
	 */
	protected static String findUrl(String url) {
		Pattern p = Pattern.compile("^(http|https)://.+?(?=/)");
		Matcher m = p.matcher(url);
		if (m.find()) {
			return m.group();
		}
		return null;
	}

	/**
	 * 发送请求
	 * @param url
	 * @param data
	 * @param charset
	 * @return
	 * @throws Exception
	 * @author zhangqiang7
	 * @UCUSER
	 */
	private String sendHttpPostGet(String url, Map<String, String> data, String charset) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
		try {
			url = url.replaceFirst("^http://|^http://", "");
			URIBuilder uriBuilder = new URIBuilder().setScheme("https").setHost(url);
			if (!CollectionUtils.isEmpty(data)) {
				//修改entryset
				for (Entry<String, String> key : data.entrySet()) {
					uriBuilder.setParameter(String.valueOf(key.getKey()), String.valueOf(data.get(key)));
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
				throw new BusinessException("调用URL地址通讯失败,失败状态：" + response.getStatusLine().getStatusCode());
			}
		} finally {
			if (null != httpClient) {
				httpClient.close();
			}
		}
	}
}
