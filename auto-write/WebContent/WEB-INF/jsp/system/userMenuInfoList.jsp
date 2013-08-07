<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.UserListEntity"%>
<%@ page import="com.jekyll.common.framework.entity.ConditionEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	UserListEntity userListEntity = (UserListEntity) request.getAttribute("UserList");
	List<UserEntity> userList = userListEntity.getUserList();
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
<title><%=leafMenu.getMenunm()%></title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

<!-- 배너 스크립트 -->
<jsp:include page="../include/banner.jsp" flush="false"/>
<!-- 배너 스크립트 끝 -->

<script>
	function userMenuInfoView(seqId, category, actionType, pageNum){
		var frm = document.readForm;
		
		frm.action = "userMenuInfoView.do";
		frm.jsp.value = "system/userMenuInfoView";
		frm.seqId.value = seqId;
		frm.category.value = category;
		frm.actionType.value = actionType;
		frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
	function userInfoList(category, pageNum){
		var frm = document.readForm;
		
		frm.action = "userMenuInfoList.do";
		frm.jsp.value = "system/userMenuInfoList";
		frm.category.value = category;
		frm.actionType.value = "READ";
		frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
</script>
</head>

<body onload="javascript:loadBanner();">

<form name="readForm" method="post">
	<input type="hidden" name="jsp" value=""/>
	<input type="hidden" name="seqId" value=""/>
	<input type="hidden" name="category" value=""/>
	<input type="hidden" name="actionType" value=""/>
	<input type="hidden" name="pageNum" value="<%=userListEntity.getPageNum()%>"/>
</form>
	
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
		
					<!-- 유저 리스트 -->
					<div class="admin_con">
						<div class="sub_title_bar"><span class="title"><%=userEntity.getBreadCrumb(category)%></span></div>
						
						<div>
							<table class="tb2">
								<colgroup>
									<col width="30">
									<col width="120" />
									<col width="120" />
									<col width="120" />
									<col width="70" />
									<col width="./" />
									<col width="100" />
									<col width="70" />
								</colgroup>
								<thead>
									<tr>
									    <th>순번</th>
										<th>ID</th>
										<th>이름</th>
										<th>닉네임</th>
										<th>포인트</th>
										<th>등록일시</th>
										<th>유저타입</th>
										<th>상태코드</th>
									</tr>
								</thead>
								
								<tbody>
									<!-- 리스트 시작-->
									<%		
										for ( int ii = 0 ; ii < userList.size() ; ii ++ ) {
											UserEntity userInfoEntity = userList.get(ii);
									%>
					 				<tr style="cursor: hand;"  onclick="javascript:userMenuInfoView('<%=userInfoEntity.getSeq_id()%>', '<%=category%>', '<%=actionType%>');">
					 					<td><%= ii + 1 %></td>
										<td><%= userInfoEntity.getId() %></td>
										<td><%= userInfoEntity.getName() %></td>
										<td><%= userInfoEntity.getNic() %></td>
										<td><%= userInfoEntity.getPoint() %></td>
										<td><%= userInfoEntity.getReg_datetime() %></td>
										<td><%= userInfoEntity.getTypeName() %></td>
										<td><%= userInfoEntity.getStatusName() %></td>
									</tr>	
									<%
										}
									%>			   
									<!-- 리스트 끝 -->
								</tbody>
							</table>
							
							<!-- 글쓰기 버튼-->
							<div class="paging3">
								<a href="javascript:alert('준비중');">
									<img class='write' src="./images/board/bt_write01.gif">
								</a>
							</div>
							
							<!--페이지링크-->
							<div class="paging">
								<%=userListEntity.getPagination("userInfoList", category, userListEntity.getPageNum())%>
							</div>
						</div>
						
					</div>
					<!-- 유저 리스트 끝 -->
					
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

</body>
</html>