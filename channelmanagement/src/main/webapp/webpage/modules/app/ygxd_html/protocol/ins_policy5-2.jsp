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
<%@page import="java.util.Map"%>
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
Map<String, String> map = (Map<String, String>)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "clPreDetail_key"+policyNo);
	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  
	Date date = format1.parse(applyObj.getCrtDt());
	String yyyy = DateUtils.formatDate(date, "yyyy");
	String MM = DateUtils.formatDate(date, "MM");
	String dd = DateUtils.formatDate(date, "dd");

	
   	BigDecimal premium = (BigDecimal)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "premium_key"+policyNo);
	
	BigDecimal numberOfMoney = new BigDecimal(applyObj.getApplyAmt()).multiply(new BigDecimal("1.2"));
    String applyAmt = NumberToCN.number2CNMontrayUnit(numberOfMoney);
    
    BigDecimal monthPre = premium.multiply(new BigDecimal(applyObj.getApplyAmt())).setScale(2, BigDecimal.ROUND_HALF_UP);
    BigDecimal sumPre = monthPre.multiply(new BigDecimal(applyObj.getApplyTnr()));
    
    String monthPreAmt = NumberToCN.number2CNMontrayUnit(monthPre);
    String sumPreAmt = NumberToCN.number2CNMontrayUnit(sumPre);
    
    String mnthBJRate = premium.multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"%";
    String mnthRate = monthPre.divide(numberOfMoney,6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"%";
    
    
 	request.setAttribute("yyyy", yyyy);
 	request.setAttribute("MM", MM);
 	request.setAttribute("dd", dd);
 	request.setAttribute("applyAmt", applyAmt);
 	request.setAttribute("sumPreAmt", sumPreAmt);
 	request.setAttribute("monthPreAmt", monthPreAmt);
 	request.setAttribute("mnthRate", mnthRate);
 	request.setAttribute("mnthBJRate", mnthBJRate);

 %>
	<meta charset="utf-8">
	<title>保险单</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-status-bar-style"  />
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<script src="../js/jquery.min.js"></script>

	<style type="text/css">
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

		.td_set30{
			width: 30%;
			border: 1px solid #aaa;
			display: table-cell;
			text-align: center;
		}

		.td_common{
			border: 1px solid #aaa;
			display: table-cell;
			text-align: center;
		}
	</style>
</head>
<body>

	<div class="font6 padding10">
		<table class="table_set">
			<tr class="tr_set">
				<td class="td_common font10" colspan="5">
					<b>阳光财产保险股份有限公司<br/>个人借款保证保险 电子保单</b>
				</td>
			</tr>

			<tr class="tr_set">
				<td class="td_common" colspan="5" style="text-align: right;">保单号：<span><%=applyObj.getPolicyNo() %></span></td>
			</tr>

			<tr class="tr_set">
				<td colspan="5">鉴于投保人已向阳光财产保险股份有限公司投保个人借款保证保险并递交了投保协议和有关材料,且声明属实,同意按约定交纳保险费。本保险人特签发本保险单并同意按照阳光财产保险股份有限公司个人借款保证保险条款（一年期）（2016版）及保险合同的约定承担保险责任,特立保险单为凭。</td>
			</tr>
			<tr class="tr_set">
				<td class="td_set15" rowspan="2"><b>投保人</b></td>
				<td class="td_set15">姓名</td>
				<td class="td_set30"><%=idCardInfoObj.getCUST_NAME() %></td>
				<td class="td_set15">联系电话</td>
				<td class="td_set25"><%=usrObj.getUSR_TEL() %></td>
			</tr>

			<tr class="tr_set">
				<td class="td_set15">身份证号码</td>
				<td class="td_set30"><%=idCardInfoObj.getID_NO() %></td>
				<td class="td_set15">地址</td>
				<td class="td_set25"><%=idCardInfoObj.getLIVE_ADDR() %></td>
			</tr>

			<tr class="tr_set">
				<td class="td_set15" rowspan="2"><b>被保险人</b></td>
				<td class="td_set15">名称</td>
				<td class="td_common" colspan="3">中国对外经济贸易信托有限公司</td>
			</tr>

			<tr class="tr_set">
				<td class="td_set15">通讯地址</td>
				<td class="td_set30">北京市西城区复兴门内大街28号凯晨世贸中心中座</td>
				<td class="td_set15">邮编</td>
				<td class="td_set25">100031</td>
			</tr>

			<tr class="tr_set">
				<td class="td_set15"><b>保险期间</b></td>
				<td class="td_common" colspan="4"><b>自贷款发放之日起至贷款本息全部清偿之日止，最长不超过12期</b></td>
			</tr>

			<tr class="tr_set">
				<td class="td_set15"><b>保险金额</b></td>
				<td class="td_common" colspan="4"><b>人民币（大写）<%=applyAmt%>  ￥  <%=numberOfMoney %> </b></td>
			</tr>

			<tr class="tr_set">
				<td rowspan="4" style="text-align: center;"><b>保险费</b></td>
				<td class="td_set15">总保险费金额</td>
				<td class="td_common" colspan="3">总保险费金额为每月保险费金额之和</td>
			</tr>

			<tr class="tr_set">
				<td class="td_set15">每月保险费金额</td>
				<td class="td_common" colspan="3">人民币（大写）<span id="proPerAmtCn"><%=monthPreAmt%></span> ¥<span id="proPerAmt"><%=monthPre %></span></td>
			</tr>

			<tr class="tr_set">
				<td class="td_set15">保险费缴纳方式</td>
				<td class="td_set15">按月期缴</td>
				<td class="td_set15">每月缴费日期</td>
				<td class="td_set40">同贷款合同约定还款日</td>
			</tr>


			<tr class="tr_set">
				<td class="td_set15">月保险费率</td>
				<td id="perRate" class="td_set30"><%=mnthRate%></td>
				<td class="td_set15">月保费本金比例</td>
				<td id="proRate" class="td_set25"><%=mnthBJRate%></td>
			</tr>

			<%-- <tr class="tr_set">
				<td class="td_set15"><b>贷款合同编号</b></td>
				<td class="td_common" colspan="4"><%=applyObj.getPolicyNo() %></td>
			</tr> --%>

			<tr class="tr_set">
				<td class="td_set15"><b>免赔额（率）</b></td>
				<td class="td_common" colspan="4">0.00%</td>
			</tr>

			<tr class="tr_set">
				<td class="td_set15"><b>特别约定</b></td>
				<td class="td_common" colspan="4" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;1、投保人授权保险人、贷款发放人直接或委托支付公司等扣除每月应缴保险费，即本保险合同的保险费缴费方式为每月按时足额缴纳，保险费率的计算基数为总保额。<br/>&nbsp;&nbsp;&nbsp;&nbsp;2、贷款申请人是投保人；发放贷款的资金提供方是被保险人；投保人应按期还款并缴纳保险费，<b>当投保人拖欠任何一期的应偿还贷款达到80天（不含），保险人将按照保险合同的约定对被保险人进行理赔。保险人将相关理赔款赔付至被保险人后，即有权对全部理赔款项和应付而未付保险费向投保人进行追偿。</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;3、保险人按照保险合同约定向被保险人赔付相关款项后，投保人需向保险人归还全部理赔款项和应付而未付保费。<b>从保险人理赔当日开始，投保人未向保险人归还全部款项则视为投保人违约。投保人需以尚欠剩余本金为基数，从保险人理赔当日开始计算，按每日千分之一，向保险人缴纳违约金。</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;4、应付而未付保费是指投保人从贷款发放之日起至理赔之日止这段保险期间，未支付的应缴保险费。<br/>&nbsp;&nbsp;&nbsp;&nbsp;5、投保人发生逾期、提前还款，保险人均有权催回应付而未付保费，投保人还款应按照保费、银行规定的相应费用、利息、本金的顺序进行；保险人基于投保人拖欠任何一期的应偿而未偿还贷款达到80天（不含）而向被保险人理赔后，保险人有权向投保人催回应付而未付保费、理赔款项、违约金、理赔及催收产生的其他费用（包括但不限于诉讼费、保全费、公告费、咨询费、律师费等）。<br/>&nbsp;&nbsp;&nbsp;&nbsp;6、本保单所载明的“保险金额”，是保险人根据被保险人要求及贷款合同载明的本金及相应利息计算的金额。<br/>&nbsp;&nbsp;&nbsp;&nbsp;7、鉴于投保人与保险人商定采用“按月期缴”方式缴纳保险费。保险人依照保险金额、保险期间及具体风险状况等将确定的“每月保险费金额”在本保单载明。每月保险费=保险金额*月保险费率；总保险费等于保险期间内各月月保险费之和，保险期间不足一个月的，按一个月计算收取保费。投保人应按照本保险合同的约定，按时足额缴纳每月保险费。<br/>&nbsp;&nbsp;&nbsp;&nbsp;8、本保单所载明的“月保费本金比例”，仅适用于根据贷款金额试算每月保险费的情况；月保费本金比例=每月保费金额/贷款合同载明的本金；月保险费率=每月保费金额/保险金额。<br/>&nbsp;&nbsp;&nbsp;&nbsp;9、投保人同意保险人根据投保单上所载明的投保人电话号码，向投保人发送相关的短信告知信息（包括但不限于保险业务相关信息、征信相关信息等）。<br/>&nbsp;&nbsp;&nbsp;&nbsp;10、本保险合同的保险人为阳光财产保险股份有限公司，保险人地址为北京市通州区永顺镇商通大道1号院2号楼三层。</td>
			</tr>

			<tr class="tr_set">
				<td class="td_set15"><b>争议处理</b></td>
				<td class="td_common" colspan="4" style="text-align: left;">因履行本合同发生争议，由当事人协商解决，协商不成的，依法向<b>北京市通州区人民法院起诉</b>。</td>
			</tr>

			<tr class="tr_set">
				<td class="td_set15"><b>明示告知</b></td>
				<td class="td_common" colspan="4" style="text-align: left;"><b>1、收到本保险单后请立即核对，填写内容如与投保不符，立即通知本保险人采取批改方式更改，其它方式无效。<br/>2、详细阅读《阳光财产保险股份有限公司个人借款保证保险条款（一年期）（2016版）》条款，特别是有关责任免除和投保人、被保险人义务部分。<br/>3、为保证投保人的合法权益，投保人可通过访问本公司官网www.sinosig.com或拨打本公司全国统一客户服务电话95510核实保单有效性等信息。<br/>4、在保险期间内，上述事项如有变更，应及时通知保险人。</b></td>
			</tr>

			<tr class="tr_set">
				<td colspan="5" class="td_common" style="text-align: left;">
					<p><b>投保人声明：</b></p>
					<p class="indent"><b>本人已知悉、理解、认可并仔细核对了电子保单信息确认的全部内容，愿意按照以电子保单信息确认页为基础而生成的本保险单的约定投保本保险，没有异议。本人认可，如果保险合同中《阳光财产保险股份有限公司个人借款保证保险（一年期）（2016版）投保单》的内容与本《阳光财产保险股份有限公司个人借款保证保险（一年期）（2016版）电子保单》的内容有所出入，以本保险单内容为准。本人已详细阅读所附保险条款，尤其是关于免除保险人责任的条款、投保人及被保险人义务的规定。</b></p>
					<p><b>特别提示：</b></p>
					<div>
						<p style="float: left; width: 60%;"><b>我公司不允许收取保险合同以外的任何款项（包括但不限于保证金、征信费等）</b></p>
						<p style="float: left; width: 40%"><b>投保人签名：<%=idCardInfoObj.getCUST_NAME() %></b></p>
					</div>
					<div style="clear: both;"></div>
					<div>
						<p style="float: left; width: 60%"><b>投诉电话：95510 </b></p>
						<p style="float: left; width: 40%"><b> 日期： <%=yyyy %>年   <%=MM %> 月  <%=dd %>  日</b></p>
					</div>
					<div style="clear: both;"></div>
				</td>
			</tr>

			<tr class="tr_set">
				<td class="td_common" colspan="5" style="text-align: left;">
					<div>
						<p style="float: left; width: 50%">签单机构名称：阳光财产保险股份有限公司北京分公司</p>
						<p style="float: left; width: 50%">签单日期：<span id="signDate"> <%=yyyy %>年   <%=MM %> 月  <%=dd %>  日</span></p>
					</div>
					<div style="clear: both;"></div>
					<div>
						<p style="float: left; width: 50%">签单机构地址：北京市朝阳区东三环南路98号1 幢高和蓝峰大厦8层</p>
						<p style="float: left; width: 50%">注：保险条款附后。</p>
					</div>
					<div style="clear: both;"></div>
					<!-- <div>
						<p style="float: left; width: 33%">核保</p>
						<p style="float: left; width: 33%">制单</p>
						<p style="float: left; width: 33%">客户经理</p>
					</div> -->
					<div style="clear: both;"></div>
				</td>
			</tr>

			
		</table>
	</div>

</body>
</html>