package com.ai.ch.user.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.ch.user.web.controller.QualificationController;
import com.alibaba.fastjson.JSON;  
  
public final class ResourceLoader {  
  
	private static final Logger LOG = LoggerFactory.getLogger(QualificationController.class);
	
    private static ResourceLoader loader = new ResourceLoader();  
    private static Map<String, Properties> loaderMap = new HashMap<String, Properties>();  
  
    private ResourceLoader() {  
    }  
  
    public static ResourceLoader getInstance() {  
        return loader;  
    }  
      
    public Properties getPropFromProperties(String fileName) throws Exception {  
    	FileInputStream fileInputStream = null;
    	Properties prop = null;
        prop = loaderMap.get(fileName);  
        if (prop != null) {  
            return prop;  
        }  
        String filePath = null;  
        String configPath = System.getProperty("configurePath");  
  
        if (configPath == null) {  
            filePath = this.getClass().getClassLoader().getResource(fileName).getPath();  
        } else {  
            filePath = configPath + "/" + fileName;  
        }  
        prop = new Properties();  
        try{
        File file = new File(filePath);
        fileInputStream = new FileInputStream(file);
        
        prop.load(fileInputStream);  
        loaderMap.put(fileName, prop);
        }catch(IOException e){
        	 LOG.error("释放ResultSet出错", e);  
        }finally {  
           safeClose(fileInputStream);
        }
        return prop;  
    }  
    
    public static void safeClose(InputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				LOG.error(JSON.toJSONString(e));
			}
		}
	}
}  

