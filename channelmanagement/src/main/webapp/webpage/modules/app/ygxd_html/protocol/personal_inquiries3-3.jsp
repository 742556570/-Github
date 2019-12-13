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
<%@page import="com.jeeplus.common.utils.NumberToCN"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%		
String policyNo = request.getParameter("policyNo");
ClUsrApply applyObj = (ClUsrApply)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "apply_key"+policyNo);
ClUsr usrObj = (ClUsr)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "usr_key"+policyNo);
ClUsrInfo usrInfoObj = (ClUsrInfo)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "usrInfo_key"+policyNo);
ClIdCardInfo idCardInfoObj = (ClIdCardInfo)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "idCard_key"+policyNo);
ClBnkCrd clBnkCrdObj = (ClBnkCrd)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "bankcard_key"+policyNo);
	Date date = new Date();
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
	<meta charset="utf-8">
	<title>个人征信授权书</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-status-bar-style"  />
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<script src="../js/jquery.min.js"></script>
</head>
<body>
	<div class="font8 padding10">
		<h1 style="width: 100%; text-align: center; line-height: 2.8rem" class="font14">个人征信授权书</h1>
		<p style="line-height: 1.6rem" class="marginTop10">阳光保险集团股份有限公司：</p>
		<p class="indent">因本人向贵集团控股子公司阳光财产保险股份有限公司（以下简称阳光产险）申请办理个人贷款保证保险业务，本人在此不可撤销的授权贵集团向征信机构(包括但不限于中国人民银行金融信用信息基础数据库和经中国人民银行批准设立的征信机构)、银行、公积金部门、社保部门、税务部门、通信运营商、电子商务平台、互联网金融平台查询、打印、使用本人的个人征信报告及个人信息数据，用于阳光产险办理、审核个人贷款保证保险业务的贷款审核及贷后风险管理、催收、追偿。同时授权贵集团将包括本人的贷款申请信息、投保信息及还款记录、还款违约行为等个人信用信息向上述机构报送。</p>
		<p class="indent">本人允许将上述涉及本人的相关信息报告、数据提供给阳光保险集团（即阳光保险集团股份有限公司及其直接或间接控股的公司）使用。</p>
		<p class="indent">无论本授权涉及的相关业务是否获得批准，该授权持续有效直至相关申请的业务全部终结时止。</p>
		<p class="indent">被授权人有权保留本授权书及授权人的个人征信报告及个人信息数据。</p>
		<p class="indent marginTop10">本人经认真阅读及询问，已完全知悉、理解并同意上述内容！</p>

		<div style="margin-left: 30%">
			<p style="line-height: 1.6rem">授权人签名：   （签章处）<%=idCardInfoObj.getCUST_NAME() %></p>
			<p style="line-height: 1.6rem">授权人身份证件号码：<span class="inputUnderLine"><%=idCardInfoObj.getID_NO() %></span>
			</p>
			<p style="line-height: 1.6rem">授权日期：<span><%=yyyy %>年   <%=MM %> 月  <%=dd %>  日</span></p>
		</div>

		<p style="line-height: 1.6rem" class="marginTop10">（为保护您的合法权益，以上空白处请填写完整）</p>

	</div>

</body>
</html>