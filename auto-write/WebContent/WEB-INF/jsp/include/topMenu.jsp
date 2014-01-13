<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>

<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	userEntity.getId();
	userEntity.getType_code();
%>
<!--탑 텍스트 메뉴-->
<div class="top_Menu_All">
	<table width="1000" cellpadding="0" cellspacing="0" border="0" align="center">
		<tr>
			<td>
				<div class="t_Menus">
					<span class="TopMenus">
						<a href="./autowriteMasterList.do?jsp=autowrite/autowriteMasterList">HOME</a>&nbsp; | &nbsp;
<!-- 						<a href="/sitemap/sitemap.asp">회원가입</a>&nbsp; | &nbsp; -->
						<a href="./index.jsp">로그아웃</a>&nbsp;&nbsp;
					</span>
				</div>
			</td>
		</tr>
	</table>
</div>


<!--탑 메뉴 로고등-->
<div class="top_Menu_All1">
	<table width="1000" cellpadding="0" cellspacing="0" border="0" align="center">
		<tr>
			<td>
				<div class="Top_Logo"><a href="/" onfocus="blur()"><img src="images/top_logo.gif"></a></div>
				
				<div class="Top_Main_menus">
					<div class="Menus_tl"><a href="autowriteMasterList.do?jsp=autowrite/autowriteMasterList" onfocus="blur()" >자동등록</a></div>
					<div class="Menus_t"><a href="contentsList.do?jsp=contents/contentsList" onfocus="blur()" >본문설정</a></div>
					<div class="Menus_t"><a href="siteList.do?jsp=site/siteList" onfocus="blur()" >사이트설정</a></div>
					<div class="Menus_t"><a href="businessInfoList.do?jsp=individual/businessInfoList" onfocus="blur()" >개인설정</a></div>
<!-- 					<div class="Menus_t"><a href="boardListView.do?jsp=board/boardList&category=020000" onfocus="blur()" >알림마당</a></div> -->
					<div class="Menus_t"><a href="addressList.do?jsp=address/addressList" onfocus="blur()" >주소록</a></div>
					<%
						if ( "M".equals(userEntity.getType_code()) ) {
					%>
					<div class="Menus_t"><a href="userInfoList.do?jsp=system/userInfoList" onfocus="blur()" >SYSTEM</a></div>
					<%
						}
					%>
				</div>
			</td>
		</tr>
	</table>
</div>

