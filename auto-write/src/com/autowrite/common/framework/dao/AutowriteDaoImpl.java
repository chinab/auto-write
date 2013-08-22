package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.AutowriteEntity;;

@Component
public class AutowriteDaoImpl implements AutowriteDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public Long countListAutowrite(Map param) {
		return (Long) sqlHelper.queryForObject("autowrite.private.list.count", param);
	}

	
	@Override
	public List<AutowriteEntity> listAutowrite(Map param) {
		return (List<AutowriteEntity>)sqlHelper.queryForList("autowrite.private.list", param);
	}
	
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
	public AutowriteEntity autowriteView(Map param) {
		return (AutowriteEntity) sqlHelper.queryForObject("autowrite.private.select", param);
	}


	@Override
	public void writeAutowriteSite(Map param) {
		sqlHelper.insert("autowrite.site.write", param);
	}
	
}
