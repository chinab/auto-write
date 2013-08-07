<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<META NAME="ROBOTS" CONTENT="NOINDEX, NOFOLLOW">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="No-cache" />
<meta http-equiv="Cache-Control" content="No-cache" />
<title>회원가입</title>

<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<link href="./css/style3.css" rel="stylesheet" type="text/css" />
<script src="js/ajax.js"></script>
<script src="js/json2.js"></script>
</head>

<script>
	function doLogin() {
		if(document.getElementById("l_id").value == '') {
			alert("ID를 입력하세요.");
			return;
		}
	
		if(document.getElementById("l_password").value == '') {
			alert("패스워드를 입력하세요.");
			return;
		}
	
		var params = gatherLoginParams();
		params["type"]="S";
		
		new ajax.xhr.Request("dologin.do", "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join('')), callbackLogin, "POST");
		
	};
	
	function gatherLoginParams() {
		// need: ID, PASS validation check.
		var u_id = document.getElementById("l_id").value;
		var password = document.getElementById("l_password").value;
		var params = {"u_id":u_id,"password":password};
	
		return params;
	}
	
	function callbackLogin(httpRequest) {
		if(httpRequest.readyState == 4 ) {
			if(httpRequest.status == 200 ) {
				//alert(httpRequest.responseText);
	
				var data = eval('(' + httpRequest.responseText + ')');
				if(data["Code"]!="S")
					alert(data["Text"]);
				else {
					location.href = "mainView.do?jsp=main/main";
				}
			}else alert('err');
		}
	}

	function userRegister(){
		if ( !validate() ) {
			return;
		}
		
		var params = gatherParams();
		//params["type"]="S";
		
		new ajax.xhr.Request("register.do", "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join('')), callback, "POST");
	}
	
	function idDupCheck(){
		var id = document.getElementById("u_id").value;
		//alert(id);
		if ( id.length == 0 ) {
			alert("ID를 입력하세요.");
			document.getElementById("u_id").focus();
			return;
		} else {
			var params = {"ID":id};
			
			new ajax.xhr.Request("idDupCheck.do", "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join('')), callbackIdDupCheck, "POST");
		}
	}
	
	function nicDupCheck(){
		var nic = document.getElementById("nic").value;
		
		if ( nic.length == 0 ) {
			alert("닉네임을 입력하세요.");
			document.getElementById("nic").focus();
			return;
		} else {
			var params = {"NIC":nic};
			
			new ajax.xhr.Request("nicDupCheck.do", "&p=" + encodeURIComponent((JSON.stringify(params)).split( "null" ).join('')), callbackNicDupCheck, "POST");
		}
	}
	
	function gatherParams() {
		var u_id = document.getElementById("u_id").value;
		var password = document.getElementById("password").value;
		var nic = document.getElementById("nic").value;
		var email = document.getElementById("email").value;
		var name = document.getElementById("name").value;
		var type_code = "";
		var typeCodeElement = document.getElementsByName("type_code");
		for ( var ii = 0 ; ii < typeCodeElement.length ; ii ++ ) {
			if ( typeCodeElement[ii].checked ) {
				type_code = typeCodeElement[ii].value;
			}
		}
		var service_code = "";
		if ( type_code == "B" ){
			var serviceCodeElement = document.getElementsByName("service_code");
			for ( var ii = 0 ; ii < serviceCodeElement.length ; ii ++ ) {
				if ( serviceCodeElement[ii].checked ) {
					service_code = serviceCodeElement[ii].value;
				}
			}
		}
		
		var params = {"u_id":u_id,"password":password,"nic":nic,"email":email,"name":name,"type_code":type_code,"service_code":service_code};
		//alert(params);
		return params;
	}
	
	function validate(){
		var u_id = document.getElementById("u_id").value;
		var id_dup_check = document.getElementById("id_dup_check").value;
		var nic_dup_check = document.getElementById("nic_dup_check").value;
		var password = document.getElementById("password").value;
		var password_confirm = document.getElementById("password_confirm").value;
		var nic = document.getElementById("nic").value;
		var email = document.getElementById("email").value;
		var name = document.getElementById("name").value;
		var type_code = "";
		var typeCodeElement = document.getElementsByName("type_code");
		for ( var ii = 0 ; ii < typeCodeElement.length ; ii ++ ) {
			if ( typeCodeElement[ii].checked ) {
				type_code = typeCodeElement[ii].value;
			}
		}
		
		if ( u_id.length == 0 ) {
			alert("아이디를 입력 해 주세요.");
			document.getElementById("u_id").focus();
			return false;
		}
		if ( u_id.length < 4 ) {
			alert("아이디 최소 4자이상 입력하세요.");
			document.getElementById("u_id").focus();
			return false;
		}
		if ( id_dup_check == "false" ) {
			alert("아이디 중복확인을 해 주세요.");
			return false;
		}
		if ( nic_dup_check == "false" ) {
			alert("닉네임 중복확인을 해 주세요.");
			return false;
		}
		if ( password.length == 0 ) {
			alert("패스워드를 입력 해 주세요.");
			document.getElementById("password").focus();
			return false;
		} else if ( password_confirm.length == 0 ) {
			alert("패스워드를 확인 해 주세요.");
			document.getElementById("password_confirm").focus();
			return false;
		} else if ( password != password_confirm ) {
			alert("패스워드가 다릅니다.");
			document.getElementById("password_confirm").focus();
			return false;
		}
		if ( nic.length == 0 ) {
			alert("닉네임을 입력 해 주세요.");
			document.getElementById("nic").focus();
			return false;
		}
		if ( nic.length < 2 ) {
			alert("닉네임을 2자 이상 입력 해 주세요.");
			document.getElementById("nic").focus();
			return false;
		}
		if ( email.length == 0 ) {
			alert("이메일을 입력 해 주세요.");
			document.getElementById("email").focus();
			return false;
		}
		if ( name.length == 0 ) {
			alert("이름을 입력 해 주세요.");
			document.getElementById("name").focus();
			return false;
		}
		
		return true;
	}

	function callback(httpRequest) {
		if(httpRequest.readyState == 4 ) {
			if(httpRequest.status == 200 ) {
				//alert(httpRequest.responseText);

				var data = eval('(' + httpRequest.responseText + ')');
				if(data["Code"]!="S") {
					alert(data["Text"]);
					location.href = "./index.jsp";
				} else {
					alert("회원가입이 완료되었습니다.");
					location.href = "./mainView.do?jsp=main/main";
				}
			} else {
				alert("통신오류. 다시 시도 해 주십시오.");
			}
		}
	}
	
	function callbackIdDupCheck(httpRequest) {
		if(httpRequest.readyState == 4 ) {
			if(httpRequest.status == 200 ) {
				//alert(httpRequest.responseText);
				var data = eval('(' + httpRequest.responseText + ')');
				if(data["Code"]=="E"){
					alert("Error Desc: " + data["Text"]);
				} else if (data["Code"]=="D"){
					alert("중복된 ID입니다.\n다른 ID를 사용해 주세요.");
				} else if (data["Code"]=="S"){
					document.getElementById("id_dup_check").value = "true";
					alert("사용가능한 ID입니다.");
				} else {
					alert("Error Desc: " + data["Text"]);
				}
			} else {
				alert("통신오류. 다시 시도 해 주십시오.");
			}
		}
	}

	function callbackNicDupCheck(httpRequest) {
		if(httpRequest.readyState == 4 ) {
			if(httpRequest.status == 200 ) {
				//alert(httpRequest.responseText);
				var data = eval('(' + httpRequest.responseText + ')');
				if(data["Code"]=="E"){
					alert("Error Desc: " + data["Text"]);
				} else if (data["Code"]=="D"){
					alert("중복된 닉네임입니다.\n다른 닉네임을 사용해 주세요.");
				} else if (data["Code"]=="S"){
					document.getElementById("nic_dup_check").value = "true";
					alert("사용가능한 닉네임입니다.");
				} else {
					alert("Error Desc: " + data["Text"]);
				}
			} else {
				alert("통신오류. 다시 시도 해 주십시오.");
			}
		}
	}
</script>

<body onLoad="">

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





<!--서브 컨텐츠 시작지점-->

<div class="h_board">
	<input type="hidden" name="category" value=""/>
	<input type="hidden" name="actionType" value=""/>
	<input type="hidden" name="type" value="S"/>
		
	<table width="1000" border="0" cellpadding="0" cellspacing="0">
	    <colgroup>
	    	<col width="./" />
	    </colgroup>
	    
	    <tr>
			<td valign="top">
				<!--리스트시작-->
				<div class="admin_con">
					<div class="sub_title_bar"><span class="title">회원가입</span></div>
					
					<!--게시판 시작-->
					<table class="tb3" style="bottom-margin:15;">
						<colgroup>
							<col width="100" />
							<col width="10" />
							<col width="./"./>
						</colgroup>
		
						<tbody>
						    <tr>
								<td height="3" background="#cccccc" colspan="3"></td>
							</tr>
							
							<tr>
								<td class="subject"><b>이름</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="text" id="name" name="name" class="s_id" size="65" style="width:150;" placeholder="이름">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>아이디</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="hidden" id="id_dup_check" name="id_dup_check" value="false"/>
									<input type="text" id="u_id" name="u_id" class="s_id" size="65" style="width:150;" style="ime-mode:disabled" placeholder="아이디">
									<a href="javascript:idDupCheck();">
										<img class='write' src="./images/bt_s1.gif">
									</a>
									<span class="ber_text">* 최소 4자이상 입력하세요.</span>
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>회원 닉네임</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="hidden" id="nic_dup_check" name="nic_dup_check" value="false"/>
									<input type="text" id="nic" name="nic" class="s_id" size="65" style="width:150;" placeholder="닉네임">
									<a href="javascript:nicDupCheck();">
										<img class='write' src="./images/bt_s1.gif">
									</a>
									<span class="ber_text">공백없이 한글,영문,숫자만 입력 가능 (한글2자, 영문4자 이상) </span>
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>비밀번호</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="password" id="password" name="password" class="s_id" size="65" style="width:150;">
								</td>
							</tr>
		
							<tr>
								<td class="subject"><b>비밀번호 확인</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input type="password" id="password_confirm" name="password_confirm" class="s_id" size="65" style="width:150;">
								</td>
							</tr>
		
							<tr>
								<td class="subject"><b>E-Mail</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input id="email" name="email" class="s_id" type="text" size="65" style="width:150;">
								</td>
							</tr>
							
							<tr>
								<td class="subject"><b>Tel</b></td>
								<td><img src="./images/board_line.gif" width="1" height="22" /></td>
								<td class="subject">
									<input id="tel1" name="tel1" class="s_id" type="text" size="20" style="width:50;"> - 
									<input id="tel2" name="tel2" class="s_id" type="text" size="20" style="width:50;"> - 
									<input id="tel3" name="tel3" class="s_id" type="text" size="20" style="width:50;">
									<br/>
									<br/>
									<strong>등업 후 이용 가능하오니 연락 가능한 전화번호를 기재하여 주시기 바랍니다.</strong>
								</td>
							</tr>
							
		            	</tbody>
					</table>
					
					<!--수정,삭제,버튼-->
					<div class="paging3">
						<a href="javascript:userRegister();">
							<img class='write' src="./images/bt_memok.gif">
						</a>
						<a href="javascript:reset();">
							<img class='write' src="./images/bt_memno.gif">
						</a>
					</div>
				<!--게시판 끗-->
				</div>
			</td>
		</tr>
	</table>
</div>
<!-- 서브 콘텐츠 끝 -->

<!--푸터-->
<jsp:include page="./WEB-INF/jsp/include/footer.jsp" flush="false"/>
<!--푸터 끝-->

</body>
</html>
 
 

