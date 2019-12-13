package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jeeplus.modules.app.api.elecpolicy.ElePolicyApi;
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
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.utils.CacheHelper;
import com.jeeplus.modules.app.utils.HttpConUtils;
import com.jeeplus.modules.app.web.LoanPrePaymentController;

@Service
public class ClApplyService {

	private final static Logger logger = LoggerFactory.getLogger(ClApplyService.class);

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
	private ClApiInfoDao apiInfoDao;

	private RulesEngineApi reapi = new RulesEngineApi();

	private PMApi pmapi = new PMApi();

	private SendInvalidInsOp siOp = new SendInvalidInsOp();

	private SmsCollectionUtil sms = new SmsCollectionUtil();

	@Autowired
	private ClFadadaParamsService fpService;

	@Autowired
	private ClUsrContractService ucService;

	@Autowired
	private ClUsrDao usrDao;
	
	@Autowired
	private ClUsrStsMdfByGonganService clUsrStsService;

	public List<ClUsrApply> getByUsrCde(String usrCde) {
		return applyDao.getByUsrCde(usrCde);
	}

	public JSONObject decision(ClUsr usr, String prdCde, String loan_number, Date sendDt, JSONObject other) {
		String usrCde = usr.getUSR_CDE();
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String user_id = usrCde;
		String name = idCard.getCUST_NAME();
		String id_no = idCard.getID_NO();
		String app_version = usr.getUSR_CLNTVER();
		String os_version = usr.getUSR_OS() + " " + usr.getUSR_OSVER();
		String time = DateUtils.formatDate(sendDt, "yyyy-MM-dd HH:mm:ss:SSS");
		ClBnkCrd bnkCrd = bnkCrdDao.getUsrDefaultCardByUsrCode(usrCde);
		List<ClUsrCnts> list = usrCntsDao.getByUsrCode(usrCde);
		String cntsName = "";
		String cntsMobile = "";
		String cntsRelation = "";

		for (ClUsrCnts clUsrCnt : list) {
			if (clUsrCnt.getLVL().equals("first")) {
				cntsName = clUsrCnt.getREL_NAME();
				cntsMobile = clUsrCnt.getREL_MOBILE();
				cntsRelation = getRelationCode(clUsrCnt.getREL_RELATION());
			}
		}

		other.put("idNo", id_no);
		other.put("idCardType", "0");
		other.put("name", name);
		other.put("mobile", usr.getUSR_TEL());
		other.put("bankCardNo", bnkCrd.getAPPL_CARD_NO());
		other.put("contactName", "");
		other.put("contactIdNumber", "");
		other.put("contactMobile", "");
		other.put("contactRelation", "");

		DecisionReq od = new DecisionReq();
		od.setApp_key("wd");
		od.setPro_type("wd");// 待定
		od.setPrd_code(prdCde);
		od.setToken("");
		od.setOrg_code("");
		od.setSession_id("");
		od.setMessage_id("");
		od.setUser_id(user_id);
		od.setName(name);
		od.setId_no(id_no);
		od.setLoan_number(loan_number);
		od.setMobile(usr.getUSR_TEL());
		od.setBank_channel("FOTIC");
		od.setTime(time);
		od.setApp_version(app_version);
		od.setOs_version(os_version);
		od.setSdk_version("");
		od.setOther(other);
		od.setChannel(usr.getAPP_CHANNEL());

		JSONObject json = reapi.applyDecision(od);
		return json;
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

	public void realApply(ClUsr usr, String policyNo, DecisionReq od) throws Exception {

		Date nowDate = new Date();
		boolean isDelaySendSms = false;
		String delaySendSmsStartPoint = AppCommonConstants.getValStr("DELAYSENDSMSTIMESTART");
		String nowYMD = DateUtils.formatDate(nowDate, "yyyyMMdd");
		String delaySendSmsDateStr = nowYMD + delaySendSmsStartPoint + "00";
		Date pointDate = DateUtils.parseDate(delaySendSmsDateStr);

		if (nowDate.getTime() >= pointDate.getTime()) {
			isDelaySendSms = true;
		}

		String msg = "";
		String aid = usr.getUSR_CDE() + "_" + policyNo;
		String usrCde = usr.getUSR_CDE();
		ClIdCardInfo idCardInfo = idCardDao.getByUsrCode(usrCde);
		String id_no = idCardInfo.getID_NO();
		long pc_start = new Date().getTime();
		JSONObject edu = reapi.getLimit(usrCde, id_no);
		long pc_end = new Date().getTime();
		logger.info(aid + ",获取白名单额度:" + edu.toJSONString());
		String isWhiteList = edu.getString("isWhiteList");
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		if (apply == null) {
			Thread.sleep(10000);
			apply = applyDao.getByPolicyNo(policyNo);
			if (apply == null) {
				msg = "ClUsrApply is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
				addFailedFirst(usr, policyNo, "DATA ERROR : ClUsrApply");
				return;
			}
		}
		// 获取审核中状态的申请
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usr.getUSR_CDE());
		if (idCard == null) {
			msg = "ClIdCardInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
			addFailedFirst(usr, policyNo, "DATA ERROR : ClIdCardInfo");
			return;
		}
		String idNo = idCard.getID_NO();
		String prdCde = apply.getPrdCde();

		net.sf.json.JSONObject paramsHt = null;

		String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		
		paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000006-1", policyNo,date));
		ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
				"YGSDPH80000006-1", "个人征信授权书（阳光）--央行", "1", "签章处", "", usr.getUSR_CDE(), "3-3", "", "CI06");

		paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000007-1", policyNo,date));
		ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
				"YGSDPH80000007-1", "个人信息查询使用授权书", "1", "签章处", "", usr.getUSR_CDE(), "3-4", "", "CI06");

		paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000009-1", policyNo,date));
		ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
				"YGSDPH80000009-1", "贷款协议（信用类）信托-阳光-附件一个人信息授权书", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-2", "",
				"CI08");

		try {
			boolean htRes = contractWithPolicyNoSerivce.dealContractWithPolicyNo(policyNo);
			if(!htRes) {
				 logger.info(aid+",上传云影像失败:"+htRes);
				 addFailedFirst(usr, policyNo, "DATA ERROR : Up2Youpaiyun");
				return;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			 logger.info(aid+",上传云影像失败:");
			 addFailedFirst(usr, policyNo, "DATA ERROR : Up2Youpaiyun");
			return;
		}

		// 调用决策
		long start = new Date().getTime();
		JSONObject resJson = reapi.applyDecision(od);
		long end = new Date().getTime();
		ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE,
				AppNormalConstants.DECISION_SERVICE, DateUtils.getNowFullFmt(), (int) (end - start),
				JSONObject.toJSONString(od), "SUCCESS", resJson.toJSONString());
		apiInfoDao.insert(apiInfo);

		boolean flag = true;
		logger.info(aid + ":调用决策参数:" + od.toString());
		logger.info(aid + ":调用决策结果:" + resJson.toJSONString());

		// 获取衍生数据
		start = new Date().getTime();
		JSONObject result = reapi.getDerivedDataJSONObject(usr.getUSR_CDE(), idNo, policyNo);
		end = new Date().getTime();
		logger.info(aid + ":获取衍生数据参数:" + usr.getUSR_CDE() + "," + idNo + "," + policyNo);
		logger.info(aid + ":获取衍生数据结果:" + result.toJSONString());
		JSONObject invokeP = new JSONObject();
		invokeP.put("usrCde", usr.getUSR_CDE());
		invokeP.put("idNo", idNo);
		invokeP.put("policyNo", policyNo);

		if ("error".equals(result.getString("code"))) {
			msg = "保单号为:" + policyNo + "的订单获取衍生数据错误。";
			addFailedFirst(usr, policyNo, "DerivedData");
			apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE,
					AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int) (end - start),
					invokeP.toJSONString(), "FAIL", result.toJSONString());
			apiInfoDao.insert(apiInfo);
			return;
		}
		if ("ok".equals(result.getString("code"))) {
			int j = 0;
			if (result.getJSONObject("data").isEmpty()) {//
				msg += "保单号为:" + policyNo + "的订单未能获取衍生数据。";
				addFailedFirst(usr, policyNo, "DerivedData");
				apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE,
						AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int) (end - start),
						invokeP.toJSONString(), "FAIL", result.toJSONString());
				apiInfoDao.insert(apiInfo);
				return;
			} else {
				
				apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE,
						AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int) (end - start),
						invokeP.toJSONString(), "SUCCESS", result.toJSONString());
				apiInfoDao.insert(apiInfo);
				ClCapChannel capChannel = capChannelDao.getByCapSeq("1");// getByUsrSource();
				ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
				if (prdInfo == null) {
					msg = "ClPrdInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					addFailedFirst(usr, policyNo, "DATA ERROR : ClPrdInfo");
					return;
				}
				ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usr.getUSR_CDE());
				if (usrInfo == null) {
					msg = "ClUsrInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					addFailedFirst(usr, policyNo, "DATA ERROR : ClUsrInfo");
					return;
				}
				List<ClUsrCnts> usrCntList = usrCntsDao.getByUsrCode(usr.getUSR_CDE());
				JSONObject usrCnts = parseUsrCntsList(usrCntList);
				if (usrCnts.isEmpty()) {
					msg = "ClUsrCnts is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					addFailedFirst(usr, policyNo, "DATA ERROR : ClUsrCnts");
					return;
				}
				ClBnkCrd bankcard = bnkCrdDao.getOneByBankNoWithOutSts(apply.getBankCardNo());
				if (bankcard == null) {
					msg = "ClBnkCrd is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					addFailedFirst(usr, policyNo, "DATA ERROR : ClBnkCrd");
					return;
				}
				// 重要
				// Map<String, String> params = new HashMap<String, String>();
				// params.put("PRD_CDE", apply.getPrdCde());
				// params.put("PAY_TERM", apply.getApplyTnr());
				// ClPremiumDetail clPreDetail = premiumDetailDao.getByPrdAndTrem(params);
				// if (clPreDetail == null) {
				// msg = "ClPremiumDetail is null for usr : " + usr.getUSR_CDE() + "=" +
				// usr.getUSR_TEL();
				// addFailedFirst(usr, policyNo, "DATA ERROR : ClPremiumDetail");
				// return;
				// }
				BigDecimal premium = new BigDecimal(apply.getPremiumRate());
				ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(usr.getUSR_CDE());
				if (isWhiteList.equals("N")) {
					crdtExt.setCRDTEXT_TYPE("unkown");
					usr.setUSR_GRD("unkown");
				} else {
					usr.setUSR_GRD("innerWhiteList");
				}
				if(!usr.getUSR_GRD().contains("gonganPoor")) {
					usrDao.updateUsrGrd(usr);
				}
				// prdInfo.setPREMIUM_RATE(premium);

				

				// PM
				// String pmXml = new PMXmlStringUtils().getPMRequestXML("1",capChannel,
				// prdInfo,
				// apply, usr, usrInfo, idCard, usrCnts,
				// result.getJSONObject("data"),premium,crdtExt);
				// System.out.println(pmXml);
				// start = new Date().getTime();
				// String pmResult = pmapi.preCredExt(pmXml);
				// end = new Date().getTime();
				// JSONObject rJsonObject = new
				// PMXMlResultUtils().parseStrategyResultXML(pmResult);
				// String maxAmount = rJsonObject.getString("maxAmount");
				String pmXml2 = "";
				String pmResult2 = "";
				String default_reason = "";
				String system_decision = "";
				String detail = "";
				try {
					pmXml2 = new PMXmlStringUtils().getPMRequestXML("2", capChannel, prdInfo, apply, usr, usrInfo,
							idCard, usrCnts, result.getJSONObject("data"), premium, crdtExt);
					logger.info(aid + ":PM决策参数:" + pmXml2);
					start = new Date().getTime();
					pmResult2 = pmapi.preCredExt(pmXml2);
					end = new Date().getTime();
					apply.setPmDt(DateUtils.getNowFullFmt());
					logger.info(aid + ":PM决策结果:" + pmResult2);
					JSONObject rJsonObject2 = new PMXMlResultUtils().parseDecisionResultXML(pmResult2);
					default_reason = rJsonObject2.getString("default_reason");
					system_decision = rJsonObject2.getString("system_decision");
					detail = rJsonObject2.get("detail").toString();
					//修改用户状态
					clUsrStsService.getGonganSts(usr, result.getJSONObject("data"));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					addFailedFirst(usr, policyNo, "DerivedData");
					return;
				}

				
				// String pmXml2 = new PMXmlStringUtils().getPMRequestXML("2",capChannel,
				// prdInfo,
				// apply, usr, usrInfo, idCard, usrCnts,
				// result.getJSONObject("data"),premium,crdtExt);
				// logger.info(aid + ":PM决策参数:"+pmXml2);
				// start = new Date().getTime();
				// String pmResult2 = pmapi.preCredExt(pmXml2);
				// end = new Date().getTime();
				// apply.setPmDt(DateUtils.getNowFullFmt());
				// logger.info(aid + ":PM决策结果:"+pmResult2);
				// JSONObject rJsonObject2 = new
				// PMXMlResultUtils().parseDecisionResultXML(pmResult2);
				// String default_reason = rJsonObject2.getString("default_reason");
				// String system_decision = rJsonObject2.getString("system_decision");
				// String detail = rJsonObject2.get("detail").toString();
				if (system_decision.equals("A")) {
					ClUsrLmtamt lmtAmt = usrLmtAmtService.getByUsrCde(usr.getUSR_CDE());

					BigDecimal lmtCrdAmt = lmtAmt.getCreditAmount();
					BigDecimal usedAmt = lmtAmt.getUsedAmount();
					BigDecimal repayAmt = lmtAmt.getRepayAmount();
					BigDecimal usrAmt = lmtCrdAmt.subtract(usedAmt).add(repayAmt);

					if (usrAmt.compareTo(new BigDecimal("0")) == -1) {
						apply.setStCde("10");
						apply.setCreditNo(crdtExt.getUSR_APPSEQ());
						apply.setTrdResult(system_decision);
						apply.setTrdDetail("申请额度大于可用额度-" + default_reason + ":" + detail);
						apply.setMdfDt(DateUtils.getNowFullFmt());
						applyDao.updateByPrimaryKeySelective(apply);
						// 失败恢复额度
						usrLmtAmtService.updateUsedAmtRecovery(usr.getUSR_CDE(), new BigDecimal(apply.getApplyAmt()));
						return;
					}
					apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM,
							AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int) (end - start),
							pmXml2, "SUCCESS", pmResult2);
					apiInfoDao.insert(apiInfo);
					
					apply.setStCde("12");
					apply.setTrdResult(system_decision);
					apply.setTrdDetail(default_reason + ":" + detail);
					apply.setMdfDt(DateUtils.getNowFullFmt());
					apply.setCreditNo(crdtExt.getUSR_APPSEQ());
					apply.setRmk(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "apply_key", apply,
							AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
					//Object obj = CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "apply_key");
					//System.out.println("缓存值" + obj.toString());
					int i = applyDao.updateByPrimaryKeySelective(apply);

					if (i < 0 ) {
						msg += "保单号为:" + policyNo + "的订单入库失败。";
						addFailedFirst(usr, policyNo, "DATA ERROR : M ClUsrApply F");
						return;
					}
					
					// 无效保单
					String invaliPolicyXml = new ReqXmlStringUtils().getReqXmlStr(capChannel, prdInfo, apply, usr, usrInfo,
							idCard, usrCnts, premium);
					start = new Date().getTime();
					JSONObject ipolicyResult = siOp.inputPolicy(invaliPolicyXml);
					end = new Date().getTime();
					System.out.println(ipolicyResult);
					String clientFlag = ipolicyResult.getString("clientFlag");
					if (!clientFlag.equals("success")) {
						msg += "保单号为:" + policyNo + "的订单无效保单失败。";
						addFailedFirst(usr, policyNo, "InvaliPolicyOnly");
						apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.INVALID_POLICY,
								AppNormalConstants.INVALID_POLICY, DateUtils.getNowFullFmt(), (int) (end - start),
								invaliPolicyXml, "FAIL", ipolicyResult.toJSONString());
						apiInfoDao.insert(apiInfo);
//						return;
					}
					apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.INVALID_POLICY,
							AppNormalConstants.INVALID_POLICY, DateUtils.getNowFullFmt(), (int) (end - start),
							invaliPolicyXml, "SUCCESS", ipolicyResult.toJSONString());
					apiInfoDao.insert(apiInfo);

					// 暂时不开放
					ElePolicyApi api = new ElePolicyApi();
					boolean elePolicy = api.sendElePolicy(capChannel, prdInfo, usr, idCard, apply, usrInfo, premium);
					if (!elePolicy) {
						addFailedFirst(usr, policyNo, "ElePolicy");
						return;
					}
				} else {
					apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM,
							AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int) (end - start),
							pmXml2, "FAIL", pmResult2);
					apiInfoDao.insert(apiInfo);
					apply.setStCde("10");
					apply.setCreditNo(crdtExt.getUSR_APPSEQ());
					apply.setTrdResult(system_decision);
					apply.setTrdDetail(default_reason + ":" + detail);
					apply.setMdfDt(DateUtils.getNowFullFmt());
					applyDao.updateByPrimaryKeySelective(apply);
					// 失败恢复额度
					usrLmtAmtService.updateUsedAmtRecovery(usr.getUSR_CDE(), new BigDecimal(apply.getApplyAmt()));
					msg += "保单号为:" + policyNo + "的订单未通过PM。";
					
					
					// 无效保单
					/*String invaliPolicyXml = new ReqXmlStringUtils().getReqXmlStr(capChannel, prdInfo, apply, usr, usrInfo,
							idCard, usrCnts, premium);
					start = new Date().getTime();
					JSONObject ipolicyResult = siOp.inputPolicy(invaliPolicyXml);
					end = new Date().getTime();
					System.out.println(ipolicyResult);
					String clientFlag = ipolicyResult.getString("clientFlag");
					if (!clientFlag.equals("success")) {
						msg += "保单号为:" + policyNo + "的订单无效保单失败。";
						addFailedFirst(usr, policyNo, "InvaliPolicy");
						apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.INVALID_POLICY,
								AppNormalConstants.INVALID_POLICY, DateUtils.getNowFullFmt(), (int) (end - start),
								invaliPolicyXml, "FAIL", ipolicyResult.toJSONString());
						apiInfoDao.insert(apiInfo);
						return;
					}
					apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.INVALID_POLICY,
							AppNormalConstants.INVALID_POLICY, DateUtils.getNowFullFmt(), (int) (end - start),
							invaliPolicyXml, "SUCCESS", ipolicyResult.toJSONString());
					apiInfoDao.insert(apiInfo);

					// 暂时不开放
					ElePolicyApi api = new ElePolicyApi();
					boolean elePolicy = api.sendElePolicy(capChannel, prdInfo, usr, idCard, apply, usrInfo, premium);
					if (!elePolicy) {
						addFailedFirst(usr, policyNo, "ElePolicy");
						return;
					}*/
					
					return;
				}
				// 电子保单

				// 同步产品信息
				// if(!syncPrd(prdCde)) {
				// msg += "保单号为:"+policyNo+"的订单同步产品失败。";
				// continue;
				// }
				// 同步放款渠道
				// if(!syncChannel(capChannel, prdInfo)) {
				// msg += "保单号为:"+policyNo+"的订单同步放款渠道失败。";
				// continue;
				// }
				// 申请放款usrLmtAmtService : ClUsrLmtamtService

				boolean loanApply = loanApplyReq(capChannel, apply, prdInfo, idCard, usr, usrInfo, bankcard, usrCnts,
						premium);
				if (!loanApply) {
					msg += "保单号为:" + policyNo + "的订单申请放款失败。";
					addFailedFirst(usr, policyNo, "LoanApply");
					return;
				}
				// 同步放款信息
				if (!syncLoanMsg(usr, apply, capChannel, prdInfo, idCard, premium)) {
					msg += "保单号为:" + policyNo + "的订单申请放款失败。";
					addFailedFirst(usr, policyNo, "SyncLoanMsg");
					return;
				}
				msg += "保单号为:" + policyNo + "的订单申请放款成功。";

				

				String[] conten_params = sms.sendFk(idCard, apply, bankcard);
				boolean sendResult = false;
				long s = new Date().getTime();
				if (isDelaySendSms) {
					String delaySendTime = AppCommonConstants.getValStr("DELAYSENDSMSTIME");
					Calendar nc = Calendar.getInstance();
					nc.add(Calendar.DAY_OF_MONTH, 1);
					String ymd = DateUtils.formatDate(nc.getTime(), "yyyyMMdd");
					String time = ymd + delaySendTime;
					sendResult = new SmsCollectionUtil().sendSmsByTime(usr.getUSR_TEL(), AppNormalConstants.LOAN,
							conten_params, time);// true;
				} else {
					sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.LOAN,
							conten_params);// true;
				}
				long e = new Date().getTime();
				String status = sendResult == true ? "SUCCESS" : "FAIL";
				apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME,
						AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
						sms.getContentByConditonAndParams(AppNormalConstants.LOAN, conten_params), status,
						new Boolean(sendResult).toString());
				apiInfoDao.insert(apiInfo);

				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000014-1", policyNo,date));
				ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000014-1", "贷款协议（信用类）信托-阳光-附件二还款代扣授权", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-3",
						"", "CI13");

				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000008-1", policyNo,date));
				ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000008-1", "贷款合同（信用类）", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-1", "", "CI11");

				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000010-1", policyNo,date));
				ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000010-1", "保险单", "2", "签单机构地址", policyNo, usr.getUSR_CDE(), "5-1", "投保人签名", "CI12");

				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000012-1", policyNo,date));
				ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000012-1", "保险单信息确认页", "1", "投保人签名", policyNo, usr.getUSR_CDE(), "5-2", "", "CI12");

				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000013-1", policyNo,date));
				ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000013-1", "委托扣款授权书-保费", "1", "签章处", policyNo, usr.getUSR_CDE(), "5-3", "", "CI13");

			}
		}

	}

	// 同步产品
	private boolean syncPrd(String prdCde) {
		SynProductMSGController SynProductMSGController = new SynProductMSGController();
		ReqProductVO rpVO = new ReqProductVO();
		rpVO.setServiceId("servP0000100012");
		rpVO.setTyp_cde(prdCde);
		rpVO.setAcct_role_desc("");
		rpVO.setOpt_typ("UPDATE");
		RespProductVO respVO = SynProductMSGController.synProductMSG(rpVO);
		if (respVO.getErrorMsg().equals("success")) {
			return true;
		}
		return false;
	}

	// 同步放款渠道
	private boolean syncChannel(ClCapChannel channel, ClPrdInfo prdInfo) {
		String qudao = channel.getOPERATE_SITE();
		// if(qudao.contains("FOTIC")) {
		qudao = "FOTIC";
		// }
		SynLoanChannelController synLoanBankController = new SynLoanChannelController();
		ReqLoanChannelVO ReqLoanBankVO = new ReqLoanChannelVO();
		List<ReqLoanChannelVO> list = new ArrayList<ReqLoanChannelVO>();
		ReqLoanBankVO.setServiceId("servP0000100011");
		ReqLoanBankVO.setDN_CHANNEL(qudao);
		ReqLoanBankVO.setTNR_OPT(12);
		ReqLoanBankVO.setLOAN_RATE_MODE("FX");
		ReqLoanBankVO.setLOAN_BASE_RATE(prdInfo.getINT_RAT().doubleValue());
		ReqLoanBankVO.setINT_ADJ_PCT("");
		ReqLoanBankVO.setRATE_TYPE("U");
		ReqLoanBankVO.setINT_RAT(prdInfo.getINT_RAT().doubleValue());
		ReqLoanBankVO.setOD_INT_RATE(prdInfo.getGL_OD_RATE().doubleValue());
		ReqLoanBankVO.setLEND_CHA_NAM(channel.getCAP_INSTU_NAME());
		ReqLoanBankVO.setOPT_TYP("UPDATE");
		ReqLoanBankVO.setLAST_CHG_USR("admin");
		ReqLoanBankVO.setLAST_CHG_DT(DateUtils.formatDateTime(new Date()));
		ReqLoanBankVO.setBEL_TYPE("07");
		ReqLoanBankVO.setTOTTERM_X("Z");
		ReqLoanBankVO.setXQ_RATE("");
		list.add(ReqLoanBankVO);
		ReqLoanBankVO.setPLoanTypMtdList(list);
		RespLoanChannelVO rlcVO = synLoanBankController.synProductMSG(ReqLoanBankVO);
		if ("success".equals(rlcVO.getErrorMsg())) {
			return true;
		}
		return false;
	}

	// 放款申请
	private boolean loanApplyReq(ClCapChannel channel, ClUsrApply apply, ClPrdInfo prd, ClIdCardInfo idcard, ClUsr usr,
			ClUsrInfo usrinfo, ClBnkCrd bankCard, JSONObject usrCnts, BigDecimal premium) {

		try {

			int seq = appIdDao.getMidPltSeq();
			String nowStr = DateUtils.getyMdHms2();
			String qudao = channel.getOPERATE_SITE();
			// if(qudao.contains("FOTIC")) {
			qudao = "FOTIC";
			// }
			RECORD creditLoanApplyVO = new RECORD();
			BODY BODY = new BODY();
			HEADER header = new HEADER();
			IN IN = new IN();
			header.setPATCH(AppNormalConstants.Mid_Patch_prefix + nowStr + seq);
			header.setTRANBANK("FOTIC");
			header.setTRANCODE("YG0003");
			header.setTRANTIME(nowStr);
			header.setDATASOURCE("ISP");
			creditLoanApplyVO.setGUARANTYID(apply.getPolicyNo());
			creditLoanApplyVO.setAPPLYADD("网贷APP来源");
			creditLoanApplyVO.setCERTTYPE("1");
			creditLoanApplyVO.setCERTID(idcard.getID_NO());
			creditLoanApplyVO.setCUSTOMERNAME(idcard.getCUST_NAME());
			creditLoanApplyVO.setSEX(idcard.getINDIV_SEX());
			creditLoanApplyVO
					.setBIRTHDAY(idcard.getBIRTHDAY_DATE().replaceAll("年", "").replaceAll("月", "").replaceAll("日", ""));
			creditLoanApplyVO.setTELNO(bankCard.getUSR_TEL());
			creditLoanApplyVO.setMOBILE(bankCard.getUSR_TEL());
			creditLoanApplyVO.setZIPCODE("000000");
			creditLoanApplyVO.setPOSTADD("阳光保险");
			creditLoanApplyVO.setAPPLYPURPOSE("网贷申请");
			creditLoanApplyVO.setAPPLYSUM(apply.getApplyAmt());
			creditLoanApplyVO.setAPPLYCURRENCY("01");
			creditLoanApplyVO.setAPPLYTERMMONTH(apply.getApplyTnr());
			creditLoanApplyVO.setPAYMENTTYPE("1");// 未确定
			creditLoanApplyVO.setPAYMENTACCTNO(bankCard.getAPPL_CARD_NO());// 未确定
			creditLoanApplyVO.setPAYMETHOD("1");
			creditLoanApplyVO.setSUBLOANTERM("");
			creditLoanApplyVO.setSUBPAYMETHOD("");
			creditLoanApplyVO.setDEBITTYPE("1");
			creditLoanApplyVO.setMONREPAYDAY("");
			creditLoanApplyVO.setMARRIAGE("");// 传空值
			creditLoanApplyVO.setEDUEXPERIENCE("99");// 大学 未确定 必传
			creditLoanApplyVO.setHUKOU(idcard.getSIGN_DEPT());// 北京
			creditLoanApplyVO.setINCOMEFLAG("1");
			creditLoanApplyVO.setMONTHLYWAGES("0");// 2000 未确定 必传
			creditLoanApplyVO.setFAMILYSTATUS("");
			creditLoanApplyVO.setCITYLIVEDSTARTYEAR("");
			creditLoanApplyVO.setFAMILYADD(usrinfo.getLIVE_PROVINCE() + usrinfo.getLIVE_CITY() + usrinfo.getLIVE_AREA()
					+ usrinfo.getLIVE_ADDR());// 北京
			creditLoanApplyVO.setFAMILYZIP("100000");
			creditLoanApplyVO.setFAMILYTEL(usrinfo.getINDIV_EMP_TEL());
			creditLoanApplyVO.setUNITTYPE("");
			creditLoanApplyVO.setUNITKIND("z");
			creditLoanApplyVO.setEMPLOYMENT(usrinfo.getINDIV_EMP_NAME());
			creditLoanApplyVO.setSTAFF(usrinfo.getDUTY_CODE());
			creditLoanApplyVO.setCOMPANYADD(usrinfo.getINDIV_EMP_PROVINCE() + usrinfo.getINDIV_EMP_CITY()
					+ usrinfo.getINDIV_EMP_AREA() + usrinfo.getINDIV_EMP_ADDR());
			creditLoanApplyVO.setCOMPANYZIP("");
			creditLoanApplyVO.setCOMPANYTEL(usrinfo.getINDIV_EMP_TEL());
			creditLoanApplyVO.setINSURERCODE("13");
			creditLoanApplyVO.setINSURERNAME("阳光保险公司");
			creditLoanApplyVO.setINSURERNAMEDETAIL("阳光保险集团股份有限公司");
			creditLoanApplyVO.setPOLICYHOLDER(idcard.getCUST_NAME());
			creditLoanApplyVO.setINSUREDTOTALAMT(
					new BigDecimal(apply.getApplyAmt()).multiply(new BigDecimal(channel.getSUM_AMOUNT())).toString());// 有问题
			creditLoanApplyVO.setGUARANTYCURRENCY("01");
			creditLoanApplyVO.setINSUREBEGINDATE("");
			creditLoanApplyVO.setINSUREENDDATE("");
			creditLoanApplyVO.setPAYBEGINDATE("");
			creditLoanApplyVO.setCONFIRMVALUE(new BigDecimal(apply.getApplyAmt()).multiply(premium)
					.multiply(new BigDecimal(apply.getApplyTnr())).toString());
			creditLoanApplyVO.setVOUCHVALUE("");
			creditLoanApplyVO.setOPERATEORGID("");
			creditLoanApplyVO.setINSURANCECONTACTS("liuwei03");
			creditLoanApplyVO.setINSURANCETELEPHONE("95510");
			creditLoanApplyVO.setIMAGEID(apply.getApplCde());
			creditLoanApplyVO.setPAYKIND("10");
			creditLoanApplyVO.setACCTTYPE("0201");
			creditLoanApplyVO.setLOANTYPE("0");
			creditLoanApplyVO.setBORROWERTYPE("1");
			creditLoanApplyVO.setENTERPRISENAME("");
			creditLoanApplyVO.setRECEIPTSPROVE("");
			creditLoanApplyVO.setENTERPRISEINCOMEMON("");
			creditLoanApplyVO.setACCOUNTINCOMEYEAR("");
			creditLoanApplyVO.setSHAREPCT("");
			creditLoanApplyVO.setLICENSENO("");
			creditLoanApplyVO.setDEPARTID("");
			creditLoanApplyVO.setINDUSTRYTYPE("");
			creditLoanApplyVO.setPURPOSETYPE("2");
			creditLoanApplyVO.setINSURERATE(premium.multiply(new BigDecimal("100")).toString());
			creditLoanApplyVO.setPREINSURATE(premium.multiply(new BigDecimal("100")).toString());
			creditLoanApplyVO.setPREMIUMPAYTYPE("01");
			creditLoanApplyVO.setPRODUCTTYPE("04");
			creditLoanApplyVO.setAPPLYNO(apply.getApplCde());
			creditLoanApplyVO.setCONTRACTNO(apply.getApplCde());
			creditLoanApplyVO.setPRODID("shandai");
			creditLoanApplyVO.setPRODNAME(apply.getPrdCde());
			// 传账务门店值有问题 modfiy by wangfz 2018-9-17
			// creditLoanApplyVO.setSTORIED(channel.getCOM_CED());
			// creditLoanApplyVO.setSTORIEDNAME(channel.getHANDLER1_CDE());
			creditLoanApplyVO.setSTORIED("00370100");
			creditLoanApplyVO.setSTORIEDNAME("新业务BJ");
			creditLoanApplyVO.setHOMEADDRPROV("");
			creditLoanApplyVO.setHOMEADDRCITY("");
			creditLoanApplyVO.setHOMEADDRDISTRICT("");
			creditLoanApplyVO.setEMPLOYEDTYPE("1");
			creditLoanApplyVO.setWORKYEAR("");
			creditLoanApplyVO.setWORKMONTH("");
			creditLoanApplyVO.setRELAPHONE1(usrCnts.getString("relationPhone"));
			creditLoanApplyVO.setRELATION1(getZWCntsRelationship(usrCnts.getString("relation")));
			creditLoanApplyVO.setEMAIL("");
			creditLoanApplyVO.setCOUNTRY("中国");
			creditLoanApplyVO.setDUEDATE("");
			creditLoanApplyVO.setEDUEXPERIANCE("99");// 最高学历要有
			creditLoanApplyVO.setEDUDEGREE("");
			creditLoanApplyVO.setPOSITION(usrinfo.getDUTY());
			creditLoanApplyVO.setFAMILYCITY("");
			creditLoanApplyVO.setCOMPANYADDHEAD("");
			creditLoanApplyVO.setBANKCODE(bankCard.getBNK_CODE());
			creditLoanApplyVO.setQUDLAY(qudao);
			creditLoanApplyVO.setBANKNAME(bankCard.getAPPL_BANK_NAME());
			creditLoanApplyVO.setBANKPROVINCE("");
			creditLoanApplyVO.setBANKCITY("");
			creditLoanApplyVO.setCONSULTRATE("");
			creditLoanApplyVO.setSUBAPPMANNO("");
			creditLoanApplyVO.setAPPLYMANNO("");
			creditLoanApplyVO.setBUSIMODE("01");
			creditLoanApplyVO.setMONCOMFEERATE("");
			creditLoanApplyVO.setCONSERVFEERATE("");
			creditLoanApplyVO.setCONMANAFEERATE("");
			creditLoanApplyVO.setXTERM("");
			creditLoanApplyVO.setYTERM("");
			creditLoanApplyVO.setBENEFICIARY("中国中间业务平台");
			BODY.setRECORD(creditLoanApplyVO);
			IN.setHEADER(header);
			IN.setBODY(BODY);
			String xmlStr = JaxbUtil.convertToXml(IN);
			System.out.println(xmlStr);
			HttpConUtils httpConUtils = new HttpConUtils();
			long start = new Date().getTime();
			String reulst = httpConUtils.doPostXML(PropertiesUtil.getString("loanapiurl"), xmlStr);
			long end = new Date().getTime();
			System.out.println("小额信贷返回结果" + reulst);
			if (reulst.contains("成功")) {
				ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.ZHANGWU,
						AppNormalConstants.FANGKUAN, DateUtils.getNowFullFmt(), (int) (end - start), xmlStr, "SUCCESS",
						reulst);
				apiInfoDao.insert(apiInfo);
				return true;
			}
			ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.ZHANGWU,
					AppNormalConstants.FANGKUAN, DateUtils.getNowFullFmt(), (int) (end - start), xmlStr, "FAIL",
					reulst);
			apiInfoDao.insert(apiInfo);
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}

	// 同步放款信息
	private boolean syncLoanMsg(ClUsr usr, ClUsrApply apply, ClCapChannel channel, ClPrdInfo prd, ClIdCardInfo idcard,
			BigDecimal premium) {
		try {
			SynLoanMessageController SynLoanMessageController = new SynLoanMessageController();
			ReqLoanMessageVO reqLoanMessageVO = new ReqLoanMessageVO();
			List<ReqLoanMessageVO> list = new ArrayList<ReqLoanMessageVO>();
			reqLoanMessageVO.setServiceId("serv10000000122");
			reqLoanMessageVO.setLOAN_CONT_NO(apply.getPolicyNo());
			reqLoanMessageVO.setBEL_TYPE("07");
			reqLoanMessageVO.setBCH_CDE(channel.getBCH_CDE());
			reqLoanMessageVO.setLOAN_NO(apply.getPolicyNo());
			reqLoanMessageVO.setCUST_NAME(idcard.getCUST_NAME());
			reqLoanMessageVO.setID_NO(idcard.getID_NO());
			reqLoanMessageVO.setORIG_PRCP(apply.getApplyAmt());
			reqLoanMessageVO.setLOAN_ACTV_DT(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
			reqLoanMessageVO.setLOAN_TYP(prd.getPRD_CDE());
			reqLoanMessageVO.setLOAN_PAYM_MTD("M0001");
			reqLoanMessageVO.setLOAN_PAYM_TYP("01");
			reqLoanMessageVO.setTNR(apply.getApplyTnr());
			reqLoanMessageVO.setDN_CHANNEL("FOTIC");
			reqLoanMessageVO.setFEE_ACT(premium.multiply(new BigDecimal("100")).toString());
			reqLoanMessageVO.setHOLD_FEE_IND("Y");
			reqLoanMessageVO.setRISK_CODE(channel.getSECURE_CDE());
			reqLoanMessageVO.setACC_IND("N");
			reqLoanMessageVO.setFIRST_FEE_RATE("0");
			reqLoanMessageVO.setSERV_FEE_RATE("0");
			reqLoanMessageVO.setGUAR_FEE_RATE(premium.multiply(new BigDecimal("100")).toString());
			reqLoanMessageVO.setPH_FLAG("N");
			list.add(reqLoanMessageVO);
			reqLoanMessageVO.setLmFeeTxTList(list);
			long start = new Date().getTime();
			RespLoanMessageVO slmc = SynLoanMessageController.synLoanMessage(reqLoanMessageVO);
			long end = new Date().getTime();
			if ("success".equals(slmc.getErrorMsg())) {
				ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.HESUAN,
						AppNormalConstants.SYNC_LOANMSG, DateUtils.getNowFullFmt(), (int) (end - start),
						JSONObject.toJSONString(reqLoanMessageVO), "SUCCESS", JSONObject.toJSONString(slmc));
				apiInfoDao.insert(apiInfo);
				return true;
			}
			ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.HESUAN,
					AppNormalConstants.SYNC_LOANMSG, DateUtils.getNowFullFmt(), (int) (end - start),
					JSONObject.toJSONString(reqLoanMessageVO), "FAIL", JSONObject.toJSONString(slmc));
			apiInfoDao.insert(apiInfo);
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
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

	private void addFailedFirst(ClUsr usr, String policyNo, String step) {
		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE(usr.getUSR_CDE());
		failed.setPOLICY_NO(policyNo);
		failed.setFAIL_STEP(step);
		failed.setCRT_DT(DateUtils.getNowFullFmt());
		failed.setISRETRY("false");
		failed.setRETRY_DT(null);
		failed.setRETRY_TIMES(0);
		failed.setISDONE("false");
		failedDao.insert(failed);
	}

	public void hetong(ClUsr usr, String policyNo) {
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		// 获取审核中状态的申请
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usr.getUSR_CDE());
		String prdCde = apply.getPrdCde();

		ClCapChannel capChannel = capChannelDao.getByCapSeq("1");
		ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
		ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usr.getUSR_CDE());
		List<ClUsrCnts> usrCntList = usrCntsDao.getByUsrCode(usr.getUSR_CDE());
		JSONObject usrCnts = parseUsrCntsList(usrCntList);
		ClBnkCrd bankcard = bnkCrdDao.getOneByBankNoWithOutSts(apply.getBankCardNo());
		// 重要
		Map<String, String> params = new HashMap<String, String>();
		params.put("PRD_CDE", apply.getPrdCde());
		params.put("PAY_TERM", apply.getApplyTnr());
		// ClPremiumDetail clPreDetail = premiumDetailDao.getByPrdAndTrem(params);
		BigDecimal premium = new BigDecimal(apply.getPremiumRate());

		BigDecimal numberOfMoney = new BigDecimal(apply.getApplyAmt());

		BigDecimal monthPre = numberOfMoney.multiply(premium);

		monthPre = monthPre.setScale(2, BigDecimal.ROUND_HALF_UP);

		BigDecimal sumP = monthPre.multiply(new BigDecimal(apply.getApplyTnr()));
		params.put("premium", premium.toString());
		params.put("monthPre", monthPre.toString());
		params.put("sumP", sumP.toString());
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "apply_key", apply,
				AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));

		Object obj = CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "apply_key");
		System.out.println("缓存值" + obj.toString());

		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "apply_key" + policyNo, apply,
				AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "usr_key" + policyNo, usr,
				AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "prdInfo_key" + policyNo, prdInfo,
				AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "bankcard_key" + policyNo, bankcard,
				AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "idCard_key" + policyNo, idCard,
				AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "usrInfo_key" + policyNo, usrInfo,
				AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "clPreDetail_key" + policyNo, params,
				AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "premium_key" + policyNo, premium,
				AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "capChannel_key" + policyNo, capChannel,
				AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));

	}

	/*public void dealApplyCallBack(ClUsr usr, String policyNo) throws Exception {

		Date nowDate = new Date();
		boolean isDelaySendSms = false;
		String delaySendSmsStartPoint = AppCommonConstants.getValStr("DELAYSENDSMSTIMESTART");
		String nowYMD = DateUtils.formatDate(nowDate, "yyyyMMdd");
		String delaySendSmsDateStr = nowYMD + delaySendSmsStartPoint + "00";
		Date pointDate = DateUtils.parseDate(delaySendSmsDateStr);

		if (nowDate.getTime() >= pointDate.getTime()) {
			isDelaySendSms = true;
		}

		String msg = "";
		String aid = usr.getUSR_CDE() + "_" + policyNo;
		String usrCde = usr.getUSR_CDE();
		ClIdCardInfo idCardInfo = idCardDao.getByUsrCode(usrCde);
		String id_no = idCardInfo.getID_NO();
		long pc_start = new Date().getTime();
		JSONObject edu = reapi.getLimit(usrCde, id_no);
		long pc_end = new Date().getTime();
		logger.info(aid + ",获取白名单额度:" + edu.toJSONString());
		String isWhiteList = edu.getString("isWhiteList");

		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		if (apply == null) {
			msg = "ClUsrApply is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
			addFailedFirst(usr, policyNo, "DATA ERROR : ClUsrApply");
			return;
		}
		// 获取审核中状态的申请
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usr.getUSR_CDE());
		if (idCard == null) {
			msg = "ClIdCardInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
			addFailedFirst(usr, policyNo, "DATA ERROR : ClIdCardInfo");
			return;
		}
		String idNo = idCard.getID_NO();
		String prdCde = apply.getPrdCde();

		net.sf.json.JSONObject paramsHt = null;

		// 获取衍生数据
		long start = new Date().getTime();
		JSONObject result = reapi.getDerivedDataJSONObject(usr.getUSR_CDE(), idNo, policyNo);
		long end = new Date().getTime();
		logger.info(aid + ":获取衍生数据参数:" + usr.getUSR_CDE() + "," + idNo + "," + policyNo);
		logger.info(aid + ":获取衍生数据结果:" + result.toJSONString());
		JSONObject invokeP = new JSONObject();
		invokeP.put("usrCde", usr.getUSR_CDE());
		invokeP.put("idNo", idNo);
		invokeP.put("policyNo", policyNo);

		if ("error".equals(result.getString("code"))) {
			msg = "保单号为:" + policyNo + "的订单获取衍生数据错误。";
			addFailedFirst(usr, policyNo, "DerivedData");
			ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE,
					AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int) (end - start),
					invokeP.toJSONString(), "FAIL", result.toJSONString());
			apiInfoDao.insert(apiInfo);
			return;
		}
		if ("ok".equals(result.getString("code"))) {
			int j = 0;
			if (result.getJSONObject("data").isEmpty()) {//
				msg += "保单号为:" + policyNo + "的订单未能获取衍生数据。";
				addFailedFirst(usr, policyNo, "DerivedData");
				ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE,
						AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int) (end - start),
						invokeP.toJSONString(), "FAIL", result.toJSONString());
				apiInfoDao.insert(apiInfo);
				return;
			} else {
				//修改用户状态
				clUsrStsService.getGonganSts(usr, result.getJSONObject("data"));
				
				ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE,
						AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int) (end - start),
						invokeP.toJSONString(), "SUCCESS", result.toJSONString());
				apiInfoDao.insert(apiInfo);
				ClCapChannel capChannel = capChannelDao.getByCapSeq("1");// getByUsrSource();
				ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
				if (prdInfo == null) {
					msg = "ClPrdInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					addFailedFirst(usr, policyNo, "DATA ERROR : ClPrdInfo");
					return;
				}
				ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usr.getUSR_CDE());
				if (usrInfo == null) {
					msg = "ClUsrInfo is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					addFailedFirst(usr, policyNo, "DATA ERROR : ClUsrInfo");
					return;
				}
				List<ClUsrCnts> usrCntList = usrCntsDao.getByUsrCode(usr.getUSR_CDE());
				JSONObject usrCnts = parseUsrCntsList(usrCntList);
				if (usrCnts.isEmpty()) {
					msg = "ClUsrCnts is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					addFailedFirst(usr, policyNo, "DATA ERROR : ClUsrCnts");
					return;
				}
				ClBnkCrd bankcard = bnkCrdDao.getOneByBankNoWithOutSts(apply.getBankCardNo());
				if (bankcard == null) {
					msg = "ClBnkCrd is null for usr : " + usr.getUSR_CDE() + "=" + usr.getUSR_TEL();
					addFailedFirst(usr, policyNo, "DATA ERROR : ClBnkCrd");
					return;
				}
				// 重要
				// Map<String, String> params = new HashMap<String, String>();
				// params.put("PRD_CDE", apply.getPrdCde());
				// params.put("PAY_TERM", apply.getApplyTnr());
				// ClPremiumDetail clPreDetail = premiumDetailDao.getByPrdAndTrem(params);
				// if (clPreDetail == null) {
				// msg = "ClPremiumDetail is null for usr : " + usr.getUSR_CDE() + "=" +
				// usr.getUSR_TEL();
				// addFailedFirst(usr, policyNo, "DATA ERROR : ClPremiumDetail");
				// return;
				// }
				BigDecimal premium = new BigDecimal(apply.getPremiumRate());
				ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(usr.getUSR_CDE());
				if (isWhiteList.equals("N")) {
					crdtExt.setCRDTEXT_TYPE("unkown");
					usr.setUSR_GRD("unkown");
				} else {
					usr.setUSR_GRD("innerWhiteList");
				}
				if(!usr.getUSR_GRD().contains("gonganPoor")) {
					usrDao.updateUsrGrd(usr);
				}
				// prdInfo.setPREMIUM_RATE(premium);

				// 无效保单
				String invaliPolicyXml = new ReqXmlStringUtils().getReqXmlStr(capChannel, prdInfo, apply, usr, usrInfo,
						idCard, usrCnts, premium);
				start = new Date().getTime();
				JSONObject ipolicyResult = siOp.inputPolicy(invaliPolicyXml);
				end = new Date().getTime();
				System.out.println(ipolicyResult);
				String clientFlag = ipolicyResult.getString("clientFlag");
				if (!clientFlag.equals("success")) {
					msg += "保单号为:" + policyNo + "的订单无效保单失败。";
					addFailedFirst(usr, policyNo, "InvaliPolicy");
					apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.INVALID_POLICY,
							AppNormalConstants.INVALID_POLICY, DateUtils.getNowFullFmt(), (int) (end - start),
							invaliPolicyXml, "FAIL", ipolicyResult.toJSONString());
					apiInfoDao.insert(apiInfo);
					return;
				}
				apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.INVALID_POLICY,
						AppNormalConstants.INVALID_POLICY, DateUtils.getNowFullFmt(), (int) (end - start),
						invaliPolicyXml, "SUCCESS", ipolicyResult.toJSONString());
				apiInfoDao.insert(apiInfo);

				// 暂时不开放
				ElePolicyApi api = new ElePolicyApi();
				boolean elePolicy = api.sendElePolicy(capChannel, prdInfo, usr, idCard, apply, usrInfo, premium);
				if (!elePolicy) {
					addFailedFirst(usr, policyNo, "ElePolicy");
					return;
				}

				// PM
				// String pmXml = new PMXmlStringUtils().getPMRequestXML("1",capChannel,
				// prdInfo,
				// apply, usr, usrInfo, idCard, usrCnts,
				// result.getJSONObject("data"),premium,crdtExt);
				// System.out.println(pmXml);
				// start = new Date().getTime();
				// String pmResult = pmapi.preCredExt(pmXml);
				// end = new Date().getTime();
				// JSONObject rJsonObject = new
				// PMXMlResultUtils().parseStrategyResultXML(pmResult);
				// String maxAmount = rJsonObject.getString("maxAmount");
				String pmXml2 = "";
				String pmResult2 = "";
				String default_reason = "";
				String system_decision = "";
				String detail = "";
				try {
					pmXml2 = new PMXmlStringUtils().getPMRequestXML("2", capChannel, prdInfo, apply, usr, usrInfo,
							idCard, usrCnts, result.getJSONObject("data"), premium, crdtExt);
					logger.info(aid + ":PM决策参数:" + pmXml2);
					start = new Date().getTime();
					pmResult2 = pmapi.preCredExt(pmXml2);
					end = new Date().getTime();
					apply.setPmDt(DateUtils.getNowFullFmt());
					logger.info(aid + ":PM决策结果:" + pmResult2);
					JSONObject rJsonObject2 = new PMXMlResultUtils().parseDecisionResultXML(pmResult2);
					default_reason = rJsonObject2.getString("default_reason");
					system_decision = rJsonObject2.getString("system_decision");
					detail = rJsonObject2.get("detail").toString();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					addFailedFirst(usr, policyNo, "PM");
					return;
				}

				// String pmXml2 = new PMXmlStringUtils().getPMRequestXML("2",capChannel,
				// prdInfo,
				// apply, usr, usrInfo, idCard, usrCnts,
				// result.getJSONObject("data"),premium,crdtExt);
				// logger.info(aid + ":PM决策参数:"+pmXml2);
				// start = new Date().getTime();
				// String pmResult2 = pmapi.preCredExt(pmXml2);
				// end = new Date().getTime();
				// apply.setPmDt(DateUtils.getNowFullFmt());
				// logger.info(aid + ":PM决策结果:"+pmResult2);
				// JSONObject rJsonObject2 = new
				// PMXMlResultUtils().parseDecisionResultXML(pmResult2);
				// String default_reason = rJsonObject2.getString("default_reason");
				// String system_decision = rJsonObject2.getString("system_decision");
				// String detail = rJsonObject2.get("detail").toString();
				if (system_decision.equals("A")) {
					ClUsrLmtamt lmtAmt = usrLmtAmtService.getByUsrCde(usr.getUSR_CDE());

					BigDecimal lmtCrdAmt = lmtAmt.getCreditAmount();
					BigDecimal usedAmt = lmtAmt.getUsedAmount();
					BigDecimal repayAmt = lmtAmt.getRepayAmount();
					BigDecimal usrAmt = lmtCrdAmt.subtract(usedAmt).add(repayAmt);

					BigDecimal ttt = usrAmt.subtract(new BigDecimal(apply.getApplyAmt()));
					if (ttt.compareTo(new BigDecimal("0")) == -1) {
						apply.setStCde("10");
						apply.setCreditNo(crdtExt.getUSR_APPSEQ());
						apply.setTrdResult(system_decision);
						apply.setTrdDetail("申请额度大于可用额度-" + default_reason + ":" + detail);
						apply.setMdfDt(DateUtils.getNowFullFmt());
						applyDao.updateByPrimaryKeySelective(apply);
						// 失败恢复额度
						usrLmtAmtService.updateUsedAmtRecovery(usr.getUSR_CDE(), new BigDecimal(apply.getApplyAmt()));
						return;
					}

					apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM,
							AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int) (end - start),
							pmXml2, "SUCCESS", pmResult2);
					apiInfoDao.insert(apiInfo);
					
					apply.setStCde("12");
					apply.setTrdResult(system_decision);
					apply.setTrdDetail(default_reason + ":" + detail);
					apply.setMdfDt(DateUtils.getNowFullFmt());
					apply.setCreditNo(crdtExt.getUSR_APPSEQ());
					apply.setRmk(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), "apply_key", apply,
							AppCommonConstants.getValInt("CACHEVALIAPPLYTTL"));
					int i = applyDao.updateByPrimaryKeySelective(apply);

					if (i < 0 || j < 0) {
						msg += "保单号为:" + policyNo + "的订单入库失败。";
					} else {
						return;
					}
				} else {
					apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM,
							AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int) (end - start),
							pmXml2, "FAIL", pmResult2);
					apiInfoDao.insert(apiInfo);
					apply.setStCde("10");
					apply.setCreditNo(crdtExt.getUSR_APPSEQ());
					apply.setTrdResult(system_decision);
					apply.setTrdDetail(default_reason + ":" + detail);
					apply.setMdfDt(DateUtils.getNowFullFmt());
					applyDao.updateByPrimaryKeySelective(apply);
					// 失败恢复额度
					usrLmtAmtService.updateUsedAmtRecovery(usr.getUSR_CDE(), new BigDecimal(apply.getApplyAmt()));
					msg += "保单号为:" + policyNo + "的订单未通过PM。";
					return;
				}
				// 电子保单

				// 同步产品信息
				// if(!syncPrd(prdCde)) {
				// msg += "保单号为:"+policyNo+"的订单同步产品失败。";
				// continue;
				// }
				// 同步放款渠道
				// if(!syncChannel(capChannel, prdInfo)) {
				// msg += "保单号为:"+policyNo+"的订单同步放款渠道失败。";
				// continue;
				// }
				// 申请放款usrLmtAmtService : ClUsrLmtamtService

				boolean loanApply = loanApplyReq(capChannel, apply, prdInfo, idCard, usr, usrInfo, bankcard, usrCnts,
						premium);
				if (!loanApply) {
					msg += "保单号为:" + policyNo + "的订单申请放款失败。";
					addFailedFirst(usr, policyNo, "LoanApply");
					return;
				}
				// 同步放款信息
				if (!syncLoanMsg(usr, apply, capChannel, prdInfo, idCard, premium)) {
					msg += "保单号为:" + policyNo + "的订单申请放款失败。";
					addFailedFirst(usr, policyNo, "SyncLoanMsg");
					return;
				}
				msg += "保单号为:" + policyNo + "的订单申请放款成功。";


				String[] conten_params = sms.sendFk(idCard, apply, bankcard);
				boolean sendResult = false;
				long s = new Date().getTime();
				if (isDelaySendSms) {
					String delaySendTime = AppCommonConstants.getValStr("DELAYSENDSMSTIME");
					Calendar nc = Calendar.getInstance();
					nc.add(Calendar.DAY_OF_MONTH, 1);
					String ymd = DateUtils.formatDate(nc.getTime(), "yyyyMMdd");
					String time = ymd + delaySendTime;
					sendResult = new SmsCollectionUtil().sendSmsByTime(usr.getUSR_TEL(), AppNormalConstants.LOAN,
							conten_params, time);// true;
				} else {
					sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.LOAN,
							conten_params);// true;
				}
				long e = new Date().getTime();
				String status = sendResult == true ? "SUCCESS" : "FAIL";
				apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME,
						AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
						sms.getContentByConditonAndParams(AppNormalConstants.LOAN, conten_params), status,
						new Boolean(sendResult).toString());
				apiInfoDao.insert(apiInfo);
				String date  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000014-1", policyNo,date));
				ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000014-1", "贷款协议（信用类）信托-阳光-附件二还款代扣授权", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-3",
						"", "CI13");

				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000008-1", policyNo,date));
				ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000008-1", "贷款合同（信用类）", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-1", "", "CI11");

				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000010-1", policyNo,date));
				ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000010-1", "保险单", "2", "签单机构地址", policyNo, usr.getUSR_CDE(), "5-1", "投保人签名", "CI12");

				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000012-1", policyNo,date));
				ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000012-1", "保险单信息确认页", "1", "投保人签名", policyNo, usr.getUSR_CDE(), "5-2", "", "CI12");

				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000013-1", policyNo,date));
				ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000013-1", "委托扣款授权书-保费", "1", "签章处", policyNo, usr.getUSR_CDE(), "5-3", "", "CI13");

			}
		}

	}*/

}