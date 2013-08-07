<!--#include virtual="/new/include/header.asp"-->

<!--탑메뉴-->
<!--#include virtual="/new/include/top_menus.asp"-->

<!--메인컨텐츠 전체-->
<!--시작지점-->
<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top:30px;">
<tr>
<!--좌측메뉴-->
<td width="220" valign="top">

<ul class="L_Menus" style="">
<li class="Menu_Title">JUMP<li>
<li class="Menu_tex"><a href="#" onfocus="blur()" >자동등록</a><li>
<li class="Menu_tex"><a href="#" onfocus="blur()" >자동등록 목록</a><li>
<li class="Menu_tex"><a href="#" onfocus="blur()" >JUMP</a><li>
</ul>


</div>
</td>


<!--우측컨-->
<td width="800" valign="top">
<div style="margin-left:30px; width:750px; ">

<div style="width:100% line-height:120px; padding:7px; border:solid 1px #eeeeee; ">대분류 > 중분류 > 소분류</div>
<div style="width:100%; margin-top:30px;"><img src="/new/images/title_dot.gif"><span style="font-weight:bold; padding-left:3px; font-size:17px; color:#219075; font-family:Malgun Gothic;">자동등록 결과</span></div>

<div style="margin-top:5px;">

<!--게시판 시작-->
<table class="list01">
   <colgroup>
     <col width="70" />
     <col width="70" />
	 <col width="100" />
     <col width="100"/>
	 <col width="150"/>
	 <col width="/"/>
   </colgroup>
   <tbody>

<tr>
<th >선택</th>
<th>순번</th>
<th>사이트명</th>
<th>등록결과</th>
<th>응답코드</th>
<th>비고</th>
</tr>

<tr>
<td><input type=checkbox value=''></td>
<td>3</td>
<td>소라넷</td>
<td>성공</td>
<td>200 OK</td>
<td></td>
</tr>

<tr>
<td><input type=checkbox value=''></td>
<td>2</td>
<td>소라넷</td>
<td>성공</td>
<td>200 OK</td>
<td></td>
</tr>

<tr>
<td><input type=checkbox value=''></td>
<td>1</td>
<td>소라넷</td>
<td>성공</td>
<td>200 OK</td>
<td></td>
</tr>

</tbody>
</table>



		<div style="width:100%; margin-top: 15px; text-align:center;"><input class="in_btn" type="button" value="목록"  OnClick="popup_check();"  class="input_gr">&nbsp;&nbsp;<input class="in_btnc" type="button" value="재등록"  OnClick="history.back();" class="input_gr">          
 
</div>
 </div>



</div>
</td>

</tr>
</table>


<!--푸터-->
<!--#include virtual="/new/include/footer_add.asp"-->



</body>
</html>