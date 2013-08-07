<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.PageListWrapper"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	PageListWrapper userLoginListEntity = (PageListWrapper) request.getAttribute("UserLoginListEntity");
	List<Map> userLoginList = userLoginListEntity.getPageList();
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
	
	String searchKey = userLoginListEntity.writeSearchKey();
	String searchValue = userLoginListEntity.writeSearchValue();
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
	function userInfoView(seqId, category, actionType, pageNum){
		var frm = document.listForm;
		
		frm.action = "userInfoView.do";
		frm.jsp.value = "system/userInfoView";
		frm.seqId.value = seqId;
		frm.category.value = category;
		frm.actionType.value = actionType;
		frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
	function userPointView(seqId, category, actionType, pageNum){
		var frm = document.listForm;
		
		frm.action = "userPointView.do";
		frm.jsp.value = "system/userPointView";
		frm.seqId.value = seqId;
		frm.category.value = category;
		frm.actionType.value = actionType;
		//frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
	function userLoginList(category, pageNum){
		var frm = document.listForm;
		
		frm.action = "userLoginList.do";
		frm.jsp.value = "system/userLoginList";
		frm.category.value = category;
		frm.actionType.value = "READ";
		frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
</script>
</head>

<body onload="javascript:loadBanner();">

<form name="listForm" method="post">
	<input type="hidden" name="jsp" value=""/>
	<input type="hidden" name="seqId" value=""/>
	<input type="hidden" name="category" value=""/>
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
						
						<!--검색시작-->
						<fieldset class="search clfix">
							<legend>검색</legend>
							<b>Total : <%=userLoginListEntity.getTotalListCount() %>건  </b> 
							&nbsp; &nbsp; &nbsp; &nbsp;
							<select name="type_code">
								<option value="">회원타입</option>
								<option value="P">일반회원</option>
								<option value="B">업소회원</option>
								<option value="W">언니회원</option>
								<option value="A">운영자</option>
								<option value="M">관리자</option>
							</select>
							
							<select name="searchKey">
								<option value="USER_NIC" <%="USER_NIC".equals(searchKey)?"selected":""%>>닉네임</option>
								<option value="USER_ID" <%="USER_ID".equals(searchKey)?"selected":""%>>ID</option>
								<option value="USER_NAME" <%="USER_NAME".equals(searchKey)?"selected":""%>>이름</option>
								<option value="LOGIN_IP" <%="LOGIN_IP".equals(searchKey)?"selected":""%>>IP</option>
							</select>
							
							<input type="text" class="s_id" name="searchValue" title="검색어를입력하세요" style="width:200;" value="<%=searchValue%>" onkeydown="javascript:if(event.keyCode==13)userLoginList('<%= category %>', '<%=userLoginListEntity.getPageNum()%>');"/>
							<input type="image" src="./images/board/btn_search2.gif" alt="SEARCH"  onclick="javascript:userLoginList('<%= category %>', '<%=userLoginListEntity.getPageNum()%>');"/>
						</fieldset>
						<!-- 검색 끝 -->
						
						
						<div>
							<table class="tb2">
								<colgroup>
									<col width="30">
									<col width="100" />
									<col width="100" />
									<col width="100" />
									<col width="100" />
									<col width="60" />
									<col width="./" />
									<col width="100" />
								</colgroup>
								<thead>
									<tr>
									    <th>순번</th>
										<th>유저순번</th>
										<th>유저닉</th>
										<th>유저ID</th>
										<th>유저이름</th>
										<th>유저타입</th>
										<th>로그인일시</th>
										<th>로그인IP</th>
									</tr>
								</thead>
								
								<tbody>
									<!-- 리스트 시작-->
									<%
										//long startSequence = userLoginListEntity.getStartSequenceNumber();
									
										for ( int ii = 0 ; ii < userLoginList.size() ; ii ++ ) {
											Map userInfoMap = userLoginList.get(ii);
									%>
					 				<tr>
					 					<td>
					 						<a href="javascript:userInfoView('<%=userInfoMap.get("SEQ_ID")%>', '<%=category%>', '<%=actionType%>', '<%=userLoginListEntity.getPageNum()%>');">
					 							<%=userInfoMap.get("SEQ_ID") %>
					 						</a>
					 					</td>
										<td>
					 						<a href="javascript:userInfoView('<%=userInfoMap.get("SEQ_ID")%>', '<%=category%>', '<%=actionType%>', '<%=userLoginListEntity.getPageNum()%>');">
					 							<%=userInfoMap.get("USER_SEQ_ID") %>
					 						</a>
					 					</td>
										<td>
					 						<a href="javascript:userInfoView('<%=userInfoMap.get("SEQ_ID")%>', '<%=category%>', '<%=actionType%>', '<%=userLoginListEntity.getPageNum()%>');">
					 							<%=userInfoMap.get("USER_NIC") %>
					 						</a>
					 					</td>
										<td>
					 						<a href="javascript:userInfoView('<%=userInfoMap.get("SEQ_ID")%>', '<%=category%>', '<%=actionType%>', '<%=userLoginListEntity.getPageNum()%>');">
					 							<%=userInfoMap.get("USER_ID") %>
					 						</a>
					 					</td>
										<td>
					 						<a href="javascript:userPointView('<%=userInfoMap.get("SEQ_ID")%>', '<%=category%>', '<%=actionType%>', '<%=userLoginListEntity.getPageNum()%>');">
					 							<%=userInfoMap.get("USER_NAME") %>
					 						</a>
					 					</td>
										<td>
											<%
												String userTypeCode = userInfoMap.get("TYPE_CODE").toString();
												String userTypeName = "";
												if ( "B".equals(userTypeCode) ){
													userTypeName = "업소";
												} else if ( "M".equals(userTypeCode) ){
													userTypeName = "관리자";
												} else if ( "A".equals(userTypeCode) ){
													userTypeName = "운영자";
												} else if ( "P".equals(userTypeCode) ){
													userTypeName = "일반";
												} else if ( "W".equals(userTypeCode) ){
													userTypeName = "아가씨";
												} else {
													userTypeName = userTypeCode;
												}
												out.println(userTypeName);
											%>
										</td>
										<td><%= userInfoMap.get("LOGIN_DATETIME").toString().substring(0, 19) %></td>
										<td><%= userInfoMap.get("LOGIN_IP") %></td>
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
								<%=userLoginListEntity.getPagination("userLoginList", category, userLoginListEntity.getPageNum())%>
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

</form>
</body>
</html>