package com.autowrite.common.framework.entity;


public class SiteCategoryEntity extends CommonEntity{
	private String seq_id;
	private String master_seq_id;
	private String category_type;
	private String category_name;
	private String category_value;
	private String use_yn;
	private String writer_seq_id;
	private String writer_id;
	private String write_datetime;
	
	
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
	public String getCategory_type() {
		return category_type;
	}
	public void setCategory_type(String category_type) {
		this.category_type = category_type;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCategory_value() {
		return category_value;
	}
	public void setCategory_value(String category_value) {
		this.category_value = category_value;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
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
	
	
}
