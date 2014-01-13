package com.autowrite.common.framework.entity;

import java.util.List;

public class AddressListEntity extends PageListWrapper {
	private List<AddressEntity> addressList;
	
	public AddressListEntity(){
		totalListCount = 0;
		totalPageNum = 1;
		pageNum = 1;
		pageSize = 20;
	}
	
	public List<AddressEntity> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressEntity> addressList) {
		this.addressList = addressList;
	}
	
}