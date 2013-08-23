<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.UserListEntity"%>
<%@ page import="com.autowrite.common.framework.entity.ConditionEntity"%>
<%@ page import="com.autowrite.common.framework.entity.MenuDto"%>
<%
	UserEntity userEntity = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
	List menus = userEntity.getMenuList();
	MenuDto menu = null;
	Map<String, String> menuMap = new HashMap<String, String>();
	
	UserListEntity userListEntity = (UserListEntity) request.getAttribute("UserList");
	List<UserEntity> userList = userListEntity.getUserList();
	
	String searchKey = userListEntity.writeSearchKey();
	String searchValue = userListEntity.writeSearchValue();
%>
<html>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />

<script>
	function userInfoView(seqId, pageNum){
		var frm = document.listForm;
		
		frm.action = "userInfoView.do";
		frm.jsp.value = "system/userInfoView";
		frm.seqId.value = seqId;
		frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
	function userPointView(seqId, pageNum){
		var frm = document.listForm;
		
		frm.action = "userPointView.do";
		frm.jsp.value = "system/userPointView";
		frm.seqId.value = seqId;
		//frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
	function userInfoList(pageNum){
		var frm = document.listForm;
		
		frm.action = "userInfoList.do";
		frm.jsp.value = "system/userInfoList";
		frm.actionType.value = "READ";
		frm.pageNum.value = pageNum;
		
		frm.submit();
	}
	
</script>

<form name="listForm" method="post">
	<input type="hidden" name="jsp" value=""/>
	<input type="hidden" name="seqId" value=""/>
	<input type="hidden" name="actionType" value=""/>
	<input type="hidden" name="pageNum" value=""/>
	
<!--메인컨텐츠 전체-->
<!--시작지점-->
<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftSystem.jsp" flush="false" />

		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

										<!--검색시작-->
						<fieldset class="search clfix">
							<legend>검색</legend>
							<b>Total : <%=userListEntity.getTotalListCount() %>건  </b> 
							&nbsp; &nbsp; &nbsp; &nbsp;
							<select name="type_code">
								<option value="">회원타입</option>
								<option value="P">일반회원</option>
								<option value="B">업소회원</option>
								<option value="W">언니회원</option>
								<option value="A">운영자</option>
								<option value="M">관리자</option>
							</select>
							
							<select name="status_code">
								<option value="">상태선택</option>
								<option value="W">승인대기</option>
								<option value="A">활동중</option>
								<option value="D">강등</option>
								<option value="B">블랙</option>
							</select>
							
							<select name="service_code">
								<option value="">업종선택</option>
								<option value="01">오피</option>
								<option value="02">안마</option>
								<option value="03">휴게,대떡</option>
								<option value="04">건마</option>
								<option value="05">립카페</option>
								<option value="06">핸플</option>
								<option value="07">키스방</option>
								<option value="08">주점</option>
								<option value="09">기타</option>
							</select>
							
							<select name="searchKey">
								<option value="nic" <%="nic".equals(searchKey)?"selected":""%>>닉네임</option>
								<option value="id" <%="id".equals(searchKey)?"selected":""%>>ID</option>
								<option value="name" <%="name".equals(searchKey)?"selected":""%>>이름</option>
							</select>
							
							<input type="text" class="s_id" name="searchValue" title="검색어를입력하세요" style="width:200;" value="<%=searchValue%>" onkeydown="javascript:if(event.keyCode==13)userInfoList('<%=userListEntity.getPageNum()%>');"/>
							<input type="image" src="./images/board/btn_search2.gif" alt="SEARCH"  onclick="javascript:userInfoList('<%=userListEntity.getPageNum()%>');"/>
						</fieldset>
						<!-- 검색 끝 -->
						
						
						<div>
							<table class="tb2">
								<colgroup>
									<col width="30">
									<col width="100" />
									<col width="100" />
									<col width="100" />
									<col width="70" />
									<col width="./" />
									<col width="100" />
									<col width="70" />
									<col width="70" />
								</colgroup>
								<thead>
									<tr>
									    <th>순번</th>
										<th>ID</th>
										<th>이름</th>
										<th>닉네임</th>
										<th>포인트</th>
										<th>등록일시</th>
										<th>유저타입</th>
										<th>상태코드</th>
										<th>서비스코드</th>
									</tr>
								</thead>
								
								<tbody>
									<!-- 리스트 시작-->
									<%
										//long startSequence = userListEntity.getStartSequenceNumber();
									
										for ( int ii = 0 ; ii < userList.size() ; ii ++ ) {
											UserEntity userInfoEntity = userList.get(ii);
									%>
					 				<tr>
					 					<td>
					 						<a href="javascript:userInfoView('<%=userInfoEntity.getSeq_id()%>', '<%=userListEntity.getPageNum()%>');">
					 							<%= userInfoEntity.getSeq_id() %>
					 						</a>
					 					</td>
										<td>
					 						<a href="javascript:userInfoView('<%=userInfoEntity.getSeq_id()%>', '<%=userListEntity.getPageNum()%>');">
					 							<%= userInfoEntity.getId() %>
					 						</a>
					 					</td>
										<td>
					 						<a href="javascript:userInfoView('<%=userInfoEntity.getSeq_id()%>', '<%=userListEntity.getPageNum()%>');">
					 							<%= userInfoEntity.getName() %>
					 						</a>
					 					</td>
										<td>
					 						<a href="javascript:userInfoView('<%=userInfoEntity.getSeq_id()%>', '<%=userListEntity.getPageNum()%>');">
					 							<%= userInfoEntity.getNic() %>
					 						</a>
					 					</td>
										<td>
					 						<a href="javascript:userPointView('<%=userInfoEntity.getSeq_id()%>', '<%=userListEntity.getPageNum()%>');">
					 							<%= userInfoEntity.getPoint() %>
					 						</a>
					 					</td>
										<td><%= userInfoEntity.getReg_datetime().substring(0, 19) %></td>
										<td><%= userInfoEntity.getTypeName() %></td>
										<td><%= userInfoEntity.getStatusName() %></td>
										<td><%= userInfoEntity.getServiceName() %></td>
									</tr>	
									<%
										}
									%>			   
									<!-- 리스트 끝 -->
								</tbody>
							</table>
							
							<!-- 글쓰기 버튼-->
							<div class="paging3">
								<a href="javascript:alert('준비중');">
									<img class='write' src="./images/board/bt_write01.gif">
								</a>
							</div>
							
							<!--페이지링크-->
							<div class="paging">
								<%=userListEntity.getPagination("userInfoList", "", userListEntity.getPageNum())%>
							</div>
						</div>
						
					</div>
					<!-- 유저 리스트 끝 -->



			</div>
		</td>

	</tr>
</table>


<!--푸터-->
<jsp:include page="../include/footer.jsp" flush="false" />
<!--푸터 끝-->


</body>
</html>
