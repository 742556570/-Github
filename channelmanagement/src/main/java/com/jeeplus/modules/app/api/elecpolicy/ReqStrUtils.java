package com.jeeplus.modules.app.api.elecpolicy;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.NumberToCN;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrInfo;

public class ReqStrUtils {
	NumberToCN nc = new NumberToCN();
	
	
	public JSONObject getReqString(ClCapChannel capChannel,ClPrdInfo prd,ClUsr usr,ClIdCardInfo idcard,ClUsrApply apply,ClUsrInfo usrInfo,
			BigDecimal premium) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		String sendName = PropertiesUtil.getString("elec_sendName");//"chanxianxinbao";
		String sendPwd = PropertiesUtil.getString("elec_sendPwd");//"xinbaopro";//xinbao123
		String sendType = PropertiesUtil.getString("elec_sendType");//"1891";
		String billNo = apply.getPolicyNo();
		String imgId = apply.getPolicyNo();
		String productName = prd.getPRD_NAME();
		String secondOrgan = "01000000";
		String billEffectStartTime = "2099-01-01";
		String billEffectEndTime = "2099-01-01";
		String insuredPeriodMonths = apply.getApplyTnr();
		String insuredPeriod = getInsuredPeriodStr(apply.getCrtDt(), insuredPeriodMonths);
		String lowercaseSumInsured = df.format(new BigDecimal(apply.getApplyAmt()).multiply(new BigDecimal("1.2")));
		String capitalSumInsured = nc.number2CNMontrayUnit(new BigDecimal(apply.getApplyAmt()).multiply(new BigDecimal("1.2")));
		
		BigDecimal monthPre = new BigDecimal(apply.getApplyAmt()).multiply(premium);
		
		monthPre = monthPre.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal sumP = monthPre.multiply(new BigDecimal(apply.getApplyTnr()));
		
		String capitalAllBillFee = nc.number2CNMontrayUnit(sumP);
		String lowercaseAllBillFee = sumP.toString();
		String billFeeEachMonth = monthPre.toString();
		String capitalBillFeeEachMonth = nc.number2CNMontrayUnit(monthPre);
		String premiumRateEachMonth = premium.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"%";
		String loanContractNo = apply.getPolicyNo();
		String deductibleFranchise = "0.00%";
		String organName = "阳光财产保险股份有限公司北京分公司";
		String organAddress = "北京市朝阳区东三环南路98号1 幢高和蓝峰大厦8层";
		String releaseTime = apply.getCrtDt();
		String verify = "HBYQ";
		String make = "XTZD";
		String salesName = "新业务BJ";
		String holderName = idcard.getCUST_NAME();
		String holderMobile = usr.getUSR_TEL();
		String holderCardType = "身份证";//01
		String holderCardNo = idcard.getID_NO();
		String holderAddress = usrInfo.getLIVE_PROVINCE()+usrInfo.getLIVE_CITY()+usrInfo.getLIVE_AREA()+usrInfo.getLIVE_ADDR();
		String insuredName = "中国对外经济贸易信托有限公司";
		String insuredAddress = "北京市西城区复兴门内大街28号凯晨世贸中心中座F6层";
		String insuredPostalCode = "100031";
		String premRate = "";
		BigDecimal premRated = monthPre.divide(new BigDecimal(apply.getApplyAmt()).multiply(new BigDecimal("1.2")),6,BigDecimal.ROUND_HALF_UP);
		premRated = premRated.setScale(6, BigDecimal.ROUND_HALF_UP); 
		premRate = premRated.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%";
		
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
				"<DigitalBill>" + 
				"<sendName>"+sendName+"</sendName>" + 
				"<sendPwd>"+sendPwd+"</sendPwd>" + 
				"<sendType>"+sendType+"</sendType>" + 
				"<billNo>"+billNo+"</billNo>" + 
				"<imgId>"+imgId+"</imgId>" + 
				"<productName>"+productName+"</productName>" + 
				"<secondOrgan>"+secondOrgan+"</secondOrgan>" + 
				"<billEffectStartTime>"+billEffectStartTime+"</billEffectStartTime>" + 
				"<billEffectEndTime>"+billEffectEndTime+"</billEffectEndTime>" + 
				"<insuredPeriodMonths>"+insuredPeriodMonths+"</insuredPeriodMonths>" + 
				"<insuredPeriod>"+insuredPeriod+"</insuredPeriod>" + 
				"<lowercaseSumInsured>"+lowercaseSumInsured+"</lowercaseSumInsured>" + 
				"<capitalSumInsured>"+capitalSumInsured+"</capitalSumInsured>" + 
				"<capitalAllBillFee>"+capitalAllBillFee+"</capitalAllBillFee>" + 
				"<lowercaseAllBillFee>"+lowercaseAllBillFee+"</lowercaseAllBillFee>" + 
				"<billFeeEachMonth>"+billFeeEachMonth+"</billFeeEachMonth>" + 
				"<capitalBillFeeEachMonth>"+capitalBillFeeEachMonth+"</capitalBillFeeEachMonth>" + 
				"<premiumRateEachMonth>"+premiumRateEachMonth+"</premiumRateEachMonth>" +
				"<premRate>"+premRate+"</premRate>" +
				"<loanContractNo>"+loanContractNo+"</loanContractNo>" + 
				"<deductibleFranchise>"+deductibleFranchise+"</deductibleFranchise>" + 
				"<organName>"+organName+"</organName>" + 
				"<organAddress>"+organAddress+"</organAddress>" + 
				"<releaseTime>"+releaseTime+"</releaseTime>" + 
				"<verify>"+verify+"</verify>" + 
				"<make>"+make+"</make>" + 
				"<salesName>"+salesName+"</salesName>" + 
				"<holder>" + 
				"<holderName>"+holderName+"</holderName>" + 
				"<holderMobile>"+holderMobile+"</holderMobile>" + 
				"<holderCardType>"+holderCardType+"</holderCardType>" + 
				"<holderCardNo>"+holderCardNo+"</holderCardNo>" + 
				"<holderAddress>"+holderAddress+"</holderAddress>" + 
				"</holder>" + 
				"<insureds>" + 
				"<insured>" + 
				"<insuredName>"+insuredName+"</insuredName>" + 
				"<insuredAddress>"+insuredAddress+"</insuredAddress>" + 
				"<insuredPostalCode>"+insuredPostalCode+"</insuredPostalCode>" + 
				"</insured>" + 
				"</insureds>" + 
				"</DigitalBill>";
		
		
		
		JSONObject root = new JSONObject();
		root.put("appId", "WANGDAI");
		root.put("orderNo", "LN"+DateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		JSONObject data = new JSONObject();
		data.put("imgId", apply.getPolicyNo());
		data.put("elePolicyId", apply.getPolicyNo());
		data.put("xmlMsg", xml);
		data.put("bank", "FOTIC");
		root.put("data", data);
		
		return root;
	}
	
	
	public String   getInsuredPeriodStr(String start,String trn) {
		Date st = new Date();
		try {
			st = DateUtils.parseDate(start);
		} catch (Exception e) {
		}
		Calendar cl = Calendar.getInstance();
		cl.setTime(st);
		cl.add(Calendar.MONTH, Integer.parseInt(trn));
		Date ed = cl.getTime();
		String stStr = DateUtils.formatDate(st, "yyyy年MM月dd日 HH:mm:ss");
		String edStr = DateUtils.formatDate(ed, "yyyy年MM月dd日 HH:mm:ss");
		
		String result = stStr + " 至 " + edStr;
		return result;
		
	}
	
	public static void main(String[] args) {
//		System.out.println(getXMLString());
	}

}
