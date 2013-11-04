package com.autowrite.common.framework.entity;


/**
 * @author KSH
 * 
 */
public class UserBusinessEntity extends CommonEntity{
	public static final String UserSessionKey = "userSessionKey";
	  
	private String seq_id;
	private String user_seq_id;
	private String business_name;
	private String business_region;
	private String business_tel;
	private String business_category;
	private String business_time;
	private String business_price;
	private String business_address;
	private String write_datetime;
	
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
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getBusiness_region() {
		return business_region;
	}
	public void setBusiness_region(String business_region) {
		this.business_region = business_region;
	}
	public String getBusiness_tel() {
		return business_tel;
	}
	public void setBusiness_tel(String business_tel) {
		this.business_tel = business_tel;
	}
	public String getBusiness_category() {
		return business_category;
	}
	public void setBusiness_category(String business_category) {
		this.business_category = business_category;
	}
	public String getBusiness_time() {
		return business_time;
	}
	public void setBusiness_time(String business_time) {
		this.business_time = business_time;
	}
	public String getBusiness_price() {
		return business_price;
	}
	public void setBusiness_price(String business_price) {
		this.business_price = business_price;
	}
	public String getBusiness_address() {
		return business_address;
	}
	public void setBusiness_address(String business_address) {
		this.business_address = business_address;
	}
	public String getWrite_datetime() {
		return write_datetime;
	}
	public void setWrite_datetime(String write_datetime) {
		this.write_datetime = write_datetime;
	}
	public static String getUsersessionkey() {
		return UserSessionKey;
	}
}
