<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.UserBusinessEntity"%>
<%@ page import="com.autowrite.common.framework.entity.UserBusinessListEntity"%>
<%@ page import="com.autowrite.common.framework.entity.ConditionEntity"%>

<%
	List<UserBusinessEntity> businessInfoList = null;
	
	if ( request.getAttribute("UserBusinessList") != null ){
		businessInfoList = (List<UserBusinessEntity>) request.getAttribute("UserBusinessList");
	}
	
	UserBusinessListEntity businessListEntity = (UserBusinessListEntity) request.getAttribute("UserBusinessListEntity");
	
	String searchKey = businessListEntity.writeSearchKey();
	String searchValue = businessListEntity.writeSearchValue();
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

<script>
	function readContents(seqId){
		var hrefStr = "businessInfoView.do?jsp=individual/businessInfoWrite";
		hrefStr += "&seqId=" + seqId;
		
		location.href = hrefStr;
	}
	
	function modifyContents(){
		var frm = document.listForm;
		
		var checkedCount = 0;
		var seqId;
		
		for ( var ii = 0 ; ii < frm.selectedSeqId.length ; ii ++ ) {
			if ( frm.selectedSeqId[ii].checked == true ){
				seqId = frm.selectedSeqId[ii].value;
				checkedCount ++;
			}
		}
		
		if ( checkedCount == 0 ) {
			alert("대상을 선택 해 주세요.");
			return;
		} else if ( checkedCount > 1 ) {
			alert("하나만 선택 해 주세요.");
			return;
		} else {
			var hrefStr = "businessInfoRead.do?jsp=individual/businessInfoWrite";
			hrefStr += "&seqId=" + seqId;
			
			location.href = hrefStr;
		}
	}
	
	function deleteContents(){
		var frm = document.listForm;
		
		var checkedCount = 0;
		
		for ( var ii = 0 ; ii < frm.selectedSeqId.length ; ii ++ ) {
			if ( frm.selectedSeqId[ii].checked == true ){
				checkedCount ++;
			}
		}
		
		if ( checkedCount == 0 ) {
			alert("대상을 선택 해 주세요.");
			return;
		} else {
			if ( !confirm("선택한 대상을 삭제하시겠습니까?") ){
				return;
			}
			
			frm.action = "businessInfoDelete.do";
			frm.method = "post";
			frm.submit();
		}
	}
</script>

<body>
	<form name="listForm" method="post">
		<input type="hidden" name="seqId" value=""/>
		<input type="hidden" name="pageNum" value="<%=businessListEntity.getPageNum()%>"/>
		
<!--메인컨텐츠 전체-->
<!--시작지점-->
<table cellpadding="0" cellspacing="0" border="0" width="1000"
	align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftIndividual.jsp" flush="false" />
		
		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div
					style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">개인설정 > 업소정보 리스트</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif">
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">업소정보 리스트</span>
				</div>

				<div style="margin-top: 5px;">

					<!--게시판 시작-->
					<table class="list01">
						<colgroup>
							<col width="70" />
							<col width="70" />
							<col width="/" />
							<col width="70" />
							<col width="150" />
							<col width="100" />
						</colgroup>
						<tbody>

							<tr>
								<th><input name="checkAll" type="checkbox" onClick="javascript:changeAllChecked('selectedSeqId');"></th>
								<th>순번</th>
								<th>업소명</th>
								<th>지역</th>
								<th>업종</th>
								<th>등록일</th>
							</tr>
							
							<!--글 목록 시작-->
							<%
								if ( businessInfoList.size() == 0 ) {
									out.println("<tr><td colspan='5'><b>글이 없습니다</b></td></tr>");
								}
							%>
							<%		
								String regionStr = "";
								String facilityNameStr = "";
								
								long startSequence = businessListEntity.getStartSequenceNumber();
								
								for ( int ii = 0 ; ii < businessInfoList.size() ; ii ++ ) {
									UserBusinessEntity userBusinessEntity = businessInfoList.get(ii);
							%>
							<tr>
								<td><input name="selectedSeqId" type="checkbox" value="<%=userBusinessEntity.getSeq_id()%>"></td><td><%= startSequence-- %></td>
								<td>
									<a href="businessInfoRead.do?jsp=individual/businessInfoWrite&seqId=<%=userBusinessEntity.getSeq_id()%>">
										<%=userBusinessEntity.getBusiness_name()%>
									</a>
								</td>
								<td><%=userBusinessEntity.getBusiness_region()%></td>
								<td><%=userBusinessEntity.getBusiness_category()%></td>
								<td><%=userBusinessEntity.getWriteBoardDateTime(userBusinessEntity.getWrite_datetime())%></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
					
					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="등록" OnClick="location.href='jspView.do?jsp=individual/businessInfoWrite';" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc2" type="button" value="수정" OnClick="modifyContents();" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc" type="button" value="삭제" OnClick="deleteContents();" class="input_gr">
					</div>
					
					<!--페이지링크-->
					<div class="paging">
						<%=businessListEntity.getPagination("listSite", null, businessListEntity.getPageNum())%>
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