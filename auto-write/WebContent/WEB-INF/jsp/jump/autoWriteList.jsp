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


		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">대분류 > 자동등록목록</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif"/>
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">자동등록 목록</span>
				</div>

				<div style="margin-top: 5px;">

					<!--게시판 시작-->
					<table class="list01">
						<colgroup>
							<col width="70" />
							<col width="70" />
							<col width="/" />
							<col width="100" />
							<col width="100" />
							<col width="100" />
						</colgroup>
						<tbody>

							<tr>
								<th>선택</th>
								<th>순번</th>
								<th>본문이름</th>
								<th>실행일시</th>
								<th>성공</th>
								<th>실패</th>
							</tr>

							<tr>
								<td><input type=checkbox value=''></td>
								<td>5</td>
								<td>8월 5일 출근부</td>
								<td>2013.08.05</td>
								<td>123</td>
								<td>123</td>
							</tr>

							<tr>
								<td><input type=checkbox value=''></td>
								<td>4</td>
								<td>8월 5일 출근부</td>
								<td>2013.08.05</td>
								<td>123</td>
								<td>123</td>
							</tr>

							<tr>
								<td><input type=checkbox value=''></td>
								<td>3</td>
								<td>8월 5일 출근부</td>
								<td>2013.08.05</td>
								<td>123</td>
								<td>123</td>
							</tr>

							<tr>
								<td><input type=checkbox value=''></td>
								<td>2</td>
								<td>8월 3일 출근부</td>
								<td>2013.08.05</td>
								<td>345</td>
								<td>1213</td>
							</tr>

							<tr>
								<td><input type=checkbox value=''></td>
								<td>1</td>
								<td>8월 2일 출근부</td>
								<td>2013.08.05</td>
								<td>567</td>
								<td>123</td>
							</tr>

						</tbody>
					</table>



					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="자동등록"
							OnClick="popup_check();" class="input_gr">&nbsp;&nbsp;<input
							class="in_btnc" type="button" value="JUMP"
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