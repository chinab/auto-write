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

import com.autowrite.common.framework.bean.SiteService;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.SiteEntity;
import com.autowrite.common.framework.entity.SiteListEntity;
import com.autowrite.common.framework.entity.UserEntity;

@Controller
public class SiteController extends CommonController{
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	SiteService siteService;
	
	@Autowired 
    private ServletContext servletContext;
	
	private Logger logger;
	
	public SiteController() {
		logger = Logger.getLogger(getClass());
	}
	
	@RequestMapping("/siteList.do")
	public ModelAndView siteList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "site/siteList");
		
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
		
		SiteListEntity siteListEntity = siteService.listPrivateSite(param);
		List<SiteEntity> siteList = siteListEntity.getSiteList();
		
		siteListEntity.setSearchKey(searchKey);
		siteListEntity.setSearchValue(searchValue);
		
		model.addObject("SiteList", siteList);
		model.addObject("SiteListEntity", siteListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/siteInfoList.do")
	public ModelAndView siteInfoList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "system/siteInfoList");
		
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
		
		SiteListEntity siteListEntity = siteService.listMasterSite(param);
		List<SiteEntity> siteList = siteListEntity.getSiteList();
		
		siteListEntity.setSearchKey(searchKey);
		siteListEntity.setSearchValue(searchValue);
		
		model.addObject("SiteList", siteList);
		model.addObject("SiteListEntity", siteListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/siteWrite.do")
	public ModelAndView siteWrite(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("MASTER_SEQ_ID", req.getParameter("masterSeqId"));
			param.put("DOMAIN", req.getParameter("siteDomain"));
			param.put("SITE_NAME", req.getParameter("siteName"));
			param.put("SITE_ID", req.getParameter("siteId"));
			param.put("SITE_PASSWD", req.getParameter("sitePasswd"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		SiteListEntity siteListEntity = siteService.writePrivateSite(req, param);
		List<SiteEntity> siteList = siteListEntity.getSiteList();
		
		
		String redirectUrl = "siteList.do?jsp=site/siteList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/siteInfoWrite.do")
	public ModelAndView siteInfoWrite(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("DOMAIN", req.getParameter("siteDomain"));
			param.put("SITE_NAME", req.getParameter("siteName"));
			param.put("SITE_ID_KEY", req.getParameter("siteIdKey"));
			param.put("SITE_PASSWD_KEY", req.getParameter("sitePasswdKey"));
			param.put("LOGIN_URL", req.getParameter("loginUrl"));
			param.put("WRITE_URL", req.getParameter("writeUrl"));
			param.put("MODIFY_URL", req.getParameter("modifyUrl"));
			param.put("DELETE_URL", req.getParameter("deleteUrl"));
			param.put("LOGIN_TYPE", req.getParameter("loginType"));
			param.put("WRITE_TYPE", req.getParameter("writeType"));
			param.put("MODIFY_TYPE", req.getParameter("modifyType"));
			param.put("DELETE_TYPE", req.getParameter("deleteType"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		SiteListEntity siteListEntity = siteService.writeMasterSite(req, param);
		List<SiteEntity> siteList = siteListEntity.getSiteList();
		
		String redirectUrl = "siteInfoList.do?jsp=system/siteInfoList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/siteUpdate.do")
	public ModelAndView siteUpdate(String p, HttpServletRequest req, ServletResponse res) throws Exception {
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
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
			param.put("MASTER_SEQ_ID", req.getParameter("masterSeqId"));
			param.put("DOMAIN", req.getParameter("siteDomain"));
			param.put("SITE_NAME", req.getParameter("siteName"));
			param.put("SITE_ID", req.getParameter("siteId"));
			param.put("SITE_PASSWD", req.getParameter("sitePasswd"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		
		SiteListEntity siteListEntity = siteService.modifyPrivateSite(req, param);
		List<SiteEntity> siteList = siteListEntity.getSiteList();
		
		String redirectUrl = "siteList.do?jsp=site/siteList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/siteInfoModify.do")
	public ModelAndView siteInfoModify(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
			param.put("DOMAIN", req.getParameter("siteDomain"));
			param.put("SITE_NAME", req.getParameter("siteName"));
			param.put("SITE_ID_KEY", req.getParameter("siteIdKey"));
			param.put("SITE_PASSWD_KEY", req.getParameter("sitePasswdKey"));
			param.put("LOGIN_URL", req.getParameter("loginUrl"));
			param.put("WRITE_URL", req.getParameter("writeUrl"));
			param.put("MODIFY_URL", req.getParameter("modifyUrl"));
			param.put("DELETE_URL", req.getParameter("deleteUrl"));
			param.put("LOGIN_TYPE", req.getParameter("loginType"));
			param.put("WRITE_TYPE", req.getParameter("writeType"));
			param.put("MODIFY_TYPE", req.getParameter("modifyType"));
			param.put("DELETE_TYPE", req.getParameter("deleteType"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		SiteListEntity siteListEntity = siteService.modifyMasterSite(req, param);
		List<SiteEntity> siteList = siteListEntity.getSiteList();
		
		String redirectUrl = "siteInfoList.do?jsp=system/siteInfoList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/siteDelete.do")
	public ModelAndView siteDelete(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
		} else {
			throw new Exception("Login please.");
		}
		
		SiteListEntity siteListEntity = siteService.deletePrivateSite(req, param);
		List<SiteEntity> siteList = siteListEntity.getSiteList();
		
		String redirectUrl = "siteList.do?jsp=site/siteList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/siteInfoDelete.do")
	public ModelAndView siteInfoDelete(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
		} else {
			throw new Exception("Login please.");
		}
		
		SiteListEntity siteListEntity = siteService.deleteMasterSite(req, param);
		List<SiteEntity> siteList = siteListEntity.getSiteList();
		
		String redirectUrl = "siteInfoList.do?jsp=system/siteInfoList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/siteRead.do")
	public ModelAndView siteRead(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		ModelAndView model = setJsp(req, "site/siteWrite");
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
		} else {
			throw new Exception("Login please.");
		}
		
		if ( req.getParameter("seqId") != null ) {
			SiteEntity siteEntity = siteService.readPrivateSite(req, param);
			model.addObject("SiteEntity", siteEntity);
		}
		
		SiteListEntity siteListEntity = siteService.listMasterSite(param);
		model.addObject("SiteMasterList", siteListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/siteInfoRead.do")
	public ModelAndView siteInfoRead(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		ModelAndView model = setJsp(req, "system/siteInfoWrite");
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
		} else {
			throw new Exception("Login please.");
		}
		
		SiteEntity siteEntity = siteService.readMasterSite(req, param);
		
		model.addObject("SiteEntity", siteEntity);
		
		return model;
	}
}
