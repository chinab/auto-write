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

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">JUMP > JUMP</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif"/>
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">JUMP</span>
				</div>

				<div style="margin-top: 5px;">

					<!--게시판 시작-->
					<table class="list01">
						<colgroup>
							<col width="70" />
							<col width="/" />
							<col width="100" />
							<col width="150" />
							<col width="150" />
						</colgroup>
						<tbody>

							<tr>
								<th>순번</th>
								<th>사이트명</th>
								<th>JUMP 일시</th>
								<th>JUMP 회수</th>
								<th></th>
							</tr>

							<tr>
								<td>3</td>
								<td>소라넷</td>
								<td>13:50:11</td>
								<td>112</td>
								<td><input class="in_btnc1" type="button" value="실행"
									OnClick="history.back();" class="input_gr"></td>
							</tr>

							<tr>
								<td>2</td>
								<td>소라넷</td>
								<td>13:50:11</td>
								<td>200</td>
								<td><input class="in_btnc1" type="button" value="실행"
									OnClick="history.back();" class="input_gr"></td>
							</tr>

							<tr>
								<td>1</td>
								<td>소라넷</td>
								<td>13:50:11</td>
								<td>200</td>
								<td><input class="in_btnc1" type="button" value="실행"
									OnClick="history.back();" class="input_gr"></td>
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