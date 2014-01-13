CREATE  TABLE `autowrite_address_contents` (
	`seq_id` BIGINT NOT NULL AUTO_INCREMENT ,
	
	`master_seq_id` BIGINT NULL ,
	
	`name` VARCHAR(255) NULL ,
	
	`cell_phone` VARCHAR(255) NULL ,
	
	`company_phone` VARCHAR(255) NULL ,
	
	`home_phone` VARCHAR(255) NULL ,
	
	`fax` VARCHAR(255) NULL ,
	
	`extention_number` VARCHAR(255) NULL ,
	
	`etc_number` VARCHAR(255) NULL ,
	
	`email` VARCHAR(255) NULL ,
	
	`birth_day` VARCHAR(255) NULL ,
	
	`marry_day` VARCHAR(255) NULL ,
	
	`anniversary` VARCHAR(255) NULL ,
	
	`etc_anniversary` VARCHAR(255) NULL ,
	
	`homepage` VARCHAR(255) NULL ,
	
	`company_name` VARCHAR(4000) NULL ,
	
	`company_division` VARCHAR(4000) NULL ,
	
	`company_class` VARCHAR(255) NULL ,
	
	`home_zipcode` VARCHAR(255) NULL ,
	
	`home_address` VARCHAR(255) NULL ,
	
	`company_zipcode` VARCHAR(255) NULL ,
	
	`company_address` VARCHAR(4000) NULL ,
	
	`etc_zipcode` VARCHAR(255) NULL ,
	
	`etc_address` VARCHAR(4000) NULL ,
	
	`memo` LONGTEXT NULL ,
	
	`del_yn` VARCHAR(10) NULL ,
	
	`writer_seq_id` BIGINT NULL ,
	
	`writer_ip` VARCHAR(500) NULL ,
	
	`write_datetime`  DATETIME NULL ,
	
	PRIMARY KEY (`seq_id`) , 
  
	INDEX `AUTOWRITE_ADDRESS_CONTENTS_IDX` (`writer_seq_id` ASC),

	INDEX `AUTOWRITE_ADDRESS_CONTENTS_NAME_IDX` (`name` ASC),

	INDEX `AUTOWRITE_ADDRESS_CONTENTS_CELL_PHONE_IDX` (`cell_phone` ASC) )
  
COMMENT = '자동등록 주소록';