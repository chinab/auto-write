package com.autowrite.common.framework.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autowrite.common.framework.dao.MemoDao;
import com.autowrite.common.framework.entity.MemoEntity;
import com.autowrite.common.framework.entity.MenuEntity;

@Component
public class MemoService {

	public MemoService() {
	}

	
	/**
	 * 메모 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<MemoEntity> listMemo(Map param) throws Exception {
		String selectedMenu = "";
		int startNum = 0;
		int pageSize = 20;
		
		if ( param.get("category") != null ){
			selectedMenu = param.get("category").toString();
		} else {
			throw new Exception("Menu code is not selected.");
		}
		
		String tableName = getTableNameAsMenuCode(selectedMenu);
		
		param.put("TABLE_NAME", tableName);
		
		String conditionUser = getCondionUserAsMenuCode(selectedMenu);
		
		param.put("CONDITION_USER", conditionUser);
		
		param.put("CATEGORY", selectedMenu);
		param.put("START_NUM", startNum);
		param.put("PAGE_SIZE", pageSize);
		
		List<MemoEntity> memoList = memoDao.listMemo(param);
		
		return memoList;
	}
	
	
	public List<MemoEntity> writeMemo(Map param) throws Exception {
		param.put("TABLE_NAME", getTableNameAsMenuCode("02"));
		Long seqId = memoDao.writeMemo(param);
		param.put("SEND_MESSAGE_SEQ_ID", seqId);
		
		param.put("TABLE_NAME", getTableNameAsMenuCode("01"));
		memoDao.writeMemo(param);
		
		return listMemo(param);
	}
	
	
	public MemoEntity readMemo(Map param) throws Exception {
		String menuCode = "";
		
		if ( param.get("CATEGORY") != null ){
			menuCode = param.get("CATEGORY").toString();
		} else {
			throw new Exception("Menu code is not selected.");
		}
		
		String tableName = getTableNameAsMenuCode(menuCode);
		
		param.put("TABLE_NAME", tableName);
		
		String conditionUser = getCondionUserAsMenuCode(menuCode);
		
		param.put("CONDITION_USER", conditionUser);
		
		return memoDao.readMemo(param);
	}
	
	
	/**
	 * 메뉴코드로 테이블명을 가져온다.
	 * @param selectedMenu
	 * @return
	 * @throws Exception 
	 */
	private String getTableNameAsMenuCode(String selectedMenu) throws Exception {
		// TODO : DB화 시킬것
		if ( selectedMenu.startsWith("01") ) {
			return "T_MESSAGE_RECEIVED";
		} else if ( selectedMenu.startsWith("02") ) {
			return "T_MESSAGE_SEND";
		} else if ( selectedMenu.startsWith("03") ) {
			return "T_MESSAGE_DELETED";
		} else if ( selectedMenu.startsWith("04") ) {
			return "T_MESSAGE_RESERVED";
		} else {
			throw new Exception ("No such menu code start with [" + selectedMenu + "]");
		}
	}


	/**
	 * 메뉴코드로 조건식에 들어갈 유저종류(보낸이, 받는이)를 선택한다.
	 * @param selectedMenu
	 * @return
	 * @throws Exception 
	 */
	private String getCondionUserAsMenuCode(String selectedMenu) {
		if ( selectedMenu.startsWith("02") ) {
			return "SND_USER_SEQ_ID";
		} else if ( selectedMenu.startsWith("01") ){
			return "RCV_USER_SEQ_ID";
		} else if ( selectedMenu.startsWith("03") ){
			return "MV_USER_SEQ_ID";
		} else if ( selectedMenu.startsWith("04") ){
			return "MV_USER_SEQ_ID";
		} else {
			return "RCV_USER_SEQ_ID";
		}
	}


	@Autowired
	MemoDao memoDao;


	public List<MemoEntity> deleteMemo(Map param) throws Exception {
		String tableName = "";
		String category = "";
		
		if ( param.get("category") != null ){
			category = param.get("category").toString();
		} else {
			throw new Exception("category is not selected.");
		}
		
		tableName = getTableNameAsMenuCode(category);
		param.put("TABLE_NAME", tableName);
		param.put("TARGET_TABLE_NAME", "T_MESSAGE_DELETED");
		
		if ( "03".equals(category) ){
			memoDao.deleteMemo(param);
		} else {
			memoDao.moveMemo(param);
			memoDao.deleteMemo(param);
		}
		
		return listMemo(param);
	}
	
	
	public List<MemoEntity> reserveMemo(Map param) throws Exception {
		String tableName = "";
		String category = "";
		
		if ( param.get("category") != null ){
			category = param.get("category").toString();
		} else {
			throw new Exception("category is not selected.");
		}
		
		tableName = getTableNameAsMenuCode(category);
		param.put("TABLE_NAME", tableName);
		param.put("TARGET_TABLE_NAME", "T_MESSAGE_RESERVED");
		
		memoDao.moveMemo(param);
		memoDao.deleteMemo(param);
		
		return listMemo(param);
	}


	public void updateReadFlag(Map param) throws Exception {
		String menuCode = "";
		
		if ( param.get("CATEGORY") != null ){
			menuCode = param.get("CATEGORY").toString();
		} else {
			throw new Exception("Menu code is not selected.");
		}
		
		if ( "01".equals(menuCode) ){
			memoDao.updateReceivedMessageReadFlag(param);
			memoDao.updateSentMessageReadFlag(param);
		} else {
			return;
		}
		param.put("CONDITION_USER", "RCV_USER_SEQ_ID");
		
		
//		return memoDao.readMemo(param);
	}
}
