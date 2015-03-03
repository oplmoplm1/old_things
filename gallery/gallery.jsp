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
		$("#addGallery").click(function(){
			$(".add").css("display","block");
		});
		$("#cancelAdd").click(function(){
			$(".add").css("display","none");
	 	});
		$(".update").click(function(){
			var gallery = $(this).parent();
			var text = gallery.find(".content");
			$.each(text,function(key,val){
				var textField =$(val).next();
			 	$(val).next().val($(val).text());
				textField.css("display","block");
			});
			text.css("display","none");
			$(this).next().css("display","block");
			$(this).css("display","none");
			
			$(this).parents(".gallery").unbind();
			return false;
		});
/* 		$("#submit").click(function(){
			$.post("gallery.do?method=update",$(this).parents("form").serilize(),function(){
				return false;
			});
			return false;
		}); */
		$(".cancel").click(function(){
			$(".twoButton").css("display","none");
			$(".title input").css("display","none");
			$(".content").css("display","block");
			$(".update").css("display","block");
			$(this).parents(".gallery").bind("click",function(){
				var gal_id =$(this).find("[name=gal_id]").val();
				window.location.href="photo.do?method=intitial&gal_id="+gal_id;
			});
			return false;
			
		});
		
		$(".delete").children("a").click(function(){
			var gal_id =$(this).parent().siblings("[name=gal_id]").val();
			var user_id =$(this).parent().siblings("[name=user_id]").val();
			$.get("gallery.do?method=delete&gal_id="+gal_id+"&user_id="+user_id);
			$(this).parents(".gallery").empty().remove();
			
			return false;
		});
		
		$(".gallery").click(function(){
			var gal_id =$(this).find("[name=gal_id]").val();
			var user_id =$(this).find("[name=user_id]").val();
			window.location.href="photo.do?method=intitial&gal_id="+gal_id+"&user_id="+user_id;
		});
	});
  	
  </script>

  </head>
  <style type="text/css">
  	#header{
  		width:100%;
  		height:50px
  	}
  	.gallery> *{
  		float:left;
  	}
  	.gallery{
  		width:500px;
  		height:400px;
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
  	<div id="header">
    	<input id ="addGallery" type="button" value ="添加相册" />
    </div>
    <div class="add" style="display:none"> 
    	<form id="addForm" name="Gallery" action="gallery.do?method=add&user_id=<%= request.getParameter("user_id") %>" method="post">
  		<img src ="${face[it.count-1] }" width ="200" height="200"/><div>
      	<div ><span>相册名</span><br/><div class="content"><input name="gal_name" type='text' class='addValue'/></div></div>
  		<div >内容描述 <br/><div class="content"><input name="gal_desc" type='text' class='addValue'/></div></div>
  		<div>权限<br/><div class="content"><input name="gal_authrity" type='text' class='addValue'/></div></div> 
  		</div>
  		<input type="submit" />
  		<input type="button" id="cancelAdd" value="取消"/>
  		</form>
    </div> 
    <c:forEach items="${galList }" var="gallery" varStatus="it">
    	 <div class="gallery" > 
    	 	<form  action="gallery.do?method=update" method="post">
	  			<img src ="${face[it.count-1] }" width ="200" height="200"/>
	      		<div class= title><span>相册名</span><br/>
					<div class="content">${gallery.gal_name }</div>
						<input name="gal_name"  style="display:none"  type='text'/>
				</div>
				
	  			<div class="title">内容描述 <br/>
		  			<div class="content">${gallery.gal_desc }</div>
		  			<input  style="display:none" name="gal_desc" type='text'/>
	  			</div>
	  			<div class= title>权限<br/>
	  				<div class="content">${gallery.gal_authrity }</div>
	  				<select style="display:none">
					  <option value ="p">公开</option>
					  <option value ="pr">私有</option>
					</select>
	  				<input  style="display:none" name="gal_authrity" type='text'/>
	  			</div> 
	  			<input name="gal_id" type="text" value="${gallery.gal_id }" style="display:none"/>
	  			<input name="user_id" type="text" value="${gallery.user_id }" style="display:none"/>
	  			<div class="delete"><a href="#">删除</a></div>
	  			<div class="update"><a href="#">修改</a></div>
	  			<div class="twoButton"  style="display:none">
	  				<input type="submit" id="submit"  value="确定">
	  				<input type="button" class="cancel" value="取消">
	  			</div>
  			</form>
  		</div>
    </c:forEach>
    
  </body>
</html>
