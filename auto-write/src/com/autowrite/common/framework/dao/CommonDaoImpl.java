package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

@Component
public class CommonDaoImpl implements CommonDao {

	@Autowired
	SqlMapClientTemplate sqlHelper;

	public void createCommon(Map bean) {
		this.sqlHelper.insert("commoncode.insert", bean);
	}

	public void deleteCommon(Map bean) {
	}

	public void updateCommon(Map bean) {
		List keys = (List) bean.get("keys");
		for (int i = 0; i < keys.size(); i++) {
			bean.put("CommonCODE", keys.get(i));
			this.sqlHelper.update("commoncode.updateStatus", bean);
		}
	}

}
