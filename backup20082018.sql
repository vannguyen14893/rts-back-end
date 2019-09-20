/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : cmc_recruitment

Target Server Type    : MYSQL
Target Server Version : 50199
File Encoding         : 65001

Date: 2018-08-20 14:45:52
*/
drop database if exists cmc_recruitment;

create database if not exists cmc_recruitment;
use cmc_recruitment;

create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication BLOB,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication BLOB
);

create table oauth_code (
  code VARCHAR(256), authentication BLOB
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	lastModifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- customized oauth_client_details table
create table ClientDetails (
  appId VARCHAR(256) PRIMARY KEY,
  resourceIds VARCHAR(256),
  appSecret VARCHAR(256),
  scope VARCHAR(256),
  grantTypes VARCHAR(256),
  redirectUrl VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(256)
);

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `account_global`
-- ----------------------------
DROP TABLE IF EXISTS `account_global`;
CREATE TABLE `account_global` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`pass`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`csrf_token`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci ,
`logout_token`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
`base_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=2

;

-- ----------------------------
-- Records of account_global
-- ----------------------------
BEGIN;
INSERT INTO account_global VALUES ('1', 'Recruiter', '1qaz2wsx3edc','','','http://101.99.14.196:5656/');
COMMIT;

-- ----------------------------
-- Table structure for `assignee_interview`
-- ----------------------------
DROP TABLE IF EXISTS `assignee_interview`;
CREATE TABLE `assignee_interview` (
`user_id`  bigint(20) NOT NULL ,
`interview_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`user_id`, `interview_id`),
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`interview_id`) REFERENCES `interview` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`interview_id`) REFERENCES `interview` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of assignee_interview
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `candidate`
-- ----------------------------
DROP TABLE IF EXISTS `candidate`;
CREATE TABLE `candidate` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`cv_id`  bigint(20) NOT NULL ,
`request_id`  bigint(20) NOT NULL ,
`status_id`  bigint(20) NOT NULL ,
`evaluate_point`  float NULL DEFAULT NULL ,
`source`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`create_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
`created_by`  bigint(20) NULL DEFAULT NULL ,
`onboard_date`  timestamp NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`status_id`) REFERENCES `candidate_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`status_id`) REFERENCES `candidate_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=59

;

-- ----------------------------
-- Records of candidate
-- ----------------------------
BEGIN;
INSERT INTO candidate VALUES ('17', null, '14', '24', '3', null, null, '2018-07-12 04:54:30', '7', null), ('18', null, '15', '34', '3', null, null, '2018-07-16 08:07:10', '6', '2018-07-26 00:00:00'), ('19', null, '17', '24', '3', null, null, '2018-07-16 09:12:19', '4', '2018-07-19 00:00:00'), ('20', null, '16', '27', '6', null, null, '2018-07-16 09:15:20', '4', null), ('21', null, '13', '27', '5', null, null, '2018-07-16 09:16:59', '4', '2018-07-23 00:00:00'), ('22', null, '17', '27', '6', null, null, '2018-07-17 02:18:44', '4', '2018-07-23 00:00:00'), ('23', 'Senior Technical', '16', '24', '3', null, 'Internal', '2018-07-17 02:22:55', '4', '2018-07-21 00:00:00'), ('24', null, '18', '35', '6', null, null, '2018-07-17 02:27:56', '5', null), ('25', null, '19', '26', '3', null, null, '2018-07-18 02:41:07', '4', null), ('26', null, '21', '25', '6', null, null, '2018-07-20 02:49:06', '5', null), ('27', null, '20', '37', '5', null, null, '2018-07-20 02:51:42', '4', '2018-07-23 00:00:00'), ('28', null, '22', '38', '4', null, null, '2018-07-20 02:58:05', '4', null), ('29', null, '23', '60', '4', null, null, '2018-07-20 03:01:48', '4', null), ('30', null, '24', '28', '5', null, null, '2018-07-20 03:41:34', '7', '2018-08-06 00:00:00'), ('31', null, '25', '21', '3', null, null, '2018-07-20 03:53:06', '5', null), ('32', null, '18', '21', '3', null, null, '2018-07-20 03:53:06', '5', null), ('33', null, '26', '28', '5', null, null, '2018-07-20 03:57:17', '7', '2018-08-06 00:00:00'), ('34', null, '27', '39', '5', null, null, '2018-07-23 02:41:41', '4', '2018-08-15 00:00:00'), ('35', null, '28', '61', '4', null, null, '2018-07-23 03:10:27', '3', null), ('36', null, '29', '61', '4', null, null, '2018-07-23 03:22:26', '6', null), ('37', null, '32', '33', '3', null, null, '2018-07-23 04:39:48', '7', null), ('38', null, '33', '33', '1', null, null, '2018-07-23 04:47:53', '7', null), ('39', null, '37', '35', '5', null, null, '2018-07-23 06:00:45', '7', '2018-08-01 00:00:00'), ('40', null, '38', '25', '5', null, null, '2018-07-23 06:32:45', '7', '2018-08-01 00:00:00'), ('41', null, '36', '20', '4', null, null, '2018-07-23 06:49:05', '7', null), ('42', null, '35', '20', '1', null, null, '2018-07-23 06:49:05', '7', null), ('43', null, '34', '20', '4', null, null, '2018-07-23 06:49:05', '7', null), ('44', null, '40', '25', '3', null, null, '2018-07-23 07:56:07', '7', null), ('45', null, '41', '24', '1', null, null, '2018-07-23 08:24:48', '7', null), ('46', null, '44', '25', '3', null, null, '2018-07-23 09:51:02', '7', null), ('47', null, '45', '34', '2', null, null, '2018-07-23 09:52:18', '6', null), ('48', null, '42', '34', '2', null, null, '2018-07-23 09:52:18', '6', null), ('49', null, '43', '34', '2', null, null, '2018-07-23 09:52:18', '6', null), ('50', null, '46', '34', '2', null, null, '2018-07-23 09:56:47', '6', null), ('51', null, '47', '34', '2', null, null, '2018-07-23 10:00:42', '6', null), ('52', null, '30', '63', '5', null, null, '2018-07-23 10:04:20', '6', '2018-07-23 00:00:00'), ('53', null, '48', '34', '2', null, null, '2018-07-23 10:12:38', '6', null), ('54', null, '49', '34', '2', null, null, '2018-07-24 02:39:05', '6', null), ('55', null, '50', '21', '3', null, null, '2018-07-24 06:49:37', '5', null), ('56', null, '21', '64', '5', null, null, '2018-07-24 08:15:41', '5', '2018-09-04 00:00:00'), ('57', null, '51', '34', '2', null, null, '2018-07-24 08:52:09', '6', null), ('58', null, '52', '25', '1', null, null, '2018-07-24 10:48:13', '7', null);
COMMIT;

-- ----------------------------
-- Table structure for `candidate_status`
-- ----------------------------
DROP TABLE IF EXISTS `candidate_status`;
CREATE TABLE `candidate_status` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=7

;

-- ----------------------------
-- Records of candidate_status
-- ----------------------------
BEGIN;
INSERT INTO candidate_status VALUES ('1', 'Apply', ''), ('2', 'Contacting', ''), ('3', 'Interview', ''), ('4', 'Offer', ''), ('5', 'Onboard', ''), ('6', 'Closed', '');
COMMIT;

-- ----------------------------
-- Table structure for `certification`
-- ----------------------------
DROP TABLE IF EXISTS `certification`;
CREATE TABLE `certification` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`description`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`is_delete`  bit(1) NULL DEFAULT NULL ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of certification
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `clientdetails`
-- ----------------------------
DROP TABLE IF EXISTS `clientdetails`;
CREATE TABLE `clientdetails` (
`appId`  varchar(256) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`resourceIds`  varchar(256) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`appSecret`  varchar(256) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`scope`  varchar(256) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`grantTypes`  varchar(256) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`redirectUrl`  varchar(256) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`authorities`  varchar(256) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`access_token_validity`  int(11) NULL DEFAULT NULL ,
`refresh_token_validity`  int(11) NULL DEFAULT NULL ,
`additionalInformation`  varchar(4096) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`autoApproveScopes`  varchar(256) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`appId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Records of clientdetails
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`candidate_id`  bigint(20) NOT NULL ,
`user_id`  bigint(20) NOT NULL ,
`comment_detail`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`create_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
`interview_id`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`interview_id`) REFERENCES `interview` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`interview_id`) REFERENCES `interview` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of comment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `cv`
-- ----------------------------
DROP TABLE IF EXISTS `cv`;
CREATE TABLE `cv` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`full_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`dob`  date NULL DEFAULT NULL ,
`gender`  bit(1) NULL DEFAULT NULL ,
`phone`  varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`email`  varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`profile_img`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`address`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`education`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`experience_id`  bigint(20) NULL DEFAULT NULL ,
`status_id`  bigint(20) NULL DEFAULT NULL ,
`created_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`created_by`  bigint(20) NULL DEFAULT NULL ,
`edited_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`edited_by`  bigint(20) NULL DEFAULT NULL ,
`facebook`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`skype`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`linkedin`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`note`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`experience_id`) REFERENCES `experience` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`edited_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`status_id`) REFERENCES `cv_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`edited_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`experience_id`) REFERENCES `experience` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`status_id`) REFERENCES `cv_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=53

;

-- ----------------------------
-- Records of cv
-- ----------------------------
BEGIN;
INSERT INTO cv VALUES ('13', null, 'Nguyễn Hữu Nhất', '1986-07-30', '', '0961522633', 'onracings@gmail.com', '', 'Duy Tan – Cau Giay – Ha Noi', 'University of Transport and Communications', '3', '1', '2018-07-12 00:00:00', '4', '2018-07-17 07:35:51', '4', '', '', '', ''), ('14', null, 'Bui Van Menh', '1989-01-19', '', '0964655666 ', 'menhict@gmail.com', 'Capture.JPG', 'Hoanh Bo district, Quang Ninh province', 'Hanoi University of Science and Technology', '4', '1', '2018-07-12 00:00:00', '7', '2018-07-13 14:29:00', '3', '', '', '', ''), ('15', null, 'Lưu Phương Quỳnh', '1990-07-12', '', '0964050860', 'lpqisno102@gmail.com', '', '', 'Hanoi University', '4', '1', '2018-07-16 07:51:00', '6', '2018-07-16 07:50:59', null, '', 'lpqisno1', '', ''), ('16', null, 'Trần Quốc Dũng', null, '', '0982599941', 'trandung.fpt@gmail.com', '', 'Hà Nội', 'FPT University, VietNam', '4', '1', '2018-07-16 09:06:38', '4', '2018-07-16 09:06:38', null, '', '', '', ''), ('17', null, 'Vũ Mạnh Tùng', null, '', '0942880826', 'vutungcoltech@gmail.com', '', 'HN', 'ĐH Công nghệ - ĐHQG HN', '4', '1', '2018-07-16 09:08:33', '4', '2018-07-16 09:08:32', null, '', '', '', ''), ('18', null, 'Trần Thị Thúy Hà', '1992-11-19', '', '', 'tranthuyha.tb@gmail.com', '', 'Ha Noi', 'Đại học Quốc Gia Hà Nội', '2', '1', '2018-07-17 00:00:00', '5', '2018-07-20 03:44:55', '5', '', '', '', ''), ('19', null, 'Nguyễn Việt Anh', '1986-06-19', '', '', '', '', '', '', '4', '1', '2018-07-18 00:00:00', '4', '2018-07-18 03:21:43', '22', '', '', '', ''), ('20', null, 'Lê Văn Thắng', '1995-08-15', '', '01633878739', 'vanthang0508@gmail.com', '', 'Ngõ 281, Trương Định, Hoàng Mai, Hà Nội', 'Đại học Bách khoa Hà Nội', '2', '1', '2018-07-20 02:32:23', '4', '2018-07-20 02:32:23', null, '', '', '', ''), ('21', null, 'Bùi Hồng Đức', '1988-10-05', '', '01228358123 ', 'ducbh88@gmail.com', '', 'HCMC', 'Da Lat University', '3', '1', '2018-07-20 02:47:54', '5', '2018-07-20 02:47:53', null, '', '', '', ''), ('22', null, 'Nguyễn Văn Sơn', null, '', '0969110464', 'hailos1000@gmail.com', '', '158, Yen Hoa, Cau giay', 'Posts and Telecommunications Institute of Technology', '2', '1', '2018-07-20 02:57:33', '4', '2018-07-20 02:57:33', null, '', '', '', ''), ('23', null, 'Nguyễn Văn Khánh', '1991-03-11', '', '0944479968', 'khanhnd113@gmail.com', '', 'Nguyễn An Ninh, Hoàng Mai, HN', 'University of Economics and Business', '3', '1', '2018-07-20 03:01:36', '4', '2018-07-20 03:01:35', null, '', '', '', ''), ('24', null, 'Nguyễn Hữu Vinh', '1992-01-10', '', '0902574748', 'davidnguyen.beats@gmail.com', '', 'Hồ Chí Minh', 'Thanh Dong University', '3', '1', '2018-07-20 03:40:52', '7', '2018-07-20 03:40:52', null, '', '', '', 'Good at English'), ('25', null, 'Giang Thị Thu Hà', '1989-03-12', '', '0902263596', 'hagiang.gtvt@gmail.com', '', 'Ha Noi', 'Đại học Giao thông Vận tải', null, '1', '2018-07-20 03:52:45', '5', '2018-07-20 03:52:45', null, '', '', '', ''), ('26', null, 'Cao Duy Trưng', '1990-01-01', '', '0989996517  ', 'tcaoduyit070189@gmail.com', '', 'Ho Chi Minh City', 'Vietnam Ministry of Posts and Telematics.', '4', '1', '2018-07-20 03:53:52', '7', '2018-07-20 03:53:52', null, '', '', '', 'Good at English\nERP'), ('27', null, 'Nguyễn Văn Tùng', '1987-07-02', '', '0946448399', 'tungnv.gtvt@gmail.com', '', 'Hưng Yên', 'Military Technical Academy', '4', '1', '2018-07-23 02:41:32', '4', '2018-07-23 02:41:31', null, '', '', '', ''), ('28', null, 'Đỗ Đức Giang', '1982-06-04', '', '08099797343', 'doducgiang82@gmail.com', '', '', '', '3', '1', '2018-07-23 00:00:00', '3', '2018-07-24 23:05:06', '3', '', '', '', ''), ('29', null, 'Nguyễn Đức Nguyên', null, '', '07031369803', 'Nguyennd.hus@gmail.com', '', '', 'FPT University', '4', '1', '2018-07-23 00:00:00', '6', '2018-07-24 23:05:43', '3', '', 'nguyenndfu', '', ''), ('30', null, 'Ngô Thị Thảo Phương', '1995-11-27', '', '01658802540', 'thaophuongnt.1127@gmail.com', '', 'Rap Hat Street, Ve An District. Bac Ninh City', 'Foreign Trade University', '2', '1', '2018-07-23 03:36:02', '6', '2018-07-23 03:36:01', null, '', 'phuongtho95', '', ''), ('31', null, 'Trần Văn Kiên', '1984-02-09', '', '0983082334', 'kientranvan@gmail.com', '', '3520 HH4C Linh Đàm, Hoàng Liệt, Hoàng Mai, Hà Nội', '', '4', '1', '2018-07-23 03:41:41', '6', '2018-07-23 03:41:40', null, '', 'tran.van.kien', 'https://www.linkedin.com/in/tran-van-kien-03a377a9/', ''), ('32', null, 'Phạm Thị Tho', '1989-09-04', '', '0989913218', 'noonapham@gmail.com', 'Tho BA.jpg', 'Hà Nội', 'Ha Noi University of Education', '3', '1', '2018-07-23 00:00:00', '7', '2018-07-23 06:50:56', '7', '', 'thopt49', '', 'Phỏng vấn pass cho DU3 - Offer 18.7\nYêu cầu: 1000$'), ('33', null, 'Nguyễn Thị Thoa', '1984-04-30', '', '0918709658', 'nguyenthoa2905@gmail.com', 'Nguyễn Thị Thoa BA.jpg', '3416CT12C KĐT Kim Văn Kim Lũ – Đại Kim – Hoàng Mai', 'Hung Yen university of technology and Education', '4', '1', '2018-07-23 04:47:27', '7', '2018-07-23 04:47:27', null, '', 'nguyenthoa2905', '', ''), ('34', null, 'Nguyễn Cảnh Vinh', '1986-08-21', '', '0909210886', 'vinhnc86@gmail.com', 'Nguyễn Cảnh Vinh PM.png', 'Hà Nội', 'HO CHI MINH CITY UNIVERSITY OF TECHNOLOGY', '3', '1', '2018-07-23 05:15:29', '7', '2018-07-23 05:15:29', null, '', 'vinhnc86@gmail.com', '', 'Có chứng chỉ PMP'), ('35', null, 'Phi Quang Phú', '1977-11-19', '', '0904859696', 'phiphu@gmail.com', '', 'No. 27, 120 HoangHoaTham, Hanoi, Vietnam', '1995 – 2000 Bachelor of Telecommunication Engineering Department of Electronics and Telecommunication Engineering Hanoi University of Communication and Transport, Vietnam 2004 – 2006 Master’s degree of Computer Science School of Engineering of INJE Univer', '4', '1', '2018-07-23 05:45:23', '7', '2018-07-23 05:45:23', null, '', '', '', '1995 – 2000 Bachelor of Telecommunication Engineering\nDepartment of Electronics and Telecommunication Engineering\nHanoi University of Communication and Transport, Vietnam\n2004 – 2006 Master’s degree of Computer Science\nSchool of Engineering of INJE University, Republic of Korea\n2005 TOEFL certificate, 247 CBT'), ('36', null, 'Lê Ngọc Hân', '1986-07-30', '', '0905205786', 'ngochan.business@gmail.com', 'Lê Ngọc Hân  PM.png', 'Hà Nội', 'ngochan.business@gmail.com', '4', '1', '2018-07-23 05:54:59', '7', '2018-07-23 05:54:58', null, 'https://www.facebook.com/hanlhn', '', '', 'Chief Executive Officer\nA course organized by Van Nguyen Education Biz held in Ha Noi, 2018\n58 PDUs – Project Management training\nBased on PMI standard \n - Pass PM nhưng chỉ muốn làm DUlead'), ('37', null, 'Đỗ Văn Việt', '1992-07-29', '', '0936003771', 'vietdv.hus@gmail.com', '', '', 'VNU University of Science', '3', '1', '2018-07-23 06:00:24', '7', '2018-07-23 06:00:24', null, '', 'live:2688c08ea40e008f', '', ''), ('38', null, 'Mai Hải Dương', '1989-12-24', '', '01686348501', 'duongmh.mta@gmail.com', 'Mai Hải Dương.jpg', 'Ha Noi', 'Military Technical Academy', '4', '1', '2018-07-23 00:00:00', '7', '2018-07-23 06:30:29', '7', '', 'duongmh_mta', '', ''), ('39', null, 'Dương Công Nghiệp', '1989-04-14', '', '01686969208', 'congnghiepxd@gmail.com', '', 'Huu Le, Huu Hoa, Thanh Tri, Ha Noi', 'University of Civil Engineering', '4', '1', '2018-07-23 06:47:40', '7', '2018-07-23 06:47:40', null, '', '', '', 'Không dùng dc tiếng anh\nđòi lương quá cao 1k4'), ('40', null, 'Trần Văn Tiệp', '2004-02-15', '', '01679449776', 'tieptran.152@gmail.com', '', 'Ha Noi', 'FPT University', '3', '1', '2018-07-23 07:55:48', '7', '2018-07-23 07:55:48', null, '', 'tieptran.152', '', ''), ('41', null, 'Nguyễn Văn Quy', '1989-01-01', '', '0965528521 ', 'quynv.prodev.vn@gmail.com', '', 'Ha Noi', 'Hanoi University of Business and Technology', null, '1', '2018-07-23 00:00:00', '7', '2018-07-23 08:26:15', '7', '', 'anhquy_1989', '', '092233124'), ('42', null, 'Nguyễn Thị Phương Dung', '1989-08-01', '', '0943989189', 'jung.ntp@gmail.com', '', 'So 18, ngo 122, duong Kim Giang, Hoang Mai, Ha Noi', 'Hanoi University', '4', '1', '2018-07-23 09:47:13', '6', '2018-07-23 09:47:12', null, '', '', '', ''), ('43', null, 'Đỗ Linh Chi', '1995-09-23', '', '0868656105', 'taponkito@gmail.com', '', '26-TT17, khu đô thị Văn Phú, 26-TT17, khu đô thị Văn Phú, phường Phú La,Ha Dong,Ha Noi City', 'The University of Auckland', '2', '1', '2018-07-23 00:00:00', '6', '2018-07-23 09:51:50', '6', '', '', '', 'JLPT N1 (12/2017): 140/180 - Passed \nTOEIC (02/2017): 985/990'), ('44', null, 'Lại Thế Anh', '1991-07-25', '', '0939513666', 'laitheanh.hn@gmail.com', 'Lại Thế Anh.jpg', '34/1194 Chua Lang street, Dong Da, Ha Noi', 'Water Resources University', '3', '1', '2018-07-23 09:50:45', '7', '2018-07-23 09:50:45', null, '', 'anh_lai_the', '', 'Tiếng ANh Khá'), ('45', null, 'Vũ Thị Phương Liên', '1990-06-27', '', '0966841708     ', '27.zun.90@gmail.com', '', 'Room 804, Bld 21 floor, Hacinco Students Village, Trung Hoa, Nhan Chinh, Thanh Xuan District, Hanoi', 'University of Tokyo, Japan', '4', '1', '2018-07-23 09:50:51', '6', '2018-07-23 09:50:51', null, '', '', '', ''), ('46', null, 'Lê Yến', '1990-06-29', '', '01689937021', 'tsubame90@gmail.com', '', 'Phường Cự KHối- Long BIên- Hà Phường Cự KHối- Long BIên- Hà Nội,Long Bien,Ha Noi City', 'Hanoi University', '4', '1', '2018-07-23 09:56:32', '6', '2018-07-23 09:56:31', null, '', '', '', ''), ('47', null, 'Lê Thanh Thư', null, '', '', '', '', '', 'Foreign Trade University', null, '1', '2018-07-23 10:00:27', '6', '2018-07-23 10:00:27', null, 'https://www.facebook.com/thanh.thu.942', '', '', 'Chưa có kinh nghiệm với automotive domain, MMQ. \nComtor tại ARROW Technologies Co., LTD\nĐã làm việc tại JPC FTU (Japanese club)\nĐã làm việc tại VJCC\nTừng học tại Foreign Trade University\nTừng học tại 追手門学院大学　Otemon Gakuin University'), ('48', null, 'Trần Hoa', null, '', '', '', '', '', 'Thang Long University', '4', '1', '2018-07-23 10:12:18', '6', '2018-07-23 10:12:17', null, '', '', 'https://www.linkedin.com/in/hoa-tran-21782011a/', '* JLPT: N1\n* Apr 2017 – Present: \nJapanese Communicator - Team leader Panasonic R&D Center Vietnam'), ('49', null, 'Phạm Trang', '1988-07-17', '', '', 'trang.ats@gmail.com', '', '', '', '4', '1', '2018-07-24 00:00:00', '6', '2018-07-24 09:30:51', '6', '', 'phamtrang0788', '', '- Hiện đang làm tại Framgia. \n- Có kinh nghiệm Comtor - BrSE - PM. \n- Có kinh nghiệm viết spec\n- Có kinh nghiệm sử dụng mmQ.'), ('50', null, 'Nguyễn Thu Hà', '1988-12-12', '', '0985046088', '', '', 'Ha Noi', 'Đại học Mở Hà Nội', '3', '1', '2018-07-24 06:49:18', '5', '2018-07-24 06:49:17', null, '', '', '', ''), ('51', null, 'Nguyễn Cẩm Linh', '1984-12-15', '', '0902037036', 'linhnc284@gmail.com', '', '51/514 – Tran Cung – Bac Tu Liem - Ha Noi', 'Hanoi University', '4', '1', '2018-07-24 08:51:53', '6', '2018-07-24 08:51:52', null, 'https://www.facebook.com/furin.bong', '', '', '- N1\n- Kinh nghiệm Comtor Leader\n- Kinh nghiệm dịch dự án automation test/selenium\n- Mong muốn mức lương 32.000.000'), ('52', null, 'Đào Thế Nam', null, '', '0942082009', 'namdaothe@gmail.com', '', 'Hà Nội', 'University of Greenwich', '3', '1', '2018-07-24 10:47:50', '7', '2018-07-24 10:47:49', null, '', 'gnc.namdt5', '', '');
COMMIT;

-- ----------------------------
-- Table structure for `cv_certification`
-- ----------------------------
DROP TABLE IF EXISTS `cv_certification`;
CREATE TABLE `cv_certification` (
`cv_id`  bigint(20) NOT NULL ,
`certification_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`cv_id`, `certification_id`),
FOREIGN KEY (`certification_id`) REFERENCES `certification` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of cv_certification
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `cv_skill`
-- ----------------------------
DROP TABLE IF EXISTS `cv_skill`;
CREATE TABLE `cv_skill` (
`cv_id`  bigint(20) NOT NULL ,
`skill_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`cv_id`, `skill_id`),
FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of cv_skill
-- ----------------------------
BEGIN;
INSERT INTO cv_skill VALUES ('14', '1'), ('21', '1'), ('24', '1'), ('26', '1'), ('36', '1'), ('37', '1'), ('14', '2'), ('16', '2'), ('17', '2'), ('21', '2'), ('27', '2'), ('34', '2'), ('38', '2'), ('39', '2'), ('40', '2'), ('44', '2'), ('52', '2'), ('14', '3'), ('31', '4'), ('13', '5'), ('32', '6'), ('33', '6'), ('50', '6'), ('16', '7'), ('17', '7'), ('19', '7'), ('29', '7'), ('34', '7'), ('35', '7'), ('36', '7'), ('14', '9'), ('19', '11'), ('21', '11'), ('38', '11'), ('39', '11'), ('40', '11'), ('44', '11'), ('52', '11'), ('27', '14'), ('15', '15'), ('42', '15'), ('43', '15'), ('45', '15'), ('46', '15'), ('47', '15'), ('48', '15'), ('49', '15'), ('51', '15'), ('30', '16'), ('23', '17'), ('20', '22'), ('22', '23'), ('32', '25'), ('33', '25'), ('52', '28'), ('39', '31'), ('52', '31'), ('28', '32'), ('29', '32'), ('28', '33'), ('29', '33'), ('50', '35');
COMMIT;

-- ----------------------------
-- Table structure for `cv_status`
-- ----------------------------
DROP TABLE IF EXISTS `cv_status`;
CREATE TABLE `cv_status` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of cv_status
-- ----------------------------
BEGIN;
INSERT INTO cv_status VALUES ('1', 'Sourced', ''), ('2', 'Blacklist', '');
COMMIT;

-- ----------------------------
-- Table structure for `cv_url`
-- ----------------------------
DROP TABLE IF EXISTS `cv_url`;
CREATE TABLE `cv_url` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`url`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`cv_id`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=42

;

-- ----------------------------
-- Records of cv_url
-- ----------------------------
BEGIN;
INSERT INTO cv_url VALUES ('4', 'CV_MenhBuiVan.pdf', '14'), ('5', 'IT Comtor_ Ms.Luu Phuong Quynh.doc', '15'), ('6', '100718-PM-Trần Quốc Dũng.docx', '16'), ('7', '120718-PM-Vũ Mạnh Tùng.pdf', '17'), ('9', '100718-Android-Nguyễn Hữu Nhất.doc', '13'), ('12', 'Scope.xlsx', '19'), ('13', '030718-Reactnative- Lê Văn Thắng.pdf', '20'), ('14', '0001dotnet_Bui Hong Duc.pdf', '21'), ('15', '050718 -AngularJS- Nguyễn Văn Sơn.doc', '22'), ('16', '170718-AM-Nguyen Van Khanh.pdf', '23'), ('17', '6.26. Nguyen_Huu_Vinh_3790492.pdf', '24'), ('18', '0001 BA Trần Thị Thúy Hà.docx', '18'), ('19', '0002 BA Giang Thi Thu Ha.docx', '25'), ('20', 'Cao Duy Trung - CV Apply.docx', '26'), ('21', '030718-Net-Nguyen Van Tung.doc', '27'), ('22', 'CV-DO-DUC-GIANG2018 (1).pdf', '28'), ('23', '180723 CV - Ngo Thi Thao Phuong_Comtor.pdf', '30'), ('24', '180903 - CV_Trần Văn Kiên_ PHP Teamlead.pdf', '31'), ('25', 'CV_Business Analyst-PhamThiTho_2018.doc', '32'), ('26', 'CV_NguyenThiThoa.docx', '33'), ('27', 'Resume - Nguyen Canh Vinh.pdf', '34'), ('28', 'PhiQuangPhu-CMC-2018.pdf', '35'), ('29', 'CMC-Resume-Mark-Ngoc-Han-Le.pdf', '36'), ('30', 'Curriculum vitae - Mai Hai Duong.doc', '38'), ('31', '7.17. Dương Công Nghiệp.doc', '39'), ('32', 'Technical-Summary-Tiep-Tran.docx', '40'), ('33', 'Nguyễn Văn Quy.docx', '41'), ('34', 'Comtor N2 - Nguyễn Thị Phương Dung.pdf', '42'), ('35', 'Comtor N1 - Đỗ Linh Chi.pdf', '43'), ('36', 'AnhLT.C#.Java.CV.EN.pdf', '44'), ('37', 'IT Comtor - N1 - Vũ Thị Phương Liên.pdf', '45'), ('38', 'IT Comtor N1 - Lê Yến.pdf', '46'), ('39', '0003 BA Nguyễn Thu Hà.pdf', '50'), ('40', '履歴書_Nguyen-Cam-Linh.doc', '51'), ('41', 'CV_DAO THE NAM_Senior Developer.docx', '52');
COMMIT;

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`is_delete`  bit(1) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=30

;

-- ----------------------------
-- Records of department
-- ----------------------------
BEGIN;
INSERT INTO department VALUES ('1', 'DU1', 'Delivery Unit 1 - TTSX 1', ''), ('2', 'DU2', 'Delivery Unit 2 - TTSX 2', ''), ('3', 'DU3', 'Delivery Unit 3 - TTSX 3', ''), ('4', 'BU', 'Trung tâm Kinh doanh', ''), ('7', 'BOM', 'Ban giám đốc', ''), ('8', 'QA', 'Phòng Quản trị Chất lượng', ''), ('9', 'RRC', 'Trung tâm phát triển nguồn lực', ''), ('10', 'ACCOUNTING Dept.', 'Phòng Tài chính Kế toán', ''), ('11', 'HR Dept.', 'Phòng Quản trị Nhân lực', ''), ('12', 'ADMIN. Dept.', 'Phòng Hành chính', ''), ('23', 'DU5', 'Delivery Unit 5 - TTSX 5', ''), ('24', 'DU6', 'Delivery Unit 6 - TTSX 6', ''), ('25', 'IT', 'It support', ''), ('26', 'BU1', 'BU1', ''), ('27', 'BU2', 'BU2', ''), ('28', 'BU3', 'BU3', ''), ('29', 'HR', 'HR', '');
COMMIT;

-- ----------------------------
-- Table structure for `experience`
-- ----------------------------
DROP TABLE IF EXISTS `experience`;
CREATE TABLE `experience` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`is_delete`  bit(1) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=5

;

-- ----------------------------
-- Records of experience
-- ----------------------------
BEGIN;
INSERT INTO experience VALUES ('1', 'Less than 1 year', 'Less than 1 year', ''), ('2', '1 to 3 years', '1 to 3 years', ''), ('3', '3 to 5 years', '3 to 5 years', ''), ('4', 'More than 5 years', 'More than 5 years', '');
COMMIT;

-- ----------------------------
-- Table structure for `foreign_language`
-- ----------------------------
DROP TABLE IF EXISTS `foreign_language`;
CREATE TABLE `foreign_language` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`is_delete`  bit(1) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=6

;

-- ----------------------------
-- Records of foreign_language
-- ----------------------------
BEGIN;
INSERT INTO foreign_language VALUES ('1', 'English', 'English', ''), ('2', 'Japanese', 'Japanese', ''), ('3', 'Korea', 'Korea', ''), ('5', 'Korean', 'Korean', '');
COMMIT;

-- ----------------------------
-- Table structure for `group_request`
-- ----------------------------
DROP TABLE IF EXISTS `group_request`;
CREATE TABLE `group_request` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`description`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of group_request
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `groups`
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`description`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=5

;

-- ----------------------------
-- Records of groups
-- ----------------------------
BEGIN;
INSERT INTO groups VALUES ('1', 'Delivery Unit', 'DU'), ('2', 'Back Office', 'BO'), ('3', 'Sale', 'Sale'), ('4', 'QA', 'QA');
COMMIT;

-- ----------------------------
-- Table structure for `interview`
-- ----------------------------
DROP TABLE IF EXISTS `interview`;
CREATE TABLE `interview` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`start_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`end_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`location`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`note`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`status_id`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`status_id`) REFERENCES `interview_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`status_id`) REFERENCES `interview_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of interview
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `interview_candidate`
-- ----------------------------
DROP TABLE IF EXISTS `interview_candidate`;
CREATE TABLE `interview_candidate` (
`interview_id`  bigint(20) NOT NULL ,
`candidate_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`interview_id`, `candidate_id`),
FOREIGN KEY (`interview_id`) REFERENCES `interview` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`interview_id`) REFERENCES `interview` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of interview_candidate
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `interview_status`
-- ----------------------------
DROP TABLE IF EXISTS `interview_status`;
CREATE TABLE `interview_status` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of interview_status
-- ----------------------------
BEGIN;
INSERT INTO interview_status VALUES ('1', 'New', ''), ('2', 'In-Process', ''), ('3', 'Done', '');
COMMIT;

-- ----------------------------
-- Table structure for `log`
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`actor`  bigint(20) NOT NULL ,
`action`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`log_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`table_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`content`  text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`assignee_id`  int(11) NULL DEFAULT NULL ,
`interviewer_id`  int(11) NULL DEFAULT NULL ,
`request_id`  int(11) NULL DEFAULT NULL ,
`candidate_id`  int(11) NULL DEFAULT NULL ,
`interview_id`  int(11) NULL DEFAULT NULL ,
`cv_id`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`actor`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`actor`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=636

;

-- ----------------------------
-- Records of log
-- ----------------------------
BEGIN;
INSERT INTO log VALUES ('261', '8', 'create', '2018-07-11 15:12:46', 'request', '', null, null, '20', null, null, null), ('262', '8', 'create', '2018-07-11 15:15:20', 'request', '', null, null, '21', null, null, null), ('263', '8', 'approved', '2018-07-11 15:15:30', 'request', '', null, null, '20', null, null, null), ('264', '3', 'assign', '2018-07-11 15:16:01', 'request', '', '7', null, '20', null, null, null), ('265', '8', 'approved', '2018-07-12 02:08:59', 'request', '', null, null, '21', null, null, null), ('266', '8', 'create', '2018-07-12 02:17:30', 'request', '', null, null, '22', null, null, null), ('267', '8', 'create', '2018-07-12 02:18:49', 'request', '', null, null, '23', null, null, null), ('268', '8', 'approved', '2018-07-12 02:18:59', 'request', '', null, null, '23', null, null, null), ('269', '8', 'approved', '2018-07-12 02:19:08', 'request', '', null, null, '22', null, null, null), ('270', '8', 'create', '2018-07-12 02:28:00', 'request', '', null, null, '24', null, null, null), ('271', '8', 'approved', '2018-07-12 02:29:33', 'request', '', null, null, '24', null, null, null), ('272', '21', 'update', '2018-07-12 02:32:14', 'request', '', null, null, '24', null, null, null), ('273', '18', 'update', '2018-07-12 02:35:13', 'request', '', null, null, '24', null, null, null), ('274', '8', 'create', '2018-07-12 02:38:55', 'request', '', null, null, '25', null, null, null), ('275', '8', 'create', '2018-07-12 02:40:58', 'request', '', null, null, '26', null, null, null), ('276', '8', 'approved', '2018-07-12 02:41:02', 'request', '', null, null, '26', null, null, null), ('277', '8', 'approved', '2018-07-12 02:41:07', 'request', '', null, null, '25', null, null, null), ('278', '18', 'update', '2018-07-12 02:43:47', 'request', '', null, null, '20', null, null, null), ('279', '8', 'create', '2018-07-12 02:43:51', 'project', '', null, null, '25', null, null, null), ('280', '18', 'update', '2018-07-12 02:43:51', 'request', '', null, null, '20', null, null, null), ('281', '22', 'update', '2018-07-12 02:52:48', 'request', '', null, null, '26', null, null, null), ('282', '22', 'update', '2018-07-12 02:53:18', 'request', '', null, null, '26', null, null, null), ('283', '18', 'update', '2018-07-12 03:48:38', 'request', '', null, null, '20', null, null, null), ('284', '3', 'assign', '2018-07-12 04:31:05', 'request', '', '25', null, '20', null, null, null), ('285', '3', 'assign', '2018-07-12 04:33:37', 'request', '', '25', null, '22', null, null, null), ('286', '3', 'assign', '2018-07-12 04:33:44', 'request', '', '24', null, '22', null, null, null), ('287', '3', 'assign', '2018-07-12 04:34:59', 'request', '', '7', null, '21', null, null, null), ('288', '3', 'assign', '2018-07-12 04:35:08', 'request', '', '5', null, '21', null, null, null), ('289', '3', 'assign', '2018-07-12 04:35:30', 'request', '', '25', null, '23', null, null, null), ('290', '3', 'assign', '2018-07-12 04:35:37', 'request', '', '24', null, '23', null, null, null), ('291', '3', 'assign', '2018-07-12 04:36:18', 'request', '', '4', null, '25', null, null, null), ('292', '3', 'assign', '2018-07-12 04:36:27', 'request', '', '7', null, '25', null, null, null), ('293', '3', 'assign', '2018-07-12 04:36:54', 'request', '', '7', null, '24', null, null, null), ('294', '3', 'assign', '2018-07-12 04:37:01', 'request', '', '4', null, '24', null, null, null), ('295', '3', 'assign', '2018-07-12 04:37:23', 'request', '', '6', null, '26', null, null, null), ('296', '3', 'assign', '2018-07-12 04:37:33', 'request', '', '5', null, '26', null, null, null), ('297', '3', 'assign', '2018-07-12 04:37:43', 'request', '', '4', null, '26', null, null, null), ('298', '7', 'Make candidate to request', '2018-07-12 04:54:30', 'candidate', null, null, null, '24', '17', null, '14'), ('299', '7', 'change status', '2018-07-12 04:55:26', 'candidate', 'Apply to Interview', null, null, null, '17', null, null), ('300', '7', 'change status', '2018-07-12 04:55:51', 'candidate', 'Interview to Contacting', null, null, null, '17', null, null), ('301', '7', 'change status', '2018-07-12 04:56:14', 'candidate', 'Contacting to Interview', null, null, null, '17', null, null), ('302', '7', 'change status', '2018-07-12 04:56:42', 'candidate', 'Interview to Contacting', null, null, null, '17', null, null), ('303', '7', 'change status', '2018-07-12 04:56:58', 'candidate', 'Contacting to Interview', null, null, null, '17', null, null), ('304', '22', 'update', '2018-07-12 04:58:39', 'request', '', null, null, '21', null, null, null), ('305', '18', 'update', '2018-07-12 04:59:17', 'request', '', null, null, '21', null, null, null), ('306', '18', 'update', '2018-07-12 05:05:49', 'request', '', null, null, '21', null, null, null), ('307', '9', 'create', '2018-07-12 05:58:01', 'request', '', null, null, '27', null, null, null), ('308', '11', 'create', '2018-07-12 06:03:21', 'request', '', null, null, '29', null, null, null), ('309', '11', 'create', '2018-07-12 06:03:21', 'request', '', null, null, '28', null, null, null), ('310', '18', 'update', '2018-07-12 06:03:46', 'request', '', null, null, '21', null, null, null), ('311', '11', 'update', '2018-07-12 06:04:19', 'request', '', null, null, '29', null, null, null), ('312', '18', 'update', '2018-07-12 06:04:30', 'request', '', null, null, '21', null, null, null), ('313', '11', 'update', '2018-07-12 06:05:16', 'request', '', null, null, '29', null, null, null), ('314', '11', 'update', '2018-07-12 06:05:50', 'request', '', null, null, '28', null, null, null), ('315', '11', 'update', '2018-07-12 06:06:19', 'request', '', null, null, '29', null, null, null), ('316', '11', 'submit', '2018-07-12 06:23:12', 'request', '', null, null, '29', null, null, null), ('317', '11', 'submit', '2018-07-12 06:23:14', 'request', '', null, null, '28', null, null, null), ('318', '8', 'approved', '2018-07-12 06:23:35', 'request', '', null, null, '29', null, null, null), ('319', '8', 'approved', '2018-07-12 06:23:40', 'request', '', null, null, '28', null, null, null), ('320', '9', 'submit', '2018-07-12 06:24:18', 'request', '', null, null, '27', null, null, null), ('321', '8', 'approved', '2018-07-12 06:24:34', 'request', '', null, null, '27', null, null, null), ('322', '10', 'create', '2018-07-12 06:30:05', 'request', '', null, null, '30', null, null, null), ('323', '10', 'submit', '2018-07-12 06:30:10', 'request', '', null, null, '30', null, null, null), ('324', '8', 'approved', '2018-07-12 06:30:33', 'request', '', null, null, '30', null, null, null), ('325', '11', 'create', '2018-07-12 06:33:38', 'request', '', null, null, '31', null, null, null), ('326', '22', 'update', '2018-07-12 06:33:45', 'request', '', null, null, '30', null, null, null), ('327', '11', 'submit', '2018-07-12 06:33:46', 'request', '', null, null, '31', null, null, null), ('328', '11', 'create', '2018-07-12 06:35:31', 'request', '', null, null, '32', null, null, null), ('329', '11', 'submit', '2018-07-12 06:35:35', 'request', '', null, null, '32', null, null, null), ('330', '3', 'assign', '2018-07-12 06:37:34', 'request', '', '6', null, '30', null, null, null), ('331', '18', 'update', '2018-07-12 06:37:43', 'request', '', null, null, '21', null, null, null), ('332', '3', 'assign', '2018-07-12 06:37:56', 'request', '', '7', null, '29', null, null, null), ('333', '3', 'assign', '2018-07-12 06:38:08', 'request', '', '7', null, '28', null, null, null), ('334', '3', 'assign', '2018-07-12 06:38:22', 'request', '', '4', null, '27', null, null, null), ('335', '18', 'update', '2018-07-12 06:53:25', 'request', '', null, null, '26', null, null, null), ('336', '8', 'approved', '2018-07-12 07:15:35', 'request', '', null, null, '32', null, null, null), ('337', '8', 'rejected', '2018-07-12 07:15:46', 'request', 'Duplicated', null, null, '31', null, null, null), ('338', '3', 'assign', '2018-07-12 08:30:52', 'request', '', '7', null, '32', null, null, null), ('339', '11', 'create', '2018-07-13 02:48:52', 'request', '', null, null, '33', null, null, null), ('340', '11', 'submit', '2018-07-13 02:50:09', 'request', '', null, null, '33', null, null, null), ('341', '8', 'create', '2018-07-13 08:13:08', 'request', '', null, null, '34', null, null, null), ('342', '8', 'approved', '2018-07-13 08:13:12', 'request', '', null, null, '34', null, null, null), ('343', '3', 'assign', '2018-07-13 08:16:23', 'request', '', '6', null, '34', null, null, null), ('344', '8', 'comment', '2018-07-13 08:17:22', 'candidate', 'Tiếng Anh trao đổi không tốt lắm', null, null, null, '17', null, null), ('345', '8', 'comment', '2018-07-13 08:18:09', 'candidate', 'Về năng lực quản lý phù hợp với các dự án làm sản phẩm, trong thị trường trong nước', null, null, null, '17', null, null), ('346', '18', 'update', '2018-07-13 09:08:44', 'request', '', null, null, '21', null, null, null), ('347', '10', 'create', '2018-07-13 10:56:05', 'request', '', null, null, '35', null, null, null), ('348', '10', 'submit', '2018-07-13 10:56:18', 'request', '', null, null, '35', null, null, null), ('349', '8', 'approved', '2018-07-13 11:01:05', 'request', '', null, null, '33', null, null, null), ('350', '8', 'approved', '2018-07-13 11:01:11', 'request', '', null, null, '35', null, null, null), ('351', '3', 'assign', '2018-07-13 11:20:31', 'request', '', '5', null, '35', null, null, null), ('352', '3', 'assign', '2018-07-13 14:24:22', 'request', '', '7', null, '33', null, null, null), ('353', '22', 'update', '2018-07-16 02:14:21', 'request', '', null, null, '35', null, null, null), ('354', '22', 'update', '2018-07-16 02:18:47', 'request', '', null, null, '34', null, null, null), ('355', '22', 'update', '2018-07-16 02:43:15', 'request', '', null, null, '35', null, null, null), ('356', '22', 'update', '2018-07-16 02:57:47', 'request', '', null, null, '26', null, null, null), ('357', '22', 'update', '2018-07-16 02:59:25', 'request', '', null, null, '29', null, null, null), ('360', '22', 'update', '2018-07-16 03:57:45', 'request', '', null, null, '34', null, null, null), ('361', '8', 'create', '2018-07-16 03:58:07', 'project', '', null, null, '29', null, null, null), ('362', '22', 'update', '2018-07-16 03:58:07', 'request', '', null, null, '34', null, null, null), ('363', '22', 'update', '2018-07-16 04:49:19', 'request', '', null, null, '26', null, null, null);
INSERT INTO log VALUES ('364', '22', 'update', '2018-07-16 06:50:07', 'request', '', null, null, '24', null, null, null), ('365', '22', 'update', '2018-07-16 06:50:19', 'request', '', null, null, '25', null, null, null), ('366', '22', 'update', '2018-07-16 07:06:24', 'request', '', null, null, '27', null, null, null), ('367', '6', 'Make candidate to request', '2018-07-16 08:07:11', 'candidate', null, null, null, '34', '18', null, '15'), ('368', '6', 'change status', '2018-07-16 09:06:30', 'candidate', 'Apply to Interview', null, null, null, '18', null, null), ('369', '6', 'change status', '2018-07-16 09:06:43', 'candidate', 'Interview to Contacting', null, null, null, '18', null, null), ('370', '4', 'Make candidate to request', '2018-07-16 09:12:19', 'candidate', null, null, null, '24', '19', null, '17'), ('371', '4', 'change status', '2018-07-16 09:13:28', 'candidate', 'Apply to Interview', null, null, null, '19', null, null), ('372', '4', 'change status', '2018-07-16 09:13:48', 'candidate', 'Interview to Offer', null, null, null, '19', null, null), ('373', '4', 'change status', '2018-07-16 09:13:58', 'candidate', 'Offer to Interview', null, null, null, '19', null, null), ('374', '4', 'Make candidate to request', '2018-07-16 09:15:20', 'candidate', null, null, null, '27', '20', null, '16'), ('375', '4', 'Make candidate to request', '2018-07-16 09:17:00', 'candidate', null, null, null, '27', '21', null, '13'), ('376', '4', 'change status', '2018-07-16 09:17:12', 'candidate', 'Apply to Closed', null, null, null, '20', null, null), ('377', '4', 'change status', '2018-07-16 09:17:59', 'candidate', 'Apply to Offer', null, null, null, '21', null, null), ('378', '4', 'Make candidate to request', '2018-07-17 02:18:45', 'candidate', null, null, null, '27', '22', null, '17'), ('379', '4', 'change status', '2018-07-17 02:18:57', 'candidate', 'Apply to Onboard', null, null, null, '22', null, null), ('380', '4', 'change status', '2018-07-17 02:19:30', 'candidate', 'Onboard to Apply', null, null, null, '22', null, null), ('381', '4', 'change status', '2018-07-17 02:20:16', 'candidate', 'Offer to Closed', null, null, null, '21', null, null), ('382', '4', 'Make candidate to request', '2018-07-17 02:22:55', 'candidate', null, null, null, '24', '23', null, '16'), ('383', '4', 'change status', '2018-07-17 02:23:04', 'candidate', 'Apply to Interview', null, null, null, '23', null, null), ('384', '4', 'comment', '2018-07-17 02:25:16', 'candidate', 'hhhh', null, null, null, '23', null, null), ('385', '5', 'Make candidate to request', '2018-07-17 02:27:57', 'candidate', null, null, null, '35', '24', null, '18'), ('388', '6', 'change status', '2018-07-17 02:48:19', 'candidate', 'Interview to Onboard', null, null, null, '18', null, null), ('389', '4', 'change status', '2018-07-17 02:49:50', 'candidate', 'Interview to Onboard', null, null, null, '23', null, null), ('390', '4', 'change status', '2018-07-17 02:50:03', 'candidate', 'Interview to Onboard', null, null, null, '19', null, null), ('391', '4', 'change status', '2018-07-17 02:50:09', 'candidate', 'Onboard to Closed', null, null, null, '19', null, null), ('392', '4', 'change status', '2018-07-17 02:50:47', 'candidate', 'Closed to Interview', null, null, null, '19', null, null), ('393', '4', 'change status', '2018-07-17 02:50:57', 'candidate', 'Onboard to Interview', null, null, null, '23', null, null), ('394', '4', 'change status', '2018-07-17 02:51:12', 'candidate', 'Interview to Onboard', null, null, null, '23', null, null), ('395', '4', 'change status', '2018-07-17 02:51:27', 'candidate', 'Onboard to Closed', null, null, null, '23', null, null), ('396', '4', 'change status', '2018-07-17 03:07:49', 'candidate', 'Interview to Onboard', null, null, null, '19', null, null), ('397', '4', 'change status', '2018-07-17 03:07:54', 'candidate', 'Onboard to Closed', null, null, null, '19', null, null), ('398', '4', 'change status', '2018-07-17 03:09:57', 'candidate', 'Closed to Contacting', null, null, null, '23', null, null), ('399', '4', 'change status', '2018-07-17 03:10:04', 'candidate', 'Closed to Contacting', null, null, null, '19', null, null), ('400', '4', 'change status', '2018-07-17 03:10:09', 'candidate', 'Contacting to Interview', null, null, null, '23', null, null), ('401', '4', 'change status', '2018-07-17 03:10:13', 'candidate', 'Contacting to Interview', null, null, null, '19', null, null), ('403', '8', 'create', '2018-07-17 03:39:43', 'request', '', null, null, '36', null, null, null), ('404', '8', 'update', '2018-07-17 03:39:59', 'request', '', null, null, '36', null, null, null), ('405', '8', 'approved', '2018-07-17 03:40:17', 'request', '', null, null, '36', null, null, null), ('406', '4', 'Update title, source.', '2018-07-17 03:54:33', 'candidate', '', null, null, null, '23', null, null), ('407', '22', 'update', '2018-07-17 08:38:21', 'request', '', null, null, '34', null, null, null), ('408', '22', 'update', '2018-07-17 08:39:05', 'request', '', null, null, '20', null, null, null), ('409', '22', 'update', '2018-07-17 08:53:12', 'request', '', null, null, '34', null, null, null), ('410', '22', 'update', '2018-07-17 08:57:13', 'request', '', null, null, '20', null, null, null), ('411', '22', 'update', '2018-07-17 09:00:14', 'request', '', null, null, '21', null, null, null), ('412', '22', 'update', '2018-07-17 09:00:34', 'request', '', null, null, '21', null, null, null), ('413', '22', 'update', '2018-07-17 09:00:45', 'request', '', null, null, '21', null, null, null), ('414', '22', 'update', '2018-07-17 09:00:58', 'request', '', null, null, '21', null, null, null), ('415', '8', 'create', '2018-07-17 09:13:49', 'project', '', null, null, '30', null, null, null), ('416', '22', 'update', '2018-07-17 09:13:49', 'request', '', null, null, '21', null, null, null), ('417', '22', 'update', '2018-07-17 09:18:25', 'request', '', null, null, '36', null, null, null), ('418', '22', 'update', '2018-07-17 09:19:40', 'request', '', null, null, '35', null, null, null), ('419', '10', 'create', '2018-07-17 09:20:22', 'project', '', null, null, '31', null, null, null), ('420', '22', 'update', '2018-07-17 09:20:22', 'request', '', null, null, '35', null, null, null), ('421', '11', 'create', '2018-07-17 09:27:14', 'request', '', null, null, '37', null, null, null), ('422', '11', 'create', '2018-07-17 09:28:44', 'request', '', null, null, '38', null, null, null), ('423', '11', 'submit', '2018-07-17 09:28:49', 'request', '', null, null, '38', null, null, null), ('424', '11', 'submit', '2018-07-17 09:28:51', 'request', '', null, null, '37', null, null, null), ('425', '3', 'create', '2018-07-17 09:37:30', 'request', '', null, null, '39', null, null, null), ('426', '3', 'update', '2018-07-17 09:37:51', 'request', '', null, null, '39', null, null, null), ('427', '22', 'update', '2018-07-17 09:55:05', 'request', '', null, null, '39', null, null, null), ('428', '22', 'update', '2018-07-17 09:55:42', 'request', '', null, null, '39', null, null, null), ('429', '22', 'update', '2018-07-17 09:56:29', 'request', '', null, null, '39', null, null, null), ('430', '22', 'update', '2018-07-17 09:56:55', 'request', '', null, null, '39', null, null, null), ('431', '22', 'update', '2018-07-17 10:04:20', 'request', '', null, null, '39', null, null, null), ('432', '22', 'update', '2018-07-17 10:06:38', 'request', '', null, null, '39', null, null, null), ('433', '22', 'update', '2018-07-17 10:06:55', 'request', '', null, null, '39', null, null, null), ('434', '22', 'update', '2018-07-17 10:07:20', 'request', '', null, null, '39', null, null, null), ('435', '22', 'update', '2018-07-17 10:07:32', 'request', '', null, null, '39', null, null, null), ('436', '8', 'comment', '2018-07-17 10:32:23', 'candidate', 'Tiếng anh trao đổi cũng tàm tạm. Nhưng không tốt lắm. Đã quản lý các dự án 15~20 người. Năng lực quản lý 3~4 năm kinh nghiệm, tương đối chắc chắn.', null, null, null, '19', null, null), ('437', '8', 'comment', '2018-07-17 10:33:09', 'candidate', 'Có kinh nghiệm làm presales', null, null, null, '19', null, null), ('438', '22', 'comment', '2018-07-17 10:36:41', 'candidate', 'comment', null, null, null, '24', null, null), ('439', '4', 'change status', '2018-07-18 02:27:46', 'candidate', 'Apply to Onboard', null, null, null, '22', null, null), ('440', '22', 'update', '2018-07-18 02:33:58', 'request', '', null, null, '21', null, null, null), ('441', '4', 'Make candidate to request', '2018-07-18 02:41:08', 'candidate', null, null, null, '26', '25', null, '19'), ('442', '4', 'change status', '2018-07-18 02:41:13', 'candidate', 'Apply to Interview', null, null, null, '25', null, null), ('443', '3', 'assign', '2018-07-18 02:42:18', 'request', '', '5', null, '25', null, null, null), ('444', '3', 'assign', '2018-07-18 02:52:14', 'request', '', '5', null, '25', null, null, null), ('445', '3', 'assign', '2018-07-18 02:52:14', 'request', '', '7', null, '25', null, null, null), ('446', '3', 'assign', '2018-07-18 02:52:14', 'request', '', '4', null, '25', null, null, null), ('447', '4', 'change status', '2018-07-18 02:58:37', 'candidate', 'Onboard to Closed', null, null, null, '22', null, null), ('448', '4', 'change status', '2018-07-18 02:58:47', 'candidate', 'Closed to Onboard', null, null, null, '21', null, null), ('450', '8', 'approved', '2018-07-19 07:13:21', 'request', '', null, null, '38', null, null, null), ('451', '8', 'approved', '2018-07-19 07:13:27', 'request', '', null, null, '37', null, null, null), ('452', '8', 'update', '2018-07-19 07:13:56', 'request', '', null, null, '38', null, null, null), ('453', '8', 'update', '2018-07-19 07:14:19', 'request', '', null, null, '37', null, null, null), ('454', '8', 'update', '2018-07-19 07:16:39', 'request', '', null, null, '26', null, null, null), ('455', '8', 'update', '2018-07-19 07:16:58', 'request', '', null, null, '25', null, null, null), ('456', '8', 'update', '2018-07-19 07:17:22', 'request', '', null, null, '20', null, null, null), ('457', '8', 'update', '2018-07-19 07:17:30', 'request', '', null, null, '20', null, null, null), ('459', '22', 'update', '2018-07-19 08:09:48', 'request', '', null, null, '35', null, null, null), ('460', '22', 'update', '2018-07-19 08:09:56', 'request', '', null, null, '35', null, null, null), ('461', '22', 'update', '2018-07-19 08:10:14', 'request', '', null, null, '35', null, null, null), ('462', '22', 'update', '2018-07-19 08:21:54', 'request', '', null, null, '36', null, null, null), ('463', '12', 'create', '2018-07-19 08:25:07', 'request', '', null, null, '56', null, null, null), ('464', '8', 'update', '2018-07-19 08:30:29', 'request', '', null, null, '24', null, null, null), ('465', '3', 'create', '2018-07-19 09:06:55', 'request', '', null, null, '57', null, null, null), ('466', '3', 'create', '2018-07-19 09:17:23', 'request', '', null, null, '58', null, null, null), ('467', '3', 'create', '2018-07-19 09:21:56', 'request', '', null, null, '59', null, null, null), ('468', '3', 'update', '2018-07-19 09:22:15', 'request', '', null, null, '58', null, null, null), ('469', '3', 'create', '2018-07-19 09:25:55', 'request', '', null, null, '60', null, null, null);
INSERT INTO log VALUES ('470', '3', 'update', '2018-07-19 09:26:28', 'request', '', null, null, '60', null, null, null), ('471', '3', 'assign', '2018-07-19 10:32:23', 'request', '', '4', null, '37', null, null, null), ('472', '3', 'submit', '2018-07-19 11:41:15', 'request', '', null, null, '60', null, null, null), ('473', '3', 'submit', '2018-07-19 11:41:17', 'request', '', null, null, '59', null, null, null), ('474', '3', 'submit', '2018-07-19 11:41:20', 'request', '', null, null, '58', null, null, null), ('475', '3', 'submit', '2018-07-19 11:41:23', 'request', '', null, null, '57', null, null, null), ('476', '3', 'submit', '2018-07-19 11:41:27', 'request', '', null, null, '56', null, null, null), ('477', '3', 'submit', '2018-07-19 11:41:31', 'request', '', null, null, '39', null, null, null), ('478', '17', 'approved', '2018-07-19 11:42:02', 'request', '', null, null, '60', null, null, null), ('479', '17', 'approved', '2018-07-19 11:42:07', 'request', '', null, null, '59', null, null, null), ('480', '17', 'approved', '2018-07-19 11:42:12', 'request', '', null, null, '58', null, null, null), ('481', '17', 'approved', '2018-07-19 11:42:19', 'request', '', null, null, '57', null, null, null), ('482', '17', 'approved', '2018-07-19 11:42:24', 'request', '', null, null, '56', null, null, null), ('483', '3', 'assign', '2018-07-19 11:43:00', 'request', '', '4', null, '60', null, null, null), ('484', '3', 'assign', '2018-07-19 11:43:14', 'request', '', '6', null, '59', null, null, null), ('485', '3', 'assign', '2018-07-19 11:43:27', 'request', '', '6', null, '59', null, null, null), ('486', '3', 'assign', '2018-07-19 11:43:39', 'request', '', '6', null, '59', null, null, null), ('487', '3', 'assign', '2018-07-19 11:43:39', 'request', '', '24', null, '59', null, null, null), ('488', '3', 'assign', '2018-07-19 11:43:54', 'request', '', '3', null, '59', null, null, null), ('489', '3', 'assign', '2018-07-19 11:43:54', 'request', '', '6', null, '59', null, null, null), ('490', '3', 'assign', '2018-07-19 11:44:23', 'request', '', '6', null, '58', null, null, null), ('491', '3', 'assign', '2018-07-19 11:44:58', 'request', '', '6', null, '58', null, null, null), ('492', '3', 'assign', '2018-07-19 11:44:58', 'request', '', '3', null, '58', null, null, null), ('493', '3', 'assign', '2018-07-19 11:45:18', 'request', '', '6', null, '57', null, null, null), ('494', '3', 'assign', '2018-07-19 11:45:30', 'request', '', '6', null, '57', null, null, null), ('495', '3', 'assign', '2018-07-19 11:45:30', 'request', '', '3', null, '57', null, null, null), ('496', '3', 'assign', '2018-07-19 11:45:52', 'request', '', '6', null, '56', null, null, null), ('497', '3', 'assign', '2018-07-19 11:45:52', 'request', '', '4', null, '56', null, null, null), ('498', '3', 'assign', '2018-07-19 11:46:06', 'request', '', '4', null, '38', null, null, null), ('499', '5', 'Make candidate to request', '2018-07-20 02:49:07', 'candidate', null, null, null, '25', '26', null, '21'), ('500', '5', 'change status', '2018-07-20 02:49:29', 'candidate', 'Apply to Offer', null, null, null, '26', null, null), ('501', '4', 'Make candidate to request', '2018-07-20 02:51:42', 'candidate', null, null, null, '37', '27', null, '20'), ('502', '4', 'change status', '2018-07-20 02:51:51', 'candidate', 'Apply to Onboard', null, null, null, '27', null, null), ('503', '8', 'approved', '2018-07-20 02:53:48', 'request', '', null, null, '39', null, null, null), ('504', '4', 'Make candidate to request', '2018-07-20 02:58:06', 'candidate', null, null, null, '38', '28', null, '22'), ('505', '4', 'change status', '2018-07-20 02:58:14', 'candidate', 'Apply to Offer', null, null, null, '28', null, null), ('506', '4', 'Make candidate to request', '2018-07-20 03:01:49', 'candidate', null, null, null, '60', '29', null, '23'), ('507', '4', 'change status', '2018-07-20 03:01:56', 'candidate', 'Apply to Offer', null, null, null, '29', null, null), ('508', '7', 'Make candidate to request', '2018-07-20 03:41:35', 'candidate', null, null, null, '28', '30', null, '24'), ('509', '7', 'change status', '2018-07-20 03:41:56', 'candidate', 'Apply to Onboard', null, null, null, '30', null, null), ('510', '5', 'Make candidate to request', '2018-07-20 03:53:06', 'candidate', null, null, null, '21', '31', null, '25'), ('511', '5', 'Make candidate to request', '2018-07-20 03:53:07', 'candidate', null, null, null, '21', '32', null, '18'), ('512', '5', 'change status', '2018-07-20 03:53:16', 'candidate', 'Apply to Interview', null, null, null, '32', null, null), ('513', '5', 'change status', '2018-07-20 03:53:26', 'candidate', 'Apply to Interview', null, null, null, '31', null, null), ('514', '7', 'Make candidate to request', '2018-07-20 03:57:18', 'candidate', null, null, null, '28', '33', null, '26'), ('515', '7', 'change status', '2018-07-20 07:10:53', 'candidate', 'Apply to Onboard', null, null, null, '33', null, null), ('516', '3', 'assign', '2018-07-20 07:11:21', 'request', '', '5', null, '35', null, null, null), ('517', '3', 'assign', '2018-07-20 07:11:21', 'request', '', '7', null, '35', null, null, null), ('518', '3', 'update', '2018-07-20 07:13:20', 'request', '', null, null, '39', null, null, null), ('519', '3', 'assign', '2018-07-20 07:14:48', 'request', '', '4', null, '39', null, null, null), ('520', '4', 'Make candidate to request', '2018-07-23 02:41:42', 'candidate', null, null, null, '39', '34', null, '27'), ('521', '4', 'change status', '2018-07-23 02:41:50', 'candidate', 'Apply to Offer', null, null, null, '34', null, null), ('522', '4', 'change status', '2018-07-23 02:42:22', 'candidate', 'Offer to Onboard', null, null, null, '34', null, null), ('523', '3', 'create', '2018-07-23 02:43:52', 'request', '', null, null, '61', null, null, null), ('524', '3', 'submit', '2018-07-23 02:44:31', 'request', '', null, null, '61', null, null, null), ('525', '17', 'update', '2018-07-23 02:47:31', 'request', '', null, null, '61', null, null, null), ('526', '17', 'approved', '2018-07-23 02:47:46', 'request', '', null, null, '61', null, null, null), ('527', '3', 'assign', '2018-07-23 02:48:49', 'request', '', '6', null, '61', null, null, null), ('528', '3', 'assign', '2018-07-23 02:49:38', 'request', '', '6', null, '61', null, null, null), ('529', '3', 'assign', '2018-07-23 02:49:38', 'request', '', '3', null, '61', null, null, null), ('530', '8', 'create', '2018-07-23 03:07:33', 'request', '', null, null, '62', null, null, null), ('531', '8', 'approved', '2018-07-23 03:07:37', 'request', '', null, null, '62', null, null, null), ('532', '3', 'Make candidate to request', '2018-07-23 03:10:28', 'candidate', null, null, null, '61', '35', null, '28'), ('533', '3', 'change status', '2018-07-23 03:10:51', 'candidate', 'Apply to Offer', null, null, null, '35', null, null), ('534', '6', 'Make candidate to request', '2018-07-23 03:22:26', 'candidate', null, null, null, '61', '36', null, '29'), ('535', '6', 'change status', '2018-07-23 03:22:57', 'candidate', 'Apply to Interview', null, null, null, '36', null, null), ('536', '3', 'create', '2018-07-23 03:23:02', 'request', '', null, null, '63', null, null, null), ('537', '3', 'submit', '2018-07-23 03:23:10', 'request', '', null, null, '63', null, null, null), ('538', '6', 'change status', '2018-07-23 03:23:23', 'candidate', 'Interview to Offer', null, null, null, '36', null, null), ('539', '6', 'change status', '2018-07-23 03:23:48', 'candidate', 'Onboard to Interview', null, null, null, '18', null, null), ('540', '7', 'Make candidate to request', '2018-07-23 04:39:48', 'candidate', null, null, null, '33', '37', null, '32'), ('541', '7', 'change status', '2018-07-23 04:43:44', 'candidate', 'Apply to Offer', null, null, null, '37', null, null), ('542', '7', 'change status', '2018-07-23 04:43:53', 'candidate', 'Offer to Closed', null, null, null, '37', null, null), ('543', '7', 'Make candidate to request', '2018-07-23 04:47:53', 'candidate', null, null, null, '33', '38', null, '33'), ('544', '7', 'change status', '2018-07-23 04:48:00', 'candidate', 'Apply to Interview', null, null, null, '38', null, null), ('545', '7', 'change status', '2018-07-23 04:48:15', 'candidate', 'Interview to Apply', null, null, null, '38', null, null), ('546', '7', 'Make candidate to request', '2018-07-23 06:00:45', 'candidate', null, null, null, '35', '39', null, '37'), ('547', '7', 'change status', '2018-07-23 06:01:00', 'candidate', 'Apply to Onboard', null, null, null, '39', null, null), ('548', '7', 'Make candidate to request', '2018-07-23 06:32:46', 'candidate', null, null, null, '25', '40', null, '38'), ('549', '7', 'change status', '2018-07-23 06:33:03', 'candidate', 'Apply to Onboard', null, null, null, '40', null, null), ('550', '3', 'assign', '2018-07-23 06:34:57', 'request', '', '7', null, '20', null, null, null), ('551', '3', 'assign', '2018-07-23 06:35:05', 'request', '', '25', null, '20', null, null, null), ('552', '3', 'assign', '2018-07-23 06:35:16', 'request', '', '25', null, '20', null, null, null), ('553', '3', 'assign', '2018-07-23 06:35:16', 'request', '', '7', null, '20', null, null, null), ('554', '3', 'assign', '2018-07-23 06:35:33', 'request', '', '25', null, '20', null, null, null), ('555', '3', 'assign', '2018-07-23 06:35:33', 'request', '', '7', null, '20', null, null, null), ('556', '3', 'assign', '2018-07-23 06:35:33', 'request', '', '4', null, '20', null, null, null), ('557', '7', 'Make candidate to request', '2018-07-23 06:49:05', 'candidate', null, null, null, '20', '41', null, '36'), ('558', '7', 'Make candidate to request', '2018-07-23 06:49:05', 'candidate', null, null, null, '20', '42', null, '35'), ('559', '7', 'Make candidate to request', '2018-07-23 06:49:05', 'candidate', null, null, null, '20', '43', null, '34'), ('560', '7', 'change status', '2018-07-23 06:49:12', 'candidate', 'Apply to Offer', null, null, null, '43', null, null), ('561', '7', 'change status', '2018-07-23 06:49:26', 'candidate', 'Apply to Offer', null, null, null, '41', null, null), ('562', '5', 'change status', '2018-07-23 06:50:32', 'candidate', 'Apply to Closed', null, null, null, '24', null, null), ('563', '7', 'change status', '2018-07-23 06:55:11', 'candidate', 'Onboard to Offer', null, null, null, '39', null, null), ('564', '7', 'change status', '2018-07-23 06:55:24', 'candidate', 'Offer to Onboard', null, null, null, '39', null, null), ('565', '7', 'change status', '2018-07-23 06:57:20', 'candidate', 'Closed to Apply', null, null, null, '37', null, null), ('566', '7', 'change status', '2018-07-23 07:03:17', 'candidate', 'Apply to Closed', null, null, null, '37', null, null), ('567', '7', 'change status', '2018-07-23 07:39:02', 'candidate', 'Closed to Interview', null, null, null, '37', null, null), ('568', '8', 'approved', '2018-07-23 07:52:04', 'request', '', null, null, '63', null, null, null), ('569', '3', 'assign', '2018-07-23 07:52:47', 'request', '', '6', null, '63', null, null, null), ('570', '7', 'Make candidate to request', '2018-07-23 07:56:07', 'candidate', null, null, null, '25', '44', null, '40');
INSERT INTO log VALUES ('571', '7', 'change status', '2018-07-23 07:56:16', 'candidate', 'Apply to Interview', null, null, null, '44', null, null), ('572', '8', 'create', '2018-07-23 08:00:13', 'request', '', null, null, '64', null, null, null), ('573', '8', 'approved', '2018-07-23 08:00:19', 'request', '', null, null, '64', null, null, null), ('574', '8', 'update', '2018-07-23 08:03:23', 'request', '', null, null, '33', null, null, null), ('575', '7', 'Make candidate to request', '2018-07-23 08:24:49', 'candidate', null, null, null, '24', '45', null, '41'), ('576', '7', 'Make candidate to request', '2018-07-23 09:51:02', 'candidate', null, null, null, '25', '46', null, '44'), ('577', '7', 'change status', '2018-07-23 09:51:10', 'candidate', 'Apply to Interview', null, null, null, '46', null, null), ('578', '6', 'Make candidate to request', '2018-07-23 09:52:18', 'candidate', null, null, null, '34', '47', null, '45'), ('579', '6', 'Make candidate to request', '2018-07-23 09:52:18', 'candidate', null, null, null, '34', '48', null, '42'), ('580', '6', 'Make candidate to request', '2018-07-23 09:52:18', 'candidate', null, null, null, '34', '49', null, '43'), ('581', '6', 'change status', '2018-07-23 09:52:59', 'candidate', 'Apply to Contacting', null, null, null, '49', null, null), ('582', '6', 'change status', '2018-07-23 09:53:09', 'candidate', 'Apply to Contacting', null, null, null, '48', null, null), ('583', '6', 'change status', '2018-07-23 09:53:47', 'candidate', 'Apply to Contacting', null, null, null, '47', null, null), ('584', '6', 'Make candidate to request', '2018-07-23 09:56:48', 'candidate', null, null, null, '34', '50', null, '46'), ('585', '6', 'change status', '2018-07-23 09:57:33', 'candidate', 'Apply to Contacting', null, null, null, '50', null, null), ('586', '3', 'assign', '2018-07-23 10:00:03', 'request', '', '6', null, '26', null, null, null), ('587', '3', 'assign', '2018-07-23 10:00:03', 'request', '', '5', null, '26', null, null, null), ('588', '3', 'assign', '2018-07-23 10:00:03', 'request', '', '4', null, '26', null, null, null), ('589', '3', 'assign', '2018-07-23 10:00:03', 'request', '', '7', null, '26', null, null, null), ('590', '6', 'Make candidate to request', '2018-07-23 10:00:42', 'candidate', null, null, null, '34', '51', null, '47'), ('591', '6', 'change status', '2018-07-23 10:02:17', 'candidate', 'Offer to Interview', null, null, null, '36', null, null), ('592', '6', 'change status', '2018-07-23 10:02:22', 'candidate', 'Interview to Offer', null, null, null, '36', null, null), ('593', '6', 'change status', '2018-07-23 10:03:10', 'candidate', 'Offer to Interview', null, null, null, '36', null, null), ('594', '6', 'change status', '2018-07-23 10:03:15', 'candidate', 'Interview to Offer', null, null, null, '36', null, null), ('595', '6', 'Make candidate to request', '2018-07-23 10:04:21', 'candidate', null, null, null, '63', '52', null, '30'), ('596', '6', 'change status', '2018-07-23 10:04:53', 'candidate', 'Apply to Onboard', null, null, null, '52', null, null), ('597', '6', 'change status', '2018-07-23 10:06:54', 'candidate', 'Apply to Contacting', null, null, null, '51', null, null), ('598', '6', 'Make candidate to request', '2018-07-23 10:12:38', 'candidate', null, null, null, '34', '53', null, '48'), ('599', '6', 'change status', '2018-07-23 10:12:57', 'candidate', 'Apply to Contacting', null, null, null, '53', null, null), ('600', '8', 'update', '2018-07-24 01:48:33', 'request', '', null, null, '34', null, null, null), ('601', '6', 'Make candidate to request', '2018-07-24 02:39:06', 'candidate', null, null, null, '34', '54', null, '49'), ('602', '6', 'change status', '2018-07-24 02:39:52', 'candidate', 'Apply to Contacting', null, null, null, '54', null, null), ('603', '8', 'create', '2018-07-24 04:24:42', 'request', '', null, null, '65', null, null, null), ('604', '8', 'approved', '2018-07-24 05:01:02', 'request', '', null, null, '65', null, null, null), ('605', '5', 'Make candidate to request', '2018-07-24 06:49:37', 'candidate', null, null, null, '21', '55', null, '50'), ('606', '5', 'change status', '2018-07-24 06:49:58', 'candidate', 'Apply to Interview', null, null, null, '55', null, null), ('607', '3', 'update', '2018-07-24 08:10:37', 'request', '', null, null, '65', null, null, null), ('608', '3', 'assign', '2018-07-24 08:10:57', 'request', '', '5', null, '65', null, null, null), ('609', '3', 'assign', '2018-07-24 08:12:00', 'request', '', '5', null, '64', null, null, null), ('610', '5', 'change status', '2018-07-24 08:14:27', 'candidate', 'Offer to Closed', null, null, null, '26', null, null), ('611', '5', 'Make candidate to request', '2018-07-24 08:15:41', 'candidate', null, null, null, '64', '56', null, '21'), ('612', '5', 'change status', '2018-07-24 08:16:01', 'candidate', 'Apply to Onboard', null, null, null, '56', null, null), ('613', '6', 'Make candidate to request', '2018-07-24 08:52:10', 'candidate', null, null, null, '34', '57', null, '51'), ('614', '6', 'change status', '2018-07-24 09:28:12', 'candidate', 'Apply to Contacting', null, null, null, '57', null, null), ('615', '7', 'Make candidate to request', '2018-07-24 10:48:14', 'candidate', null, null, null, '25', '58', null, '52'), ('616', '3', 'create', '2018-07-24 23:00:15', 'request', '', null, null, '66', null, null, null), ('617', '3', 'submit', '2018-07-24 23:00:23', 'request', '', null, null, '66', null, null, null), ('618', '8', 'approved', '2018-07-25 11:45:17', 'request', '', null, null, '66', null, null, null), ('619', '8', 'create', '2018-07-25 11:47:51', 'request', '', null, null, '67', null, null, null), ('620', '8', 'approved', '2018-07-25 11:47:55', 'request', '', null, null, '67', null, null, null), ('621', '8', 'create', '2018-07-25 11:50:49', 'request', '', null, null, '68', null, null, null), ('622', '8', 'approved', '2018-07-25 11:50:52', 'request', '', null, null, '68', null, null, null), ('623', '9', 'create', '2018-07-25 14:10:38', 'request', '', null, null, '69', null, null, null), ('624', '9', 'submit', '2018-07-25 14:10:45', 'request', '', null, null, '69', null, null, null), ('625', '9', 'create', '2018-07-25 14:11:42', 'request', '', null, null, '70', null, null, null), ('626', '9', 'create', '2018-07-25 14:15:12', 'request', '', null, null, '71', null, null, null), ('627', '10', 'submit', '2018-07-25 14:19:12', 'request', '', null, null, '71', null, null, null), ('628', '8', 'approved', '2018-07-25 14:19:29', 'request', '', null, null, '69', null, null, null), ('629', '8', 'update', '2018-07-25 15:11:46', 'request', '', null, null, '71', null, null, null), ('630', '8', 'update', '2018-07-25 15:15:11', 'request', '', null, null, '71', null, null, null), ('631', '8', 'update', '2018-07-25 15:18:37', 'request', '', null, null, '71', null, null, null), ('632', '8', 'update', '2018-07-25 15:22:28', 'request', '', null, null, '71', null, null, null), ('633', '8', 'update', '2018-07-25 16:01:24', 'request', '', null, null, '71', null, null, null), ('634', '8', 'update', '2018-07-25 16:03:43', 'request', '', null, null, '71', null, null, null), ('635', '8', 'update', '2018-07-25 16:07:07', 'request', '', null, null, '71', null, null, null);
COMMIT;

-- ----------------------------
-- Table structure for `notification`
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`candidate_id`  bigint(20) NULL DEFAULT NULL ,
`user_id`  bigint(20) NOT NULL ,
`content`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`create_date`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`request_id`  bigint(20) NULL DEFAULT NULL ,
`interview_id`  bigint(20) NULL DEFAULT NULL ,
`notification_type`  bigint(20) NULL DEFAULT NULL ,
`comment_id`  bigint(20) NULL DEFAULT NULL ,
`status`  tinyint(1) NULL DEFAULT NULL ,
`receiver_id`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`interview_id`) REFERENCES `interview` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`interview_id`) REFERENCES `interview` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=443

;

-- ----------------------------
-- Records of notification
-- ----------------------------
BEGIN;
INSERT INTO notification VALUES ('133', null, '3', 'assigned', '2018-07-11 15:16:01', '20', null, '4', null, null, '7'), ('134', null, '3', 'assigned', '2018-07-12 04:31:05', '20', null, '4', null, null, '25'), ('135', null, '3', 'assigned', '2018-07-12 04:33:37', '22', null, '4', null, null, '25'), ('136', null, '3', 'assigned', '2018-07-12 04:33:44', '22', null, '4', null, null, '24'), ('137', null, '3', 'assigned', '2018-07-12 04:34:59', '21', null, '4', null, null, '7'), ('138', null, '3', 'assigned', '2018-07-12 04:35:08', '21', null, '4', null, null, '5'), ('139', null, '3', 'assigned', '2018-07-12 04:35:30', '23', null, '4', null, null, '25'), ('140', null, '3', 'assigned', '2018-07-12 04:35:37', '23', null, '4', null, null, '24'), ('141', null, '3', 'assigned', '2018-07-12 04:36:18', '25', null, '4', null, null, '4'), ('142', null, '3', 'assigned', '2018-07-12 04:36:27', '25', null, '4', null, null, '7'), ('143', null, '3', 'assigned', '2018-07-12 04:36:54', '24', null, '4', null, null, '7'), ('144', null, '3', 'assigned', '2018-07-12 04:37:01', '24', null, '4', null, null, '4'), ('145', null, '3', 'assigned', '2018-07-12 04:37:23', '26', null, '4', null, null, '6'), ('146', null, '3', 'assigned', '2018-07-12 04:37:33', '26', null, '4', null, null, '5'), ('147', null, '3', 'assigned', '2018-07-12 04:37:43', '26', null, '4', null, null, '4'), ('148', '17', '7', 'changed status from Apply to Interview', '2018-07-12 04:55:26', '24', null, '1', null, '1', '3'), ('149', '17', '7', 'changed status from Apply to Interview', '2018-07-12 04:55:26', '24', null, '1', null, '1', '15'), ('150', '17', '7', 'changed status from Apply to Interview', '2018-07-12 04:55:26', '24', null, '1', null, '1', '22'), ('151', '17', '7', 'changed status from Interview to Contacting', '2018-07-12 04:55:51', '24', null, '1', null, '1', '3'), ('152', '17', '7', 'changed status from Interview to Contacting', '2018-07-12 04:55:51', '24', null, '1', null, '1', '15'), ('153', '17', '7', 'changed status from Interview to Contacting', '2018-07-12 04:55:51', '24', null, '1', null, '1', '22'), ('154', '17', '7', 'changed status from Contacting to Interview', '2018-07-12 04:56:14', '24', null, '1', null, '1', '3'), ('155', '17', '7', 'changed status from Contacting to Interview', '2018-07-12 04:56:14', '24', null, '1', null, '1', '15'), ('156', '17', '7', 'changed status from Contacting to Interview', '2018-07-12 04:56:14', '24', null, '1', null, '1', '22'), ('157', '17', '7', 'changed status from Interview to Contacting', '2018-07-12 04:56:42', '24', null, '1', null, '1', '3'), ('158', '17', '7', 'changed status from Interview to Contacting', '2018-07-12 04:56:42', '24', null, '1', null, '1', '15'), ('159', '17', '7', 'changed status from Interview to Contacting', '2018-07-12 04:56:42', '24', null, '1', null, '1', '22'), ('160', '17', '7', 'changed status from Contacting to Interview', '2018-07-12 04:56:58', '24', null, '1', null, '1', '3'), ('161', '17', '7', 'changed status from Contacting to Interview', '2018-07-12 04:56:58', '24', null, '1', null, '1', '15'), ('162', '17', '7', 'changed status from Contacting to Interview', '2018-07-12 04:56:58', '24', null, '1', null, '1', '22'), ('163', null, '3', 'assigned', '2018-07-12 06:37:34', '30', null, '4', null, null, '6'), ('164', null, '3', 'assigned', '2018-07-12 06:37:56', '29', null, '4', null, null, '7'), ('165', null, '3', 'assigned', '2018-07-12 06:38:08', '28', null, '4', null, null, '7'), ('166', null, '3', 'assigned', '2018-07-12 06:38:22', '27', null, '4', null, null, '4'), ('167', null, '3', 'assigned', '2018-07-12 08:30:52', '32', null, '4', null, null, '7'), ('168', null, '3', 'assigned', '2018-07-13 08:16:23', '34', null, '4', null, null, '6'), ('169', '17', '8', 'commented', '2018-07-13 08:17:22', '24', null, '2', null, '1', '3'), ('170', '17', '8', 'commented', '2018-07-13 08:17:22', '24', null, '2', null, '1', '15'), ('171', '17', '8', 'commented', '2018-07-13 08:17:22', '24', null, '2', null, '1', '22'), ('172', '17', '8', 'commented', '2018-07-13 08:18:09', '24', null, '2', null, '1', '3'), ('173', '17', '8', 'commented', '2018-07-13 08:18:09', '24', null, '2', null, '1', '15'), ('174', '17', '8', 'commented', '2018-07-13 08:18:09', '24', null, '2', null, '1', '22'), ('175', null, '3', 'assigned', '2018-07-13 11:20:31', '35', null, '4', null, null, '5'), ('176', null, '3', 'assigned', '2018-07-13 14:24:22', '33', null, '4', null, null, '7'), ('177', '18', '6', 'changed status from Apply to Interview', '2018-07-16 09:06:30', '34', null, '1', null, '1', '3'), ('178', '18', '6', 'changed status from Apply to Interview', '2018-07-16 09:06:30', '34', null, '1', null, '1', '15'), ('179', '18', '6', 'changed status from Apply to Interview', '2018-07-16 09:06:30', '34', null, '1', null, '1', '22'), ('180', '18', '6', 'changed status from Interview to Contacting', '2018-07-16 09:06:43', '34', null, '1', null, '1', '3'), ('181', '18', '6', 'changed status from Interview to Contacting', '2018-07-16 09:06:43', '34', null, '1', null, '1', '15'), ('182', '18', '6', 'changed status from Interview to Contacting', '2018-07-16 09:06:43', '34', null, '1', null, '1', '22'), ('183', '19', '4', 'changed status from Apply to Interview', '2018-07-16 09:13:28', '24', null, '1', null, '1', '3'), ('184', '19', '4', 'changed status from Apply to Interview', '2018-07-16 09:13:28', '24', null, '1', null, '1', '15'), ('185', '19', '4', 'changed status from Apply to Interview', '2018-07-16 09:13:28', '24', null, '1', null, '1', '22'), ('186', '19', '4', 'changed status from Interview to Offer', '2018-07-16 09:13:48', '24', null, '1', null, '1', '3'), ('187', '19', '4', 'changed status from Interview to Offer', '2018-07-16 09:13:48', '24', null, '1', null, '1', '15'), ('188', '19', '4', 'changed status from Interview to Offer', '2018-07-16 09:13:48', '24', null, '1', null, '1', '22'), ('189', '19', '4', 'changed status from Offer to Interview', '2018-07-16 09:13:58', '24', null, '1', null, '1', '3'), ('190', '19', '4', 'changed status from Offer to Interview', '2018-07-16 09:13:58', '24', null, '1', null, '1', '15'), ('191', '19', '4', 'changed status from Offer to Interview', '2018-07-16 09:13:58', '24', null, '1', null, '1', '22'), ('192', '20', '4', 'changed status from Apply to Closed', '2018-07-16 09:17:12', '27', null, '1', null, '1', '3'), ('193', '20', '4', 'changed status from Apply to Closed', '2018-07-16 09:17:12', '27', null, '1', null, '1', '15'), ('194', '20', '4', 'changed status from Apply to Closed', '2018-07-16 09:17:12', '27', null, '1', null, '1', '22'), ('195', '21', '4', 'changed status from Apply to Offer', '2018-07-16 09:17:59', '27', null, '1', null, '1', '3'), ('196', '21', '4', 'changed status from Apply to Offer', '2018-07-16 09:17:59', '27', null, '1', null, '1', '15'), ('197', '21', '4', 'changed status from Apply to Offer', '2018-07-16 09:17:59', '27', null, '1', null, '1', '22'), ('198', '22', '4', 'changed status from Apply to Onboard', '2018-07-17 02:18:57', '27', null, '1', null, '1', '3'), ('199', '22', '4', 'changed status from Apply to Onboard', '2018-07-17 02:18:57', '27', null, '1', null, '1', '15'), ('200', '22', '4', 'changed status from Apply to Onboard', '2018-07-17 02:18:57', '27', null, '1', null, '1', '22'), ('201', '22', '4', 'changed status from Onboard to Apply', '2018-07-17 02:19:29', '27', null, '1', null, '1', '3'), ('202', '22', '4', 'changed status from Onboard to Apply', '2018-07-17 02:19:29', '27', null, '1', null, '1', '15'), ('203', '22', '4', 'changed status from Onboard to Apply', '2018-07-17 02:19:30', '27', null, '1', null, '1', '22'), ('204', '21', '4', 'changed status from Offer to Closed', '2018-07-17 02:20:16', '27', null, '1', null, '1', '3'), ('205', '21', '4', 'changed status from Offer to Closed', '2018-07-17 02:20:16', '27', null, '1', null, '1', '15'), ('206', '21', '4', 'changed status from Offer to Closed', '2018-07-17 02:20:16', '27', null, '1', null, '1', '22'), ('207', '23', '4', 'changed status from Apply to Interview', '2018-07-17 02:23:04', '24', null, '1', null, '1', '3'), ('208', '23', '4', 'changed status from Apply to Interview', '2018-07-17 02:23:04', '24', null, '1', null, '1', '15'), ('209', '23', '4', 'changed status from Apply to Interview', '2018-07-17 02:23:04', '24', null, '1', null, '1', '22'), ('210', '23', '4', 'commented', '2018-07-17 02:25:17', '24', null, '2', null, '1', '3'), ('211', '23', '4', 'commented', '2018-07-17 02:25:17', '24', null, '2', null, '1', '15'), ('212', '23', '4', 'commented', '2018-07-17 02:25:17', '24', null, '2', null, '1', '22'), ('215', '18', '6', 'changed status from Interview to Onboard', '2018-07-17 02:48:19', '34', null, '1', null, '1', '3'), ('216', '18', '6', 'changed status from Interview to Onboard', '2018-07-17 02:48:19', '34', null, '1', null, '1', '15'), ('217', '18', '6', 'changed status from Interview to Onboard', '2018-07-17 02:48:19', '34', null, '1', null, '1', '22'), ('218', '23', '4', 'changed status from Interview to Onboard', '2018-07-17 02:49:50', '24', null, '1', null, '1', '3'), ('219', '23', '4', 'changed status from Interview to Onboard', '2018-07-17 02:49:50', '24', null, '1', null, '1', '15'), ('220', '23', '4', 'changed status from Interview to Onboard', '2018-07-17 02:49:50', '24', null, '1', null, '1', '22'), ('221', '19', '4', 'changed status from Interview to Onboard', '2018-07-17 02:50:03', '24', null, '1', null, '1', '3'), ('222', '19', '4', 'changed status from Interview to Onboard', '2018-07-17 02:50:03', '24', null, '1', null, '1', '15'), ('223', '19', '4', 'changed status from Interview to Onboard', '2018-07-17 02:50:03', '24', null, '1', null, '1', '22'), ('224', '19', '4', 'changed status from Onboard to Closed', '2018-07-17 02:50:09', '24', null, '1', null, '1', '3'), ('225', '19', '4', 'changed status from Onboard to Closed', '2018-07-17 02:50:09', '24', null, '1', null, '1', '15'), ('226', '19', '4', 'changed status from Onboard to Closed', '2018-07-17 02:50:09', '24', null, '1', null, '1', '22'), ('227', '19', '4', 'changed status from Closed to Interview', '2018-07-17 02:50:47', '24', null, '1', null, '1', '3'), ('228', '19', '4', 'changed status from Closed to Interview', '2018-07-17 02:50:47', '24', null, '1', null, '1', '15'), ('229', '19', '4', 'changed status from Closed to Interview', '2018-07-17 02:50:47', '24', null, '1', null, '1', '22'), ('230', '23', '4', 'changed status from Onboard to Interview', '2018-07-17 02:50:57', '24', null, '1', null, '1', '3'), ('231', '23', '4', 'changed status from Onboard to Interview', '2018-07-17 02:50:57', '24', null, '1', null, '1', '15'), ('232', '23', '4', 'changed status from Onboard to Interview', '2018-07-17 02:50:57', '24', null, '1', null, '1', '22'), ('233', '23', '4', 'changed status from Interview to Onboard', '2018-07-17 02:51:12', '24', null, '1', null, '1', '3'), ('234', '23', '4', 'changed status from Interview to Onboard', '2018-07-17 02:51:12', '24', null, '1', null, '1', '15'), ('235', '23', '4', 'changed status from Interview to Onboard', '2018-07-17 02:51:12', '24', null, '1', null, '1', '22');
INSERT INTO notification VALUES ('236', '23', '4', 'changed status from Onboard to Closed', '2018-07-17 02:51:27', '24', null, '1', null, '1', '3'), ('237', '23', '4', 'changed status from Onboard to Closed', '2018-07-17 02:51:27', '24', null, '1', null, '1', '15'), ('238', '23', '4', 'changed status from Onboard to Closed', '2018-07-17 02:51:27', '24', null, '1', null, '1', '22'), ('239', '19', '4', 'changed status from Interview to Onboard', '2018-07-17 03:07:49', '24', null, '1', null, '1', '3'), ('240', '19', '4', 'changed status from Interview to Onboard', '2018-07-17 03:07:49', '24', null, '1', null, '1', '15'), ('241', '19', '4', 'changed status from Interview to Onboard', '2018-07-17 03:07:49', '24', null, '1', null, '1', '22'), ('242', '19', '4', 'changed status from Onboard to Closed', '2018-07-17 03:07:54', '24', null, '1', null, '1', '3'), ('243', '19', '4', 'changed status from Onboard to Closed', '2018-07-17 03:07:54', '24', null, '1', null, '1', '15'), ('244', '19', '4', 'changed status from Onboard to Closed', '2018-07-17 03:07:54', '24', null, '1', null, '1', '22'), ('245', '23', '4', 'changed status from Closed to Contacting', '2018-07-17 03:09:57', '24', null, '1', null, '1', '3'), ('246', '23', '4', 'changed status from Closed to Contacting', '2018-07-17 03:09:57', '24', null, '1', null, '1', '15'), ('247', '23', '4', 'changed status from Closed to Contacting', '2018-07-17 03:09:57', '24', null, '1', null, '1', '22'), ('248', '19', '4', 'changed status from Closed to Contacting', '2018-07-17 03:10:04', '24', null, '1', null, '1', '3'), ('249', '19', '4', 'changed status from Closed to Contacting', '2018-07-17 03:10:04', '24', null, '1', null, '1', '15'), ('250', '19', '4', 'changed status from Closed to Contacting', '2018-07-17 03:10:04', '24', null, '1', null, '1', '22'), ('251', '23', '4', 'changed status from Contacting to Interview', '2018-07-17 03:10:09', '24', null, '1', null, '1', '3'), ('252', '23', '4', 'changed status from Contacting to Interview', '2018-07-17 03:10:09', '24', null, '1', null, '1', '15'), ('253', '23', '4', 'changed status from Contacting to Interview', '2018-07-17 03:10:09', '24', null, '1', null, '1', '22'), ('254', '19', '4', 'changed status from Contacting to Interview', '2018-07-17 03:10:13', '24', null, '1', null, '1', '3'), ('255', '19', '4', 'changed status from Contacting to Interview', '2018-07-17 03:10:13', '24', null, '1', null, '1', '15'), ('256', '19', '4', 'changed status from Contacting to Interview', '2018-07-17 03:10:13', '24', null, '1', null, '1', '22'), ('258', '19', '8', 'commented', '2018-07-17 10:32:23', '24', null, '2', null, '1', '3'), ('259', '19', '8', 'commented', '2018-07-17 10:32:23', '24', null, '2', null, '1', '15'), ('260', '19', '8', 'commented', '2018-07-17 10:32:23', '24', null, '2', null, '1', '22'), ('261', '19', '8', 'commented', '2018-07-17 10:33:09', '24', null, '2', null, '1', '3'), ('262', '19', '8', 'commented', '2018-07-17 10:33:09', '24', null, '2', null, '1', '15'), ('263', '19', '8', 'commented', '2018-07-17 10:33:09', '24', null, '2', null, '1', '22'), ('264', '24', '22', 'commented', '2018-07-17 10:36:41', '35', null, '2', null, '1', '3'), ('265', '24', '22', 'commented', '2018-07-17 10:36:41', '35', null, '2', null, '1', '15'), ('266', '24', '22', 'commented', '2018-07-17 10:36:41', '35', null, '2', null, '1', '22'), ('267', '22', '4', 'changed status from Apply to Onboard', '2018-07-18 02:27:46', '27', null, '1', null, '1', '3'), ('268', '22', '4', 'changed status from Apply to Onboard', '2018-07-18 02:27:46', '27', null, '1', null, '1', '15'), ('269', '22', '4', 'changed status from Apply to Onboard', '2018-07-18 02:27:46', '27', null, '1', null, '1', '22'), ('270', '25', '4', 'changed status from Apply to Interview', '2018-07-18 02:41:13', '26', null, '1', null, '1', '3'), ('271', '25', '4', 'changed status from Apply to Interview', '2018-07-18 02:41:13', '26', null, '1', null, '1', '15'), ('272', '25', '4', 'changed status from Apply to Interview', '2018-07-18 02:41:13', '26', null, '1', null, '1', '22'), ('273', null, '3', 'assigned', '2018-07-18 02:42:18', '25', null, '4', null, null, '5'), ('274', null, '3', 'assigned', '2018-07-18 02:52:14', '25', null, '4', null, null, '4'), ('275', '22', '4', 'changed status from Onboard to Closed', '2018-07-18 02:58:37', '27', null, '1', null, '1', '3'), ('276', '22', '4', 'changed status from Onboard to Closed', '2018-07-18 02:58:37', '27', null, '1', null, '1', '15'), ('277', '22', '4', 'changed status from Onboard to Closed', '2018-07-18 02:58:37', '27', null, '1', null, '1', '22'), ('278', '21', '4', 'changed status from Closed to Onboard', '2018-07-18 02:58:47', '27', null, '1', null, '1', '3'), ('279', '21', '4', 'changed status from Closed to Onboard', '2018-07-18 02:58:47', '27', null, '1', null, '1', '15'), ('280', '21', '4', 'changed status from Closed to Onboard', '2018-07-18 02:58:47', '27', null, '1', null, '1', '22'), ('281', null, '3', 'assigned', '2018-07-19 10:32:23', '37', null, '4', null, null, '4'), ('282', null, '3', 'assigned', '2018-07-19 11:43:00', '60', null, '4', null, null, '4'), ('283', null, '3', 'assigned', '2018-07-19 11:43:14', '59', null, '4', null, null, '6'), ('284', null, '3', 'assigned', '2018-07-19 11:43:27', '59', null, '4', null, null, '6'), ('285', null, '3', 'assigned', '2018-07-19 11:43:39', '59', null, '4', null, null, '24'), ('286', null, '3', 'assigned', '2018-07-19 11:43:54', '59', null, '4', null, null, '6'), ('287', null, '3', 'assigned', '2018-07-19 11:44:23', '58', null, '4', null, null, '6'), ('288', null, '3', 'assigned', '2018-07-19 11:44:59', '58', null, '4', null, null, '3'), ('289', null, '3', 'assigned', '2018-07-19 11:45:18', '57', null, '4', null, null, '6'), ('290', null, '3', 'assigned', '2018-07-19 11:45:30', '57', null, '4', null, null, '3'), ('291', null, '3', 'assigned', '2018-07-19 11:45:52', '56', null, '4', null, null, '4'), ('292', null, '3', 'assigned', '2018-07-19 11:46:06', '38', null, '4', null, null, '4'), ('293', '26', '5', 'changed status from Apply to Offer', '2018-07-20 02:49:29', '25', null, '1', null, '1', '3'), ('294', '26', '5', 'changed status from Apply to Offer', '2018-07-20 02:49:29', '25', null, '1', null, '1', '15'), ('295', '26', '5', 'changed status from Apply to Offer', '2018-07-20 02:49:29', '25', null, '1', null, '1', '22'), ('296', '27', '4', 'changed status from Apply to Onboard', '2018-07-20 02:51:50', '37', null, '1', null, '1', '3'), ('297', '27', '4', 'changed status from Apply to Onboard', '2018-07-20 02:51:50', '37', null, '1', null, '1', '15'), ('298', '27', '4', 'changed status from Apply to Onboard', '2018-07-20 02:51:50', '37', null, '1', null, '1', '22'), ('299', '28', '4', 'changed status from Apply to Offer', '2018-07-20 02:58:14', '38', null, '1', null, '1', '3'), ('300', '28', '4', 'changed status from Apply to Offer', '2018-07-20 02:58:14', '38', null, '1', null, '1', '15'), ('301', '28', '4', 'changed status from Apply to Offer', '2018-07-20 02:58:14', '38', null, '1', null, '1', '22'), ('302', '29', '4', 'changed status from Apply to Offer', '2018-07-20 03:01:56', '60', null, '1', null, '1', '3'), ('303', '29', '4', 'changed status from Apply to Offer', '2018-07-20 03:01:56', '60', null, '1', null, '1', '15'), ('304', '29', '4', 'changed status from Apply to Offer', '2018-07-20 03:01:56', '60', null, '1', null, '1', '22'), ('305', '30', '7', 'changed status from Apply to Onboard', '2018-07-20 03:41:56', '28', null, '1', null, '1', '3'), ('306', '30', '7', 'changed status from Apply to Onboard', '2018-07-20 03:41:56', '28', null, '1', null, '1', '15'), ('307', '30', '7', 'changed status from Apply to Onboard', '2018-07-20 03:41:56', '28', null, '1', null, '1', '22'), ('308', '32', '5', 'changed status from Apply to Interview', '2018-07-20 03:53:16', '21', null, '1', null, '1', '3'), ('309', '32', '5', 'changed status from Apply to Interview', '2018-07-20 03:53:16', '21', null, '1', null, '1', '15'), ('310', '32', '5', 'changed status from Apply to Interview', '2018-07-20 03:53:16', '21', null, '1', null, '1', '22'), ('311', '31', '5', 'changed status from Apply to Interview', '2018-07-20 03:53:26', '21', null, '1', null, '1', '3'), ('312', '31', '5', 'changed status from Apply to Interview', '2018-07-20 03:53:26', '21', null, '1', null, '1', '15'), ('313', '31', '5', 'changed status from Apply to Interview', '2018-07-20 03:53:26', '21', null, '1', null, '1', '22'), ('314', '33', '7', 'changed status from Apply to Onboard', '2018-07-20 07:10:53', '28', null, '1', null, '1', '3'), ('315', '33', '7', 'changed status from Apply to Onboard', '2018-07-20 07:10:53', '28', null, '1', null, '1', '15'), ('316', '33', '7', 'changed status from Apply to Onboard', '2018-07-20 07:10:53', '28', null, '1', null, '1', '22'), ('317', null, '3', 'assigned', '2018-07-20 07:11:21', '35', null, '4', null, null, '7'), ('318', null, '3', 'assigned', '2018-07-20 07:14:48', '39', null, '4', null, null, '4'), ('319', '34', '4', 'changed status from Apply to Offer', '2018-07-23 02:41:50', '39', null, '1', null, '1', '3'), ('320', '34', '4', 'changed status from Apply to Offer', '2018-07-23 02:41:50', '39', null, '1', null, '1', '15'), ('321', '34', '4', 'changed status from Apply to Offer', '2018-07-23 02:41:50', '39', null, '1', null, '1', '22'), ('322', '34', '4', 'changed status from Offer to Onboard', '2018-07-23 02:42:22', '39', null, '1', null, '1', '3'), ('323', '34', '4', 'changed status from Offer to Onboard', '2018-07-23 02:42:22', '39', null, '1', null, '1', '15'), ('324', '34', '4', 'changed status from Offer to Onboard', '2018-07-23 02:42:22', '39', null, '1', null, '1', '22'), ('325', null, '3', 'assigned', '2018-07-23 02:48:49', '61', null, '4', null, null, '6'), ('326', null, '3', 'assigned', '2018-07-23 02:49:38', '61', null, '4', null, null, '3'), ('327', '35', '3', 'changed status from Apply to Offer', '2018-07-23 03:10:51', '61', null, '1', null, '1', '3'), ('328', '35', '3', 'changed status from Apply to Offer', '2018-07-23 03:10:51', '61', null, '1', null, '1', '15'), ('329', '35', '3', 'changed status from Apply to Offer', '2018-07-23 03:10:51', '61', null, '1', null, '1', '22'), ('330', '36', '6', 'changed status from Apply to Interview', '2018-07-23 03:22:57', '61', null, '1', null, '1', '3'), ('331', '36', '6', 'changed status from Apply to Interview', '2018-07-23 03:22:57', '61', null, '1', null, '1', '15'), ('332', '36', '6', 'changed status from Apply to Interview', '2018-07-23 03:22:57', '61', null, '1', null, '1', '22'), ('333', '36', '6', 'changed status from Interview to Offer', '2018-07-23 03:23:22', '61', null, '1', null, '1', '3'), ('334', '36', '6', 'changed status from Interview to Offer', '2018-07-23 03:23:22', '61', null, '1', null, '1', '15'), ('335', '36', '6', 'changed status from Interview to Offer', '2018-07-23 03:23:23', '61', null, '1', null, '1', '22'), ('336', '18', '6', 'changed status from Onboard to Interview', '2018-07-23 03:23:48', '34', null, '1', null, '1', '3'), ('337', '18', '6', 'changed status from Onboard to Interview', '2018-07-23 03:23:48', '34', null, '1', null, '1', '15');
INSERT INTO notification VALUES ('338', '18', '6', 'changed status from Onboard to Interview', '2018-07-23 03:23:48', '34', null, '1', null, '1', '22'), ('339', '37', '7', 'changed status from Apply to Offer', '2018-07-23 04:43:44', '33', null, '1', null, '1', '3'), ('340', '37', '7', 'changed status from Apply to Offer', '2018-07-23 04:43:44', '33', null, '1', null, '1', '15'), ('341', '37', '7', 'changed status from Apply to Offer', '2018-07-23 04:43:44', '33', null, '1', null, '1', '22'), ('342', '37', '7', 'changed status from Offer to Closed', '2018-07-23 04:43:53', '33', null, '1', null, '1', '3'), ('343', '37', '7', 'changed status from Offer to Closed', '2018-07-23 04:43:53', '33', null, '1', null, '1', '15'), ('344', '37', '7', 'changed status from Offer to Closed', '2018-07-23 04:43:53', '33', null, '1', null, '1', '22'), ('345', '38', '7', 'changed status from Apply to Interview', '2018-07-23 04:48:00', '33', null, '1', null, '1', '3'), ('346', '38', '7', 'changed status from Apply to Interview', '2018-07-23 04:48:00', '33', null, '1', null, '1', '15'), ('347', '38', '7', 'changed status from Apply to Interview', '2018-07-23 04:48:00', '33', null, '1', null, '1', '22'), ('348', '38', '7', 'changed status from Interview to Apply', '2018-07-23 04:48:15', '33', null, '1', null, '1', '3'), ('349', '38', '7', 'changed status from Interview to Apply', '2018-07-23 04:48:15', '33', null, '1', null, '1', '15'), ('350', '38', '7', 'changed status from Interview to Apply', '2018-07-23 04:48:15', '33', null, '1', null, '1', '22'), ('351', '39', '7', 'changed status from Apply to Onboard', '2018-07-23 06:00:59', '35', null, '1', null, '1', '3'), ('352', '39', '7', 'changed status from Apply to Onboard', '2018-07-23 06:01:00', '35', null, '1', null, '1', '15'), ('353', '39', '7', 'changed status from Apply to Onboard', '2018-07-23 06:01:00', '35', null, '1', null, '1', '22'), ('354', '40', '7', 'changed status from Apply to Onboard', '2018-07-23 06:33:02', '25', null, '1', null, '1', '3'), ('355', '40', '7', 'changed status from Apply to Onboard', '2018-07-23 06:33:03', '25', null, '1', null, '1', '15'), ('356', '40', '7', 'changed status from Apply to Onboard', '2018-07-23 06:33:03', '25', null, '1', null, '1', '22'), ('357', null, '3', 'assigned', '2018-07-23 06:34:57', '20', null, '4', null, null, '7'), ('358', null, '3', 'assigned', '2018-07-23 06:35:05', '20', null, '4', null, null, '25'), ('359', null, '3', 'assigned', '2018-07-23 06:35:16', '20', null, '4', null, null, '7'), ('360', null, '3', 'assigned', '2018-07-23 06:35:33', '20', null, '4', null, null, '4'), ('361', '43', '7', 'changed status from Apply to Offer', '2018-07-23 06:49:12', '20', null, '1', null, '1', '3'), ('362', '43', '7', 'changed status from Apply to Offer', '2018-07-23 06:49:12', '20', null, '1', null, '1', '15'), ('363', '43', '7', 'changed status from Apply to Offer', '2018-07-23 06:49:12', '20', null, '1', null, '1', '22'), ('364', '41', '7', 'changed status from Apply to Offer', '2018-07-23 06:49:26', '20', null, '1', null, '1', '3'), ('365', '41', '7', 'changed status from Apply to Offer', '2018-07-23 06:49:26', '20', null, '1', null, '1', '15'), ('366', '41', '7', 'changed status from Apply to Offer', '2018-07-23 06:49:26', '20', null, '1', null, '1', '22'), ('367', '24', '5', 'changed status from Apply to Closed', '2018-07-23 06:50:32', '35', null, '1', null, '1', '3'), ('368', '24', '5', 'changed status from Apply to Closed', '2018-07-23 06:50:32', '35', null, '1', null, '1', '15'), ('369', '24', '5', 'changed status from Apply to Closed', '2018-07-23 06:50:32', '35', null, '1', null, '1', '22'), ('370', '39', '7', 'changed status from Onboard to Offer', '2018-07-23 06:55:11', '35', null, '1', null, '1', '3'), ('371', '39', '7', 'changed status from Onboard to Offer', '2018-07-23 06:55:11', '35', null, '1', null, '1', '15'), ('372', '39', '7', 'changed status from Onboard to Offer', '2018-07-23 06:55:11', '35', null, '1', null, '1', '22'), ('373', '39', '7', 'changed status from Offer to Onboard', '2018-07-23 06:55:24', '35', null, '1', null, '1', '3'), ('374', '39', '7', 'changed status from Offer to Onboard', '2018-07-23 06:55:24', '35', null, '1', null, '1', '15'), ('375', '39', '7', 'changed status from Offer to Onboard', '2018-07-23 06:55:24', '35', null, '1', null, '1', '22'), ('376', '37', '7', 'changed status from Closed to Apply', '2018-07-23 06:57:20', '33', null, '1', null, '1', '3'), ('377', '37', '7', 'changed status from Closed to Apply', '2018-07-23 06:57:20', '33', null, '1', null, '1', '15'), ('378', '37', '7', 'changed status from Closed to Apply', '2018-07-23 06:57:20', '33', null, '1', null, '1', '22'), ('379', '37', '7', 'changed status from Apply to Closed', '2018-07-23 07:03:17', '33', null, '1', null, '1', '3'), ('380', '37', '7', 'changed status from Apply to Closed', '2018-07-23 07:03:17', '33', null, '1', null, '1', '15'), ('381', '37', '7', 'changed status from Apply to Closed', '2018-07-23 07:03:17', '33', null, '1', null, '1', '22'), ('382', '37', '7', 'changed status from Closed to Interview', '2018-07-23 07:39:02', '33', null, '1', null, '1', '3'), ('383', '37', '7', 'changed status from Closed to Interview', '2018-07-23 07:39:02', '33', null, '1', null, '1', '15'), ('384', '37', '7', 'changed status from Closed to Interview', '2018-07-23 07:39:02', '33', null, '1', null, '1', '22'), ('385', null, '3', 'assigned', '2018-07-23 07:52:47', '63', null, '4', null, null, '6'), ('386', '44', '7', 'changed status from Apply to Interview', '2018-07-23 07:56:16', '25', null, '1', null, '1', '3'), ('387', '44', '7', 'changed status from Apply to Interview', '2018-07-23 07:56:16', '25', null, '1', null, '1', '15'), ('388', '44', '7', 'changed status from Apply to Interview', '2018-07-23 07:56:16', '25', null, '1', null, '1', '22'), ('389', '46', '7', 'changed status from Apply to Interview', '2018-07-23 09:51:10', '25', null, '1', null, '1', '3'), ('390', '46', '7', 'changed status from Apply to Interview', '2018-07-23 09:51:10', '25', null, '1', null, '1', '15'), ('391', '46', '7', 'changed status from Apply to Interview', '2018-07-23 09:51:10', '25', null, '1', null, '1', '22'), ('392', '49', '6', 'changed status from Apply to Contacting', '2018-07-23 09:52:58', '34', null, '1', null, '1', '3'), ('393', '49', '6', 'changed status from Apply to Contacting', '2018-07-23 09:52:58', '34', null, '1', null, '1', '15'), ('394', '49', '6', 'changed status from Apply to Contacting', '2018-07-23 09:52:58', '34', null, '1', null, '1', '22'), ('395', '48', '6', 'changed status from Apply to Contacting', '2018-07-23 09:53:09', '34', null, '1', null, '1', '3'), ('396', '48', '6', 'changed status from Apply to Contacting', '2018-07-23 09:53:09', '34', null, '1', null, '1', '15'), ('397', '48', '6', 'changed status from Apply to Contacting', '2018-07-23 09:53:09', '34', null, '1', null, '1', '22'), ('398', '47', '6', 'changed status from Apply to Contacting', '2018-07-23 09:53:47', '34', null, '1', null, '1', '3'), ('399', '47', '6', 'changed status from Apply to Contacting', '2018-07-23 09:53:47', '34', null, '1', null, '1', '15'), ('400', '47', '6', 'changed status from Apply to Contacting', '2018-07-23 09:53:47', '34', null, '1', null, '1', '22'), ('401', '50', '6', 'changed status from Apply to Contacting', '2018-07-23 09:57:33', '34', null, '1', null, '1', '3'), ('402', '50', '6', 'changed status from Apply to Contacting', '2018-07-23 09:57:33', '34', null, '1', null, '1', '15'), ('403', '50', '6', 'changed status from Apply to Contacting', '2018-07-23 09:57:33', '34', null, '1', null, '1', '22'), ('404', null, '3', 'assigned', '2018-07-23 10:00:04', '26', null, '4', null, null, '7'), ('405', '36', '6', 'changed status from Offer to Interview', '2018-07-23 10:02:17', '61', null, '1', null, '1', '3'), ('406', '36', '6', 'changed status from Offer to Interview', '2018-07-23 10:02:17', '61', null, '1', null, '1', '15'), ('407', '36', '6', 'changed status from Offer to Interview', '2018-07-23 10:02:17', '61', null, '1', null, '1', '22'), ('408', '36', '6', 'changed status from Interview to Offer', '2018-07-23 10:02:22', '61', null, '1', null, '1', '3'), ('409', '36', '6', 'changed status from Interview to Offer', '2018-07-23 10:02:22', '61', null, '1', null, '1', '15'), ('410', '36', '6', 'changed status from Interview to Offer', '2018-07-23 10:02:22', '61', null, '1', null, '1', '22'), ('411', '36', '6', 'changed status from Offer to Interview', '2018-07-23 10:03:10', '61', null, '1', null, '1', '3'), ('412', '36', '6', 'changed status from Offer to Interview', '2018-07-23 10:03:10', '61', null, '1', null, '1', '15'), ('413', '36', '6', 'changed status from Offer to Interview', '2018-07-23 10:03:10', '61', null, '1', null, '1', '22'), ('414', '36', '6', 'changed status from Interview to Offer', '2018-07-23 10:03:15', '61', null, '1', null, '1', '3'), ('415', '36', '6', 'changed status from Interview to Offer', '2018-07-23 10:03:15', '61', null, '1', null, '1', '15'), ('416', '36', '6', 'changed status from Interview to Offer', '2018-07-23 10:03:15', '61', null, '1', null, '1', '22'), ('417', '52', '6', 'changed status from Apply to Onboard', '2018-07-23 10:04:53', '63', null, '1', null, '1', '3'), ('418', '52', '6', 'changed status from Apply to Onboard', '2018-07-23 10:04:53', '63', null, '1', null, '1', '15'), ('419', '52', '6', 'changed status from Apply to Onboard', '2018-07-23 10:04:53', '63', null, '1', null, '1', '22'), ('420', '51', '6', 'changed status from Apply to Contacting', '2018-07-23 10:06:54', '34', null, '1', null, '1', '3'), ('421', '51', '6', 'changed status from Apply to Contacting', '2018-07-23 10:06:54', '34', null, '1', null, '1', '15'), ('422', '51', '6', 'changed status from Apply to Contacting', '2018-07-23 10:06:54', '34', null, '1', null, '1', '22'), ('423', '53', '6', 'changed status from Apply to Contacting', '2018-07-23 10:12:57', '34', null, '1', null, '1', '3'), ('424', '53', '6', 'changed status from Apply to Contacting', '2018-07-23 10:12:57', '34', null, '1', null, '1', '15'), ('425', '53', '6', 'changed status from Apply to Contacting', '2018-07-23 10:12:57', '34', null, '1', null, '1', '22'), ('426', '54', '6', 'changed status from Apply to Contacting', '2018-07-24 02:39:52', '34', null, '1', null, '1', '3'), ('427', '54', '6', 'changed status from Apply to Contacting', '2018-07-24 02:39:52', '34', null, '1', null, '1', '15'), ('428', '54', '6', 'changed status from Apply to Contacting', '2018-07-24 02:39:52', '34', null, '1', null, '1', '22'), ('429', '55', '5', 'changed status from Apply to Interview', '2018-07-24 06:49:58', '21', null, '1', null, '1', '3'), ('430', '55', '5', 'changed status from Apply to Interview', '2018-07-24 06:49:58', '21', null, '1', null, '1', '15'), ('431', '55', '5', 'changed status from Apply to Interview', '2018-07-24 06:49:58', '21', null, '1', null, '1', '22'), ('432', null, '3', 'assigned', '2018-07-24 08:10:57', '65', null, '4', null, null, '5'), ('433', null, '3', 'assigned', '2018-07-24 08:12:00', '64', null, '4', null, null, '5'), ('434', '26', '5', 'changed status from Offer to Closed', '2018-07-24 08:14:27', '25', null, '1', null, '1', '3'), ('435', '26', '5', 'changed status from Offer to Closed', '2018-07-24 08:14:27', '25', null, '1', null, '1', '15'), ('436', '26', '5', 'changed status from Offer to Closed', '2018-07-24 08:14:27', '25', null, '1', null, '1', '22'), ('437', '56', '5', 'changed status from Apply to Onboard', '2018-07-24 08:16:01', '64', null, '1', null, '1', '3'), ('438', '56', '5', 'changed status from Apply to Onboard', '2018-07-24 08:16:01', '64', null, '1', null, '1', '15');
INSERT INTO notification VALUES ('439', '56', '5', 'changed status from Apply to Onboard', '2018-07-24 08:16:01', '64', null, '1', null, '1', '22'), ('440', '57', '6', 'changed status from Apply to Contacting', '2018-07-24 09:28:12', '34', null, '1', null, '1', '3'), ('441', '57', '6', 'changed status from Apply to Contacting', '2018-07-24 09:28:12', '34', null, '1', null, '1', '15'), ('442', '57', '6', 'changed status from Apply to Contacting', '2018-07-24 09:28:12', '34', null, '1', null, '1', '22');
COMMIT;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO oauth_client_details VALUES ('myclientid', null, 'secret', 'read,write', 'password,refresh_token', null, 'ROLE_ADMIN,ROLE_APPROVER,ROLE_CREATER', '36000', '36000', null, '1');
COMMIT;

-- ----------------------------
-- Table structure for `position`
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`is_delete`  bit(1) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=34

;

-- ----------------------------
-- Records of position
-- ----------------------------
BEGIN;
INSERT INTO position VALUES ('1', 'PM', '', ''), ('2', 'Java Developers', '', ''), ('3', 'Tester', '', ''), ('4', 'QA', '', ''), ('5', 'Marketing Executive', '', ''), ('6', 'dotNet Developer', '', ''), ('7', 'Business Analyst', '', ''), ('8', 'PHP Developer', '', ''), ('9', 'Internal PR', '', ''), ('10', 'Java Team Lead', '', ''), ('11', '.Net Team Lead', '', ''), ('12', 'Software Architect', 'Software Architect', ''), ('13', 'Cobol Dev', '', ''), ('14', 'Market Development', 'Phát triển thị trường', ''), ('15', 'AM', 'Account Manager', ''), ('16', 'Test Lead', '', ''), ('17', 'BA Lead', '', ''), ('18', 'DU Lead', '', ''), ('19', 'PreSale Lead', '', ''), ('20', 'Java Technical Lead', '', ''), ('21', '.Net Technical Lead', '', ''), ('22', 'Android Developer', '', ''), ('23', 'iOS Developer', '', ''), ('24', 'VB.Net', '', ''), ('25', 'Japanese Comtor', '', ''), ('26', 'Korean Comtor', '', ''), ('27', 'BA', '', ''), ('28', 'React Native Dev', '', ''), ('29', 'Node JS', '', ''), ('30', 'Training Executive', '', ''), ('31', 'Pre-Sales', '', ''), ('32', 'BrSE', '', ''), ('33', 'Dev', '', null);
COMMIT;

-- ----------------------------
-- Table structure for `priority`
-- ----------------------------
DROP TABLE IF EXISTS `priority`;
CREATE TABLE `priority` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_delete`  bit(1) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of priority
-- ----------------------------
BEGIN;
INSERT INTO priority VALUES ('1', 'Urgent', '7 days - 10 days', ''), ('2', 'Medium', '> 15 days - <30 days', ''), ('3', 'Normal', '> 30 days', '');
COMMIT;

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`description`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`is_delete`  bit(1) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=33

;

-- ----------------------------
-- Records of project
-- ----------------------------
BEGIN;
INSERT INTO project VALUES ('1', 'CJ', '', ''), ('5', 'hot', null, ''), ('6', 'CIT-ERP', 'ChoongWae - Giải pháp Y tế\nDựa án: ERP', ''), ('7', 'Kloon', 'Migration Project', ''), ('8', 'CGV', 'Khách hàng CJ', ''), ('9', 'CGV2', 'Dự án PayRoll', ''), ('10', 'Hitachi', '', ''), ('11', 'OSTech', '', ''), ('12', 'NICs', '', ''), ('13', 'Techcombank', '', ''), ('14', 'VP Bank', '', ''), ('15', 'Toshiba', '', ''), ('16', 'Ezyhaul', '', ''), ('17', 'SISTIC', '', ''), ('18', 'IBM', '', ''), ('19', 'NEC', '', ''), ('20', 'Ssan Yong', '', ''), ('21', 'LG', '', ''), ('22', 'INTERNAL', '', ''), ('23', 'LG Test', '', ''), ('24', 'Other', '', ''), ('26', 'Panasonic Automotive', '', ''), ('30', '7', null, ''), ('32', 'Embrio ODC', '', '');
COMMIT;

-- ----------------------------
-- Table structure for `recruitment_type`
-- ----------------------------
DROP TABLE IF EXISTS `recruitment_type`;
CREATE TABLE `recruitment_type` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`description`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`is_delete`  bit(1) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=5

;

-- ----------------------------
-- Records of recruitment_type
-- ----------------------------
BEGIN;
INSERT INTO recruitment_type VALUES ('1', 'New', 'New', ''), ('2', 'Replace', 'Replace leaving resource', ''), ('4', 'Instead', 'Instead', '');
COMMIT;

-- ----------------------------
-- Table structure for `request`
-- ----------------------------
DROP TABLE IF EXISTS `request`;
CREATE TABLE `request` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`request_code`  varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`position_id`  bigint(20) NOT NULL ,
`deadline`  date NULL DEFAULT NULL ,
`number`  int(10) NOT NULL ,
`description`  text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`major`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`others`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`salary`  varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`benefit`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`created_date`  date NULL DEFAULT NULL ,
`edited_date`  date NULL DEFAULT NULL ,
`published_date`  date NULL DEFAULT NULL ,
`approved_date`  date NULL DEFAULT NULL ,
`certificate`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`priority_id`  bigint(10) NULL DEFAULT NULL ,
`experience_id`  bigint(10) NULL DEFAULT NULL ,
`cv_deadline`  date NULL DEFAULT NULL ,
`request_status_id`  bigint(10) NULL DEFAULT NULL ,
`created_by`  bigint(10) NULL DEFAULT NULL ,
`edited_by`  bigint(10) NULL DEFAULT NULL ,
`recruitment_type_id`  bigint(10) NULL DEFAULT NULL ,
`project_id`  bigint(10) NULL DEFAULT NULL ,
`groups_request_id`  bigint(10) NULL DEFAULT NULL ,
`reject_reason`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`group_request_id`  bigint(20) NULL DEFAULT NULL ,
`department_id`  bigint(10) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`priority_id`) REFERENCES `priority` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`experience_id`) REFERENCES `experience` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`recruitment_type_id`) REFERENCES `recruitment_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`edited_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`groups_request_id`) REFERENCES `groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`group_request_id`) REFERENCES `group_request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`position_id`) REFERENCES `position` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_status_id`) REFERENCES `request_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`experience_id`) REFERENCES `experience` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`groups_request_id`) REFERENCES `groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`position_id`) REFERENCES `position` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`priority_id`) REFERENCES `priority` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`recruitment_type_id`) REFERENCES `recruitment_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_status_id`) REFERENCES `request_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`edited_by`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=72

;

-- ----------------------------
-- Records of request
-- ----------------------------
BEGIN;
INSERT INTO request VALUES ('20', 'ORD2018001', 'PM-Kloon', '1', '2018-08-11', '1', 'Yêu càu 5+ kinh nghiệm quản lý dự án trong mảng outsourcing, team size 30~50 người.\nƯu tiên có tech skill về mảng .net\nƯu tiên có kinh nghiệm trong các dự án mitigration', '', 'Tiếng Anh giao tiếp và viết tốt', 'Upto 2000', '', '2018-07-11', '2018-07-19', null, '2018-07-12', '', '2', '3', null, '2', '8', '8', '1', '7', '1', '', null, '24'), ('21', 'ORD2018002', 'BA - Kloon', '7', '2018-08-11', '4', '1. Mô tả:\n\n-Trao đổi với khách hàng thu thập các yêu cầu của khàng hàng;\n-Tham gia viết và review tài liệu đặc tả yêu cầu;\n-Trả lời các câu hỏi của team dự án và khách hàng;\n-Tham gia giả quyết các issue của dự án;\n\n2. Yêu cầu:\n\n-Từ 1 - 3 năm kinh nghiệm làm BA trong mảng IT outsourcing, ưu tiên các dự án làm trong khối tiếng Anh\n-Thông thạo các tools như Excel, Word, Visio, UML.\n-Kỹ năng trình bày tốt\n-Thành văn tiếng Anh tốt, ưu tiên khả năng trao đổi trực tiếp với khách hàng\n', '', '', '', '', '2018-07-11', '2018-07-18', null, '2018-07-12', '', '2', '2', null, '2', '8', '22', '1', '7', '1', '', null, '7'), ('22', 'ORD2018003', 'DU Lead cho DU1', '18', '2018-08-31', '1', 'Have experience managing at least 30~50 members. \nGood understanding of IT outsourcing Industry\n', '', '', '', '', '2018-07-12', null, null, '2018-07-12', '', '1', '4', null, '2', '8', null, '2', '24', '1', null, null, '7'), ('23', 'ORD2018004', 'DU Lead cho DU 6', '18', '2018-08-31', '1', 'Have experience managing 30~50 member, Good knowledge of IT outsourcing industry\n\n', '', '', '', '', '2018-07-12', null, null, '2018-07-12', '', '1', '4', null, '2', '8', null, '1', '24', '1', null, null, '7'), ('24', 'ORD2018005', 'Senior Technical Lead', '21', '2018-08-15', '2', 'Strong technical knowledge, especially Microsoft based technologies\nResponsible for providing project technical approach, and supervise the implementation', '', '', '30~35 triệu', '', '2018-07-12', '2018-07-19', null, '2018-07-12', '', '1', '4', null, '2', '8', '8', '1', '7', '1', '', null, '24'), ('25', 'ORD2018006', '5 Senior .net Developers ', '6', '2018-08-15', '5', 'Require 3~5 year exp of .Net framework\nprefer developer has exp in either Angular or React\n', '', '', '', '', '2018-07-12', '2018-07-19', null, '2018-07-12', '', '1', '3', null, '2', '8', '8', '1', '7', '1', '', null, '24'), ('26', 'ORD2018007', 'Sub lead/SRCUM Master ', '1', '2018-08-31', '4', 'Mô tả\nLead team dự án 5~10 người\nCác dự án đa phần sẽ chạy theo SRCUM/Agile\nLập kế hoach, tracking, báo cáo trạng thái dự án\nGiải quyết các issue của dự án\n\nYêu cầu\nLead project team 5~10 members\n1~3 year exp in project management\nPrefer how has SCRUM exp\nTiếng Anh trao đổi tốt.\nƯu tiên background về .Net', '', '', '', '', '2018-07-12', '2018-07-19', null, '2018-07-12', '', '1', '2', null, '2', '8', '8', '1', '7', '1', '', null, '24'), ('27', 'ORD2018008', 'ANDROID DEV ', '22', '2018-07-15', '1', 'Đã gửi qua email', '', '', '', '', '2018-07-12', '2018-07-16', null, '2018-07-12', '', '1', '2', null, '4', '9', '22', '2', '16', '1', '', null, '1'), ('28', 'ORD2018009', 'JAVA-CGV', '2', '2018-07-15', '2', 'Đã gửi qua email', '', '', '', '', '2018-07-12', '2018-07-12', null, '2018-07-12', '', '2', '2', null, '4', '11', '11', '2', '1', '1', null, null, '3'), ('29', 'ORD2018009', 'JAVA-PAYROLL', '2', '2018-07-31', '2', 'Đã gửi qua email.', '', '', '', '', '2018-07-12', '2018-07-16', null, '2018-07-12', '', '2', '2', null, '4', '11', '22', '1', '1', '1', '', null, '3'), ('30', 'ORD2018011', 'VB.NET - NICS', '24', '2018-07-15', '1', 'Lỗi rồiaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\naaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n', '', '', '15-25M', '', '2018-07-12', '2018-07-12', null, '2018-07-12', '', '2', '2', null, '4', '10', '22', '1', '12', '1', null, null, '2'), ('31', 'ORD2018012', 'Korean Comtor', '26', '2018-07-31', '1', 'Tiếng Hàn Quốc Topik 5 trở lên\nƯu tiên ứng viên biết tiếng Anh', '', '', '', '', '2018-07-12', null, null, '2018-07-12', '', '2', '2', null, '6', '11', null, '1', '6', '1', 'Duplicated', null, '3'), ('32', 'ORD2018013', 'Korean Comtor', '26', '2018-07-31', '1', 'Tiếng Nhật Topik 5 trở lên\nTiếng Anh giao tiếp thành thạo\nCó kinh nghiệm dịch IT là một lợi thế', '', '', '', '', '2018-07-12', null, null, '2018-07-12', '', '2', '2', null, '4', '11', null, '1', '9', '1', null, null, '3'), ('33', 'ORD2018014', 'Senior Business Analyst', '27', '2018-08-01', '3', 'MÔ TẢ:\n- Communicate with clients and all the teams for client’s requirements understanding;\n- Analyze project requirements based on given documents;\n- Support Project Manager in communicating with customers to get requirements, analyze project requirements;\n- Support UAT in testing, verifying bugs/issues with client;\n- Contact to Partners for integration with outside system (Payment gateway, Third-party supplier, …);\n- Have ability to study and capture new business domain quickly.\n- Write documents (project or product specifications, description, design and analysis):\no Create mockup, prototype\no Create Function List (if needed); \no Create SRS (System Requirement Spec); \no Create Database Design; \no Create Basic Design (if required);\n- Review Detail Design, Testcase;\n- Answer Q&A of customers and development team.\nYÊU CẦU:\n- At least 3 year as System Analyst or Business Analyst for Web/App;\n- At least 3 years as Risk Analyst for Business.\n- Having experience in ERP sector.\n- Have experience in Architecture Solution is an advantage;\n- Good understanding of client’s business requirements;\n- Good UML design is required;\n- Have good knowledge at database;\n- Have experience with Software Project workflow;\n- Bachelor’s degree in computer science/ computer engineer\n- Fluent in English and Korean language skill is a big plus\n- Passionate and positive attitude\n- Strong problem solving and analytical skills', '', '', '15-23M', '', '2018-07-13', '2018-07-23', null, '2018-07-13', '', '2', '3', null, '2', '11', '8', '1', '6', '1', '', null, '3'), ('34', 'ORD2018015', 'Panasonic Automotive 4 comtors', '25', '2018-08-01', '4', 'Tuyển Japanese comtor.\nCó 2~3 năm làm việc trong các dự án IT outsourcing\nƯu tiên có kinh nghiệm trong domain automotive\nƯu tiên các bạn trao đổi lưu loát', '', '', '', '', '2018-07-13', '2018-07-24', null, '2018-07-13', '', '1', '2', null, '2', '8', '8', '1', '26', '1', '', null, '23'), ('35', 'ORD2018016', 'Java Dev - Techcombank', '2', '2018-08-01', '2', 'Đã gửi rồi', '', '', '', '', '2018-07-13', '2018-07-19', null, '2018-07-13', '', '1', '2', null, '2', '10', '22', '2', '13', '1', '', null, '2'), ('36', 'ORD2018017', 'DU6 RRC - PM', '1', '2018-08-31', '2', 'kinh nghiệm quản lý dự án 1~3 năm.\nTiếng Anh trao đổi tốt\nít nhât 3~5 năm kinh nghiệm trong ngạch outsource\nƯu tiên các bạn đã làm quen với SCRUM/Agile', '', '', '', '', '2018-07-17', '2018-07-19', null, '2018-07-17', '', '2', '2', null, '5', '8', '22', '2', '24', '1', '', null, '24'), ('37', 'ORD2018018', 'React Native', '28', '2018-07-20', '1', 'Tony gửi riêng cho Ngô Anh :D', '', '', '', '', '2018-07-17', '2018-07-19', null, '2018-07-19', '', '1', '2', null, '4', '11', '8', '1', '32', '1', '', null, '3'), ('38', 'ORD2018019', 'Node JS Dev', '29', '2018-08-17', '1', 'Gửi riêng cho Ngô Anh vì toàn tuyển chui', '', '', '', '', '2018-07-17', '2018-07-19', null, '2018-07-19', '', '2', '2', null, '4', '11', '8', '1', '18', '1', '', null, '3'), ('39', 'ORD2018020', 'VB.NET - NICS', '24', '2018-07-25', '1', 'hhjjj', '', '', '', '', '2018-07-17', '2018-07-20', null, '2018-07-20', '', '2', '2', null, '4', '3', '3', '1', '12', '1', '', null, '2'), ('56', 'ORD2018021', 'Account Manager (Thị trường tiếng Anh)', '15', '2018-09-20', '4', '1. MÔ TẢ CÔNG VIỆC:\no	Planning for annual sales activities to achieve the assigned objectives\no	Planning for new strategical accounts exploring and market penetration \no	Generate and manage sales pipeline \no	Manage and Review internal sales process compliance \no	Manage cash flow\n2. YÊU CẦU CỦA VỊ TRÍ\no	Excellent at English - TOEIC: 650\no	Excellent negotiation skills\no	Strong listening and questioning skills - outstanding communication and\no	interpersonal skills\no	Vision to understand customer needs and translate them in to practical solutions\no	planning skills\no	Disciplined time management and ability to work under pressure\no	Excellent proposal writing, project management and analytical skills.\no	Able to analyze results and identify and explain any variances from targets\no	Ability to follow and ensure compliance with operational processes\no	Highly developed social, interpersonal and communication skills.\no	Excellent team working and team developing ability.\no	Disciplined in time management and ability to work under pressure without', '', '', '', '', '2018-07-19', null, null, '2018-07-19', '', '3', '2', null, '2', '12', null, '1', '22', '3', null, null, '4'), ('57', 'ORD2018022', 'Account Manager (Thị trường Korea)', '15', '2018-09-20', '2', '1. MÔ TẢ CÔNG VIỆC:\no	Planning for annual sales activities to achieve the assigned objectives\no	Planning for new strategical accounts exploring and market penetration \no	Generate and manage sales pipeline \no	Manage and Review internal sales process compliance \no	Manage cash flow\n2. YÊU CẦU CỦA VỊ TRÍ\no	Excellent at English and Korean - Topik level 5/ TOEIC: 650\no	Excellent negotiation skills\no	Strong listening and questioning skills - outstanding communication and\no	interpersonal skills\no	Vision to understand customer needs and translate them in to practical solutions\no	planning skills\no	Disciplined time management and ability to work under pressure\no	Excellent proposal writing, project management and analytical skills.\no	Able to analyze results and identify and explain any variances from targets\no	Ability to follow and ensure compliance with operational processes\no	Highly developed social, interpersonal and communication skills.\no	Excellent team working and team developing ability.\no	Disciplined in time management and ability to work under pressure without', '', '', '', '', '2018-07-19', null, null, '2018-07-19', '', '3', '2', null, '2', '3', null, '1', '22', '3', null, null, '4'), ('58', 'ORD2018023', 'Nhân viên phát triển thị trường tiếng Anh (MD)', '14', '2018-10-31', '10', '1. MÔ TẢ\n- Find the targeted clients based on the target market\n\n2. YÊU CẦU\n- English: Fluent/ Excellent (TOEIC: 650)\n- Excellent at communication skill\n- Result oriented management skill\n- Linkedin and social media skill', '', '', '', '', '2018-07-19', '2018-07-19', null, '2018-07-19', '', '3', '2', null, '2', '3', '3', '1', '22', '3', '', null, '4'), ('59', 'ORD2018024', 'Nhân viên phát triển thị trường Hàn Quốc (Korea MD)', '14', '2018-10-31', '4', '1. MÔ TẢ\n- Find the targeted clients based on the target market\n2. YÊU CẦU\n- English and Korean: Fluent (TOEIC 650/ TOPIK 4)\n- Excellent at communication skill\n- Result oriented management skill\n- Linkedin and social media skill', '', '', '', '', '2018-07-19', null, null, '2018-07-19', '', '3', '2', null, '2', '3', null, '1', '22', '3', null, null, '4'), ('60', 'ORD2018025', 'Senior Account Manager ', '15', '2018-08-31', '1', '1. MÔ TẢ CÔNG VIỆC:\n	Planning for annual sales activities to achieve the assigned objectives\n	Planning for new strategical accounts exploring and market penetration \n	Generate and manage sales pipeline \n	Manage and Review internal sales process compliance \n	Manage cash flow \n2. YÊU CẦU CỦA VỊ TRÍ\nThe Key Account Manager is accountable for delivering a broad and deep relationship with our key accounts, ensuring a longer term strategic relationship is maintained. The role requires a strongly motivated person that can meet customer’s expectations and be able to take a hands-on approach to problem solving by using their initiative and available resources to provide customer proposals and solutions. The role’s key metric is sales contribution and Key Account Managers aim to increase market share and deliver incremental contribution using incentives, initiatives, commitment programmes and marketing activity. Key Account Managers have a positive attitude to dealing with people and the ability to work under pressure whilst maintaining work priorities\n\nComplete the sales target of at least $700000 per year\n\n	Excellent at English - TOEIC: 700\n	Excellent negotiation skills\n	Strong listening and questioning skills - outstanding communication and\n	interpersonal skills\n	Vision to understand customer needs and translate them in to practical solutions\n	planning skills\n	Disciplined time management and ability to work under pressure\n	Excellent proposal writing, project management and analytical skills.\n	Able to analyze results and identify and explain any variances from targets\n	Ability to follow and ensure compliance with operational processes\n	Highly developed social, interpersonal and communication skills.\n	Excellent team working and team developing ability.\n	Disciplined in time management and ability to work under pressure without\n', '', '', '', '', '2018-07-19', '2018-07-19', null, '2018-07-19', '', '3', '3', null, '2', '3', '3', '1', '22', '3', '', null, '4'), ('61', 'ORD2018026', 'Pre-Sales (CMC Japan)', '31', '2018-08-31', '2', 'Như Pre-Sales và BrSE trong nước\nTiếng Nhật N1\nƯu tiên biết tiêng Anh\nCó base về kỹ thuật\nKhả năng thuyết trình và communicate tốt\nKhả năng viết proposal và bảo vệ trước khách hàng\nAm hiểu văn hóa Nhật\n', '', '', '', '', '2018-07-23', '2018-07-23', null, '2018-07-23', '', '2', '3', null, '2', '3', '17', '1', '22', '3', '', null, '11'), ('62', 'ORD2018027', 'Japanese speaking DU Lead', '18', '2018-08-31', '1', 'Trách nhiệm\nQuản lý SX cho một team từ 50~100 người cho thị trường Nhật\nBuild team cho thị trường Nhật, xây dựng training đội ngũ\nTham gia vào công việc presale\nQuản lý nhân sự \nMục tiêu xây dựng team 50 người trong 1 năm, 100 người trong 2 năm\n\nYêu cầu:\nTiếng Nhật N2+, trao đổi lưu loát.ưu tiên các bạn đã có thời gian làm việc onsite\nCó ít nhất 3~5 năm kinh nghiệm lead team khoảng 30~50 người \nKinh nghiệm 5+ trong thị trường outsoucring Nhật\nCó kinh nghiệm presale\nBiết tiếng Anh là lợi thế\n', '', '', '', '', '2018-07-23', null, null, '2018-07-23', '', '2', '3', null, '5', '8', null, '1', '24', '1', null, null, '7'), ('63', 'ORD2018028', 'Korean Comtor-CIT ERP', '26', '2018-08-15', '1', 'Mô tả: Biên phiên dịch cho dự án\nYêu cầu:\n- Tiếng Hàn Topik 5 trở lên\n- Tiếng Anh giao tiếp tốt', '', '', '', '', '2018-07-23', null, null, '2018-07-23', '', '2', '2', null, '2', '3', null, '1', '6', '1', null, null, '3'), ('64', 'ORD2018029', 'Senior Technical Lead (SA)', '11', '2018-09-08', '1', 'Strong technical knowledge, especially Microsoft based technologies\nResponsible for providing project technical approach, and supervise the implementation', '', '', '30-35 tr', '', '2018-07-23', null, null, '2018-07-23', '', '1', '4', null, '2', '8', null, '1', '7', '1', null, null, '24'), ('65', 'ORD2018030', 'Automotive - PM', '1', '2018-08-31', '1', 'Trách nhiệm\nLead team dự án test automotive với KH Nhật\nQuản lý nguồn lưc, đảm bảo chất lượng công việc\nTrao đổi với PM của KH, giải quyết các vấn đề dự án\n\nYC:\nCó kinh nghiệm quản lý team 30~50 người.\nƯu tiên người có background về automotive\nNếu biết tiếng Nhật là tốt nhất ', '', '', '', '', '2018-07-24', '2018-07-24', null, '2018-07-24', '', '1', '4', null, '2', '8', '3', '1', '26', '1', '', null, '23'), ('66', 'ORD2018031', 'Automotive Tester (Fresher)', '3', '2018-09-01', '10', 'Đã gửi Nhung qua email', '', '', '2-3M', '', '2018-07-24', null, null, '2018-07-25', '', '2', '1', null, '5', '3', null, '1', '26', '1', null, null, '23'), ('67', 'ORD2018032', 'adasdasdas', '1', '2018-07-29', '10', 's', '', '', '', '', '2018-07-25', null, null, '2018-07-25', '', '1', '2', null, '5', '8', null, '1', '1', '1', null, null, '1'), ('68', 'ORD2018033', 'dsaadsadsa', '2', '2018-07-31', '100', 's', '', '', '', '', '2018-07-25', null, null, '2018-07-25', '', '1', '1', null, '5', '8', null, '1', '1', '1', null, null, '2'), ('69', 'ORD2018034', 'Giải trình chấm công ngày 20/06/2018', '2', '2018-07-26', '1', '221', '', '', '', '', '2018-07-25', null, null, '2018-07-25', '', '3', '1', null, '5', '9', null, '1', '22', '1', null, null, '1'), ('70', 'ORD2018035', 'Giải', '1', '2018-07-29', '1', 'ôpioityere', '', '', '', '', '2018-07-25', null, null, null, '', '2', '2', null, '1', '9', null, '1', '24', '1', null, null, '2'), ('71', 'ORD2018036', 'Giải trình chấm công ngày 20/06/2018 vvv', '1', '2018-07-27', '3', 'vvv', 'vvv', 'vvv', 'vvv', 'vvv', '2018-07-25', '2018-07-25', null, null, 'vvv', '3', '1', null, '3', '9', '8', '1', '19', '1', '', null, '4');
COMMIT;

-- ----------------------------
-- Table structure for `request_assignee`
-- ----------------------------
DROP TABLE IF EXISTS `request_assignee`;
CREATE TABLE `request_assignee` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`request_id`  bigint(20) NOT NULL ,
`assignee_id`  bigint(20) NOT NULL ,
`number_of_candidate`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`assignee_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`assignee_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=82

;

-- ----------------------------
-- Records of request_assignee
-- ----------------------------
BEGIN;
INSERT INTO request_assignee VALUES ('20', '22', '25', '1'), ('21', '22', '24', '1'), ('22', '21', '7', '2'), ('23', '21', '5', '2'), ('24', '23', '25', '1'), ('25', '23', '24', '1'), ('28', '24', '7', '1'), ('29', '24', '4', '1'), ('33', '30', '6', '1'), ('34', '29', '7', '2'), ('35', '28', '7', '2'), ('36', '27', '4', '1'), ('37', '32', '7', '1'), ('38', '34', '6', '4'), ('40', '33', '7', '2'), ('42', '25', '5', '2'), ('43', '25', '7', '2'), ('44', '25', '4', '1'), ('45', '37', '4', '1'), ('46', '60', '4', '1'), ('51', '59', '3', '2'), ('52', '59', '6', '2'), ('54', '58', '6', '5'), ('55', '58', '3', '5'), ('57', '57', '6', '1'), ('58', '57', '3', '1'), ('59', '56', '6', '2'), ('60', '56', '4', '2'), ('61', '38', '4', '1'), ('62', '35', '5', '1'), ('63', '35', '7', '1'), ('64', '39', '4', '1'), ('66', '61', '6', '1'), ('67', '61', '3', '1'), ('72', '20', '25', '1'), ('73', '20', '7', '1'), ('74', '20', '4', '1'), ('75', '63', '6', '1'), ('76', '26', '6', '1'), ('77', '26', '5', '1'), ('78', '26', '4', '1'), ('79', '26', '7', '1'), ('80', '65', '5', '1'), ('81', '64', '5', '1');
COMMIT;

-- ----------------------------
-- Table structure for `request_certification`
-- ----------------------------
DROP TABLE IF EXISTS `request_certification`;
CREATE TABLE `request_certification` (
`request_id`  bigint(20) NOT NULL ,
`certification_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`request_id`, `certification_id`),
FOREIGN KEY (`certification_id`) REFERENCES `certification` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of request_certification
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `request_foreign_language`
-- ----------------------------
DROP TABLE IF EXISTS `request_foreign_language`;
CREATE TABLE `request_foreign_language` (
`request_id`  bigint(20) NOT NULL ,
`foreign_language_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`request_id`, `foreign_language_id`),
FOREIGN KEY (`foreign_language_id`) REFERENCES `foreign_language` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`foreign_language_id`) REFERENCES `foreign_language` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of request_foreign_language
-- ----------------------------
BEGIN;
INSERT INTO request_foreign_language VALUES ('20', '1'), ('21', '1'), ('22', '1'), ('23', '1'), ('24', '1'), ('25', '1'), ('26', '1'), ('28', '1'), ('29', '1'), ('32', '1'), ('36', '1'), ('56', '1'), ('57', '1'), ('58', '1'), ('59', '1'), ('60', '1'), ('63', '1'), ('64', '1'), ('65', '1'), ('66', '1'), ('34', '2'), ('61', '2'), ('62', '2'), ('31', '3'), ('32', '3'), ('57', '5'), ('59', '5'), ('63', '5');
COMMIT;

-- ----------------------------
-- Table structure for `request_skill`
-- ----------------------------
DROP TABLE IF EXISTS `request_skill`;
CREATE TABLE `request_skill` (
`request_id`  bigint(20) NOT NULL ,
`skill_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`request_id`, `skill_id`),
FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of request_skill
-- ----------------------------
BEGIN;
INSERT INTO request_skill VALUES ('28', '1'), ('29', '1'), ('35', '1'), ('67', '1'), ('68', '1'), ('69', '1'), ('70', '1'), ('71', '1'), ('27', '5'), ('21', '6'), ('33', '6'), ('20', '9'), ('22', '10'), ('23', '10'), ('62', '10'), ('65', '10'), ('24', '11'), ('25', '11'), ('64', '11'), ('26', '12'), ('26', '13'), ('36', '13'), ('30', '14'), ('39', '14'), ('34', '15'), ('31', '16'), ('32', '16'), ('63', '16'), ('56', '17'), ('57', '17'), ('58', '17'), ('59', '17'), ('60', '17'), ('56', '18'), ('57', '18'), ('58', '18'), ('59', '18'), ('60', '18'), ('56', '19'), ('57', '19'), ('58', '19'), ('59', '19'), ('60', '19'), ('37', '22'), ('38', '23'), ('37', '24'), ('61', '32'), ('61', '33'), ('65', '34'), ('66', '34'), ('65', '35');
COMMIT;

-- ----------------------------
-- Table structure for `request_status`
-- ----------------------------
DROP TABLE IF EXISTS `request_status`;
CREATE TABLE `request_status` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=7

;

-- ----------------------------
-- Records of request_status
-- ----------------------------
BEGIN;
INSERT INTO request_status VALUES ('1', 'New', ''), ('2', 'In-Progress', ''), ('3', 'Pending', ''), ('4', 'Closed', ''), ('5', 'Approved', ''), ('6', 'Rejected', '');
COMMIT;

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`role_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=8

;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO role VALUES ('1', 'ROLE_ADMIN'), ('2', 'ROLE_DU_LEAD'), ('3', 'ROLE_DU_MEMBER'), ('4', 'ROLE_HR_MANAGER'), ('5', 'ROLE_HR_MEMBER'), ('6', 'ROLE_GROUP_LEAD'), ('7', 'ROLE_RRC_LEAD');
COMMIT;

-- ----------------------------
-- Table structure for `skill`
-- ----------------------------
DROP TABLE IF EXISTS `skill`;
CREATE TABLE `skill` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`is_delete`  bit(1) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=36

;

-- ----------------------------
-- Records of skill
-- ----------------------------
BEGIN;
INSERT INTO skill VALUES ('1', 'Java', 'Java', ''), ('2', 'C#', 'C#', ''), ('3', 'C++', 'C++', ''), ('4', 'PHP', 'PHP', ''), ('5', 'Android', 'Android', ''), ('6', 'BA', '', ''), ('7', 'PM', 'Project Management', ''), ('8', 'Cobol', '', ''), ('9', 'dotNet', '', ''), ('10', 'Management', null, ''), ('11', '.Net', null, ''), ('12', 'SCRUM', null, ''), ('13', 'Project Management', null, ''), ('14', 'VB.Net', '', ''), ('15', 'Japanese', '', ''), ('16', 'Korean', '', ''), ('17', 'Sale skill', '', ''), ('18', 'Marketing Skill', '', ''), ('19', 'Customer Care', '', ''), ('20', 'Research', '', ''), ('21', 'Planning Skill', '', ''), ('22', 'React Native', '', ''), ('23', 'Node JS', '', ''), ('24', 'mobile', null, ''), ('25', 'ERP', '', ''), ('26', 'Oracle', '', ''), ('27', 'C Shap', '', ''), ('28', 'Angular JS', '', ''), ('29', 'Angular 2', '', ''), ('30', 'Spring boot', '', ''), ('31', 'SQL', '', ''), ('32', 'Pre-Sale', '', ''), ('33', 'BrSE', '', ''), ('34', 'Automtotive', null, ''), ('35', 'Test', null, '');
COMMIT;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`username`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`password`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`email`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ,
`full_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`groups_id`  bigint(20) NULL DEFAULT NULL ,
`avatar_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`department_id`  bigint(20) NULL DEFAULT NULL ,
`is_active`  bit(1) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
AUTO_INCREMENT=26

;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO user VALUES ('1', 'admin', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'admin@gmail.com', 'Admin', null, 'admin_Logo 1.png', '8', ''), ('2', 'hrlead', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'hrlead@gmail.com', 'Hr Lead', null, '', '11', ''), ('3', 'ntnhan', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'ntnhan@cmc.com.vn', 'Nguyễn Thanh Nhàn', null, 'ntnhan_13179425_833162176787687_1020887991978727300_n.jpg', '11', ''), ('4', 'ntanh11', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'ntanh11@cmc.com.vn', 'Ngô Thị Anh', null, 'ntanh11_14182191_897493983687839_739313338_n.jpg', '11', ''), ('5', 'vthnhung1', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'vthnhung1@cmc.com.vn', 'Vũ Thị Hồng Nhung', null, 'vthnhung1_30743241_473306159750692_8372008290703376384_n.jpg', '11', ''), ('6', 'ptnanh', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'ptnanh@cmc.com.vn', 'Phạm Thị Ngọc Anh', null, 'ptnanh_22195944_10207860613513612_6439592326920879385_n.jpg', '11', ''), ('7', 'ttpthao2', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'ttpthao2@cmc.com.vn', 'Thiều Thị Phương Thảo', null, 'ttpthao2_20431540_1756674131024480_6087841272923669508_n.jpg', '11', ''), ('8', 'dnbao', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'dnbao@cmc.com.vn', 'Đặng Ngọc Bảo', null, null, '7', ''), ('9', 'hnhieu', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'hnhieu@cmc.com.vn', 'Hà Ngọc Hiệu', null, 'hnhieu_24955484_10208168214322664_4503087357658890812_o.jpg', '1', ''), ('10', 'nxcanh', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'nxcanh@cmc.com.vn', 'Ngô Xuân Cảnh', null, 'nxcanh_22851821_1962741753741466_5474367568210523797_n.jpg', '2', ''), ('11', 'dtanh', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'dtanh@cmc.com.vn', 'Đào Tuấn Anh', null, 'dtanh_16996415_102063656988699_3030676334424105861_n.jpg', '3', ''), ('12', 'lvtuong', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'lvtuong@cmc.com.vn', 'Lăng Vĩnh Tường', null, 'lvtuong_23736080_10208608114920244_6417345326232270403_o.jpg', '4', ''), ('13', 'htthoa', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'htthoa@cmc.com.vn', 'Hoàng Thị Thanh Hoa', null, '', '10', ''), ('14', 'hthoa1', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'hthoa1@cmc.com.vn', 'Hoàng Thị Hòa', null, '', '8', ''), ('15', 'txlam', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'txlam@cmc.com.vn', 'Trần Xuân Lâm', null, 'txlam_193810_1888461980599_2890175_o.jpg', '11', ''), ('16', 'nmha', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'nmha@cmc.com.vn', 'Nguyễn Minh Hà', null, '', '12', ''), ('17', 'hnhung', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'hnhung@cmc.com.vn', 'Hoàng Ngọc Hùng', null, 'hnhung_31113560_10216049700892151_1066348054979805184_n.jpg', '7', ''), ('18', 'rrclead', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'rrclead@cmc.com.vn', 'Đặng Ngọc Bảo', null, '', '7', ''), ('21', 'vbhuan', '3619aa4d21c3e60b97db4198050ad66bced698d07cea7b8cfb66ad57a8f28383c43d3252b88099b4', 'vbhuan@cmc.com.vn', 'Vũ Bá Huấn', null, 'vbhuan_vbhuan.jpg', '9', ''), ('22', 'vubahuan', '7ae3b1cb0ab5d44096d3246150f51ea78f578fb4b77d89e99427b2efd0c68a18bbc10b6c866d1351', 'vubahuan@gmail.com', 'Vũ Bá Huấn', null, 'vubahuan_Selena-Gomez-Most-Beautiful-Women-2017-e1462768130807.jpg', '9', ''), ('23', 'admin2', 'ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c', 'admin2@gmail.com', 'Admin2', null, '', '8', ''), ('24', 'ntnhan1', '38da39d6adc78137511a47f8b38ca31e6a1be67212c215f22a46f057747ffd2db287876c2d43336c', 'ntnhan1980@gmail.com', 'Nguyễn Thanh Nhàn', null, null, '11', ''), ('25', 'txlam1', '4c9d80643a2403f4536232bcafea421c66c1845e03ed7f9045c8055f1bc843d157b71761b560eb02', 'ukr_txl@yahoo.com', 'Trần Xuân Lâm', null, null, '11', '');
COMMIT;

-- ----------------------------
-- Table structure for `user_groups`
-- ----------------------------
DROP TABLE IF EXISTS `user_groups`;
CREATE TABLE `user_groups` (
`user_id`  bigint(20) NOT NULL ,
`groups_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`user_id`, `groups_id`),
FOREIGN KEY (`groups_id`) REFERENCES `groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`groups_id`) REFERENCES `groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of user_groups
-- ----------------------------
BEGIN;
INSERT INTO user_groups VALUES ('8', '1'), ('9', '1'), ('10', '1'), ('11', '1'), ('18', '1'), ('21', '1'), ('2', '2'), ('3', '2'), ('4', '2'), ('5', '2'), ('6', '2'), ('7', '2'), ('13', '2'), ('15', '2'), ('16', '2'), ('17', '2'), ('22', '2'), ('24', '2'), ('25', '2'), ('12', '3'), ('14', '4');
COMMIT;

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
`user_id`  bigint(20) NOT NULL ,
`role_id`  bigint(20) NOT NULL ,
PRIMARY KEY (`user_id`, `role_id`),
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO user_role VALUES ('1', '1'), ('2', '2'), ('9', '2'), ('10', '2'), ('11', '2'), ('12', '2'), ('13', '2'), ('14', '2'), ('16', '2'), ('18', '2'), ('21', '2'), ('3', '4'), ('15', '4'), ('22', '4'), ('4', '5'), ('5', '5'), ('6', '5'), ('7', '5'), ('24', '5'), ('25', '5'), ('8', '6'), ('17', '6');
COMMIT;

-- ----------------------------
-- Indexes structure for table `account_global`
-- ----------------------------
CREATE UNIQUE INDEX `name` USING BTREE ON `account_global`(`name`) ;

-- ----------------------------
-- Auto increment value for `account_global`
-- ----------------------------
ALTER TABLE `account_global` AUTO_INCREMENT=2;

-- ----------------------------
-- Indexes structure for table `assignee_interview`
-- ----------------------------
CREATE INDEX `FKowkcugry0f4t0h91u684f9md0` USING BTREE ON `assignee_interview`(`interview_id`) ;

-- ----------------------------
-- Indexes structure for table `candidate`
-- ----------------------------
CREATE UNIQUE INDEX `uq_cvid_requestid` USING BTREE ON `candidate`(`cv_id`, `request_id`) ;
CREATE INDEX `FKt0aii4qk4ht0q6hk0uydp2jc7` USING BTREE ON `candidate`(`created_by`) ;
CREATE INDEX `FKkvl6uiwt0x7i8oor76cqsn279` USING BTREE ON `candidate`(`request_id`) ;
CREATE INDEX `FKghn8lkgleriy0vod3of91cnyh` USING BTREE ON `candidate`(`status_id`) ;

-- ----------------------------
-- Auto increment value for `candidate`
-- ----------------------------
ALTER TABLE `candidate` AUTO_INCREMENT=59;

-- ----------------------------
-- Auto increment value for `candidate_status`
-- ----------------------------
ALTER TABLE `candidate_status` AUTO_INCREMENT=7;

-- ----------------------------
-- Auto increment value for `certification`
-- ----------------------------
ALTER TABLE `certification` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `comment`
-- ----------------------------
CREATE INDEX `FKrj8gmxkixwqivstv5hp05y3di` USING BTREE ON `comment`(`candidate_id`) ;
CREATE INDEX `FKde3tumt50y7sk87ju3y7ugrqx` USING BTREE ON `comment`(`interview_id`) ;
CREATE INDEX `FK8kcum44fvpupyw6f5baccx25c` USING BTREE ON `comment`(`user_id`) ;

-- ----------------------------
-- Auto increment value for `comment`
-- ----------------------------
ALTER TABLE `comment` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `cv`
-- ----------------------------
CREATE INDEX `FK6ig9lsg56jd7008yqfpxddmjf` USING BTREE ON `cv`(`created_by`) ;
CREATE INDEX `FKoku0neavp8r7rwy7s20ob61dy` USING BTREE ON `cv`(`edited_by`) ;
CREATE INDEX `FKhr0igwtwh149j3quv16snoevg` USING BTREE ON `cv`(`experience_id`) ;
CREATE INDEX `FKq1p7xd4hxa7bngsnu1orw8sqh` USING BTREE ON `cv`(`status_id`) ;

-- ----------------------------
-- Auto increment value for `cv`
-- ----------------------------
ALTER TABLE `cv` AUTO_INCREMENT=53;

-- ----------------------------
-- Indexes structure for table `cv_certification`
-- ----------------------------
CREATE INDEX `FK7q20i66o6h9skb1axu9gm5nod` USING BTREE ON `cv_certification`(`certification_id`) ;

-- ----------------------------
-- Indexes structure for table `cv_skill`
-- ----------------------------
CREATE INDEX `FKeeel9vqe9h1d1xsb8s7wknd07` USING BTREE ON `cv_skill`(`skill_id`) ;

-- ----------------------------
-- Indexes structure for table `cv_status`
-- ----------------------------
CREATE UNIQUE INDEX `title` USING BTREE ON `cv_status`(`title`) ;

-- ----------------------------
-- Auto increment value for `cv_status`
-- ----------------------------
ALTER TABLE `cv_status` AUTO_INCREMENT=3;

-- ----------------------------
-- Indexes structure for table `cv_url`
-- ----------------------------
CREATE INDEX `FK4b80kjhb7jjcgey6b48gfahkm` USING BTREE ON `cv_url`(`cv_id`) ;

-- ----------------------------
-- Auto increment value for `cv_url`
-- ----------------------------
ALTER TABLE `cv_url` AUTO_INCREMENT=42;

-- ----------------------------
-- Indexes structure for table `department`
-- ----------------------------
CREATE UNIQUE INDEX `title` USING BTREE ON `department`(`title`) ;

-- ----------------------------
-- Auto increment value for `department`
-- ----------------------------
ALTER TABLE `department` AUTO_INCREMENT=30;

-- ----------------------------
-- Auto increment value for `experience`
-- ----------------------------
ALTER TABLE `experience` AUTO_INCREMENT=5;

-- ----------------------------
-- Auto increment value for `foreign_language`
-- ----------------------------
ALTER TABLE `foreign_language` AUTO_INCREMENT=6;

-- ----------------------------
-- Auto increment value for `group_request`
-- ----------------------------
ALTER TABLE `group_request` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `groups`
-- ----------------------------
ALTER TABLE `groups` AUTO_INCREMENT=5;

-- ----------------------------
-- Indexes structure for table `interview`
-- ----------------------------
CREATE INDEX `FK729abw46u4y6q5idcw1w388ke` USING BTREE ON `interview`(`status_id`) ;

-- ----------------------------
-- Auto increment value for `interview`
-- ----------------------------
ALTER TABLE `interview` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `interview_candidate`
-- ----------------------------
CREATE INDEX `FKmd8rdnoo863sfq0xbd0kbjd92` USING BTREE ON `interview_candidate`(`candidate_id`) ;

-- ----------------------------
-- Auto increment value for `interview_status`
-- ----------------------------
ALTER TABLE `interview_status` AUTO_INCREMENT=4;

-- ----------------------------
-- Indexes structure for table `log`
-- ----------------------------
CREATE INDEX `FK1xy1ey33xfix5odi15p3fp1s4` USING BTREE ON `log`(`actor`) ;

-- ----------------------------
-- Auto increment value for `log`
-- ----------------------------
ALTER TABLE `log` AUTO_INCREMENT=636;

-- ----------------------------
-- Indexes structure for table `notification`
-- ----------------------------
CREATE INDEX `FK60wm5mhnrqejmgnwfc0matkjv` USING BTREE ON `notification`(`candidate_id`) ;
CREATE INDEX `FKgmcypgrcb3oo4ujbbk7cyaro2` USING BTREE ON `notification`(`comment_id`) ;
CREATE INDEX `FKmb6307w01w92koi95i5f3nl3b` USING BTREE ON `notification`(`interview_id`) ;
CREATE INDEX `FKmlidwdldgmdw67l7pbrval0un` USING BTREE ON `notification`(`receiver_id`) ;
CREATE INDEX `FKg86ujqmbte2xv73dbarc460j9` USING BTREE ON `notification`(`request_id`) ;
CREATE INDEX `FKb0yvoep4h4k92ipon31wmdf7e` USING BTREE ON `notification`(`user_id`) ;

-- ----------------------------
-- Auto increment value for `notification`
-- ----------------------------
ALTER TABLE `notification` AUTO_INCREMENT=443;

-- ----------------------------
-- Auto increment value for `position`
-- ----------------------------
ALTER TABLE `position` AUTO_INCREMENT=34;

-- ----------------------------
-- Auto increment value for `priority`
-- ----------------------------
ALTER TABLE `priority` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for `project`
-- ----------------------------
ALTER TABLE `project` AUTO_INCREMENT=33;

-- ----------------------------
-- Auto increment value for `recruitment_type`
-- ----------------------------
ALTER TABLE `recruitment_type` AUTO_INCREMENT=5;

-- ----------------------------
-- Indexes structure for table `request`
-- ----------------------------
CREATE INDEX `FK4gx543dl6iqjwjedu0ukuarwb` USING BTREE ON `request`(`created_by`) ;
CREATE INDEX `FKdipv3mup2v6qg3b4a03sl90vn` USING BTREE ON `request`(`edited_by`) ;
CREATE INDEX `FK8td30o5260jv369eotqdvpyb4` USING BTREE ON `request`(`experience_id`) ;
CREATE INDEX `FKh1ygrbvfrjhns6ok1werm91uq` USING BTREE ON `request`(`groups_request_id`) ;
CREATE INDEX `FKjostakp31wbdp00ne92161j7q` USING BTREE ON `request`(`position_id`) ;
CREATE INDEX `FK4gv1g162ybmts82img5y298di` USING BTREE ON `request`(`priority_id`) ;
CREATE INDEX `FKkvq26sm6o0fejk6syyfhygl1q` USING BTREE ON `request`(`project_id`) ;
CREATE INDEX `FKa7krh34qff8csqpkc6k46mmhr` USING BTREE ON `request`(`recruitment_type_id`) ;
CREATE INDEX `FKxkku3uvj4wnc8prooej3ti6l` USING BTREE ON `request`(`request_status_id`) ;
CREATE INDEX `FKismn99qjoxhkcev4wjnsg4mwm` USING BTREE ON `request`(`group_request_id`) ;
CREATE INDEX `FK8efits1bgv0p2u99jaxynja07` USING BTREE ON `request`(`department_id`) ;

-- ----------------------------
-- Auto increment value for `request`
-- ----------------------------
ALTER TABLE `request` AUTO_INCREMENT=72;

-- ----------------------------
-- Indexes structure for table `request_assignee`
-- ----------------------------
CREATE UNIQUE INDEX `pk_request_assignee` USING BTREE ON `request_assignee`(`request_id`, `assignee_id`) ;
CREATE INDEX `FKqq5d69iajsd4h12g42h3kr000` USING BTREE ON `request_assignee`(`assignee_id`) ;

-- ----------------------------
-- Auto increment value for `request_assignee`
-- ----------------------------
ALTER TABLE `request_assignee` AUTO_INCREMENT=82;

-- ----------------------------
-- Indexes structure for table `request_certification`
-- ----------------------------
CREATE INDEX `FKok2ob2nckqv9qfyeyt6o58dfe` USING BTREE ON `request_certification`(`certification_id`) ;

-- ----------------------------
-- Indexes structure for table `request_foreign_language`
-- ----------------------------
CREATE INDEX `FKe75h46xtrh0tyg70ehisanvbx` USING BTREE ON `request_foreign_language`(`foreign_language_id`) ;

-- ----------------------------
-- Indexes structure for table `request_skill`
-- ----------------------------
CREATE INDEX `FK43vqq1bieiulh54jsuknd32uv` USING BTREE ON `request_skill`(`skill_id`) ;

-- ----------------------------
-- Auto increment value for `request_status`
-- ----------------------------
ALTER TABLE `request_status` AUTO_INCREMENT=7;

-- ----------------------------
-- Auto increment value for `role`
-- ----------------------------
ALTER TABLE `role` AUTO_INCREMENT=8;

-- ----------------------------
-- Indexes structure for table `skill`
-- ----------------------------
CREATE UNIQUE INDEX `title` USING BTREE ON `skill`(`title`) ;

-- ----------------------------
-- Auto increment value for `skill`
-- ----------------------------
ALTER TABLE `skill` AUTO_INCREMENT=36;

-- ----------------------------
-- Indexes structure for table `user`
-- ----------------------------
CREATE UNIQUE INDEX `username` USING BTREE ON `user`(`username`) ;
CREATE UNIQUE INDEX `email` USING BTREE ON `user`(`email`) ;
CREATE INDEX `FKgkh2fko1e4ydv1y6vtrwdc6my` USING BTREE ON `user`(`department_id`) ;

-- ----------------------------
-- Auto increment value for `user`
-- ----------------------------
ALTER TABLE `user` AUTO_INCREMENT=26;

-- ----------------------------
-- Indexes structure for table `user_groups`
-- ----------------------------
CREATE INDEX `FKp2p74bdhj5bso5tr0smnjlbhn` USING BTREE ON `user_groups`(`groups_id`) ;

-- ----------------------------
-- Indexes structure for table `user_role`
-- ----------------------------
CREATE INDEX `FKa68196081fvovjhkek5m97n3y` USING BTREE ON `user_role`(`role_id`) ;
