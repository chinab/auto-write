package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.SiteEntity;

public interface AutowriteDao {

	public abstract Long writeAutowriteMaster(Map param);
	
	public abstract Long writeAutowriteReserveMaster(Map param);
	
	public abstract Long writeAutowriteSite(Map param);
	
	public abstract Long writeAutowriteReserveSite(Map param);
	
	public abstract void writeAutowriteLog(Map param);

	public abstract List<AutowriteEntity> listAutowriteReserve(Map param);

	public abstract List<AutowriteEntity> listAutowriteMaster(Map param);

	public abstract Long countListAutowriteReserve(Map param);

	public abstract Long countListAutowriteMaster(Map param);

	public abstract List<AutowriteEntity> listAutowriteSite(Map param);

	public abstract Long countListAutowriteSite(Map param);

	public abstract Long countListAutowriteLog(Map param);

	public abstract List<AutowriteEntity> listAutowriteLog(Map param);

	public abstract AutowriteEntity getAutowriteInfo(Map param);

	public abstract void deleteAutowriteReserveMaster(Map param);
	
	public abstract void deleteAutowriteReserveSite(Map param);
	
	public abstract void deleteAutowriteMaster(Map param);
	
	public abstract void deleteAutowriteSite(Map param);
	
	public abstract void deleteAutowriteLog(Map param);

	public abstract AutowriteEntity getRestoredAutowrite(Map param);

	public abstract List<String> getSiteSeqIdList(Map param);

	public abstract AutowriteEntity getReservedAutowriteEntity(Map param);

	public abstract List<String> getReservedSiteSeqIdList(Map param);

	public abstract void modifyAutowriteReserveMaster(Map param);

	public abstract List<AutowriteEntity> getEffectiveReservedAutowriteEntity();

	public abstract List<String> getEffectiveReservedSite(Map param);

	public abstract void updateReserveRemainMinute(Map param);
	
}
