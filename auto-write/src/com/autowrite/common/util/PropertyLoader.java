package com.autowrite.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

public class PropertyLoader {

	public static Propertys loadProperty(Element app) throws Exception {
		List props = DOMUtil.getChildren(app, "property");
		String pname = null;
		String val = null;
		String type = null;
		String sfil = null;
		Element cdele = null;
		Propertys prop = new Propertys();

		for (int i = 0; props != null && i < props.size(); i++) {
			cdele = (Element) props.get(i);
			pname = DOMUtil.getAttribute(cdele, "name");
			type = DOMUtil.getAttribute(cdele, "type");
			if (type == null || type.length() == 0) {
				val = DOMUtil.getAttribute(cdele, "value");
				prop.set(pname, val);
				sfil = DOMUtil.getAttribute(cdele, "file");
				if (sfil != null && sfil.length() > 0) {

					prop.loadConfig(pname, XIIConstant.DIR_CONFIG + sfil);
				}
			} else if (type.equals("map")) {
				List mems = DOMUtil.getChildren(cdele);
				Map pp = readProperty(mems);
				System.out.println("PP:" + pp.toString());
				prop.set(pname, pp);
			} else if (type.equals("list")) {
				List mems = DOMUtil.getChildren(cdele);
				List al = readList(mems);
				System.out.println("PP:" + al.toString());
				prop.set(pname, al);
			}

		}
		return prop;
	}

	private static List readList(List mems) {
		// TODO Auto-generated method stub
		ArrayList al = new ArrayList();
		Element cdele = null;
		String val = null;
		for (int i = 0; i < mems.size(); i++) {
			cdele = (Element) mems.get(i);
			val = DOMUtil.getElementText(cdele);
			al.add(val);
		}
		return al;
	}

	private static Map readProperty(List mems) {
		// TODO Auto-generated method stub
		String pname = null;
		String val = null;
		String type = null;
		String sfil = null;
		Element cdele = null;
		HashMap prop = new HashMap();
		for (int i = 0; i < mems.size(); i++) {
			cdele = (Element) mems.get(i);
			pname = DOMUtil.getAttribute(cdele, "name");
			val = DOMUtil.getAttribute(cdele, "value");
			prop.put(pname, val);
		}

		return prop;
	}
}
