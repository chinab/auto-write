<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.SiteCategoryEntity"%>
<%@ page import="com.autowrite.common.framework.entity.SiteListEntity"%>
<%@ page import="com.autowrite.common.framework.entity.SiteEntity"%>

<%
	SiteCategoryEntity siteCategoryEntity = (SiteCategoryEntity) request.getAttribute("SiteCategoryEntity");
	
if ( siteCategoryEntity == null ) {
		siteCategoryEntity = new SiteCategoryEntity();
	}
	
	SiteListEntity siteMasterListEntity = (SiteListEntity) request.getAttribute("SiteMasterList");
	List<SiteEntity> siteMasterList = null;
	if ( siteMasterListEntity != null ) {
		siteMasterList = siteMasterListEntity.getSiteList();
	}
%>

<html>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />


<script>
	function siteCategoryWrite(){
		var frm = document.writeForm;
		
		if(frm.masterSeqId.value.length == 0) {
			alert("사이트를 선택하세요.");
			frm.masterSeqId.focus();
			
			return false;
		}
		if(frm.categoryType.value.length == 0) {
			alert("분류타입을 선택하세요.");
			frm.categoryType.focus();
			
			return false;
		}
		if(frm.categoryName.value.length == 0) {
			alert("NAME을 입력하세요.");
			frm.categoryName.focus();
			
			return false;
		}
		if(frm.categoryValue.value.length == 0) {
			alert("VALUE를 선택하세요.");
			frm.categoryValue.focus();
			
			return false;
		}
		
		if ( frm.seqId.value.length > 0 ) {
			frm.action = "siteCategoryUpdate.do";
		} else {
			frm.action = "siteCategoryWrite.do";
		}
		frm.method = "post";
		
		frm.submit();
	}
</script>

<body>

<!--메인컨텐츠 전체-->
<!--시작지점-->
<form name="writeForm">
	<input type="hidden" name="seqId" value="<%=siteCategoryEntity.nvl(siteCategoryEntity.getSeq_id())%>"/>
	
<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftSystem.jsp" flush="false" />

		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div
					style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">SYSTEM &gt; 분류마스터등록</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif">
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">분류마스터등록 </span>
				</div>

				<div style="margin-top: 5px;">
					<!--게시판 시작-->
					<table class="tb4">
						<colgroup>
							<col width="130" />
							<col width="10" />
							<col width="/" />
							<col width="150" />
						</colgroup>


						<tbody>
							<tr>
								<td colspan="4"></td>
							</tr>

							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>연계사이트</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<select name="masterSeqId" style="width: 150px;">
										<option> === 선택하세요 ===</option>
									<%
										
										if ( siteMasterList != null ) {
											String selectedStr = "";
											for ( int ii = 0 ; ii < siteMasterList.size() ; ii ++ ) {
												SiteEntity siteMasterEntity = siteMasterList.get(ii);
												if ( siteMasterEntity.getSeq_id().equals(siteCategoryEntity.getMaster_seq_id()) ) {
													selectedStr = " selected=\"selected\"";
												} else {
													selectedStr = "";
												}
									%>
										<option value="<%=siteMasterEntity.getSeq_id()%>"<%=selectedStr%>><%=siteMasterEntity.getSite_name()%></option>		
									<%	
											}
										}
									%>
									</select>
									&nbsp;&nbsp;
									
								</td>
								<td class="subject">
									<b> </b>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>분류타입</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<%
										String categoryType = siteCategoryEntity.nvl(siteCategoryEntity.getCategory_type());
									%>
									<select name="categoryType" style="width: 150px;">
										<option> === 선택하세요 ===</option>
										<option value="C" <%="C".equals(categoryType)?" selected=\"selected\"":"" %>>대분류</option>
										<option value="R" <%="R".equals(categoryType)?" selected=\"selected\"":"" %>>지역</option>
										<option value="S" <%="S".equals(categoryType)?" selected=\"selected\"":"" %>>세부지역</option>
									</select>
								</td>
								<td class="subject">
									<b> </b>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>NAME</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="categoryName" class="s_id" type="text" size="65" style="width: 150px;" value="<%=siteCategoryEntity.nvl(siteCategoryEntity.getCategory_name())%>">
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>VALUE</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="categoryValue" class="s_id" type="text" size="65" style="width: 150px;" value="<%=siteCategoryEntity.nvl(siteCategoryEntity.getCategory_value())%>">
								</td>
								<td class="subject">
									
								</td>
							</tr>
							
						</tbody>
					</table>

					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="등록" OnClick="siteCategoryWrite();" class="input_gr">
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