package com.autowrite.common.framework.bean;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autowrite.common.config.Constant;
import com.autowrite.common.framework.dao.ContentsDao;
import com.autowrite.common.framework.dao.IndividualDao;
import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.AutowriteListEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.SiteParameterEntity;
import com.autowrite.common.framework.entity.UserBusinessEntity;
import com.autowrite.common.framework.entity.UserBusinessListEntity;
import com.autowrite.service.AutowriterInterface;

@Component
public class IndividualService extends CommonService{

	@Autowired
	IndividualDao individualDao;

	@Autowired
	ContentsDao contentsDao;

	public IndividualService() {
	}


	public UserBusinessListEntity listBusinessInfo(Map param) {
		setCondition(param);
		
		UserBusinessListEntity listEntity = new UserBusinessListEntity();
		
		try {
			Long pageNum = (Long) param.get("PAGE_NUM");
			Long pageSize = (Long) param.get("PAGE_SIZE");
			listEntity.setPageNum(pageNum);
			listEntity.setPageSize(pageSize);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		Long boardCount = individualDao.countListBusinessInfo(param);
		listEntity.setTotalListCount(boardCount);
		
		List<UserBusinessEntity> businessList = individualDao.listBusinessInfo(param);
		
		listEntity.setBusinessList(businessList);
		
		return listEntity;
	}


	public UserBusinessListEntity writeBusinessInfo(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		String[] siteSeqidArray = req.getParameterValues("siteSeqIdList");
		
		individualDao.writeListBusinessInfo(param);
		
		return listBusinessInfo(param);
	}


	public void deleteBusinessInfo(HttpServletRequest req, Map param) {
		setCondition(param);
		
		individualDao.deleteBusinessInfo(param);
	}
	
	
	public UserBusinessEntity readBusinessInfo(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		return individualDao.readBusinessInfo(param);
	}
}
