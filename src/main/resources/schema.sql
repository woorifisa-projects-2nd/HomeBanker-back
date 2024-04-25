DROP TABLE users IF EXISTS;
DROP TABLE product IF EXISTS;
DROP TABLE sale IF EXISTS;
DROP TABLE board IF EXISTS;
DROP TABLE counsel IF EXISTS;
DROP TABLE identified IF EXISTS;
DROP TABLE product_code IF EXISTS;
DROP TABLE banker_log IF EXISTS;
DROP TABLE customer_log IF EXISTS;


CREATE TABLE `users` (
                            `id`	INT	NOT NULL AUTO_INCREMENT,
                            `name`	VARCHAR(10)	NOT NULL,
                            `birth`	DATE	NOT NULL,
                            `phone`	VARCHAR(11)	NOT NULL,
                            `address`	VARCHAR(50)	NOT NULL,
                            `login_id`	VARCHAR(10)	NOT NULL,
                            `login_pw`	VARCHAR(100)	NOT NULL,
                            `role` VARCHAR(15)	NOT NULL,
                            `identification_num`	VARCHAR(256)	NOT NULL,
                            `recent_login`	DATETIME	NULL,
                            `join_date`	DATE	NOT NULL
);

CREATE TABLE `product` (
                           `product_id`	INT	NOT NULL AUTO_INCREMENT,
                           `product_code`	VARCHAR(30)	NOT NULL,
                           `product_name`	VARCHAR(255) NOT NULL,
                           `product_description`	VARCHAR(100) NOT NULL,
                           `product_interest`	NUMERIC(4,2) NULL,
                           `max_month`	INT NULL,
                           `is_shown`	BIT	NOT NULL
);

CREATE TABLE `sale` (
                        `sale_id`	BIGINT	NOT NULL AUTO_INCREMENT,
                        `banker_login_id`	VARCHAR(10)	NOT NULL,
                        `customer_login_id`	VARCHAR(10)	NOT NULL,
                        `product_id`	INT	NOT NULL,
                        `created_at`	DATETIME NOT NULL,
                        `sale_month`	INT	NULL,
                        `sale_amount`	INT	NULL
);

CREATE TABLE `board` (
                         `board_id`	BIGINT	NOT NULL AUTO_INCREMENT,
                         `customer_id`	INT	NULL,
                         `banker_id`	INT	NULL,
                         `banker_name` VARCHAR(10) NULL,
                         `content`	VARCHAR(500) NOT NULL,
                         `reply_YN`	VARCHAR(10)	NOT NULL,
                         `created_at`	DATETIME	NOT NULL,
                         `updated_at`	DATETIME	NULL
);

CREATE TABLE `counsel` (
                           `counsel_id`	BIGINT	NOT NULL AUTO_INCREMENT,
                           `banker_id`	INT	NOT NULL,
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
                                `product_code`	VARCHAR(30)	NOT NULL,
                                `type_name`	VARCHAR(30)	NOT NULL
);

CREATE TABLE `customer_log` (
                                `log_id`	INT	NOT NULL AUTO_INCREMENT,
                                `customer_id`	VARCHAR(30)	NOT NULL,
                                `customer_status` VARCHAR(10) NOT NULL,
                                `customer_time` DATETIME NOT NULL
);

CREATE TABLE `banker_log` (
                              `log_id`	INT	NOT NULL AUTO_INCREMENT,
                              `banker_id`	VARCHAR(30)	NOT NULL,
                              `banker_status` VARCHAR(10) NOT NULL,
                              `banker_time` DATETIME NOT NULL
);

ALTER TABLE `users` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
                                                                 `id`
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

ALTER TABLE `banker_log` ADD CONSTRAINT `PK_BANKER_LOG` PRIMARY KEY (
                                                                     `log_id`
    );

ALTER TABLE `board` ADD CONSTRAINT `FK_BOARD_USER` FOREIGN KEY (customer_id) REFERENCES `Users` (id);