<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.jekyll.common.framework.entity.UserEntity"%>
<%@ page import="com.jekyll.common.framework.entity.PaymentListEntity"%>
<%@ page import="com.jekyll.common.framework.entity.PaymentMasterEntity"%>
<%@ page import="com.jekyll.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	PaymentListEntity paymentListEntity = (PaymentListEntity) request.getAttribute("PaymentListEntity");
	List<PaymentMasterEntity> paymentList = paymentListEntity.getPaymentMasterList();
	
	String category = request.getAttribute("category").toString();
	
	MenuDto leafMenu = (MenuDto)userEntity.getMenuMap().get(category);
	String leftMenuStr = leafMenu.getParmenuid();
	MenuDto leftMenu = (MenuDto)userEntity.getMenuMap().get(leftMenuStr);
	
	String searchKey = paymentListEntity.writeSearchKey();
	String searchValue = paymentListEntity.writeSearchValue();
	
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

<!-- 배너 스크립트 -->
<jsp:include page="../include/banner.jsp" flush="false"/>
<!-- 배너 스크립트 끝 -->

<script>
	function readBoard(seqId, category, pageNum){
		var frm = document.listForm;
		
		frm.action = "userPaymentView.do";
		frm.jsp.value = "system/userPaymentView";
		frm.seqId.value = seqId;
		frm.category.value = category;
		frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
	function listBoard(category, pageNum){
		var frm = document.listForm;
		
		frm.action = "userPaymentList.do";
		frm.jsp.value = "system/userPaymentList";
		frm.category.value = category;
		frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
	function viewUserInfo(userSeqId){
		alert("userInfo:" + userSeqId);
	}
</script>

<body onLoad="javascript:loadBanner();">
	<form name="listForm" method="post">
		<input type="hidden" name="jsp" value=""/>
		<input type="hidden" name="seqId" value=""/>
		<input type="hidden" name="category" value="<%=category%>"/>
		<input type="hidden" name="pageNum" value="<%=paymentListEntity.getPageNum()%>"/>

	
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
	      <col width="250" height="1000">
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
			<div class="admin_center" style="">
	
				<!--리스트시작-->
				<div class="admin_con">
					<div class="sub_title_bar"><span class="title"><%=userEntity.getBreadCrumb(category)%></span></div>
				
					<!--게시판 시작-->
					
					<!--검색시작-->
					<fieldset class="search clfix">
						<legend>검색</legend>
						<b>Total : <%=paymentListEntity.getTotalListCount() %>건  </b> &nbsp; &nbsp; &nbsp; &nbsp;
						<select name="banner_type_code">
							<option value="">배너타입선택</option>
							<option value="N">대표이미지만</option>
							<option value="B">메인,센터하단</option>
							<option value="M">메인</option>
							<option value="C">센터하단</option>
						</select>
						<select name="status_code">
							<option value="">상태선택</option>
							<option value="R">결제대기</option>
							<option value="Y">결제완료</option>
							<option value="N">미납</option>
							<option value="E">이벤트결제</option>
						</select>
						<select name="searchKey">
							<option value="facility_name" <%="facility_name".equals(searchKey)?"selected":""%>>업소명</option>
							<option value="user_nic" <%="user_nic".equals(searchKey)?"selected":""%>>유저 닉네임</option>
							<option value="user_id" <%="user_id".equals(searchKey)?"selected":""%>>유저 ID</option>
						</select>
						
						<input type="text" class="s_id" name="searchValue" title="검색어를입력하세요" style="width:200;" value="<%=searchValue%>" onkeydown="javascript:if(event.keyCode==13)listBoard('<%= category %>', '<%=paymentListEntity.getPageNum()%>');"/>
						<input type="image" src="./images/board/btn_search2.gif" alt="SEARCH"  onclick="javascript:listBoard('<%= category %>', '<%=paymentListEntity.getPageNum()%>');"/>
					</fieldset>
	 				
					<!--게시판 시작-->
					<table class="tb2">
						<colgroup>
							<col width="60">
							<col width="120" />
							<col width="./" />
							<col width="70" />
							<col width="70" />
							<col width="70" />
							<col width="70" />
							<col width="100" />
							<col width="100" />
						</colgroup>
						<thead>
							<tr>
							    <th>번호</th>
								<th>결제자닉</th>
								<th>업소명</th>
								<th>지역</th>
								<th>업종</th>
								<th>결제여부</th>
								<th>배너타입</th>
								<th>결제시작일</th>
								<th>결제종료일</th>
							</tr>
						</thead>
						
						<tbody>
							<!--글 목록 시작-->
							<%
								if ( paymentList.size() == 0 ) {
									out.println("<tr><td colspan='6'><b>결제정보가 없습니다</b></td></tr>");
								}
							%>
							<%	
								long startSequence = paymentListEntity.getStartSequenceNumber();
								
								for ( int ii = 0 ; ii < paymentList.size() ; ii ++ ) {
									PaymentMasterEntity paymentEntity = paymentList.get(ii);
							%>
			 					<tr style="cursor: hand;" onclick="javascript:readBoard('<%=paymentEntity.getSeq_id()%>', '<%=category%>', '<%=paymentListEntity.getPageNum()%>');">
								<td><%=startSequence--%></td>
								<td><%=paymentEntity.getUser_nic()%></td>
								<td><%=paymentEntity.getFacility_name()%></td>
								<td><%=paymentEntity.getFacility_region()%></td>
								<td><%=facilityCategoryMap.get(paymentEntity.getFacility_category())%></td>
								<td><%=paymentMap.get(paymentEntity.getPayment_status())%></td>
								<td><%=bannerTypeMap.get(paymentEntity.getBanner_type_code())%></td>
								<td><%=paymentEntity.getStart_datetime().substring(0, 10)%></td>
								<td><%=paymentEntity.getEnd_datetime().substring(0, 10)%></td>
							</tr>	
							<%
								}
							%>			   
							<!--글 목록 끝 -->
						</tbody>
					</table>
	
	
					<!-- 글쓰기 버튼-->
					<div class="paging3">
						<a href="javascript:location.href='jspView.do?jsp=system/userPaymentWrite&category=${category}&actionType=WRITE';">
							<img class='write' src="./images/board/bt_write01.gif">
						</a>
					</div>
					
					<!--페이지링크-->
					<div class="paging">
						<%=paymentListEntity.getPagination("listBoard", category, paymentListEntity.getPageNum())%>
					</div>
				
				</div>
				<!--게시판 끗-->
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
 
 

  