<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardEntity"%>
<%@ page import="com.autowrite.common.framework.entity.SiteEntity"%>
<%@ page import="com.autowrite.common.framework.entity.AutowriteEntity"%>

<%
	AutowriteEntity autowriteEntity = (AutowriteEntity) request.getAttribute("AutowriteEntity");
	
	if ( autowriteEntity == null ){
		autowriteEntity = new AutowriteEntity();
	}
	
	String selectedContent = "";
	String selectedReserveType = "";
	String selectedTitle = "";
	String selectedContentSeqId = "";
	if ( autowriteEntity.getSelectedContentsEntity() != null ) {
		selectedContent = autowriteEntity.getSelectedContentsEntity().getContent();
		selectedTitle = autowriteEntity.getSelectedContentsEntity().getTitle();
		selectedContentSeqId = autowriteEntity.getSelectedContentsEntity().getSeq_id();
	}
	
	Calendar calendar = Calendar.getInstance();
	
	String reservePivotDate = "" + calendar.get(Calendar.YEAR);
	
	if ( calendar.get(Calendar.MONTH) + 1 < 10 ) {
		reservePivotDate += "0" + (calendar.get(Calendar.MONTH) + 1);
	} else {
		reservePivotDate += "" + (calendar.get(Calendar.MONTH) + 1);
	}
	
	if ( calendar.get(Calendar.DAY_OF_MONTH) < 10 ) {
		reservePivotDate += "0" + calendar.get(Calendar.DAY_OF_MONTH);
	} else {
		reservePivotDate += "" + calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	String reserveStartDate = autowriteEntity.getReserve_start_date(reservePivotDate);
	String reserveStartTime = autowriteEntity.getReserve_start_time("00:00");
	String reserveEndDate = autowriteEntity.getReserve_end_date(reservePivotDate);
	String reserveEndTime = autowriteEntity.getReserve_end_time("24:00");
	String reserveTerm = autowriteEntity.getReserve_term();
%>

<html>
<head>
<title>Auto Write - <%=calendar.get(Calendar.DAY_OF_MONTH) %></title>
</head>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />


<script>
	function autowriteReserveWrite(){
		var frm = document.writeForm;
		
		if(frm.title.value.length == 0) {
			alert("제목을 입력하세요.");
			frm.title.focus();
			
			return false;
		}
		if(frm.content.value.length == 0) {
			alert("내용을 입력하세요.");
			frm.content.focus();
			
			return false;
		}
		
		frm.action = "autowriteReserveWrite.do";
		frm.method = "post";
		
		frm.submit();
	}
	
	function changeContents(){
		var frm = document.writeForm;
		
		var locStr = "autowriteReserveWriteForm.do?jsp=autowrite/autowriteReserveWrite&contentsSeqId=" + frm.contentsSeqId.value + "&autowriteReserveSeqid=" + frm.seqId.value;
		
		location.href = locStr;
	}
</script>

<body>

<!--메인컨텐츠 전체-->
<!--시작지점-->
<form name="writeForm">
	<input type="hidden" name="seqId" value="<%=autowriteEntity.getSeq_id()%>"/>
	
<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftAutowrite.jsp" flush="false" />

		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">Auto Write > 예약등록</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif"/>
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">예약등록</span>
				</div>

				<div style="margin-top: 5px;">
					<!--게시판 시작-->
					<table class="tb4">
						<colgroup>
							<col width="130" />
							<col width="10" />
							<col width="/" />
						</colgroup>
						
						<tbody>
							<tr>
								<td colspan="3"></td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>본문선택</b></td>
								<td>
									<img src="/images/board_line.gif" width="1" height="22" />
								</td>
								<td class="subject">
									<select name="contentsSeqId" onChange="javascript:changeContents();" style="width: 250px;">
									<%
										List<BoardEntity> contentsEntityList = autowriteEntity.getContentsEntityList();
										
										String selectedStr = "";
										
										for ( int ii = 0 ; ii < contentsEntityList.size() ;  ii++ ){
											BoardEntity contentsEntity = contentsEntityList.get(ii);
											if ( selectedContentSeqId.equals(contentsEntity.getSeq_id()) ) {
												selectedStr = "selected=\"selected\"";
											} else {
												selectedStr = "";
											}
									%>
										<option value="<%=contentsEntity.getSeq_id()%>" <%=selectedStr%>><%=contentsEntity.getContents_name()%></option>
									<%
										}
									%>
									</select>
									&nbsp;&nbsp;&nbsp;&nbsp;<b>본문을 먼저 선택하세요.</b>
								</td>
							</tr>

							<tr>
								<td class="subject5">
									&nbsp;&nbsp;<b>사이트선택</b>
								</td>
								<td>
									<img src="/images/board_line.gif" width="1" height="22"/>
								</td>
								<td class="subject">
									<%
										List<SiteEntity> siteEntityList = autowriteEntity.getSiteEntityList();
										
										String checkedStr = "";
										
										for ( int ii = 0 ; ii < siteEntityList.size() ;  ii++ ){
											SiteEntity siteEntity = siteEntityList.get(ii);
											if ( "Y".equals(siteEntity.getUse_yn()) ) {
												checkedStr = "checked=\"checked\"";
											} else {
												checkedStr = "";
											}
									%>
										<input type="checkbox" name="siteSeqIdList" value="<%=siteEntity.getSeq_id()%>" <%=checkedStr%>>
										<%=siteEntity.getSite_name()%>
										&nbsp;&nbsp;&nbsp;&nbsp;
									<%
											if ( ii%5 == 4 ){
												out.println("<br/>");
											}
										}
									%>
								</td>
							</tr>

							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>예약 사용여부</b></td>
								<td><img src="/images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="useYn" type="radio" value="Y" <%=autowriteEntity.getUse_yn("Y")%>> 사용
									<input name="useYn" type="radio" value="N" <%=autowriteEntity.getUse_yn("N")%>> 미사용
									&nbsp;&nbsp;&nbsp;&nbsp;<b>사용에 체크가 되어야 예약기능이 동작합니다.</b>
								</td>
							</tr>
							
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>예약기간</b></td>
								<td><img src="/images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<img src="images/calendar.gif" onclick="fnPopUpCalendar(reserveStartDate,reserveStartDate,'yyyymmdd')"/>
									<input name="reserveStartDate" class="s_id" type="text" size="8" style="width: 100px;" value="<%=reserveStartDate%>">
									<select name="reserveStartTime" class="s_id" style="width: 60px;">
									<%
										String reserveStartTimeSelectedStr = "";
									
										for ( int ii = 1 ; ii <= 24 ; ii ++) {
											if ( (ii + "").equals(reserveStartTime) ){
												reserveStartTimeSelectedStr = "selected=\"selecdted\"";
											} else {
												reserveStartTimeSelectedStr = "";
											}
									%>
										<option value="<%=ii%>" <%=reserveStartTimeSelectedStr %>><%=ii%></option>
									<%
										}
									%>
									</select>시
									~
									<img src="images/calendar.gif" onclick="fnPopUpCalendar(reserveEndDate,reserveEndDate,'yyyymmdd')"/>
									<input name="reserveEndDate" class="s_id" type="text" size="8" style="width: 100px;" value="<%=reserveEndDate%>">
									<select name="reserveEndTime" class="s_id" style="width: 60px;">
									<%
										String reserveEndTimeSelectedStr = "";
									
										for ( int ii = 1 ; ii <= 24 ; ii ++) {
											if ( (ii + "").equals(reserveEndTime) ){
												reserveEndTimeSelectedStr = "selected=\"selecdted\"";
											} else {
												reserveEndTimeSelectedStr = "";
											}
									%>
										<option value="<%=ii%>" <%=reserveEndTimeSelectedStr %>><%=ii%></option>
									<%
										}
									%>
									</select>시
								</td>
							</tr>
							
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>예약간격</b></td>
								<td><img src="/images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<select name="reserveTerm" style="width: 250px;">
										<option value="10" <%=autowriteEntity.getReserve_termSelectedStr("10") %>>10분</option>
										<option value="30" <%=autowriteEntity.getReserve_termSelectedStr("30") %>>30분</option>
										<option value="60" <%=autowriteEntity.getReserve_termSelectedStr("60") %>>1시간</option>
										<option value="120" <%=autowriteEntity.getReserve_termSelectedStr("120") %>>2시간</option>
										<option value="240" <%=autowriteEntity.getReserve_termSelectedStr("240") %>>4시간</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>제목</b></td>
								<td><img src="/images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="title" class="s_id" type="text" size="65" style="width: 550px;" value="<%=selectedTitle%>">
								</td>
							</tr>

							<tr>
								<td colspan="3" align="left" style="padding: 9px;">
									<textarea name="content" style="width: 701px; height: 450px;"><%=selectedContent%></textarea>
								</td>
							</tr>
						</tbody>
					</table>

					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="예약등록" OnClick="autowriteReserveWrite();" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc" type="button" value="취소" OnClick="history.back();" class="input_gr">
					</div>
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