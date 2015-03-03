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
		
		$(".fancybox").fancybox({beforeLoad: function() {
			var text_id = $(this.element).attr("value");
			var index = $(this.element).index();
	        this.title = '<a class ="updateTitle" index="'+index+'" href="' + text_id + '">修改</a> &nbsp;&nbsp;&nbsp' + '<a  href="photo.do?method=deletePhoto&photo_id=' + text_id + '">删除</a> &nbsp;&nbsp;&nbsp;' +'<a class="updateFace" href="'+text_id+'" >设为封面</a> &nbsp;&nbsp;&nbsp;'+'<span>'+this.title+'</span>';
			/* $(this.title.split("&nbsp;&nbsp;&nbsp;")[0]).click(function(){
				return false;
				<form class="update_form" name="Photo" method="post" action=""><p class="update_error">请输入新名字</p><p><input type="text" class="update_name" name="photo_name" size="30" /></p><p><input type="submit" value="确定修改" /></p></form> &nbsp;&nbsp;&nbsp;'
			}); */
			
	    	}
		});
		/* $(".updateTitle").live("click",function(){
			return false;
		}); */
		//文件上传插件
		    $("#file_upload").uploadify({
		    	buttonText: '上传图片',
		        height        : 30,
		        swf           : 'gallery/uploadify/uploadify.swf',
		        uploader      : "upload.do?method=upload&user_id="+getUrlVar("user_id")+"&gal_id="+getUrlVar("gal_id"),
		        width         : 120,
		        fileExt		: '*.png; *.jpg ;*.bmp *;*.psd; *.gif ;*. jpeg',
		        fileDesc :"请选择图片文件"
		    });
		//parameter获取方法		
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
		    
		    //修改
    		$(document).delegate(".updateTitle","click",function(){
    		    var text_id = $(this).attr("href");
    		    alert($("#update_form").find("[name=photo_name]").val());
    			$("#update_form").insertAfter(this);
    		    $("#update_form").bind("submit", function() {
    		    	if ($("#update_name").val().length < 1) {
    		    	    $("#update_error").show();
    		    	    $.fancybox.resize();
    		    	    return false;
    		    	}
    		    	$.ajax({
    		    		type		: "POST",
    		    		cache	: false,
    		    		context : $(this),
    		    		url		: "photo.do?method=updatePhotoName&photo_id="+text_id,
    		    		data		: $(this).serializeArray(),
    		    		success: function(data) {
    		    			var text = $(this).find("[name=photo_name]").val();
    		    			$(this).parent().find("span").html(text);
    		    			$(this).appendTo("#hide");
    		    			$.fancybox(data);
    		    		},
    		    		complete: function(XMLHttpRequest, textStatus){
    						//HideLoading();
    		    			$(this).appendTo("#hide");
    					},
    					error: function(){
    					//请求出错处理
    						$(this).appendTo("#hide");
    					}
    		    	});
    		    	return false;
    		    });
    			
				return false;
			});
			$("#update_form").delegate("[name=photo_name]","change",function(){
				alert($(this));
			});
		    
    		$(document).delegate(".updateFace","click",function(){
    			var text_id=$(this).attr("href");
    			$.ajax({
		    		type		: "POST",
		    		cache	: false,
		    		url		: "photo.do?method=updateFace&photo_id="+text_id,
		    		data		: $(this).serializeArray(),
		    		success: function(data) {
		    			alert("已经设置为封面");
		    		}
		    	});
    			return false;
    		});

		});

	</script>
  </head>
  
  <body>
    <input id="file_upload" name="file_upload" type="file" />
    
    <div id="photoList">
    	<c:forEach items="${photoList }" var="photo" >
    		<a class="fancybox" value="${photo.photo_id }"   href="${photo.photo_data }${photo.photo_name_in_database}"  data-fancybox-group="gallery" title="${photo.photo_name }">
    			<img src="${photo.photo_data }${photo.photo_name_in_database}" width="200" height="200" alt="" />
    		</a>
    	</c:forEach>
    	<div id="hide" style="display:none">
			<form class="update_form" name="Photo" method="post" action=""><p class="update_error">请输入新名字</p><p><input type="text" class="update_name" name="photo_name" size="30" /></p><p><input type="submit" value="确定修改" /></p></form>
</div>
    </div>
    
    
  </body>
</html>
