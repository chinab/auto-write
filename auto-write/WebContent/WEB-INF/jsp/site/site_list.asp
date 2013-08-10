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
<li class="Menu_Title">사이트설정<li>
<li class="Menu_tex"><a href="site_list.asp" onfocus="blur()" >사이트리스트</a><li>
<li class="Menu_tex"><a href="site_write.asp" onfocus="blur()" >사이트등록</a><li>
</ul>

</td>


<!--우측컨-->
<td width="800" valign="top">
<div style="margin-left:30px; width:750px; ">

<div style="width:100% line-height:120px; padding:7px; border:solid 1px #eeeeee; ">대분류 > 중분류 > 소분류</div>
<div style="width:100%; margin-top:30px;"><img src="/new/images/title_dot.gif"><span style="font-weight:bold; padding-left:3px; font-size:17px; color:#219075; font-family:Malgun Gothic;">사이트등록 </span></div>

<div style="margin-top:5px;">

<!--게시판 시작-->
<table class="list01">
   <colgroup>
     <col width="70" />
     <col width="70" />
	 <col width="/" />
     <col width="100"/>
	 <col width="150"/>
   </colgroup>
   <tbody>

<tr>
<th >선택</th>
<th>순번</th>
<th>사이트명</th>
<th>등록일</th>
<th>도메인</th>
</tr>

<tr>
<td><input type=checkbox value=''></td>
<td>3</td>
<td>8월 6일 출근부</td>
<td>13:50:11</td>
<td>Yes</td>
</tr>

<tr>
<td><input type=checkbox value=''></td>
<td>2</td>
<td>8월 6일 출근부</td>
<td>2013.08.05</td>
<td>No</td>
</tr>

<tr>
<td><input type=checkbox value=''></td>
<td>1</td>
<td>8월 6일 출근부</td>
<td>2013.08.05</td>
<td>Yes</td>
</tr>

</tbody>
</table>



		<div style="width:100%; margin-top: 15px; text-align:center;"><input class="in_btn" type="button" value="등록"  OnClick="popup_check();"  class="input_gr">&nbsp;&nbsp;<input class="in_btnc2" type="button" value="수정"  OnClick="history.back();" class="input_gr">&nbsp;&nbsp;<input class="in_btnc" type="button" value="삭제"  OnClick="history.back();" class="input_gr">            
 
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