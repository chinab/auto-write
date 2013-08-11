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

import com.autowrite.common.framework.bean.SiteService;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.BoardListEntity;

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
	public ModelAndView boardListView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
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
		
		BoardListEntity boardListEntity = siteService.listPrivateSite(param);
		List<BoardEntity> boardList = boardListEntity.getBoardList();
		
		boardListEntity.setSearchKey(searchKey);
		boardListEntity.setSearchValue(searchValue);
		
		model.addObject("SiteList", boardList);
		model.addObject("SiteListEntity", boardListEntity);
		
		return model;
	}
}
