<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.AutowriteEntity"%>
<%@ page import="com.autowrite.common.framework.entity.AutowriteListEntity"%>
<%@ page import="com.autowrite.common.framework.entity.ConditionEntity"%>

<%
	AutowriteListEntity autowriteMasterListEntity = (AutowriteListEntity) request.getAttribute("AutowriteListEntity");
	
	List<AutowriteEntity> autowriteMasterList = autowriteMasterListEntity.getAutowriteList();
	
	String searchKey = autowriteMasterListEntity.writeSearchKey();
	String searchValue = autowriteMasterListEntity.writeSearchValue();
%>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />

<script>
	function rewrite(){
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
			var hrefStr = "autowriteRewrite.do?jsp=autowrite/autowriteMasterList";
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
			
			frm.action = "autowriteReserveDelete.do";
			frm.method = "post";
			frm.submit();
		}
	}
</script>

<!--메인컨텐츠 전체-->
<!--시작지점-->
<form name="listForm">
	<input type="hidden" name="jsp" value=""/>

<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftAutowrite.jsp" flush="false" />

		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">Auto Write > 예약목록</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif"/>
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">예약목록</span>
				</div>

				<div style="margin-top: 5px;">

					<!--게시판 시작-->
					<table class="list01">
						<colgroup>
							<col width="50" />
							<col width="60" />
							<col width="/" />
							<col width="70" />
							<col width="70" />
							<col width="70" />
							<col width="70" />
						</colgroup>
						<tbody>

							<tr>
								<th><input name="checkAll" type="checkbox" onClick="javascript:changeAllChecked('selectedSeqId');"></th>
								<th>순번</th>
								<th>제목</th>
								<th>예약시작</th>
								<th>예약종료</th>
								<th>예약간격</th>
								<th>다음예약</th>
								<th>사용여부</th>
							</tr>
							
							<%
								for ( int ii = 0 ; ii < autowriteMasterList.size() ; ii ++ ) {
									AutowriteEntity autowriteEntity = autowriteMasterList.get(ii);
							%>
							<tr>
								<td><input name="selectedSeqId" type="checkbox" value="<%=autowriteEntity.getSeq_id()%>"></td>
								<td><%=autowriteEntity.getSeq_id()%></td>
								<td>
									<a href="autowriteReserveWriteForm.do?jsp=autowrite/autowriteReserveWrite&autowriteReserveSeqid=<%=autowriteEntity.getSeq_id()%>&contentsSeqId=<%=autowriteEntity.getContents_seq_id()%>">
										<%=autowriteEntity.getTitleLength(autowriteEntity.getTitle(), 30)%>
									</a>
								</td>
								<td><%=autowriteEntity.writeReserve_start_datetime()%></td>
								<td><%=autowriteEntity.writeReserve_end_datetime()%></td>
								<td><%=autowriteEntity.writeListReserve_term()%></td>
								<td><%=autowriteEntity.writeReserve_remain_minute()%></td>
								<td><%=autowriteEntity.writeUse_yn()%></td>
							</tr>
							<%
								}
							
								if ( autowriteMasterList.size() == 0 ) {
							%>
							<tr>
								<td colspan="7">글이 없습니다.</td>
							</tr>
							<%
								}
							%>
							

						</tbody>
					</table>
					
					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="재실행" OnClick="rewrite();">
						&nbsp;&nbsp;
						<input class="in_btnc2" type="button" value="뒤로가기" OnClick="history.back();">
						&nbsp;&nbsp;
						<input class="in_btnc" type="button" value="삭제" OnClick="deleteContents();">
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