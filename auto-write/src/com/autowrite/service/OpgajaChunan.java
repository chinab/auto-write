package com.autowrite.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

public class OpgajaChunan extends AutowriterCommon {
	/**
	 * 생성자
	 */
	public OpgajaChunan(){
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
			String loginUrl = getFullUrl(siteInfo, siteInfo.getLogin_url());
			HttpPost httpost = new HttpPost(loginUrl);
	
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("user_id", siteInfo.getSite_id()));
	        nvps.add(new BasicNameValuePair("password", siteInfo.getSite_passwd()));
	        
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
            nvps.add(new BasicNameValuePair("user_id", siteInfo.getSite_id()));
	        nvps.add(new BasicNameValuePair("password", siteInfo.getSite_passwd()));
	        
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
    	// delete URL
    	// http://www.opgaja14.com/index.php?mid=s2_4&category=203497&document_srl=8604384&act=procBoardDeleteDocument
    	deleteBoard(autowriteInfo);
    	
    	SiteEntity siteInfo = autowriteInfo.getSiteEntity();
    	String writeUrl = getFullUrl(siteInfo, siteInfo.getWrite_url()); 
		
    	System.out.println("============== writeUrl:" + writeUrl);
    	
    	
		HttpPost httpost = new HttpPost(writeUrl);
		List<NameValuePair> nvps2 = setNvpsParams(autowriteInfo);
		httpost.setEntity(new UrlEncodedFormEntity(nvps2, autowriteInfo.getSiteEntity().getSite_encoding()));
        httpost.setHeader("Content-Type", "application/x-www-form-urlencoded;");
		
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();

//		String responseBody = parseResponse(entity);
//		
//		System.out.println(responseBody);
	}
    
    
    private void deleteBoard(AutowriteEntity autowriteInfo) throws Exception {
    	String paramName = "document_srl=";
    	
    	SiteEntity siteInfo = autowriteInfo.getSiteEntity();
    	String keyStr = siteInfo.getSite_keyword();
    	
    	if ( keyStr == null || keyStr.trim().length() == 0 ) {
    		throw new Exception("사이트 키워드를 설정하세요.");
    	}
    	
    	String contentKey = null;
    	
    	try {
			contentKey = readBoardKey(autowriteInfo, paramName, keyStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    	
    	String categoryStr = siteInfo.getSite_category();
    	if ( categoryStr == null || categoryStr.trim().length() == 0 ) {
    		throw new Exception("사이트 대분류(category_srl)를 설정하세요.");
    	}
    	String deleteUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/index.php?mid=s2_4&category=" + categoryStr + "&act=procBoardDeleteDocument&document_srl=" + contentKey;
    	    	
//    	String deleteUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/index.php?mid=s2_4&category=203497&act=procBoardDeleteDocument&document_srl=" + contentKey;
    	
		System.out.println("deleteUrl:" + deleteUrl);
		
		HttpPost httpost = new HttpPost(deleteUrl);
//		List<NameValuePair> nvps = setNvpsParams(autowriteInfo);
//		httpost.setEntity(new UrlEncodedFormEntity(nvps, autowriteInfo.getSiteEntity().getSite_encoding()));
//        httpost.setHeader("Content-Type", "application/x-www-form-urlencoded;");
        
        HttpResponse response = httpclient.execute(httpost);            
//        HttpEntity entity = response.getEntity();
//        String responseBody = parseResponse(entity);
//		System.out.println(responseBody);
        httpost.releaseConnection();
	}
    
    
    private String readBoardKey(AutowriteEntity autowriteInfo, String paramName, String keyStr) throws Exception {
    	SiteEntity siteInfo = autowriteInfo.getSiteEntity();
    	String categoryStr = siteInfo.getSite_category();
    	
    	if ( categoryStr == null || categoryStr.trim().length() == 0 ) {
    		throw new Exception("사이트 대분류(category_srl)를 설정하세요.");
    	}
    	
    	// http://www.opgaja14.com/index.php?mid=s2_4&category=203497
//    	String listUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/index.php?mid=s2_4&category=203497";
    	String listUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/index.php?mid=s2_4&category=" + categoryStr;
    	
    	System.out.println("listUrl:" + listUrl);
    	
    	HttpPost httpost = new HttpPost(listUrl);
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
		
		String contentKey = getContentKey(autowriteInfo, entity, paramName, keyStr);
    	
    	return contentKey;
	}
    
    public List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo) throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		// 제목
		String subjectStr = autowriteInfo.getTitle();
		nvps.add(new BasicNameValuePair("title", subjectStr));
		nvps.add(new BasicNameValuePair("postTitle", subjectStr));
		
		System.out.println("subjectStr:" + subjectStr);
		
		// 내용
		String contentStr = autowriteInfo.getContent();
		nvps.add(new BasicNameValuePair("content", contentStr));
		

    	SiteEntity siteInfo = autowriteInfo.getSiteEntity();
    	String categoryStr = siteInfo.getSite_category();
    	
    	if ( categoryStr == null || categoryStr.trim().length() == 0 ) {
    		throw new Exception("사이트 대분류(category_srl)를 설정하세요.");
    	}
    	
		// 분류
		// 203502 : 인천
		// 203497 : 부평
		// 203496 : 부천
		// 203501 : 안양
		// 203498 : 분당
		// 203499 : 수원
		// 4129001 : 동탄
		// 203503 : 일산
		// 203500 : 안산
		// 3920392: 시화
		// 203504 : 평택
		// 8505551 : 광명
    	// ================= 추가분
    	// mid=s2_4
    	// 13753050 : 천안
    	// 13753050 : 대전
    	// 203767 : 청주
    	// 13753050 : 음성
//    	nvps.add(new BasicNameValuePair("category_srl", "203497"));
    	nvps.add(new BasicNameValuePair("category_srl", categoryStr));
		
    	System.out.println("categoryStr:" + categoryStr);
    	
		// 게시판 분류
		// s2_4 : 업소 라인업 
		nvps.add(new BasicNameValuePair("mid", "s2_4"));
				
		nvps.add(new BasicNameValuePair("act", "procBoardInsertDocument"));
		
		nvps.add(new BasicNameValuePair("vid", ""));
		nvps.add(new BasicNameValuePair("error_return_url", "/index.php?mid=so01&act=dispBoardWrite"));
		nvps.add(new BasicNameValuePair("module", "board"));
		nvps.add(new BasicNameValuePair("status", "PUBLIC"));
		nvps.add(new BasicNameValuePair("comment_status", "ALLOW"));
		nvps.add(new BasicNameValuePair("allow_trackback", "Y"));
		nvps.add(new BasicNameValuePair("notify_message", ""));
		
		return nvps;
	}

}
