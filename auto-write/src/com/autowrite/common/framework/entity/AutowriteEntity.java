package com.autowrite.common.framework.entity;

import java.util.Calendar;
import java.util.List;

public class AutowriteEntity extends CommonEntity {
	private List<BoardEntity> contentsEntityList;
	private List<SiteEntity> siteEntityList;
	private BoardEntity selectedContentsEntity;
	
	private String seq_id;
	private String contents_seq_id;
	private String contents_name;
	private List<String> site_seq_id_list;
	private String title;
	private String content;
	private String writer_seq_id;
	private String writer_id;
	private String write_datetime;
	
	// for master
	private String success_count;
	private String fail_count;
	
	// for site
	private String autowrite_master_seq_id;
	private String site_seq_id;
	private String site_name;
	private String success_yn;
	private String response_content;
	private String try_count;
	private String try_datetime;
	
	// for log
	private String autowrite_site_seq_id;
	
	// for http autowrite
	private SiteEntity siteEntity;
	
	
	public List<BoardEntity> getContentsEntityList() {
		return contentsEntityList;
	}
	public void setContentsEntityList(List<BoardEntity> contentsEntityList) {
		this.contentsEntityList = contentsEntityList;
	}
	public List<SiteEntity> getSiteEntityList() {
		return siteEntityList;
	}
	public void setSiteEntityList(List<SiteEntity> siteEntityList) {
		this.siteEntityList = siteEntityList;
	}
	public BoardEntity getSelectedContentsEntity() {
		return selectedContentsEntity;
	}
	public void setSelectedContentsEntity(BoardEntity selectedContentsEntity) {
		this.selectedContentsEntity = selectedContentsEntity;
	}
	public String getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}
	public String getContents_seq_id() {
		return contents_seq_id;
	}
	public void setContents_seq_id(String contents_seq_id) {
		this.contents_seq_id = contents_seq_id;
	}
	public String getContents_name() {
		return contents_name;
	}
	public void setContents_name(String contents_name) {
		this.contents_name = contents_name;
	}
	public List<String> getSite_seq_id_list() {
		return site_seq_id_list;
	}
	public void setSite_seq_id_list(List<String> site_seq_id_list) {
		this.site_seq_id_list = site_seq_id_list;
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
	public String getSuccess_count() {
		return success_count;
	}
	public void setSuccess_count(String success_count) {
		this.success_count = success_count;
	}
	public String getFail_count() {
		return fail_count;
	}
	public void setFail_count(String fail_count) {
		this.fail_count = fail_count;
	}
	public String getAutowrite_master_seq_id() {
		return autowrite_master_seq_id;
	}
	public void setAutowrite_master_seq_id(String autowrite_master_seq_id) {
		this.autowrite_master_seq_id = autowrite_master_seq_id;
	}
	public String getSite_seq_id() {
		return site_seq_id;
	}
	public void setSite_seq_id(String site_seq_id) {
		this.site_seq_id = site_seq_id;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getSuccess_yn() {
		return success_yn;
	}
	public void setSuccess_yn(String success_yn) {
		this.success_yn = success_yn;
	}
	public String getResponse_content() {
		return response_content;
	}
	public void setResponse_content(String response_content) {
		this.response_content = response_content;
	}
	public String getTry_count() {
		return try_count;
	}
	public void setTry_count(String try_count) {
		this.try_count = try_count;
	}
	public String getTry_datetime() {
		return try_datetime;
	}
	public void setTry_datetime(String try_datetime) {
		this.try_datetime = try_datetime;
	}
	public String getAutowrite_site_seq_id() {
		return autowrite_site_seq_id;
	}
	public void setAutowrite_site_seq_id(String autowrite_site_seq_id) {
		this.autowrite_site_seq_id = autowrite_site_seq_id;
	}
	public SiteEntity getSiteEntity() {
		return siteEntity;
	}
	public void setSiteEntity(SiteEntity siteEntity) {
		this.siteEntity = siteEntity;
	}
}
