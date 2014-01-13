<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.AddressEntity"%>
<%@ page import="com.autowrite.common.framework.entity.AddressListEntity"%>
<%@ page import="com.autowrite.common.framework.entity.ConditionEntity"%>

<%
	List<AddressEntity> contentsList = null;
	ConditionEntity cond = null;
	
	if ( request.getAttribute("AddressList") != null ){
		contentsList = (List<AddressEntity>) request.getAttribute("AddressList");
	}
	
	AddressListEntity addressListEntity = (AddressListEntity) request.getAttribute("AddressListEntity");
	
	String searchKey = "";
	String searchValue = "";
	if ( addressListEntity != null ){
		searchKey = addressListEntity.getSearchKey();
		searchValue = addressListEntity.getSearchValue();
	}
	
	if ( contentsList == null ){
		contentsList = new ArrayList();
	}
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
			var hrefStr = "contentsView.do?jsp=contents/contentsWrite";
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
			
			frm.action = "contentsDelete.do";
			frm.method = "post";
			frm.submit();
		}
	}
	
	
	function listSearch(){
		var frm = document.searchForm;
		
		frm.action = "addressList.do";
		frm.jsp.value = "address/addressList";
		frm.method = "post";
		frm.submit();
	}
</script>


<!--메인컨텐츠 전체-->
<!--시작지점-->
<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftAddress.jsp" flush="false" />
		
		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">주소록 &gt; 주소록 리스트</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif"/>
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">주소록 리스트</span>
					
					<!--검색시작-->
					<form name="searchForm">
					<input type="hidden" name="jsp" value=""/>
					
					<fieldset class="search clfix">
						<legend>검색</legend>
						<b>Total : <%=addressListEntity.getTotalListCount() %>건  </b> &nbsp; &nbsp; &nbsp; &nbsp;
						<select name="searchKey">
							<option value="cell_phone" <%="cell_phone".equals(searchKey)?"selected":""%>>번호</option>
							<option value="name" <%="name".equals(searchKey)?"selected":""%>>이름</option>
						</select>
						
						<input type="text" class="s_id" name="searchValue" title="검색어를입력하세요" style="width:200;" value="<%=searchValue%>" onkeydown="javascript:if(event.keyCode==13)listSearch();"/>
						<input class="in_btn" type="button" value="SEARCH"onclick="javascript:listSearch();" class="input_gr">
					</fieldset>
					</form>
						
				</div>

				<div style="margin-top: 5px;">
				
					<form name="listForm">
						<input type="hidden" name="jsp" value=""/>
	
							<!--게시판 시작-->
							<table class="list01">
								
								<colgroup>
									<col width="40" />
									<col width="70" />
									<col width="/" />
									<col width="100" />
									<col width="100" />
								</colgroup>
								<tbody>
		
									<tr>
										<th><input name="checkAll" type="checkbox" onClick="javascript:changeAllChecked('selectedSeqId');"></th>
										<th>순번</th>
										<th>이름</th>
										<th>번호</th>
										<th>등록일</th>
									</tr>
									
									<!--글 목록 시작-->
									<%
										if ( contentsList.size() == 0 ) {
											out.println("<tr><td colspan='5'><b>글이 없습니다</b></td></tr>");
										} else {
									%>
									<%		
											String regionStr = "";
											String facilityNameStr = "";
											
											long startSequence = addressListEntity.getStartSequenceNumber();
											
											for ( int ii = 0 ; ii < contentsList.size() ; ii ++ ) {
												AddressEntity contentsEntity = contentsList.get(ii);
									%>
									<tr>
										<td><input name="selectedSeqId" type="checkbox" value="<%=contentsEntity.getSeq_id()%>"></td>
										<td><%= startSequence-- %></td>
										<td>
											<a href="javascript:readContents('<%=contentsEntity.getSeq_id()%>');">
												<%=contentsEntity.getName()%>
											</a>
										</td>
										<td><%=contentsEntity.getCell_phone()%></td>
										<td><%=contentsEntity.getWriteBoardDateTime(contentsEntity.getWrite_datetime())%></td>
									</tr>
									<%
											}
										}
									%>
		
								</tbody>
							</table>
						</form>


					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="등록" OnClick="location.href='jspView.do?jsp=address/addressWrite';" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc2" type="button" value="수정" OnClick="modifyContents();" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc" type="button" value="삭제" OnClick="deleteContents();" class="input_gr">

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