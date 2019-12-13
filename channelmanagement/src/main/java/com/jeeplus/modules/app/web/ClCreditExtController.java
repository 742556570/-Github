package com.jeeplus.modules.app.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.api.invalidpolicy.SendInvalidInsOp;
import com.jeeplus.modules.app.api.pm.PMApi;
import com.jeeplus.modules.app.api.rulesengine.RulesEngineApi;
import com.jeeplus.modules.app.api.rulesengine.pojo.DecisionReq;
import com.jeeplus.modules.app.api.sms.SmsCollectionUtil;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClApiInfoDao;
import com.jeeplus.modules.app.dao.ClAppIdGenDao;
import com.jeeplus.modules.app.dao.ClPrdInfoDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClApplyService;
import com.jeeplus.modules.app.service.ClBnkCrdService;
import com.jeeplus.modules.app.service.ClCreditExtService;
import com.jeeplus.modules.app.service.ClDealContractWithPolicyNoService;
import com.jeeplus.modules.app.service.ClFadadaParamsService;
import com.jeeplus.modules.app.service.ClIdCardInfoService;
import com.jeeplus.modules.app.service.ClPmPreCreditService;
import com.jeeplus.modules.app.service.ClUsrCntsService;
import com.jeeplus.modules.app.service.ClUsrContractService;
import com.jeeplus.modules.app.service.ClUsrInfoService;
import com.jeeplus.modules.app.task.UnsyncDealPreCredit;

@Controller
@RequestMapping(value = "api/creditExt")
public class ClCreditExtController {
	private final static Logger logger = LoggerFactory.getLogger(ClCreditExtController.class);
	
	@Autowired
	private ClCreditExtService creditService;
	
	@Autowired
	private ClIdCardInfoService   idCardInfoService;
	
	@Autowired
	private ClBnkCrdService  bnkCrdService;
	
	@Autowired
	private ClUsrCntsService usrCntsService;
	
	@Autowired
	private ClUsrInfoService usrInfoService;
	
	@Autowired
	private ClApplyService applyService;
	
	@Autowired
	private ClPmPreCreditService pmService;
	
	private RulesEngineApi reapi = new RulesEngineApi();

	
	@Autowired
	private ClFadadaParamsService fpService;
	
	@Autowired
	private ClUsrContractService ucService;
	@Autowired
	private ClPrdInfoDao prdDao;
	@Autowired
	private ClUsrDao usrDao;
	@Autowired
	private ClDealContractWithPolicyNoService contractWithPolicyNoSerivce;
	@Autowired
	private ClApiInfoDao apiInfoDao;
	@Autowired
	private ClUsrInfoService usrinfoService;
	
	@ResponseBody
	@RequestMapping(value = "applyCreditExt")
	@Transactional(readOnly = false)
	public String applyCreditExt
	(
			HttpServletRequest request,
			HttpServletResponse response) {
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		
		int crtId = creditService.getCrtId();
		String prepareInsurePolice = AppNormalConstants.policePrefix+crtId+DateUtils.getyMdHms()+AppNormalConstants.prePoliceSuffix;
		
		
		ClCrdtExt crdExt = new ClCrdtExt();
		crdExt.setUSR_APPSEQ(prepareInsurePolice);
		crdExt.setUSR_CDE(clUsr.getUSR_CDE());
		crdExt.setSTATUS("00");
		crdExt.setCRT_DT(DateUtils.getNowFullFmt());
		
		ClCrdtExt x = creditService.getCrdtExt(clUsr.getUSR_CDE());
		
		if(x != null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "已提交授信申请，请勿重复提交", null);
		}
		
		int i = creditService.saveCrdExt(crdExt);
		
		
		String isDealyCreditService = AppCommonConstants.getValStr("IsNeedDealyCreditService");
		if("true".equals(isDealyCreditService)) {
			String DelayCreditServiceStart = AppCommonConstants.getValStr("DelayCreditServiceStart"); 
			String startTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+DelayCreditServiceStart+"00";
			Date startTime = DateUtils.parseDate4ChkFkTime(startTimeStr);
			if(new Date().getTime() - startTime.getTime() >= 0) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交授信成功，授信流程延迟处理。", null);
			}
			
			
			String DelayCreditServiceEnd = AppCommonConstants.getValStr("DelayCreditServiceEnd"); 
			String endTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+DelayCreditServiceEnd+"00";
			Date endTime = DateUtils.parseDate4ChkFkTime(endTimeStr);
			if(new Date().getTime() - endTime.getTime() <= 0) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交授信成功，授信流程延迟处理。", null);
			}
			
			
		}
		
		
		
		if(i > 0) {
			DecisionReq od = new DecisionReq();
			od.setStrategy_code("CL_CP_0004_00000025");
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
			od.setLoan_number(prepareInsurePolice);
			od.setMobile(clUsr.getUSR_TEL());
			od.setTime(DateUtils.getNowFullFmt());
			od.setApp_version(clUsr.getUSR_CLNTVER());
			od.setOs_version(clUsr.getUSR_OS() +" "+clUsr.getUSR_OSVER());
			od.setSdk_version("");
			
			JsonObject cnts = usrCntsService.getUsrCntsJsonObj(clUsr.getUSR_CDE());
			ClBnkCrd bcard = bnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
			if(bcard==null) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请检查默认银行卡状态", null);
			}
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
			obj.put("apply_amount", "0.00");
//			obj.put("applyTime", DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			
			ClUsrInfo uinfo = usrInfoService.getUsrInfo(clUsr.getUSR_CDE());
			
//			obj.put("companyName", uinfo.getINDIV_EMP_NAME());
//			obj.put("companyPhone", uinfo.getINDIV_EMP_TEL());
			od.setOther(obj);
			
			UnsyncDealPreCredit preCredit = new UnsyncDealPreCredit(clUsr, prepareInsurePolice, od);
			preCredit.start();
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交预授信成功", null);
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交预授信失败", null);
		}
		
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "applyCreditExtNew")
	@Transactional(readOnly = false)
	public String applyCreditExtNew
	(
			HttpServletRequest request,
			HttpServletResponse response) {
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		
		int crtId = creditService.getCrtId();
		String prepareInsurePolice = AppNormalConstants.policePrefix+crtId+DateUtils.getyMdHms()+AppNormalConstants.prePoliceSuffix;
		
		
		ClCrdtExt crdExt = new ClCrdtExt();
		crdExt.setUSR_APPSEQ(prepareInsurePolice);
		crdExt.setUSR_CDE(clUsr.getUSR_CDE());
		crdExt.setSTATUS("00");
		crdExt.setCRT_DT(DateUtils.getNowFullFmt());
		
		ClCrdtExt x = creditService.getCrdtExt(clUsr.getUSR_CDE());
		
		if(x != null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "已提交授信申请，请勿重复提交", null);
		}
		
		int i = creditService.saveCrdExt(crdExt);
		
		
		String isDealyCreditService = AppCommonConstants.getValStr("IsNeedDealyCreditService");
		if("true".equals(isDealyCreditService)) {
			String DelayCreditServiceStart = AppCommonConstants.getValStr("DelayCreditServiceStart"); 
			String startTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+DelayCreditServiceStart+"00";
			Date startTime = DateUtils.parseDate4ChkFkTime(startTimeStr);
			if(new Date().getTime() - startTime.getTime() >= 0) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交授信成功，授信流程延迟处理。", null);
			}
			
			
			String DelayCreditServiceEnd = AppCommonConstants.getValStr("DelayCreditServiceEnd"); 
			String endTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+DelayCreditServiceEnd+"00";
			Date endTime = DateUtils.parseDate4ChkFkTime(endTimeStr);
			if(new Date().getTime() - endTime.getTime() <= 0) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交授信成功，授信流程延迟处理。", null);
			}
			
			
		}
		
		
		
		if(i > 0) {
			DecisionReq od = new DecisionReq();
			od.setStrategy_code("CL_CP_0004_00000025");
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
			od.setLoan_number(prepareInsurePolice);
			od.setMobile(clUsr.getUSR_TEL());
			od.setTime(DateUtils.getNowFullFmt());
			od.setApp_version(clUsr.getUSR_CLNTVER());
			od.setOs_version(clUsr.getUSR_OS() +" "+clUsr.getUSR_OSVER());
			od.setSdk_version("");
			
			JsonObject cnts = usrCntsService.getUsrCntsJsonObj(clUsr.getUSR_CDE());
			ClBnkCrd bcard = bnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
			if(bcard==null) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请检查默认银行卡状态", null);
			}
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
			obj.put("apply_amount", "0.00");
//			obj.put("applyTime", DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			ClUsrInfo usrInfo = usrinfoService.getUsrInfo(clUsr.getUSR_CDE());
//			obj.put("companyName", usrInfo.getINDIV_EMP_NAME());
//			obj.put("companyPhone", usrInfo.getINDIV_EMP_TEL());
			od.setOther(obj);
			
			String usrCde = clUsr.getUSR_CDE();
			String aid = usrCde+"_"+prepareInsurePolice;
			
			ClCrdtExt crdtExt = creditService.getClCrdtExtByUsrCde(usrCde);
			boolean flag = true;//标识,为true标识是第一次授信；
			if(crdtExt.getADJ_AMT()  == null) {
				flag = true;
			}else {
				flag = false;
			}
			
			if(!flag) {
				crdtExt.setIS_EFF("false");
				creditService.updateByAppSeq(crdtExt);
			}
	
			String ytb_no = crdtExt.getUSR_APPSEQ();
			ClIdCardInfo idCardInfo2 = idCardInfoService.getByUsrCode(usrCde);
			String id_no = idCardInfo2.getID_NO();
			long pc_start = new Date().getTime();
			JSONObject obj2 = reapi.getLimit(usrCde, id_no);
			long pc_end = new Date().getTime();
			logger.info(aid+",获取白名单额度:"+obj2.toJSONString());
			String time = obj2.getString("time");
			String code = obj2.getString("code");
			String amount = obj2.getString("amount");
			String isWhiteList = obj2.getString("isWhiteList");;
			BigDecimal amt = new BigDecimal(amount);
			
			ClPrdInfo prd = prdDao.getByPrdCde("TQD");
			crdtExt.setPRD_CDE("TQD");
			crdtExt.setADJ_AMT(amt);
			crdtExt.setADJ_AMT_MAX(amt);
			crdtExt.setADJ_AMT_MIN(amt);
			crdtExt.setADJ_RAT(prd.getINT_RAT());
			if(isWhiteList.equals("N")) {
				crdtExt.setCRDTEXT_TYPE("unkown");
				clUsr.setUSR_GRD("unkown");
			}else {
				clUsr.setUSR_GRD("innerWhiteList");
			}
			if(!clUsr.getUSR_GRD().contains("gonganPoor")) {
				usrDao.updateUsrGrd(clUsr);
			}
			String date  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
			net.sf.json.JSONObject paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000005-1", ytb_no,date));
			ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), clUsr.getUSR_TEL(), paramsHt, "YGSDPH80000005-1", "投保单模板（产险-信托）", "1", "签章处", "", clUsr.getUSR_CDE(), "3-2", "","CI05");
			
			paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000006-1", ytb_no,date));
			ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), clUsr.getUSR_TEL(), paramsHt, "YGSDPH80000006-1", "个人征信授权书（阳光）--央行", "1", "签章处", "", clUsr.getUSR_CDE(), "3-3", "","CI06");
	
			paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000007-1", ytb_no,date));
			ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), clUsr.getUSR_TEL(), paramsHt, "YGSDPH80000007-1", "个人信息查询使用授权书", "1", "签章处", "", clUsr.getUSR_CDE(), "3-4", "","CI06");
	
			try {
				contractWithPolicyNoSerivce.dealContractWithPolicyNo(prepareInsurePolice);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			String msg = "";
			//调用决策
			long start = new Date().getTime();
			JSONObject resJson = reapi.applyDecision(od);
			long end = new Date().getTime();
			logger.info(aid+",调用决策授信:"+resJson.toJSONString());
			ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(), clUsr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DECISION_SERVICE, DateUtils.getNowFullFmt(), (int)(end-start), JSONObject.toJSONString(od), "SUCCESS", resJson.toJSONString());
			apiInfoDao.insert(apiInfo);
			System.out.println(resJson);
			
			
			
//			UnsyncDealPreCredit preCredit = new UnsyncDealPreCredit(clUsr, prepareInsurePolice, od);
//			preCredit.start();
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交预授信成功", null);
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交预授信失败", null);
		}
		
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "bcCreditExt")
	@Transactional(readOnly = false)
	public String bcCreditExt(
			@RequestParam(required=true) String policyNo,
			@RequestParam(required=true) String isSms,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String prepareInsurePolice = policyNo;
		
		ClUsr clUsr = creditService.getUsrByPolicyNo(policyNo);
		
		String isDealyCreditService = AppCommonConstants.getValStr("IsNeedDealyCreditService");
		if("true".equals(isDealyCreditService)) {
			String DelayCreditServiceStart = AppCommonConstants.getValStr("DelayCreditServiceStart"); 
			String startTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+DelayCreditServiceStart+"00";
			Date startTime = DateUtils.parseDate4ChkFkTime(startTimeStr);
			if(new Date().getTime() - startTime.getTime() >= 0) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "授信补偿暂不可用，检查配置", null);
			}
			
			
			String DelayCreditServiceEnd = AppCommonConstants.getValStr("DelayCreditServiceEnd"); 
			String endTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+DelayCreditServiceEnd+"00";
			Date endTime = DateUtils.parseDate4ChkFkTime(endTimeStr);
			if(new Date().getTime() - endTime.getTime() <= 0) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "授信补偿暂不可用，检查配置", null);
			}
		}
		
		DecisionReq od = new DecisionReq();
		od.setStrategy_code("CL_CP_0004_00000025");
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
		od.setLoan_number(prepareInsurePolice);
		od.setMobile(clUsr.getUSR_TEL());
		od.setTime(DateUtils.getNowFullFmt());
		od.setApp_version(clUsr.getUSR_CLNTVER());
		od.setOs_version(clUsr.getUSR_OS() +" "+clUsr.getUSR_OSVER());
		od.setSdk_version("");
		
		JsonObject cnts = usrCntsService.getUsrCntsJsonObj(clUsr.getUSR_CDE());
		ClBnkCrd bcard = bnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
		
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
		obj.put("apply_amount", "0.00");
		od.setOther(obj);
		
		try {
			
			pmService.dealCreditCallBack(clUsr);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "预授回调处理偿成功", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "预授回调处理失败", null);
		}
		
		
	}
	
	
	//决策回调
	@ResponseBody
	@RequestMapping(value = "creditCallBack")
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
		JSONObject jobj = JSONObject.parseObject(json);
		String id_no = jobj.getString("idcard_no");
		String isReady = jobj.getString("isReady");
		
		
		if(isReady.equals("true")) {
			ClIdCardInfo idCardInfo = idCardInfoService.getByUsrIdNo(id_no);
			ClUsr usr = usrDao.getByUsrCode(idCardInfo.getUSR_CDE());

			
			
			
			return "success";
		} else {
			return "even not ready";
		}
		
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "queryCreditExt")
	public String queryCreditExt
	(
			HttpServletRequest request,
			HttpServletResponse response) {
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		
		String usrCde = clUsr.getUSR_CDE();
		
		String result = creditService.queryCredit(clUsr);
		
		return result;
		
	}
	
	
	
	

}
