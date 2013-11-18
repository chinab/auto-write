package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.SiteParameterEntity;

public interface SiteDao {

	public abstract void writePrivateSite(Map param);

	public abstract void writeMasterSite(Map param);

	public abstract void modifyPrivateSite(Map param);
	
	public abstract void modifyMasterSite(Map param);

	public abstract void deletePrivateSite(Map param);

	public abstract void deleteMasterSite(Map param);

	public abstract SiteEntity readPrivateSite(Map param);

	public abstract SiteEntity readMasterSite(Map param);
	
	public abstract List<SiteEntity> listPrivateSite(Map param);

	public abstract List<SiteEntity> listMasterSite(Map param);

	public abstract List<SiteEntity> listReservedSite(Map param);

	public abstract Long countListPrivateSite(Map param);

	public abstract Long countListMasterSite(Map param);

	public abstract SiteEntity getAutowriteSiteInfo(Map param);

	public abstract List<SiteParameterEntity> listSiteParameter(Map param);
}
