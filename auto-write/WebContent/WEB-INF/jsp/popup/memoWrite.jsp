<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder, java.net.URLDecoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardEntity"%>
<%@ page import="com.autowrite.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	
	request.setCharacterEncoding("UTF-8");
	String rcvUserNic = request.getParameter("rcvUserNic");
	if ( rcvUserNic == null ){
		rcvUserNic = "";
	}
	
	// System.out.println("rcvUserNic:"+rcvUserNic);
	// System.out.println("rcvUserNic:"+URLDecoder.decode("%EA%B4%80%EB%A6%AC%EC%9E%90%EB%8B%89", "UTF-8"));
	// System.out.println("rcvUserNic:"+rcvUserNic);
	// System.out.println("rcvUserNic:"+URLDecoder.decode(rcvUserNic, "UTF-8"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>쪽지 쓰기</title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

</head>
<script>
	function writeMemo(){
		var frm = document.writeForm;
		
		if ( validate() ) {
			frm.action="writeMemo.do";
			
			frm.submit();
			
			return false;
		}
	}
	
	function validate(){
		var frm = document.writeForm;
		
		if (frm.rcv_user_seq_id.value.length == 0) {
			alert ("수신자가 없습니다.");
			return false;
		}
		if (frm.content.value.length == 0) {
			alert ("내용이 없습니다.");
			return false;
		}
		
		return true;
	}
	
	function searchUser(){
	    noticeWindow  =  window.open('jspView.do?jsp=popup/searchUser','SearchUser','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=600,height=500, left=600 top=400');
	    noticeWindow.focus();
	}
</script>
<body>
<form name="writeForm" method="post">
	<input type="hidden" id="rcv_user_seq_id" name="rcv_user_seq_id" value="${rcvUserSeqId}"/>
	<input type="hidden" id="rcv_user_id" name="rcv_user_id" value="${rcvUserId}"/>
	
<div class="memo_title">
	<span style="width:300px;"><strong>쪽지 쓰기</strong></span>
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
			<col width="./"/>
		</colgroup>
		
		<tbody>
			<tr>
				<td colspan="3" class="subject2">
				
				</td>
			</tr>
			
			<tr>
				<td class="subject"><b>받는사람</b></td>
				<td><img src="./images/board_line.gif" width="1" height="22" /></td>
				<td class="subject">
					<input id="rcv_user_nic" name="rcv_user_nic" class="s_id" type="text" value="${rcvUserNic}" size="65" style="width:200;" readonly="readonly" onkeydown="javascript:searchUser();return false;"/>
					<input type="image" src="./images/board/btn_search2.gif" alt="SEARCH" onclick="javascript:searchUser();return false;"/>
				</td>
			</tr>
			
			<tr>
				<td class="subject"><b>쪽지 내용</b></td>
				<td><img src="./images/board_line.gif" width="1" height="50px" /></td>
				<td class="subject">
					<textarea  id="content" name="content" style="width:90%; height:50px;"></textarea>
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
	<div class="paging3">
		<a href="javascript:writeMemo();"><img class='write' src="./images/btn_paper_send.gif"></a>
		<a href="javascript:window.close();"><img class='write' src="./images/btn_close.gif"></a>
	</div>
	
</div>

	
</form>	
</body>
</html>	
