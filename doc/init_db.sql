-- ----------------------------
-- Re-create database `evc`
-- ----------------------------
DROP DATABASE IF EXISTS evc;
CREATE DATABASE evc;
USE evc;
-- ----------------------------
-- Table structure for table `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(40) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for table `exam`
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `subject_ids` varchar(100) NOT NULL,
  `people` int NOT NULL DEFAULT 0,
  `date` datetime NOT NULL,
  `semester_number` int NOT NULL,
  `is_show_rank` tinyint NOT NULL DEFAULT '1',
  `is_score_uploaded` tinyint NOT NULL DEFAULT '0',
  `is_show_grade_rank` tinyint NOT NULL DEFAULT '1',
  `is_show_class_rank` tinyint NOT NULL DEFAULT '1',
  `note` varchar(50) DEFAULT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='this table records all exams of every subject of every semester';

-- ----------------------------
-- Table structure for table `file`
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(20) DEFAULT NULL,
  `type` varchar(20) NOT NULL COMMENT 'file type. may be video, music, excel file, zip file and so on',
  `name` varchar(40) NOT NULL,
  `path` varchar(100) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `download_count` int(11) NOT NULL DEFAULT '0',
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for table `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL COMMENT 'user can leave a message to admin, message can be several types. question, suggestion, complain, ...',
  `nick` varchar(45),
  `contact` varchar(40) NOT NULL,
  `title` varchar(40) NOT NULL,
  `content` varchar(100) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for table `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) NOT NULL,
  `title` varchar(40) NOT NULL,
  `important_level` enum('2','1','0') DEFAULT '0',
  `content` varchar(200) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `notice_admin_id_idx` (`admin_id`),
  CONSTRAINT `notice_admin_id` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='this table records all admin''s announcement to user';

-- ----------------------------
-- Table structure for table `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(40) NOT NULL,
  `student_id` int(11) DEFAULT NULL COMMENT 'this is the related student of current user. user may not have a related student, thus this field can be null.',
  `email` varchar(40) DEFAULT NULL COMMENT 'user''s contact info, can be null for now.',
  `last_login` datetime,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_student_id_idx` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='this is a normal user table of current site. users can leave a message to admin and can reply a message to admin after login.';

-- ----------------------------
-- Table structure for table `reply`
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) NOT NULL,
  `admin_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `content` varchar(100) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `message_reply_message_id_idx` (`message_id`),
  KEY `reply_admin_id_idx` (`admin_id`),
  KEY `reply_user_id_idx` (`user_id`),
  CONSTRAINT `reply_admin_id` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `reply_message_id` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `reply_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for table `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `name_pinyin` varchar(45) NOT NULL,
  `sex` enum('1','0') NOT NULL DEFAULT '1',
  `grade` int NOT NULL,
  `class` int NOT NULL,
  `birth_year` char(4) NOT NULL,
  `birth_day` char(4) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for table `score`
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_number` int(11) NOT NULL,
  `exam_id` int(11) NOT NULL,
  `chinese` decimal(4,1),
  `chinese_rank` int,
  `math` decimal(4,1),
  `math_rank` int,
  `english` decimal(4,1),
  `english_rank` int,
  `physics` decimal(4,1),
  `physics_rank` int,
  `chemistry` decimal(4,1),
  `chemistry_rank` int,
  `biologic` decimal(4,1),
  `biologic_rank` int,
  `politics` decimal(4,1),
  `politics_rank` int,
  `history` decimal(4,1),
  `history_rank` int,
  `geography` decimal(4,1),
  `geography_rank` int,
  `physical` decimal(4,1),
  `physical_rank` int,
  `experiment` decimal(4,1),
  `experiment_rank` int,
  `score1` decimal(4,1),
  `score1_rank` int,
  `score2` decimal(4,1),
  `score2_rank` int,
  `total` decimal(4,1),
  `total_rank` int,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `score_exam_id_idx` (`exam_id`),
  CONSTRAINT `score_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `carousel`
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img_url` varchar(255) NOT NULL,
  `link_url` varchar(255) DEFAULT NULL,
  `order` int NOT NULL,
  `alt_text` varchar(255) DEFAULT NULL,
  `enabled` tinyint NOT NULL DEFAULT '1',
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `subject`
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `semester`
-- ----------------------------
DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `number` int(5) NOT NULL,
  `year` smallint NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1,'2018004','张三丰','zhangsanfeng','1',2021,3,'1990','0809',NULL,'2017-11-27 19:55:31');

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1,1,'dev',MD5('pass'),0,'a@b.com',NULL,'2017-11-26 12:20:23');

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1,'admin',MD5('admin'),'2017-11-27 19:55:31');

-- ----------------------------
-- Records of carousel
-- ----------------------------
INSERT INTO `carousel` VALUES (1, '/images/slides/slide1.jpg', '', 1, NULL, 1, '2021-03-11 00:17:01');
INSERT INTO `carousel` VALUES (2, '/images/slides/slide2.jpg', '', 2, NULL, 1, '2021-03-11 00:17:49');
INSERT INTO `carousel` VALUES (3, '/images/slides/slide3.jpg', '', 3, NULL, 1, '2021-03-11 00:18:04');
INSERT INTO `carousel` VALUES (4, '/images/slides/slide4.jpg', '', 4, NULL, 1, '2021-03-11 00:18:16');
INSERT INTO `carousel` VALUES (5, '/images/slides/slide5.jpg', '', 5, NULL, 1, '2021-03-11 00:18:36');

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES (1, '语文', '2021-03-11 00:27:29');
INSERT INTO `subject` VALUES (2, '数学', '2021-03-11 00:27:37');
INSERT INTO `subject` VALUES (3, '英语', '2021-03-11 00:27:42');
INSERT INTO `subject` VALUES (4, '政治', '2021-03-11 00:27:53');
INSERT INTO `subject` VALUES (5, '历史', '2021-03-11 00:27:59');
INSERT INTO `subject` VALUES (6, '地理', '2021-03-11 00:28:04');
INSERT INTO `subject` VALUES (7, '物理', '2021-03-11 00:28:09');
INSERT INTO `subject` VALUES (8, '化学', '2021-03-11 00:28:14');
INSERT INTO `subject` VALUES (9, '生物', '2021-03-11 00:28:18');
INSERT INTO `subject` VALUES (10, '体育', '2021-03-11 00:28:25');
INSERT INTO `subject` VALUES (11, '实验', '2021-03-11 00:28:25');

-- ----------------------------
-- Records of semester
-- ----------------------------
INSERT INTO `semester` VALUES (1, '2017~2018上学期', 20181, 2018, '2021-03-11 00:33:21');
INSERT INTO `semester` VALUES (2, '2017~2018下学期', 20182, 2018, '2021-03-11 00:33:48');
INSERT INTO `semester` VALUES (3, '2018~2019上学期', 20191, 2019, '2021-03-11 00:34:14');
INSERT INTO `semester` VALUES (4, '2018~2019下学期', 20192, 2019, '2021-03-11 00:35:06');
INSERT INTO `semester` VALUES (5, '2019~2020上学期', 20201, 2020, '2021-03-11 00:35:24');
INSERT INTO `semester` VALUES (6, '2019~2020下学期', 20202, 2020, '2021-03-11 00:35:38');
INSERT INTO `semester` VALUES (7, '2020~2021上学期', 20211, 2021, '2021-03-11 00:36:14');

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, 1, '2021年春节放假通知', '2', '根据国务院通知，2021年春节从2月11日放假，到2月17日，共7天，2月18日正式上班。', '2021-03-11 01:03:52');
INSERT INTO `notice` VALUES (2, 1, '2021春节返校通知', '2', '2月18号返校，请各位家长及时将子女送来。', '2021-03-11 22:00:46');

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES (1, '第一次月考', '1,2,3', 50, '2020-11-28 00:00:00', 20211, 1, 0, 1, 1, NULL, '2021-03-11 20:50:19');
INSERT INTO `exam` VALUES (2, '半期考试', '1,2,3,4,5,6,7,8,9', 47, '2020-12-28 00:00:00', 20211, 1, 0, 1, 1, NULL, '2021-03-11 21:56:01');
INSERT INTO `exam` VALUES (3, '期末考试', '1,2,3,4,5,6,7,8,9', 50, '2021-01-28 00:00:00', 20211, 1, 0, 1, 1, NULL, '2021-03-11 21:56:42');

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, '3', '哈哈', '13800002222', '系统好慢啊，查询成绩都要几分钟', '系统好慢啊，查询成绩都要几分钟', '2021-03-11 20:54:53');
INSERT INTO `message` VALUES (2, '1', '哈哈', '1234@qq.com', '请问这个月的月假哪天放啊？', '请问这个月的月假哪天放啊？', '2021-03-11 20:55:35');
INSERT INTO `message` VALUES (3, '2', '哈哈', '18799994444', '要是有手机版的页面就好了', '现在大多数家长都用手机来查成绩，要是这个系统有手机版的页面就好了', '2021-03-11 20:56:49');

