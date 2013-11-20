package com.autowrite.scheduling;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.EofSensorInputStream;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.SiteEntity;

public class ReserveJob extends QuartzJobBean{
	//실행될 클래스는 꼭 QuartzJobBean을 상속받아야 되며 
	//executeInternal method를 override 하면 자동으로 이 메소드가 실행
	
	/**
	 * 생성자
	 */
	public ReserveJob(){
		httpclient = new DefaultHttpClient();

		httpclient.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				long keepAlive = super.getKeepAliveDuration(response, context);
				if (keepAlive == -1) {
					keepAlive = 5000;
				}
				return keepAlive;
			}
		});

		httpclient.removeRequestInterceptorByClass(RequestUserAgent.class);
		httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
			public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
				request.setHeader(HTTP.USER_AGENT, "My-own-client");
			}
		});
	}
	
	
	protected DefaultHttpClient httpclient;
	protected List<Cookie> cookies;
	
	protected static String domain = "http://localhost:8080";
	protected static String applicationPath = "/auto-write/";
	protected static String loginUri = "dologin.do";
	protected static String executeUri = "executeReservedAutowrite.do";
	
	protected void executeInternal(JobExecutionContext ex) throws JobExecutionException {
		// HTTP로 해당 서비스 호출.
		login();
		
		httpServiceCall();
	}
	
	public boolean login()  {
    	
    	try {
			String loginUrl = domain + applicationPath + loginUri;
			HttpPost httpost = new HttpPost(loginUrl);
	
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			
			JSONObject json = new JSONObject();
			json.put("u_id", "kshrabbit");
			json.put("password", "!lim0301");
			
			nvps.add(new BasicNameValuePair("p", json.toString()));
	
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			
			String responseBody = parseResponse(entity);
			
			if ( responseBody.contains("권한이 없습니다") ) {
				throw new Exception("권한이 없습니다");
			} else {
				System.out.println(responseBody);
			}
			
			System.out.println("Login form get: " + response.getStatusLine());
			EntityUtils.consume(entity);
	
			System.out.println("Post logon cookies:");
			cookies = httpclient.getCookieStore().getCookies();
			if (cookies.isEmpty()) {
				System.out.println("None");
			} else {
				for (int i = 0; i < cookies.size(); i++) {
					System.out.println("- " + cookies.get(i).toString());
				}
			}
    	} catch (Exception e){
    		e.printStackTrace();
    		return false;
    	}
		
		return true;
	}
	
	/**
	 * @MethodName : httpServiceCall
	 * @작성일      : 2013. 11. 19.
	 * @변경이력    :
	 * @Method 설명 : 
	 * @return
	 */
	protected int httpServiceCall() {
		int responseCode = 0;
		StatusLine statusLine = null;
		
		try {
			String executeUrl = domain + applicationPath + executeUri;
			
			System.out.println("executeUrl:" + executeUrl);
			
			HttpPost httpost = new HttpPost(executeUrl);
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();			
			
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			
			statusLine = response.getStatusLine();
			responseCode = statusLine.getStatusCode();
			System.out.println("responseCode:" + responseCode);
			System.out.println("response:" + parseResponse(entity));
			
			EntityUtils.consume(entity);
			
    	} catch (Exception e){
    		e.printStackTrace();
    	}
        
        return responseCode;
    }
	
	 public String parseResponse(HttpEntity entity) throws Exception {
			EofSensorInputStream content = (EofSensorInputStream) entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String curr = null;
			
			StringBuffer sb = new StringBuffer();
			
			try {
				while((curr = reader.readLine()) != null ){
					sb.append(curr + "\n");
				}
			} catch ( Exception e ) {
				throw e;
			} finally {
				content.close();
			}
			
			return sb.toString();
		}
}