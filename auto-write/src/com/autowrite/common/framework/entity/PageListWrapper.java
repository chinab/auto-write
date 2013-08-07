package com.autowrite.common.framework.entity;

import java.util.List;
import java.util.Map;

public class PageListWrapper {
	protected List pageList;
	
	protected long totalListCount;
	protected long pageNum;
	protected long totalPageNum;
	protected long pageSize;
	
	protected String searchKey;
	protected String searchValue;
	
	protected String pagination;
	
	protected String regionTab;
	
	public PageListWrapper(){
		totalListCount = 0;
		totalPageNum = 1;
		pageNum = 1;
		pageSize = 20;
	}

	public List getPageList() {
		return pageList;
	}

	public void setPageList(List pageList) {
		this.pageList = pageList;
	}
	
	public void setSearchCondtion(Map param) {
		if ( param.get("TYPE_CODE") != null ) {
			searchKey = param.get("TYPE_CODE").toString();
		}
		if ( param.get("STATUS_CODE") != null ) {
			searchKey = param.get("STATUS_CODE").toString();
		}
		if ( param.get("SERVICE_CODE") != null ) {
			searchKey = param.get("SERVICE_CODE").toString();
		}
		if ( param.get("SEARCH_KEY") != null ) {
			searchKey = param.get("SEARCH_KEY").toString();
		}
		if ( param.get("SEARCH_VALUE") != null ) {
			searchKey = param.get("SEARCH_VALUE").toString();
		}
	}
	
	public long getStartSequenceNumber(){
		long startSequenceNumber = 10;
		startSequenceNumber = totalListCount - (pageNum-1)*pageSize;
		return startSequenceNumber;
	}

	public String getPagination() {
		return pagination;
	}
	public String getPagination(String functionName, String category) {
		setPagination(functionName, category, this.pageNum);
		return pagination;
	}
	public String getPagination(String functionName, String category, long pageNum) {
		setPagination(functionName, category, pageNum);
		return pagination;
	}
	public String getPagination(String functionName, String category, String region) {
		setPagination(functionName, category, region, this.pageNum);
		return pagination;
	}
	public String getPagination(String functionName, String category, String region, long pageNum) {
		setPagination(functionName, category, region, pageNum);
		return pagination;
	}
	public void setPagination(String functionName, String category, long pageNum) {
		
		StringBuffer sb = new StringBuffer();
		
		totalPageNum = totalListCount/pageSize;
		if ( totalListCount%pageSize > 0 ){
			totalPageNum++;
		}
		
		sb.append(getPageRef(functionName, category, pageNum));
		sb.append("<img src='./images/btn_pagingFirst.gif'></a>&nbsp;&nbsp;&nbsp;");
		
		if ( pageNum > 10 ) {
			sb.append(getPageRef(functionName, category, pageNum - 10));
			sb.append("<img src='./images/btn_pagingPrev.gif'></a>&nbsp;&nbsp;&nbsp;");
		}
		
		sb.append("<span class='gray'>");
		
		long startPage = pageNum/10%10 + 1;
		
		for ( long ii = startPage ; ii < startPage + 10 ; ii ++ ){
			sb.append(getPageRef(functionName, category, ii));
			if ( pageNum == ii ){
				sb.append("<b>");
				sb.append(ii);
				sb.append("</b>");
			} else {
				sb.append(ii);
			}
			sb.append("</a>&nbsp;&nbsp;");
			if ( ii == totalPageNum ){
				break;
			}
		}
		sb.append("</span>&nbsp;");
		if ( totalPageNum/10 != pageNum/10 ) {
			sb.append(getPageRef(functionName, category, pageNum + 10));
			sb.append("<img src='./images/btn_pagingNext.gif'></a>&nbsp;&nbsp;&nbsp;");
		}
		
		sb.append(getPageRef(functionName, category, totalPageNum));
		sb.append("<img src='./images/btn_pagingEnd.gif'></a>");
		
		this.pagination = sb.toString();
	}
	protected StringBuffer getPageRef(String functionName, String category, long pageNum) {
		StringBuffer functionSb = new StringBuffer();
		
		functionSb.append("<a href=\"");
		functionSb.append("javascript:");
		functionSb.append(functionName);
		functionSb.append("(\'");
		functionSb.append(category);
		functionSb.append("\', \'");
		functionSb.append(pageNum);
		functionSb.append("\');\">");
		
		return functionSb;
	}
	public void setPagination(String functionName, String category, String region, long pageNum) {
		
		StringBuffer sb = new StringBuffer();
		
		totalPageNum = totalListCount/pageSize;
		if ( totalListCount%pageSize > 0 ){
			totalPageNum++;
		}
		
		sb.append(getPageRef(functionName, category, region, pageNum));
		sb.append("<img src='./images/btn_pagingFirst.gif'></a>&nbsp;&nbsp;&nbsp;");
		
		if ( pageNum > 10 ) {
			sb.append(getPageRef(functionName, category, region, pageNum - 10));
			sb.append("<img src='./images/btn_pagingPrev.gif'></a>&nbsp;&nbsp;&nbsp;");
		}
		
		sb.append("<span class='gray'>");
		
		long startPage = pageNum/10%10 + 1;
		
		for ( long ii = startPage ; ii < startPage + 10 ; ii ++ ){
			sb.append(getPageRef(functionName, category, region, ii));
			if ( pageNum == ii ){
				sb.append("<b>");
				sb.append(ii);
				sb.append("</b>");
			} else {
				sb.append(ii);
			}
			sb.append("</a>&nbsp;&nbsp;");
			if ( ii == totalPageNum ){
				break;
			}
		}
		sb.append("</span>&nbsp;");
		if ( totalPageNum/10 != pageNum/10 ) {
			sb.append(getPageRef(functionName, category, region, pageNum + 10));
			sb.append("<img src='./images/btn_pagingNext.gif'></a>&nbsp;&nbsp;&nbsp;");
		}
		
		sb.append(getPageRef(functionName, category, region, totalPageNum));
		sb.append("<img src='./images/btn_pagingEnd.gif'></a>");
		
		this.pagination = sb.toString();
	}
	public String getRegionTab() {
		return regionTab;
	}
	public String getRegionTab(String category, String region) {
		setRegionTab(category, region);
		
		return regionTab;
	}
	
	public void setRegionTab(String regionTab) {
		this.regionTab = regionTab;
	}

	public void setRegionTab(String category, String region) {
		StringBuffer sb = new StringBuffer();
		
		String[] regions = {"전지역", "강남", "강남외", "수원권", "인부천", "분당권", "일산권", "경기권", "지방권"};
		
		if ( category.startsWith("02") ||  category.startsWith("03") ||  category.startsWith("04") ){
			sb.append("<div id=\"Summary-tab\">\n");
			for ( int ii = 0; ii < regions.length ; ii ++ ) {
				sb.append("<a href=\"javascript:listBoard('");
				sb.append(category);
				sb.append("', '");
				sb.append(regions[ii]);
				sb.append("', '");
				sb.append(this.getPageNum());
				sb.append("');\"");
				if ( regions[ii].equals(region) ){
					sb.append(" class='tab-ov'");
				} else {
					sb.append(" class='tab'");
				}
				sb.append(">");
				sb.append(regions[ii]);
				sb.append("</a>");
			}
			sb.append("</div>\n");
			sb.append("	<br/><br/>");
		}
		
		this.regionTab = sb.toString();
	}

	protected StringBuffer getPageRef(String functionName, String category, String region, long pageNum) {
		StringBuffer functionSb = new StringBuffer();
		
		functionSb.append("<a href=\"");
		functionSb.append("javascript:");
		functionSb.append("listBoard(\'");
		functionSb.append(category);
		functionSb.append("\', \'");
		functionSb.append(region);
		functionSb.append("\', \'");
		functionSb.append(pageNum);
		functionSb.append("\');\">");
		
		return functionSb;
	}

	public long getTotalListCount() {
		return totalListCount;
	}
	public void setTotalListCount(long totalListCount) {
		this.totalListCount = totalListCount;
	}
	public long getPageNum() {
		return pageNum;
	}
	public void setPageNum(long pageNum) {
		this.pageNum = pageNum;
	}
	public long getTotalPageNum() {
		return totalPageNum;
	}
	public void setTotalPageNum(long totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public String writeSearchKey() {
		if ( searchKey == null ){
			searchKey = "";
		}
		return searchKey;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String writeSearchValue() {
		if ( searchValue == null ){
			searchValue = "";
		}
		return searchValue;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
}
