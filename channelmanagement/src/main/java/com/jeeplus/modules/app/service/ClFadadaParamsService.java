package com.jeeplus.modules.app.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.DateUtils;

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
import com.jeeplus.modules.app.dao.ClUsrLoanDetailDao;
import com.jeeplus.modules.app.entity.ClBnkCrd;
import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClPremiumDetail;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.entity.ClUsrLoanDetail;
import com.jeeplus.modules.app.utils.Number2Chinese;

@Service
public class ClFadadaParamsService {
	
	@Autowired
	private ClUsrDao usrDao;

	@Autowired
	private ClUsrLmtamtService usrLmtAmtService;

	@Autowired
	private ClCrdtExtDao crdtDao;

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
	private ClDealFailedDao failedDao;
	
	@Autowired
	private ClApiInfoDao apiInfoDao;
	
	@Autowired
	private ClUsrLoanDetailDao detailDao;
	
	DecimalFormat df = new DecimalFormat("0.00");
	
	Number2Chinese n2c = new Number2Chinese();


	public JSONObject getParams(String usrCde,String modelCde,String policyNo,String date) {
		if("YGSDPH80000001-1".equalsIgnoreCase(modelCde)) {
			return agreement4_1_1();
		} else if("YGSDPH80000002-1".equalsIgnoreCase(modelCde)) {
			return agreement4_2_1(usrCde,date);
		} else if("YGSDPH80000003-1".equalsIgnoreCase(modelCde)) {
			return agreement4_2_2(usrCde,date);
		} else if("YGSDPH80000004-1".equalsIgnoreCase(modelCde)) {
			return agreement4_3_1();
		} else if("YGSDPH80000005-1".equalsIgnoreCase(modelCde)) {
			return agreement4_3_2(usrCde,policyNo,date);
		} else if("YGSDPH80000006-1".equalsIgnoreCase(modelCde)) {
			return agreement4_3_3(usrCde,date);
		} else if("YGSDPH80000007-1".equalsIgnoreCase(modelCde)) {
			return agreement4_3_4(usrCde,date);
		} else if("YGSDPH80000008-1".equalsIgnoreCase(modelCde)) {
			return agreement4_4_1(usrCde,policyNo);
		} else if("YGSDPH80000009-1".equalsIgnoreCase(modelCde)) {
			return agreement4_4_2(usrCde,policyNo);
		} else if("YGSDPH80000014-1".equalsIgnoreCase(modelCde)) {
			return agreement4_4_3(usrCde,policyNo);
		} else if("YGSDPH80000011-1".equalsIgnoreCase(modelCde)) {
			return agreement4_4_4(usrCde,policyNo);
		} else if("YGSDPH80000010-1".equalsIgnoreCase(modelCde)) {
			return agreement4_5_1(usrCde,policyNo);
		} else if("YGSDPH80000012-1".equalsIgnoreCase(modelCde)) {
			return agreement4_5_2(usrCde,policyNo);
		} else if("YGSDPH80000013-1".equalsIgnoreCase(modelCde)) {
			return agreement4_5_3(usrCde,policyNo);
		} else {
			return null;
		}

	
	}
	
	private JSONObject agreement4_1_1() {
		JSONObject json = new JSONObject();
		json.put("protocolCode", getSeq());
		return json;
	}
	
	private JSONObject agreement4_2_1(String usrCde,String date) {
//		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
//		String custName = idCard.getCUST_NAME();
//		String idNo = idCard.getID_NO();
		String nowDate  = date; /*DateUtils.formatDate(new Date(), "yyyy-MM-dd");*/
		JSONObject json = new JSONObject();
		json.put("custName", "");
		json.put("idNo", "");
		json.put("effectDate", nowDate);
		json.put("signDate", nowDate);
		return json;
	}

	private JSONObject agreement4_2_2(String usrCde,String date) {
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		String nowDate  = date;/*DateUtils.formatDate(new Date(), "yyyy-MM-dd");*/
		JSONObject json = new JSONObject();
		json.put("custName", custName);
		json.put("idNo", idNo);
		json.put("signDate", nowDate);
		return json;
	}
	
	private JSONObject agreement4_3_1() {
		JSONObject json = new JSONObject();
		return json;
	}
	
	private JSONObject agreement4_3_2(String usrCde, String policyNo,String date) {
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		ClUsr usr = usrDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		String sex = idCard.getINDIV_SEX();
		String effectDate = idCard.getVALID_DATE1() +"-"+ idCard.getVALID_DATE2();
		String phone = usr.getUSR_TEL();
		String nowDate  = date;/*DateUtils.formatDate(new Date(), "yyyy-MM-dd");*/
		JSONObject json = new JSONObject();
		json.put("applyCode", policyNo);
		json.put("custName", custName);
		json.put("sex", sex);
		json.put("nation", "中华人民共和国");
		json.put("idNo", idNo);
		json.put("effectDate", effectDate);
		json.put("phone", phone);
		json.put("signDate", nowDate);
		return json;
	}
	
	/*private JSONObject agreement4_3_3(String usrCde) {
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String idNo = idCard.getID_NO();
		String nowDate  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		JSONObject json = new JSONObject();
		json.put("idNo", idNo);
		json.put("signDate", nowDate);
		return json;
	}*/
	
	
	private JSONObject agreement4_3_3(String usrCde,String date) {
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String idNo = idCard.getID_NO();
		JSONObject json = new JSONObject();
		json.put("idNo", idNo);
		json.put("signDate", date);/*DateUtils.formatDate(new Date(), "yyyy-MM-dd");*/
		return json;
	}
	
	
	/*private JSONObject agreement4_3_4(String usrCde) {
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		String nowDate  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		JSONObject json = new JSONObject();
		json.put("custName", custName);
		json.put("idNo", idNo);
		json.put("signDate", nowDate);
		return json;
	}*/
	
	private JSONObject agreement4_3_4(String usrCde,String date) {
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		String nowDate  = date;/*DateUtils.formatDate(new Date(), "yyyy-MM-dd");*/
		JSONObject json = new JSONObject();
		json.put("custName", custName);
		json.put("idNo", idNo);
		json.put("signDate", nowDate);
		return json;
	}
	
	
	private JSONObject agreement4_4_1(String usrCde,String policyNo) {
		String protocolCode = policyNo;
		String email = "";
		String gdTel = "";
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		
		ClUsr usr = usrDao.getByUsrCode(usrCde);
		String  telNo = usr.getUSR_TEL();
		
		ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usrCde);
		String addr = usrInfo.getLIVE_PROVINCE()+usrInfo.getLIVE_CITY()+usrInfo.getLIVE_AREA()+usrInfo.getLIVE_ADDR();
		
		
		
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		Date now = DateUtils.parseDate(apply.getCrtDt());
		String signYear = DateUtils.formatDate(now, "yyyy");
		String signMonth = DateUtils.formatDate(now, "MM"); 
		String signDay = DateUtils.formatDate(now, "dd"); 
		String signDate = DateUtils.formatDate(now, "yyyy-MM-dd");
		String signTerm = apply.getApplyTnr();
		BigDecimal amt = new BigDecimal(apply.getApplyAmt());
		String signAmt = df.format(amt);
		String signAmtCny = n2c.number2CNMontrayUnit(new BigDecimal(signAmt));
		
		ClBnkCrd bnkCrd = bnkCrdDao.getOneByBankNoWithOutSts(apply.getBankCardNo());
		String accountName = bnkCrd.getAPPL_AC_NAM();
		String bankName = bnkCrd.getAPPL_BANK_NAME();
		String bankCard = bnkCrd.getAPPL_CARD_NO();
		
		
		String prdCde = apply.getPrdCde();
//		ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
		BigDecimal rate = new BigDecimal(apply.getIntRate());
		String signRate = df.format(rate.multiply(new BigDecimal("100")));
		
		String nowDate  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		JSONObject json = new JSONObject();
		json.put("signYear", signYear);
		json.put("signMonth", signMonth);
		json.put("signDay", signDay);
		json.put("protocolCode", protocolCode);
		json.put("email", email);
		json.put("custName", custName);
		json.put("idNo", idNo);
		json.put("telNo", telNo);
		json.put("gdTel", gdTel);
		json.put("addr", addr);
		json.put("accountName", accountName);
		json.put("bankName", bankName);
		json.put("bankCard", bankCard);
		json.put("signTerm", signTerm);
		json.put("signYear", signYear);
		json.put("signAmtCny", signAmtCny);
		json.put("signAmt", signAmt);
		json.put("signRate", signRate);
		return json;
	}
	
	private JSONObject agreement4_4_2(String usrCde,String policyNo) {
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		Date now = DateUtils.parseDate(apply.getCrtDt());
		String signYear = DateUtils.formatDate(now, "yyyy");
		String signMonth = DateUtils.formatDate(now, "MM"); 
		String signDay = DateUtils.formatDate(now, "dd"); 
		String signDate = DateUtils.formatDate(now, "yyyy-MM-dd");
		JSONObject json = new JSONObject();
		json.put("signYear", signYear);
		json.put("signMonth", signMonth);
		json.put("signDay", signDay);
		json.put("custName", custName);
		json.put("idNo", idNo);
		return json;
	}
	
	private JSONObject agreement4_4_3(String usrCde,String policyNo) {
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		
		String protocolCode = policyNo;
		
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		Date now = DateUtils.parseDate(apply.getCrtDt());
		String signYear = DateUtils.formatDate(now, "yyyy");
		String signMonth = DateUtils.formatDate(now, "MM"); 
		String signDay = DateUtils.formatDate(now, "dd"); 
		String signDate = DateUtils.formatDate(now, "yyyy-MM-dd");
		String signTerm = apply.getApplyTnr();
		BigDecimal amt = new BigDecimal(apply.getApplyAmt());
		String signAmt = df.format(amt);
		String signAmtCny = n2c.number2CNMontrayUnit(new BigDecimal(signAmt));
		
		ClBnkCrd bnkCrd = bnkCrdDao.getOneByBankNoWithOutSts(apply.getBankCardNo());
		String accountName = bnkCrd.getAPPL_AC_NAM();
		String bankName = bnkCrd.getAPPL_BANK_NAME();
		String bankCard = bnkCrd.getAPPL_CARD_NO();
		
		
		JSONObject json = new JSONObject();
		json.put("signYear", signYear);
		json.put("signMonth", signMonth);
		json.put("signDay", signDay);
		json.put("custName", custName);
		json.put("idNo", idNo);
		json.put("protocolCode", protocolCode);
		json.put("accountName", accountName);
		json.put("bankCard", bankCard);
		json.put("bankName", bankName);
		return json;
	}
	
	
	private JSONObject agreement4_4_4(String usrCde,String policyNo) {
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		
		String term1 = "";
		String term2 = "";
		String term3 = "";
		String term4 = "";
		String term5 = "";
		String term6 = "";
		String term7 = "";
		String term8 = "";
		String term9 = "";
		String term10 = "";
		String term11 = "";
		String term12 = "";
		
		String repaydate1= "" ;
		String repaydate2= "" ;
		String repaydate3= "" ;
		String repaydate4= "" ;
		String repaydate5= "" ;
		String repaydate6= "" ;
		String repaydate7= "" ;
		String repaydate8= "" ;
		String repaydate9= "" ;
		String repaydate10= "" ;
		String repaydate11= "" ;
		String repaydate12= "" ;
		
		String repayamt1= "" ;
		String repayamt2= "" ;
		String repayamt3= "" ;
		String repayamt4= "" ;
		String repayamt5= "" ;
		String repayamt6= "" ;
		String repayamt7= "" ;
		String repayamt8= "" ;
		String repayamt9= "" ;
		String repayamt10= "" ;
		String repayamt11= "" ;
		String repayamt12= "" ;
		
		
		List<ClUsrLoanDetail> details = detailDao.getByPolicy(policyNo);
		
		for (int i = 0; i < details.size(); i++) {
			ClUsrLoanDetail tmp = details.get(i);
			if(tmp.getsCapi() == null) {
				tmp.setsCapi(new BigDecimal("0.00"));
			}
			if(tmp.getsInte() == null) {
				tmp.setsInte(new BigDecimal("0.00"));
			}
			if(tmp.getsFine() == null) {
				tmp.setsFine(new BigDecimal("0.00"));
			}
			if(tmp.getsInsuamt() == null) {
				tmp.setsInsuamt(new BigDecimal("0.00"));
			}
			if(tmp.getsContamt() == null) {
				tmp.setsContamt(new BigDecimal("0.00"));
			}
			if(i==0) {
				term1 = "1";
				repaydate1 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt1 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==1) {
				term2 = "2";
				repaydate2 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt2 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==2) {
				term3 = "3";
				repaydate3 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt3 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==3) {
				term4 = "4";
				repaydate4 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt4 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==4) {
				term5 = "5";
				repaydate5 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt5 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==5) {
				term6 = "6";
				repaydate6 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt6 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==6) {
				term7 = "7";
				repaydate7 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt7 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==7) {
				term8 = "8";
				repaydate8 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt8 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==8) {
				term9 = "9";
				repaydate9 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt9 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==9) {
				term10 = "10";
				repaydate10 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt10 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==10) {
				term11 = "11";
				repaydate11 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt11 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
			if(i==11) {
				term12 = "12";
				repaydate12 = DateUtils.formatDate(tmp.getsDate(), "yyyy-MM-dd");
				repayamt12 = df.format(tmp.getsCapi().add(tmp.getsInte()).add(tmp.getsFine()).add(tmp.getsInsuamt()).add(tmp.getsContamt()));
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("custName", custName);
		json.put("term1",term1);
		json.put("term2",term2);
		json.put("term3",term3);
		json.put("term4",term4);
		json.put("term5",term5);
		json.put("term6",term6);
		json.put("term7",term7);
		json.put("term8",term8);
		json.put("term9",term9);
		json.put("term10",term10);
		json.put("term11",term11);
		json.put("term12",term12);
		
		json.put("repaydate1",repaydate1);
		json.put("repaydate2",repaydate2);
		json.put("repaydate3",repaydate3);
		json.put("repaydate4",repaydate4);
		json.put("repaydate5",repaydate5);
		json.put("repaydate6",repaydate6);
		json.put("repaydate7",repaydate7);
		json.put("repaydate8",repaydate8);
		json.put("repaydate9",repaydate9);
		json.put("repaydate10",repaydate10);
		json.put("repaydate11",repaydate11);
		json.put("repaydate12",repaydate12);
		
		json.put("repayamt1",repayamt1);
		json.put("repayamt2",repayamt2);
		json.put("repayamt3",repayamt3);
		json.put("repayamt4",repayamt4);
		json.put("repayamt5",repayamt5);
		json.put("repayamt6",repayamt6);
		json.put("repayamt7",repayamt7);
		json.put("repayamt8",repayamt8);
		json.put("repayamt9",repayamt9);
		json.put("repayamt10",repayamt10);
		json.put("repayamt11",repayamt11);
		json.put("repayamt12",repayamt12);
		
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		Date now = DateUtils.parseDate(apply.getCrtDt());
		String signDate = DateUtils.formatDate(now, "yyyy-MM-dd");
		json.put("signDate",signDate);
		
		return json;
	}
	
	
	private JSONObject agreement4_5_1(String usrCde,String policyNo) {
		String protocolCode = policyNo;
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		
		ClUsr usr = usrDao.getByUsrCode(usrCde);
		String  telNo = usr.getUSR_TEL();
		
		ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usrCde);
		String addr = usrInfo.getLIVE_PROVINCE()+usrInfo.getLIVE_CITY()+usrInfo.getLIVE_AREA()+usrInfo.getLIVE_ADDR();
		
		String bbxrName = "中国对外经济贸易信托有限公司";
		String bbxrAddr = "北京市西城区复兴门内大街 28 号凯晨世贸中心中座";
		String postCode = "100031";
		
		
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		Date now = DateUtils.parseDate(apply.getCrtDt());
		String signYear = DateUtils.formatDate(now, "yyyy");
		String signMonth = DateUtils.formatDate(now, "MM"); 
		String signDay = DateUtils.formatDate(now, "dd"); 
		String signDate = DateUtils.formatDate(now, "yyyy-MM-dd");
		String signTerm = apply.getApplyTnr();
		BigDecimal amt = new BigDecimal(apply.getApplyAmt());
		String signAmt = df.format(amt);
		BigDecimal amount = new BigDecimal(apply.getApplyAmt()).multiply(new BigDecimal("1.2"));
//		String amountStr = df.format(amount);
		String amountCNY = n2c.number2CNMontrayUnit(amount);
		
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("PRD_CDE", apply.getPrdCde());
//		params.put("PAY_TERM", apply.getApplyTnr());
//		ClPremiumDetail clPreDetail = premiumDetailDao.getByPrdAndTrem(params);
		

		BigDecimal monthPre = amt.multiply(new BigDecimal(apply.getPremiumRate()));
		
		monthPre = monthPre.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal sumP = monthPre.multiply(new BigDecimal(signTerm));
		
		String sumpremCNY = n2c.number2CNMontrayUnit(sumP) ;
		String premCNY = n2c.number2CNMontrayUnit(monthPre) ;
		String preRate = new BigDecimal(apply.getPremiumRate()).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"%";
		String mpRate = "0.00%";
		
		
		
		String prdCde = apply.getPrdCde();
//		ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
		BigDecimal rate = new BigDecimal(apply.getIntRate());
		String signRate = df.format(rate.multiply(new BigDecimal("100")));
		
		String nowDate  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		
		
		BigDecimal tmp = monthPre.divide(amount,6,BigDecimal.ROUND_HALF_UP);
		tmp = tmp.setScale(6, BigDecimal.ROUND_HALF_UP);
		tmp = tmp.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
		String preBJRate= tmp+" %";
		
		
		JSONObject json = new JSONObject();
		json.put("policyNo", policyNo);
		json.put("custName", custName);
		json.put("telNo", telNo);
		json.put("idNo", idNo);
		json.put("tbrAddr", addr);
		json.put("bbxrName", bbxrName);
		json.put("bbxrAddr", bbxrAddr);
		json.put("postCode", postCode);
		json.put("amountCNY", amountCNY);
		json.put("amount", amount.toString());
//		json.put("sumpremCNY", sumpremCNY);
		json.put("premCNY", premCNY);
		json.put("prem", monthPre.toString());
		json.put("preRate", preBJRate);
		json.put("preBJRate", preRate);
//		json.put("protocolCode", protocolCode);
		json.put("mpRate", mpRate);
		json.put("signYear", signYear);
		json.put("signMonth", signMonth);
		json.put("signDay", signDay);
		json.put("signDate", signDate);
		
		
		json.put("orgName", AppCommonConstants.getValStr("signOrgName"));
		json.put("orgAdd", AppCommonConstants.getValStr("signOrgAdd"));
		return json;
	}
	
	private JSONObject agreement4_5_2(String usrCde,String policyNo) {
		String protocolCode = policyNo;
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		
		ClUsr usr = usrDao.getByUsrCode(usrCde);
		String  telNo = usr.getUSR_TEL();
		
		ClUsrInfo usrInfo = usrInfoDao.getByUsrCode(usrCde);
		String addr = usrInfo.getLIVE_PROVINCE()+usrInfo.getLIVE_CITY()+usrInfo.getLIVE_AREA()+usrInfo.getLIVE_ADDR();
		
		String bbxrName = "中国对外经济贸易信托有限公司";
		String bbxrAddr = "北京市西城区复兴门内大街 28 号凯晨世贸中心中座";
		String postCode = "100031";
		
		
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		Date now = DateUtils.parseDate(apply.getCrtDt());
		String signYear = DateUtils.formatDate(now, "yyyy");
		String signMonth = DateUtils.formatDate(now, "MM"); 
		String signDay = DateUtils.formatDate(now, "dd"); 
		String signDate = DateUtils.formatDate(now, "yyyy-MM-dd");
		String signTerm = apply.getApplyTnr();
		BigDecimal amt = new BigDecimal(apply.getApplyAmt());
		BigDecimal amount = new BigDecimal(apply.getApplyAmt()).multiply(new BigDecimal("1.2"));
		String signAmt = df.format(amount);
		String amountCNY = n2c.number2CNMontrayUnit(new BigDecimal(signAmt));
		
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("PRD_CDE", apply.getPrdCde());
//		params.put("PAY_TERM", apply.getApplyTnr());
//		ClPremiumDetail clPreDetail = premiumDetailDao.getByPrdAndTrem(params);
		

		BigDecimal monthPre = amt.multiply(new BigDecimal(apply.getPremiumRate()));
		
		monthPre = monthPre.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal preBjRate = monthPre.divide(amount,6,BigDecimal.ROUND_HALF_UP);
		preBjRate = preBjRate.multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP);
		
		String preBJRate = preBjRate.toString()+"%";
		
		BigDecimal sumP = monthPre.multiply(new BigDecimal(signTerm));
		
		String sumpremCNY = n2c.number2CNMontrayUnit(sumP) ;
		String premCNY = n2c.number2CNMontrayUnit(monthPre) ;
		String preRate = new BigDecimal(apply.getPremiumRate()).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"%";
		String mpRate = "0.00";
		
		
		
		String prdCde = apply.getPrdCde();
//		ClPrdInfo prdInfo = prdDao.getByPrdCde(prdCde);
		BigDecimal rate = new BigDecimal(apply.getIntRate());
		String signRate = df.format(rate.multiply(new BigDecimal("100")))+"%";
		
		String nowDate  = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		JSONObject json = new JSONObject();
//		json.put("policyNo", policyNo);
		json.put("custName", custName);
		json.put("telNo", telNo);
		json.put("idNo", idNo);
		json.put("tbrAddr", addr);
		json.put("bbxrName", bbxrName);
		json.put("bbxrAddr", bbxrAddr);
		json.put("postCode", postCode);
		json.put("amountCNY", amountCNY);
		json.put("amount", amount.toString());
//		json.put("sumpremCNY", sumpremCNY);
		
		json.put("premCNY", premCNY);
		json.put("prem", monthPre.toString());
		json.put("preRate", preBJRate);
		json.put("preBJRate", preRate);
//		json.put("protocolCode", protocolCode);
//		json.put("mpRate", mpRate);
		json.put("signYear", signYear);
		json.put("signMonth", signMonth);
		json.put("signDay", signDay);
		json.put("signDate", signDate);
		json.put("orgName", AppCommonConstants.getValStr("signOrgName"));
		json.put("orgAdd", AppCommonConstants.getValStr("signOrgAdd"));
		return json;
	}
	
	
	private JSONObject agreement4_5_3(String usrCde,String policyNo) {
		
		String protocolCode = policyNo;
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		
		ClBnkCrd bnkCrd = bnkCrdDao.getOneByBankNoWithOutSts(apply.getBankCardNo());
		
		ClCapChannel capChannel = capChannelDao.getByCapSeq("1"); 
		
		String bankName = bnkCrd.getAPPL_BANK_NAME();
		String bankCard = bnkCrd.getAPPL_CARD_NO();
		
		String capChannelName= capChannel.getCAP_INSTU_NAME();
		
		Date now = DateUtils.parseDate(apply.getCrtDt());
		String signYear = DateUtils.formatDate(now, "yyyy");
		String signMonth = DateUtils.formatDate(now, "MM"); 
		String signDay = DateUtils.formatDate(now, "dd"); 
		String signDate = DateUtils.formatDate(now, "yyyy-MM-dd");
		
		
		JSONObject json = new JSONObject();
		json.put("year1", signYear);
		json.put("month1", signMonth);
		json.put("day1", signDay);
		json.put("capChannelName", capChannelName);
		json.put("policyNo", policyNo);
		json.put("bankName", bankName);
		json.put("bankCard", bankCard);
		json.put("year2", signYear);
		json.put("month2", signMonth);
		json.put("day2", signDay);
		return json;
	}
	
	private JSONObject agreement4_5_3_old(String usrCde,String policyNo) {
		String protocolCode = policyNo;
		ClIdCardInfo idCard = idCardDao.getByUsrCode(usrCde);
		String custName = idCard.getCUST_NAME();
		String idNo = idCard.getID_NO();
		
		ClUsrApply apply = applyDao.getByPolicyNo(policyNo);
		Date now = DateUtils.parseDate(apply.getCrtDt());
		String signYear = DateUtils.formatDate(now, "yyyy");
		String signMonth = DateUtils.formatDate(now, "MM"); 
		String signDay = DateUtils.formatDate(now, "dd"); 
		String signDate = DateUtils.formatDate(now, "yyyy-MM-dd");
		ClBnkCrd bnkCrd = bnkCrdDao.getOneByBankNoWithOutSts(apply.getBankCardNo());
		
		String bankName = bnkCrd.getAPPL_BANK_NAME();
		String bankBranch = "";
		String bankCard = bnkCrd.getAPPL_CARD_NO();
		
		JSONObject json = new JSONObject();
		json.put("year1", signYear);
		json.put("month1", signMonth);
		json.put("day1", signDay);
		json.put("protocolCode", protocolCode);
		json.put("policyNo", policyNo);
		json.put("bankName", bankName);
		json.put("bankBranch", bankBranch);
		json.put("bankCard", bankCard);
		json.put("year2", signYear);
		json.put("month2", signMonth);
		json.put("day2", signDay);
		return json;
	}
	
	
	
	
	private String getSeq() {
		int seq = appIdDao.getContractSeq();
		String contSeq = "WD"+seq+DateUtils.getyMdHms()+"C";
		return contSeq;
	}

	
	
	public static String newLineStr(int len ,String str) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < str.length(); i=i+len) {
			sb.append(str.substring(i, i+len)).append("\r\n");
		}
		
		return sb.toString();
	}
	
}