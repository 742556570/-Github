package com.jeeplus.modules.app.web;

import java.math.BigDecimal;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jeeplus.modules.app.api.loantrial.SynLoanTrial;
import com.jeeplus.modules.app.api.loantrial.request.ReqLoanTrialVO;
import com.jeeplus.modules.app.api.loantrial.response.RespLoanTrialVO;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;

/**
 * app提供还款计划核算接口
 * 
 * @author 阳光保险
 *
 */
@Controller
@RequestMapping(value = "api/collection")
public class LoanTrialController {

	private final static Logger logger = LoggerFactory.getLogger(LoanTrialController.class);
	/**
	 * 还款计划试算
	 * @param request
	 * @param response
	 * @param ORIG_PRCP
	 * @param LOAN_INT_RATE
	 * @param TNR
	 * @param USR_CDE
	 * @return
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "getLoanTrial")
	public String getLoanTrial(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "ORIG_PRCP") String ORIG_PRCP,
			@RequestParam(value = "LOAN_INT_RATE") String LOAN_INT_RATE,
			@RequestParam(value = "TNR") String TNR,
			@RequestParam(value = "PRDCDE") String PRDCDE,
			@RequestParam(value = "CHRG_PCT") String CHRG_PCT,
			@RequestParam(value = "USR_CDE") String USR_CDE) throws ParseException {
		SynLoanTrial synLoanTrial = new SynLoanTrial();
		ReqLoanTrialVO reqLoanTrialVO = setLoanTrialData(ORIG_PRCP, LOAN_INT_RATE, CHRG_PCT,TNR,PRDCDE);
		RespLoanTrialVO respLoanTrialVO = synLoanTrial.synLoanTrial(reqLoanTrialVO);
		logger.info("还款计划试算接口返回结果" + respLoanTrialVO.toString());
		
		if (respLoanTrialVO.getErrorCode().equals("00000")) {

			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "成功", respLoanTrialVO);
		}

		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "失败", null);
	}
	/**
	 * 解析参数 设值
	 * @param job
	 * @return
	 * @throws ParseException
	 */
	private ReqLoanTrialVO setLoanTrialData(String ORIG_PRCP,String LOAN_INT_RATE,String CHRG_PCT,String TNR,String PRDCDE) throws ParseException {
		ReqLoanTrialVO ReqLoanTrialVO = new ReqLoanTrialVO();
		List<ReqLoanTrialVO> list = new ArrayList<ReqLoanTrialVO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String bdate = sdf.format(new Date());
		Calendar now = Calendar.getInstance();
		ReqLoanTrialVO.setServiceId("serv10000100027");
		ReqLoanTrialVO.setORIG_PRCP(ORIG_PRCP);
		ReqLoanTrialVO.setDUE_DAY(String.valueOf(now.get(Calendar.DAY_OF_MONTH)));
		ReqLoanTrialVO.setLOAN_ACTV_DT(bdate);
		ReqLoanTrialVO.setLOAN_TYP(PRDCDE);//等待核算确认
		ReqLoanTrialVO.setINT_START_DT(bdate);
		ReqLoanTrialVO.setLOAN_INT_RATE(LOAN_INT_RATE);
		ReqLoanTrialVO.setPAYM_FREQ_UNIT("M");
		ReqLoanTrialVO.setPAYM_FREQ_FREQ("1");
		ReqLoanTrialVO.setLOAN_PAYM_TYP("01");
		ReqLoanTrialVO.setINSTM_IND("Y");
		ReqLoanTrialVO.setTNR(TNR);
		ReqLoanTrialVO.setCAL_TOT_INSTM("");
		ReqLoanTrialVO.setPS_DUE_DT("");
		ReqLoanTrialVO.setFEE_CDE("F0001");//费用代码是kong
		ReqLoanTrialVO.setRECV_PAY_IND("R");
		ReqLoanTrialVO.setFEE_TYP("06");
		ReqLoanTrialVO.setFEE_CALC_TYP("PCT");
		ReqLoanTrialVO.setFEE_PCT_BASE("01");
		ReqLoanTrialVO.setBASE_AMT(ORIG_PRCP);
		ReqLoanTrialVO.setCHRG_PCT(CHRG_PCT);
		ReqLoanTrialVO.setHOLD_FEE_IND("Y");
		ReqLoanTrialVO.setHOLD_FEE_SETL_DT(bdate);
		ReqLoanTrialVO.setACC_IND("N");
		list.add(ReqLoanTrialVO);
		ReqLoanTrialVO.setLmDnShdMtdTList(list);
		ReqLoanTrialVO.setLmFeeTxTList(list);
		ReqLoanTrialVO.setLmPmShdTList(list);
		return ReqLoanTrialVO;
	}
}
