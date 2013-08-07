package com.autowrite.common.framework.entity;

import java.util.Calendar;
import java.util.List;

/**
 * @author KSH
 * 
 */
public class BoardEntity {
	public static final String UserSessionKey = "userSessionKey";
	
	private UserEntity userInfo;
	
	private List<ReplyEntity> replyList; 
	private List<AttachmentEntity> attachmentList;
	
	private String table_name;
	
	private String seq_id;
	private String user_seq_id;
	private String user_id;
	private String user_nic;
	private String title;
	private String content;
	private String category;
	private String target_category;
	private String region;
	private String hit;
	private String recommend;
	private String reject;
	private String secret_yn;
	private String blind_yn;
	private String del_yn;
	private String writer_ip;
	private String write_datetime;
	
	private String facility_name;
	private String facility_user_name;
	private String facility_phone;
	private String facility_address;
	private String facility_home_page;
	private String visit_time;
	private String waiteress_name;
	
	private String black_user_nic;
	private String black_user_phone;
	private String black_user_phone1;
	private String black_user_phone2;
	private String black_user_phone3;
	
	private String writer_point;
	private String writer_type_code;
	private String writer_image_path;
	
	private String reply_cnt;
	
	private int pageNum = 0;
	
	private ConditionEntity conditionInfo;
	
	String userControlBox;
	
	public UserEntity getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserEntity userInfo) {
		this.userInfo = userInfo;
	}
	public List<ReplyEntity> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<ReplyEntity> replyList) {
		this.replyList = replyList;
	}
	public List<AttachmentEntity> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<AttachmentEntity> attachmentList) {
		this.attachmentList = attachmentList;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}
	public String getUser_seq_id() {
		return user_seq_id;
	}
	public void setUser_seq_id(String user_seq_id) {
		this.user_seq_id = user_seq_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_nic() {
		return user_nic;
	}
	public void setUser_nic(String user_nic) {
		this.user_nic = user_nic;
	}
	public String getTitleLink(int titleLen) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href = \"javascript:readBoard('");
		sb.append(seq_id);
		sb.append("', '");
		sb.append(category);
		sb.append("', '");
		sb.append(region);
		sb.append("');\">");
		sb.append(getTitle(titleLen));
		sb.append("</a>");
		
		return sb.toString();
	}
	public String getLink(String linkStr) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href = \"javascript:readBoard('");
		sb.append(seq_id);
		sb.append("', '");
		sb.append(category);
		sb.append("', '");
		sb.append(region);
		sb.append("');\">");
		sb.append(linkStr);
		sb.append("</a>");
		
		return sb.toString();
	}
	public String getTitle() {
		return title;
	}
	public String getTitle(int length) {
		if ( title.length() > length ){
			return title.substring(0, length) + "...";
		} else {
			return title;
		}
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public String getContent(int length) {
		if ( content.length() > length ){
			return content.substring(0, length) + "...";
		} else {
			return content;
		}
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTarget_category() {
		return target_category;
	}
	public void setTarget_category(String target_category) {
		this.target_category = target_category;
	}
	public String writeRegion() {
		if ( region == null ) {
			return "";
		} else {
			return region;
		}
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getHit() {
		return hit;
	}
	public void setHit(String hit) {
		this.hit = hit;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getReject() {
		return reject;
	}
	public void setReject(String reject) {
		this.reject = reject;
	}
	public String getSecret_yn() {
		return secret_yn;
	}
	public void setSecret_yn(String secret_yn) {
		this.secret_yn = secret_yn;
	}
	public String getBlind_yn() {
		return blind_yn;
	}
	public void setBlind_yn(String blind_yn) {
		this.blind_yn = blind_yn;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public String getWriter_ip() {
		return writer_ip;
	}
	public void setWriter_ip(String writer_ip) {
		this.writer_ip = writer_ip;
	}
	public String getWrite_datetime() {
		return write_datetime;
	}
	public void setWrite_datetime(String write_datetime) {
		this.write_datetime = write_datetime;
	}
	
	
	public String getWriteBoardDateTime() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		String monthStr = null;
		String dayStr = null;
		
		if ( month < 10 ) {
			monthStr = "0" + month;
		} else {
			monthStr = "" + month;
		}
		
		if ( day < 10 ) {
			dayStr = "0" + day;
		} else {
			dayStr = "" + day;
		}
		
		String dbDate = write_datetime.substring(0, 10);
		String sysDate = year + "-" + monthStr + "-" + dayStr;
		
		if ( dbDate.equals(sysDate) ) {
			return write_datetime.substring(11, 16);
		} else {
			if ( new Integer(write_datetime.substring(0, 4)) == year ) {
				return write_datetime.substring(5, 10);
			} else {
				return dbDate;
			}
		}
	}
	
	public String getFacility_name() {
		return facility_name;
	}
	public void setFacility_name(String facility_name) {
		this.facility_name = facility_name;
	}
	public String getFacility_user_name() {
		return facility_user_name;
	}
	public void setFacility_user_name(String facility_user_name) {
		this.facility_user_name = facility_user_name;
	}
	public String getFacility_phone() {
		return facility_phone;
	}
	public void setFacility_phone(String facility_phone) {
		this.facility_phone = facility_phone;
	}
	public String getFacility_address() {
		return facility_address;
	}
	public void setFacility_address(String facility_address) {
		this.facility_address = facility_address;
	}
	public String getFacility_home_page() {
		return facility_home_page;
	}
	public void setFacility_home_page(String facility_home_page) {
		this.facility_home_page = facility_home_page;
	}
	public String getVisit_time() {
		return visit_time;
	}
	public void setVisit_time(String visit_time) {
		this.visit_time = visit_time;
	}
	public String getWaiteress_name() {
		return waiteress_name;
	}
	public void setWaiteress_name(String waiteress_name) {
		this.waiteress_name = waiteress_name;
	}
	public String getWebReply_cnt() {
		
		if ( reply_cnt == null || reply_cnt.length() == 0 || "0".equals(reply_cnt) ){
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("&nbsp;<font color='red'><b>(");
			sb.append(reply_cnt);
			sb.append(")</b></font>");
			return sb.toString();
		}
	}
	public String getReply_cnt() {
		return reply_cnt;
	}
	public void setReply_cnt(String reply_cnt) {
		this.reply_cnt = reply_cnt;
	}
	public String getBlack_user_nic() {
		return black_user_nic;
	}
	public void setBlack_user_nic(String black_user_nic) {
		this.black_user_nic = black_user_nic;
	}
	public String getBlack_user_phone() {
		return black_user_phone;
	}
	public void setBlack_user_phone(String black_user_phone) {
		this.black_user_phone = black_user_phone;
	}
	public String getBlack_user_phone1() {
		return black_user_phone1;
	}
	public void setBlack_user_phone1(String black_user_phone1) {
		this.black_user_phone1 = black_user_phone1;
	}
	public String getBlack_user_phone2() {
		return black_user_phone2;
	}
	public void setBlack_user_phone2(String black_user_phone2) {
		this.black_user_phone2 = black_user_phone2;
	}
	public String getBlack_user_phone3() {
		return black_user_phone3;
	}
	public void setBlack_user_phone3(String black_user_phone3) {
		this.black_user_phone3 = black_user_phone3;
	}
	public String getWriter_point() {
		return writer_point;
	}
	public void setWriter_point(String writer_point) {
		this.writer_point = writer_point;
	}
	public String getWriter_type_code() {
		return writer_type_code;
	}
	public void setWriter_type_code(String writer_type_code) {
		this.writer_type_code = writer_type_code;
	}
	public String getWriter_image_path() {
		return writer_image_path;
	}
	public void setWriter_image_path(String writer_image_path) {
		this.writer_image_path = writer_image_path;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public ConditionEntity getConditionInfo() {
		return conditionInfo;
	}
	public void setConditionInfo(ConditionEntity conditionInfo) {
		this.conditionInfo = conditionInfo;
	}
	
	public String getUserControlBox() {
		if ( userControlBox == null || userControlBox.length() == 0 ) {
			setUserControlBox();
		}
		return userControlBox;
	}
	public void setUserControlBox(String userControlBox) {
		this.userControlBox = userControlBox;
	}
	public void setUserControlBox() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("	<div class=\"btn-group\">\n");
		sb.append("		<a class=\"btn btn-mini dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">\n");
		sb.append("			").append(user_nic);
		sb.append("			<span class=\"caret\"></span>\n");
		sb.append("		</a>\n");
		sb.append("		<ul class=\"dropdown-menu\" role=\"menu\" aria-labelledby=\"dropdownMenu\">\n");
//		sb.append("			<li class=\"divider\"></li>\n");
		sb.append("			<li><a tabindex=\"-1\" href=\"javascript:viewUserInfo('");
		sb.append(user_seq_id);
		sb.append("');\">정보보기</a></li>\n");
		sb.append("			<li><a tabindex=\"-1\" href=\"javascript:writeMemo('");
		sb.append(user_seq_id);
		sb.append("', '");
		sb.append(user_id);
		sb.append("', '");
		sb.append(user_nic);
		sb.append("');\">쪽지쓰기</a></li>\n");
//		sb.append("			<li class=\"divider\"></li>\n");
//		sb.append("	<li><a tabindex=\"-1\" href=\"#\">Something else here</a></li>\n");
//		sb.append("	<li><a tabindex=\"-1\" href=\"#\">Separated link</a></li>\n");
//		sb.append("	<li class=\"divider\"></li>\n");
		sb.append("		</ul>\n");
		sb.append("	</div>\n");
		
		
		
		setUserControlBox(sb.toString());
	}
	
	public String getPagination() {
		int pageSize = 10;
		int startPage = pageNum%pageSize;
		String diabled = " class=\"disabled\"";
		String active =  " class=\"active\"";
		StringBuffer sb = new StringBuffer();
		
		sb.append("		<ul>\n");
		sb.append("			<li");
		if ( pageNum < 10 ) {
			sb.append(diabled);
		} else {
			sb.append(active);
		}
		sb.append("><span><<</span></li>\n");
		sb.append("			<li");
		if ( pageNum < 10 ) {
			sb.append(diabled);
		} else {
			sb.append(active);
		}
		sb.append("><span>Prev</span></li>\n");
		for ( int ii = startPage ; ii < pageSize ; ii ++ ) {
			sb.append("			<li");
			if ( ii == pageNum ){
				sb.append(diabled);
			} else {
				sb.append(active);
			}
			sb.append("><span>");
			sb.append(ii);
			sb.append("</span></li>\n");
		}
		sb.append("			<li><span>Next</span></li>\n");
		sb.append("			<li><span>>></span></li>\n");
		sb.append("		</ul>\n");
		
		
		return sb.toString();
	}
}
