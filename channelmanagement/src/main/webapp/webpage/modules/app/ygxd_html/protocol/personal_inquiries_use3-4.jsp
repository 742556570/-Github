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
	<title>个人信息查询使用授权书</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-status-bar-style"  />
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<script src="../js/jquery.min.js"></script>
</head>
<body>
	<div class="font8 padding10">
		<h1 style="width: 100%; text-align: center; line-height: 2.8rem" class="font14">个人信息查询使用授权书</h1>
		<p style="line-height: 1.6rem" class="marginTop10"><b>致：阳光财产保险股份有限公司</b></p>
		<p class="indent">鉴于，本人（姓名：<span class="inputUnderLine"><%=idCardInfoObj.getCUST_NAME() %></span>；身份证号：<span class="inputUnderLine"><%=idCardInfoObj.getID_NO() %></span>）拟向贵司投保个人贷款/借款保证保险等相关业务，为便于贵司风控管理，本人在此不可撤销地同意并授权如下：</p>
		<p class="indent">1、授权内容： </p>
		<p class="indent">贵司有权自行或通过贵司合作第三方机构（本授权书中贵司合作第三方机构包括贵司关联公司、代理人、服务机构、外包作业机构及贵司认为必要的其他业务合作机构）收集保存、加工分析、传递、查询、核实本人个人信息及行为数据，包括但不限于：①本人身份信息、住所地、电话号码、电子邮箱及其它联系信息，教育背景，职业工作信息等；②本人访问合作第三方机构网站、应用程序或使用相关产品、服务的操作日志及留存、形成的其他数据信息，如本人网络账户开立及认证信息、账户余额、流水信息、银行账户信息、访问设备的地理位置等；③财务状况，如本人店铺经营状况、财税信息、房产信息、车辆信息、各类投资理财信息和负债信息等；④政府、事业单位、自律组织等公共服务机构及其他机构依法留存的各类信息，如本人作为法定代表人或实际控制人的工商信息、诉讼信息、执行信息、违法犯罪等不良信息、社保公积金相关信息等（如社保参保状态、缴费额度、缴费年限、缴费单位等；公积金基本信息、缴存状态、缴存支用额度流水、缴存年限、缴费单位、贷款信息等）；⑤个人诚信数据及个人信用行为记录（如本人通讯号码、入网时间、联系地址等基本信息，账户余额、消费记录、欠费情况等通讯费用信息以及往来通讯号码、通话时长等通话详单信息；本人银行信息，如开卡银行、银行卡号、额度、还款情况等基本信息，刷卡时间、地点、金额等用卡信息，账号开立信息、账号余额、流水信息等）；⑥合法获取的与本人接受贵司服务有关的其他信息，如本人担保物信息及行踪轨迹信息等：</p>
		<p class="indent">（1）在法律规定的范围内，贵司有权将上述个人信息及行为数据用于贵司自行或与贵司合作第三方共同开展的经营业务，如：投保申请审核、贷款审核、担保审核、承保前反欺诈、信息关联审核、保后及贷后管理、案件调查、催收及资金追索等过程中。</p>
		<p class="indent">（2）<b>本人同意信息提供者可以依本授权条款向贵司及贵司合作第三方机构提供本人的个人信息及行为数据而无需另行逐一获得本人授权。</b></p>
		<p class="indent">（3）本人同意并授权，贵司有权将在保险服务过程中及本授权书项下收集、获取的本人个人资料、信息数据、还款记录等<b>披露</b>给贵司合作第三方机构、中国人民银行金融信用信息基础数据库、征信机构、仲裁/司法及行政机关、贷款业务监管机构、行业协会及其他行业自律组织而无须另行逐一获得本人授权，<b>对第三方及相关机构使用贵司披露的本人信息所产生的法律后果，均与贵司无关。</b></p>
		<p class="indent">（4）<b>本人知晓并已被明确告知贵司及贵司合作第三方机构获取本人收入、存款、有价证券、商业保险、不动产的信息和纳税数额信息以及本授权书项下其他本人个人信息及行为数据，有可能会给本人带来财产或信用损失以及其他可能的不利后果，包括但不限于：采集这些信息对本人的信用评级（评分）、信用报告等结果可能产生的不利影响，以及该等信息被信息使用者依法提供给第三方后被他人不当利用的风险，但本人仍然同意授权向贵司及贵司合作第三方机构提供这些信息。</b></p>
		<p class="indent">（5）贵司有权保留本授权书及授权人的个人征信报告、个人信息数据等本人个人信息及行为数据。</p>

		<p class="indent">2、本人允许将上述涉及本人的相关信息报告、数据等本人个人信息及行为数据提供给阳光保险集团（即阳光保险集团股份有限公司及其直接或间接控股的公司）使用。</p>
		<p class="indent">3、授权期限：本人同意上述授权期限为本授权书生效之日起直至本人在贵司保险业务完全结束之日止。</p>
		<p class="indent">4、授权书生效：本人确认并同意，本授权书自本人签字或在线签名确认之日起生效。</p>

		<p class="indent">特别提示：</p>
		<p class="indent">为了保障您的合法权益，您应当阅读并遵守本授权书。请您务必审慎阅读，并充分理解本授权书的全部内容，特别是以加粗形式提示您注意的。<b>若您不接受本授权书的任何条款，请您立即停止授权。</b></p>
		<p class="indent">特别声明：</p>
		<p class="indent">本授权书经接受后即时生效，且效力具有独立性，不因相关业务合同或条款无效或被撤销而无效或失效，<b>本授权一经做出，便不可撤销。</b></p>
		<p class="indent"><b>本人已知悉本授权书全部内容（特别是加粗字体内容）的含义及因此产生的法律效力，自愿作出以上授权。本授权书是本人真实意思表示，本人同意承担由此带来的一切法律后果。</b></p>

		<div style="margin-bottom: 4rem" class="padding10">
			<div style="position: absolute; right: 1rem;">
				<p style="line-height: 1.6rem">授权人（本人签名）：   （签章处）<%=idCardInfoObj.getCUST_NAME() %></p>
				<p style="line-height: 1.6rem">签署日期：<span><%=yyyy %>年   <%=MM %> 月  <%=dd %>  日</span></p>
			</div>
		</div>
	</div>

</body>
</html>