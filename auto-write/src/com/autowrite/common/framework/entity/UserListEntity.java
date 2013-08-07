package com.autowrite.common.framework.entity;

import java.util.List;
import java.util.Map;

public class UserListEntity extends PageListWrapper {
	private List<UserEntity> userList;
	
	public UserListEntity(){
		totalListCount = 0;
		totalPageNum = 1;
		pageNum = 1;
		pageSize = 20;
	}

	public List<UserEntity> getUserList() {
		return userList;
	}
	public void setUserList(List<UserEntity> userList) {
		this.userList = userList;
	}
}
