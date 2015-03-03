<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'upload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  	<link href="gallery/uploadify/uploadify.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="gallery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="gallery/uploadify/jquery.uploadify.min.js"></script>
	<script type="text/javascript" src="gallery/source/jquery.fancybox.js?v=2.1.5"></script>
	<link rel="stylesheet" type="text/css" href="gallery/source/jquery.fancybox.css?v=2.1.5" media="screen" />
	<script type="text/javascript">
	$(document).ready(function(){
	// 图片效果插件	
		$(".fancybox").fancybox();
		//文件上传插件		//parameter获取方法		
		//"/gallery/img/"+getUrlVar("user_id")+"/"+getUrlVar("gal_id")+"/"
		    function getUrlVars(){
		        var vars = [], hash;
		        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
		        for(var i = 0; i < hashes.length; i++)
		        {
		            hash = hashes[i].split('=');
		            vars.push(hash[0]);
		            vars[hash[0]] = hash[1];
		        }
		        return vars;
		    }
		    //得到指定参数的value
		    function getUrlVar(name){
		        return getUrlVars()[name];
		    }
		    
		});

	</script>
  </head>
  
  <body>
	<a href ="gallery.do?method=intialize&user_id=<%= request.getParameter("user_id")%>">返回</a>    
    <div id="photoList">
    	<c:forEach items="${photoList }" var="photo">
    		<a class="fancybox" href="${photo.photo_data }${photo.photo_name_in_database}"  data-fancybox-group="gallery" title="${photo.photo_name }"><img src="${photo.photo_data }${photo.photo_name_in_database}" width="200" height="200" alt="" /></a>
    	</c:forEach>
    </div>
    
    
  </body>
</html>
