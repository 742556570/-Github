package com.jeeplus.modules.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.service.ClCheckLoanStatusService;
import com.jeeplus.modules.app.task.UnsyncCheckLoanStatus;

@Controller
@RequestMapping(value = "api/checkloan")
public class TCheckLoanStatusTask {
	
	
	
	@ResponseBody
	@RequestMapping(value = "checkAndDeal")
	public String process(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		
		UnsyncCheckLoanStatus ucs = new UnsyncCheckLoanStatus();
		ucs.start();
		return "done";

		
	}
}
