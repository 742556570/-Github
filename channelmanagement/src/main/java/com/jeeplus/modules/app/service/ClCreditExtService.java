package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.api.eventreprot.Reporter;
import com.jeeplus.modules.app.api.eventreprot.ReqJsonUtils;
import com.jeeplus.modules.app.api.pm.PMApi;
import com.jeeplus.modules.app.api.rulesengine.RulesEngineApi;
import com.jeeplus.modules.app.api.rulesengine.pojo.DecisionReq;
import com.jeeplus.modules.app.dao.ClAppIdGenDao;
import com.jeeplus.modules.app.dao.ClBnkCrdDao;
import com.jeeplus.modules.app.dao.ClCrdtExtDao;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClPrdInfoDao;
import com.jeeplus.modules.app.dao.ClUsrApplyDao;
import com.jeeplus.modules.app.dao.ClUsrCntsDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrCnts;
import com.jeeplus.modules.app.entity.ClUsrLmtamt;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;

@Service
public class ClCreditExtService {

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
	private ClUsrApplyDao applyDao;
	
	@Autowired
	private ClUsrDao usrDao;
	
	@Autowired
	private ClUsrLmtamtService lmtAmtService;
	
	@Autowired
	private ClPmPreCreditService pmPreService;
	
	private RulesEngineApi reapi = new RulesEngineApi();
	
	private PMApi pmApi = new PMApi();
	
	
	
	public JSONObject decision(ClUsr usr,String prdCde,String loan_number,Date sendDt,JSONObject other) {
		return null;
	}
	
	public ClCrdtExt getCrdtExt(String usrCde) {
		ClCrdtExt crdt = crdtDao.getClCrdtExtByUsrCde(usrCde);
		return crdt;
	}
	

	public String getWdData(ClUsr usr,String prepareInsurePoluceSeq) {
		String usrCde = usr.getUSR_CDE();
		ClIdCardInfo idCardInfo = idCardDao.getByUsrCode(usrCde);
		String id_no = idCardInfo.getID_NO();
		String wdData = reapi.getDerivedData(usrCde, id_no, prepareInsurePoluceSeq);
		
		return wdData;
	}
	
	public ClCrdtExt queryCreditJsonObj(ClUsr usr) {
		String usrCde = usr.getUSR_CDE();
		ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(usrCde);
		/*if("00".equals(crdtExt.getSTATUS())) {
			String ytb_no = crdtExt.getUSR_APPSEQ();
			ClIdCardInfo idCardInfo = idCardDao.getByUsrCode(usrCde);
			String id_no = idCardInfo.getID_NO();
			JSONObject obj = getCrdtResult(usrCde, id_no);
			String time = obj.getString("time");
			String code = obj.getString("code");
			String amount = obj.getString("amount");
			if("ok".equalsIgnoreCase(code)) {
				if("0.00".equals(amount)) {
					crdtExt.setSTATUS("10");
				}else {
					crdtExt.setSTATUS("11");
					BigDecimal amt = new BigDecimal(amount);
					ClPrdInfo prd = prdDao.getByPrdCde("TQD");
					String prdCde = "TQD";
					crdtExt.setPRD_CDE("TQD");
					crdtExt.setADJ_AMT(amt);
					crdtExt.setADJ_AMT_MAX(amt);
					crdtExt.setADJ_AMT_MIN(amt);
					crdtExt.setADJ_RAT(prd.getINT_RAT());
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
			int i = crdtDao.updateByAppSeq(crdtExt);
			if(i > 0) {
				return crdtExt;
			}else {
				return crdtExt;
			}
		}else {
			return crdtExt;
		}*/
		return crdtExt;
	}
	
	
	
	public String queryCredit(ClUsr usr) {
		String usrCde = usr.getUSR_CDE();
		ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(usrCde);
		if("10".equals(crdtExt.getSTATUS())) {
			String ytb_no = crdtExt.getUSR_APPSEQ();
			ClIdCardInfo idCardInfo = idCardDao.getByUsrCode(usrCde);
			String id_no = idCardInfo.getID_NO();
			JSONObject obj = getCrdtResult(usrCde, id_no);
			String time = obj.getString("time");
			String code = obj.getString("code");
			String amount = obj.getString("amount");
			
			if("ok".equalsIgnoreCase(code)) {
				if("0".equals(amount)) {
					crdtExt.setSTATUS("10");
				}else {
					crdtExt.setSTATUS("11");
					BigDecimal amt = new BigDecimal(amount);
					ClPrdInfo prd = prdDao.getByPrdCde("TQD");
					String prdCde = "TQD";
					crdtExt.setPRD_CDE("TQD");
					crdtExt.setADJ_AMT(amt);
					crdtExt.setADJ_AMT_MAX(amt);
					crdtExt.setADJ_AMT_MIN(amt);
					crdtExt.setADJ_RAT(prd.getINT_RAT());
					lmtAmtService.add(usrCde, amt);
					
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
			int i = crdtDao.update(crdtExt);
			if(i > 0) {
				return getReturnByCrtd(crdtExt);
			}else {
				return ResponeJsonObj.getResJsonObj(ResponeJsonObj.FAIL, "更新授信状态失败", null);
			}
		}else {
			return getReturnByCrtd(crdtExt);
		}
	}
	
	
	private String getReturnByCrtd(ClCrdtExt crdtExt) {
		if("10".equals(crdtExt.getSTATUS())) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "授信未通过", null);
		}else if ("11".equals(crdtExt.getSTATUS())){
			String prdId = crdtExt.getPRD_CDE();
			ClPrdInfo clPrdInfo = prdDao.getByPrdCde(prdId);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "授信未通过", clPrdInfo);
		}else if("21".equals(crdtExt.getSTATUS())) {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "人工授信未通过", null);
		}else if ("22".equals(crdtExt.getSTATUS())){
			String prdId = crdtExt.getPRD_CDE();
			ClPrdInfo clPrdInfo = prdDao.getByPrdCde(prdId);
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "授信未通过", clPrdInfo);
		}else {
			return ResponeJsonObj.getResJsonObj(ResponeJsonObj.SUCCESS, "授信中", null);
		}
	}
	
	
	
	
	
	public JSONObject getCrdtResult(String usrCde ,String id_no) {
		
		JSONObject jsonStr = reapi.getLimit(usrCde, id_no);
		
		return jsonStr;
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
	
	public String queryCrditPrdId(String usrCde) {

		ClCrdtExt crdt = crdtDao.getClCrdtExtByUsrCde(usrCde);

		if (crdt != null && !"10".equals(crdt.getST_CDE())) {
			String prdSeq = crdt.getPRD_CDE();
			
			return prdSeq;

		} else {
			return "";
		}

	}
	
	public JSONObject queryPrdInfo(ClUsr usr,String id_no) {
		JSONObject json = reapi.getLimit(usr.getUSR_CDE(), id_no);
		String code = json.getString("code");
		String amount = json.getString("amount");
		if("ok".equalsIgnoreCase(code)) {
			if("0".equals(amount)) {
				return new JSONObject();
			}else {
				ClPrdInfo prd = prdDao.getByPrdCde("TQD");
				String jsonStr = new Gson().toJson(prd);
				return JSONObject.parseObject(jsonStr);
			}
			
		}else {
			return new JSONObject();
		}
	}

	
	public int getCrtId() {
		return appIdDao.getCrtId();
	}
	
	public int saveCrdExt(ClCrdtExt crdtExt) {
		return crdtDao.insert(crdtExt);
	}

	
	public ClCrdtExt getClCrdtExtByPolicyNo(String USR_APPSEQ) {
		return crdtDao.getClCrdtExtByPolicyNo(USR_APPSEQ);
	}
	
	
	public List<ClCrdtExt> getClCrdtExtBySts(String STATUS){
		return crdtDao.getClCrdtExtBySts(STATUS);
	}
	
	public ClUsr getUsrByPolicyNo(String policyNo) {
		ClCrdtExt oc = getClCrdtExtByPolicyNo(policyNo);
		String usrCde = oc.getUSR_CDE();
		ClUsr usr = usrDao.getByUsrCode(usrCde);
		return usr;
	}

	public ClCrdtExt getClCrdtExtByUsrCde(String usrCde) {
		return crdtDao.getClCrdtExtByUsrCde(usrCde);
	}

	public void updateByAppSeq(ClCrdtExt crdtExt) {
		crdtDao.updateByAppSeq(crdtExt);
	}
}
