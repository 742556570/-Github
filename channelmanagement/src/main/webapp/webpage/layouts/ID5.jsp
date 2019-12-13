<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
阳光保险验证四要素
<html >
<head>

<title>验证四要素</title>

<link rel="stylesheet" href="css/style.css">

<script type="text/javascript" src="js/jquery-2.1.0.min.js"></script>

</head>
<body>

<div class="form-div">
    <form id="reg-form" action="" method="post">
	<input name="basePath" type="hidden" id="basePath" value=<%=basePath %>>
        <table>
            <tr>
                <td>用户名</td>
                <td><input name="username" type="text" id="username">
                </td>
            </tr>
            <tr>
                <td>身份证号</td>
                <td><input name="idcard" type="text" id="idcard" ></td>
            </tr>
            <tr>
                <td>手机号</td>
                <td><input name="phone" type="text" id="phone" ></td>
            </tr>
            <tr>
                <td>银行卡号</td>
                <td><input name="bankcard" type="text" id="bankcard" ></td>
            </tr>
       
        </table>

		<div class="buttons">
			<input value="提交" type="button" onclick="findAll();" style="margin-right:20px; margin-top:20px;">
        </div>
		
        <br class="clear">
    </form>
 
</div>
<div class="form-div">
    <form id="reg-form" action="" method="post">
   
   <table id="tbody" >
    	国政通返回结果
    </table>
 </form></div>
<script type="text/javascript">

function findAll(){
	var username=$("#username").val();

	var idcard=$("#idcard").val();
	var phone=$("#phone").val();
	var bankcard=$("#bankcard").val();
	var basePath=$("#basePath").val();
	if(null==username &&""==username){
			alert("用户名不能为空");
			return false;
	}
	if(null==idcard &&""==idcard){
			alert("身份证号不能为空");
			return false;
	}
	if(null==phone &&""==phone){
			alert("手机号不能为空");
			return false;
	}
	if(null==bankcard &&""==bankcard){
			alert("银行卡号不能为空");
			return false;
	}
	$("#tbody").html("");
	$.post(basePath+'api/sysCollection/getID5Client',{"username":username,"idcard":idcard,"phone":phone,"bankcard":bankcard},function(resultJSONObject){
	       if(resultJSONObject.success){
	    		alert("验证四要素成功");
	    		 var trs = "";
	    		 var tbody = ""; 
	            trs += "<tr style=text-align:center><td> " + resultJSONObject.message +"</td>" 
	             		+"</tr>";  
	             tbody += trs;  
	             $("#tbody").append(tbody);
	    	}
	       else{
	    	   
	    	   alert("验证四要素失败");
	    	   var trs = "";
	    		 var tbody = ""; 
	            trs += "<tr style=text-align:center><td> " + resultJSONObject.message +"</td>" 
	             		+"</tr>";  
	             tbody += trs;  
	             $("#tbody").append(tbody);
	       }
	    },"json");
}
</script>

</body>
</html>
