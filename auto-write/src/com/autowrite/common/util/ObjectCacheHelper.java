package com.autowrite.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectCacheHelper {
	static Map storage = new HashMap();
	static Map refreshTime = new HashMap();
	static Map lastRefreshTime = new HashMap();

	public static void setRefreshTime(String stype, int itime) {
		refreshTime.put(stype, new Long(itime * 1000));
	}

	public static void putObject(String stype, Object skey, Object obj) {

		ObjectCache cache = (ObjectCache) storage.get(stype);
		if (cache == null) {
			cache = new ObjectCache();
			storage.put(stype, cache);
		}
		cache.put(skey, obj);
	}

	public static Object getObject(String stype, Object skey) {
		Long lref = (Long) refreshTime.get(stype);
		if (lref != null && lref.longValue() > 0) {
			Long lastref = (Long) lastRefreshTime.get(stype);
			long curtime = System.currentTimeMillis();
			if (lastref != null) {

				if ((curtime - lastref.longValue()) > lref.longValue()) {
					storage.remove(stype);
					lastRefreshTime.put(stype, new Long(curtime));
				}
			} else {
				lastRefreshTime.put(stype, new Long(curtime));
			}
		}
		ObjectCache cache = (ObjectCache) storage.get(stype);
		if (cache == null)
			return null;

		return cache.get(skey);
	}

	public static boolean useCache(String stype) {
		Long lref = (Long) refreshTime.get(stype);
		if (lref != null) {
			return true;
		}
		return false;
	}

	public static List getObjects(String stype) {
		Long lref = (Long) refreshTime.get(stype);
		if (lref != null && lref.longValue() > 0) {
			Long lastref = (Long) lastRefreshTime.get(stype);
			long curtime = System.currentTimeMillis();
			if (lastref != null) {

				if ((curtime - lastref.longValue()) > lref.longValue()) {
					storage.remove(stype);
					lastRefreshTime.put(stype, new Long(curtime));
				}
			} else {
				lastRefreshTime.put(stype, new Long(curtime));
			}
		}
		ObjectCache cache = (ObjectCache) storage.get(stype);
		if (cache == null)
			return null;

		return cache.getObjects();
	}
}
