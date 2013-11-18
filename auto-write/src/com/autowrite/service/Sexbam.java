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
import com.autowrite.common.framework.entity.UserBusinessEntity;

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
    	// http://uuubam.com/index.php?mid=10444580&category=203497&document_srl=9436029&act=procBoardDeleteDocument
    	
    	String contentKey = deleteBoard(autowriteInfo);
    	autowriteInfo.setContentKey(contentKey);
    	
    	// 삭제하지 않으면 MODIFY BOARD
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
    
    
    private String deleteBoard(AutowriteEntity autowriteInfo) throws Exception {
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
    	
    	String deleteUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/index.php?mid=sschop3&category=203497&act=procBoardDeleteDocument&document_srl=" + contentKey;
    	
		System.out.println("DELETE URL:" + deleteUrl);
		
		HttpPost httpost = new HttpPost(deleteUrl);
//		List<NameValuePair> nvps = setNvpsParams(autowriteInfo);
//		httpost.setEntity(new UrlEncodedFormEntity(nvps, autowriteInfo.getSiteEntity().getSite_encoding()));
//        httpost.setHeader("Content-Type", "application/x-www-form-urlencoded;");
        
        HttpResponse response = httpclient.execute(httpost);            
//        HttpEntity entity = response.getEntity();
//        String responseBody = parseResponse(entity);
//		System.out.println(responseBody);
        httpost.releaseConnection();
        
        return contentKey;
	}
    
    
    private String readBoardKey(AutowriteEntity autowriteInfo, String paramName, String keyStr) throws Exception {
    	SiteEntity siteInfo = autowriteInfo.getSiteEntity();
    	String categoryStr = siteInfo.getSite_category();
    	
    	if ( categoryStr == null || categoryStr.trim().length() == 0 ) {
    		throw new Exception("지역 설정이 필요합니다. 관리자에게 문의하세요.");
    	}
    	
    	// http://uuubam.com/index.php?mid=sschop3&category=2828355
//    	String listUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/index.php?mid=sschop3&category=2828355";
    	String listUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/index.php?mid=sschop3&category=" + categoryStr;
    	
    	System.out.println("LIST URL:" + listUrl);
    	
    	HttpPost httpost = new HttpPost(listUrl);
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
		
		String contentKey = getContentKey(autowriteInfo, entity, paramName, keyStr);
    	
    	return contentKey;
	}
    
	public List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		// 제목
		String subjectStr = autowriteInfo.getTitle();
		nvps.add(new BasicNameValuePair("title", subjectStr));
		
		// 내용
		String contentStr = autowriteInfo.getContent();
		nvps.add(new BasicNameValuePair("content", contentStr));
		
		// 분류
		// 1465403 : 짤방
		// 1465427 : 동영상
		nvps.add(new BasicNameValuePair("category_srl", "2828355"));
		
		nvps.add(new BasicNameValuePair("category", "2828355"));
		
		// 삭제, 수정시 글의 키
		nvps.add(new BasicNameValuePair("document_srl", autowriteInfo.getContentKey()));
		
		nvps.add(new BasicNameValuePair("error_return_url", "/index.php?mid=so01&amp;act=dispBoardWrite"));
		nvps.add(new BasicNameValuePair("act", "procBoardInsertDocument"));
		nvps.add(new BasicNameValuePair("vid", ""));
		
		// so01 : 자유게시판
		// so03 : 놀이터
		// sschop3 : 오피
		nvps.add(new BasicNameValuePair("mid", "sschop3"));
		
		nvps.add(new BasicNameValuePair("module", "board"));
		nvps.add(new BasicNameValuePair("status", "PUBLIC"));
		nvps.add(new BasicNameValuePair("comment_status", "ALLOW"));
		nvps.add(new BasicNameValuePair("allow_trackback", "Y"));
		
		// 업소 정보
		List<UserBusinessEntity> userBusinessInfoList = autowriteInfo.getUserBusinessEntityList();
		
		if ( userBusinessInfoList != null && userBusinessInfoList.size() > 0 ){
			// TODO : 한 아이디당 여러 업소를 등록 할 경우 필요.
			// 현재는 한 업소정보만 등록하도록 유도할것.
			UserBusinessEntity userBusinessEntity = userBusinessInfoList.get(0);
			
			nvps.add(new BasicNameValuePair("extra_vars1", userBusinessEntity.getBusiness_name()));					//업소 이름
			nvps.add(new BasicNameValuePair("extra_vars2", userBusinessEntity.getBusiness_region()));				//지역
			nvps.add(new BasicNameValuePair("extra_vars3", userBusinessEntity.getBusiness_tel()));					//전화번호
			nvps.add(new BasicNameValuePair("extra_vars4", userBusinessEntity.getBusiness_category()));				//업종
			nvps.add(new BasicNameValuePair("extra_vars5", userBusinessEntity.getBusiness_time()));					//영업시간 및 예약안내
			nvps.add(new BasicNameValuePair("extra_vars6", userBusinessEntity.getBusiness_price()));				//가격 및 기타정보
			nvps.add(new BasicNameValuePair("extra_vars7", userBusinessEntity.getBusiness_address()));				//오시는길
			nvps.add(new BasicNameValuePair("extra_vars8", ""));
		}
		
		return nvps;
	}

}
