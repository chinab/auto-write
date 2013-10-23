package com.autowrite.common.framework.entity;

import java.util.List;

public class SiteCategoryListEntity extends PageListWrapper {
	private List<SiteCategoryEntity> siteCategoryList;
	
	public SiteCategoryListEntity(){
		totalListCount = 0;
		totalPageNum = 1;
		pageNum = 1;
		pageSize = 20;
	}

	public List<SiteCategoryEntity> getSiteCategoryList() {
		return siteCategoryList;
	}
	public void setSiteCategoryList(List<SiteCategoryEntity> siteCategoryList) {
		this.siteCategoryList = siteCategoryList;
	}
	
}
