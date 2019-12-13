<%@page import="com.jeeplus.modules.app.constant.AppCommonConstants"%>
<%@page import="com.jeeplus.modules.app.utils.CacheHelper"%>
<%@page import="com.jeeplus.modules.app.entity.ClUsrApply"%>
<%@page import="com.jeeplus.modules.app.entity.ClUsr"%>
<%@page import="com.jeeplus.modules.app.entity.ClUsrInfo"%>
<%@page import="com.jeeplus.modules.app.entity.ClIdCardInfo"%>
<%@page import="com.jeeplus.modules.app.entity.ClBnkCrd"%>
<%@page import="com.jeeplus.common.utils.DateUtils"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Date"%>
<%@page import="com.jeeplus.common.utils.NumberToCN"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%		
	String policyNo = request.getParameter("policyNo");
	
	ClUsrApply applyObj = (ClUsrApply)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "apply_key"+policyNo);
	ClUsr usrObj = (ClUsr)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "usr_key"+policyNo);
	ClUsrInfo usrInfoObj = (ClUsrInfo)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "usrInfo_key"+policyNo);
	ClIdCardInfo idCardInfoObj = (ClIdCardInfo)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "idCard_key"+policyNo);
	ClBnkCrd clBnkCrdObj = (ClBnkCrd)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "bankcard_key"+policyNo);
	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  
	Date date = format1.parse(applyObj.getCrtDt());
	String yyyy = DateUtils.formatDate(date, "yyyy");
	String MM = DateUtils.formatDate(date, "MM");
	String dd = DateUtils.formatDate(date, "dd");

	
	BigDecimal numberOfMoney = new BigDecimal(applyObj.getApplyAmt());
    String applyAmt = NumberToCN.number2CNMontrayUnit(numberOfMoney);
     
 	request.setAttribute("yyyy", yyyy);
 	request.setAttribute("MM", MM);
 	request.setAttribute("dd", dd);
 	request.setAttribute("applyAmt", applyAmt);

 %>
 


<h1><%= policyNo%></h1>
</body>
</html>