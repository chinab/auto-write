package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.SiteEntity;

public interface AutowriteDao {

	public abstract Long writeAutowriteMaster(Map param);
	
	public abstract Long writeAutowriteSite(Map param);
	
	public abstract void writeAutowriteLog(Map param);

	public abstract List<AutowriteEntity> listAutowriteMaster(Map param);

	public abstract Long countListAutowriteMaster(Map param);

	public abstract List<AutowriteEntity> listAutowriteSite(Map param);

	public abstract Long countListAutowriteSite(Map param);

	public abstract Long countListAutowriteLog(Map param);

	public abstract List<AutowriteEntity> listAutowriteLog(Map param);

	public abstract AutowriteEntity getAutowriteInfo(Map param);

	public abstract void deleteAutowriteMaster(Map param);
	
	public abstract void deleteAutowriteSite(Map param);
	
	public abstract void deleteAutowriteLog(Map param);

	public abstract AutowriteEntity getRestoredAutowrite(Map param);

	public abstract List<String> getSiteSeqIdList(Map param);
}
