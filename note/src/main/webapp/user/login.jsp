<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	 #loginForm{
	 	text-align: center;
	 	margin-left: auto;
	 	margin-top: 300px;
	 }
	</style>

  </head>
  
  <body>
	 <div id="loginForm">
	 	用户登录
	    <form action="post" name="user">
	    	帐号:<input name="userName" /><br/>
	    	密码:<input name="userPassword" type="password"/><br/>
	    	<input type="submit" value="登录">
	    </form>
	 </div>
  </body>
</html>
