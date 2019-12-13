package com.jeeplus.modules.app.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.loantrial.SynLoanTrial;
import com.jeeplus.modules.app.api.loantrial.request.ReqLoanTrialVO;
import com.jeeplus.modules.app.api.loantrial.response.RespLoanTrialVO;
import com.jeeplus.modules.app.api.rulesengine.RulesEngineApi;
import com.jeeplus.modules.app.api.rulesengine.pojo.DecisionReq;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClApiInfoDao;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClPremiumDetail;
import com.jeeplus.modules.app.entity.ClPsnCHk;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.entity.ClUsrLmtamt;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClApplyService;
import com.jeeplus.modules.app.service.ClBnkCrdService;
import com.jeeplus.modules.app.service.ClCapChannelService;
import com.jeeplus.modules.app.service.ClCheckPassWordService;
import com.jeeplus.modules.app.service.ClCreditExtService;
import com.jeeplus.modules.app.service.ClDealContractWithPolicyNoService;
import com.jeeplus.modules.app.service.ClFadadaParamsService;
import com.jeeplus.modules.app.service.ClIdCardInfoService;
import com.jeeplus.modules.app.service.ClPrdInfoService;
import com.jeeplus.modules.app.service.ClPremiumDetailService;
import com.jeeplus.modules.app.service.ClPsnChkService;
import com.jeeplus.modules.app.service.ClUsrCntsService;
import com.jeeplus.modules.app.service.ClUsrContractService;
import com.jeeplus.modules.app.service.ClUsrInfoService;
import com.jeeplus.modules.app.service.ClUsrLmtamtService;
import com.jeeplus.modules.app.service.ClUsrService;
import com.jeeplus.modules.app.service.SingleApplyLimitService;
import com.jeeplus.modules.app.task.UnsyncDealApply;


@Controller
@RequestMapping(value = "api/apply")
public class ClApplyController {
	
	private final static Logger logger = LoggerFactory.getLogger(ClApplyController.class);
	
	@Autowired
	private ClApiInfoDao apiInfoDao;
	
	@Autowired
	private ClUsrService usrService;
	
	@Autowired
	private ClApplyService applyService;
	
	@Autowired
	private ClCreditExtService crtdService;
	
	@Autowired
	private ClPrdInfoService prdService;
	
	@Autowired
	private ClUsrLmtamtService lmtAmtService;
	
	@Autowired
	private ClUsrInfoService usrInfoService;
	
	@Autowired
	private ClUsrCntsService usrCntsService;
	
	@Autowired
	private ClCapChannelService capChannelService;
	
	@Autowired
	private ClIdCardInfoService   idCardInfoService;
	
	@Autowired
	private ClBnkCrdService  bnkCrdService;
	
	@Autowired
	private ClCheckPassWordService clCheckPassWordService;
	
	@Autowired
	private ClPremiumDetailService premService;
	
	@Autowired
	private ClPsnChkService psnChkService;
	
	private RulesEngineApi reapi = new RulesEngineApi();
	
	
	@Autowired
	private ClFadadaParamsService fpService;
	
	@Autowired
	private ClUsrContractService ucService;
	
	@Autowired
	private ClDealContractWithPolicyNoService contractWithPolicyNoSerivce;
	
	
	@Autowired
	private SingleApplyLimitService singleApplyLimitService;
	
	@ResponseBody
	@RequestMapping(value = "getApplyParam")
	public String getApplyParam(HttpServletRequest request,
			HttpServletResponse response) {
		
		ClUsr usr = (ClUsr)request.getAttribute("usr");
		ClCrdtExt crdt = crtdService.getCrdtExt(usr.getUSR_CDE());
		if(crdt == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成授信", null);
		}
		String prdId = crdt.getPRD_CDE();
		ClPrdInfo prd = prdService.getClPrdInfoByPrdCde(prdId);
		if(prd == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未获取到授信产品信息", null);
		}
		ClUsrLmtamt lmtAmt = lmtAmtService.getByUsrCde(usr.getUSR_CDE());
		if(lmtAmt == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未获取到用户额度信息", null);
		}
		List<ClPremiumDetail> list = premService.getByPrdCde(prdId);
		ClBnkCrd bnkCrd = bnkCrdService.getUsrDefaultBnkCrd(usr.getUSR_CDE());
		
		if(bnkCrd == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "未获取到用户可用银行卡", null);
		}
		BigDecimal crdAmt = crdt.getADJ_AMT();
		BigDecimal prdMinAmt = prd.getMIN_LOAN_AMT();
		BigDecimal prdMaxAmt = prd.getMAX_LOAN_AMT();
		BigDecimal crdRate = crdt.getADJ_RAT();
		BigDecimal prdRate = prd.getINT_RAT();
		BigDecimal prdRateEd = prd.getINT_RAT_ED();
		BigDecimal lmtCrdAmt = lmtAmt.getCreditAmount();
		BigDecimal usedAmt = lmtAmt.getUsedAmount();
		BigDecimal repayAmt = lmtAmt.getRepayAmount();
		
		BigDecimal minAmt = new BigDecimal(prdMinAmt.toString());
		BigDecimal usrAmt = lmtCrdAmt.subtract(usedAmt).add(repayAmt);
		
		
		String loanTrem = prd.getPAY_TERM();
		JSONObject param = new JSONObject();
		param.put("minAmt", minAmt);
		param.put("usrAmt", usrAmt);
		param.put("maxAmt", lmtCrdAmt);
		param.put("prdRate", prdRate);
		param.put("prdRateEd", prdRateEd);
		param.put("loanTrem", loanTrem);
		param.put("prdCde", prdId);
		param.put("bankName", bnkCrd.getAPPL_BANK_NAME());
		param.put("cardNo", bnkCrd.getAPPL_CARD_NO());
		param.put("prems", list);
		
		
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", param);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "loanTrialTest")
	public String loanTrialTest(
			String prdCde,
			String loanAmt,
			String loanTerm,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		
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
		
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		ClPrdInfo prd = prdService.getClPrdInfoByPrdCde(prdCde);
		
		SynLoanTrial synLoanTrial = new SynLoanTrial();
		ReqLoanTrialVO ReqLoanTrialVO = new ReqLoanTrialVO();
		List<ReqLoanTrialVO> list = new ArrayList<ReqLoanTrialVO>();
		ReqLoanTrialVO.setServiceId("serv10000100027");
		ReqLoanTrialVO.setORIG_PRCP(loanAmt);
		ReqLoanTrialVO.setDUE_DAY(DateUtils.formatDate(new Date(), "dd"));
		ReqLoanTrialVO.setLOAN_ACTV_DT(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		ReqLoanTrialVO.setLOAN_TYP("goodPropertyA");
		ReqLoanTrialVO.setINT_START_DT(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		ReqLoanTrialVO.setLOAN_INT_RATE(prd.getINT_RAT().toString());
		ReqLoanTrialVO.setPAYM_FREQ_UNIT("M");
		ReqLoanTrialVO.setPAYM_FREQ_FREQ("1");
		ReqLoanTrialVO.setLOAN_PAYM_TYP("01");
		ReqLoanTrialVO.setINSTM_IND("Y");
		ReqLoanTrialVO.setTNR(loanTerm);
		ReqLoanTrialVO.setCAL_TOT_INSTM("");
		ReqLoanTrialVO.setPS_DUE_DT("");
		ReqLoanTrialVO.setFEE_CDE("F0001");
		ReqLoanTrialVO.setRECV_PAY_IND("R");
		ReqLoanTrialVO.setFEE_TYP("06");
		ReqLoanTrialVO.setFEE_CALC_TYP("PCT");
		ReqLoanTrialVO.setFEE_PCT_BASE("01");
		ReqLoanTrialVO.setBASE_AMT("0");
		ReqLoanTrialVO.setCHRG_PCT("0.0103");
		ReqLoanTrialVO.setHOLD_FEE_IND("Y");
		ReqLoanTrialVO.setHOLD_FEE_SETL_DT(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		ReqLoanTrialVO.setACC_IND("N");
		list.add(ReqLoanTrialVO);
		ReqLoanTrialVO.setLmDnShdMtdTList(list);
		ReqLoanTrialVO.setLmFeeTxTList(list);
		ReqLoanTrialVO.setLmPmShdTList(list);
		
		ReqLoanTrialVO vo = new ReqLoanTrialVO();
		
		RespLoanTrialVO resp = synLoanTrial.synLoanTrial(vo);
		
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resp);
	}
	
	//申请 
	@ResponseBody
	@RequestMapping(value = "loanTrial")
	@Transactional(readOnly = false)
	public String loanTrial(
			String prdCde,
			String loanAmt,
			String loanTerm,
			String useFor,
//			String PASSWORDSIGN,
			HttpServletRequest request,
			HttpServletResponse response) {	
		
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
		

		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		clUsr = usrService.getByUsrCde(clUsr.getUSR_CDE());
		
		if(clUsr.getUSR_GRD().contains("gonganPoor")) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "抱歉，冻结中，无法使用", null);
		}
		
		
			
//		String payPwd = clCheckPassWordService.getPayPwdByUsrCde(clUsr.getUSR_CDE());
//
//		if(StringUtils.isEmpty(payPwd) || !payPwd.equals(PASSWORDSIGN)) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "验证支付密码错误", null);
//		}
		
		
		List<ClPsnCHk> list = psnChkService.getListByUsrCde(clUsr.getUSR_CDE());
		boolean flag = true;
		String nowDateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		for (ClPsnCHk clPsnCHk : list) {
			String clDate = clPsnCHk.getCrtDt();
			if("3".equals(clPsnCHk.getMediaDesc()) && "true".equals(clPsnCHk.getCkinfo()) && clDate.contains(nowDateStr)) {
				flag = false;
			}
		}
		
		
		if(flag) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成活体识别。", null);
		}
		
		
		ClUsrLmtamt limt = lmtAmtService.getByUsrCde(clUsr.getUSR_CDE());
		
		BigDecimal a = limt.getCreditAmount().add(limt.getRepayAmount()).subtract(limt.getUsedAmount());
		if(a.compareTo(new BigDecimal("0")) == -1) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "额度已用尽。", null);
		}
		
		ClPrdInfo prdInfo = prdService.getClPrdInfoByPrdCde(prdCde);
		
		BigDecimal la = new BigDecimal(loanAmt);
		BigDecimal min = prdInfo.getMIN_LOAN_AMT();
		BigDecimal max = prdInfo.getMAX_LOAN_AMT();
		
		if(la.compareTo(min) == -1 ) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请额度小于产品最小申请额度。", null);
		}
		
		if(la.compareTo(max) == 1) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请额度大于产品最大申请额度。", null);
		}
		
		String loanTrnList = prdInfo.getPAY_TERM();
		String[] loanTrns = loanTrnList.split(",");
		boolean trnFlag = false;
		for (String string : loanTrns) {
			if(string.equals(loanTerm)) {
				trnFlag = true;
				break;
			}
		}
		if(trnFlag == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请期数不在产品支持期数列表。", null);
		}
		
		List<ClUsrApply> applies = applyService.getByUsrCde(clUsr.getUSR_CDE());
		for (ClUsrApply apply : applies) {
			if(apply.getStCde().equals("01")){
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请审核中,请稍后再试", null);
			}
			
			if(apply.getStCde().equals("12")){
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请放款中,请稍后再试", null);
			}
			
			boolean isSingleApply = singleApplyLimitService.singleApplyStatus();
			String msg = AppCommonConstants.getValStr("SINGLEAPPLYWORD");
			if (isSingleApply == true) {

				int i = singleApplyLimitService.getSingleApply(clUsr.getUSR_CDE());

				if (i >= 1) {

					result.put("isOn", "true");
					result.put("msg", msg);
					logger.info("单笔限制报文：" + result.toString());
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "成功", result);
				}
			}

		}
		
		
		int applyId = applyService.getCurrId();
		//1 生成保单号
		String insurePolice = AppNormalConstants.policePrefix+applyId+DateUtils.getyMdHms()+AppNormalConstants.policeSuffix;
		
		//获取基础利率，月保费率
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("PRD_CDE", prdCde);
		params.put("PAY_TERM", loanTerm);
		ClPremiumDetail clPreDetail = premService.getByPrdAndTrem(params);
		
		ClBnkCrd bcard = bnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
		
		//2记录申请放款信息 包括保单号
		ClUsrApply usrApply = new ClUsrApply();
		usrApply.setApplCde(insurePolice);
		usrApply.setPolicyNo(insurePolice);
		usrApply.setPrdCde(prdCde);
		usrApply.setApplyAmt(loanAmt);
		usrApply.setApplyTnr(loanTerm);
		usrApply.setCrtDt(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		usrApply.setStCde("01");
		usrApply.setUsrCde(clUsr.getUSR_CDE());
		usrApply.setApplyFor(useFor);
		usrApply.setIntRate(prdInfo.getINT_RAT().toString());
		usrApply.setPremiumRate(clPreDetail.getPREMIUM_RATE().toString());
		usrApply.setBankCardNo(bcard.getAPPL_CARD_NO());
		//3调决策
		
		DecisionReq od = new DecisionReq();
		od.setStrategy_code("CL_CP_0005_00000016");
		od.setApp_key("wd");
		od.setPro_type("xjd");//待定
		od.setPrd_code("tqd");
		od.setToken("");
		od.setOrg_code("");
		od.setBank_channel("FOTIC");
		od.setChannel("wdapp");
		
		od.setSession_id("");
		od.setMessage_id("");
		od.setUser_id(clUsr.getUSR_CDE());
		ClIdCardInfo idCardInfo = idCardInfoService.getByUsrCode(clUsr.getUSR_CDE());
		od.setName(idCardInfo.getCUST_NAME());
		od.setId_no(idCardInfo.getID_NO());
		od.setLoan_number(insurePolice);
		od.setMobile(clUsr.getUSR_TEL());
		od.setTime(DateUtils.getNowFullFmt());
		od.setApp_version(clUsr.getUSR_CLNTVER());
		od.setOs_version(clUsr.getUSR_OS() +" "+clUsr.getUSR_OSVER());
		od.setSdk_version("");
		
		JsonObject cnts = usrCntsService.getUsrCntsJsonObj(clUsr.getUSR_CDE());
		
		
		JSONObject obj = new JSONObject();
		obj.put("idNo", idCardInfo.getID_NO());
		obj.put("idCardType", "0");
		obj.put("name",idCardInfo.getCUST_NAME());
		obj.put("mobile", clUsr.getUSR_TEL());
		obj.put("bankCardNo", bcard.getAPPL_CARD_NO());
		obj.put("contactName", cnts.get("relationName").getAsString());
		obj.put("contactIdNumber", "");
		obj.put("contactMobile", cnts.get("relationPhone").getAsString());
		obj.put("contactRelation", applyService.getRelationCode(cnts.get("relation").getAsString()));
		obj.put("otherContactMobile", cnts.get("otherPhone").getAsString());
		obj.put("apply_amount", loanAmt);
		obj.put("applyTime", usrApply.getCrtDt());
		
		ClUsrInfo uinfo = usrInfoService.getUsrInfo(clUsr.getUSR_CDE());
		
		obj.put("companyName", uinfo.getINDIV_EMP_NAME());
		obj.put("companyPhone", uinfo.getINDIV_EMP_TEL());
		od.setOther(obj);
		
		int i = applyService.addApplyInfo(usrApply);
		if(i > 0) {
			
			int j = lmtAmtService.updateUsedAmt(clUsr.getUSR_CDE(), new BigDecimal(usrApply.getApplyAmt()));
			
			JSONObject loanNo = new JSONObject();
			loanNo.put("loan_number", insurePolice);
			loanNo.put("applyStatus", "01");
			//异步处理具体申请流程
			UnsyncDealApply uda = new UnsyncDealApply(clUsr,insurePolice,od);
			uda.start();
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "审核中", loanNo);
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交失败请重试", null);
		}
		
	}
	
	
	
	
	
	

	//决策回调
	@ResponseBody
	@RequestMapping(value = "derivedDataCallBack")
	public String derivedDataCallBack(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String json = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));

		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		json = sb.toString();
		System.out.println(json);
//		JSONObject jobj = JSONObject.parseObject(json);
//		String id_no = jobj.getString("idcard_no");
//		String isReady = jobj.getString("isReady");
//		
//		
//		if(isReady.equals("true")) {
//			ClIdCardInfo idCardInfo = idCardInfoService.getByUsrIdNo(id_no);
//			ClUsr usr = usrService.getByUsrCde(idCardInfo.getUSR_CDE());
//			List<ClUsrApply> applies = applyService.getByUsrCde(usr.getUSR_CDE());
//			for (ClUsrApply apply : applies) {
//				if("01".equals(apply.getStCde())) {
//					
//					
//					applyService.dealApplyCallBack(usr, apply.getPolicyNo());
//					System.out.println("------------------>done");
//				}
//			}
			return "success";
//		} else {
//			return "even not ready";
//		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "getData4HT")
	public String getData4HT(
			String policyNo,
			String type,
			HttpServletRequest request,
			HttpServletResponse response,Model model) {
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		applyService.hetong(clUsr, policyNo);
		 return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "成功", null);
		
	}
	
	
	
	
	//申请 
		@ResponseBody
		@RequestMapping(value = "loanTrialNew")
		@Transactional(readOnly = false)
		public String loanTrialNew(
				String prdCde,
				String loanAmt,
				String loanTerm,
				String useFor,
//				String PASSWORDSIGN,
				HttpServletRequest request,
				HttpServletResponse response) {	
			
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
			

			ClUsr clUsr = (ClUsr)request.getAttribute("usr");
				
//			String payPwd = clCheckPassWordService.getPayPwdByUsrCde(clUsr.getUSR_CDE());
	//
//			if(StringUtils.isEmpty(payPwd) || !payPwd.equals(PASSWORDSIGN)) {
//				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "验证支付密码错误", null);
//			}
			
			
			List<ClPsnCHk> list = psnChkService.getListByUsrCde(clUsr.getUSR_CDE());
			boolean flag = true;
			String nowDateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
			for (ClPsnCHk clPsnCHk : list) {
				String clDate = clPsnCHk.getCrtDt();
				if("3".equals(clPsnCHk.getMediaDesc()) && "true".equals(clPsnCHk.getCkinfo()) && clDate.contains(nowDateStr)) {
					flag = false;
				}
			}
			
			
			if(flag) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成活体识别。", null);
			}
			
			
			ClUsrLmtamt limt = lmtAmtService.getByUsrCde(clUsr.getUSR_CDE());
			
			BigDecimal a = limt.getCreditAmount().add(limt.getRepayAmount()).subtract(limt.getUsedAmount());
			if(a.doubleValue() <= 0) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "额度已用尽。", null);
			}
			
			ClPrdInfo prdInfo = prdService.getClPrdInfoByPrdCde(prdCde);
			
			BigDecimal la = new BigDecimal(loanAmt);
			BigDecimal min = prdInfo.getMIN_LOAN_AMT();
			BigDecimal max = prdInfo.getMAX_LOAN_AMT();
			
			if(la.subtract(min).doubleValue() < 0 ) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请额度小于产品最小申请额度。", null);
			}
			
			if(la.subtract(max).doubleValue() > 0 ) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请额度大于产品最大申请额度。", null);
			}
			
			String loanTrnList = prdInfo.getPAY_TERM();
			String[] loanTrns = loanTrnList.split(",");
			boolean trnFlag = false;
			for (String string : loanTrns) {
				if(string.equals(loanTerm)) {
					trnFlag = true;
					break;
				}
			}
			if(trnFlag == false) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请期数不在产品支持期数列表。", null);
			}
			
			List<ClUsrApply> applies = applyService.getByUsrCde(clUsr.getUSR_CDE());
			for (ClUsrApply apply : applies) {
				if(apply.getStCde().equals("01")){
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请审核中,请稍后再试", null);
				}
				
				if(apply.getStCde().equals("12")){
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "申请放款中,请稍后再试", null);
				}

//				if(apply.getDebtStatus().equals("30")) {
//					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "贷款未结清,请结清后再试", null);
//				}
			}
			
			
			int applyId = applyService.getCurrId();
			//1 生成保单号
			String insurePolice = AppNormalConstants.policePrefix+applyId+DateUtils.getyMdHms()+AppNormalConstants.policeSuffix;
			
			//获取基础利率，月保费率
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("PRD_CDE", prdCde);
			params.put("PAY_TERM", loanTerm);
			ClPremiumDetail clPreDetail = premService.getByPrdAndTrem(params);
			
			ClBnkCrd bcard = bnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
			
			//2记录申请放款信息 包括保单号
			ClUsrApply usrApply = new ClUsrApply();
			usrApply.setApplCde(insurePolice);
			usrApply.setPolicyNo(insurePolice);
			usrApply.setPrdCde(prdCde);
			usrApply.setApplyAmt(loanAmt);
			usrApply.setApplyTnr(loanTerm);
			usrApply.setCrtDt(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			usrApply.setStCde("01");
			usrApply.setUsrCde(clUsr.getUSR_CDE());
			usrApply.setApplyFor(useFor);
			usrApply.setIntRate(prdInfo.getINT_RAT().toString());
			usrApply.setPremiumRate(clPreDetail.getPREMIUM_RATE().toString());
			usrApply.setBankCardNo(bcard.getAPPL_CARD_NO());
			//3调决策
			
			DecisionReq od = new DecisionReq();
			od.setStrategy_code("CL_CP_0005_00000016");
			od.setApp_key("wd");
			od.setPro_type("xjd");//待定
			od.setPrd_code("tqd");
			od.setToken("");
			od.setOrg_code("");
			od.setBank_channel("FOTIC");
			od.setChannel("wdapp");
			
			od.setSession_id("");
			od.setMessage_id("");
			od.setUser_id(clUsr.getUSR_CDE());
			ClIdCardInfo idCardInfo = idCardInfoService.getByUsrCode(clUsr.getUSR_CDE());
			od.setName(idCardInfo.getCUST_NAME());
			od.setId_no(idCardInfo.getID_NO());
			od.setLoan_number(insurePolice);
			od.setMobile(clUsr.getUSR_TEL());
			od.setTime(DateUtils.getNowFullFmt());
			od.setApp_version(clUsr.getUSR_CLNTVER());
			od.setOs_version(clUsr.getUSR_OS() +" "+clUsr.getUSR_OSVER());
			od.setSdk_version("");
			
			JsonObject cnts = usrCntsService.getUsrCntsJsonObj(clUsr.getUSR_CDE());
			
			
			JSONObject obj = new JSONObject();
			obj.put("idNo", idCardInfo.getID_NO());
			obj.put("idCardType", "0");
			obj.put("name",idCardInfo.getID_NO());
			obj.put("mobile", clUsr.getUSR_TEL());
			obj.put("bankCardNo", bcard.getAPPL_CARD_NO());
			obj.put("contactName", cnts.get("relationName").getAsString());
			obj.put("contactIdNumber", "");
			obj.put("contactMobile", cnts.get("relationPhone").getAsString());
			obj.put("contactRelation", applyService.getRelationCode(cnts.get("relation").getAsString()));
			obj.put("otherContactMobile", cnts.get("otherPhone").getAsString());
			obj.put("apply_amount", loanAmt);
			obj.put("applyTime", usrApply.getCrtDt());
			ClUsrInfo uinfo = usrInfoService.getUsrInfo(clUsr.getUSR_CDE());
			
			obj.put("companyName", uinfo.getINDIV_EMP_NAME());
			obj.put("companyPhone", uinfo.getINDIV_EMP_TEL());
			od.setOther(obj);
			
			int i = applyService.addApplyInfo(usrApply);
			if(i > 0) {
				
				int j = lmtAmtService.updateUsedAmt(clUsr.getUSR_CDE(), new BigDecimal(usrApply.getApplyAmt()));
				
				JSONObject loanNo = new JSONObject();
				loanNo.put("loan_number", insurePolice);
				loanNo.put("applyStatus", "01");
				
				String msg = "";
				String aid = clUsr.getUSR_CDE() + "_" + insurePolice;
				String usrCde = clUsr.getUSR_CDE();
				String id_no = idCardInfo.getID_NO();
				long pc_start = new Date().getTime();
				JSONObject edu = reapi.getLimit(usrCde, id_no);
				long pc_end = new Date().getTime();
				logger.info(aid+",获取白名单额度:"+edu.toJSONString());
				String isWhiteList = edu.getString("isWhiteList");
				
				net.sf.json.JSONObject paramsHt = null;
				String date  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
				paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000006-1", insurePolice,date));
				ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), clUsr.getUSR_TEL(), paramsHt, "YGSDPH80000006-1", "个人征信授权书（阳光）--央行", "1", "签章处", "", clUsr.getUSR_CDE(), "3-3", "","CI06");

				paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000007-1", insurePolice,date));
				ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), clUsr.getUSR_TEL(), paramsHt, "YGSDPH80000007-1", "个人信息查询使用授权书", "1", "签章处", "", clUsr.getUSR_CDE(), "3-4", "","CI06");
				
				paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000009-1", insurePolice,date));
				ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), clUsr.getUSR_TEL(), paramsHt, "YGSDPH80000009-1", "贷款协议（信用类）信托-阳光-附件一个人信息授权书", "1", "签章处", insurePolice, clUsr.getUSR_CDE(), "4-2", "","CI08");

				
				try {
					boolean htRes = contractWithPolicyNoSerivce.dealContractWithPolicyNo(insurePolice);
					if(!htRes) {
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交失败请重试", null);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交失败请重试", null);
				} 
				//调用决策
				long start = new Date().getTime();
				JSONObject resJson = reapi.applyDecision(od);
				long end = new Date().getTime();
				ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(), clUsr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DECISION_SERVICE, DateUtils.getNowFullFmt(), (int)(end-start), JSONObject.toJSONString(od), "SUCCESS", resJson.toJSONString());
				apiInfoDao.insert(apiInfo);
				
				logger.info(aid + ":调用决策参数:"+od.toString());
				logger.info(aid + ":调用决策结果:"+resJson.toJSONString());
				
				
				//异步处理具体申请流程
//				UnsyncDealApply uda = new UnsyncDealApply(clUsr,insurePolice,od);
//				uda.start();
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "审核中", loanNo);
			}else {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交失败请重试", null);
			}
			
		}
}
