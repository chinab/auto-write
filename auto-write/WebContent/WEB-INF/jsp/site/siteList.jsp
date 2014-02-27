<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.SiteEntity"%>
<%@ page import="com.autowrite.common.framework.entity.SiteListEntity"%>
<%@ page import="com.autowrite.common.framework.entity.ConditionEntity"%>

<%
	List<SiteEntity> siteList = null;
	
	if ( request.getAttribute("SiteList") != null ){
		siteList = (List<SiteEntity>) request.getAttribute("SiteList");
	}
	
	SiteListEntity siteListEntity = (SiteListEntity) request.getAttribute("SiteListEntity");
	
	String searchKey = siteListEntity.writeSearchKey();
	String searchValue = siteListEntity.writeSearchValue();
%>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />

<script>
	function readContents(seqId){
		var hrefStr = "siteRead.do?jsp=site/siteWrite";
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
			var hrefStr = "siteRead.do?jsp=site/siteWrite";
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
			
			frm.action = "siteDelete.do";
			frm.method = "post";
			frm.submit();
		}
	}
</script>

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
		<jsp:include page="../include/leftSite.jsp" flush="false" />
		
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
							<col width="70" />
							<col width="150" />
							<col width="100" />
						</colgroup>
						<tbody>

							<tr>
								<th><input name="checkAll" type="checkbox" onClick="javascript:changeAllChecked('selectedSeqId');"></th>
								<th>순번</th>
								<th>사이트명</th>
								<th>기본선택</th>
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
									SiteEntity siteEntity = siteList.get(ii);
							%>
							<tr>
								<td><input name="selectedSeqId" type="checkbox" value="<%=siteEntity.getSeq_id()%>"></td><td><%= startSequence-- %></td>
								<td>
									<a href="siteRead.do?jsp=site/siteWrite&seqId=<%=siteEntity.getSeq_id()%>">
										<%=siteEntity.getSite_name()%>
									</a>
								</td>
								<td><%="Y".equals(siteEntity.getUse_yn())?"선택":""%></td>
								<td>
									<a href="http://<%=siteEntity.getDomain()%>" target="_blank">
										<%=siteEntity.getDomain()%>
									</a>
								</td>
								<td><%=siteEntity.getWriteBoardDateTime(siteEntity.getWrite_datetime())%></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
					
					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="등록" OnClick="javascript:location.href='siteRead.do?jsp=site/siteWrite';" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc2" type="button" value="수정" OnClick="modifyContents();" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc" type="button" value="삭제" OnClick="deleteContents();" class="input_gr">
					</div>
					
					<!--페이지링크-->
					<div class="paging">
						<%=siteListEntity.getPagination("listSite", null, siteListEntity.getPageNum())%>
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