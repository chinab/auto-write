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

public class Gongsa extends AutowriterCommon {
	/**
	 * 생성자
	 */
	public Gongsa(){
		super();
	}
	
    @Override
    public boolean login(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, UnsupportedEncodingException {
    	
    	try {
			SiteEntity siteInfo = autowriteInfo.getSiteEntity();
			String loginUrl = getFullUrl(siteInfo, siteInfo.getLogin_url());
			HttpPost httpost = new HttpPost(loginUrl);
	
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("userID", siteInfo.getSite_id()));
	        nvps.add(new BasicNameValuePair("userPass", siteInfo.getSite_passwd()));
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

//		String responseBody = parseResponse(entity);
//		
//		System.out.println(responseBody);
		
		printResponseEuckr(entity);
		
		System.out.println("Post logon cookies:");
	}
    
    @Override
	public List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		// 제목
		String subjectStr = new String("test");
//		String subjectStr = new String("인천부평스타 오픈 준비중입니다.");
//		String subjectStr = autowriteInfo.getTitle();
//		URLEncoder.encode(subjectStr);
		nvps.add(new BasicNameValuePair("bd_title", subjectStr));
		
		// 내용
		String contentStr = new String("test");
//		String contentStr = new String(" 9월 10일에 찾아뵙도록 하겠습니다.");
//		String contentStr = autowriteInfo.getContent();
//		URLEncoder.encode(contentStr);
		nvps.add(new BasicNameValuePair("bd_content", contentStr));
		
		nvps.add(new BasicNameValuePair("bd_noBr", "0"));
		
		// 카테고리
		// 03 : 업소클레임
		// 04 : 선수찾기
		nvps.add(new BasicNameValuePair("ca_no", "4"));
		
		nvps.add(new BasicNameValuePair("mode", "write_ok"));
		nvps.add(new BasicNameValuePair("url_back", "list.phpid=qna"));
		nvps.add(new BasicNameValuePair("id", "qna"));
		nvps.add(new BasicNameValuePair("p", "1"));
		nvps.add(new BasicNameValuePair("or", "bd_regDate"));
		nvps.add(new BasicNameValuePair("al", "desc"));
		nvps.add(new BasicNameValuePair("no", ""));
		nvps.add(new BasicNameValuePair("ca_no", ""));
		nvps.add(new BasicNameValuePair("ca", ""));
		nvps.add(new BasicNameValuePair("sv", ""));
		nvps.add(new BasicNameValuePair("st", ""));
		nvps.add(new BasicNameValuePair("sc", ""));
		nvps.add(new BasicNameValuePair("sn", ""));
		
		
		return nvps;
	}
}
