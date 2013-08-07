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
		var frm = document.boardForm;
		
		if ( !validate(frm) ) {
			return;
		}
		
		frm.black_user_phone.value = frm.black_user_phone1.value + "-" + frm.black_user_phone2.value + "-" + frm.black_user_phone3.value;
		frm.title.value = "[" + frm.black_user_nic.value + "] " + frm.black_user_phone.value;
		
		frm.submit();
		
		return false;
	}
	
	
	function goList(){
		location.href = "blackUserList.do?jsp=board/blackUserList&category=${category}";
	}
	
	function validate(frm){
		if ( frm.black_user_nic.value.length == 0 ){
			alert("닉네임이 없습니다.");
			frm.black_user_nic.focus();
			return false;
		}
		if ( frm.black_user_phone1.value.length == 0 ){
			alert("전화번호가 없습니다.");
			frm.black_user_phone1.focus();
			return false;
		}
		if ( frm.black_user_phone2.value.length == 0 ){
			alert("전화번호가 없습니다.");
			frm.black_user_phone2.focus();
			return false;
		}
		if ( frm.black_user_phone3.value.length == 0 ){
			alert("전화번호가 없습니다.");
			frm.black_user_phone3.focus();
			return false;
		}
		if ( frm.content.value.length == 0 ){
			alert("사유가 없습니다.");
			return false;
		}
		
		return true;
	}
</script>

<body onLoad="javascript:loadBanner();">
	<form name="boardForm" method="post" action="blackUserWrite.do">
		<input type="hidden" name="category" value="${category}"/>
		<input type="hidden" name="actionType" value="WRITE"/>
		<input type="hidden" name="title" value=""/>
		<input type="hidden" name="secret_yn" value=""/>
		<input type="hidden" name="blind_yn" value=""/>
		<input type="hidden" name="del_yn" value=""/>
		
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
					<col width="200" />
					<col width="10" />
					<col width="600" />
				</colgroup>
		
				<tbody>
					<tr>
						<td class="subject"><b>작성자</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject"><span style="float:left; margin-right:285;"><%=userEntity.getNic()%></span> </td>
					</tr>
					<tr>
						<td class="subject"><b>블랙 닉네임</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input name="black_user_nic" class="s_id" type="text" size="65" style="width:180;">
						</td>
					</tr>
					<tr>
						<td class="subject"><b>블랙 전화번호</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<input name="black_user_phone" class="s_id" type="hidden">
							<input name="black_user_phone1" class="s_id" type="text" size="5" style="width:50;">
							 - <input name="black_user_phone2" class="s_id" type="text" size="5" style="width:50;">
							 - <input name="black_user_phone3" class="s_id" type="text" size="5" style="width:50;">
						</td>
					</tr>
					<tr>
						<td class="subject"><b>사유</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject"></td>
					</tr>
					<tr>
						<td colspan="3" class="subject2">
							<textarea  id="content" name="content" style="width:720; height:300;"></textarea>
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