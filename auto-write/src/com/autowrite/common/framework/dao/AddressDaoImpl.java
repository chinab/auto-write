package com.autowrite.common.framework.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.AddressEntity;
import com.ibatis.sqlmap.client.SqlMapClient;

@Component
public class AddressDaoImpl implements AddressDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public Long countListPrivateAddress(Map param) {
		return (Long) sqlHelper.queryForObject("address.private.list.count", param);
	}

	
	@Override
	public List<AddressEntity> listPrivateAddress(Map param) {
		return (List<AddressEntity>)sqlHelper.queryForList("address.private.list", param);
	}
	
	@Override
	public Long countListMasterAddress(Map param) {
		return (Long) sqlHelper.queryForObject("address.master.list.count", param);
	}

	
	@Override
	public List<AddressEntity> listMasterAddress(Map param) {
		return (List<AddressEntity>)sqlHelper.queryForList("address.master.list", param);
	}
	
	@Override
	public void writePrivateAddress(Map param) {
		try {
			sqlHelper.insert("address.private.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


	@Override
	public void modifyPrivateAddress(Map param) {
		try {
			sqlHelper.insert("address.private.modify", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@Override
	public AddressEntity addressView(Map param) {
		return (AddressEntity) sqlHelper.queryForObject("address.private.select", param);
	}


	@Override
	public void deletePrivateAddress(Map param) {
		sqlHelper.delete("address.private.delete", param);
	}


	@Override
	public void writeExcelAddressList(List<AddressEntity> adressList) throws SQLException {
		SqlMapClient sqlMap = sqlHelper.getSqlMapClient();
		
		sqlMap.startTransaction();
		AddressEntity addressEntity = null;
		
		try {
			sqlMap.startBatch ();
			
			int listSize = adressList.size();
			
			for ( int ii = 0 ; ii < listSize ; ii ++ ){
				addressEntity = adressList.get(ii);
//				sqlMap.insert("autorwrite.address.contents.write", addressEntity);
				sqlHelper.insert("autorwrite.address.contents.write", addressEntity);
			}
			
			sqlMap.executeBatch();
			sqlMap.commitTransaction();
		} catch ( SQLException se ){
			se.printStackTrace();
			throw se;
		} finally {
			sqlMap.endTransaction();
			// TODO DB logging.
		}
		
		
	}
	
}
