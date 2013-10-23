package com.autowrite.common.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.autowrite.common.framework.bean.SiteCategoryService;
import com.autowrite.common.framework.bean.SiteService;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.SiteCategoryEntity;
import com.autowrite.common.framework.entity.SiteCategoryListEntity;
import com.autowrite.common.framework.entity.SiteListEntity;
import com.autowrite.common.framework.entity.UserEntity;

@Controller
public class SiteCategoryController extends CommonController{
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	SiteCategoryService siteCategoryService;
	
	@Autowired
	SiteService siteService;
	
	@Autowired 
    private ServletContext servletContext;
	
	private Logger logger;
	
	public SiteCategoryController() {
		logger = Logger.getLogger(getClass());
	}
	
	@RequestMapping("/siteCategoryList.do")
	public ModelAndView siteCategoryList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "system/siteCategoryList");
		
		Map param = new HashMap();
		
		String masterSeqId = req.getParameter("masterSeqId");
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		if ( masterSeqId != null && masterSeqId.length() > 0) {
			param.put("MASTER_SEQ_ID", masterSeqId);
		}
		if ( searchKey != null && searchKey.length() > 0) {
			param.put("SEARCH_KEY", searchKey);
		}
		if ( searchValue != null && searchValue.toString().length() > 0 ) {
			param.put("SEARCH_VALUE", searchValue);
		}
		
		setDefaultParameter(req, e, model, param);
		
		SiteCategoryListEntity siteCategoryListEntity = siteCategoryService.listCategory(param);
		List<SiteCategoryEntity> siteCategoryList = siteCategoryListEntity.getSiteCategoryList();
		
		siteCategoryListEntity.setSearchKey(searchKey);
		siteCategoryListEntity.setSearchValue(searchValue);
		
		model.addObject("SiteCategoryList", siteCategoryList);
		model.addObject("SiteCategoryListEntity", siteCategoryListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/siteCategoryWrite.do")
	public ModelAndView siteCategoryWrite(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("MASTER_SEQ_ID", req.getParameter("masterSeqId"));
			param.put("CATEGORY_TYPE", req.getParameter("categoryType"));
			param.put("CATEGORY_NAME", req.getParameter("categoryName"));
			param.put("CATEGORY_VALUE", req.getParameter("categoryValue"));
			param.put("USE_YN", req.getParameter("useYn"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		SiteCategoryListEntity siteCategoryListEntity = siteCategoryService.writeCategory(req, param);
		List<SiteCategoryEntity> siteCategoryList = siteCategoryListEntity.getSiteCategoryList();
		
		String redirectUrl = "siteCategoryList.do?jsp=system/siteCategoryList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/siteCategoryUpdate.do")
	public ModelAndView siteCategoryUpdate(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
			param.put("MASTER_SEQ_ID", req.getParameter("masterSeqId"));
			param.put("CATEGORY_TYPE", req.getParameter("categoryType"));
			param.put("CATEGORY_NAME", req.getParameter("categoryName"));
			param.put("CATEGORY_VALUE", req.getParameter("categoryValue"));
			param.put("USE_YN", req.getParameter("useYn"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		SiteCategoryListEntity siteCategoryListEntity = siteCategoryService.modifyCategory(req, param);
		List<SiteCategoryEntity> siteCategoryList = siteCategoryListEntity.getSiteCategoryList();
		
		String redirectUrl = "siteCategoryList.do?jsp=system/siteCategoryList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/siteCategoryDelete.do")
	public ModelAndView siteCategoryDelete(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
		} else {
			throw new Exception("Login please.");
		}
		
		SiteCategoryListEntity siteCategoryListEntity = siteCategoryService.deleteCategory(req, param);
		List<SiteCategoryEntity> siteCategoryList = siteCategoryListEntity.getSiteCategoryList();
		
		String redirectUrl = "siteCategoryList.do?jsp=system/siteCategoryList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/siteCategoryRead.do")
	public ModelAndView siteCategoryRead(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		ModelAndView model = setJsp(req, "system/siteCategoryWrite");
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
		} else {
			throw new Exception("Login please.");
		}
		
		if ( req.getParameter("seqId") != null ) {
			SiteCategoryEntity siteCategoryEntity = siteCategoryService.readCategory(req, param);
			model.addObject("SiteCategoryEntity", siteCategoryEntity);
		}
		
		SiteListEntity siteListEntity = siteService.listMasterSite(param);
		model.addObject("SiteMasterList", siteListEntity);
		
		return model;
	}
}
