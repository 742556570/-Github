package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClUsrApplyDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;

@Service
public class ReGenContractService {
	
	private final static Logger logger = LoggerFactory.getLogger(ReGenContractService.class);
	
	@Autowired
	private ClUsrDao usrDao;
	
	@Autowired
	private ClIdCardInfoDao idCardDao;
	
	@Autowired
	private ClUsrApplyDao applyDao;
	
	@Autowired
	private ClFadadaParamsService fpService;
	
	@Autowired
	private ClUsrContractService ucService;
	
	public void dealApplyAll() {
		String[] steps = {"4-1","4-2","4-3","4-4","5-1","5-2","5-3"};
		
		for (String step : steps) {
			dealApply(step);
		}
	}
	
	public void dealApply(String step) {
		/**
		 * step
		 * 4-1  y
		 * 4-2  n
		 * 4-3  y
		 * 4-4  y
		 * 5-1  y
		 * 5-2  y
		 * 5-3  y
		 * 
		 */
		
		String isApplySuccess = "true";
		
		if("4-2".equals(step)) {
			isApplySuccess = "false";
		}
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("STEP", step);
		params.put("isApplySuccess",isApplySuccess);
		List<ClUsrApply> list = applyDao.getNeedReGenContractByStep(params);
		
		for (ClUsrApply apply : list) {
			boolean res = genConctract(apply, step);
			if(res) {
				logger.info(apply.getPolicyNo() + " gen contract step" + step +" success");
			}else {
				logger.info(apply.getPolicyNo() + " gen contract step" + step +" fail");
			}
		}
	}
	
	
	public boolean genConctract(ClUsrApply apply, String step) {
		try {
			String usrCde = apply.getUsrCde();
			String policyNo = apply.getPolicyNo();
			String date = DateUtils.formatDate(DateUtils.parseDate(apply.getCrtDt()), "yyyy-MM-dd");
			ClIdCardInfo idcard = idCardDao.getByUsrCode(usrCde);
			ClUsr usr = usrDao.getByUsrCode(usrCde);
			
			net.sf.json.JSONObject paramsHt = null;
			
			if("4-1".equals(step)) {
				paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000008-1", policyNo,date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000008-1", "贷款合同（信用类）", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-1", "","CI11");
				return true;
			} else if("4-2".equals(step)) {
				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000009-1", policyNo,date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt,
						"YGSDPH80000009-1", "贷款协议（信用类）信托-阳光-附件一个人信息授权书", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-2", "",
						"CI08");
				return true;
			} else if("4-3".equals(step)) {
				paramsHt = net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000014-1", policyNo,date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000014-1", "贷款协议（信用类）信托-阳光-附件二还款代扣授权", "1", "签章处", policyNo, usr.getUSR_CDE(), "4-3", "","CI13");
				return true;
			} else if("4-4".equals(step)) {
				boolean isExist = ucService.isExistByPolicyNo(policyNo, "YGSDPH80000011-1");
				if(!isExist) {
					paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000011-1", policyNo,date));
					ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), idcard.getUSR_TEL(), paramsHt, "YGSDPH80000011-1", "还款事项提醒函", "1", "签章处", policyNo, usrCde, "4-4", "","CI06");
				}
				return true;
			} else if("5-1".equals(step)) {
				paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000010-1", policyNo,date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000010-1", "保险单", "2", "签单机构地址", policyNo, usr.getUSR_CDE(), "5-1", "投保人签名","CI12");
				return true;
			} else if("5-2".equals(step)) {
				paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000012-1", policyNo,date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000012-1", "保险单信息确认页", "1", "投保人签名", policyNo, usr.getUSR_CDE(), "5-2", "","CI12");
				return true;
			} else if("5-3".equals(step)) {
				paramsHt =  net.sf.json.JSONObject.fromObject(fpService.getParams(usrCde, "YGSDPH80000013-1", policyNo,date));
				ucService.fadadaContract(idcard.getCUST_NAME(), idcard.getID_NO(), usr.getUSR_TEL(), paramsHt, "YGSDPH80000013-1", "委托扣款授权书-保费", "1", "签章处", policyNo, usr.getUSR_CDE(), "5-3", "","CI13");
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info(apply.getPolicyNo() + " gen contract step" + step +" fail");
			return false;
		}
	}

}
