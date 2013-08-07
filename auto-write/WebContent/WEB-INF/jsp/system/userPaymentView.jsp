<%@page import="com.jekyll.common.framework.entity.PaymentListEntity"%>
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
	
	PaymentMasterEntity paymentMasterEntity = (PaymentMasterEntity) request.getAttribute("PaymentMaster");
	String category = request.getAttribute("category").toString();
	
	MenuDto leafMenu = (MenuDto)userEntity.getMenuMap().get(category);
	String leftMenuStr = leafMenu.getParmenuid();
	MenuDto leftMenu = (MenuDto)userEntity.getMenuMap().get(leftMenuStr);
%>

<%
	Map<String, String> facilityCategoryMap = new HashMap();
	facilityCategoryMap.put("01", "오피");
	facilityCategoryMap.put("02", "안마");
	facilityCategoryMap.put("03", "휴게,대떡");
	facilityCategoryMap.put("04", "건마");
	facilityCategoryMap.put("05", "립카페");
	facilityCategoryMap.put("06", "핸플");
	facilityCategoryMap.put("07", "키스방");
	facilityCategoryMap.put("08", "주점");
	facilityCategoryMap.put("09", "기타");
	
	Map<String, String> bannerTypeMap = new HashMap();
	bannerTypeMap.put("N", "없음");
	bannerTypeMap.put("B", "메인,센터");
	bannerTypeMap.put("M", "메인");
	bannerTypeMap.put("C", "센터");
	
	Map<String, String> paymentMap = new HashMap();
	paymentMap.put("R", "결제대기");
	paymentMap.put("Y", "결제완료");
	paymentMap.put("N", "미납");
	paymentMap.put("E", "이벤트결제");
	
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
		var frm = document.paymentForm;
		
		frm.action = "userPaymentList.do";
		frm.jsp.value = "system/userPaymentList";
		frm.category.value = "${category}";
		
		frm.submit();
		
		return false;
	}
	
	function goModify(){
		var frm = document.paymentForm;
		
		frm.action = "userPaymentView.do";
		frm.jsp.value = "system/userPaymentModify";
		frm.category.value = "${category}";
		
		frm.submit();
		
		return false;
	}
	
	function goDelete(){
		var frm = document.paymentForm;
		
		if ( confirm("삭제하시겠습니까?") ){
			frm.action = "userPaymentDelete.do";
			frm.submit();
		}
	}
</script>

<body onLoad="javascript:loadBanner();">
	<form name="paymentForm" method="post">
		<input type="hidden" name="jsp" value=""/>
		<input type="hidden" name="category" value="<%=category%>"/>
		<input type="hidden" name="seqId" value="<%=paymentMasterEntity.getSeq_id()%>"/>
		<input type="hidden" name="category" value="<%=paymentMasterEntity.getFacility_category()%>"/>
		<input type="hidden" name="region" value="<%=paymentMasterEntity.getFacility_region()%>"/>
		<input type="hidden" name="pageNum" value="<%=request.getAttribute("pageNum")%>"/>
		<input type="hidden" name="actionType" value="READ"/>
		
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
				<a href="javascript:goModify();"><img class='write' src="./images/board/bt_mod.gif"></a>
				<a href="javascript:goDelete();"><img class='write' src="./images/board/bt_del.gif"></a>
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
							<%= paymentMasterEntity.getUser_nic()%>
						</td>
					</tr>
					<tr>
							<td class="subject"><b>업소명</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject">
								<%= paymentMasterEntity.getFacility_name()%>
							</td>
						</tr>
						<tr>
							<td class="subject"><b>담당자</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject">
								<%= paymentMasterEntity.getFacility_user_name()%>
							</td>
						</tr>
						<tr>
							<td class="subject"><b>대표전화</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject">
								<%= paymentMasterEntity.getFacility_phone()%>
							</td>
						</tr>
						<tr>
							<td class="subject"><b>주소</b></td>
							<td><img src="./images/board_line.gif" width="1" height="22" /></td>
							<td class="subject">
								<%= paymentMasterEntity.getFacility_address()%>
							</td>
						</tr>
						<tr>
							<td colspan="3" class="subject"><b>업소 소개</b></td>
						</tr>
						<tr>
							<td colspan="3" class="subject2">
								<%
									String facilityIntroduce = paymentMasterEntity.getFacility_introduce();
									if ( facilityIntroduce != null ) {
										out.println(facilityIntroduce.replaceAll("\r\n", "<br/>"));
									}
								%>
	                        </td>
						</tr>
					
					<tr>
						<td class="subject"><b>시작일</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<%= paymentMasterEntity.getStart_datetime()%>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>종료일</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<%= paymentMasterEntity.getEnd_datetime()%>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>지역</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<%= paymentMasterEntity.getFacility_region()%>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>업종</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
						    <%= facilityCategoryMap.get(paymentMasterEntity.getFacility_category()) %>
		 				</td>
					</tr>
					
					<tr>
						<td class="subject"><b>결제상태</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<%= paymentMap.get(paymentMasterEntity.getPayment_status()) %>
		 				</td>
					</tr>
					<tr>
						<td class="subject"><b>배너타입</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
							<%= bannerTypeMap.get(paymentMasterEntity.getBanner_type_code()) %>
		 				</td>
					</tr>
					
					<tr>
						<td class="subject"><b>메인배너</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
						<%
							String mainBannerWebLink = paymentMasterEntity.getMain_banner_web_link();
							if ( mainBannerWebLink != null && mainBannerWebLink.length() > 0 ){
								out.println("<img src='" + mainBannerWebLink + "'/>");	
							}
						%>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>센터배너</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
						<%
							String centerBannerWebLink = paymentMasterEntity.getCenter_banner_web_link();
							if ( centerBannerWebLink != null && centerBannerWebLink.length() > 0 ){
								out.println("<img src='" + centerBannerWebLink + "'/>");	
							}
						%>
						</td>
					</tr>
					<tr>
						<td class="subject"><b>대표이미지</b></td>
						<td><img src="./images/board_line.gif" width="1" height="22" /></td>
						<td class="subject">
						<%
							String titleImageWebLink = paymentMasterEntity.getL_file_name();
							if ( titleImageWebLink != null && titleImageWebLink.length() > 0 ){
								out.println("<img src='" + titleImageWebLink + "'/>");	
							}
						%>
						</td>
					</tr>
				</tbody>
			</table>
			
			<!-- 버튼 -->
			<!--수정,삭제,버튼-->
			<div class="paging3">
				<a href="javascript:goList();"><img class='write' src="./images/board/bt_list01.gif"></a>
				<a href="javascript:goModify();"><img class='write' src="./images/board/bt_mod.gif"></a>
				<a href="javascript:goDelete();"><img class='write' src="./images/board/bt_del.gif"></a>
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