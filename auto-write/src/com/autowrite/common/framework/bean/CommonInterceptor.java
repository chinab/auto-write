package com.autowrite.common.framework.bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.autowrite.common.config.Constant;
import com.autowrite.common.framework.entity.UserEntity;

public class CommonInterceptor implements HandlerInterceptor {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	AdminService adminService;
	
	private List passInterceptor;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
//		System.out.println("Interceptor: preHandle");
//		System.out.println("Remote Addr :" + request.getRemoteAddr());
//		System.out.println("Remote Host :" + request.getRemoteHost());
//		System.out.println("Remote Port :" + request.getRemotePort());
//		System.out.println("Remote User :" + request.getRemoteUser());
		
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		boolean userInfoCheck = true;
		String[] requestedURIs = request.getRequestURI().split("\\/");
		String requestedURI = requestedURIs[2];
		
//		System.out.println("requestedURI:"+requestedURI);
//		for ( int ii = 0 ; ii < requestedURIs.length ; ii ++ ){
//			System.out.println("requestedURI[" + ii +"]:" + requestedURIs[ii]);
//		}
		
//		System.out.println("requestedURI:"+requestedURI);
		
		for (Object item : passInterceptor) {
			String reservedURI = item.toString();
			if (reservedURI.indexOf('?') != -1) { // URI + Param 
				String[] uri = reservedURI.split("\\?");
				String[] param = uri[1].split("\\=");
				if (requestedURI.equals(uri[0]) && (param.length == 2)
						&& request.getParameter(param[0]).equals(param[1])) {
					userInfoCheck = false;
					break;
				}
			} else {
				if (requestedURI.equals(reservedURI)) { // URI 
					userInfoCheck = false;
					break;
				}
			}
		}

		HttpSession session = request.getSession(true);

		if (userInfoCheck) {
			UserEntity userInfo = (UserEntity) session.getAttribute(UserEntity.UserSessionKey);
			
			if (userInfo == null) {
				if (request.getParameter("jsp") != null) {
					sendError(response, "로그인이 만료되었습니다. 다시 로그인 하세요.", true);
//					response.getWriter().write("<html><script>window.top.location='index.jsp'</script></html>");
				} else {
					sendError(response, "로그인이 만료되었습니다. 다시 로그인 하세요.", true);
				}
				return false;
			}

			String userId = (String) userInfo.getId();
			if (userId == null) {
				sendError(response, "로그인이 만료되었습니다. 다시 로그인 하세요.", true);
				return false;
			}
			
			if ( !checkAuth(userInfo, request, requestedURI) ){
				sendError(response, "권한이 없습니다.");
				return false;
			}
			
			if ( !checkBoardLimit(userInfo, request) ){
				sendError(response, "하루 " + Constant.BOARD_WRITE_LIMIT + "개까지 업로드 가능합니다.");
				return false;
			}
		}
		
		return true;
	}

	private boolean checkBoardLimit(UserEntity userInfo, HttpServletRequest request) {
		String category = request.getParameter("category");
		String actionType = request.getParameter("actionType");
		
		// 은꼴 게시판 5개 제한.
		if ( "050300".equals(category) && "WRITE".equals(actionType) && "P".equals(userInfo.getType_code()) ) {
			int todayBoardCount = adminService.getTodayBoardCount(category, userInfo.getSeq_id());
			if ( todayBoardCount >= Constant.BOARD_WRITE_LIMIT ) {
				return false;
			}
		}
		
		return true;
	}

	private boolean checkAuth(UserEntity userInfo, HttpServletRequest request, String requestedURI) {
		String category = null;
		if ( request.getParameter("category") != null ) {
			category = request.getParameter("category");
		} else {
			return true;
		}
		
		// memo...
		if ( category.length() == 2 ){
			return true;
		}
		
		// menu id auth
		Map menuMap = userInfo.getMenuMap();
		if ( menuMap.get(category) == null ) {
			return false;
		}
		
		// write, update, delete auth
		String actionType = null;
		if ( "jspView.do".equals(requestedURI) && request.getParameter("actionType") == null){
			actionType = "READ";
		} else if ( request.getParameter("actionType")!= null ){
			actionType = request.getParameter("actionType");
		} else if ( requestedURI.toUpperCase().contains("WRITE") ){
			actionType = "WRITE";
		} else if ( requestedURI.toUpperCase().contains("MODIFY") ){
			actionType = "UPDATE";
		} else if ( requestedURI.toUpperCase().contains("DELETE") ){
			actionType = "DELETE";
		} else {
			actionType = "READ";
		}
		
		String authType = "R";
		if ( "WRITE".equals(actionType) ) {
			authType = "W";
		} else if ( "UPDATE".equals(actionType) ) {
			authType = "U";
		} else if ( "DELETE".equals(actionType) ) {
			authType = "D";
		}
		
//		System.out.println("AuthCheck..");
//		System.out.println("requestedURI : " + requestedURI);
//		System.out.println("actionType : " + actionType);
		
		Map param = new HashMap();
		param.put("USER_SEQ_ID", userInfo.getSeq_id());
		param.put("TYPE_CODE", userInfo.getType_code());
		param.put("MENU_ID", category);
		param.put("AUTH_TYPE", authType);
		
		int authCount = adminService.getMenuAuthCountByUserType(param);
		
		if ( authCount == 0 ){
			return false;
		}
		
		return true;
	}
	
	private void sendError(HttpServletResponse res, String errorText, Boolean isLogout) {
		if ( isLogout ) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Code", "E");
			map.put("Text", errorText);

//			JSONObject jsonObject = JSONObject.fromObject(map);
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
			sb.append("<html>\n");
			sb.append("<head>\n");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n");
			sb.append("<script>\n");
			sb.append("	alert('");
			sb.append(errorText);
			sb.append("');\n");
			sb.append("	location.href = './' ;\n");
			sb.append("</script>");
			sb.append("</head>\n");
			sb.append("</html>\n");
			
			try {
//				res.getWriter().write(jsonObject.toString());
				res.setContentType("text/html");
				res.getWriter().write(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sendError(res, errorText);
		}
	}
	private void sendError(HttpServletResponse res, String errorText) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Code", "E");
		map.put("Text", errorText);

//		JSONObject jsonObject = JSONObject.fromObject(map);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n");
		sb.append("<script>\n");
		sb.append("	alert('");
		sb.append(errorText);
		sb.append("');\n");
		sb.append("	location.href = './mainView.do?jsp=main/main' ;\n");
		sb.append("</script>");
		sb.append("</head>\n");
		sb.append("</html>\n");
		
		try {
//			res.getWriter().write(jsonObject.toString());
			res.setContentType("text/html");
			res.getWriter().write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView jsp) throws Exception {
//		System.out.println("Interceptor: postHandle");
		
		try {
			adminService.writeUserAction(request);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
//		System.out.println("Interceptor: afterCompletion");
	}

	public List getPassInterceptor() {
		return passInterceptor;
	}

	public void setPassInterceptor(List passInterceptor) {
		this.passInterceptor = passInterceptor;
	}

}
