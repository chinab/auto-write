package com.autowrite.common.framework.bean;

import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.UserClassEntity;
import com.autowrite.common.framework.entity.UserEntity;

@Component
public class LoginUserService {

	@Autowired 
    private ServletContext servletContext;

	@Autowired
	UserDao userDao;
	
	public LoginUserService() {
	}

	public UserEntity doLogin(Map param) throws Exception {
		UserEntity userEntity = null;
		
		try {
			userEntity = userDao.list(param);
		} catch ( SocketException se ) {
			se.printStackTrace();
			userEntity = userDao.list(param);
		}
		
		if ( userEntity != null ){
			param.put("USER_SEQ_ID", userEntity.getSeq_id());
			// login log
			userDao.insertLoginLog(param);
			
			Integer userPoint = new Integer(userEntity.getPoint());
			String userTypeCode = userEntity.getType_code();
				
			userEntity.setClassInfo(userPoint, userTypeCode);
			
			// get visit count
			int visitCountToday = userDao.getVisitCountToday(param);
			int visitCountYesterday = userDao.getVisitCountYesterday(param);
			int visitCountTotal = userDao.getVisitCountTotal(param);
			
			userEntity.setVisitCountToday(visitCountToday);
			userEntity.setVisitCountYesterday(visitCountYesterday);
			userEntity.setVisitCountTotal(visitCountTotal);
			
			// getQuickLink
			List quickLinkList = userDao.getQuickLink(param);
			Map<String, String> quickLinkMap = new HashMap<String, String>();
			Map resultMap;
			String quickLinkName;
			String quickLinkUrl;
			for ( int ii = 0 ; ii < quickLinkList.size() ; ii++ ){
				resultMap = (Map) quickLinkList.get(ii);
				quickLinkName = resultMap.get("QUICK_LINK_NAME").toString();
				quickLinkUrl = resultMap.get("QUICK_LINK_URL").toString();
				quickLinkMap.put(quickLinkName, quickLinkUrl);
			}
			userEntity.setQuickLinkMap(quickLinkMap);
		}
		
		return userEntity;
	}
	
	
	public UserEntity authenticate(Map param) throws Exception {
		UserEntity userEntity = null;
		try {
			userEntity = userDao.list(param);
		} catch ( SocketException se ) {
			se.printStackTrace();
			userEntity = userDao.list(param);
		}
		
		if ( userEntity != null ){
			param.put("USER_SEQ_ID", userEntity.getSeq_id());
			// login log
			userDao.insertLoginLog(param);
			
			List<UserClassEntity> userClassList = (List<UserClassEntity>) servletContext.getAttribute("UserClassList");
			if ( userClassList == null ){
				userClassList = userDao.selectUserClass();
				servletContext.setAttribute("UserClassList", userClassList);
			}
			
			userEntity.setUserClassList(userClassList);
			
			Integer userPoint = new Integer(userEntity.getPoint());
			String userTypeCode = userEntity.getType_code();
				
			userEntity.setClassInfo(userPoint, userTypeCode);
			
			// get memo count
			int uncheckedMemoCount = userDao.getUncheckedMemoCount(param);
			userEntity.setUncheckedMemoCount(uncheckedMemoCount);
			
			// get visit count
			int visitCountToday = userDao.getVisitCountToday(param);
			int visitCountYesterday = userDao.getVisitCountYesterday(param);
			int visitCountTotal = userDao.getVisitCountTotal(param);
			
			userEntity.setVisitCountToday(visitCountToday);
			userEntity.setVisitCountYesterday(visitCountYesterday);
			userEntity.setVisitCountTotal(visitCountTotal);
			
			// getQuickLink
			List quickLinkList = userDao.getQuickLink(param);
			Map<String, String> quickLinkMap = new HashMap<String, String>();
			Map resultMap;
			String quickLinkName;
			String quickLinkUrl;
			for ( int ii = 0 ; ii < quickLinkList.size() ; ii++ ){
				resultMap = (Map) quickLinkList.get(ii);
				quickLinkName = resultMap.get("QUICK_LINK_NAME").toString();
				quickLinkUrl = resultMap.get("QUICK_LINK_URL").toString();
				quickLinkMap.put(quickLinkName, quickLinkUrl);
			}
			userEntity.setQuickLinkMap(quickLinkMap);
		}
		
		return userEntity;
	}
	
	
	public List checkDuplicateId(Map param) throws Exception {
		return userDao.checkDuplicateId(param);
	}
	

	public void addQuickLink(Map param) {
		userDao.addQuickLink(param);
	}

	
	public int selectQuickLink(Map param) {
		return userDao.countQuickLink(param);
	}
}
