package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.BODY;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.HEADER;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.IN;
import com.jeeplus.modules.app.api.account.CreditLoanApply.request.RECORD;
import com.jeeplus.modules.app.api.account.utils.JaxbUtil;
import com.jeeplus.modules.app.api.eventreprot.Reporter;
import com.jeeplus.modules.app.api.eventreprot.ReqJsonUtils;
import com.jeeplus.modules.app.api.invalidpolicy.ReqXmlStringUtils;
import com.jeeplus.modules.app.api.invalidpolicy.SendInvalidInsOp;
import com.jeeplus.modules.app.api.loanChannel.SynLoanChannelController;
import com.jeeplus.modules.app.api.loanChannel.request.ReqLoanChannelVO;
import com.jeeplus.modules.app.api.loanChannel.response.RespLoanChannelVO;
import com.jeeplus.modules.app.api.loanMessage.SynLoanMessageController;
import com.jeeplus.modules.app.api.loanMessage.request.ReqLoanMessageVO;
import com.jeeplus.modules.app.api.loanMessage.response.RespLoanMessageVO;
import com.jeeplus.modules.app.api.pm.PMApi;
import com.jeeplus.modules.app.api.pm.PMXMlResultUtils;
import com.jeeplus.modules.app.api.pm.PMXmlStringUtils;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.api.product.SynProductMSGController;
import com.jeeplus.modules.app.api.product.request.ReqProductVO;
import com.jeeplus.modules.app.api.product.response.RespProductVO;
import com.jeeplus.modules.app.api.rulesengine.RulesEngineApi;
import com.jeeplus.modules.app.api.rulesengine.pojo.DecisionReq;
import com.jeeplus.modules.app.api.sms.SmsCollectionUtil;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClApiInfoDao;
import com.jeeplus.modules.app.dao.ClAppIdGenDao;
import com.jeeplus.modules.app.dao.ClBnkCrdDao;
import com.jeeplus.modules.app.dao.ClCapChannelDao;
import com.jeeplus.modules.app.dao.ClCrdtExtDao;
import com.jeeplus.modules.app.dao.ClDealFailedDao;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClPrdInfoDao;
import com.jeeplus.modules.app.dao.ClPremiumDetailDao;
import com.jeeplus.modules.app.dao.ClUsrApplyDao;
import com.jeeplus.modules.app.dao.ClUsrCntsDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.dao.ClUsrInfoDao;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClDealFailed;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClPremiumDetail;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrCnts;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.entity.ClUsrLmtamt;
import com.jeeplus.modules.app.utils.CacheHelper;
import com.jeeplus.modules.app.utils.HttpConUtils;

@Service
public class ClPmPreCreditService {
	
	private final static Logger logger = LoggerFactory.getLogger(ClPmPreCreditService.class);

	@Autowired
	private ClUsrLmtamtService usrLmtAmtService;
	
	@Autowired
	private ClDealContractWithPolicyNoService contractWithPolicyNoSerivce;

	@Autowired
	private ClCrdtExtDao crdtDao;

	@Autowired
	private ClPrdInfoDao prdDao;

	@Autowired
	private ClAppIdGenDao appIdDao;

	@Autowired
	private ClIdCardInfoDao idCardDao;

	@Autowired
	private ClBnkCrdDao bnkCrdDao;

	@Autowired
	private ClUsrCntsDao usrCntsDao;

	@Autowired
	private ClCapChannelDao capChannelDao;

	@Autowired
	private ClUsrApplyDao applyDao;

	@Autowired
	private ClUsrInfoDao usrInfoDao;

	@Autowired
	private ClPremiumDetailDao premiumDetailDao;

	@Autowired
	private ClDealFailedDao failedDao;
	
	@Autowired
	private ClUsrLmtamtService lmtAmtService;
	
	@Autowired
	private ClApiInfoDao apiInfoDao;
	
	@Autowired
	private ClUsrDao usrDao;

	private RulesEngineApi reapi = new RulesEngineApi();

	private PMApi pmapi = new PMApi();
	
	private SmsCollectionUtil sms = new SmsCollectionUtil();

	private SendInvalidInsOp siOp = new SendInvalidInsOp();
	
	@Autowired
	private ClFadadaParamsService fpService;
	
	@Autowired
	private ClUsrContractService ucService;

	public List<ClUsrApply> getByUsrCde(String usrCde) {
		return applyDao.getByUsrCde(usrCde);
	}


	public String loanTrial(ClUsr clUsr, int applyId) {
		return "";
	}

	public String queryPrdId(String usrCde) {

		ClCrdtExt crdt = crdtDao.getClCrdtExtByUsrCde(usrCde);

		if (crdt != null) {
			String prdSeq = crdt.getPRD_CDE();

			return prdSeq;

		} else {
			return "";
		}

	}

	public Integer getCurrId() {
		return appIdDao.getApplyId();
	}

	public String getRelationCode(String relation) {
		// 00 配偶；
		// 01 子女
		// 02 父母
		// 03 兄弟姐妹
		// 04 其他亲属
		// 05 朋友
		// 06 同事
		// 07 同学
		// 08 单位
		// 09 其他
		if ("配偶".equals(relation)) {
			return "spouse";
		} else if ("子女".equals(relation)) {
			return "child";
		} else if ("父母".equals(relation)) {
			return "father";
		} else if ("兄弟姐妹".equals(relation)) {
			return "other_relative";
		} else if ("其他亲属".equals(relation)) {
			return "other_relative";
		} else if ("朋友".equals(relation)) {
			return "friend";
		} else if ("同事".equals(relation)) {
			return "coworker";
		} else if ("同学".equals(relation)) {
			return "others";
		} else if ("单位".equals(relation)) {
			return "others";
		} else if ("其他".equals(relation)) {
			return "others";
		} else {
			return "others";
		}
	}

	public int addApplyInfo(ClUsrApply usrApply) {
		return applyDao.insert(usrApply);
	}

	public void pmRePreCredit(ClUsr usr, String policyNo,DecisionReq od,boolean isSendSms) throws Exception {
		
		String usrCde = usr.getUSR_CDE();
		String aid = usrCde+"_"+policyNo;
		
		ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(usrCde);

		String ytb_no = crdtExt.getUSR_APPSEQ();
		ClIdCardInfo idCardInfo = idCardDao.getByUsrCode(usrCde);
		String id_no = idCardInfo.getID_NO();
		long pc_start = new Date().getTime();
		JSONObject obj = reapi.getLimit(usrCde, id_no);
		long pc_end = new Date().getTime();
		logger.info(aid+",获取白名单额度:"+obj.toJSONString());
		String time = obj.getString("time");
		String code = obj.getString("code");
		String amount = obj.getString("amount");
		String isWhiteList = obj.getString("isWhiteList");
		BigDecimal amt = new BigDecimal(amount);
		
		ClPrdInfo prd = prdDao.getByPrdCde("TQD");
		crdtExt.setPRD_CDE("TQD");
		crdtExt.setADJ_AMT(amt);
		crdtExt.setADJ_AMT_MAX(amt);
		crdtExt.setADJ_AMT_MIN(amt);
		crdtExt.setADJ_RAT(prd.getINT_RAT());
		if(isWhiteList.equals("N")) {
			crdtExt.setCRDTEXT_TYPE("unkown");
			usr.setUSR_GRD("unkown");
		}else {
			usr.setUSR_GRD("innerWhiteList");
		}
		if(!usr.getUSR_GRD().contains("gonganPoor")) {
			usrDao.updateUsrGrd(usr);
		}
		String date  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		net.sf.json.JSONObject paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000005-1", ytb_no,date));
		ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000005-1", "投保单模板（产险-信托）", "1", "签章处", "", usr.getUSR_CDE(), "3-2", "","CI05");
		
		paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000006-1", ytb_no,date));
		ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000006-1", "个人征信授权书（阳光）--央行", "1", "签章处", "", usr.getUSR_CDE(), "3-3", "","CI06");

		paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000007-1", ytb_no,date));
		ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000007-1", "个人信息查询使用授权书", "1", "签章处", "", usr.getUSR_CDE(), "3-4", "","CI06");

		try {
			boolean htRes = contractWithPolicyNoSerivce.dealContractWithPolicyNo(policyNo);
			if(!htRes) {
				 logger.info(aid+",上传云影像失败:"+htRes);
				return;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			 logger.info(aid+",上传云影像失败:");
			return;
		}
		
		String msg = "";
		//调用决策
		long start = new Date().getTime();
		JSONObject resJson = reapi.applyDecision(od);
		long end = new Date().getTime();
		logger.info(aid+",调用决策授信:"+resJson.toJSONString());
		ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DECISION_SERVICE, DateUtils.getNowFullFmt(), (int)(end-start), JSONObject.toJSONString(od), "SUCCESS", resJson.toJSONString());
		apiInfoDao.insert(apiInfo);
		System.out.println(resJson);
		
		// 获取审核中状态的申请
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usr.getUSR_CDE());
		if (idCard == null) {
			msg = "ClIdCardInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
			return;
		}
		String idNo = idCard.getID_NO();
		String prdCde = "TQD";
		
		

		// 获取衍生数据
		start = new Date().getTime();
		JSONObject result = reapi.getDerivedDataJSONObject(usr.getUSR_CDE(), idNo,
												policyNo); 
		end = new Date().getTime();
		logger.info(aid+",调用决策获取衍生数据:"+result.toJSONString());
		JSONObject invokeP = new JSONObject();
		invokeP.put("usrCde", usr.getUSR_CDE());
		invokeP.put("idNo", idNo);
		invokeP.put("policyNo", policyNo);
		
		if ("error".equals(result.getString("code"))) {
			msg = "保单号为:" + policyNo + "的订单获取衍生数据错误。";
			apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int)(end-start), invokeP.toJSONString(), "FAIL", result.toJSONString());
			apiInfoDao.insert(apiInfo);
			return;
		}
		if ("ok".equals(result.getString("code"))) {
			int j = 0;
			if ( result.getJSONObject("data").isEmpty()) {//
				msg += "保单号为:" + policyNo + "的订单未能获取衍生数据。";
				apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int)(end-start), invokeP.toJSONString(), "FAIL", result.toJSONString());
				apiInfoDao.insert(apiInfo);
				return;
			} else {
				apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int)(end-start), invokeP.toJSONString(), "SUCCESS", result.toJSONString());
				apiInfoDao.insert(apiInfo);
				ClCapChannel capChannel = capChannelDao.getByCapSeq("1");
				ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
				if (prdInfo == null) {
					msg = "ClPrdInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					return;
				}
				BigDecimal policyRate4CS = prdInfo.getPREMIUM_RATE().multiply(new BigDecimal("100"));
				ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usr.getUSR_CDE());
				if (usrInfo == null) {
					msg = "ClUsrInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					return;
				}
				List<ClUsrCnts> usrCntList = usrCntsDao.getByUsrCode(usr.getUSR_CDE());
				JSONObject usrCnts = parseUsrCntsList(usrCntList);
				if (usrCnts.isEmpty()) {
					msg = "ClUsrCnts is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					return;
				}
				ClBnkCrd bankcard = bnkCrdDao.getUsrDefaultCardByUsrCode(usr.getUSR_CDE());
				if (bankcard == null) {
					msg = "ClBnkCrd is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					return;
				}
				BigDecimal premium = prdInfo.getPREMIUM_RATE();
//				prdInfo.setPREMIUM_RATE(premium);

				ClUsrApply usrApply = new ClUsrApply();
				usrApply.setPolicyNo(policyNo);
				usrApply.setApplyAmt("0");
				usrApply.setApplyTnr("0");
				usrApply.setApplyFor("");
				usrApply.setPrdCde("TQD");
				usrApply.setCrtDt(crdtExt.getCRT_DT());
				usrApply.setIntRate(prdInfo.getINT_RAT().toString());
				usrApply.setPremiumRate(premium.toString());

				// PM
				
				
				 String pmXml = new PMXmlStringUtils().getPMRequestXML("1",capChannel, prdInfo,
						 usrApply, usr, usrInfo, idCard, usrCnts, result.getJSONObject("data"),premium,crdtExt);
				 start = new Date().getTime();
				 String pmResult = pmapi.preCredExt(pmXml);
				 end = new Date().getTime();
				 logger.info(aid+",调用PM预授信:"+pmXml);
				 logger.info(aid+",调用PM预授信:"+pmResult);
				 JSONObject rJsonObject = new PMXMlResultUtils().parseStrategyResultXML(pmResult);
				
				 JSONObject rJsonObject2 = new PMXMlResultUtils().parseDecisionResultXML(pmResult);
				 
				 String maxAmount = rJsonObject.getString("maxAmount");
				 String detail = rJsonObject2.get("detail").toString();
				 String default_reason = rJsonObject2.getString("default_reason");
				 String system_decision = rJsonObject2.getString("system_decision");
				 
				 if((StringUtils.isNotEmpty(maxAmount) && "0.00".equals(maxAmount)) || !system_decision.equals("A")) {
					 apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM, AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int)(end-start), pmXml, "FAIL", pmResult);
					 apiInfoDao.insert(apiInfo);
				 } else {
					 apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM, AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int)(end-start), pmXml, "SUCCESS", pmResult);
					 apiInfoDao.insert(apiInfo);
				 }
				 
				 crdtExt.setST_CDE(system_decision);
				 crdtExt.setDETAIL(default_reason+":"+detail);
				 if("ok".equalsIgnoreCase(code)) {
						if("0.00".equals(amount) || !system_decision.equals("A")) {
							apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.PRECREDIT_SERVICE, DateUtils.getNowFullFmt(), (int)(pc_end-pc_start), usrCde+","+id_no, "FAIL", obj.toJSONString());
							apiInfoDao.insert(apiInfo);
							crdtExt.setSTATUS("10");
						}else {
							crdtExt.setSTATUS("11");
							crdtExt.setCRDTEXT_TYPE("innerWhiteList");
							apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.PRECREDIT_SERVICE, DateUtils.getNowFullFmt(), (int)(pc_end-pc_start), usrCde+","+id_no, "SUCCESS", obj.toJSONString());
							apiInfoDao.insert(apiInfo);
							ClUsrLmtamt lmtAmt = lmtAmtService.getByUsrCde(usrCde);
							if(lmtAmt == null) {
								lmtAmtService.add(usrCde, amt);
							}else {
								lmtAmtService.updateCreditAmt(usrCde, amt);
							}
							
						}
						ClUsrLmtamt lmtAmt = lmtAmtService.getByUsrCde(usrCde);
						List<ClUsrApply> list = applyDao.getByUsrCde(usrCde);
						String falg = "否";
						for (ClUsrApply clUsrApply : list) {
							if(StringUtils.isNotEmpty(clUsrApply.getDebtStatus())) {
								falg = "是";
							}
						}
						Reporter r = new Reporter();
						ReqJsonUtils jsonUtils = new ReqJsonUtils();
						String jsonStr = jsonUtils.getJsonStr(usr, idCardInfo, crdtExt, lmtAmt, falg);
						r.report(jsonStr);
					}else {
						crdtExt.setSTATUS("01");
					}
					crdtExt.setMDF_DT(time);
					crdtExt.setCRDTEXT_DT(time);
					crdtDao.updateByAppSeq(crdtExt);
					
					if(isSendSms) {
						String name = idCard.getCUST_NAME();
						String sex = idCard.getINDIV_SEX();
						SmsCollectionUtil smsu = new SmsCollectionUtil();
						String sendSex = smsu.getSendSex(sex);
						 
						if(crdtExt.getSTATUS().equals("11")) {
							String[] params = {name,crdtExt.getADJ_AMT().toString(),sendSex};
							long s = new Date().getTime();
							boolean sendResult = smsu.sendSms(usr.getUSR_TEL(), AppNormalConstants.CREDIT_SUCC, params);//true;
							long e = new Date().getTime();
							String status = sendResult==true?"SUCCESS":"FAIL";
							apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME, AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int)(e-s), sms.getContentByConditonAndParams(AppNormalConstants.CREDIT_SUCC, params), status, new Boolean(sendResult).toString());
							apiInfoDao.insert(apiInfo);
						} else if(crdtExt.getSTATUS().equals("10")) {
							String[] params = {name,sendSex};
							long s = new Date().getTime();
							boolean sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.CREDIT_FAIL, params);//true;
							long e = new Date().getTime();
							String status = sendResult==true?"SUCCESS":"FAIL";
							apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME, AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int)(e-s), sms.getContentByConditonAndParams(AppNormalConstants.CREDIT_FAIL, params), status, new Boolean(sendResult).toString());
							apiInfoDao.insert(apiInfo);
	//						if(StringUtils.isNotEmpty(usr.getUSR_GRD()) && !"WL".equals(usr.getUSR_GRD())) {
	//							usr.setUSR_GRD("WL");
	//							usrDao.update(usr);
	//						}
						}
					}
					
			}
		}
		
		

	}

	
	
public void dealCreditCallBack(ClUsr usr) throws Exception {
		
		String usrCde = usr.getUSR_CDE();
		
		boolean isSendSms = true;
		ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(usrCde);

		String ytb_no = crdtExt.getUSR_APPSEQ();
		
		String aid = usrCde+"_"+ytb_no;
		
		ClIdCardInfo idCardInfo = idCardDao.getByUsrCode(usrCde);
		String id_no = idCardInfo.getID_NO();
		long pc_start = new Date().getTime();
		JSONObject obj = reapi.getLimit(usrCde, id_no);
		long pc_end = new Date().getTime();
		logger.info(aid+",获取白名单额度:"+obj.toJSONString());
		String time = obj.getString("time");
		String code = obj.getString("code");
		String amount = obj.getString("amount");
		String isWhiteList = obj.getString("isWhiteList");
		BigDecimal amt = new BigDecimal(amount);
		
		ClPrdInfo prd = prdDao.getByPrdCde("TQD");
		crdtExt.setPRD_CDE("TQD");
		crdtExt.setADJ_AMT(amt);
		crdtExt.setADJ_AMT_MAX(amt);
		crdtExt.setADJ_AMT_MIN(amt);
		crdtExt.setADJ_RAT(prd.getINT_RAT());
		if(isWhiteList.equals("N")) {
			crdtExt.setCRDTEXT_TYPE("unkown");
			usr.setUSR_GRD("unkown");
		}else {
			usr.setUSR_GRD("innerWhiteList");
		}
		if(!usr.getUSR_GRD().contains("gonganPoor")) {
			usrDao.updateUsrGrd(usr);
		}
		String msg = "";
		
		// 获取审核中状态的申请
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usr.getUSR_CDE());
		if (idCard == null) {
			msg = "ClIdCardInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
			return;
		}
		String idNo = idCard.getID_NO();
		String prdCde = "TQD";
		
		

		// 获取衍生数据
		long start = new Date().getTime();
		JSONObject result = reapi.getDerivedDataJSONObject(usr.getUSR_CDE(), idNo,
				ytb_no); 
		long end = new Date().getTime();
		logger.info(aid+",调用决策获取衍生数据:"+result.toJSONString());
		JSONObject invokeP = new JSONObject();
		invokeP.put("usrCde", usr.getUSR_CDE());
		invokeP.put("idNo", idNo);
		invokeP.put("policyNo", ytb_no);
		
		if ("error".equals(result.getString("code"))) {
			msg = "保单号为:" + ytb_no + "的订单获取衍生数据错误。";
			ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int)(end-start), invokeP.toJSONString(), "FAIL", result.toJSONString());
			apiInfoDao.insert(apiInfo);
			return;
		}
		if ("ok".equals(result.getString("code"))) {
			int j = 0;
			if ( result.getJSONObject("data").isEmpty()) {//
				msg += "保单号为:" + ytb_no + "的订单未能获取衍生数据。";
				ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int)(end-start), invokeP.toJSONString(), "FAIL", result.toJSONString());
				apiInfoDao.insert(apiInfo);
				return;
			} else {
				ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int)(end-start), invokeP.toJSONString(), "SUCCESS", result.toJSONString());
				apiInfoDao.insert(apiInfo);
				ClCapChannel capChannel = capChannelDao.getByCapSeq("1");
				ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
				if (prdInfo == null) {
					msg = "ClPrdInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					return;
				}
				BigDecimal policyRate4CS = prdInfo.getPREMIUM_RATE().multiply(new BigDecimal("100"));
				ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usr.getUSR_CDE());
				if (usrInfo == null) {
					msg = "ClUsrInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					return;
				}
				List<ClUsrCnts> usrCntList = usrCntsDao.getByUsrCode(usr.getUSR_CDE());
				JSONObject usrCnts = parseUsrCntsList(usrCntList);
				if (usrCnts.isEmpty()) {
					msg = "ClUsrCnts is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					return;
				}
				ClBnkCrd bankcard = bnkCrdDao.getUsrDefaultCardByUsrCode(usr.getUSR_CDE());
				if (bankcard == null) {
					msg = "ClBnkCrd is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					return;
				}
				BigDecimal premium = prdInfo.getPREMIUM_RATE();
//				prdInfo.setPREMIUM_RATE(premium);

				ClUsrApply usrApply = new ClUsrApply();
				usrApply.setPolicyNo(ytb_no);
				usrApply.setApplyAmt("0");
				usrApply.setApplyTnr("0");
				usrApply.setApplyFor("");
				usrApply.setPrdCde("TQD");
				usrApply.setCrtDt(crdtExt.getCRT_DT());
				usrApply.setIntRate(prdInfo.getINT_RAT().toString());
				usrApply.setPremiumRate(premium.toString());

				// PM
				
				
				 String pmXml = new PMXmlStringUtils().getPMRequestXML("1",capChannel, prdInfo,
						 usrApply, usr, usrInfo, idCard, usrCnts, result.getJSONObject("data"),premium,crdtExt);
				 start = new Date().getTime();
				 String pmResult = pmapi.preCredExt(pmXml);
				 end = new Date().getTime();
				 logger.info(aid+",调用PM预授信:"+pmXml);
				 logger.info(aid+",调用PM预授信:"+pmResult);
				 JSONObject rJsonObject = new PMXMlResultUtils().parseStrategyResultXML(pmResult);
				
				 JSONObject rJsonObject2 = new PMXMlResultUtils().parseDecisionResultXML(pmResult);
				 
				 String maxAmount = rJsonObject.getString("maxAmount");
				 String detail = rJsonObject2.get("detail").toString();
				 String default_reason = rJsonObject2.getString("default_reason");
				 String system_decision = rJsonObject2.getString("system_decision");
				 
				 if((StringUtils.isNotEmpty(maxAmount) && "0.00".equals(maxAmount)) || !system_decision.equals("A")) {
					 apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM, AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int)(end-start), pmXml, "SUCCESS", pmResult);
					 apiInfoDao.insert(apiInfo);
				 } else {
					 apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM, AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int)(end-start), pmXml, "FAIL", pmResult);
					 apiInfoDao.insert(apiInfo);
				 }
				 
				 crdtExt.setST_CDE(system_decision);
				 crdtExt.setDETAIL(default_reason+":"+detail);
				 if("ok".equalsIgnoreCase(code)) {
						if("0.00".equals(amount) || !system_decision.equals("A")) {
							apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.PRECREDIT_SERVICE, DateUtils.getNowFullFmt(), (int)(pc_end-pc_start), usrCde+","+id_no, "FAIL", obj.toJSONString());
							apiInfoDao.insert(apiInfo);
							crdtExt.setSTATUS("10");
						}else {
							crdtExt.setSTATUS("11");
							crdtExt.setCRDTEXT_TYPE("innerWhiteList");
							apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.PRECREDIT_SERVICE, DateUtils.getNowFullFmt(), (int)(pc_end-pc_start), usrCde+","+id_no, "SUCCESS", obj.toJSONString());
							apiInfoDao.insert(apiInfo);
							ClUsrLmtamt lmtAmt = lmtAmtService.getByUsrCde(usrCde);
							if(lmtAmt == null) {
								lmtAmtService.add(usrCde, amt);
							}else {
								lmtAmtService.updateCreditAmt(usrCde, amt);
							}
							
						}
						ClUsrLmtamt lmtAmt = lmtAmtService.getByUsrCde(usrCde);
						List<ClUsrApply> list = applyDao.getByUsrCde(usrCde);
						String falg = "否";
						for (ClUsrApply clUsrApply : list) {
							if(StringUtils.isNotEmpty(clUsrApply.getDebtStatus())) {
								falg = "是";
							}
						}
						Reporter r = new Reporter();
						ReqJsonUtils jsonUtils = new ReqJsonUtils();
						String jsonStr = jsonUtils.getJsonStr(usr, idCardInfo, crdtExt, lmtAmt, falg);
						r.report(jsonStr);
					}else {
						crdtExt.setSTATUS("01");
					}
					crdtExt.setMDF_DT(time);
					crdtExt.setCRDTEXT_DT(time);
					crdtDao.updateByAppSeq(crdtExt);
					
					if(isSendSms) {
						String name = idCard.getCUST_NAME();
						String sex = idCard.getINDIV_SEX();
						SmsCollectionUtil smsu = new SmsCollectionUtil();
						String sendSex = smsu.getSendSex(sex);
						 
						if(crdtExt.getSTATUS().equals("11")) {
							String[] params = {name,crdtExt.getADJ_AMT().toString(),sendSex};
							long s = new Date().getTime();
							boolean sendResult = smsu.sendSms(usr.getUSR_TEL(), AppNormalConstants.CREDIT_SUCC, params);//true;
							long e = new Date().getTime();
							String status = sendResult==true?"SUCCESS":"FAIL";
							apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME, AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int)(e-s), sms.getContentByConditonAndParams(AppNormalConstants.CREDIT_SUCC, params), status, new Boolean(sendResult).toString());
							apiInfoDao.insert(apiInfo);
						} else if(crdtExt.getSTATUS().equals("10")) {
							String[] params = {name,sendSex};
							long s = new Date().getTime();
							boolean sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.CREDIT_FAIL, params);//true;
							long e = new Date().getTime();
							String status = sendResult==true?"SUCCESS":"FAIL";
							apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME, AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int)(e-s), sms.getContentByConditonAndParams(AppNormalConstants.CREDIT_FAIL, params), status, new Boolean(sendResult).toString());
							apiInfoDao.insert(apiInfo);
	//						if(StringUtils.isNotEmpty(usr.getUSR_GRD()) && !"WL".equals(usr.getUSR_GRD())) {
	//							usr.setUSR_GRD("WL");
	//							usrDao.update(usr);
	//						}
						}
					}
					
			}
		}
		
		

	}
	
	

	private JSONObject parseUsrCntsList(List<ClUsrCnts> usrCntsList) {
		JSONObject jsObj = new JSONObject();

		if (usrCntsList.size() == 1) {
			jsObj.put("otherName", "");
			jsObj.put("otherPhone", "");
			jsObj.put("otherRelation", "");
		}

		for (ClUsrCnts clUsrCnts : usrCntsList) {
			if (clUsrCnts.getLVL().equals("first")) {
				jsObj.put("relationName", clUsrCnts.getREL_NAME());
				jsObj.put("relationPhone", clUsrCnts.getREL_MOBILE());
				jsObj.put("relation", clUsrCnts.getREL_RELATION());
			} else if (clUsrCnts.getLVL().equals("second")) {
				jsObj.put("otherName", clUsrCnts.getREL_NAME());
				jsObj.put("otherPhone", clUsrCnts.getREL_MOBILE());
				jsObj.put("otherRelation", clUsrCnts.getREL_RELATION());
			}
		}

		return jsObj;
	}


	private String getZWCntsRelationship(String relationStr) {
		// 1:父母5:子女8:兄弟姐妹99:其他
		if ("父母".equals(relationStr)) {
			return "1";
		} else if ("配偶".equals(relationStr)) {
			return "1";
		} else if ("亲戚".equals(relationStr)) {
			return "1";
		} else if ("朋友".equals(relationStr)) {
			return "99";
		}

		return "";
	}


	public void pmPreCredit(ClUsr usr, String policyNo,DecisionReq od) throws Exception {
			
			String usrCde = usr.getUSR_CDE();
			String aid = usrCde+"_"+policyNo;
			
			ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(usrCde);
			boolean flag = true;//标识,为true标识是第一次授信；
			if(crdtExt.getADJ_AMT()  == null) {
				flag = true;
			}else {
				flag = false;
			}
			
			if(!flag) {
				crdtExt.setIS_EFF("false");
				crdtDao.updateByAppSeq(crdtExt);
			}
	
			String ytb_no = crdtExt.getUSR_APPSEQ();
			ClIdCardInfo idCardInfo = idCardDao.getByUsrCode(usrCde);
			String id_no = idCardInfo.getID_NO();
			long pc_start = new Date().getTime();
			JSONObject obj = reapi.getLimit(usrCde, id_no);
			long pc_end = new Date().getTime();
			logger.info(aid+",获取白名单额度:"+obj.toJSONString());
			String time = obj.getString("time");
			String code = obj.getString("code");
			String amount = obj.getString("amount");
			String isWhiteList = obj.getString("isWhiteList");
			BigDecimal amt = new BigDecimal(amount);
			
			ClPrdInfo prd = prdDao.getByPrdCde("TQD");
			crdtExt.setPRD_CDE("TQD");
			crdtExt.setADJ_AMT(amt);
			crdtExt.setADJ_AMT_MAX(amt);
			crdtExt.setADJ_AMT_MIN(amt);
			crdtExt.setADJ_RAT(prd.getINT_RAT());
			if(isWhiteList.equals("N")) {
				crdtExt.setCRDTEXT_TYPE("unkown");
				usr.setUSR_GRD("unkown");
			}else {
				usr.setUSR_GRD("innerWhiteList");
			}
			if(!usr.getUSR_GRD().contains("gonganPoor")) {
				usrDao.updateUsrGrd(usr);
			}
			
			String date  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
			net.sf.json.JSONObject paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000005-1", ytb_no,date));
			ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000005-1", "投保单模板（产险-信托）", "1", "签章处", "", usr.getUSR_CDE(), "3-2", "","CI05");
			
			paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000006-1", ytb_no,date));
			ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000006-1", "个人征信授权书（阳光）--央行", "1", "签章处", "", usr.getUSR_CDE(), "3-3", "","CI06");
	
			paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000007-1", ytb_no,date));
			ucService.fadadaContract(idCardInfo.getCUST_NAME(), idCardInfo.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000007-1", "个人信息查询使用授权书", "1", "签章处", "", usr.getUSR_CDE(), "3-4", "","CI06");
	
			try {
				boolean htRes = contractWithPolicyNoSerivce.dealContractWithPolicyNo(policyNo);
				if(!htRes) {
					 logger.info(aid+",上传云影像失败:"+htRes);
					return;
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				 logger.info(aid+",上传云影像失败:");
				return;
			}
			
			String msg = "";
			//调用决策
			long start = new Date().getTime();
			JSONObject resJson = reapi.applyDecision(od);
			long end = new Date().getTime();
			logger.info(aid+",调用决策授信:"+resJson.toJSONString());
			ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DECISION_SERVICE, DateUtils.getNowFullFmt(), (int)(end-start), JSONObject.toJSONString(od), "SUCCESS", resJson.toJSONString());
			apiInfoDao.insert(apiInfo);
			System.out.println(resJson);
			
			// 获取审核中状态的申请
			ClIdCardInfo idCard = idCardDao.getByUsrCode(usr.getUSR_CDE());
			if (idCard == null) {
				msg = "ClIdCardInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
				return;
			}
			String idNo = idCard.getID_NO();
			String prdCde = "TQD";
			
			
	
			// 获取衍生数据
			start = new Date().getTime();
			JSONObject result = reapi.getDerivedDataJSONObject(usr.getUSR_CDE(), idNo,
													policyNo); 
			end = new Date().getTime();
			logger.info(aid+",调用决策获取衍生数据:"+result.toJSONString());
			JSONObject invokeP = new JSONObject();
			invokeP.put("usrCde", usr.getUSR_CDE());
			invokeP.put("idNo", idNo);
			invokeP.put("policyNo", policyNo);
			
			if ("error".equals(result.getString("code"))) {
				msg = "保单号为:" + policyNo + "的订单获取衍生数据错误。";
				apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int)(end-start), invokeP.toJSONString(), "FAIL", result.toJSONString());
				apiInfoDao.insert(apiInfo);
				return;
			}
			if ("ok".equals(result.getString("code"))) {
				int j = 0;
				if ( result.getJSONObject("data").isEmpty()) {//
					msg += "保单号为:" + policyNo + "的订单未能获取衍生数据。";
					apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int)(end-start), invokeP.toJSONString(), "FAIL", result.toJSONString());
					apiInfoDao.insert(apiInfo);
					return;
				} else {
					apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int)(end-start), invokeP.toJSONString(), "SUCCESS", result.toJSONString());
					apiInfoDao.insert(apiInfo);
					ClCapChannel capChannel = capChannelDao.getByCapSeq("1");
					ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
					if (prdInfo == null) {
						msg = "ClPrdInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
						return;
					}
					BigDecimal policyRate4CS = prdInfo.getPREMIUM_RATE().multiply(new BigDecimal("100"));
					ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usr.getUSR_CDE());
					if (usrInfo == null) {
						msg = "ClUsrInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
						return;
					}
					List<ClUsrCnts> usrCntList = usrCntsDao.getByUsrCode(usr.getUSR_CDE());
					JSONObject usrCnts = parseUsrCntsList(usrCntList);
					if (usrCnts.isEmpty()) {
						msg = "ClUsrCnts is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
						return;
					}
					ClBnkCrd bankcard = bnkCrdDao.getUsrDefaultCardByUsrCode(usr.getUSR_CDE());
					if (bankcard == null) {
						msg = "ClBnkCrd is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
						return;
					}
					BigDecimal premium = prdInfo.getPREMIUM_RATE();
//					prdInfo.setPREMIUM_RATE(premium);
	
					ClUsrApply usrApply = new ClUsrApply();
					usrApply.setPolicyNo(policyNo);
					usrApply.setApplyAmt("0");
					usrApply.setApplyTnr("0");
					usrApply.setApplyFor("");
					usrApply.setPrdCde("TQD");
					usrApply.setCrtDt(crdtExt.getCRT_DT());
					usrApply.setIntRate(prdInfo.getINT_RAT().toString());
					usrApply.setPremiumRate(premium.toString());
	
					// PM
					
					
					 String pmXml = new PMXmlStringUtils().getPMRequestXML("1",capChannel, prdInfo,
							 usrApply, usr, usrInfo, idCard, usrCnts, result.getJSONObject("data"),premium,crdtExt);
					 start = new Date().getTime();
					 String pmResult = pmapi.preCredExt(pmXml);
					 end = new Date().getTime();
					 logger.info(aid+",调用PM预授信:"+pmXml);
					 logger.info(aid+",调用PM预授信:"+pmResult);
					 JSONObject rJsonObject = new PMXMlResultUtils().parseStrategyResultXML(pmResult);
					
					 JSONObject rJsonObject2 = new PMXMlResultUtils().parseDecisionResultXML(pmResult);
					 
					 String maxAmount = rJsonObject.getString("maxAmount");
					 String detail = rJsonObject2.get("detail").toString();
					 String default_reason = rJsonObject2.getString("default_reason");
					 String system_decision = rJsonObject2.getString("system_decision");
					 
					 if((StringUtils.isNotEmpty(maxAmount) && "0.00".equals(maxAmount)) || !system_decision.equals("A")) {
						 apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM, AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int)(end-start), pmXml, "FAIL", pmResult);
						 apiInfoDao.insert(apiInfo);
					 } else {
						 apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM, AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int)(end-start), pmXml, "SUCCESS", pmResult);
						 apiInfoDao.insert(apiInfo);
					 }
					 
					 crdtExt.setST_CDE(system_decision);
					 crdtExt.setDETAIL(default_reason+":"+detail);
					 if("ok".equalsIgnoreCase(code)) {
							if("0.00".equals(amount) || !system_decision.equals("A")) {
								apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.PRECREDIT_SERVICE, DateUtils.getNowFullFmt(), (int)(pc_end-pc_start), usrCde+","+id_no, "FAIL", obj.toJSONString());
								apiInfoDao.insert(apiInfo);
								crdtExt.setSTATUS("10");
							}else {
								crdtExt.setSTATUS("11");
								crdtExt.setCRDTEXT_TYPE("innerWhiteList");
								apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE, AppNormalConstants.PRECREDIT_SERVICE, DateUtils.getNowFullFmt(), (int)(pc_end-pc_start), usrCde+","+id_no, "SUCCESS", obj.toJSONString());
								apiInfoDao.insert(apiInfo);
								ClUsrLmtamt lmtAmt = lmtAmtService.getByUsrCde(usrCde);
								if(lmtAmt == null) {
									lmtAmtService.add(usrCde, amt);
								}else {
									lmtAmtService.updateCreditAmt(usrCde, amt);
								}
								
							}
							ClUsrLmtamt lmtAmt = lmtAmtService.getByUsrCde(usrCde);
							List<ClUsrApply> list = applyDao.getByUsrCde(usrCde);
							String falg = "否";
							for (ClUsrApply clUsrApply : list) {
								if(StringUtils.isNotEmpty(clUsrApply.getDebtStatus())) {
									falg = "是";
								}
							}
							Reporter r = new Reporter();
							ReqJsonUtils jsonUtils = new ReqJsonUtils();
							String jsonStr = jsonUtils.getJsonStr(usr, idCardInfo, crdtExt, lmtAmt, falg);
							r.report(jsonStr);
						}else {
							crdtExt.setSTATUS("01");
						}
						crdtExt.setMDF_DT(time);
						crdtExt.setCRDTEXT_DT(time);
						if(flag) {
							int i = crdtDao.updateByAppSeq(crdtExt);
						}else {
							crdtExt.setCRDTEXT_SEQ(-1);
							crdtExt.setIS_EFF("true");
							int i = crdtDao.insert(crdtExt);
						}
						String name = idCard.getCUST_NAME();
						String sex = idCard.getINDIV_SEX();
						SmsCollectionUtil smsu = new SmsCollectionUtil();
						String sendSex = smsu.getSendSex(sex);
						 
						if(crdtExt.getSTATUS().equals("11")) {
							String[] params = {name,crdtExt.getADJ_AMT().toString(),sendSex};
							long s = new Date().getTime();
							boolean sendResult = smsu.sendSms(usr.getUSR_TEL(), AppNormalConstants.CREDIT_SUCC, params);//true;
							long e = new Date().getTime();
							String status = sendResult==true?"SUCCESS":"FAIL";
							apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME, AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int)(e-s), sms.getContentByConditonAndParams(AppNormalConstants.CREDIT_SUCC, params), status, new Boolean(sendResult).toString());
							apiInfoDao.insert(apiInfo);
						} else if(crdtExt.getSTATUS().equals("10")) {
							String[] params = {name,sendSex};
							long s = new Date().getTime();
							boolean sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.CREDIT_FAIL, params);//true;
							long e = new Date().getTime();
							String status = sendResult==true?"SUCCESS":"FAIL";
							apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME, AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int)(e-s), sms.getContentByConditonAndParams(AppNormalConstants.CREDIT_FAIL, params), status, new Boolean(sendResult).toString());
							apiInfoDao.insert(apiInfo);
	//						if(StringUtils.isNotEmpty(usr.getUSR_GRD()) && !"WL".equals(usr.getUSR_GRD())) {
	//							usr.setUSR_GRD("WL");
	//							usrDao.update(usr);
	//						}
						}
						
				}
			}
	
		}
	
	
}