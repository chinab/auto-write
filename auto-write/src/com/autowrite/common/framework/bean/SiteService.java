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
public class SiteService {

	@Autowired 
    private ServletContext servletContext;

	@Autowired 
    AdminService adminService;
	
	@Autowired
	SiteDao siteDao;

	@Autowired 
	UserDao userDao;

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
	
	
	/**
	 * 어플리케이션 스코프의 메인화면 내용을 갱신.
	 * @param param
	 * @param tableName
	 * @throws Exception 
	 */
	private void setBoardMainEntityToContext(Map param, String tableName) throws Exception {
//		Map param2 = new HashMap();
//		param2.put("USER_SEQ_ID", param.get("USER_SEQ_ID"));
//		param2.put("MENU", param.get("MENU"));
//		BoardMainEntity boardMainEntity = listMainBoard(param2);
//		servletContext.setAttribute("BoardMainEntity", boardMainEntity);
//		
//		adminService.updateBannerContext();
	}
	

	private void setCondition(Map param) {
		long pageNum = 0;
		long pageSize = Constant.BOARD_PAGE_SIZE;
		int startNum;
		int endNum;
		
		if ( param.get("pageNum") != null && param.get("pageNum").toString().length() > 0) {
			pageNum = new Long(param.get("pageNum").toString());
			param.put("PAGE_NUM", pageNum);
		}
		
		if ( pageNum == 0 ){
			pageNum = 1;
		}
		
		startNum = (int) (( pageNum - 1 ) * pageSize);
		endNum = (int) (pageNum * pageSize);
		
		param.put("PAGE_NUM", pageNum);
		param.put("PAGE_SIZE", pageSize);
		param.put("START_NUM", startNum);
		param.put("END_NUM", endNum);
		
//		System.out.println("PAGE_NUM:"+pageNum);
//		System.out.println("PAGE_SIZE:"+pageSize);
//		System.out.println("START_NUM:"+startNum);
//		System.out.println("END_NUM:"+endNum);
	}
	
	
	
	public List<BoardEntity> getMyBoard(Map param){
		return siteDao.listBoardByUser(param);
	}
	
	
	public BoardListEntity writeBoard(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("category").toString();
		
		String tableName = getTableNameAsMenuCode(menuCode);
		
		param.put("TABLE_NAME", tableName);
		
		setCondition(param);
		
		// 업소정보, 라인업 - 이전 글 지움.
		if ( menuCode.startsWith("02") || menuCode.startsWith("03") ){
			siteDao.deleteRecentLineUp(param);
		}
		
		long seqId = siteDao.writeBoardWithFile(param);
		param.put("SEQ_ID", seqId);
		this.fileUpload(req, param);
		
		// 라인업 - 배너 컨텍스트 업데이트
		if ( menuCode.startsWith("03") ){
			adminService.updateBannerContext();
		}
		
		setBoardMainEntityToContext(param, tableName);
		
		return listBoard(param);
	}
	
	
	public BoardListEntity blackUserWrite(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("category").toString();
		
		param.put("TABLE_NAME", "T_BOARD_BLACK");
		
		setCondition(param);
		
		siteDao.blackUserWrite(param);
		
		return getBlackUserList(param);
	}
	
	
	public BoardListEntity modifyBoard(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("category").toString();
		
		String tableName = getTableNameAsMenuCode(menuCode);
		
		param.put("TABLE_NAME", tableName);
		
		setCondition(param);
		
		fileUpload(req, param);
		
		if ( menuCode.startsWith("02") || menuCode.startsWith("03") ){
			siteDao.modifyBoardAdmin(param);
		} else {
			siteDao.modifyBoard(param);
		}
		
		setBoardMainEntityToContext(param, tableName);
		
		return listBoard(param);
	}


	private void fileUpload(HttpServletRequest req, Map param) throws IOException {
		MultipartRequest multipartRequest = (MultipartRequest) req;
		
		Iterator files = multipartRequest.getFileNames();
		
		if ( files.hasNext() ){
			// office dev
//			String baseDir = "D:\\eGovFrameDev-2.0.1-FullVer\\workspace\\autowrite\\WebContent\\upload";
			
			// home dev
//			String baseDir = "C:\\dev\\junoWork\\autowrite\\WebContent\\upload";
			
			// server dev
//			String baseDir = "D:\\apache-tomcat-6.0.36\\webapps\\autowrite\\upload";
			
			String baseDir = (String) servletContext.getAttribute("UPLOAD_BASE_DIR");
			if ( baseDir == null ){
				baseDir = PropertyUtil.getProperty("UPLOAD_BASE_DIR");
				servletContext.setAttribute("UPLOAD_BASE_DIR", baseDir);
			}
			
			String webDir = req.getContextPath() + File.separator + "upload";
			String uploadDir = FileUtil.buildDatePathDir(baseDir);
			
			long seqId = 0;
			
			if (param.get("SEQ_ID") instanceof Long){
				seqId = (Long) param.get("SEQ_ID") ;
			} else if  (param.get("SEQ_ID") instanceof String) {
				seqId = new Long(param.get("SEQ_ID").toString());
			} else {
				seqId = (Long) param.get("SEQ_ID") ;
			}
			
			while ( files.hasNext() ) {
				String attachmentName = files.next().toString();
				
				MultipartFile multipartFile = multipartRequest.getFile(attachmentName);
				
				String originalFileName = multipartFile.getOriginalFilename();
				
				if ( originalFileName.length() > 0 ) {
					
					String targetFileName = "";
					targetFileName = System.currentTimeMillis()+"";
					File target = new File(uploadDir, targetFileName);
					
					String targetPath = uploadDir + File.separator + targetFileName;
				    String webPath = webDir + uploadDir.substring(baseDir.length(), uploadDir.length()) + File.separator + targetFileName;
				    String thumbnailPath = uploadDir + File.separator + "thumb_" + targetFileName + ".jpg";
				    String thumbnailWebPath = webDir + uploadDir.substring(baseDir.length(), uploadDir.length()) + File.separator + "thumb_" + targetFileName + ".jpg";
				    
				    multipartFile.transferTo(target);
				    
				    Thumbnailer Thumbnailer = new Thumbnailer();
					Thumbnailer.createImageThumbnail(targetPath, thumbnailPath, 100, 100);
					
				   
					param.put("TABLE_SEQ_ID", seqId);
					param.put("P_FILE_NAME", originalFileName);
					param.put("L_FILE_NAME", webPath);
					param.put("THUMBNAIL_FILE_NAME", thumbnailWebPath);
					param.put("FILE_EXT", "");
					param.put("FILE_SIZE", multipartFile.getSize());
					param.put("DEL_YN", "N");
					
					siteDao.writeAttachmentFile(param);
				}
			}
		}
	}

	public BoardListEntity deleteBoard(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("category").toString();
		
		String tableName = getTableNameAsMenuCode(menuCode);
		param.put("TABLE_NAME", tableName);
		
		param.put("DEL_YN", "Y");
		
		siteDao.boardUpdateDelYn(param);
		
		setBoardMainEntityToContext(param, tableName);
		
		return listBoard(param);
	}


	public BoardEntity readBoard(Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		BoardEntity boardEntity = siteDao.readBoard(param);
		
		setWriterImagePath(param, boardEntity);
		
		return boardEntity;
	}
	
	
	public BoardEntity readBoardWithDeletedUser(Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		return siteDao.readBoardWithDeletedUser(param);
	}
	
	
	public BoardEntity blackUserView(Map param) throws Exception {
		return siteDao.blackUserView(param);
	}
	
	
	public List<AttachmentEntity> readAttachment(Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		return siteDao.readAttachment(param);
	}
	
	
	public void writeReply(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		siteDao.writeReply(param);
	}
	
	
	public void modifyReply(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		siteDao.modifyReply(param);
	}
	
	
	public void deleteReply(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		siteDao.deleteReply(param);
	}
	
	
	public List<Map> readReply(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		List<Map> returnListMap = siteDao.readReply(param);
		
		setReplyUserClassImagePath(returnListMap);
		
		return returnListMap;
	}


	private void setReplyUserClassImagePath(List<Map> returnListMap) {
		Map replyMap = null;
		Integer userPoint = null;
		String userTypeCode = null;
		String userClassImgPath = null;
		List<UserClassEntity> userClassList = (List<UserClassEntity>) servletContext.getAttribute("UserClassList");
		if ( userClassList == null ){
			userClassList = userDao.selectUserClass();
			servletContext.setAttribute("UserClassList", userClassList);
		}
		
		for ( int ii = 0 ; ii < returnListMap.size() ; ii ++ ) {
			replyMap = returnListMap.get(ii);
			
			userPoint = (Integer)replyMap.get("USER_POINT");
			userTypeCode = (String)replyMap.get("USER_TYPE_CODE");
			
			userClassImgPath = getUserClassImagePath(userPoint, userTypeCode, userClassList);
			replyMap.put("USER_CLASS_IMG_PATH", userClassImgPath);
		}
	}
	
	public void updateHitCount(Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		siteDao.updateHitCount(param);
	}
	
	
	public void updateFeedbackCount(Map param) throws Exception {
		String category = param.get("CATEGORY").toString();
		String isRecommend = param.get("IS_RECOMMEND").toString();
		String isReject = param.get("IS_REJECT").toString();
		String countColumnName = "";
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(category));
		
		if ( "Y".equals(isRecommend) ){
			countColumnName = "IS_RECOMMEND";
		} else if ( "Y".equals(isReject) ){
			countColumnName = "IS_REJECT";
		} else {
			throw new Exception("UPDATE FEEDBACK Error.\n" + param.toString());
		}
		param.put("COUNT_COLUMN_NAME", countColumnName);
		
		int feedbackCount = siteDao.countFeedbackLog(param);
		
		if ( feedbackCount == 0 ){
			siteDao.insertFeedbackLog(param);
			
			if ( "Y".equals(isRecommend) ){
				siteDao.updateRecommendCount(param);
			} else if ( "Y".equals(isReject) ){
				siteDao.updateRejectCount(param);
			} else {
				throw new Exception("UPDATE FEEDBACK Error.\n" + param.toString());
			}
		} else {
			String errMsg;
			if ( "Y".equals(isRecommend) ){
				errMsg = "이미 추천 하셨습니다.";
			} else if ( "Y".equals(isReject) ){
				errMsg = "이미 비추 하셨습니다.";
			} else {
				errMsg = "UPDATE FEEDBACK COUNT Error.\n" + param.toString();
			}
			
			throw new Exception(errMsg);
		}
	}
	

	public List<String> getFacilityNamesAsRegion(Map param) {
		return siteDao.getFacilityNamesAsRegion(param);
	}


	public List<BoardEntity> listNotice(Map param) {
		param.put("CATEGORY", param.get("category"));
		
		List<BoardEntity> noticeList = siteDao.listNotice(param);
		setWriterImagePath(param, noticeList);
		
		return noticeList;
	}

}
