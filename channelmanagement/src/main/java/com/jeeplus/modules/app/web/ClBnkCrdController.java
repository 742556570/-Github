package com.jeeplus.modules.app.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.modules.app.id5.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.account.bankcard.BankCardModifyService;
import com.jeeplus.modules.app.api.account.bankcard.request.BankCardRequestBody;
import com.jeeplus.modules.app.api.account.bankcard.request.BankCardRequestRecord;
import com.jeeplus.modules.app.api.account.bankcard.request.BankCardRequestVO;
import com.jeeplus.modules.app.api.account.bankcard.response.BankCardResponseVO;
import com.jeeplus.modules.app.api.account.header.DataHeader;
import com.jeeplus.modules.app.api.account.loanorderquery.LoanOrderQuery;
import com.jeeplus.modules.app.api.fcpp.BankCardOcr;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCardBin;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClIdImg;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.id5.req.Id5Client;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.service.ClApiInfoService;
import com.jeeplus.modules.app.service.ClBnkCrdService;
import com.jeeplus.modules.app.service.ClCardBinService;
import com.jeeplus.modules.app.service.ClCheckPassWordService;
import com.jeeplus.modules.app.service.ClIdCardInfoService;
import com.jeeplus.modules.app.service.ClIdImgService;
import com.jeeplus.modules.app.service.ClUsrApplyService;
import com.jeeplus.modules.app.service.PayChannelBankCardService;
import com.jeeplus.modules.app.utils.CheckNumber;

@Controller
@RequestMapping(value = "api/bnkcrd")
public class ClBnkCrdController {

	@Autowired
	private ClBnkCrdService clBnkCrdService;

	@Autowired
	private ClIdCardInfoService clIdCardInfoService;

	@Autowired
	private ClApiInfoService apiService;

	@Autowired
	private ClIdImgService idImgService;

	@Autowired
	private ClCardBinService clCardBinService;
	
	@Autowired
	private PayChannelBankCardService payChannelBankCardService;

	private Id5Client id5Client = new Id5Client();

	@Autowired
	private ClCheckPassWordService clCheckPassWordService;
	
	@Autowired
	private ClUsrApplyService applyService;
	
	private BankCardOcr bcOcr = new BankCardOcr();
	
	
	private BankCardModifyService bcModifyService = new BankCardModifyService();

	@ResponseBody
	@RequestMapping(value = "sendBnkCrdPhone")
	public String sendBnkCrdPhone(@RequestParam(value = "bankCardPhone") String USR_TEL, HttpServletRequest request,
			HttpServletResponse response) {
		
		boolean isMn = CheckNumber.isMobileNum(USR_TEL);
		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		
		JsonObject resultJson = new JsonObject();
		resultJson.addProperty("tel", USR_TEL);
		int allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD, USR_TEL);
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD,
				USR_TEL);
		if (allCnt >= AppCommonConstants.getValInt("CHKBANKCARDLIMIT")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "发送短信次数已耗尽");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		if (todayCnt >= AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "今天发送短信次数已用尽，请明天再试");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		long start = new Date().getTime();
		int rmdcde = clBnkCrdService.sendBnkCrd(USR_TEL);
		long end = new Date().getTime();
		ClApiInfo apiInfo = new ClApiInfo("",USR_TEL,AppNormalConstants.SMS_API_NAME,AppNormalConstants.SMS_API_VERIFY_BANKCARD,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL,"","");
		if (rmdcde == -1) {
			apiInfo.setSTATUS("FAIL");
			apiInfo.setRESULT("-1");
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "短信发送失败，请重试");
			resultJson.addProperty("allCnt", allCnt + 1);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt + 1);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
		} else {
			apiInfo.setSTATUS("SUCCESS");
			apiInfo.setRESULT(rmdcde+"");
			resultJson.addProperty("result", "true");
			resultJson.addProperty("msg", "短信发送成功");
			resultJson.addProperty("allCnt", allCnt + 1);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt + 1);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
		}
		apiService.addInvokeApiInfo(apiInfo);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
	}

	@ResponseBody
	@RequestMapping(value = "addBnkCrd")
	@Transactional(readOnly = false)
	public String addBnkCrdJsonObj(@RequestParam(value = "bankCardNum") String APPL_CARD_NO,
			@RequestParam(value = "bankCardPhone") String USR_TEL, @RequestParam(value = "msgCode") String MSG_CDE,
			HttpServletRequest request, HttpServletResponse response) {
		
		APPL_CARD_NO = org.apache.commons.lang3.StringUtils.deleteWhitespace(APPL_CARD_NO);
		USR_TEL = org.apache.commons.lang3.StringUtils.deleteWhitespace(USR_TEL);
		
		boolean isMn = CheckNumber.isMobileNum(USR_TEL);
		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		int allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.FOUR_ELEMNET_SERIVE_NAME, clUsr.getUSR_TEL());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.FOUR_ELEMNET_SERIVE_NAME,
				clUsr.getUSR_TEL());
		JsonObject resultJson = new JsonObject();
		
		
		if(!StringUtils.isNumber(APPL_CARD_NO)) {
			resultJson.addProperty("result", "3");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡号含有非数字字符,请重试", resultJson);
		}
	
		

		String cachedSmsCode = clBnkCrdService.getCachedBnkCrdPhone(USR_TEL);
		if (cachedSmsCode == null) {
			resultJson.addProperty("result", "3");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "验证码失效，请重新发送", resultJson);
		} else if (!cachedSmsCode.equals(MSG_CDE)) {
			int i = clBnkCrdService.countFailSmsCde(USR_TEL);
			if(i >= 5) {
				resultJson.addProperty("result", "3");
				resultJson.addProperty("allCnt", allCnt);
				resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
				resultJson.addProperty("todayCnt", todayCnt);
				resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
				clBnkCrdService.rmvCachedBnkCrdPhone(USR_TEL);
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "重复提交错误验证码次数过多，请重新发送验证码再试", resultJson);
			}
			resultJson.addProperty("result", "3");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
			clBnkCrdService.rmvCachedBnkCrdPhone(USR_TEL);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "短信验证码错误，请重新发送", resultJson);
		}
		clBnkCrdService.rmvCachedBnkCrdPhone(USR_TEL);
		ClIdCardInfo idCardInfo = clIdCardInfoService.getByUsrCodeAndValidate(clUsr.getUSR_CDE());
		if (idCardInfo == null) {
			resultJson.addProperty("result", "3");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成身份证ocr识别", resultJson);
		}
		
//		if(clBnkCrdService.isUsrExists(clUsr.getUSR_CDE())) {
//			resultJson.addProperty("result", "3");
//			resultJson.addProperty("allCnt", allCnt);
//			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
//			resultJson.addProperty("todayCnt", todayCnt);
//			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "用户已绑定银行卡", resultJson);
//		}
		
//		if(clBnkCrdService.isUsrBnkCrdExists(clUsr.getUSR_CDE(),APPL_CARD_NO)) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡号已存在", null);
//		}
		
		ClBnkCrd target = clBnkCrdService.getOneByCardNo(APPL_CARD_NO);
		if(target != null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡号已存在", resultJson);
		}
		
		
		if (allCnt >= AppCommonConstants.getValInt("FOURCHKLIMIT")) {
			resultJson.addProperty("result", "3");
			resultJson.addProperty("msg", "银行卡认证次数已耗尽");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		if (todayCnt >= AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY")) {
			resultJson.addProperty("result", "3");
			resultJson.addProperty("msg", "今天银行卡认证次数已用尽，请明天再试");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		String tmpNum = APPL_CARD_NO.replaceAll("\\s*", "");
		String tmp1 = tmpNum.substring(0, 6);
		ClCardBin cardBin = clCardBinService.getByClCardBinIdObj(tmp1);
		if (cardBin == null) {
			tmp1 = tmpNum.substring(0, 9);
			cardBin = clCardBinService.getByClCardBinIdObj(tmp1);
			if (cardBin == null) {
				resultJson.addProperty("result", "3");
				resultJson.addProperty("allCnt", allCnt);
				resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
				resultJson.addProperty("todayCnt", todayCnt);
				resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "获取银行卡信息错误,请确认后再次提交", resultJson);
			}

		}

		if (cardBin.getCardbank() == null || !isAllowBank(cardBin.getCardbank())) {
			resultJson.addProperty("result", "3");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", resultJson);
		}

		if(tmpNum.startsWith("622309") && tmpNum.length() == 19) {
			resultJson.addProperty("result", "3");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", resultJson);
		}
		//检查支持银行卡列表
		if (cardBin.getCardbank() == null || !isAllowBank(cardBin.getCardbank())) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", null);
		}
		String param = idCardInfo.getCUST_NAME() + "," + tmpNum + "," + idCardInfo.getID_NO() + "," + USR_TEL;
		long start = new Date().getTime();
		
		String isOnline = Global.getConfig("isOnline");
		String baffleFlg = "N";
		if("true".equals(isOnline)) {
			baffleFlg = "N";
		}else if ("false".equals(isOnline)) {
			baffleFlg = "Y";
		}else {
			baffleFlg = "N";
		}
		
		String code = id5Client.buildRequest(param, baffleFlg);
		long end = new Date().getTime();
		ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.FOUR_ELEMNET_CHK,AppNormalConstants.FOUR_ELEMNET_SERIVE_NAME,DateUtils.getNowFullFmt(),(int)(end-start),param,"","");
		if (!code.equals("7")) {
			apiInfo.setSTATUS("FAIL");
			apiInfo.setRESULT(code);
			apiService.addInvokeApiInfo(apiInfo);
			resultJson.addProperty("result", "2");
			resultJson.addProperty("msg", "银行卡验证错误,请确认后再次提交");
			resultJson.addProperty("allCnt", allCnt+1);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt+1);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "银行卡验证错误,请确认后再次提交", resultJson);
		}else {
			apiInfo.setSTATUS("SUCCESS");
			apiInfo.setRESULT(code);
			apiService.addInvokeApiInfo(apiInfo);
		}
		
		ClBnkCrd usrDefault = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
		
		ClBnkCrd bnkCrd = new ClBnkCrd();
		if(usrDefault == null) {//
			bnkCrd.setUSR_CDE(clUsr.getUSR_CDE());
			bnkCrd.setAPPL_BANK_NAME(cardBin.getCardbank());
			bnkCrd.setBNK_CODE(cardBin.getCardabb());
			bnkCrd.setAPPL_AC_BANK(cardBin.getCardbank());
			bnkCrd.setET_BNK_ADDR("");
			bnkCrd.setCARDTYPE(cardBin.getCardtype() + "");
			bnkCrd.setAPPL_CARD_NO(tmpNum);
			bnkCrd.setEPR_DT("");
			bnkCrd.setSGN_CDE("");
			bnkCrd.setAPPL_AC_NAM(idCardInfo.getCUST_NAME());
			bnkCrd.setAPPL_ID_NO(idCardInfo.getID_NO());
			bnkCrd.setUSR_TEL(USR_TEL);
			bnkCrd.setSTATUS("1");
			bnkCrd.setIS_DEF("1");
			bnkCrd.setIS_DEL("0");
			bnkCrd.setWGHT(-1);
			bnkCrd.setIS_BIND("0");
			bnkCrd.setBIND_DT(DateUtils.getNowFullFmt());
			bnkCrd.setBIND_IP("");
			bnkCrd.setBIND_CDE("");
			bnkCrd.setIS_BINDFOTIC("-1");
			bnkCrd.setMDF_DT(null);
		}else {
			bnkCrd.setUSR_CDE(clUsr.getUSR_CDE());
			bnkCrd.setAPPL_BANK_NAME(cardBin.getCardbank());
			bnkCrd.setBNK_CODE(cardBin.getCardabb());
			bnkCrd.setAPPL_AC_BANK(cardBin.getCardbank());
			bnkCrd.setET_BNK_ADDR("");
			bnkCrd.setCARDTYPE(cardBin.getCardtype() + "");
			bnkCrd.setAPPL_CARD_NO(tmpNum);
			bnkCrd.setEPR_DT("");
			bnkCrd.setSGN_CDE("");
			bnkCrd.setAPPL_AC_NAM(idCardInfo.getCUST_NAME());
			bnkCrd.setAPPL_ID_NO(idCardInfo.getID_NO());
			bnkCrd.setUSR_TEL(USR_TEL);
			bnkCrd.setSTATUS("1");
			bnkCrd.setIS_DEF("0");
			bnkCrd.setIS_DEL("0");
			bnkCrd.setWGHT(0);
			bnkCrd.setIS_BIND("0");
			bnkCrd.setBIND_DT(DateUtils.getNowFullFmt());
			bnkCrd.setBIND_IP("");
			bnkCrd.setBIND_CDE("");
			bnkCrd.setIS_BINDFOTIC("-1");
			bnkCrd.setMDF_DT(null);
		}
		
		boolean result = clBnkCrdService.addBnkCrdBoolean(bnkCrd);

		if( result) {
			resultJson.addProperty("result", "1");
			resultJson.addProperty("msg", "保存成功");
			resultJson.addProperty("allCnt", allCnt+1);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt+1);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "保存成功", resultJson);
		} else {
			resultJson.addProperty("result", "1");
			resultJson.addProperty("msg", "保存失败");
			resultJson.addProperty("allCnt", allCnt+1);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt+1);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "保存失败", resultJson);
		}

	}

	@ResponseBody
	@RequestMapping(value = "getBnkCrds")
	public String getUsrInfo(HttpServletRequest request, HttpServletResponse response) {
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");

		String str = clBnkCrdService.getUsrBnkCrdJsonObj(clUsr.getUSR_CDE());

		return str;
	}

	@ResponseBody
	@RequestMapping(value = "getBnkCrdByCrdNum")
	public String getUsrInfo(@RequestParam(value = "bankCardNum") String APPL_CARD_NO, HttpServletRequest request,
			HttpServletResponse response) {
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");

		String str = clBnkCrdService.getUsrBnkCrd(clUsr.getUSR_CDE(), APPL_CARD_NO);

		return str;
	}

	@ResponseBody
	@RequestMapping(value = "bankCardOcr")
	@Transactional(readOnly = false)
	public String bankCardOcr(@RequestParam MultipartFile file1, HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {

		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		String usrCde = clUsr.getUSR_CDE();

		int allCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.BOCR_API_SERVICE_NAME, usrCde);
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrCode(AppNormalConstants.BOCR_API_SERVICE_NAME,
				usrCde);
		if (allCnt >= AppCommonConstants.getValInt("BNKCRDOCRLIMIT")) {
			JsonObject responseData = new JsonObject();
			responseData.addProperty("allCnt", allCnt);
			responseData.addProperty("allCntLimit", AppCommonConstants.getValInt("BNKCRDOCRLIMIT"));
			responseData.addProperty("todayCnt", todayCnt);
			responseData.addProperty("edCntLimit", AppCommonConstants.getValInt("BNKCRDOCRLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERALLCNT, "您的识别次数已用完", responseData);
		}
		if (todayCnt >= AppCommonConstants.getValInt("BNKCRDOCRLIMITEACHDAY")) {
			JsonObject responseData = new JsonObject();
			responseData.addProperty("allCnt", allCnt);
			responseData.addProperty("allCntLimit", AppCommonConstants.getValInt("BNKCRDOCRLIMIT"));
			responseData.addProperty("todayCnt", todayCnt);
			responseData.addProperty("edCntLimit", AppCommonConstants.getValInt("BNKCRDOCRLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.OVERDAYCNT, "您今天的识别数已用完,请明天再试", responseData);
		}
		String trd_no = UUID.randomUUID().toString().replace("-", "");
		File localfile = new File(
				AppCommonConstants.getValStr("Local_FilePath") + "/bankcard/" + clUsr.getUSR_TEL() + "/"+trd_no+"_bankCard.jpg");
		localfile.mkdirs();
		file1.transferTo(localfile);

		
		int a = idImgService.addIdImg(trd_no, usrCde, localfile.getAbsolutePath(), "bankcard",
				DateUtils.getNowFullFmt());

		if (a > 0) {
			long start = new Date().getTime();
			JsonObject json = bcOcr.bankCardOcr(localfile.getAbsolutePath());
			long end = new Date().getTime();
			JsonElement je = json.get("number");
			if(je != null) {
				String tmpNum = json.get("number").getAsString();
				tmpNum = StringUtils.delBlank(tmpNum);
				ClIdImg idImg = new ClIdImg();
				idImg.setIMG_TRDNO(trd_no);
				idImg.setIMG_FEATURE(tmpNum);
				idImgService.update4FeatureByTrdno(idImg);
			}
			JsonObject responseData = new JsonObject();
			responseData.addProperty("trdNo", trd_no);
			responseData.addProperty("allCnt", allCnt + 1);
			responseData.addProperty("allCntLimit", AppCommonConstants.getValInt("BNKCRDOCRLIMIT"));
			responseData.addProperty("todayCnt", todayCnt + 1);
			responseData.addProperty("edCntLimit", AppCommonConstants.getValInt("BNKCRDOCRLIMITEACHDAY"));
			responseData.add("bankcard", json);
			ClApiInfo apiInfo = new ClApiInfo(usrCde, clUsr.getUSR_TEL(), AppNormalConstants.OCR_API_NAME,
					AppNormalConstants.BOCR_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (end - start), trd_no,
					"SUCCESS", responseData.toString());
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", responseData);
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "图片存储失败", null);
		}
	}

	private boolean isAllowBank(String inName) {
		String allowBanksStr = AppCommonConstants.getValStr("ALLOWBANKS");
		String[] bankNames = allowBanksStr.split(",");
		for (String name : bankNames) {
			if (name.contains(inName) || inName.contains(name)) {
				return true;
			}
		}
		return false;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "getBnkCrdNumByUsr")
	public String getBnkCrdNumByUsr(
			@RequestParam(required = false,value = "bankCardNum") String bankCardNum,
			@RequestParam(required = false,value = "isOldCard") String isOldCard,
			@RequestParam(required = false,value = "mobile") String mobile,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		ClBnkCrd bnkcrd = null;
		if("true".equals(isOldCard)) {
			bnkcrd = clBnkCrdService.getUsrOldCardByUsrCode(clUsr.getUSR_CDE());
		}else {
			if(StringUtils.isEmpty(bankCardNum)) {
				bnkcrd = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
			} else {
				bnkcrd = clBnkCrdService.getBnkCrdByUsrAndCrdNum(clUsr.getUSR_CDE(), bankCardNum);
			}
		}
		String bnkNo = "";
		String tel = "";
		String bindStep = "-1";
		String bankName = "";
		String bankCode = "";
		
		if(bnkcrd != null) {
			bnkNo = bnkcrd.getAPPL_CARD_NO();
			tel = bnkcrd.getUSR_TEL();
			String isBindFotic = bnkcrd.getIS_BINDFOTIC();
			String isOldBind = bnkcrd.getIS_BIND();
			if("0".equals(isOldBind) && "2".equals(isBindFotic)) {
				bindStep = "2";
			}else if("0".equals(isOldBind) && !"2".equals(isBindFotic)) {
				bindStep = "0";
			}else if(!"0".equals(isOldBind) && "2".equals(isBindFotic)) {
				bindStep = "2";
			}else {
				bindStep = "-1"; 
			}
			
			bankName = bnkcrd.getAPPL_BANK_NAME();
			bankCode = bnkcrd.getBNK_CODE();
		}else {
			bnkcrd = clBnkCrdService.getUsrDefaultBnkCrdWithOutSts(clUsr.getUSR_CDE());
			if(bnkcrd != null) {
				bnkNo = bnkcrd.getAPPL_CARD_NO();
				tel = bnkcrd.getUSR_TEL();
				String isBindFotic = bnkcrd.getIS_BINDFOTIC();
				String isOldBind = bnkcrd.getIS_BIND();
				if("0".equals(isOldBind) && "2".equals(isBindFotic)) {
					bindStep = "2";
				}else if("0".equals(isOldBind) && !"2".equals(isBindFotic)) {
					bindStep = "0";
				}else if(!"0".equals(isOldBind) && "2".equals(isBindFotic)) {
					bindStep = "2";
				}else {
					bindStep = "-1"; 
				}
				bankName = bnkcrd.getAPPL_BANK_NAME();
				bankCode = bnkcrd.getBNK_CODE();
			}
		}
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isNotEmpty(mobile)) {
			if(mobile.equals(tel)) {
				boolean isMn = CheckNumber.isMobileNum(mobile);
				if(isMn == false) {
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
				}
				jsonObject.put("bnkNo", bnkNo);
				jsonObject.put("tel", tel);
				jsonObject.put("bindStep", bindStep);
				jsonObject.put("bankName", bankName);
				jsonObject.put("bankCode", bankCode);
			} else {
				jsonObject.put("bnkNo", "");
				jsonObject.put("tel", "");
				jsonObject.put("bindStep", "-1");
				jsonObject.put("bankName", "");
				jsonObject.put("bankCode", "");
			}
		}else {
			jsonObject.put("bnkNo", bnkNo);
			jsonObject.put("tel", tel);
			jsonObject.put("bindStep", bindStep);
			jsonObject.put("bankName", bankName);
			jsonObject.put("bankCode", bankCode);
		}
		

		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", jsonObject);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "getWFCCBBnkCrdNumByUsr")
	public String getWFCCBBnkCrdNumByUsr(
			@RequestParam(required = false,value = "bankCardNum") String bankCardNum,
			@RequestParam(required = false,value = "isOldCard") String isOldCard,
			@RequestParam(required = false,value = "mobile") String mobile,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		ClBnkCrd bnkcrd = null;
		if("true".equals(isOldCard)) {
			bnkcrd = clBnkCrdService.getUsrOldCardByUsrCode(clUsr.getUSR_CDE());
		}else {
			if(StringUtils.isEmpty(bankCardNum)) {
				bnkcrd = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
			} else {
				bnkcrd = clBnkCrdService.getBnkCrdByUsrAndCrdNum(clUsr.getUSR_CDE(), bankCardNum);
			}
		}
		String bnkNo = "";
		String tel = "";
		String bindStep = "-1";
		String bankName = "";
		String bankCode = "";
		
		if(bnkcrd != null) {
			bnkNo = bnkcrd.getAPPL_CARD_NO();
			tel = bnkcrd.getUSR_TEL();
			String isBindFotic = bnkcrd.getIS_BINDFOTIC();
			String isOldBind = bnkcrd.getIS_BIND();
			if("0".equals(isOldBind) && "2".equals(isBindFotic)) {
				bindStep = "2";
			}else if("0".equals(isOldBind) && !"2".equals(isBindFotic)) {
				bindStep = "0";
			}else if(!"0".equals(isOldBind) && "2".equals(isBindFotic)) {
				bindStep = "2";
			}else {
				bindStep = "-1"; 
			}
			
			bankName = bnkcrd.getAPPL_BANK_NAME();
			bankCode = bnkcrd.getBNK_CODE();
		}else {
			bnkcrd = clBnkCrdService.getUsrDefaultBnkCrdWithOutSts(clUsr.getUSR_CDE());
			if(bnkcrd != null) {
				bnkNo = bnkcrd.getAPPL_CARD_NO();
				tel = bnkcrd.getUSR_TEL();
				String isBindFotic = bnkcrd.getIS_BINDFOTIC();
				String isOldBind = bnkcrd.getIS_BIND();
				if("0".equals(isOldBind) && "2".equals(isBindFotic)) {
					bindStep = "2";
				}else if("0".equals(isOldBind) && !"2".equals(isBindFotic)) {
					bindStep = "0";
				}else if(!"0".equals(isOldBind) && "2".equals(isBindFotic)) {
					bindStep = "2";
				}else {
					bindStep = "-1"; 
				}
				bankName = bnkcrd.getAPPL_BANK_NAME();
				bankCode = bnkcrd.getBNK_CODE();
			}
		}
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isNotEmpty(mobile)) {
			if(mobile.equals(tel)) {
				boolean isMn = CheckNumber.isMobileNum(mobile);
				if(isMn == false) {
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
				}
				jsonObject.put("bnkNo", bnkNo);
				jsonObject.put("tel", tel);
				jsonObject.put("bindStep", bindStep);
				jsonObject.put("bankName", bankName);
				jsonObject.put("bankCode", bankCode);
			} else {
				jsonObject.put("bnkNo", "");
				jsonObject.put("tel", "");
				jsonObject.put("bindStep", "-1");
				jsonObject.put("bankName", "");
				jsonObject.put("bankCode", "");
			}
		}else {
			jsonObject.put("bnkNo", bnkNo);
			jsonObject.put("tel", tel);
			jsonObject.put("bindStep", bindStep);
			jsonObject.put("bankName", bankName);
			jsonObject.put("bankCode", bankCode);
		}
		

		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", jsonObject);
	}
	
	@ResponseBody
	@RequestMapping(value = "preBindCard")
	public String preBindCard(
			@RequestParam(value = "bankCardNum") String APPL_CARD_NO,
			@RequestParam(value = "bankCardPhone") String USR_TEL,
			@RequestParam(value = "channel") String channel,
			@RequestParam(required=false,value = "isOldCard") String isOldCard,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		boolean isMn = CheckNumber.isMobileNum(USR_TEL);
		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		JsonObject resultJson = new JsonObject();
		resultJson.addProperty("bankCardNum", APPL_CARD_NO);
		resultJson.addProperty("bankCardPhone", USR_TEL);
		resultJson.addProperty("channel", channel);
		resultJson.addProperty("tel", USR_TEL);
		int allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD, clUsr.getUSR_TEL());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD,
				clUsr.getUSR_TEL());
		resultJson.addProperty("todayCnt", todayCnt+1);
		resultJson.addProperty("allCnt", allCnt+1);
		if (allCnt >= AppCommonConstants.getValInt("CHKBANKCARDLIMIT")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "发送短信次数已耗尽");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		if (todayCnt >= AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "今天发送短信次数已用尽，请明天再试");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		
		
		ClIdCardInfo idCardInfo = clIdCardInfoService.getByUsrCodeAndValidate(clUsr.getUSR_CDE());
		if (idCardInfo == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成身份证ocr识别", null);
		}
		
//		if(clBnkCrdService.isUsrExists(clUsr.getUSR_CDE())) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "用户已绑定银行卡", null);
//		}
		
		ClBnkCrd target = clBnkCrdService.getOneByBankNoAndBinded4FoticBind(APPL_CARD_NO);
		if(target != null && target.getIS_BIND().equals("2")) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡号已存在", resultJson);
		} 
		
		target = clBnkCrdService.getOneByBankNoWithOutSts(APPL_CARD_NO);
		if(target != null && target.getUSR_TEL().equals(USR_TEL) && target.getIS_BIND().equals("-1")) {
			clBnkCrdService.deleteByCradNo(target);
		}
		
//		if(!"true".equals(isOldCard)) {
//			target.setIS_DEL("1");
//			clBnkCrdService.updateBySEQSelective(target);
//		}
		
		String tmpNum = APPL_CARD_NO.replaceAll("\\s*", "");
		String tmp1 = tmpNum.substring(0, 6);
		ClCardBin cardBin = clCardBinService.getByClCardBinIdObj(tmp1);
		if (cardBin == null) {
			tmp1 = tmpNum.substring(0, 9);
			cardBin = clCardBinService.getByClCardBinIdObj(tmp1);
			if (cardBin == null) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "获取银行卡信息错误,请确认后再次提交", null);
			}

		}

		if (cardBin.getCardbank() == null || !isAllowBank(cardBin.getCardbank())) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", null);
		}
		
		if(channel.equals("1")) {
			channel = "CL0001";
		} else if (channel.equals("2")) {
			channel = "CL0002";
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "支付通道错误", null);
		}
		
		ClBnkCrd usrDefault = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
		String defaultStatus = "0";
		if(usrDefault == null) {
			defaultStatus = "1";
			List<ClBnkCrd> bnks = clBnkCrdService.getUsrAllBankCard(clUsr.getUSR_CDE());
			for (ClBnkCrd bnk : bnks) {
				bnk.setIS_DEF("0");
				clBnkCrdService.updateBySEQSelective(bnk);
			}
		}else {
			defaultStatus = "0";
		}
		
		ClBnkCrd bnkCrd = clBnkCrdService.getBnkCrdByUsrAndCrdNum(clUsr.getUSR_CDE(), APPL_CARD_NO);
		if(bnkCrd == null) {
			bnkCrd = new ClBnkCrd();
			bnkCrd.setUSR_CDE(clUsr.getUSR_CDE());
			bnkCrd.setAPPL_BANK_NAME(cardBin.getCardbank());
			bnkCrd.setBNK_CODE(cardBin.getCardabb());
			bnkCrd.setAPPL_AC_BANK(cardBin.getCardbank());
			bnkCrd.setET_BNK_ADDR("");
			bnkCrd.setCARDTYPE(cardBin.getCardtype() + "");
			bnkCrd.setAPPL_CARD_NO(tmpNum);
			bnkCrd.setEPR_DT("");
			bnkCrd.setSGN_CDE("");
			bnkCrd.setAPPL_AC_NAM(idCardInfo.getCUST_NAME());
			bnkCrd.setAPPL_ID_NO(idCardInfo.getID_NO());
			bnkCrd.setUSR_TEL(USR_TEL);
			bnkCrd.setSTATUS("1");
			bnkCrd.setIS_DEF(defaultStatus);
			bnkCrd.setIS_DEL("0");
			bnkCrd.setWGHT(0);
			bnkCrd.setIS_BIND("-1");
			bnkCrd.setBIND_DT(DateUtils.getNowFullFmt());
			bnkCrd.setBIND_IP("");
			bnkCrd.setBIND_CDE("");
			bnkCrd.setMDF_DT(null);
		}else {
			bnkCrd.setIS_DEF(defaultStatus);
		}
		long start = new Date().getTime();
		net.sf.json.JSONObject result = payChannelBankCardService.payChannelBankCard(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, channel,bnkCrd);
		long end = new Date().getTime();
		ClApiInfo apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.ZW_BINDCARD,AppNormalConstants.SMS_API_VERIFY_BANKCARD,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+channel+","+idCardInfo.getCUST_NAME()+","+idCardInfo.getID_NO(),"",result.toString());
		resultJson.addProperty("allCnt", allCnt + 1);
		resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
		resultJson.addProperty("todayCnt", todayCnt + 1);
		resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
		resultJson.addProperty("detail", result.getJSONObject("detail").toString());
		if(result.getString("result").equals("success")) {
			apiInfo.setSTATUS("SUCCESS");
			apiInfo.setSERVICE_NAME(AppNormalConstants.ZW_BINDCARD);
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交签约申请成功", resultJson);
		}
		apiInfo.setSTATUS("FAIL");
		apiService.addInvokeApiInfo(apiInfo);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交签约申请失败", resultJson);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "reSend")
	public String reSend(
			@RequestParam(value = "bankCardNum") String APPL_CARD_NO,
			@RequestParam(value = "bankCardPhone") String USR_TEL,
			@RequestParam(value = "channel") String channel,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		boolean isMn = CheckNumber.isMobileNum(USR_TEL);
		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		
		JsonObject resultJson = new JsonObject();
		resultJson.addProperty("bankCardNum", APPL_CARD_NO);
		resultJson.addProperty("bankCardPhone", USR_TEL);
		resultJson.addProperty("channel", channel);
		resultJson.addProperty("tel", USR_TEL);
		int allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD, clUsr.getUSR_TEL());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD,
				clUsr.getUSR_TEL());
		if (allCnt >= AppCommonConstants.getValInt("CHKBANKCARDLIMIT")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "发送短信次数已耗尽");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		if (todayCnt >= AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "今天发送短信次数已用尽，请明天再试");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		
		ClBnkCrd bnk = clBnkCrdService.getOneByBankNoWithOutSts(APPL_CARD_NO);
		if(bnk == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "支付通道错误", null);
		}
		
		
		if(channel.equals("1")) {
			channel = "CL0001";
		} else if (channel.equals("2")) {
			channel = "CL0002";
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "支付通道错误", null);
		}
		
		long start = new Date().getTime();
		net.sf.json.JSONObject result = payChannelBankCardService.payChannelBankCardSign(APPL_CARD_NO, channel);
		long end = new Date().getTime();
		ClApiInfo apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.ZW_BINDCARD,AppNormalConstants.SMS_API_VERIFY_BANKCARD,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+channel,"",result.toString());
		resultJson.addProperty("allCnt", allCnt + 1);
		resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
		resultJson.addProperty("todayCnt", todayCnt + 1);
		resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
		resultJson.addProperty("detail", result.getJSONObject("detail").toString());
		if(result.getString("result").equals("success")) {
			apiInfo.setSTATUS("SUCCESS");
			apiInfo.setSERVICE_NAME(AppNormalConstants.ZW_BINDCARD);
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "重发短信申请成功", resultJson);
		}
		apiInfo.setSTATUS("FAIL");
		apiService.addInvokeApiInfo(apiInfo);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "重发短信申请失败", resultJson);
	}

	
	@ResponseBody
	@RequestMapping(value = "bindCard")
	public String bindCard(
			@RequestParam(value = "bankCardNum") String APPL_CARD_NO,
			@RequestParam(value = "bankCardPhone") String USR_TEL,
			@RequestParam(value = "smsCode") String smsCode,
			@RequestParam(value = "channel") String channel,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		
		boolean isMn = CheckNumber.isMobileNum(USR_TEL);
		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		
		JsonObject resultJson = new JsonObject();
		
		resultJson.addProperty("bankCardNum", APPL_CARD_NO);
		resultJson.addProperty("bankCardPhone", USR_TEL);
		resultJson.addProperty("channel", channel);
		resultJson.addProperty("tel", USR_TEL);
		
		int allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD, clUsr.getUSR_TEL());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD,
				clUsr.getUSR_TEL());
	
		resultJson.addProperty("todayCnt", todayCnt);
		resultJson.addProperty("allCnt", allCnt);
		if (allCnt >= AppCommonConstants.getValInt("CHKBANKCARDLIMIT")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "发送短信次数已耗尽");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		if (todayCnt >= AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "今天发送短信次数已用尽，请明天再试");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		
		
		if(channel.equals("1")) {
			channel = "CL0001";
		} else if (channel.equals("2")) {
			channel = "CL0002";
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "支付通道错误", null);
		}
		
		long start = new Date().getTime();
		net.sf.json.JSONObject result = payChannelBankCardService.payChannelBankCardSign(smsCode, APPL_CARD_NO, channel);
		long end = new Date().getTime();
		ClApiInfo apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.ZW_BINDCARD,AppNormalConstants.SMS_API_VERIFY_BANKCARD,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+channel,"",result.toString());
		resultJson.addProperty("allCnt", allCnt + 1);
		resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
		resultJson.addProperty("todayCnt", todayCnt + 1);
		resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
		resultJson.addProperty("detail", result.getJSONObject("detail").toString());
		if(result.getString("result").equals("success")) {
			
			apiInfo.setSTATUS("SUCCESS");
			apiInfo.setSERVICE_NAME(AppNormalConstants.ZW_BINDCARD);
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "确认签约申请成功", resultJson);
		}
		apiInfo.setSTATUS("FAIL");
		apiService.addInvokeApiInfo(apiInfo);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "确认签约申请失败", resultJson);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "queryBind")
	public String queryBind(
			@RequestParam(value = "bankCardNum") String APPL_CARD_NO,
			@RequestParam(value = "bankCardPhone") String USR_TEL,
			@RequestParam(value = "channel") String channel,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		boolean isMn = CheckNumber.isMobileNum(USR_TEL);
		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		
		JSONObject cardInfo = new JSONObject();
		cardInfo.put("bankCardNum", APPL_CARD_NO);
		cardInfo.put("bankCardPhone", USR_TEL);
		cardInfo.put("channel", channel);
		cardInfo.put("isBind", "false");
		if(channel.equals("1")) {
			channel = "CL0001";
		} else if (channel.equals("2")) {
			channel = "CL0002";
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "支付通道错误", null);
		}
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		ClIdCardInfo idCardInfo = clIdCardInfoService.getByUsrCodeAndValidate(clUsr.getUSR_CDE());
//		ClBnkCrd target = clBnkCrdService.getOneByBankNoAndBinded4FoticBind(APPL_CARD_NO);
//		if(target != null && target.getIS_BIND().equals("2")) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡号已存在", cardInfo);
//		} 
		
		ClBnkCrd target = clBnkCrdService.getOneByBankNoWithOutSts(APPL_CARD_NO);
		if(target != null && target.getUSR_TEL().equals(USR_TEL) && target.getIS_BIND().equals("-1")) {
			clBnkCrdService.deleteByCradNo(target);
		}
		
//		if(!"true".equals(isOldCard)) {
//			target.setIS_DEL("1");
//			clBnkCrdService.updateBySEQSelective(target);
//		}
		
		String tmpNum = APPL_CARD_NO.replaceAll("\\s*", "");
		String tmp1 = tmpNum.substring(0, 6);
		ClCardBin cardBin = clCardBinService.getByClCardBinIdObj(tmp1);
		if (cardBin == null) {
			tmp1 = tmpNum.substring(0, 9);
			cardBin = clCardBinService.getByClCardBinIdObj(tmp1);
			if (cardBin == null) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "获取银行卡信息错误,请确认后再次提交", null);
			}

		}

		if (cardBin.getCardbank() == null || !isAllowBank(cardBin.getCardbank())) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", null);
		}
		
		
		ClBnkCrd usrDefault = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
		String defaultStatus = "0";
		if(usrDefault == null) {
			defaultStatus = "1";
			List<ClBnkCrd> bnks = clBnkCrdService.getUsrAllBankCard(clUsr.getUSR_CDE());
			for (ClBnkCrd bnk : bnks) {
				bnk.setIS_DEF("0");
				clBnkCrdService.updateBySEQSelective(bnk);
			}
		}else {
			defaultStatus = "0";
		}
		
		ClBnkCrd bnkCrd = clBnkCrdService.getBnkCrdByUsrAndCrdNum(clUsr.getUSR_CDE(), APPL_CARD_NO);
		if(bnkCrd == null) {
			bnkCrd = new ClBnkCrd();
			bnkCrd.setUSR_CDE(clUsr.getUSR_CDE());
			bnkCrd.setAPPL_BANK_NAME(cardBin.getCardbank());
			bnkCrd.setBNK_CODE(cardBin.getCardabb());
			bnkCrd.setAPPL_AC_BANK(cardBin.getCardbank());
			bnkCrd.setET_BNK_ADDR("");
			bnkCrd.setCARDTYPE(cardBin.getCardtype() + "");
			bnkCrd.setAPPL_CARD_NO(tmpNum);
			bnkCrd.setEPR_DT("");
			bnkCrd.setSGN_CDE("");
			bnkCrd.setAPPL_AC_NAM(idCardInfo.getCUST_NAME());
			bnkCrd.setAPPL_ID_NO(idCardInfo.getID_NO());
			bnkCrd.setUSR_TEL(USR_TEL);
			bnkCrd.setSTATUS("1");
			bnkCrd.setIS_DEF(defaultStatus);
			bnkCrd.setIS_DEL("0");
			bnkCrd.setWGHT(0);
			bnkCrd.setIS_BIND("-1");
			bnkCrd.setBIND_DT(DateUtils.getNowFullFmt());
			bnkCrd.setBIND_IP("");
			bnkCrd.setBIND_CDE("");
			bnkCrd.setMDF_DT(null);
			clBnkCrdService.addBnkCrd(bnkCrd);
		}else {
			bnkCrd.setIS_DEF(defaultStatus);
			clBnkCrdService.updateBySEQSelective(bnkCrd);
		}
		net.sf.json.JSONObject result = payChannelBankCardService.payChannelBankCardQuery(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, channel);
	
		if(channel.equals("CL0001")) {
			channel = "1";
		} else if (channel.equals("CL0002")) {
			channel = "2";
		} 
		if(result.getString("result").equals("success")) {
			
			cardInfo.put("channel", channel);
			cardInfo.put("isBind", "true");
			cardInfo.put("bankCardNum", APPL_CARD_NO);
			cardInfo.put("bankCardPhone", USR_TEL);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "已签约", cardInfo);
		}

		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "未签约", cardInfo);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "verifyPPwd")
	public String verifyPPwd(String PASSWORDSIGN,
			HttpServletRequest request,
			HttpServletResponse response) {
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		String payPwd = clCheckPassWordService.getPayPwdByUsrCde(clUsr.getUSR_CDE());
		//验证安全密码
		if(StringUtils.isEmpty(payPwd) || !payPwd.equals(PASSWORDSIGN)) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "验证支付密码错误", null);
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "验证支付密码成功", null);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "preModifyBankCard")
	public String preModifyBankCard(
//			String PASSWORDSIGN,
			String cbankCardNum,
			String tbankCardNum,
			@RequestParam(value = "bankCardPhone") String USR_TEL,
			@RequestParam(value = "channel") String channel,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		boolean isMn = CheckNumber.isMobileNum(USR_TEL);
		if(isMn == false) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		
		
		ClUsr clUsr = (ClUsr)request.getAttribute("usr");
		
		JsonObject resultJson = new JsonObject();
		int allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD, clUsr.getUSR_TEL());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.SMS_API_VERIFY_BANKCARD,
				clUsr.getUSR_TEL());
		resultJson.addProperty("todayCnt", todayCnt);
		resultJson.addProperty("allCnt", allCnt);
		if (allCnt >= AppCommonConstants.getValInt("CHKBANKCARDLIMIT")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "发送短信次数已耗尽");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		if (todayCnt >= AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY")) {
			resultJson.addProperty("result", "false");
			resultJson.addProperty("msg", "今天发送短信次数已用尽，请明天再试");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "", resultJson).toString();
		}
		resultJson.addProperty("cbankCardNum", cbankCardNum);
		resultJson.addProperty("cbankCardNum", tbankCardNum);
		resultJson.addProperty("bankCardPhone", USR_TEL);
		resultJson.addProperty("channel", channel);
		resultJson.addProperty("tel", USR_TEL);
		
//		String payPwd = clCheckPassWordService.getPayPwdByUsrCde(clUsr.getUSR_CDE());
//		//验证安全密码
//		if(StringUtils.isEmpty(payPwd) || !payPwd.equals(PASSWORDSIGN)) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "验证支付密码错误", null);
//		}
		ClIdCardInfo idCardInfo = clIdCardInfoService.getByUsrCodeAndValidate(clUsr.getUSR_CDE());
		if (idCardInfo == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成身份证ocr识别", null);
		}
//		ClBnkCrd current = clBnkCrdService.getOneByBankNoAndBinded4FoticBind(cbankCardNum);
//		if(current == null) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "当前银行卡未成功绑定", null);
//		}
//		
//		ClBnkCrd target = clBnkCrdService.getOneByBankNoAndBinded4FoticBind(tbankCardNum);
//		if(target != null) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡号已存在", null);
//		}
//		
//		target = clBnkCrdService.getOneByBankNoWithOutSts(tbankCardNum);
//		if(target != null && target.getUSR_TEL().equals(USR_TEL) && target.getIS_BIND().equals("-1")) {
//			clBnkCrdService.deleteByCradNo(target);
//		}
		
		String tmpNum = tbankCardNum.replaceAll("\\s*", "");
		String tmp1 = tmpNum.substring(0, 6);
		ClCardBin cardBin = clCardBinService.getByClCardBinIdObj(tmp1);
		if (cardBin == null) {
			tmp1 = tmpNum.substring(0, 9);
			cardBin = clCardBinService.getByClCardBinIdObj(tmp1);
			if (cardBin == null) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "获取银行卡信息错误,请确认后再次提交", null);
			}

		}

		if (cardBin.getCardbank() == null || !isAllowBank(cardBin.getCardbank())) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", null);
		}
		
		if(channel.equals("1")) {
			channel = "CL0001";
		} else if (channel.equals("2")) {
			channel = "CL0002";
		} else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "支付通道错误", resultJson);
		}
		
		ClBnkCrd usrDefault = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
		String defaultStatus = "0";
		if(usrDefault == null) {
			defaultStatus = "1";
		}else {
			defaultStatus = "0";
		}
		
		ClBnkCrd bnkCrd = clBnkCrdService.getBnkCrdByUsrAndCrdNum(clUsr.getUSR_CDE(), tbankCardNum);
		if(bnkCrd == null) {
			bnkCrd = new ClBnkCrd();
			bnkCrd.setUSR_CDE(clUsr.getUSR_CDE());
			bnkCrd.setAPPL_BANK_NAME(cardBin.getCardbank());
			bnkCrd.setBNK_CODE(cardBin.getCardabb());
			bnkCrd.setAPPL_AC_BANK(cardBin.getCardbank());
			bnkCrd.setET_BNK_ADDR("");
			bnkCrd.setCARDTYPE(cardBin.getCardtype() + "");
			bnkCrd.setAPPL_CARD_NO(tmpNum);
			bnkCrd.setEPR_DT("");
			bnkCrd.setSGN_CDE("");
			bnkCrd.setAPPL_AC_NAM(idCardInfo.getCUST_NAME());
			bnkCrd.setAPPL_ID_NO(idCardInfo.getID_NO());
			bnkCrd.setUSR_TEL(USR_TEL);
			bnkCrd.setSTATUS("1");
			bnkCrd.setIS_DEF(defaultStatus);
			bnkCrd.setIS_DEL("0");
			bnkCrd.setWGHT(0);
			bnkCrd.setIS_BIND("-1");
			bnkCrd.setBIND_DT(DateUtils.getNowFullFmt());
			bnkCrd.setBIND_IP("");
			bnkCrd.setBIND_CDE("");
			bnkCrd.setMDF_DT(null);
		}
		
		//修改当前银行卡状态
//		current.setSTATUS("0");
//		clBnkCrdService.updateBySEQSelective(current);
		
		long start = new Date().getTime();
		net.sf.json.JSONObject result = payChannelBankCardService.payChannelBankCard(idCardInfo.getCUST_NAME(), tbankCardNum, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, channel,bnkCrd);
		long end = new Date().getTime();
		ClApiInfo apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.ZW_BINDCARD,AppNormalConstants.SMS_API_VERIFY_BANKCARD,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+tbankCardNum+","+channel+","+idCardInfo.getCUST_NAME()+","+idCardInfo.getID_NO(),"",result.toString());
		resultJson.addProperty("allCnt", allCnt + 1);
		resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
		resultJson.addProperty("todayCnt", todayCnt + 1);
		resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
		if(result.getString("result").equals("success")) {
			apiInfo.setSTATUS("SUCCESS");
			apiInfo.setSERVICE_NAME(AppNormalConstants.ZW_BINDCARD);
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交签约申请成功", resultJson);
		}
		apiInfo.setSTATUS("FAIL");
		apiService.addInvokeApiInfo(apiInfo);
		return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交签约申请失败", resultJson);
		
	}
	
	
	
//	@ResponseBody
//	@RequestMapping(value = "modifyBindCard")
//	public String modifyBindCard(
////			String PASSWORDSIGN,
//			String cbankCardNum,
//			String tbankCardNum,
//			@RequestParam(value = "bankCardPhone") String USR_TEL,
//			@RequestParam(value = "channel") String channel,
//			HttpServletRequest request,
//			HttpServletResponse response) {
//		
//		boolean isMn = CheckNumber.isMobileNum(USR_TEL);
//		if(isMn == false) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
//		}
//		
//		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
//		
//		JsonObject resultJson = new JsonObject();
//		
//		resultJson.addProperty("cbankCardNum", cbankCardNum);
//		resultJson.addProperty("cbankCardNum", tbankCardNum);
//		resultJson.addProperty("bankCardPhone", USR_TEL);
//		resultJson.addProperty("channel", channel);
//		resultJson.addProperty("tel", USR_TEL);
//		
////		String payPwd = clCheckPassWordService.getPayPwdByUsrCde(clUsr.getUSR_CDE());
////		//验证安全密码
////		if(StringUtils.isEmpty(payPwd) || !payPwd.equals(PASSWORDSIGN)) {
////			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "验证支付密码错误", null);
////		}
//		
//		ClBnkCrd current = clBnkCrdService.getOneByBankNoAndBinded4FoticBind(cbankCardNum);
//		if(current == null) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "当前银行卡未成功绑定", null);
//		}
//		
//		//检查更换的卡号是否已绑定成功
//		ClBnkCrd bnkCrd = clBnkCrdService.getBnkCrdByUsrAndCrdNum(clUsr.getUSR_CDE(), tbankCardNum);
//		if(bnkCrd == null) {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡未成功绑定", null);
//		} else {
//			String isBind = bnkCrd.getIS_BIND();
//			if(!"2".equals(isBind)) {
//				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡未成功绑定", null);
//			}
//		}
//		
//		//检查当前卡号是否有正在还款的申请，有需要调账务变更
//		List<ClUsrApply> applies = applyService.getUsrApplyLoanedByUsrCdeAndBankCard(clUsr.getUSR_CDE(), cbankCardNum);
//		if(applies != null && applies.size() >0) {
//			//有使用该银行卡，正在还款的
//			boolean flag = true;
//			for (ClUsrApply apply : applies) {
//				String code = apply.getDebtStatus();
//				if("30".equals(code) || "31".equals(code)) {
//					flag = modifyBnkCrdZW(apply,bnkCrd,current,clUsr);
//					if(flag == false) {
//						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "变更还款卡号失败", null);
//					}
//					applyService.updateCardNoByPolicyNo(tbankCardNum, DateUtils.getNowFullFmt(), apply.getPolicyNo());
//				}
//			}
//			
//		}
//		//更改当前卡号状态
//		current.setIS_DEF("0");
//		int i = clBnkCrdService.updateBySEQSelective(current);
//		//修改默认卡   改为目标卡号
//		bnkCrd.setIS_DEF("1");
//		int j = clBnkCrdService.updateBySEQSelective(bnkCrd);
//		if(i > 0 && j> 0 )  {
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "变更还款卡号成功", resultJson);
//		} else {
//			current.setIS_DEL("1");
//			clBnkCrdService.updateBySEQSelective(current);
//			bnkCrd.setIS_DEF("0");
//			clBnkCrdService.updateBySEQSelective(bnkCrd);
//			for (ClUsrApply apply : applies) {
//				String code = apply.getDebtStatus();
//				if("30".equals(code) || "31".equals(code)) {
//					boolean flag = modifyBnkCrdZW(apply,current,bnkCrd,clUsr);
//					if(flag == false) {
//						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "恢复还款卡号失败", resultJson);
//					}
//				}
//			}
//			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "变更还款卡号失败", resultJson);
//		}
//		
//		
//		
//		
//		
//	}
	
	
	@ResponseBody
	@RequestMapping(value = "modifyBindCard")
	public String modifyBindCard(
//			String PASSWORDSIGN,
			String cbankCardNum,
			String tbankCardNum,
//			@RequestParam(value = "bankCardPhone") String USR_TEL,
//			@RequestParam(value = "channel") String channel,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if(cbankCardNum.equals(tbankCardNum)) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "当前银行卡号与更换银行卡号一致，请检查后重试", null);
		}
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		
		int allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.MODIFYBANKCARD, clUsr.getUSR_TEL());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.MODIFYBANKCARD,
				clUsr.getUSR_TEL());
		JsonObject resultJson = new JsonObject();
		if (allCnt >= AppCommonConstants.getValInt("ModifyBankCardLimit")) {
			resultJson.addProperty("msg", "更换银行卡次数已耗尽");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("ModifyBankCardLimit"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("ModifyBankCardLimitEachDay"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "更换银行卡次数已耗尽", resultJson).toString();
		}
		if (todayCnt >= AppCommonConstants.getValInt("ModifyBankCardLimitEachDay")) {
			resultJson.addProperty("result", "3");
			resultJson.addProperty("msg", "今天更换银行卡次数已用尽，请明天再试");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("ModifyBankCardLimit"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("ModifyBankCardLimitEachDay"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "今天更换银行卡次数已用尽，请明天再试", resultJson).toString();
		}
		
		
		
		ClBnkCrd current = clBnkCrdService.getOneByBankNoAndBindedWFCCBBind(cbankCardNum);
		if(current == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "当前银行卡未成功绑定", null);
		}
		
		//检查更换的卡号是否已绑定成功
		ClBnkCrd bnkCrd = clBnkCrdService.getBnkCrdByUsrAndCrdNum(clUsr.getUSR_CDE(), tbankCardNum);
		if(bnkCrd == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡未成功绑定", null);
		} else {
			String isBind = bnkCrd.getIS_BIND();
			if(!"0".equals(isBind)) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡未成功绑定", null);
			}
		}
		
/**
 * 由于潍坊银行客户不需要用改卡还款，所以不用调用账务变更
 * 
		//检查当前卡号是否有正在还款的申请，有需要调账务变更
		List<ClUsrApply> applies = applyService.getUsrApplyLoanedByUsrCdeAndBankCard(clUsr.getUSR_CDE(), cbankCardNum);
		if(applies != null && applies.size() >0) {
			//有使用该银行卡，正在还款的
			boolean flag = true;
			for (ClUsrApply apply : applies) {
				String code = apply.getDebtStatus();
				if("30".equals(code) || "31".equals(code)) {
					flag = modifyBnkCrdZW(apply,bnkCrd,current,clUsr);
					if(flag == false) {
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "变更还款卡号失败", null);
					}
					applyService.updateCardNoByPolicyNo(tbankCardNum, DateUtils.getNowFullFmt(), apply.getPolicyNo());
				}
			}
			
		}
		
*/
		//更改当前卡号状态
		current.setIS_DEF("0");
		int i = clBnkCrdService.updateBySEQSelective(current);
		//修改默认卡   改为目标卡号
		bnkCrd.setIS_DEF("1");
		int j = clBnkCrdService.updateBySEQSelective(bnkCrd);
		if(i > 0 && j> 0 )  {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "变更还款卡号成功", resultJson);
		} else {
			current.setIS_DEL("1");
			clBnkCrdService.updateBySEQSelective(current);
			bnkCrd.setIS_DEF("0");
			clBnkCrdService.updateBySEQSelective(bnkCrd);
/**
 * 由于潍坊银行客户不需要用改卡还款，所以不用调用账务变更
			for (ClUsrApply apply : applies) {
				String code = apply.getDebtStatus();
				if("30".equals(code) || "31".equals(code)) {
					boolean flag = modifyBnkCrdZW(apply,current,bnkCrd,clUsr);
					if(flag == false) {
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "恢复还款卡号失败", resultJson);
					}
				}
			}
*/
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "变更还款卡号失败", resultJson);
		}
		
		
		
		
		
	}
	
	private boolean modifyBnkCrdZW(ClUsrApply apply,ClBnkCrd tBnkCrd,ClBnkCrd current,ClUsr usr) {
		BankCardRequestVO bankCardReq = new BankCardRequestVO();
		BankCardRequestBody reqBody = new BankCardRequestBody();
		BankCardRequestRecord record = new BankCardRequestRecord();
		record.setBankAcctName(tBnkCrd.getAPPL_AC_NAM());
		record.setBankCardNo(tBnkCrd.getAPPL_CARD_NO());
		record.setBankCityCode("");
		record.setBankCityName("");
		record.setBankCode(tBnkCrd.getBNK_CODE());
		record.setBankMobile(tBnkCrd.getUSR_TEL());
		record.setBankOwerName(tBnkCrd.getAPPL_AC_BANK());
		record.setBankProvinceCode("");
		record.setBankProvinceName("");
		record.setCertificateNo(tBnkCrd.getAPPL_ID_NO());
		record.setCertificateType("20");
		record.setGuarantyId(apply.getPolicyNo());
		record.setLoanId(apply.getLoanAcno());//借据号
		record.setMobile(tBnkCrd.getUSR_TEL());
		record.setReceiveAcctNo(current.getAPPL_CARD_NO());
		record.setReceiveBankCode(current.getBNK_CODE());
		record.setReceiveBankName(current.getAPPL_BANK_NAME());
		record.setBankName(tBnkCrd.getAPPL_BANK_NAME());
		reqBody.setBankCardRequestRecord(record);
		bankCardReq.setBankdCardRequestBody(reqBody);
		DataHeader header = new DataHeader();
		bankCardReq.setDataHeader(header);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date d = new Date();
		String date = sdf.format(d);
		String patch = "SD" + date + random3();
		header.setDataSource("ISP");
		header.setPatch(patch);
		header.setTranBank("FOTIC"); 
		header.setTranCode("YG0008");
		header.setTranTime(DateUtils.formatDate(d, "yyyyMMddHHmmss"));
		
		BankCardResponseVO bandCardResp = bcModifyService.modifyBankCard(bankCardReq);
		
		if(bandCardResp.getBankCardResponseBody().getBankCardResponseRecord().getHandleCode().equals("1")) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	private int random3() {
		int spy = 0;
		for (int i = 0; i < 3; i++) { // 生成一个3位的序列号
			spy = (int) (Math.random() * 10);
		}
		return spy;
	}
	
}
