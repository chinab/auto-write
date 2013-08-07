package com.autowrite.common.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.jekyll.bizmodel.share.UserSessionBean;
import com.jekyll.common.config.Constant;
import com.jekyll.common.config.ReadConfig;

public class SessionUtil {
	  /**
	   *    * Return true if there's a UserView object stored in the session, false otherwise.
	   *    in action class, this class is executed.
	   *
	   */
	static String CHANNEL ="GLOBAL";
	
	public static boolean isLoggedIn( HttpServletRequest request, String key )
	{
		if ( request.getSession(false) == null || (request.getSession().getAttribute(key) == null)){
			return false;
		}else{
			return true;
		}
	}

	public static boolean isUsrLoggedIn( HttpServletRequest request )
	{
		Object app =null;

		if ( request.getSession(false) != null){
			app =request.getSession(false).getAttribute(Constant.APPLICATION_ID);

			if (app==null || request.getSession().getAttribute(app+Constant.USER_SESSION_KEY) == null)
				return false;
			else return true;
		}else{
			return false;
		}
	}

	public static boolean isAdmLoggedIn( HttpServletRequest request )
	{
		UserSessionBean user =null;
		if ( request.getSession(false) == null || (request.getSession().getAttribute(Constant.ADMUSER_SESSION_KEY) == null)){
			String sid =getCookieVal(request.getCookies(),"userkey");
			System.out.println("User Key:"+sid);
			if (sid==null) {
				sid=request.getParameter("uid");
				System.out.println("User Key from request:"+sid);
				if (sid==null) return false;
				
			}
			CrossLoginCheck clc = new CrossLoginCheck();
			user =(UserSessionBean)clc.getSessionUser(sid);
			if (user==null){
				String sxml =checkCrossSession(request,sid,"login");
				if (sxml !=null){
					user =new UserSessionBean();
					try{
						user.setValuesByXML(sxml);
					}catch(Exception ex){
						System.out.println("isAdmLoggedIn error:"+ex.getMessage());
						return false;
					}
					clc.PutHashTable(user.getUserID(),sid,user);
					return true;
				}
			}
			return true;
		}else{
			return true;
		}
	}
	public static UserSessionBean getAdmLoggedIn( HttpServletRequest request )
	{
		UserSessionBean user =null;
		if ( request.getSession(false) == null || (request.getSession().getAttribute(Constant.ADMUSER_SESSION_KEY) == null)){
			String sid =getCookieVal(request.getCookies(),"userkey");
			System.out.println("User Key:"+sid);
			if (sid==null) return null;
			CrossLoginCheck clc = new CrossLoginCheck();
			user =(UserSessionBean)clc.getSessionUser(sid);
			if (user==null){
				String sxml =checkCrossSession(request,sid,"login");
				if (sxml !=null){
					user =new UserSessionBean();
					try{
						user.setValuesByXML(sxml);
					}catch(Exception ex){
						System.out.println("isAdmLoggedIn error:"+ex.getMessage());
						return null;
					}
					clc.PutHashTable(user.getUserID(),sid,user);
					return user;
				}
			}
			return user;
		}else{
			user =(UserSessionBean)request.getSession().getAttribute(Constant.ADMUSER_SESSION_KEY);
			return user;
		}
	}
	public static void removeSession(HttpServletRequest request,String sessionKey )
	{
		UserSessionBean user =null;
		CrossLoginCheck clc = new CrossLoginCheck();
		if ( request.getSession(false) == null || (request.getSession().getAttribute(sessionKey) == null)){
			String sid =getCookieVal(request.getCookies(),"userkey");
		//	System.out.println("User Key:"+sid);
			if (sid==null) return ;
			
			user =(UserSessionBean)clc.getSessionUser(sid);
			if (user !=null){
				
				clc.RemoveUserId(user.getUserID());
			}
			checkCrossSession(request,sid,"logout");
		}else{
			user =(UserSessionBean)request.getSession().getAttribute(sessionKey);
			if (user !=null)
				clc.RemoveUserId(user.getUserID());
			request.getSession().removeAttribute(sessionKey);
			
		}
	}

	public static String getCookieVal(Cookie[] cookies, String skey) {
		// TODO Auto-generated method stub
		String sret =null;
		for (int i=0;i<cookies.length;i++){
			if (cookies[i].getName().equals(skey)){
				sret=cookies[i].getValue();
				break;
			}
		}
		return sret;
	}

	private static String checkCrossSession(HttpServletRequest request,String ssid,String cmd) {
		// TODO Auto-generated method stub
		String url =ReadConfig.get(CHANNEL,"SessionSharedServer");
		String check =ReadConfig.get(CHANNEL,"IsCrossSession");
		System.out.println("Sessing check :"+check+","+url);
		if (check ==null || !check.equals("T")) return null;
		
		String sret =null;
		try{
			if (url==null ||url.length()==0) return sret;
			System.out.println("check Check to "+url);
			URL surl =new URL(url+"?sessionid="+ssid+"&cmd="+cmd);
			HttpURLConnection httpConn = (HttpURLConnection)surl.openConnection();
			httpConn.setRequestMethod("GET");
			InputStream in = httpConn.getInputStream();
			BufferedInputStream bin =new BufferedInputStream(in);
			byte[] buf= new byte[1024];
			int iret =0;
			int count =0;
			
			int repeat =0;

			while(true)
			{
				iret =bin.read();
				if(iret <0) break;
				buf[count++] =(byte)iret;

				if(count ==buf.length)
				{
					sret=sret+new String(buf);
					count=0;
				}
			}

			if (count>0)
			{
				byte[] b =new byte[count];
				System.arraycopy(buf,0,b,0,count);
				sret =sret +new String(b);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return sret;
	}

	public static boolean isAdmEPLoggedIn( HttpServletRequest request )
	{
		if ( request.getSession(false) == null || (request.getSession().getAttribute(Constant.EP_ADMUSER_SESSION_KEY) == null)){
			return false;
		}else{
			return true;
		}
	}

	public static boolean isSysAdmLoggedIn( HttpServletRequest request )
	{
		if ( request.getSession(false) == null || (request.getSession().getAttribute(Constant.SYSADMUSER_SESSION_KEY) == null)){
			return false;
		}else{
			return true;
		}
	}

	public static boolean isSysAdmEPLoggedIn( HttpServletRequest request )
	{
		if ( request.getSession(false) == null || (request.getSession().getAttribute(Constant.EP_SYSADMUSER_SESSION_KEY) == null)){
			return false;
		}else{
			return true;
		}
	}

	public Map extractDataSet(HttpServletRequest request)
	{
		String name =null;
		String value =null;
		HashMap map =new HashMap();
		Enumeration em =request.getParameterNames();
		while(em.hasMoreElements()){
			name =(String)em.nextElement();
			value =request.getParameter(name);
			System.out.println("name :"+name +"  value:"+value);
			if(value !=null) map.put(name.toUpperCase(),value);
		}
		return (Map)map;
	}
}

