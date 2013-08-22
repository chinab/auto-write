package com.autowrite.common.framework.bean;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.dao.AutowriteDao;
import com.autowrite.common.framework.dao.ContentsDao;
import com.autowrite.common.framework.dao.SiteDao;
import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.AutowriteListEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.service.Autowriter;

@Component
public class AutowriteService extends CommonService{

	@Autowired
	AutowriteDao autowriteDao;

	@Autowired
	SiteDao siteDao;

	@Autowired
	ContentsDao contentsDao;

	public AutowriteService() {
	}


	/**
	 * 자동등록 쓰기 폼 로딩 시 초기 본문 및 사이트 리스트 세팅.
	 * @param req
	 * @param param
	 * @return
	 */
	public AutowriteEntity getDefaultAutowriteEntity(HttpServletRequest req, Map param) {
		setCondition(param);
		
		AutowriteEntity autowriteEntity = new AutowriteEntity();
		
		List<BoardEntity> siteEntityList = siteDao.listPrivateSite(param);
		autowriteEntity.setSiteEntityList(siteEntityList);
		
		List<BoardEntity> contentsEntityList = contentsDao.listPrivateContents(param);
		autowriteEntity.setContentsEntityList(contentsEntityList);
		
		if ( param.get("CONTENTS_SEQ_ID") != null ) {
			String selectedContentsKey = param.get("CONTENTS_SEQ_ID").toString();
			
			for ( int ii = 0 ; ii < contentsEntityList.size() ; ii ++ ) {
				BoardEntity contentsEntity = contentsEntityList.get(ii);
				if ( selectedContentsKey.equals(contentsEntity.getSeq_id()) ){
					autowriteEntity.setSelectedContentsEntity(contentsEntity);
				}
			}
		} else {
			if ( contentsEntityList.size() > 0 ) {
				autowriteEntity.setSelectedContentsEntity(contentsEntityList.get(0));
			}
		}
		
		return autowriteEntity;
	}

	
	/**
	 * 개인별 사이트 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public AutowriteListEntity listAutowrite(Map param) throws Exception {
		setCondition(param);
		
		AutowriteListEntity listEntity = new AutowriteListEntity();
		
		try {
			Long pageNum = (Long) param.get("PAGE_NUM");
			Long pageSize = (Long) param.get("PAGE_SIZE");
			listEntity.setPageNum(pageNum);
			listEntity.setPageSize(pageSize);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		Long boardCount = autowriteDao.countListAutowrite(param);
		listEntity.setTotalListCount(boardCount);
		
		List<AutowriteEntity> boardList = autowriteDao.listAutowrite(param);
		
		listEntity.setAutowriteList(boardList);
		
		return listEntity;
	}


	public AutowriteEntity autowriteView(Map param) {
		AutowriteEntity boardEntity = autowriteDao.autowriteView(param);
		
		return boardEntity;
	}


	public AutowriteListEntity writePrivateAutowrite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		String[] siteSeqidArray = req.getParameterValues("siteSeqIdList");
		
		Long masterSeqId = autowriteDao.writeAutowriteMaster(param);
		param.put("AUTOWRITE_MASTER_SEQ_ID", masterSeqId);
		
		for ( int ii = 0 ; ii < siteSeqidArray.length ; ii ++ ) {
			param.put("SITE_SEQ_ID", siteSeqidArray[ii]);
			
			executeHttpConnection(param);
			
			param.put("TRY_COUNT", 1);
			
			autowriteDao.writeAutowriteSite(param);
		}
		
		return listAutowrite(param);
	}
	
	
	
	private Object executeHttpConnection(Map param) {
		// TODO : 사이트 정보 가져오기.
		BoardEntity siteInfo = null;
//		siteInfo = (BoardEntity) siteDao.listMasterSite(param);
		
		String successYn = "Y";
		String responseContent = "";
		
		Autowriter autowriter = new Autowriter();
		try {
			autowriter.executeHttpConnection(siteInfo);
		} catch (Exception e) {
			successYn = "N";
			responseContent = e.getMessage();
			e.printStackTrace();
		}
		
		param.put("SUCCESS_YN", successYn);
		param.put("RESPONSE_CONTENT", responseContent);
		
		return null;
	}


	public AutowriteListEntity modifyAutowrite(HttpServletRequest req, Map param) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
//	public AutowriteListEntity modifyAutowrite(HttpServletRequest req, Map param) throws Exception {
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
//			autowriteDao.modifyAutowriteAdmin(param);
//		} else {
//			autowriteDao.modifyAutowrite(param);
//		}
//		
//		return listAutowrite(param);
//	}
//
//	public AutowriteListEntity deleteAutowrite(HttpServletRequest req, Map param) throws Exception {
//		String menuCode = param.get("category").toString();
//		
//		String tableName = getTableNameAsMenuCode(menuCode);
//		param.put("TABLE_NAME", tableName);
//		
//		param.put("DEL_YN", "Y");
//		
//		autowriteDao.boardUpdateDelYn(param);
//		
//		return listAutowrite(param);
//	}
//
//
//	public AutowriteEntity readAutowrite(Map param) throws Exception {
//		String menuCode = param.get("CATEGORY").toString();
//		
//		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
//		
//		AutowriteEntity boardEntity = autowriteDao.readAutowrite(param);
//		
//		setWriterImagePath(param, boardEntity);
//		
//		return boardEntity;
//	}
}
