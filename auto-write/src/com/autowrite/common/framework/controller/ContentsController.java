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

import com.autowrite.common.framework.bean.ContentsService;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.BoardListEntity;
import com.autowrite.common.framework.entity.UserEntity;

@Controller
public class ContentsController extends CommonController{
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ContentsService contentsService;
	
	@Autowired 
    private ServletContext servletContext;
	
	private Logger logger;
	
	public ContentsController() {
		logger = Logger.getLogger(getClass());
	}
	
	@RequestMapping("/contentsList.do")
	public ModelAndView contentsList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "contents/contentsList");
		
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
		
		BoardListEntity boardListEntity = contentsService.listPrivateContents(param);
		List<BoardEntity> boardList = boardListEntity.getBoardList();
		
		boardListEntity.setSearchKey(searchKey);
		boardListEntity.setSearchValue(searchValue);
		
		model.addObject("ContentsList", boardList);
		model.addObject("ContentsListEntity", boardListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/contentsWrite.do")
	public ModelAndView contentsWrite(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("CONTENTS_NAME", req.getParameter("contentsName"));
			param.put("TITLE", req.getParameter("title"));
			param.put("CONTENT", req.getParameter("content").getBytes("UTF-8"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		BoardListEntity contentsListEntity = contentsService.writePrivateContents(req, param);
		List<BoardEntity> boardList = contentsListEntity.getBoardList();
		
		
		String redirectUrl = "contentsList.do?jsp=contents/contentsList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/contentsUpdate.do")
	public ModelAndView contentsUpdate(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		
		System.out.println("contentsUpdate");
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		param.put("SEQ_ID", req.getParameter("selectedSeqId"));
		
		BoardListEntity boardListEntity = contentsService.modifyContents(req, param);
		List<BoardEntity> boardList = boardListEntity.getBoardList();
		
		String redirectUrl = "contentsView.do?jsp=contents/contentsWrite";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/contentsDelete.do")
	public ModelAndView contentsDelete(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		
		System.out.println("contentsUpdate");
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		String[] seqIdArray = req.getParameterValues("selectedSeqId");
		
		for ( int ii = 0 ; ii < seqIdArray.length ; ii ++ ) {
			param.put("SEQ_ID", seqIdArray[ii]);
			
			contentsService.deleteContents(req, param);
		}
		
		String redirectUrl = "contentsList.do?jsp=contents/contentsList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/contentsView.do")
	public ModelAndView contentsView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "contents/contentsWrite");
		
		Map param = new HashMap();
		
		setDefaultParameter(req, e, model, param);
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		
		BoardEntity contentsEntity = contentsService.contentsView(param);
		
		model.addObject("ContentsEntity", contentsEntity);
		
		return model;
	}
	
	
	@RequestMapping("/readContents.do")
	public void readContents(@RequestParam(value = "p") String p, HttpServletRequest req, ServletResponse res) {
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
