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
	
	boolean isBlockedBoard = false;
	isBlockedBoard = ("080300".equals(category) || "080500".equals(category))
		&& !"M".equals(userEntity.getType_code()) 
		&& !(userEntity.getSeq_id()).equals(boardContent.getUser_seq_id());
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
		var frm = document.writeForm;
		
		frm.action = "boardListView.do";
		frm.jsp.value = "board/board";
		
		//frm.method = "get";frm.submit();
		
		var hrefStr = "boardListView.do?jsp=board/board";
		hrefStr += "&category=" + frm.category.value;
		hrefStr += "&region=" + frm.region.value;
		hrefStr += "&pageNum=" + frm.pageNum.value;
		
		location.href = hrefStr;
	}
	
	function goModify(){
		var frm = document.writeForm;
		
		//frm.method = "get";frm.submit();
		
		//return false;
		
		var hrefStr = "boardContentView.do?jsp=board/boardModifyView";
		hrefStr += "&seqId=" + frm.seqId.value;
		hrefStr += "&category=" + frm.category.value;
		hrefStr += "&region=" + frm.region.value;
		hrefStr += "&pageNum=" + frm.pageNum.value;
		
		location.href = hrefStr;
	}
	
	function goDelete(){
		var frm = document.writeForm;
		
		frm.actionType.value = "DELETE";
		
		if ( confirm("삭제하시겠습니까?") ){
			frm.action = "boardDelete.do";
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
	
	function makeChildReply(commentDivId){
		var targetDiv = document.getElementById(commentDivId);
		
		var relpyOrderId = getNextChildReplyOrderId(commentDivId);
		if ("_ERROR" == relpyOrderId){
			return;
		}
		var txtContentId = "txtContent_" + relpyOrderId;
		var isSecretReplyId = "isSecretReply_" + relpyOrderId;
		
		var inner = "";
		
		inner += "	<div class=\"con\">\n";
		inner += "		<input type=\"hidden\" name=\"" + relpyOrderId +"\" id=\"" + relpyOrderId +"\" value=\"" + commentDivId +"\"/>\n";
		inner += "		<div class=\"con1\">\n";
		inner += "			<input type=checkbox id=\"" + isSecretReplyId + "\" name=\"" + isSecretReplyId +"\" value=\"secret\" >\n";
		inner += "			비밀글\n";
		inner += "		</div>\n";
		inner += "		<div class=\"area\"><textarea id=\"" + txtContentId +"\" name=\"" + txtContentId +"\" style=\"width:650; height:45;\"></textarea></div>";
		inner += "		<div class=\"bt\">";
		inner += "			<a href=\"javascript:doChildReply('" + relpyOrderId +"');\"><img src=\"./images/bt_commok.gif\"></a>";
		inner += "		</div>\n";
		inner += "	</div>\n";
		
		//alert(inner);
		targetDiv.innerHTML = targetDiv.innerHTML + inner;
	}
	
	function doChildReply(commentDivId) {
		var params = gatherChildReplyWriteParams(commentDivId);
		
		var txtContent = document.getElementById("txtContent_"+commentDivId).value;
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
	
	function doReplaceReply(commentDivId) {
		var targetDiv = document.getElementById(commentDivId);
		var targetContentsId = commentDivId + "_contents";
		var replyContents = document.getElementById(targetContentsId).innerHTML;
		
		var relpyOrderId = "replyOrderId_" + commentDivId;
		var isSecretReply = "isSecretReply_" + commentDivId;
		var txtContent = "txtContent_" + commentDivId;
		
		var inner = "";
		
		inner += "	<div class=\"con\">\n";
		inner += "		<input type=\"hidden\" name=\"" + relpyOrderId +"\" id=\"" + relpyOrderId +"\" value=\"" + commentDivId +"\"/>\n";
		inner += "		<div class=\"con1\">\n";
		inner += "			<input type=checkbox id=\"" + isSecretReply + "\" name=\"" + isSecretReply +"\" value=\"secret\" >\n";
		inner += "			비밀글\n";
		inner += "		</div>\n";
		inner += "		<div class=\"area\"><textarea id=\"" + txtContent +"\" name=\"" + txtContent +"\" style=\"width:650; height:45;\">" + replyContents +"</textarea></div>";
		inner += "		<div class=\"bt\">";
		inner += "			<a href=\"javascript:doModifyReply('" + commentDivId +"');\"><img src=\"./images/bt_commok.gif\"></a>";
		inner += "		</div>\n";
		inner += "	</div>\n";
		
		//alert(inner);
		targetDiv.innerHTML = inner;
	}
	
	function doModifyReply(commentDivId) {
		var txtContentId = "txtContent_" + commentDivId;
		
		var params = gatherReplyModifyParams(commentDivId);
		
		var txtContent = document.getElementById(txtContentId).value;
		if ( txtContent.length == 0 ){
			alert("내용이 없습니다.");
			return;
		}
		
		var uri = "doModifyReply.do";
		var uriComponent = "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join(''));
		var method = "POST";
		
		params["type"]="S";
		
		new ajax.xhr.Request(uri, uriComponent, callbackReplyModify, method);
	}
	
	function doDeleteReply(commentDivId) {
		if ( !confirm("삭제하시겠습니까?") ){
			return;
		}
		var params = gatherReplyDeleteParams(commentDivId);
		
		var uri = "doDeleteReply.do";
		var uriComponent = "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join(''));
		var method = "POST";
		
		params["type"]="S";
		
		new ajax.xhr.Request(uri, uriComponent, callbackReplyDelete, method);
	}
	
	function doFeedback(isRecommend, isReject) {
		var params = gatherFeedbackParams(isRecommend, isReject);
		
		var uri = "boardFeedback.do";
		var uriComponent = "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join(''));
		var method = "POST";
		
		params["type"]="S";
		
		if ( "Y" == isRecommend ){
			new ajax.xhr.Request(uri, uriComponent, callbackRecommend, method);
		} else if ( "Y" == isReject ){
			new ajax.xhr.Request(uri, uriComponent, callbackReject, method);
		} else {
			alert("추천[" + isRecommend + "], 비추천[" + isReject + "은 잘못된 값입니다.");
		}
	}
	
	function gatherReplyWriteParams() {
		var txtContent = document.getElementById("txtContent").value;
		var category = "${category}";
		var seqId = "<%= boardContent.getSeq_id() %>";
		var JobType = "WRITE";
		var blindYn = "N";
		var replyOrderId = document.getElementById("replyOrderId").value;
		
		if ( document.getElementById("isSecretReply").checked == true ){
			blindYn = "Y";
		}
		
		var params =	{
							"txtContent":txtContent,
							"JobType":JobType,
							"category":category,
							"blindYn":blindYn,
							"replyOrderId":replyOrderId,
							"seqId":seqId
						};
		
		return params;
	}
	
	function gatherChildReplyWriteParams(commentDivId) {
		var isSecretReply = "isSecretReply_" + commentDivId;
		var txtContentId = "txtContent_" + commentDivId;
		var txtContent = document.getElementById(txtContentId).value;
		
		var category = "${category}";
		var seqId = "<%= boardContent.getSeq_id() %>";
		var JobType = "WRITE";
		var blindYn = "N";
		
		if ( document.getElementById(isSecretReply).checked == true ){
			blindYn = "Y";
		}
		
		var params =	{
							"txtContent":txtContent,
							"JobType":JobType,
							"category":category,
							"blindYn":blindYn,
							"replyOrderId":commentDivId,
							"seqId":seqId
						};
		
		return params;
	}
	
	function gatherReplyModifyParams(commentDivId) {
		var relpyOrderId = "replyOrderId_" + commentDivId;
		var replyOrder = document.getElementById(relpyOrderId).value;
		var isSecretReply = "isSecretReply_" + commentDivId;
		var txtContentId = "txtContent_" + commentDivId;
		var txtContent = document.getElementById(txtContentId).value;
		
		var category = "${category}";
		var seqId = "<%= boardContent.getSeq_id() %>";
		var JobType = "MODIFY";
		var blindYn = "N";
		
		
		if ( document.getElementById(isSecretReply).checked == true ){
			blindYn = "Y";
		}
		
		var params =	{
							"txtContent":txtContent,
							"JobType":JobType,
							"category":category,
							"blindYn":blindYn,
							"replyOrderId":replyOrder,
							"seqId":seqId
						};
		
		return params;
	}
	
	
	function gatherReplyDeleteParams(commentDivId) {
		var category = "${category}";
		var seqId = "<%= boardContent.getSeq_id() %>";
		var JobType = "DELETE";
		
		var params =	{
							"JobType":JobType,
							"category":category,
							"replyOrderId":commentDivId,
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
	
	
	function gatherFeedbackParams(isRecommend, isReject) {
		var category = "${category}";
		var userSeqId = "<%= boardContent.getUser_seq_id() %>";
		var userId = "";
		var userNic = "";
		var boardSeqId = "<%= boardContent.getSeq_id() %>";
		
		var params =	{
							"JobType":"READ",
							"userSeqId":userSeqId,
							"userId":userId,
							"userNic":userNic,
							"category":category,
							"boardSeqId":boardSeqId,
							"isRecommend":isRecommend,
							"isReject":isReject
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
	
	
	function callbackReplyModify(httpRequest) {
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
	
	
	function callbackReplyDelete(httpRequest) {
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
	
	
	function callbackRecommend(httpRequest){
		var recommendCount = document.getElementById("recommendCount").innerHTML;
		
		if (httpRequest.readyState == 4) {
			if (httpRequest.status == 200) {
				var data = eval('(' + httpRequest.responseText + ')');
				if (data["Code"] != "S") {
					alert("Error Desc: " + data["Text"]);
				} else {
					document.getElementById("recommendCount").innerHTML = eval(recommendCount) + 1;
					alert("이 게시물을 추천 하셨습니다.");
				}
			}
		}
	}
	
	
	function callbackReject(httpRequest){
		var rejectCount = document.getElementById("rejectCount").innerHTML;
		
		if (httpRequest.readyState == 4) {
			if (httpRequest.status == 200) {
				var data = eval('(' + httpRequest.responseText + ')');
				if (data["Code"] != "S") {
					alert("Error Desc: " + data["Text"]);
				} else {
					document.getElementById("rejectCount").innerHTML = eval(rejectCount) + 1;
					alert("이 게시물을 비추천 하셨습니다.");
				}
			}
		}
	}
	
	
	function makeCommentView(commentData){
		var inner = "";
		var currReplyOrderId = "";
		
		for ( var ii = 0 ; ii < commentData.Data.length ; ii ++ ){
			var commentUserSeqId = commentData.Data[ii].USER_NICUSER_SEQ_ID;
			var commentUserId = commentData.Data[ii].USER_ID;
			var commentUserNic = commentData.Data[ii].USER_NIC;
			var commentWriteDate = "";
			if ( commentData.Data[ii].WRITE_DATETIME != null ) {
				var tempDate = commentData.Data[ii].WRITE_DATETIME;
				tempDate.month = eval(tempDate.month) + 1;
				commentWriteDate = tempDate.month + "-" + tempDate.date + " " + tempDate.hours + ":" + tempDate.minutes;
			}
			var commentContent = commentData.Data[ii].CONTENT;
			var commentSeqId = commentData.Data[ii].SEQ_ID;
			var commentDivId = commentData.Data[ii].REPLY_ORDER_ID;
			var commentBlindYn = commentData.Data[ii].BLIND_YN;
			var commentReplyDepth = commentData.Data[ii].REPLY_DEPTH;
			var commentUserClassImgPath = commentData.Data[ii].USER_CLASS_IMG_PATH;
			var divClassName = "comment" + commentReplyDepth;
			if ( commentReplyDepth > eval(5) ) {
				commentReplyDepth = 5;
				divClassName = "comment5";
			}
			
			currReplyOrderId = commentDivId;
			
			<%
				if ( "M".equals(userEntity.getType_code()) || (userEntity.getSeq_id()).equals(boardContent.getUser_seq_id()) ) {
			%>
			commentContent = commentContent.split("\r\n").join("<br/>");
			<%
				} else if ( isBlockedBoard ) {
			%>
			commentContent = "글을 볼 권한이 없습니다.";
			<%
				} else {
			%>
			if ( "Y" == commentBlindYn ) {
				commentContent = "비밀글입니다.";
			} else {
				commentContent = commentContent.split("\r\n").join("<br/>");
			}
			<%
				}
			%>
			
			inner += "<div class=\"" + divClassName + "\" id=\"";
			inner += commentDivId;
			inner += "\">\n";
			inner += "	<div class=\"top\"><span style=\"float:left; margin-left:15; margin-right:25; \">";
			inner += "<img src=\"images/" + commentUserClassImgPath + "\"/>";
			inner += "<a href=\"javascript:;\" onclick=\"javascript:showSideView(this, '";
			inner += commentUserSeqId;
			inner += "', '";
			inner += commentUserNic;
			inner += "', '";
			inner += commentUserId;
			inner += "');\">";
			inner += commentUserNic;
			inner += "</a>";
			inner += "</span><span class=\"cc\">작성일</span> <span class=\"cc1\">";
			inner += commentWriteDate;
			inner += "</span><span style=\"float:right; margin-left:15; margin-right:25; \">";
			inner += "<a href=\"javascript:makeChildReply('" + commentDivId + "');\">댓글</a>";
			<%
				if ( "M".equals(userEntity.getType_code()) ) {
			%>
			inner += "<a href=\"javascript:doReplaceReply('" + commentDivId + "');\">수정</a>";
			inner += "<a href=\"javascript:doDeleteReply('" + commentDivId + "');\">삭제</a>";
			<%
				} else {
			%>
			if ( commentUserNic == "<%=userEntity.getNic()%>" ){
				inner += "<a href=\"javascript:doReplaceReply('" + commentDivId + "');\">수정</a>";
				inner += "<a href=\"javascript:doDeleteReply('" + commentDivId + "');\">삭제</a>";
			}
			<%
				}
			%>
			inner += "</span></div>\n";
			inner += "<div class=\"con\" id=\"" + commentDivId + "_contents\">\n";
			inner += commentContent;
			inner += "\n</div>\n";
			inner += "</div>\n";
		}
		//alert(inner);
		
		if ( currReplyOrderId != null && currReplyOrderId.length > 0 ){
			// 다음 댓글 순서 세팅
			var nextReplyOrderId = getNextReplyOrderId(currReplyOrderId);
			document.getElementById("replyOrderId").value = nextReplyOrderId;
			//alert(currReplyOrderId + "|" + nextReplyOrderId);
		}
		
		
		return inner;
	}
	
	function getNextReplyOrderId(currReplyOrderId){
		if ( currReplyOrderId == "ZZZ" ){
			return "ERROR";
		}
		
		var nextReplyOrderId;
		
		// "A".charCodeAt(0)=65, "Z".charCodeAt(0)=90
		if ( currReplyOrderId.charCodeAt(1) == 90 && currReplyOrderId.charCodeAt(2) == 90 ){
			// ?ZZ처리
			nextReplyOrderId = String.fromCharCode(currReplyOrderId.charCodeAt(0) + 1) + "AA";
		} else if ( currReplyOrderId.charCodeAt(1) != 90 && currReplyOrderId.charCodeAt(2) == 90 ){
			// ??Z 처리
			nextReplyOrderId = currReplyOrderId.charAt(0) + String.fromCharCode(currReplyOrderId.charCodeAt(1) + 1) + "A";
		} else {
			nextReplyOrderId = currReplyOrderId.substring(0,2) + String.fromCharCode(currReplyOrderId.charCodeAt(2) + 1);
		}
		
		return nextReplyOrderId;
	}
	
	function getNextChildReplyOrderId(parentId){
		var childId = null;
		
		var parentNode = document.getElementById(parentId);
		for ( var ii = 65 ; ii <= 90 ; ii ++ ) {
			childId = parentId + String.fromCharCode(ii);
			if ( document.getElementById(childId) == null ){
				break;
			}
			
			if ( ii == 90 ){
				alert("댓글의 댓글은 최대 26개까지 작성하실 수 있습니다.");
				childId = "_ERROR";
			}
		}
		
		// alert("childId:"+ childId);
		
		return childId;
	}
</script>

<body onLoad="javascript:loadBanner();doReadReply();">
	<form name="writeForm" method="post" action="boardContentView.do" enctype="multipart/form-data">
		<input type="hidden" name="jsp" value="board/boardModifyView"/>
		<input type="hidden" name="seqId" value="<%=boardContent.getSeq_id()%>"/>
		<input type="hidden" name="category" value="<%=boardContent.getCategory()%>"/>
		<input type="hidden" name="region" value="<%=boardContent.writeRegion()%>"/>
		<input type="hidden" name="pageNum" value="<%=request.getAttribute("pageNum")%>"/>
		<input type="hidden" name="actionType" value=""/>
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
					<table><tr>
							<td width="350" align="left">
								<a href="javascript:doFeedback('Y', 'N');"><img class='write' src="./images/bt_recommend.gif"></a>
								<a href="javascript:doFeedback('N', 'Y');"><img class='write' src="./images/bt_reject.gif"></a>
							</td>
							<td width="400" align="right">
								<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
								<%
									if ( "M".equals(userEntity.getType_code()) || (userEntity.getSeq_id()).equals(boardContent.getUser_seq_id()) ) {
								%>
								<a href="javascript:goModify();"><img class='write' src="./images/board/bt_mod.gif"></a>
								<a href="javascript:goDelete();"><img class='write' src="./images/board/bt_del.gif"></a>
								<%
									}
								%>
							</td>
					</tr></table>
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
									<span style="float:left; margin-right:180;">
										<img src="images/<%=boardContent.getWriter_image_path()%>"/>
										<a href="javascript:;" onclick="javascript:showSideView(this, '<%= boardContent.getUser_seq_id() %>', '<%= boardContent.getUser_nic() %>', '<%= boardContent.getUser_id() %>');">
											<%= boardContent.getUser_nic() %>
										</a>
									</span>
									<span class="cc">작성일</span>
									<span class="cc1"><%= boardContent.getWrite_datetime().substring(0, 16) %></span>
								<%
									if ( !category.startsWith("02") && !category.startsWith("03") ) {
								%>
									<span class="cc">조회</span>
									<span class="cc1"><%= boardContent.getHit() %></span>
								<%
									}
								%>
									<span class="cc">추천</span>
									<span class="cc1" id="recommendCount"><%= boardContent.getRecommend() %></span>
									<span class="cc">비추천</span>
									<span class="cc1" id="rejectCount"><%= boardContent.getReject() %></span>
								</td>
							</tr>
							
							<%
								if ( category.startsWith("04") ){
							%>
							<tr>
								<td class="subject"><b>업소명</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject"><%= boardContent.getFacility_name() %></td>
							</tr>
							<tr>
								<td class="subject"><b>방문일시</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject"><%= boardContent.getVisit_time() %></td>
							</tr>
							<tr>
								<td class="subject"><b>언니예명</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject"><%= boardContent.getWaiteress_name() %></td>
							</tr>
							<%
								}
							%>

							<%
								if ( category.startsWith("02") ){
							%>
							<tr>
								<td class="subject"><b>업소명</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject"><%=boardContent.getFacility_name()%></td>
							</tr>
							<tr>
								<td class="subject"><b>담당자</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject"><%=boardContent.getFacility_user_name()%></td>
							</tr>
							<tr>
								<td class="subject"><b>대표전화</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject"><%=boardContent.getFacility_phone()%></td>
							</tr>
							<tr>
								<td class="subject"><b>주소</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject"><%=boardContent.getFacility_address()%></td>
							</tr>
							<tr>
								<td colspan="3" class="subject"><b>업소 소개</b></td>
							</tr>
							<%
								}
							%>
							
							
							<%
								// 화류센터-운영자상담 비밀글 표시
								if ( isBlockedBoard ) {
							%>
							<tr>
								<td colspan="3" height="100" class="subject2">
									글을 볼 권한이 없습니다.
								</td>
							</tr>
							<%
								}  else {
							%>
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
		            		<%
								}
		            		%>
		            		
		              </tbody>
				</table>
		        
				<!--수정,삭제,버튼-->
				<div class="paging3">
					<table><tr>
							<td width="350" align="left">
								<a href="javascript:doFeedback('Y', 'N');"><img class='write' src="./images/bt_recommend.gif"></a>
								<a href="javascript:doFeedback('N', 'Y');"><img class='write' src="./images/bt_reject.gif"></a>
							</td>
							<td width="400" align="right">
								<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
								<%
									if ( "M".equals(userEntity.getType_code()) || (userEntity.getSeq_id()).equals(boardContent.getUser_seq_id()) ) {
								%>
								<a href="javascript:goModify();"><img class='write' src="./images/board/bt_mod.gif"></a>
								<a href="javascript:goDelete();"><img class='write' src="./images/board/bt_del.gif"></a>
								<%
									}
								%>
							</td>
					</tr></table>
				</div>
				<!--수정,삭제,버튼 끝 -->
				
				<!--게시판 끝-->
				
				<!--코멘트 등록-->
				<div class="comment_write">
					<div class="con"> 
						<input type="hidden" name="replyOrderId" id="replyOrderId" value="AAA"/>
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