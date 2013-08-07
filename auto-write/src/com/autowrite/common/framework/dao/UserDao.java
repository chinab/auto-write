package com.autowrite.common.framework.dao;

import com.autowrite.common.framework.entity.UserClassEntity;
import com.autowrite.common.framework.entity.UserEntity;
import java.util.List;
import java.util.Map;

public interface UserDao {

	public abstract void add(UserEntity userentity);

	public abstract void add(Map map) throws Exception;

	public abstract void update(UserEntity userentity);

	public abstract void delete(String s);

	public abstract UserEntity info(String s);

	public abstract UserEntity list(Map map) throws Exception;

	public abstract List list(UserEntity userentity);

	public abstract List checkDuplicateId(Map map);

	public abstract List checkDuplicateNic(Map map);

	public abstract List checkDuplicateRegicode(Map map);

	public abstract List checkDuplicateTpcode(Map map);

	public abstract void createUser(Map map) throws Exception;

	public abstract void delCompany(Map map) throws Exception;

	public abstract List getMyInfo(Map map);

	public abstract List getCompanyList(Map map);

	public abstract List getCompanyInfo(Map map);

	public abstract void modifiedInfo(Map map);

	public abstract void modifiedMyInfo(Map map);

	public abstract List getCompanyInfo_tp(Map map);

	public abstract List getZipCodeList(Map map);

	public abstract int approved(Map map);

	public abstract int suspends(Map map);

	public abstract Integer checkId(Map map);

	public abstract int getWatingUser();

	public abstract int getUncheckedMemoCount(Map param);

	public abstract void insertLoginLog(Map param);

	public abstract int getVisitCountToday(Map param);

	public abstract int getVisitCountYesterday(Map param);

	public abstract int getVisitCountTotal(Map param);

	public abstract List<UserClassEntity> selectUserClass();

	public abstract void addQuickLink(Map param);

	public abstract List getQuickLink(Map param);

	public abstract int countQuickLink(Map param);
}
