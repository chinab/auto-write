package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import com.autowrite.common.framework.entity.MemoEntity;
import com.autowrite.common.framework.entity.MenuEntity;

public interface MemoDao {

	public abstract Long writeMemo(Map param);
	
	public abstract List<MemoEntity> listMemo(Map param);
	
	public abstract MemoEntity readMemo(Map param);

	public abstract void deleteMemo(Map param);

	public abstract void moveMemo(Map param);

	public abstract void updateReceivedMessageReadFlag(Map param);

	public abstract void updateSentMessageReadFlag(Map param);
}
