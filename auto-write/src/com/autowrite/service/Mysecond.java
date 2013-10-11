package com.autowrite.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.SiteEntity;

public class Mysecond extends AutowriterCommon {
	/**
	 * 생성자
	 */
	public Mysecond(){
		super();
	}
	
    @Override
    public boolean login(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, UnsupportedEncodingException {
    	
    	try {
			SiteEntity siteInfo = autowriteInfo.getSiteEntity();
			String loginUrl = getFullUrl(siteInfo, siteInfo.getLogin_url());
			HttpPost httpost = new HttpPost(loginUrl);
	
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            // 회원타입
            // 1: 일반회원, 2 : 업소회원
            nvps.add(new BasicNameValuePair("mb_type", "2"));
            nvps.add(new BasicNameValuePair("mb_id", siteInfo.getSite_id()));
	        nvps.add(new BasicNameValuePair("mb_password", siteInfo.getSite_passwd()));
//	        nvps.add(new BasicNameValuePair("mb_id", "qnvudtmxk"));
//	        nvps.add(new BasicNameValuePair("mb_password", "!qnvudtmxk"));
	        
	        httpost.setEntity(new UrlEncodedFormEntity(nvps, autowriteInfo.getSiteEntity().getSite_encoding()));

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

    @Override
    public void writeBoard(AutowriteEntity autowriteInfo) throws Exception {
    	SiteEntity siteInfo = autowriteInfo.getSiteEntity();
    	String writeUrl = getFullUrl(siteInfo, siteInfo.getWrite_url()); 
				
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
    
    @Override
	public List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		// 제목
		String subjectStr = autowriteInfo.getTitle();
		URLEncoder.encode(subjectStr);
		nvps.add(new BasicNameValuePair("wr_subject", subjectStr));
		
		// 내용
		String contentStr = autowriteInfo.getContent();
		URLEncoder.encode(contentStr);
		nvps.add(new BasicNameValuePair("wr_content", contentStr));
		
		// 카테고리
		// 
		// S15 : TEST
		// S22 : 출근부
		nvps.add(new BasicNameValuePair("bo_table", "S22"));
		
		// 지역
		// 서울, 인천, 경기, 충청, 경상, 전라, 강원, 제주
		nvps.add(new BasicNameValuePair("ca_name", "인천"));
		nvps.add(new BasicNameValuePair("sca", "인천"));
		
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
}
