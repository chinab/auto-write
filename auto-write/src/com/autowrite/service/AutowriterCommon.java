package com.autowrite.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.EofSensorInputStream;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;

import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.SiteEntity;

public abstract class AutowriterCommon implements AutowriterInterface{
	protected DefaultHttpClient httpclient;
	
	protected String domainUrl;
	protected List<Cookie> cookies;
	
	/**
	 * 생성자
	 */
	public AutowriterCommon(){
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
	
	@Override
	public void setCookie(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, Exception {
		domainUrl = autowriteInfo.getSiteEntity().getDomain();
		
		if ( !domainUrl.startsWith("http://") ){
			domainUrl = "http://" + domainUrl;
		}
		
		System.out.println("[" + domainUrl + "]");
		
		HttpGet httpget = new HttpGet(domainUrl);

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		System.out.println("Login form get: " + response.getStatusLine());
		EntityUtils.consume(entity);

		System.out.println("Initial set of cookies:");
		cookies = httpclient.getCookieStore().getCookies();
	}
	
	@Override
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
	
	
	public static void printResponseEuckr(HttpEntity entity) throws IOException, UnsupportedEncodingException {
		EofSensorInputStream content = (EofSensorInputStream) entity.getContent();
		byte[] buffer = new byte[1024] ;
		int readCnt = 0;
		long conLen = entity.getContentLength();
		
		String responseHtml = null;
		while((readCnt = content.read(buffer)) != -1 ){
        		responseHtml = new String(buffer, 0, readCnt, "EUC-KR");
			System.out.print(responseHtml);
		}
	}
	
	
    @Override
	public void shutdownHttpConnection() throws Exception {
		httpclient.getConnectionManager().shutdown();
	}

	@Override
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

	@Override
	public abstract boolean login(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, UnsupportedEncodingException;

	@Override
	public abstract void writeBoard(AutowriteEntity autowriteInfo) throws Exception;

	@Override
	public abstract List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo);
	
	@Override
	public String wrapCdata(String data){
		String returnStr = "<![CDATA[" + data +"]]>";
		return returnStr;
	}
	
	@Override
	public String getFullUrl(SiteEntity siteInfo, String url){
		String fullUrl  = null;
		
		if ( url.startsWith("http://") ){
			fullUrl = url;
		} else {
			if ( domainUrl == null || domainUrl.length() == 0 ){
				domainUrl = siteInfo.getDomain();
				
				if ( !domainUrl.startsWith("http://") ){
					domainUrl = "http://" + domainUrl;
				}
			}
			
			if (url.startsWith("/")){
				fullUrl = domainUrl + url;
			} else {
				fullUrl = domainUrl + "/" + url;
			}
		}
		
		return fullUrl;
	}
	
}
