package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
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
import com.google.gson.JsonObject;
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
import com.jeeplus.modules.app.utils.FtpConManager;
import com.jeeplus.modules.app.utils.GenerateFile;
import com.jeeplus.modules.app.utils.HttpConUtils;

@Service
public class ClDealFailedService {
	private final static Logger logger = LoggerFactory.getLogger(ClDealFailedService.class);

	@Autowired
	private ClDealFailedDao failedDao;

	@Autowired
	private ClUsrDao usrDao;

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
	private ClUsrLmtamtService lmtAmtSer;

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
	private ClUsrStsMdfByGonganService clUsrStsService;

	public void process() {
		List<ClDealFailed> list = getNotDones();
		for (ClDealFailed clDealFailed : list) {

			if (clDealFailed.getPOLICY_NO().endsWith("W")) {
				boolean dealResult = processFailed(clDealFailed);
				if (dealResult) {
					ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(clDealFailed.getUSR_CDE());
					String creditNo = crdtExt.getUSR_APPSEQ();

					String failStep = clDealFailed.getFAIL_STEP();
					// 改状态
					ClUsrApply apply = applyDao.getByPolicyNo(clDealFailed.getPOLICY_NO());
					apply.setStCde("12");
					apply.setMdfDt(DateUtils.getNowFullFmt());
					apply.setCreditNo(creditNo);
					applyDao.updateByPrimaryKeySelective(apply);
					// 改额度
					if (failStep.equals("LoanApply") || failStep.equals("SyncLoanMsg")) {
						// apply.setRmk(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						applyDao.updateByPrimaryKeySelective(apply);
					} else {
						// lmtAmtSer.updateUsedAmt(apply.getUsrCde(), new
						// BigDecimal(apply.getApplyAmt()));
					}
				}
			} else if (clDealFailed.getFAIL_STEP().equals("ht=3-3")) {
				ClUsr usr = usrDao.getByUsrCode(clDealFailed.getUSR_CDE());
				ClIdCardInfo idcard = idCardDao.getByUsrCode(usr.getUSR_CDE());
				String usrCde = clDealFailed.getUSR_CDE();
				String date = DateUtils.formatDate(DateUtils.parseDate(clDealFailed.getCRT_DT()), "yyyy-MM-dd");
				net.sf.json.JSONObject paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000006-1", clDealFailed.getPOLICY_NO(), date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000006-1", "个人征信授权书（阳光）--央行", "1", "签章处", "", usr.getUSR_CDE(), "3-3", "", "CI06");

			}
		}

	}

	public List<ClDealFailed> getNotDones() {
		return failedDao.getNotDone();
	}

	public boolean processFailed(ClDealFailed failed) {

		if (failed.getFAIL_STEP().contains("DATA ERROR")) {
			return false;
		}

		String usrCde = failed.getUSR_CDE();
		String policyNo = failed.getPOLICY_NO();
		String failedStep = failed.getFAIL_STEP();
		int times = failed.getRETRY_TIMES();
		String aid = usrCde + "_" + policyNo;

		ClUsr usr = usrDao.getByUsrCode(usrCde);
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usr.getUSR_CDE());
		ClCapChannel capChannel = capChannelDao.getByCapSeq("1");
		String idNo = idCard.getID_NO();
		String prdCde = apply.getPrdCde();
		ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
		ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usr.getUSR_CDE());
		List<ClUsrCnts> usrCntList = usrCntsDao.getByUsrCode(usr.getUSR_CDE());
		JSONObject usrCnts = parseUsrCntsList(usrCntList);
		ClBnkCrd bankcard = bnkCrdDao.getOneByBankNoWithOutSts(apply.getBankCardNo());
		// 重要
		BigDecimal premium = new BigDecimal(apply.getPremiumRate());
		ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(usr.getUSR_CDE());

		String id_no = idCard.getID_NO();
		long pc_start = new Date().getTime();
		JSONObject edu = reapi.getLimit(usrCde, id_no);
		long pc_end = new Date().getTime();
		logger.info(aid + ",获取白名单额度:" + edu.toJSONString());
		String isWhiteList = edu.getString("isWhiteList");
		if (isWhiteList.equals("N")) {
			crdtExt.setCRDTEXT_TYPE("unkown");
			usr.setUSR_GRD("unkown");
		} else {
			usr.setUSR_GRD("innerWhiteList");
		}
		if (!usr.getUSR_GRD().contains("gonganPoor")) {
			usrDao.updateUsrGrd(usr);
		}

		if (failedStep.equals("DerivedData")) {
			boolean a = fromGetDerivedData(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo,
					idCard, usrCnts, bankcard, premium, crdtExt);

			if (a) {
				String[] conten_params = sms.sendFk(idCard, apply, bankcard);
				long s = new Date().getTime();
				boolean sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.LOAN,
						conten_params);// true;
				long e = new Date().getTime();
				String status = sendResult == true ? "SUCCESS" : "FAIL";
				ClApiInfo apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME,
						AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
						sms.getContentByConditonAndParams(AppNormalConstants.LOAN, conten_params), status,
						new Boolean(sendResult).toString());
				apiInfoDao.insert(apiInfo);
			}

			return a;
		}

		if (failedStep.equals("onlyPM")) {
			boolean a = onlyPM(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard, usrCnts,
					bankcard, premium, crdtExt);

			if (a) {
				String[] conten_params = sms.sendFk(idCard, apply, bankcard);
				long s = new Date().getTime();
				boolean sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.LOAN,
						conten_params);// true;
				long e = new Date().getTime();
				String status = sendResult == true ? "SUCCESS" : "FAIL";
				ClApiInfo apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME,
						AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
						sms.getContentByConditonAndParams(AppNormalConstants.LOAN, conten_params), status,
						new Boolean(sendResult).toString());
				apiInfoDao.insert(apiInfo);
			}

			return a;
		}

		if (failedStep.equals("InvaliPolicy")) {
			boolean a = fromInvalidPolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo,
					idCard, usrCnts, bankcard, premium);
			if (a) {
				String[] conten_params = sms.sendFk(idCard, apply, bankcard);
				long s = new Date().getTime();
				boolean sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.LOAN,
						conten_params);// true;
				long e = new Date().getTime();
				String status = sendResult == true ? "SUCCESS" : "FAIL";
				ClApiInfo apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME,
						AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
						sms.getContentByConditonAndParams(AppNormalConstants.LOAN, conten_params), status,
						new Boolean(sendResult).toString());
				apiInfoDao.insert(apiInfo);
			}

			return a;
		}

		if (failedStep.equals("InvaliPolicyOnly")) {
			boolean a = onlyInvalidPolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo,
					idCard, usrCnts, bankcard, premium);
			if (a) {
				// String[] conten_params = sms.sendFk(idCard, apply, bankcard);
				// long s = new Date().getTime();
				// boolean sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(),
				// AppNormalConstants.LOAN, conten_params);//true;
				// long e = new Date().getTime();
				// String status = sendResult==true?"SUCCESS":"FAIL";
				// ClApiInfo apiInfo = new ClApiInfo("", usr.getUSR_TEL(),
				// AppNormalConstants.SMS_API_NAME, AppNormalConstants.SMS_API_SERVICE_NAME,
				// DateUtils.getNowFullFmt(), (int)(e-s),
				// sms.getContentByConditonAndParams(AppNormalConstants.LOAN, conten_params),
				// status, new Boolean(sendResult).toString());
				// apiInfoDao.insert(apiInfo);
			}

			return a;
		}

		if (failedStep.equals("onlyElePolicy")) {
			boolean a = onlyElePolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard,
					usrCnts, bankcard, premium);

			return a;
		}

		if (failedStep.equals("ElePolicy")) {
			boolean a = fromElePolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard,
					usrCnts, bankcard, premium);
			if (a) {
				String[] conten_params = sms.sendFk(idCard, apply, bankcard);
				long s = new Date().getTime();
				boolean sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.LOAN,
						conten_params);// true;
				long e = new Date().getTime();
				String status = sendResult == true ? "SUCCESS" : "FAIL";
				ClApiInfo apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME,
						AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
						sms.getContentByConditonAndParams(AppNormalConstants.LOAN, conten_params), status,
						new Boolean(sendResult).toString());
				apiInfoDao.insert(apiInfo);
			}

			return a;
		}

		if (failedStep.equals("LoanApply")) {
			boolean a = fromLoanApply(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard,
					usrCnts, bankcard, premium);
			if (a) {
				String[] conten_params = sms.sendFk(idCard, apply, bankcard);
				long s = new Date().getTime();
				boolean sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.LOAN,
						conten_params);// true;
				long e = new Date().getTime();
				String status = sendResult == true ? "SUCCESS" : "FAIL";
				ClApiInfo apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME,
						AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
						sms.getContentByConditonAndParams(AppNormalConstants.LOAN, conten_params), status,
						new Boolean(sendResult).toString());
				apiInfoDao.insert(apiInfo);
			}

			return a;
		}

		if (failedStep.equals("SyncLoanMsg")) {
			boolean a = fromSyncLoanMsg(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard,
					usrCnts, bankcard, premium);
			if (a) {
				String[] conten_params = sms.sendFk(idCard, apply, bankcard);
				long s = new Date().getTime();
				boolean sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.LOAN,
						conten_params);// true;
				long e = new Date().getTime();
				String status = sendResult == true ? "SUCCESS" : "FAIL";
				ClApiInfo apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME,
						AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
						sms.getContentByConditonAndParams(AppNormalConstants.LOAN, conten_params), status,
						new Boolean(sendResult).toString());
				apiInfoDao.insert(apiInfo);
			}

			return a;
		}

		if (failedStep.startsWith("ht")) {
			String[] tmp = failedStep.split("=");
			if (tmp.length == 2) {
				String step = tmp[1];
				String date = DateUtils.formatDate(DateUtils.parseDate(failed.getRETRY_DT()), "yyyy-MM-dd");
				boolean a = contract(usr, idCard, apply, step, policyNo, date);
				if (a) {
					ClDealFailed f = new ClDealFailed();
					f.setUSR_CDE(usr.getUSR_CDE());
					f.setPOLICY_NO(apply.getPolicyNo());
					f.setISRETRY("true");
					failed.setFAIL_STEP("");
					failed.setRETRY_DT(DateUtils.getNowFullFmt());
					failed.setRETRY_TIMES(times + 1);
					failed.setISDONE("true");
					failedDao.update(failed);
				}

				return a;

			}

		}

		return false;
	}

	private boolean fromGetDerivedData(String usrCde, String idNo, String policyNo, int times, ClCapChannel capChannel,
			ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo, ClIdCardInfo idCard, JSONObject usrCnts,
			ClBnkCrd bankcard, BigDecimal premium, ClCrdtExt crdtExt) {

		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE(usr.getUSR_CDE());
		failed.setPOLICY_NO(apply.getPolicyNo());
		failed.setISRETRY("true");
		failed.setISDONE("false");

		if (!getDerivedDataAndInvokePm(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard,
				usrCnts, premium, crdtExt)) {
			return false;
		}
		if (!invalidPolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard, usrCnts,
				premium)) {
			return false;
		}
		if (!elePolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard, usrCnts,
				premium)) {
			return false;
		}
		ClUsrApply tmp = applyDao.getByPolicyNo(policyNo);
		if (tmp.getStCde().equals("10")) {
			failed.setFAIL_STEP("DerivedData_END4PmReject");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("true");
			failedDao.update(failed);
			return true;
		}
		// 申请放款
		if (!loanApplyReq(capChannel, apply, prdInfo, idCard, usr, usrInfo, bankcard, usrCnts, premium)) {
			failed.setFAIL_STEP("LoanApply");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("false");
			failedDao.update(failed);
			return false;
		}
		if (!syncLoanMsg(usr, apply, capChannel, prdInfo, idCard, premium)) {
			failed.setFAIL_STEP("SyncLoanMsg");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("false");
			failedDao.update(failed);
			return false;
		}

		failed.setFAIL_STEP("");
		failed.setRETRY_DT(DateUtils.getNowFullFmt());
		failed.setRETRY_TIMES(times + 1);
		failed.setISDONE("true");
		failedDao.update(failed);

		return true;
	}

	private boolean onlyPM(String usrCde, String idNo, String policyNo, int times, ClCapChannel capChannel,
			ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo, ClIdCardInfo idCard, JSONObject usrCnts,
			ClBnkCrd bankcard, BigDecimal premium, ClCrdtExt crdtExt) {

		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE(usr.getUSR_CDE());
		failed.setPOLICY_NO(apply.getPolicyNo());
		failed.setISRETRY("true");
		failed.setISDONE("false");

		if (!getDerivedDataAndInvokePm(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard,
				usrCnts, premium, crdtExt)) {
			return false;
		}

		failed.setFAIL_STEP("");
		failed.setRETRY_DT(DateUtils.getNowFullFmt());
		failed.setRETRY_TIMES(times + 1);
		failed.setISDONE("true");
		failedDao.update(failed);

		return true;
	}

	private boolean onlyInvalidPolicy(String usrCde, String idNo, String policyNo, int times, ClCapChannel capChannel,
			ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo, ClIdCardInfo idCard, JSONObject usrCnts,
			ClBnkCrd bankcard, BigDecimal premium) {
		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE(usr.getUSR_CDE());
		failed.setPOLICY_NO(apply.getPolicyNo());
		failed.setISRETRY("true");
		failed.setISDONE("false");

		if (!invalidPolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard, usrCnts,
				premium)) {
			return false;
		}

		failed.setFAIL_STEP("");
		failed.setRETRY_DT(DateUtils.getNowFullFmt());
		failed.setRETRY_TIMES(times + 1);
		failed.setISDONE("true");
		failedDao.update(failed);

		return true;
	}

	private boolean onlyElePolicy(String usrCde, String idNo, String policyNo, int times, ClCapChannel capChannel,
			ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo, ClIdCardInfo idCard, JSONObject usrCnts,
			ClBnkCrd bankcard, BigDecimal premium) {
		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE(usr.getUSR_CDE());
		failed.setPOLICY_NO(apply.getPolicyNo());
		failed.setISRETRY("true");
		failed.setISDONE("false");

		if (!elePolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard, usrCnts,
				premium)) {
			return false;
		}

		failed.setFAIL_STEP("");
		failed.setRETRY_DT(DateUtils.getNowFullFmt());
		failed.setRETRY_TIMES(times + 1);
		failed.setISDONE("true");
		failedDao.update(failed);

		return true;
	}

	private boolean fromInvalidPolicy(String usrCde, String idNo, String policyNo, int times, ClCapChannel capChannel,
			ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo, ClIdCardInfo idCard, JSONObject usrCnts,
			ClBnkCrd bankcard, BigDecimal premium) {

		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE(usr.getUSR_CDE());
		failed.setPOLICY_NO(apply.getPolicyNo());
		failed.setISRETRY("true");
		failed.setISDONE("false");

		if (!invalidPolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard, usrCnts,
				premium)) {
			return false;
		}

		if (!elePolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard, usrCnts,
				premium)) {
			failed.setFAIL_STEP("ElePolicy");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("false");
			failedDao.update(failed);
			return false;
		}

		if (apply.getStCde().equals("10")) {
			failed.setFAIL_STEP("InvalidPolicy_END4PmReject");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("true");
			failedDao.update(failed);
			return true;
		}

		// 申请放款
		if (!loanApplyReq(capChannel, apply, prdInfo, idCard, usr, usrInfo, bankcard, usrCnts, premium)) {
			failed.setFAIL_STEP("LoanApply");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("false");
			failedDao.update(failed);
			return false;
		}
		if (!syncLoanMsg(usr, apply, capChannel, prdInfo, idCard, premium)) {
			failed.setFAIL_STEP("SyncLoanMsg");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("false");
			failedDao.update(failed);
			return false;
		}

		failed.setFAIL_STEP("");
		failed.setRETRY_DT(DateUtils.getNowFullFmt());
		failed.setRETRY_TIMES(times + 1);
		failed.setISDONE("true");
		failedDao.update(failed);

		return true;
	}

	private boolean fromElePolicy(String usrCde, String idNo, String policyNo, int times, ClCapChannel capChannel,
			ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo, ClIdCardInfo idCard, JSONObject usrCnts,
			ClBnkCrd bankcard, BigDecimal premium) {
		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE(usr.getUSR_CDE());
		failed.setPOLICY_NO(apply.getPolicyNo());
		failed.setISRETRY("true");
		failed.setISDONE("false");

		// 电子保单
		if (!elePolicy(usrCde, idNo, policyNo, times, capChannel, prdInfo, apply, usr, usrInfo, idCard, usrCnts,
				premium)) {
			return false;
		}
		if (apply.getStCde().equals("10")) {
			failed.setFAIL_STEP("ElePolicy_END4PmReject");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("true");
			failedDao.update(failed);
			return true;
		}
		// 申请放款
		if (!loanApplyReq(capChannel, apply, prdInfo, idCard, usr, usrInfo, bankcard, usrCnts, premium)) {
			failed.setFAIL_STEP("LoanApply");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("false");
			failedDao.update(failed);
			return false;
		}
		if (!syncLoanMsg(usr, apply, capChannel, prdInfo, idCard, premium)) {
			failed.setFAIL_STEP("SyncLoanMsg");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("false");
			failedDao.update(failed);
			return false;
		}

		failed.setFAIL_STEP("");
		failed.setRETRY_DT(DateUtils.getNowFullFmt());
		failed.setRETRY_TIMES(times + 1);
		failed.setISDONE("true");
		failedDao.update(failed);

		return true;

	}

	private boolean fromLoanApply(String usrCde, String idNo, String policyNo, int times, ClCapChannel capChannel,
			ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo, ClIdCardInfo idCard, JSONObject usrCnts,
			ClBnkCrd bankcard, BigDecimal premium) {

		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE(usr.getUSR_CDE());
		failed.setPOLICY_NO(apply.getPolicyNo());
		failed.setISRETRY("true");
		failed.setISDONE("false");

		if (apply.getStCde().equals("10")) {
			failed.setFAIL_STEP("LoanApply_END4PmReject");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("true");
			failedDao.update(failed);
			return true;
		}

		// 申请放款
		if (!loanApplyReq(capChannel, apply, prdInfo, idCard, usr, usrInfo, bankcard, usrCnts, premium)) {
			failed.setFAIL_STEP("LoanApply");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("false");
			failedDao.update(failed);
			return false;
		}
		if (!syncLoanMsg(usr, apply, capChannel, prdInfo, idCard, premium)) {
			failed.setFAIL_STEP("SyncLoanMsg");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("false");
			failedDao.update(failed);
			return false;
		}

		failed.setFAIL_STEP("");
		failed.setRETRY_DT(DateUtils.getNowFullFmt());
		failed.setRETRY_TIMES(times + 1);
		failed.setISDONE("true");
		failedDao.update(failed);

		return true;
	}

	private boolean fromSyncLoanMsg(String usrCde, String idNo, String policyNo, int times, ClCapChannel capChannel,
			ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo, ClIdCardInfo idCard, JSONObject usrCnts,
			ClBnkCrd bankcard, BigDecimal premium) {

		ClDealFailed failed = new ClDealFailed();
		failed.setUSR_CDE(usr.getUSR_CDE());
		failed.setPOLICY_NO(apply.getPolicyNo());
		failed.setISRETRY("true");
		failed.setISDONE("false");

		if (apply.getStCde().equals("10")) {
			failed.setFAIL_STEP("SyncLoanMsg_END4PmReject");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("true");
			failedDao.update(failed);
			return true;
		}

		if (!syncLoanMsg(usr, apply, capChannel, prdInfo, idCard, premium)) {
			failed.setFAIL_STEP("SyncLoanMsg");
			failed.setRETRY_DT(DateUtils.getNowFullFmt());
			failed.setRETRY_TIMES(times + 1);
			failed.setISDONE("false");
			failedDao.update(failed);
			return false;
		}

		failed.setFAIL_STEP("");
		failed.setRETRY_DT(DateUtils.getNowFullFmt());
		failed.setRETRY_TIMES(times + 1);
		failed.setISDONE("true");
		failedDao.update(failed);

		return true;
	}

	// 衍生数据与PM
	private boolean getDerivedDataAndInvokePm(String usrCde, String idNo, String policyNo, int times,
			ClCapChannel capChannel, ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo,
			ClIdCardInfo idCard, JSONObject usrCnts, BigDecimal premium, ClCrdtExt crdtExt) {
		// 获取衍生数据
		net.sf.json.JSONObject paramsHt = null;
		String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000006-1", policyNo, date));
		ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
				"YGSDPH80000006-1", "个人征信授权书（阳光）--央行", "1", "签章处", "", usr.getUSR_CDE(), "3-3", "", "CI06");

		paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000007-1", policyNo, date));
		ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
				"YGSDPH80000007-1", "个人信息查询使用授权书", "1", "签章处", "", usr.getUSR_CDE(), "3-4", "", "CI06");

		paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000009-1", policyNo, date));
		ucService.fadadaContract(idCard.getCUST_NAME(), idCard.getID_NO(), usr.getUSR_TEL(), paramsHt,
				"YGSDPH80000009-1", "贷款协议（信用类）信托-阳光-附件一个人信息授权书", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-2", "",
				"CI08");

		try {
			boolean htRes = contractWithPolicyNoSerivce.dealContractWithPolicyNo(policyNo);
			if (!htRes) {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		try {
			DecisionReq od = new DecisionReq();
			od.setStrategy_code("CL_CP_0005_00000016");
			od.setApp_key("wd");
			od.setPro_type("xjd");// 待定
			od.setPrd_code("tqd");
			od.setToken("");
			od.setOrg_code("");
			od.setBank_channel("FOTIC");
			od.setChannel("wdapp");

			od.setSession_id("");
			od.setMessage_id("");
			od.setUser_id(usr.getUSR_CDE());
			od.setName(idCard.getCUST_NAME());
			od.setId_no(idCard.getID_NO());
			od.setLoan_number(policyNo);
			od.setMobile(usr.getUSR_TEL());
			od.setTime(DateUtils.getNowFullFmt());
			od.setApp_version(usr.getUSR_CLNTVER());
			od.setOs_version(usr.getUSR_OS() + " " + usr.getUSR_OSVER());
			od.setSdk_version("");

			JSONObject obj = new JSONObject();
			obj.put("idNo", idCard.getID_NO());
			obj.put("idCardType", "0");
			obj.put("name", idCard.getCUST_NAME());
			obj.put("mobile", usr.getUSR_TEL());
			obj.put("bankCardNo", apply.getBankCardNo());
			obj.put("contactName", usrCnts.getString("relationName"));
			obj.put("contactIdNumber", "");
			obj.put("contactMobile", usrCnts.getString("relationPhone"));
			obj.put("contactRelation", getRelationCode(usrCnts.getString("relation")));
			obj.put("otherContactMobile", usrCnts.getString("otherPhone"));
			obj.put("apply_amount", apply.getApplyAmt());
			obj.put("applyTime", apply.getCrtDt());

			obj.put("companyName", usrInfo.getINDIV_EMP_NAME());
			obj.put("companyPhone", usrInfo.getINDIV_EMP_TEL());
			od.setOther(obj);
			long start = new Date().getTime();
			JSONObject resJson = reapi.applyDecision(od);
			long end = new Date().getTime();

			start = new Date().getTime();
			JSONObject result = reapi.getDerivedDataJSONObject(usrCde, idNo, policyNo);
			end = new Date().getTime();
			JSONObject invokeP = new JSONObject();
			invokeP.put("usrCde", usr.getUSR_CDE());
			invokeP.put("idNo", idNo);
			invokeP.put("policyNo", policyNo);
			if ("error".equals(result.getString("code"))) {
				ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE,
						AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int) (end - start),
						invokeP.toJSONString(), "FAIL", result.toJSONString());
				apiInfoDao.insert(apiInfo);
				ClDealFailed failed = new ClDealFailed();
				failed.setUSR_CDE(usrCde);
				failed.setPOLICY_NO(policyNo);
				failed.setFAIL_STEP("DerivedData");
				failed.setISRETRY("true");
				failed.setRETRY_DT(DateUtils.getNowFullFmt());
				failed.setRETRY_TIMES(times + 1);
				failed.setISDONE("false");
				failedDao.update(failed);
				return false;
			}
			if ("ok".equals(result.getString("code"))) {
				if (result.getJSONObject("data").isEmpty()) {//
					ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(),
							AppNormalConstants.RULE_ENGINE, AppNormalConstants.DERIVEDDATA_SERIVCE,
							DateUtils.getNowFullFmt(), (int) (end - start), invokeP.toJSONString(), "FAIL",
							result.toJSONString());
					apiInfoDao.insert(apiInfo);
					ClDealFailed failed = new ClDealFailed();
					failed.setUSR_CDE(usrCde);
					failed.setPOLICY_NO(policyNo);
					failed.setFAIL_STEP("DerivedData");
					failed.setISRETRY("true");
					failed.setRETRY_DT(DateUtils.getNowFullFmt());
					failed.setRETRY_TIMES(times + 1);
					failed.setISDONE("false");
					failedDao.update(failed);
					return false;
				}
				ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.RULE_ENGINE,
						AppNormalConstants.DERIVEDDATA_SERIVCE, DateUtils.getNowFullFmt(), (int) (end - start),
						invokeP.toJSONString(), "SUCCESS", result.toJSONString());
				apiInfoDao.insert(apiInfo);
			}
			// PM
			long pc_start = new Date().getTime();
			JSONObject edu = reapi.getLimit(usrCde, idCard.getCUST_NAME());
			long pc_end = new Date().getTime();
			logger.info(usr.getUSR_CDE() + ",获取白名单额度:" + edu.toJSONString());
			String isWhiteList = edu.getString("isWhiteList");
			if (isWhiteList.equals("N")) {
				crdtExt.setCRDTEXT_TYPE("unkown");
			} else {
				crdtExt.setCRDTEXT_TYPE("innerWhiteList");
			}
			String pmXml = new PMXmlStringUtils().getPMRequestXML("2", capChannel, prdInfo, apply, usr, usrInfo, idCard,
					usrCnts, result.getJSONObject("data"), premium, crdtExt);
			System.out.println(pmXml);
			// 修改用户状态
			clUsrStsService.getGonganSts(usr, result.getJSONObject("data"));
			start = new Date().getTime();
			String pmResult = pmapi.preCredExt(pmXml);
			end = new Date().getTime();
			apply.setPmDt(DateUtils.getNowFullFmt());
			JSONObject rJsonObject2 = new PMXMlResultUtils().parseDecisionResultXML(pmResult);
			String default_reason = rJsonObject2.getString("default_reason");
			String system_decision = rJsonObject2.getString("system_decision");
			String detail = rJsonObject2.get("detail").toString();
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
					ClDealFailed failed = new ClDealFailed();
					failed.setUSR_CDE(usrCde);
					failed.setPOLICY_NO(policyNo);
					failed.setFAIL_STEP("DerivedData");
					failed.setISRETRY("true");
					failed.setRETRY_DT(DateUtils.getNowFullFmt());
					failed.setRETRY_TIMES(times + 1);
					failed.setISDONE("true");
					failedDao.update(failed);
					return false;
				}
				ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM,
						AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int) (end - start), pmXml,
						"SUCCESS", pmResult);
				apiInfoDao.insert(apiInfo);
				apply.setStCde("10");
				apply.setCreditNo(crdtExt.getUSR_APPSEQ());
				apply.setTrdResult(system_decision);
				apply.setTrdDetail(default_reason);
				apply.setMdfDt(DateUtils.getNowFullFmt());
				applyDao.updateByPrimaryKeySelective(apply);
			} else {
				ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.PM,
						AppNormalConstants.PM_DECISION_SERVICE, DateUtils.getNowFullFmt(), (int) (end - start), pmXml,
						"FAIL", pmResult);
				apiInfoDao.insert(apiInfo);
				apply.setStCde("10");
				apply.setCreditNo(crdtExt.getUSR_APPSEQ());
				apply.setTrdResult(system_decision);
				apply.setTrdDetail(default_reason);
				applyDao.updateByPrimaryKeySelective(apply);
				// 失败恢复额度
				usrLmtAmtService.updateUsedAmtRecovery(usr.getUSR_CDE(), new BigDecimal(apply.getApplyAmt()));
				ClDealFailed failed = new ClDealFailed();
				failed.setUSR_CDE(usrCde);
				failed.setPOLICY_NO(policyNo);
				failed.setFAIL_STEP("DerivedData");
				failed.setISRETRY("true");
				failed.setRETRY_DT(DateUtils.getNowFullFmt());
				failed.setRETRY_TIMES(times + 1);
				failed.setISDONE("true");
				failedDao.update(failed);
				return false;
			}

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	// 无效保单
	private boolean invalidPolicy(String usrCde, String idNo, String policyNo, int times, ClCapChannel capChannel,
			ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo, ClIdCardInfo idCard, JSONObject usrCnts,
			BigDecimal premium) {
		try {
			String invaliPolicyXml = new ReqXmlStringUtils().getReqXmlStr(capChannel, prdInfo, apply, usr, usrInfo,
					idCard, usrCnts, premium);
			JSONObject ipolicyResult = new JSONObject();
			ClApiInfo apiInfo = null;
			long start = 0L;
			long end = 0L;
			try {
				start = new Date().getTime();
				ipolicyResult = siOp.inputPolicy(invaliPolicyXml);
				end = new Date().getTime();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ClDealFailed failed = new ClDealFailed();
				failed.setUSR_CDE(usrCde);
				failed.setPOLICY_NO(policyNo);
				failed.setISRETRY("true");
				failed.setRETRY_DT(DateUtils.getNowFullFmt());
				failed.setRETRY_TIMES(times + 1);
				failed.setISDONE("false");
				failedDao.update(failed);
				apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.INVALID_POLICY,
						AppNormalConstants.INVALID_POLICY, DateUtils.getNowFullFmt(), (int) (end - start),
						invaliPolicyXml, "FAIL", ipolicyResult.toJSONString());
				apiInfoDao.insert(apiInfo);
				return false;
			}
			String clientFlag = ipolicyResult.getString("clientFlag");

			if (clientFlag.equals("success")) {
				apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.INVALID_POLICY,
						AppNormalConstants.INVALID_POLICY, DateUtils.getNowFullFmt(), (int) (end - start),
						invaliPolicyXml, "SUCCESS", ipolicyResult.toJSONString());
				apiInfoDao.insert(apiInfo);
				return true;
			} else {
				ClDealFailed failed = new ClDealFailed();
				failed.setUSR_CDE(usrCde);
				failed.setPOLICY_NO(policyNo);
				failed.setISRETRY("true");
				failed.setRETRY_DT(DateUtils.getNowFullFmt());
				failed.setRETRY_TIMES(times + 1);
				failed.setISDONE("false");
				failedDao.update(failed);
				apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.INVALID_POLICY,
						AppNormalConstants.INVALID_POLICY, DateUtils.getNowFullFmt(), (int) (end - start),
						invaliPolicyXml, "FAIL", ipolicyResult.toJSONString());
				apiInfoDao.insert(apiInfo);
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	// 电子保单
	private boolean elePolicy(String usrCde, String idNo, String policyNo, int times, ClCapChannel capChannel,
			ClPrdInfo prdInfo, ClUsrApply apply, ClUsr usr, ClUsrInfo usrInfo, ClIdCardInfo idCard, JSONObject usrCnts,
			BigDecimal premium) {
		try {
			ElePolicyApi api = new ElePolicyApi();
			boolean elePolicy = api.sendElePolicy(capChannel, prdInfo, usr, idCard, apply, usrInfo, premium);
			return elePolicy;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}

	// 同步产品
	private boolean syncPrd(String prdCde) {
		SynProductMSGController SynProductMSGController = new SynProductMSGController();
		ReqProductVO rpVO = new ReqProductVO();
		try {
			rpVO.setServiceId("servP0000100012");
			rpVO.setTyp_cde(prdCde);
			rpVO.setAcct_role_desc("");
			rpVO.setOpt_typ("UPDATE");
			RespProductVO respVO = SynProductMSGController.synProductMSG(rpVO);
			if (respVO.getErrorMsg().equals("success")) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	// 同步放款渠道
	private boolean syncChannel(ClCapChannel channel, ClPrdInfo prdInfo) {
		try {
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
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
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
			creditLoanApplyVO.setACCTTYPE("0");
			creditLoanApplyVO.setACCTTYPE("0");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// 同步放款信息
	private boolean syncLoanMsg(ClUsr usr, ClUsrApply apply, ClCapChannel channel, ClPrdInfo prd, ClIdCardInfo idcard,
			BigDecimal premium) {
		SynLoanMessageController SynLoanMessageController = new SynLoanMessageController();
		try {
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

				String usrCde = idcard.getUSR_CDE();
				String policyNo = apply.getPolicyNo();
				String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
				net.sf.json.JSONObject paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000014-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000014-1", "贷款协议（信用类）信托-阳光-附件二还款代扣授权", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-3",
						"", "CI13");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000008-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000008-1", "贷款合同（信用类）", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-1", "", "CI11");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000010-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000010-1", "保险单", "2", "签单机构地址", policyNo, usr.getUSR_CDE(), "5-1", "投保人签名", "CI12");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000012-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000012-1", "保险单信息确认页", "1", "投保人签名", policyNo, usr.getUSR_CDE(), "5-2", "", "CI12");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000013-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000013-1", "委托扣款授权书-保费", "1", "签章处", policyNo, usr.getUSR_CDE(), "5-3", "", "CI13");
				return true;
			}
			ClApiInfo apiInfo = new ClApiInfo(usr.getUSR_CDE(), usr.getUSR_TEL(), AppNormalConstants.HESUAN,
					AppNormalConstants.SYNC_LOANMSG, DateUtils.getNowFullFmt(), (int) (end - start),
					JSONObject.toJSONString(reqLoanMessageVO), "FAIL", JSONObject.toJSONString(slmc));
			apiInfoDao.insert(apiInfo);
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private boolean sendFkSms(ClUsrApply apply, ClIdCardInfo idcard, ClUsr usr, ClUsrInfo usrinfo, ClBnkCrd bankCard) {
		String[] conten_params = sms.sendFk(idcard, apply, bankCard);
		boolean sendResult = false;
		long s = new Date().getTime();
		sendResult = new SmsCollectionUtil().sendSms(usr.getUSR_TEL(), AppNormalConstants.LOAN, conten_params);// true;
		long e = new Date().getTime();
		String status = sendResult == true ? "SUCCESS" : "FAIL";
		ClApiInfo apiInfo = new ClApiInfo("", usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME,
				AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
				sms.getContentByConditonAndParams(AppNormalConstants.LOAN, conten_params), status,
				new Boolean(sendResult).toString());
		apiInfoDao.insert(apiInfo);
		return sendResult;
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

	private boolean contract(ClUsr usr, ClIdCardInfo idcard, ClUsrApply apply, String step, String policyNoHt,
			String date) {

		String usrCde = idcard.getUSR_CDE();
		String policyNo = "";
		if (policyNoHt.endsWith("Y")) {
			policyNo = policyNo;
		} else if (policyNoHt.endsWith("W")) {
			policyNo = apply.getPolicyNo();
		}

		//

		net.sf.json.JSONObject paramsHt = null;
		try {
			if ("all".equals(step)) {
				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000002-1", "", date));
				paramsHt.put("custName", idcard.getCUST_NAME());
				paramsHt.put("idNo", idcard.getID_NO());
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000002-1", "身份验证授权书", "1", "签章处", "", usr.getUSR_CDE(), "2-1", "", "CI02");

				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000003-1", "", date));
				ucService.fadadaContract(paramsHt.getString("custName"), paramsHt.getString("idNo"), usr.getUSR_TEL(),
						paramsHt, "YGSDPH80000003-1", "数字证书确认及授权书", "1", "签章处", "", usr.getUSR_CDE(), "2-2", "",
						"CI02");

				String ytb_no = apply.getCreditNo();
				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000005-1", ytb_no, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000005-1", "投保单模板（产险-信托）", "1", "签章处", ytb_no, usr.getUSR_CDE(), "3-2", "", "CI05");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000006-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000006-1", "个人征信授权书（阳光）--央行", "1", "签章处", "", usr.getUSR_CDE(), "3-3", "", "CI06");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000007-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000007-1", "个人信息查询使用授权书", "1", "签章处", "", usr.getUSR_CDE(), "3-4", "", "CI06");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000008-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000008-1", "贷款合同（信用类）", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-1", "", "CI11");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000009-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000009-1", "贷款协议（信用类）信托-阳光-附件一个人信息授权书", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-2",
						"", "CI08");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000014-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000014-1", "贷款协议（信用类）信托-阳光-附件二还款代扣授权", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-3",
						"", "CI13");

				boolean isExist = ucService.isExistByPolicyNo(policyNo, "YGSDPH80000011-1");
				if (!isExist) {
					paramsHt = net.sf.json.JSONObject
							.fromObject(fpService.getParams(usrCde, "YGSDPH80000011-1", policyNo, date));
					ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), idcard.getUSR_TEL(), paramsHt,
							"YGSDPH80000011-1", "还款事项提醒函", "1", "签章处", policyNo, usrCde, "4-4", "", "CI06");
				}

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000010-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000010-1", "保险单", "2", "签单机构地址", policyNo, usr.getUSR_CDE(), "5-1", "投保人签名", "CI12");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000012-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000012-1", "保险单信息确认页", "1", "投保人签名", policyNo, usr.getUSR_CDE(), "5-2", "", "CI12");

				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000013-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000013-1", "委托扣款授权书-保费", "1", "签章处", policyNo, usr.getUSR_CDE(), "5-3", "", "CI13");

				return true;
			} else if ("2-1".equals(step)) {
				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000002-1", "", date));
				paramsHt.put("custName", idcard.getCUST_NAME());
				paramsHt.put("idNo", idcard.getID_NO());
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000002-1", "身份验证授权书", "1", "签章处", "", usr.getUSR_CDE(), "2-1", "", "CI02");
				return true;
			} else if ("2-2".equals(step)) {
				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000003-1", "", date));
				ucService.fadadaContract(paramsHt.getString("custName"), paramsHt.getString("idNo"), usr.getUSR_TEL(),
						paramsHt, "YGSDPH80000003-1", "数字证书确认及授权书", "1", "签章处", "", usr.getUSR_CDE(), "2-2", "",
						"CI02");
				return true;
			} else if ("3-2".equals(step)) {
				String ytb_no = apply.getCreditNo();
				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000005-1", ytb_no, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000005-1", "投保单模板（产险-信托）", "1", "签章处", ytb_no, usr.getUSR_CDE(), "3-2", "", "CI05");
				return true;
			} else if ("3-3".equals(step)) {
				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000006-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000006-1", "个人征信授权书（阳光）--央行", "1", "签章处", "", usr.getUSR_CDE(), "3-3", "", "CI06");
				return true;
			} else if ("3-4".equals(step)) {
				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000007-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000007-1", "个人信息查询使用授权书", "1", "签章处", "", usr.getUSR_CDE(), "3-4", "", "CI06");
				return true;
			} else if ("4-1".equals(step)) {
				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000008-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000008-1", "贷款合同（信用类）", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-1", "", "CI11");
				return true;
			} else if ("4-2".equals(step)) {
				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000009-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000009-1", "贷款协议（信用类）信托-阳光-附件一个人信息授权书", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-2",
						"", "CI08");
				return true;
			} else if ("4-3".equals(step)) {
				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000014-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000014-1", "贷款协议（信用类）信托-阳光-附件二还款代扣授权", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-3",
						"", "CI13");
				return true;
			} else if ("4-4".equals(step)) {
				boolean isExist = ucService.isExistByPolicyNo(policyNo, "YGSDPH80000011-1");
				if (!isExist) {
					paramsHt = net.sf.json.JSONObject
							.fromObject(fpService.getParams(usrCde, "YGSDPH80000011-1", policyNo, date));
					ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), idcard.getUSR_TEL(), paramsHt,
							"YGSDPH80000011-1", "还款事项提醒函", "1", "签章处", policyNo, usrCde, "4-4", "", "CI06");
				}
				return true;
			} else if ("5-1".equals(step)) {
				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000010-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000010-1", "保险单", "2", "签单机构地址", policyNo, usr.getUSR_CDE(), "5-1", "投保人签名", "CI12");
				return true;
			} else if ("5-2".equals(step)) {
				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000012-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000012-1", "保险单信息确认页", "1", "投保人签名", policyNo, usr.getUSR_CDE(), "5-2", "", "CI12");
				return true;
			} else if ("5-3".equals(step)) {
				paramsHt = net.sf.json.JSONObject
						.fromObject(fpService.getParams(usrCde, "YGSDPH80000013-1", policyNo, date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000013-1", "委托扣款授权书-保费", "1", "签章处", policyNo, usr.getUSR_CDE(), "5-3", "", "CI13");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	private String getRelationCode(String relation) {
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
}
