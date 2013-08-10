<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="java.security.MessageDigest"%>

<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	
	//String userNic = java.net.URLEncoder.encode(userEntity.getNic(), "UTF-8");
	String userNic = userEntity.getNic();
	String chatRoomSecurityKey = "91abfc4c5b8736da2925764dfc180b16";
	String encrypt = MD5(MD5(userNic + chatRoomSecurityKey) + chatRoomSecurityKey);
	
	if ( "M".equals(userEntity.getType_code()) ){
		encrypt = MD5(MD5(encrypt + chatRoomSecurityKey) + chatRoomSecurityKey);
	}
%>

<%!
	private String MD5(String str){
		String MD5 = ""; 
		
		try{
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(str.getBytes("UTF-8"));
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0 ; i < byteData.length ; i++) {
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			
			MD5 = sb.toString();
		} catch (Exception e){
			MD5 = null; 
		}
		
		return MD5;
	}
%> 


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HWARU</title>
<link href="./css/style3.css" rel="stylesheet" type="text/css" />
</head>

<body>
<embed height="600" width="100%" id="chatEmbed" src="http://www.gagalive.kr/livechat1.swf?chatroom=~~~HWARU&user=<%=userNic%>&encrypt=<%=encrypt%>"></embed>

</body>
</html>	
