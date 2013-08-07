package com.autowrite.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONHelper {

	Map map = new HashMap();

	public void putMap(String skey) {
		if (skey == null) {
			return;
		}
		String[] keys = StringUtils.getToken(skey, ".");
		Map m = null;
		Map pm = this.map;
		for (int i = 0; i < keys.length; i++) {
			m = (Map) pm.get(keys[i]);
			if (m == null) {
				m = this.createMap();
				pm.put(keys[i], m);
			}
			pm = m;
		}

	}

	public void putList(String skey) {
		String[] keys = StringUtils.getToken(skey, ".");
		Map m = null;
		Map pm = this.map;
		for (int i = 0; i < keys.length - 1; i++) {
			m = (Map) pm.get(keys[i]);
			if (m == null) {
				m = this.createMap();
				pm.put(keys[i], m);
			}
			pm = m;
		}
		pm.put(keys[keys.length - 1], this.createList());
	}

	public void put(String skey, Object val) {
		if (val == null)
			val = "";

		String[] keys = StringUtils.getToken(skey, ".");
		Map m = null;
		Map pm = this.map;
		for (int i = 0; i < keys.length - 1; i++) {
			m = (Map) pm.get(keys[i]);
			if (m == null) {
				m = this.createMap();
				pm.put(keys[i], m);
			}
			pm = m;
		}

		Object obj = pm.get(keys[keys.length - 1]);
		if (obj == null || obj instanceof String) {
			pm.put(keys[keys.length - 1], val);
		} else if (obj instanceof List) {
			((List) obj).add(val);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		toString(sb, this.map);
		return sb.toString();
	}

	protected void toString(StringBuffer sb, Map map) {
		// StringBuffer sb =new StringBuffer();
		sb.append("{");
		Iterator ite = map.keySet().iterator();
		String skey = null;
		Object obj = null;
		while (ite.hasNext()) {
			skey = (String) ite.next();
			sb.append("\"").append(skey).append("\":");
			obj = map.get(skey);
			if (obj instanceof String)
				sb.append("\"").append(obj).append("\"");
			else if (obj instanceof Map)
				toString(sb, (Map) obj);
			else if (obj instanceof List)
				toString(sb, (List) obj);
			sb.append(",");
		}
		if (sb.toString().endsWith(","))
			sb.delete(sb.length() - 1, sb.length());
		sb.append("}");
	}

	protected void toString(StringBuffer sb, List al) {
		// StringBuffer sb =new StringBuffer();
		sb.append("[");

		String skey = null;
		Object obj = null;
		for (int i = 0; i < al.size(); i++) {
			obj = al.get(i);

			if (obj instanceof String)
				sb.append("\"").append(obj).append("\"");
			else if (obj instanceof Map)
				toString(sb, (Map) obj);
			else if (obj instanceof List)
				toString(sb, (List) obj);
			sb.append(",");
		}
		if (sb.toString().endsWith(","))
			sb.delete(sb.length() - 1, sb.length());
		sb.append("]");
	}

	public Map getData() {
		return this.map;
	}

	private Map createMap() {
		return new HashMap();
	}

	private List createList() {
		return new ArrayList();
	}
}
