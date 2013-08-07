<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List<MenuDto> menus = userEntity.getMenuList();
	MenuDto menu = null;
	
	List<MenuDto> menuList = (List<MenuDto>) request.getAttribute("MenuList");
	String category = request.getAttribute("category").toString();
	String actionType;
	if ( request.getAttribute("actionType") != null ) {
		actionType = request.getAttribute("actionType").toString();
	} else {
		actionType = "READ";
	}
	
	MenuDto leafMenu = (MenuDto)userEntity.getMenuMap().get(category);
	String leftMenuStr = leafMenu.getParmenuid();
	MenuDto leftMenu = (MenuDto)userEntity.getMenuMap().get(leftMenuStr);
%>
<html>
<head>

<title>HWARU</title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

<!-- 배너 스크립트 -->
<jsp:include page="../include/banner.jsp" flush="false"/>
<!-- 배너 스크립트 끝 -->

<script>
	function menuInfoView(menuId, category, actionType){
		var frm = document.listForm;
		
		frm.action = "menuInfoView.do";
		frm.jsp.value = "system/menuInfoView";
		frm.menuId.value = menuId;
		frm.category.value = category;
		frm.actionType.value = actionType;
		
		frm.submit();
	}
	
	function menuInfoList(category, pageNum){
		var frm = document.listForm;
		
		frm.action = "menuInfoList.do";
		frm.jsp.value = "system/menuAuthInfoList";
		frm.category.value = category;
		frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
</script>
</head>
<body>

<body onLoad="javascript:loadBanner();">
	<form name="listForm" method="post">
		<input type="hidden" name="jsp" value=""/>
		<input type="hidden" name="menuId" value=""/>
		<input type="hidden" name="category" value="<%=category%>"/>
		<input type="hidden" name="actionType" value=""/>
		<input type="hidden" name="pageNum" value=""/>
		
	
<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false">
	<jsp:param name="topMenu" value="<%= userEntity.getTopMenu() %>"/>
	<jsp:param name="currMenuName" value="<%= userEntity.getMenuMap().get(category).getMenunm() %>"/>
</jsp:include>
<!--탑메뉴 끝-->


<!--로그인&배너-->
<jsp:include page="../include/loginBox.jsp" flush="false"/>
<!--로그인&배너 끝-->


<!--서브 컨텐츠 시작지점-->
<table width="1000" align="center">
<tr>
<td>   
<div class="h_board">
	<table width="1000" border="0" cellpadding="0" cellspacing="0">
	    <colgroup>
	      <col width="250" height="1000">
	      <col width="./" />
	    </colgroup>
	
	
		<!--좌측메뉴-->
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
			<div class="admin_center" style="">
	
				<!--리스트시작-->
				<div class="admin_con">
					<div class="sub_title_bar"><span class="title"><%=userEntity.getBreadCrumb(category)%></span></div>
				
					<!--게시판 시작-->
					
					<!--검색시작-->
					<fieldset class="search clfix">
						<legend>검색</legend>
						<select name="searchKey">
							<option value="MENU_ID" >메뉴ID</option>
							<option value="MENU_NM" >메뉴명</option>
							<option value="MENU_LVL" >메뉴레벨</option>
						</select>
						
						<input type="text" class="s_id" name="searchValue" title="검색어를입력하세요" style="width:200;" value="" onkeydown="javascript:if(event.keyCode==13)menuInfoList('<%= category %>');"/>
						<input type="image" src="./images/board/btn_search2.gif" alt="SEARCH"  onclick="javascript:menuInfoList('<%= category %>');"/>
					</fieldset>
					
					<!-- 글쓰기 버튼-->
					<div class="paging3">
						<a href="javascript:location.href='jspView.do?jsp=system/menuRegister&category=${category}';">
							<img class='write' src="./images/board/bt_write01.gif">
						</a>
					</div>
					
					<!--게시판 시작-->
					<table class="tb2">
						<colgroup>
							<col width="60">
							<col width="70" />
							<col width="70" />
							<col width="./" />
							<col width="70" />
							<col width="70" />
							<col width="100" />
						</colgroup>
						<thead>
							<tr>
							    <th>메뉴 키</th>
								<th>메뉴 ID</th>
								<th>메뉴 레벨</th>
								<th>메뉴명</th>
								<th>부모메뉴 ID</th>
								<th>사용여부</th>
								<th>메뉴 타입코드</th>
							</tr>
						</thead>
						
						<tbody>
							<!--글 목록 시작-->
							<%
								if ( menuList.size() == 0 ) {
									out.println("<tr><td colspan='6'><b>글이 없습니다</b></td></tr>");
								}
							%>
							<%		
									for ( int ii = 0 ; ii < menuList.size() ; ii ++ ) {
										menu = (MenuDto)menuList.get(ii);
							%>
			 				<tr>
								<td><%= ii+1 %></td>
								<td class="subject">
									<a href = "javascript:menuInfoView('<%=menu.getMenuid()%>', '<%=category%>', '<%=actionType%>');">
										<%=menu.getMenuid()%>
									</a>
								</td>
								<td>
									<%=menu.getMenulvl()%>
								</td>
								<td><%=menu.getMenunm()%></td>
								<td><%=menu.getParmenuid()%></td>
								<td><%=menu.getUseyn()%></td>
								<td><%=menu.getMenutype()%></td>
							</tr>
							<%
								}
							%>			   
							<!--글 목록 끝 -->
						</tbody>
					</table>
	
	
					<!-- 글쓰기 버튼-->
					<div class="paging3">
						<a href="javascript:location.href='jspView.do?jsp=system/menuRegister&category=${category}';">
							<img class='write' src="./images/board/bt_write01.gif">
						</a>
					</div>
					
					<!--페이지링크-->
					<div class="paging">
						
					</div>
				
				</div>
				<!--게시판 끗-->
			</div>
		</td>
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