package com.ai.ch.user.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class HttpURLClient {

	public static String getAPIData(String urlStr,Map<String,String> mapParam){
		PrintWriter out = null;  
        BufferedReader in = null;  
        String apiData = ""; 
		try {
			//请求地址
			URL	url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=UTF-8"); 
			// 设置通用的请求属性  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent",  "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
			//发送Post强求，开启其读写的功能
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            //传参
            for(Map.Entry<String, String> entry:mapParam.entrySet())
            connection.setRequestProperty(entry.getKey(), entry.getValue());
            //发送Post请求
            connection.connect();
            out = new PrintWriter(connection.getOutputStream());  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(  new InputStreamReader(connection.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                apiData +=  line;  
            }  
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 finally {  
	            try {  
	                if (out != null) {  
	                    out.close();  
	                }  
	                if (in != null) {  
	                    in.close();  
	                }  
	            } catch (IOException ex) {  
	                ex.printStackTrace();  
	            }  
	        }  
		return apiData;
	}
	
}
