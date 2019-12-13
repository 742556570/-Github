package com.jeeplus.modules.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.rulesengine.pojo.DecisionReq;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.pojo.ResponeJsonObj;
import com.jeeplus.modules.app.utils.SendMailUtil;

@Service
public class ClReportOutTimeBillsService {

	@Autowired
	private ClCreditExtService creditService;
	
	@Autowired
	private ClUsrApplyService applyService;
	
	
	public List<ClCrdtExt> getCreditTargets (){
		List<ClCrdtExt> targets = new ArrayList<ClCrdtExt>();
		List<ClCrdtExt> list = creditService.getClCrdtExtBySts("00");
		
		for (ClCrdtExt clCrdtExt : list) {
			String crtDt = clCrdtExt.getCRT_DT();
			Date crtDate = DateUtils.parseDate(crtDt);
			Date now = new Date();
			long tspan = now.getTime() - crtDate.getTime();
			if(tspan > 2*60*60*1000) {
				targets.add(clCrdtExt);
			}
		}
		
		return targets;
	}
	
	
	public List<ClUsrApply> getApplyTargets (){
		List<ClUsrApply> targets = new ArrayList<ClUsrApply>();
		List<ClUsrApply> list = applyService.getByStCde("01");
		
		for (ClUsrApply apply : list) {
			String crtDt = apply.getCrtDt();
			Date crtDate = DateUtils.parseDate(crtDt);
			Date now = new Date();
			long tspan = now.getTime() - crtDate.getTime();
			if(tspan > 2*60*60*1000) {
				targets.add(apply);
			}
		}
		
		return targets;
	}
	
	
	public void process() {
		String creditPoliyNos = "";
		List<ClCrdtExt> crdts = getCreditTargets();
		if(crdts != null && crdts.size() >0) {
			for (ClCrdtExt clCrdtExt : crdts) {
				creditPoliyNos = creditPoliyNos + clCrdtExt.getUSR_APPSEQ() + "\r\n";
			}
		}
		
		String applyPoliyNos = "";
		List<ClUsrApply> applies = getApplyTargets();
		if(applies != null && applies.size() >0) {
			for (ClUsrApply apply : applies) {
				applyPoliyNos = applyPoliyNos + apply.getPolicyNo() + "\r\n";
			}
		}
		String isOnline = Global.getConfig("isOnline");
		String prefix = "";
		if("true".equals(isOnline)) {
			prefix = "[Online]";
		}else {
			prefix = "[Test]";
		}
		String title = prefix + "网贷核心:";
		String content = "";
		if(StringUtils.isNotEmpty(creditPoliyNos)) {
			title = title+"授信滞留";
			content = "以下授信单号超过2小时未处理：\r\n" + creditPoliyNos + "\r\n";
		}
		if(StringUtils.isNotEmpty(applyPoliyNos)) {
			if(title.endsWith(":")) {
				title = title+"提现滞留";
				content = "以下提现单号超过2小时未处理：\r\n" + applyPoliyNos + "\r\n";
			}else {
				title = title+"、提现滞留";
				content += "以下提现单号超过2小时未处理：\r\n" + applyPoliyNos + "\r\n";
			}
		}
		if(StringUtils.isNotEmpty(content)) {
			SendMailUtil.send(title, content);
		}
	}
}
