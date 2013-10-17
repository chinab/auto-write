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
    	// 사이트 별 특성. 하나밖에 못 올리므로 기존 글을 지워야 함.
    	deleteBoard(autowriteInfo);
    	
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
	}
    
    private void deleteBoard(AutowriteEntity autowriteInfo) throws Exception {
    	String paramName = "wr_id=";
    	// TODO : 키워드 디비화
    	String keyStr = "부평 스타";
    	
    	String contentKey = null;
    	
    	try {
			contentKey = readBoardKey(autowriteInfo, paramName, keyStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    	
    	String deleteUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/bbs/delete.php?bo_table=S22&wr_id=" + contentKey;
    	
		System.out.println("deleteUrl:" + deleteUrl);
		
    	HttpPost httpost = new HttpPost(deleteUrl);
		List<NameValuePair> nvps2 = setNvpsParams(autowriteInfo);
		httpost.setEntity(new UrlEncodedFormEntity(nvps2, autowriteInfo.getSiteEntity().getSite_encoding()));
        httpost.setHeader("Content-Type", "application/x-www-form-urlencoded;");
        
        HttpResponse response = httpclient.execute(httpost);            
        HttpEntity entity = response.getEntity();
        
        httpost.releaseConnection();
	}
    
    private String readBoardKey(AutowriteEntity autowriteInfo, String paramName, String keyStr) throws Exception {
    	String listUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/bbs/board.php?bo_table=S22";
    	
    	HttpPost httpost = new HttpPost(listUrl);
		List<NameValuePair> nvps2 = setNvpsParams(autowriteInfo);
		httpost.setEntity(new UrlEncodedFormEntity(nvps2, autowriteInfo.getSiteEntity().getSite_encoding()));
        httpost.setHeader("Content-Type", "application/x-www-form-urlencoded;");
		
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
    	
    	String contentKey = getContentKey(autowriteInfo, entity, paramName, keyStr);
    	
    	httpost.releaseConnection();
    	
    	return contentKey;
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
		
		return nvps;
	}
}
