package com.autowrite.bizmodel.share;


import java.util.*;

public class DataTransfer {
	private HashMap map = new HashMap();

	public void set(Object okey, Object value) {
		if (okey == null || value == null)
			return;
		if (okey instanceof String) {
			String skey = (String) okey;
			map.put(skey.toUpperCase(), value);
		} else
			map.put(okey, value);
	}

	public String get(String skey) {
		return (String) map.get(skey.toUpperCase());
	}

	public Object getObj(String skey) {
		return map.get(skey.toUpperCase());
	}
	public int getInt(String skey) throws Exception {
		String tmp = (String) map.get(skey.toUpperCase());

		return Integer.parseInt(tmp);
	}
	public Object getFirst() {
		if (map.size() == 0)
			return null;
		return map.values().iterator().next();

	}

	public void remove(String skey) {
		map.remove(skey.toUpperCase());
	}

	public boolean isEmpty() {
		if (map.size() > 0)
			return false;
		else
			return true;
	}
	public int size() {
		return map.size();
	}

	public void setMap(HashMap hmp) {
		Object obj = null;
		String skey = null;
		Iterator lst = hmp.keySet().iterator();

		while (lst.hasNext()) {
			obj = lst.next();
			if (obj instanceof String) {
				skey = (String) obj;
				map.put(skey.toUpperCase(), hmp.get(obj));
			} else
				map.put(obj, hmp.get(obj));
		}

	}

	public HashMap getMap() {
		return map;
	}

	public void copyMap(Map hmp) {
		map.putAll(hmp);
	}

	public void clear() {
		map.clear();
	}

}
