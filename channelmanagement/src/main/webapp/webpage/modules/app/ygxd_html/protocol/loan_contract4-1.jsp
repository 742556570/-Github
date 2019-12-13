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
	<title>贷款合同</title>
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
		.tdStyle{
			line-height: 1.6rem;
			font: 0.8rem;
		}
	</style>
</head>
<body>
	<div class="font8 padding10">
		<h1 style="text-align: center;" class="font14">贷款合同 </h1>

		<p class="indent" style="width: 100%; text-align: right;">合同编号：<span><%=applyObj.getPolicyNo() %> </span></p>
		<p class="indent">本贷款合同由以下各方于<span><%=yyyy %></span>年<span><%=MM %></span>月<span><%=dd %></span>日于中华人民共和国北京市通州区签署：</p>


		<div style="margin-top: 0.6rem">
			<p class="lh16"><b>甲方：<span></span></b></p>
			<p class="lh16">借款人：<span><%=idCardInfoObj.getCUST_NAME() %></span></p>
			<p class="lh16">身份证号码：<span><%=idCardInfoObj.getID_NO() %></span></p>
			<p class="lh16">移动电话：<span><%=usrObj.getUSR_TEL() %></span></p>
			<p class="lh16">固定电话：<span></span></p>
			<p class="lh16">电子邮箱：<span></span></p>
			<p class="lh16">地址：<span><%=idCardInfoObj.getLIVE_ADDR() %></span></p>
		</div>

		<div style="margin-top: 0.6rem">
			<p class="lh16"><b>乙方（贷款人）：中国对外经济贸易信托有限公司</b></p>
			<p class="lh16">公司地址：北京市西城区复兴门内大街28号凯晨世贸中心中座</p>
			<p class="lh16">联系电话：010-56286222</p>
		</div>

		<div class="marginTop10">
			<p class="lh16"><b>第一条 贷款要素</b></p>
			<p class="indent">1.本合同项下贷款<b>仅限用于甲方个人消费或生产经营，具体为：个人消费/生产经营；</b></p>
			<p class="indent">2.<b>未经乙方书面同意，甲方不得擅自改变贷款用途，贷款不得用于购房支出、不得用于认购和买卖股票或其它权益性投资、不得发放P2P平台贷款或受让P2P平台贷款债权；贷款用途不得违反法律、行政法规、规章、规范性文件及司法解释等法律文件的禁止性或限制性规定；</b></p>
			<p class="indent">3.贷款金额为￥<span class="underLine666"><%=applyObj.getApplyAmt()%></span>元（人民币大写<span class="underLine666"><%=applyAmt %></span>元）；</p>
			<p class="indent">4.贷款利率为：  <%=new BigDecimal(applyObj.getIntRate()).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) %> %/年 ；</p>
			<p class="indent">5.还款起止日期：自贷款起始日，【   <%=applyObj.getApplyTnr()%>】个自然月；</p>
			<p class="indent">6.还款方式为</p>
			<p class="indent">□到期一次性还清贷款本金及利息</p>
			<p class="indent">□等额本息；</p>
			<p class="indent">□分期还款方式，还款分期期数为【 <%=applyObj.getApplyTnr()%> 】期，具体以乙方或乙方指定机构向甲方发送的还款通知为准（有效通知方式包括但不限于电话、短信、传真、信件、电子邮件、微信、QQ、APP通知等）。</p>
			<p class="indent">7.还款日为：第（ 2 ）种</p>
			<p class="indent">（1）固定日还款：每月   日/贷款发放之日起第   日/<b>（如遇法定节假日，不顺延）</b></p>
			<p class="indent">（2）非固定日还款：以放款后乙方或乙方指定机构向甲方发送的还款通知为准。</p>
			<p class="indent">8.（1）甲方专用账户（接受贷款账户暨还款账户）信息：</p>
			<p class="indent" style="text-indent: 3rem;">户  名：<%=clBnkCrdObj.getAPPL_AC_NAM()%></p>
			<p class="indent" style="text-indent: 3rem;">开户行：<%=clBnkCrdObj.getAPPL_AC_BANK()%></p>
			<p class="indent" style="text-indent: 3rem;">账  号：<%=clBnkCrdObj.getAPPL_CARD_NO()%></p>
			<p class="indent">&nbsp;&nbsp;&nbsp;（2）甲方消费商户账户</p>
			<p class="indent" style="text-indent: 3rem;">户  名：</p>
			<p class="indent" style="text-indent: 3rem;">开户行：</p>
			<p class="indent" style="text-indent: 3rem;">账  号：</p>
		</div>

		<div class="marginTop10">
			<p class="lh16"><b>第二条 贷款发放</b></p>
			
			<table>
				<tr>
					<td style="width: 2rem; vertical-align: top; padding-top: 0.3rem" class="font8">1.</td>
					<td class="tdStyle">本合同项下贷款期限为<span class="underLine666"><%=applyObj.getApplyTnr()%></span>个自然月：<br/>
					<span class="color_href">【适用于贷款发放至借款人账户】：</span>贷款起始日以贷款本金划离乙方账户之日为准，贷款本金划出即视为乙方的贷款义务履行完毕，划出日即为该笔贷款的实际放款日并开始计算该笔贷款的利息，除因乙方过错外，错划、无法划入甲方专用账户的法律后果均由甲方承担，且不影响甲方履行本合同项下的所有义务。
					<span class="color_href">【适用于受托支付】</span>贷款起始日以贷款本金划离乙方账户之日为准，贷款本金划出即视为乙方的贷款义务履行完毕，甲方签署本合同日期开始计算该笔贷款的利息，除因乙方过错外，错划、无法划入甲方专用账户的法律后果均由甲方承担，且不影响甲方履行本合同项下的所有义务。</td>
				</tr>

				<tr>
					<td style="width: 2rem; vertical-align: top; padding-top: 0.3rem" class="font8">2.</td>
					<td class="tdStyle">乙方自主发放或委托第三方支付机构/银行以资金代付的方式将本合同项下的贷款本金数额从乙方账户划付至如下第(  二  )方案的账户中:<br/> <span class="color_href">第一方案   甲方专用银行账户，详见本合同第一条。</span><br/><span class="color_href">第二方案   甲方消费商户账户，详见本合同第一条。</span></td>
				</tr>
			</table>
		</div>

		<div class="marginTop10">
			<p class="lh16"><b>第三条 还款</b></p>
			<p class="lh16"><b>1.还款方式：</b></p>
			<p class="lh16">（1）甲方应按照本合同第一条确定的还款方式及还款期限按期偿还贷款本金及利息。</p>
			<p class="lh16">（2）若甲方按照本合同约定足额、按期偿还贷款本息的，则甲方在本合同项下的债务总负担不超过放款后乙方或乙方指定机构向甲方发送的还款通知所载应还总金额；但若甲方未按照本合同约定足额、按期偿还贷款本息的，除须依据本合同约定继续履行还款义务外，还须依据本合同约定，支付相关逾期违约金、提前还款违约金等。</p>
			<p class="lh16"><b>2.扣款授权及乙方收款账户：</b></p>
			<p class="lh16">（1）甲方必须在每期还款日/到期一次性还本付息日（当日[  09:30  ]以前）或之前将当期/到期一次性应偿还本息数额存入甲方专用账户中；</p>
			<p class="lh16"><b>（2）甲方不可撤销地授权乙方每月通过乙方委托的第三方支付机构/银行每月/到期日一次性从甲方本合同第一条约定的甲方专用账户或甲方以书面形式通知乙方并经乙方同意后变更的还款账户中，将甲方当月/到期一次性应当偿还的贷款本息、逾期违约金（如有）、提前还款违约金（如有）及其他本合同项下甲方应付款项直接扣除并划付至乙方在本合同约定的以下乙方账户中：</b></p>
			<p class="indent">户  名：中国对外经济贸易信托有限公司    </p>
			<p class="indent">开户行：交通银行股份有限公司北京市分行中关村园区支行</p>
			<p class="indent">账  号：110061241018010178701</p>
			<p class="lh16"><b>3.还款日确定原则：</b>乙方将以从甲方专用账户（还款账户）成功扣划还款金额的时间为准记录甲方的还款时间。如果还款日遇到法定节假日或公休日，还款日期不进行顺延,还款日的确定原则遵照以下方案：贷款发放日次月对应日为还款日。如果遇到天数不足的月份，还款日为应还款当月的最后一日。</p>
			<p class="lh16"><b>4.提前还款：</b>贷款存续期间，甲方可要求提前偿还全额贷款本金，甲方除须向乙方支付截止至提前还款日（含）应还未还的本金、利息、逾期违约金（如有）以及剩余未还本金外，还须按剩余未还本金的 3% 向乙方支付提前还款违约金。</p>
			<p class="lh16">贷款存续期间，甲方不可要求提前偿还部分贷款本金。    </p>
			<p class="lh16"><b>5.贷款展期及其限制：</b>本贷款不接受展期。</p>
			<p class="lh16"><b>6.误还款处理：</b>无论由于任何原因导致发生错误还款、多还款(即将不应支付乙方账户内的款项支付至乙方账户),甲方均认可由乙方委托的第三方合作机构代为处理错误还款、多还款的退款相关事宜。</p>
		</div>

		<div class="marginTop10">
			<p class="indent"><b>第四条 双方的权利和义务</b></p>
			<p class="indent"><b>（一）甲方的权利和义务</b></p>
			<p class="indent"><b>1.甲方按本合同约定的用途使用贷款，未经乙方书面同意，甲方不得擅自改变贷款用途。甲方申请的贷款不得用于购房支出、不得用于认购和买卖股票或其它权益性投资、不得发放P2P平台贷款或受让P2P平台贷款债权，贷款用途不得违反法律、行政法规、部门规章、规范性文件及司法解释等法律文件的禁止性或限制性规定；</b></p>
			<p class="indent">2.甲方应按本合同的约定按期、足额偿还贷款本息；</p>
			<p class="indent">3.未经乙方书面同意，甲方不得将本合同项下的任何权利、义务转让给他人；</p>
			<p class="indent">4.甲方在向乙方申请贷款的过程中，应当按照乙方及其委托的第三方要求提供相关的个人信息和材料，并保证该等材料的真实、完整、准确和有效，不得隐瞒或夸大；</p>
			<p class="indent">5.甲方有义务接受乙方及其委托的第三方对甲方个人信用资料及贷款使用情况的监督检查，包括但不限于乙方在贷款发放前对甲方身份真实性、贷款需求及用途等进行电话核实，以及在贷款发放后对甲方贷款使用情况及贷款用途真实性进行电话或现场抽查等，甲方应当全力配合并按照要求提供相关的个人信息、材料；</p>
			<p class="indent"><b>6.贷款期限内，甲方不得随意变更第一条所列甲方专用银行账户。若确需变更的，应当提前至少3个工作日将变更后的专用银行账户信息通知乙方及乙方委托的第三方，否则该变更对乙方不发生法律效力。因未及时通知造成的全部损失，均由甲方自行承担并负责进行赔偿。</b></p>
			<p class="indent">7.甲方在申请贷款时需提交其个人信用报告，或同意乙方或乙方授权的第三方查询其信用报告，了解其信用状况；</p>
			<p class="indent">8.甲方应当根据《中华人民共和国银行法》、《中华人民共和国反洗钱法》、《金融机构反洗钱规定》等相关法律法规的规定，配合乙方履行反洗钱义务，提供反洗钱监控所需的必要信息等。</p>

			<p class="indent"><b>（二）乙方的权利和义务</b></p>
			<p class="indent">1.乙方有权了解甲方对贷款的使用情况，包括但不限于乙方在贷款发放前对甲方身份真实性、贷款需求及用途等进行电话核实，以及在贷款发放后对甲方贷款使用情况及贷款用途真实性进行电话或现场抽查等，甲方应当无条件配合；</p>
			<p class="indent">2.甲方未按照本合同的约定履行还本付息义务及本合同项下其他应由甲方履行的付款义务的，乙方有权自行或委托第三方在法律法规允许的范围内采取相应措施进行催收和追讨；</p>
			<p class="indent">3.乙方有权将其享有的对甲方的债权转让给任何第三方。乙方决定转让债权的，无需事先取得甲方的同意；转让完成后由乙方委托的第三方代为通知甲方债权转让事宜，甲方应按本合同的约定向债权受让方继续履行本合同项下包括偿还贷款本金和利息在内的各项甲方义务；</p>
			<p class="indent">4.甲方未按照乙方的要求提供各项与本贷款合同相关的信息、材料或者提供的信息、材料存在虚假、不准确、隐瞒有关事实等情况的，或者甲方逾期还款的，乙方及其委托的第三方有权公开甲方的违约失信情形。</p>
			<p class="indent"><b>5.乙方或其委托第三方有权要求甲方提供贷款审查所需的全部资料（包括甲方个人征信信息），乙方除可将前述资料用于评估甲方贷款资格外，并可将前述资料（包括甲方个人征信信息）提供给征信机构。乙方或其委托第三方有权主动收集甲方资料，对甲方进行信用调查，包括但不限于向征信机构、身份信息系统等合法机构查询甲方的信用信息（具体以本合同附件《个人信息授权书》约定为准）；</b></p>
			<p class="indent"><b>6.甲方同意并授权乙方或其委托第三方按照中国法律法规、金融监管机构或征信机构的要求：<br/>（1）将有关本合同的信息和甲方提供的其他信息，录入银行间信贷咨询系统、征信信息系统或其他合法设立的信息库，包括但不限于不可撤销地授权乙方向中国人民银行征信中心（下称“征信中心”）、以及任何其他合法设立的征信机构报送乙方的个人信用信息（包括但不限于申请信息和交易信息），并同意征信中心以及任何其他合法设立的征信机构将乙方的个人信用信息纳入到征信中心运营的金融信用信息基础数据库（下称“信用信息数据库”）、以及任何其他合法设立的征信机构运营的信用信息系统之中，供具有适当资格的单位/个人查询和使用，并用于相关法律、法规、规章和规范性文件规定的用途和本合同约定的用途；<br/>（2）向征信中心的信用信息数据库以及任何其他合法设立的征信机构运营的信用信息系统查询甲方的个人信用信息、信用报告和信用评价。</b></p>
			<p class="indent"><b>7.当甲方发生本合同项下的违约时，乙方或其委托第三方有权根据甲方的违约情况决定公开甲方的违约信息，并且甲方在此不可撤销地同意并授权乙方或其委托第三方将有关本合同的信息、甲方提供的其他信息、甲方个人信用信息、信用报告和信用评价披露给有关的催收机构、保险机构、银行、其它金融机构、以及任何合法设立的征信机构、以及律师事务所。</b></p>
		</div>

		<div class="marginTop10">
			<p class="indent"><b>第五条 违约责任</b></p>
			<p class="indent">1．甲方任何违反法定义务及违反本合同约定义务的行为，均属违约行为，甲方应承担相应违约责任，包括但不限于以下任一情形：</p>
			<p class="indent">（1）甲方未能按期、足额偿还贷款本息（包括乙方根据本合同宣布贷款提前到期甲方未能一次性提前清偿所有应付款项的）；</p>
			<p class="indent">（2）甲方违反诚实信用原则取得贷款或甲方违反本合同约定的贷款用途或将贷款用于法律法规禁止或限制的领域或用途的；</p>
			<p class="indent">（3）甲方提供的信息、材料虚假、不准确或故意隐瞒身份、联系方式、资产状况等重要事实；甲方拒绝或不配合乙方及其委托第三方对甲方个贷款使用情况进行监督检查；</p>
			<p class="indent">（4）本合同项下贷款期限虽未届满，但是甲方明示或者以其实际行为向乙方表明不能按期履行还款义务；</p>
			<p class="indent"> (5) 有迹象表明甲方采取欺诈或其他手段套取本合同项下贷款，或甲方恶意逃避本合同债务的；</p>
			<p class="indent">（6）甲方涉及或可能涉及诉讼、仲裁、刑事案件或其他纠纷，或者受到行政部门的处罚、处理决定，影响或可能影响本合同履行的；甲方的任何财产被扣押、被查封或者被没收或者存在上述威胁、风险的；甲方的全部或者部分财产遭受或者可能遭受没收、征用、征收或者其他毁损灭失风险的；</p>
			<p class="indent">（7）甲方死亡（被宣告死亡）、失踪（被宣告失踪）、因民事权利能力、行为能力丧失或受限制等原因而丧失履行本合同债务能力，且无继承人、受遗赠人、监护人或财产代管人，或其继承人或受遗赠人、监护人或财产代管人拒绝代甲方履行本合同项下义务的； </p>
			<p class="indent">（8）甲方存在其他可能无法正常还款的情况（包括但不限于甲方财务状况恶化、无法联系、注销还款账户、账户被冻结、拒绝承认欠款等情形）；</p>
			<p class="indent">（9）甲方违反本合同其他约定。</p>
			<p class="indent">2．违约救济措施：</p>
			<p class="indent">甲方如出现违约的情况，乙方可以对甲方采取以下一项或多项措施追究其违约责任：</p>
			<p class="indent">（1）乙方有权停止发放贷款、宣布贷款提前到期以提前实现债权、要求甲方立即归还贷款本息、逾期违约金（如有）、提前还款违约金（如有）及其他本合同项下甲方应付款项；</p>
			<p class="indent">（2）甲方出现第五条第1款（1）项规定的违约情形，未能按时、足额偿还贷款本息的，乙方有权按照下列条款的约定行使权利：甲方按下述方案计算并向乙方支付<b>逾期违约金，</b>逾期违约金率为2%/月。</p>
			<p class="indent">逾期违约金 =逾期本息*逾期违约金率/30*逾期天数+逾期本金*月利率/30*逾期天数</p>
			<p class="indent">（4）<b>提前到期：</b></p>
			<p class="indent">1）甲方发生第五条第1款除（1）项外其他违约情形的，乙方有权宣布贷款提前到期，甲方应当在乙方向甲方发出贷款提前到期的通知后3个自然日内一次性偿还本合同项下贷款的全部本息、逾期违约金及因此给乙方造成的全部损失（包括但不限于因收回债权而产生的律师费、诉讼费、仲裁费、执行费、交通费、差旅费等费用）。</p>
			<p class="indent">2）甲方偿还金额不足时，偿还的先后顺序为按下述方案计算并向乙方支付逾期违约金：为实现债权而产生的费用、逾期违约金、提前还款违约金、应还利息、本金。</p>
			<p class="indent"><b>3.甲方同意，乙方可将甲方违约的相关信息予以公开披露。如甲方存在涉嫌欺诈等犯罪行为，乙方有权向司法机关报案，以追究其刑事责任。</b></p>
		</div>

		<div class="marginTop10">
			<p class="indent"><b>第六条 通知</b></p>
			<p class="indent">1．甲方变更通知</p>
			<p class="indent">自本合同签订之日起至本合同项下贷款本息全部得到清偿之日止，甲方下列信息发生变更后的3个自然日内，应当以有效方式通知乙方或其委托的第三方：甲方本人、其家庭联系人及其紧急联系人的工作单位、居住地址、住址电话、手机号码、传真号码、电子邮箱等发生变更；有效通知方式包括但不限于电话、短信、传真、信件、电子邮件、微信、QQ、APP通知。因甲方未能及时向乙方变更上述信息而产生的相关费用及损失均由甲方负责承担。</p>
			<p class="indent">2．乙方通知</p>
			<p class="indent">（1）乙方需将本合同项下有关贷款确认、提前还款等内容通知甲方的，由其委托的第三方代为执行，有效通知方式包括但不限于电话、短信、传真、信件、电子邮件、微信、QQ、APP通知等。</p>
			<p class="indent">（2）乙方的通知由专人送达的，以签收之日视为送达，以快递形式发送的，以快递发出单据上所示时间后4个自然日视为送达；通过挂号信邮递的，以国内挂号函件收据所示日后7个自然日视为送达；以传真、电子邮件、短信、微信消息、QQ消息、APP消息等方式通知的，以发送之日视为送达日；一经送达乙方即完成通知义务，甲方不得以未收到通知为不履行合同义务的抗辩理由。</p>
			<p class="indent">（3）法院、仲裁机关、行政机关处理案件需要需通知甲方的，适用上述通知条款。</p>
		</div>

		<div class="marginTop10">
			<p class="indent"><b>第七条 债权转让</b></p>
			<p class="indent">1.乙方有权根据自己的意愿，将其在本合同项下享有的对甲方的债权进行转让，乙方决定转让债权的，无需事先取得甲方的同意；乙方有权委托第三方（包括但不限于债权受让方）代为通知甲方债权转让事宜。在乙方的债权转让后，甲方需对债权受让人继续履行本合同项下包括偿还贷款本金和利息在内的还款义务及其他甲方义务。</p>
			<p class="indent"><b>2.债权转让后，甲方不可撤销的同意授权新的债权受让人及其委托第三方对其相应的还款账户进行划扣。</b></p>
		</div>

		<div class="marginTop10">
			<p class="indent"><b>第八条 其他</b></p>
			<table>
				<tr>
					<td style="vertical-align: top" class="font8 indent">1.</td>
					<td class="tdStyle">甲乙双方签署本合同后，本合同于文首所载日期成立，自乙方将本合同第一条所规定的贷款金额划离乙方账户之日起生效，自本合同项下贷款本金、利息、逾期违约金（如有）、提前还款违约金（如有）及所有其他甲方应付款项（如有）清偿之日终止。</td>
				</tr>
				<tr>
					<td style="vertical-align: top" class="font8 indent">2.</td>
					<td class="tdStyle">本合同成立至生效期间，如果甲方提供资料在真实性、准确性或完整性方面存在瑕疵，经通知仍不能依照要求提供的，或乙方在放款前审查时发现甲方不符合放款条件，导致乙方付款迟延或拒绝付款的，乙方依据本合同不承担任何经济及相关法律责任。</td>
				</tr>
				<tr>
					<td style="vertical-align: top" class="font8 indent">3.</td>
					<td class="tdStyle">双方签署合同的方式包括：当面签署和远程签署。<b>远程签署是指在甲乙双方身处异地时：<br/>（1）甲方应在远程拍摄或录音设备全程监控下提供身份证明等必要资料、配合宣读相关确认事项，并在贷款人提供的合同上签字，经乙方确认无误后合同成立；<br/>（2）或者甲乙双方在[  /  ]网络平台（平台运营方： /  ，甲方之平台账户名：   /    ，平台展现形式包括不限于微信、APP、网页等）以线上点击确认的方式进行电子签署后合同成立(甲方应妥善保管自己在[  /  ]网络平台的注册用户名和密码，自行承担因注册用户名和密码丢失、泄露或允许他人使用所产生的后果。通过甲方用户名和密码登陆的任何操作均视为该方本人的真实意思表示)</b>。远程签署的合同效力与当面签署的合同效力一致。</td>
				</tr>
				<tr>
					<td style="vertical-align: top" class="font8 indent">4.</td>
					<td class="tdStyle"><b>甲方在此特别声明：甲方知道、了解电子印章的概念、定义和作用效力，甲方同意本贷款合同可以采用电子印章的方式进行签署，并对于采用电子印章签署的本合同效力不存在任何质疑，不对本合同电子印章的效力提出任何异议。</b></td>
				</tr>
				<tr>
					<td style="vertical-align: top" class="font8 indent">5.</td>
					<td class="tdStyle">本合同及其附件的任何修改、补充均须以书面形式作出。本合同的补充合同及附件与本合同具有同等的法律效力。本合同的传真件、复印件、扫描件等有效副本的效力与本合同原件效力一致。</td>
				</tr>
				<tr>
					<td style="vertical-align: top" class="font8 indent">6.</td>
					<td class="tdStyle">甲乙双方承诺，为达成及/或履行本合同，其及其关联方的董事、管理人员、雇员、代理人或顾问不曾也不会违反任何相关的法律法规，向任何政府官员、本合同对方、任何相关第三方及其关联方的董事、管理人员、雇员、代理人或者顾问在内的任何有关人员直接或间接地提供资金、礼品或其他任何有价物品、服务，或者从事任何其他贿赂行为。甲乙双方确认，任何一方违反前述规定的行为都将给对方造成损害，并应当向对方支付违约金作为补偿，违约金金额为违反前述规定的一方履行本合同可获得的全部收益。</td>
				</tr>
				<tr>
					<td style="vertical-align: top" class="font8 indent">7.</td>
					<td class="tdStyle">如果甲乙双方在本合同履行过程中发生任何争议，应友好协商解决；如协商不成，不论争议金额大小，均选择下述第( 二  )方案解决。</td>
				</tr>
				<tr>
					<td colspan="2" class="tdStyle">
						<p class="indent">第一方案 提交XX仲裁委员会在仲裁规则项下进行仲裁（方式为现场或网络）。仲裁适用简易程序，并由独任仲裁员进行裁决。仲裁文书送达双方约定的电子邮箱或手机号码视为送达，仲裁裁决是终局的，对双方均有约束力；</p>
						<p class="indent">第二方案 向北京市通州区人民法院提起诉讼；</p>
						<p class="indent">第三方案 本合同经公证后由公证机关赋予本合同强制执行力，申请有管辖权人民法院进行强制执行。</p>
						<p class="indent">第四方案 其他。</p>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top" class="font8 indent">8.</td>
					<td class="tdStyle">本合同一式叁份，甲方保留壹份，乙方保留贰份，具有同等法律效力。如有担保人的，则甲方应负责向担保人提供一份本合同的复印件，但是甲方未能提供并不对贷款人的债权和担保权产生不利影响。本合同如需办理登记、公证的，则应再多签署相应份数正本。</td>
				</tr>
				<tr>
					<td style="vertical-align: top" class="font8 indent">9.</td>
					<td class="tdStyle"><b>甲方再次确认并承诺：（1）甲方为具备完全民事行为能力的主体，其签署本贷款合同并向乙方申请贷款为其自主意愿表示，未受到任何误导、诱导或胁迫；（2）甲方对于本合同所有条款及其含义已仔细阅读、充分知悉并明确条款含义，包括但不限于贷款要素、还款方式、个人信息授权、债权转让、担保、违约责任、合同签署及效力、争议解决条款等，尤其对于放款后乙方或乙方指定机构向甲方发送的还款通知所列甲方应还款总额及各期应还款金额确认无误；（3）乙方或其委托的第三方合作机构已充分向甲方揭示了所有相关风险，甲方对本合同不存在误解或歧义；（4）甲方非在校学生，具备收入来源和相应的还款能力。</b></td>
				</tr>
				<tr>
					<td style="vertical-align: top" class="font8 indent">10.</td>
					<td class="tdStyle"><b>乙方特别提示如下：甲方除应按照本合同约定按期足额偿还贷款本金、利息、逾期违约金（如有）、提前还款违约金（如有）及本合同项下应由甲方支付的其他款项之外，甲方无需为向乙方申请贷款向任何第三方支付其他任何费用。</b></td>
				</tr>
			</table>
		</div>

		<div class="marginTop10" style="margin-bottom: 4rem;">
			<p class="indent">本合同附件：</p>
			<p class="indent">附件一、《个人信息授权书》</p>
			<p class="indent" style="margin-top: 0.6rem">附件二、《还款代扣授权书》</p>
			<p class="indent" style="margin: 2rem 0">（以下无正文）</p>
			<p class="indent"><b>签署处: <span></span></b></p>
			<div>
				<p class="indent" style="float: left;"><b>甲方（借款人）</b></p>
				<p class="lh16" style="float: left; margin-left: 40%"><b> 签字：<%=idCardInfoObj.getCUST_NAME() %><span></span></b></p>
			</div>
			<div style="width: 100%; clear: both;"></div>
			<p class="indent" style="margin-top: 0.6rem"><b>乙方（贷款人） ：中国对外经济贸易信托有限公司（盖章）</b></p>
		</div>
	</div>

	<div class="font8 padding10">
		<p class="indent"><b>附件一： </b></p>
		<p class="lh16" style="width: 100%; text-align: center;">《个人信息授权书》</p>
		<p class="indent" style="margin-top: 1rem"><b>致：中国对外经济贸易信托有限公司、其他被授权人</b>（其他被授权人明细详见本授权书第五条）</p>
		<p class="indent">一、因本人（姓名：<span class="underLine666"><%=idCardInfoObj.getCUST_NAME() %></span>身份证号：<span class="underLine666"><%=idCardInfoObj.getID_NO() %></span>）存在借款需求，通过被授权人申请贷款相关业务（包括不限于贷款、贷款咨询与服务、贷款担保等业务，下同），<b>故本人不可撤销地授权被授权人：</b></p>
		<p class="indent">1、有权向中国人民银行个人信用信息基础数据库（以下简称“人行数据库”）查询及使用授权人（暨本人）的个人信息，包括基本信息和信用报告，并将本人之基本信息和信用记录报送给人行数据库。</p>
		<p class="indent"><b>2、有权向依法设立的征信机构、资信评估机构、身份信息验证机构、数据分析处理机构、或有关法律、监管机构许可的类似机构（以下统称“信用机构”，包括不限于前海征信、百度征信、芝麻信用、鹏元征信等机构）、以及为本人申请的业务和产品提供必要技术和服务的被授权人或被授权人的合作机构（简称“被授权机构及其合作机构”，包括不限于中国对外经济贸易信托有限公司之FOTIC 小微数据库）查询本人在信用机构、被授权机构及其合作机构的个人信息，包括基本信息和信用信息，并有权采集、保存以及向信用机构、被授权机构及其合作机构提供本人与被授权人之业务往来中提供或产生的个人信息;并同意授权信用机构、被授权机构及其合作机构采集前述信息后，可依法保存、整理、加工、使用本人个人信息,包括将本人不良信息向信用机构、被授权机构及其合作机构提供，且不再另行告知本人。</b></p>
		<p class="indent"><b>3、授权人同意授权信用机构向信息提供者采集本人收入、存款、有价证券、商业保险、不动产的信息和纳税数额信息。本人已被明确告知提供上述信息可能会给本人带来财产或信用损失，以及其他可能的不利后果，包括但不限于：将上述信息应用于对本人的信用评估或出具信用报告后，可能导致本人在申请某项业务时（如信贷业务），提供该等业务的机构因参考上述信用评估或信用报告而最终拒绝或终止向本人提供该等业务；以及该等信息被信息使用者依法提供给第三方后被他人不当利用的风险，以及虽采取了严格的符合国家认证标准的安全保护措施对所采集的上述信息实施了安全保护，但仍可能存在的因不可抗力或高科技本身所不可克服的漏洞或意外的恶意黑客攻击导致部分信息泄露的风险。但本人仍然同意授权依法成立的征信机构采集这些信息。</b></p>
		<p class="indent">二、为使被授权人能够履行上述授权事宜，授权人同意向被授权人提交本人有效身份证明文件以及被授权人要求的其他证明材料。</p>
		<p class="indent">三、本人同意信用机构、被授权机构及其合作机构将合法获取的本人个人信息用于下述范围：</p>
		<p class="indent">1、受理、审批、办理贷款相关业务，贷后服务和管理，以及在该业务存续期间监控本人的信用变化；</p>
		<p class="indent">2、向本人推荐产品及服务、开展市场调查与信息数据分析；</p>
		<p class="indent">3、其他经过本人同意的合法用途。</p>
		<p class="indent">四、本授权书自本人签字之日起生效，至本人通过被授权人办理的所有贷款相关业务之应付款项完全结清后30个工作日终止。</p>
		<p class="indent"><b>五、本人同意：本次授权的被授权人包括下列主体中的一个或者多个：</b></p>
		<p class="indent">中国对外经济贸易信托有限公司</p>
		<p class="indent">其他被授权人名称:阳光财产保险股份有限公司、阳光普惠金融信息服务有限公司</p>
		<p class="indent"><b>本人已知晓并认可：上述被授权人可能会因为本人所办理贷款之实际情况而不时更改或增加，无需本人另行签署授权书。若因为本人所办理贷款之实际情况而需由上述被授权人之关联企业提供相关服务的，则亦视为本人通过签署本授权书已授权该等关联企业，无需本人另行签署授权书。</b></p>
		<div class="marginTop10" style="margin-bottom: 4rem;">
			<p class="indent" style="float: left;"><b>授权人: <%=idCardInfoObj.getCUST_NAME() %> </b></p>
			<p class="lh16" style="float: right; margin-right: : 4rem"><b>签署时间：<span class="underLine666"><%=yyyy %></span>年<span class="underLine666"><%=MM %></span>月<span class="underLine666"><%=dd %></span>日</b></p>
			<p style="clear: both;"></p>
		</div>
	</div>

	<div  class="font8 padding10">
		<p class="indent"><b>附件二： </b></p>
		<p class="lh16" style="width: 100%; text-align: center;"><b>还款代扣授权书</b></p>

		<p class="indent" style="margin-top: 0.4rem">委托人：<%=idCardInfoObj.getCUST_NAME() %><span></span></p>
		<p class="indent" style="margin-top: 0.4rem">身份证号码：<%=idCardInfoObj.getID_NO() %><span></span></p>

		<p class="indent marginTop10">受托人：中国对外经济贸易信托有限公司</p>

		<p class="indent">委托人与受托人于年月日签订了《贷款合同》（编号为：<span class="underLine666"><%=applyObj.getPolicyNo() %></span>）。根据《贷款合同》的约定，委托人应当向受托人按月偿还贷款本息，如逾期还款的，还须按《贷款合同》的约定支付逾期违约金。</p>
		<p class="indent">委托人在此特别授权受托人并不可撤销地同意受托人每月/到期一次性通过其委托的第三方支付机构/银行从委托人的银行账户中，/到期一次性将委托人当月应当偿还的逾期违约金（或有）、应还利息、应还本金直接扣除并划付至受托人指定账户中。</p>
		<p class="indent">委托人的专用账户信息如下：</p>
		<p class="indent">开户名：<%=clBnkCrdObj.getAPPL_AC_NAM()%></p>
		<p class="indent">开户行：<%=clBnkCrdObj.getAPPL_AC_BANK()%></p>
		<p class="indent">账&nbsp;&nbsp;号：<%=clBnkCrdObj.getAPPL_CARD_NO()%></p>
		<p class="indent">委托人确保上述专用账户在委托扣款日账户状态正常（即非冻结、销户、挂失等）且账户内余额充足，受托人或其委托的第三方支付机构/银行可实现成功扣款。因还款日委托人专用账户状态不正常或者账户内余额不足导致扣款不成功的，由此产生的逾期违约金均由委托人承担。</p>
		<p class="indent">本授权书项下的委托期限自《贷款合同》生效之日起至委托人在《贷款合同》项下约定的各项贷款本息及其他款项（如有）全部偿还完毕之日止。委托人在此特别声明：委托人对于受托人及其委托的第三方支付机构/银行在本授权书项下对委托人银行账户中款项的划扣，不提出任何异议。<b>委托人通过电话、短信、微信、电子邮件、书面函件等形式变更本授权书下的银行账户，受托人及其委托的第三方支付机构/银行有权从变更后的银行账户直接进行划扣，无需委托人另行出具纸质授权书。</b>受托人在《贷款合同》存续期间或《贷款合同》期满将《贷款合同》项下债权转让给新的受让人的，委托人不可撤销的同意授权新的债权受让人及其委托的第三方支付机构/银行委托扣划的权利。</p>
		<p class="indent">本授权书自《贷款合同》文首所载之日起生效，一经签署在《贷款合同》有效期内不可撤销。</p>
		<p class="indent">本授权书一式叁份，委托人、受托人及受托人委托的第三方支付机构/银行各执壹份，每份具有相同的法律效力。</p>
		<p class="indent marginTop10"><b>委托人签字：<%=idCardInfoObj.getCUST_NAME() %></b></p>
		<p class="indent marginTop10" style="margin-bottom: 4rem"><b>日期：<span><%=yyyy %></span>年<span><%=MM %></span>月<span><%=dd %></span>日</b></p>
		<p class="indent"></p>
	</div>

</body>
</html>