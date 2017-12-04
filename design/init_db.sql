-----------------------------------
-- Re-create database `evc`
-----------------------------------
DROP DATABASE IF EXISTS evc;
CREATE DATABASE evc;

-----------------------------------
-- Table structure for table `admin`
-----------------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(40) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------
-- Table structure for table `exam`
-----------------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) NOT NULL COMMENT 'the creator of this exam',
  `name` varchar(20) DEFAULT NULL,
  `year` int(11) NOT NULL,
  `semester` enum('1','2') NOT NULL DEFAULT '1',
  `subject` varchar(10) NOT NULL,
  `nth` int(11) DEFAULT NULL COMMENT 'the Nth exams',
  `is_half` enum('1','0') NOT NULL DEFAULT '0',
  `is_final` enum('1','0') NOT NULL DEFAULT '0',
  `note` varchar(50) DEFAULT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `exam_admin_id_idx` (`admin_id`),
  CONSTRAINT `exam_admin_id` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='this table records all exams of every subject of every semester';

-----------------------------------
-- Table structure for table `file`
-----------------------------------
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------
-- Table structure for table `message`
-----------------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL COMMENT 'user can leave a message to admin, message can be several types. question, suggestion, complain, ...',
  `title` varchar(40) NOT NULL,
  `contact` varchar(40) NOT NULL,
  `content` varchar(100) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------
-- Table structure for table `notice`
-----------------------------------
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='this table records all admin''s announcement to user';

-----------------------------------
-- Table structure for table `reply`
-----------------------------------
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------
-- Table structure for table `score`
-----------------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `exam_id` int(11) NOT NULL,
  `score` decimal(4,1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `score_exam_id_idx` (`exam_id`),
  KEY `score_student_id_idx` (`student_id`),
  CONSTRAINT `score_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `score_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------
-- Table structure for table `student`
-----------------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `sex` enum('1','0') NOT NULL DEFAULT '1',
  `grade` int(11) NOT NULL,
  `birth_year` int(11) NOT NULL,
  `birth_month` int(11) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-----------------------------------
-- Dumping data for table `student`
-----------------------------------
LOCK TABLES `student` WRITE;
INSERT INTO `student` VALUES 
(1,'default','1',0,1990,1,NULL,'2017-11-27 19:55:31');
UNLOCK TABLES;

-----------------------------------
-- Table structure for table `user`
-----------------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(40) NOT NULL,
  `student_id` int(11) DEFAULT NULL COMMENT 'this is the related student of current user. user may not have a related student, thus this field can be null.',
  `email` varchar(40) DEFAULT NULL COMMENT 'user''s contact info, can be null for now.',
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_student_id_idx` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='this is a normal user table of current site. users can leave a message to admin and can reply a message to admin after login.';

-----------------------------------
-- Dumping data for table `user`
-----------------------------------
LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES 
(1,'dev','pass',0,'a@b.com','2017-11-26 12:20:23');
UNLOCK TABLES;

