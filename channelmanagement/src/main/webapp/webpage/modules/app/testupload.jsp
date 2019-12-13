<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
request.setAttribute("basePath", basePath);  
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="${basePath}api/idcard/saveidcardInfo" method="post" enctype="multipart/form-data">  
    <label>用户名：</label><input type="text" name="name"/><br/>  
    <label>密 码：</label><input type="password" name="password"/><br/>  
    <label>头 像1</label><input type="file" name="file1"/><br/>  
    <label>头 像2</label><input type="file" name="file2"/><br/>  
    <input type="submit" value="提  交"/>  
</form>   
</body>
</html>