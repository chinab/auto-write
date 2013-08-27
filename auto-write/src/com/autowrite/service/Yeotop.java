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

public class Yeotop implements AutowriterInterface{
	private DefaultHttpClient httpclient;
	
	private String domainUrl;
	private List<Cookie> cookies;
	
	/**
	 * 생성자
	 */
	public Yeotop(){
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
			
			if ( login(autowriteInfo) ) {
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

    public void setCookie(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, Exception {
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

	
    public boolean login(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, UnsupportedEncodingException {
    	
    	try {
			SiteEntity siteInfo = autowriteInfo.getSiteEntity();
			String loginUrl = siteInfo.getLogin_url();
			HttpPost httpost = new HttpPost(loginUrl);
	
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            // 회원타입
            // 1: 일반회원, 2 : 업소회원
            nvps.add(new BasicNameValuePair("mb_type", "2"));
            nvps.add(new BasicNameValuePair("mb_id", siteInfo.getSite_id()));
	        nvps.add(new BasicNameValuePair("mb_password", siteInfo.getSite_passwd()));
//	        nvps.add(new BasicNameValuePair("mb_id", "qnvudtmxk"));
//	        nvps.add(new BasicNameValuePair("mb_password", "!qnvudtmxk"));
	        
	        httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			
			String responseBody = parseResponse(entity);
			
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


    public void writeBoard(AutowriteEntity autowriteInfo) throws Exception {
		String writeUrl = autowriteInfo.getSiteEntity().getWrite_url(); 
				
		HttpPost httpost = new HttpPost(writeUrl);
		List<NameValuePair> nvps2 = setNvpsParams(autowriteInfo);
		httpost.setEntity(new UrlEncodedFormEntity(nvps2, "EUC-KR"));
        httpost.setHeader("Content-Type", "application/x-www-form-urlencoded;");
		
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();

		String responseBody = parseResponse(entity);
		
		System.out.println(responseBody);
		
		System.out.println("Post logon cookies:");
	}
    
    
	public List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		// 제목
//		String subjectStr = new String("인천부평스타 오픈 준비중입니다.");
		String subjectStr = autowriteInfo.getTitle();
		nvps.add(new BasicNameValuePair("wr_subject", subjectStr));
		
		// 내용
//		String contentStr = new String(" 9월 10일에 찾아뵙도록 하겠습니다.");
		String contentStr = autowriteInfo.getContent();
		nvps.add(new BasicNameValuePair("wr_content", contentStr));
		
		// 카테고리
		// board30 : 업소홍보1 - 오피, 기타. 
		// board55 : 창작, 유머
		nvps.add(new BasicNameValuePair("bo_table", "board55"));
		
		// 모름
		nvps.add(new BasicNameValuePair("wr_trackback", "4"));

		// 모름
		nvps.add(new BasicNameValuePair("html", "html1"));
		
		// 형식구분
		// A: 일반형식, B : 자유형식
		nvps.add(new BasicNameValuePair("wr_2", "B"));
		
		// 분류
		// 오피스텔(강남), 오피스텔(비강남), 기타
		nvps.add(new BasicNameValuePair("ca_name", "오피스텔(비강남)"));
		
		return nvps;
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

	@Override
	public void shutdownHttpConnection() throws Exception {
		httpclient.getConnectionManager().shutdown();
	}
}
