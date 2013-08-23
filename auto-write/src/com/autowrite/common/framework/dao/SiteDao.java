package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.AttachmentEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.SiteParameterEntity;

public interface SiteDao {

	public abstract void writePrivateSite(Map param);
	
	public abstract SiteEntity readSite(Map param);
	
	public abstract List<SiteEntity> listPrivateSite(Map param);

	public abstract List<SiteEntity> listMasterSite(Map param);

	public abstract Long countListPrivateSite(Map param);

	public abstract Long countListMasterSite(Map param);

	public abstract SiteEntity getAutowriteSiteInfo(Map param);

	public abstract List<SiteParameterEntity> listSiteParameter(Map param);

}
