CREATE  TABLE `site_master` (

  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,

  `domain` VARCHAR(255) NULL ,

  `site_name` VARCHAR(255) NULL ,

  `write_url` VARCHAR(255) NULL ,

  `modify_url` VARCHAR(255) NULL ,

  `delete_url` VARCHAR(255) NULL ,

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

  PRIMARY KEY (`seq_id`) )

COMMENT = '사이트별 파라미터 설정';


CREATE  TABLE `site_private` (

  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,

  `master_seq_id` BIGINT NULL ,

  `domain` VARCHAR(255) NULL ,

  `site_name` VARCHAR(255) NULL ,

  `writer_seq_id` BIGINT NULL ,

  `writer_id` VARCHAR(45) NULL ,

  `write_datetime` DATETIME NULL ,

  PRIMARY KEY (`seq_id`) )

COMMENT = '개인별 사이트 설정';



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

  `contents_name` VARCHAR(4000) NULL ,

  `title` VARCHAR(4000) NULL ,

  `content` LONGTEXT NULL ,

  `writer_seq_id` BIGINT NULL ,

  `writer_id` VARCHAR(255) NULL ,

  `write_datetime` DATETIME NULL ,

  PRIMARY KEY (`seq_id`) )

COMMENT = '자동등록';



CREATE  TABLE `autowrite_site` (
  `seq_id` BIGINT NOT NULL AUTO_INCREMENT ,
  
  `autowrite_seq_id` BIGINT NULL ,

  `site_seq_id` BIGINT NULL ,

  PRIMARY KEY (`seq_id`) )
  
COMMENT = '자동등록 사이트 목록';
