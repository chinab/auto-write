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
	Map<String, MenuDto> menuMap = userEntity.getMenuMap();
	
	List<Map> pointLogList = (List<Map>) request.getAttribute("PointLogList");
	
	Map actionTypeMap = new HashMap();
	actionTypeMap.put("READ", "읽기");
	actionTypeMap.put("RECOMMEND", "추천");
	actionTypeMap.put("WRITE", "쓰기");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>MY POINT</title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

<script type="text/javascript">
	
</script>
</head>
<body>
<form name="memoForm" method="post">
	<input type="hidden" name="jsp" value=""/>
	<input type="hidden" name="category" value=""/>
	<input type="hidden" name="seqId" value=""/>
	<input type="hidden" name="pageNum" value=""/>
</form>

<div class="memo_title">
	<span style="width:300px;">MY POINT</span>
</div>

<div style="width:600; margin-top:10px;" >	
	<table class="tb2">
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
