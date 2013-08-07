package com.autowrite.common.framework.entity;

import java.util.List;
import java.util.Map;

public class BoardMainEntity {
	public BoardMainEntity(){
		
	}
	
	List<BoardEntity> noticeNew;
	List<BoardEntity> noticeRecommend;
	List<BoardEntity> noticeHit;
	
	List<BoardEntity> businessInfoNew;
	List<BoardEntity> businessInfoRecommend;
	List<BoardEntity> businessInfoHit;
	
	List<BoardEntity> lineUpNew;
	List<BoardEntity> lineUpRecommend;
	List<BoardEntity> lineUpHit;
	
	List<BoardEntity> postscriptNew;
	List<BoardEntity> postscriptRecommend;
	List<BoardEntity> postscriptHit;
	
	List<BoardEntity> communityNew;
	List<BoardEntity> communityRecommend;
	List<BoardEntity> communityHit;
	
	List<BoardEntity> centerNew;
	List<BoardEntity> centerRecommend;
	List<BoardEntity> centerHit;
	
	List<BoardEntity> myBoard;
	
	List<BoardEntity> eventBoard;
	
	List<PaymentMasterEntity> recommendedBusinessInfo;			// center tab1
	
	Map<String, List<BoardEntity>> allCategoriedBusinessInfo;	// center-left new business info
	Map<String, List<BoardEntity>> categoriedBusinessInfo;		// center tab2
	Map<String, List<BoardEntity>> categoriedLineUp;			// center tab3
	Map<String, List<BoardEntity>> categoriedPostscript;		// center tab4
	
	public void setList(String tableName, List<BoardEntity> list, String string) {
		if ( "T_BOARD_NOTICE".equals(tableName) && "HIT".equals(string) ){
			this.noticeHit = list;
		} else if ( "T_BOARD_BUSINESS_INFO".equals(tableName) && "HIT".equals(string) ){
			this.businessInfoHit = list;
		} else if ( "T_BOARD_LINE_UP".equals(tableName) && "HIT".equals(string) ){
			this.lineUpHit = list;
		} else if ( "T_BOARD_POSTSCRIPT".equals(tableName) && "HIT".equals(string) ){
			this.postscriptHit = list;
		} else if ( "T_BOARD_COMMUNITY".equals(tableName) && "HIT".equals(string) ){
			this.communityHit = list;
		} else if ( "T_BOARD_CENTER".equals(tableName) && "HIT".equals(string) ){
			this.centerHit = list;
		} else if ( "T_BOARD_NOTICE".equals(tableName) && "NEW".equals(string) ){
			this.noticeNew = list;
		} else if ( "T_BOARD_BUSINESS_INFO".equals(tableName) && "NEW".equals(string) ){
			this.businessInfoNew = list;
		} else if ( "T_BOARD_LINE_UP".equals(tableName) && "NEW".equals(string) ){
			this.lineUpNew = list;
		} else if ( "T_BOARD_POSTSCRIPT".equals(tableName) && "NEW".equals(string) ){
			this.postscriptNew = list;
		} else if ( "T_BOARD_COMMUNITY".equals(tableName) && "NEW".equals(string) ){
			this.communityNew = list;
		} else if ( "T_BOARD_CENTER".equals(tableName) && "NEW".equals(string) ){
			this.centerNew = list;
		} else if ( "T_BOARD_NOTICE".equals(tableName) && "RECOMMEND".equals(string) ){
			this.noticeRecommend = list;
		} else if ( "T_BOARD_BUSINESS_INFO".equals(tableName) && "RECOMMEND".equals(string) ){
			this.businessInfoRecommend = list;
		} else if ( "T_BOARD_LINE_UP".equals(tableName) && "RECOMMEND".equals(string) ){
			this.lineUpRecommend = list;
		} else if ( "T_BOARD_POSTSCRIPT".equals(tableName) && "RECOMMEND".equals(string) ){
			this.postscriptRecommend = list;
		} else if ( "T_BOARD_COMMUNITY".equals(tableName) && "RECOMMEND".equals(string) ){
			this.communityRecommend = list;
		} else if ( "T_BOARD_CENTER".equals(tableName) && "RECOMMEND".equals(string) ){
			this.centerRecommend = list;
		}
	}

	public List<BoardEntity> getNoticeNew() {
		return noticeNew;
	}

	public void setNoticeNew(List<BoardEntity> noticeNew) {
		this.noticeNew = noticeNew;
	}

	public List<BoardEntity> getNoticeRecommend() {
		return noticeRecommend;
	}

	public void setNoticeRecommend(List<BoardEntity> noticeRecommend) {
		this.noticeRecommend = noticeRecommend;
	}

	public List<BoardEntity> getNoticeHit() {
		return noticeHit;
	}

	public void setNoticeHit(List<BoardEntity> noticeHit) {
		this.noticeHit = noticeHit;
	}

	public List<BoardEntity> getBusinessInfoNew() {
		return businessInfoNew;
	}

	public void setBusinessInfoNew(List<BoardEntity> businessInfoNew) {
		this.businessInfoNew = businessInfoNew;
	}

	public List<BoardEntity> getBusinessInfoRecommend() {
		return businessInfoRecommend;
	}

	public void setBusinessInfoRecommend(List<BoardEntity> businessInfoRecommend) {
		this.businessInfoRecommend = businessInfoRecommend;
	}

	public List<BoardEntity> getBusinessInfoHit() {
		return businessInfoHit;
	}

	public void setBusinessInfoHit(List<BoardEntity> businessInfoHit) {
		this.businessInfoHit = businessInfoHit;
	}

	public List<BoardEntity> getLineUpNew() {
		return lineUpNew;
	}

	public void setLineUpNew(List<BoardEntity> lineUpNew) {
		this.lineUpNew = lineUpNew;
	}

	public List<BoardEntity> getLineUpRecommend() {
		return lineUpRecommend;
	}

	public void setLineUpRecommend(List<BoardEntity> lineUpRecommend) {
		this.lineUpRecommend = lineUpRecommend;
	}

	public List<BoardEntity> getLineUpHit() {
		return lineUpHit;
	}

	public void setLineUpHit(List<BoardEntity> lineUpHit) {
		this.lineUpHit = lineUpHit;
	}

	public List<BoardEntity> getPostscriptNew() {
		return postscriptNew;
	}

	public void setPostscriptNew(List<BoardEntity> postscriptNew) {
		this.postscriptNew = postscriptNew;
	}

	public List<BoardEntity> getPostscriptRecommend() {
		return postscriptRecommend;
	}

	public void setPostscriptRecommend(List<BoardEntity> postscriptRecommend) {
		this.postscriptRecommend = postscriptRecommend;
	}

	public List<BoardEntity> getPostscriptHit() {
		return postscriptHit;
	}

	public void setPostscriptHit(List<BoardEntity> postscriptHit) {
		this.postscriptHit = postscriptHit;
	}

	public List<BoardEntity> getCommunityNew() {
		return communityNew;
	}

	public void setCommunityNew(List<BoardEntity> communityNew) {
		this.communityNew = communityNew;
	}

	public List<BoardEntity> getCommunityRecommend() {
		return communityRecommend;
	}

	public void setCommunityRecommend(List<BoardEntity> communityRecommend) {
		this.communityRecommend = communityRecommend;
	}

	public List<BoardEntity> getCommunityHit() {
		return communityHit;
	}

	public void setCommunityHit(List<BoardEntity> communityHit) {
		this.communityHit = communityHit;
	}

	public List<BoardEntity> getCenterNew() {
		return centerNew;
	}

	public void setCenterNew(List<BoardEntity> centerNew) {
		this.centerNew = centerNew;
	}

	public List<BoardEntity> getCenterRecommend() {
		return centerRecommend;
	}

	public void setCenterRecommend(List<BoardEntity> centerRecommend) {
		this.centerRecommend = centerRecommend;
	}

	public List<BoardEntity> getCenterHit() {
		return centerHit;
	}

	public void setCenterHit(List<BoardEntity> centerHit) {
		this.centerHit = centerHit;
	}

	public List<BoardEntity> getMyBoard() {
		return myBoard;
	}

	public void setMyBoard(List<BoardEntity> myBoard) {
		this.myBoard = myBoard;
	}

	public List<BoardEntity> getEventBoard() {
		return eventBoard;
	}

	public void setEventBoard(List<BoardEntity> eventBoard) {
		this.eventBoard = eventBoard;
	}

	public List<PaymentMasterEntity> getRecommendedBusinessInfo() {
		return recommendedBusinessInfo;
	}

	public void setRecommendedBusinessInfo(List<PaymentMasterEntity> recommendedBusinessInfo) {
		this.recommendedBusinessInfo = recommendedBusinessInfo;
	}

	public Map<String, List<BoardEntity>> getAllCategoriedBusinessInfo() {
		return allCategoriedBusinessInfo;
	}

	public void setAllCategoriedBusinessInfo(Map<String, List<BoardEntity>> allCategoriedBusinessInfo) {
		this.allCategoriedBusinessInfo = allCategoriedBusinessInfo;
	}

	public Map<String, List<BoardEntity>> getCategoriedBusinessInfo() {
		return categoriedBusinessInfo;
	}

	public void setCategoriedBusinessInfo(Map<String, List<BoardEntity>> categoriedBusinessInfo) {
		this.categoriedBusinessInfo = categoriedBusinessInfo;
	}

	public Map<String, List<BoardEntity>> getCategoriedLineUp() {
		return categoriedLineUp;
	}

	public void setCategoriedLineUp(Map<String, List<BoardEntity>> categoriedLineUp) {
		this.categoriedLineUp = categoriedLineUp;
	}

	public Map<String, List<BoardEntity>> getCategoriedPostscript() {
		return categoriedPostscript;
	}

	public void setCategoriedPostscript(Map<String, List<BoardEntity>> categoriedPostscript) {
		this.categoriedPostscript = categoriedPostscript;
	}
}
