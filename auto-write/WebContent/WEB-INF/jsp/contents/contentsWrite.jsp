<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.BoardEntity"%>

<%
	BoardEntity contentsEntity = (BoardEntity) request.getAttribute("ContentsEntity");
	
	if ( contentsEntity == null ){
		contentsEntity = new BoardEntity();
		
		contentsEntity.setContent("");
		contentsEntity.setContents_name("");
		contentsEntity.setTitle("");
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
	function contentsWrite(){
		var frm = document.writeForm;
		
		if(frm.contentsName.value.length == 0) {
			alert("본문 이름을 입력하세요.");
			frm.contentsName.focus();
			
			return false;
		}
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
		
		frm.action = "contentsWrite.do";
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
		<td width="220" valign="top">

			<ul class="L_Menus" style="">
				<li class="Menu_Title">본문설정
				<li>
				<li class="Menu_tex"><a href="contentsList.do?jsp=contents/contentsList" onfocus="blur()">본문리스트</a>
				<li>
				<li class="Menu_tex"><a href="jspView.do?jsp=contents/contentsWrite" onfocus="blur()">본문등록</a>
				<li>
			</ul>
		</td>


		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div
					style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">본문설정 > 본문등록</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif">
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">본문등록 </span>
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
								<td class="subject5">&nbsp;&nbsp;<b>본문이름</b></td>
								<td><img src="/images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="contentsName" class="s_id" type="text" size="65" style="width: 550px;" value="<%=contentsEntity.getContents_name()%>">
								</td>
							</tr>
							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>제목</b></td>
								<td><img src="/images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input name="title" class="s_id" type="text" size="65" style="width: 550px;" value="<%=contentsEntity.getTitle()%>">
								</td>
							</tr>

							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>기본글 설정</b></td>
								<td><img src="/images/board_line.gif" width="1"
									height="22" /></td>
								<td class="subject">
									<input type="radio" name="default_yn" value='Y' checked="checked">Yes
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="default_yn" value='N'>No
								</td>
							</tr>

							<tr>
								<td colspan="3" align="left" style="padding: 9px;">
									<textarea name="content" style="width: 701px; height: 450px;"><%=contentsEntity.getContent()%></textarea>
								</td>
						</tbody>
					</table>

					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="등록" OnClick="contentsWrite();" class="input_gr">
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