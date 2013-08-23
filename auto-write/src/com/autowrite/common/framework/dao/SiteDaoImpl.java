package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.AttachmentEntity;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.SiteParameterEntity;

@Component
public class SiteDaoImpl implements SiteDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public Long countListPrivateSite(Map param) {
		return (Long) sqlHelper.queryForObject("site.private.list.count", param);
	}

	
	@Override
	public List<SiteEntity> listPrivateSite(Map param) {
		return (List<SiteEntity>)sqlHelper.queryForList("site.private.list", param);
	}
	
	@Override
	public Long countListMasterSite(Map param) {
		return (Long) sqlHelper.queryForObject("site.master.list.count", param);
	}

	
	@Override
	public List<SiteEntity> listMasterSite(Map param) {
		return (List<SiteEntity>)sqlHelper.queryForList("site.master.list", param);
	}
	
	@Override
	public void writePrivateSite(Map param) {
		try {
			sqlHelper.insert("site.private.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public SiteEntity readSite(Map param) {
		return (SiteEntity) sqlHelper.queryForObject("board.read", param);
	}


	@Override
	public SiteEntity getAutowriteSiteInfo(Map param) {
		return (SiteEntity) sqlHelper.queryForObject("autowrite.site.info.read", param);
	}


	@Override
	public List<SiteParameterEntity> listSiteParameter(Map param) {
		return (List<SiteParameterEntity>)sqlHelper.queryForList("site.parameter.list", param);
	}
	
}
