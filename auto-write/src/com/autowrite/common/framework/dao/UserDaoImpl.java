package com.autowrite.common.framework.dao;

import com.autowrite.common.framework.entity.UserClassEntity;
import com.autowrite.common.framework.entity.UserEntity;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {
	
	@Autowired
	SqlMapClientTemplate sqlHelper;
	
	public UserDaoImpl() {
	}

	public void add(UserEntity vo) {
		try {
			sqlHelper.queryForObject("user.insert", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void add(Map param) throws Exception {
		sqlHelper.insert("member.tpcode.insert", param);
		String keys[] = {"U_ZIP_CODE", "U_ADDRESS1", "U_ADDRESS2", "U_ADDRESS3", "U_E_MAIL", "U_DEP_NAME", "U_POSITION", "U_TEL_NO", "U_FAX_NO", "U_PASSWORD"};
		param = convertKeyName(keys, param);
		param.put("U_TYPE", "PA");
		sqlHelper.insert("member.user.insert", param);
	}

	public void delete(String s) {
	}

	public UserEntity info(String uid) {
		return null;
	}

	public List list(UserEntity vo) {
		return null;
	}

	public UserEntity list(Map param) throws Exception {
		if (param.get("type") != null && param.get("type").equals("S"))
			return (UserEntity) sqlHelper.queryForObject("user.info.certificate_s", param);
		else
			return (UserEntity) sqlHelper.queryForObject("user.info.certificate", param);
	}

	public void update(UserEntity userentity) {
	}
	
	@Override
	public List checkDuplicateId(Map param) {
		return sqlHelper.queryForList("user.duplicated.id", param);
	}

	@Override
	public List checkDuplicateNic(Map param) {
		return sqlHelper.queryForList("user.duplicated.nic", param);
	}

	public List checkDuplicateRegicode(Map param) {
		return sqlHelper.queryForList("user.duplicated.regicode", param);
	}

	public List checkDuplicateTpcode(Map param) {
		return sqlHelper.queryForList("user.duplicated.tpcode", param);
	}

	private Map convertKeyName(String convertList[], Map param) {
		String value = null;
		String convertedKeyName = null;
		String as[];
		int j = (as = convertList).length;
		for (int i = 0; i < j; i++) {
			Object items = as[i];
			String keyName = (String) items;
			value = (String) param.get(keyName);
			convertedKeyName = keyName.substring(2);
			param.put(convertedKeyName, value);
		}

		return param;
	}

	public List getMyInfo(Map bean) {
		// System.out.println(bean);
		return sqlHelper.queryForList("user.myinfo.info", bean);
	}

	public void createUser(Map bean) throws Exception {
		sqlHelper.insert("member.user.create", bean);
	}

	public void delCompany(Map bean) throws Exception {
		sqlHelper.delete("user.company.delete", bean);
	}

	public List getCompanyList(Map bean) {
		return sqlHelper.queryForList("user.company.list", bean);
	}

	public List getCompanyInfo(Map bean) {
		// System.out.println(bean);
		return sqlHelper.queryForList("user.companyinfo.info", bean);
	}

	public void modifiedInfo(Map bean) {
		sqlHelper.update("user.modify", bean);
		sqlHelper.update("modifiedTP_CODE", bean);
	}

	public void modifiedMyInfo(Map bean) {
		sqlHelper.update("user.modify", bean);
	}

	public List getCompanyInfo_tp(Map bean) {
		return sqlHelper.queryForList("user.companyinfo_tp.info", bean);
	}

	public List getZipCodeList(Map bean) {
		return sqlHelper.queryForList("user.zipcode.info", bean);
	}

	public int approved(Map bean) {
		int approved_pre = ((Integer) sqlHelper.queryForObject("user.approved_pre", bean)).intValue();
		if (approved_pre == 0)
			sqlHelper.update("user.approved", bean);
		return approved_pre;
	}

	public Integer checkId(Map bean) {
		return (Integer) sqlHelper.queryForObject("user.checkId", bean);
	}

	public int suspends(Map bean) {
		int suspend_pre = ((Integer) sqlHelper.queryForObject("user.suspend_pre", bean)).intValue();
		// System.out.println(suspend_pre);
		if (suspend_pre == 0)
			sqlHelper.update("user.suspend", bean);
		return suspend_pre;
	}

	public int getWatingUser() {
		return ((Integer) sqlHelper.queryForObject("user.getWatingUser")).intValue();
	}

	@Override
	public int getUncheckedMemoCount(Map param) {
		return (Integer) sqlHelper.queryForObject("user.unckecked.memo.count", param);
	}

	@Override
	public void insertLoginLog(Map param) {
		sqlHelper.insert("user.login.log.insert", param);
	}

	@Override
	public int getVisitCountToday(Map param) {
		return ((Integer) sqlHelper.queryForObject("user.visit.today")).intValue();
	}

	@Override
	public int getVisitCountYesterday(Map param) {
		return ((Integer) sqlHelper.queryForObject("user.visit.yesterday")).intValue();
	}

	@Override
	public int getVisitCountTotal(Map param) {
		return ((Integer) sqlHelper.queryForObject("user.visit.total")).intValue();
	}

	@Override
	public List<UserClassEntity> selectUserClass() {
		return sqlHelper.queryForList("user.class.info.select");
	}

	@Override
	public void addQuickLink(Map param) {
		sqlHelper.insert("user.quick.link.insert", param);
	}

	@Override
	public List getQuickLink(Map param) {
		return sqlHelper.queryForList("user.quick.link.select", param);
	}

	@Override
	public int countQuickLink(Map param) {
		return (Integer) sqlHelper.queryForObject("user.quick.link.count", param);
	}
}
