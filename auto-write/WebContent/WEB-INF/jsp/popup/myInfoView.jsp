<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardEntity"%>
<%@ page import="com.autowrite.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>MY INFORMATION</title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

<script>
	function readBoard(seqId, category, region, pageNum){
		var frm = document.boardForm;
		
		frm.action = "boardContentView.do";
		frm.jsp.value = "board/boardView";
		frm.seqId.value = seqId;
		frm.category.value = category;
		frm.region.value = region;
		frm.pageNum.value = pageNum;
		
		frm.submit();
	}
</script>

</head>

<body>

<div class="memo_title">
	<span style="width:300px;">MY INFORMATION</span>
</div>

<div style="width:600; margin-top:10px;" >
	<table class="tb3" style="bottom-margin:15;">
		<colgroup>
			<col width="80" />
			<col width="10" />
			<col width="590"/>
		</colgroup>
		
		<tbody>
			<tr>
				<td class="subject"><b>이름</b></td>
				<td><img src="./images/board_line.gif" width="1" height="22" /></td>
				<td class="subject">
					<%=userEntity.getName()%>
				</td>
			</tr>
			<tr>
				<td class="subject"><b>ID</b></td>
				<td><img src="./images/board_line.gif" width="1" height="22" /></td>
				<td class="subject">
					<%=userEntity.getId()%>
				</td>
			</tr>
			<tr>
				<td class="subject"><b>닉네임</b></td>
				<td><img src="./images/board_line.gif" width="1" height="22" /></td>
				<td class="subject">
					<%=userEntity.getNic()%>
				</td>
			</tr>
			<tr>
				<td class="subject"><b>E-Mail</b></td>
				<td><img src="./images/board_line.gif" width="1" height="22" /></td>
				<td class="subject">
					<%=userEntity.getEmail()%>
				</td>
			</tr>
			<tr>
				<td class="subject"><b>포인트</b></td>
				<td><img src="./images/board_line.gif" width="1" height="22" /></td>
				<td class="subject">
					<%=userEntity.getPoint()%>
				</td>
			</tr>
			<tr>
				<td class="subject"><b>가입일</b></td>
				<td><img src="./images/board_line.gif" width="1" height="22" /></td>
				<td class="subject">
					<%=userEntity.getReg_datetime()%>
				</td>
			</tr>
		</tbody>
	</table>
	
	<br/><br/><br/><br/>
	
	<!--전체선택 등 버튼-->
	<div class="paging3">
		<a href="javascript:window.close();"><img class='write' src="./images/btn_close.gif"></a>
	</div>
</div>

</body>
</html>	
