<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.BoardEntity"%>
<%@ page import="com.jekyll.common.framework.entity.AttachmentEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	BoardEntity boardContent = (BoardEntity) request.getAttribute("BoardContent");
	String category = request.getAttribute("category").toString();
	String targetCategory = "";
	if ( boardContent.getTarget_category() != null ){
		targetCategory = boardContent.getTarget_category();
	}
	
	MenuDto leafMenu = (MenuDto)userEntity.getMenuMap().get(category);
	String leftMenuStr = leafMenu.getParmenuid();
	MenuDto leftMenu = (MenuDto)userEntity.getMenuMap().get(leftMenuStr);
%>

<%
	String content = boardContent.getContent();
	content = content.replaceAll("\"", "\\\"");
	content = content.replaceAll("\'", "\\\'");
%>
<html>
<head>
<title>글 수정</title>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

</head>

<!-- 배너 스크립트 -->
<jsp:include page="../include/banner.jsp" flush="false"/>
<!-- 배너 스크립트 끝 -->

<script>
	function goList(){
		var frm = document.writeForm;
		
		frm.action = "boardListView.do";
		frm.jsp.value = "board/board";
		
		//frm.method = "get";frm.submit();
		
		var hrefStr = "boardListView.do?jsp=board/board";
		hrefStr += "&category=" + frm.category.value;
		//hrefStr += "&region=" + frm.region.value;
		hrefStr += "&pageNum=" + frm.pageNum.value;
		
		location.href = hrefStr;
	}

	function modifyBoard(){
		var frm = document.writeForm;
		
		setRealContent();
<%
	if ( (category.startsWith("02")||category.startsWith("03")) && "M".equals(userEntity.getType_code()) ){
%>
		if ( frm.rcv_user_seq_id.value.length == 0 ){
			alert("업소 담당자를 선택하세요.");
			document.getElementById("rcv_user_nic").focus();
			return;
		}
		frm.user_seq_id.value = frm.rcv_user_seq_id.value;
		frm.user_id.value = frm.rcv_user_id.value;
		frm.user_nic.value = frm.rcv_user_nic.value;
<%
	}
%>
		if ( !validate(frm) ) {
			return;
		}
		
		frm.submit();
	}
	
	
	// 지역별 업소명 가져오기
	function getFacilityNames(){
		var region = document.getElementById("region").value;
		var category = document.getElementById("category").value;
		var facility_category = category.substring(2,4);
		
		//alert(region);
		
		if ( region.length == 0 ) {
			alert("지역을 선택하세요.");
			document.getElementById("region").focus();
			return;
		} else {
			var params = {"REGION":region,"FACILITY_CATEGORY":facility_category};
			
			new ajax.xhr.Request("getFacilityNamesAsRegion.do", "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join('')), callFacilityNames, "POST");
		}
	}
	
	function callFacilityNames(httpRequest) {
		if(httpRequest.readyState == 4 ) {
			if(httpRequest.status == 200 ) {
				//alert(httpRequest.responseText);
				var data = eval('(' + httpRequest.responseText + ')');
				if(data["Code"]=="E"){
					alert("Error Desc: " + data["Text"]);
				} else if (data["Code"]=="S"){
					makeFacilityNamesSelectBox(data);
				} else {
					alert("Error Desc: " + data["Text"]);
				}
			} else {
				alert("통신오류. 다시 시도 해 주십시오.");
			}
		}
	}
	
	function makeFacilityNamesSelectBox(facilityNamesData){
		var resultArray = facilityNamesData.RESULT_LIST;
		
		var obj = document.writeForm.facility_name;
		var region = document.writeForm.region.value;
		
		if ( obj != null ){
			obj.length = 0;
			
			if ( resultArray.length == 0 ){
				alert(facilityNamesData.REGION + "의 제휴업소가 없습니다.");
				obj.options[obj.options.length] = new Option("비제휴", "비제휴");
				return;
			} else {
				obj.options[obj.options.length] = new Option("====== 선택 ======", "");
				
				for ( var ii = 0 ; ii < resultArray.length ; ii ++ ){
					var facilityName = resultArray[ii];
					if ( "<%=boardContent.getFacility_name()%>" == facilityName ){
						obj.options[obj.options.length] = new Option(facilityName, facilityName, true, true);
					} else {
						obj.options[obj.options.length] = new Option(facilityName, facilityName);
					}
				}
			}
		}
	}
	
	function setFacilityUserName(){
		var frm = document.writeForm;
		
		frm.facility_user_name.value = frm.rcv_user_nic.value;
		
		alert(frm.rcv_user_nic.value);
		alert(frm.facility_user_name.value);
	}
	
	function setFacilityUserName(){
		var frm = document.writeForm;
		
		frm.facility_user_name.value = frm.rcv_user_nic.value;
		
		alert(frm.rcv_user_nic.value);
		alert(frm.facility_user_name.value);
	}
	
	function searchUser(){
	    noticeWindow  =  window.open('jspView.do?jsp=popup/searchUser','SearchUser','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=600,height=500, left=600 top=400');
	    noticeWindow.focus();
	}
	
	window.onload = function() {
		//menuClick("jspView.do?jsp=board/main&menuid=020000");
	};
	
	
	function validate(frm){
		if ( frm.title.value.length == 0 ){
			alert("제목을 입력하세요.");
			frm.title.focus();
			return false;
		}
<%
	if ( category.startsWith("02") || category.startsWith("03") || category.startsWith("04") ){
%>
		if ( frm.region.value.length == 0 ){
			alert("지역을 선택하세요.");
			frm.region.focus();
			return false;
		}
<%
	}
%>
	
<%
	if ( category.startsWith("04") ){
%>
		if ( frm.facility_name.value.length == 0 ){
			alert("업소명을 선택하세요.");
			frm.facility_name.focus();
			return false;
		}
		if ( frm.visit_time.value.length == 0 ){
			alert("방문일시를 입력하세요.");
			frm.visit_time.focus();
			return false;
		}
		if ( frm.waiteress_name.value.length == 0 ){
			alert("언니예명을 입력하세요.");
			frm.waiteress_name.focus();
			return false;
		}
<%
	}
%>

<%
	if ( category.startsWith("02") ){
%>
		if ( frm.facility_name.value.length == 0 ){
			alert("업소명을 선택하세요.");
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
<%
	}
%>

		if ( frm.content.value.length == 0 ){
			alert("내용을 입력하세요.");
			return false;
		}
		
		return true;
	}
	
	
	function changeInputMode(){
		setContent();
		
		var inputMode = document.getElementById("inputMode");
		var inputModeValue = inputMode[inputMode.selectedIndex].value;
		
		var htmlDiv = document.getElementById("HTMLDiv");
		var ckeDiv = document.getElementById("CKEDiv");
		
		if ( inputModeValue == "wysiwyg") {
			htmlDiv.style.display = "none";
			ckeDiv.style.display = "block";
			CKEDITOR.instances.CKEContent.setData(document.writeForm.content.value);
		} else if ( inputModeValue == "html") {
			htmlDiv.style.display = "block";
			ckeDiv.style.display = "none";
			document.getElementById("HTMLContent").value = document.writeForm.content.value;
		}
	}
	
	// onChange시 호출.
	function setContent(){
		var inputMode = document.getElementById("inputMode");
		var inputModeValue = inputMode[inputMode.selectedIndex].value;
		
		if ( inputModeValue == "wysiwyg") {
			document.writeForm.content.value = document.getElementById("HTMLContent").value;
		} else if ( inputModeValue == "html") {
			document.writeForm.content.value = CKEDITOR.instances.CKEContent.getData();
		}
	}
	
	// submit시 호출.
	function setRealContent(){
		var inputMode = document.getElementById("inputMode");
		var inputModeValue = inputMode[inputMode.selectedIndex].value;
		
		if ( inputModeValue == "wysiwyg") {
			document.writeForm.content.value = CKEDITOR.instances.CKEContent.getData();
		} else if ( inputModeValue == "html") {
			document.writeForm.content.value = document.getElementById("HTMLContent").value;
		}
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
	
	function deleteFile(seqId){
		alert('준비중');
	}
	
	function init(){
		var targetCategory = document.getElementById("target_category");
		
		for ( var ii = 0 ; ii < targetCategory.options.length ; ii ++ ){
			if ( "<%=targetCategory%>" == targetCategory.options[ii].value ){
				targetCategory.selectedIndex = ii;
				break;
			}
		}
	}
</script>

<body onLoad="javascript:loadBanner();init();">
	<form name="writeForm" method="post" action="boardModify.do" enctype="multipart/form-data">
		<input type="hidden" name="jsp" value="board/board"/>
		<input type="hidden" name="seqId" value="<%=boardContent.getSeq_id()%>"/>
		<input type="hidden" name="category" id="category" value="<%=boardContent.getCategory()%>"/>
		<input type="hidden" name="content" value=""/>
		<input type="hidden" name="secretYn" value="N"/>
		<input type="hidden" name="blindYn" value="N"/>
		<input type="hidden" name="pageNum" value="<%=request.getAttribute("pageNum")%>"/>
	
	
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
				
 				<!--이전글 , 다음글 버튼 -->
<!-- 				<div class="paging3"> -->
<!-- 					<a href="javascript:alert('준비중');"><img class='write' src="./images/board/bt_pre.gif"></a> -->
<!-- 					<a href="javascript:alert('준비중');"><img class='write' src="./images/board/bt_nex.gif"></a> -->
<!-- 				</div> -->
				
				<!--수정,삭제,버튼 -->
				<div class="paging3">
					<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
					<a href="javascript:modifyBoard();"><img class='write' src="./images/board/bt_mod.gif"></a>
				</div>
				<!--수정,삭제,버튼 끝 -->
				
				<!--게시판 시작-->
				<table class="tb3" style="bottom-margin:15;">
					<colgroup>
						<col width="80" />
						<col width="10" />
						<col width="649"./>
					</colgroup>
	
					<tbody>
						<%
							if ( "M".equals(userEntity.getType_code()) && (category.startsWith("02") || category.startsWith("03")) ){
						%>
						<tr>
							<td class="subject"><b>작성자</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><input name="rcv_user_nic" class="s_id" type="text" size="45" style="width:200;" value="<%=boardContent.getUser_nic() %>" readonly onkeydown="javascript:searchUser();return false;" onchange="javascript:setFacilityUserName();">
								<input type="image" src="./images/board/btn_search2.gif" alt="SEARCH"  onclick="javascript:searchUser();return false;"/>
							</td>
							<input name="user_seq_id" type="hidden" value="<%=boardContent.getUser_seq_id() %>">
							<input name="user_id" type="hidden" value="<%=boardContent.getUser_id() %>">
							<input name="user_nic" type="hidden" value="<%=boardContent.getUser_nic() %>">
							<input name="rcv_user_seq_id" type="hidden" value="<%=boardContent.getUser_seq_id() %>">
							<input name="rcv_user_id" type="hidden" value="<%=boardContent.getUser_id() %>">
						</tr>
						<%
							} else {
						%>
						<tr>
								<td class="subject"><b>작성자</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject"><span style="float:left; margin-right:285;"><%=boardContent.getUser_nic()%></span> </td>
						</tr>
						<%
							}
						%>
						
						<%
							// 공지사항 특정 공지 게시판 설정.
							if ( "M".equals(userEntity.getType_code()) && category.equals("010100") ){
						%>
						<tr>
							<td class="subject"><b>공지대상</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject">
							    <select name="target_category" id="target_category" style="width:200;">
									<option value="">=== 공지대상 게시판 선택 ===</option>
									<option value="000000">전체 게시판 공지</option>
									<option value="">=======================</option>
									<option value="010000">NEWS 공지</option>
									<option value="020000">업소정보 공지</option>
									<option value="030000">라인업 공지</option>
									<option value="040000">후기 공지</option>
									<option value="050000">커뮤니티 공지</option>
									<option value="080000">화류센터 공지</option>
									<option value="">=======================</option>
									<option value="010100">NEWS-공지사항</option>
									<option value="010200">NEWS-쿠폰경매</option>
									<option value="010300">NEWS-새소식</option>
									<option value="020100">업소정보-오피</option>
									<option value="020200">업소정보-안마</option>
									<option value="020300">업소정보-휴게,대떡</option>
									<option value="020400">업소정보-건마</option>
									<option value="020500">업소정보-립카페</option>
									<option value="020600">업소정보-핸플</option>
									<option value="020700">업소정보-키스방</option>
									<option value="020800">업소정보-주점</option>
									<option value="020900">업소정보-기타</option>
									<option value="030100">라인업-오피</option>
									<option value="030200">라인업-안마</option>
									<option value="030300">라인업-휴게,대떡</option>
									<option value="030400">라인업-건마</option>
									<option value="030500">라인업-립카페</option>
									<option value="030600">라인업-핸플</option>
									<option value="030800">라인업-키스방</option>
									<option value="030900">라인업-주점</option>
									<option value="030700">라인업-기타</option>
									<option value="040100">후기-오피</option>
									<option value="040200">후기-안마</option>
									<option value="040300">후기-휴게,대떡</option>
									<option value="040400">후기-건마</option>
									<option value="040500">후기-립카페</option>
									<option value="040600">후기-핸플</option>
									<option value="040700">후기-키스방</option>
									<option value="040800">후기-주점</option>
									<option value="040900">후기-기타</option>
									<option value="050100">커뮤니티-자유게시판</option>
									<option value="050200">커뮤니티-유머</option>
									<option value="050300">커뮤니티-은꼴</option>
									<option value="050400">커뮤니티-미공개실사</option>
									<option value="050500">커뮤니티-질문답변</option>
									<option value="050600">커뮤니티-언니이동정보</option>
									<option value="050800">커뮤니티-언니들의편지</option>
									<option value="080200">화류센터-회원검색</option>
									<option value="080300">화류센터-제휴문의</option>
									<option value="080500">화류센터-운영자상담</option>
									<option value="080600">화류센터-구인구직</option>
								</select>
							</td>
						</tr>
						<%
							}
						%>
						
						<tr>
							<td class="subject"><b>입력방법</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject">
							    <select name="inputMode" id="inputMode" style="width:200;" onChange="javascript:changeInputMode();">
									<option value="html" >HTML</option>
									<option value="wysiwyg" >웹게시판</option>
								</select>
							</td>
						</tr>
	
						<%
							if ( category.startsWith("02") ||  category.startsWith("03") ||  category.startsWith("04") ){
						%>
						<tr>
							<td class="subject"><b>지역</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject">
							    <select name="region" id="region" style="width:200;" onchange="javascript:getFacilityNames();">
			                        <option value="강남" <%="강남".equals(boardContent.getRegion())?"selected='selected'":""%>>강남</option>
			                        <option value="강남외" <%="강남외".equals(boardContent.getRegion())?"selected='selected'":""%>>강남외</option>
			                        <option value="수원권" <%="수원권".equals(boardContent.getRegion())?"selected='selected'":""%>>수원권</option>
									<option value="인부천" <%="인부천".equals(boardContent.getRegion())?"selected='selected'":""%>>인부천</option>
								    <option value="분당권" <%="분당권".equals(boardContent.getRegion())?"selected='selected'":""%>>분당권</option>
								    <option value="일산권" <%="일산권".equals(boardContent.getRegion())?"selected='selected'":""%>>일산권</option>
			                        <option value="경기권" <%="경기권".equals(boardContent.getRegion())?"selected='selected'":""%>>경기권</option>
			                        <option value="지방권"  <%="지방권".equals(boardContent.getRegion())?"selected='selected'":""%>>지방권</option>
									<option value="기타"  <%="기타".equals(boardContent.getRegion())?"selected='selected'":""%>>기타</option>
								</select>
	 						</td>
						</tr>
						<tr>
							<td class="subject"><b>업소명</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject">
							    <select name="facility_name" id="facility_name" style="width:200;">
									<option>====== 선택 ======</option>
								</select>
			 				</td>
						</tr>
						<%
							}
						%>
						
						<%
							if ( category.startsWith("04") ){
						%>
						<tr>
							<td class="subject"><b>방문일시</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><input name="visit_time" value="<%=boardContent.getVisit_time()%>" class="s_id" type="text" size="45" style="width:200;"></td>
						</tr>
						<tr>
							<td class="subject"><b>언니예명</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><input name="waiteress_name" value="<%=boardContent.getWaiteress_name()%>" class="s_id" type="text" size="45" style="width:200;"></td>
						</tr>
						<%
							}
						%>
						
						<%
							if ( category.startsWith("02") ){
						%>
						<tr>
							<td class="subject"><b>담당자</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><input name="facility_user_name" value="<%=boardContent.getFacility_user_name()%>" class="s_id" type="text" size="45" style="width:200;"></td>
						</tr>
						<tr>
							<td class="subject"><b>대표전화</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><input name="facility_phone" value="<%=boardContent.getFacility_phone()%>" class="s_id" type="text" size="45" style="width:200;"></td>
						</tr>
						<tr>
							<td class="subject"><b>주소</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><input name="facility_address" value="<%=boardContent.getFacility_address()%>" class="s_id" type="text" size="500" style="width:550;"></td>
						</tr>
						<tr>
							<td class="subject"><b>제목</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><input name="title" class="s_id" type="text" size="65" style="width:550;" value="<%= boardContent.getTitle() %>"></td>
						</tr>
						<tr>
							<td colspan="3" class="subject"><b>업소 소개</b></td>
						</tr>
						<%
							} else {
						%>
						<tr>
							<td class="subject"><b>제목</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><input name="title" class="s_id" type="text" size="65" style="width:550;" value="<%= boardContent.getTitle() %>"></td>
						</tr>
						<%
							}
						%>
						<tr>
							<td colspan="3" class="subject2">
								<div id="HTMLDiv" style="display:block;">
	                        		<textarea  id="HTMLContent" name="HTMLContent" style="width:720; height:300;"><%= boardContent.getContent() %></textarea>
	                        	</div>
	                        	<div id="CKEDiv" style="display:none;">
	                        		<textarea  id="CKEContent" name="CKEContent" style="width:720; height:300;"><%= boardContent.getContent() %></textarea>
	                        		<script>
	                        			enableCKEditor("CKEContent");
	                        		</script>
	                        	</div>
	                        </td>
						</tr>
						
						
						<tr>
							<td class="subject"><b>첨부파일</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject">
								<input type="file" name="attachFile1"/>
								&nbsp;
								<input type="button" value="파일추가" onclick="javascript:addFileForm();"/>
								<br/>
								<div id="file_add_form2"></div>
							</td>
						</tr>
	            		
	            		<%
	            			List<AttachmentEntity> fileList = boardContent.getAttachmentList();
	            			String fileName = "";
	            			int listSize = fileList.size();
	            			for ( int ii = 0 ; ii < listSize ; ii ++ ) {
	            				AttachmentEntity attachment = fileList.get(ii);
	            		%>
	            		<tr>
							<td class="subject">
								<input type="button" value="파일삭제" onclick="javascript:deleteFile('<%=attachment.getSeq_id()%>');"/>
							</td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><%= attachment.getP_file_name()  %></td>
						</tr>
	            		<%
	            			}
	            		%>
	              </tbody>
			</table>
		        
				<!--수정,삭제,버튼 -->
				<div class="paging3">
					<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
					<a href="javascript:modifyBoard();"><img class='write' src="./images/board/bt_mod.gif"></a>
<!-- 					<a href="./board/board_list.asp"><img class='write' src="./images/board/bt_write01.gif"></a> -->
<!-- 					<a href="javascript:doReply();"><img class='write' src="./images/board/bt_reply01.gif"></a> -->
				</div>
				<!--수정,삭제,버튼 끝 -->
				
				<!--게시판 끝-->
				
			</div>
		</td>
	</table>
</div>
</td></tr></table>

<!--푸터-->
<div class="bot_bg">
	<div class="bot_add01"><img src="./images/bottom.jpg"></div>
</div>

	</form>

<script>
	// 페이지 로딩 후 호출
	getFacilityNames();
</script>

</body>
</html>