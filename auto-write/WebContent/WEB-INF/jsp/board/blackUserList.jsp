<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.BoardEntity"%>
<%@ page import="com.jekyll.common.framework.entity.BoardListEntity"%>
<%@ page import="com.jekyll.common.framework.entity.ConditionEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	List<BoardEntity> boardList = null;
	ConditionEntity cond = null;
	
	if ( request.getAttribute("BoardList") != null ){
		boardList = (List<BoardEntity>) request.getAttribute("BoardList");
	}
	
	BoardListEntity boardListEntity = (BoardListEntity) request.getAttribute("BoardListEntity");
	
	if ( boardList != null && boardList.size() > 0 &&  boardList.get(0).getConditionInfo() != null ){
		cond = boardList.get(0).getConditionInfo();
	} else {
		cond = new ConditionEntity();
	}
	
	String category = request.getAttribute("category").toString();
	String region = "";
	
	if ( request.getAttribute("region") != null ) {
		region = request.getAttribute("region").toString();
	}
	
	MenuDto leafMenu = (MenuDto)userEntity.getMenuMap().get(category);
	String leftMenuStr = leafMenu.getParmenuid();
	MenuDto leftMenu = (MenuDto)userEntity.getMenuMap().get(leftMenuStr);
	
	String searchKey = boardListEntity.writeSearchKey();
	String searchValue = boardListEntity.writeSearchValue();
	
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
	function readBoard(seqId, category, region, pageNum){
		var frm = document.boardForm;
		
		frm.action = "blackUserView.do";
		frm.jsp.value = "board/blackUserView";
		frm.seqId.value = seqId;
		frm.category.value = category;
		frm.region.value = region;
		frm.pageNum.value = pageNum;
		
		//frm.method = "get";frm.submit();
		
		var hrefStr = "blackUserView.do?jsp=board/blackUserView";
		hrefStr += "&seqId=" + seqId;
		hrefStr += "&category=" + category;
		hrefStr += "&region=" + region;
		hrefStr += "&pageNum=" + pageNum;
		
		location.href = hrefStr;
	}
	
	function listBoard(category, region, pageNum){
		var frm = document.boardForm;
		
		frm.action = "blackUserList.do";
		frm.jsp.value = "board/blackUserList";
		frm.category.value = category;
		frm.region.value = region;
		frm.pageNum.value = pageNum;
		
		//frm.method = "get";frm.submit();
		
		var hrefStr = "blackUserList.do?jsp=board/blackUserList";
		hrefStr += "&category=" + category;
		hrefStr += "&region=" + region;
		hrefStr += "&pageNum=" + pageNum;
		
		location.href = hrefStr;
	}
	
	function viewUserInfo(userSeqId){
		alert("userInfo:" + userSeqId);
	}
</script>

<body onLoad="javascript:loadBanner();">
	<form name="boardForm" method="post">
		<input type="hidden" name="jsp" value=""/>
		<input type="hidden" name="seqId" value=""/>
		<input type="hidden" name="category" value="<%=category%>"/>
		<input type="hidden" name="region" value="<%=region%>"/>
		<input type="hidden" name="pageNum" value="<%=boardListEntity.getPageNum()%>"/>

	
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
							<option value="title" <%="title".equals(searchKey)?"selected":""%>>제목</option>
							<option value="content" <%="content".equals(searchKey)?"selected":""%>>내용</option>
							<option value="user_nic" <%="user_nic".equals(searchKey)?"selected":""%>>닉네임</option>
						</select>
						
						<input type="text" class="s_id" name="searchValue" title="검색어를입력하세요" style="width:200;" value="<%=searchValue%>" onkeydown="javascript:if(event.keyCode==13)listBoard('<%= category %>', '<%= region %>', '<%=boardListEntity.getPageNum()%>');"/>
						<input type="image" src="./images/board/btn_search2.gif" alt="SEARCH"  onclick="javascript:listBoard('<%= category %>', '<%= region %>', '<%=boardListEntity.getPageNum()%>');"/>
					</fieldset>
	 				
					<!-- 지역구분 시작 -->
					<%=boardListEntity.getRegionTab(category, region)%>
					<!-- 지역구분 끝 -->
					
					<!--게시판 시작-->
					<table class="tb2">
						<colgroup>
							<col width="60">
							<col width="./" />
							<col width="70" />
							<col width="70" />
							<col width="70" />
							<col width="70" />
						</colgroup>
						<thead>
							<tr>
							    <th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>등록일</th>
								<th>조회수</th>
								<th>추천</th>
							</tr>
						</thead>
						
						<tbody>
							<!--글 목록 시작-->
							<%
								if ( boardList.size() == 0 ) {
									out.println("<tr><td colspan='6'><b>글이 없습니다</b></td></tr>");
								}
							%>
							<%		
								String regionStr = "";
								for ( int ii = 0 ; ii < boardList.size() ; ii ++ ) {
									BoardEntity boardEntity = boardList.get(ii);
									
									if ( category.startsWith("02") ||  category.startsWith("03") ||  category.startsWith("04") ){
										if ( boardEntity.getRegion().length() > 0 ){
											regionStr = "<a href = \"javascript:listBoard('";
											regionStr += boardEntity.getCategory();
											regionStr += "', '";
											regionStr += boardEntity.getRegion();
											regionStr += "', '";
											regionStr += boardEntity.getPageNum();
											regionStr += "');\">\n<font color=\"blue\"><b>";
											regionStr += "[" + boardEntity.getRegion() + "]";
											regionStr += "</b></font></a>&nbsp;";
										} else {
											regionStr = "";
										}
									} else {
										regionStr = "";
									}
							%>
			 					<tr>
								<td><%= boardEntity.getSeq_id() %></td>
								<td class="subject">
									<%=regionStr%>
									<a href = "javascript:readBoard('<%=boardEntity.getSeq_id()%>', '<%=boardEntity.getCategory()%>', '<%=boardEntity.getRegion()%>', '<%=boardListEntity.getPageNum()%>');">
										<%=boardEntity.getTitle()%>
									</a>
									<%= boardEntity.getWebReply_cnt() %>
								</td>
								<td>
									<%=boardEntity.getUser_nic()%>
								</td>
								<td><%=boardEntity.getWriteBoardDateTime()%></td>
								<td><%=boardEntity.getHit()%></td>
								<td><%=boardEntity.getRecommend()%></td>
							</tr>	
							<%
								}
							%>			   
							<!--글 목록 끝 -->
						</tbody>
					</table>
	
	
					<!-- 글쓰기 버튼-->
					<div class="paging3">
						<a href="javascript:location.href='jspView.do?jsp=board/blackUserWrite&category=${category}';">
							<img class='write' src="./images/board/bt_write01.gif">
						</a>
					</div>
					
					<!--페이지링크-->
					<div class="paging">
						<%=boardListEntity.getPagination("listBoard", category, region, boardListEntity.getPageNum())%>
					</div>
				
				</div>
				<!--게시판 끗-->
			</div>
		</td>
	</table>
</div>


<!--푸터-->
<div class="bot_bg">
<div class="bot_add01"><img src="./images/bottom.jpg"></div>
</div>

	</form>
</body>
</html>
 
 

  