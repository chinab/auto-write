<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.BoardEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	BoardEntity boardContent = (BoardEntity) request.getAttribute("BoardContent");
	String category = request.getAttribute("category").toString();
	
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

<!-- 배너 스크립트 -->
<jsp:include page="../include/banner.jsp" flush="false"/>
<!-- 배너 스크립트 끝 -->

<script>
	function goList(){
		var frm = document.boardForm;
		
		frm.action = "boardListView.do";
		frm.jsp.value = "board/board";
		
		//frm.method = "get";frm.submit();
		
		var hrefStr = "blackUserList.do?jsp=board/blackUserList";
		hrefStr += "&category=" + frm.category.value;
		hrefStr += "&region=" + frm.region.value;
		hrefStr += "&pageNum=" + frm.pageNum.value;
		
		location.href = hrefStr;
	}

	window.onload = function() {
		//menuClick("jspView.do?jsp=board/main&menuid=020000");
	};
	
	
	function modifyBoard(){
		var frm = document.boardForm;
		
		setRealContent();
		
		if ( !validate(frm) ) {
			return;
		}
		
		//frm.method = "get";frm.submit();
		frm.submit();
	}
	
	
	function validate(frm){
		if ( frm.title.value.length == 0 ){
			alert("제목이 없습니다.");
			frm.title.focus();
			return false;
		}
		if ( frm.content.value.length == 0 ){
			alert("내용이 없습니다.");
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
			CKEDITOR.instances.CKEContent.setData(document.boardForm.content.value);
		} else if ( inputModeValue == "html") {
			htmlDiv.style.display = "block";
			ckeDiv.style.display = "none";
			document.getElementById("HTMLContent").value = document.boardForm.content.value;
		}
	}
	
	// onChange시 호출.
	function setContent(){
		var inputMode = document.getElementById("inputMode");
		var inputModeValue = inputMode[inputMode.selectedIndex].value;
		
		if ( inputModeValue == "wysiwyg") {
			document.boardForm.content.value = document.getElementById("HTMLContent").value;
		} else if ( inputModeValue == "html") {
			document.boardForm.content.value = CKEDITOR.instances.CKEContent.getData();
		}
	}
	
	// submit시 호출.
	function setRealContent(){
		var inputMode = document.getElementById("inputMode");
		var inputModeValue = inputMode[inputMode.selectedIndex].value;
		
		if ( inputModeValue == "wysiwyg") {
			document.boardForm.content.value = CKEDITOR.instances.CKEContent.getData();
		} else if ( inputModeValue == "html") {
			document.boardForm.content.value = document.getElementById("HTMLContent").value;
		}
	}
</script>
</head>

<body onLoad="javascript:loadBanner();">
	<form name="boardForm" method="post" action="boardModify.do" enctype="multipart/form-data">
		<input type="hidden" name="jsp" value="board/board"/>
		<input type="hidden" name="seqId" value="<%=boardContent.getSeq_id()%>"/>
		<input type="hidden" name="category" value="<%=boardContent.getCategory()%>"/>
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
						<tr>
							<td class="subject"><b>작성자</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><span style="float:left; margin-right:285;"><%=userEntity.getNic()%></span> </td>
						</tr>
						<tr>
							<td class="subject"><b>입력방법</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject">
							    <select name="inputMode" id="inputMode" style="width:150;" onChange="javascript:changeInputMode();">
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
							    <select name="region" id="region" style="width:150;">
			                        <option value="강남" <%="강남".equals(boardContent.getRegion())?"selected='selected'":""%>>강남</option>
			                        <option value="강남외" <%="강남외".equals(boardContent.getRegion())?"selected='selected'":""%>>강남외</option>
			                        <option value="경기" <%="경기".equals(boardContent.getRegion())?"selected='selected'":""%>>경기</option>
			                        <option value="인부천" <%="인부천".equals(boardContent.getRegion())?"selected='selected'":""%>>인부천</option>
								    <option value="일산" <%="일산".equals(boardContent.getRegion())?"selected='selected'":""%>>일산</option>
			                        <option value="분당" <%="분당".equals(boardContent.getRegion())?"selected='selected'":""%>>분당</option>
								    <option value="천안" <%="천안".equals(boardContent.getRegion())?"selected='selected'":""%>>천안</option>
								    <option value="충북" <%="충북".equals(boardContent.getRegion())?"selected='selected'":""%>>충북</option>
								    <option value="대구" <%="대구".equals(boardContent.getRegion())?"selected='selected'":""%>>대구</option>
								    <option value="부산" <%="부산".equals(boardContent.getRegion())?"selected='selected'":""%>>부산</option>
								    <option value="기타" <%="기타".equals(boardContent.getRegion())?"selected='selected'":""%>>기타</option>
								</select>
	 						</td>
						</tr>
						<%
							}
						%>
						
						<tr>
							<td class="subject"><b>제목</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject"><input name="title" class="s_id" type="text" size="65" style="width:550;" value="<%= boardContent.getTitle() %>"></td>
						</tr>
	
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
							<td class="subject"><input type="file" name="attachFile"></td>
						</tr>
	            
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
</body>
</html>