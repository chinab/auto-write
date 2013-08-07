<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.PaymentMasterEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	PaymentMasterEntity paymentContent = (PaymentMasterEntity) request.getAttribute("PaymentContent");
	String category = request.getAttribute("category").toString();
	
	MenuDto leafMenu = (MenuDto)userEntity.getMenuMap().get(category);
	String leftMenuStr = leafMenu.getParmenuid();
	MenuDto leftMenu = (MenuDto)userEntity.getMenuMap().get(leftMenuStr);
%>

<html>
<head>
<title>HWARU</title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

</head>

<!-- 배너 스크립트 -->
<jsp:include page="../include/banner.jsp" flush="false"/>
<!-- 배너 스크립트 끝 -->

<script>
	function writeBoard(){
		var frm = document.writeForm;
		
		frm.action="userPaymentWrite.do";
		frm.user_seq_id.value = frm.rcv_user_seq_id.value;
		frm.user_id.value = frm.rcv_user_id.value;
		frm.user_nic.value = frm.rcv_user_nic.value;
		
		if ( !validate(frm) ) {
			return;
		}
		
		frm.submit();
		
		return false;
	}
	
	function setFacilityUserName(){
		var frm = document.writeForm;
		
		frm.facility_user_name.value = frm.rcv_user_nic.value;
		
		alert(frm.rcv_user_nic.value);
		alert(frm.facility_user_name.value);
	}
	
	function goList(){
		location.href = "userPaymentList.do?jsp=system/userPaymentList&category=${category}";
	}
	
	function searchUser(){
	    noticeWindow  =  window.open('jspView.do?jsp=popup/searchUser','SearchUser','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=600,height=500, left=600 top=400');
	    noticeWindow.focus();
	}
	
	function validate(frm){
		if ( frm.rcv_user_nic.value.length == 0 ){
			alert("결제자 닉네임을 검색하세요.");
			frm.rcv_user_nic.focus();
			return false;
		}
		
		if ( frm.facility_name.value.length == 0 ){
			alert("업소명을 입력하세요.");
			frm.facility_name.focus();
			return false;
		}
		if ( frm.facility_user_name.value.length == 0 ){
			alert("담당자를 입력하세요.");
			frm.facility_user_name.focus();
			return false;
		}
		if ( frm.facility_phone.value.length == 0 ){
			alert("대표전화를 입력하세요.");
			frm.facility_phone.focus();
			return false;
		}
		if ( frm.facility_address.value.length == 0 ){
			alert("주소를 입력하세요.");
			frm.facility_address.focus();
			return false;
		}
		if ( frm.facility_introduce.value.length == 0 ){
			alert("업소소개를 입력하세요.");
			frm.facility_introduce.focus();
			return false;
		}
		
		if ( frm.start_datetime.value.length == 0 ){
			alert("시작일을 입력하세요.");
			frm.start_datetime.focus();
			return false;
		}

		if ( frm.end_datetime.value.length == 0 ){
			alert("종료일을 입력하세요.");
			frm.end_datetime.focus();
			return false;
		}
		
		if ( frm.facility_region.value.length == 0 ){
			alert("지역을 선택하세요.");
			frm.facility_region.focus();
			return false;
		}

		if ( frm.facility_category.value.length == 0 ){
			alert("업종을 선택하세요.");
			frm.facility_category.focus();
			return false;
		}

		if ( frm.payment_status.value.length == 0 ){
			alert("결제상태를 선택하세요.");
			frm.payment_status.focus();
			return false;
		}
		
		if ( frm.banner_type_code.value.length == 0 ){
			alert("배너타입을 선택하세요.");
			frm.banner_type_code.focus();
			return false;
		}

		if ( "B" == frm.banner_type_code.value || "M" == frm.banner_type_code.value ) {
			if ( frm.mainBanner.value.length == 0 ){
				alert("메인 배너파일을 선택하세요.");
				frm.mainBanner.focus();
				return false;
			}
		}
		
		if ( "B" == frm.banner_type_code.value || "C" == frm.banner_type_code.value ) {
			if ( frm.centerBanner.value.length == 0 ){
				alert("센터 배너파일을 선택하세요.");
				frm.centerBanner.focus();
				return false;
			}
		}

		return true;
	}
	
	
	var fileCount = 1;
	function addFileForm(){
		fileCount++;
		
		var innerHtml = "";
		
		innerHtml += "<input type='file' name='attachFile";
		innerHtml += fileCount;
		innerHtml += "'/>&nbsp;&nbsp;&nbsp;";
		innerHtml += "<input type='button' value='파일삭제' onclick='javascript:deleteFileForm(";
		innerHtml += fileCount + 1;
		innerHtml += ");'/><br/>";
		
		var currDiv = document.getElementById("file_add_form2");
		
		var parent = currDiv.parentNode;
		
		var nextDiv = document.createElement('div');
		divId = fileCount + 1;
		divId = "file_add_form" + divId;
		nextDiv.setAttribute('id', divId);
		parent.appendChild(nextDiv);
		
		
		nextDiv.innerHTML = innerHtml;
		
		
	}
	function deleteFileForm(fileIndex){
		var innerHtml = "";
		
		eval('file_add_form'+fileIndex).innerHTML = "";
	}
</script>

<body onLoad="javascript:loadBanner();">
	<form name="writeForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="category" value="${category}"/>
		<input type="hidden" name="actionType" value="WRITE"/>
		
<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false">
	<jsp:param name="topMenu" value="<%= userEntity.getTopMenu() %>"/>
	<jsp:param name="currMenuName" value="<%= userEntity.getMenuMap().get(category).getMenunm() %>"/>
</jsp:include>
<!--탑메뉴 끝-->


<!--로그인&배너-->
<jsp:include page="../include/loginBox.jsp" flush="false"/>
<!--로그인&배너 끝-->


<!--서브 컨텐츠 시작지점-->
<table width="1000" align="center">
<tr>
<td>   
<div class="h_board">
	<table width="1000" border="0" cellpadding="0" cellspacing="0">
	    <colgroup>
	      <col width="250">
	      <col width="./" />
	    </colgroup>

		<!--좌측메뉴-->
		<td valign="top">
			<div class="sub_title">
				<div class="je"><%=leftMenu.getMenunm()%></div>
				<%=userEntity.getLeftMenu(leftMenuStr)%>
			</div>
			
			<div>
				<jsp:include page="../include/leftChatting.jsp" flush="false"/>
			</div>
		</td>
		<!-- 좌측메뉴 끝-->

	<td valign="top">
		<!--리스트시작-->
		<div class="admin_con">
			<div class="sub_title_bar"><span class="title"><%=userEntity.getBreadCrumb(category)%></span></div>
			
			<!-- 버튼 -->
			<div class="paging3">
				<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
				<a href="javascript:writeBoard();"><img class='write' src="./images/board/bt_write01.gif"></a>
			</div>
			
			<!--게시판 시작-->
			<table class="tb3" style="bottom-margin:15;">
				<colgroup>
					<col width="80" />
					<col width="10" />
					<col width="649"/>
				</colgroup>
		
				<tbody>
					<tr>
						<td class="subject"><b>작성자</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject"><span style="float:left; margin-right:285;"><%=userEntity.getNic()%></span> </td>
					</tr>
					<tr>
						<td class="subject"><b>결제자닉</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input name="rcv_user_nic" class="s_id" type="text" size="45" style="width:200;" readonly onkeydown="javascript:searchUser();return false;" onchange="javascript:setFacilityUserName();">
							<input type="image" src="./images/board/btn_search2.gif" alt="SEARCH"  onclick="javascript:searchUser();return false;"/>
							 &nbsp; <b>검색하여 입력</b>
						</td>
						<input name="user_seq_id" type="hidden">
						<input name="user_id" type="hidden">
						<input name="user_nic" type="hidden">
						<input name="rcv_user_seq_id" type="hidden">
						<input name="rcv_user_id" type="hidden">
					</tr>
					<tr>
						<td class="subject"><b>업소명</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input name="facility_name" class="s_id" type="text" size="45" style="width:200;">
							 &nbsp; <b>대표 이미지 옆에 나오는 업소 이름</b>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>담당자</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input name="facility_user_name" class="s_id" type="text" size="45" style="width:200;">
							 &nbsp; <b>대표 이미지 옆에 나오는 담당자 명</b>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>대표전화</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input name="facility_phone" class="s_id" type="text" size="45" style="width:200;">
							 &nbsp; <b>대표 이미지 옆에 나오는 전화번호</b>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>주소</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input name="facility_address" class="s_id" type="text" size="500" style="width:550;">
							 &nbsp; <b>참고용</b>
						</td>
					</tr>
					<tr>
						<td colspan="3" class="subject"><b>업소 소개</b> (업소정보 작성시 참고용.. 대충 적어도 됨.)</td>
					</tr>
					<tr>
						<td colspan="3" class="subject2">
							<textarea name="facility_introduce" style="width:720; height:100;"></textarea>
						</td>
					</tr>
					
					<tr>
						<td class="subject"><b>시작일</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input name="start_datetime" class="s_id" type="text" size="45" value='2012-12-31 00:00:00' style="width:200;">
							&nbsp; <b>( 연-월-일 시:분:초 형식. YYYY-MM-DD HH:MI:SS 형식으로 입력. )</b>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>종료일</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input name="end_datetime" class="s_id" type="text" size="45" value='2013-12-31 00:00:00' style="width:200;">
							&nbsp; <b>( 연-월-일 시:분:초 형식. YYYY-MM-DD HH:MI:SS 형식으로 입력. )</b>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>지역</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							 <select name="facility_region" id="facility_region" style="width:200;">
								<option>====== 선택 ======</option>
								<option value="강남">강남</option>
								<option value="강남외">강남외</option>
								<option value="수원권">수원권 - 수원, 동탄</option>
								<option value="인부천">인부천</option>
								<option value="분당권">분당권 - 분당, 성남</option>
								<option value="일산권">일산권 - 일산, 김포</option>
								<option value="경기권">경기권 - 기타 경기지역</option>
								<option value="지방권">지방권</option>
								<option value="기타">기타</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>업종</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
						    <select name="facility_category" id="facility_category" style="width:200;">
								<option>====== 선택 ======</option>
								<option value="01">오피</option>
								<option value="02">안마</option>
								<option value="03">휴게,대떡</option>
								<option value="04">건마</option>
								<option value="05">립카페</option>
								<option value="06">핸플</option>
								<option value="07">키스방</option>
								<option value="08">주점</option>
								<option value="09">기타</option>
							</select>
		 				</td>
					</tr>
					
					<tr>
						<td class="subject"><b>결제상태</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<select name="payment_status" id="payment_status" style="width:200;">
								<option>====== 선택 ======</option>
								<option value="R">결제대기</option>
								<option value="Y" selected>결제완료</option>
								<option value="N">미납</option>
								<option value="E">이벤트결제</option>
							</select>
							 &nbsp; <b>결제완료로 선택하세요. </b>
		 				</td>
					</tr>
					<tr>
						<td class="subject"><b>배너타입</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<select name="banner_type_code" id="banner_type_code" style="width:200;">
								<option>====== 선택 ======</option>
								<option value="N">대표이미지만</option>
								<option value="B">메인,센터하단</option>
								<option value="M">메인</option>
								<option value="C">센터하단</option>
							</select>
							 &nbsp; <b></b>
		 				</td>
					</tr>
					
					<tr>
						<td class="subject"><b>메인배너</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input type="file" name="mainBanner"/>
							 &nbsp; <b>상단 메인배너</b>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>센터배너</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input type="file" name="centerBanner"/>
							 &nbsp; <b>하단 작은배너</b>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>대표이미지</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input type="file" name="facility_image"/>
							 &nbsp; <b>중앙 대표 이미지</b>
						</td>
					</tr>
	              </tbody>
			</table>
			
			<!-- 버튼 -->
			<div class="paging3">
				<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
				<a href="javascript:writeBoard();"><img class='write' src="./images/board/bt_write01.gif"></a>
			</div>
		
			<!--게시판 끝-->
		 	</div>
		</td>
	</table>
</div>
</td></tr></table>
</form>

<!--푸터-->
<div class="bot_bg">
	<div class="bot_add01"><img src="./images/bottom.jpg"></div>
</div>

</body>
</html>