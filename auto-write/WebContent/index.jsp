<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
response.setDateHeader("Expires",0);
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
%>

<%
	// System.out.println("Login jsp2");
	String error = (String)request.getAttribute("result");
    if(error!=null){
        String msg = "No user information found." ; 
%>
<script language="javascript">
	alert("<%=msg%>");
</script>
<%
    }
%>


<html>
<head>
<title>Auto Write</title>

<!-- 헤더 스크립트 -->
<jsp:include page="WEB-INF/jsp/include/htmlHeader.jsp" flush="false"/>
<!-- 헤더 스크립트 끝 -->

<script language="JavaScript">
	function MM_openBrWindow() {
	    noticeWindow  =  window.open('new_notice.html','Notice','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=430,height=400, left=500 top=300');
	    noticeWindow.focus();
	}

	function loadNotice() {
	
		notice = 'Notice';
		if( getCookie(notice) != 'check' )
		{
			var url = new Array('notice_popup.html');
			//var url = new Array('pop2.html','pop3.html');
			//opt = "width=" + wSize + ",height=" + hSize + ",resizable=no,scrollbars=no";
			//remote = window.open(url, 'remote', opt);
	
			var wid = new Array(400,416); 
			var hei = new Array(420, 600); 
			var lef = new Array(0,400 ); 
	
	
			for (i = 0; i < url.length; i++)
				window.open(url[i], '_blank', 'width=' + wid[i] + ', height=' + hei[i] + ', left=' + lef[i] + ', top = 0' );
		}
	}
	
	function doLogin() {
		if(document.getElementById("u_id").value == '') {
			alert("ID를 입력하세요.");
			return;
		}
	
		if(document.getElementById("password").value == '') {
			alert("패스워드를 입력하세요.");
			return;
		}
	
		var params = gatherParams();
		params["type"]="S";
		
		new ajax.xhr.Request("dologin.do", "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join('')), callback, "POST");
		
	};
	
	function gatherParams() {
		// need: ID, PASS validation check.
		var u_id = document.getElementById("u_id").value;
		var password = document.getElementById("password").value;
		var params = {"u_id":u_id,"password":password};
	
		return params;
	}
	
	function callback(httpRequest) {
		if(httpRequest.readyState == 4 ) {
			if(httpRequest.status == 200 ) {
				//alert(httpRequest.responseText);
	
				var data = eval('(' + httpRequest.responseText + ')');
				if(data["Code"]!="S")
					alert(data["Text"]);
				else {
					location.href = "jspView.do?jsp=main/main";
				}
			}else alert('err');
		}
	}
	
	function onload(){
		var u_id = document.getElementById("u_id");
		u_id.focus();
	}
</script>
</head>

<body onLoad="onload()" oncontextmenu="return false">

<!--최상위 메뉴-->
<div class="ttop_menubg">
	<ul class="ttop_menu">
		<li>
			<span class="ll">
				<a href="#" style="cursor:hand" onclick="this.style.behavior='url(#default#homepage)';this.setHomePage('http://www.autowrite.co.kr');">
					시작페이지로설정
				</a>
				<a href="#" onfocus="this.blur()" onclick="{window.external.AddFavorite('http://www.autowrite.co.kr', '자동등록')}">
					즐겨찾기설정
				</a>
			</span>
			<span class="le"></span>
		</li>
	</ul>
</div>

<!--중앙 로그인 전체-->
<div class="login_bg">
	<br/>
	<br/>
	<br/>
	<br/>
	
	
	<div style="position:relative; left:50%;">
		<!--로그인-->
		<div class="login_bg01" style="">
			<form name="myForm" method="post" target="_self" action="dologin.do" style="margin: 0" autocomplete=off>
				<div style="margin-left:22; margin-top:48;">
					<input type="text" class="s_id" value="" name="u_id" id="u_id" onKeyPress="if (event.keyCode == 13) doLogin();" style="width:85;ime-mode:disabled;">
					<input style="margin-left:3;" type="checkbox" id="idsave" onclick="javascript:saveID(this);" tabindex="3"./>
					<span class="save_id">아이디 저장</span>
				</div>
				<div style="margin-left:22; margin-top:4;">
					<input type="password" class="s_id" value="" name="password" id="password" onKeyPress="if (event.keyCode == 13) doLogin();" style="width:85;">
					<span class="save_id">
					<a href="#"><img style="margin-left:7;" src="./images/bt_login.gif" onClick="doLogin();"></a></span>
				</div>
				<div style="margin-top:12; margin-left:22;">
					<span class="save_idpass"><b><a href="javascript:location.href='register.jsp'">회원가입</a></b></span>
					<span class="save_idpass1"><a href="javascript:alert('working');">아이디/패스워드찾기</a></span>
				</div>
			</form>
		</div>
		
	</div>
</div>

<!--푸터-->
<div align="center">
<jsp:include page="./WEB-INF/jsp/include/footer.jsp" flush="false"/>
</div>
<!--푸터 끝-->

</body>
</html>
