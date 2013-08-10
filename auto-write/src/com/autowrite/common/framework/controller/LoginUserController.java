package com.autowrite.common.framework.controller;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Enumeration;
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

import com.autowrite.common.framework.bean.AdminService;
import com.autowrite.common.framework.bean.BoardService;
import com.autowrite.common.framework.bean.LoginUserService;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;
import com.autowrite.common.framework.entity.UserEntity;

@Controller
public class LoginUserController implements Controller {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	LoginUserService loginUserService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired 
    private ServletContext servletContext;

	private Logger logger;
	
	public LoginUserController() {
		logger = Logger.getLogger(getClass());
	}
	
	@RequestMapping("/user/register.do")
	public void doRegister(@RequestParam(value = "p") String p,HttpServletRequest req, ServletResponse res) {
		// System.out.println("/user/register.do!!!!");
		doUserRegister(p, req, res);
	}
	
	@RequestMapping("/register.do")
	public void doUserRegister(@RequestParam(value = "p") String p, HttpServletRequest req, ServletResponse res) {
		// System.out.println("register.do!!!!");
		
		String name = req.getParameter("name");
		String id = req.getParameter("id");
		String nic = req.getParameter("nic");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String tel1 = req.getParameter("tel1");
		String tel2 = req.getParameter("tel2");
		String tel3 = req.getParameter("tel3");
		
		String point = "500";
		String status_code = "A";
		String service_code = req.getParameter("service_code");
		
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			
			id = (String) bean.get("u_id");
			password = (String) bean.get("password");
			name = (String) bean.get("name");
			nic = (String) bean.get("nic");
			email = (String) bean.get("email");
			tel1 = (String) bean.get("tel1");
			tel2 = (String) bean.get("tel2");
			tel3 = (String) bean.get("tel3");
			
			System.out.println("name:" + name);
			System.out.println("id:" + id);
			System.out.println("nic:" + nic);
			System.out.println("password:" + password);
			System.out.println("email:" + email);
			
			
			// 업소회원
//			if ( "B".equals(type_code) ){
//				// 승인대기
//				status_code = "W";
//			}
			
			map.put("NAME", name);
			map.put("ID", id);
			map.put("NIC", nic);
			map.put("PASSWORD", password);
			map.put("EMAIL", email);
			map.put("POINT", point);
			map.put("STATUS_CODE", status_code);
			map.put("TEL1", tel1);
			map.put("TEL2", tel2);
			map.put("TEL3", tel3);
			
			adminService.userRegister(map);
			
			bean.put("LOGIN_IP", req.getRemoteAddr());
			
			UserEntity userInfo = loginUserService.authenticate(bean);
			
			String userSeqId = userInfo.getSeq_id();
			// set default menu allocation
			map.put("USER_SEQ_ID", userSeqId);
			adminService.setDefaultMenu(map);
			
			doLogin(p, req, res);
			
		} catch (Exception ex) {
			logger.error("doLogin error", ex);
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
			map.put("Text", errorText);
		}
		
	}
	
	@RequestMapping("/dologin.do")
	public void doLogin(String p, HttpServletRequest req, ServletResponse res) {
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		
		try {
//			List<PaymentMasterEntity> mainBannerList = (List<PaymentMasterEntity>)servletContext.getAttribute("MainBannerList");
//			
//			if ( mainBannerList == null ){
//				adminService.updateBannerContext();
//			}
			
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			String id = (String) bean.get("u_id");
			String password = (String) bean.get("password");
			UserEntity userInfo = null;
			
			bean.put("LOGIN_IP", req.getRemoteAddr());
			
			try {
				userInfo = loginUserService.doLogin(bean);
			} catch (NullPointerException npe){
				logger.error("doLogin error", npe);
				map.put("Code", "E");
				map.put("Text", "존재하지 않는 ID이거나 패스워드가 다릅니다.");
			}
			
			if (userInfo != null) {
				if ( "A".equals(userInfo.getStatus_code()) ) {
					HttpSession httpSession = req.getSession(true);
					httpSession.setAttribute("userSessionKey", userInfo);
					// System.out.println((new StringBuilder("login auth compelete. session id: ")).append(userInfo).toString());
					Map mpar = new HashMap();
					List mal = null;
	
					if ("M".equalsIgnoreCase(userInfo.getType_code())) {
						mal = adminService.getMenuListAll();
					} else {
						mpar.put("U_ID", userInfo.getId());
						mpar.put("U_SEQ_ID", userInfo.getSeq_id());
						mal = adminService.getMenuList(mpar);
					}
					userInfo.setMenuList(mal);
				} else if ( "W".equals(userInfo.getStatus_code()) ) {
					map.put("Code", "E");
					map.put("Text", "승인 대기중인 ID입니다.");
				} else if ( "B".equals(userInfo.getStatus_code()) ) {
					map.put("Code", "E");
					map.put("Text", "운영자에 의해 사용 중지 된 ID입니다.");
				} else {
					map.put("Code", "E");
					map.put("Text", "접속 불가능한 ID입니다.");
				}
			} else {
				map.put("Code", "E");
				map.put("Text", "존재하지 않는 ID이거나 패스워드가 다릅니다.");
			}
		} catch (Exception ex) {
			logger.error("doLogin error", ex);
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
	
	@RequestMapping("/doLogout.do")
	public void doLogout(String p, UserEntity user_info, HttpSession s, ServletResponse res) {
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "Success");
		try {
			String userId = user_info.getId();
			s.invalidate();
			// System.out.println((new StringBuilder(String.valueOf(userId))).append(" logout compelete.").toString());
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
	
	@RequestMapping("/idDupCheck.do")
	public void idDupCheck(String p, HttpServletRequest req, ServletResponse res) {
		Map map = new HashMap();
		
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			String id = (String) bean.get("ID");
			map.put("ID", id);
			
			List dupList = userDao.checkDuplicateId(map);
			Map dupMap = (Map) dupList.get(0);
			
			Long dupCount = (Long) dupMap.get("DUPL_CNT");
			
			if (dupCount == 0) {
				map.put("Code", "S");
				map.put("Text", "Success");
			} else {
				map.put("Code", "D");
				map.put("Text", "User ID exists.");
			}
		} catch (Exception ex) {
			logger.error("doLogin error", ex);
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
	
	@RequestMapping("/nicDupCheck.do")
	public void nicDupCheck(String p, HttpServletRequest req, ServletResponse res) {
		Map map = new HashMap();
		
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			String nic = (String) bean.get("NIC");
			map.put("NIC", nic);
			
			List dupList = userDao.checkDuplicateNic(map);
			Map dupMap = (Map) dupList.get(0);
					
			Long dupCount = (Long) dupMap.get("DUPL_CNT");
			
			if (dupCount == 0) {
				map.put("Code", "S");
				map.put("Text", "Success");
			} else {
				map.put("Code", "D");
				map.put("Text", "User Nicname exists.");
			}
		} catch (Exception ex) {
			logger.error("doLogin error", ex);
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
	
	@RequestMapping("/addQuickLink.do")
	public void addQuickLink(String p, HttpServletRequest req, ServletResponse res) {
		Map map;
		map = new HashMap();
		map.put("Code", "S");
		map.put("Text", "퀵링크가 등록되었습니다.");
		
		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			
			String quickLinkName = (String) bean.get("quickLinkName");
			String quickLinkValue = (String) bean.get("quickLinkValue");
			
			HttpSession httpSession = req.getSession(true);
			UserEntity userEntity = (UserEntity) httpSession.getAttribute(UserEntity.UserSessionKey);
			userEntity.getQuickLinkMap().put(quickLinkName, quickLinkValue);
			httpSession.setAttribute(UserEntity.UserSessionKey, userEntity);
			
			bean.put("USER_SEQ_ID", userEntity.getSeq_id());
			bean.put("ORDER_SEQ", 0);
			bean.put("QUICK_LINK_NAME", quickLinkName);
			bean.put("QUICK_LINK_URL", quickLinkValue);
			
			int count = loginUserService.selectQuickLink(bean);
			
			if ( count > 0 ) {
				map.put("Code", "E");
				map.put("Text", "이미 등록된 페이지입니다.");
			} else {
				loginUserService.addQuickLink(bean);
			}
		} catch (Exception ex) {
			logger.error("addQuickLink error", ex);
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
	
	
	@RequestMapping("/jspView.do")
	public ModelAndView viewJsp(String jsp, HttpServletRequest req, HttpSession e) {
		String key = req.getParameter("keys");
		e.setAttribute("keys", req.getParameter("keys"));
		Enumeration em = req.getParameterNames();
		String okey = null;
		ModelAndView model = new ModelAndView(jsp);
		while (em.hasMoreElements()) {
			okey = (String) em.nextElement();
			if (!okey.equals("jsp") && !okey.equals("keys"))
				model.addObject(okey, req.getParameter(okey));
		}
		// System.out.println((new StringBuilder(" main Top:")).append(jsp).toString());
		return model;
	}
	
	
	@RequestMapping("/myInfoView.do")
	public ModelAndView myInfoView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		
		ModelAndView model = new ModelAndView(jsp);
		
		Map param = new HashMap();
		
		setBoardParameter(req, e, model, param);
		
		return model;
	}
	
	
	@RequestMapping("/pointLogView.do")
	public ModelAndView pointLogView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		
		ModelAndView model = new ModelAndView(jsp);
		
		Map param = new HashMap();
		
		setBoardParameter(req, e, model, param);
		
		List pointLogList = adminService.getPointLogList(param);
		
		model.addObject("PointLogList", pointLogList);
		
		return model;
	}
	
	
	private void setBoardParameter(HttpServletRequest req, HttpSession e, ModelAndView model, Map param) {
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		param.put("USER_SEQ_ID", userInfo.getSeq_id());
		param.put("USER_INFO", userInfo);
		
		List menus = userInfo.getMenuList();
		param.put("MENU", menus);
		
		String key = req.getParameter("keys");
		e.setAttribute("keys", req.getParameter("keys"));
		Enumeration em = req.getParameterNames();
		String okey = null;
		
		while (em.hasMoreElements()) {
			okey = (String) em.nextElement();
			
			if (!okey.equals("jsp") && !okey.equals("keys")) {
				model.addObject(okey, req.getParameter(okey));
			}
		}
		
		// set default pageNum
		if ( req.getParameter("pageNum") == null ){
			param.put("pageNum", 1);
			model.addObject("pageNum", 1);
		}
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
