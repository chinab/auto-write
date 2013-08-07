package com.autowrite.common.framework.controller;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
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
	CommonDao commonDao;
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	UserEntity userEntity;
	
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String strName = req.getParameter("name");
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", strName);
		// System.out.println("test.do!!!!" + req.getContextPath());
		return new ModelAndView("/WEB-INF/views/index.jsp", model);
		
	}
	
	@RequestMapping("/test.do")
	public void doTest(@RequestParam(value = "p") String p,
			HttpServletRequest req, ServletResponse res) {
		
		// System.out.println("test.do!!!!");
	}
	
	@RequestMapping("/login.do")
	public void doLogin(@RequestParam(value = "p") String p,
			HttpServletRequest req, ServletResponse res) {
		
		// System.out.println("asdfasdf");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");

		try {
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters,
					java.util.HashMap.class);
			
			//insert CM_TPCODE
			String jobType =(String)bean.get("JobType");
			if ("MOD".equals(jobType)) this.sqlHelper.insert("tp.tpcode.update", bean);
			else this.sqlHelper.insert("tp.tpcode.insert", bean);
			
			String deliveryYn =(String)bean.get("DELIVARABLE");
			String protocol =(String)bean.get("PROTOCOL");
			if ("O".equals(deliveryYn)){
				this.sqlHelper.insert("tp."+protocol.toLowerCase()+".delete", bean);
			    //tp.http.insert, tp.ftp.insert, tp.dir.insert
				this.sqlHelper.insert("tp."+protocol.toLowerCase()+".insert", bean);
			}

		} catch (Exception ex) {
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
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
	
	
	public void login(@RequestParam(value="p") String p, HttpServletRequest req, ServletResponse res)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Code", "S");
		map.put("Text", "Success");	
		
		try{
			JSONObject parameters = JSONObject.fromObject(p);
			Map bean = (Map) JSONObject.toBean(parameters, java.util.HashMap.class);
			
			String id = (String)bean.get("u_id");
			String password = (String)bean.get("password");
			UserEntity userInfo = null;
			if (userEntity != null 
					&& id != null && id.equals(userEntity.getId()) 
					&& password != null && password.equals(userEntity.getPassword())) {
				userInfo = new UserEntity();
				userInfo.setId(id);
				userInfo.setName(userInfo.getName());
			} else {
				userInfo = new UserEntity();
				userInfo.setId("test");
				userInfo.setName("테스트");
			}
			
			if(userInfo != null) {
				HttpSession httpSession = req.getSession(true);
				httpSession.setAttribute(UserEntity.UserSessionKey, userInfo);
				// System.out.println("login auth compelete. session id: " + userInfo);
				
				// session close
				// httpSession.invalidate();
			}else {
				map.put("Code", "E");
				map.put("Text", "no user info");	
			}
		}catch(Exception ex){
			ex.printStackTrace();
			map.put("Code", "E");
			String errorText = Arrays.toString(ex.getStackTrace());
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

	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String value() {
		// TODO Auto-generated method stub
		return null;
	}

}
