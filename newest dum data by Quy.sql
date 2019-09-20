USE cmc_recruitment;
INSERT INTO oauth_client_details
    (client_id, client_secret, scope, authorized_grant_types,
    web_server_redirect_uri, authorities, access_token_validity,
    refresh_token_validity, additional_information, autoapprove)
VALUES
    ("myclientid", "secret", "read,write",
    "password,refresh_token", null, "ROLE_ADMIN,ROLE_APPROVER,ROLE_CREATER", 36000, 36000, null, true);
    
INSERT INTO `department` (`id`, `title`, `description`) VALUES
(1, 'DU1', 'DU1'),
(2, 'DU2', 'DU2'),
(3, 'DU3', 'DU3'),
(4, 'BU1', 'BU1'),
(5, 'BU2', 'BU2'),
(6, 'BU3', 'BU3'),
(7, 'HR', 'HR'),
(8, 'QA', 'QA'),
(9, 'RRC', 'RRC'),
(10, 'TCKT', 'TCKT'),
(11, 'QT', 'QT'),
(12, 'HC', 'HC'),
(13, 'Corp', 'Corp');


INSERT INTO `role`(`id`, `role_name`) VALUES 
(1,'ROLE_ADMIN'),
(2,'ROLE_DU_LEAD'),
(3,'ROLE_DU_MEMBER'),
(4,'ROLE_HR_MANAGER'),
(5,'ROLE_HR_MEMBER'),
(6,'ROLE_GROUP_LEAD');

INSERT INTO `groups`(`id`, `title`, `description`) VALUES 
(1,'Delivery Unit','DU'),
(2,'Back Office','BO'), 
(3,'Sale','Sale'),
(4,'QA','QA');


INSERT INTO `user`(`id`, `username`, `password`, `email`, `full_name`, `avatar_url`, `department_id`, `is_active`) VALUES 
(1,'admin','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','admin@gmail.com','Admin','',8,1),
(2,'hrlead','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','hrlead@gmail.com','Hr Lead','',7,1),
(3,'ntnhan','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','ntnhan@cmc.com.vn','Nguyễn Thị Thanh Nhàn','',7,1),
(4,'ntanh11','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','ntanh11@cmc.com.vn','Ngô Thị Ánh','',7,1),
(5,'vthnhung1','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','vthnhung1@cmc.com.vn','Vũ Thị Hồng Nhung','',7,1),
(6,'ptnanh','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','ptnanh@cmc.com.vn','Phạm Thị Ngọc Anh','',7,1),
(7,'ttpthao2','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','ttpthao2@cmc.com.vn','Thiều Thị Phương Thảo','',7,1),
(8,'dnbao','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','dnbao@cmc.com.vn','Đặng Ngọc Bảo','',9,1),
(9,'hnhieu','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','hnhieu@cmc.com.vn','Hà Ngọc Hiệu','',1,1),
(10,'nxcanh','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','nxcanh@cmc.com.vn','Ngô Xuân Cảnh','',2,1),
(11,'dtanh','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','dtanh@cmc.com.vn','Đào Tuấn Anh','',3,1),
(12,'lvtuong','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','lvtuong@cmc.com.vn','Lăng Vĩnh Tường','',4,1),
(13,'htthoa','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','htthoa@cmc.com.vn','Hoàng Thị Thanh Hoa','',10,1),
(14,'hthoa1','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','hthoa1@cmc.com.vn','Hoàng Thị Hòa','',8,1),
(15,'txlam','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','txlam@cmc.com.vn','Trần Xuân Lâm','',11,1),
(16,'nmha','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','nmha@cmc.com.vn','Nguyễn Minh Hà','',12,1),
(17,'hnhung','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','hnhung@cmc.com.vn','Hoàng Ngọc Hùng','',13,1),
(18,'rrclead','ded66f5babd21f0034523dd47d1ef4e42afe325fa432d0bb69bb5609a54188e08233466bafedb47c','rrclead@cmc.com.vn','Đặng Ngọc Bảo','',9,1);

INSERT INTO `user_role`(`user_id`, `role_id`) VALUES 
('1','1'),
('2','2'),
('3','4'),
('4','5'),
('5','5'),
('6','5'),
('7','5'),
('8','6'),
('9','2'),
('10','2'),
('11','2'),
('12','2'),
('13','2'),
('14','2'),
('15','2'),
('16','2'),
('17','6'),
('18','2');

INSERT INTO `user_groups`(`user_id`, `groups_id`) VALUES 
('2','2'),
('3','2'),
('4','2'),
('5','2'),
('6','2'),
('7','2'),
('8','1'),
('9','1'),
('10','1'),
('11','1'),
('12','3'),
('13','2'),
('14','4'),
('15','2'),
('16','2'),
('17','2'),
('17','3'),
('17','4'),
('18','1');

INSERT INTO `request_status`(`id`, `title`, `description`) VALUES 
(1,'New',''), 
(2,'In-Progress',''), 
(3,'Pending',''), 
(4,'Closed',''), 
(5,'Approved',''),
(6,'Rejected','');

INSERT INTO `priority`(`id`, `title`, `description`) VALUES 
(1,'Urgent','Urgent'), 
(2,'Medium','Medium'), 
(3,'Normal','Normal');

INSERT INTO `recruitment_type`(`id`, `title`, `description`) VALUES 
(1,'New','New'),
(2,'Instead','Instead');

INSERT INTO `experience`(`id`, `title`, `description`) VALUES 
(1,'Less than 1 year','Less than 1 year'),
(2,'1 to 3 years','1 to 3 years'),
(3,'3 to 5 years','3 to 5 years'),
(4,'More than 5 years','More than 5 years');

INSERT INTO `foreign_language`(`id`, `title`, `description`) VALUES 
(1,'English','English'),
(2,'Japanese','Japanese'),
(3,'Korea','Korea');

 INSERT INTO `candidate_status`(`id`, `title`, `description`) VALUES 
(1,'Apply',''),
(2,'Contacting',''),
(3,'Interview',''),
(4,'Offer',''),
(5,'Onboard',''),
(6,'Closed','');

INSERT INTO `interview_status`(`id`, `title`, `description`) VALUES 
(1,'New',''), 
(2,'In-Process',''),
(3, 'Done','');

INSERT INTO `cv_status`(`id`, `title`, `description`) VALUES
(1,'Sourced',''), 
(2,'Blacklist','');

INSERT INTO `account_global`(`id`, `name`, `pass`) VALUES
(1,'Recruiter','1qaz2wsx3edc');

