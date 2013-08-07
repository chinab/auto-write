<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.BoardEntity"%>
<%@ page import="com.jekyll.common.framework.entity.ConditionEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	UserEntity userContent = (UserEntity) request.getAttribute("UserInfo");
	
	String category = request.getAttribute("category").toString();
	String actionType = request.getAttribute("actionType").toString();
	
	MenuDto leafMenu = (MenuDto)userEntity.getMenuMap().get(category);
	String leftMenuStr = leafMenu.getParmenuid();
	MenuDto leftMenu = (MenuDto)userEntity.getMenuMap().get(leftMenuStr);
%>
<html>
<head>
<title><%=leafMenu.getMenunm()%></title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

<!-- 배너 스크립트 -->
<jsp:include page="../include/banner.jsp" flush="false"/>
<!-- 배너 스크립트 끝 -->

<script>
	function init(){
		var frm = document.userForm;
		
		frm.category.value = "<%=category%>";
		frm.actionType.value = "<%=actionType%>";
		frm.seqId.value = "<%=userContent.getSeq_id()%>";
		frm.point.value = "<%=userContent.getPoint()%>";
		
		frm.name.value = "<%=userContent.getName()%>";
		frm.id.value = "<%=userContent.getId()%>";
		frm.nic.value = "<%=userContent.getNic()%>";
		frm.password.value = "<%=userContent.getPassword()%>";
		frm.email.value = "<%=userContent.getEmail()%>";
		
		frm.type_code[getTypeSelectedIndex()].checked = true;
		frm.status_code[getStatusSelectedIndex()].checked = true;
		try {
			frm.service_code[getServiceSelectedIndex()].checked = true;
		} catch ( Exception ) {
			
		}
	}
	
	function getTypeSelectedIndex(){
		var value = "<%=userContent.getType_code()%>";
		var valueArray = ["P", "B", "W", "A", "M"];
		
		for ( var ii = 0 ; ii < valueArray.length ; ii ++ ) {
			if ( value == valueArray[ii] ){
				return ii;
			}
		}
	}
	
	function getStatusSelectedIndex(){
		var value = "<%=userContent.getStatus_code()%>";
		var valueArray = ["W", "A", "D", "B"];
		
		for ( var ii = 0 ; ii < valueArray.length ; ii ++ ) {
			if ( value == valueArray[ii] ){
				return ii;
			}
		}
	}
	
	function getServiceSelectedIndex(){
		var value = "<%=userContent.getService_code()%>";
		var valueArray = ["01", "02", "03", "04", "05", "06", "07", "08", "09"];
		
		for ( var ii = 0 ; ii < valueArray.length ; ii ++ ) {
			if ( value == valueArray[ii] ){
				return ii;
			}
		}
	}
	
	function userModify(){
		var frm = document.userForm;
		
	<%
		if ( !"M".equals(userEntity.getType_code()) ){
	%>
		userType = "<%=userContent.getType_code()%>";
		if ( userType == "A" || userType == "M" ){
			alert("운영자 및 관리자 정보는 관리자만 변경할 수 있습니다.");
			return;
		}
	<%
		}	
	%>
		frm.actionType.value = "MODIFY";
		
		//validate(frm);
		
		var statusCode = frm.status_code[3].checked;
		
		if ( statusCode ){
			if ( confirm("블랙 시 해당 사용자의 글과 답글이 모두 삭제됩니다.\n블랙처리 하시겠습니까?") ){
				frm.submit();
			} else {
				alert("사용자 정보 변경이 취소되었습니다.");
				return;
			}
		} else {
			frm.submit();
		}
		
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
</head>

<body onload="javascript:loadBanner();init();">

<form name="userForm" method="post" action="userModify.do">
	<input type="hidden" name="category" value=""/>
	<input type="hidden" name="actionType" value=""/>
	<input type="hidden" name="seqId" value=""/>
	<input type="hidden" name="point" value=""/>
	
<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false">
	<jsp:param name="topMenu" value="<%= userEntity.getTopMenu() %>"/>
	<jsp:param name="currMenuName" value="<%= userEntity.getMenuMap().get(category).getMenunm() %>"/>
</jsp:include>
<!--탑메뉴 끝-->


<!--로그인&배너-->
<jsp:include page="../include/loginBox.jsp" flush="false"/>
<!--로그인&배너 끝-->

<table width="1000" align="center">
<tr>
<td>   
<div class="h_board">
	<table width="1000" border="0" cellpadding="0" cellspacing="0">
		<colgroup>
	      <col width="250">
	      <col width="./" />
	    </colgroup>
	
	
		<!--좌측메뉴-->
		<tr>
		    <td valign="top">
				<div class="sub_title">
					<div class="je"><%=leftMenu.getMenunm()%></div>
					<%=userEntity.getLeftMenu(leftMenuStr)%>
				</div>
			
				<div>
					<jsp:include page="../include/leftChatting.jsp" flush="false"/>
				</div>
			</td>
			<!-- 좌측메뉴 끝-->
			
			<td valign="top">
				<div class="admin_center">
					
					<div class="sub_title_bar"><span class="title"><%=userEntity.getBreadCrumb(category)%></span></div>
						
					<!-- 본문 시작-->
					<table class="tb3" style="bottom-margin:15;">
						<colgroup>
							<col width="100" />
							<col width="10" />
							<col width="./"./>
						</colgroup>
		
						<tbody>
						    <tr>
								<td height="3" background="#cccccc" colspan="3"></td>
							</tr>
							
							<tr>
								<td class="subject"><b>이름</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="name" name="name" class="s_id" size="65" style="width:150;" placeholder="이름">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>아이디</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="hidden" id="id_dup_check" name="id_dup_check" value="false"/>
									<input type="text" id="id" name="id" class="s_id" size="65" style="width:150;" placeholder="아이디">
									<input id="dupIdCheck" name="dupIdCheck" class="s_id" type="image" src="./images/bt_s1.gif" onclick="javascript:idDupCheck();">
									<span class="ber_text">* 최소 3자이상 입력하세요.</span>
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>회원 닉네임</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="hidden" id="nic_dup_check" name="nic_dup_check" value="false"/>
									<input type="text" id="nic" name="nic" class="s_id" size="65" style="width:150;" placeholder="닉네임">
									<input id="dupNicCheck" name="dupNicCheck" class="s_id" type="image" src="./images/bt_s1.gif" onclick="javascript:nicDupCheck();">
									<span class="ber_text">공백없이 한글,영문,숫자만 입력 가능 (한글2자, 영문4자 이상) </span>
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>비밀번호</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="password" id="password" name="password" class="s_id" size="65" style="width:150;">
								</td>
							</tr>
		
							<tr>
								<td class="subject"><b>비밀번호 확인</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="password" id="password_confirm" name="password_confirm" class="s_id" size="65" style="width:150;">
								</td>
							</tr>
		
							<tr>
								<td class="subject"><b>E-Mail</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input id="email" name="email" class="s_id" type="text" size="65" style="width:150;">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>회원 구분</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="radio" id="type_code" name="type_code" value="P" placeholder="회원종류">일반회원
									<br/>
									<input type="radio" id="type_code" name="type_code" value="B" placeholder="회원종류">업소회원
									<br/>
									<input type="radio" id="type_code" name="type_code" value="W" placeholder="회원종류">여성회원
								<%
									if ( "M".equals(userEntity.getType_code()) ){
								%>
									<br/>
									<input type="radio" id="type_code" name="type_code" value="A" placeholder="회원종류">운영자
									<br/>
									<input type="radio" id="type_code" name="type_code" value="M" placeholder="회원종류">관리자
								<%
									} else {
								%>
									<br/>
									<input type="radio" id="type_code" name="type_code" value="A" placeholder="회원종류" disabled="true">운영자
									<br/>
									<input type="radio" id="type_code" name="type_code" value="M" placeholder="회원종류" disabled="true">관리자
								<%
									}
								%>
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>회원 상태</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="radio" id="status_code" name="status_code" value="W" placeholder="회원종류">승인대기
									<br/>
									<input type="radio" id="status_code" name="status_code" value="A" placeholder="회원종류">활동중
									<br/>
									<input type="radio" id="status_code" name="status_code" value="D" placeholder="회원종류">강등
									<br/>
									<input type="radio" id="status_code" name="status_code" value="B" placeholder="회원종류">블랙
								</td>
							</tr>
							
							<%
								if ( "B".equals(userContent.getType_code()) ) {
							%>
							<tr>
								<td class="subject"><b>업종</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="radio" id="service_code" name="service_code" value="01" placeholder="업종">오피
									&nbsp;
									<input type="radio" id="service_code" name="service_code" value="02" placeholder="업종">안마
									&nbsp;
									<input type="radio" id="service_code" name="service_code" value="03" placeholder="업종">휴게,대떡
									&nbsp;
									<input type="radio" id="service_code" name="service_code" value="04" placeholder="업종">건마
									&nbsp;
									<input type="radio" id="service_code" name="service_code" value="05" placeholder="업종">립카페
									&nbsp;
									<input type="radio" id="service_code" name="service_code" value="06" placeholder="업종">핸플
									&nbsp;
									<input type="radio" id="service_code" name="service_code" value="07" placeholder="업종">키스방
									&nbsp;
									<input type="radio" id="service_code" name="service_code" value="08" placeholder="업종">주점
									&nbsp;
									<input type="radio" id="service_code" name="service_code" value="09" placeholder="업종">기타
								</td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
					
					<!-- 수정, 목록 버튼-->
					<div class="paging3">
						<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
						<a href="javascript:userModify()"><img class='write' src="./images/board/bt_mod.gif"></a>
					</div>
					
				</div>
			</td>
		</tr>
	</table>
</div>
</td></tr></table>

<!--푸터-->
<div class="bot_bg">
<div class="bot_add01"><img src="./images/bottom.jpg"></div>
</div>

</form>
</body>
</html>
