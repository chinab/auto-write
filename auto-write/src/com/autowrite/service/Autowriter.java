package com.autowrite.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.apache.http.client.ClientProtocolException;
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

import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.SiteEntity;

public class Autowriter {
	private DefaultHttpClient httpclient;
	
	private String domainUrl;
	private List<Cookie> cookies;
	
	/**
	 * 생성자
	 */
	public Autowriter(){
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
	
    public void executeHttpConnection(AutowriteEntity autowriteInfo) throws Exception {
		try {
			setCookie(autowriteInfo);
			
			if ( loginJson(autowriteInfo) ) {
				writeBoard(autowriteInfo);
			} else {
				// login 100회 반복.
//				for ( int ii = 0 ; ii < 100 ; ii ++ ) {
//					if ( loginJson(autowriteInfo) ){
//						writeBoard(autowriteInfo);
//						break;
//					}
//				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

    private void setCookie(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, Exception {
		domainUrl = autowriteInfo.getSiteEntity().getDomain();
		
		if ( !domainUrl.startsWith("http://") ){
			domainUrl = "http://" + domainUrl;
		}
		
		HttpGet httpget = new HttpGet(domainUrl);

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		System.out.println("Login form get: " + response.getStatusLine());
		EntityUtils.consume(entity);

		System.out.println("Initial set of cookies:");
		cookies = httpclient.getCookieStore().getCookies();
	}

	
    private boolean loginJson(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, UnsupportedEncodingException {
    	
    	try {
			SiteEntity siteInfo = autowriteInfo.getSiteEntity();
			String loginUrl = siteInfo.getLogin_url();
			HttpPost httpost = new HttpPost(loginUrl);
	
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("u_id", "hwaru"));
			nvps.add(new BasicNameValuePair("password", "!lim0301"));
			
			
			JSONObject json = new JSONObject();
			json.put("u_id", "edge0117");
			json.put("password", "ai0105");
//			json.put("u_id", siteInfo.getSite_id());
//			json.put("password", siteInfo.getSite_passwd());
			
			nvps.add(new BasicNameValuePair("p", json.toString()));
	
			httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			
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


	private void writeBoard(AutowriteEntity autowriteInfo) throws Exception {
		String writeUrl = autowriteInfo.getSiteEntity().getWrite_url(); 
				
		HttpPost httpost = new HttpPost(writeUrl);
		List<NameValuePair> nvps2 = setNvpsParams(autowriteInfo);
		httpost.setEntity(new UrlEncodedFormEntity(nvps2, Consts.UTF_8));
		
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();

		String responseBody = parseResponse(entity);
		
		System.out.println(responseBody);
		
		if ( responseBody.contains("권한이 없습니다") ) {
			throw new Exception("권한이 없습니다");
		} else {
			System.out.println(responseBody);
		}

		System.out.println("Post logon cookies:");
	}

	private static List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		String content = autowriteInfo.getContent();
		
		nvps.add(new BasicNameValuePair("title", autowriteInfo.getTitle()));
		nvps.add(new BasicNameValuePair("content", content));
		nvps.add(new BasicNameValuePair("category", "030600"));
		nvps.add(new BasicNameValuePair("region", "인부천"));
		
		return nvps;
	}


	private String parseResponse(HttpEntity entity) throws Exception {
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
