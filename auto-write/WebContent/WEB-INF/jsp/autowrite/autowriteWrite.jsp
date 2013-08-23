<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardEntity"%>
<%@ page import="com.autowrite.common.framework.entity.AutowriteEntity"%>

<%
	AutowriteEntity autowriteEntity = (AutowriteEntity) request.getAttribute("AutowriteEntity");
	
	if ( autowriteEntity == null ){
		autowriteEntity = new AutowriteEntity();
		autowriteEntity.setSiteEntityList(new ArrayList<BoardEntity>());
		autowriteEntity.setContentsEntityList(new ArrayList<BoardEntity>());
	}
	
	String selectedContent = "";
	String selectedTitle = "";
	String selectedContentSeqId = "";
	if ( autowriteEntity.getSelectedContentsEntity() != null ) {
		selectedContent = autowriteEntity.getSelectedContentsEntity().getContent();
		selectedTitle = autowriteEntity.getSelectedContentsEntity().getTitle();
		selectedContentSeqId = autowriteEntity.getSelectedContentsEntity().getSeq_id();
	}
	
%>

<html>
<head>
<title>Auto Write</title>
</head>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />


<script>
	function autowriteWrite(){
		alert("autowriteWrite");
		
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
		
		frm.action = "autowriteWrite.do";
		frm.method = "post";
		
		frm.submit();
	}
	
	function changeContents(){
		var frm = document.writeForm;
		
		var locStr = "autowriteWriteForm.do?jsp=autowrite/autowriteWrite&contentsSeqId=" + frm.contentsSeqId.value;
		
		location.href = locStr;
	}
</script>

<body>

<!--메인컨텐츠 전체-->
<!--시작지점-->
<form name="writeForm">
<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftAutowrite.jsp" flush="false" />

		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">자동등록 > 자동등록</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif"/>
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">자동등록</span>
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
										List<BoardEntity> siteEntityList = autowriteEntity.getSiteEntityList();
									
										for ( int ii = 0 ; ii < siteEntityList.size() ;  ii++ ){
											BoardEntity siteEntity = siteEntityList.get(ii);
									%>
										<input type="checkbox" name="siteSeqIdList" value="<%=siteEntity.getSeq_id()%>" checked="checked">
										<%=siteEntity.getSite_name()%>
										&nbsp;&nbsp;&nbsp;&nbsp;
									<%
										}
									%>
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
						<input class="in_btn" type="button" value="자동등록" OnClick="autowriteWrite();" class="input_gr">
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