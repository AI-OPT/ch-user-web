package com.ai.ch.user.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;  
  
public final class ResourceLoader {  
  
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
        File file = new File(filePath);
        fileInputStream = new FileInputStream(file);
        
        prop.load(fileInputStream);  
        loaderMap.put(fileName, prop);  
        return prop;  
    }  
}  

