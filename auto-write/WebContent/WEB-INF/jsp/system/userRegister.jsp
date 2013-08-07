<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.BoardEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	String category = request.getAttribute("category").toString();
	String actionType = "";
	if ( request.getAttribute("actionType") != null ) {
		actionType= request.getAttribute("actionType").toString();
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<!--  End Bootstrap -->
<!-- <link href="css/common.css" type="text/css" rel="stylesheet" /> -->
<!-- <link href="css/contents.css" type="text/css" rel="stylesheet" /> -->
</head>

<script>
	function userRegister(){
		var frm = document.userForm;
		
		//validate(frm);
		
		frm.submit();
	}
	
	
	function goList(){
		location.href = "userInfoList.do?jsp=system/userInfoList&category=${category}";
	}
	
	
	function validate(frm){
		var frmLen = frm.elements.length;
		var element = null;
		var key = "";
		var value = "";
		var type = "";
		
		for ( var ii = 0 ; ii < frmLen ; ii ++ ) {
			element = frm.element[ii];
			type = element.type;
			key = element.name;
			if ( key == null || key == "" ) {
				continue;
			}
			
			if ( type == "text" || type == "number" || type == "hidden"){
				value = element.value;
			} else if ( type == "checkbox" || type == "radio" ){
				value = element.checked;
			} else if ( type == "select-one" ){
				var optionIndex = element.options.selectedIndex;
				value = element.options[optionIndex].value;
			}
			
			if ( value == null || value == "" ){
				alert("[" + element.opt + "] value is empty.");
				element.focus();
			}
		}
	}
	
</script>

<body>

<div id="bodyFrame" class="container">
	<form name="userForm" method="post" action="userRegister.do" class="form-horizontal">
		<input type="hidden" name="category" value="<%=category%>"/>
		<input type="hidden" name="actionType" value="<%=actionType%>"/>
		
		<div id="bodyHead">
			<br/>
			<%=userEntity.getBreadCrumb(category)%>
		</div>
	
		<div id="bodyHeaderButton">
			<blockquote class="pull-right">
				<input type="button" class="btn btn-mini btn-primary" value="유저등록" onClick="javascript:userRegister();"/>
				<input type="button" class="btn btn-mini btn-primary" value="LIST" onClick="javascript:goList();"/>
			</blockquote>
		</div>
		
		<div id="bodyContent">
		
			<div  class="control-group">
				<label class="control-label" for="name">이름</label>
				<div class="controls">
					<input type="text" id="name" name="name" placeholder="이름">
				</div>
			</div>
			
			<div  class="control-group">
				<label class="control-label" for="id">아이디</label>
				<div class="controls">
					<input type="text" id="id" name="id" pplaceholder="아이디">
				</div>
			</div>
			
			<div  class="control-group">
				<label class="control-label" for="nic">닉네임</label>
				<div class="controls">
					<input type="text" id="nic" name="nic" pplaceholder="닉네임">
				</div>
			</div>
			
			<div  class="control-group">
				<label class="control-label" for="password">비밀번호</label>
				<div class="controls">
					<input type="password" id="password" name="password" pplaceholder="비밀번호">
				</div>
			</div>
			
			<div  class="control-group">
				<label class="control-label" for="password_confirm">비밀번호 확인</label>
				<div class="controls">
					<input type="password" id="password_confirm" name="password_confirm" pplaceholder="비밀번호 확인">
				</div>
			</div>
			
			<div  class="control-group">
				<label class="control-label" for="email">Email</label>
				<div class="controls">
					<input type="text" id="email" name="email" pplaceholder="Email">
				</div>
			</div>
			
			<div  class="control-group">
				<label class="control-label" for="nic">회원종류</label>
				<div class="controls">
					<input type="radio" id="type_code" name="type_code" value="P" placeholder="회원종류" selected="selected"> 일반회원
					<input type="radio" id="type_code" name="type_code" value="B" placeholder="회원종류"> 업소회원
					<input type="radio" id="type_code" name="type_code" value="W" placeholder="회원종류"> 여성회원
					<input type="radio" id="type_code" name="type_code" value="A" placeholder="회원종류"> 관리자
					<input type="radio" id="type_code" name="type_code" value="M" placeholder="회원종류"> 마스터 관리자
				</div>
			</div>
			
			<div  class="control-group">
				<label class="control-label" for="nic">회원상태</label>
				<div class="controls">
					<input type="radio" id="status_code" name="status_code" value="W" placeholder="회원종류" selected="selected"> 승인대기
					<input type="radio" id="status_code" name="status_code" value="A" placeholder="회원종류"> 활동중
					<input type="radio" id="status_code" name="status_code" value="D" placeholder="회원종류"> 강등
					<input type="radio" id="status_code" name="status_code" value="B" placeholder="회원종류"> 블랙
				</div>
			</div>
			
		</div>
		
		<div id="bodyFooterButton">
			<blockquote class="pull-right">
				<input type="button" class="btn btn-mini btn-primary" value="유저등록" onClick="javascript:userRegister();"/>
				<input type="button" class="btn btn-mini btn-primary" value="LIST" onClick="javascript:goList();"/>
			</blockquote>
		</div>
		
	</form>
	
</div>

</body>
</html>