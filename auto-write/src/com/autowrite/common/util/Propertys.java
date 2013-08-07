package com.autowrite.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Propertys {

	private String configFile = null;
	private Map mInfo = null;
	private Propertys parent = null;

	public Propertys() {
		this.mInfo = new HashMap();
	}

	protected void loadConfig(String sclass, String fname) {
		try {
			Document doc = DOMUtil.parse(fname);
			List clist = DOMUtil.getChildren(doc.getDocumentElement());
			Element ele = null;
			Map prop = new HashMap();
			for (int i = 0; i < clist.size(); i++) {
				ele = (Element) clist.get(i);
				String skey = DOMUtil.getAttribute(ele, "name");
				String val = DOMUtil.getAttribute(ele, "value");
				if (skey != null && val != null) {
					prop.put(skey, val);
				}
			}
			this.set(sclass, prop);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setParent(Propertys p) {
		this.parent = p;
	}

	/*
	 * public void setProperty(String sclass,Propertys prop) {
	 * 
	 * this.mInfo.put(sclass, prop); }
	 * 
	 * public Propertys getProperty(String sclass) {
	 * 
	 * return (Propertys)mInfo.get(sclass); }
	 */
	public void set(String skey, Object val) {
		if (skey != null && val != null)
			this.mInfo.put(skey, val);
	}

	public String get(String skey) {
		String prefix = null;
		String keyName = skey;
		int ipos = skey.indexOf(".");
		if (ipos > 0) {
			prefix = skey.substring(0, ipos);
			keyName = skey.substring(ipos + 1);
		}
		String str = null;
		if (prefix != null) {
			Map mp = this.getMap(prefix);
			if (mp == null)
				return null;
			str = (String) mp.get(keyName);
		} else {
			str = (String) this.mInfo.get(skey);
			if (str == null && this.getParent() != null)
				str = this.getParent().get(skey);
		}
		return str;
	}

	public String toString() {
		return this.mInfo.toString();
	}

	/*
	 * public void setList(String pname, List al) { // TODO Auto-generated
	 * method stub if (pname !=null && al !=null) this.mInfo.put(pname,al); }
	 */
	public List getList(String skey) {
		List al = (List) this.mInfo.get(skey);
		if (al == null && this.getParent() != null)
			al = this.getParent().getList(skey);
		return al;
	}

	public Map getMap(String skey) {
		Map mp = (Map) this.mInfo.get(skey);
		if (mp == null && this.getParent() != null)
			mp = this.getParent().getMap(skey);
		return mp;
	}

	public String[] getKeys() {
		String[] slist = new String[this.mInfo.size()];
		Iterator ite = this.mInfo.keySet().iterator();
		int cnt = 0;
		while (ite.hasNext()) {
			slist[cnt] = (String) ite.next();
			cnt++;
		}
		return slist;

	}

	protected Propertys getParent() {
		if (this.parent == this)
			return null;
		return this.parent;
	}
}
