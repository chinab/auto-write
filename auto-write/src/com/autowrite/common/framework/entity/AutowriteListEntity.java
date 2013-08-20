package com.autowrite.common.framework.entity;

import java.util.List;

public class AutowriteListEntity extends PageListWrapper {
	private List<AutowriteEntity> autowriteList;
	
	public AutowriteListEntity(){
		totalListCount = 0;
		totalPageNum = 1;
		pageNum = 1;
		pageSize = 20;
	}

	public List<AutowriteEntity> getAutowriteList() {
		return autowriteList;
	}
	public void setAutowriteList(List<AutowriteEntity> autowriteList) {
		this.autowriteList = autowriteList;
	}
	
}