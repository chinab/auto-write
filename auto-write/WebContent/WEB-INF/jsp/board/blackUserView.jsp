<%@page import="com.jekyll.common.framework.entity.AttachmentEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.io.File"%>
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
	function goList(){
		var frm = document.boardForm;
		
		frm.action = "blackUserList.do";
		frm.jsp.value = "board/blackUserList";
		
		//frm.method = "get";frm.submit();
		
		var hrefStr = "blackUserList.do?jsp=board/blackUserList";
		hrefStr += "&category=" + frm.category.value;
		hrefStr += "&region=" + frm.region.value;
		hrefStr += "&pageNum=" + frm.pageNum.value;
		
		location.href = hrefStr;
	}
	
	function goModify(){
		var frm = document.boardForm;
		
		frm.method = "get";frm.submit();
		
		return false;
	}
	
	function goDelete(){
		var frm = document.boardForm;
		
		if ( confirm("삭제하시겠습니까?") ){
			frm.action = "blackUserDelete.do";
			frm.method = "get";frm.submit();
		}
	}
	
	function doReply() {
		var params = gatherReplyWriteParams();
		
		var txtContent = document.getElementById("txtContent").value;
		if ( txtContent.length == 0 ){
			alert("내용이 없습니다.");
			return;
		}
		
		
		var uri = "doWriteReply.do";
		var uriComponent = "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join(''));
		var method = "POST";
		
		params["type"]="S";
		
		new ajax.xhr.Request(uri, uriComponent, callbackReplyWrite, method);
	}
	
	function doReadReply() {
		var params = gatherReplyParams("READ");
		
		var uri = "doReadReply.do";
		var uriComponent = "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join(''));
		var method = "POST";
		
		params["type"]="S";
		
		new ajax.xhr.Request(uri, uriComponent, callbackReplyRead, method);
	}
	
	function doModifyReply(replySeqId) {
		alert("준비중");
	}
	
	function doDeleteReply(replySeqId) {
		alert("준비중");
	}
	
	function gatherReplyWriteParams() {
		var txtContent = document.getElementById("txtContent").value;
		var category = "${category}";
		var seqId = "<%= boardContent.getSeq_id() %>";
		var JobType = "WRITE";
		var blindYn = "N";
		
		if ( document.getElementById("isSecretReply").checked == true ){
			blindYn = "Y";
		}
		
		var params =	{
							"txtContent":txtContent,
							"JobType":JobType,
							"category":category,
							"blindYn":blindYn,
							"seqId":seqId
						};
		
		return params;
	}
	
	
	function gatherReplyParams(JobType) {
		var category = "${category}";
		var seqId = "<%= boardContent.getSeq_id() %>";
		
		var params =	{
							"JobType":JobType,
							"category":category,
							"seqId":seqId
						};
		return params;
	}
	
	
	function callbackReplyWrite(httpRequest) {
		if (httpRequest.readyState == 4) {
			if (httpRequest.status == 200) {
				var data = eval('(' + httpRequest.responseText + ')');
				if (data["Code"] != "S") {
					alert("Error Desc: " + data["Text"]);
				} else {
					var commentDiv = document.getElementById("commentDiv");
					commentDiv.innerHTML = makeCommentView(data);
					document.getElementById("txtContent").value = "";
				}
			}
		}
	}
	
	
	function callbackReplyRead(httpRequest) {
		if (httpRequest.readyState == 4) {
			if (httpRequest.status == 200) {
				var data = eval('(' + httpRequest.responseText + ')');
				if (data["Code"] != "S") {
					alert("Error Desc: " + data["Text"]);
				} else {
					var commentDiv = document.getElementById("commentDiv");
					commentDiv.innerHTML = makeCommentView(data);
				}
			}
		}
	}
	
	
	function makeCommentView(commentData){
		var inner = "";
		
		for ( var ii = 0 ; ii < commentData.Data.length ; ii ++ ){
			var commentUserNic = commentData.Data[ii].USER_NIC;
			var commentWriteDate = "";
			if ( commentData.Data[ii].WRITE_DATETIME != null ) {
				var tempDate = commentData.Data[ii].WRITE_DATETIME;
				if ( tempDate.month == "0" ){
					tempDate.month = "1";
				}
				commentWriteDate = tempDate.month + "-" + tempDate.date + " " + tempDate.hours + ":" + tempDate.minutes;
			}
			var commentContent = commentData.Data[ii].CONTENT;
			var commentSeqId = commentData.Data[ii].SEQ_ID;
			var commentDivId = "";
			
			commentContent = commentContent.split("\r\n").join("<br/>");
			
			inner += "<div class=\"comment\" id=\"";
			inner += commentDivId;
			inner += "\">\n";
			inner += "	<div class=\"top\"><span style=\"float:left; margin-left:15; margin-right:25; \">";
			inner += commentUserNic;
			inner += "</span><span class=\"cc\">작성일</span> <span class=\"cc1\">";
			inner += commentWriteDate;
			<%
				if ( "M".equals(userEntity.getType_code()) || (userEntity.getSeq_id()).equals(boardContent.getUser_seq_id()) ) {
			%>
			inner += "</span><span style=\"float:right; margin-left:15; margin-right:25; \">";
			inner += "<a href='javascript:doModifyReply(" + commentSeqId + ");'>수정</a>";
			inner += "<a href='javascript:doDeleteReply(" + commentSeqId + ");'>삭제</a>";
			<%
				}
			%>
			inner += "</span></div>\n";
			inner += "<div class=\"con\">\n";
			inner += commentContent;
			inner += "\n</div>\n";
		}
		//alert(inner);
		return inner;
		
	}
</script>

<body onLoad="javascript:loadBanner();doReadReply();">
	<form name="boardForm" method="post" action="blackUserView.do" enctype="multipart/form-data">
		<input type="hidden" name="jsp" value="board/blackUserVieww"/>
		<input type="hidden" name="seqId" value="<%=boardContent.getSeq_id()%>"/>
		<input type="hidden" name="category" value="<%=boardContent.getCategory()%>"/>
		<input type="hidden" name="region" value="<%=boardContent.writeRegion()%>"/>
		<input type="hidden" name="pageNum" value="<%=request.getAttribute("pageNum")%>"/>
	</form>
	
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
				
				<!-- 이전글 , 다음글 버튼-->
<!-- 				<div class="paging3"> -->
<!-- 					<a href="javascript:alert('준비중');"><img class='write' src="./images/board/bt_pre.gif"></a> -->
<!-- 					<a href="javascript:alert('준비중');"><img class='write' src="./images/board/bt_nex.gif"></a> -->
<!-- 				</div> -->
				
				<!--수정,삭제,버튼-->
				<div class="paging3">
					<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
					<%
						if ( "M".equals(userEntity.getType_code()) || (userEntity.getSeq_id()).equals(boardContent.getUser_seq_id()) ) {
					%>
					<a href="javascript:goModify();"><img class='write' src="./images/board/bt_mod.gif"></a>
					<a href="javascript:goDelete();"><img class='write' src="./images/board/bt_del.gif"></a>
					<%
						}
					%>
				</div>
				<!--수정,삭제,버튼 끝 -->
				
				<!--게시판 시작-->
		 		<table class="tb3" style="bottom-margin:15;">
						<colgroup>
							<col width="80" />
							<col width="10" />
							<col width="649"./>
						</colgroup>
						<thead>
							<tr>
								<th colspan="3" class="subject"><%= boardContent.getTitle() %></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="subject"><b>작성자</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<span style="float:left; margin-right:285;"><%= boardContent.getUser_nic() %></span>
									<span class="cc">작성일</span>
									<span class="cc1"><%= boardContent.getWriteBoardDateTime() %></span>
									<span class="cc">조회</span>
									<span class="cc1"><%= boardContent.getHit() %></span>
									<span class="cc">추천</span>
									<span class="cc1"><%= boardContent.getRecommend() %></span>
									<span class="cc">비추천</span>
									<span class="cc1"><%= boardContent.getReject() %></span>
								</td>
							</tr>
							
							<tr>
								<td colspan="3" height="100" class="subject2">
									<div class="paging">
										<%
											if ( boardContent.getAttachmentList() != null ) {
												String imagePath = "";
												for ( int ii = 0 ; ii < boardContent.getAttachmentList().size() ; ii++ ) {
													AttachmentEntity attachment = boardContent.getAttachmentList().get(ii);
													imagePath = "./upload/2012/12/30/" + attachment.getP_file_name();
													imagePath = attachment.getL_file_name();
										%>
										<img src="<%= imagePath%>"/>
										<%
												}
											}
										%>
									</div>
									<%= boardContent.getContent().replaceAll("\r\n", "<br/>") %>
								</td>
							</tr>
		            
		              </tbody>
				</table>
		        
				<!--수정,삭제,버튼-->
				<div class="paging3">
					<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
					<%
						if ( "M".equals(userEntity.getType_code()) || (userEntity.getSeq_id()).equals(boardContent.getUser_seq_id()) ) {
					%>
					<a href="javascript:goModify();"><img class='write' src="./images/board/bt_mod.gif"></a>
					<a href="javascript:goDelete();"><img class='write' src="./images/board/bt_del.gif"></a>
					<%
						}
					%>
					<!-- 					<a href="./board/board_list.asp"><img class='write' src="./images/board/bt_write01.gif"></a> -->
<!-- 					<a href="javascript:doReply();"><img class='write' src="./images/board/bt_reply01.gif"></a> -->
				</div>
				<!--수정,삭제,버튼 끝 -->
				
				<!--게시판 끝-->
				
				<!--코멘트 등록-->
				<div class="comment_write">
					<div class="con"> 
						<div class="con1">
							<input type=checkbox id="isSecretReply" name="isSecretReply" value="secret" >
							비밀글
						</div>
						<div class="area"><textarea  id="txtContent" name="txtContent" style="width:650; height:45;"></textarea></div>
						<div class="bt">
							<a href="javascript:doReply();"><img src="./images/bt_commok.gif"></a>
						</div>
					</div>
				</div>
				
				<!--코멘트 DIV-->
				<div id="commentDiv">
					
				</div>
				
			</div>
		</td>
	</table>
</div>
</td></tr></table>

<!--푸터-->
<div class="bot_bg">
	<div class="bot_add01"><img src="./images/bottom.jpg"></div>
</div>

</body>
</html>