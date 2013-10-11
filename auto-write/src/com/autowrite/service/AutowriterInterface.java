package com.autowrite.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.SiteEntity;

public interface AutowriterInterface {
	
	public abstract void executeHttpConnection(AutowriteEntity autowriteInfo) throws Exception;
	
	public abstract void shutdownHttpConnection() throws Exception;
	
	public abstract void setCookie(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, Exception;
	
	public abstract boolean login(AutowriteEntity autowriteInfo) throws IOException, ClientProtocolException, UnsupportedEncodingException;
	
	public void writeBoard(AutowriteEntity autowriteInfo) throws Exception;
	
	public abstract List<NameValuePair> setNvpsParams(AutowriteEntity autowriteInfo);
	
	public abstract String parseResponse(HttpEntity entity) throws Exception;

	public abstract String wrapCdata(String data);

	public abstract String getFullUrl(SiteEntity siteInfo, String url);

	public abstract String getContentKey(HttpEntity entity, String paramName, String keyStr) throws Exception;
}
