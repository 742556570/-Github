package com.jeeplus.modules.app.constant;

import java.util.HashMap;
import java.util.List;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.app.dao.ClConfigDao;
import com.jeeplus.modules.app.entity.ClConfig;

public class AppCommonConstants {
	
	private static ClConfigDao clCfgDao = SpringContextHolder.getBean(ClConfigDao.class); 
	
	private static List<ClConfig> clCfgs = clCfgDao.getAllConfig();
	
	private static HashMap<String, ClConfig> configs = new HashMap<String, ClConfig>();
	
	static {
		for (ClConfig clConfig : clCfgs) {
			configs.put(clConfig.getKey(), clConfig);
		}
	}
	
	
	public static String getValStr(String key){
		ClConfig clCfg = configs.get(key);
		if(clCfg.getType().equalsIgnoreCase("string")) {
			return clCfg.getVal();
		}else {
			return "";
		}
		
	}
	
	public static int getValInt(String key){
		ClConfig clCfg = configs.get(key);
		if(clCfg.getType().equalsIgnoreCase("int")) {
			return new Integer(clCfg.getVal());
		}else {
			return -1;
		}
		
	}
	
	public static void refresh() {
		List<ClConfig> clCfgs = clCfgDao.getAllConfig();
		for (ClConfig clConfig : clCfgs) {
			configs.put(clConfig.getKey(), clConfig);
		}
	}
	
	
	
	
}
