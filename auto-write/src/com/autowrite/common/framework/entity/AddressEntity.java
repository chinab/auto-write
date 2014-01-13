package com.autowrite.common.framework.entity;


/**
 * @author KSH
 * 
 */
public class AddressEntity extends CommonEntity {
	public static final String UserSessionKey = "userSessionKey";
	
	private String master_seq_id;
	private String seq_id;
	
	private String name;
	private String cell_phone;
	private String company_phone;
	private String home_phone;
	private String fax;
	private String extention_number;
	private String etc_number;
	private String email;
	private String birth_day;
	private String marry_day;
	private String anniversary;
	private String etc_anniversary;
	private String homepage;
	private String company_name;
	private String company_division;
	private String company_class;
	private String home_zipcode;
	private String home_address;
	private String company_zipcode;
	private String company_address;
	private String etc_zipcode;
	private String etc_address;
	private String memo;

	private String del_yn;
	private String writer_seq_id;
	private String writer_ip;
	private String write_datetime;
	
	public String getMaster_seq_id() {
		return master_seq_id;
	}
	public void setMaster_seq_id(String master_seq_id) {
		this.master_seq_id = master_seq_id;
	}
	public String getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCell_phone() {
		return cell_phone;
	}
	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}
	public String getCompany_phone() {
		return company_phone;
	}
	public void setCompany_phone(String company_phone) {
		this.company_phone = company_phone;
	}
	public String getHome_phone() {
		return home_phone;
	}
	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getExtention_number() {
		return extention_number;
	}
	public void setExtention_number(String extention_number) {
		this.extention_number = extention_number;
	}
	public String getEtc_number() {
		return etc_number;
	}
	public void setEtc_number(String etc_number) {
		this.etc_number = etc_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirth_day() {
		return birth_day;
	}
	public void setBirth_day(String birth_day) {
		this.birth_day = birth_day;
	}
	public String getMarry_day() {
		return marry_day;
	}
	public void setMarry_day(String marry_day) {
		this.marry_day = marry_day;
	}
	public String getAnniversary() {
		return anniversary;
	}
	public void setAnniversary(String anniversary) {
		this.anniversary = anniversary;
	}
	public String getEtc_anniversary() {
		return etc_anniversary;
	}
	public void setEtc_anniversary(String etc_anniversary) {
		this.etc_anniversary = etc_anniversary;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_division() {
		return company_division;
	}
	public void setCompany_division(String company_division) {
		this.company_division = company_division;
	}
	public String getCompany_class() {
		return company_class;
	}
	public void setCompany_class(String company_class) {
		this.company_class = company_class;
	}
	public String getHome_zipcode() {
		return home_zipcode;
	}
	public void setHome_zipcode(String home_zipcode) {
		this.home_zipcode = home_zipcode;
	}
	public String getHome_address() {
		return home_address;
	}
	public void setHome_address(String home_address) {
		this.home_address = home_address;
	}
	public String getCompany_zipcode() {
		return company_zipcode;
	}
	public void setCompany_zipcode(String company_zipcode) {
		this.company_zipcode = company_zipcode;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	public String getEtc_zipcode() {
		return etc_zipcode;
	}
	public void setEtc_zipcode(String etc_zipcode) {
		this.etc_zipcode = etc_zipcode;
	}
	public String getEtc_address() {
		return etc_address;
	}
	public void setEtc_address(String etc_address) {
		this.etc_address = etc_address;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
	public String getWriter_seq_id() {
		return writer_seq_id;
	}
	public void setWriter_seq_id(String writer_seq_id) {
		this.writer_seq_id = writer_seq_id;
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
	public static String getUsersessionkey() {
		return UserSessionKey;
	}
	
}
