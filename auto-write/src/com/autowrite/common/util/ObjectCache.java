package com.autowrite.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectCache {

	Map storage = new HashMap();

	public void put(Object skey, Object obj) {
		// TODO Auto-generated method stub
		if (skey == null || obj == null)
			return;

		storage.put(skey, obj);
	}

	public Object get(Object skey) {
		// TODO Auto-generated method stub
		return storage.get(skey);
	}

	public List getObjects() {
		Object[] objs = storage.entrySet().toArray();
		List al = new ArrayList();
		for (int i = 0; i < objs.length; i++) {
			al.add(objs[i]);
		}
		return al;
	}
}
