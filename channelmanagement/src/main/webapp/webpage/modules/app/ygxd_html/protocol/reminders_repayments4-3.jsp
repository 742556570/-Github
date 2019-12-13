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
<%@page import="com.jeeplus.modules.app.entity.ClIdCardInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response.LoanRepaymentPlanDetail"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%	
String policyNo = request.getParameter("policyNo");

	List<Object> loanRepaymentPlanDetailList = CacheHelper.getListObject(AppCommonConstants.getValStr("CACHESCHEMA"), "LoanRepaymentPlan"+policyNo);
 	ClUsrApply applyObj = (ClUsrApply)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "apply_key"+policyNo);
 	ClIdCardInfo idCardInfoObj = (ClIdCardInfo)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "idCard_key"+policyNo);
 		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  
 		Date date = format1.parse(applyObj.getCrtDt());
 		String yyyy = DateUtils.formatDate(date, "yyyy");
 		String MM = DateUtils.formatDate(date, "MM");
 		String dd = DateUtils.formatDate(date, "dd");
 	 	request.setAttribute("loanRepaymentPlanDetailList", loanRepaymentPlanDetailList);
 	 	request.setAttribute("yyyy", yyyy);
 	 	request.setAttribute("MM", MM);
 	 	request.setAttribute("dd", dd);

 %>
	<meta charset="utf-8">
	<title>还款事项提醒函</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-status-bar-style"  />
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<script src="../js/jquery.min.js"></script>
	<style type="text/css">
		.lh16{
			line-height: 1.6rem;
		}
		.underLine666{
			color: #666;
			text-decoration: underline;
		}
		.td_common{
			display: table-cell;
			padding: 0.4rem;
			vertical-align: top;
			line-height: 1.6rem;
		}
		.td_set10{
			width: 10%;
			border: 1px solid #aaa;
			display: table-cell;
			text-align: center;
		}
		.td_set15{
			width: 15%;
			border: 1px solid #aaa;
			display: table-cell;
			text-align: center;
		}
		.td_set25{
			width: 25%;
			border: 1px solid #aaa;
			display: table-cell;
			text-align: center;
		}
	</style>
</head>
<body>
	<div class="font8 padding10">
		<h1 style="width: 100%; text-align: center; line-height: 2.8rem" class="font14">还款事项提醒函</h1>

		<p class="lh16"><span class="underLine666"><%=idCardInfoObj.getCUST_NAME() %></span>先生/女士您好：</p>
		<p class="indent">为了您更加方便、快捷地进行还款，同时在我司积累良好的信用记录，在此，请您特别注意如下事项：</p>

		<table style="border-collapse: collapse; width: 100%">
			<tr class="tr_set">
				<td class="td_common" style="width: 1rem;">1.</td>
				<td class="td_common"><b>还款账户：</b>在贷款合同里列示的指定还款划扣账号是您每次用于还款的账户，请您妥善保管好该银行卡。如发生此银行卡丢失、损坏的情况，请您第一时间联系我们。我们将根据具体情况，协助您安排按时还款事宜，以避免不必要的逾期违约金的产生。如您需要变更还款账户，请向我们提出申请（还款日当天不受理），我们的工作人员将协助您，进行变更贷款合同中专用账号的事项办理。</td>
			</tr>

			<tr class="tr_set">
				<td class="td_common" style="width: 1rem;">2.</td>
				<td class="td_common"><b>还款账户最低余额为15元：</b>为了保证您的还款成功划扣，请确保您的还款账户余额在划扣当月还款额以后，还有至少15元的余额存在您的还款账户里，此金额有可能用于支付还款银行的“年费”和其他相关费用。关于此“年费”和其他费用收费标准，请致电您的还款银行咨询。同时为了保证扣款日能够成功划扣，请您保持专用账号中有充足的资金，避免因其他未知或遗忘的代扣等业务造成违约。</td>
			</tr>

			<tr class="tr_set">
				<td class="td_common" style="width: 1rem;">3.</td>
				<td class="td_common"><b>还款金额：</b>您的每月还款额详见下表（包含保费、服务费及本息），<b>请您务必牢记此数字。</b></td>
			</tr>


			<tr class="tr_set">
				<td class="td_common" style="width: 1rem;">4.</td>
				<td class="td_common"><b>还款时间：</b>请您在贷款合同约定的每月还款日09:00点或之前，将每月还款额足额存到您的借记卡里，逾期将产生逾期违约金，并会影响您的个人信用记录。<b>请您务必牢记此还款日，</b>此还款日期不因节假日顺延。<b>如您的还款日为每月30日，那么2月还款日为2月末最后一天。</b><br/><b>若您采用跨行转账方式汇入还款账户，请提前一周进行划转。</b>我们将以成功划扣还款账户的还款金额时间为准，记录还款时间。</td>
			</tr>


			<tr class="tr_set">
				<td class="td_common" style="width: 1rem;">5.</td>
				<td class="td_common">如果您因特殊原因暂时无法按时足额还款时，请在第一时间（<b>当月还款日期之前</b>）与我们联系。（电话：【400-151-5500】）。我们将协助您安排预约还款并准确告知您还款金额（本息、逾期违约金、协议约定的其他费用等）。<b>特别需要您值得注意的是：针对违约行为，根据贷款合同，出借人有权提前终止贷款合同，您须在出借人提出终止贷款合同要求的三日内一次性支付余下的所有本金、利息和逾期违约金。</b></td>
			</tr>


			<tr class="tr_set">
				<td class="td_common" style="width: 1rem;">6.</td>
				<td class="td_common"><b>联系方式变更：</b>为了方便我们更好地服务于您，如果您需要变更还款账号，或者您（或是您共同借款人）的联系方式、工作单位、居住或工作地址等与借款有关的信息发生变化，请在第一时间拨打【400-151-5500】通知我们。注：在还款日当天不予变更还款账号。</td>
			</tr>


			<tr class="tr_set">
				<td class="td_common" style="width: 1rem;">7.</td>
				<td class="td_common"><b>提前还款（即一次性还款）：</b>为了方便我们更好地服务于您，如果您决定一次性将全部款项结清，请您务必提前三个工作日与我们进行书面确认（还款日及节假日期间不办理）。我们将帮助您安排预约一次性还款的相关事宜。提前还款电话：【400-151-5500】。</td>
			</tr>


			<tr class="tr_set">
				<td class="td_common" style="width: 1rem;">8.</td>
				<td class="td_common">您知晓并确认：本《还款事项提醒函》已包含了您与出借人所签署贷款合同的附件三《还款计划表》内容，您就本提醒函所作还款承诺效力及于贷款合同，您对此不持有任何异议。</td>
			</tr>
		</table>

		<p class="lh16">您的还款分期表如下：</p>
		<table class="table_set">
			
			<tr class="tr_set">
				<td class="td_set10">期数</td>
				<td class="td_set25">应还款额</td>
			</tr>
			<c:forEach items="${loanRepaymentPlanDetailList}" var="item">
			<tr>
				<td class="td_set10">${item.sterm}</td>
				<td class="td_set25">${item.sinsuamt+item.scapi+item.sinte}</td>
				</tr>
			</c:forEach>
			
		</table>
		<div style="float: right; width: 50%; margin: 4rem 0;">
			<p>借款人：   （签章处）<%=idCardInfoObj.getCUST_NAME() %></p>
			<p>日期：<%=yyyy %> - <%=MM %> -  <%=dd %></p>
		</div>

	</div>
</body>
</html>