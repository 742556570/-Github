package com.jeeplus.modules.app.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.service.ClDealContractWithPolicyNoService;
import com.jeeplus.modules.app.task.UnsyncRePreCredit;

@Controller
@RequestMapping(value = "api/upImgsAndCa")
public class TUpImgsAndCAByPolicyNoTask {
	
	@Autowired
	private ClDealContractWithPolicyNoService  dealService;
	
	@ResponseBody
	@RequestMapping(value = "process")
	public String process(
			String policyNo,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		dealService.dealContractWithPolicyNo(policyNo);
		return "done";

		
	}
	

}
