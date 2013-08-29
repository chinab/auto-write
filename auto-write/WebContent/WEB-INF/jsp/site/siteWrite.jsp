<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.SiteEntity"%>
<%@ page import="com.autowrite.common.framework.entity.SiteListEntity"%>

<%
	SiteEntity siteEntity = (SiteEntity) request.getAttribute("SiteEntity");
	
	if ( siteEntity == null ) {
		siteEntity = new SiteEntity();
	}
	
	SiteListEntity siteListEntity = (SiteListEntity) request.getAttribute("SiteMasterList");
	List<SiteEntity> siteList = null;
	if ( siteListEntity != null ) {
		siteList = siteListEntity.getSiteList();
	}
%>

<html>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />


<script>
	function siteWrite(){
		var frm = document.writeForm;
		
		if(frm.siteId.value.length == 0) {
			alert("사이트 아이디를 입력하세요.");
			frm.siteName.focus();
			
			return false;
		}
		if(frm.sitePasswd.value.length == 0) {
			alert("사이트 패스워드를 입력하세요.");
			frm.siteDomain.focus();
			
			return false;
		}
		
		if ( frm.seqId.value.length > 0 ) {
			frm.action = "siteUpdate.do";
		} else {
			frm.action = "siteWrite.do";
		}
		frm.method = "post";
		
		frm.submit();
	}
</script>

<body>

<!--메인컨텐츠 전체-->
<!--시작지점-->
<form name="writeForm">
	<input type="hidden" name="seqId" value="<%=siteEntity.nvl(siteEntity.getSeq_id())%>"/>
	
<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftSite.jsp" flush="false" />

		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">사이트설정 &gt; 사이트등록</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif">
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">사이트등록 </span>
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
										if ( siteList != null ) {
											String selectedStr = "";
											for ( int ii = 0 ; ii < siteList.size() ; ii ++ ) {
												SiteEntity siteMasterEntity = siteList.get(ii);
												if ( siteMasterEntity.getSeq_id().equals(siteEntity.getMaster_seq_id()) ) {
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
									<b>미선택시 자동등록 안됨.</b>
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>기본선택여부</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<select name="useYn" style="width: 150px;">
										<option value="Y" <%="Y".equals(siteEntity.getUse_yn())?"selected=\"selected\"":""%>>기본선택</option>
										<option value="N" <%="N".equals(siteEntity.getUse_yn())?"selected=\"selected\"":""%>>미선택</option>
									</select>
								</td>
								<td class="subject">
									<b> ex) www.naver.com</b>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>ID</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="siteId" class="s_id" type="text" size="65" style="width: 150px;" value="<%=siteEntity.nvl(siteEntity.getSite_id())%>">
									&nbsp;
									<b>해당 사이트의 사용자 ID를 입력하세요.</b>
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>Password</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="sitePasswd" class="s_id" type="text" size="65" style="width: 150px;" value="<%=siteEntity.nvl(siteEntity.getSite_passwd())%>">
									&nbsp;
									<b>해당 사이트의 패스워드를 입력하세요.</b>
								</td>
								<td class="subject">
									
								</td>
							</tr>

						</tbody>
					</table>

					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="등록" OnClick="siteWrite();" class="input_gr">
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