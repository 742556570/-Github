package com.jeeplus.modules.app.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.api.account.LoanPrePayment.LoanPrePayment;
import com.jeeplus.modules.app.api.account.LoanPrePayment.response.LoanPrepaymentResp;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.dao.ClUsrLoanDao;
import com.jeeplus.modules.app.entity.ClUsrLoan;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClCheckPassWordService;


/**
 * 提前还款接口
 * 
 * @author 阳光保险
 *
 */
@Controller
@RequestMapping(value = "api/collection")
public class LoanPrePaymentController {

	private final static Logger logger = LoggerFactory.getLogger(LoanPrePaymentController.class);
	@Autowired
	private ClCheckPassWordService clCheckPassWordService;
	@Autowired
	private ClUsrLoanDao usrLoanDao;
	/**
	 * 提前还款试算接口
	 * @param request
	 * @param response
	 * @param USR_CDE
	 * @param GUARANTYID
	 * @param TYPE
	 * @return
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "getLoanPrePaymentTrial")
	public String getLoanPrePaymentTrial(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "GUARANTYID") String GUARANTYID) throws ParseException {
		
		String isCloseService = AppCommonConstants.getValStr("IsNeedShutdownLoanService");
		JSONObject result = new JSONObject();
		result.put("isOn", "true");
		if("true".equals(isCloseService)) {
			result.put("isOn", "false");
			result.put("msg", "服务升级中，请稍后再试。");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "服务升级中，请稍后再试。",result );
		}
		
		String CloseServiceByTimeStart = AppCommonConstants.getValStr("ShutdownLoanServiceStart"); 
		String startTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+CloseServiceByTimeStart+"00";
		Date startTime = DateUtils.parseDate4ChkFkTime(startTimeStr);
		if(new Date().getTime() - startTime.getTime() >= 0) {
			result.put("isOn", "false");
			result.put("msg", "服务升级中，请稍后再试。");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "服务升级中，请稍后再试。", result);
		}
		
		String CloseServiceByTimeEnd = AppCommonConstants.getValStr("ShutdownLoanServiceEnd"); 
		String endTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+CloseServiceByTimeEnd+"00";
		Date endTime = DateUtils.parseDate4ChkFkTime(endTimeStr);
		if(new Date().getTime() - endTime.getTime() <= 0) {
			result.put("isOn", "false");
			result.put("msg", "服务升级中，请稍后再试。");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "服务升级中，请稍后再试。", result);
		}
		
		
		
		LoanPrePayment loanPrePayment = new LoanPrePayment ();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String CALDATE = sdf.format(new Date());
		String TYPE = "1";
		LoanPrepaymentResp loanPrepaymentResp = loanPrePayment.loanPrePayment(GUARANTYID, "", TYPE, CALDATE, "");
		logger.info("提前还款试算接口返回结果" + loanPrepaymentResp.toString());

		if (loanPrepaymentResp.getDataBody().getDataRecord().getHandleCode().equals("1")) {
			JsonObject resultObj = new JsonObject();
			resultObj.addProperty("AMOUNT", loanPrepaymentResp.getDataBody().getDataRecord().getAmount());
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, loanPrepaymentResp.getDataBody().getDataRecord().getHandleInfo(), resultObj);
		}

		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, loanPrepaymentResp.getDataBody().getDataRecord().getHandleInfo(), null);
	}
	/**
	 * 提前还款申请接口
	 * @param request
	 * @param response
	 * @param USR_CDE
	 * @param GUARANTYID
	 * @param TYPE
	 * @return
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "getLoanPrePaymentApply")
	public String getLoanPrePaymentApply(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "GUARANTYID") String GUARANTYID, 
			@RequestParam(value = "PASSWORDSIGN") String PASSWORDSIGN, 
			@RequestParam(value = "USR_CDE") String USR_CDE) throws ParseException {
		
		String isCloseService = AppCommonConstants.getValStr("IsNeedShutdownLoanService");
		JSONObject result = new JSONObject();
		result.put("isOn", "true");
		if("true".equals(isCloseService)) {
			result.put("isOn", "false");
			result.put("msg", "服务升级中，请稍后再试。");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "服务升级中，请稍后再试。",result );
		}
		
		String CloseServiceByTimeStart = AppCommonConstants.getValStr("ShutdownLoanServiceStart"); 
		String startTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+CloseServiceByTimeStart+"00";
		Date startTime = DateUtils.parseDate4ChkFkTime(startTimeStr);
		if(new Date().getTime() - startTime.getTime() >= 0) {
			result.put("isOn", "false");
			result.put("msg", "服务升级中，请稍后再试。");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "服务升级中，请稍后再试。", result);
		}
		
		String CloseServiceByTimeEnd = AppCommonConstants.getValStr("ShutdownLoanServiceEnd"); 
		String endTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+CloseServiceByTimeEnd+"00";
		Date endTime = DateUtils.parseDate4ChkFkTime(endTimeStr);
		if(new Date().getTime() - endTime.getTime() <= 0) {
			result.put("isOn", "false");
			result.put("msg", "服务升级中，请稍后再试。");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "服务升级中，请稍后再试。", result);
		}
		
		
		String TYPE = "2";
		LoanPrePayment loanPrePayment = new LoanPrePayment ();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String CALDATE = sdf.format(new Date());
		String payPwd = clCheckPassWordService.getPayPwdByUsrCde(USR_CDE);

		if(!payPwd.equals(PASSWORDSIGN)) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "验证支付密码错误", null);
		}
		
		ClUsrLoan usrLoan = usrLoanDao.selectByPrimaryKey(GUARANTYID);
		if(usrLoan == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未查询到保单号对应借款记录", null);
		}
		Date startDate = usrLoan.getBeginDt();
		String sdt = DateUtils.formatDate(startDate, "yyyyMMdd");
		String ndt = DateUtils.formatDate(new Date(), "yyyyMMdd");
		if(sdt.equals(ndt)) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "放款当天不允许提现还款", null);
		}
		
		
		LoanPrepaymentResp loanPrepaymentResp = loanPrePayment.loanPrePayment(GUARANTYID, "", TYPE, CALDATE, "");
		logger.info("提前还款申请接口返回结果" + loanPrepaymentResp.toString());

		if (loanPrepaymentResp.getDataBody().getDataRecord().getHandleCode().equals("1")) {
	
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, loanPrepaymentResp.getDataBody().getDataRecord().getHandleInfo(), null);
		}

		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, loanPrepaymentResp.getDataBody().getDataRecord().getHandleInfo(), null);
	}
	

}
