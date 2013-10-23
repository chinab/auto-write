package com.autowrite.common.framework.bean;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.dao.SiteCategoryDao;
import com.autowrite.common.framework.entity.SiteCategoryEntity;
import com.autowrite.common.framework.entity.SiteCategoryListEntity;

@Component
public class SiteCategoryService extends CommonService{

	@Autowired 
    AdminService adminService;
	
	@Autowired
	SiteCategoryDao siteCategoryDao;

	public SiteCategoryService() {
	}

	
	/**
	 * 개인별 사이트 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public SiteCategoryListEntity listCategory(Map param) throws Exception {
		setCondition(param);
		
		SiteCategoryListEntity listEntity = new SiteCategoryListEntity();
		
		try {
			Long pageNum = (Long) param.get("PAGE_NUM");
			Long pageSize = (Long) param.get("PAGE_SIZE");
			listEntity.setPageNum(pageNum);
			listEntity.setPageSize(pageSize);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		Long boardCount = siteCategoryDao.countListCategory(param);
		listEntity.setTotalListCount(boardCount);
		
		List<SiteCategoryEntity> siteList = siteCategoryDao.listCategory(param);
		
		listEntity.setSiteCategoryList(siteList);
		
		return listEntity;
	}


	public SiteCategoryListEntity writeCategory(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		siteCategoryDao.writeCategory(param);
		
		return listCategory(param);
	}
	
	
	
	public SiteCategoryListEntity modifyCategory(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		siteCategoryDao.modifyCategory(param);
		
		return listCategory(param);
	}
	
	
	public SiteCategoryListEntity deleteCategory(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		siteCategoryDao.deleteCategory(param);
		
		return listCategory(param);
	}
	
	
	public SiteCategoryEntity readCategory(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		return siteCategoryDao.readCategory(param);
	}

}
