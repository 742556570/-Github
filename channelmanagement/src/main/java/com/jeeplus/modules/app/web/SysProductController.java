package com.jeeplus.modules.app.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.modules.app.api.loanChannel.SynLoanChannelController;
import com.jeeplus.modules.app.api.loanChannel.request.ReqLoanChannelVO;
import com.jeeplus.modules.app.api.loanChannel.response.RespLoanChannelVO;
import com.jeeplus.modules.app.api.product.SynProductMSGController;
import com.jeeplus.modules.app.api.product.request.ReqProductVO;
import com.jeeplus.modules.app.api.product.response.RespProductVO;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;

/**
 * 同步核算的产品信息和放款渠道信息
 * 
 * @author 阳光保险
 *
 */
@Controller
@RequestMapping(value = "api/sysProduct")
public class SysProductController {

	/**
	 * 同步核算的产品信息
	 * 
	 * @param request
	 * @param response
	 * @param TYP_CDE
	 * @param ACCT_ROLE_DESC
	 * @param OPT_TYP
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSynProductMSG")
	public String getSynProductMSG(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "TYP_CDE") String TYP_CDE,
			@RequestParam(value = "ACCT_ROLE_DESC") String ACCT_ROLE_DESC,
			@RequestParam(value = "OPT_TYP") String OPT_TYP) {
		ReqProductVO reqProductVO = new ReqProductVO();
		SynProductMSGController synProductMSG = new SynProductMSGController();
		reqProductVO.setServiceId("servP0000100012");
		reqProductVO.setTyp_cde(TYP_CDE);
		reqProductVO.setAcct_role_desc(ACCT_ROLE_DESC);
		reqProductVO.setOpt_typ(OPT_TYP);
		System.out.println("产品信息同步请求参数：" + reqProductVO.toString());
		RespProductVO respProductVO = synProductMSG.synProductMSG(reqProductVO);
		System.out.println("产品信息同步返回结果：" + respProductVO.toString());
		if (respProductVO.getErrorCode().equals("00000")) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "同步产品信息成功", null);
		}
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "同步产品信息失败", null);
	}

	/**
	 * 同步核算的产品信息
	 * 
	 * @param request
	 * @param response
	 * @param TYP_CDE
	 * @param ACCT_ROLE_DESC
	 * @param OPT_TYP
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSynLoanChannel")
	public String getSynLoanChannel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "TNR_OPT") int TNR_OPT, @RequestParam(value = "LOAN_BASE_RATE") double LOAN_BASE_RATE,
			@RequestParam(value = "INT_RAT") double INT_RAT, @RequestParam(value = "OD_INT_RATE") double OD_INT_RATE,
			@RequestParam(value = "LAST_CHG_DT") String LAST_CHG_DT, @RequestParam(value = "OPT_TYP") String OPT_TYP) {
		SynLoanChannelController synLoanChannel = new SynLoanChannelController();
		ReqLoanChannelVO ReqLoanBankVO = new ReqLoanChannelVO();
		List<ReqLoanChannelVO> list = new ArrayList<ReqLoanChannelVO>();
		ReqLoanBankVO.setServiceId("servP0000100011");
		ReqLoanBankVO.setDN_CHANNEL("FOTIC");
		ReqLoanBankVO.setTNR_OPT(TNR_OPT);
		ReqLoanBankVO.setLOAN_RATE_MODE("FX");
		ReqLoanBankVO.setLOAN_BASE_RATE(LOAN_BASE_RATE);
		ReqLoanBankVO.setINT_ADJ_PCT("");
		ReqLoanBankVO.setRATE_TYPE("U");
		ReqLoanBankVO.setINT_RAT(INT_RAT);
		ReqLoanBankVO.setOD_INT_RATE(OD_INT_RATE);
		ReqLoanBankVO.setLEND_CHA_NAM("中国对外经济贸易信托有限公司");
		ReqLoanBankVO.setOPT_TYP(OPT_TYP);
		ReqLoanBankVO.setLAST_CHG_USR("admin");
		ReqLoanBankVO.setLAST_CHG_DT(LAST_CHG_DT);
		ReqLoanBankVO.setBEL_TYPE("07");
		ReqLoanBankVO.setTOTTERM_X("Z");
		ReqLoanBankVO.setXQ_RATE("");
		list.add(ReqLoanBankVO);
		ReqLoanBankVO.setPLoanTypMtdList(list);
		RespLoanChannelVO respLoanChannelVO = synLoanChannel.synProductMSG(ReqLoanBankVO);
		if (respLoanChannelVO.getErrorCode().equals("00000")) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "同步放款渠道信息成功", null);
		}
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "同步放款渠道信息失败", null);
	}

}
