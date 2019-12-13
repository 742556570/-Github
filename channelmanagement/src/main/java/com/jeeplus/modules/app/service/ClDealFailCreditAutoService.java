package com.jeeplus.modules.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.api.rulesengine.pojo.DecisionReq;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;

@Service
public class ClDealFailCreditAutoService {

	@Autowired
	private ClPmPreCreditService preCreditService;
	@Autowired
	private ClCreditExtService creditService;
	@Autowired
	private ClIdCardInfoService   idCardInfoService;
	
	@Autowired
	private ClBnkCrdService  bnkCrdService;
	
	@Autowired
	private ClUsrCntsService usrCntsService;
	
	@Autowired
	private ClApplyService applyService;
	
	
	public List<ClCrdtExt> getTargets (){
		List<ClCrdtExt> targets = new ArrayList<ClCrdtExt>();
		List<ClCrdtExt> list = creditService.getClCrdtExtBySts("00");
		
		for (ClCrdtExt clCrdtExt : list) {
			String crtDt = clCrdtExt.getCRT_DT();
			Date crtDate = DateUtils.parseDate(crtDt);
			Date now = new Date();
			long tspan = now.getTime() - crtDate.getTime();
			if(tspan > 30*60*1000) {
				targets.add(clCrdtExt);
			}
		}
		
		return targets;
	}
	
	public void deal(List<ClCrdtExt> targets) {
		for (ClCrdtExt clCrdtExt : targets) {
			String policyNo = clCrdtExt.getUSR_APPSEQ();
			
			ClUsr clUsr = creditService.getUsrByPolicyNo(policyNo);
			
			
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
			od.setLoan_number(policyNo);
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
				boolean issendSms = true;
				preCreditService.pmRePreCredit(clUsr, policyNo, od,issendSms);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void process() {
		String isDealyCreditService = AppCommonConstants.getValStr("IsNeedDealyCreditService");
		if("true".equals(isDealyCreditService)) {
			String DelayCreditServiceStart = AppCommonConstants.getValStr("DelayCreditServiceStart"); 
			String startTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+DelayCreditServiceStart+"00";
			Date startTime = DateUtils.parseDate4ChkFkTime(startTimeStr);
			if(new Date().getTime() - startTime.getTime() >= 0) {
				return;
			}
			
			
			String DelayCreditServiceEnd = AppCommonConstants.getValStr("DelayCreditServiceEnd"); 
			String endTimeStr = DateUtils.formatDate(new Date(), "yyyyMMdd")+DelayCreditServiceEnd+"00";
			Date endTime = DateUtils.parseDate4ChkFkTime(endTimeStr);
			if(new Date().getTime() - endTime.getTime() <= 0) {
				return;
			}
		}
		List<ClCrdtExt> targets = getTargets();
		deal(targets);
	}
}
