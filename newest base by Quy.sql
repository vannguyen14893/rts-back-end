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

create table department(
	id bigint primary key auto_increment,
    title varchar(255) NOT NULL UNIQUE,
    description varchar(255)
);
ALTER TABLE department CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
create table role(
	id bigint primary key auto_increment,
    role_name varchar(255)
);

CREATE TABLE groups(
    id bigint NOT NULL AUTO_INCREMENT primary key,
    title varchar(255) NOT NULL,
    description varchar(1000) NOT NULL
);
ALTER TABLE groups CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

create table user(
	id bigint primary key auto_increment,
    username varchar(255) unique not null,
    password varchar(255) not null,
    email varchar(255) unique not null,
    full_name nvarchar(255),
    groups_id bigint,
    avatar_url varchar(255),
    department_id bigint,
    is_active bit,
    constraint fk_user_department foreign key(department_id) references department(id)
);
ALTER TABLE user CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
create table user_role(
	user_id bigint,
    role_id bigint,
    constraint pk_user_role primary key(user_id, role_id),
    constraint fk_user_role_user foreign key(user_id) references user(id),
    constraint fk_user_role_role foreign key(role_id) references role(id)
);

create table user_groups(
	user_id bigint,
    groups_id bigint,
    constraint pk_user_groups primary key(user_id, groups_id),
    constraint fk_user_groups_user foreign key(user_id) references user(id),
    constraint fk_user_groups_group foreign key(groups_id) references groups(id)
);

CREATE TABLE `position`(
 id bigint PRIMARY KEY AUTO_INCREMENT,
 title varchar(255) NOT NULL,
 description varchar(255)
);
ALTER TABLE position CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE skill(
 id bigint PRIMARY KEY AUTO_INCREMENT,
 title varchar(255) NOT NULL UNIQUE,
 description varchar(255)
);
ALTER TABLE skill CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
CREATE TABLE request_status(
 id bigint PRIMARY KEY AUTO_INCREMENT,
 title varchar(255) NOT NULL,
 description varchar(255)
);

CREATE TABLE priority(
 id bigint PRIMARY KEY AUTO_INCREMENT,
 title varchar(255) NOT NULL,
 description varchar(255)
);

CREATE TABLE project (
    id bigint NOT NULL AUTO_INCREMENT primary key,
    title varchar(255) NOT NULL,
    description varchar(1000)
);
ALTER TABLE project CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE recruitment_type (
    id bigint NOT NULL AUTO_INCREMENT primary key,
    title varchar(255) NOT NULL,
    description varchar(1000) NOT NULL
);
ALTER TABLE recruitment_type CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE experience (
	id bigint NOT NULL AUTO_INCREMENT primary key,
    title varchar(255) NOT NULL,
    description varchar(1000) NOT NULL
);

CREATE TABLE foreign_language (
	id bigint NOT NULL AUTO_INCREMENT primary key,
    title varchar(255) NOT NULL,
    description varchar(1000) NOT NULL
);

create table request (
	id bigint AUTO_INCREMENT primary key,
	request_code varchar(50),
    title varchar(255) not null,
    position_id bigint not null,
    deadline date,
    number integer(10) not null,
    description varchar(255) not null,
    major varchar(255),
    others varchar(255),
    salary varchar(50),
    benefit varchar(255),
    created_date date,
    edited_date date,
    published_date date,
    approved_date date,
    certificate varchar(255),
    priority_id bigint(10),
    experience_id bigint(10),
    cv_deadline date,
    request_status_id bigint(10),
    created_by bigint(10),
    edited_by bigint(10),
    recruitment_type_id bigint(10),
    project_id bigint(10),
    groups_request_id bigint(10),
    reject_reason nvarchar(255),
    constraint fk_request_request_status foreign key(request_status_id) references request_status(id),
    constraint fk_request_user_created_by foreign key(created_by) references `user`(id),
    constraint fk_request_user_edited_by foreign key(edited_by) references `user`(id),
    constraint fk_request_position foreign key(position_id) references `position`(id),
    constraint fk_request_priority foreign key(priority_id) references `priority`(id),
    constraint fk_request_experience foreign key(experience_id) references `experience`(id),
    constraint fk_request_recruitment_type foreign key(recruitment_type_id) references `recruitment_type`(id),
    constraint fk_request_project foreign key(project_id) references `project`(id),
    constraint fk_request_groups foreign key(groups_request_id) references `groups`(id)
);
ALTER TABLE request CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE request_assignee(
 id bigint PRIMARY KEY AUTO_INCREMENT,
 request_id bigint NOT NULL,
 assignee_id bigint NOT NULL,
 number_of_candidate int,
 CONSTRAINT pk_request_assignee UNIQUE(request_id, assignee_id),
 CONSTRAINT fk_request_assignee_request FOREIGN KEY(request_id) REFERENCES request(id),
 CONSTRAINT fk_request_assignee_assignee FOREIGN KEY(assignee_id) REFERENCES user(id)
);

CREATE TABLE request_skill(
 request_id bigint NOT NULL,
 skill_id bigint NOT NULL,
 CONSTRAINT pk_request_skill PRIMARY KEY(request_id, skill_id),
 CONSTRAINT fk_request_skill_request FOREIGN KEY(request_id) REFERENCES request(id),
 CONSTRAINT fk_request_skill_skill FOREIGN KEY(skill_id) REFERENCES skill(id)
);

CREATE TABLE request_foreign_language (
	request_id bigint NOT NULL,
    foreign_language_id bigint NOT NULL,
    CONSTRAINT pk_request_foreign_language PRIMARY KEY(request_id, foreign_language_id),
	CONSTRAINT fk_request_foreign_language_request FOREIGN KEY(request_id) REFERENCES request(id),
	CONSTRAINT fk_request_foreign_language_foreign_language FOREIGN KEY(foreign_language_id) REFERENCES foreign_language(id)
);

CREATE TABLE candidate_status (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    description varchar(255)
);

CREATE TABLE interview_status (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    description varchar(255)
);

create table cv_status(
	id bigint primary key auto_increment,
    title varchar(255) unique not null,
    description varchar(255)
);

CREATE TABLE cv (
    id bigint PRIMARY KEY AUTO_INCREMENT,
	title varchar(255),
    full_name varchar(255),
    dob date,
    gender bit,
    phone varchar(15),
    email varchar(100),
    profile_img varchar(255),
    address varchar(255),
    education varchar(255),
    experience_id bigint,
    status_id bigint,
    created_date timestamp DEFAULT CURRENT_TIMESTAMP,
    created_by bigint,
    edited_date timestamp DEFAULT CURRENT_TIMESTAMP,
    edited_by bigint,
    facebook varchar(255),
    skype varchar(255),
    linkedin varchar(255),
    note nvarchar(1000),
    CONSTRAINT fk_cv_status FOREIGN KEY (status_id)
    REFERENCES cv_status(id),
    CONSTRAINT fk_cv_experience FOREIGN KEY (experience_id)
    REFERENCES experience(id),
    CONSTRAINT fk_cv_create_by FOREIGN KEY (created_by)
    REFERENCES user(id),
    CONSTRAINT fk_cv_edit_by FOREIGN KEY (edited_by)
    REFERENCES user(id)
);
ALTER TABLE cv CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

create table cv_url(
	id bigint primary key auto_increment,
    url varchar(1000),
    cv_id bigint,
    CONSTRAINT fk_cvUrl_cv FOREIGN KEY (cv_id)
    REFERENCES cv(id)
);
ALTER TABLE cv_url CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
create table cv_skill(
	  cv_id bigint,
    skill_id bigint,
    primary key(cv_id, skill_id),
    constraint fk_cv_skill_cv foreign key(cv_id) references cv(id),
    constraint fk_cv_skill_skill foreign key(skill_id) references skill(id)
);
CREATE TABLE candidate (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    title nvarchar(255),
    cv_id bigint NOT NULL,
    request_id bigint NOT NULL,
    status_id bigint NOT NULL,
    evaluate_point float,
 	source varchar(255),
    create_date timestamp,
 	created_by bigint,
	onboard_date timestamp,
 CONSTRAINT fk_candidate_user FOREIGN KEY (created_by)
    REFERENCES user(id),
    CONSTRAINT fk_candidate_cv FOREIGN KEY (cv_id)
    REFERENCES cv(id),
    CONSTRAINT fk_candidate_request FOREIGN KEY (request_id)
    REFERENCES request(id),
    CONSTRAINT fk_candidate_status FOREIGN KEY (status_id)
    REFERENCES candidate_status(id),
    CONSTRAINT uq_cvid_requestid UNIQUE(cv_id, request_id)
);
ALTER TABLE candidate CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE interview (
    id bigint primary key AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    start_time timestamp DEFAULT CURRENT_TIMESTAMP,
    end_time timestamp DEFAULT CURRENT_TIMESTAMP,
    location varchar(255),
    note varchar(255),
    status_id bigint,
    constraint fk_interview_interview_status foreign key(status_id) references interview_status(id)
);
ALTER TABLE interview CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE `comment` (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    candidate_id bigint NOT NULL,
    user_id bigint NOT NULL,
    comment_detail varchar(255) NOT NULL,
    create_date timestamp,
    interview_id bigint NULL,
    CONSTRAINT fk_comment_interview FOREIGN KEY (interview_id)
    REFERENCES interview(id),
    CONSTRAINT fk_comment_candidate FOREIGN KEY (candidate_id)
    REFERENCES candidate(id),
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id)
    REFERENCES user(id)
);
ALTER TABLE cmc_recruitment.comment CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;


CREATE TABLE interview_candidate(
 interview_id bigint NOT NULL,
 candidate_id bigint NOT NULL,
 CONSTRAINT pk_interview_candidate PRIMARY KEY(interview_id, candidate_id),
 CONSTRAINT fk_interview_candidate_interview FOREIGN KEY(interview_id) REFERENCES interview(id),
 CONSTRAINT fk_interview_candidate_candidate FOREIGN KEY(candidate_id) REFERENCES candidate(id)
);

CREATE TABLE assignee_interview (
    user_id bigint NOT NULL,
    interview_id bigint NOT NULL,
    primary key(user_id, interview_id),
    CONSTRAINT fk_assignee_user FOREIGN KEY (user_id)
    REFERENCES user(id),
    CONSTRAINT fk_assignee_interview FOREIGN KEY (interview_id)
    REFERENCES interview(id)
);
CREATE TABLE log (
    id bigint primary key AUTO_INCREMENT,
    actor bigint NOT NULL,
 action nvarchar(255),
    log_time timestamp DEFAULT CURRENT_TIMESTAMP,
    table_name varchar(50),
    content text,
    assignee_id int,
    interviewer_id int,
    request_id int,
    candidate_id int,
    interview_id int,
    cv_id int,

    CONSTRAINT fk_log_actor FOREIGN KEY (actor)
    REFERENCES user(id)
);
ALTER TABLE log CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE `notification` (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    candidate_id bigint NULL,
    user_id bigint NOT NULL,
    content varchar(255) NOT NULL,
    create_date timestamp DEFAULT CURRENT_TIMESTAMP,
    request_id bigint NULL,
    interview_id bigint NULL,
    notification_type bigint NULL,
    comment_id bigint NULL,
	status boolean,
	receiver_id bigint,
    CONSTRAINT fk_notification_interview FOREIGN KEY (interview_id)
    REFERENCES interview(id),
    CONSTRAINT fk_notification_candidate FOREIGN KEY (candidate_id)
    REFERENCES candidate(id),
    CONSTRAINT fk_notification_comment FOREIGN KEY (comment_id)
    REFERENCES comment(id),
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id)
    REFERENCES user(id),
    CONSTRAINT fk_notification_request FOREIGN KEY (request_id)
    REFERENCES request(id),
	CONSTRAINT fk_notification_receiver FOREIGN KEY (user_id)
    REFERENCES user(id)
);

CREATE TABLE account_global (
	id int primary key auto_increment,
    name varchar(255) unique not null,
    pass varchar(255) not null,
    csrf_token varchar(255) not null,
    logout_token varchar(255) not null
);

ALTER TABLE notification CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER DATABASE cmc_recruitment CHARACTER SET utf8 COLLATE utf8_unicode_ci;

