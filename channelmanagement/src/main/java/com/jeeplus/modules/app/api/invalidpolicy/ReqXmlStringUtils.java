package com.jeeplus.modules.app.api.invalidpolicy;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.utils.AgeUtils;

public class ReqXmlStringUtils {

	public String getReqXmlStr(ClCapChannel capChannel,ClPrdInfo prdInfo,ClUsrApply usrApply,ClUsr usr, ClUsrInfo usrInfo,
			ClIdCardInfo idCardInfo, JSONObject usrCnts,BigDecimal premium) {
		
		
		String Apply_Limit = "";
		String Loan_Period = "";
		String Loan_Usage = "";
		String PolicyNo = "";
		if (usrApply != null) {
			Apply_Limit = StringUtils.isEmpty(usrApply.getApplyAmt()) ? "0" : usrApply.getApplyAmt();
			Loan_Period = StringUtils.isEmpty(usrApply.getApplyTnr()) ? "0" : usrApply.getApplyTnr();
			Loan_Usage =  StringUtils.isEmpty(usrApply.getApplyFor()) ? "" : usrApply.getApplyFor();
			PolicyNo =  StringUtils.isEmpty(usrApply.getPolicyNo()) ? "" : usrApply.getPolicyNo();
		}
		
		String Loan_Rate = "";
		String Loan_Type = "";
		if(prdInfo != null) {
			Loan_Rate = premium.multiply(new BigDecimal("100")).toString();
			Loan_Type = prdInfo.getPRD_CDE();
		}
		
		
		String Premium = "";
		String Branch = "";
		String Channel_Cde = "";
		String Sales_Person = "";
		String Report_User = "";
		String BUSINESS_NATURE = "";
		String SECURE_CDE = "";
		String Handler_Cde = "";
		String CAP_INSTU_NAME = "";
		String CAP_INSTU_CDE = "";
		String KIND_CDE = "";
		String KIND_NAME = "";
		String OPERATE_SITE = "";
		String MAKE_COM = "";
		if(capChannel != null) {
			Premium = new BigDecimal(Apply_Limit).multiply(new BigDecimal(capChannel.getSUM_AMOUNT())).subtract(new BigDecimal(Apply_Limit)).toString();
			Branch = capChannel.getCOM_CED();
			Channel_Cde = capChannel.getCHANNEL_CDE();
			Sales_Person = capChannel.getHANDLER1_CDE();
			Report_User = capChannel.getOPERATOR_CDE();
			BUSINESS_NATURE = capChannel.getBUSINESS_NATURE();
			SECURE_CDE = capChannel.getSECURE_CDE();
			Handler_Cde = capChannel.getHANDLER1_CDE();
			CAP_INSTU_NAME = capChannel.getCAP_INSTU_NAME();
			CAP_INSTU_CDE = capChannel.getCAP_INSUR_CDE();
			OPERATE_SITE = capChannel.getOPERATE_SITE();
			KIND_CDE = capChannel.getKIND_CDE();
			KIND_NAME = capChannel.getKIND_NAME();
			MAKE_COM = capChannel.getMAKE_COM();
		}
		
		String Name = idCardInfo.getCUST_NAME();
		String Gender = idCardInfo.getINDIV_SEX();
		String Nation = idCardInfo.getINDIV_NATION();
		String Age = getAge(idCardInfo.getBIRTHDAY_DATE())+"";
		String Birthday = getBirthFormat(idCardInfo.getBIRTHDAY_DATE());
		String Id_Type = "身份证";
		String Id_Number = idCardInfo.getID_NO();
		String Mobile = usr.getUSR_TEL();
		
		String Home_Address_State = usrInfo.getLIVE_PROVINCE();
		String Home_Address_City = usrInfo.getLIVE_CITY();
		String Home_Address_County = usrInfo.getLIVE_AREA();
		String Home_Address_Street_Address = usrInfo.getLIVE_ADDR();
		String Company_Name = usrInfo.getINDIV_EMP_NAME();
		String Company_Address_State = usrInfo.getINDIV_EMP_PROVINCE();
		String Company_Address_City = usrInfo.getINDIV_EMP_CITY();
		String Company_Address_County = usrInfo.getINDIV_EMP_AREA();
		String Company_Address_Street_Address = usrInfo.getINDIV_EMP_ADDR();
		String Company_Phone = usrInfo.getINDIV_EMP_TEL();
		String DUTY = usrInfo.getDUTY();
		
		String Cnts_Name1 = usrCnts.getString("relationName");
		String Cnts_Relation1 = usrCnts.getString("relation");
		String Cnts_Mobile1 = usrCnts.getString("relationPhone").replaceAll("\\s*", "");
		String Cnts_Name2 = usrCnts.getString("otherName");
		String Cnts_Relation2 = usrCnts.getString("otherRelation");
		String Cnts_Mobile2 = usrCnts.getString("otherPhone").replaceAll("\\s*", "");
		
		String inputDate = usrApply.getCrtDt().substring(0, usrApply.getCrtDt().indexOf(" "));
		
		Date  now = new Date();
		String loanStartDate = inputDate;//getLoanStartDate(now);
		String loanEndDate = getLoanEndDate(inputDate,Loan_Period);
		
		BigDecimal sumAmount = new BigDecimal(Apply_Limit).multiply(new BigDecimal(capChannel.getSUM_AMOUNT()));
		
		BigDecimal monthPre = new BigDecimal(Apply_Limit).multiply(premium);
		
		monthPre = monthPre.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal sumP = monthPre.multiply(new BigDecimal(Loan_Period));
		
		String xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"GBK\"?>").append(
				"<TransPolicyRequest>").append(
						"<modeFlag>5</modeFlag>").append(
						"<operateType>INPUT</operateType>").append(
						"<policyCount>1</policyCount>").append(
						"<sendName>xdxbpt</sendName>").append(
						"<sendPwd>xdxbpt</sendPwd>").append(
						"<sendSeq>"+UUID.randomUUID().toString().replaceAll("-", "")+"</sendSeq>").append(
						"<sendTime>"+DateUtils.formatDate(new Date(), "yyyyMMddHHmmss")+"</sendTime>").append(
						"<sysFlag>XDXBPT</sysFlag>").append(
						"<policyGroup>").append(
						"<policy>").append(
						"<businessNature>"+BUSINESS_NATURE+"</businessNature>").append(
						"<channelType>"+Channel_Cde+"</channelType>").append(
						"<shortRate>100</shortRate>").append(
						"<insuredIdentifyNumber>"+CAP_INSTU_CDE+"</insuredIdentifyNumber>").append(
						"<insuredPhoneNumber></insuredPhoneNumber>").append(
						"<endDate>2099-01-01</endDate>").append(
						"<appliName>"+Name+"</appliName>").append(
						"<amount>"+sumAmount+"</amount>").append(
						"<signDate>"+inputDate+"</signDate>").append(
						"<appliFlag>2</appliFlag>").append(
						"<handler1Code>"+Sales_Person+"</handler1Code>").append(
						"<linkPersonGroup>").append(
						"<linkPersonDetail>").append(
						"<linkPhone></linkPhone>").append(
						"<linkShip>"+Cnts_Relation1+"</linkShip>").append(
						"<linkMobile>"+Cnts_Mobile1+"</linkMobile>").append(
						"<linkAdress></linkAdress>").append(
						"<insuredFlag>1</insuredFlag>").append(
						"<linkName>"+Cnts_Name1+"</linkName>").append(
						"</linkPersonDetail>").append(
						"<linkPersonDetail>").append(
						"<linkPhone></linkPhone>").append(
						"<linkShip>"+Cnts_Relation2+"</linkShip>").append(
						"<linkMobile>"+Cnts_Mobile2+"</linkMobile>").append(
						"<linkAdress></linkAdress>").append(
						"<insuredFlag>1</insuredFlag>").append(
						"<linkName>"+Cnts_Name2+"</linkName>").append(
						"</linkPersonDetail>").append(
						"</linkPersonGroup>").append(
						"<loanEndDate>2013-06-01</loanEndDate>").append(
						"<insureagrt></insureagrt>").append(
						"<loanStartDate>2013-06-01</loanStartDate>").append(
						"<agreementNo></agreementNo>").append(
						"<addressName>"+Home_Address_State+Home_Address_City+Home_Address_County+Home_Address_Street_Address+"</addressName>").append(
						"<argueSolution>1</argueSolution>").append(
						"<appliPhoneNumber>"+Mobile+"</appliPhoneNumber>").append(
						"<structure></structure>").append(
						"<inputDate>"+inputDate+"</inputDate>").append(
						"<pledgeName>"+DUTY+"</pledgeName>").append(
						"<riskCode>"+SECURE_CDE+"</riskCode>").append(
						"<appliNature>3</appliNature>").append(
						"<loanNature>2</loanNature>").append(
						"<insuredAddress></insuredAddress>").append(
						"<insuredNature>4</insuredNature>").append(
						"<agentCode></agentCode>").append(
						"<appliMobile>"+Mobile+"</appliMobile>").append(
						"<isDisability>1</isDisability>").append(
						"<perRepaidAmount></perRepaidAmount>").append(
						"<appliIdentifyType>01</appliIdentifyType>").append(
						"<houseVariety></houseVariety>").append(
						"<addressCode></addressCode>").append(
						"<insuredFlag>1</insuredFlag>").append(
						"<insuredType>2</insuredType>").append(
						"<disRate>0.0</disRate>").append(
						"<handlerCode>"+Handler_Cde+"</handlerCode>").append(
						"<mortgageSate></mortgageSate>").append(
						"<startDate>2099-01-01</startDate>").append(
						"<loanAmount>"+Apply_Limit+"</loanAmount>").append(
						"<loanUsage>32</loanUsage>").append(
						"<appliAddress>"+Home_Address_State+Home_Address_City+Home_Address_County+Home_Address_Street_Address+"</appliAddress>").append(
						"<repaidType></repaidType>").append(
						"<deliverDate></deliverDate>").append(
						"<insuredName>"+CAP_INSTU_NAME+"</insuredName>").append(
						"<appliType>1</appliType>").append(
						"<education></education>").append(
						"<payTimes>"+Loan_Period+"</payTimes>").append(
						"<appliPostCode></appliPostCode>").append(
						"<comCode>"+Branch+"</comCode>").append(
						"<kindCode>"+KIND_CDE+"</kindCode>").append(
						"<kindName>"+KIND_NAME+"</kindName>").append(
						"<rate>"+Loan_Rate+"</rate>").append(
						"<repaidStartDate>2099-01-01</repaidStartDate>").append(
						"<appliIdentifyNumber>"+Id_Number+"</appliIdentifyNumber>").append(
						"<policyNo>"+PolicyNo+"</policyNo>").append(
						"<sumPremium>"+sumP+"</sumPremium>").append(
						"<sumAmount>"+sumAmount+"</sumAmount>").append(
						"<appliLocalYear>2000</appliLocalYear>").append(
						"<disabilityPart>"+Company_Address_State+Company_Address_City+Company_Address_County+Company_Address_Street_Address+"</disabilityPart>").append(
						"<operatorCode>"+Report_User+"</operatorCode>").append(
						"<operatesite>"+OPERATE_SITE+"</operatesite>").append(
						"<makeCom>"+MAKE_COM+"</makeCom>").append(
						"<insuredMobile></insuredMobile>").append(
						"<marriage></marriage>").append(
						"<applyNum></applyNum>").append(
						"<itemHouseGroup>").append(
						"<itemHouseDetail>").append(
						"<structure></structure>").append(
						"<houseVariety></houseVariety>").append(
						"<buildArea></buildArea>").append(
						"<buildTime></buildTime>").append(
						"<buildPost></buildPost>").append(
						"<buildAdress></buildAdress>").append(
						"<unitValue></unitValue>").append(
						"</itemHouseDetail>").append(
						"</itemHouseGroup>").append(
						"<limitedFee></limitedFee>").append(
						"<mortgageNo>"+Company_Phone+"</mortgageNo>").append(
						"<insuredPostCode>100100</insuredPostCode>").append(
						"<addressNo>1</addressNo>").append(
						"<insuredIdentifyType>08</insuredIdentifyType>").append(
						"<perRepaidDate>10</perRepaidDate>").append(
						"</policy>").append(
						"</policyGroup></TransPolicyRequest>").toString();
		
		return xml;
	}
	
	
	private int getAge(String birthday) {
		birthday = birthday.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
		return AgeUtils.getAgeFromBirthTime(birthday);
	}
	
	private String getBirthFormat(String str) {
		return str.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
	}
	
	private String getLoanStartDate(Date now) {
		return DateUtils.formatDate(now, "yyyy-MM-dd");
	}
	
	private String getLoanEndDate(Date now , String loan_tern) {
		Calendar c = Calendar.getInstance();
		int a = Integer.parseInt(loan_tern);
		c.setTime(now);
		c.add(Calendar.MONTH,a);
		Date end = c.getTime();
		return DateUtils.formatDate(end, "yyyy-MM-dd");
		
	}
	
	
	private String getLoanEndDate(String start , String loan_tern) {
		Date sd = DateUtils.parseDate(start);
		Calendar c = Calendar.getInstance();
		int a = Integer.parseInt(loan_tern);
		c.setTime(sd);
		c.add(Calendar.MONTH,a);
		Date end = c.getTime();
		return DateUtils.formatDate(end, "yyyy-MM-dd");
		
	}
}
