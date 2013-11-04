package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.UserBusinessEntity;

public interface IndividualDao {

	public abstract List<UserBusinessEntity> listBusinessInfo(Map param);

	public abstract Long countListBusinessInfo(Map param);

	public abstract Long writeListBusinessInfo(Map param);
	
	public abstract void deleteBusinessInfo(Map param);
	
	public abstract UserBusinessEntity readBusinessInfo(Map param);
}
