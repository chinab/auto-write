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
	
	List<UserEntity> userList = new ArrayList<UserEntity>();
	ConditionEntity cond = null;
	
	if ( request.getAttribute("UserList") != null ){
		userList = (List<UserEntity>) request.getAttribute("UserList");
	}
	if ( userList != null && userList.size() > 0 &&  userList.get(0).getConditionInfo() != null ){
		cond = userList.get(0).getConditionInfo();
	} else {
		cond = new ConditionEntity();
	}
	
	List<BoardEntity> boardList = new ArrayList<BoardEntity>();
	if ( request.getAttribute("UserBoardList") != null ){
		boardList = (List<BoardEntity>) request.getAttribute("UserBoardList");
	}
	
	String category = request.getAttribute("category").toString();
	
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
	function readBoard(seqId, category, region){
		var frm = document.listForm;
		
		frm.action = "boardContentView.do";
		frm.seqId.value = seqId;
		frm.category.value = category;
		frm.region.value = region;
		
		//frm.method = "get";frm.submit();
		
		var hrefStr = "boardContentView.do?jsp=board/boardView";
		hrefStr += "&seqId=" + seqId;
		hrefStr += "&category=" + category;
		hrefStr += "&region=" + region;
		
		location.href = hrefStr;
	}
	
	function searchUser(userSeqId){
		var frm = document.searchForm;
		
		frm.action = "userSearch.do";
		frm.userSeqId.value = userSeqId;
		
		frm.method = "get";frm.submit();
	}
	
	function viewUserInfo(userSeqId){
		alert("userInfo:" + userSeqId);
	}
</script>

<body onLoad="javascript:loadBanner();">
	<form name="listForm" method="post">
		<input type="hidden" name="jsp" value="board/boardView"/>
		<input type="hidden" name="seqId" value=""/>
		<input type="hidden" name="category" value=""/>
		<input type="hidden" name="region" value=""/>
		<input type="hidden" name="pageNum" value="<%=cond.getPageNumber()%>"/>
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


<!--서브 컨텐츠 시작지점-->
<table width="1000" align="center">
<tr>
<td>   
<div class="h_board">
	<table width="1000" border="0" cellpadding="0" cellspacing="0">
	    <colgroup>
	      <col width="250">
	      <col width="./" />
	    </colgroup>
		
		<tr>
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
		
					<!-- 유저 검색 -->
					<div class="admin_con">
						<div class="sub_title_bar"><span class="title"><%=userEntity.getBreadCrumb(category)%></span></div>
					
						<div>
		 					<strong>
		 						회원 검색 후 회원 목록을 클릭하시면 해당 회원이 쓴 글 목록을 보실 수 있습니다.
		 					</strong>
		 				</div>
		 				
		 				<!--게시판 시작-->
						<!--검색시작-->
						<form name="searchForm" method="post">
							<input type="hidden" name="jsp" value="userInfo/userSearchList"/>
							<input type="hidden" name="category" value="${category}"/>
							<input type="hidden" name="userSeqId" value=""/>
							<fieldset class="search clfix">
								<legend>검색</legend>
								<strong>회원 ID / NIC :</strong> 
								<input type="text" class="s_id" name="USER_SEARCH_KEY" id="USER_SEARCH_KEY" title="회원 닉네임 또는 ID를입력하세요" style="width:200;" value="${USER_SEARCH_KEY}" onkeydown="javascript:if(event.keyCode==13)searchUser();"/>
								<input type="image" src="./images/board/btn_search2.gif" alt="SEARCH"  onclick="javascript:searchUser();return false;"/>
							</fieldset>
						</form>
		 				
		 				<div>
						<!--유저 검색 리스트 시작-->
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
									<th>가입일</th>
									<th>유저타입</th>
									<th>상태코드</th>
								</tr>
							</thead>
							
							<tbody>
								<!--유저 목록 시작-->
								<%		
									for ( int ii = 0 ; ii < userList.size() ; ii ++ ) {
										UserEntity UserEntity = userList.get(ii);
								%>
				 				<tr style="cursor: hand;" onclick="javascript:searchUser('<%=UserEntity.getSeq_id()%>');">
				 					<td><%= ii + 1 %></td>
									<td><%= UserEntity.getId() %></td>
									<td><%= UserEntity.getName() %></td>
									<td><%= UserEntity.getNic() %></td>
									<td><%= UserEntity.getPoint() %></td>
									<td><%= UserEntity.getReg_datetime() %></td>
									<td><%= UserEntity.getTypeName() %></td>
									<td><%= UserEntity.getStatusName() %></td>
								</tr>	
								<%
									}
								%>			   
								<!-- 유저 목록 끝 -->
							</tbody>
						</table>
						</div>
						
						<br/>
						
						<div>
						<%
							if ( boardList != null && boardList.size() > 0 ) {
						%>
						<b>총 게시글 수 : <%=boardList.size()%> 건. ( 최근 20개만 표시됩니다. )</b>
						<br/><br/>
						<!--게시판 시작-->
						<table class="tb2">
							<colgroup>
								<col width="60">
								<col width="./" />
								<col width="150" />
								<col width="70" />
								<col width="70" />
							</colgroup>
							<thead>
								<tr>
								    <th>번호</th>
									<th>제목</th>
									<th>등록일</th>
									<th>조회수</th>
									<th>추천</th>
								</tr>
							</thead>
							
							<tbody>
								<!--글 목록 시작-->
								<%
									int maxLoop = 20;
									if ( boardList.size() < maxLoop ){
										maxLoop = boardList.size();
									}
									for ( int ii = 0 ; ii < maxLoop ; ii ++ ) {
										BoardEntity boardEntity = boardList.get(ii);
								%>
				 				<tr>
									<td><%= boardEntity.getSeq_id() %></td>
									<td class="subject">
										<a href = "javascript:readBoard('<%=boardEntity.getSeq_id()%>', '<%=boardEntity.getCategory()%>', '<%=boardEntity.getRegion()%>');">
											<%=boardEntity.getTitle()%>
										</a>
									</td>
									<td><%=boardEntity.getWrite_datetime()%></td>
									<td><%=boardEntity.getHit()%></td>
									<td><%=boardEntity.getRecommend()%></td>
								</tr>	
								<%
									}
								%>			   
								<!--글 목록 끝 -->
							</tbody>
						</table>
						<%
							} else if ( userList.size() != 0 
									&& request.getAttribute("userSeqId") != null 
									&& !"undefined".equals(request.getAttribute("userSeqId"))
									&& !"".equals(request.getAttribute("userSeqId"))
									){
						%>
						<table class="tb2">
							<tr>
								<td>
									글이 없습니다.
								</td>
							</tr>
						</table>
						<%
							}
						%>
						</div>
		
						<!--수정,삭제,버튼
						<div class="paging3">
							<a href="javascript:location.href='jspView.do?jsp=board/boardWrite&category=${category}';">
								<img class='write' src="./images/board/bt_write01.gif">
							</a>
						</div>
						-->
						
						<!--페이지링크
						<div class="paging">
							<img src='./images/btn_pagingFirst.gif'>&nbsp;&nbsp;&nbsp;<span class='gray'>1&nbsp;&nbsp;2&nbsp;&nbsp;3&nbsp;&nbsp;5&nbsp;&nbsp;6&nbsp;&nbsp;7&nbsp;&nbsp;8&nbsp;&nbsp;9 &nbsp;&nbsp;10</span>&nbsp;&nbsp;&nbsp;<img src='./images/btn_pagingEnd.gif'>
						</div>
						-->
					</div>
					<!-- 유저 검색 리스트 끝 -->
					
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
 
 

  