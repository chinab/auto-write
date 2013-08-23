package com.autowrite.common.framework.bean;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.dao.SiteDao;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.BoardListEntity;
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


	public SiteListEntity listMasterSite(Map param) {
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


	public SiteListEntity writePrivateSite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		siteDao.writePrivateSite(param);
		
		return listPrivateSite(param);
	}
	
	
	
	public BoardListEntity modifySite(HttpServletRequest req, Map param) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
//	public BoardListEntity modifyBoard(HttpServletRequest req, Map param) throws Exception {
//		String menuCode = param.get("category").toString();
//		
//		String tableName = getTableNameAsMenuCode(menuCode);
//		
//		param.put("TABLE_NAME", tableName);
//		
//		setCondition(param);
//		
//		fileUpload(req, param);
//		
//		if ( menuCode.startsWith("02") || menuCode.startsWith("03") ){
//			siteDao.modifyBoardAdmin(param);
//		} else {
//			siteDao.modifyBoard(param);
//		}
//		
//		return listBoard(param);
//	}
//
//	public BoardListEntity deleteBoard(HttpServletRequest req, Map param) throws Exception {
//		String menuCode = param.get("category").toString();
//		
//		String tableName = getTableNameAsMenuCode(menuCode);
//		param.put("TABLE_NAME", tableName);
//		
//		param.put("DEL_YN", "Y");
//		
//		siteDao.boardUpdateDelYn(param);
//		
//		return listBoard(param);
//	}
//
//
//	public BoardEntity readBoard(Map param) throws Exception {
//		String menuCode = param.get("CATEGORY").toString();
//		
//		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
//		
//		BoardEntity boardEntity = siteDao.readBoard(param);
//		
//		setWriterImagePath(param, boardEntity);
//		
//		return boardEntity;
//	}
}
