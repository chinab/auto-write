<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.PageListWrapper"%>
<%@ page import="com.jekyll.common.framework.entity.ConditionEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, MenuDto> menuMap = userEntity.getMenuMap();
	
	String category = request.getAttribute("category").toString();
	
	MenuDto leafMenu = (MenuDto)userEntity.getMenuMap().get(category);
	String leftMenuStr = leafMenu.getParmenuid();
	MenuDto leftMenu = (MenuDto)userEntity.getMenuMap().get(leftMenuStr);
	
	PageListWrapper pageListEntity = (PageListWrapper) request.getAttribute("PointLogListEntity");
	List<Map> pointLogList = (List<Map>)pageListEntity.getPageList();
	
	Map actionTypeMap = new HashMap();
	actionTypeMap.put("READ", "읽기");
	actionTypeMap.put("RECOMMEND", "추천");
	actionTypeMap.put("WRITE", "쓰기");
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

	function goList(){
		location.href = "userInfoList.do?jsp=system/userInfoList&category=${category}&pageNum=${pageNum}";
	}

	function userPointView(category, pageNum){
		var frm = document.readForm;
		
		frm.action = "userPointView.do";
		frm.jsp.value = "system/userPointView";
		frm.category.value = category;
		frm.actionType.value = "READ";
		//frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
</script>
</head>

<body onload="javascript:loadBanner();">

<form name="readForm" method="post">
	<input type="hidden" name="category" value=""/>
	<input type="hidden" name="actionType" value=""/>
	<input type="hidden" name="seqId" value=""/>
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
					
					<b>Total : <%=pageListEntity.getTotalListCount() %>건  </b>
					
					<!-- 본문 시작-->
					<table class="tb3" style="bottom-margin:15;">
						<colgroup>
							<col width="60">
							<col width="100" />
							<col width="300" />
							<col width="140" />
						</colgroup>
						<thead>
							<tr>
							   <th>순번</th>
							   <th>획득 포인트</th>
							   <th>포인트 획득 사유</th>
							   <th>포인트 획득 시각</th>
							</tr>
						</thead>
						<tbody>
							
					<%
						if ( pointLogList == null || pointLogList.size() == 0 ) {
					%>
							<tr>
								<td colspan="4">
									<strong>포인트 획득 기록이 없습니다.</strong>
								</td>
							</tr>
					<%
						} else {
							String categoryMajorName = "";
							String categoryName = "";
							String actionName = "";
							String majorCategory = "";
							String categoryStr = "";
							
							for ( int ii = 0 ; ii < pointLogList.size() ; ii ++ ) {
								categoryName = "";
								categoryMajorName = "";
								
								Map pointLogMap = pointLogList.get(ii);
								if ( pointLogMap.get("CATEGORY") != null ){
									categoryStr = pointLogMap.get("CATEGORY").toString();
								}
								
								if (categoryStr.length() > 2 ){
									majorCategory = categoryStr.toString().substring(0,2) + "0000";
									if ( menuMap.get(majorCategory) != null ){
										categoryMajorName = menuMap.get(majorCategory).getMenunm();
									} else {
										categoryMajorName = categoryStr;
									}
									if ( menuMap.get(categoryStr) != null ) {
										categoryName = menuMap.get(categoryStr).getMenunm();
									}
									
									if ( categoryName.length() > 0 ) {
										categoryName = categoryMajorName + " - " + categoryName;
									} else {
										categoryName = categoryMajorName;
									}
								}
								
								if ( pointLogMap.get("ACTION_TYPE") != null && actionTypeMap.get(pointLogMap.get("ACTION_TYPE")) != null ){
									actionName = actionTypeMap.get(pointLogMap.get("ACTION_TYPE")).toString();
								} else {
									actionName = "";
								}
								
								if ( "LOGIN".equals(categoryName) ){
									actionName = "";
								}
								
					%>
							<tr>
								<td><%= ii + 1 %></td>
								<td><%=pointLogMap.get("ACTION_POINT")%></td>
								<td><%=categoryName%> <%=actionName%></td>
								<td><%=pointLogMap.get("ACTION_DATETIME").toString().substring(0, 16)%></td>
							</tr>
					<%		
							}  // end for
						} // end if
					%>
						</tbody>
					</table>
					
					<!--페이지링크-->
					<div class="paging">
						<%=pageListEntity.getPagination("userPointView", category, pageListEntity.getPageNum())%>
					</div>
							
					<!-- 수정, 목록 버튼-->
					<div class="paging3">
						<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
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
