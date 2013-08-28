package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.AttachmentEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;

@Component
public class ContentsDaoImpl implements ContentsDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public Long countListPrivateContents(Map param) {
		return (Long) sqlHelper.queryForObject("contents.private.list.count", param);
	}

	
	@Override
	public List<BoardEntity> listPrivateContents(Map param) {
		return (List<BoardEntity>)sqlHelper.queryForList("contents.private.list", param);
	}
	
	@Override
	public Long countListMasterContents(Map param) {
		return (Long) sqlHelper.queryForObject("contents.master.list.count", param);
	}

	
	@Override
	public List<BoardEntity> listMasterContents(Map param) {
		return (List<BoardEntity>)sqlHelper.queryForList("contents.master.list", param);
	}
	
	@Override
	public void writePrivateContents(Map param) {
		try {
			sqlHelper.insert("contents.private.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


	@Override
	public void modifyPrivateContents(Map param) {
		try {
			sqlHelper.insert("contents.private.modify", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@Override
	public BoardEntity contentsView(Map param) {
		return (BoardEntity) sqlHelper.queryForObject("contents.private.select", param);
	}


	@Override
	public void deletePrivateContents(Map param) {
		sqlHelper.delete("contents.private.delete", param);
	}
	
}
