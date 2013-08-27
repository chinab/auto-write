package com.autowrite.common.framework.bean;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autowrite.common.config.Constant;
import com.autowrite.common.framework.dao.AutowriteDao;
import com.autowrite.common.framework.dao.ContentsDao;
import com.autowrite.common.framework.dao.SiteDao;
import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.AutowriteListEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.SiteParameterEntity;
import com.autowrite.service.AutowriterInterface;

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
		
		List<SiteEntity> siteEntityList = siteDao.listPrivateSite(param);
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
	 * 자동등록 마스터 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public AutowriteListEntity listAutowriteMaster(Map param) throws Exception {
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
		
		Long boardCount = autowriteDao.countListAutowriteMaster(param);
		listEntity.setTotalListCount(boardCount);
		
		List<AutowriteEntity> boardList = autowriteDao.listAutowriteMaster(param);
		
		listEntity.setAutowriteList(boardList);
		
		return listEntity;
	}


	public AutowriteListEntity listAutowriteSite(Map param) {
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
		
		Long boardCount = autowriteDao.countListAutowriteSite(param);
		listEntity.setTotalListCount(boardCount);
		
		List<AutowriteEntity> boardList = autowriteDao.listAutowriteSite(param);
		
		listEntity.setAutowriteList(boardList);
		
		return listEntity;
	}


	public AutowriteListEntity listAutowriteLog(Map param) {
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
		
		Long boardCount = autowriteDao.countListAutowriteLog(param);
		listEntity.setTotalListCount(boardCount);
		
		List<AutowriteEntity> boardList = autowriteDao.listAutowriteLog(param);
		
		listEntity.setAutowriteList(boardList);
		
		return listEntity;
	}


	public AutowriteListEntity writePrivateAutowrite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		String[] siteSeqidArray = req.getParameterValues("siteSeqIdList");
		
		Long autowriteMasterSeqId = autowriteDao.writeAutowriteMaster(param);
		param.put("AUTOWRITE_MASTER_SEQ_ID", autowriteMasterSeqId);
		
		for ( int ii = 0 ; ii < siteSeqidArray.length ; ii ++ ) {
			param.put("SITE_SEQ_ID", siteSeqidArray[ii]);
			
			executeHttpConnection(param);
			
			param.put("TRY_COUNT", 1);
			
			Long autowriteSiteSeqId = autowriteDao.writeAutowriteSite(param);
			
			param.put("AUTOWRITE_SITE_SEQ_ID", autowriteSiteSeqId);
			
			autowriteDao.writeAutowriteLog(param);
		}
		
		return listAutowriteMaster(param);
	}
	
	
	
	private void executeHttpConnection(Map param) {
		String successYn = "Y";
		String responseContent = "";
		
		AutowriteEntity autowriteInfo = new AutowriteEntity();
		
		autowriteInfo.setTitle(param.get("TITLE").toString());
		autowriteInfo.setContent(param.get("CONTENT").toString());
//		autowriteDao.getAutowriteInfo(param);
		
		SiteEntity siteInfo = siteDao.getAutowriteSiteInfo(param);
		
		if ( siteInfo == null ){
			successYn = "N";
			responseContent = "사이트 마스터 정보 가져오기 실패.";
		} else if ( siteInfo.getMaster_seq_id() == null ){
			successYn = "N";
			responseContent = "사이트 마스터 연결이 필요합니다.";
		} else {
			param.put("SITE_MASTER_SEQ_ID", siteInfo.getMaster_seq_id());
			List<SiteParameterEntity> siteParamList = siteDao.listSiteParameter(param);
			
			for ( int ii = 0 ; ii < siteParamList.size() ; ii ++ ) {
				SiteParameterEntity siteParam = siteParamList.get(ii);
				String mode = siteParam.getMode();
				String pKey = siteParam.getP_key();
				String pValue = siteParam.getP_value();
				
				if ( "LOGIN".equals(mode) ) {
					siteInfo.getLoginParam().put(pKey, pValue);
				} else if ( "WRITE".equals(mode) ) {
					siteInfo.getWriteParam().put(pKey, pValue);
				} else if ( "MODIFY".equals(mode) ) {
					siteInfo.getModifyParam().put(pKey, pValue);
				} else if ( "DELETE".equals(mode) ) {
					siteInfo.getDeleteParam().put(pKey, pValue);
				} else {
					successYn = "N";
					responseContent = "[" + mode + "] 모드는 설정되어 있지 않습니다.";
				}
				
			}
			
			autowriteInfo.setSiteEntity(siteInfo);
		}
		
		if ( "Y".equals(successYn) ) {
			String serviceClassName = siteInfo.getService_class_name();
			String serviceFullClassName = Constant.AUTOWRITE_SERVICE_PACKAGE + "." + serviceClassName;
			
			if ( serviceClassName == null ){
				successYn = "N";
				responseContent = siteInfo.getSite_name() + "사이트의 클래스 명을 설정해야 함.";
			}
			
			Class c;
			Object obj = null;
			
			try {
				c = Class.forName(serviceFullClassName);
				obj = c.newInstance();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				successYn = "N";
				responseContent = serviceFullClassName + " 클래스 없음.";
			} catch (InstantiationException e) {
				e.printStackTrace();
				successYn = "N";
				responseContent = serviceFullClassName + " 클래스 초기화 실패.";
			} catch (IllegalAccessException e) {
				successYn = "N";
				responseContent = serviceFullClassName + " 클래스 접근 실패.";
			}
			
			AutowriterInterface autowriter = (AutowriterInterface)obj;
			
			System.out.println("AUTOWRITE CLASS NAME : " + serviceFullClassName);
			
			try {
				autowriter.setCookie(autowriteInfo);
				
				if ( autowriter.login(autowriteInfo) ) {
					autowriter.writeBoard(autowriteInfo);
				} else {
					successYn = "N";
					responseContent = "로그인 실패.";
					// login 100회 반복.
//					for ( int ii = 0 ; ii < 100 ; ii ++ ) {
//						if ( loginJson(autowriteInfo) ){
//							writeBoard(autowriteInfo);
//							break;
//						}
//					}
				}
				
			} catch (Exception e) {
				successYn = "N";
				responseContent = e.getMessage();
				e.printStackTrace();
			}
		}
		
		param.put("SUCCESS_YN", successYn);
		param.put("RESPONSE_CONTENT", responseContent);
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
