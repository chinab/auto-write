package com.autowrite.common.framework.entity;

import java.util.List;

public class UserBusinessListEntity extends PageListWrapper {
	private List<UserBusinessEntity> businessList;
	
	public UserBusinessListEntity(){
		totalListCount = 0;
		totalPageNum = 1;
		pageNum = 1;
		pageSize = 20;
	}

	public List<UserBusinessEntity> getSiteList() {
		return businessList;
	}
	public void setSiteList(List<UserBusinessEntity> businessList) {
		this.businessList = businessList;
	}
	
}
