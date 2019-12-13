package com.jeeplus.modules.app.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.task.UnsyncRePreCredit;
import com.jeeplus.modules.app.task.UnsyncReportOutTimeBills;

@Controller
@RequestMapping(value = "api/reportOutTimeBills")
public class TReportOutOfTimeCreditOrApplyTask {
	
	
	
	@ResponseBody
	@RequestMapping(value = "process")
	public String process(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		UnsyncReportOutTimeBills udf = new UnsyncReportOutTimeBills();
		udf.start();
		return "done";

		
	}
	

}
