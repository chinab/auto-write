package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.SiteCategoryEntity;

public interface SiteCategoryDao {

	public abstract void writeCategory(Map param);

	public abstract void modifyCategory(Map param);
	
	public abstract void deleteCategory(Map param);

	public abstract SiteCategoryEntity readCategory(Map param);

	public abstract List<SiteCategoryEntity> listCategory(Map param);

	public abstract Long countListCategory(Map param);

}
