package com.autowrite.common.framework.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.autowrite.common.framework.bean.AdminService;
import com.autowrite.common.framework.bean.BoardService;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.MenuDto;
import com.autowrite.common.framework.entity.MenuEntity;
import com.autowrite.common.framework.entity.PageListWrapper;
import com.autowrite.common.framework.entity.PaymentListEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;
import com.autowrite.common.framework.entity.UserEntity;
import com.autowrite.common.framework.entity.UserListEntity;

@Controller
public class AdminController {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	AdminService adminService;
	
	public AdminController() {
	}
	
	private void setAdminParameter(HttpServletRequest req, HttpSession e, ModelAndView model, Map param) {
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		param.put("USER_SEQ_ID", userInfo.getSeq_id());
		
		String searchKey = req.getParameter("searchKey");
		String searchValue = req.getParameter("searchValue");
		String userTypeCode = req.getParameter("type_code");
		String statusCode = req.getParameter("status_code");
		String serviceCode = req.getParameter("service_code");
		String bannerTypeCode = req.getParameter("banner_type_code");
		
		if ( searchKey != null && searchKey.length() > 0) {
			param.put("SEARCH_KEY", searchKey);
		}
		if ( searchValue != null && searchValue.toString().length() > 0 ) {
			param.put("SEARCH_VALUE", searchValue);
		}
		if ( userTypeCode != null && userTypeCode.toString().length() > 0 ) {
			param.put("TYPE_CODE", userTypeCode);
		}
		if ( statusCode != null && statusCode.toString().length() > 0 ) {
			param.put("STATUS_CODE", statusCode);
		}
		if ( serviceCode != null && serviceCode.toString().length() > 0 ) {
			param.put("SERVICE_CODE", serviceCode);
		}
		if ( bannerTypeCode != null && bannerTypeCode.toString().length() > 0 ) {
			param.put("BANNER_TYPE_CODE", bannerTypeCode);
		}
		
		String key = req.getParameter("keys");
		e.setAttribute("keys", req.getParameter("keys"));
		Enumeration em = req.getParameterNames();
		String okey = null;
		
		while (em.hasMoreElements()) {
			okey = (String) em.nextElement();
			
			if ( !okey.equals("region") || req.getParameter("region").toString().length() > 0 ){
				param.put(okey, req.getParameter(okey));
			}
			
			if (!okey.equals("jsp") && !okey.equals("keys")) {
//				System.out.println(okey + " : " + req.getParameter(okey));
				model.addObject(okey, req.getParameter(okey));
			}
		}
		
		// set default pageNum
		if ( req.getParameter("pageNum") == null ){
			param.put("pageNum", 1);
			model.addObject("pageNum", 1);
		}
	}
	
	public void getNotice(String p, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			List contents = adminService.getMgnt(bean);
			if (contents == null) {
				map.put("Code", "E");
				map.put("Text", "\u800C\u2466\uB017\uF9E5\uFFFD \uFFFD\uBFBE\uFFFD\uC4EC.");
			} else {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("contents", contents);
				map.put("Data", dataMap);
				map.put("Count", Integer.valueOf(contents.size()));
			}
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);

		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getMenuItem(String p, ServletResponse res, HttpSession s) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			UserEntity userEntity = (UserEntity) s.getAttribute("userSessionKey");
			bean.put("U_ID", userEntity.getId());
			List ret = adminService.getMenuItem(bean);
			map.put("Data", ret);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Code", "E");
			String errorText = e.getMessage();
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createMenuItem(String p, ServletResponse res, HttpSession s) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			UserEntity userEntity = (UserEntity) s.getAttribute("userSessionKey");
			bean.put("U_ID", userEntity.getId());
			adminService.createMenuItem(bean);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Code", "E");
			String errorText = e.getMessage();
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeMenuItem(String p, ServletResponse res, HttpSession s) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			UserEntity userEntity = (UserEntity) s.getAttribute("userSessionKey");
			bean.put("U_ID", userEntity.getId());
			adminService.changeMenuItem(bean);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Code", "E");
			String errorText = e.getMessage();
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteMenuItem(String p, ServletResponse res, HttpSession s) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			UserEntity userEntity = (UserEntity) s.getAttribute("userSessionKey");
			bean.put("U_ID", userEntity.getId());
			adminService.deleteMenuItem(bean);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Code", "E");
			String errorText = e.getMessage();
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getQuickMenuSelection(String p, ServletResponse res, HttpSession s) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			UserEntity userEntity = (UserEntity) s.getAttribute("userSessionKey");
			bean.put("U_ID", userEntity.getId());
			Map dataMap = adminService.getQuickMenuSelection(bean);
			map.put("Data", dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Code", "E");
			String errorText = e.getMessage();
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getMenuSelection(String p, ServletResponse res, HttpSession s) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			UserEntity userEntity = (UserEntity) s.getAttribute("userSessionKey");
			bean.put("U_ID", userEntity.getId());
			Map dataMap = adminService.getMenuSelection(bean);
			map.put("Data", dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Code", "E");
			String errorText = e.getMessage();
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveMenuSelection(UserEntity user_info, String p, ServletResponse res, HttpSession s) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			UserEntity userEntity = (UserEntity) s.getAttribute("userSessionKey");
			bean.put("U_ID", userEntity.getId());
			adminService.setMenuSelection(bean);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Code", "E");
			String errorText = e.getMessage();
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setQuickMenuSelection(UserEntity user_info, String p, ServletResponse res, HttpSession s) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			UserEntity userEntity = (UserEntity) s.getAttribute("userSessionKey");
			bean.put("U_ID", userEntity.getId());
			adminService.setQuickMenuSelection(bean);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Code", "E");
			String errorText = e.getMessage();
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getVocCharger(String p, ServletResponse res, HttpSession s) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			UserEntity userEntity = (UserEntity) s.getAttribute("userSessionKey");
			bean.put("U_ID", userEntity.getId());
			Map dataMap = adminService.getVocCharger(bean);
			map.put("Data", dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Code", "E");
			String errorText = e.getMessage();
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setVocCharger(UserEntity user_info, String p, ServletResponse res, HttpSession s) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			UserEntity userEntity = (UserEntity) s.getAttribute("userSessionKey");
			adminService.setVocCharger(bean);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Code", "E");
			String errorText = e.getMessage();
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ModelAndView getUserMenuList(UserEntity userEntity, String subType, ServletResponse res) {
		ModelAndView model = new ModelAndView("inc/left");
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("Code", "S");
		map1.put("Text", "Success");
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put("U_ID", userEntity.getId());
		List returnList = new ArrayList();
		try {
			returnList = adminService.getMenuList(bean);
			model.addObject("menu", returnList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	public void createPartner(String p, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			adminService.createPartner(bean);
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changePartner(String p, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			adminService.changePartner(bean);
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getAccount(String p, HttpSession s, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			Map<String, Object> dataMap = adminService.getAccount(bean);
			map.put("Data", dataMap);
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ModelAndView mngTpcode(String jspPage, String job, String keys) {
		ModelAndView mav = new ModelAndView(jspPage);
		mav.addObject("jobType", job);
		mav.addObject("keys", keys);
		return mav;
	}

	public void getCodeData(String p, HttpSession s, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			Map dataMap = adminService.getCodeData("admin.getCode", bean);
			map.put("Data", dataMap);
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getCodeList(String p, String queryId, HttpSession s, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			List dataList = adminService.getCodeList(queryId, bean);
			map.put("Data", dataList);
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void inserCode(String p, String queryId, HttpSession s, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			adminService.insertCode(queryId, bean);
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateCode(String p, String queryId, HttpSession s, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			adminService.updateCode(queryId, bean);
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteCode(String p, String queryId, HttpSession s, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map<String, Object> bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			adminService.deleteCode(queryId, bean);
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertMailAlert(String p, HttpSession s, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			adminService.insertMailAlert(bean);
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getZipCodeList(String p, HttpServletRequest req, ServletResponse res) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			String key = (String) bean.get("U_ID");
			List dataList = adminService.getCodeList("admin.zipcode.info", bean);
			if (dataList != null && dataList.size() > 0) {
				map.put("Data", dataList);
				map.put("ZIP_CNT", Integer.valueOf(dataList.size()));
			} else {
				map.put("ZIP_CNT", Integer.valueOf(0));
			}
		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		try {
			res.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doFileMonite(String p, String type, ServletResponse res) {
		String fileTree;
		// System.out.println((new StringBuilder("Param :")).append(p).toString());
		fileTree = null;
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			// System.out.println((new StringBuilder("Map")).append(bean).toString());
			if (type.equals("init"))
				fileTree = adminService.getFileTree(bean);
			else if (type.equals("sub")) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Code", "S");
				map.put("Text", "Success");
				List al = adminService.searchFileSubTree(bean);
				map.put("Data", al);
				JSONObject jsonObject = JSONObject.fromObject(map);
				fileTree = jsonObject.toString();
			}
			// System.out.println((new StringBuilder("System Mm result :")).append(fileTree).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			fileTree = (new StringBuilder("ERROR :")).append(ex.getMessage()).toString();
		}
		try {
			res.getWriter().write(fileTree);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("/userInfoList.do")
	public ModelAndView userInfoList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		Map param = new HashMap();
		
		setAdminParameter(req, e, model, param);
		
		param.put("U_ID", req.getParameter("U_ID"));
		UserListEntity userListEntity = adminService.getUserList(param);
		
		userListEntity.setSearchCondtion(param);
		
		model.addObject("UserList", userListEntity);
		
		// System.out.println((new StringBuilder("userInfoList:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/userSearch.do")
	public ModelAndView userSearch(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		String key = req.getParameter("keys");
		e.setAttribute("keys", key);
		Enumeration em = req.getParameterNames();
		String okey = null;
		
		if ( jsp == null ) {
			jsp = req.getParameter("jsp");
		}
		
		ModelAndView model = new ModelAndView(jsp);
		
		while (em.hasMoreElements()) {
			okey = (String) em.nextElement();
			if (!okey.equals("jsp") && !okey.equals("keys")) {
				model.addObject(okey, req.getParameter(okey));
			}
		}
		
		String userSearchKey = req.getParameter("USER_SEARCH_KEY");
		if ( userSearchKey != null && userSearchKey.length() > 0 ) {
			Map param = new HashMap();
			param.put("USER_SEARCH_KEY", userSearchKey);
			List<UserEntity> userList = adminService.getUserSearchList(param);
			model.addObject("UserList", userList);
		} else {
			model.addObject("UserList", null);
		}
		
		String userSeqId = req.getParameter("userSeqId");
		
		if ( userSeqId != null && userSeqId.length() > 0 ) {
			Map param = new HashMap();
			param.put("USER_SEQ_ID", userSeqId);
			List<BoardEntity> userBoardList = adminService.getUserBoardList(param);
			model.addObject("UserBoardList", userBoardList);
			// System.out.println("UserBoardList size:" + userBoardList.size());
		} else {
			model.addObject("UserBoardList", null);
		}
		
		// System.out.println((new StringBuilder("userSearch:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/userRegister.do")
	public ModelAndView userRegister(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		// System.out.println("userRegister.do");
		
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		String category = req.getParameter("category");
		
		if ( userInfo != null ) {
			map.put("NAME", req.getParameter("name"));
			map.put("ID", req.getParameter("id"));
			map.put("NIC", req.getParameter("nic"));
			map.put("PASSWORD", req.getParameter("password"));
			map.put("EMAIL", req.getParameter("email"));
			map.put("POINT", "500");
			map.put("TYPE_CODE", req.getParameter("type_code"));
			map.put("STATUS_CODE", req.getParameter("status_code"));
		} else {
			throw new Exception("Login please.");
		}
		
		map.put("category", category);
		UserListEntity userList = adminService.userRegister(map);
		
		ModelAndView model = new ModelAndView("system/userInfoList");
		model.addObject("UserList", userList);
		model.addObject("category", req.getParameter("category"));
		
		return model;
	}
	
	@RequestMapping("/userModify.do")
	public ModelAndView userModify(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		// System.out.println("userModify.do");
		
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		String category = req.getParameter("category");
		
		if ( userInfo != null ) {
			map.put("SEQ_ID", req.getParameter("seqId"));
			map.put("USER_SEQ_ID", req.getParameter("seqId"));
			map.put("NAME", req.getParameter("name"));
			map.put("ID", req.getParameter("id"));
			map.put("NIC", req.getParameter("nic"));
			map.put("PASSWORD", req.getParameter("password"));
			map.put("EMAIL", req.getParameter("email"));
			map.put("POINT", req.getParameter("point"));
			map.put("TYPE_CODE", req.getParameter("type_code"));
			map.put("STATUS_CODE", req.getParameter("status_code"));
			map.put("SERVICE_CODE", req.getParameter("service_code"));
			} else {
			throw new Exception("Login please.");
		}
		
		map.put("category", category);
		UserListEntity userList = adminService.userModify(map);
		
		try {
			// 블랙 사용자 게시물 및 덧글 삭제
			if ( map.get("STATUS_CODE") != null && map.get("USER_SEQ_ID") != null ){
				if ( "B".equals(map.get("STATUS_CODE").toString()) ) {
					adminService.deleteBlackUserBoardContents(map.get("USER_SEQ_ID").toString());
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		ModelAndView model = new ModelAndView("system/userInfoList");
		model.addObject("UserList", userList);
		model.addObject("category", req.getParameter("category"));
		
		return model;
	}
	
	@RequestMapping("/userInfoView.do")
	public ModelAndView userInfoView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		
		Map param = new HashMap();
		
		setAdminParameter(req, e, model, param);
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		
		UserEntity userInfo = adminService.getUserInfo(param);
		model.addObject("UserInfo", userInfo);
		
		// System.out.println((new StringBuilder("userInfo:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/userPointView.do")
	public ModelAndView userPointView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		
		ModelAndView model = new ModelAndView(jsp);
		
		Map param = new HashMap();
		
		setAdminParameter(req, e, model, param);
		
		// override user sequence id.
		param.put("USER_SEQ_ID", req.getParameter("seqId") );
		
		PageListWrapper pointLogListEntity = adminService.getUserPointLogList(param);
		
		model.addObject("PointLogListEntity", pointLogListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/menuInfoList.do")
	public ModelAndView menuInfoList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		Map param = new HashMap();
		
		setAdminParameter(req, e, model, param);
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		
		List returnList = adminService.getAdminMenuList(param);
		model.addObject("MenuList", returnList);
		
		// System.out.println((new StringBuilder("MenuList:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/menuInfoView.do")
	public ModelAndView menuInfoView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		Map param = new HashMap();
		
		setAdminParameter(req, e, model, param);
		param.put("MENU_ID", req.getParameter("menuId"));
		
		MenuDto menuEntity = adminService.getAdminMenuEntity(param);
		model.addObject("MenuEntity", menuEntity);
		
		return model;
	}
	
	
	@RequestMapping("/defaultMenuList.do")
	public ModelAndView defaultMenuList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		Map param = new HashMap();
		
		setAdminParameter(req, e, model, param);
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		
		if ( req.getParameter("userType") != null && req.getParameter("userType").toString().length() > 0 ) {
			param.put("TYPE_CODE", req.getParameter("userType"));
		}
		
		List<MenuDto> menuList = adminService.getAdminMenuList(param);
		Map returnMap = adminService.getDefaultMenuMap(param, menuList);
		
		model.addObject("AllMenuList", menuList);
		model.addObject("DefaultMenuMap", returnMap);
		
		// System.out.println((new StringBuilder("defaultMenuList:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/pointMasterList.do")
	public ModelAndView pointMasterList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		Map param = new HashMap();
		
		setAdminParameter(req, e, model, param);
		
		if ( req.getParameter("userType") != null && req.getParameter("userType").toString().length() > 0 ) {
			param.put("TYPE_CODE", req.getParameter("userType"));
		}
		
		List<MenuDto> menuList = adminService.getAdminMenuList(param);
		Map returnMap = adminService.getPointMasterList(param, menuList);
		
		model.addObject("AllMenuList", menuList);
		model.addObject("PointMasterMap", returnMap);
		
		// System.out.println((new StringBuilder("defaultMenuList:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/userPaymentList.do")
	public ModelAndView userPaymentList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		String category = req.getParameter("category");
		
		Map param = new HashMap();
		
		setAdminParameter(req, e, model, param);
		
		// MASTER, LOG
		param.put("PAYMENT_CATEGORY", "MASTER");
		PaymentListEntity paymentListEntity = adminService.getPaymentListEntity(param);
		List<PaymentMasterEntity> userPaymentList = paymentListEntity.getPaymentMasterList();
		
		paymentListEntity.setSearchCondtion(param);
		
		model.addObject("PaymentListEntity", paymentListEntity);
		
		// System.out.println((new StringBuilder("userPaymentList:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/userPaymentWrite.do")
	public ModelAndView userPaymentWrite(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		// System.out.println("userPaymentWrite.do");
		
		ModelAndView model = new ModelAndView("system/userPaymentList");
		String category = req.getParameter("category");
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setAdminParameter(req, httpSession, model, param);
		
		if ( userInfo != null ) {
			param.put("USER_SEQ_ID", req.getParameter("user_seq_id"));
			param.put("USER_NAME", req.getParameter("user_name"));
			param.put("USER_ID", req.getParameter("user_id"));
			param.put("USER_NIC", req.getParameter("user_nic"));
			param.put("FACILITY_NAME", req.getParameter("facility_name"));
			param.put("FACILITY_USER_NAME", req.getParameter("facility_user_name"));
			param.put("FACILITY_PHONE", req.getParameter("facility_phone"));
			param.put("FACILITY_ADDRESS", req.getParameter("facility_address"));
			param.put("FACILITY_HOME_PAGE", req.getParameter("facility_home_page"));
			param.put("FACILITY_INTRODUCE", req.getParameter("facility_introduce"));
			param.put("FACILITY_CATEGORY", req.getParameter("facility_category"));
			param.put("FACILITY_REGION", req.getParameter("facility_region"));
			param.put("TOTAL_PAYMENT_AMOUNT", req.getParameter("total_payment_amount"));
			param.put("TOTAL_PAYMENT_NUMBER", req.getParameter("total_payment_number"));
			param.put("PAYMENT_STATUS", req.getParameter("payment_status"));
			param.put("BANNER_TYPE_CODE", req.getParameter("banner_type_code"));
			param.put("START_DATETIME", req.getParameter("start_datetime"));
			param.put("END_DATETIME", req.getParameter("end_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		param.put("category", category);
		PaymentListEntity paymentListEntity = adminService.writePayment(req, param);
		
		model.addObject("category", req.getParameter("category"));
		model.addObject("PaymentListEntity", paymentListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/userPaymentModify.do")
	public ModelAndView userPaymentModify(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		// System.out.println("userPaymentModify.do");
		
		ModelAndView model = new ModelAndView("system/userPaymentList");
		String category = req.getParameter("category");
		
		Map param;
		param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		String secretYn = "N";
		if ( req.getParameter("secret_yn") != null ){
			secretYn = req.getParameter("secret_yn").toString();
		}
		
		setAdminParameter(req, httpSession, model, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
			param.put("USER_SEQ_ID", req.getParameter("user_seq_id"));
			param.put("USER_NAME", req.getParameter("user_name"));
			param.put("USER_ID", req.getParameter("user_id"));
			param.put("USER_NIC", req.getParameter("user_nic"));
			param.put("FACILITY_NAME", req.getParameter("facility_name"));
			param.put("FACILITY_USER_NAME", req.getParameter("facility_user_name"));
			param.put("FACILITY_PHONE", req.getParameter("facility_phone"));
			param.put("FACILITY_ADDRESS", req.getParameter("facility_address"));
			param.put("FACILITY_HOME_PAGE", req.getParameter("facility_home_page"));
			param.put("FACILITY_INTRODUCE", req.getParameter("facility_introduce"));
			param.put("FACILITY_CATEGORY", req.getParameter("facility_category"));
			param.put("FACILITY_REGION", req.getParameter("facility_region"));
			param.put("TOTAL_PAYMENT_AMOUNT", req.getParameter("total_payment_amount"));
			param.put("TOTAL_PAYMENT_NUMBER", req.getParameter("total_payment_number"));
			param.put("PAYMENT_STATUS", req.getParameter("payment_status"));
			param.put("BANNER_TYPE_CODE", req.getParameter("banner_type_code"));
			param.put("START_DATETIME", req.getParameter("start_datetime"));
			param.put("END_DATETIME", req.getParameter("end_datetime"));
			param.put("MAIN_BANNER_FILE_NAME", nvl(req.getParameter("main_banner_file_name")));
			param.put("MAIN_BANNER_WEB_LINK", nvl(req.getParameter("main_banner_web_link")));
			param.put("CENTER_BANNER_FILE_NAME", nvl(req.getParameter("center_banner_file_name")));
			param.put("CENTER_BANNER_WEB_LINK", nvl(req.getParameter("center_banner_web_link")));
			param.put("P_FILE_NAME", nvl(req.getParameter("p_file_name")));
			param.put("L_FILE_NAME", nvl(req.getParameter("l_file_name")));
			param.put("THUMBNAIL_FILE_NAME", nvl(req.getParameter("thumbnail_file_name")));
		} else {
			throw new Exception("Login please.");
		}	
		
		param.put("category", category);
		PaymentListEntity paymentListEntity = adminService.modifyPayment(req, param);
		
		model.addObject("category", req.getParameter("category"));
		model.addObject("PaymentListEntity", paymentListEntity);
		
		return model;
	}
	
	private String nvl(String sourceStr){
		if ( sourceStr != null && sourceStr.trim().length() > 0 ){
			return sourceStr;
		} else {
			return "";
		}
	}
	
	
	@RequestMapping("/userPaymentView.do")
	public ModelAndView userPaymentView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		
		String category = req.getParameter("category");
		
		Map param = new HashMap();
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		param.put("CATEGORY", req.getParameter("category"));
		
		setAdminParameter(req, e, model, param);
		
		PaymentMasterEntity paymentMasterEntity = adminService.selectPaymentMaster(param);
		
		model.addObject("category", category);
		model.addObject("PaymentMaster", paymentMasterEntity);
		
		// System.out.println((new StringBuilder("userPaymentView:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/userPaymentDelete.do")
	public ModelAndView userPaymentDelete(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		// System.out.println("userPaymentDelete.do");
		
		ModelAndView model = new ModelAndView("system/userPaymentList");
		
		String category = req.getParameter("category");
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setAdminParameter(req, httpSession, model, param);
		
		if ( userInfo != null ) {
			param.put("SEQ_ID", req.getParameter("seqId"));
		} else {
			throw new Exception("Login please.");
		}
		
		param.put("category", category);
		PaymentListEntity paymentListEntity = adminService.userPaymentDelete(req, param);
		
		model.addObject("category", req.getParameter("category"));
		model.addObject("BoardListEntity", paymentListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/userLoginList.do")
	public ModelAndView userLoginList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = new ModelAndView(jsp);
		Map param = new HashMap();
		
		setAdminParameter(req, e, model, param);
		
		PageListWrapper userLoginListEntity = adminService.getUserLoginList(param);
		
		userLoginListEntity.setSearchCondtion(param);
		
		model.addObject("UserLoginListEntity", userLoginListEntity);
		
		// System.out.println((new StringBuilder("userInfoList:")).append(jsp).toString());
		return model;
	}
	
		
		
}
