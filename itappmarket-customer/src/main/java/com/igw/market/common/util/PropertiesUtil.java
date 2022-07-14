package com.igw.market.common.util;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesUtil {

	@Value("#{configProperties}")
	private Properties properties;
	
	
	public static PropertiesUtil commonUtil;
	
	@PostConstruct
	public void init() {
		commonUtil = this;
	}
	
	
    
    public static String getCfg(String configName) {
    	try {
    		return (String)commonUtil.properties.get(configName);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static String getCfg(String configName,String defaultVal) {
    	String result = getCfg(configName);
    	if(result != null && !"".equals(result)){
    		result = defaultVal;
    	}
    	return result;
    }
	
}
