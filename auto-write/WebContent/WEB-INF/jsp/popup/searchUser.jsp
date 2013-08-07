<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserListEntity"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	UserListEntity userListEntity  = (UserListEntity) request.getAttribute("UserList");
	List<UserEntity> userList = null;
	
	if ( userListEntity != null ) {
		userList = userListEntity.getUserList();
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>사용자 검색</title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

<script>
	function searchUser(){
		var frm = document.searchForm;
		
		frm.action="userInfoList.do";
		frm.jsp.value="popup/searchUser";
		frm.U_ID.value = frm.rcv_user_id.value;
		
		frm.submit();
	}
	
	function setParent(seq, id, nic) {
		opener.document.writeForm.rcv_user_seq_id.value = seq;
		opener.document.writeForm.rcv_user_id.value = id;
		opener.document.writeForm.rcv_user_nic.value = nic;
		window.close();
	}
</script>
</head>
<body>
<form name="searchForm" method="post">
	<input type="hidden" name="jsp" value=""/>
	<input type="hidden" name="U_ID" value=""/>
	
<div class="memo_title">
	<span style="width:300px;"><strong>사용자 검색</strong></span>
</div>
	
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
				<td class="subject"><b>수신자 닉 또는 ID</b></td>
				<td><img src="./images/board_line.gif" width="1" height="22" /></td>
				<td class="subject">
					<input type="text" name="rcv_user_id" value="${rcvUserId}" size="30" style="width:100;" onkeydown="javascript:if(event.keyCode==13)searchUser();"/>
					<input type="image" src="./images/board/btn_search2.gif" alt="SEARCH"  onclick="javascript:searchUser();return false;"/>
				</td>
			</tr>
            
		</tbody>
	</table>
</div>
	
	<br/>

<div style="width:600; margin-top:10px; margin-left:6px;" >		
	<table class="tb3" style="width:100%;bottom-margin:15;">
		<colgroup>
			<col width="150" />
			<col width="150" />
			<col width="150" />
			<col width="150" />
		</colgroup>
		
		<tbody>
<%
	if ( userList == null || userList.size() == 0 ) {
%>
			<tr>
				<td colspan="4">
					검색 된 수신자가 없습니다.
				</td>
			</tr>
<%
	} else {
%>
			<tr>
				<th>순번</th>
				<th>ID</th>
				<th>NIC</th>
				<th>선택</th>
			</tr>	
<%			
		for ( int ii = 0 ; ii < userList.size() ; ii ++ ) {
			UserEntity searchUserEntity = userList.get(ii);
%>
			<tr>
				<td class="docStyle01">
					<%=ii+1%>
				</td>
				<td class="docStyle01">
					<%=searchUserEntity.getId()%>
				</td>
				<td class="docStyle01">
					<%=searchUserEntity.getNic()%>
				</td>
				<td class="docStyle01">
					<input type="button" class="btn btn-mini" value="선택" onClick="javascript:setParent('<%=searchUserEntity.getSeq_id()%>', '<%=searchUserEntity.getId()%>', '<%=searchUserEntity.getNic()%>')"/>
				</td>
			</tr>
<%		
		}  // end for
	} // end if
%>
		</tbody>
	</table>
	
	<!--전체선택 등 버튼-->
	<div class="paging3">
		<a href="javascript:window.close();"><img class='write' src="./images/btn_close.gif"></a>
	</div>
	
</div>

	
</form>
</body>
</html>	
