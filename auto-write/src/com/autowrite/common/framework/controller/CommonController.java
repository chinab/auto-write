package com.autowrite.common.framework.controller;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.autowrite.common.framework.dao.CommonDao;
import com.autowrite.common.framework.entity.UserEntity;

@Controller
public class CommonController implements Controller {
	
//	@Autowired
//	JSONUtil jSONUtil;
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	UserEntity userEntity;

	protected ModelAndView setJsp(HttpServletRequest req, String uri) {
		ModelAndView model;
		if ( req.getParameter("jsp") != null && req.getParameter("jsp").length() > 0 ){
			model = new ModelAndView(req.getParameter("jsp"));
		} else {
			model = new ModelAndView(uri);
		}
		return model;
	}	
	
	protected void setDefaultParameter(HttpServletRequest req, HttpSession e, ModelAndView model, Map param) {
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		param.put("USER_SEQ_ID", userInfo.getSeq_id());
		param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
		param.put("USER_INFO", userInfo);
		
//		List menus = userInfo.getMenuList();
//		param.put("MENU", menus);
		
		String key = req.getParameter("keys");
		e.setAttribute("keys", req.getParameter("keys"));
		Enumeration em = req.getParameterNames();
		String okey = null;
		
		while (em.hasMoreElements()) {
			okey = (String) em.nextElement();
			
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
	
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String strName = req.getParameter("name");
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", strName);
		// System.out.println("test.do!!!!" + req.getContextPath());
		return new ModelAndView("/WEB-INF/views/index.jsp", model);
		
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
