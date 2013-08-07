package com.autowrite.common.framework.controller;

import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.autowrite.common.framework.bean.AdminService;
import com.autowrite.common.framework.bean.MemoService;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.MemoEntity;
import com.autowrite.common.framework.entity.UserEntity;

@Controller
public class MemoController implements Controller {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	MemoService memoService;
	
	@Autowired
	AdminService adminService;
	
	private Logger logger;
	
	public MemoController() {
		logger = Logger.getLogger(getClass());
	}
	
	@RequestMapping("/writeMemo.do")
	public ModelAndView doWriteMemo(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		// System.out.println("writeMemo.do");
		
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		String secretYn = "N";
		if ( req.getParameter("secret_yn") != null ){
			secretYn = req.getParameter("secret_yn").toString();
		}
		
		String category = req.getParameter("category");
		String content = req.getParameter("content");
		
		if ( userInfo != null ) {
			map.put("SND_USER_SEQ_ID", userInfo.getSeq_id());
			map.put("SND_USER_ID", userInfo.getId());
			map.put("SND_USER_NIC", userInfo.getNic());
			map.put("RCV_USER_SEQ_ID", req.getParameter("rcv_user_seq_id"));
			map.put("RCV_USER_ID", req.getParameter("rcv_user_id"));
			map.put("RCV_USER_NIC", req.getParameter("rcv_user_nic"));
			map.put("TITLE", req.getParameter("title"));
			map.put("CONTENT", content);
			map.put("RCV_DATETIME", "");
			map.put("RCV_YN", "N");
			map.put("DEL_YN", "N");
			map.put("WRITER_IP", req.getRemoteAddr());
		} else {
			throw new Exception("Login please.");
		}
		
		if ( category == null ){
			category = "02";
		}
		map.put("category", category);
		map.put("USER_SEQ_ID", userInfo.getSeq_id());
		
		List<MemoEntity> memoList = memoService.writeMemo(map);
		
		ModelAndView model = new ModelAndView("popup/memoList");
		model.addObject("MemoList", memoList);
		model.addObject("category", category);
		
		return model;
	}
	
	
	@RequestMapping("/memoListView.do")
	public ModelAndView memoListView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		String key = req.getParameter("keys");
		e.setAttribute("keys", req.getParameter("keys"));
		Enumeration em = req.getParameterNames();
		String okey = null;
		ModelAndView model = new ModelAndView(jsp);
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		param.put("USER_SEQ_ID", userInfo.getSeq_id());
		
		while (em.hasMoreElements()) {
			okey = (String) em.nextElement();
			if ( okey.equals("category") ) {
				
				param.put("category", req.getParameter(okey));
				List<MemoEntity> memoList = memoService.listMemo(param);
				// System.out.println("category : " + req.getParameter(okey));
				// System.out.println("memoList size : " + memoList.size());
				model.addObject("MemoList", memoList);
			} 
			
			if (!okey.equals("jsp") && !okey.equals("keys")) {
				model.addObject(okey, req.getParameter(okey));
			}
		}
		// System.out.println((new StringBuilder("memoListView:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/memoContentView.do")
	public ModelAndView memoContentView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		String key = req.getParameter("keys");
		e.setAttribute("keys", req.getParameter("keys"));
		Enumeration em = req.getParameterNames();
		String okey = null;
		ModelAndView model = new ModelAndView(jsp);
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		param.put("USER_SEQ_ID", userInfo.getSeq_id());
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		param.put("CATEGORY", req.getParameter("category"));
		param.put("SEND_MESSAGE_SEQ_ID", req.getParameter("sendMessageSeqId"));
		
		while (em.hasMoreElements()) {
			okey = (String) em.nextElement();
			if (!okey.equals("jsp") && !okey.equals("keys")) {
				model.addObject(okey, req.getParameter(okey));
			}
		}
		
		memoService.updateReadFlag(param);
		
		// get memo count
		int uncheckedMemoCount = userDao.getUncheckedMemoCount(param);
		userInfo.setUncheckedMemoCount(uncheckedMemoCount);
		
		MemoEntity memoContent = memoService.readMemo(param);
		model.addObject("MemoContent", memoContent);
		model.addObject("category", req.getParameter("category"));
		
		// System.out.println((new StringBuilder("memoContentView:")).append(jsp).toString());
		return model;
	}
	
	@RequestMapping("/memoDelete.do")
	public ModelAndView memoDelete(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		param.put("USER_SEQ_ID", userInfo.getSeq_id());
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		param.put("CATEGORY", req.getParameter("category"));
		
		
		String category = req.getParameter("category");
		
		param.put("category", category);
		param.put("USER_SEQ_ID", userInfo.getSeq_id());
		
		List<MemoEntity> memoList = memoService.deleteMemo(param);
		
		ModelAndView model = new ModelAndView("popup/memoList");
		model.addObject("MemoList", memoList);
		model.addObject("category", category);
		
		return model;
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
