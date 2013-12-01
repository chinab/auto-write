package com.autowrite.common.framework.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autowrite.common.config.Constant;
import com.autowrite.common.framework.dao.AutowriteDao;
import com.autowrite.common.framework.dao.ContentsDao;
import com.autowrite.common.framework.dao.IndividualDao;
import com.autowrite.common.framework.dao.SiteDao;
import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.AutowriteListEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.SiteParameterEntity;
import com.autowrite.common.framework.entity.UserBusinessEntity;
import com.autowrite.service.AutowriterInterface;

@Component
public class AutowriteService extends CommonService{

	@Autowired
	AutowriteDao autowriteDao;

	@Autowired
	SiteDao siteDao;

	@Autowired
	ContentsDao contentsDao;

	@Autowired
	IndividualDao individualDao;

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
		
		getSelectedContents(param, autowriteEntity);
		
		return autowriteEntity;
	}


	/**
	 * 본문 리스트 중 선택 된 본문을 AutowriteEntity에 세팅.
	 * @param param
	 * @param autowriteEntity
	 */
	private void getSelectedContents(Map param, AutowriteEntity autowriteEntity) {
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
	}

	
	/**
	 * 자동등록 예약 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public AutowriteListEntity listAutowriteReserve(Map param) throws Exception {
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
		
		Long autowriteCount = autowriteDao.countListAutowriteReserve(param);
		listEntity.setTotalListCount(autowriteCount);
		
		List<AutowriteEntity> autowriteList = autowriteDao.listAutowriteReserve(param);
		
		listEntity.setAutowriteList(autowriteList);
		
		return listEntity;
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
		
		Long autowriteCount = autowriteDao.countListAutowriteMaster(param);
		listEntity.setTotalListCount(autowriteCount);
		
		List<AutowriteEntity> autowriteList = autowriteDao.listAutowriteMaster(param);
		
		listEntity.setAutowriteList(autowriteList);
		
		return listEntity;
	}


	/**
	 * 자동등록 사이트 목록
	 * 
	 * @param param
	 * @return
	 */
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
		
		Long autowriteCount = autowriteDao.countListAutowriteSite(param);
		listEntity.setTotalListCount(autowriteCount);
		
		List<AutowriteEntity> autowriteList = autowriteDao.listAutowriteSite(param);
		
		listEntity.setAutowriteList(autowriteList);
		
		return listEntity;
	}


	/**
	 * 자동등록 사이트 로그 목록
	 * 
	 * @param param
	 * @return
	 */
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
		
		Long autowriteCount = autowriteDao.countListAutowriteLog(param);
		listEntity.setTotalListCount(autowriteCount);
		
		List<AutowriteEntity> autowriteList = autowriteDao.listAutowriteLog(param);
		
		listEntity.setAutowriteList(autowriteList);
		
		return listEntity;
	}


	/**
	 * autowrite 실행.
	 * @param req
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public AutowriteListEntity writePrivateAutowrite(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		String[] siteSeqidArray = req.getParameterValues("siteSeqIdList");
		
		siteLoop(param, siteSeqidArray);
		
		return listAutowriteMaster(param);
	}


	/**
	 * autowrite 예약 저장.
	 * @param req
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public AutowriteListEntity writeAutowriteReservation(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		String[] siteSeqidArray = req.getParameterValues("siteSeqIdList");
		
		String seqId = req.getParameter("seqId");
		
		if ( seqId != null && !"null".equals(seqId) && seqId.length() > 0 ){
			modifyAutowriteReservation(param, siteSeqidArray);
		} else {
			writeAutowriteReservation(param, siteSeqidArray);
		}
		
		return listAutowriteMaster(param);
	}


	/**
	 * autowrite 예약 사이트 저장.
	 * @param param
	 * @param siteSeqidArray
	 */
	private void writeAutowriteReservation(Map param, String[] siteSeqidArray) {
		Long autowriteMasterSeqId = autowriteDao.writeAutowriteReserveMaster(param);
		param.put("RESERVE_MASTER_SEQ_ID", autowriteMasterSeqId);
		
		writeAutowriteReservationSite(param, siteSeqidArray);
	}


	/**
	 * autowrite 예약 사이트 업데이트
	 * @param param
	 * @param siteSeqidArray
	 */
	private void modifyAutowriteReservation(Map param, String[] siteSeqidArray) {
		param.put("RESERVE_MASTER_SEQ_ID", param.get("SEQ_ID"));
		
		autowriteDao.modifyAutowriteReserveMaster(param);
		
		// 서브테이블은 삭제 후 insert
		autowriteDao.deleteAutowriteReserveSite(param);
		
		writeAutowriteReservationSite(param, siteSeqidArray);
	}
	
	
	private void writeAutowriteReservationSite(Map param, String[] siteSeqidArray) {
		for ( int ii = 0 ; ii < siteSeqidArray.length ; ii ++ ) {
			param.put("SITE_SEQ_ID", siteSeqidArray[ii]);
			
			Long autowriteSiteSeqId = autowriteDao.writeAutowriteReserveSite(param);
		}
	}
	
	
	/**
	 * autowrite 재실행 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public AutowriteListEntity writePrivateAutowrite(Map param) throws Exception {
		setCondition(param);
		
		List<String> siteSeqIdList = autowriteDao.getSiteSeqIdList(param);
		
		siteLoop(param, siteSeqIdList);
		
		return listAutowriteMaster(param);
	}


	/**
	 * 각 사이트별로 자동등록 실행.
	 * @param param
	 * @param siteSeqidArray
	 */
	private void siteLoop(Map param, String[] siteSeqidArray) {
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
	}
	
	
	/**
	 * 각 사이트별로 자동등록 실행.
	 * @param param
	 * @param siteSeqidArray
	 */
	private void siteLoop(Map param, List<String> siteSeqIdList) {
		Long autowriteMasterSeqId = autowriteDao.writeAutowriteMaster(param);
		param.put("AUTOWRITE_MASTER_SEQ_ID", autowriteMasterSeqId);
		
		for ( int ii = 0 ; ii < siteSeqIdList.size() ; ii ++ ) {
			param.put("SITE_SEQ_ID", siteSeqIdList.get(ii));
			
			executeHttpConnection(param);
			
			param.put("TRY_COUNT", 1);
			
			Long autowriteSiteSeqId = autowriteDao.writeAutowriteSite(param);
			
			param.put("AUTOWRITE_SITE_SEQ_ID", autowriteSiteSeqId);
			
			autowriteDao.writeAutowriteLog(param);
		}
	}
	
	
	/**
	 * HTTP CLIENT를 호출하고 기록을 남긴다.
	 * @param param
	 */
	private void executeHttpConnection(Map param) {
		String successYn = "Y";
		String responseContent = "";
		
		AutowriteEntity autowriteInfo = new AutowriteEntity();
		
		autowriteInfo.setTitle(param.get("TITLE").toString());
		autowriteInfo.setContent(param.get("CONTENT").toString());
//		autowriteDao.getAutowriteInfo(param);
		
		// 섹밤 업소정보 세팅.
		List<UserBusinessEntity> userBusinessInfo = individualDao.listBusinessInfo(param);
		autowriteInfo.setUserBusinessEntityList(userBusinessInfo);
		
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
			
			if ( serviceClassName == null || serviceClassName.trim().length() == 0 ){
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


	/**
	 * 재실행 시 seq_id로 기존에 저장 된 autowrite_master 테이블의 title, content를 가져옴.
	 * @param param
	 * @return
	 */
	public AutowriteEntity getRestoredAutowriteEntity(Map param) {
		setCondition(param);
		
		AutowriteEntity autowriteEntity = autowriteDao.getRestoredAutowrite(param);
		
		return autowriteEntity;
	}


	/**
	 * 자동등록 기록을 모두 삭제한다.
	 * @param req
	 * @param param
	 */
	public void deleteAutowrite(HttpServletRequest req, Map param) {
		setCondition(param);
		
		autowriteDao.deleteAutowriteMaster(param);
		
		autowriteDao.deleteAutowriteSite(param);
		
		autowriteDao.deleteAutowriteLog(param);
	}
	
	
	/**
	 * 자동등록 예약 목록을 모두 삭제한다.
	 * @param req
	 * @param param
	 */
	public void deleteReserveAutowrite(HttpServletRequest req, Map param) {
		setCondition(param);
		
		autowriteDao.deleteAutowriteReserveMaster(param);
		
		autowriteDao.deleteAutowriteReserveSite(param);
	}
	
	
	/**
	 * 예약 등록 내역을 읽어온다.
	 * 
	 * @param req
	 * @param param
	 * @return
	 */
	public AutowriteEntity getReservedAutowriteEntity(HttpServletRequest req, Map param) {
		String autowriteReserveSeqid = (String) param.get("RESERVE_MASTER_SEQ_ID");
		
		AutowriteEntity autowriteEntity = null;
		
		if ( autowriteReserveSeqid != null && autowriteReserveSeqid.length() > 0 ){
			autowriteEntity = autowriteDao.getReservedAutowriteEntity(param);
			List<SiteEntity> siteEntityFullList = siteDao.listPrivateSite(param);
			
			List<SiteEntity> reservedSiteEntityList = siteDao.listReservedSite(param);
			
			if ( reservedSiteEntityList.size() > 0 ) {
				SiteEntity reservedSiteEntity = null;
				SiteEntity fullSiteEntity = null;
				String fullSiteSeqId = null;
				String reservedSiteSeqId = null;
				
				for ( int ii = 0 ; ii < siteEntityFullList.size() ; ii ++) {
					fullSiteEntity = siteEntityFullList.get(ii);
					fullSiteEntity.setUse_yn("N");
					
					fullSiteSeqId = fullSiteEntity.getSeq_id();
					
					for ( int jj = 0 ; jj < reservedSiteEntityList.size() ; jj ++ ){
						reservedSiteEntity = reservedSiteEntityList.get(jj);
						reservedSiteSeqId = reservedSiteEntity.getSeq_id();
						
						if ( fullSiteSeqId.equals(reservedSiteSeqId) ){
							fullSiteEntity.setUse_yn("Y");
						}
					}
					
				}
			}
				
			autowriteEntity.setSiteEntityList(siteEntityFullList);
			
//			List<String> siteSeqIdList = autowriteDao.getReservedSiteSeqIdList(param);
//			autowriteEntity.setSite_seq_id_list(siteSeqIdList);
			
			getSelectedContents(param, autowriteEntity);
		} else {
			autowriteEntity = getDefaultAutowriteEntity(req, param);
		}
		
		return autowriteEntity;
	}


	public void executeReservedAutowrite() {
		Map param = new HashMap();
		
		List<AutowriteEntity> autowriteEntityList = autowriteDao.getEffectiveReservedAutowriteEntity();
		
		for ( int ii = 0 ; ii < autowriteEntityList.size() ; ii++ ) {
			AutowriteEntity autowriteEntity = autowriteEntityList.get(ii);
			int reserveRemainMinute = autowriteEntity.getReserve_remain_minute();
			
			param.put("RESERVE_MASTER_SEQ_ID", autowriteEntity.getSeq_id());
			
			if ( reserveRemainMinute == 0 ) {
				List<String> siteSeqIdList = autowriteDao.getEffectiveReservedSite(param);
				
				param.put("CONTENTS_SEQ_ID", autowriteEntity.getContents_seq_id());
				param.put("TITLE", autowriteEntity.getTitle());
				param.put("CONTENT", autowriteEntity.getContent());
				param.put("USER_SEQ_ID", autowriteEntity.getWriter_seq_id());
				param.put("WRITER_SEQ_ID", autowriteEntity.getWriter_seq_id());
				param.put("WRITER_ID", autowriteEntity.getWriter_id());
				param.put("WRITER_IP", "RESERVE_SYSTEM");
				
				siteLoop(param, siteSeqIdList);
				
				reserveRemainMinute = Integer.parseInt(autowriteEntity.getReserve_term()) - 1;
			} else {
				reserveRemainMinute--;
			}
			
			param.put("RESERVE_REMAIN_MINUTE", reserveRemainMinute);
			
			autowriteDao.updateReserveRemainMinute(param);
			
		}
	}
}
