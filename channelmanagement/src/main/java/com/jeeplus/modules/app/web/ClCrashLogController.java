package com.jeeplus.modules.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.entity.ClCrashLog;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClCrashLogService;

@Controller
@RequestMapping(value = "api/crashlog")
public class ClCrashLogController {
	
	@Autowired
	private ClCrashLogService crashLogService;
	
	@ResponseBody
	@RequestMapping(value = "addLog")
	public String addLog(@RequestParam(value="platform" )String PLATFORM,
			@RequestParam(value="brand" )String BRAND,
			@RequestParam(value="model" )String MODEL,
			@RequestParam(value="display" )String DISPLAY,
			@RequestParam(value="osVersion" )String OS_VERSION,
			@RequestParam(value="appVersion" )String APP_VERSION,
			@RequestParam(value="time" )String CRT_DT,
			@RequestParam(value="crashLog" )String CRASH_LOG,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		ClCrashLog clCrashLog = new ClCrashLog(PLATFORM, BRAND, MODEL, DISPLAY, OS_VERSION, APP_VERSION, CRT_DT, CRASH_LOG);
		
		int i = crashLogService.addCrashLog(clCrashLog);
		
		if(i > 0) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "保存日志成功", null);
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存日志失败", null);
		}
	}

}
