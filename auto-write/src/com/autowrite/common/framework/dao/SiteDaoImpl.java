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
public class SiteDaoImpl implements SiteDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public Long countListPrivateSite(Map param) {
		return (Long) sqlHelper.queryForObject("site.private.list.count", param);
	}

	
	@Override
	public List<BoardEntity> listPrivateSite(Map param) {
		return (List<BoardEntity>)sqlHelper.queryForList("site.private.list", param);
	}
	
	@Override
	public Long countListMasterSite(Map param) {
		return (Long) sqlHelper.queryForObject("site.master.list.count", param);
	}

	
	@Override
	public List<BoardEntity> listMasterSite(Map param) {
		return (List<BoardEntity>)sqlHelper.queryForList("site.master.list", param);
	}
	
	@Override
	public void writeBoard(Map param) {
		try {
			sqlHelper.insert("board.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<BoardEntity> listBoard(Map param) {
		return sqlHelper.queryForList("board.list", param);
	}

	@Override
	public BoardEntity readBoard(Map param) {
		return (BoardEntity) sqlHelper.queryForObject("board.read", param);
	}
	
}
