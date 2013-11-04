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

import com.autowrite.common.framework.bean.IndividualService;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.UserBusinessEntity;
import com.autowrite.common.framework.entity.UserBusinessListEntity;
import com.autowrite.common.framework.entity.UserEntity;

@Controller
public class IndividualController extends CommonController{
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	IndividualService individualService;
	
	@Autowired 
    private ServletContext servletContext;
	
	private Logger logger;
	
	public IndividualController() {
		logger = Logger.getLogger(getClass());
	}
	
	@RequestMapping("/businessInfoList.do")
	public ModelAndView businessMasterList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "individual/businessInfoList");
		
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
		
		UserBusinessListEntity userBusinessListEntity = individualService.listBusinessInfo(param);
		List<UserBusinessEntity> userBusinessList = userBusinessListEntity.getBusinessList();
		
		userBusinessListEntity.setSearchKey(searchKey);
		userBusinessListEntity.setSearchValue(searchValue);
		
		model.addObject("UserBusinessList", userBusinessList);
		model.addObject("UserBusinessListEntity", userBusinessListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/businessInfoWrite.do")
	public ModelAndView businessWrite(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("USER_SEQ_ID", userInfo.getSeq_id());
			param.put("BUSINESS_NAME", req.getParameter("businessName"));
			param.put("BUSINESS_REGION", req.getParameter("businessRegion"));
			param.put("BUSINESS_TEL", req.getParameter("businessTel"));
			param.put("BUSINESS_CATEGORY", req.getParameter("businessCategory"));
			param.put("BUSINESS_TIME", req.getParameter("businessTime"));
			param.put("BUSINESS_PRICE", req.getParameter("businessPrice"));
			param.put("BUSINESS_ADDRESS", req.getParameter("businessAddress"));
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		UserBusinessListEntity businessListEntity = individualService.writeBusinessInfo(req, param);
		List<UserBusinessEntity> businessList = businessListEntity.getBusinessList();
		
		
		String redirectUrl = "businessInfoList.do?jsp=individual/businessInfoList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	@RequestMapping("/businessInfoDelete.do")
	public ModelAndView businessDelete(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		String[] seqIdArray = req.getParameterValues("selectedSeqId");
		
		for ( int ii = 0 ; ii < seqIdArray.length ; ii ++ ) {
			param.put("SEQ_ID", seqIdArray[ii]);
			
			individualService.deleteBusinessInfo(req, param);
		}
		
		String redirectUrl = "businessInfoList.do?jsp=individual/businessInfoList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	@RequestMapping("/businessInfoRead.do")
	public ModelAndView siteRead(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		ModelAndView model = setJsp(req, "individual/businessInfoWrite");
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
			param.put("USER_SEQ_ID", userInfo.getSeq_id());
		} else {
			throw new Exception("Login please.");
		}
		
		if ( req.getParameter("seqId") != null ) {
			UserBusinessEntity userBusinessEntity = individualService.readBusinessInfo(req, param);
			model.addObject("UserBusinessEntity", userBusinessEntity);
		}
		
		UserBusinessListEntity userBusinessListEntity = individualService.listBusinessInfo(param);
		model.addObject("UserBusinessListEntity", userBusinessListEntity);
		
		return model;
	}
	
	
}
