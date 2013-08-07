package com.autowrite.common.framework.entity;

import java.util.ArrayList;
import java.util.List;

import com.autowrite.common.util.DateUtil;

public class ConditionEntity {
    /**
     * 조회기간 종류
     */
    protected List<String> searchTerm = new ArrayList<String>();

    /**
     * 조회조건 종류
     */
    protected List<String> searchField = new ArrayList<String>();

    /**
     * 조회기간 시작일
     */
    protected String searchStartDt = DateUtil.getDateFormat("yy") + DateUtil.getDateFormat("MM");

    /**
     * 조회기간 종료일
     */
    protected String searchEndDt = DateUtil.getToday();

    /**
     * 조회조건 검색어
     */
    protected String searchKeyword = "";
    
	/**
     * 현재 페이지
     */
    protected int pageNumber = 1;
    
    /**
     * 페이지 사이즈
     */
    protected int pageSize = 20;

	public List<String> getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(List<String> searchTerm) {
		this.searchTerm = searchTerm;
	}

	public List<String> getSearchField() {
		return searchField;
	}

	public void setSearchField(List<String> searchField) {
		this.searchField = searchField;
	}

	public String getSearchStartDt() {
		return searchStartDt;
	}

	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}

	public String getSearchEndDt() {
		return searchEndDt;
	}

	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
