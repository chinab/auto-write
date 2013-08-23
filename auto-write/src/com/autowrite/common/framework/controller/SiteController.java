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
import org.springframework.web.bind.annotation.RequestParam;
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
		
		model = setJsp(req, "system/siteList");
		
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
			param.put("MASTER_SEQ_ID", req.getParameter("master_seq_id"));
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
	
	
	@RequestMapping("/siteUpdate.do")
	public ModelAndView doSiteModify(String p, HttpServletRequest req, ServletResponse res) throws Exception {
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
			param.put("TITLE", req.getParameter("title"));
			param.put("FACILITY_NAME", req.getParameter("facility_name"));
			param.put("FACILITY_USER_NAME", req.getParameter("facility_user_name"));
			param.put("FACILITY_PHONE", req.getParameter("facility_phone"));
			param.put("FACILITY_ADDRESS", req.getParameter("facility_address"));
			param.put("FACILITY_HOME_PAGE", req.getParameter("facility_home_page"));
			param.put("VISIT_TIME", req.getParameter("visit_time"));
			param.put("WAITERESS_NAME", req.getParameter("waiteress_name"));
			param.put("CONTENT", req.getParameter("content").getBytes("UTF-8"));
			param.put("TARGET_CATEGORY", req.getParameter("target_category"));
			if ( req.getParameter("region") != null && req.getParameter("region").toString().length() > 0 ){
				param.put("REGION", req.getParameter("region"));
			}
			param.put("SECRET_YN", req.getParameter("secretYn"));
			param.put("BLIND_YN", req.getParameter("blindYn"));
		} else {
			throw new Exception("Login please.");
		}
		
		
//		SiteListEntity siteListEntity = siteService.modifySite(req, param);
//		List<SiteEntity> siteList = siteListEntity.getSiteList();
		
		String redirectUrl = "siteListView.do?jsp=site/siteList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
}
