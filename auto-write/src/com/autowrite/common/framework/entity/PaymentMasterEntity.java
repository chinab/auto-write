package com.autowrite.common.framework.entity;

import java.util.List;

public class PaymentMasterEntity {
	private String seq_id;
	private String user_seq_id;
	private String user_name;
	private String user_id;
	private String user_nic;
	private String facility_name;
	private String facility_user_name;
	private String facility_phone;
	private String facility_address;
	private String facility_home_page;
	private String facility_introduce;
	private String facility_category;
	private String facility_region;
	private String total_payment_amount;
	private String total_payment_number;
	private String payment_status;
	private String banner_type_code;
	private String start_datetime;
	private String end_datetime;
	private String main_banner_file_name;
	private String main_banner_web_link;
	private String center_banner_file_name;
	private String center_banner_web_link;
	private String p_file_name;
	private String l_file_name;
	private String thumbnail_file_name;
	
	private List<BoardEntity> recentLineUpList;
	private List<BoardEntity> recentPostscriptList;
	
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
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
	public String getFacility_introduce() {
		return facility_introduce;
	}
	public void setFacility_introduce(String facility_introduce) {
		this.facility_introduce = facility_introduce;
	}
	public String getFacility_category() {
		return facility_category;
	}
	public void setFacility_category(String facility_category) {
		this.facility_category = facility_category;
	}
	public String getFacility_region() {
		return facility_region;
	}
	public void setFacility_region(String facility_region) {
		this.facility_region = facility_region;
	}
	public String getTotal_payment_amount() {
		return total_payment_amount;
	}
	public void setTotal_payment_amount(String total_payment_amount) {
		this.total_payment_amount = total_payment_amount;
	}
	public String getTotal_payment_number() {
		return total_payment_number;
	}
	public void setTotal_payment_number(String total_payment_number) {
		this.total_payment_number = total_payment_number;
	}
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	public String getBanner_type_code() {
		return banner_type_code;
	}
	public void setBanner_type_code(String banner_type_code) {
		this.banner_type_code = banner_type_code;
	}
	public String getStart_datetime() {
		return start_datetime;
	}
	public void setStart_datetime(String start_datetime) {
		this.start_datetime = start_datetime;
	}
	public String getEnd_datetime() {
		return end_datetime;
	}
	public void setEnd_datetime(String end_datetime) {
		this.end_datetime = end_datetime;
	}
	public String getMain_banner_file_name() {
		return main_banner_file_name;
	}
	public String writeMain_banner_file_name() {
		if ( main_banner_file_name == null || "null".equals(main_banner_file_name) ){
			return "";
		} else {
			return main_banner_file_name;
		}
	}
	public void setMain_banner_file_name(String main_banner_file_name) {
		this.main_banner_file_name = main_banner_file_name;
	}
	public String getMain_banner_web_link() {
		return main_banner_web_link;
	}
	public String writeMain_banner_web_link() {
		if ( main_banner_web_link == null || "null".equals(main_banner_web_link) ){
			return "";
		} else {
			return main_banner_web_link;
		}
	}
	public void setMain_banner_web_link(String main_banner_web_link) {
		this.main_banner_web_link = main_banner_web_link;
	}
	public String getCenter_banner_file_name() {
		return center_banner_file_name;
	}
	public String writeCenter_banner_file_name() {
		if ( center_banner_file_name == null || "null".equals(center_banner_file_name) ){
			return "";
		} else {
			return center_banner_file_name;
		}
	}
	public void setCenter_banner_file_name(String center_banner_file_name) {
		this.center_banner_file_name = center_banner_file_name;
	}
	public String getCenter_banner_web_link() {
		return center_banner_web_link;
	}
	public String writeCenter_banner_web_link() {
		if ( center_banner_web_link == null || "null".equals(center_banner_web_link) ){
			return "";
		} else {
			return center_banner_web_link;
		}
	}
	public void setCenter_banner_web_link(String center_banner_web_link) {
		this.center_banner_web_link = center_banner_web_link;
	}
	public String getP_file_name() {
		return p_file_name;
	}
	public String writeP_file_name() {
		if ( p_file_name == null || "null".equals(p_file_name) ){
			return "";
		} else {
			return p_file_name;
		}
	}
	public void setP_file_name(String p_file_name) {
		this.p_file_name = p_file_name;
	}
	public String write() {
		return l_file_name;
	}
	public String getL_file_name() {
		return l_file_name;
	}
	public String writeL_file_name() {
		if ( l_file_name == null || "null".equals(l_file_name) ){
			return "";
		} else {
			return l_file_name;
		}
	}
	public void setL_file_name(String l_file_name) {
		this.l_file_name = l_file_name;
	}
	public String getThumbnail_file_name() {
		return thumbnail_file_name;
	}
	public String writeThumbnail_file_name() {
		if ( thumbnail_file_name == null || "null".equals(thumbnail_file_name) ){
			return "";
		} else {
			return thumbnail_file_name;
		}
	}
	public void setThumbnail_file_name(String thumbnail_file_name) {
		this.thumbnail_file_name = thumbnail_file_name;
	}
	public List<BoardEntity> getRecentLineUpList() {
		return recentLineUpList;
	}
	public void setRecentLineUpList(List<BoardEntity> recentLineUpList) {
		this.recentLineUpList = recentLineUpList;
	}
	public List<BoardEntity> getRecentPostscriptList() {
		return recentPostscriptList;
	}
	public void setRecentPostscriptList(List<BoardEntity> recentPostscriptList) {
		this.recentPostscriptList = recentPostscriptList;
	}
	
}
