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

public class GaggaoDduck extends AutowriterCommon {
	/**
	 * 생성자
	 */
	public GaggaoDduck(){
		super();
	}
	
    public void executeHttpConnection(AutowriteEntity autowriteInfo) throws Exception {
		try {
			setCookie(autowriteInfo);
			
			if ( login(autowriteInfo) ) {
//				writeBoard(autowriteInfo);
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
            nvps.add(new BasicNameValuePair("mb_id", siteInfo.getSite_id()));
	        nvps.add(new BasicNameValuePair("mb_password", siteInfo.getSite_passwd()));
//	        nvps.add(new BasicNameValuePair("user_id", "kshrabbit"));
//	        nvps.add(new BasicNameValuePair("password", "!lim0301"));
	        
            httpost.setEntity(new UrlEncodedFormEntity(nvps, autowriteInfo.getSiteEntity().getSite_encoding()));

			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
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
		httpost.setEntity(new UrlEncodedFormEntity(nvps2, autowriteInfo.getSiteEntity().getSite_encoding()));
        httpost.setHeader("Content-Type", "application/x-www-form-urlencoded;");
		
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();

		printResponseEuckr(entity);
		
		System.out.println("Post logon cookies:");
	}
    
    
	public List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		// 제목
//		String subjectStr = new String("인천부평스타 오픈 준비중입니다.");
		String subjectStr = autowriteInfo.getTitle();
		nvps.add(new BasicNameValuePair("bd_subject", subjectStr));
		
		// 내용
//		String contentStr = new String(" 9월 10일에 찾아뵙도록 하겠습니다.");
		String contentStr = autowriteInfo.getContent();
		nvps.add(new BasicNameValuePair("bd_content", contentStr));
		
		// 분류
		// qa4 : 유흥/빡촌/여관바리/기타질문 - 일반/(性)고민상담 - 글쓰기 
		// 1465427 : 동영상
		nvps.add(new BasicNameValuePair("bbs_code", "qa4"));
		
		// hidden input
		nvps.add(new BasicNameValuePair("mode", "write"));
		nvps.add(new BasicNameValuePair("act", "ok"));
		nvps.add(new BasicNameValuePair("bd_num", ""));
		nvps.add(new BasicNameValuePair("old_pass", ""));
		nvps.add(new BasicNameValuePair("bd_email", ""));
		
		// 익명글쓰기
		nvps.add(new BasicNameValuePair("ikmyeong", "1"));
		
		// 내공선택
		nvps.add(new BasicNameValuePair("bd_ext3", ""));
		
		// html 모드
		// 0 : 사용안함
		// 2: HTML
		nvps.add(new BasicNameValuePair("bd_html", "0"));
		
		
		return nvps;
	}

}
