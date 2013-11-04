<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.UserBusinessEntity"%>
<%@ page import="com.autowrite.common.framework.entity.UserBusinessListEntity"%>

<%
	UserBusinessEntity businessEntity = (UserBusinessEntity) request.getAttribute("UserBusinessEntity");
	
	if ( businessEntity == null ) {
		businessEntity = new UserBusinessEntity();
	}
	
	UserBusinessListEntity businessListEntity = (UserBusinessListEntity) request.getAttribute("UserBusinessList");
	List<UserBusinessEntity> businessList = null;
	if ( businessListEntity != null ) {
		businessList = businessListEntity.getBusinessList();
	}
%>

<html>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />


<script>
	function businessInfoWrite(){
		var frm = document.writeForm;
		
		if ( frm.seqId.value.length > 0 ) {
			frm.action = "businessInfoUpdate.do";
		} else {
			frm.action = "businessInfoWrite.do";
		}
		frm.method = "post";
		
		frm.submit();
	}
</script>

<body>

<!--메인컨텐츠 전체-->
<!--시작지점-->
<form name="writeForm">
	<input type="hidden" name="seqId" value="<%=businessEntity.nvl(businessEntity.getSeq_id())%>"/>
	
<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftIndividual.jsp" flush="false" />

		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">개인설정 &gt; 업소정보 등록</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif">
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">업소정보 등록 </span>
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
								<td class="subject5">&nbsp;&nbsp;<b>상호</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="businessName" class="s_id" type="text" size="65" style="width: 350px;" value="<%=businessEntity.nvl(businessEntity.getBusiness_name())%>">
									&nbsp;
									<b></b>
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>지역</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="businessRegion" class="s_id" type="text" size="65" style="width: 350px;" value="<%=businessEntity.nvl(businessEntity.getBusiness_region())%>">
									&nbsp;
									<b></b>
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>전화번호</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="businessTel" class="s_id" type="text" size="65" style="width: 350px;" value="<%=businessEntity.nvl(businessEntity.getBusiness_tel())%>">
									&nbsp;
									<b></b>
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>업종</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="businessCategory" class="s_id" type="text" size="65" style="width: 350px;" value="<%=businessEntity.nvl(businessEntity.getBusiness_category())%>">
									&nbsp;
									<b></b>
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>영업시간</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="businessTime" class="s_id" type="text" size="65" style="width: 350px;" value="<%=businessEntity.nvl(businessEntity.getBusiness_time())%>">
									&nbsp;
									<b></b>
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>가격</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="businessPrice" class="s_id" type="text" size="65" style="width: 350px;" value="<%=businessEntity.nvl(businessEntity.getBusiness_price())%>">
									&nbsp;
									<b></b>
								</td>
								<td class="subject">
									
								</td>
							</tr>
								<td class="subject5">&nbsp;&nbsp;<b>오시는 길</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="businessAddress" class="s_id" type="text" size="65" style="width: 350px;" value="<%=businessEntity.nvl(businessEntity.getBusiness_address())%>">
									&nbsp;
									<b></b>
								</td>
								<td class="subject">
									
								</td>
							</tr>
							
						</tbody>
					</table>

					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="등록" OnClick="businessInfoWrite();" class="input_gr">
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