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

public class Sexbam extends AutowriterCommon {
	/**
	 * 생성자
	 */
	public Sexbam(){
		super();
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

    public boolean login(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, UnsupportedEncodingException {
    	
    	try {
			SiteEntity siteInfo = autowriteInfo.getSiteEntity();
			String loginUrl = siteInfo.getLogin_url();
			HttpPost httpost = new HttpPost(loginUrl);
	
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("user_id", siteInfo.getSite_id()));
	        nvps.add(new BasicNameValuePair("password", siteInfo.getSite_passwd()));
//	        nvps.add(new BasicNameValuePair("user_id", "kshrabbit"));
//	        nvps.add(new BasicNameValuePair("password", "!lim0301"));
	        
	        httpost.setEntity(new UrlEncodedFormEntity(nvps, autowriteInfo.getSiteEntity().getSite_encoding()));

			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			EntityUtils.consume(entity);
			
	        nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("vid", ""));
            nvps.add(new BasicNameValuePair("act", "member"));
            nvps.add(new BasicNameValuePair("act", "procMemberLogin"));
            nvps.add(new BasicNameValuePair("ruleset", "@login"));
            nvps.add(new BasicNameValuePair("error_return_url", "/index.php?mid=login"));
            nvps.add(new BasicNameValuePair("success_return_url", "/index.php?mid=login"));
            nvps.add(new BasicNameValuePair("user_id", "kshrabbit"));
	        nvps.add(new BasicNameValuePair("password", "!lim0301"));
	        
	        httpost.setEntity(new UrlEncodedFormEntity(nvps, autowriteInfo.getSiteEntity().getSite_encoding()));

			HttpResponse response2 = httpclient.execute(httpost);
			HttpEntity entity2 = response2.getEntity();
			
			String responseBody = parseResponse(entity2);
			
			System.out.println("Login form get: " + responseBody);
			EntityUtils.consume(entity2);
	
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
		httpost.setEntity(new UrlEncodedFormEntity(nvps2, autowriteInfo.getSiteEntity().getSite_encoding()));
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
		nvps.add(new BasicNameValuePair("title", subjectStr));
		
		// 내용
//		String contentStr = new String(" 9월 10일에 찾아뵙도록 하겠습니다.");
		String contentStr = autowriteInfo.getContent();
		nvps.add(new BasicNameValuePair("content", contentStr));
		
		// 분류
		// 1465403 : 짤방
		// 1465427 : 동영상
		nvps.add(new BasicNameValuePair("category_srl", "1465403"));
		
		nvps.add(new BasicNameValuePair("error_return_url", "/index.php?mid=so01&amp;act=dispBoardWrite"));
		nvps.add(new BasicNameValuePair("act", "procBoardInsertDocument"));
		nvps.add(new BasicNameValuePair("vid", ""));
		
		// so01 : 자유게시판
		// so03 : 놀이터
		nvps.add(new BasicNameValuePair("mid", "so03"));
		
		nvps.add(new BasicNameValuePair("module", "board"));
		nvps.add(new BasicNameValuePair("status", "PUBLIC"));
		nvps.add(new BasicNameValuePair("comment_status", "ALLOW"));
		nvps.add(new BasicNameValuePair("allow_trackback", "Y"));
		
		return nvps;
	}

}
