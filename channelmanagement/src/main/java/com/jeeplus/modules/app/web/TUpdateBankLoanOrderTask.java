package com.jeeplus.modules.app.web;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.task.UnsyncUpdateBankLoanOrder;


@Controller
@RequestMapping(value = "api/updateLoanOrder")
public class TUpdateBankLoanOrderTask {
	
	
	@ResponseBody
	@RequestMapping(value = "updateBank")
	public String getLoanRepaymentPlanQuery(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		UnsyncUpdateBankLoanOrder uupp = new UnsyncUpdateBankLoanOrder();
		uupp.start();
		return "done";
	}
	
	

}
