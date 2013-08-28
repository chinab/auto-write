package com.autowrite.common.framework.entity;

import java.util.Map;

public class SiteEntity extends CommonEntity{
	private String seq_id;
	private String master_seq_id;
	private String domain;
	private String site_name;
	private String site_id;
	private String site_passwd;
	
	private String site_id_key;
	private String site_passwd_key;
	
	private String login_url;
	private String write_url;
	private String modify_url;
	private String delete_url;
	
	private String login_type;
	private String write_type;
	private String modify_type;
	private String delete_type;
	
	private String writer_seq_id;
	private String writer_id;
	private String write_datetime;
	
	private String service_class_name;
	private String site_encoding;
	
	private Map loginParam;
	private Map writeParam;
	private Map modifyParam;
	private Map deleteParam;
	
	private String use_yn;
	
	public String getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}
	public String getMaster_seq_id() {
		return master_seq_id;
	}
	public void setMaster_seq_id(String master_seq_id) {
		this.master_seq_id = master_seq_id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public String getSite_passwd() {
		return site_passwd;
	}
	public void setSite_passwd(String site_passwd) {
		this.site_passwd = site_passwd;
	}
	public String getSite_id_key() {
		return site_id_key;
	}
	public void setSite_id_key(String site_id_key) {
		this.site_id_key = site_id_key;
	}
	public String getSite_passwd_key() {
		return site_passwd_key;
	}
	public void setSite_passwd_key(String site_passwd_key) {
		this.site_passwd_key = site_passwd_key;
	}
	public String getLogin_url() {
		return login_url;
	}
	public void setLogin_url(String login_url) {
		this.login_url = login_url;
	}
	public String getWrite_url() {
		return write_url;
	}
	public void setWrite_url(String write_url) {
		this.write_url = write_url;
	}
	public String getModify_url() {
		return modify_url;
	}
	public void setModify_url(String modify_url) {
		this.modify_url = modify_url;
	}
	public String getDelete_url() {
		return delete_url;
	}
	public void setDelete_url(String delete_url) {
		this.delete_url = delete_url;
	}
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	public String getWrite_type() {
		return write_type;
	}
	public void setWrite_type(String write_type) {
		this.write_type = write_type;
	}
	public String getModify_type() {
		return modify_type;
	}
	public void setModify_type(String modify_type) {
		this.modify_type = modify_type;
	}
	public String getDelete_type() {
		return delete_type;
	}
	public void setDelete_type(String delete_type) {
		this.delete_type = delete_type;
	}
	public String getWriter_seq_id() {
		return writer_seq_id;
	}
	public void setWriter_seq_id(String writer_seq_id) {
		this.writer_seq_id = writer_seq_id;
	}
	public String getWriter_id() {
		return writer_id;
	}
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}
	public String getWrite_datetime() {
		return write_datetime;
	}
	public void setWrite_datetime(String write_datetime) {
		this.write_datetime = write_datetime;
	}
	public String getService_class_name() {
		return service_class_name;
	}
	public void setService_class_name(String service_class_name) {
		this.service_class_name = service_class_name;
	}
	public String getSite_encoding() {
		return site_encoding;
	}
	public void setSite_encoding(String site_encoding) {
		this.site_encoding = site_encoding;
	}
	public Map getLoginParam() {
		return loginParam;
	}
	public void setLoginParam(Map loginParam) {
		this.loginParam = loginParam;
	}
	public Map getWriteParam() {
		return writeParam;
	}
	public void setWriteParam(Map writeParam) {
		this.writeParam = writeParam;
	}
	public Map getModifyParam() {
		return modifyParam;
	}
	public void setModifyParam(Map modifyParam) {
		this.modifyParam = modifyParam;
	}
	public Map getDeleteParam() {
		return deleteParam;
	}
	public void setDeleteParam(Map deleteParam) {
		this.deleteParam = deleteParam;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	
}
