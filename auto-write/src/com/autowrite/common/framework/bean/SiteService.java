package com.autowrite.common.framework.bean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.autowrite.common.config.Constant;
import com.autowrite.common.framework.dao.SiteDao;
import com.autowrite.common.framework.dao.SiteDaoImpl;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.AttachmentEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.BoardListEntity;
import com.autowrite.common.framework.entity.BoardMainEntity;
import com.autowrite.common.framework.entity.MenuDto;
import com.autowrite.common.framework.entity.PaymentMasterEntity;
import com.autowrite.common.framework.entity.UserClassEntity;
import com.autowrite.common.util.FileUtil;
import com.autowrite.common.util.PropertyUtil;
import com.autowrite.common.util.Thumbnailer;

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
	public BoardListEntity listPrivateSite(Map param) throws Exception {
		setCondition(param);
		
		BoardListEntity listEntity = new BoardListEntity();
		
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
		
		List<BoardEntity> boardList = siteDao.listPrivateSite(param);
		
		listEntity.setBoardList(boardList);
		
		return listEntity;
	}
	
	
//	public BoardListEntity writeBoard(HttpServletRequest req, Map param) throws Exception {
//		String menuCode = param.get("category").toString();
//		
//		String tableName = getTableNameAsMenuCode(menuCode);
//		
//		param.put("TABLE_NAME", tableName);
//		
//		setCondition(param);
//		
//		// 업소정보, 라인업 - 이전 글 지움.
//		if ( menuCode.startsWith("02") || menuCode.startsWith("03") ){
//			siteDao.deleteRecentLineUp(param);
//		}
//		
//		long seqId = siteDao.writeBoardWithFile(param);
//		param.put("SEQ_ID", seqId);
//		this.fileUpload(req, param);
//		
//		// 라인업 - 배너 컨텍스트 업데이트
//		if ( menuCode.startsWith("03") ){
//			adminService.updateBannerContext();
//		}
//		
//		return listBoard(param);
//	}
//	
//	
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
