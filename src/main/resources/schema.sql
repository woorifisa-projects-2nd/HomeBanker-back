DROP TABLE customer IF EXISTS;
DROP TABLE employee IF EXISTS;
DROP TABLE product IF EXISTS;
DROP TABLE sale IF EXISTS;
DROP TABLE board IF EXISTS;
DROP TABLE admin IF EXISTS;
DROP TABLE counsel IF EXISTS;
DROP TABLE identified IF EXISTS;
DROP TABLE product_code IF EXISTS;
DROP TABLE employee_log IF EXISTS;
DROP TABLE customer_log IF EXISTS;


CREATE TABLE `customer` (
	`customer_id`	INT	NOT NULL AUTO_INCREMENT,
	`customer_name`	VARCHAR(10)	NOT NULL,
	`customer_birth`	DATE	NOT NULL,
	`customer_phone`	VARCHAR(11)	NOT NULL,
	`customer_address`	VARCHAR(50)	NOT NULL,
	`customer_login_id`	VARCHAR(20)	NOT NULL,
	`customer_login_pw`	VARCHAR(30)	NOT NULL,
	`customer_role` VARCHAR(15) NOT NULL,
	`customer_identification_num`	VARCHAR(200)	NOT NULL,
	`customer_recent_login`	DATETIME	NOT NULL,
	`join_date`	DATE	NOT NULL
);

CREATE TABLE `employee` (
	`employee_id`	INT	NOT NULL AUTO_INCREMENT,
	`employee_name`	VARCHAR(10)	NOT NULL,
	`employee_department`	VARCHAR(20)	NOT NULL,
	`employee_grade`	VARCHAR(30)	NOT NULL,
	`employee_login_id`	VARCHAR(20)	NOT NULL,
	`employee_login_pw`	VARCHAR(30)	NOT NULL,
	`employee_role` VARCHAR(15) NOT NULL,
	`recent_login`	DATETIME NOT NULL
);

CREATE TABLE `product` (
	`product_id`	INT	NOT NULL AUTO_INCREMENT,
	`product_code`	VARCHAR(30)	NOT NULL,
	`product_name`	VARCHAR(255) NOT NULL,
	`product_description`	VARCHAR(30) NOT NULL,
	`product_interest`	DECIMAL NULL,
	`max_month`	INT NULL,
	`is_shown`	BIT	NOT NULL
);

CREATE TABLE `sale` (
	`sale_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`employee_id`	INT	NOT NULL,
	`customer_id`	INT	NOT NULL,
	`product_id`	INT	NOT NULL,
	`created_at`	DATETIME NOT NULL,
	`sale_month`	INT	NULL,
	`sale_amount`	INT	NULL
);

CREATE TABLE `board` (
	`board_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`customer_id`	INT	NULL,
	`employee_id`	INT	NULL,
	`content`	VARCHAR(500) NOT NULL,
	`reply_YN`	VARCHAR(10)	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

CREATE TABLE `counsel` (
	`counsel_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`employee_id`	INT	NOT NULL,
	`customer_id`	INT	NOT NULL,
	`counsel_type`	VARCHAR(10)	NOT NULL,
	`started_at`	DATETIME	NOT NULL,
	`ended_at`	DATETIME	NOT NULL
);

CREATE TABLE `identified` (
	`identified_id`	INT	NOT NULL AUTO_INCREMENT,
	`name`	VARCHAR(256)	NOT NULL,
	`identification_type`	VARCHAR(10)	NOT NULL,
	`issue_date`	VARCHAR(256)	NOT NULL,
	`identification_num`	VARCHAR(256)	NOT NULL
);

CREATE TABLE `product_code` (
	`product_code`	VARCHAR(30)	NOT NULL ,
	`type_name`	VARCHAR(30)	NOT NULL
);

CREATE TABLE `customer_log` (
	`log_id`	VARCHAR(255)	NOT NULL ,
	`customer_id`	INT	NOT NULL
);

CREATE TABLE `employee_log` (
	`log_id`	INT	NOT NULL ,
	`employee_id`	INT	NOT NULL
);

ALTER TABLE `customer` ADD CONSTRAINT `PK_CUSTOMER` PRIMARY KEY (
	`customer_id`
);

ALTER TABLE `employee` ADD CONSTRAINT `PK_EMPLOYEE` PRIMARY KEY (
	`employee_id`
);

ALTER TABLE `product` ADD CONSTRAINT `PK_PRODUCT` PRIMARY KEY (
	`product_id`
);

ALTER TABLE `sale` ADD CONSTRAINT `PK_SALE` PRIMARY KEY (
	`sale_id`
);

ALTER TABLE `board` ADD CONSTRAINT `PK_BOARD` PRIMARY KEY (
	`board_id`);

ALTER TABLE `counsel` ADD CONSTRAINT `PK_COUNSEL` PRIMARY KEY (
	`counsel_id`
);

ALTER TABLE `identified` ADD CONSTRAINT `PK_IDENTIFIED` PRIMARY KEY (
	`identified_id`
);

ALTER TABLE `product_code` ADD CONSTRAINT `PK_PRODUCT_CODE` PRIMARY KEY (
	`product_code`
);

ALTER TABLE `customer_log` ADD CONSTRAINT `PK_CUSTOMER_LOG` PRIMARY KEY (
	`log_id`
);

ALTER TABLE `employee_log` ADD CONSTRAINT `PK_EMPLOYEE_LOG` PRIMARY KEY (
	`log_id`
);