	var isLogouted = false;
	
	
	
	function viewMyInfo(){
	    popupWindow  =  window.open('myInfoView.do?jsp=popup/myInfoView','myInfoView','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=400, left=500 top=300');
	    popupWindow.focus();
	}
	
	function viewPointLog(){
	    popupWindow  =  window.open('pointLogView.do?jsp=popup/pointLogView','pointLogView','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=600,height=730, left=400 top=200');
	    popupWindow.focus();
	}
	
	function goChat(){
	    popupWindow  =  window.open('jspView.do?jsp=popup/chatting','chatting','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=600,height=400, left=500 top=300');
	    popupWindow.focus();
	}
	
	function toggleLayer(currMenuId, targetUri, title) {
		if (targetUri && targetUri != 'null') {
			showMenu(currMenuId, targetUri, title, null);
			return;
		}
	}

	function showMenu(currMenuId, targetUri, title, e) {
		window.location = (targetUri + (targetUri.indexOf('=') != -1 ? "&" : "?") + "title=" + title);
	}
	
	function menuClick(menuurl) {
		window.location = menuurl;
	}
	
	function doLogout() {
		if (!isLogouted){
			var uri = "doLogout.do";
			var uriComponent = "&p=" + encodeURIComponent((JSON.stringify({})).split("null").join(''));
			var method = "POST";
			new ajax.xhr.Request(uri, uriComponent, callback_logout, method);
		}
	}
	
	function callback_logout(httpRequest) {
		if (httpRequest.readyState == 4) {
			if (httpRequest.status == 200) {
	
				var data = eval('(' + httpRequest.responseText + ')');
				if (data["Code"] != "S")
					alert("Error Desc: " + data["Text"]);
				else {
					isLogouted = true;
					window.top.location = "index.jsp";
				}
			}
		}
	}
	
	// CKEditor 대체
	function enableCKEditor(content){
		//<![CDATA[
		CKEDITOR.replace( content,{ 
		    toolbar: 
		    [ 
		      ['Bold','Italic','Underline','Strike','-'], 
		      ['TextColor','BGColor'],
		      ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		      ['Styles','Format','Font','FontSize']
		      //,
		      //['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak']
		    ] 
		  }); 
		//]]
	}
	

	