package com.autowrite.common.framework.entity;

import java.util.Calendar;

/**
 * @author KSH
 * 
 */
public class MemoEntity {
	public static final String UserSessionKey = "userSessionKey";
	
	private UserEntity userInfo;
	
	private String seq_id;
	private String send_message_seq_id;
	private String snd_user_seq_id;
	private String snd_user_id;
	private String snd_user_nic;
	private String rcv_user_seq_id;
	private String rcv_user_id;
	private String rcv_user_nic;
	private String title;
	private String content;
	private String rcv_datetime;
	private String rcv_yn;
	private String del_yn;
	private String writer_ip;
	private String write_datetime;
	
	private int pageNum = 0;
	
	private ConditionEntity conditionInfo;
	
	String userControlBox;
	
	public UserEntity getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserEntity userInfo) {
		this.userInfo = userInfo;
	}

	public String getSeq_id() {
		return seq_id;
	}

	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}

	public String getSnd_user_seq_id() {
		return snd_user_seq_id;
	}

	public String getSend_message_seq_id() {
		return send_message_seq_id;
	}

	public void setSend_message_seq_id(String send_message_seq_id) {
		this.send_message_seq_id = send_message_seq_id;
	}

	public void setSnd_user_seq_id(String snd_user_seq_id) {
		this.snd_user_seq_id = snd_user_seq_id;
	}

	public String getSnd_user_id() {
		return snd_user_id;
	}

	public void setSnd_user_id(String snd_user_id) {
		this.snd_user_id = snd_user_id;
	}

	public String getSnd_user_nic() {
		return snd_user_nic;
	}

	public void setSnd_user_nic(String snd_user_nic) {
		this.snd_user_nic = snd_user_nic;
	}

	public String getRcv_user_seq_id() {
		return rcv_user_seq_id;
	}

	public void setRcv_user_seq_id(String rcv_user_seq_id) {
		this.rcv_user_seq_id = rcv_user_seq_id;
	}

	public String getRcv_user_id() {
		return rcv_user_id;
	}

	public void setRcv_user_id(String rcv_user_id) {
		this.rcv_user_id = rcv_user_id;
	}

	public String getRcv_user_nic() {
		return rcv_user_nic;
	}

	public void setRcv_user_nic(String rcv_user_nic) {
		this.rcv_user_nic = rcv_user_nic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRcv_datetime() {
		return rcv_datetime;
	}

	public String writeRcv_datetime() {
		if ( rcv_datetime == null ){
			return "읽지 않음";
		} else {
			return writeDateTime(rcv_datetime);
		}
	}

	public void setRcv_datetime(String rcv_datetime) {
		this.rcv_datetime = rcv_datetime;
	}

	public String getRcv_yn() {
		return rcv_yn;
	}

	public void setRcv_yn(String rcv_yn) {
		this.rcv_yn = rcv_yn;
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

	public String writeDateTime(String dateTime) {
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
		
		String dbDate = dateTime.substring(0, 10);
		String sysDate = year + "-" + monthStr + "-" + dayStr;
		
		if ( dbDate.equals(sysDate) ) {
			return dateTime.substring(11, 16);
		} else {
			if ( new Integer(dateTime.substring(0, 4)) == year ) {
				return dateTime.substring(5, 10);
			} else {
				return dbDate;
			}
		}
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
		
//		sb.append("	<div class=\"btn-group\">\n");
//		sb.append("		<a class=\"btn btn-mini dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">\n");
//		sb.append("			").append(user_nic);
//		sb.append("			<span class=\"caret\"></span>\n");
//		sb.append("		</a>\n");
//		sb.append("		<ul class=\"dropdown-menu\" role=\"menu\" aria-labelledby=\"dropdownMenu\">\n");
////		sb.append("			<li class=\"divider\"></li>\n");
//		sb.append("			<li><a tabindex=\"-1\" href=\"javascript:viewUserInfo('");
//		sb.append(user_seq_id);
//		sb.append("');\">정보보기</a></li>\n");
//		sb.append("			<li><a tabindex=\"-1\" href=\"javascript:writeMemo('");
//		sb.append(user_seq_id);
//		sb.append("');\">쪽지쓰기</a></li>\n");
//		sb.append("		</ul>\n");
//		sb.append("	</div>\n");
		
		
		
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
