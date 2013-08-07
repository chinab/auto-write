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
import com.autowrite.common.framework.dao.BoardDao;
import com.autowrite.common.framework.dao.BoardDaoImpl;
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
public class BoardService {

	@Autowired 
    private ServletContext servletContext;

	@Autowired 
    AdminService adminService;
	
	@Autowired
	BoardDao boardDao;

	@Autowired 
	UserDao userDao;

	public BoardService() {
	}

	
	/**
	 * 단일 게시판 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BoardListEntity listBoard(Map param) throws Exception {
		String category = "";
		String region = "";
		String facilityName = "";
		
		if ( param.get("category") != null ){
			category = param.get("category").toString();
		} else {
			throw new Exception("Menu code is not selected.");
		}
		param.put("CATEGORY", category);
		
		if ( param.get("region") != null ){
			region = (String) param.get("region");
			if ( !"전지역".equals(region) ) {
				param.put("REGION", region);
			}
		}
		
		if ( param.get("facilityName") != null ){
			facilityName = (String) param.get("facilityName");
			if ( facilityName.trim().length() > 0 ) {
				param.put("FACILITY_NAME", facilityName);
			}
		}
		
		String tableName = getTableNameAsMenuCode(category);
		param.put("TABLE_NAME", tableName);
		
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
		
		Long boardCount = boardDao.countListBoard(param);
		listEntity.setTotalListCount(boardCount);
		
		
		List<BoardEntity> boardList = boardDao.listBoard(param);
		setWriterImagePath(param, boardList);
		
		for ( int ii = 0 ; ii < boardList.size() ; ii ++ ){
			BoardEntity boardEntity = boardList.get(ii);
			param.put("TABLE_SEQ_ID", boardEntity.getSeq_id());
			List<AttachmentEntity> attachmentList = boardDao.readAttachment(param);
			boardEntity.setAttachmentList(attachmentList);
		}
		
		listEntity.setBoardList(boardList);
		
//		listEntity.getPagination("listBoard", "020100", "", listEntity.getPageNum());
		return listEntity;
	}


	private void setWriterImagePath(Map param, List<BoardEntity> boardList) {
		BoardEntity boardEntity;
		
		for ( int ii = 0 ; ii < boardList.size() ; ii ++ ){
			boardEntity = boardList.get(ii);
			setWriterImagePath(param, boardEntity);
		}
	}
	
	
	private void setWriterImagePath(Map param, BoardEntity boardEntity) {
		String writerImagePath;
		
		List<UserClassEntity> userClassList = (List<UserClassEntity>) servletContext.getAttribute("UserClassList");
		if ( userClassList == null ){
			userClassList = userDao.selectUserClass();
			servletContext.setAttribute("UserClassList", userClassList);
		}
		
		writerImagePath = getUserClassImagePath(boardEntity, userClassList);
		boardEntity.setWriter_image_path(writerImagePath);
	}
	
	
	private String getUserClassImagePath(BoardEntity boardEntity, List<UserClassEntity> userClassList) {
		Integer userPoint = new Integer(boardEntity.getWriter_point());
		String userTypeCode = boardEntity.getWriter_type_code();
		
		return getUserClassImagePath(userPoint, userTypeCode, userClassList);
	}
	
	
	private String getUserClassImagePath(Integer userPoint, String userTypeCode, List<UserClassEntity> userClassList) {
		int classPoint;
		UserClassEntity userClassEntity;
		
		if ( "A".equals(userTypeCode) ){
			return "icon_admin_level_all.gif";
		} else if ( "M".equals(userTypeCode) ){
			return "icon_master_level_all.gif";
		} else if ( "B".equals(userTypeCode) ){
			return "icon_business_level_all.gif";
		}
		
		int listSize = userClassList.size();
		String classImgPath = "";
		
		for ( int ii = 0 ; ii < listSize ; ii ++ ) {
			userClassEntity = userClassList.get(ii);
			if ( (userClassEntity.getClass_type_code()).equals(userTypeCode) ){
				classPoint = new Integer(userClassEntity.getClass_point());
				classImgPath = userClassEntity.getClass_img_path();
				if ( userPoint <= classPoint || ii == listSize - 1 ){
					return classImgPath;
				}
			}
		}
		return "";
	}
	
	
	/**
	 * 어플리케이션 스코프의 메인화면 내용을 갱신.
	 * @param param
	 * @param tableName
	 * @throws Exception 
	 */
	private void setBoardMainEntityToContext(Map param, String tableName) throws Exception {
		Map param2 = new HashMap();
		param2.put("USER_SEQ_ID", param.get("USER_SEQ_ID"));
		param2.put("MENU", param.get("MENU"));
		BoardMainEntity boardMainEntity = listMainBoard(param2);
		servletContext.setAttribute("BoardMainEntity", boardMainEntity);
		
		adminService.updateBannerContext();
		
//		List<BoardEntity> list = boardDao.listBoardByHitCount(param);
//		boardMainEntity.setList(tableName, list, "HIT");
//		list = boardDao.listBoardByNew(param);
//		boardMainEntity.setList(tableName, list, "NEW");
	}
	
	
	/**
	 * 메뉴코드로 테이블명을 가져온다.
	 * @param selectedMenu
	 * @return
	 * @throws Exception 
	 */
	private String getTableNameAsMenuCode(String selectedMenu) throws Exception {
		// TODO : DB화 시킬것
		if ( selectedMenu.startsWith("01") ) {
			return "T_BOARD_NOTICE";
		} else if ( selectedMenu.startsWith("02") ) {
			return "T_BOARD_BUSINESS_INFO";
		} else if ( selectedMenu.startsWith("03") ) {
			return "T_BOARD_LINE_UP";
		} else if ( selectedMenu.startsWith("04") ) {
			return "T_BOARD_POSTSCRIPT";
		} else if ( selectedMenu.startsWith("05") ) {
			return "T_BOARD_COMMUNITY";
		} else if ( selectedMenu.startsWith("08") ) {
			return "T_BOARD_CENTER";
		} else {
			throw new Exception ("No such menu code start with [" + selectedMenu + "]");
		}
	}


	public BoardListEntity getBlackUserList(Map param) throws Exception {
		String selectedMenu = "";
		
		if ( param.get("category") != null ){
			selectedMenu = param.get("category").toString();
		} else {
			throw new Exception("Menu code is not selected.");
		}
		param.put("CATEGORY", selectedMenu);
		
		param.put("TABLE_NAME", "T_BOARD_BLACK");
		
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
		
		Long boardCount = boardDao.countListBoard(param);
		listEntity.setTotalListCount(boardCount);
		
		List<BoardEntity> boardList = boardDao.listBoard(param);
		listEntity.setBoardList(boardList);
		
		return listEntity;
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
	
	
	/**
	 * 각 카테고리 별 메인 게시판 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<BoardEntity>> listCategoryBoard(Map param) throws Exception {
		String selectedMenu = "";
		
		if ( param.get("selectedMenu") != null ){
			selectedMenu = param.get("selectedMenu").toString();
		}
		
		Map<String, List<BoardEntity>> returnMap = new HashMap<String, List<BoardEntity>>();
		
		String tableName = getTableNameAsMenuCode(selectedMenu);
		
		param.put("TABLE_NAME", tableName);
		param.put("MENU_CODE", selectedMenu);
		
		setCondition(param);
		
//		List<Map> categoryList = boardDao.getCategoryList(param);
//		String category = "test";
//		param.put("TABLE_NAME", tableName);
//		for ( int ii = 0 ; ii < categoryList.size() ; ii ++ ) {
//			category = (String) categoryList.get(ii).get("MENU_ID");
//			param.put("CATEGORY", category);
//			List<BoardEntity> list = boardDao.listBoardByRecommendCount(param);
//			returnMap.put(category,  list);
//		}
		
		List menus = (List) param.get("MENU");
		List categoryList = null;
		
		for ( int ii = 0 ; ii < menus.size() ; ii ++ ) {
			MenuDto menu = (MenuDto) menus.get(ii);
			if ( menu.getMenuid().equals(selectedMenu) ) {
				categoryList = menu.getSubmenus();
				break;
			}
		}
		
		String category = "";
		for ( int ii = 0 ; ii < categoryList.size() ; ii ++ ) {
			MenuDto menu = (MenuDto) categoryList.get(ii);
			category = menu.getMenuid();
			param.put("CATEGORY", category);
			if ( "080100".equals(category) ) {
				param.put("TABLE_NAME", "T_BOARD_BLACK");
			} else {
				param.put("TABLE_NAME", tableName);
			}
			List<BoardEntity> list = boardDao.listBoardByNew(param);
			returnMap.put(category,  list);
		}
		
		
		return returnMap;
	}
	
	
	/**
	 * 메인화면 게시판 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BoardMainEntity listMainBoard(Map param) throws Exception {
		Map<String, List<BoardEntity>> returnMap = new HashMap<String, List<BoardEntity>>();
		
		List<String> tableList = new ArrayList<String>();
		tableList.add("T_BOARD_NOTICE");
		tableList.add("T_BOARD_BUSINESS_INFO");
		tableList.add("T_BOARD_LINE_UP");
		tableList.add("T_BOARD_POSTSCRIPT");
		tableList.add("T_BOARD_COMMUNITY");
		tableList.add("T_BOARD_CENTER");
		
		param.put("START_NUM", 0);
		param.put("PAGE_SIZE", 7);
		
		BoardMainEntity boardMainEntity = new BoardMainEntity();
		String tableName;
		List<BoardEntity> list;
		
		for ( int ii = 0 ; ii < tableList.size() ; ii ++ ) {
			tableName = tableList.get(ii);
			param.put("TABLE_NAME", tableName);
//			list = boardDao.listBoardByHitCount(param);
//			boardMainEntity.setList(tableName, list, "HIT");
			list = boardDao.listBoardByRecommendCount(param);
			boardMainEntity.setList(tableName, list, "RECOMMEND");
			list = boardDao.listBoardByNew(param);
			boardMainEntity.setList(tableName, list, "NEW");
		}
		
//		List<BoardEntity> myBoardlist = boardDao.listBoardByUser(param);
//		boardMainEntity.setMyBoard(myBoardlist);
		
		// event
		
		
		// new businessInfo
		param.put("selectedMenu", "020000");
		List<BoardEntity> allBusinessInfo = boardDao.listAllBusinessInfo(param);
		Map<String, List<BoardEntity>> allCategoriedBusinessInfo = new HashMap();
		for ( int ii = 0 ; ii < allBusinessInfo.size() ; ii ++ ){
			BoardEntity boardEntity = allBusinessInfo.get(ii);
			List<BoardEntity> categorylist = allCategoriedBusinessInfo.get(boardEntity.getCategory());
			// 맵에 해당 카테고리 리스트가 없으면
			if ( categorylist == null ){
				categorylist = new ArrayList<BoardEntity>();
			}
			categorylist.add(boardEntity);
			allCategoriedBusinessInfo.put(boardEntity.getCategory(), categorylist);
		}
		boardMainEntity.setAllCategoriedBusinessInfo(allCategoriedBusinessInfo);
		
		// recommended facility info
		param.put("selectedMenu", "020000");
		param.put("category", "020000");
		List<PaymentMasterEntity> recommendedBusinessInfo = boardDao.listRecommendedBusinessInfo(param);
		boardMainEntity.setRecommendedBusinessInfo(recommendedBusinessInfo);
		if ( recommendedBusinessInfo != null && recommendedBusinessInfo.size() > 0 ){
			PaymentMasterEntity paymentMasterEntity;
			
			List<BoardEntity> recentLineUpList;
			List<BoardEntity> recentPostscriptList;
			
			for ( int ii = 0 ; ii < recommendedBusinessInfo.size() ; ii ++ ){
				paymentMasterEntity = recommendedBusinessInfo.get(ii);
				
				Map<String, Object> subParam = new HashMap();
				// for lineup
				subParam.put("START_NUM", 0);
				subParam.put("PAGE_SIZE", 1);
				subParam.put("USER_SEQ_ID", paymentMasterEntity.getUser_seq_id());
				// for postscript
				subParam.put("START_NUM", 0);
				subParam.put("PAGE_SIZE", 6);
				subParam.put("FACILITY_NAME", paymentMasterEntity.getFacility_name());
				
				recentLineUpList = boardDao.getRecentLineUpList(subParam);
				recentPostscriptList = boardDao.getRecentPostscriptList(subParam);
				paymentMasterEntity.setRecentLineUpList(recentLineUpList);
				paymentMasterEntity.setRecentPostscriptList(recentPostscriptList);
			}
		}
		
		
		// business info by category
		param.put("selectedMenu", "020000");
		param.put("category", "020000");
		Map<String, List<BoardEntity>> categoriedBusinessInfo = listCategoryBoard(param);
		boardMainEntity.setCategoriedBusinessInfo(categoriedBusinessInfo);
		
		// lineup by category
		param.put("selectedMenu", "030000");
		param.put("category", "030000");
		Map<String, List<BoardEntity>> categoriedLineUp = listCategoryBoard(param);
		boardMainEntity.setCategoriedLineUp(categoriedLineUp);
		
		// postscript by category
		param.put("selectedMenu", "040000");
		param.put("category", "040000");
		Map<String, List<BoardEntity>> categoriedPostscript = listCategoryBoard(param);
		boardMainEntity.setCategoriedPostscript(categoriedPostscript);
		
		return boardMainEntity;
	}
	
	public List<BoardEntity> getMyBoard(Map param){
		return boardDao.listBoardByUser(param);
	}
	
	
	public BoardListEntity writeBoard(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("category").toString();
		
		String tableName = getTableNameAsMenuCode(menuCode);
		
		param.put("TABLE_NAME", tableName);
		
		setCondition(param);
		
		// 업소정보, 라인업 - 이전 글 지움.
		if ( menuCode.startsWith("02") || menuCode.startsWith("03") ){
			boardDao.deleteRecentLineUp(param);
		}
		
		long seqId = boardDao.writeBoardWithFile(param);
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
		
		boardDao.blackUserWrite(param);
		
		return getBlackUserList(param);
	}
	
	
	public BoardListEntity modifyBoard(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("category").toString();
		
		String tableName = getTableNameAsMenuCode(menuCode);
		
		param.put("TABLE_NAME", tableName);
		
		setCondition(param);
		
		fileUpload(req, param);
		
		if ( menuCode.startsWith("02") || menuCode.startsWith("03") ){
			boardDao.modifyBoardAdmin(param);
		} else {
			boardDao.modifyBoard(param);
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
					
					boardDao.writeAttachmentFile(param);
				}
			}
		}
	}

	public BoardListEntity deleteBoard(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("category").toString();
		
		String tableName = getTableNameAsMenuCode(menuCode);
		param.put("TABLE_NAME", tableName);
		
		param.put("DEL_YN", "Y");
		
		boardDao.boardUpdateDelYn(param);
		
		setBoardMainEntityToContext(param, tableName);
		
		return listBoard(param);
	}


	public BoardEntity readBoard(Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		BoardEntity boardEntity = boardDao.readBoard(param);
		
		setWriterImagePath(param, boardEntity);
		
		return boardEntity;
	}
	
	
	public BoardEntity readBoardWithDeletedUser(Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		return boardDao.readBoardWithDeletedUser(param);
	}
	
	
	public BoardEntity blackUserView(Map param) throws Exception {
		return boardDao.blackUserView(param);
	}
	
	
	public List<AttachmentEntity> readAttachment(Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		return boardDao.readAttachment(param);
	}
	
	
	public void writeReply(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		boardDao.writeReply(param);
	}
	
	
	public void modifyReply(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		boardDao.modifyReply(param);
	}
	
	
	public void deleteReply(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		boardDao.deleteReply(param);
	}
	
	
	public List<Map> readReply(HttpServletRequest req, Map param) throws Exception {
		String menuCode = param.get("CATEGORY").toString();
		
		param.put("TABLE_NAME", getTableNameAsMenuCode(menuCode));
		
		List<Map> returnListMap = boardDao.readReply(param);
		
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
		
		boardDao.updateHitCount(param);
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
		
		int feedbackCount = boardDao.countFeedbackLog(param);
		
		if ( feedbackCount == 0 ){
			boardDao.insertFeedbackLog(param);
			
			if ( "Y".equals(isRecommend) ){
				boardDao.updateRecommendCount(param);
			} else if ( "Y".equals(isReject) ){
				boardDao.updateRejectCount(param);
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
		return boardDao.getFacilityNamesAsRegion(param);
	}


	public List<BoardEntity> listNotice(Map param) {
		param.put("CATEGORY", param.get("category"));
		
		List<BoardEntity> noticeList = boardDao.listNotice(param);
		setWriterImagePath(param, noticeList);
		
		return noticeList;
	}

}
