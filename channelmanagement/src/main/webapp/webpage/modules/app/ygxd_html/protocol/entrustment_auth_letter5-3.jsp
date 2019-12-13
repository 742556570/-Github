<%@page import="com.jeeplus.modules.app.constant.AppCommonConstants"%>
<%@page import="com.jeeplus.modules.app.utils.CacheHelper"%>
<%@page import="com.jeeplus.modules.app.entity.ClUsrApply"%>
<%@page import="com.jeeplus.modules.app.entity.ClUsr"%>
<%@page import="com.jeeplus.modules.app.entity.ClUsrInfo"%>
<%@page import="com.jeeplus.modules.app.entity.ClIdCardInfo"%>
<%@page import="com.jeeplus.modules.app.entity.ClBnkCrd"%>
<%@page import="com.jeeplus.modules.app.entity.ClCapChannel"%>
<%@page import="com.jeeplus.common.utils.DateUtils"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.jeeplus.common.utils.NumberToCN"%>
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
	ClCapChannel capChannelObj = (ClCapChannel)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "capChannel_key"+policyNo);
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
	<meta charset="utf-8">
	<title>委托扣款授权书（保费）</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-status-bar-style"  />
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<script src="../js/jquery.min.js"></script>
	<script type="text/javascript" src="../js/appPublic.js"></script>
	<style type="text/css">
		.lh16{
			line-height: 1.6rem;
		}
		.underLine666{
			color: #666;
			text-decoration: underline;
		}
	</style>
	<script type="text/javascript">
		var userInfo = window.sessionStorage.getItem(userInfoKey);
		if(userInfo != null){
			var proInfoObj = window.JSON.parse(userInfo);
		}

		$(document).ready(function(){
			if(proInfoObj != null){
				$("#signY, #signY2").text(getYear());
				$("#signM, #signM2").text(getMonth());
				$("#signD, #signD2").text(getDay());
				$("#accBank").text(proInfoObj.bank);
				$("#accNum").text(proInfoObj.bankcardNum);
				$("#signName").text(proInfoObj.name);
			}
			
		});


	</script>
</head>
<body>
	<div class="font8 padding10">
		<h1 style="text-align: center;" class="font14">委托扣款授权书 </h1>
		
		<p class="lh16" style="margin-top: 2rem">阳光财产保险股份有限公司：</p>
		<p class="indent">本人已于<span id="signY" class="underLine666"><%=yyyy %></span>年<span id="signM" class="underLine666"><%=MM %></span>月<span id="signD" class="underLine666"><%=dd %></span>日与<span id="moneyFather" class="underLine666"><%=capChannelObj.getCAP_INSTU_NAME()%></span>签订了《贷款合同》（下称“贷款合同”）。为保证贷款合同项下的借款的归还，本人向贵司投保了个人借款保证保险，签署了保单号为<span class="underLine666"><%=applyObj.getPolicyNo() %></span>的《阳光财产保险股份有限公司 个人借款保证保险（一年期）（2016 版）保险单》。</p>

		<p class="indent">本人现授权贵司及贵司指定收款方可以委托第三方账户管理机构据保险单约定的缴费日期及金额从本人在<span id="accBank" class="underLine666"><%=clBnkCrdObj.getAPPL_BANK_NAME()%></span>开立的个人账户（账号为：<span id="accNum" class="underLine666"><%=clBnkCrdObj.getAPPL_CARD_NO()%></span>）中扣收保险费。如本人账户余额不足导致应缴保费未能全额扣划或扣划失败的，贵司可委托第三方账户管理机构在本人账户余额大于 0元时自动补偿扣划。</p>

		<p class="indent">若贵司对贷款合同项下的贷款本息进行理赔后，本人特授权贵司可委托第三方账户管理机构从本人的上述个人账户中扣除本人应向贵司归还的全部款项（包括贵司已赔付的贷款本息、罚息、复利、及本人应向贵司支付的未付保费和其他任何款项，包括因贵司代本人赔付而产生的滞纳金和各项手续费）结清之日止。</p>

		<p class="indent">如授权期间发生本人个人账户信息的变更，本人承诺在变更后 2 个工作日内及时告知贵司，否则本人将承担一切法律和经济后果。上述授权将延续至变更后的个人账户。</p>

		<p class="indent">本委托在《贷款合同》及《阳光财产保险股份有限公司 个人借款保证保险（一年期）（2016 版）保险单》有效期内不可撤销。且仅适用于本人申请的个人借款保证保险产品。</p>

		<div style="width: 100%;">
			<div style="position: absolute;right: 3rem">
				<p style="margin-top: 2rem"  >委托人（签名）<span id="signName"><%=idCardInfoObj.getCUST_NAME() %></span></p>
				<p style="margin-top: 2rem; margin-bottom: 4rem;"><span id="signY2"><%=yyyy %></span>年<span id="signM2"><%=MM %></span>月<span id="signD2"><%=dd %></span>日</p>
			</div>
			

		</div>
		


	</div>
</body>
</html>