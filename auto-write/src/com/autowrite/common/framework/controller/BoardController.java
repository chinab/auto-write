package com.autowrite.common.framework.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.autowrite.common.config.Constant;
import com.autowrite.common.framework.bean.AdminService;
import com.autowrite.common.framework.bean.BoardService;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.AttachmentEntity;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.BoardListEntity;
import com.autowrite.common.framework.entity.BoardMainEntity;
import com.autowrite.common.framework.entity.ConditionEntity;
import com.autowrite.common.framework.entity.UserEntity;

@Controller
public class BoardController implements Controller {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired 
    private ServletContext servletContext;

	private Logger logger;
	
	public BoardController() {
		logger = Logger.getLogger(getClass());
	}
	
	@RequestMapping("/writeBoard.do")
	public ModelAndView doWriteBoard(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		// System.out.println("writeBoard.do");
//		ModelAndView model = new ModelAndView("board/board");
		String category = req.getParameter("category");
//		if ( "050300".equals(category) ){
//			model = new ModelAndView("board/imageBoardList");
//		}
		
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setBoardParameter(req, httpSession, null, param);
		
		String secretYn = "N";
		if ( req.getParameter("secret_yn") != null ){
			secretYn = req.getParameter("secret_yn").toString();
		}
		
		if ( userInfo != null ) {
			if ( (category.startsWith("02") || category.startsWith("03")) && "M".equals(userInfo.getType_code()) ){
				param.put("USER_SEQ_ID",  req.getParameter("user_seq_id"));
				param.put("USER_ID",  req.getParameter("user_id"));
			} else {
				param.put("USER_SEQ_ID", userInfo.getSeq_id());
				param.put("USER_ID", userInfo.getId());
			}
			param.put("TITLE", req.getParameter("title"));
			param.put("FACILITY_NAME", req.getParameter("facility_name"));
			param.put("FACILITY_USER_NAME", req.getParameter("facility_user_name"));
			param.put("FACILITY_PHONE", req.getParameter("facility_phone"));
			param.put("FACILITY_ADDRESS", req.getParameter("facility_address"));
			param.put("FACILITY_HOME_PAGE", req.getParameter("facility_home_page"));
			param.put("VISIT_TIME", req.getParameter("visit_time"));
			param.put("WAITERESS_NAME", req.getParameter("waiteress_name"));
			param.put("CONTENT", req.getParameter("content").getBytes("UTF-8"));
			param.put("CATEGORY", category);
			param.put("TARGET_CATEGORY", req.getParameter("target_category"));
			param.put("REGION", req.getParameter("region"));
			param.put("HIT", 0);
			param.put("RECOMMEND", 0);
			param.put("REJECT", 0);
			param.put("SECRET_YN", secretYn);
			param.put("BLIND_YN", "N");
			param.put("DEL_YN", "N");
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		param.put("category", category);
		BoardListEntity boardListEntity = boardService.writeBoard(req, param);
		List<BoardEntity> boardList = boardListEntity.getBoardList();
		
//		model.addObject("BoardList", boardList);
//		model.addObject("category", req.getParameter("category"));
//		model.addObject("BoardListEntity", boardListEntity);
		
//		RedirectView rv = new RedirectView("boardListView.do");
//		rv.setExposeModelAttributes(true);
//		rv.setAttributesMap(model.getModelMap());
		
		String redirectUrl = "boardListView.do?jsp=board/board&category=" + category;
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/blackUserWrite.do")
	public ModelAndView blackUserWrite(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		// System.out.println("blackUserWrite.do");
		
		ModelAndView model = new ModelAndView("board/board");
		
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setBoardParameter(req, httpSession, model, param);
		
		String category = req.getParameter("category");
		
		if ( userInfo != null ) {
			param.put("USER_SEQ_ID", userInfo.getSeq_id());
			param.put("USER_ID", userInfo.getId());
			param.put("TITLE", req.getParameter("title"));
			param.put("BLACK_USER_NIC", req.getParameter("black_user_nic"));
			param.put("BLACK_USER_PHONE", req.getParameter("black_user_phone"));
			param.put("BLACK_USER_PHONE1", req.getParameter("black_user_phone1"));
			param.put("BLACK_USER_PHONE2", req.getParameter("black_user_phone2"));
			param.put("BLACK_USER_PHONE3", req.getParameter("black_user_phone3"));
			param.put("CONTENT", req.getParameter("content"));
			param.put("CATEGORY", category);
			param.put("TARGET_CATEGORY", req.getParameter("target_category"));
			param.put("REGION", req.getParameter("region"));
			param.put("HIT", 0);
			param.put("RECOMMEND", 0);
			param.put("REJECT", 0);
			param.put("SECRET_YN", "N");
			param.put("BLIND_YN", "N");
			param.put("DEL_YN", "N");
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		param.put("category", category);
		BoardListEntity boardListEntity = boardService.blackUserWrite(req, param);
		List<BoardEntity> boardList = boardListEntity.getBoardList();
		
		model.addObject("BoardList", boardList);
		model.addObject("category", req.getParameter("category"));
		model.addObject("BoardListEntity", boardListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/boardModify.do")
	public ModelAndView doBoardModify(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		// System.out.println("boardModify.do");
		
//		ModelAndView model = new ModelAndView("board/board");
		String category = req.getParameter("category");
//		if ( "050300".equals(category) ){
//			model = new ModelAndView("board/imageBoardList");
//		}
		
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		String secretYn = "N";
		if ( req.getParameter("secret_yn") != null ){
			secretYn = req.getParameter("secret_yn").toString();
		}
		
		setBoardParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
			if ( (category.startsWith("02")||category.startsWith("03")) && "M".equals(userInfo.getType_code()) ){
				param.put("USER_SEQ_ID",  req.getParameter("user_seq_id"));
				param.put("USER_ID",  req.getParameter("user_id"));
			}
			param.put("TITLE", req.getParameter("title"));
			param.put("FACILITY_NAME", req.getParameter("facility_name"));
			param.put("FACILITY_USER_NAME", req.getParameter("facility_user_name"));
			param.put("FACILITY_PHONE", req.getParameter("facility_phone"));
			param.put("FACILITY_ADDRESS", req.getParameter("facility_address"));
			param.put("FACILITY_HOME_PAGE", req.getParameter("facility_home_page"));
			param.put("VISIT_TIME", req.getParameter("visit_time"));
			param.put("WAITERESS_NAME", req.getParameter("waiteress_name"));
			param.put("CONTENT", req.getParameter("content").getBytes("UTF-8"));
			param.put("CATEGORY", category);
			param.put("TARGET_CATEGORY", req.getParameter("target_category"));
			if ( req.getParameter("region") != null && req.getParameter("region").toString().length() > 0 ){
				param.put("REGION", req.getParameter("region"));
			}
			param.put("SECRET_YN", req.getParameter("secretYn"));
			param.put("BLIND_YN", req.getParameter("blindYn"));
		} else {
			throw new Exception("Login please.");
		}	
		
		param.put("category", category);
		BoardListEntity boardListEntity = boardService.modifyBoard(req, param);
		List<BoardEntity> boardList = boardListEntity.getBoardList();
		
//		model.addObject("BoardList", boardList);
//		model.addObject("category", req.getParameter("category"));
//		model.addObject("BoardListEntity", boardListEntity);
		
//		RedirectView rv = new RedirectView("boardListView.do");
//		rv.setExposeModelAttributes(true);
//		rv.setAttributesMap(model.getModelMap());
		
		String redirectUrl = "boardListView.do?jsp=board/board&category=" + category;
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/boardFeedback.do")
	public void doBoardFeedback(@RequestParam(value = "p") String p, HttpServletRequest req, ServletResponse res) {
		// System.out.println("register.do!!!!");
		
		String rootUserSeqId = req.getParameter("userSeqId");
		String userSeqId = "";
		String userId = req.getParameter("userId");
		String userNic = req.getParameter("userNic");
		String category = req.getParameter("category");
		String boardSeqId = req.getParameter("boardSeqId");
		String isRecommend = req.getParameter("isRecommend");
		String isReject = req.getParameter("isReject");
		
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			
			rootUserSeqId = (String) bean.get("userSeqId");
			
			if ( rootUserSeqId.equals(userInfo.getSeq_id()) ){
				userSeqId = (String) bean.get("userSeqId");
				param.put("Code", "E");
				param.put("Text", "본인이 쓴 글은 추천/비추천 할 수 없습니다.");
			} else {
				userSeqId = userInfo.getSeq_id();
				userId = userInfo.getId();
				userNic = userInfo.getNic();
				category = (String) bean.get("category");
				boardSeqId = (String) bean.get("boardSeqId");
				isRecommend = (String) bean.get("isRecommend");
				isReject = (String) bean.get("isReject");
				
				param.put("ROOT_USER_SEQ_ID", rootUserSeqId);
				param.put("USER_SEQ_ID", userSeqId);
				param.put("USER_ID", userId);
				param.put("USER_NIC", userNic);
				param.put("CATEGORY", category);
				param.put("BOARD_SEQ_ID", boardSeqId);
				param.put("IS_RECOMMEND", isRecommend);
				param.put("IS_REJECT", isReject);
				
				boardService.updateFeedbackCount(param);
				
				if ( "Y".equals(isRecommend) ) {
					param.put("REQ_URI", "boardFeedback.do");
					param.put("ACTION_IP", req.getRemoteAddr());
					param.put("ACTION_SEQ_ID", boardSeqId);
					param.put("ACTION_TYPE", "RECOMMEND");
					param.put("ACTION_POINT", Constant.BOARD_RECOMMEND_POINT);
					adminService.writeOtherUserAction(param);
				}
				
//				if ( "Y".equals(isReject) ) {
//					param.put("REQ_URI", "boardFeedback.do");
//					param.put("ACTION_IP", req.getRemoteAddr());
//					param.put("ACTION_SEQ_ID", boardSeqId);
//					param.put("ACTION_TYPE", "RECOMMEND");
//					param.put("ACTION_POINT", Constant.BOARD_REJECT_POINT);
//					adminService.writeOtherUserAction(param);
//				}
			}
		} catch (Exception ex) {
			logger.error("doBoardFeedback error", ex);
			param.put("Code", "E");
			param.put("Text", ex.getMessage());
		}
		
		JSONObject jsonObject = JSONObject.fromObject(param);
		
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
//	@RequestMapping("/boardJump.do")
//	public void doBoardJump(@RequestParam(value = "p") String p, HttpServletRequest req, ServletResponse res) {
//		// System.out.println("boardJump.do!!!!");
//		
//		String userSeqId = req.getParameter("userSeqId");
//		String boardSeqId = req.getParameter("boardSeqId");
//		String category = req.getParameter("category");
//		
//		Map param;
//		param = new HashMap();
//		param.put("Code", "S");
//		param.put("Text", "Success");
//		
//		HttpSession httpSession = req.getSession(true);
//		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
//		
//		try {
//			JSONObject parameters = JSONObject.fromObject(p);
//			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
//			
//			userSeqId = userInfo.getSeq_id();
//			boardSeqId = (String) bean.get("boardSeqId");
//			
//			param.put("USER_SEQ_ID", userSeqId);
//			param.put("CATEGORY", category);
//			param.put("BOARD_SEQ_ID", boardSeqId);
//			
//			int jumpResult = boardService.boardJump(param);
//			
//			if ( jumpResult == 0 ){
//				userSeqId = (String) bean.get("userSeqId");
//				param.put("Code", "E");
//				param.put("Text", "점프 실패");
//			}
//		} catch (Exception ex) {
//			logger.error("doBoardJump error", ex);
//			param.put("Code", "E");
//			param.put("Text", ex.getMessage());
//		}
//		
//		JSONObject jsonObject = JSONObject.fromObject(param);
//		
//		try {
//			res.getWriter().write(jsonObject.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	@RequestMapping("/boardDelete.do")
	public ModelAndView doBoardDelete(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		// System.out.println("boardDelete.do");
		
		ModelAndView model = new ModelAndView("board/board");
		
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setBoardParameter(req, httpSession, model, param);
		
		String category = req.getParameter("category");
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
		} else {
			throw new Exception("Login please.");
		}
		
		param.put("category", category);
		BoardListEntity boardListEntity =boardService.deleteBoard(req, param);
		List<BoardEntity> boardList = boardListEntity.getBoardList();
		
		model.addObject("BoardList", boardList);
		model.addObject("category", req.getParameter("category"));
		model.addObject("BoardListEntity", boardListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/doWriteReply.do")
	public void doWriteReply(@RequestParam(value = "p") String p, HttpServletRequest req, ServletResponse res) {
		// System.out.println("doWriteReply.do");
		
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters,
					java.util.HashMap.class);
			
			// System.out.println(bean);
			
			String jobType =(String)bean.get("JobType");
			String txtContent =(String)bean.get("txtContent");
			String category =(String)bean.get("category");
			String seqId =(String)bean.get("seqId");
			String blindYn =(String)bean.get("blindYn");
			String replyOrderId =(String)bean.get("replyOrderId");
			
			HttpSession httpSession = req.getSession(true);
			UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
			
			if ( userInfo != null ) {
				map.put("CATEGORY", category);
				map.put("JOB_TYPE", jobType);
				
				map.put("TABLE_NAME", "");
				map.put("ROOT_SEQ_ID", seqId);
				map.put("REPLY_ORDER_ID", replyOrderId);
				map.put("PARENT_SEQ_ID", seqId);
				map.put("REPLY_DEPTH", replyOrderId.length() -2 );
				map.put("USER_SEQ_ID", userInfo.getSeq_id());
				map.put("USER_ID", userInfo.getId());
				map.put("USER_NIC", userInfo.getNic());
				map.put("TITLE", "");
				map.put("CONTENT", txtContent);
				map.put("BLIND_YN", blindYn);
				map.put("DEL_YN", "N");
				map.put("WRITER_IP", req.getRemoteAddr());
				map.put("WRITE_DATETIME", req.getParameter("write_datetime"));
			} else {
				throw new Exception("Login please.");
			}
			
			boardService.writeReply(req, map);
			
			List<Map> dataMap = boardService.readReply(req, map);
			map.put("Data", dataMap);
		} catch (Exception ex) {
			map.put("Code", "E");
			ex.printStackTrace();
			String errorText = ex.getMessage();
			map.put("Text", errorText);
		} finally {
			JSONObject jsonObject = JSONObject.fromObject(map);
			try {
				res.getWriter().write(jsonObject.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@RequestMapping("/doModifyReply.do")
	public void doModifyReply(@RequestParam(value = "p") String p, HttpServletRequest req, ServletResponse res) {
		// System.out.println("doWriteReply.do");
		
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters,
					java.util.HashMap.class);
			
			// System.out.println(bean);
			
			String jobType =(String)bean.get("JobType");
			String txtContent =(String)bean.get("txtContent");
			String category =(String)bean.get("category");
			String seqId =(String)bean.get("seqId");
			String blindYn =(String)bean.get("blindYn");
			String replyOrderId =(String)bean.get("replyOrderId");
			
			HttpSession httpSession = req.getSession(true);
			UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
			
			if ( userInfo != null ) {
				map.put("CATEGORY", category);
				map.put("JOB_TYPE", jobType);
				
				map.put("TABLE_NAME", "");
				map.put("ROOT_SEQ_ID", seqId);
				map.put("REPLY_ORDER_ID", replyOrderId);
				map.put("PARENT_SEQ_ID", seqId);
				map.put("REPLY_DEPTH", replyOrderId.length() -2 );
				map.put("USER_SEQ_ID", userInfo.getSeq_id());
				map.put("USER_ID", userInfo.getId());
				map.put("USER_NIC", userInfo.getNic());
				map.put("TITLE", "");
				map.put("CONTENT", txtContent);
				map.put("BLIND_YN", blindYn);
			} else {
				throw new Exception("Login please.");
			}
			
			boardService.modifyReply(req, map);
			
			List<Map> dataMap = boardService.readReply(req, map);
			map.put("Data", dataMap);
		} catch (Exception ex) {
			map.put("Code", "E");
			ex.printStackTrace();
			String errorText = ex.getMessage();
			map.put("Text", errorText);
		} finally {
			JSONObject jsonObject = JSONObject.fromObject(map);
			try {
				res.getWriter().write(jsonObject.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@RequestMapping("/doDeleteReply.do")
	public void doDeleteReply(@RequestParam(value = "p") String p, HttpServletRequest req, ServletResponse res) {
		// System.out.println("doWriteReply.do");
		
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			
			String jobType =(String)bean.get("JobType");
			String category =(String)bean.get("category");
			String seqId =(String)bean.get("seqId");
			String replyOrderId =(String)bean.get("replyOrderId");
			
			HttpSession httpSession = req.getSession(true);
			UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
			
			if ( userInfo != null ) {
				map.put("CATEGORY", category);
				map.put("JOB_TYPE", jobType);
				
				map.put("TABLE_NAME", "");
				map.put("ROOT_SEQ_ID", seqId);
				map.put("REPLY_ORDER_ID", replyOrderId);
			} else {
				throw new Exception("Login please.");
			}
			
			boardService.deleteReply(req, map);
			
			List<Map> dataMap = boardService.readReply(req, map);
			map.put("Data", dataMap);
		} catch (Exception ex) {
			map.put("Code", "E");
			ex.printStackTrace();
			String errorText = ex.getMessage();
			map.put("Text", errorText);
		} finally {
			JSONObject jsonObject = JSONObject.fromObject(map);
			try {
				res.getWriter().write(jsonObject.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@RequestMapping("/doReadReply.do")
	public void doReadReply(@RequestParam(value = "p") String p, HttpServletRequest req, ServletResponse res) {
		// System.out.println("doReadReply.do");
		
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters,
					java.util.HashMap.class);
			
			// System.out.println(bean);
			
			String jobType =(String)bean.get("JobType");
			String category =(String)bean.get("category");
			String seqId =(String)bean.get("seqId");
			
			HttpSession httpSession = req.getSession(true);
			UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
			
			if ( userInfo != null ) {
				map.put("CATEGORY", category);
				map.put("JOB_TYPE", jobType);
				
				map.put("ROOT_SEQ_ID", seqId);
			} else {
				throw new Exception("Login please.");
			}
			
			List<Map> dataMap = boardService.readReply(req, map);
			map.put("Data", dataMap);
		} catch (Exception ex) {
			map.put("Code", "E");
			ex.printStackTrace();
			String errorText = ex.getMessage();
			map.put("Text", errorText);
		} finally {
			JSONObject jsonObject = JSONObject.fromObject(map);
			try {
				res.getWriter().write(jsonObject.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@RequestMapping("/mainView.do")
	public ModelAndView mainView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		Map param = new HashMap();
		
		setBoardParameter(req, e, model, param);
		
		param.put("selectedMenu", req.getParameter("category"));
		
		BoardMainEntity boardMainEntity = (BoardMainEntity) servletContext.getAttribute("BoardMainEntity");
		
		if ( boardMainEntity == null ) {
			boardMainEntity = boardService.listMainBoard(param);
			servletContext.setAttribute("BoardMainEntity", boardMainEntity);
		}
		
//		BoardMainEntity boardMainEntity = boardService.listMainBoard(param);
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity) httpSession.getAttribute("userSessionKey");
		String userSeqId = userInfo.getSeq_id();
		String myBoardKey = userSeqId + "_MyBoardEntity";
		
		// System.out.println("myBoardKey:"+myBoardKey);
		
		List<BoardEntity> myBoardList = (List<BoardEntity>) httpSession.getAttribute(myBoardKey);
		if ( myBoardList == null ){
			myBoardList = boardService.getMyBoard(param);
			httpSession.setAttribute(myBoardKey, myBoardList);
		}
		
		boardMainEntity.setMyBoard(myBoardList);
			
		model.addObject("BoardMainEntity", boardMainEntity);
		
		// System.out.println((new StringBuilder("mainView:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/boardMainView.do")
	public ModelAndView boardMainView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		Map param = new HashMap();
		
		setBoardParameter(req, e, model, param);
		
		Map boardMainListMap = boardService.listCategoryBoard(param);
		
		model.addObject("BoardListMap", boardMainListMap);
		
		// System.out.println((new StringBuilder("boardMainView:")).append(jsp).toString());
		return model;
	}
	

	@RequestMapping("/boardListView.do")
	public ModelAndView boardListView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		String category = req.getParameter("category");
		if ( "050300".equals(category) ){
			model = new ModelAndView("board/imageBoardList");
		}
		
		Map param = new HashMap();
		
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		if ( searchKey != null && searchKey.length() > 0) {
			param.put("SEARCH_KEY", searchKey);
		}
		if ( searchValue != null && searchValue.toString().length() > 0 ) {
			param.put("SEARCH_VALUE", searchValue);
		}
		
		setBoardParameter(req, e, model, param);
		
		List<BoardEntity> noticeList = boardService.listNotice(param);
		
		BoardListEntity boardListEntity = boardService.listBoard(param);
		List<BoardEntity> boardList = boardListEntity.getBoardList();
		
		boardListEntity.setSearchKey(searchKey);
		boardListEntity.setSearchValue(searchValue);
		
		model.addObject("NoticeList", noticeList);
		model.addObject("BoardList", boardList);
		model.addObject("BoardListEntity", boardListEntity);
		
		// System.out.println((new StringBuilder("boardListView:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/blackUserList.do")
	public ModelAndView blackUserList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		
		Map param = new HashMap();
		
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		if ( searchKey != null && searchKey.length() > 0) {
			param.put("SEARCH_KEY", searchKey);
		}
		if ( searchValue != null && searchValue.toString().length() > 0 ) {
			param.put("SEARCH_VALUE", searchValue);
		}
		
		setBoardParameter(req, e, model, param);
		
		BoardListEntity boardListEntity = boardService.getBlackUserList(param);
		List<BoardEntity> boardList = boardListEntity.getBoardList();
		
		boardListEntity.setSearchKey(searchKey);
		boardListEntity.setSearchValue(searchValue);
		
		model.addObject("BoardList", boardList);
		model.addObject("BoardListEntity", boardListEntity);
		
		// System.out.println((new StringBuilder("boardListView:")).append(jsp).toString());
		return model;
	}
	

	private void setBoardParameter(HttpServletRequest req, HttpSession e, ModelAndView model, Map param) {
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		param.put("USER_SEQ_ID", userInfo.getSeq_id());
		param.put("USER_INFO", userInfo);
		
		List menus = userInfo.getMenuList();
		param.put("MENU", menus);
		
		String key = req.getParameter("keys");
		e.setAttribute("keys", req.getParameter("keys"));
		Enumeration em = req.getParameterNames();
		String okey = null;
		
		while (em.hasMoreElements()) {
			okey = (String) em.nextElement();
			
			if ( !okey.equals("region") || req.getParameter("region").toString().length() > 0 ){
				param.put(okey, req.getParameter(okey));
			}
			
			if ( model != null && !okey.equals("jsp") && !okey.equals("keys")) {
				// System.out.println(okey + " : " + req.getParameter(okey));
				model.addObject(okey, req.getParameter(okey));
			}
		}
		
		// set default pageNum
		if ( req.getParameter("pageNum") == null ){
			param.put("pageNum", 1);
			if ( model != null ) {
				model.addObject("pageNum", 1);
			}
		}
	}
	
	
	private ConditionEntity setCondition(HttpServletRequest req) {
		ConditionEntity cond = new ConditionEntity();
		
		try {
			if ( req.getParameter("pageNum") != null ){
				cond.setPageNumber(Integer.parseInt(req.getParameter("pageNum")));
			} else {
				cond.setPageNumber(1);
			}
		} catch (NumberFormatException e) {
			cond.setPageNumber(1);
		}
		
		String[] searchFieldArray;
		String[] searchKeyList;
//		cond.setSearchField(req.getParameter("searchKey"));
//		cond.setSearchKeyword(req.getParameter("searchValue"));
		
//		String[] searchFieldArray = req.getParameterValues("searchKey");
//		String[] searchTermArray = req.getParameterValues("searchTerm");
//		
//		ArrayList<String> searchFieldList = new ArrayList<String>();
//		for ( int ii = 0 ; ii < searchFieldArray.length ; ii ++ ) {
//			searchFieldList.add(searchFieldArray[ii]);
//		}
//		
//		ArrayList<String> searchTermList = new ArrayList<String>();
//		for ( int ii = 0 ; ii < searchTermArray.length ; ii ++ ) {
//			searchTermList.add(searchTermArray[ii]);
//		}
//		
//		cond.setSearchEndDt();
//		cond.setSearchStartDt();
//		cond.setSearchField(req.getParameterValues("searchField"));
//		cond.setSearchTerm(req.getParameterValues("searchTerm"));
//		cond.setSearchKeyword(req.getParameter("searchKeyword"));
		
		return cond;
	}
	
	
	@RequestMapping("/boardContentView.do")
	public ModelAndView boardContentView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		
		Map param = new HashMap();
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		param.put("CATEGORY", req.getParameter("category"));
		param.put("REGION", req.getParameter("region"));
		
		setBoardParameter(req, e, model, param);
		
		boardService.updateHitCount(param);
		
		BoardEntity boardContent = boardService.readBoard(param);
		
		if ( boardContent == null && boardContent.getSeq_id() == null ) {
			boardContent = boardService.readBoardWithDeletedUser(param);
		}
		
		param.put("TABLE_SEQ_ID", boardContent.getSeq_id());
		List<AttachmentEntity> attachmentList = boardService.readAttachment(param);
		boardContent.setAttachmentList(attachmentList);
		
		model.addObject("BoardContent", boardContent);
		
		// System.out.println((new StringBuilder("boardContentView:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/blackUserView.do")
	public ModelAndView blackUserView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		
		Map param = new HashMap();
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		param.put("CATEGORY", req.getParameter("category"));
		param.put("REGION", req.getParameter("region"));
		
		setBoardParameter(req, e, model, param);
		
		boardService.updateHitCount(param);
		
		BoardEntity boardContent = boardService.blackUserView(param);
		
		model.addObject("BoardContent", boardContent);
		
		// System.out.println((new StringBuilder("boardContentView:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/getFacilityNamesAsRegion.do")
	public void getFacilityNamesAsRegion(String p, HttpServletRequest req, ServletResponse res) {
		Map map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			String region = (String) bean.get("REGION");
			String facility_category = (String) bean.get("FACILITY_CATEGORY");
			
			map.put("REGION", region);
			map.put("FACILITY_CATEGORY", facility_category);
			
			List facilityNamesList = boardService.getFacilityNamesAsRegion(map);
			map.put("RESULT_LIST", facilityNamesList);
			
		} catch (Exception ex) {
			logger.error("doLogin error", ex);
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/boardAttachmentDown.do")
    public void down(HttpServletRequest req, HttpServletResponse res) throws Exception {
//		FileEntity dto = dao.read(no);
//        String orgname = dto.getOrgname();
//        String newname = dto.getNewname();
		String orgname = "test.uml";
		String newname = "C:\\upload\\2012\\12\\11\\1355210535966_test.uml";
		
        // MIME Type 을 application/octet-stream 타입으로 변경. 무조건 팝업(다운로드창)이 뜨게 된다.
        res.setContentType("application/octet-stream");

        // 브라우저는 ISO-8859-1을 인식하기 때문에 UTF-8 -> ISO-8859-1로 디코딩, 인코딩 한다.
        orgname = new String(orgname.getBytes("UTF-8"), "iso-8859-1");

        // 파일명 지정
        res.setHeader("Content-Disposition", "attachment; filename=\""+orgname+"\"");

        OutputStream os = res.getOutputStream();
        // String path = servletContext.getRealPath("/resources");
        // d:/upload 폴더를 생성한다.
        // server에 clean을 하면 resources 경로의 것이 다 지워지기 때문에
        // 다른 경로로 잡는다(실제 서버에서는 위의 방식으로)
        String path = "c:/upload";
        FileInputStream fis = new FileInputStream(path + File.separator + newname);
        int n = 0;
        byte[] b = new byte[512];
        while((n = fis.read(b)) != -1 ) {
            os.write(b, 0, n);
        }
        fis.close();
        os.close();
    }
	
	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String value() {
		// TODO Auto-generated method stub
		return null;
	}
}
