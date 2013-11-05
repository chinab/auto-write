package com.autowrite.common.framework.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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

import com.autowrite.common.framework.bean.AutowriteService;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.AutowriteEntity;
import com.autowrite.common.framework.entity.AutowriteListEntity;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.UserEntity;

@Controller
public class AutowriteController extends CommonController{
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AutowriteService autowriteService;
	
	@Autowired 
    private ServletContext servletContext;
	
	private Logger logger;
	
	public AutowriteController() {
		logger = Logger.getLogger(getClass());
	}
	
	@RequestMapping("/autowriteMasterList.do")
	public ModelAndView autowriteMasterList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "autowrite/autowriteMasterList");
		
		Map param = new HashMap();
		
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		if ( searchKey != null && searchKey.length() > 0) {
			param.put("SEARCH_KEY", searchKey);
		}
		if ( searchValue != null && searchValue.toString().length() > 0 ) {
			param.put("SEARCH_VALUE", searchValue);
		}
		
		setDefaultParameter(req, e, model, param);
		
		AutowriteListEntity autowriteListEntity = autowriteService.listAutowriteMaster(param);
		List<AutowriteEntity> autowriteList = autowriteListEntity.getAutowriteList();
		
		autowriteListEntity.setSearchKey(searchKey);
		autowriteListEntity.setSearchValue(searchValue);
		
		model.addObject("AutowriteList", autowriteList);
		model.addObject("AutowriteListEntity", autowriteListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/autowriteSiteList.do")
	public ModelAndView autowriteSiteList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "autowrite/autowriteSiteList");
		
		Map param = new HashMap();
		
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		if ( searchKey != null && searchKey.length() > 0) {
			param.put("SEARCH_KEY", searchKey);
		}
		if ( searchValue != null && searchValue.toString().length() > 0 ) {
			param.put("SEARCH_VALUE", searchValue);
		}
		
		setDefaultParameter(req, e, model, param);
		
		String autowriteMasterSeqid = req.getParameter("autowriteMasterSeqid");
		param.put("AUTOWRITE_MASTER_SEQ_ID", autowriteMasterSeqid);
		
		AutowriteListEntity autowriteListEntity = autowriteService.listAutowriteSite(param);
		List<AutowriteEntity> autowriteList = autowriteListEntity.getAutowriteList();
		
		autowriteListEntity.setSearchKey(searchKey);
		autowriteListEntity.setSearchValue(searchValue);
		
		model.addObject("AutowriteList", autowriteList);
		model.addObject("AutowriteListEntity", autowriteListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/autowriteLogList.do")
	public ModelAndView autowriteLogList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "autowrite/autowriteLogList");
		
		Map param = new HashMap();
		
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		if ( searchKey != null && searchKey.length() > 0) {
			param.put("SEARCH_KEY", searchKey);
		}
		if ( searchValue != null && searchValue.toString().length() > 0 ) {
			param.put("SEARCH_VALUE", searchValue);
		}
		
		setDefaultParameter(req, e, model, param);
		
		String autowriteMasterSeqid = req.getParameter("autowriteMasterSeqid");
		param.put("AUTOWRITE_MASTER_SEQ_ID", autowriteMasterSeqid);
		
		String autowriteSiteSeqid = req.getParameter("autowriteSiteSeqid");
		param.put("AUTOWRITE_SITE_SEQ_ID", autowriteSiteSeqid);
		
		AutowriteListEntity autowriteListEntity = autowriteService.listAutowriteLog(param);
		List<AutowriteEntity> autowriteList = autowriteListEntity.getAutowriteList();
		
		autowriteListEntity.setSearchKey(searchKey);
		autowriteListEntity.setSearchValue(searchValue);
		
		model.addObject("AutowriteList", autowriteList);
		model.addObject("AutowriteListEntity", autowriteListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/autowriteWriteForm.do")
	public ModelAndView autowriteWriteForm(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		String selectedContentsKey = req.getParameter("contentsSeqId");
		
		if ( selectedContentsKey != null && selectedContentsKey.length() > 0 ) {
			  param.put("CONTENTS_SEQ_ID", selectedContentsKey);
		}
		
		if ( userInfo != null ) {
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
		} else {
			throw new Exception("Login please.");
		}
		
		AutowriteEntity autowriteEntity = autowriteService.getDefaultAutowriteEntity(req, param);
		
		ModelAndView model = setJsp(req, "autowrite/autowriteWrite");
		
		model.addObject("AutowriteEntity", autowriteEntity);
		
		return model;
	}
	
	
	@RequestMapping("/autowriteWrite.do")
	public ModelAndView autowriteWrite(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("CONTENTS_SEQ_ID", req.getParameter("contentsSeqId"));
			param.put("TITLE", req.getParameter("title"));
			param.put("CONTENT", req.getParameter("content"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		AutowriteListEntity autowriteListEntity = autowriteService.writePrivateAutowrite(req, param);
		List<AutowriteEntity> autowriteList = autowriteListEntity.getAutowriteList();
		
		
		String redirectUrl = "autowriteMasterList.do?jsp=autowrite/autowriteMasterList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	/**
	 * autowrite 재실행.
	 * @param jsp
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/autowriteRewrite.do")
	public ModelAndView autowriteRewrite(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("AUTOWRITE_MASTER_SEQ_ID", req.getParameter("seqId"));
			
			AutowriteEntity autowriteEntity = autowriteService.getRestoredAutowriteEntity(param);
			
			param.put("TITLE", autowriteEntity.getTitle());
			param.put("CONTENT", autowriteEntity.getContent());
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		AutowriteListEntity autowriteListEntity = autowriteService.writePrivateAutowrite(param);
		List<AutowriteEntity> autowriteList = autowriteListEntity.getAutowriteList();
		
		String redirectUrl = "autowriteMasterList.do?jsp=autowrite/autowriteMasterList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/autowriteDelete.do")
	public ModelAndView autowriteDelete(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		String[] seqIdArray = req.getParameterValues("selectedSeqId");
		
		for ( int ii = 0 ; ii < seqIdArray.length ; ii ++ ) {
			param.put("SEQ_ID", seqIdArray[ii]);
			
			autowriteService.deleteAutowrite(req, param);
		}
		
		String redirectUrl = "autowriteMasterList.do?jsp=autowrite/autowriteMasterList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
}
