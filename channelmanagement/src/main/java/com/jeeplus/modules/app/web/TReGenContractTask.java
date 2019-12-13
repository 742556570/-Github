package com.jeeplus.modules.app.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.service.ReGenContractService;


@Controller
@RequestMapping(value = "api/regencontract")
public class TReGenContractTask {
	
	@Autowired
	private ReGenContractService service;
	
	@ResponseBody
	@RequestMapping(value = "applyBatch")
	public String process(
			HttpServletRequest request, 
			HttpServletResponse response) {
		
		service.dealApplyAll();
		
		
		
		return "done";

		
	}
	

}
