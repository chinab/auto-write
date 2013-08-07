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
	
	String category = request.getAttribute("category").toString();
	
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


	function menuRegister(){
		var frm = document.menuForm;
		
		frm.actionType.value = "WRITE";
		
		//validate(frm);
		
		alert("준비중");
		
		return;
		
		frm.submit();
	}
	
	
	function goList(){
		location.href = "menuInfoList.do?jsp=system/menuInfoList&category=${category}";
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
	
	function changeSubCategory(){
		var mainCategory = document.getElementById("menuMainCategory").value;
		alert(mainCategory);
		
		
	}
	
</script>
</head>

<body onload="javascript:loadBanner();">

<form name="menuForm" method="post" action="menuRegister.do">
	<input type="hidden" name="category" value=""/>
	<input type="hidden" name="actionType" value=""/>
	
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
							
							<!-- 
							<tr>
								<td class="subject"><b>메뉴 대분류</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<select naeme="menuMainCategory" id="menuMainCategory" onchange="javascript:changeSubCategory();">
										<option value="">=== 대분류 선택 ===</option>
										<%
											List menuList = userEntity.getMenuList();
											MenuDto tempMenu = null;
											for ( int ii = 0 ; ii < menuList.size() ; ii ++ ){
												tempMenu = (MenuDto)menuList.get(ii);
												out.println("<option value=\"" + tempMenu.getMenuid() + "\">" + tempMenu.getMenunm() + "</option>");
												out.print("										");
											}
										%>
									</select>
								</td>
							</tr>
							 -->
							 
							<tr>
								<td class="subject"><b>메뉴 ID</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="menuId" name="menuId" class="s_id" size="65" style="width:150;" placeholder="메뉴 ID">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>메뉴명</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="menuName" name="menuName" class="s_id" size="65" style="width:150;" placeholder="메뉴명">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>메뉴 레벨</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="menuLevel" name="menuLevel" class="s_id" size="65" style="width:150;" placeholder="메뉴 레벨">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>메뉴 URL</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="menuPageUrl" name="menuPageUrl" class="s_id" size="65" style="width:500;" placeholder="메뉴 URL">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>메뉴 타입</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="menuType" name="menuType" class="s_id" size="65" style="width:150;" placeholder="메뉴 타입">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>부모메뉴 ID</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="menuParentId" name="menuParentId" class="s_id" size="65" style="width:150;" placeholder="부모메뉴 ID">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>이미지 경로</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="menuImagePath" name="menuImagePath" class="s_id" size="65" style="width:500;" placeholder="이미지 경로">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>사용여부</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="menuUseYn" name="menuUseYn" class="s_id" size="65" style="width:150;" placeholder="사용여부">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>디스플레이 순서</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="menuDisplaySeq" name="menuDisplaySeq" class="s_id" size="65" style="width:150;" placeholder="사용여부">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>허용유저 타입</b></td>
								<td ><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="menuAllowedUserType" name="menuAllowedUserType" class="s_id" size="65" style="width:150;" placeholder="사용여부">
								</td>
							</tr>
							
						</tbody>
					</table>
					
					<!-- 수정, 목록 버튼-->
					<div class="paging3">
						<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
						<a href="javascript:menuRegister()"><img class='write' src="./images/board/bt_mod.gif"></a>
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
