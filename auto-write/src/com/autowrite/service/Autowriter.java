package com.autowrite.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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

import com.autowrite.common.framework.entity.BoardEntity;

public class Autowriter {

    public static void main(String[] args) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        
		httpclient.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				long keepAlive = super.getKeepAliveDuration(response, context);
				if (keepAlive == -1) {
					// Keep connections alive 5 seconds if a keep-alive value
					// has not be explicitly set by the server
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

        try {
        	String domainUrl = "http://www.hwaru1.net";
        	HttpGet httpget = new HttpGet(domainUrl);
        	
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            System.out.println("Login form get: " + response.getStatusLine());
            EntityUtils.consume(entity);

            System.out.println("Initial set of cookies:");
            List<Cookie> cookies = httpclient.getCookieStore().getCookies();
            if (cookies.isEmpty()) {
                System.out.println("None");
            } else {
                for (int i = 0; i < cookies.size(); i++) {
                    System.out.println("- " + cookies.get(i).toString());
                }
            }
            
            String loginUrl = "http://www.hwaru1.net/jekyll/dologin.do";
            HttpPost httpost = new HttpPost(loginUrl);
            
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	        nvps.add(new BasicNameValuePair("u_id", "kshrabbit"));
	        nvps.add(new BasicNameValuePair("password", "!lim0301"));
	    	
	        String jsonStr = "{'resultCode':'success', 'errorMsg': '','header':{'userNm':'ddakker'},'resultList':[{'dlvrAddr1': '서울~'},{'dlvrAddr1': '충남~'}]}";
	        JSONObject json = new JSONObject();
	        json.put("u_id", "hwaru");
	        json.put("password", "!lim0301");
	        
	    	nvps.add(new BasicNameValuePair("p", json.toString()));
            
	        
            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

            response = httpclient.execute(httpost);
            
            entity = response.getEntity();
            
            printResponseBody(entity);
            
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
            
            String writeUrl = "http://www.hwaru1.net/jekyll/writeBoard.do";
            
            httpost = new HttpPost(writeUrl);
            List <NameValuePair> nvps2 = setNvpsParams();
            httpost.setEntity(new UrlEncodedFormEntity(nvps2, Consts.UTF_8));
            
            response = httpclient.execute(httpost);
            entity = response.getEntity();
            
            printResponseBody(entity);
        	
//        	for ( int ii = 0 ; ii < conLen ; ii ++ ){
//        		int data = content.read();
//        		System.out.print((char)data);
//        	}
        	
            System.out.println("Post logon cookies:");
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }
    
	public void executeHttpConnection(BoardEntity siteInfo) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();

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
			public void process(HttpRequest request, HttpContext context)
					throws HttpException, IOException {
				request.setHeader(HTTP.USER_AGENT, "My-own-client");
			}
		});

		try {
			String domainUrl = "http://www.hwaru1.net";
			HttpGet httpget = new HttpGet(domainUrl);

			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			System.out.println("Login form get: " + response.getStatusLine());
			EntityUtils.consume(entity);

			System.out.println("Initial set of cookies:");
			List<Cookie> cookies = httpclient.getCookieStore().getCookies();
			if (cookies.isEmpty()) {
				System.out.println("None");
			} else {
				for (int i = 0; i < cookies.size(); i++) {
					System.out.println("- " + cookies.get(i).toString());
				}
			}

			String loginUrl = "http://www.hwaru1.net/jekyll/dologin.do";
			HttpPost httpost = new HttpPost(loginUrl);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("u_id", "kshrabbit"));
			nvps.add(new BasicNameValuePair("password", "!lim0301"));

			JSONObject json = new JSONObject();
			json.put("u_id", "hwaru");
			json.put("password", "!lim0301");

			nvps.add(new BasicNameValuePair("p", json.toString()));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

			response = httpclient.execute(httpost);

			entity = response.getEntity();

			printResponseBody(entity);

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

			String writeUrl = "http://www.hwaru1.net/jekyll/writeBoard.do";

			httpost = new HttpPost(writeUrl);
			List<NameValuePair> nvps2 = setNvpsParams();
			httpost.setEntity(new UrlEncodedFormEntity(nvps2, Consts.UTF_8));

			response = httpclient.execute(httpost);
			entity = response.getEntity();

			printResponseBody(entity);

			System.out.println("Post logon cookies:");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}


	private static List<NameValuePair> setNvpsParams() {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("title", "한글테스트"));
		nvps.add(new BasicNameValuePair("content", "한글테스트"));
		nvps.add(new BasicNameValuePair("category", "020100"));
		nvps.add(new BasicNameValuePair("actionType", "WRITE"));
		
		return nvps;
	}


	private static void printResponseBody(HttpEntity entity) throws IOException, UnsupportedEncodingException {
		EofSensorInputStream content = (EofSensorInputStream) entity.getContent();
		byte[] buffer = new byte[1024] ;
		int readCnt = 0;
		long conLen = entity.getContentLength();
		
		System.out.println("Response content length: " + conLen);
		System.out.println("Response content: ");
		
		String responseHtml = null;
		while((readCnt = content.read(buffer)) != -1 ){
			responseHtml = new String(buffer, 0, readCnt, "UTF-8");
//        		responseHtml = new String(buffer, 0, readCnt, "EUC-KR");
			System.out.print(responseHtml);
		}
	}
}
