package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.UserBusinessEntity;

@Component
public class IndividualDaoImpl implements IndividualDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public List<UserBusinessEntity> listBusinessInfo(Map param) {
		return (List<UserBusinessEntity>)sqlHelper.queryForList("individual.business.list", param);
	}


	@Override
	public Long countListBusinessInfo(Map param) {
		return (Long) sqlHelper.queryForObject("individual.business.list.count", param);
	}


	@Override
	public Long writeListBusinessInfo(Map param) {
		long seqId = 0;
		
		try {
			seqId = (Long) sqlHelper.insert("individual.business.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return seqId;
	}


	@Override
	public void deleteBusinessInfo(Map param) {
		sqlHelper.delete("individual.business.delete", param);
	}


	@Override
	public UserBusinessEntity readBusinessInfo(Map param) {
		return (UserBusinessEntity) sqlHelper.queryForObject("individual.business.read", param);
	}
}
