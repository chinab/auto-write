package com.autowrite.common.framework.bean;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.dao.SiteDao;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.SiteListEntity;

@Component
public class SiteService extends CommonService{

	@Autowired 
    AdminService adminService;
	
	@Autowired
	SiteDao siteDao;

	public SiteService() {
	}

	
	/**
	 * 개인별 사이트 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SiteListEntity listPrivateSite(Map param) throws Exception {
		setCondition(param);
		
		SiteListEntity listEntity = new SiteListEntity();
		
		try {
			Long pageNum = (Long) param.get("PAGE_NUM");
			Long pageSize = (Long) param.get("PAGE_SIZE");
			listEntity.setPageNum(pageNum);
			listEntity.setPageSize(pageSize);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		Long boardCount = siteDao.countListPrivateSite(param);
		listEntity.setTotalListCount(boardCount);
		
		List<SiteEntity> siteList = siteDao.listPrivateSite(param);
		
		listEntity.setSiteList(siteList);
		
		return listEntity;
	}


	public SiteListEntity listMasterSite(Map param) throws Exception {
		setCondition(param);
		
		SiteListEntity listEntity = new SiteListEntity();
		
		try {
			Long pageNum = (Long) param.get("PAGE_NUM");
			Long pageSize = (Long) param.get("PAGE_SIZE");
			listEntity.setPageNum(pageNum);
			listEntity.setPageSize(pageSize);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		Long boardCount = siteDao.countListMasterSite(param);
		listEntity.setTotalListCount(boardCount);
		
		List<SiteEntity> siteList = siteDao.listMasterSite(param);
		
		listEntity.setSiteList(siteList);
		
		return listEntity;
	}


	public SiteListEntity writePrivateSite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		siteDao.writePrivateSite(param);
		
		return listPrivateSite(param);
	}


	public SiteListEntity writeMasterSite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		siteDao.writeMasterSite(param);
		
		return listMasterSite(param);
	}
	
	
	
	public SiteListEntity modifyPrivateSite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		siteDao.modifyPrivateSite(param);
		
		return listPrivateSite(param);
	}


	public SiteListEntity modifyMasterSite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		siteDao.modifyMasterSite(param);
		
		return listMasterSite(param);
	}
	
	
	public SiteListEntity deletePrivateSite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		siteDao.deletePrivateSite(param);
		
		return listPrivateSite(param);
	}
	
	
	public SiteListEntity deleteMasterSite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		siteDao.deleteMasterSite(param);
		
		return listMasterSite(param);
	}
	
	
	public SiteEntity readPrivateSite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		return siteDao.readPrivateSite(param);
	}
	
	
	public SiteEntity readMasterSite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		return siteDao.readMasterSite(param);
	}

}
