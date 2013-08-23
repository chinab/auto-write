package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.AutowriteEntity;

@Component
public class AutowriteDaoImpl implements AutowriteDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public Long writeAutowriteMaster(Map param) {
		long seqId = 0;
		
		try {
			seqId = (Long) sqlHelper.insert("autowrite.master.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return seqId;
	}


	@Override
	public Long writeAutowriteSite(Map param) {
		long seqId = 0;
		
		try {
			seqId = (Long) sqlHelper.insert("autowrite.site.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return seqId;
	}

	@Override
	public void writeAutowriteLog(Map param) {
		sqlHelper.insert("autowrite.log.write", param);
	}

	@Override
	public Long countListAutowriteMaster(Map param) {
		return (Long) sqlHelper.queryForObject("autowrite.master.list.count", param);
	}

	
	@Override
	public List<AutowriteEntity> listAutowriteMaster(Map param) {
		return (List<AutowriteEntity>)sqlHelper.queryForList("autowrite.master.list", param);
	}
	
	@Override
	public Long countListAutowriteSite(Map param) {
		return (Long) sqlHelper.queryForObject("autowrite.site.list.count", param);
	}

	
	@Override
	public List<AutowriteEntity> listAutowriteSite(Map param) {
		return (List<AutowriteEntity>)sqlHelper.queryForList("autowrite.site.list", param);
	}
	
	@Override
	public Long countListAutowriteLog(Map param) {
		return (Long) sqlHelper.queryForObject("autowrite.log.list.count", param);
	}

	
	@Override
	public List<AutowriteEntity> listAutowriteLog(Map param) {
		return (List<AutowriteEntity>)sqlHelper.queryForList("autowrite.log.list", param);
	}


	@Override
	public AutowriteEntity getAutowriteInfo(Map param) {
		return (AutowriteEntity) sqlHelper.queryForObject("autowrite.info.read", param);
	}
}
