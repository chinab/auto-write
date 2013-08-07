package com.autowrite.common.framework.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.MemoEntity;
import com.autowrite.common.framework.entity.MenuEntity;

@Component
public class MemoDaoImpl implements MemoDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public Long writeMemo(Map param) {
		long seqId = 0;
		
		try {
			seqId = (Long) sqlHelper.insert("memo.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return seqId;
	}

	@Override
	public List<MemoEntity> listMemo(Map param) {
		return sqlHelper.queryForList("memo.list", param);
	}

	@Override
	public MemoEntity readMemo(Map param) {
		return (MemoEntity) sqlHelper.queryForObject("memo.read", param);
	}

	@Override
	public void deleteMemo(Map param) {
		try {
			sqlHelper.insert("memo.delete", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void moveMemo(Map param) {
		try {
			sqlHelper.insert("memo.move", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void updateReceivedMessageReadFlag(Map param) {
		sqlHelper.update("memo.update.received.read.flag", param);
	}

	@Override
	public void updateSentMessageReadFlag(Map param) {
		sqlHelper.update("memo.update.sent.read.flag", param);
	}
}
