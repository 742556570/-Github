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
	<title>还款代扣授权</title>
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
		.tdStyle{
			line-height: 1.6rem;
			font: 0.8rem;
		}
	</style>

	<script type="text/javascript">

		$(document).ready(function(){
			setTimeout("getInfo()",500);
			
		});

		//获取用户的个人信息 以及 借款情况
    	function getInfo(){
    		window.WebViewJavascriptBridge.callHandler(
    			'YGH5CallNative',
    			{'pageName': '投保单',
    			 'func': 'getUserInfo',
    			 'params': null},
    			 function(responseData){

    		 		window.sessionStorage.setItem(userInfoKey, responseData);

    		 		var proInfoObj = window.JSON.parse(responseData);

    		 		$('#signY, #signY2').text(getYear());
					$('#signM, #signM2').text(getMonth());
					$('#signD, #signD2').text(getDay());
					$('#name2, #loanSign3, #accName2').text(proInfoObj.name);
					$('#accBank2').text(proInfoObj.bank);
					$('#accNum2').text(proInfoObj.bankcardNum);
					$('#id2').text(proInfoObj.idNum);
					
    		 	}
    		);
    	}

	</script>

</head>
<body>
	<div  class="font8 padding10">
		<p class="indent"><b>附件二： </b></p>
		<p class="lh16" style="width: 100%; text-align: center;"><b>还款代扣授权书</b></p>

		<p class="indent" style="margin-top: 0.4rem">委托人：<span id="name2"><%=idCardInfoObj.getCUST_NAME() %></span></p>
		<p class="indent" style="margin-top: 0.4rem">身份证号码：<span id="id2"><%=idCardInfoObj.getID_NO() %></span></p>

		<p class="indent marginTop10">受托人：中国对外经济贸易信托有限公司</p>

		<p class="indent">委托人与受托人于<span id="signY" class="underLine666"><%=yyyy %></span>年<span id="signM" class="underLine666"><%=MM%></span>月<span id="signD" class="underLine666"><%=dd%></span>日签订了《贷款合同》（编号为：<span class="underLine666"><%=applyObj.getPolicyNo() %></span>） 。根据《贷款合同》的约定，委托人应当向受托人按月偿还贷款本息，如逾期还款的，还须按《贷款合同》的约定支付逾期违约金。</p>
		<p class="indent">委托人在此特别授权受托人并不可撤销地同意受托人每月/到期一次性通过其委托的第三方支付机构/银行从委托人的银行账户中，/到期一次性将委托人当月应当偿还的逾期违约金（或有）、应还利息、应还本金直接扣除并划付至受托人指定账户中。</p>
		<p class="indent">委托人的专用账户信息如下：</p>
		<p class="indent">开户名：<span id="accName2"><%=clBnkCrdObj.getAPPL_AC_NAM()%></span></p>
		<p class="indent">开户行：<span id="accBank2"><%=clBnkCrdObj.getAPPL_AC_BANK()%></span></p>
		<p class="indent">账&nbsp;&nbsp;号：<span id="accNum2"><%=clBnkCrdObj.getAPPL_CARD_NO()%></span></p>
		<p class="indent">委托人确保上述专用账户在委托扣款日账户状态正常（即非冻结、销户、挂失等）且账户内余额充足，受托人或其委托的第三方支付机构/银行可实现成功扣款。因还款日委托人专用账户状态不正常或者账户内余额不足导致扣款不成功的，由此产生的逾期违约金均由委托人承担。</p>
		<p class="indent">本授权书项下的委托期限自《贷款合同》生效之日起至委托人在《贷款合同》项下约定的各项贷款本息及其他款项（如有）全部偿还完毕之日止。委托人在此特别声明：委托人对于受托人及其委托的第三方支付机构/银行在本授权书项下对委托人银行账户中款项的划扣，不提出任何异议。<b>委托人通过电话、短信、微信、电子邮件、书面函件等形式变更本授权书下的银行账户，受托人及其委托的第三方支付机构/银行有权从变更后的银行账户直接进行划扣，无需委托人另行出具纸质授权书。</b>受托人在《贷款合同》存续期间或《贷款合同》期满将《贷款合同》项下债权转让给新的受让人的，委托人不可撤销的同意授权新的债权受让人及其委托的第三方支付机构/银行委托扣划的权利。</p>
		<p class="indent">本授权书自《贷款合同》文首所载之日起生效，一经签署在《贷款合同》有效期内不可撤销。</p>
		<p class="indent">本授权书一式叁份，委托人、受托人及受托人委托的第三方支付机构/银行各执壹份，每份具有相同的法律效力。</p>
		<p class="indent marginTop10"><b>委托人签字：</b><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><b>（签章处）<span id="loanSign3"><%=idCardInfoObj.getCUST_NAME() %></span></b></p>
		<p class="indent marginTop10" style="margin-bottom: 4rem"><b>日期：<span id="signY2"><%=yyyy %></span>年<span id="signM2"><%=MM %></span>月<span id="signD2"><%=dd%></span>日</b></p>
		<p class="indent"></p>
	</div>


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
		bridge.registerHandler("YGNativeCallH5", function(data, responseCallback) {
			data.replace('YGNativeCallH5:', '');
			window.sessionStorage.setItem(userInfoKey, data);

    		var proInfoObj1 = window.JSON.parse(data);
			$('#name2, #loanSign3, #accName2').text(proInfoObj1.name);
			$('#accBank2').text(proInfoObj1.bank);
			$('#accNum2').text(proInfoObj1.bankcardNum);
			$('#id2').text(proInfoObj1.idNum);
			$('#signY2').text(getYear());
			$('#signM2').text(getMonth());
			$('#signD2').text(getDay());
    	});
    });



</script>
</html>