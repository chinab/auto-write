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
	public void writeAutowrite(Map param) {
		try {
			sqlHelper.insert("autowrite.private.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public AutowriteEntity autowriteView(Map param) {
		return (AutowriteEntity) sqlHelper.queryForObject("autowrite.private.select", param);
	}
	
}
