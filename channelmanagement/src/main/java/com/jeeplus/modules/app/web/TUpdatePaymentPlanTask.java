package com.jeeplus.modules.app.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.LoanRepaymentPlanQuery;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response.LoanRepaymentPlanDetail;
import com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response.LoanRepaymentPlanResp;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClUsrApplyService;
import com.jeeplus.modules.app.service.LoanRepaymentPlanQueryService;
import com.jeeplus.modules.app.task.UnsyncUpdatePaymentPlan;
import com.jeeplus.modules.app.utils.CacheHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "api/updatePayPlan")
public class TUpdatePaymentPlanTask {
	
	
	@ResponseBody
	@RequestMapping(value = "update")
	public String getLoanRepaymentPlanQuery(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		UnsyncUpdatePaymentPlan uupp = new UnsyncUpdatePaymentPlan();
		uupp.start();
		return "done";
	}
	
	

}
