package com.autowrite.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BootServlet extends HttpServlet {
	
	private static final long serialVersionUID = 7338645307611316181L;

	public void init(ServletConfig config) {
		systemSet();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 서블릿 컨텍스트 객체 얻어오기

		ServletContext ctx = getServletContext();

		// 컨텍스트에서 serverip라는 이름의 객체얻어오기

		String serverIp =

		(String) ctx.getAttribute("serverIp"); // 이름을 적음 리턴값이 object

		// web.xml에 설정된 초기화 파라미터 얻어오기

		String url = ctx.getInitParameter("url"); // 컨피그영역

		// 서블릿 컨피그 객체 얻어오기

		ServletConfig config = getServletConfig();

		// web.xml에 설정된 초기화 파라미터 얻어오기

		String user = config.getInitParameter("user");

		// xml은 메모장이기때문에 객체는 읽어올수 없고 String 값을 읽어옴

		// 컨텍스트 영역에 저장된 공유자원 얻어오기

		response.setContentType("text/html; charset=euc-kr");

		PrintWriter pw = response.getWriter();

		pw.println("컨텍스트 영역에 저장된 객체값 : " + serverIp + "<br/>");

		pw.println("컨텍스트 영역에 저장된 객체값 : " + url + "<br/>");

		pw.println("컨피그 영역에 저장된 객체값 : " + user + "<br/>");

		pw.close();

	}

	private void systemSet() {
		ServletContext ctx = getServletContext();
		
		if ( ctx == null ){
			ctx = new ServletContext() {
				
				@Override
				public void setAttribute(String arg0, Object arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void removeAttribute(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void log(String arg0, Throwable arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void log(Exception arg0, String arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void log(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public Enumeration getServlets() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Enumeration getServletNames() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String getServletContextName() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Servlet getServlet(String arg0) throws ServletException {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String getServerInfo() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Set getResourcePaths(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public InputStream getResourceAsStream(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public URL getResource(String arg0) throws MalformedURLException {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public RequestDispatcher getRequestDispatcher(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String getRealPath(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public RequestDispatcher getNamedDispatcher(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public int getMinorVersion() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public String getMimeType(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public int getMajorVersion() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public Enumeration getInitParameterNames() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String getInitParameter(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String getContextPath() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public ServletContext getContext(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Enumeration getAttributeNames() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Object getAttribute(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
			};
		}
		
		// 컨텍스트에서 serverip라는 이름의 객체얻어오기

		String serverIp = (String) ctx.getAttribute("serverIp"); // 이름을 적음 리턴값이 object
		
		if ( serverIp == null ) {
			serverIp = "test";
			ctx.setAttribute("serverIp", serverIp);
		}
		
		System.out.println("serverIp:" + serverIp);
	}
}
