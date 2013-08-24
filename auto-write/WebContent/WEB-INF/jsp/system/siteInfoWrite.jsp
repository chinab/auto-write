<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


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
		
		frm.action = "siteInfoWrite.do";
		frm.method = "post";
		
		frm.submit();
	}
</script>

<body>

<!--메인컨텐츠 전체-->
<!--시작지점-->
<form name="writeForm">
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
									<input name="siteName" class="s_id" type="text" size="65" style="width: 150px;">
								</td>
								<td class="subject">
									<b> ex) 네이버</b>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>도메인</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="siteDomain" class="s_id" type="text" size="65" style="width: 150px;">
								</td>
								<td class="subject">
									<b> ex) www.naver.com</b>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>ID KEY</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="siteIdKey" class="s_id" type="text" size="65" style="width: 150px;">
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>Password KEY</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="sitePasswdKey" class="s_id" type="text" size="65" style="width: 150px;">
								</td>
								<td class="subject">
									
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>LOGIN URL</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="loginUrl" class="s_id" type="text" size="255" style="width: 400px;">
								</td>
								<td class="subject">
									<select name="loginType">
										<option value="COMMON">COMMON</option>
										<option value="JSON">JSON</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>WRITE URL</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="writeUrl" class="s_id" type="text" size="255" style="width: 400px;">
								</td>
								<td class="subject">
									<select name="writeType">
										<option value="COMMON">COMMON</option>
										<option value="JSON">JSON</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>MODIFY URL</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="modifyUrl" class="s_id" type="text" size="255" style="width: 400px;">
								</td>
								<td class="subject">
									<select name="modifyType">
										<option value="COMMON">COMMON</option>
										<option value="JSON">JSON</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>DELETE URL</b></td>
								<td><img src="images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="deleteUrl" class="s_id" type="text" size="255" style="width: 400px;">
								</td>
								<td class="subject">
									<select name="deleteType">
										<option value="COMMON">COMMON</option>
										<option value="JSON">JSON</option>
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