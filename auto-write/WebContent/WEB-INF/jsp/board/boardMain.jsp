<%@page import="java.util.SortedMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="java.util.Set, java.util.Arrays"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.BoardEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Object[] boardListArray = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	Map<String, List<BoardEntity>> boardListMap = (Map<String, List<BoardEntity>>) request.getAttribute("BoardListMap");
	String category = request.getAttribute("selectedMenu").toString();
	// System.out.println("category:" + category);
	
	MenuDto leftMenu = (MenuDto)userEntity.getMenuMap().get(category);
	// System.out.println("leftMenuName:" + leftMenu.getMenunm());
	
	String selectedMenuId = (String) request.getParameter("selectedMenu");
	boolean bfind = false;
	for (int i = 0; i < menus.size(); i++) {
		menu = (MenuDto) menus.get(i);
		if (menu.getMenuid().equals(selectedMenuId)) {
			bfind = true;
			break;
		}
	}
%>
<%
	if ( boardListMap != null && boardListMap.keySet() != null ) {
		Set set = boardListMap.keySet();
		boardListArray = set.toArray();
		Arrays.sort(boardListArray);
	} else {
%>
	<center><span style="width:100%; font-family:verdana, sans-serif; text-align:center;font-weight:bold; font-size:1.5em; color:#357EC7">글이 없습니다.</span></center>	
<%
	}
%>

<html>
<%
	String error = (String)request.getAttribute("result");
    if (error != null) {
		String msg = "No user information found.";
%>
<head>
<script language="javascript">
	alert("<%=msg%>");
</script>
<%
    }
%>
<title>HWARU</title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

<!-- 배너 스크립트 -->
<jsp:include page="../include/banner.jsp" flush="false"/>
<!-- 배너 스크립트 끝 -->

<script language="JavaScript">
	var previousMenu;
	
	window.onunload = function() {
		//doLogout();
	};
	
	function readBoard(seqId, category, region){
		var frm = document.readForm;
		
		if ( category == "080100" ){
			frm.action = "blackUserView.do";
		} else {
			frm.action = "boardContentView.do";
		}
		
		frm.seqId.value = seqId;
		frm.category.value = category;
		frm.region.value = region;
		
		//frm.method = "get";frm.submit();
		
		var hrefStr = "boardContentView.do?jsp=board/boardView";
		if ( category == "080100" ){
			hrefStr = "blackUserView.do?jsp=board/boardView";
		}
		hrefStr += "&seqId=" + seqId;
		hrefStr += "&category=" + category;
		hrefStr += "&region=" + region;
		
		location.href = hrefStr;
	}
</script>
</head>
<body onLoad="javascript:loadBanner();">
	<form name="readForm" method="post">
		<input type="hidden" name="jsp" value="board/boardView"/>
		<input type="hidden" name="seqId" value=""/>
		<input type="hidden" name="category" value=""/>
		<input type="hidden" name="region" value=""/>
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


<!-- 서브 컨텐츠 시작지점 -->
<table width="1000" align="center">
<tr>
<td>   
<div class="h_board">
	<table width="1000" border="0" cellpadding="0" cellspacing="0">
	    <colgroup>
	      <col width="250" />
	      <col width="375" />
	      <col width="375" />
	    </colgroup>
	
		<!--좌측메뉴-->
		<td valign="top">
			<div class="sub_title">
				<div class="je"><%=leftMenu.getMenunm()%></div>
				<%=userEntity.getLeftMenu(category)%>
			</div>
			
<!-- 			<div> -->
<%-- 				<jsp:include page="../include/leftChatting.jsp" flush="false"/> --%>
<!-- 			</div> -->
		</td>
		<!-- 좌측메뉴 끝-->
		
		<!-- 게시판 summary -->
		<td valign="top">
			<%
				String rowClass = "board_line01";
				int index = 0;
				
				for ( int arrayIndex = 0 ; arrayIndex < boardListArray.length ; arrayIndex ++ ) {
					index ++;
					
					String menuId = boardListArray[arrayIndex].toString();
					menu = (MenuDto)userEntity.getMenuMap().get(menuId);
					
					if ( arrayIndex > boardListArray.length/2 ) {
						rowClass = "board_line02";
					}
			%>
			<div class="<%=rowClass%>">
				<div class="title_bar"><span class="title"><%=menu.getMenunm()%></span></div>
				<div id="notice_table" class="organCnt06">
					<ul class="long_pre" >
					<%		
						List<BoardEntity> boardList = boardListMap.get(menuId);
						
						if ( boardList.size() == 0 ) {
							out.print("<li><span class='zum'>&nbsp;</span>글이 없습니다.</li>");
						}
						
						for ( int ii = 0 ; ii < boardList.size() ; ii ++ ) {
							BoardEntity boardEntity = boardList.get(ii);
							out.print("<li><span class='zum'>&nbsp;</span><span class='l_ir'>");
							out.print("<a href = \"javascript:readBoard('");
							out.print(boardEntity.getSeq_id());
							out.print("', '");
							out.print(boardEntity.getCategory());
							out.print("', '");
							out.print(boardEntity.getRegion());
							out.print("');\">");
							out.print(boardEntity.getTitle(28));
							out.print("</a>");
							out.print("</span><span class='data'>");
							out.print(boardEntity.getWriteBoardDateTime());
							out.print("</span></li>");
						}
					%>
					</ul>
				</div>
			</div>
			<%
					// supplimentary div section for empty space.
					if ( arrayIndex == boardListArray.length - 1 && boardListArray.length%2 == 1) {
						out.println("<div class='");
						out.println(rowClass);
						out.println("'></div>");
					}
					
					if ( boardListArray.length%2 == 1 && arrayIndex == boardListArray.length/2 ) {
						out.print("</td>\n		<td>");
					} else if ( boardListArray.length%2 == 0 && arrayIndex == boardListArray.length/2 - 1) {
						out.print("</td>\n		<td>");
					} else {
						;
					}
					
				}
			%>
		</td>
		<!-- 게시판 summary 끝-->

	</table>
</div>
</td></tr></table>
<!-- 서브 컨텐츠 끝 -->

<!--푸터-->
<div class="bot_bg">
	<div class="bot_add01"><img src="./images/bottom.jpg"></div>
</div>

</body>
</html>