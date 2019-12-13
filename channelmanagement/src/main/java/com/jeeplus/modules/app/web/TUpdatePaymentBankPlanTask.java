package com.jeeplus.modules.app.web;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.task.UnsyncUpdateBankPaymentPlan;

@Controller
@RequestMapping(value = "api/updatePayPlan")
public class TUpdatePaymentBankPlanTask {
	
	
	@ResponseBody
	@RequestMapping(value = "updateBankPlan")
	public String getLoanRepaymentPlanQuery(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		UnsyncUpdateBankPaymentPlan uupp = new UnsyncUpdateBankPaymentPlan();
		uupp.start();
		return "done";
	}
	
	

}
