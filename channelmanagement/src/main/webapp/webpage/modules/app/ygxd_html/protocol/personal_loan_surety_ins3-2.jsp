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
ClBnkCrd clBnkCrdObj = (ClBnkCrd)CacheHelper.getObject(AppCommonConstants.getValStr("CACHESCHEMA"), "bankcard_key"+policyNo);	Date date = new Date();
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
	<title>预投保单</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-status-bar-style"  />
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<script src="../js/jquery.min.js"></script>

	<style type="text/css">   
		.linear{   
		       FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=##15A216,endColorStr=#fafafa); /*IE*/   
		       background:-webkit-linear-gradient(#00999999, #66999999);
		       background:-o-linear-gradient(#00999999, #66999999);
		       background:linear-gradient(#00999999, #66999999);/*火狐*/   
		       background:-webkit-gradient(linear, 0 0, 0 bottom, from(rgba(153, 153, 153, 0)), to(rgba(153, 153, 153, 0.4)));
		     
		       
		       /*background-image: -webkit-gradient(linear,left bottom,left top,color-start(0.6, #999999),color-stop(1, #999999));/* Safari & Chrome*/*/  
		       filter:  progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#00999999', endColorstr='#66999999'); /*IE6 & IE7*/  
		       -ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#00999999', endColorstr='#66999999')"; /* IE8 */  
		    }   
	</style>  

	<script type="text/javascript">
		$(document).ready(function(){

			// postMessage("进入页面");

			// $("#agree").click(function(){
			// 	if($("#protocol_status").is(":checked")){
			// 		//点击跳转，，，
			// 	 	// $(location).attr('href', 'personal_loan_surety_ins3-1.html');  
			// 	 	window.WebViewJavascriptBridge.callHandler(
   //              		'YGH5CallNative'
   //              		, {'pageName': '授信预投保保单确认',
   //              			'func': 'personalLoan_click',
   //              			'params': null}
   //              		, function(responseData) {
   //       				}
   //          		);
			// 	}else{
			// 		alert("请阅读并同意授权协议");
			// 	}
			// });

		});
	</script>

	

	<script type="text/javascript">
        window.onbeforeunload = onbeforeunload_handler;
        function onbeforeunload_handler() {
            postMessage("页面退出");
            return null;
        }
    </script>



</head>
<body>
	<div style="width: 100%">
		<img src="..\img\personal_loan_title.png" style="width: 100%">
	</div>

	<div class="font8 padding10">
		
		<p style="line-height: 1.6rem" class="marginTop10">
		<b>提示：</b>请您仔细阅读如下条款，特别注意<b>责任免除</b>及其他<b>加粗字体</b>的部分。</p>

		<div>
			<p style="line-height: 1.6rem" class="marginTop10"><b>产品介绍<br/>投保特别约定<br/>信息查询及使用授权条款<br/>其他<br/>阳光财产保险股份有限公司个人借款保证保险条款（一年期）（2016版）</b></p>
		</div>

		<h1 style="width: 100%; text-align: center; margin: 1rem 0;" class="font14">阳光财产保险股份有限公司<br/>个人借款保证保险投保单</h1>

		<div>
			<table class="table_set">
				<tr class="tr_set">
					<td class="td_set20">申请号</td>
					<td class="td_set80"></td>
				</tr>

				<tr class="tr_set">
					<td class="td_set20">投保人</td>
					<td class="td_set80">
						<ul>
							<li style="list-style-type: none;">姓名：<span class="inputUnderLine"><%=idCardInfoObj.getCUST_NAME() %></span>；性别：<span class="inputUnderLine"><%=idCardInfoObj.getINDIV_SEX() %></span>；国籍：<span class="inputUnderLine">中国</span>；</li>
							<li style="list-style-type: none;">身份证号：<span class="inputUnderLine"><%=idCardInfoObj.getID_NO() %></span>；身份证有效期：<span class="inputUnderLine"><%=idCardInfoObj.getVALID_DATE1()%>-<%=idCardInfoObj.getVALID_DATE2()%></span>；</li>
							<li style="list-style-type: none;">手机号：<span class="inputUnderLine"><%=usrObj.getUSR_TEL() %></span>；</li>
						</ul>
					</td>
				</tr>

				<tr class="tr_set">
					<td class="td_set20">被保险人</td>
					<td class="td_set80">为以保险单承保的贷款合同中所载明的贷款人</td>
				</tr>

				<tr class="tr_set">
					<td class="td_set20"><b>投保人与被保险人的关系</b></td>
					<td class="td_set80">借贷关系</td>
				</tr>

				<!-- <tr class="tr_set">
					<td class="td_set20">投保的贷款金额</td>
					<td class="td_set80">最高不超过20万元人民币，最终以保险单承保的贷款合同中所约定的贷款本金及应付利息之和为准</td>
				</tr> -->

				<tr class="tr_set">
					<td class="td_set20"><b>投保的保险期间</b></td>
					<td class="td_set80">自贷款发放之日起至全部贷款本息清偿之日止。最终以保险单约定为准，最长不超过12个月</td>
				</tr>

				<tr class="tr_set">
					<td class="td_set20"><b>月保险费率</b></td>
					<td class="td_set80">以保险单约定为准</td>
				</tr>

				<tr class="tr_set">
					<td class="td_set20"><b>免赔额（率）</b></td>
					<td class="td_set80">0%</td>
				</tr>

				<tr class="tr_set">
					<td class="td_set20"><b>贷款用途</b></td>
					<td class="td_set80">本人了解并声明：<br/>A、本次申请贷款用途为本人真实表述； B、贷款用途保证符合国家有关监管部门和保险人的以下规定和要求：a）贷款资金不会以任何形式进入证券市场，或用于股本权益性投资；b）贷款资金不会用于房地产项目开发；c）贷款资金不会用于国家明令禁止或限制的经营活动；d）该笔贷款发放账户未开通第三方存管功能（即该结算账户未开通用于股票、基金、期权、期货等资金往来结算功能）。</td>
				</tr>

				<tr class="tr_set">
					<td class="td_set20"><b>争议处理方式</b></td>
					<td class="td_set80">因履行本合同发生争议，由当事人协商解决，协商不成的，依法向北京市通州区人民法院起诉。</td>
				</tr>
			</table>
		</div>

		<p class="indent"><b>为获得贷款，本人自愿投保阳光财产保险股份有限公司个人借款保证保险。本人（投保人）同意实际承保的贷款本金及期限根据本人以贷款合同项下载明的贷款金额确定，并以保险公司签发的保险单约定为准。</b></p>
		<p class="indent"><b>本人已仔细阅读并接受关于投保的保险产品的责任免除、投保人及被保险人义务等重要事项，确认投保须知全部内容、个人借款保证保险条款及费率等，并对该等内容均无异议。本人确认并同意本投保单的所有授权条款，完全同意并确认遵守及履行本投保单所载之所有承诺、声明及条款。</b></p>
		<p class="indent"><b>投保人的如实告知义务：</b>（1）投保人对其所提供材料的真实性、准确性、完整性负责。（2）保险公司就投保人的有关情况提出询问的，投保人应当如实告知，并积极配合。<b>违反如实告知义务的后果：（1）投保人故意或者因重大过失未履行前款规定的如实告知义务，足以影响保险公司决定是否同意承保或者提高保险费率的，保险公司有权解除保险合同。</b>前述合同解除权，自保险公司知道有解除事由之日起，超过三十日不行使而消灭。自合同成立之日起超过二年的，保险公司不得解除合同；发生保险事故的，保险公司应当承担赔偿保险金的责任。<b>（2）投保人故意不履行如实告知义务的，保险公司对于合同解除前发生的保险事故，不承担赔偿保险金的责任，并不退还保险费。（3）投保人因重大过失未履行如实告知义务，对保险事故的发生有严重影响的，保险公司对于合同解除前发生的保险事故，不承担理赔保险金的责任，但应当退还保险费。</b>保险公司在本保险合同订立时已经知道投保人未如实告知的情况的，保险公司不得解除合同；发生保险事故的，保险公司应当承担理赔保险金的责任。</p>
		<h1 style="width: 100%; text-align: center; line-height: 2.8rem" class="font11 marginTop10">一、产品介绍</h1>
		<p class="indent">（一）您作为投保人向保险公司进行投保，被保险人为保险单承保的贷款合同中所载明的贷款人。保险产品名称为个人借款保证保险，保险条款为《阳光财产保险股份有限公司个人借款保证保险条款（一年期）（2016版）》，产品备案编号为(阳光财险)(备-保证保险)[ 2016] (主)049号。保险责任、责任免除、保险金额与免赔率、保险期间、保险费、保险人义务、理赔处理等内容请见保险条款。</p>
		<p class="indent">（二）保险产品由阳光财产保险股份有限公司承保，保险公司在全国各省及直辖市（港澳台除外）设有分公司，在设有分公司的区域销售；投保人在线投保，保险公司仅提供电子保单，该电子保单作为投保单成立的合法有效凭证，可在<span>http://www.sinosig.com/</span>网址进行查询。投保人仅限于具有民事权利能力及民事行为能力的中国大陆公民（不含港澳台人士）。</p>
		<p class="indent">（三）保险责任：在保险期间内，投保人未按照与被保险人签订的贷款合同的约定履行还款义务，且拖欠任何一期欠款达到80天以上的，保险公司对投保人应偿还而未偿还的贷款本金及相应的利息按照本保险合同的规定承担理赔责任。</p>
		<p class="indent">无论贷款合同如何约定，保险公司认定投保人向被保险人贷款合同项下应还款顺序为：</p>
		<p class="indent">1、先偿还已逾期部分的欠款，再偿还未逾期欠款；</p>
		<p class="indent">2、对已逾期部分的欠款，按逾期情形发生的先后顺序依次偿还。</p>
		<p class="indent">（四）保险金额：保险合同的保险金额在保险单中予以载明。保险金额是保险人根据被保险人要求及贷款合同载明的本金及相应利息计算的金额。<b>实际承保的保险金额有可能小于投保人申请的贷款本息，实际贷款本金由被保险人审批决定，并以贷款合同载明为准。</b></p>
		<p class="indent">（五）保险服务流程：</p>
		<p class="indent">1、投保、承保办理流程：借款人按照保险公司客户经理的指引或本产品须知的介绍，通过阅读并点击确认投保相关文件的方式进行投保，并承担缴纳保险费的义务。保险公司依据投保相关文件以及经投保人授权查询的其他信息进行风险评估并出具电子保单信息，电子保单信息经投保人确认，保险公司出具电子保单后，双方即达成保险合同关系。</p>
		<p class="indent">2、理赔办理流程：投保人拖欠任何一期的应偿还贷款达到80天（不含）时，保险公司将依据保险合同约定对被保险人进行理赔。保险公司赔偿相关款项后，投保人即需向保险公司归还全部赔偿款项和应付未付保费及其他应付金额，保险公司有权进行追偿。</p>
		<p class="indent">保险赔款支付方式：被保险人向保险公司提供理赔申请信息且经保险公司确认理赔款金额无误的前提下，在投保人拖欠任何一期应偿还贷款达到80天（不含）后，由被保险人从保险公司理赔账户中划扣理赔款至被保险人指定收款账户。</p>
		<p class="indent">3、退保办理流程：在保险合同有效期内，未还清贷款本息前，投保人不得退保。</p>
		<p class="indent">投保人提前还清借款本息的，可申请退保。投保人凭保险单正本、被保险人提供的还清借款本金及利息的书面证明和退保申请书向保险人申请退保。</p>
		<p class="indent">符合退保条件的，保险人应根据已经过的保险期间实际天数按照投保时约定的保费缴纳方式计算应付保险费。应付保险费若高于已付保险费，投保人应补交其差额；若已付保险费高于应付保险费，保险人应退还其差额。</p>

		<!-- <div style="padding: 1rem 1.4rem">
			<table class="table_set">
				<tr class="tr_set">
					<td class="td_set60">保险期间已经过月份数占保险期间总月份数的比例</td>
					<td class="td_set40">应付保险费占总保险费的比例</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">0-1/12（含）</td>
					<td class="td_set40">12%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">1/12-1/6（含）</td>
					<td class="td_set40">18%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">1/6-1/4（含）</td>
					<td class="td_set40">24%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">1/4-1/3（含）</td>
					<td class="td_set40">32%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">1/3-5/12（含）</td>
					<td class="td_set40">40%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">5/12-1/2（含）</td>
					<td class="td_set40">48%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">1/2-7/12（含）</td>
					<td class="td_set40">56%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">7/12-2/3（含）</td>
					<td class="td_set40">64%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">2/3-3/4（含）</td>
					<td class="td_set40">72%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">3/4-5/6（含）</td>
					<td class="td_set40">80%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">5/6-11/12（含）</td>
					<td class="td_set40">90%</td>
				</tr>
				<tr class="tr_set">
					<td class="td_set60">11/12-1（含）</td>
					<td class="td_set40">100%</td>
				</tr>
				
			</table> 
		</div> -->
		
		<div>
			<p class="indent"><b>（六）主要责任免除条款</b></p>
			<p class="indent"><b>1、出现下列任一情形时，保险人不负责赔偿：</b></p>
			<p class="indent"><b>1）未经保险人同意，投保人、被保险人擅自变更个人借款合同，损害保险人利益的；</b></p>
			<p class="indent"><b>2）个人借款合同无效或被撤销。</b></p>
			<p class="indent"><b>2、下列原因造成的损失、费用，保险人不负责赔偿：</b></p>
			<p class="indent"><b>1）投保人、被保险人及其代表的故意行为,但投保人逾期或拒不偿还借款的行为除外；</b></p>
			<p class="indent"><b>2）战争、敌对行动、军事行为、武装冲突、罢工、骚乱、暴动、恐怖活动；</b></p>
			<p class="indent"><b>3）核辐射、核爆炸、核污染及其他放射性污染；</b></p>
			<p class="indent"><b>4）地震、海啸及其他人力不可抗拒的自然灾害；</b></p>
			<p class="indent"><b>5）投保人与被保险人恶意串通，损害保险人利益。</b></p>
			<p class="indent"><b>3、对于下列损失、费用，保险人不负责赔偿：</b></p>
			<p class="indent"><b>1）除借款本金、利息以外的任何费用；</b></p>
			<p class="indent"><b>2）按本保险合同中载明的免赔率计算的免赔金额。</b></p>
			<p class="indent"><b>4、其他不属于本保险合同责任范围内的损失和费用，保险人不负责赔偿。</b></p>
		</div>
		
		<p class="indent">（七）适用保险条款：本投保单未约定之处，以附件一《阳光财产保险股份有限公司个人借款保证保险条款（一年期）（2016版）》（以下简称“保险条款”）及保险单为准。</p>
		<p class="indent">（八）保险公司将严格遵守现行的关于个人信息、数据及隐私保护的法律法规，采取充分的技术手段和制度管理，保护投保人提供的个人信息、数据和隐私不受到非法的泄露或披露给非获授权的第三方。</p>
		<p class="indent">（九）本产品投保咨询电话：95510。本产品投诉电话：95510。</p>

		<h1 style="width: 100%; text-align: center; line-height: 2.8rem" class="font11 marginTop10">二、投保特别约定</h1>
		<p class="indent">（一）保险合同组成：投保须知为投保单的组成部分。电子投保单（电子投保单由投保单正文及附件保险条款以及投保须知组成）为保险合同的组成部分。电子投保单和电子保险单构成有效的保险合同。保险公司不另外签发纸质保险单。</p>
		<p class="indent">（二）保险合同的订立方式：投保须知、电子投保单经投保人在“阳光保呗”APP上点击确认之日起生效。保险公司有权自主决定是否承保。保险公司决定承保的，保险合同自保险公司出具并确认电子保险单之日起成立。</p>
		<p class="indent">（三）保险期间：保险合同成立后，保险期间自贷款发放之日起至贷款合同约定的全部贷款本息清偿之日止。保险期间最长不超过一年。</p>
		<p class="indent">（四）保险费及支付方式：（1）总保险费按如下公式计算：被保险人与投保人之间已签署的贷款合同中载明的本金金额×月保费本金比例×保险期间月份数；其中月保费本金比例详见保险单页面。（2）为了减轻客户缴纳保费的经济压力，并迎合投保人贷款合同按月期缴的还款方式，保险公司特与投保人约定保费的支付方式同为按月期缴，如投保人不同意此缴费方式，投保人应立即停止投保申请行为。保险费交费日期同贷款合同约定的每月还款日。投保人承诺并同意，保险合同生效后，在本投保单约定的每个保险费交费日期向保险公司支付保险费。投保人同意并授权保险公司通过合作的第三方支付机构及其合作银行从投保人指定账户划扣每月应缴保险费。</p>
		<p class="indent"><b>（五）违约金及催收</b></p>
		<p class="indent">1、投保人拖欠任何一期的应偿还贷款达到80天（不含），保险公司将依据保险合同约定对被保险人进行理赔。保险公司理赔相关款项后，投保人即需向保险公司归还全部理赔款项和应付未付保费。<b>从保险公司理赔当日，投保人未向保险公司归还全部款项，则需以投保人尚欠剩余本金为基数，从保险公司理赔当日开始计算，按每日千分之一向保险公司缴纳违约金。</b>应付未付保费是指投保人自贷款发放之日起至理赔之日止未支付的应缴保险费。</p>
		<p class="indent">2、保险公司按保险合同约定理赔后，保险公司有权根据《保险法》有关代位求偿权的相关规定，代位行使被保险人对投保人请求赔偿的权利。被保险人还应根据保险人的需要向其出具权益转让书。保险人通过前述方式所取得的求偿权利包括但不限于被保险人对投保人所享有的贷款债权和抵押权、质权等从权利。</p>
		<p class="indent">3、投保人发生逾期、提前还款，保险公司均有权催回应付而未付保费。投保人还款应按照保费、贷款方规定的相应费用、利息、本金的顺序进行；保险公司基于投保人拖欠任何一期的欠款达到80天（不含）而向被保险人理赔后，投保人同意保险公司向投保人催回应付而未付保费、理赔款项、违约金、理赔及催收产生的其他费用（包括但不限于诉讼费、保全费、公告费、咨询费、律师费等）。</p>
		<p class="indent"><b>（六）争议处理方式	</b></p>
		<p class="indent">因履行保险合同发生争议，由当事人协商解决，协商不成的，依法向北京市通州区人民法院起诉。</p>

		<h1 style="width: 100%; text-align: center; line-height: 2.8rem" class="font11 marginTop10">三、信息查询及使用授权条款</h1>
		<p class="indent">（一）投保人同意，保险公司有权批核或拒绝投保人之保险申请而无需提出任何理由及无须退回有关申请文件或文件副本予投保人。</p>
		<p class="indent">（二）投保人同意授权条款自本投保单签署之日起生效，至投保人在保险公司处全部保险业务结束之日止，其效力不受保险合同成立与否、效力状态的影响。</p>
		<p class="indent">（三）投保人同意保险公司将收集、获取的投保人相关信息披露给阳光集团（指阳光保险集团股份有限公司及其直接或间接持股的公司），投保人同意前述信息及投保人享受阳光集团金融服务产生的信息，可用于阳光集团及因服务必要而委托的第三方为投保人提供服务及推荐产品，法律禁止的除外。</p>

		<h1 style="width: 100%; text-align: center; line-height: 2.8rem" class="font11 marginTop10">四、其他</h1>
		<p class="indent">保险合同与法律、法规、监管政策不一致的，以法律、法规、监管政策和自律规定为准。保险合同生效后，新的法律、法规、监管政策或行业自律规定出台的，双方应及时按照新的法律、法规、监管政策或行业自律规定变更相关协议。</p>
		<p class="indent"><b>投保人承诺与声明：本人兹声明已阅读产品详细条款，并已对本投保单及所附保险条款，特别是责任免除条款、投保人义务条款、被保险人义务条款、特别约定条款、保险合同解除条款的内容进行阅读并完全了解。保险公司已对保险合同的条款内容履行了说明义务,并对免除保险公司责任的条款和投保人、被保险人义务的条款履行了明确说明义务，本人同意、接受并自愿进行投保，特此承诺与声明。</b></p>
		<p class="indent" style="margin-top: 3rem"><b>投保人:  <%=idCardInfoObj.getCUST_NAME() %> （签章处）<span id="name2"></span></b></p>
		<p class="indent" style="margin-top: 1rem"><b>日期: <%=yyyy %>年   <%=MM %> 月  <%=dd %>  日<span id="signDate"></span></b></p>

		
		<div>
			<h1 style="width: 100%; text-align: center; margin-top: 10rem" class="font11">附件一：阳光财产保险股份有限公司个人借款保证保险条款（一年期）（2016版）</h1>
			<h1 style="width: 100%; text-align: center;" class="font11 marginTop10">阳光财产保险股份有限公司<br/>个人借款保证保险条款（一年期）（2016版）</h1>

			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">总则</h1>
			<p class="indent"><b>第一条</b>&nbsp;保险合同由保险条款、投保单、保险单、保险凭证以及批单组成。凡涉及本保险合同的约定，均应采用书面形式。</p>
			<p class="indent"><b>第二条</b>&nbsp;凡年满18周岁，具有完全民事行为能力，个人信用记录良好，有稳定收入的中国（不含香港、澳门及台湾地区，下同）公民，可作为本保险合同的投保人。</p>
			<p class="indent"><b>第三条</b>&nbsp;凡符合以下条件的自然人和法人均可作为本保险合同的被保险人：</p>
			<p class="indent">（1）凡年满18周岁，具有完全民事行为能力,与投保人签署合法有效的借款合同并向该投保人出借资金的中国公民。</p>
			<p class="indent">（2）凡与投保人签署合法有效的借款合同或贷款合同（与前款借款合同合称为“个人借款合同”），向该投保人出借资金或提供贷款，依法设立且具备合法的出借资金或提供贷款资质的法人或其他社会团体、组织。</p>

			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">保险责任</h1>
			<p class="indent"><b>第四条</b>&nbsp;投保人未按照与被保险人签订的个人借款合同的约定履行还款义务，且投保人拖欠任何一期欠款达到保险单约定的期限以上的，保险人对投保人应偿还而未偿还的借款本金及相应的利息按照本保险合同的规定负责赔偿。</p>
			<p class="indent">无论个人贷款合同如何约定，保险人认定投保人的还款顺序为：</p>
			<p class="indent">（一）先偿还已逾期部分的欠款，再偿还未逾期欠款；</p>
			<p class="indent">（二）对已逾期部分的欠款，按逾期情形发生的先后顺序依次偿还。</p>
			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">责任免除</h1>
			<p class="indent"><b>第五条&nbsp;出现下列任一情形时，保险人不负责赔偿：</b></p>
			<p class="indent"><b>（一）未经保险人同意，投保人、被保险人擅自变更个人借款合同，损害保险人利益的；</b></p>
			<p class="indent"><b>（二）个人借款合同无效或被撤销。</b></p>
			<p class="indent"><b>第六条&nbsp;下列原因造成的损失、费用，保险人不负责赔偿：</b></p>
			<p class="indent"><b>（一）投保人、被保险人及其代表的故意行为,但投保人逾期或拒不偿还借款的行为除外；</b></p>
			<p class="indent"><b>（二）战争、敌对行动、军事行为、武装冲突、罢工、骚乱、暴动、恐怖活动；</b></p>
			<p class="indent"><b>（三）核辐射、核爆炸、核污染及其他放射性污染；</b></p>
			<p class="indent"><b>（四）地震、海啸及其他人力不可抗拒的自然灾害；</b></p>
			<p class="indent"><b>（五）投保人与被保险人恶意串通，损害保险人利益。</b></p>
			<p class="indent"><b>第七条&nbsp;对于下列损失、费用，保险人不负责赔偿：</b></p>
			<p class="indent"><b>（一）除借款本金、利息以外的任何费用；</b></p>
			<p class="indent"><b>（二）按本保险合同中载明的免赔率计算的免赔金额。</b></p>
			<p class="indent"><b>第八条&nbsp;其他不属于本保险合同责任范围内的损失和费用，保险人不负责赔偿。</b></p>
			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">保险金额与免赔率</h1>
			<p class="indent"><b>第九条</b>&nbsp;本保险合同的保险金额为投保时投保人与被保险人订立的个人借款合同项下应偿还的全部或部分借款本金及相应的利息之和（以下简称“借款本息”），具体金额在保险单上载明。</p>
			<p class="indent"><b>第十条&nbsp;本保险实行绝对免赔，免赔金额为保险事故发生时投保人所欠被保险人借款本息的一定比例，具体比例在保险单上载明。</b></p>
			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">保险期间</h1>
			<p class="indent"><b>第十一条</b>&nbsp;保险期间自个人借款合同项下借款发放之日起，至个人借款合同约定的、清偿全部借款本息之日止，以保险单载明起讫时间为准，但最长不超过一年。</p>
			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">保险费</h1>
			<p class="indent"><b>第十二条</b>&nbsp;本保险合同的保险费由保险人依照保险金额、保险期间及具体风险状况等确定并在保险单上载明。</p>
			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">保险人义务</h1>
			<p class="indent"><b>第十三条</b>&nbsp;订立保险合同时，采用保险人提供的格式条款的，保险人向投保人提供的投保单应当附格式条款，保险人应当向投保人说明保险合同的内容。对保险合同中免除保险人责任的条款，保险人在订立合同时应当在投保单、保险单或者其他保险凭证上作出足以引起投保人注意的提示，并对该条款的内容以书面或者口头形式向投保人作出明确说明；未作提示或者明确说明的，该条款不产生效力。</p>
			<p class="indent"><b>第十四条</b>&nbsp;本保险合同成立后，保险人应当及时向投保人签发保险单或其他保险凭证。</p>
			<p class="indent"><b>第十五条</b>&nbsp;保险人按照本条款第二十六条的约定，认为被保险人提供的有关索赔的证明和资料不完整的，应当及时一次性通知投保人、被保险人补充提供。</p>
			<p class="indent"><b>第十六条</b>&nbsp;保险人收到被保险人的赔偿保险金的请求后，应当及时作出是否属于保险责任的核定；情形复杂的，保险人将在确定是否属于保险责任的基本材料收集齐全后，尽快做出核定。</p>
			<p class="indent">保险人应当将核定结果通知被保险人；对属于保险责任的，在与被保险人达成赔偿保险金的协议后十日内，履行赔偿保险金义务。保险合同对赔偿保险金的期限有约定的，保险人应当按照约定履行赔偿保险金的义务。</p>
			<p class="indent">保险人依照前款的规定作出核定后，对不属于保险责任的，应当自作出核定之日起三日内向被保险人发出拒绝赔偿保险金通知书，并说明理由。</p>
			<p class="indent"><b>第十七条</b>&nbsp;保险人自收到赔偿保险金的请求和有关证明、资料之日起六十日内，对其赔偿保险金的数额不能确定的，应当根据已有证明和资料可以确定的数额先予支付；保险人最终确定赔偿的数额后，应当支付相应的差额。</p>
			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">投保人、被保险人义务</h1>
			<p class="indent"><b>第十八条</b>&nbsp;订立保险合同时及本保险期间内，投保人应承担以下义务，包含但不限于：</p>
			<p class="indent">（一）投保人对本保险合同订立过程中所提供材料的真实性、准确性、完整性负责；</p>
			<p class="indent">（二）自觉接受保险人对本保险合同项下相关贷款使用情况的调查、了解和监督；</p>
			<p class="indent">（三）变更住所、通讯地址、联系方式，工作单位等事项的，应在有关事项变更后七日内通知保险人。</p>
			<p class="indent">投保人违反上述义务的，应按照本保险合同其他条款及保险法相关规定承担违约责任。</p>
			<p class="indent"><b>第十九条</b>&nbsp;订立本保险合同，保险人就投保人的有关情况提出询问的，投保人应当如实告知。</p>
			<p class="indent"><b>投保人故意或者因重大过失未履行前款规定的如实告知义务，足以影响保险人决定是否同意承保或者提高保险费率的，保险人有权解除保险合同。</b></p>
			<p class="indent">前款规定的合同解除权，自保险人知道有解除事由之日起，超过三十日不行使而消灭。自合同成立之日起超过二年的，保险人不得解除合同；发生保险事故的，保险人应当承担赔偿保险金的责任。</p>
			<p class="indent"><b>投保人故意不履行如实告知义务的，保险人对于合同解除前发生的保险事故，不承担赔偿保险金的责任，并不退还保险费。</b></p>
			<p class="indent"><b>投保人因重大过失未履行如实告知义务，对保险事故的发生有严重影响的，保险人对于合同解除前发生的保险事故，不承担赔偿保险金的责任，但应当退还保险费。</b></p>
			<p class="indent">保险人在本保险合同订立时已经知道投保人未如实告知的情况的，保险人不得解除合同；发生保险事故的，保险人应当承担赔偿保险金的责任。</p>
			<p class="indent">投保人应按照个人借款合同约定的用途使用借款，不得挪用借款从事股本权益性投资、房地产投机等国家明令禁止或限制的活动。</p>
			<p class="indent"><b>第二十条</b>&nbsp;申请投保时，投保人应如实填写投保单，提供保险人要求的必要证明资料，并接受被保险人及保险人对其资信进行审查。被保险人应按有关法律法规和借贷管理规定，严格审查投保人的资信情况，发放贷款。</p>
			<p class="indent"><b>第二十一条</b>&nbsp;除另有约定外，投保人应当在约定交费日支付当期保险费。<b>投保人未按约定交付保险费的，对于保险费交付前发生的保险事故，保险人不承担赔偿责任。</b></p>
			<p class="indent"><b>第二十二条</b>&nbsp;被保险人应及时检查个人贷款合同的执行情况，做好欠款的催收工作和催收记录，如出现投保人未按个人贷款合同约定履行任何一期本息偿还义务的情况，应最迟于四十八小时内通知保险人，并开展催收工作。如被保险人发现投保人可能存在不还款风险，或有任何导致本保险标的危险程度显著增加的情况，应在四十八小时内通知保险人。<b>被保险人未履行前述通知义务，因保险标的危险程度显著增加而发生的保险事故，保险人不负责赔偿。</b></p>
			<p class="indent">被保险人应接受保险人的催收建议，并出具书面授权委托，授权委托事项包括但不限于授权保险人协助催收欠款、宣布贷款提前到期、授权保险人协助收回欠款等。<b>被保险人拒绝委托保险人协助催收的，对因此增加的保险责任，保险人不负责赔偿。</b></p>
			<p class="indent"><b>第二十三条</b>&nbsp;如果投保人与被保险人就个人贷款合同签订任何抵（质）押合同的，应依法办理抵（质）押登记手续。投保人在保险期间，未经保险人书面同意，不得将抵（质）押合同约定的抵（质）押物进行转卖、转让、转赠、重复或再次办理抵（质）押。</p>
			<p class="indent"><b>投保人未按约定履行前述义务的损害保险人利益的，发生保险事故，保险人不承担赔偿责任。</b></p>
			<p class="indent"><b>第二十四</b>&nbsp;条被保险人与投保人所签订的个人贷款合同如有变更或解除，须事先征得保险人的书面同意。<b>未经保险人书面同意，如因合同变更导致保险标的危险程度增加或损害保险人利益的，保险人不承担相应的赔偿责任。</b></p>
			<p class="indent"><b>第二十五条</b>&nbsp;知道保险事故发生后：</p>
			<p class="indent">（一）被保险人应尽力采取必要、合理的措施，包括但不限于积极开展催收工作、行使担保权利，防止或减少损失，<b>否则，对因此扩大的损失，保险人不承担赔偿责任；</b></p>
			<p class="indent">（二）投保人、被保险人应及时通知保险人，并书面说明事故发生的原因、经过和损失情况；<b>故意或者因重大过失未及时通知，致使保险事故的性质、原因、损失程度等难以确定的，保险人对无法确定的部分，不承担赔偿保险金的责任，</b>但保险人通过其他途径已经及时知道或者应当及时知道保险事故发生的除外。</p>
			<p class="indent"><b>第二十六条</b>&nbsp;被保险人请求赔偿时，应向保险人提供下列证明和资料：</p>
			<p class="indent">（一）保险单正本；</p>
			<p class="indent">（二）被保险人或其代表填具的索赔申请书；</p>
			<p class="indent">（三）个人贷款合同正本；</p>
			<p class="indent">（四）投保人的资信调查记录、还款记录及凭证；</p>
			<p class="indent">（五）逾期还款催收通知书副本以及其他催收文件副本；</p>
			<p class="indent">（六）投保人、被保险人所能提供的与确认保险事故的性质、原因、损失程度等有关的其他证明和资料。</p>
			<p class="indent"><b>被保险人未履行前款约定的索赔材料提供义务，导致保险人无法核实损失情况的，保险人对无法核实的部分不承担赔偿责任。</b></p>
			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">赔偿处理</h1>
			<p class="indent"><b>第二十七条</b>&nbsp;被保险人已向投保人提起诉讼的，索赔时应向保险人提供起诉状，如诉讼已终结，应提供相应的判决书、裁定书、调解书及其他法律文书。</p>
			<p class="indent"><b>第二十八条</b>&nbsp;被保险人与投保人就个人贷款合同发生的争议需进行和解、调解的，应先行征得保险人的书面同意。</p>
			<p class="indent"><b>第二十九条</b>&nbsp;保险事故发生时，如果存在重复保险，保险人按照本保险合同的相应保险金额与其他保险合同及本保险合同相应保险金额总和的比例承担赔偿责任。</p>
			<p class="indent"><b>其他保险人应承担的赔偿金额，本保险人不负责垫付。</b>若被保险人未如实告知导致保险人多支付赔偿金的，保险人有权向被保险人追回多支付的部分。</p>
			<p class="indent"><b>第三十条</b>&nbsp;发生保险责任范围内的损失，保险人自向被保险人赔偿保险金之日起，在赔偿金额范围内代位行使被保险人对投保人请求赔偿的权利，被保险人应当向保险人提供必要的文件和所知道的有关情况。</p>
			<p class="indent">被保险人已经从投保人处取得赔偿的，保险人赔偿保险金时，可以相应扣减被保险人已从投保人处取得的赔偿金额。</p>
			<p class="indent"><b>保险事故发生后，在保险人未赔偿保险金之前，被保险人放弃对投保人请求赔偿权利的，保险人不承担赔偿责任；</b>保险人向被保险人赔偿保险金后，被保险人未经保险人同意放弃对投保人请求赔偿权利的，该行为无效；由于被保险人故意或者因重大过失致使保险人不能行使代位请求赔偿的权利的，保险人可以扣减或者要求返还相应的保险金。</p>
			<p class="indent"><b>第三十一条</b>&nbsp;保险人受理报案、参与案件诉讼、向被保险人提供建议等行为，均不构成保险人对赔偿责任的承诺。</p>
			<p class="indent"><b>第三十二条</b>&nbsp;被保险人向保险人请求赔偿保险金的诉讼时效期间为二年，自其知道或者应当知道保险事故发生之日起计算。</p>
			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">争议处理和法律适用</h1>
			<p class="indent"><b>第三十三条</b>&nbsp;在履行本保险合同过程中所发生的争议，应先由当事人协商解决；协商不成的，则提交保险单载明的仲裁机构仲裁；保险单未载明仲裁机构且争议发生后未达成仲裁协议的，依法向人民法院起诉。</p>
			<p class="indent"><b>第三十四条</b>&nbsp;与本保险合同有关的以及履行本保险合同产生的一切争议，适用于中华人民共和国法律（不包括港澳台地区法律）。</p>
			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">其他事项</h1>
			<p class="indent"><b>第三十五条</b>&nbsp;在保险合同有效期内，未还清借款本息前，投保人不得退保。</p>
			<p class="indent">投保人提前还清借款本息的，可申请退保。投保人凭保险单正本、被保险人提供的还清借款本金及利息的书面证明和退保申请书向保险人申请退保。</p>
			<p class="indent">符合退保条件的，保险人应根据已经过的保险期间实际天数按照投保时约定的保费缴纳方式计算应付保险费。保费缴纳方式由保险人和投保人在投保时从以下三种方式中选择，并在保单中约定：</p>
			<p class="indent">方式一：根据保险期间实际天数按照年保险费的日比例标准计算应付保险费。</p>
			<p class="indent">方式二：根据保险期间实际月数按照年保险费的月比例标准计算应付保险费（不足一个月的按一个月计算）。</p>
			<p class="indent">方式三：按以下标准计算应付保险费（按年保险费的百分比计算，不足一个月的按一个月计算）。</p>
			
			<div style="padding: 1rem 1.4rem">
				<table class="table_set">
					<tr class="tr_set">
						<td class="td_set60">保险期间</td>
						<td class="td_set40">年保险费的百分比</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">一个月</td>
						<td class="td_set40">10%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">二个月</td>
						<td class="td_set40">20%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">三个月</td>
						<td class="td_set40">30%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">四个月</td>
						<td class="td_set40">40%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">五个月</td>
						<td class="td_set40">50%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">六个月</td>
						<td class="td_set40">60%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">七个月</td>
						<td class="td_set40">70%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">八个月</td>
						<td class="td_set40">80%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">九个月</td>
						<td class="td_set40">85%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">十个月</td>
						<td class="td_set40">90%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">十一个月</td>
						<td class="td_set40">95%</td>
					</tr>
					<tr class="tr_set">
						<td class="td_set60">十二个月</td>
						<td class="td_set40">100%</td>
					</tr>
				
				</table>
			</div>
			<p class="indent">应付保险费若高于已付保险费，投保人应补交其差额；若已付保险费高于应付保险费，保险人应退还其差额。</p>
			
			<h1 style="width: 100%; text-align: center; line-height: 1.6rem" class="font8 marginTop10">释义</h1>
			<p class="indent">【个人借款】</p>
			<p class="indent">依法设立且具备合法出借资金或提供贷款资质的法人或其他社会团体、组织或个人向个人提供抵质押或信用贷款或借款。</p>
			<p class="indent">【利息】</p>
			<p class="indent" style="margin-bottom: 10rem">投保人和被保险人签订的个人借款合同中约定的符合相关法律规定的被保险人的全部应收利息，包括但不限于法定利息、约定利息、罚息、复利等。</p>

			
		</div>

		

	</div>

	<footer style="width: 100%; position: fixed; bottom: 0">
		<div style="width: 100%; height: 20px;" class="linear"></div>

		<div style="width: 100%; background: #fff; padding: 1rem 10%;">
			<p style="width: 80%; margin-bottom: 1rem" class="font8"><input id="protocol_status" type="checkbox" checked="true" name="check_protocol" style="vertical-align:text-bottom; margin-bottom:1px; margin-right: 4px">我已阅读并同意<a href="personal_inquiries3-3.html" class="href_style">《个人征信授权书》</a>和<a href="personal_inquiries_use3-4.html" class="href_style">《个人信息查询使用授权书》</a></p>

			<p onclick="ensurePro()" id="click_agree" style="width: 80%; line-height: 3rem;background-color: #4C7EE9; border-radius: 4px; text-align: center; color: #fff" class="font12">确认投保</p>
		</div>
	
	</footer>
	

</body>

<script type="text/javascript">
	function connectWebViewJavascriptBridge(callback) {
            //android使用
            if (window.WebViewJavascriptBridge) {
                callback(WebViewJavascriptBridge)
            } else {
                document.addEventListener(
                    'WebViewJavascriptBridgeReady'
                    , function() {
                        callback(WebViewJavascriptBridge)
                    },
                    false
                );
            }

            //iOS使用
            if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
            if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
            window.WVJBCallbacks = [callback];
            var WVJBIframe = document.createElement('iframe');
            WVJBIframe.style.display = 'none';
            WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
            document.documentElement.appendChild(WVJBIframe);
            setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)

        }

    //必须要用，在里面register事件
    connectWebViewJavascriptBridge(function(bridge) {

    });

    function ensurePro(){
    	if($("#protocol_status").is(":checked")){
					//点击跳转，，，
				 	// $(location).attr('href', 'personal_loan_surety_ins3-1.html');  
				window.WebViewJavascriptBridge.callHandler(
                	'YGH5CallNative'
                	, {'pageName': '授信预投保保单确认',
                		'func': 'personalLoan_click',
                		'params': null}
                		, function(responseData) {
         			}
            		);
		}else{
			alert("请阅读并同意授权协议");
		}
    }



</script>

</html>