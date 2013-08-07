package com.autowrite.common.framework.entity;


public class PointMasterEntity {

	public PointMasterEntity() {
	}

	private String menuId = null;
	private String menuName = null;
	private int privateRead = 0;
	private int privateWrite = 0;
	private int privateUpdate = 0;
	private int privateDelete = 0;
	private int businessRead = 0;
	private int businessWrite = 0;
	private int businessUpdate = 0;
	private int businessDelete = 0;	
	private int waiteressRead = 0;
	private int waiteressWrite = 0;
	private int waiteressUpdate = 0;
	private int waiteressDelete = 0;
	private int adminRead = 0;
	private int adminWrite = 0;
	private int adminUpdate = 0;
	private int adminDelete = 0;	
	private int masterRead = 0;
	private int masterWrite = 0;
	private int masterUpdate = 0;
	private int masterDelete = 0;
	
	
	public String getMenuId() {
		return menuId;
	}


	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}


	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	public int getPrivateRead() {
		return privateRead;
	}


	public void setPrivateRead(int privateRead) {
		this.privateRead = privateRead;
	}


	public int getPrivateWrite() {
		return privateWrite;
	}


	public void setPrivateWrite(int privateWrite) {
		this.privateWrite = privateWrite;
	}


	public int getPrivateUpdate() {
		return privateUpdate;
	}


	public void setPrivateUpdate(int privateUpdate) {
		this.privateUpdate = privateUpdate;
	}


	public int getPrivateDelete() {
		return privateDelete;
	}


	public void setPrivateDelete(int privateDelete) {
		this.privateDelete = privateDelete;
	}


	public int getBusinessRead() {
		return businessRead;
	}


	public void setBusinessRead(int businessRead) {
		this.businessRead = businessRead;
	}


	public int getBusinessWrite() {
		return businessWrite;
	}


	public void setBusinessWrite(int businessWrite) {
		this.businessWrite = businessWrite;
	}


	public int getBusinessUpdate() {
		return businessUpdate;
	}


	public void setBusinessUpdate(int businessUpdate) {
		this.businessUpdate = businessUpdate;
	}


	public int getBusinessDelete() {
		return businessDelete;
	}


	public void setBusinessDelete(int businessDelete) {
		this.businessDelete = businessDelete;
	}


	public int getWaiteressRead() {
		return waiteressRead;
	}


	public void setWaiteressRead(int waiteressRead) {
		this.waiteressRead = waiteressRead;
	}


	public int getWaiteressWrite() {
		return waiteressWrite;
	}


	public void setWaiteressWrite(int waiteressWrite) {
		this.waiteressWrite = waiteressWrite;
	}


	public int getWaiteressUpdate() {
		return waiteressUpdate;
	}


	public void setWaiteressUpdate(int waiteressUpdate) {
		this.waiteressUpdate = waiteressUpdate;
	}


	public int getWaiteressDelete() {
		return waiteressDelete;
	}


	public void setWaiteressDelete(int waiteressDelete) {
		this.waiteressDelete = waiteressDelete;
	}


	public int getAdminRead() {
		return adminRead;
	}


	public void setAdminRead(int adminRead) {
		this.adminRead = adminRead;
	}


	public int getAdminWrite() {
		return adminWrite;
	}


	public void setAdminWrite(int adminWrite) {
		this.adminWrite = adminWrite;
	}


	public int getAdminUpdate() {
		return adminUpdate;
	}


	public void setAdminUpdate(int adminUpdate) {
		this.adminUpdate = adminUpdate;
	}


	public int getAdminDelete() {
		return adminDelete;
	}


	public void setAdminDelete(int adminDelete) {
		this.adminDelete = adminDelete;
	}


	public int getMasterRead() {
		return masterRead;
	}


	public void setMasterRead(int masterRead) {
		this.masterRead = masterRead;
	}


	public int getMasterWrite() {
		return masterWrite;
	}


	public void setMasterWrite(int masterWrite) {
		this.masterWrite = masterWrite;
	}


	public int getMasterUpdate() {
		return masterUpdate;
	}


	public void setMasterUpdate(int masterUpdate) {
		this.masterUpdate = masterUpdate;
	}


	public int getMasterDelete() {
		return masterDelete;
	}


	public void setMasterDelete(int masterDelete) {
		this.masterDelete = masterDelete;
	}


	public void setPoint(String userTypeCode, String actionType, int point) {
		if ( "M".equals(userTypeCode) && "READ".equals(actionType) ){
			setMasterRead(point);
		} else if ( "M".equals(userTypeCode) && "WRITE".equals(actionType) ){
			setMasterWrite(point);
		} else if ( "M".equals(userTypeCode) && "UPDATE".equals(actionType) ){
			setMasterUpdate(point);
		} else if ( "M".equals(userTypeCode) && "DELETE".equals(actionType) ){
			setMasterDelete(point);
		} else if ( "A".equals(userTypeCode) && "READ".equals(actionType) ){
			setAdminRead(point);
		} else if ( "A".equals(userTypeCode) && "WRITE".equals(actionType) ){
			setAdminWrite(point);
		} else if ( "A".equals(userTypeCode) && "UPDATE".equals(actionType) ){
			setAdminUpdate(point);
		} else if ( "A".equals(userTypeCode) && "DELETE".equals(actionType) ){
			setAdminDelete(point);
		} else if ( "P".equals(userTypeCode) && "READ".equals(actionType) ){
			setPrivateRead(point);
		} else if ( "P".equals(userTypeCode) && "WRITE".equals(actionType) ){
			setPrivateWrite(point);
		} else if ( "P".equals(userTypeCode) && "UPDATE".equals(actionType) ){
			setPrivateUpdate(point);
		} else if ( "P".equals(userTypeCode) && "DELETE".equals(actionType) ){
			setPrivateDelete(point);
		} else if ( "B".equals(userTypeCode) && "READ".equals(actionType) ){
			setBusinessRead(point);
		} else if ( "B".equals(userTypeCode) && "WRITE".equals(actionType) ){
			setBusinessWrite(point);
		} else if ( "B".equals(userTypeCode) && "UPDATE".equals(actionType) ){
			setBusinessUpdate(point);
		} else if ( "B".equals(userTypeCode) && "DELETE".equals(actionType) ){
			setBusinessDelete(point);
		} else if ( "W".equals(userTypeCode) && "READ".equals(actionType) ){
			setWaiteressRead(point);
		} else if ( "W".equals(userTypeCode) && "WRITE".equals(actionType) ){
			setWaiteressWrite(point);
		} else if ( "W".equals(userTypeCode) && "UPDATE".equals(actionType) ){
			setWaiteressUpdate(point);
		} else if ( "W".equals(userTypeCode) && "DELETE".equals(actionType) ){
			setWaiteressDelete(point);
		} 
	}
	
	public static String writePoint(int point){
		if ( point == 0 ){
			return point+"";
		} else {
			StringBuffer sb = new StringBuffer();
			
			sb.append("<font color='red'><b>");
			sb.append(point);
			sb.append("</b></font>");
			
			return sb.toString();
		}
	}
	
	
}