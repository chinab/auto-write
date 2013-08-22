package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.AutowriteEntity;

public interface AutowriteDao {

	public abstract Long writeAutowriteMaster(Map param);
	
	public abstract void writeAutowriteSite(Map param);
	
	public abstract List<AutowriteEntity> listAutowrite(Map param);

	public abstract Long countListAutowrite(Map param);

	public abstract AutowriteEntity autowriteView(Map param);

	
}
