package com.jeeplus.modules.app.web;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.task.UnsyncSendBillRemindSmsTask;
import com.jeeplus.modules.app.task.UnsyncSendBindBankCardSmsTask;
import com.jeeplus.modules.app.task.UnsyncSendCreditReportRemindSmsTask;
import com.jeeplus.modules.app.task.UnsyncSendOverDueRemindSmsTask;
import com.jeeplus.modules.app.task.UnsyncUpdatePaymentPlan;

@Controller
@RequestMapping(value = "api/sendSms")
public class TSmsSendTask {

	
	@ResponseBody
	@RequestMapping(value = "bill")
	public String bill(HttpServletRequest request, HttpServletResponse response)  {
		UnsyncSendBillRemindSmsTask task = new UnsyncSendBillRemindSmsTask();
		task.start();
		return "done";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "overDue")
	public String overDue(HttpServletRequest request, HttpServletResponse response)  {
		UnsyncSendOverDueRemindSmsTask task = new UnsyncSendOverDueRemindSmsTask();
		task.start();
		return "done";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "creditReport")
	public String creditReport(HttpServletRequest request, HttpServletResponse response)  {
		UnsyncSendCreditReportRemindSmsTask task = new UnsyncSendCreditReportRemindSmsTask();
		task.start();
		return "done";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "binBankCard")
	public String binBankCard(HttpServletRequest request, HttpServletResponse response)  {
		UnsyncSendBindBankCardSmsTask task = new UnsyncSendBindBankCardSmsTask();
		task.start();
		return "done";
	}
	

}


