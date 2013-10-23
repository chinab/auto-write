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

public class Bamsamo extends AutowriterCommon {
	/**
	 * 생성자
	 */
	public Bamsamo(){
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
			String loginUrl = getFullUrl(siteInfo, siteInfo.getLogin_url());
			HttpPost httpost = new HttpPost(loginUrl);
	
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("mb_id", siteInfo.getSite_id()));
	        nvps.add(new BasicNameValuePair("mb_password", siteInfo.getSite_passwd()));
	        
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

		printResponseEuckr(entity);
		
		System.out.println("Post logon cookies:");
	}
    
    private void deleteBoard(AutowriteEntity autowriteInfo) throws Exception {
    	String paramName = "wr_id=";
    	
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
    	
    	String deleteUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/bbs/delete.php?bo_table=line_2&wr_id=" + contentKey;
    	
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
    	String listUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/bbs/board.php?bo_table=line_2";
    	
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
    
	public List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		// 제목
		String subjectStr = autowriteInfo.getTitle();
		nvps.add(new BasicNameValuePair("wr_subject", subjectStr));
		
		// 내용
		String contentStr = autowriteInfo.getContent();
		nvps.add(new BasicNameValuePair("wr_content", contentStr));
		
		// 카테고리
		nvps.add(new BasicNameValuePair("bo_table", "line_2"));
		
		// html 형식
		nvps.add(new BasicNameValuePair("html", "html1"));
		
		
		// 제목글꼴
		// 굴림, 돋움, 바탕, 궁서
		nvps.add(new BasicNameValuePair("wr_subject_font", "돋움"));
		
		
		// wr_subject_color
		// #000000:검정
		// #ff9900:주황
		// #b3a14d:노랑
		// #3cb371:초록
		// #0033ff:파랑
		// #000099:남색
		// #9900cc:보라
		nvps.add(new BasicNameValuePair("wr_subject_color", "#0033ff"));
		
		// 분류
		// 
		nvps.add(new BasicNameValuePair("ca_name", "인천-엣지"));
		
		return nvps;
	}

}
