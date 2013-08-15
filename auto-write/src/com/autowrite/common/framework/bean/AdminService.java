package com.autowrite.common.framework.bean;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.autowrite.common.config.Constant;
import com.autowrite.common.framework.dao.AdminDao;
import com.autowrite.common.framework.dao.BoardDao;
import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.BoardMainEntity;
import com.autowrite.common.framework.entity.DefaultMenuEntity;
import com.autowrite.common.framework.entity.MenuDto;
import com.autowrite.common.framework.entity.MenuEntity;
import com.autowrite.common.framework.entity.PageListWrapper;
import com.autowrite.common.framework.entity.PaymentListEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;
import com.autowrite.common.framework.entity.PointMasterEntity;
import com.autowrite.common.framework.entity.UserEntity;
import com.autowrite.common.framework.entity.UserListEntity;
import com.autowrite.common.util.ConfigManager;
import com.autowrite.common.util.PropertyUtil;
import com.autowrite.common.util.Thumbnailer;

@Component
public class AdminService {

	@Autowired
	AdminDao adminDao;

	@Autowired
	BoardDao boardDao;

	@Autowired
	private ServletContext servletContext;

	public AdminService() {
	}

	public List getMgnt(Map param) throws Exception {
		List resultInfo = adminDao.list(param);
		if (resultInfo.size() > 0)
			return resultInfo;
		else
			return null;
	}

	public Map getQuickMenuSelection(Map param) throws Exception {
		List resultAll = adminDao.getFiledSelectionAll(param);
		List resultSelected = adminDao.getMenuList(param);
		if (resultSelected.size() == 0) {
			param.put("defaultYN", "Y");
			resultSelected = adminDao.getFiledSelectionAll(param);
		}
		Map dataMap = new HashMap();
		dataMap.put("all", resultAll);
		dataMap.put("selected", resultSelected);
		return dataMap;
	}

	public void setQuickMenuSelection(Map param) throws Exception {
		adminDao.removePreviousFieldSelection(param);
		adminDao.insertCurrentFieldSelection(param);
	}

	public void setVocCharger(Map param) throws Exception {
		adminDao.setVocCharger(param);
	}

	public Map getVocCharger(Map param) throws Exception {
		List resultAll = adminDao.getVocChargerAll(param);
		Map dataMap = new HashMap();
		dataMap.put("all", resultAll);
		return dataMap;
	}

	public List getMenuList(Map param) throws Exception {
		param.put("menulvl", "1");
		List m1lst = adminDao.getMenuList(param);
		MenuDto dto = null;
		for (int i = 0; i < m1lst.size(); i++) {
			dto = (MenuDto) m1lst.get(i);
			param.put("menulvl", "2");
			param.put("parmenuid", dto.getMenuid());
			List m2lst = adminDao.getMenuList(param);
			dto.setSubmenus(m2lst);
			for (int j = 0; j < m2lst.size(); j++) {
				dto = (MenuDto) m2lst.get(j);
				param.put("menulvl", "3");
				param.put("parmenuid", dto.getMenuid());
				List m3lst = adminDao.getMenuList(param);
				dto.setLeafmenus(m3lst);
			}

		}

		return m1lst;
	}

	public List getMenuListAll() throws Exception {
		Map param = new HashMap();
		param.put("menulvl", "1");
		List m1lst = adminDao.getMenuListAll(param);
		MenuDto dto = null;
		for (int i = 0; i < m1lst.size(); i++) {
			dto = (MenuDto) m1lst.get(i);
			param.put("menulvl", "2");
			param.put("parmenuid", dto.getMenuid());
			List m2lst = adminDao.getMenuListAll(param);
			dto.setSubmenus(m2lst);
			for (int j = 0; j < m2lst.size(); j++) {
				dto = (MenuDto) m2lst.get(j);
				param.put("menulvl", "3");
				param.put("parmenuid", dto.getMenuid());
				List m3lst = adminDao.getMenuListAll(param);
				dto.setLeafmenus(m3lst);
			}

		}

		return m1lst;
	}

	public List getAdminMenuList(Map param) throws Exception {
		List menuList = adminDao.getAdminMenuList(param);

		return menuList;
	}

	public MenuDto getAdminMenuEntity(Map param) {
		MenuDto menuEntity = adminDao.getAdminMenuEntity(param);
		return menuEntity;
	}

	public void createPartner(Map bean) throws Exception {
		adminDao.createPartner(bean);
	}

	public Map getAccount(Map bean) throws Exception {
		Map dataMap = new HashMap();
		List result = adminDao.getAccount(bean);
		if (result.size() > 0)
			dataMap = (HashMap) result.get(0);
		return dataMap;
	}

	public void changePartner(Map bean) throws Exception {
		adminDao.changePartner(bean);
	}

	public void createMenuItem(Map bean) throws Exception {
		adminDao.createMenu(bean);
	}

	public void changeMenuItem(Map bean) throws Exception {
		adminDao.deleteMenu(bean);
		adminDao.createMenu(bean);
	}

	public List getMenuItem(Map bean) throws Exception {
		return adminDao.getMenu(bean);
	}

	public void deleteMenuItem(Map bean) throws Exception {
		adminDao.deleteMenu(bean);
	}

	public List getCodeList(String qid, Map bean) throws Exception {
		try {
			return adminDao.getCodeList(qid, bean);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public Map getCodeData(String qid, Map bean) throws Exception {
		try {
			return adminDao.getCode(qid, bean);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public void insertCode(String qid, Map bean) throws Exception {
		try {
			adminDao.insertCode(qid, bean);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public void updateCode(String qid, Map bean) throws Exception {
		try {
			adminDao.updateCode(qid, bean);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public void deleteCode(String qid, Map bean) throws Exception {
		try {
			adminDao.deleteCode(qid, bean);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public Map getMenuSelection(Map bean) throws Exception {
		Map allMap = new HashMap();
		List mlist = new ArrayList();
		if ("ALL".equals((String) bean.get("TYPE"))) {
			List mall = getMenuListAll();
			makeMenuList(1, null, mall, mlist);
			allMap.put("all", mlist);
			allMap.put("selected", new ArrayList());
		} else {
			List muser = getAllocatedMenuList(bean);
			makeMenuList(1, null, muser, mlist);
			allMap.put("all", new ArrayList());
			allMap.put("selected", mlist);
		}
		return allMap;
	}

	private List getAllocatedMenuList(Map param) throws Exception {
		param.put("menulvl", "1");
		List m1lst = adminDao
				.getCodeList("admin.authGroupMenu.selected", param);
		MenuDto dto = null;
		for (int i = 0; i < m1lst.size(); i++) {
			dto = (MenuDto) m1lst.get(i);
			param.put("menulvl", "2");
			param.put("parmenuid", dto.getMenuid());
			List m2lst = adminDao.getCodeList("admin.authGroupMenu.selected",
					param);
			dto.setSubmenus(m2lst);
			for (int j = 0; j < m2lst.size(); j++) {
				dto = (MenuDto) m2lst.get(j);
				param.put("menulvl", "3");
				param.put("parmenuid", dto.getMenuid());
				List m3lst = adminDao.getCodeList(
						"admin.authGroupMenu.selected", param);
				dto.setLeafmenus(m3lst);
			}

		}

		return m1lst;
	}

	protected void makeMenuList(int lvl, String prefix, List mlist, List allList) {
		Map mm = null;
		prefix = prefix == null ? "" : (new StringBuilder(
				String.valueOf(prefix))).append("_").toString();
		for (int i = 0; i < mlist.size(); i++) {
			MenuDto dto = (MenuDto) mlist.get(i);
			mm = new HashMap();
			mm.put("MENU_ID", (new StringBuilder(String.valueOf(prefix)))
					.append(dto.getMenuid()).toString());
			mm.put("MENU_NM", dto.getMenunm());
			mm.put("MENU_LVL", dto.getMenulvl());
			allList.add(mm);
			if (lvl == 1)
				makeMenuList(2, (new StringBuilder(String.valueOf(prefix)))
						.append(dto.getMenuid()).toString(), dto.getSubmenus(),
						allList);
			else if (lvl == 2)
				makeMenuList(3, (new StringBuilder(String.valueOf(prefix)))
						.append(dto.getMenuid()).toString(),
						dto.getLeafmenus(), allList);
		}

	}

	public void setMenuSelection(Map bean) throws Exception {
		adminDao.deleteCode("admin.userAuthSelection.delete", bean);
		List field_names = (List) bean.get("selectionSelected");
		Map tempMap = new HashMap();
		tempMap.put("AUTH_GROUP_ID", bean.get("AUTH_GROUP_ID"));
		for (int i = 0; i < field_names.size(); i++) {
			tempMap.put("MENU_ID", field_names.get(i));
			adminDao.insertCode("admin.userAuthSelection.insert", tempMap);
		}
	}

	public void insertMailAlert(Map bean) throws Exception {
		adminDao.deleteCode("admin.deleteMailAlert", bean);
		Map m = null;
		// System.out.println(bean);
		JSONArray al = JSONArray.fromObject(bean.get("Data"));
		// System.out.println(al);
		for (int i = 0; i < al.size(); i++) {
			m = (Map) JSONObject.toBean(
					JSONObject.fromObject(al.getJSONObject(i)),
					java.util.HashMap.class);
			// System.out.println(m);
			adminDao.insertCode("admin.insertMailAlert", m);
		}

	}

	public String getFileTree(Map bean) throws Exception {
		try {
			String fPath = null;
			ConfigManager config = ConfigManager.getInstance();
			List dirs = config.getList("directoryLookup.dir");
			// System.out.println((new
			// StringBuilder("Path:")).append(dirs).toString());
			StringBuffer sb = new StringBuffer();
			sb.append("<tree id=\"0\" text=\"XII\" >");
			int fnsize = dirs.size();
			File ff = null;
			int cnt = 1;
			Map nxtm = null;
			for (int i = 0; i < fnsize; i++) {
				ff = new File((String) dirs.get(i));
				if (getDirInfo(ff, "1", Integer.toString(cnt), sb))
					cnt++;
			}

			sb.append("</tree>");
			return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public List searchFileSubTree(Map param) throws Exception {
		try {
			List files = new ArrayList();
			String fPath = null;
			fPath = (String) param.get("path");
			String id = (String) param.get("id");
			if (id == null)
				id = "";
			// System.out.println((new
			// StringBuilder("Path:")).append(fPath).toString());
			File f = new File(fPath);
			File flist[] = f.listFiles();
			int fnsize = flist.length;
			File ff = null;
			int cnt = 1;
			Map nxtm = null;
			for (int i = 0; i < fnsize; i++) {
				ff = flist[i];
				if (ff.isDirectory()) {
					nxtm = new HashMap();
					nxtm.put("id", (new StringBuilder(String.valueOf(id)))
							.append("_").append(cnt).toString());
					nxtm.put("text", ff.getName());
					nxtm.put("path", convertSeparator(ff.getAbsolutePath()));
					files.add(nxtm);
					cnt++;
				}
			}

			return files;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	private String convertSeparator(String absolutePath) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < absolutePath.length(); i++)
			if (absolutePath.charAt(i) == '\\')
				sb.append("\\\\");
			else
				sb.append(absolutePath.charAt(i));

		return sb.toString();
	}

	private boolean getDirInfo(File f, String depth, String seq, StringBuffer sb) {
		if (!f.isDirectory()) {
			return false;
		} else {
			String id = (new StringBuilder(String.valueOf(depth))).append("_")
					.append(seq).toString();
			sb.append((new StringBuilder("<item id=\"")).append(id)
					.append("\" text=\"").append(f.getName()).append("\">")
					.toString());
			sb.append((new StringBuilder("<userdata name=\"path\">"))
					.append(f.getAbsolutePath()).append("</userdata>")
					.toString());
			File flist[] = f.listFiles();
			int cnt = 1;
			int fnsize = flist.length;
			sb.append("</item>");
			return true;
		}
	}

	public UserListEntity getUserList(Map param) {
		UserListEntity listEntity = new UserListEntity();

		setPageCondition(param);

		try {
			Long pageNum = (Long) param.get("PAGE_NUM");
			Long pageSize = (Long) param.get("PAGE_SIZE");

			if (pageNum == null) {
				pageNum = new Long(1);
			}

			if (pageSize == null) {
				pageSize = new Long(20);
			}
			listEntity.setPageNum(pageNum);
			listEntity.setPageSize(pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Long userCount = adminDao.countUserList(param);
		listEntity.setTotalListCount(userCount);

		List<UserEntity> list = adminDao.getUserList(param);
		listEntity.setUserList(list);

		return listEntity;
	}

	public UserListEntity userRegister(Map param) {
		adminDao.registUser(param);

		UserListEntity userList = getUserList(param);

		return userList;
	}

	public UserEntity getUserInfo(Map param) {
		UserEntity userInfo = adminDao.getUserInfo(param);
		return userInfo;
	}

	public UserListEntity userModify(Map param) {
		adminDao.modifyUser(param);

		deleteDefaultMenu(param);

		setDefaultMenu(param);

		UserListEntity userList = getUserList(param);

		return userList;
	}

	public void deleteDefaultMenu(Map param) {
		adminDao.deleteDefaultMenu(param);
	}

	public void setDefaultMenu(Map param) {
		adminDao.setDefaultMenu(param);
	}

	public List<UserEntity> getUserSearchList(Map param) {
		String selectedMenu = "";
		int startNum = 0;
		int pageSize = 50;

		param.put("START_NUM", startNum);
		param.put("PAGE_SIZE", pageSize);

		List<UserEntity> userList = adminDao.getUserSearchList(param);

		return userList;
	}

	public List<BoardEntity> getUserBoardList(Map param) {
		List<BoardEntity> list = adminDao.getUserBoardList(param);

		return list;
	}

	public void writeUserAction(HttpServletRequest request) {
		Map param;
		param = new HashMap();

		// who
		HttpSession httpSession = request.getSession(true);
		UserEntity userInfo = (UserEntity) httpSession
				.getAttribute("userSessionKey");

		if (userInfo != null) {
			param.put("USER_SEQ_ID", userInfo.getSeq_id());
			param.put("USER_ID", userInfo.getId());
			param.put("USER_NIC", userInfo.getNic());
			param.put("USER_TYPE", userInfo.getType_code());

			// when
			// system date

			// where
			String[] requestedURIs = request.getRequestURI().split("\\/");
			String requestedURI = requestedURIs[2];
			param.put("REQ_URI", requestedURI);
			param.put("ACTION_IP", request.getRemoteAddr());

			// what
			String category = request.getParameter("category");
			if (category == null) {
				category = "";
			}
			param.put("CATEGORY", category);
			if (request.getParameter("seqId") == null) {
				param.put("ACTION_SEQ_ID", 0);
			} else {
				param.put("ACTION_SEQ_ID", request.getParameter("seqId"));
			}

			// how
			String actionType = request.getParameter("actionType");
			if (actionType == null || actionType.toString().length() == 0
					|| "null".equals(actionType.toString())) {
				param.put("ACTION_TYPE", "READ");
			} else {
				param.put("ACTION_TYPE", actionType);
			}

			// why
			// pass

			if ("READ".equals(actionType) && !"dologin.do".equals(requestedURI)) {
				return;
			}

			String point = getPointByAction(request, param, requestedURI);

			if ("dologin.do".equals(requestedURI) && isFirstLogin(userInfo)) {
				param.put("CATEGORY", "LOGIN");
				param.put("ACTION_TYPE", "READ");
				point = Constant.LOGIN_POINT;
			} else if ("writeMemo.do".equals(requestedURI)) {
				param.put("CATEGORY", "MEMO");
				param.put("ACTION_TYPE", "WRITE");
				point = Constant.MEMO_WRITE_POINT;
			} else if ("doWriteReply.do".equals(requestedURI)) {
				param.put("CATEGORY", "REPLY");
				param.put("ACTION_TYPE", "REPLY");
				point = Constant.REPLY_WRITE_POINT;
			}

			if (point != null && !"0".equals(point) && point.trim().length() > 0) {
				param.put("ACTION_POINT", point);
				adminDao.writeUserAction(param);

				String newUserPoint = calculatePoint(userInfo, point);
				userInfo.setPoint(newUserPoint);
//				userInfo.setClassInfo(new Integer(newUserPoint), userInfo.getType_code());
				httpSession.setAttribute("userSessionKey", userInfo);
				param.put("POINT", newUserPoint);
				adminDao.updateUserPoint(param);
			}
		}
	}

	public void writeOtherUserAction(Map param) {
		adminDao.writeOtherUserAction(param);
		adminDao.addUserPoint(param);
	}

	private boolean isFirstLogin(UserEntity userInfo) {
		Boolean isFirstLogin = false;
		Map param = new HashMap();
		param.put("USER_SEQ_ID", userInfo.getSeq_id());

		int loginCount = adminDao.countUserLogin(param);

		if (loginCount == 1) {
			return true;
		} else {
			return false;
		}
	}

	private String calculatePoint(UserEntity userInfo, String point) {
		if (userInfo.getPoint() == null || "null".equals(userInfo.getPoint())) {
			userInfo.setPoint("0");
		}
		if (point == null || "null".equals(point)) {
			point = "0";
		}
		BigInteger oldPoint = new BigInteger(userInfo.getPoint());
		BigInteger addPoint = new BigInteger(point);
		BigInteger newPoint = oldPoint.add(addPoint);

		// System.out.println("oldPoint:"+oldPoint);
		// System.out.println("addPoint:"+addPoint);
		// System.out.println("newPoint:"+newPoint);

		return newPoint.toString();
	}

	private String getPointByAction(HttpServletRequest request, Map param,
			String requestedURI) {
		String point = null;
		String category;
		String actionType;
		String actionMapKey;

		if ("jspView.do".equals(requestedURI)) {
			return "";
		}

		HttpSession session = request.getSession(true);
		Map pointClassMap = (Map) session.getAttribute("POINT_CLASS_MAP");
		if (pointClassMap == null) {
			pointClassMap = new HashMap();
			List<Map> pointMasterList = null;
			pointMasterList = adminDao.getPointByAction(param);

			Map pointMap = null;
			long pointByAction;
			for (int ii = 0; ii < pointMasterList.size(); ii++) {
				pointMap = pointMasterList.get(ii);
				category = pointMap.get("CATEGORY").toString();
				actionType = pointMap.get("ACTION_TYPE").toString();
				actionMapKey = category + "_" + actionType;
				pointByAction = (Long) pointMap.get("POINT");
				pointClassMap.put(actionMapKey, pointByAction);
			}
			session.setAttribute("POINT_CLASS_MAP", pointClassMap);
		}

		try {
			category = param.get("CATEGORY").toString();
			actionType = param.get("ACTION_TYPE").toString();
			actionMapKey = category + "_" + actionType;

			point = (Long) pointClassMap.get(actionMapKey) + "";

			if (point == null || "null".equals(point)) {
				point = "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return point;
	}

	private void setPageCondition(Map param) {
		long pageNum = 0;
		long pageSize = Constant.BOARD_PAGE_SIZE;
		int startNum;
		int endNum;

		if (param.get("pageNum") != null
				&& param.get("pageNum").toString().length() > 0) {
			pageNum = new Long(param.get("pageNum").toString());
			param.put("PAGE_NUM", pageNum);
		}

		if (pageNum == 0) {
			pageNum = 1;
		}

		startNum = (int) ((pageNum - 1) * pageSize);
		endNum = (int) (pageNum * pageSize);

		param.put("PAGE_NUM", pageNum);
		param.put("PAGE_SIZE", pageSize);
		param.put("START_NUM", startNum);
		param.put("END_NUM", endNum);

		// System.out.println("PAGE_NUM:"+pageNum);
		// System.out.println("PAGE_SIZE:"+pageSize);
		// System.out.println("START_NUM:"+startNum);
		// System.out.println("END_NUM:"+endNum);
	}

	public Map getDefaultMenuMap(Map param, List<MenuDto> menuList) {
		List<Map> defaulMenuList = adminDao.getDefaultMenuList(param);

		Map<String, DefaultMenuEntity> returnMap = new HashMap<String, DefaultMenuEntity>();

		for (int ii = 0; ii < menuList.size(); ii++) {
			String menuId = menuList.get(ii).getMenuid();
			String menuName = menuList.get(ii).getMenunm();

			DefaultMenuEntity defaultMenuEntity = new DefaultMenuEntity();
			defaultMenuEntity.setMenuId(menuId);
			defaultMenuEntity.setMenuName(menuName);

			for (int jj = 0; jj < defaulMenuList.size(); jj++) {
				Map defaultMap = defaulMenuList.get(jj);

				String userTypeCode = defaultMap.get("TYPE_CODE").toString();
				String defaultMenuId = defaultMap.get("MENU_ID").toString();
				String authType = defaultMap.get("AUTH_TYPE").toString();

				String key = userTypeCode + authType;
				if (menuId.equals(defaultMenuId)) {
					defaultMenuEntity.setAuth(userTypeCode, authType);
				}
			}

			returnMap.put(menuId, defaultMenuEntity);
		}

		return returnMap;
	}

	public Map getPointMasterList(Map param, List<MenuDto> menuList) {
		List<Map> pointMasterList = adminDao.getPointMasterList(param);

		Map<String, PointMasterEntity> returnMap = new HashMap<String, PointMasterEntity>();

		String category;
		String seqId;
		String userTypeCode;
		String actionType;
		int point;

		for (int ii = 0; ii < menuList.size(); ii++) {
			String menuId = menuList.get(ii).getMenuid();
			String menuName = menuList.get(ii).getMenunm();

			PointMasterEntity pointMasterEntity = new PointMasterEntity();
			pointMasterEntity.setMenuId(menuId);
			pointMasterEntity.setMenuName(menuName);

			for (int jj = 0; jj < pointMasterList.size(); jj++) {
				Map defaultMap = pointMasterList.get(jj);

				category = defaultMap.get("CATEGORY").toString();

				if (menuId.equals(category)) {
					seqId = defaultMap.get("SEQ_ID").toString();
					userTypeCode = defaultMap.get("USER_TYPE").toString();
					actionType = defaultMap.get("ACTION_TYPE").toString();
					point = Integer
							.parseInt(defaultMap.get("POINT").toString());
					pointMasterEntity.setPoint(userTypeCode, actionType, point);
				}
			}

			returnMap.put(menuId, pointMasterEntity);
		}

		return returnMap;
	}

	public int getTodayBoardCount(String category, String userSeqId) {
		Map param = new HashMap();
		param.put("CATEGORY", category);
		param.put("USER_SEQ_ID", userSeqId);
		param.put("TABLE_NAME", "T_BOARD_COMMUNITY");

		return adminDao.getTodayBoardCount(param);
	}

	public int getMenuAuthCountByUserType(Map param) {
		return adminDao.getMenuAuthCountByUserType(param);
	}

	public List<PaymentMasterEntity> getUserPaymentList(Map param) {
		return adminDao.getUserPaymentList(param);
	}

	public PaymentListEntity getPaymentListEntity(Map param) throws Exception {
		String selectedMenu = "";

		setPageCondition(param);

		PaymentListEntity listEntity = new PaymentListEntity();

		try {
			Long pageNum = (Long) param.get("PAGE_NUM");
			Long pageSize = (Long) param.get("PAGE_SIZE");
			listEntity.setPageNum(pageNum);
			listEntity.setPageSize(pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int boardCount = adminDao.countUserPaymentList(param);
		listEntity.setTotalListCount(boardCount);

		List<PaymentMasterEntity> paymentMasterList = adminDao.getUserPaymentList(param);
		listEntity.setPaymentMasterList(paymentMasterList);

		return listEntity;
	}

	public PaymentListEntity writePayment(HttpServletRequest req, Map param) throws Exception {
		setPageCondition(param);

		bannerUpload(req, param);
		adminDao.writePayment(param);

		updateBannerContext();

		return getPaymentListEntity(param);
	}

	public void updateBannerContext() throws Exception {
		Map param = new HashMap();

		List<PaymentMasterEntity> paymentMasterList = adminDao.getPaymentMasterListForBanner();

		PaymentMasterEntity paymentMasterEntity;
		ArrayList<PaymentMasterEntity> mainBannerArrayList = new ArrayList<PaymentMasterEntity>();
		ArrayList<PaymentMasterEntity> centerBannerArrayList = new ArrayList<PaymentMasterEntity>();

		for (int ii = 0; ii < paymentMasterList.size(); ii++) {
			paymentMasterEntity = paymentMasterList.get(ii);

			// recent lineup setting
			setRecentLineUp(paymentMasterEntity);

			String bannerType = paymentMasterEntity.getBanner_type_code();
			if ("M".equals(bannerType) || "B".equals(bannerType)) {
				if (paymentMasterEntity.getMain_banner_web_link() != null) {
					mainBannerArrayList.add(paymentMasterEntity);
				}
			}
			if ("C".equals(bannerType) || "B".equals(bannerType)) {
				if (paymentMasterEntity.getCenter_banner_web_link() != null) {
					centerBannerArrayList.add(paymentMasterEntity);
				}
			}
		}

		servletContext.setAttribute("MainBannerList", mainBannerArrayList);
		servletContext.setAttribute("CenterBannerList", centerBannerArrayList);
		
		updateCenterBanner();
	}

	private void updateCenterBanner() {
		Map param = new HashMap();
		param.put("selectedMenu", "020000");
		param.put("category", "020000");
		
		BoardMainEntity boardMainEntity = (BoardMainEntity) servletContext.getAttribute("BoardMainEntity");
		
		if ( boardMainEntity != null ) {
			List<PaymentMasterEntity> recommendedBusinessInfo = boardDao.listRecommendedBusinessInfo(param);
			boardMainEntity.setRecommendedBusinessInfo(recommendedBusinessInfo);
			if ( recommendedBusinessInfo != null && recommendedBusinessInfo.size() > 0 ){
				PaymentMasterEntity paymentMasterEntity;
				
				List<BoardEntity> recentLineUpList;
				List<BoardEntity> recentPostscriptList;
				
				for ( int ii = 0 ; ii < recommendedBusinessInfo.size() ; ii ++ ){
					paymentMasterEntity = recommendedBusinessInfo.get(ii);
					
					Map<String, Object> subParam = new HashMap();
					// for lineup
					subParam.put("START_NUM", 0);
					subParam.put("PAGE_SIZE", 1);
					subParam.put("USER_SEQ_ID", paymentMasterEntity.getUser_seq_id());
					// for postscript
					subParam.put("START_NUM", 0);
					subParam.put("PAGE_SIZE", 6);
					subParam.put("FACILITY_NAME", paymentMasterEntity.getFacility_name());
					
					recentLineUpList = boardDao.getRecentLineUpList(subParam);
					recentPostscriptList = boardDao.getRecentPostscriptList(subParam);
					paymentMasterEntity.setRecentLineUpList(recentLineUpList);
					paymentMasterEntity.setRecentPostscriptList(recentPostscriptList);
				}
			}
			
			servletContext.setAttribute("BoardMainEntity", boardMainEntity);
		}
	}

	private void setRecentLineUp(PaymentMasterEntity paymentMasterEntity) {
		Map<String, Object> subParam = new HashMap();
		// for lineup
		subParam.put("START_NUM", 0);
		subParam.put("PAGE_SIZE", 1);
		subParam.put("USER_SEQ_ID", paymentMasterEntity.getUser_seq_id());

		List<BoardEntity> recentLineUpList = boardDao.getRecentLineUpList(subParam);
		paymentMasterEntity.setRecentLineUpList(recentLineUpList);
	}

	private void bannerUpload(HttpServletRequest req, Map param) throws IOException {
		MultipartRequest multipartRequest = (MultipartRequest) req;

		Iterator files = multipartRequest.getFileNames();

		if (files.hasNext()) {
			// office dev
			// String baseDir = "D:\\eGovFrameDev-2.0.1-FullVer\\workspace\\autowrite\\WebContent\\banner";

			// home dev
			// String baseDir = "C:\\dev\\junoWork\\autowrite\\WebContent\\banner";

			// server dev
			//String baseDir = "D:\\apache-tomcat-6.0.36\\webapps\\autowrite\\banner";
			
			String baseDir = (String) servletContext.getAttribute("BANNER_BASE_DIR");
			if ( baseDir == null ){
				baseDir = PropertyUtil.getProperty("BANNER_BASE_DIR");
				servletContext.setAttribute("BANNER_BASE_DIR", baseDir);
			}
			
			String webDir = req.getContextPath() + File.separator + "banner";
			String uploadDir = baseDir;

			while (files.hasNext()) {
				String attachmentName = files.next().toString();
				String prefixStr = attachmentName + "_";

				MultipartFile multipartFile = multipartRequest
						.getFile(attachmentName);

				String originalFileName = multipartFile.getOriginalFilename();

				if (originalFileName.length() > 0) {

					String targetFileName = "";
					targetFileName = prefixStr + System.currentTimeMillis() + "";
					File target = new File(uploadDir, targetFileName);

					String targetPath = uploadDir + File.separator + targetFileName;
					String webPath = webDir + uploadDir.substring(baseDir.length(), uploadDir.length()) + File.separator + targetFileName;
					String thumbnailPath = uploadDir + File.separator + "thumb_" + targetFileName + ".jpg";
					String thumbnailWebPath = webDir + uploadDir.substring(baseDir.length(), uploadDir.length()) + File.separator + "thumb_" + targetFileName + ".jpg";

					multipartFile.transferTo(target);

					if ("mainBanner".equals(attachmentName)) {
						param.put("MAIN_BANNER_FILE_NAME", originalFileName);
						param.put("MAIN_BANNER_WEB_LINK", webPath);
					} else if ("centerBanner".equals(attachmentName)) {
						param.put("CENTER_BANNER_FILE_NAME", originalFileName);
						param.put("CENTER_BANNER_WEB_LINK", webPath);
					} else if ("facility_image".equals(attachmentName)) {
						Thumbnailer Thumbnailer = new Thumbnailer();
						Thumbnailer.createImageThumbnail(targetPath, thumbnailPath, 150, 99);

						param.put("P_FILE_NAME", originalFileName);
						param.put("L_FILE_NAME", webPath);
						param.put("THUMBNAIL_FILE_NAME", thumbnailWebPath);
					}
				}
			}
		}
	}

	public PaymentListEntity modifyPayment(HttpServletRequest req, Map param) throws Exception {
		setPageCondition(param);

		bannerUpload(req, param);
		adminDao.modifyPayment(param);

		updateBannerContext();

		return getPaymentListEntity(param);
	}

	public PaymentMasterEntity selectPaymentMaster(Map param) {
		return adminDao.selectPaymentMaster(param);
	}

	public PaymentListEntity userPaymentDelete(HttpServletRequest req, Map param)
			throws Exception {
		adminDao.userPaymentDelete(param);

		return getPaymentListEntity(param);
	}

	public List<Map> getPointLogList(Map param) {
		setPageCondition(param);

		return adminDao.selectPointLogList(param);
	}
	
	public PageListWrapper getUserPointLogList(Map param){
		setPageCondition(param);

		PageListWrapper listEntity = new PageListWrapper();

		try {
			Long pageNum = (Long) param.get("PAGE_NUM");
			Long pageSize = (Long) param.get("PAGE_SIZE");
			listEntity.setPageNum(pageNum);
			listEntity.setPageSize(pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int boardCount = adminDao.countPointLogList(param);
		listEntity.setTotalListCount(boardCount);

		List userPointLogList = adminDao.selectPointLogList(param);
		listEntity.setPageList(userPointLogList);

		return listEntity;
	}
	
	public PageListWrapper getUserLoginList(Map param) {
		PageListWrapper listEntity = new PageListWrapper();

		setPageCondition(param);

		try {
			Long pageNum = (Long) param.get("PAGE_NUM");
			Long pageSize = (Long) param.get("PAGE_SIZE");

			if (pageNum == null) {
				pageNum = new Long(1);
			}

			if (pageSize == null) {
				pageSize = new Long(20);
			}
			listEntity.setPageNum(pageNum);
			listEntity.setPageSize(pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Long userCount = adminDao.countUserLoginList(param);
		listEntity.setTotalListCount(userCount);

		List<Map> list = adminDao.getUserLoginList(param);
		listEntity.setPageList(list);

		return listEntity;
	}

	/**
	 * 유저 블랙 처리시 해당 사용자가 등록한 글과 덧글을 모두 삭제한다.
	 * @param string 블랙유저 seq_id
	 */
	public void deleteBlackUserBoardContents(String userSeqId) throws Exception{
		Map param = new HashMap();
		param.put("USER_SEQ_ID", userSeqId);
		param.put("DEL_YN", "Y");
		
		boardDao.deleteReplyByUserSeqId(param);
		
		this.boardUpdateDelYnByUserSeqId(param);
		
		
	}
	private void boardUpdateDelYnByUserSeqId(Map param) throws Exception {
		String tableName = "T_BOARD_BUSINESS_INFO";
		param.put("TABLE_NAME", tableName);
		boardDao.boardUpdateDelYnByUserSeqId(param);
		
		tableName = "T_BOARD_LINE_UP";
		param.put("TABLE_NAME", tableName);
		boardDao.boardUpdateDelYnByUserSeqId(param);
		
		tableName = "T_BOARD_POSTSCRIPT";
		param.put("TABLE_NAME", tableName);
		boardDao.boardUpdateDelYnByUserSeqId(param);
		
		tableName = "T_BOARD_COMMUNITY";
		param.put("TABLE_NAME", tableName);
		boardDao.boardUpdateDelYnByUserSeqId(param);
		
		tableName = "T_BOARD_CENTER";
		param.put("TABLE_NAME", tableName);
		boardDao.boardUpdateDelYnByUserSeqId(param);
	}
}
