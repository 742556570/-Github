package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.api.sms.SmsCollectionUtil;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClApiInfoDao;
import com.jeeplus.modules.app.dao.ClBnkCrdDao;
import com.jeeplus.modules.app.dao.ClIdCardInfoDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.dao.ClUsrLoanDetailDao;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;

@Service
public class ClCreditReportUpLdSmsMsgService {
	
	private final static Logger logger = LoggerFactory.getLogger(ClCreditReportUpLdSmsMsgService.class);
	
	@Autowired
	private ClUsrLoanDetailDao clUsrLoanDetailDao;
	
	@Autowired
	private ClIdCardInfoDao idCardInfoDao;
	
	@Autowired
	private ClBnkCrdDao bnkCrdDao;
	
	@Autowired
	private ClUsrDao usrDao;
	
	@Autowired
	private ClApiInfoDao apiInfoDao;
	
	SmsCollectionUtil scu = new SmsCollectionUtil();
	
	public void getAndSend() {
		Map<String, List<ClUsrLoanDetail>> maps = getNDayLaterLoandetail();
		Set<String> keys = maps.keySet();
		
		for (String key : keys) {
			List<ClUsrLoanDetail> details = maps.get(key);
			for (ClUsrLoanDetail detail : details) {
				getInfoAndSendSms(detail,key) ;
			}
		}
	}
	
	
	
	private void getInfoAndSendSms(ClUsrLoanDetail detail,String nDays) {
		
		String usrCde = detail.getUsrCde();
		if(detail.getsCapi() == null) {
			detail.setsCapi(new BigDecimal("0.00"));
		}
		if(detail.getsInte() == null) {
			detail.setsInte(new BigDecimal("0.00"));
		}
		if(detail.getsFine() == null) {
			detail.setsFine(new BigDecimal("0.00"));
		}
		if(detail.getsInsuamt() == null) {
			detail.setsInsuamt(new BigDecimal("0.00"));
		}
		if(detail.getsContamt() == null) {
			detail.setsContamt(new BigDecimal("0.00"));
		}
		ClIdCardInfo idCardinfo = idCardInfoDao.getByUsrCode(usrCde);
		ClUsr usr = usrDao.getByUsrCode(usrCde);
		DecimalFormat df =new DecimalFormat("0.00");	
		String name = idCardinfo.getCUST_NAME();
		String days = DateUtils.formatDate(detail.getsDate(), "MM月dd日");
		String sInsuAmt = df.format(detail.getsInsuamt());
		String sex = idCardinfo.getINDIV_SEX();
		String sendSex = scu.getSendSex(sex);
		
		String[] params = {name,days,sInsuAmt,sendSex};
		String sendDay = DateUtils.formatDate(new Date(), "yyyyMMdd");
		String sendHm = AppCommonConstants.getValStr("CREDITREPORT_UPLD_SEND_HM");
		String sendTime = sendDay+sendHm;
		long s = new Date().getTime();
		boolean sendResult = scu.sendSmsByTime(usr.getUSR_TEL(), AppNormalConstants.CREDITREPORT_UPLD, params,sendTime);
		long e = new Date().getTime();
		String status = sendResult==true?"SUCCESS":"FAIL";
		ClApiInfo apiInfo = new ClApiInfo(usrCde, usr.getUSR_TEL(), AppNormalConstants.SMS_API_NAME, AppNormalConstants.SMS_API_SERVICE_NAME, DateUtils.getNowFullFmt(), (int)(e-s), scu.getContentByConditonAndParams(AppNormalConstants.CREDITREPORT_UPLD, params), status, new Boolean(sendResult).toString());
		apiInfoDao.insert(apiInfo);
		logger.info(usrCde,usr.getUSR_TEL(), "征信提醒", new String[0],sendTime,status);
	}
	
	
	private Map<String, List<ClUsrLoanDetail>> getNDayLaterLoandetail() {
		String nDaysStr = AppCommonConstants.getValStr("CREDITREPORT_UPLD_DAYS");
		String [] nds = nDaysStr.split(",");
		Map<String, List<ClUsrLoanDetail>> maps = new HashMap<String, List<ClUsrLoanDetail>>();
		for (int i = 0; i < nds.length; i++) {
			int nDays = Integer.parseInt(nds[i]);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -nDays);
			String targetDt = DateUtils.formatDate(c.getTime(), "yyyy-MM-dd");
			
			String startDt = targetDt + " 00:00:00";
			String endDt = targetDt + " 23:59:59";
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("startDt", startDt);
			params.put("endDt", endDt);
			
			List<ClUsrLoanDetail> list = clUsrLoanDetailDao.getByStartAndEndDate(params);
			maps.put(nDays+"", list);
		}
		return maps;
	}

}
