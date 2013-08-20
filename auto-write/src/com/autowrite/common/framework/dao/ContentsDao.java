package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.AttachmentEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;

public interface ContentsDao {

	public abstract void writePrivateContents(Map param);
	
	public abstract List<BoardEntity> listPrivateContents(Map param);

	public abstract List<BoardEntity> listMasterContents(Map param);

	public abstract Long countListPrivateContents(Map param);

	public abstract Long countListMasterContents(Map param);

	public abstract BoardEntity contentsView(Map param);

	
}
