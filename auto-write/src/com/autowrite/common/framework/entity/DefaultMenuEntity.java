package com.autowrite.common.framework.entity;


public class DefaultMenuEntity {

	public DefaultMenuEntity() {
	}

	private String menuId = null;
	private String menuName = null;
	private boolean privateRead = false;
	private boolean privateWrite = false;
	private boolean privateUpdate = false;
	private boolean privateDelete = false;
	private boolean businessRead = false;
	private boolean businessWrite = false;
	private boolean businessUpdate = false;
	private boolean businessDelete = false;	
	private boolean waiteressRead = false;
	private boolean waiteressWrite = false;
	private boolean waiteressUpdate = false;
	private boolean waiteressDelete = false;
	private boolean adminRead = false;
	private boolean adminWrite = false;
	private boolean adminUpdate = false;
	private boolean adminDelete = false;	
	private boolean masterRead = false;
	private boolean masterWrite = false;
	private boolean masterUpdate = false;
	private boolean masterDelete = false;
	
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
	public boolean isPrivateRead() {
		return privateRead;
	}
	public void setPrivateRead(boolean privateRead) {
		this.privateRead = privateRead;
	}
	public boolean isPrivateWrite() {
		return privateWrite;
	}
	public void setPrivateWrite(boolean privateWrite) {
		this.privateWrite = privateWrite;
	}
	public boolean isPrivateUpdate() {
		return privateUpdate;
	}
	public void setPrivateUpdate(boolean privateUpdate) {
		this.privateUpdate = privateUpdate;
	}
	public boolean isPrivateDelete() {
		return privateDelete;
	}
	public void setPrivateDelete(boolean privateDelete) {
		this.privateDelete = privateDelete;
	}
	public boolean isBusinessRead() {
		return businessRead;
	}
	public void setBusinessRead(boolean businessRead) {
		this.businessRead = businessRead;
	}
	public boolean isBusinessWrite() {
		return businessWrite;
	}
	public void setBusinessWrite(boolean businessWrite) {
		this.businessWrite = businessWrite;
	}
	public boolean isBusinessUpdate() {
		return businessUpdate;
	}
	public void setBusinessUpdate(boolean businessUpdate) {
		this.businessUpdate = businessUpdate;
	}
	public boolean isBusinessDelete() {
		return businessDelete;
	}
	public void setBusinessDelete(boolean businessDelete) {
		this.businessDelete = businessDelete;
	}
	public boolean isWaiteressRead() {
		return waiteressRead;
	}
	public void setWaiteressRead(boolean waiteressRead) {
		this.waiteressRead = waiteressRead;
	}
	public boolean isWaiteressWrite() {
		return waiteressWrite;
	}
	public void setWaiteressWrite(boolean waiteressWrite) {
		this.waiteressWrite = waiteressWrite;
	}
	public boolean isWaiteressUpdate() {
		return waiteressUpdate;
	}
	public void setWaiteressUpdate(boolean waiteressUpdate) {
		this.waiteressUpdate = waiteressUpdate;
	}
	public boolean isWaiteressDelete() {
		return waiteressDelete;
	}
	public void setWaiteressDelete(boolean waiteressDelete) {
		this.waiteressDelete = waiteressDelete;
	}
	public boolean isAdminRead() {
		return adminRead;
	}
	public void setAdminRead(boolean adminRead) {
		this.adminRead = adminRead;
	}
	public boolean isAdminWrite() {
		return adminWrite;
	}
	public void setAdminWrite(boolean adminWrite) {
		this.adminWrite = adminWrite;
	}
	public boolean isAdminUpdate() {
		return adminUpdate;
	}
	public void setAdminUpdate(boolean adminUpdate) {
		this.adminUpdate = adminUpdate;
	}
	public boolean isAdminDelete() {
		return adminDelete;
	}
	public void setAdminDelete(boolean adminDelete) {
		this.adminDelete = adminDelete;
	}
	public boolean isMasterRead() {
		return masterRead;
	}
	public void setMasterRead(boolean masterRead) {
		this.masterRead = masterRead;
	}
	public boolean isMasterWrite() {
		return masterWrite;
	}
	public void setMasterWrite(boolean masterWrite) {
		this.masterWrite = masterWrite;
	}
	public boolean isMasterUpdate() {
		return masterUpdate;
	}
	public void setMasterUpdate(boolean masterUpdate) {
		this.masterUpdate = masterUpdate;
	}
	public boolean isMasterDelete() {
		return masterDelete;
	}
	public void setMasterDelete(boolean masterDelete) {
		this.masterDelete = masterDelete;
	}
	public void setAuth(String userTypeCode, String authType) {
		String[] userTypeCodeArray = {"M", "A", "P", "B", "W"};
		String[] authTypeArray = {"R", "W", "U", "D"};
		
		if ( "M".equals(userTypeCode) && "R".equals(authType) ){
			setMasterRead(true);
		} else if ( "M".equals(userTypeCode) && "W".equals(authType) ){
			setMasterWrite(true);
		} else if ( "M".equals(userTypeCode) && "U".equals(authType) ){
			setMasterUpdate(true);
		} else if ( "M".equals(userTypeCode) && "D".equals(authType) ){
			setMasterDelete(true);
		} else if ( "A".equals(userTypeCode) && "R".equals(authType) ){
			setAdminRead(true);
		} else if ( "A".equals(userTypeCode) && "W".equals(authType) ){
			setAdminWrite(true);
		} else if ( "A".equals(userTypeCode) && "U".equals(authType) ){
			setAdminUpdate(true);
		} else if ( "A".equals(userTypeCode) && "D".equals(authType) ){
			setAdminDelete(true);
		} else if ( "P".equals(userTypeCode) && "R".equals(authType) ){
			setPrivateRead(true);
		} else if ( "P".equals(userTypeCode) && "W".equals(authType) ){
			setPrivateWrite(true);
		} else if ( "P".equals(userTypeCode) && "U".equals(authType) ){
			setPrivateUpdate(true);
		} else if ( "P".equals(userTypeCode) && "D".equals(authType) ){
			setPrivateDelete(true);
		} else if ( "B".equals(userTypeCode) && "R".equals(authType) ){
			setBusinessRead(true);
		} else if ( "B".equals(userTypeCode) && "W".equals(authType) ){
			setBusinessWrite(true);
		} else if ( "B".equals(userTypeCode) && "U".equals(authType) ){
			setBusinessUpdate(true);
		} else if ( "B".equals(userTypeCode) && "D".equals(authType) ){
			setBusinessDelete(true);
		} else if ( "W".equals(userTypeCode) && "R".equals(authType) ){
			setWaiteressRead(true);
		} else if ( "W".equals(userTypeCode) && "W".equals(authType) ){
			setWaiteressWrite(true);
		} else if ( "W".equals(userTypeCode) && "U".equals(authType) ){
			setWaiteressUpdate(true);
		} else if ( "W".equals(userTypeCode) && "D".equals(authType) ){
			setWaiteressDelete(true);
		} 
	}
	
	
	
	
}