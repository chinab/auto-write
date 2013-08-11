<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />

<!--메인컨텐츠 전체-->
<!--시작지점-->
<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<td width="220" valign="top">
			<ul class="L_Menus" style="">
			<li class="Menu_Title">JUMP<li>
			<li class="Menu_tex"><a href="jspView.do?jsp=jump/autoWriteList" onfocus="blur()" >자동등록 목록</a><li>
			<li class="Menu_tex"><a href="jspView.do?jsp=jump/jumpWrite" onfocus="blur()" >자동등록</a><li>
			<li class="Menu_tex"><a href="jspView.do?jsp=jump/jumpList" onfocus="blur()" >JUMP</a><li>
			</ul>
		</td>


			</div>
		</td>


		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">JUMP > 자동등록</div>
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
								<td><img src="/images/board_line.gif" width="1"
									height="22" /></td>
								<td class="subject">
									<!--전화번호--> <select name="12"
									setColor="#000000,#FFFFFF,#000000,#E6E4E4,#C0C0C0,#000000">
										<option value="11">선택하세요</option>
										<option value="11">가나다라마바사아자차카타파하</option>
								</select>
								</td>
							</tr>

							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>사이트선택</b></td>
								<td><img src="/images/board_line.gif" width="1"
									height="22" /></td>
								<td class="subject"><input type=checkbox value=''>소라넷&nbsp;&nbsp;&nbsp;&nbsp;<input
									type=checkbox value=''>소라넷&nbsp;&nbsp;&nbsp;&nbsp;<input
									type=checkbox value=''>소라넷&nbsp;&nbsp;&nbsp;&nbsp;<input
									type=checkbox value=''>소라넷&nbsp;&nbsp;&nbsp;&nbsp;<input
									type=checkbox value=''>소라넷&nbsp;&nbsp;&nbsp;&nbsp;<input
									type=checkbox value=''>소라넷&nbsp;&nbsp;&nbsp;&nbsp;<input
									type=checkbox value=''>소라넷&nbsp;&nbsp;&nbsp;&nbsp;<input
									type=checkbox value=''>소라넷</td>
							</tr>

							<tr>
								<td class="subject5">&nbsp;&nbsp;<b>제목</b></td>
								<td><img src="/images/board_line.gif" width="1"
									height="22" /></td>
								<td class="subject"><input name="title" class="s_id"
									type="text" size="65" style="width: 550px;"></td>
							</tr>

							<tr>
								<td colspan="3" align="left" style="padding: 9px;"><textarea
										style="width: 701px; height: 450px;">ㅇㄹㄴㅇㄹㄴㅇㄹ</textarea></td>
								</td>
						</tbody>
					</table>

					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="초기화"
							OnClick="popup_check();" class="input_gr">&nbsp;&nbsp;<input
							class="in_btnc" type="button" value="등록실행"
							OnClick="history.back();" class="input_gr">

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