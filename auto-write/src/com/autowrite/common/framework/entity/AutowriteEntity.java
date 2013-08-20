package com.autowrite.common.framework.entity;

import java.util.List;

public class AutowriteEntity {
	private List<BoardEntity> contentsEntityList;
	private List<BoardEntity> siteEntityList;
	
	private String seq_id;
	private String contents_seq_id;
	private List<String> site_seq_id_list;
	private String title;
	private String content;
	private String writer_seq_id;
	private String writer_id;
	private String write_datetime;
	
	
	public List<BoardEntity> getContentsEntityList() {
		return contentsEntityList;
	}
	public void setContentsEntityList(List<BoardEntity> contentsEntityList) {
		this.contentsEntityList = contentsEntityList;
	}
	public List<BoardEntity> getSiteEntityList() {
		return siteEntityList;
	}
	public void setSiteEntityList(List<BoardEntity> siteEntityList) {
		this.siteEntityList = siteEntityList;
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
}
