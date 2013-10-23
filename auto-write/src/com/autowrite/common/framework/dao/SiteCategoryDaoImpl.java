package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.SiteCategoryEntity;

@Component
public class SiteCategoryDaoImpl implements SiteCategoryDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public void writeCategory(Map param) {
		try {
			sqlHelper.insert("site.category.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public Long countListCategory(Map param) {
		return (Long) sqlHelper.queryForObject("site.category.list.count", param);
	}

	
	@Override
	public List<SiteCategoryEntity> listCategory(Map param) {
		return (List<SiteCategoryEntity>)sqlHelper.queryForList("site.category.list", param);
	}
	
	@Override
	public void modifyCategory(Map param) {
		try {
			sqlHelper.insert("site.category.update", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCategory(Map param) {
		sqlHelper.delete("site.category.delete", param);
	}

	@Override
	public SiteCategoryEntity readCategory(Map param) {
		return (SiteCategoryEntity) sqlHelper.queryForObject("site.category.read", param);
	}
	
}
