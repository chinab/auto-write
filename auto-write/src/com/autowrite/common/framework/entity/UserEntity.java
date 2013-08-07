package com.autowrite.common.framework.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KSH
 * 
 */
public class UserEntity {
	public static final String UserSessionKey = "userSessionKey";

	private String seq_id;
	private String name;
	private String id;
	private String nic;
	private String password;
	private String email;
	private String point;
	private String type_code;
	private String status_code;
	private String service_code;
	private String reg_datetime;
	private String approve_datetime;
	private String approval_user_seq_id;
	
	private String class_id;
	private String class_img_path;
	
	private String typeName;
	private String statusName;
	private String serviceName;
	
	private List<MenuDto> menuList;
	private Map<String, MenuDto> menuMap;
	
	private Map<String, String> quickLinkMap;
	
	private int uncheckedMemoCount = 0;
	
	private int visitCountToday = 0;
	private int visitCountYesterday = 0;
	private int visitCountTotal = 0;
	
	List<UserClassEntity> userClassList;
	
	private ConditionEntity conditionInfo;
	
	public String getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNic() {
		return nic;
	}
	public void setNic(String nic) {
		this.nic = nic;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getReg_datetime() {
		return reg_datetime;
	}
	public void setReg_datetime(String reg_datetime) {
		this.reg_datetime = reg_datetime;
	}
	public String getApprove_datetime() {
		return approve_datetime;
	}
	public void setApprove_datetime(String approve_datetime) {
		this.approve_datetime = approve_datetime;
	}
	public String getApproval_user_seq_id() {
		return approval_user_seq_id;
	}
	public void setApproval_user_seq_id(String approval_user_seq_id) {
		this.approval_user_seq_id = approval_user_seq_id;
	}
	public String getTypeName() {
		if ( type_code != null && type_code.length() > 0 ){
			if ( "P".equals(type_code) ){
				typeName = "일반";
			} else if ( "B".equals(type_code) ) {
				typeName = "업소";
			} else if ( "W".equals(type_code) ) {
				typeName = "아가씨";
			} else if ( "A".equals(type_code) ) {
				typeName = "운영자";
			} else if ( "M".equals(type_code) ) {
				typeName = "관리자";
			} else {
				typeName = "";
			}
		} else {
			typeName = "";
		}
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getStatusName() {
		if ( status_code != null && status_code.length() > 0 ){
			if ( "W".equals(status_code) ){
				statusName = "승인대기";
			} else if ( "A".equals(status_code) ) {
				statusName = "활동중";
			} else if ( "D".equals(status_code) ) {
				statusName = "강등";
			} else if ( "B".equals(status_code) ) {
				statusName = "블랙";
			} else {
				statusName = "";
			}
		} else {
			statusName = "";
		}
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getServiceName() {
		if ( service_code != null && service_code.length() > 0 ){
			if ( "01".equals(service_code) ){
				serviceName = "오피";
			} else if ( "02".equals(service_code) ) {
				serviceName = "안마";
			} else if ( "03".equals(service_code) ) {
				serviceName = "휴게,대떡";
			} else if ( "04".equals(service_code) ) {
				serviceName = "건마";
			} else if ( "05".equals(service_code) ){
				serviceName = "립카페";
			} else if ( "06".equals(service_code) ) {
				serviceName = "핸플";
			} else if ( "07".equals(service_code) ) {
				serviceName = "키스방";
			} else if ( "08".equals(service_code) ) {
				serviceName = "주점";
			} else if ( "09".equals(service_code) ) {
				serviceName = "기타";
			} else {
				serviceName = "";
			}
		} else {
			serviceName = "";
		}
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public List<MenuDto> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<MenuDto> menuList) {
		this.menuList = menuList;
	}
	
	public Map<String, MenuDto> getMenuMap() {
		if ( this.menuMap == null ){
			setMenuMap();
		}
		return this.menuMap;
	}
	

	public void setMenuMap(Map<String, MenuDto> menuMap) {
		this.menuMap = menuMap;
	}
	
	public void setMenuMap() {
		MenuDto menu = null;
		MenuDto subMenu = null;
		MenuDto leafMenu = null;
		this.menuMap = new HashMap<String, MenuDto>();
		
		for ( int ii = 0 ; ii < menuList.size() ; ii ++ ) {
			menu = menuList.get(ii);
			menuMap.put(menu.getMenuid(), menu);
			
			List<MenuDto> subMenuList = menu.getSubmenus();
			if ( subMenuList != null ) {
				for ( int jj = 0 ; jj < subMenuList.size() ; jj ++ ) {
					subMenu = new MenuDto(subMenuList.get(jj));
					subMenu.setParmenuid(menu.getMenuid());
					subMenu.setParmenunm(menu.getMenunm());
					menuMap.put(subMenu.getMenuid(), subMenu);
					
					
					List<MenuDto> leafMenuList = menu.getLeafmenus();
					if ( leafMenuList != null ) {
						for ( int kk = 0 ; kk < leafMenuList.size() ; kk ++ ) {
							leafMenu = new MenuDto(leafMenuList.get(kk));
							leafMenu.setParmenuid(subMenu.getMenuid());
							leafMenu.setParmenunm(subMenu.getMenunm());
							menuMap.put(leafMenu.getMenuid(), leafMenu);
						}
					}
				}
			}
		}
	}
	

	public Map<String, String> getQuickLinkMap() {
		return quickLinkMap;
	}
	public void setQuickLinkMap(Map<String, String> quickLinkMap) {
		this.quickLinkMap = quickLinkMap;
	}
	public int getUncheckedMemoCount() {
		return uncheckedMemoCount;
	}
	public void setUncheckedMemoCount(int uncheckedMemoCount) {
		this.uncheckedMemoCount = uncheckedMemoCount;
	}
	public String getBreadCrumb(String menuId) throws Exception{
		if ( this.menuMap == null ) {
			setMenuMap();
		}
		
		MenuDto mainMenu = null;
		MenuDto subMenu = null;
		MenuDto leafMenu = null;
		
		MenuDto menu = this.menuMap.get(menuId);
		if ( "1".equals(menu.getMenulvl()) ){
			mainMenu = menu;
		} else if ( "2".equals(menu.getMenulvl()) ){
			subMenu = this.menuMap.get(menu.getMenuid());
			mainMenu = this.menuMap.get(subMenu.getParmenuid());
		} else if ( "3".equals(menu.getMenulvl()) ){
			leafMenu = this.menuMap.get(menu.getMenuid());
			subMenu = this.menuMap.get(leafMenu.getParmenuid());
			mainMenu = this.menuMap.get(subMenu.getParmenuid());
		} else {
			throw new Exception("Menu level [" + menu.getMenulvl() + "] does not used.");
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<a href=\"javascript:goRoot();\">Home</a> / <a href=\"javascript:goMenu('");
		sb.append(mainMenu.getMenuid());
		sb.append("')\">");
		sb.append(mainMenu.getMenunm());
		sb.append("</a>\n");
		
		if ( subMenu != null ) {
			sb.append(" / <a href=\"javascript:goMenu('");
			sb.append(subMenu.getMenuid());
			sb.append("')\">");
			sb.append(subMenu.getMenunm());
			sb.append("</a>\n");
		}
		if ( leafMenu != null ) {
			sb.append(leafMenu.getMenunm());
		}
		
		return sb.toString();
	}
	
	public String getBootStrapBreadCrumb(String menuId) throws Exception{
		if ( this.menuMap == null ) {
			setMenuMap();
		}
		
		MenuDto mainMenu = null;
		MenuDto subMenu = null;
		MenuDto leafMenu = null;
		
		MenuDto menu = this.menuMap.get(menuId);
		if ( "1".equals(menu.getMenulvl()) ){
			mainMenu = menu;
		} else if ( "2".equals(menu.getMenulvl()) ){
			subMenu = this.menuMap.get(menu.getMenuid());
			mainMenu = this.menuMap.get(subMenu.getParmenuid());
		} else if ( "3".equals(menu.getMenulvl()) ){
			leafMenu = this.menuMap.get(menu.getMenuid());
			subMenu = this.menuMap.get(leafMenu.getParmenuid());
			mainMenu = this.menuMap.get(subMenu.getParmenuid());
		} else {
			throw new Exception("Menu level [" + menu.getMenulvl() + "] does not used.");
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<ul class=\"breadcrumb\">\n");
		sb.append("	<li><a href=\"javascript:goRoot();\">Home</a></li>\n");
		sb.append(" <li><span class=\"divider\">/</span><a href=\"javascript:goMenu('");
		sb.append(mainMenu.getMenuid());
		sb.append("')\">");
		sb.append(mainMenu.getMenunm());
		sb.append("</a> </li>\n");
		
		if ( subMenu != null ) {
			sb.append(" <li><span class=\"divider\">/</span><a href=\"javascript:goMenu('");
			sb.append(subMenu.getMenuid());
			sb.append("')\">");
			sb.append(subMenu.getMenunm());
			sb.append("</a> </li>\n");
		}
		if ( leafMenu != null ) {
			sb.append("<li class=\"active\"><span class=\"divider\">/</span>");
			sb.append(leafMenu.getMenunm());
			sb.append("</li>\n");
		}
		sb.append("</ul>\n");
		
		return sb.toString();
	}
	
	public String getTopMenu(){
		StringBuffer topMenuSb = new StringBuffer();
		
		// home 
		topMenuSb.append("		<div class=\"top_sm\">\n");
		topMenuSb.append("			<a href=\"mainView.do?jsp=main/main\">\n");
		topMenuSb.append("				HOME\n");
		topMenuSb.append("			</a>\n");
		topMenuSb.append("		</div>\n");
		
		MenuDto menu = null;
		List menus = this.getMenuList();
		
		for (int i = 0; i < menus.size(); i++) {
			menu = (MenuDto) menus.get(i);
			topMenuSb.append("		<div class=\"top_sm\">\n");
			topMenuSb.append("			<a href=\"javascript:menuClick('");
			topMenuSb.append(menu.getMenupageurl());
			topMenuSb.append("')\">\n");
			topMenuSb.append(menu.getMenunm());
			topMenuSb.append("\n			</a>\n");
			topMenuSb.append("		</div>\n");
		}
		
		return topMenuSb.toString();
	}
	
	public String getLeftMenu(String leftMenuId) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		MenuDto leftMenu = (MenuDto)this.getMenuMap().get(leftMenuId);
		List subMenus = leftMenu.getSubmenus();
		MenuDto subMenu = null;
		MenuDto lefMenu = null;

		String defaultMenuId = null;
		String defaultMenupageurl = null;
		String defaultMenuTitle = null;

		for (int i = 0; i < subMenus.size(); i++) {
			subMenu = (MenuDto) subMenus.get(i);
			List lefMenus = subMenu.getLeafmenus();
			boolean bleafmenu = false;

			if (lefMenus != null && lefMenus.size() > 0)
				bleafmenu = true;
			if (defaultMenuId == null && bleafmenu == false && subMenu.getMenupageurl() != null) {
				defaultMenuId = subMenu.getMenuid();
				defaultMenupageurl = subMenu.getMenupageurl();
				defaultMenuTitle = URLEncoder.encode(subMenu.getMenunm(), "UTF-8");
			}
			
			sb.append("	<div class=\"list\" id=\"");
			sb.append(subMenu.getMenuid());
			sb.append("\">\n");
			sb.append("		<a href=\"javascript:toggleLayer('");
			sb.append(subMenu.getMenuid());
			sb.append("', '");
			sb.append(subMenu.getMenupageurl());
			sb.append("', '");
			sb.append(subMenu.getMenunm());
			sb.append("')\">\n			");
			sb.append(subMenu.getMenunm());
			sb.append("		</a>\n");
			sb.append("	</div>\n");
		}
		
		return sb.toString();
		
	}
	public int getVisitCountToday() {
		return visitCountToday;
	}
	public void setVisitCountToday(int visitCountToday) {
		this.visitCountToday = visitCountToday;
	}
	public int getVisitCountYesterday() {
		return visitCountYesterday;
	}
	public void setVisitCountYesterday(int visitCountYesterday) {
		this.visitCountYesterday = visitCountYesterday;
	}
	public int getVisitCountTotal() {
		return visitCountTotal;
	}
	public void setVisitCountTotal(int visitCountTotal) {
		this.visitCountTotal = visitCountTotal;
	}
	
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getClass_img_path() {
		return class_img_path;
	}
	public void setClass_img_path(String class_img_path) {
		this.class_img_path = class_img_path;
	}
	public List<UserClassEntity> getUserClassList() {
		return userClassList;
	}
	public void setUserClassList(List<UserClassEntity> userClassList) {
		this.userClassList = userClassList;
	}
	public void setClassInfo(Integer userPoint, String userTypeCode) {
		Integer classPoint;
		
		if ( "A".equals(userTypeCode) ){
			this.setClass_img_path("icon_admin_level_all.gif");
		} else if ( "M".equals(userTypeCode) ){
			this.setClass_img_path("icon_master_level_all.gif");
		} else if ( "B".equals(userTypeCode) ){
			this.setClass_img_path("icon_business_level_all.gif");
		} else {
		
			for ( int ii = 0 ; ii < userClassList.size() ; ii ++ ) {
				UserClassEntity userClassEntity = userClassList.get(ii);
				classPoint = new Integer(userClassEntity.getClass_point());
				if ( userTypeCode.equals(userClassEntity.getClass_type_code()) ) {
					if ( userPoint >= classPoint ){
						this.setClass_id(userClassEntity.getClass_id());
						this.setClass_img_path(userClassEntity.getClass_img_path());
					} else {
						break;
					}
				}
			}
			
		}
	}
	
	public ConditionEntity getConditionInfo() {
		return conditionInfo;
	}
	public void setConditionInfo(ConditionEntity conditionInfo) {
		this.conditionInfo = conditionInfo;
	}
}
