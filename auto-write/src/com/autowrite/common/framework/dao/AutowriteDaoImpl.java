package com.autowrite.common.framework.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.SiteEntity;

@Component
public class AutowriteDaoImpl implements AutowriteDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Override
	public Long writeAutowriteMaster(Map param) {
		long seqId = 0;
		
		try {
			seqId = (Long) sqlHelper.insert("autowrite.master.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return seqId;
	}


	@Override
	public Long writeAutowriteSite(Map param) {
		long seqId = 0;
		
		try {
			seqId = (Long) sqlHelper.insert("autowrite.site.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return seqId;
	}

	@Override
	public void writeAutowriteLog(Map param) {
		sqlHelper.insert("autowrite.log.write", param);
	}


	@Override
	public Long writeAutowriteReserveMaster(Map param) {
		long seqId = 0;
		
		try {
			seqId = (Long) sqlHelper.insert("autowrite.reserve.master.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return seqId;
	}


	@Override
	public Long writeAutowriteReserveSite(Map param) {
		long seqId = 0;
		
		try {
			seqId = (Long) sqlHelper.insert("autowrite.reserve.site.write", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return seqId;
	}


	@Override
	public Long countListAutowriteReserve(Map param) {
		return (Long) sqlHelper.queryForObject("autowrite.reserve.list.count", param);
	}
	

	@Override
	public Long countListAutowriteMaster(Map param) {
		return (Long) sqlHelper.queryForObject("autowrite.master.list.count", param);
	}

	
	@Override
	public List<AutowriteEntity> listAutowriteReserve(Map param) {
		return (List<AutowriteEntity>)sqlHelper.queryForList("autowrite.reserve.list", param);
	}


	@Override
	public List<AutowriteEntity> listAutowriteMaster(Map param) {
		return (List<AutowriteEntity>)sqlHelper.queryForList("autowrite.master.list", param);
	}
	
	@Override
	public Long countListAutowriteSite(Map param) {
		return (Long) sqlHelper.queryForObject("autowrite.site.list.count", param);
	}

	
	@Override
	public List<AutowriteEntity> listAutowriteSite(Map param) {
		return (List<AutowriteEntity>)sqlHelper.queryForList("autowrite.site.list", param);
	}
	
	@Override
	public Long countListAutowriteLog(Map param) {
		return (Long) sqlHelper.queryForObject("autowrite.log.list.count", param);
	}

	
	@Override
	public List<AutowriteEntity> listAutowriteLog(Map param) {
		return (List<AutowriteEntity>)sqlHelper.queryForList("autowrite.log.list", param);
	}


	@Override
	public AutowriteEntity getAutowriteInfo(Map param) {
		return (AutowriteEntity) sqlHelper.queryForObject("autowrite.info.read", param);
	}


	@Override
	public void deleteAutowriteReserveMaster(Map param) {
		sqlHelper.delete("autowrite.reserve.master.delete", param);
	}
	
	
	@Override
	public void deleteAutowriteReserveSite(Map param) {
		sqlHelper.delete("autowrite.reserve.site.delete", param);
	}
	
	
	@Override
	public void deleteAutowriteMaster(Map param) {
		sqlHelper.delete("autowrite.master.delete", param);
	}
	
	
	@Override
	public void deleteAutowriteSite(Map param) {
		sqlHelper.delete("autowrite.site.delete", param);
	}
	
	
	@Override
	public void deleteAutowriteLog(Map param) {
		sqlHelper.delete("autowrite.log.delete", param);
	}
	
	
	@Override
	public AutowriteEntity getRestoredAutowrite(Map param) {
		return (AutowriteEntity) sqlHelper.queryForObject("autowrite.info.restored.read", param);
	}


	@Override
	public List<String> getSiteSeqIdList(Map param) {
		return (List<String>) sqlHelper.queryForList("autowrite.site.seqid.list", param);
	}


	@Override
	public AutowriteEntity getReservedAutowriteEntity(Map param) {
		return (AutowriteEntity) sqlHelper.queryForObject("autowrite.reserve.master.read", param);
	}
	
	
	@Override
	public List<String> getReservedSiteSeqIdList(Map param) {
		return (List<String>) sqlHelper.queryForList("autowrite.reserve.site.seqid.list", param);
	}


	@Override
	public void modifyAutowriteReserveMaster(Map param) {
		try {
			sqlHelper.insert("autowrite.reserve.master.update", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
