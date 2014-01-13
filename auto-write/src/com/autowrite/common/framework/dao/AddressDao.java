package com.autowrite.common.framework.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.AddressEntity;
import com.autowrite.common.framework.entity.AttachmentEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;

public interface AddressDao {

	public abstract void writePrivateAddress(Map param);

	public abstract void modifyPrivateAddress(Map param);
	
	public abstract List<AddressEntity> listPrivateAddress(Map param);

	public abstract List<AddressEntity> listMasterAddress(Map param);

	public abstract Long countListPrivateAddress(Map param);

	public abstract Long countListMasterAddress(Map param);

	public abstract AddressEntity addressView(Map param);

	public abstract void deletePrivateAddress(Map param);

	public abstract void writeExcelAddressList(List<AddressEntity> adressList) throws SQLException;
}
