package com.autowrite.common.framework.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.MenuDto;
import com.autowrite.common.framework.entity.PaymentMasterEntity;
import com.autowrite.common.framework.entity.UserEntity;

@Component
public class AdminDaoImpl implements AdminDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;

	public AdminDaoImpl() {
	}

	public void delete(String s) {
	}

	public UserEntity info(String uid) {
		return null;
	}

	public List list(Map param) {
		return sqlHelper.queryForList("admin.userMgnt.list", param);
	}

	public List acList(Map param) {
		return sqlHelper.queryForList("admin.accountMgnt.list", param);
	}

	public List getFiledSelectionAll(Map param) throws Exception {
		return sqlHelper.queryForList("admin.quickMenuSelection.all", param);
	}

	public List getMenuList(Map param) throws Exception {
		return sqlHelper.queryForList("admin.menuSelection.selected", param);
	}

	public List getMenuListAll(Map param) throws Exception {
		return sqlHelper.queryForList("admin.allMenu.selected", param);
	}
	
	@Override
	public List<MenuDto> getAdminMenuList(Map param) {
		return sqlHelper.queryForList("admin.menu.list.new", param);
	}

	@Override
	public MenuDto getAdminMenuEntity(Map param) {
		return (MenuDto) sqlHelper.queryForObject("admin.menu.info", param);
	}
	
	public List getVocChargerAll(Map param) throws Exception {
		return sqlHelper.queryForList("support.getVocCharger.all", param);
	}

	public List getFiledSelectionList(Map param) throws Exception {
		return sqlHelper.queryForList("admin.quickMenuSelection.selected.list", param);
	}

	public void removePreviousFieldSelection(Map param) throws Exception {
		sqlHelper.update("admin.quickMenuSelection.removeSelected", param);
	}

	public void insertCurrentFieldSelection(Map param) throws Exception {
		List field_names = (List) param.get("selectionSelected");
		Map tempMap = new HashMap();
		tempMap.put("U_ID", param.get("U_ID"));
		for (int i = 0; i < field_names.size(); i++) {
			tempMap.put("MENU_ID", field_names.get(i));
			sqlHelper.update("admin.quickMenuSelection.insertSelected", tempMap);
		}

	}

	public void setVocCharger(Map param) throws Exception {
		List field_names = (List) param.get("selectionSelected");
		Map tempMap = new HashMap();
		tempMap.put("SUBMIT_ID", param.get("SUBMIT_ID"));
		tempMap.put("REMARK", param.get("REMARK"));
		for (int i = 0; i < field_names.size(); i++) {
			tempMap.put("U_ID", field_names.get(i));
			sqlHelper.update("support.setVocCharger.updateSelected", tempMap);
		}

	}

	public void createPartner(Map bean) {
		sqlHelper.insert("admin.createPartner", bean);
	}

	public List getAccount(Map bean) {
		return sqlHelper.queryForList("admin.getAccount", bean);
	}

	public Map getAccountByIdt(String idt) {
		return (Map) sqlHelper.queryForObject("admin.getAccountByIdt", idt);
	}

	public void changePartner(Map bean) throws Exception {
		sqlHelper.update("changePartner", bean);
	}

	public List getMenu(Map bean) throws Exception {
		return sqlHelper.queryForList("admin.getMenu", bean);
	}

	public void createMenu(Map bean) throws Exception {
		sqlHelper.insert("admin.createMenu", bean);
	}

	public void deleteMenu(Map bean) throws Exception {
		sqlHelper.update("admin.deleteMenu", bean);
	}

	public Map getCode(String queryId, Map bean) throws Exception {
		return (Map) sqlHelper.queryForObject(queryId, bean);
	}

	public List getCodeList(String queryId, Map bean) throws Exception {
		return sqlHelper.queryForList(queryId, bean);
	}

	public void insertCode(String qid, Map bean) throws Exception {
		sqlHelper.insert(qid, bean);
	}

	public void updateCode(String qid, Map bean) throws Exception {
		sqlHelper.update(qid, bean);
	}

	public void deleteCode(String qid, Map bean) throws Exception {
		sqlHelper.delete(qid, bean);
	}

	@Override
	public Long countUserList(Map param) {
		return (Long) sqlHelper.queryForObject("admin.user.count", param);
	}

	@Override
	public List<UserEntity> getUserList(Map param) {
		return sqlHelper.queryForList("admin.user.list", param);
	}

	@Override
	public void registUser(Map param) {
		sqlHelper.insert("admin.user.register", param);
	}

	@Override
	public UserEntity getUserInfo(Map param) {
		return (UserEntity) sqlHelper.queryForObject("admin.user.view", param);
	}

	@Override
	public void modifyUser(Map param) {
		sqlHelper.insert("admin.user.modify", param);
	}

	@Override
	public void deleteDefaultMenu(Map param) {
		sqlHelper.insert("admin.menu.default.delete", param);
	}

	@Override
	public void setDefaultMenu(Map param) {
		sqlHelper.insert("admin.menu.default.insert", param);
	}

	@Override
	public List<UserEntity> getUserSearchList(Map param) {
		return sqlHelper.queryForList("admin.user.search.list", param);
	}

	@Override
	public List<BoardEntity> getUserBoardList(Map param) {
		return sqlHelper.queryForList("admin.user.board.list", param);
	}

	@Override
	public void writeUserAction(Map param) {
		sqlHelper.insert("admin.user.action.insert", param);
	}

	@Override
	public void writeOtherUserAction(Map param) {
		sqlHelper.insert("admin.other.user.action.insert", param);
	}

	@Override
	public void updateUserPoint(Map param) {
		sqlHelper.update("admin.user.point.update", param);
	}

	@Override
	public void addUserPoint(Map param) {
		sqlHelper.update("admin.user.point.add", param);
	}

	@Override
	public List<Map> getPointByAction(Map param) {
		return sqlHelper.queryForList("admin.point.master.select", param);
	}

	@Override
	public List<Map> getDefaultMenuList(Map param) {
		return sqlHelper.queryForList("admin.default.menu.select", param);
	}

	@Override
	public List<Map> getPointMasterList(Map param) {
		return sqlHelper.queryForList("admin.point.master.list", param);
	}

	@Override
	public int countUserLogin(Map param) {
		return (Integer) sqlHelper.queryForObject("admin.user.login.count", param);
	}

	@Override
	public int getTodayBoardCount(Map param) {
		return (Integer) sqlHelper.queryForObject("admin.user.today.board.count", param);
	}

	@Override
	public int getMenuAuthCountByUserType(Map param) {
		return (Integer) sqlHelper.queryForObject("admin.user.menu.auth.count", param);
	}

	@Override
	public List<PaymentMasterEntity> getPaymentMasterListForBanner() {
		return sqlHelper.queryForList("admin.payment.master.list.for.banner");
	}

	@Override
	public List<PaymentMasterEntity> getUserPaymentList(Map param) {
		return sqlHelper.queryForList("admin.payment.master.list", param);
	}

	@Override
	public int countUserPaymentList(Map param) {
		return (Integer) sqlHelper.queryForObject("admin.payment.master.list.count", param);
	}

	@Override
	public long writePayment(Map param) {
		long seqId = 0;
		
		try {
			seqId = (Long) sqlHelper.insert("admin.payment.master.insert", param);
		} catch ( Exception e ){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return seqId;
	}

	@Override
	public void modifyPayment(Map param) {
		sqlHelper.update("admin.user.payment.update", param);
	}

	@Override
	public PaymentMasterEntity selectPaymentMaster(Map param) {
		return (PaymentMasterEntity) sqlHelper.queryForObject("admin.user.payment.select", param);
	}

	@Override
	public void userPaymentDelete(Map param) {
		sqlHelper.delete("admin.user.payment.delete", param);
	}

	@Override
	public List<Map> selectPointLogList(Map param) {
		return sqlHelper.queryForList("admin.point.log.list", param);
	}

	@Override
	public int countPointLogList(Map param) {
		return (Integer) sqlHelper.queryForObject("admin.point.log.count", param);
	}

	@Override
	public Long countUserLoginList(Map param) {
		return (Long) sqlHelper.queryForObject("admin.user.login.list.count", param);
	}
	
	@Override
	public List<Map> getUserLoginList(Map param) {
		return sqlHelper.queryForList("admin.user.login.list", param);
	}

	@Override
	public void add(com.autowrite.common.framework.entity.UserEntity userentity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(
			com.autowrite.common.framework.entity.UserEntity userentity) {
		// TODO Auto-generated method stub
		
	}
}
