package com.autowrite.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

@Component
public class JSONUtil {
	public JSONUtil(){}
	
	public static Map convertJSONObjToMap( JSONObject inputData ) {
		Map returnMap = new HashMap();
		
		Iterator keys = inputData.keys();
		
		while(keys.hasNext()) {
			String currKey = (String) keys.next();
			
			if( inputData.get(currKey) instanceof JSONArray ) {
				returnMap.put(currKey, convertJSONArrToList(inputData.getJSONArray(currKey)));
			} else if( inputData.get(currKey) instanceof JSONObject ) {
				returnMap.put(currKey, convertJSONObjToMap((JSONObject)inputData.getJSONObject(currKey))); 
			} else
				returnMap.put(currKey, inputData.get(currKey));
		}
		return returnMap;
	}
	
	public static List convertJSONArrToList( JSONArray inputData ) {
		List returnList = new ArrayList();
		
		for(int i = 0; i < inputData.size(); i++ ) {
			if( inputData.get(i) instanceof JSONArray ) {
				returnList.add(convertJSONArrToList((JSONArray)inputData.get(i)));
			} else if( inputData.get(i) instanceof JSONObject ) {
				returnList.add(convertJSONObjToMap((JSONObject)inputData.get(i))); 
			} else
				returnList.add(inputData.get(i));			
		}
		
		return returnList;
	}	
}