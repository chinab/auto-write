<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder, java.net.URLDecoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.MemoEntity"%>
<%@ page import="com.autowrite.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);

	MemoEntity memoEntity = (MemoEntity) request.getAttribute("MemoContent");
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>쪽지 읽기</title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

</head>
<script>
	function goList(){
		location.href='memoListView.do?jsp=popup/memoList&category=${category}';
	}
</script>
<body>
<form name="memoForm" method="post">
	<input type="hidden" name="category" value="${category}"/>
		
<div class="memo_title">
	<span style="width:300px;"><strong>쪽지 읽기</strong></span>
</div>

<!-- <div class="memo_edit"> -->
<!-- 	<span style="width:480px;"><b>쪽지 받기 설정 </b>: <input name="mb_nicksc" type="radio" value="" checked>친구에게만&nbsp;&nbsp;<input name="mb_nicksc" type="radio" value="" >모두에게&nbsp;&nbsp;<INPUT TYPE="submit" VALUE="설정" style="width:40;height:20;background-color:#ffffff;border:1 solid #c0c0c0;"></span> -->
<!-- </div> -->

<div style="width:600px;">
	<div class="butt"><a href="javascript:location.href='memoListView.do?jsp=popup/memoList&category=01'">받은쪽지함</a></div>
	<div class="butt"><a href="javascript:location.href='memoListView.do?jsp=popup/memoList&category=02'">보낸쪽지함</a></div>
	<div class="butt"><a href="javascript:location.href='memoListView.do?jsp=popup/memoList&category=03'">지운쪽지함</a></div>
	<div class="butt"><a href="javascript:location.href='memoListView.do?jsp=popup/memoList&category=04'">보관쪽지함</a></div>
	<div class="butt"><a href="javascript:location.href='jspView.do?jsp=popup/memoWrite'">쪽지보내기</a></div>
</div>
<br/><br/>

<div style="width:600; margin-top:10px; margin-left:6px;" >	
	<table class="tb3" style="bottom-margin:15;">
		<colgroup>
			<col width="100" />
			<col width="10" />
			<col width="490"/>
		</colgroup>
		
		<tbody>
			<tr>
				<td colspan="3" class="subject2">
				</td>
			</tr>
			
			<tr>
				<td class="subject"><b>보낸사람</b></td>
				<td><img src="./images/board_line.gif" width="1" height="22" /></td>
				<td class="subject">
					<%= memoEntity.getSnd_user_nic() %>
				</td>
			</tr>
			
			<tr>
				<td class="subject"><b>쪽지 내용</b></td>
				<td><img src="./images/board_line.gif" width="1" height="50px" /></td>
				<td class="subject">
					<%= memoEntity.getContent().replaceAll("\r\n", "<br/>") %>
				</td>
			</tr>

<!-- 			<tr> -->
<!-- 				<td class="subject"><b>첨부파일</b></td> -->
<!-- 				<td ><img src="./images/board_line.gif" width="1" height="22" /></td> -->
<!-- 				<td class="subject"><input type="file" name="filename"></td> -->
<!-- 			</tr> -->
            
		</tbody>
	</table>
	
	<br/>
	
	<!--전체선택 등 버튼-->
	<div class="paging">
		<table><tr><td align="center">
			<div class="butt_mini"><a href="javascript:goList();">목록</a></div>
			<div class="butt_mini"><a href="javascript:window.close();">창닫기</a></div>
		</td></tr></table>
	</div>
	
</div>

	
</form>	
</body>
</html>	
