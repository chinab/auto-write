package com.autowrite.common.framework.entity;

import java.util.List;

public class MenuDto {

	public MenuDto() {
		menuid = "";
		menulvl = "";
		menunm = "";
		parmenuid = "";
		parmenunm = "";
		useyn = "";
		insdt = "";
		menupageurl = "";
		srvname = "";
		fstimgpath = "";
		sndimgpath = "";
		submenucnt = 0;
		userid = "";
		orgUserid = "";
		fstmenuid = "";
		sndmenuid = "";
		trdmenuid = "";
		leftposition = 0;
		hasper = "N";
		docnm = "";
		menutype = "";
	}
	
	public MenuDto(MenuDto target){
		menuid = target.getMenuid();
		menulvl = target.getMenulvl();
		menunm = target.getMenunm();
		parmenuid = target.getParmenuid();
		parmenunm = target.getParmenunm();
		useyn = target.getUseyn();
		insdt = target.getInsdt();
		menupageurl = target.getMenupageurl();
		srvname = target.getSrvname();
		fstimgpath = target.getFstimgpath();
		sndimgpath = target.getSndimgpath();
		submenucnt = target.getSubmenucnt();
		userid = target.getUserid();
		orgUserid = target.getOrgUserid();
		fstmenuid = target.getFstmenuid();
		sndmenuid = target.getSndmenuid();
		trdmenuid = target.getTrdmenuid();
		leftposition = target.getLeftposition();
		hasper = target.getHasper();
		docnm = target.getDocnm();
		menutype = target.getMenutype();
	}

	public String getDocnm() {
		return docnm;
	}

	public void setDocnm(String docnm) {
		this.docnm = docnm;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getMenunm() {
		return menunm;
	}

	public void setMenunm(String menunm) {
		this.menunm = menunm;
	}

	public String getParmenuid() {
		return parmenuid;
	}

	public void setParmenuid(String parmenuid) {
		this.parmenuid = parmenuid;
	}

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}

	public String getInsdt() {
		return insdt;
	}

	public void setInsdt(String insdt) {
		this.insdt = insdt;
	}

	public String getMenupageurl() {
		return menupageurl;
	}

	public void setUrl(String menupageurl) {
		this.menupageurl = menupageurl;
	}

	public String getSrvname() {
		return srvname;
	}

	public void setSrvname(String srvname) {
		this.srvname = srvname;
	}

	public String getParmenunm() {
		return parmenunm;
	}

	public void setParmenunm(String parmenunm) {
		this.parmenunm = parmenunm;
	}

	public String getMenulvl() {
		return menulvl;
	}

	public void setMenulvl(String menulvl) {
		this.menulvl = menulvl;
	}

	public String getFstimgpath() {
		return fstimgpath;
	}

	public void setFstimgpath(String fstimgpath) {
		this.fstimgpath = fstimgpath;
	}

	public String getSndimgpath() {
		return sndimgpath;
	}

	public void setSndimgpath(String sndimgpath) {
		this.sndimgpath = sndimgpath;
	}

	public void setMenupageurl(String menupageurl) {
		this.menupageurl = menupageurl;
	}

	public int getSubmenucnt() {
		return submenucnt;
	}

	public void setSubmenucnt(int submenucnt) {
		this.submenucnt = submenucnt;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrgUserid() {
		return orgUserid;
	}

	public void setOrgUserid(String orgUserid) {
		this.orgUserid = orgUserid;
	}

	public List getLeafmenus() {
		return leafmenus;
	}

	public void setLeafmenus(List leafmenus) {
		this.leafmenus = leafmenus;
	}

	public List getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List submenus) {
		this.submenus = submenus;
	}

	public String getFstmenuid() {
		return fstmenuid;
	}

	public void setFstmenuid(String fstmenuid) {
		this.fstmenuid = fstmenuid;
	}

	public String getSndmenuid() {
		return sndmenuid;
	}

	public void setSndmenuid(String sndmenuid) {
		this.sndmenuid = sndmenuid;
	}

	public String getTrdmenuid() {
		return trdmenuid;
	}

	public void setTrdmenuid(String trdmenuid) {
		this.trdmenuid = trdmenuid;
	}

	public int getLeftposition() {
		return leftposition;
	}

	public void setLeftposition(int leftposition) {
		this.leftposition = leftposition;
	}

	public String getInitsubmenuid() {
		return initsubmenuid;
	}

	public void setInitsubmenuid(String initsubmenuid) {
		this.initsubmenuid = initsubmenuid;
	}

	public String getHasper() {
		return hasper;
	}

	public void setHasper(String hasper) {
		this.hasper = hasper;
	}

	public void setMenutype(String menutype) {
		this.menutype = menutype;
	}

	public String getMenutype() {
		return menutype;
	}
	
	private String menuid;
	private String menulvl;
	private String menunm;
	private String parmenuid;
	private String parmenunm;
	private String useyn;
	private String insdt;
	private String menupageurl;
	private String srvname;
	private String fstimgpath;
	private String sndimgpath;
	private int submenucnt;
	private String userid;
	private String orgUserid;
	private List leafmenus;
	private List submenus;
	private String fstmenuid;
	private String sndmenuid;
	private String trdmenuid;
	private int leftposition;
	private String initsubmenuid;
	private String hasper;
	private String docnm;
	private String menutype;
}
