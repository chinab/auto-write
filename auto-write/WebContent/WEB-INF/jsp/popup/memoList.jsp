<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MemoEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	List<MemoEntity> memoList = (List<MemoEntity>) request.getAttribute("MemoList");
	
	String category = "";
	String titleBox = "";
	
	if ( request.getAttribute("category") == null ){
		category = request.getParameter("category");
	} else {
		category = request.getAttribute("category").toString();
	}
	
	if ( "01".equals(category) ) {
		titleBox = "받은 쪽지함";
	} else if ( "02".equals(category) ) {
		titleBox = "보낸 쪽지함";
	} else if ( "03".equals(category) ) {
		titleBox = "지운 쪽지함";
	} else if ( "04".equals(category) ) {
		titleBox = "보관 쪽지함";
	} else {
		titleBox = "내 쪽지함";
	}
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title><%= titleBox %></title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

<script type="text/javascript">
	function viewMemoContent(category, seqId, sendMessageSeqId){
		var frm = document.memoForm;
		
		frm.action = "memoContentView.do";
		frm.jsp.value = "popup/memoContentView";
		frm.category.value = category;
		frm.seqId.value = seqId;
		frm.sendMessageSeqId.value = sendMessageSeqId;
		
		frm.submit();
	}
	
	function deleteMemo(category, seqId){
		if ( category == "03" && !confirm("삭제하시겠습니까?") ){
			return;
		}
		
		var frm = document.memoForm;
		
		frm.action = "memoDelete.do";
		frm.jsp.value = "popup/memoList";
		frm.category.value = category;
		frm.seqId.value = seqId;
		
		frm.submit();
	}
</script>
</head>
<body>
<form name="memoForm" method="post">
	<input type="hidden" name="jsp" value=""/>
	<input type="hidden" name="category" value=""/>
	<input type="hidden" name="seqId" value=""/>
	<input type="hidden" name="sendMessageSeqId" value=""/>
</form>

<div class="memo_title">
	<span style="width:300px;"><%= titleBox %></span>
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

<div style="width:600; margin-top:10px;" >	
	<table class="tb2">
		<colgroup>
			<col width="60">
			<col width="100" />
			<col width="100" />
			<col width="100" />
			<col width="100" />
			<col width="140" />
		</colgroup>
		<thead>
			<tr>
			   <th>순번</th>
			   <th>보낸이</th>
			   <th>받는이</th>
			   <th>발송시각</th>
			   <th>수신시각</th>
			   <th>관리</th>
			</tr>
		</thead>
		<tbody>

<%
	if ( memoList == null || memoList.size() == 0 ) {
%>
		<tr>
			<td colspan="6">
				<strong>쪽지가 없습니다.</strong>
			</td>
		</tr>
<%
	} else {
		for ( int ii = 0 ; ii < memoList.size() ; ii ++ ) {
			MemoEntity memoEntity = memoList.get(ii);
%>
		<tr style="cursor: hand;" onclick="javascript:event.cancelBubble=true;viewMemoContent('<%=category%>', '<%=memoEntity.getSeq_id()%>', '<%=memoEntity.getSend_message_seq_id()%>')" >
<!-- 			<INPUT onclick="if (this.checked) all_checked(true); else all_checked(false);" type="checkbox"> -->
			<td><%= ii + 1 %></td>
			<td><%=memoEntity.getSnd_user_nic()%></td>
			<td><%=memoEntity.getRcv_user_nic()%></td>
			<td><%=memoEntity.writeDateTime(memoEntity.getWrite_datetime())%></td>
			<td><%=memoEntity.writeRcv_datetime()%></td>
			<td><input type="button" value="삭제" onClick="javascript:event.cancelBubble=true;deleteMemo('<%=category%>', '<%=memoEntity.getSeq_id()%>')"/></td>
		</tr>
<%		
		}  // end for
	} // end if
%>
		</tbody>
	</table>
	
	<br/>
	
	<!--페이지링크-->
	<div class="paging">
		<img src='./images/btn_pagingFirst.gif'>&nbsp;&nbsp;&nbsp;<span class='gray'>1&nbsp;&nbsp;2&nbsp;&nbsp;3&nbsp;&nbsp;5&nbsp;&nbsp;6&nbsp;&nbsp;7&nbsp;&nbsp;8&nbsp;&nbsp;9 &nbsp;&nbsp;10</span>&nbsp;&nbsp;&nbsp;<img src='./images/btn_pagingEnd.gif'>
	</div>
	
	<!--전체선택 등 버튼-->
	<div class="paging3">
		<a href="javascript:window.close();"><img class='write' src="./images/btn_close.gif"></a>
	</div>
		
	
</div>

</body>
</html>	
