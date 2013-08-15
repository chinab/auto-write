package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.AttachmentEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;

public interface SiteDao {

	public abstract void writePrivateSite(Map param);
	
	public abstract List<BoardEntity> listBoard(Map param);
	
	public abstract BoardEntity readBoard(Map param);
	
	public abstract List<BoardEntity> listPrivateSite(Map param);

	public abstract List<BoardEntity> listMasterSite(Map param);

	public abstract Long countListPrivateSite(Map param);

	public abstract Long countListMasterSite(Map param);

	
}
