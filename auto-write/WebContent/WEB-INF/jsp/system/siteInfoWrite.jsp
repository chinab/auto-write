<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.SiteEntity"%>

<%
	SiteEntity siteEntity = (SiteEntity) request.getAttribute("SiteEntity");
	
	if ( siteEntity == null ) {
		siteEntity = new SiteEntity();
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
		
		if(frm.siteName.value.length == 0) {
			alert("사이트 이름을 입력하세요.");
			frm.siteName.focus();
			
			return false;
		}
		if(frm.siteDomain.value.length == 0) {
			alert("사이트 도메인을 입력하세요.");
			frm.siteDomain.focus();
			
			return false;
		}
		
		if ( frm.seqId.value.length > 0 ) {
			frm.action = "siteInfoUpdate.do";
		} else {
			frm.action = "siteInfoWrite.do";
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
		<jsp:include page="../include/leftSystem.jsp" flush="false" />

		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div
					style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">SYSTEM &gt; 사이트마스터등록</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif">
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">사이트마스터등록 </span>
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
								<td class="subject5">&nbsp;&nbsp;<b>사이트이름</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="siteName" class="s_id" type="text" size="65" style="width: 150px;" value="<%=siteEntity.nvl(siteEntity.getSite_name())%>">
								</td>
								<td class="subject">
									<b> ex) 네이버</b>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>도메인</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="siteDomain" class="s_id" type="text" size="65" style="width: 150px;" value="<%=siteEntity.nvl(siteEntity.getDomain())%>">
								</td>
								<td class="subject">
									<b> ex) www.naver.com</b>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>ID KEY</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="siteIdKey" class="s_id" type="text" size="65" style="width: 150px;" value="<%=siteEntity.nvl(siteEntity.getSite_id_key())%>">
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>Password KEY</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="sitePasswdKey" class="s_id" type="text" size="65" style="width: 150px;" value="<%=siteEntity.nvl(siteEntity.getSite_passwd_key())%>">
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>서비스 클래스명</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="serviceClassName" class="s_id" type="text" size="65" style="width: 150px;" value="<%=siteEntity.nvl(siteEntity.getService_class_name())%>">
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>인코딩</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<select name="siteEncoding" class="s_id" style="width: 150px;">
										<option value="UTF-8" <%="UTF-8".equals(siteEntity.getSite_encoding())?"selected=\"selected\"":""%>>UTF-8</option>
										<option value="EUC-KR" <%="EUC-KR".equals(siteEntity.getSite_encoding())?"selected=\"selected\"":""%>>EUC-KR</option>
									</select>
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>LOGIN URL</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<b>http://<%=siteEntity.nvl(siteEntity.getDomain())%>/</b>
									<input name="loginUrl" class="s_id" type="text" size="200" style="width: 200px;" value="<%=siteEntity.nvl(siteEntity.getLogin_url())%>">
								</td>
								<td class="subject">
									<select name="loginType">
										<option value="COMMON" <%="COMMON".equals(siteEntity.getLogin_type())?"selected=\"selected\"":""%>>COMMON</option>
										<option value="JSON" <%="JSON".equals(siteEntity.getLogin_type())?"selected=\"selected\"":""%>>JSON</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>WRITE URL</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<b>http://<%=siteEntity.nvl(siteEntity.getDomain())%>/</b>
									<input name="writeUrl" class="s_id" type="text" size="200" style="width: 200px;" value="<%=siteEntity.nvl(siteEntity.getWrite_url())%>">
								</td>
								<td class="subject">
									<select name="writeType">
										<option value="COMMON" <%="COMMON".equals(siteEntity.getWrite_type())?"selected=\"selected\"":""%>>COMMON</option>
										<option value="JSON" <%="JSON".equals(siteEntity.getWrite_type())?"selected=\"selected\"":""%>>JSON</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>MODIFY URL</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<b>http://<%=siteEntity.nvl(siteEntity.getDomain())%>/</b>
									<input name="modifyUrl" class="s_id" type="text" size="200" style="width: 200px;" value="<%=siteEntity.nvl(siteEntity.getModify_url())%>">
								</td>
								<td class="subject">
									<select name="modifyType">
										<option value="COMMON" <%="COMMON".equals(siteEntity.getModify_type())?"selected=\"selected\"":""%>>COMMON</option>
										<option value="JSON" <%="JSON".equals(siteEntity.getModify_type())?"selected=\"selected\"":""%>>JSON</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>DELETE URL</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<b>http://<%=siteEntity.nvl(siteEntity.getDomain())%>/</b>
									<input name="deleteUrl" class="s_id" type="text" size="200" style="width: 200px;" value="<%=siteEntity.nvl(siteEntity.getDelete_url())%>">
								</td>
								<td class="subject">
									<select name="deleteType">
										<option value="COMMON" <%="COMMON".equals(siteEntity.getDelete_type())?"selected=\"selected\"":""%>>COMMON</option>
										<option value="JSON" <%="JSON".equals(siteEntity.getDelete_type())?"selected=\"selected\"":""%>>JSON</option>
									</select>
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