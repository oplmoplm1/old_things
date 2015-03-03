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
    
    <title>Gallery</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script type="text/javascript" src="gallery/jquery-1.10.2.min.js"></script>
  <script type="text/javascript">
  	
  	$().ready(function (){
		$(".gallery").click(function(){
			var gal_id =$(this).find("[name=gal_id]").val();
			var user_id =$(this).find("[name=user_id]").val();
			window.location.href="photo.do?method=intitial&gal_id="+gal_id+"&user_id="+user_id;
		});
		$(".gallery").mouseover(function(){
			$(this).css("background","lightgrey");
		});
		$(".gallery").mouseout(function(){
			$(this).css("background","white");
		});
	});
  	
  </script>

  </head>
  <style type="text/css">
  	#header{
  		width:100%;
  		height:50px
  	}
  	.gallery> img{
  		float:left;
  	}
  	.gallery{
  		padding:20px;
  		width:400px;
  		height:200px;
  		float:left;
  		display:block;
  	}
  	.content {
  		font-weight: normal;
  	}
  	.title{
  		font-weight: bold;
  	
  	}
  </style>
  <body>
    <c:forEach items="${galList }" var="gallery" varStatus="it">
    	 <div class="gallery" > 
	  			<img src ="${face[it.count-1] }" width ="200" height="200"/>
	      		<div class= title><span>相册名</span><br/>
					<div class="content">${gallery.gal_name }</div><br/>
				</div>
				
	  			<div class="title">内容描述 <br/>
		  			<div class="content">${gallery.gal_desc }</div><br/>
	  			</div>
	  			<div class= title>权限<br/>
	  				<div class="content">${gallery.gal_authrity }</div><br/>
	  			</div> 
	  			<input name="gal_id" type="text" value="${gallery.gal_id }" style="display:none"/>
	  			<input name="user_id" type="text" value="${gallery.user_id }" style="display:none"/>
  		</div>
    </c:forEach>
    
  </body>
</html>
