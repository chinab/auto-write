<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.net.URLEncoder"%>
<%@ page import="java.util.Map, java.util.Iterator, java.util.HashMap"%>
<%@ page import="com.autowrite.common.framework.entity.UserEntity"%>
<%@ page import="com.autowrite.common.framework.entity.AutowriteEntity"%>
<%@ page import="com.autowrite.common.framework.entity.AutowriteListEntity"%>
<%@ page import="com.autowrite.common.framework.entity.ConditionEntity"%>

<%
	AutowriteListEntity autowriteSiteListEntity = (AutowriteListEntity) request.getAttribute("AutowriteListEntity");
	
	List<AutowriteEntity> autowriteSiteList = autowriteSiteListEntity.getAutowriteList();
	
	String searchKey = autowriteSiteListEntity.writeSearchKey();
	String searchValue = autowriteSiteListEntity.writeSearchValue();
%>

<!-- 헤더 스크립트 -->
<jsp:include page="../include/header.jsp" flush="false" />
<!-- 헤더 스크립트 끝 -->

<!--탑메뉴-->
<jsp:include page="../include/topMenu.jsp" flush="false" />

<!--메인컨텐츠 전체-->
<!--시작지점-->
<table cellpadding="0" cellspacing="0" border="0" width="1000" align="center" style="margin-top: 30px;">
	<tr>
		<!--좌측메뉴-->
		<jsp:include page="../include/leftAutowrite.jsp" flush="false" />

		<!--우측컨-->
		<td width="800" valign="top">
			<div style="margin-left: 30px; width: 750px;">

				<div style="width: 100% line-height:120px; padding: 7px; border: solid 1px #eeeeee;">자동등록 > 자동등록목록 > 자동등록현황</div>
				<div style="width: 100%; margin-top: 30px;">
					<img src="images/title_dot.gif"/>
					<span style="font-weight: bold; padding-left: 3px; font-size: 17px; color: #219075; font-family: Malgun Gothic;">자동등록현황</span>
				</div>

				<div style="margin-top: 5px;">

					<!--게시판 시작-->
					<table class="list01">
						<colgroup>
							<col width="70" />
							<col width="100" />
							<col width="100" />
							<col width="100" />
							<col width="100" />
							<col width="/" />
						</colgroup>
						<tbody>

							<tr>
								<th>순번</th>
								<th>사이트명</th>
								<th>실행일시</th>
								<th>성공여부</th>
								<th>등록시도회수</th>
								<th>최종응답</th>
							</tr>
							
							<%
								for ( int ii = 0 ; ii < autowriteSiteList.size() ; ii ++ ) {
									AutowriteEntity autowriteEntity = autowriteSiteList.get(ii);
							%>
							<tr>
								<td><%=autowriteSiteList.size() - ii%></td>
								<td>
									<a href="autowriteLogList.do?jsp=autowrite/autowriteLogList&autowriteSiteSeqid=<%=autowriteEntity.getSeq_id()%>&autowriteMasterSeqid=<%=autowriteEntity.getAutowrite_master_seq_id()%>">
										<%=autowriteEntity.getSite_name()%>
									</a>
								</td>
								<td><%=autowriteEntity.getTry_datetime()%></td>
								<td><%=autowriteEntity.getSuccess_yn()%></td>
								<td><%=autowriteEntity.getTry_count()%></td>
								<td><%=autowriteEntity.getResponse_content()%></td>
							</tr>
							<%
								}
							%>
							

						</tbody>
					</table>



					<div style="width: 100%; margin-top: 15px; text-align: center;">
						<input class="in_btn" type="button" value="재실행" OnClick="popup_check();" class="input_gr">
						&nbsp;&nbsp;
						<input class="in_btnc" type="button" value="뒤로가기" OnClick="history.back();" class="input_gr">
					</div>
				</div>



			</div>
		</td>

	</tr>
</table>


<!--푸터-->
<jsp:include page="../include/footer.jsp" flush="false" />
<!--푸터 끝-->


</body>
</html>