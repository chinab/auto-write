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
import com.autowrite.common.framework.dao.ContentsDao;
import com.autowrite.common.framework.dao.ContentsDaoImpl;
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
public class ContentsService extends CommonService{

	@Autowired 
    AdminService adminService;
	
	@Autowired
	ContentsDao contentsDao;

	public ContentsService() {
	}

	
	/**
	 * 개인별 사이트 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BoardListEntity listPrivateContents(Map param) throws Exception {
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
		
		Long boardCount = contentsDao.countListPrivateContents(param);
		listEntity.setTotalListCount(boardCount);
		
		List<BoardEntity> boardList = contentsDao.listPrivateContents(param);
		
		listEntity.setBoardList(boardList);
		
		return listEntity;
	}


	public BoardListEntity writePrivateContents(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		contentsDao.writePrivateContents(param);
		
		return listPrivateContents(param);
	}
	
	
	
	public BoardListEntity modifyContents(HttpServletRequest req, Map param) {
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
//			contentsDao.modifyBoardAdmin(param);
//		} else {
//			contentsDao.modifyBoard(param);
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
//		contentsDao.boardUpdateDelYn(param);
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
//		BoardEntity boardEntity = contentsDao.readBoard(param);
//		
//		setWriterImagePath(param, boardEntity);
//		
//		return boardEntity;
//	}
}
