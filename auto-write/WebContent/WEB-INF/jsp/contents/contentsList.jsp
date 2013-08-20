<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardListEntity"%>
<%@ page import="com.autowrite.common.framework.entity.ConditionEntity"%>

<%
	List<BoardEntity> contentsList = null;
	ConditionEntity cond = null;
	
	if ( request.getAttribute("ContentsList") != null ){
		contentsList = (List<BoardEntity>) request.getAttribute("ContentsList");
	}
	
	BoardListEntity contentsListEntity = (BoardListEntity) request.getAttribute("ContentsListEntity");
	
	if ( contentsList == null ){
		contentsList = new ArrayList();
	}
	
	String searchKey = contentsListEntity.writeSearchKey();
	String searchValue = contentsListEntity.writeSearchValue();
%>

<html>
<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />


<script>
	function readContents(seqId){
		var hrefStr = "contentsView.do?jsp=contents/contentsWrite";
		hrefStr += "&seqId=" + seqId;
		
		location.href = hrefStr;
	}
</script>


<!--메인컨텐츠 전체-->
<!--시작지점-->
<table cellpadding="0" cellspacing="0" border="0" width="1000"
	align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<td width="220" valign="top">

			<ul class="L_Menus" style="">
				<li class="Menu_Title">본문설정
				<li>
				<li class="Menu_tex"><a href="contentsList.do?jsp=contents/contentsList" onfocus="blur()">본문리스트</a>
				<li>
				<li class="Menu_tex"><a href="jspView.do?jsp=contents/contentsWrite" onfocus="blur()">본문등록</a>
				<li>
			</ul>
		</td>


		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div
					style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">본문설정 > 본문 리스트</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif"/>
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">본문 리스트</span>
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
								<th>선택</th>
								<th>순번</th>
								<th>제목</th>
								<th>등록일</th>
								<th>기본글여부</th>
							</tr>
							
							<!--글 목록 시작-->
							<%
								if ( contentsList.size() == 0 ) {
									out.println("<tr><td colspan='5'><b>글이 없습니다</b></td></tr>");
								}
							%>
							<%		
								String regionStr = "";
								String facilityNameStr = "";
								
								long startSequence = contentsListEntity.getStartSequenceNumber();
								
								for ( int ii = 0 ; ii < contentsList.size() ; ii ++ ) {
									BoardEntity contentsEntity = contentsList.get(ii);
							%>
							<tr>
								<td><input type=checkbox value=''></td>
								<td><%= startSequence-- %></td>
								<td>
									<a href="javascript:readContents('<%=contentsEntity.getSeq_id()%>');">
										<%=contentsEntity.getTitle()%>
									</a>
								</td>
								<td><%=contentsEntity.getWriteBoardDateTime()%></td>
								<td><%=contentsEntity.getBlind_yn()%></td>
							</tr>
							<%
								}
							%>

						</tbody>
					</table>



					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="등록" OnClick="location.href='jspView.do?jsp=contents/contentsWrite';" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc2" type="button" value="수정" OnClick="history.back();" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc" type="button" value="삭제" OnClick="history.back();" class="input_gr">

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