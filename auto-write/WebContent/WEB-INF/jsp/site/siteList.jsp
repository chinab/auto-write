<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardListEntity"%>
<%@ page import="com.autowrite.common.framework.entity.ConditionEntity"%>

<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	
	List<BoardEntity> siteList = null;
	ConditionEntity cond = null;
	
	if ( request.getAttribute("SiteList") != null ){
		siteList = (List<BoardEntity>) request.getAttribute("SiteList");
	}
	
	BoardListEntity siteListEntity = (BoardListEntity) request.getAttribute("SiteListEntity");
	
	if ( siteList != null && siteList.size() > 0 &&  siteList.get(0).getConditionInfo() != null ){
		cond = siteList.get(0).getConditionInfo();
	} else {
		cond = new ConditionEntity();
	}
	
	String searchKey = siteListEntity.writeSearchKey();
	String searchValue = siteListEntity.writeSearchValue();
%>

<html>
<head>
<title>자동등록</title>
</head>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />

<body>
	<form name="listForm" method="post">
		<input type="hidden" name="seqId" value=""/>
		<input type="hidden" name="pageNum" value="<%=siteListEntity.getPageNum()%>"/>
		
<!--메인컨텐츠 전체-->
<!--시작지점-->
<table cellpadding="0" cellspacing="0" border="0" width="1000"
	align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<td width="220" valign="top">

			<ul class="L_Menus" style="">
				<li class="Menu_Title">사이트설정
				<li>
				<li class="Menu_tex"><a href="siteList.do?jsp=site/siteList" onfocus="blur()">사이트리스트</a>
				<li>
				<li class="Menu_tex"><a href="jspView.do?jsp=site/siteWrite" onfocus="blur()">사이트등록</a>
				<li>
			</ul>
		</td>


		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div
					style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">사이트 설정 > 사이트리스트</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif">
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">사이트리스트</span>
				</div>

				<div style="margin-top: 5px;">

					<!--게시판 시작-->
					<table class="list01">
						<colgroup>
							<col width="70" />
							<col width="70" />
							<col width="/" />
							<col width="150" />
							<col width="100" />
						</colgroup>
						<tbody>

							<tr>
								<th>선택</th>
								<th>순번</th>
								<th>사이트명</th>
								<th>도메인</th>
								<th>등록일</th>
							</tr>
							
							<!--글 목록 시작-->
							<%
								if ( siteList.size() == 0 ) {
									out.println("<tr><td colspan='5'><b>글이 없습니다</b></td></tr>");
								}
							%>
							<%		
								String regionStr = "";
								String facilityNameStr = "";
								
								long startSequence = siteListEntity.getStartSequenceNumber();
								
								for ( int ii = 0 ; ii < siteList.size() ; ii ++ ) {
									BoardEntity siteEntity = siteList.get(ii);
							%>
							<tr>
								<td><input type=checkbox value=''></td>
								<td><%= startSequence-- %></td>
								<td><%=siteEntity.getSite_name()%></td>
								<td><%=siteEntity.getDomain()%></td>
								<td><%=siteEntity.getWrite_datetime()%></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>



					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="등록"
							OnClick="javascript:location.href='jspView.do?jsp=site/siteWrite';" class="input_gr">&nbsp;&nbsp;<input
							class="in_btnc2" type="button" value="수정"
							OnClick="history.back();" class="input_gr">&nbsp;&nbsp;<input
							class="in_btnc" type="button" value="삭제"
							OnClick="history.back();" class="input_gr">
					</div>
					
					<!--페이지링크-->
					<div class="paging">
						<%=siteListEntity.getPagination("listBoard", null, siteListEntity.getPageNum())%>
					</div>
				</div>

			</div>
		</td>

	</tr>
</table>


<!--푸터-->
<jsp:include page="../include/footer.jsp" flush="false" />
<!--푸터 끝-->


</body>
</html>