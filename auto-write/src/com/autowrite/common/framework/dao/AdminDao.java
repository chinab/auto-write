package com.autowrite.common.framework.dao;

import com.autowrite.common.framework.entity.BoardEntity;
import com.autowrite.common.framework.entity.MenuDto;
import com.autowrite.common.framework.entity.MenuEntity;
import com.autowrite.common.framework.entity.PaymentMasterEntity;
import com.autowrite.common.framework.entity.UserEntity;
import com.autowrite.common.framework.entity.UserListEntity;

import java.util.List;
import java.util.Map;

public interface AdminDao {

	public abstract void add(UserEntity userentity);

	public abstract void update(UserEntity userentity);

	public abstract void delete(String s);

	public abstract UserEntity info(String s);

	public abstract List list(Map map);

	public abstract List getFiledSelectionAll(Map map) throws Exception;

	public abstract List getMenuList(Map map) throws Exception;

	public abstract List getMenuListAll(Map map) throws Exception;

	public abstract List getVocChargerAll(Map map) throws Exception;

	public abstract List getFiledSelectionList(Map map) throws Exception;

	public abstract void removePreviousFieldSelection(Map map) throws Exception;

	public abstract void insertCurrentFieldSelection(Map map) throws Exception;

	public abstract void setVocCharger(Map map) throws Exception;

	public abstract void createPartner(Map map) throws Exception;

	public abstract List getAccount(Map map);

	public abstract Map getAccountByIdt(String s);

	public abstract void changePartner(Map map) throws Exception;

	public abstract List getMenu(Map map) throws Exception;

	public abstract void createMenu(Map map) throws Exception;

	public abstract void deleteMenu(Map map) throws Exception;

	public abstract List getCodeList(String s, Map map) throws Exception;

	public abstract Map getCode(String s, Map map) throws Exception;

	public abstract void insertCode(String s, Map map) throws Exception;

	public abstract void updateCode(String s, Map map) throws Exception;

	public abstract void deleteCode(String s, Map map) throws Exception;

	public abstract List<UserEntity> getUserList(Map param);

	public abstract void registUser(Map param);

	public abstract UserEntity getUserInfo(Map param);

	public abstract void modifyUser(Map param);

	public abstract void setDefaultMenu(Map param);

	public abstract List<UserEntity> getUserSearchList(Map param);

	public abstract List<BoardEntity> getUserBoardList(Map param);

	public abstract List<MenuDto> getAdminMenuList(Map param);

	public abstract MenuDto getAdminMenuEntity(Map param);

	public abstract void writeUserAction(Map param);

	public abstract void writeOtherUserAction(Map param);

	public abstract void updateUserPoint(Map param);

	public abstract void addUserPoint(Map param);

	public abstract List<Map> getPointByAction(Map param);

	public abstract Long countUserList(Map param);

	public abstract List<Map> getDefaultMenuList(Map param);

	public abstract List<Map> getPointMasterList(Map param);

	public abstract int countUserLogin(Map param);

	public abstract int getTodayBoardCount(Map param);

	public abstract int getMenuAuthCountByUserType(Map param);

	public abstract List<PaymentMasterEntity> getUserPaymentList(Map param);

	public abstract int countUserPaymentList(Map param);

	public abstract long writePayment(Map param);

	public abstract void modifyPayment(Map param);

	public abstract PaymentMasterEntity selectPaymentMaster(Map param);

	public abstract void userPaymentDelete(Map param);

	public abstract void deleteDefaultMenu(Map param);
	
	public abstract List<Map> selectPointLogList(Map param);

	public abstract int countPointLogList(Map param);
	
	public abstract List<PaymentMasterEntity> getPaymentMasterListForBanner();

	public abstract Long countUserLoginList(Map param);

	public abstract List<Map> getUserLoginList(Map param);
}
