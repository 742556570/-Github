package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.app.api.sms.SmsCollectionUtil;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClBnkCrdDao;
import com.jeeplus.modules.app.dao.ClCrdtExtDao;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClPrdInfoDao;
import com.jeeplus.modules.app.dao.ClPsnCHKDao;
import com.jeeplus.modules.app.dao.ClSyncDxDao;
import com.jeeplus.modules.app.dao.ClUsrLoanDao;
import com.jeeplus.modules.app.dao.ClUsrApplyDao;
import com.jeeplus.modules.app.dao.ClUsrCntsDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.dao.ClUsrInfoDao;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClPsnCHk;
import com.jeeplus.modules.app.entity.ClSyncDx;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrCnts;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.entity.ClUsrLmtamt;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.utils.AppDateUtils;
import com.jeeplus.modules.app.utils.CacheHelper;
import com.jeeplus.modules.app.utils.GenToken;

@Service
@Transactional(readOnly = false)
public class ClUsrService extends CrudService<ClUsrDao, ClUsr> {

	@Autowired
	private ClUsrDao clUsrDao;

	@Autowired
	private ClIdCardInfoDao clIdCrdDao;

	@Autowired
	private ClPsnCHKDao clPsnChkDao;

	@Autowired
	private ClBnkCrdDao clBnkCrdDao;

	@Autowired
	private ClUsrCntsDao clUsrCntsDao;

	@Autowired
	private ClUsrInfoDao clUsrInfoDao;

	@Autowired
	private ClCrdtExtDao clCrdtExtDao;

	@Autowired
	private ClPrdInfoDao clPrdInfoDao;

	@Autowired
	private ClUsrApplyDao clApplyDao;
	
	@Autowired
	private ClUsrLoanDao clUsrLoanDao;

	@Autowired
	private ClSyncDxDao syncDxDao;

	@Autowired
	private ClCreditExtService clCreditServicce;

	@Autowired
	private ClUsrLmtamtService lmtAmtService;

	@Autowired
	private ClApiInfoService apiService;

	public boolean valiMdnExists(String tel) {
		int i = clUsrDao.countByUsrTel(tel);
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	public int sendSms(String tel) {
		String isOnline = Global.getConfig("isOnline");
		if (!"false".equals(isOnline)) {
			Random rnd = new Random();
			int num = rnd.nextInt(8999) + 1000;
			long s = new Date().getTime();
			String[] params = { num + "" };
			boolean sendResult = new SmsCollectionUtil().sendSms(tel, AppNormalConstants.LOGINORREGIST, params);
			long e = new Date().getTime();
			String status = sendResult == true ? "SUCCESS" : "FAIL";
			ClApiInfo apiInfo = new ClApiInfo("", tel, AppNormalConstants.SMS_API_NAME,
					AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
					"验证码：" + num + "。", status, new Boolean(sendResult).toString());
			apiService.addInvokeApiInfo(apiInfo);
			if (sendResult) {
				return num;
			} else {
				return -1;
			}
		} else {
			Random rnd = new Random();
			int num = 1234;// rnd.nextInt(8999) + 1000;
			long s = new Date().getTime();
			String[] params = { num + "" };
			boolean sendResult = true;// new SmsCollectionUtil().sendSms(tel, AppNormalConstants.LOGINORREGIST,
										// params);
			long e = new Date().getTime();
			String status = sendResult == true ? "SUCCESS" : "FAIL";
			ClApiInfo apiInfo = new ClApiInfo("", tel, AppNormalConstants.SMS_API_NAME,
					AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int) (e - s),
					"验证码：" + num + "。", status, new Boolean(sendResult).toString());
			apiService.addInvokeApiInfo(apiInfo);
			if (sendResult) {
				return num;
			} else {
				return -1;
			}
		}
	}

	public void cacheRmdCde(String tel, String rmdCde) {
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"),
				tel + AppCommonConstants.getValStr("CACHETYPESUFFIX_REGS"), rmdCde,
				AppCommonConstants.getValInt("CACHEREGSTTL"));
	}

	public String getRmdCde(String tel) {
		String result = "";
		Object obj = CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"),
				tel + AppCommonConstants.getValStr("CACHETYPESUFFIX_REGS"));
		if (obj == null) {
			result = null;
		} else {
			result = obj.toString();
		}
		return result;
	}

	public int countFailSmsCde(String tel) {
		int i = 0;
		Object obj = CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), tel + "_cntFail");
		if (obj == null) {
			i = 0;
		} else {
			i = (int) obj;
		}
		i++;
		CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"), tel + "_cntFail", i, 300);
		return i;

	}

	public void rmvCde(String tel) {
		CacheHelper.removeObject(AppCommonConstants.getValStr("CACHESCHEMA"),
				tel + AppCommonConstants.getValStr("CACHETYPESUFFIX_REGS"));
	}

	public ClUsr getByTel(String tel) {
		return clUsrDao.getByUsrTel(tel);
	}

	public ClUsr regist(String tel, Map<String, String> headerMap) {
		ClUsr usr = new ClUsr();
		String usrCde = IdGen.uuid2();
		usr.setUSR_CDE(usrCde);
		usr.setUSR_TEL(tel);
		usr.setUSR_STS("1");
		usr.setCRT_DT(AppDateUtils.getNowAs_yMDHmsS());
		usr.setUSR_LAST_DT(AppDateUtils.getNowAs_yMDHmsS());
		usr.setUSR_LAST_IP(headerMap.get("ip"));
		usr.setUSR_PLTFM(headerMap.get("usrPltfm"));
		usr.setUSR_OS(headerMap.get("usrOs"));
		usr.setUSR_OSVER(headerMap.get("usrOsVer"));
		usr.setUSR_CLNTVER(headerMap.get("usrClntVer"));
		usr.setUSR_IMEI(headerMap.get("usrImei"));
		usr.setUSR_OPENID("");
		usr.setUSR_PROMOV("");
		usr.setUSR_THRDID1("");
		usr.setUSR_THRDID2("");
		usr.setUSR_THRDID3("");
		String token = GenToken.getToken(usrCde, tel);
		usr.setUSR_TOKEN(token);
		usr.setUSR_SOURCE(headerMap.get("usrSource"));
		usr.setUSR_PPWD("");

		int i = clUsrDao.insert(usr);
		if (i > 0) {
			CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"),
					token + AppCommonConstants.getValStr("CACHETYPESUFFIX_LOGIN"), usr,
					AppCommonConstants.getValInt("CACHELOGINTTL"));
			// CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"),
			// token+AppCommonConstants.getValStr("CACHETYPESUFFIX_LOGIN"), usrCde,
			// AppCommonConstants.getValInt("CACHELOGINTTL"));
			CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"),
					usrCde + AppCommonConstants.getValStr("CACHETYPESUFFIX_LOGIN"), token,
					AppCommonConstants.getValInt("CACHELOGINTTL"));
			return usr;
		}
		return null;
	}

	public ClUsr login(String tel, Map<String, String> headerMap) {
		ClUsr usr = clUsrDao.getByUsrTel(tel);
		if (usr != null) {
			String usrCde = usr.getUSR_CDE();
			String token = GenToken.getToken(usrCde, tel);
			CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"),
					token + AppCommonConstants.getValStr("CACHETYPESUFFIX_LOGIN"), usr,
					AppCommonConstants.getValInt("CACHELOGINTTL"));
			// CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"),
			// token+AppCommonConstants.getValStr("CACHETYPESUFFIX_LOGIN"), usrCde,
			// AppCommonConstants.getValInt("CACHELOGINTTL"));
			CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"),
					usrCde + AppCommonConstants.getValStr("CACHETYPESUFFIX_LOGIN"), token,
					AppCommonConstants.getValInt("CACHELOGINTTL"));
			ClUsr musr = new ClUsr();
			musr.setUSR_CDE(usrCde);
			musr.setUSR_TEL(tel);
			musr.setUSR_TOKEN(token);
			musr.setUSR_LAST_DT(AppDateUtils.getNowAs_yMDHmsS());
			musr.setUSR_LAST_IP(headerMap.get("ip"));
			musr.setUSR_PLTFM(headerMap.get("usrOs"));
			musr.setUSR_OS(headerMap.get("source"));
			musr.setUSR_OSVER(headerMap.get("systemver"));
			musr.setUSR_CLNTVER(headerMap.get("appversion"));
			musr.setUSR_IMEI(headerMap.get("usrImei"));
			musr.setUSR_SOURCE(headerMap.get("yyqd"));
			musr.setAPP_CHANNEL(headerMap.get("channelsource"));
			clUsrDao.update(musr);
			usr.setUSR_TOKEN(token);
			return usr;
		}
		return null;
	}

	public String checkUsrStatus(ClUsr usr,String isLoaning) {

		String USR_CDE = usr.getUSR_CDE();
		JsonObject resultJson = new JsonObject();
		JsonObject responseData = new JsonObject();
		
		usr = clUsrDao.getByUsrCode(USR_CDE);

		ClIdCardInfo idCrd = clIdCrdDao.getByUsrCode(USR_CDE);
		if (idCrd != null) {
			responseData.addProperty("isOcr", "true");
		} else {
			responseData.addProperty("isOcr", "false");
		}

		List<ClPsnCHk> psnChk = clPsnChkDao.selectByUsrCdeAndStatus(USR_CDE);
		if (psnChk != null && psnChk.size() >= 1) {
			if(psnChk.get(0).getCkinfo().equals("true"))  {
				responseData.addProperty("isLivBdy", "true");
			}
		} else {
			responseData.addProperty("isLivBdy", "false");
		}

		List<ClBnkCrd> bnkCrds = clBnkCrdDao.getByUsrCode(USR_CDE);
		String isNeedPop = "false";
		String popType = "";
		if (bnkCrds != null && bnkCrds.size() > 0) {
			responseData.addProperty("isBindBnkCd", "false");
			for (ClBnkCrd clBnkCrd : bnkCrds) {
				String isBind = clBnkCrd.getIS_BIND();
				String isBindFotic = clBnkCrd.getIS_BINDFOTIC();
				if ("0".equals(isBind) && !"2".endsWith(isBindFotic) && "1".equals(clBnkCrd.getIS_DEF())) {
					isNeedPop = "true";
				}
				if (("0".equals(isBind) || "2".equals(isBindFotic)) && "1".equals(clBnkCrd.getIS_DEF())) {
					responseData.addProperty("isBindBnkCd", "true");
				}
			}
		} else {
			responseData.addProperty("isBindBnkCd", "false");
		}

		List<ClUsrCnts> usrCnts = clUsrCntsDao.getByUsrCode(USR_CDE);
		if (usrCnts != null && usrCnts.size() > 0) {
			responseData.addProperty("isContact", "true");
		} else {
			responseData.addProperty("isContact", "false");
		}

		ClUsrInfo usrInfo = clUsrInfoDao.getByUsrCode(USR_CDE);
		if (usrInfo != null) {
			responseData.addProperty("isCmpInf", "true");
		} else {
			responseData.addProperty("isCmpInf", "false");
		}

		
		BigDecimal used = new BigDecimal("0");
		List<ClUsrApply> list = clApplyDao.getByUsrCde(USR_CDE);// 审核状态
		if (list.size() > 0) {
			boolean isFlag = false;
			responseData.addProperty("isApplied", "true");
			for (ClUsrApply clUsrApply : list) {
				if (clUsrApply.getStCde().equals("01")) {
					isFlag = true;

				} else if (clUsrApply.getStCde().equals("11") && (!clUsrApply.getDebtStatus().equals("40"))) {
					popType = "applied";
				}
				
				if("11".equals(clUsrApply.getStCde()) || "12".equals(clUsrApply.getStCde())) {
					used.add(new BigDecimal(clUsrApply.getApplyAmt()));
				}
			}
			
			if (isFlag) {
				responseData.addProperty("applyStatus", "提现审核中");
			} else {
				if ("true".equals(isLoaning)) {
					responseData.addProperty("applyStatus", "提现未结清");
				}else if ("false".equals(isLoaning)){
					responseData.addProperty("applyStatus", "提现已结清");
				}else {
					responseData.addProperty("applyStatus", "");
				}
			}
		} else {
			responseData.addProperty("isApplied", "false");
			responseData.addProperty("applyStatus", "未申请提现");
		}
		
		ClCrdtExt cedtExt = clCrdtExtDao.getClCrdtExtByUsrCde(USR_CDE);
		if (cedtExt != null) {
			responseData.addProperty("isCreditExtension", "true");

			ClCrdtExt jsonObj = clCreditServicce.queryCreditJsonObj(usr);
			if ("11".equals(jsonObj.getSTATUS())) {
				if(used.doubleValue() > 0) {
					lmtAmtService.updateUsedAmtOnce(USR_CDE, used);
				}
				lmtAmtService.syncRepayAmt(USR_CDE);
				ClUsrLmtamt lmtAmt = lmtAmtService.getByUsrCde(USR_CDE);
				BigDecimal usrUsable = lmtAmt.getCreditAmount().subtract(lmtAmt.getUsedAmount())
						.add(lmtAmt.getRepayAmount());
				BigDecimal userMax = lmtAmt.getCreditAmount();
				String prdSeq = cedtExt.getPRD_CDE();
				ClPrdInfo prdInfo = clPrdInfoDao.getByPrdCde(prdSeq);
				responseData.addProperty("creditStatus", "已授信");
				responseData.addProperty("usrMax", userMax.toString());
				responseData.addProperty("usrUsable", usrUsable.toString());
				responseData.add("prdInfo", new Gson().fromJson(new Gson().toJson(prdInfo), JsonElement.class));
				if(!"applied".equals(popType)) {
					popType = "credit";
				}
				String imgPath = AppCommonConstants.getValStr("img4ShowPath");
				responseData.addProperty("isShowImg", "true");
				responseData.addProperty("imgPath", imgPath);
			} else if ("10".equals(jsonObj.getSTATUS())) {
				responseData.add("prdInfo", new Gson().fromJson(new Gson().toJson(new Object()), JsonElement.class));
				responseData.addProperty("creditStatus", "未通过授信");
			} else {
				responseData.add("prdInfo", new Gson().fromJson(new Gson().toJson(new Object()), JsonElement.class));
				responseData.addProperty("creditStatus", "授信中");
			}
		} else {
			ClPrdInfo prdInfo = clPrdInfoDao.getByPrdCde("QD");
			responseData.addProperty("isCreditExtension", "false");
			responseData.addProperty("creditStatus", "未授信");
			responseData.add("prdInfo", new Gson().fromJson(new Gson().toJson(prdInfo), JsonElement.class));
			responseData.addProperty("isShowImg", "false");
			responseData.addProperty("imgPath", "");
		}
		if("true".equals(isNeedPop)) {
			responseData.addProperty("isNeedPop",isNeedPop);
			responseData.addProperty("popType",popType);
//			responseData.addProperty("isBindBnkCd","true");
		}else {
			responseData.addProperty("isNeedPop",isNeedPop);
			responseData.addProperty("popType","");
		}
		String isAllDown = AppCommonConstants.getValStr("isAllDown");
		responseData.addProperty("isAllDown", isAllDown);
		
		
		String isGonganPoor = "false";
		if(usr.getUSR_GRD().contains("gonganPoor")) {
			isGonganPoor = "true";
		}
		responseData.addProperty("isGonganPoor", isGonganPoor);
		
		resultJson.addProperty("status", ResponeJsonObj.SUCCESS);
		resultJson.addProperty("msg", "");
		
		int foticNum = clUsrLoanDao.getUsrLoanFotic(USR_CDE);
		if(foticNum >=1) {
			responseData.addProperty("isFotic", "true");
		}else {
			responseData.addProperty("isFotic", "false");
		}
		
		
		String isShowImg = AppCommonConstants.getValStr("IsShowImg");
		if(cedtExt != null && "true".equals(isShowImg) && "false".equals(isLoaning)) {
			String imgPath = AppCommonConstants.getValStr("img4ShowPath");
			responseData.addProperty("isShowImg", "true");
			responseData.addProperty("imgPath", imgPath);
		}else {
			responseData.addProperty("isShowImg", "false");
			responseData.addProperty("imgPath", "");
		}
		
		if(cedtExt == null) {
			responseData.addProperty("isShowImg", "false");
			responseData.addProperty("imgPath", "");
		}
		
		String isCloseService = AppCommonConstants.getValStr("IsNeedShutdownLoanService");
		System.out.println("isCloseService="+isCloseService);
		JSONObject result = new JSONObject();
		result.put("isLoanServiceOn", "true");
		result.put("loanServiceMsg", "");
		if("true".equals(isCloseService)) {
			responseData.addProperty("isLoanServiceOn", "false");
			responseData.addProperty("loanServiceMsg", "服务升级中，请稍后再试。");
		}
		
		String singleApp = AppCommonConstants.getValStr("SingleApplyStatusService");
		String singleApplyMsg = AppCommonConstants.getValStr("SINGLEAPPLYWORD");
		responseData.addProperty("sngApSts", singleApp);
		responseData.addProperty("sngApMsg", singleApplyMsg);
		if("true".equals(singleApp)) {
			if ("true".equals(isLoaning)) {
				responseData.addProperty("isSingleApplyOn", "true");
				responseData.addProperty("singleApplyMsg", singleApplyMsg);
			}else if ("false".equals(isLoaning)){
				responseData.addProperty("isSingleApplyOn", "false");
				responseData.addProperty("singleApplyMsg", "无需限制");
			}
		} else {
			responseData.addProperty("isSingleApplyOn", "false");
			responseData.addProperty("singleApplyMsg", "无需限制");
		}

		
		resultJson.add("responseData", responseData);
		return resultJson.toString();

	}

	public Map<String, String> idCardInfo(String usrCde) {
		ClIdCardInfo idCrd = clIdCrdDao.getByUsrCode(usrCde);
		Map<String, String> map = new HashMap<String, String>();
		map.put("id_no", "");
		map.put("name", "");
		if (idCrd != null) {
			map.put("id_no", idCrd.getID_NO());
			map.put("name", idCrd.getCUST_NAME());
		}
		return map;
	}

	public ClUsr getByUsrCde(String usrCde) {
		return clUsrDao.getByUsrCode(usrCde);
	}

	public JsonObject getUsrStatus(ClUsr usr) {

		String USR_CDE = usr.getUSR_CDE();
		System.out.println(USR_CDE);

		boolean isUpdate = false;

		ClSyncDx i = syncDxDao.getByUsrCode(USR_CDE);

		System.out.println("i=" + i);
		if (i != null) {
			isUpdate = true;
		}
		System.out.println("isUpdate=" + isUpdate);

		JsonObject resultJson = new JsonObject();
		JsonObject responseData = new JsonObject();

		resultJson.addProperty("usrCde", usr.getUSR_CDE());
		resultJson.addProperty("usrMobile", usr.getUSR_TEL());

		ClIdCardInfo idCrd = clIdCrdDao.getByUsrCode(USR_CDE);
		System.out.println("idCrd=" + idCrd);
		if (idCrd != null) {
			responseData.addProperty("isOcr", "true");
		} else {
			responseData.addProperty("isOcr", "false");
		}

		List<ClPsnCHk> psnChk = clPsnChkDao.selectByUsrCdeAndStatus(USR_CDE);
		if (psnChk != null && psnChk.size() >= 1) {
			if(psnChk.get(0).getCkinfo().equals("true"))  {
				responseData.addProperty("isLivBdy", "true");
			}
		} else {
			responseData.addProperty("isLivBdy", "false");
		}

		List<ClBnkCrd> bnkCrds = clBnkCrdDao.getByUsrCode(USR_CDE);
		System.out.println("bnkCrds=" + bnkCrds);
		if (bnkCrds != null && bnkCrds.size() > 0) {
			responseData.addProperty("isBindBnkCd", "false");
			for (ClBnkCrd clBnkCrd : bnkCrds) {
				String isBind = clBnkCrd.getIS_BIND();
				String isBindFotic = clBnkCrd.getIS_BINDFOTIC();
				if ("0".equals(isBind) || "2".equals(isBindFotic)) {
					responseData.addProperty("isBindBnkCd", "true");
				}
			}
		} else {
			responseData.addProperty("isBindBnkCd", "false");
		}

		List<ClUsrCnts> usrCnts = clUsrCntsDao.getByUsrCode(USR_CDE);
		System.out.println("usrCnts=" + usrCnts);
		if (usrCnts != null && usrCnts.size() > 0) {
			responseData.addProperty("isContact", "true");
		} else {
			responseData.addProperty("isContact", "false");
		}

		ClUsrInfo usrInfo = clUsrInfoDao.getByUsrCode(USR_CDE);
		System.out.println("usrInfo=" + usrInfo);
		if (usrInfo != null) {
			responseData.addProperty("isCmpInf", "true");
		} else {
			responseData.addProperty("isCmpInf", "false");
		}

		ClCrdtExt cedtExt = clCrdtExtDao.getClCrdtExtByUsrCde(USR_CDE);
		System.out.println("cedtExt=" + cedtExt);
		if (cedtExt != null) {
			responseData.addProperty("isCreditExtension", "true");

			ClCrdtExt jsonObj = clCreditServicce.queryCreditJsonObj(usr);
			if ("11".equals(jsonObj.getSTATUS())) {
				lmtAmtService.syncRepayAmt(USR_CDE);
				ClUsrLmtamt lmtAmt = lmtAmtService.getByUsrCde(USR_CDE);
				BigDecimal usrUsable = lmtAmt.getCreditAmount().subtract(lmtAmt.getUsedAmount())
						.add(lmtAmt.getRepayAmount());
				BigDecimal userMax = lmtAmt.getCreditAmount();
				String prdSeq = cedtExt.getPRD_CDE();
				ClPrdInfo prdInfo = clPrdInfoDao.getByPrdCde(prdSeq);
				responseData.addProperty("creditStatus", "已授信");
				responseData.addProperty("usrMax", userMax.toString());
				responseData.addProperty("usrUsable", usrUsable.toString());
				responseData.add("prdInfo", new Gson().fromJson(new Gson().toJson(prdInfo), JsonElement.class));
				String imgPath = AppCommonConstants.getValStr("img4ShowPath");
				responseData.addProperty("isShowImg", "true");
				responseData.addProperty("imgPath", imgPath);
			} else if ("10".equals(jsonObj.getSTATUS())) {
				responseData.add("prdInfo", new Gson().fromJson(new Gson().toJson(new Object()), JsonElement.class));
				responseData.addProperty("creditStatus", "未通过授信");
			} else {
				responseData.add("prdInfo", new Gson().fromJson(new Gson().toJson(new Object()), JsonElement.class));
				responseData.addProperty("creditStatus", "授信中");
			}
		} else {
			ClPrdInfo prdInfo = clPrdInfoDao.getByPrdCde("QD");
			responseData.addProperty("isCreditExtension", "false");
			responseData.addProperty("creditStatus", "未授信");
			responseData.add("prdInfo", new Gson().fromJson(new Gson().toJson(prdInfo), JsonElement.class));
		}

		List<ClUsrApply> list = clApplyDao.getByUsrCde(USR_CDE);// 审核状态
		System.out.println("list=" + list);
		if (list.size() > 0) {
			boolean isFlag = false;
			boolean isFlag2 = false;
			boolean isFlag3 = false;
			responseData.addProperty("isApplied", "true");
			for (ClUsrApply clUsrApply : list) {
				if (clUsrApply.getStCde().equals("01")) {
					isFlag = true;
				}
				if (clUsrApply.getStCde().equals("11")) {
					isFlag2 = true;
				}
				if (clUsrApply.getStCde().equals("13")) {
					isFlag3 = true;
				}
			}
			if (isFlag) {
				responseData.addProperty("applyStatus", "提现审核中");
			} else if (isFlag2) {
				responseData.addProperty("applyStatus", "已提现");
			} else if (isFlag3) {
				responseData.addProperty("applyStatus", "放款失败");
			}else {
				responseData.addProperty("applyStatus", "");
			}
		} else {
			responseData.addProperty("isApplied", "false");
			responseData.addProperty("applyStatus", "未申请提现");
		}

		String isAuthentication = "false";
		String idNo = "";
		String isCredit = "false";
		String creditStatus = "na";
		String isLoan = "false";
		String loanStatus = "na";
		String limit = "0.00";

		if (responseData.get("isOcr").getAsString().equals("true")
				&& responseData.get("isLivBdy").getAsString().equals("true")
				&& responseData.get("isBindBnkCd").getAsString().equals("true")) {
			isAuthentication = "true";
			resultJson.addProperty("isAuthentication", isAuthentication);
			idNo = idCrd.getID_NO();
			resultJson.addProperty("idNo", idNo);
		} else {
			resultJson.addProperty("usrCde", usr.getUSR_CDE());
			resultJson.addProperty("usrMobile", usr.getUSR_TEL());
			resultJson.addProperty("isAuthentication", isAuthentication);
			resultJson.addProperty("idNo", idNo);
			resultJson.addProperty("isCredit", isCredit);
			resultJson.addProperty("creditStatus", creditStatus);
			resultJson.addProperty("isLoan", isLoan);
			resultJson.addProperty("loanStatus", loanStatus);
			resultJson.addProperty("limit", limit);

			return resultJson;
		}

		if (responseData.get("isCreditExtension").getAsString().equals("true")) {
			isCredit = "true";
			resultJson.addProperty("isCredit", isCredit);
		} else {
			resultJson.addProperty("usrCde", usr.getUSR_CDE());
			resultJson.addProperty("usrMobile", usr.getUSR_TEL());
			resultJson.addProperty("isAuthentication", isAuthentication);
			resultJson.addProperty("idNo", idNo);
			resultJson.addProperty("isCredit", isCredit);
			resultJson.addProperty("creditStatus", creditStatus);
			resultJson.addProperty("isLoan", isLoan);
			resultJson.addProperty("loanStatus", loanStatus);
			resultJson.addProperty("limit", limit);

			return resultJson;
		}

		if (responseData.get("creditStatus").getAsString().equals("已授信")) {
			creditStatus = "accept";
		} else {
			creditStatus = "reject";
		}
		resultJson.addProperty("creditStatus", creditStatus);

		if (responseData.get("isApplied").getAsString().equals("true")) {
			isLoan = "true";
			resultJson.addProperty("isLoan", isLoan);
		} else {
			resultJson.addProperty("usrCde", usr.getUSR_CDE());
			resultJson.addProperty("usrMobile", usr.getUSR_TEL());
			resultJson.addProperty("isAuthentication", isAuthentication);
			resultJson.addProperty("idNo", idNo);
			resultJson.addProperty("isCredit", isCredit);
			resultJson.addProperty("creditStatus", creditStatus);
			resultJson.addProperty("isLoan", isLoan);
			resultJson.addProperty("loanStatus", loanStatus);
			if (responseData.get("usrMax") == null) {
				limit = "0.00";
			} else {
				limit = responseData.get("usrMax").getAsString();
				if (StringUtils.isEmpty(limit)) {
					limit = "0.00";
				}
			}

			resultJson.addProperty("limit", limit);
			return resultJson;
		}

		boolean reject_flag = false;

		for (ClUsrApply apply : list) {
			if (!apply.getStCde().equals("10")) {
				reject_flag = true;
			}
		}

		if (reject_flag) {
			loanStatus = "accept_om";
		} else {
			loanStatus = "reject_al";
		}
		resultJson.addProperty("loanStatus", loanStatus);
		resultJson.addProperty("limit", "0.00");
		if (isUpdate) {
			ClSyncDx dx = new ClSyncDx();
			dx.setUSR_CDE(USR_CDE);
			dx.setIS_SYNC((Integer.parseInt(i.getIS_SYNC()) + 1) + "");
			dx.setCRT_DT(DateUtils.getNowFullFmt());
			syncDxDao.update(dx);
			return null;
		} else {
			ClSyncDx dx = new ClSyncDx();
			dx.setUSR_CDE(USR_CDE);
			dx.setIS_SYNC("1");
			dx.setCRT_DT(DateUtils.getNowFullFmt());
			syncDxDao.insert(dx);
		}

		return resultJson;
	}

	public ClUsr getByToken(String usrToken) {
		return clUsrDao.getByToken(usrToken);
	}

	public int updateUsrGrd(ClUsr usr) {
		return clUsrDao.updateUsrGrd(usr);
	}

	public int getDayIpCount(String loginIp) {
		return clUsrDao.getDayIpCount(loginIp);
	}

	public int getMonthIpCount(String loginIp) {
		return clUsrDao.getMonthIpCount(loginIp);
	}
}
