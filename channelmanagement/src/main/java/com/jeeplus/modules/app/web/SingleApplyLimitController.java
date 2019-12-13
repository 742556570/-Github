package com.jeeplus.modules.app.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.app.api.loantrial.SynLoanTrial;
import com.jeeplus.modules.app.api.loantrial.request.ReqLoanTrialVO;
import com.jeeplus.modules.app.api.loantrial.response.RespLoanTrialVO;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClApplyService;
import com.jeeplus.modules.app.service.ClUsrInfoService;
import com.jeeplus.modules.app.service.ClUsrService;
import com.jeeplus.modules.app.service.SingleApplyLimitService;

/**
 * app提提现单笔限制
 * 
 * @author 阳光保险
 *
 */
@Controller
@RequestMapping(value = "api/applylimit")
public class SingleApplyLimitController {
	@Autowired
	private ClUsrService usrService;
	@Autowired
	private SingleApplyLimitService singleApplyLimitService;
	@Autowired
	private ClApplyService applyService;
	@Autowired
	private ClUsrInfoService clUsrInfoService;
	
	private final static Logger logger = LoggerFactory.getLogger(SingleApplyLimitController.class);

	/**
	 * 单笔提现限制接口
	 * 
	 * @param request
	 * @param response
	 * @param USR_CDE
	 * @return
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "singleApply")
	public String singleApply(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "USR_CDE") String USR_CDE) throws ParseException {

		try {
			

			List<ClUsrApply> applies = applyService.getByUsrCde(USR_CDE);
			for (ClUsrApply apply : applies) {
				if (apply.getStCde().equals("01")) {
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请审核中,请稍后再试", null);
				}

				if (apply.getStCde().equals("12")) {
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请放款中,请稍后再试", null);
				}
			}
			
//增加判断用户是否曾经填写过月收入，和最高学历
			ClUsrInfo clUsrInfo = clUsrInfoService.getUsrInfo(USR_CDE);
			String checkDegreeAndIncomeStatus = "";
			if((clUsrInfo.getMON_INCOME() != null && !clUsrInfo.getMON_INCOME().equalsIgnoreCase("")) && (clUsrInfo.getHIGH_DEGREE() != null && !clUsrInfo.getHIGH_DEGREE().equalsIgnoreCase(""))) {
				checkDegreeAndIncomeStatus = "false";
			}else {
				checkDegreeAndIncomeStatus = "true";
			}
			
			JSONObject result = new JSONObject();
			boolean isCloseService = singleApplyLimitService.singleApplyStatus();
			String msg = AppCommonConstants.getValStr("SINGLEAPPLYWORD");
			ClUsr usr = usrService.getByUsrCde(USR_CDE);
			String isGonganPoor = "false";
			if(usr.getUSR_GRD().contains("gonganPoor")) {
				isGonganPoor = "true";
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "抱歉，冻结中，无法使用", "");
			}
			if (isCloseService == true) {

				int flag = singleApplyLimitService.getSingleApply(USR_CDE);

				if (flag >= 1) {

					result.put("isOn", "true");
					result.put("isGonganPoor", isGonganPoor);
					result.put("checkDegreeAndIncomeStatus", checkDegreeAndIncomeStatus);
					result.put("msg", msg);
					logger.info("单笔限制报文：" + result.toString());
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "成功", result);
				} else {
					result.put("isOn", "false");
					result.put("isGonganPoor", isGonganPoor);
					result.put("checkDegreeAndIncomeStatus", checkDegreeAndIncomeStatus);
					result.put("msg", "无需限制");
					logger.info("单笔限制报文：" + result.toString());
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "成功", result);
				}
			} else {
				result.put("isOn", "false");
				result.put("isGonganPoor", isGonganPoor);
				result.put("checkDegreeAndIncomeStatus", checkDegreeAndIncomeStatus);
				result.put("msg", "无需限制");
				logger.info("单笔限制报文：" + result.toString());
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "成功", result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "失败", "");
		}
	}

	/**
	 * 温馨提示接口
	 * 
	 * @param request
	 * @param response
	 * @param USR_CDE
	 * @return
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "singleApplyStatus")
	public String singleApplyStatus(HttpServletRequest request, HttpServletResponse response) throws ParseException {

		try {
			boolean isCloseService = singleApplyLimitService.warmPromptStatus();
			String SingleApplyStatusMSG = AppCommonConstants.getValStr("SingleApplyStatusMSG");
			JSONObject result = new JSONObject();
			result.put("isOn", "true");
			result.put("msg", SingleApplyStatusMSG);
			if (!isCloseService) {
				result.put("isOn", "false");
				result.put("msg", "温馨提示开关关闭");
				logger.info("单温馨提示开关报文：" + result.toString());
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", result);
			}
			logger.info("温馨提示开关报文：" + result.toString());
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "失败", "");
		}
	}
}
