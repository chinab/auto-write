<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardListEntity"%>
<%@ page import="com.autowrite.common.framework.entity.ConditionEntity"%>
<%@ page import="com.autowrite.common.framework.entity.MenuDto"%>
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
	
	List<BoardEntity> noticeList = (List<BoardEntity>) request.getAttribute("NoticeList");
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
	
	String searchKey = boardListEntity.writeSearchKey();
	String searchValue = boardListEntity.writeSearchValue();
	
%>

<html>
<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />


<script>
	function readBoard(seqId, category, region, pageNum){
		var frm = document.boardForm;
		
		frm.action = "boardContentView.do";
		frm.jsp.value = "board/boardView";
		frm.seqId.value = seqId;
		frm.category.value = category;
		frm.region.value = region;
		frm.pageNum.value = pageNum;
		
		//frm.method = "get";frm.submit();
		
		var hrefStr = "boardContentView.do?jsp=board/boardView";
		hrefStr += "&seqId=" + seqId;
		hrefStr += "&category=" + category;
		hrefStr += "&region=" + region;
		hrefStr += "&pageNum=" + pageNum;
		
		location.href = hrefStr;
	}
	
	function jumpBoard(boardSeqId, userSeqId, category){
		alert("boardSeqId = " + boardSeqId);
		alert("userSeqId = " + userSeqId);
		alert("category = " + category);
		
		var hrefStr = "boardJump.do?jsp=board/board";
		hrefStr += "&boardSeqId=" + boardSeqId;
		hrefStr += "&userSeqId=" + userSeqId;
		hrefStr += "&category=" + category;
		
		location.href = hrefStr;
	}
	
	function listBoard(category, region, pageNum, facilityName){
		var frm = document.boardForm;
		
		frm.action = "boardListView.do";
		frm.jsp.value = "board/board";
		frm.category.value = category;
		frm.region.value = region;
		frm.pageNum.value = pageNum;
		
		if ( facilityName != null && facilityName.length > 0 ){
			frm.facilityName.value = facilityName;
		}
		
		//frm.method = "get";frm.submit();
		
		var hrefStr = "boardListView.do?jsp=board/board";
		hrefStr += "&category=" + category;
		hrefStr += "&region=" + region;
		hrefStr += "&pageNum=" + pageNum;
		if ( facilityName != null && facilityName.length > 0 ){
			hrefStr += "&facilityName=" + facilityName;
		}
		
		location.href = hrefStr;
	}
	
	function viewUserInfo(userSeqId){
		alert("userInfo:" + userSeqId);
	}
</script>

<!--메인컨텐츠 전체-->
<!--시작지점-->
	<form name="boardForm" method="post">
		<input type="hidden" name="jsp" value=""/>
		<input type="hidden" name="seqId" value=""/>
		<input type="hidden" name="category" value="<%=category%>"/>
		<input type="hidden" name="region" value="<%=region%>"/>
		<input type="hidden" name="facilityName"/>
		<input type="hidden" name="pageNum" value="<%=boardListEntity.getPageNum()%>"/>

<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftBoard.jsp" flush="false" />
		
		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">알림마당 > 공지사항</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif"/>
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">공지사항</span>
				</div>

				<div style="margin-top: 5px;">

					<!--게시판 시작-->
					<table class="list01">
						<colgroup>
							<col width="70" />
							<col width="70" />
							<col width="/" />
							<col width="100" />
							<col width="100" />
						</colgroup>
						<tbody>

							<tr>
								<th><input name="checkAll" type="checkbox" onClick="javascript:changeAllChecked('selectedSeqId');"></th>
								<th>순번</th>
								<th>제목</th>
								<th>등록일</th>
								<th>등록자</th>
							</tr>
							
							<!--글 목록 시작-->
							<%
								if ( boardList.size() == 0 ) {
									out.println("<tr><td colspan='5'><b>글이 없습니다</b></td></tr>");
								}
							%>
							<%		
								String regionStr = "";
								String facilityNameStr = "";
								
								long startSequence = boardListEntity.getStartSequenceNumber();
								
								for ( int ii = 0 ; ii < boardList.size() ; ii ++ ) {
									BoardEntity contentsEntity = boardList.get(ii);
							%>
							<tr>
								<td><input name="selectedSeqId" type="checkbox" value="<%=contentsEntity.getSeq_id()%>"></td>
								<td><%= startSequence-- %></td>
								<td>
									<a href="javascript:readContents('<%=contentsEntity.getSeq_id()%>');">
										<%=contentsEntity.getContents_name()%>
									</a>
								</td>
								<td><%=contentsEntity.getWriteBoardDateTime()%></td>
								<td><%=contentsEntity.getUser_nic()%></td>
							</tr>
							<%
								}
							%>

						</tbody>
					</table>



					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="등록" OnClick="location.href='jspView.do?jsp=contents/contentsWrite';" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc2" type="button" value="수정" OnClick="modifyContents();" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc" type="button" value="삭제" OnClick="deleteContents();" class="input_gr">

					</div>
				</div>

					<!--페이지링크-->
					<div class="paging">
						<%=boardListEntity.getPagination("listBoard", category, region, boardListEntity.getPageNum())%>
					</div>


			</div>
		</td>

	</tr>
</table>
</form>

<!--푸터-->
<jsp:include page="../include/footer.jsp" flush="false" />
<!--푸터 끝-->

</body>
</html>
 
 

  