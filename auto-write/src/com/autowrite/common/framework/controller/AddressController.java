package com.autowrite.common.framework.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import oracle.net.TNSAddress.AddressList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.autowrite.common.framework.bean.AddressService;
import com.autowrite.common.framework.dao.UserDao;
import com.autowrite.common.framework.entity.AddressEntity;
import com.autowrite.common.framework.entity.AddressListEntity;
import com.autowrite.common.framework.entity.UserEntity;


/**
 * @author shkim
 * @since 2013-12-29
 *
 */
@Controller
public class AddressController extends CommonController{
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AddressService addressService;
	
//	@Autowired 
//    private ServletContext servletContext;
	
	private Logger logger;
	
	public AddressController() {
		logger = Logger.getLogger(getClass());
	}
	
	@RequestMapping("/addressList.do")
	public ModelAndView addressList(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "address/addressList");
		
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
		
		AddressListEntity addressListEntity = null;
		List<AddressEntity> addressList = null;
		
		if ( searchValue != null && searchValue.length() > 0 ) {
			addressListEntity = addressService.listPrivateAddress(param);
			addressList = addressListEntity.getAddressList();
		} else {
			addressListEntity = new AddressListEntity();
		}
		
		addressListEntity.setSearchKey(searchKey);
		addressListEntity.setSearchValue(searchValue);
		
		model.addObject("AddressList", addressList);
		model.addObject("AddressListEntity", addressListEntity);
		
		return model;
	}
	
	
	@RequestMapping("/addressExcelUpload.do")
	public ModelAndView addressExcelUpload(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		File uploadedFile = getUploadFile(req);
		
		if ( userInfo != null ) {
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_IP", req.getRemoteAddr());
		} else {
			throw new Exception("Login please.");
		}
		
		List<AddressEntity> adressList = addressService.extractExcelContents(param, uploadedFile);
		
//		addressService.writeExcelAddressMaster(req, adressList);
		
		addressService.writeExcelAddressList(adressList);
		
		
		
		String redirectUrl = "addressList.do?jsp=address/addressList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	private File getUploadFile(HttpServletRequest req) throws Exception {
		String uploadDir = "C:\\address\\temp";
		
		MultipartRequest multipartRequest = (MultipartRequest) req;
		
		Iterator files = multipartRequest.getFileNames();
		
		File uploadedFile = null;
		
		while ( files.hasNext() ) {
			String attachmentName = files.next().toString();
			
			MultipartFile multipartFile = multipartRequest.getFile(attachmentName);
			
			String originalFileName = multipartFile.getOriginalFilename();
			
			if ( originalFileName.length() > 0 ) {
				
				String targetFileName = "";
				targetFileName = System.currentTimeMillis()+"";
				File target = new File(uploadDir, targetFileName);
				
				String targetPath = uploadDir + File.separator + targetFileName;
			    
			    multipartFile.transferTo(target);
			    
			    System.out.println("file path = " + target.getAbsolutePath());
			    
			    uploadedFile = target;
			} else {
				throw new Exception("No file uploaded.");
			}
		}
		
		return uploadedFile;
	}

	@RequestMapping("/addressWrite.do")
	public ModelAndView addressWrite(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		Map param;
		param = new HashMap();
		param.put("Code", "S");
		param.put("Text", "Success");
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		if ( userInfo != null ) {
			param.put("CONTENTS_NAME", req.getParameter("addressName"));
			param.put("TITLE", req.getParameter("title"));
			param.put("CONTENT", req.getParameter("content").getBytes("UTF-8"));
			param.put("WRITER_SEQ_ID", userInfo.getSeq_id());
			param.put("WRITER_ID", userInfo.getId());
			param.put("WRITER_IP", req.getRemoteAddr());
			param.put("WRITE_DATETIME", req.getParameter("write_datetime"));
		} else {
			throw new Exception("Login please.");
		}
		
		AddressListEntity addressListEntity = addressService.writePrivateAddress(req, param);
		List<AddressEntity> boardList = addressListEntity.getAddressList();
		
		
		String redirectUrl = "addressList.do?jsp=address/addressList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/addressUpdate.do")
	public ModelAndView addressUpdate(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		
		System.out.println("addressUpdate");
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		param.put("SEQ_ID", req.getParameter("selectedSeqId"));
		
		AddressListEntity boardListEntity = addressService.modifyAddress(req, param);
		List<AddressEntity> boardList = boardListEntity.getAddressList();
		
		String redirectUrl = "addressView.do?jsp=address/addressWrite";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/addressDelete.do")
	public ModelAndView addressDelete(String p, HttpServletRequest req, ServletResponse res) throws Exception {
		
		System.out.println("addressUpdate");
		
		Map param = new HashMap();
		
		HttpSession httpSession = req.getSession(true);
		UserEntity userInfo = (UserEntity)httpSession.getAttribute("userSessionKey");
		
		setDefaultParameter(req, httpSession, null, param);
		
		String[] seqIdArray = req.getParameterValues("selectedSeqId");
		
		for ( int ii = 0 ; ii < seqIdArray.length ; ii ++ ) {
			param.put("SEQ_ID", seqIdArray[ii]);
			
			addressService.deleteAddress(req, param);
		}
		
		String redirectUrl = "addressList.do?jsp=address/addressList";
		ModelAndView model = new ModelAndView(new RedirectView(redirectUrl));
		
		return model;
	}
	
	
	@RequestMapping("/addressView.do")
	public ModelAndView addressView(String jsp, HttpServletRequest req, HttpSession e) throws Exception {
		ModelAndView model = null;
		
		model = setJsp(req, "address/addressWrite");
		
		Map param = new HashMap();
		
		setDefaultParameter(req, e, model, param);
		
		param.put("SEQ_ID", req.getParameter("seqId"));
		
		AddressEntity addressEntity = addressService.addressView(param);
		
		model.addObject("AddressEntity", addressEntity);
		
		return model;
	}
	
	
	@RequestMapping("/readAddress.do")
	public void readAddress(@RequestParam(value = "p") String p, HttpServletRequest req, ServletResponse res) {
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
