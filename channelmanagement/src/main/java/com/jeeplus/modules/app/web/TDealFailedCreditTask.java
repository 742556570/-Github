package com.jeeplus.modules.app.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.task.UnsyncRePreCredit;

@Controller
@RequestMapping(value = "api/reCredit")
public class TDealFailedCreditTask {
	
	
	
	@ResponseBody
	@RequestMapping(value = "process")
	public String process(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		UnsyncRePreCredit udf = new UnsyncRePreCredit();
		udf.start();
		return "done";

		
	}
	

}
