package com.autowrite.common.framework.entity;

import java.util.List;

public class SiteListEntity extends PageListWrapper {
	private List<SiteEntity> siteList;
	
	public SiteListEntity(){
		totalListCount = 0;
		totalPageNum = 1;
		pageNum = 1;
		pageSize = 20;
	}

	public List<SiteEntity> getSiteList() {
		return siteList;
	}
	public void setSiteList(List<SiteEntity> siteList) {
		this.siteList = siteList;
	}
	
}
