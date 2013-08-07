package com.autowrite.common.framework.entity;

import java.util.*;

public class MenuEntity {

	public MenuEntity() {
		menuName = null;
		menuLevel = null;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getMenuLevel() {
		return menuLevel;
	}

	public static List getMenu(List menus, String lv) {
		ArrayList al = new ArrayList();
		Map m = null;
		for (int i = 0; i < menus.size(); i++) {
			m = (Map) menus.get(i);
			MenuEntity menu = new MenuEntity();
			menu.setMenuLevel(lv);
			if (lv.equals("TOP"))
				menu.setMenuName((String) m.get("TOP_ITEM"));
			else if (lv.equals("MIDDLE"))
				menu.setMenuName((String) m.get("MID_ITEM"));
			else if (lv.equals("DETAIL"))
				menu.setMenuName((String) m.get("DETAIL_ITEM"));
			al.add(menu);
		}

		return al;
	}

	private String menuName;
	private String menuLevel;
}