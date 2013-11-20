CREATE  TABLE `site_master` (

  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,

  `domain` VARCHAR(255) NULL ,

  `site_name` VARCHAR(255) NULL ,

  `site_id_key` VARCHAR(255) NULL ,

  `site_passwd_key` VARCHAR(255) NULL ,

  `login_url` VARCHAR(255) NULL ,

  `write_url` VARCHAR(255) NULL ,

  `modify_url` VARCHAR(255) NULL ,

  `delete_url` VARCHAR(255) NULL ,

  `login_type` VARCHAR(255) NULL ,

  `write_type` VARCHAR(255) NULL ,

  `modify_type` VARCHAR(255) NULL ,

  `delete_type` VARCHAR(255) NULL ,
  
  `service_class_name` VARCHAR(4000) NULL ,
  
  `site_encoding` VARCHAR(255) NULL ,
  
  `writer_seq_id` BIGINT NULL ,

  `writer_id` VARCHAR(45) NULL ,

  `write_datetime` DATETIME NULL ,

  PRIMARY KEY (`seq_id`) )

COMMENT = '사이트설정 마스터';


CREATE  TABLE `site_parameter` (

  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,

  `master_seq_id` BIGINT NULL ,

  `mode` VARCHAR(45) NULL ,

  `p_key` VARCHAR(255) NULL ,

  `p_value` VARCHAR(255) NULL ,
  
  `user_seq_id` VARCHAR(255) NULL ,
  
  PRIMARY KEY (`seq_id`) )

COMMENT = '사이트별 파라미터 설정';


CREATE  TABLE `site_private` (

  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,

  `master_seq_id` BIGINT NULL ,

  `domain` VARCHAR(255) NULL ,

  `site_name` VARCHAR(255) NULL ,
  
  `site_id` VARCHAR(255) NULL ,
  
  `site_passwd` VARCHAR(255) NULL ,
  
  `site_keyword` VARCHAR(4000) NULL ,
  
  `site_category` VARCHAR(255) NULL ,
  
  `site_region` VARCHAR(255) NULL ,
  
  `site_sub_region` VARCHAR(255) NULL ,
  
  `use_yn` VARCHAR(10) NULL ,
  
  `write_type_code` VARCHAR(10) NULL ,
  
  `writer_seq_id` BIGINT NULL ,

  `writer_id` VARCHAR(45) NULL ,

  `write_datetime` DATETIME NULL ,

  PRIMARY KEY (`seq_id`) )

COMMENT = '개인별 사이트 설정';



CREATE  TABLE `site_category` (

  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,

  `master_seq_id` BIGINT NULL ,

  `category_type` VARCHAR(255) NULL ,

  `category_name` VARCHAR(4000) NULL ,
  
  `category_value` VARCHAR(4000) NULL ,
  
  `use_yn` VARCHAR(10) NULL ,
  
  `writer_seq_id` BIGINT NULL ,

  `writer_id` VARCHAR(45) NULL ,

  `write_datetime` DATETIME NULL ,

  PRIMARY KEY (`seq_id`) )

COMMENT = '사이트 분류 설정';



CREATE  TABLE `contents_master` (

  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,

  `contents_name` VARCHAR(4000) NULL ,

  `title` VARCHAR(4000) NULL ,

  `content` LONGTEXT NULL ,

  `writer_seq_id` BIGINT NULL ,

  `writer_id` VARCHAR(255) NULL ,

  `write_datetime` DATETIME NULL ,

  PRIMARY KEY (`seq_id`) )

COMMENT = '본문 설정';


CREATE  TABLE `autowrite_master` (
  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,

  `contents_seq_id` BIGINT NULL ,

  `title` VARCHAR(4000) NULL ,

  `content` LONGTEXT NULL ,

  `writer_seq_id` BIGINT NULL ,

  `writer_id` VARCHAR(255) NULL ,

  `write_datetime` DATETIME NULL ,

  PRIMARY KEY (`seq_id`) )

COMMENT = '자동등록';



CREATE  TABLE `autowrite_site` (
  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,
  
  `autowrite_master_seq_id` BIGINT NULL ,

  `site_seq_id` BIGINT NULL ,

  `success_yn` VARCHAR(255) NULL ,
  
  `response_content` LONGTEXT NULL ,
  
  `try_count` BIGINT NULL ,

  `try_datetime` DATETIME NULL ,

  PRIMARY KEY (`seq_id`) , 
  
  INDEX `AUTOWRITE_SITE_MASTER_SEQ_ID` (`autowrite_master_seq_id` ASC) )
  
COMMENT = '자동등록 사이트 목록';



CREATE  TABLE `autowrite_reserve_master` (
  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,
  
  `contents_seq_id` BIGINT NULL ,
  
  `title` VARCHAR(4000) NULL ,
  
  `content` LONGTEXT NULL ,
  
  `reserve_start_date` VARCHAR(45) NULL ,
  
  `reserve_start_time` VARCHAR(45) NULL ,
  
  `reserve_end_date` VARCHAR(45) NULL ,
  
  `reserve_end_time` VARCHAR(45) NULL ,
  
  `reserve_term` VARCHAR(45) NULL ,
  
  `reserve_remain_minute` INT(11) NULL DEFAULT 30,
  
  `use_yn` VARCHAR(45) NULL DEFAULT 'Y' ,
  
  `writer_seq_id` BIGINT NULL ,
  
  `writer_id` VARCHAR(255) NULL ,
  
  `write_datetime` DATETIME NULL ,
  
  PRIMARY KEY (`seq_id`) )

COMMENT = '자동등록 예약';



CREATE  TABLE `autowrite_reserve_site` (
  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,
  
  `reserve_master_seq_id` BIGINT NULL ,

  `site_seq_id` BIGINT NULL ,

  PRIMARY KEY (`seq_id`) , 
  
  INDEX `AUTOWRITE_RESERVE_SITE_MASTER_SEQ_ID` (`reserve_master_seq_id` ASC) )
  
COMMENT = '자동등록 예약 사이트 목록';



CREATE  TABLE `autowrite_log` (
  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,
  
  `autowrite_master_seq_id` BIGINT NULL ,

  `autowrite_site_seq_id` BIGINT NULL ,

  `site_seq_id` BIGINT NULL ,

  `success_yn` VARCHAR(255) NULL ,
  
  `response_content` LONGTEXT NULL ,

  `try_datetime` DATETIME NULL ,

  PRIMARY KEY (`seq_id`) , 
  
  INDEX `AUTOWRITE_SITE_MASTER_SEQ_ID` (`autowrite_master_seq_id` ASC) )
  
COMMENT = '자동등록 사이트 목록';


-- U_INFO
-- DROP TABLE `U_INFO` CASCADE ;

CREATE  TABLE `U_INFO` (

  `SEQ_ID` BIGINT NOT NULL AUTO_INCREMENT ,

  `NAME` VARCHAR(45) NOT NULL ,

  `ID` VARCHAR(45) NOT NULL ,

  `NIC` VARCHAR(45) NOT NULL ,

  `PASSWORD` VARCHAR(45) NOT NULL ,

  `EMAIL` VARCHAR(200) NULL ,

  `POINT` INT NULL ,

  `TYPE_CODE` VARCHAR(5) NULL DEFAULT 'P' ,

  `STATUS_CODE` VARCHAR(5) NULL DEFAULT 'W' ,

  `SERVICE_CODE` VARCHAR(45) NULL,

  `REG_DATETIME` DATETIME NULL ,

  `APPROVE_DATETIME` DATETIME NULL ,

  `APPROVAL_USER_SEQ_ID` BIGINT NULL ,

  PRIMARY KEY (`SEQ_ID`) ,

  UNIQUE INDEX `U_INFO_ID` (`ID` ASC),
  
  UNIQUE INDEX `U_INFO_NIC` (`NIC` ASC) );
  
  
-- U_INFO_BUSINESS
-- DROP TABLE `U_INFO_BUSINESS` CASCADE ;

CREATE  TABLE `U_INFO_BUSINESS` (

  `SEQ_ID` BIGINT NOT NULL AUTO_INCREMENT ,

  `USER_SEQ_ID` BIGINT NOT NULL ,
		
  `BUSINESS_NAME` VARCHAR(500) NULL ,

  `BUSINESS_REGION` VARCHAR(500) NULL ,

  `BUSINESS_TEL` VARCHAR(45) NULL ,

  `BUSINESS_CATEGORY` VARCHAR(500) NULL ,

  `BUSINESS_TIME` VARCHAR(4000) NULL ,

  `BUSINESS_PRICE` VARCHAR(4000) NULL ,

  `BUSINESS_ADDRESS` VARCHAR(4000) NULL ,

  `WRITE_DATETIME` DATETIME NULL ,

  PRIMARY KEY (`SEQ_ID`) ,

  INDEX `USER_SEQ_ID` (`USER_SEQ_ID` ASC) );