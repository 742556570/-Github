package com.jeeplus.modules.app.utils;


import java.util.Date;

import com.jeeplus.common.utils.DateUtils;

public class AppDateUtils {
	
	
	public static String getNowAs_yMDHmsS() {
		return DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	
}
