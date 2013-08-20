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
	
	@RequestMapping("/autowriteList.do")
	public ModelAndView autowriteList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "autowrite/autowriteList");
		
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
		
		AutowriteListEntity boardListEntity = autowriteService.listAutowrite(param);
		List<AutowriteEntity> boardList = boardListEntity.getAutowriteList();
		
		boardListEntity.setSearchKey(searchKey);
		boardListEntity.setSearchValue(searchValue);
		
		model.addObject("AutowriteList", boardList);
		model.addObject("AutowriteListEntity", boardListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/autowriteWriteForm.do")
	public ModelAndView autowriteWriteForm(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
		} else {
			throw new Exception("Login please.");
		}
		
		AutowriteEntity autowriteEntity = autowriteService.getDefaultAutowriteEntity(req, param);
		
		ModelAndView model = setJsp(req, "autowriteList.do?jsp=autowrite/autowriteList");
		
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
			param.put("CONTENTS_NAME", req.getParameter("autowriteName"));
			param.put("TITLE", req.getParameter("title"));
			param.put("CONTENT", req.getParameter("content").getBytes("UTF-8"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		AutowriteListEntity autowriteListEntity = autowriteService.writePrivateAutowrite(req, param);
		List<AutowriteEntity> boardList = autowriteListEntity.getAutowriteList();
		
		
		String redirectUrl = "autowriteList.do?jsp=autowrite/autowriteList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/autowriteUpdate.do")
	public ModelAndView doAutowriteModify(String p, HttpServletRequest req, ServletResponse res) throws Exception {
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
		
		
		AutowriteListEntity boardListEntity = autowriteService.modifyAutowrite(req, param);
		List<AutowriteEntity> boardList = boardListEntity.getAutowriteList();
		
//		model.addObject("AutowriteList", boardList);
//		model.addObject("category", req.getParameter("category"));
//		model.addObject("AutowriteListEntity", boardListEntity);
		
//		RedirectView rv = new RedirectView("boardListView.do");
//		rv.setExposeModelAttributes(true);
//		rv.setAttributesMap(model.getModelMap());
		
		String redirectUrl = "boardListView.do?jsp=autowrite/autowriteList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/autowriteView.do")
	public ModelAndView autowriteView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "autowrite/autowriteWrite");
		
		Map param = new HashMap();
		
		setDefaultParameter(req, e, model, param);
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		
		AutowriteEntity autowriteEntity = autowriteService.autowriteView(param);
		
		model.addObject("AutowriteEntity", autowriteEntity);
		
		return model;
	}
	
	
	@RequestMapping("/readAutowrite.do")
	public void readAutowrite(@RequestParam(value = "p") String p, HttpServletRequest req, ServletResponse res) {
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
			
			HttpSession httpSession = req.getSession(true);
			UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
			
			if ( userInfo != null ) {
				map.put("ROOT_SEQ_ID", seqId);
			} else {
				throw new Exception("Login please.");
			}
			
//			List<Map> dataMap = boardService.readReply(req, map);
//			map.put("Data", dataMap);
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
}
