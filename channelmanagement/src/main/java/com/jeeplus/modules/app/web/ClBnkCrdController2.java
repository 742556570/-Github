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
import com.jeeplus.modules.app.service.PayChannelBankCardService2;
import com.jeeplus.modules.app.utils.CheckNumber;

@Controller
@RequestMapping(value = "api/bnkcrd2")
public class ClBnkCrdController2 {

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
	private PayChannelBankCardService2 payChannelBankCardService;

	private Id5Client id5Client = new Id5Client();

	@Autowired
	private ClCheckPassWordService clCheckPassWordService;
	
	@Autowired
	private ClUsrApplyService applyService;
	
	private BankCardOcr bcOcr = new BankCardOcr();
	
	
	private BankCardModifyService bcModifyService = new BankCardModifyService();


	@ResponseBody
	@RequestMapping(value = "bindCard")
	public String bindCardZJAndGZT(
			@RequestParam(value = "cardNo") String APPL_CARD_NO,
			@RequestParam(value = "mobile") String USR_TEL,
			@RequestParam(value = "smsCode") String smsCode,
			HttpServletRequest request,
			HttpServletResponse response) {
		//1.调用绑定接口
		//2.调用国政通
		//3.更改装态
		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		JsonObject resultJson = new JsonObject();
		
		int allCnt = apiService.getInvokeCntByApiNameAndUsrTel(AppNormalConstants.BINDFAIL, clUsr.getUSR_TEL());
		int todayCnt = apiService.getInvokeCntTodayByApiNameAndUsrTel(AppNormalConstants.BINDFAIL,
				clUsr.getUSR_TEL());
		if (allCnt >= AppCommonConstants.getValInt("BindFailLimit")) {
			resultJson.addProperty("msg", "绑定银行卡次数已耗尽");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("BindFailLimit"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("BindFailLimitEachDay"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "绑定银行卡次数已耗尽", resultJson).toString();
		}
		if (todayCnt >= AppCommonConstants.getValInt("BindFailLimitEachDay")) {
			resultJson.addProperty("result", "3");
			resultJson.addProperty("msg", "今天绑定银行卡次数已用尽，请明天再试");
			resultJson.addProperty("allCnt", allCnt);
			resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("BindFailLimit"));
			resultJson.addProperty("todayCnt", todayCnt);
			resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("BindFailLimitEachDay"));
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "今天绑定银行卡次数已用尽，请明天再试", resultJson).toString();
		}
		
		boolean isMn = CheckNumber.isMobileNum(USR_TEL);
		if(isMn == false) {
			ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.BINDFAIL,AppNormalConstants.BINDFAIL,DateUtils.getNowFullFmt(),0,"","FAIL","");
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请输入正确手机号", null).toString();
		}
		
		resultJson.addProperty("tel", USR_TEL);
		ClIdCardInfo idCardInfo = clIdCardInfoService.getByUsrCodeAndValidate(clUsr.getUSR_CDE());
		if (idCardInfo == null) {
			ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.BINDFAIL,AppNormalConstants.BINDFAIL,DateUtils.getNowFullFmt(),0,"","FAIL","");
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成身份证ocr识别", resultJson);
		}
		long start = new Date().getTime();
		net.sf.json.JSONObject result = payChannelBankCardService.payChannelBankCardSign(smsCode, APPL_CARD_NO, "CL0001");
		long end = new Date().getTime();
		ClApiInfo apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_BIND,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+"CL0001","",result.toString());
		if(result.getString("result").equals("success")) {
			apiInfo.setSTATUS("SUCCESS");
			apiService.addInvokeApiInfo(apiInfo);
			ClBnkCrd target = clBnkCrdService.getOneByBankNoWithOutSts(APPL_CARD_NO);
			String tmpNum = APPL_CARD_NO.replaceAll("\\s*", "");
			String param = idCardInfo.getCUST_NAME() + "," + tmpNum + "," + idCardInfo.getID_NO() + "," + USR_TEL;
			start = new Date().getTime();
			
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
			end = new Date().getTime();
			apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.FOUR_ELEMNET_CHK,AppNormalConstants.FOUR_ELEMNET_SERIVE_NAME,DateUtils.getNowFullFmt(),(int)(end-start),param,"","");
			if (!code.equals("7")) {
				apiInfo.setSTATUS("FAIL");
				apiInfo.setRESULT(code);
				apiService.addInvokeApiInfo(apiInfo);
				resultJson.addProperty("result", "2");
				resultJson.addProperty("msg", "银行卡验证错误,请确认后再次提交");
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡验证错误,请确认后再次提交", resultJson);
			}else {
				apiInfo.setSTATUS("SUCCESS");
				apiInfo.setRESULT(code);
				apiService.addInvokeApiInfo(apiInfo);
				target.setIS_BINDFOTIC("2");
				clBnkCrdService.updateBySEQSelective(target);
				ClBnkCrd bcard = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
				if(bcard==null) {
					List<ClBnkCrd> crds = clBnkCrdService.getUsrAllBankCard(clUsr.getUSR_CDE());
					for (ClBnkCrd tmp : crds) {
						tmp.setIS_DEF("0");
						clBnkCrdService.updateBySEQSelective(tmp);
					}
					target.setIS_DEF("1");
					clBnkCrdService.updateBySEQSelective(target);
				}
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "确认签约申请成功", resultJson);
			}
			
		}else {
			apiInfo.setSTATUS("FAIL");
			apiService.addInvokeApiInfo(apiInfo);
			apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.BINDFAIL,AppNormalConstants.BINDFAIL,DateUtils.getNowFullFmt(),0,"","FAIL","");
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "确认签约申请失败", resultJson);
		}
	}



	@ResponseBody
	@RequestMapping(value = "sendSmsCde")
	public String sendBnkCrdPhone(
			@RequestParam(value = "cardNo") String APPL_CARD_NO,
			@RequestParam(value = "mobile") String USR_TEL,
			HttpServletRequest request,
			HttpServletResponse response) {
		//1.查询是否绑定对应的渠道
		//2.查询卡号是否存在
		//3.存在检查卡号绑定进度
		//4.老卡，检查是否已成功帮过中金   是 成功  否  检查是否有签约记录  无  申请签约 有 重发短信  重发提示签约号不存在 则 申请签约
		//5.新卡，检查中金是否成功  是  检查国政通  
		//                 否 发送短信验证码
		
		
		ClUsr clUsr = (ClUsr) request.getAttribute("usr");
		
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
		ClIdCardInfo idCardInfo = clIdCardInfoService.getByUsrCodeAndValidate(clUsr.getUSR_CDE());
		if (idCardInfo == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "请先完成身份证ocr识别", null);
		}
		//查询卡号是否存在
		ClBnkCrd target = clBnkCrdService.getOneByBankNoWithOutSts(APPL_CARD_NO);
		
		//用户输入卡号存在且(已绑定国政通或中金),不允许继续
		if(target != null && ("2".equals(target.getIS_BINDFOTIC())) ){
			if(!target.getUSR_CDE().equals(clUsr.getUSR_CDE())) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡号已被其他用户绑定", resultJson).toString();
			}
			resultJson = new JsonObject();
			resultJson.addProperty("isBindSuccess", "true");
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "卡号已绑定", resultJson).toString();
		}else if(target != null && (!"2".equals(target.getIS_BINDFOTIC()) && "0".equals(target.getIS_BIND())) ) {
			//已绑定国政通的卡号 ，但未成功绑定中金
			if(!target.getUSR_CDE().equals(clUsr.getUSR_CDE())) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡号已被其他用户绑定", resultJson).toString();
			}
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
			//检查支持银行卡列表
			if (cardBin.getCardbank() == null || !isAllowBank(cardBin.getCardbank())) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", null);
			}
			if(tmpNum.startsWith("622309") && tmpNum.length() == 19) {
				resultJson.addProperty("result", "3");
				resultJson.addProperty("allCnt", allCnt);
				resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
				resultJson.addProperty("todayCnt", todayCnt);
				resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", resultJson);
			}
			long start = new Date().getTime();
			net.sf.json.JSONObject result = payChannelBankCardService.payChannelQuery(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001");
			long end = new Date().getTime();
			ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_QUERY,DateUtils.getNowFullFmt(),(int)(end-start),StringUtils.contractString(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001"),"",result.toString());
			if(result.getString("result").equals("success")) {
				apiInfo.setSTATUS("SUCCESS");
				apiService.addInvokeApiInfo(apiInfo);
				//已经成功绑定中金
				target.setIS_BINDFOTIC("2");
				clBnkCrdService.updateBySEQSelective(target);
				ClBnkCrd bcard = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
				if(bcard==null) {
					List<ClBnkCrd> crds = clBnkCrdService.getUsrAllBankCard(clUsr.getUSR_CDE());
					for (ClBnkCrd tmp : crds) {
						tmp.setIS_DEF("0");
						clBnkCrdService.updateBySEQSelective(tmp);
					}
					target.setIS_DEF("1");
					clBnkCrdService.updateBySEQSelective(target);
				}
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "绑定成功", resultJson);
			}else {
				//未查询到绑定信息，发送短信
				apiInfo.setSTATUS("SUCCESS");
				apiService.addInvokeApiInfo(apiInfo);
				if(StringUtils.isEmpty(target.getBIND_IP())) {
					//没有申请签约记录的，申请签约
					start = new Date().getTime();
					net.sf.json.JSONObject sendResult = payChannelBankCardService.payChannelBankCard(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001",target);
					end = new Date().getTime();
					apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_SEND,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+"CL0001"+","+idCardInfo.getCUST_NAME()+","+idCardInfo.getID_NO(),"",sendResult.toString());
					resultJson.addProperty("allCnt", allCnt + 1);
					resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
					resultJson.addProperty("todayCnt", todayCnt + 1);
					resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
					resultJson.addProperty("detail", result.getJSONObject("detail").toString());
					if(sendResult.getString("result").equals("success")) {
						apiInfo.setSTATUS("SUCCESS");
						apiInfo.setSERVICE_NAME(AppNormalConstants.PAYCHANNEL);
						apiService.addInvokeApiInfo(apiInfo);
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交签约申请成功", resultJson);
					}
					apiInfo.setSTATUS("FAIL");
					apiService.addInvokeApiInfo(apiInfo);
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交签约申请失败", resultJson);
				}else {
					//有申请签约记录的
					start = new Date().getTime();
					net.sf.json.JSONObject reSendResult = payChannelBankCardService.payChannelBankCardSign(APPL_CARD_NO, "CL0001");
					end = new Date().getTime();
					apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_RESEND,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+"CL0001","",reSendResult.toString());
					resultJson.addProperty("allCnt", allCnt + 1);
					resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
					resultJson.addProperty("todayCnt", todayCnt + 1);
					resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
					resultJson.addProperty("detail", result.getJSONObject("detail").toString());
					
					String msg = "";
					if(reSendResult.getJSONObject("detail").containsKey("msg")) {
						msg = reSendResult.getJSONObject("detail").getString("msg");
					} else if (reSendResult.getJSONObject("detail").containsKey("errorMsg")) {
						msg = reSendResult.getJSONObject("detail").getString("errorMsg");
					} else {
						msg = "流水号不存在";
					}
					
					
					if(reSendResult.getString("result").equals("success")) {
						apiInfo.setSTATUS("SUCCESS");
						apiInfo.setSERVICE_NAME(AppNormalConstants.PAYCHANNEL);
						apiService.addInvokeApiInfo(apiInfo);
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "重发短信申请成功", resultJson);
					} else if(reSendResult.getString("result").equals("fail") && msg.contains("流水号不存在")){
						start = new Date().getTime();
						net.sf.json.JSONObject sendResult = payChannelBankCardService.payChannelBankCard(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001",target);
						end = new Date().getTime();
						apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_SEND,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+"CL0001"+","+idCardInfo.getCUST_NAME()+","+idCardInfo.getID_NO(),"",sendResult.toString());
						resultJson.addProperty("allCnt", allCnt + 1);
						resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
						resultJson.addProperty("todayCnt", todayCnt + 1);
						resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
						resultJson.addProperty("detail", result.getJSONObject("detail").toString());
						if(sendResult.getString("result").equals("success")) {
							apiInfo.setSTATUS("SUCCESS");
							apiInfo.setSERVICE_NAME(AppNormalConstants.PAYCHANNEL);
							apiService.addInvokeApiInfo(apiInfo);
							return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交签约申请成功", resultJson);
						}
						apiInfo.setSTATUS("FAIL");
						apiService.addInvokeApiInfo(apiInfo);
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交签约申请失败", resultJson);
					}else {
						apiInfo.setSTATUS("FAIL");
						apiService.addInvokeApiInfo(apiInfo);
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "重发短信申请失败", resultJson);
					}
				}
			}
			
		}else if(target != null && (!"2".equals(target.getIS_BINDFOTIC()) && !"0".equals(target.getIS_BIND())) ) {
			//两个通道都没绑定
			if(!target.getUSR_CDE().equals(clUsr.getUSR_CDE())) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡号已被其他用户绑定", resultJson).toString();
			}
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
			//检查支持银行卡列表
			if (cardBin.getCardbank() == null || !isAllowBank(cardBin.getCardbank())) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", null);
			}
			if(tmpNum.startsWith("622309") && tmpNum.length() == 19) {
				resultJson.addProperty("result", "3");
				resultJson.addProperty("allCnt", allCnt);
				resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
				resultJson.addProperty("todayCnt", todayCnt);
				resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", resultJson);
			}
			long start = new Date().getTime();
			net.sf.json.JSONObject result = payChannelBankCardService.payChannelQuery(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001");
			long end = new Date().getTime();
			//查询是否绑定成功
			ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_QUERY,DateUtils.getNowFullFmt(),(int)(end-start),StringUtils.contractString(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001"),"",result.toString());
			if(result.getString("result").equals("success")) {
				apiInfo.setSTATUS("SUCCESS");
				apiService.addInvokeApiInfo(apiInfo);
				//国政通验证
				String isOnline = Global.getConfig("isOnline");
				String baffleFlg = "N";
				if("true".equals(isOnline)) {
					baffleFlg = "N";
				}else if ("false".equals(isOnline)) {
					baffleFlg = "Y";
				}else {
					baffleFlg = "N";
				}
				String param = idCardInfo.getCUST_NAME() + "," + tmpNum + "," + idCardInfo.getID_NO() + "," + USR_TEL;
				String code = id5Client.buildRequest(param, baffleFlg);
				end = new Date().getTime();
				apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.FOUR_ELEMNET_CHK,AppNormalConstants.FOUR_ELEMNET_SERIVE_NAME,DateUtils.getNowFullFmt(),(int)(end-start),param,"","");
				if (!code.equals("7")) {
					apiInfo.setSTATUS("FAIL");
					apiInfo.setRESULT(code);
					apiService.addInvokeApiInfo(apiInfo);
					resultJson.addProperty("result", "2");
					resultJson.addProperty("msg", "国政通验证四要素不一致");
					resultJson.addProperty("allCnt", allCnt+1);
					resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
					resultJson.addProperty("todayCnt", todayCnt+1);
					resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡验证错误,请确认后再次提交", resultJson);
				}else {
					apiInfo.setSTATUS("SUCCESS");
					apiInfo.setRESULT(code);
					apiService.addInvokeApiInfo(apiInfo);
					target.setIS_BINDFOTIC("2");
					clBnkCrdService.updateBySEQSelective(target);
					ClBnkCrd bcard = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
					if(bcard==null) {
						List<ClBnkCrd> crds = clBnkCrdService.getUsrAllBankCard(clUsr.getUSR_CDE());
						for (ClBnkCrd tmp : crds) {
							tmp.setIS_DEF("0");
							clBnkCrdService.updateBySEQSelective(tmp);
						}
						target.setIS_DEF("1");
						clBnkCrdService.updateBySEQSelective(target);
					}
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "绑定成功", resultJson);
				}
				//已经成功绑定中金
			}else {
				//未查询到绑定信息，发送短信
				apiInfo.setSTATUS("SUCCESS");
				apiService.addInvokeApiInfo(apiInfo);
				if(StringUtils.isEmpty(target.getBIND_IP())) {
					//没有申请签约记录的，申请签约
					start = new Date().getTime();
					net.sf.json.JSONObject sendResult = payChannelBankCardService.payChannelBankCard(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001",target);
					end = new Date().getTime();
					apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_SEND,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+"CL0001"+","+idCardInfo.getCUST_NAME()+","+idCardInfo.getID_NO(),"",sendResult.toString());
					resultJson.addProperty("allCnt", allCnt + 1);
					resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
					resultJson.addProperty("todayCnt", todayCnt + 1);
					resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
					resultJson.addProperty("detail", result.getJSONObject("detail").toString());
					if(sendResult.getString("result").equals("success")) {
						apiInfo.setSTATUS("SUCCESS");
						apiInfo.setSERVICE_NAME(AppNormalConstants.PAYCHANNEL);
						apiService.addInvokeApiInfo(apiInfo);
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交签约申请成功", resultJson);
					}
					apiInfo.setSTATUS("FAIL");
					apiService.addInvokeApiInfo(apiInfo);
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交签约申请失败", resultJson);
				}else {
					//有申请签约记录的
					start = new Date().getTime();
					net.sf.json.JSONObject reSendResult = payChannelBankCardService.payChannelBankCardSign(APPL_CARD_NO, "CL0001");
					end = new Date().getTime();
					apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_RESEND,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+"CL0001","",reSendResult.toString());
					resultJson.addProperty("allCnt", allCnt + 1);
					resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
					resultJson.addProperty("todayCnt", todayCnt + 1);
					resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
					resultJson.addProperty("detail", result.getJSONObject("detail").toString());
					if(reSendResult.getString("result").equals("success")) {
						apiInfo.setSTATUS("SUCCESS");
						apiInfo.setSERVICE_NAME(AppNormalConstants.PAYCHANNEL);
						apiService.addInvokeApiInfo(apiInfo);
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "重发短信申请成功", resultJson);
					} else if(reSendResult.getString("result").equals("fail") && reSendResult.getJSONObject("detail").getString("errorMsg").contains("不存在待确认的签约信息")){
						target.setBIND_IP("");
						target.setBIND_CDE("");
						clBnkCrdService.updateBySEQSelective(target);
						start = new Date().getTime();
						net.sf.json.JSONObject sendResult = payChannelBankCardService.payChannelBankCard(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001",target);
						end = new Date().getTime();
						apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_SEND,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+"CL0001"+","+idCardInfo.getCUST_NAME()+","+idCardInfo.getID_NO(),"",sendResult.toString());
						resultJson.addProperty("allCnt", allCnt + 1);
						resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
						resultJson.addProperty("todayCnt", todayCnt + 1);
						resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
						resultJson.addProperty("detail", result.getJSONObject("detail").toString());
						if(sendResult.getString("result").equals("success")) {
							apiInfo.setSTATUS("SUCCESS");
							apiInfo.setSERVICE_NAME(AppNormalConstants.PAYCHANNEL);
							apiService.addInvokeApiInfo(apiInfo);
							return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交签约申请成功", resultJson);
						}
						apiInfo.setSTATUS("FAIL");
						apiService.addInvokeApiInfo(apiInfo);
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交签约申请失败", resultJson);
					}else {
						apiInfo.setSTATUS("FAIL");
						apiService.addInvokeApiInfo(apiInfo);
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "重发短信申请失败", resultJson);
					}
				}
			}
			
		}else if(target == null) {
			//用户输入卡号不存在
			//检查卡bin
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
			//检查支持银行卡列表
			if (cardBin.getCardbank() == null || !isAllowBank(cardBin.getCardbank())) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", null);
			}
			if(tmpNum.startsWith("622309") && tmpNum.length() == 19) {
				resultJson.addProperty("result", "3");
				resultJson.addProperty("allCnt", allCnt);
				resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("FOURCHKLIMIT"));
				resultJson.addProperty("todayCnt", todayCnt);
				resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("FOURCHKLIMITEACHDAY"));
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡不在支持银行卡列表,请更换银行卡后再次提交", resultJson);
			}
			//获取用户默认卡
			ClBnkCrd usrDefault = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
			String defaultStatus = "0";
			if(usrDefault == null) {
				defaultStatus = "1";
				List<ClBnkCrd> bnks = clBnkCrdService.getUsrAllBankCard(clUsr.getUSR_CDE());
				for (ClBnkCrd bnk : bnks) {
					if(!"0".equals(bnk.getIS_DEF())) {
						bnk.setIS_DEF("0");
						clBnkCrdService.updateBySEQSelective(bnk);
					}
				}
			}else {
				defaultStatus = "0";
			}
			target = new ClBnkCrd();
			target.setUSR_CDE(clUsr.getUSR_CDE());
			target.setAPPL_BANK_NAME(cardBin.getCardbank());
			target.setBNK_CODE(cardBin.getCardabb());
			target.setAPPL_AC_BANK(cardBin.getCardbank());
			target.setET_BNK_ADDR("");
			target.setCARDTYPE(cardBin.getCardtype() + "");
			target.setAPPL_CARD_NO(tmpNum);
			target.setEPR_DT("");
			target.setSGN_CDE("");
			target.setAPPL_AC_NAM(idCardInfo.getCUST_NAME());
			target.setAPPL_ID_NO(idCardInfo.getID_NO());
			target.setUSR_TEL(USR_TEL);
			target.setSTATUS("1");
			target.setIS_DEF(defaultStatus);
			target.setIS_DEL("0");
			target.setWGHT(0);
			target.setIS_BIND("-1");
			target.setBIND_DT(DateUtils.getNowFullFmt());
			target.setBIND_IP("");
			target.setBIND_CDE("");
			target.setMDF_DT(null);
			target.setIS_BINDFOTIC("-1");
			//查询是否绑定中金
			long start = new Date().getTime();
			net.sf.json.JSONObject result = payChannelBankCardService.payChannelQuery(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001");
			long end = new Date().getTime();
			ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_QUERY,DateUtils.getNowFullFmt(),(int)(end-start),StringUtils.contractString(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001"),"",result.toString());
			if(result.getString("result").equals("success")) {
				//中金签约成功
				apiInfo.setSTATUS("SUCCESS");
				apiService.addInvokeApiInfo(apiInfo);
				target.setIS_BINDFOTIC("1");
				start = new Date().getTime();
				
				String isOnline = Global.getConfig("isOnline");
				String baffleFlg = "N";
				if("true".equals(isOnline)) {
					baffleFlg = "N";
				}else if ("false".equals(isOnline)) {
					baffleFlg = "Y";
				}else {
					baffleFlg = "N";
				}
				String param = idCardInfo.getCUST_NAME() + "," + tmpNum + "," + idCardInfo.getID_NO() + "," + USR_TEL;
				String code = id5Client.buildRequest(param, baffleFlg);
				end = new Date().getTime();
				apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.FOUR_ELEMNET_CHK,AppNormalConstants.FOUR_ELEMNET_SERIVE_NAME,DateUtils.getNowFullFmt(),(int)(end-start),param,"","");
				if (!code.equals("7")) {
					apiInfo.setSTATUS("FAIL");
					apiInfo.setRESULT(code);
					apiService.addInvokeApiInfo(apiInfo);
					resultJson.addProperty("result", "2");
					resultJson.addProperty("msg", "国政通验证四要素不一致");
					resultJson.addProperty("allCnt", allCnt+1);
					resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
					resultJson.addProperty("todayCnt", todayCnt+1);
					resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡验证错误,请确认后再次提交", resultJson);
				}else {
					apiInfo.setSTATUS("SUCCESS");
					apiInfo.setRESULT(code);
					apiService.addInvokeApiInfo(apiInfo);
					target.setIS_BINDFOTIC("2");
					clBnkCrdService.addBnkCrd(target);
					ClBnkCrd bcard = clBnkCrdService.getUsrDefaultBnkCrd(clUsr.getUSR_CDE());
					if(bcard==null) {
						List<ClBnkCrd> crds = clBnkCrdService.getUsrAllBankCard(clUsr.getUSR_CDE());
						for (ClBnkCrd tmp : crds) {
							tmp.setIS_DEF("0");
							clBnkCrdService.updateBySEQSelective(tmp);
						}
						target.setIS_DEF("1");
						clBnkCrdService.updateBySEQSelective(target);
					}
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "绑定成功", resultJson);
				}
			}else {
				//查询绑定中金未绑定 发送短信验证码
				clBnkCrdService.addBnkCrd(target);
				target = clBnkCrdService.getOneByBankNoWithOutSts(tmpNum);
				start = new Date().getTime();
				net.sf.json.JSONObject sendResult = payChannelBankCardService.payChannelBankCard(idCardInfo.getCUST_NAME(), APPL_CARD_NO, cardBin.getCardabb(), idCardInfo.getID_NO(), USR_TEL, "CL0001",target);
				end = new Date().getTime();
				apiInfo = new ClApiInfo("",clUsr.getUSR_TEL(),AppNormalConstants.PAYCHANNEL,AppNormalConstants.PAYCHANNEL_SEND,DateUtils.getNowFullFmt(),(int)(end-start),USR_TEL+","+APPL_CARD_NO+","+"CL0001"+","+idCardInfo.getCUST_NAME()+","+idCardInfo.getID_NO(),"",sendResult.toString());
				resultJson.addProperty("allCnt", allCnt + 1);
				resultJson.addProperty("allCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMIT"));
				resultJson.addProperty("todayCnt", todayCnt + 1);
				resultJson.addProperty("edCntLimit", AppCommonConstants.getValInt("CHKBANKCARDLIMITEACHDAY"));
				resultJson.addProperty("detail", result.getJSONObject("detail").toString());
				if(sendResult.getString("result").equals("success")) {
					apiInfo.setSTATUS("SUCCESS");
					apiInfo.setSERVICE_NAME(AppNormalConstants.PAYCHANNEL);
					apiService.addInvokeApiInfo(apiInfo);
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "提交签约申请成功", resultJson);
				}else {
					apiInfo.setSTATUS("FAIL");
					apiService.addInvokeApiInfo(apiInfo);
					return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "提交签约申请失败", resultJson);
				}
			}
		}

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
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "银行卡验证错误,请确认后再次提交", resultJson);
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
	@RequestMapping(value = "modifyBindCard")
	public String modifyBindCard(
			String cbankCardNum,
			String tbankCardNum,
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
		
		
		ClBnkCrd current = clBnkCrdService.getOneByBankNoAndBinded4FoticBind(cbankCardNum);
		if(current == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "当前银行卡未成功绑定", null);
			
		}
		
		
		//检查更换的卡号是否已绑定成功
		ClBnkCrd bnkCrd = clBnkCrdService.getBnkCrdByUsrAndCrdNum(clUsr.getUSR_CDE(), tbankCardNum);
		if(bnkCrd == null) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡未成功绑定", null);
		} else {
			String isBind = bnkCrd.getIS_BINDFOTIC();
			if(!"2".equals(isBind)) {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "卡未成功绑定", null);
			}
		}
		
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
		//更改当前卡号状态
		current.setIS_DEF("0");
		int i = clBnkCrdService.updateBySEQSelective(current);
		//修改默认卡   改为目标卡号
		bnkCrd.setIS_DEF("1");
		int j = clBnkCrdService.updateBySEQSelective(bnkCrd);
		if(i > 0 && j> 0 )  {
			ClApiInfo apiInfo = new ClApiInfo(clUsr.getUSR_CDE(),clUsr.getUSR_TEL(),AppNormalConstants.MODIFYBANKCARD,AppNormalConstants.MODIFYBANKCARD,DateUtils.getNowFullFmt(),0,"","FAIL","");
			apiService.addInvokeApiInfo(apiInfo);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "变更还款卡号成功", resultJson);
		} else {
			current.setIS_DEL("1");
			clBnkCrdService.updateBySEQSelective(current);
			bnkCrd.setIS_DEF("0");
			clBnkCrdService.updateBySEQSelective(bnkCrd);
			for (ClUsrApply apply : applies) {
				String code = apply.getDebtStatus();
				if("30".equals(code) || "31".equals(code)) {
					boolean flag = modifyBnkCrdZW(apply,current,bnkCrd,clUsr);
					if(flag == false) {
						return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "恢复还款卡号失败", resultJson);
					}
					applyService.updateCardNoByPolicyNo(cbankCardNum, DateUtils.getNowFullFmt(), apply.getPolicyNo());
				}
			}
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
