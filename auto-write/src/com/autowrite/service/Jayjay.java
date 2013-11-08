package com.autowrite.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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


/**
 * @author Administrator
 * 여탑 변형 소스..  출근부 수정으로 등록.
 */
public class Jayjay extends AutowriterCommon {
	/**
	 * 생성자
	 */
	public Jayjay(){
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
//    	deleteBoard(autowriteInfo);
    	
    	SiteEntity siteInfo = autowriteInfo.getSiteEntity();
    	String modifyUrl = getFullUrl(siteInfo, siteInfo.getModify_url()); 
    	
		HttpPost httpost = new HttpPost(modifyUrl);
		List<NameValuePair> nvps2 = setNvpsParams(autowriteInfo);
		httpost.setEntity(new UrlEncodedFormEntity(nvps2, autowriteInfo.getSiteEntity().getSite_encoding()));
        httpost.setHeader("Content-Type", "application/x-www-form-urlencoded;");
		
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();

//		printResponseEuckr(entity);
		
		String responseStr = parseResponse(entity);
		
		System.out.println("After write : " + responseStr);
	}
    
    public List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo) throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		// 제목
		String subjectStr = autowriteInfo.getTitle();
		nvps.add(new BasicNameValuePair("IS_Subject", subjectStr));
		
		// 내용
		String contentStr = autowriteInfo.getContent();
		nvps.add(new BasicNameValuePair("up_contents", contentStr));
		
		// 업소명
		nvps.add(new BasicNameValuePair("up_name", "[인천-엣지]"));
		
		// 업소명
		nvps.add(new BasicNameValuePair("add1", "인천 계양구 계산동"));
		nvps.add(new BasicNameValuePair("add2", "126.72451021728524"));
		nvps.add(new BasicNameValuePair("add3", "37.54286079631704"));
		
		// 업소위치
		nvps.add(new BasicNameValuePair("up_addr", "인천 계산동 계양구청 도보5분거리"));
		
		// 연락처
		nvps.add(new BasicNameValuePair("up_person", "인천엣지실장"));
		
		// 담당자
		nvps.add(new BasicNameValuePair("up_tel", "010-4636-8473"));
		
		// 자동등록 방지 키
		String wr_key = null;
		try {
			wr_key = extractBanAutowriteDigit(autowriteInfo, "wr_key");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("자동등록 방지 키 추출 실패");
		}
		nvps.add(new BasicNameValuePair("wr_key", wr_key));
		
		// 오토점프 여부
		nvps.add(new BasicNameValuePair("autoJump", "N"));
		
		// 오토점프 시간
		nvps.add(new BasicNameValuePair("timeChoice", "240"));
		
		return nvps;
	}
	
    private String extractBanAutowriteDigit(AutowriteEntity autowriteInfo, String keyStr) throws Exception {
    	String modifyUrl = "http://" + autowriteInfo.getSiteEntity().getDomain() + "/establish/shop_write.php";
    	
    	HttpPost httpost = new HttpPost(modifyUrl);
//		List<NameValuePair> nvps2 = setNvpsParams(autowriteInfo);
//		httpost.setEntity(new UrlEncodedFormEntity(nvps2, autowriteInfo.getSiteEntity().getSite_encoding()));
//        httpost.setHeader("Content-Type", "application/x-www-form-urlencoded;");
		
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
    	
    	String banAutowriteDigit = getBanAutowriteDigit(autowriteInfo, entity, keyStr);
    	
    	int resultDigit = calculateKorDigit(banAutowriteDigit);
    	
    	httpost.releaseConnection();
    	
    	return resultDigit+"";
	}
    
    
	private String getBanAutowriteDigit(AutowriteEntity autowriteInfo, HttpEntity entity, String keyStr) throws Exception {
		EofSensorInputStream content = (EofSensorInputStream) entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(content, autowriteInfo.getSiteEntity().getSite_encoding()));
		String beforeLine = null;
		String curr = null;
		String contentKey = null;
		
		try {
			
			System.out.println("============= parse response =============");
			
			while((curr = reader.readLine()) != null ){
				if ( curr.contains(keyStr) ){
					System.out.println(beforeLine);
					System.out.println(curr);
					try {
						contentKey = beforeLine;
						while ( contentKey.contains("<") ){
							contentKey = contentKey.substring(contentKey.indexOf("+") - 1, contentKey.length() );
							contentKey = contentKey.substring(0, contentKey.indexOf("<") );
						}
						
						
						System.out.println("contentKey = [" + contentKey + "]");
						
						if ( contentKey!= null ){
							return contentKey;
						}
					} catch ( StringIndexOutOfBoundsException e ) {
						System.out.println(curr);
					} 
				}
				beforeLine = curr;
			}
			
		} catch ( Exception e ) {
			throw e;
		} finally {
			content.close();
		}
		
		return null;
	}
    
	private int calculateKorDigit(String testStr) {
		StringTokenizer st = new StringTokenizer(testStr, "+");
		
		int digitSum = 0;
		while ( st.hasMoreTokens() ) {
			String token = st.nextToken();
			int tempDigit = 0;
			
			try {
				tempDigit = new Integer(token);
			} catch (NumberFormatException nfe) {
				tempDigit = getDigitFromKor(token); 
			}
			System.out.println(tempDigit);
			
			digitSum = digitSum + tempDigit;
		}
		
		System.out.println(digitSum);
		
		return digitSum;
	}

	private static int getDigitFromKor(String token) {
		for ( int ii = 0 ; ii < 9 ; ii ++ ){
			if ( token.equals(kor1[ii]) || token.equals(kor2[ii])){
				return ii + 1;
			}
		}
		
		// 개놈들 오타..ㅡ.ㅡㅋ
		if ( token.equals("여덞") ){
			return 8;
		}
		
		return 0;
	}
	
	private static String[] kor1 = {"하나", "둘", "셋", "넷", "다섯", "여섯", "일곱", "여덟", "아홉"};
	private static String[] kor2 = {"일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
	

}
