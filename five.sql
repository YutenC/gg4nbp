CREATE DATABASE  IF NOT EXISTS `five` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `five`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: five
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `act`
--

DROP TABLE IF EXISTS `act`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `act` (
  `Act_id` int NOT NULL AUTO_INCREMENT,
  `Act_name` varchar(30) NOT NULL,
  `Act_time` datetime NOT NULL,
  `Act_location` varchar(100) DEFAULT NULL,
  `Act_quota` int NOT NULL,
  `Act_price` int DEFAULT NULL,
  `Act_value` text NOT NULL,
  `Invite_Target` tinyint NOT NULL,
  `Organizer_bank_num` char(14) DEFAULT NULL,
  `Organizer_id` int NOT NULL,
  `Del_from` int DEFAULT NULL,
  `Start_time` datetime NOT NULL,
  `End_time` datetime NOT NULL,
  `Join_number` int DEFAULT NULL,
  `act_Image` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`Act_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act`
--

LOCK TABLES `act` WRITE;
/*!40000 ALTER TABLE `act` DISABLE KEYS */;
INSERT INTO `act` VALUES (5,'sdfg','2000-03-31 00:00:00','rf',103,112,'rfe4',0,'123456781234',12,2,'2011-01-01 00:00:00','1999-05-05 00:00:00',NULL,'../imgact/sdfg.jpg'),(6,'sdfg','2000-10-21 00:00:00','dfkcvjnbn',52,4,'23ere',0,'123456781234',12,2,'2011-01-01 00:00:00','2011-01-01 00:00:00',NULL,'../imgact/sdfg.jpg'),(7,'234','2000-10-12 00:00:00','edfd',300,112,'lkm',0,'123456781234',12,2,'1999-05-05 00:00:00','2011-01-01 00:00:00',NULL,'../imgact/sdfg.jpg'),(8,'地獄大遊行','2023-06-04 00:00:00','北京天安門',300,300,'我來囉習大大',0,'000011112222',12,2,'2023-05-05 00:00:00','2023-05-15 00:00:00',NULL,'../imgact/sdfg.jpg'),(9,'大吉大利今晚吃雞','2012-12-22 00:00:00','P城',99,0,'更新囉',1,'123456781234',12,2,'2012-12-10 00:00:00','2012-12-15 00:00:00',NULL,'../imgact/sdfg.jpg'),(10,'地獄大遊行','2000-10-10 00:00:00','台北',84,200,'好地獄',0,'123456781234',12,2,'2011-01-01 00:00:00','2011-01-01 00:00:00',NULL,'../imgact/sdfg.jpg'),(12,'sdfg','2020-12-12 00:00:00','/Users/wujoe/Documents/act_spring/src/main/resources/static/imgact/sdfg.jpg',101,4,'23',0,'123456781234',12,2,'2011-01-01 00:00:00','1999-05-05 00:00:00',NULL,'../imgact/sdfg.jpg'),(14,'sdfg','2020-12-12 00:00:00','/Users/wujoe/Documents/act_spring/src/main/resources/static/imgact/sdfg.jpg',65,112,'3er',0,'123456781234',12,2,'1999-05-05 00:00:00','2011-01-01 00:00:00',NULL,'/Users/wujoe/Documents/act_spring/src/main/resources/static/imgact/sdfg.jpg'),(16,'34567876543','1999-05-05 00:00:00','/Users/wujoe/Documents/act_spring/src/main/resources/static/imgact/34567876543.jpg',69,3,'eddddddd',1,'123456781234',12,2,'1999-05-05 00:00:00','2011-01-01 00:00:00',NULL,'/Users/wujoe/Documents/act_spring/src/main/resources/static/imgact/34567876543.jpg'),(17,'你好','2020-12-12 00:00:00','../imgact/.jpg',91,112,'wsedcfds',0,'123456781234',12,2,'1999-05-05 00:00:00','2011-01-01 00:00:00',NULL,'../imgact/.jpg'),(18,'sdc','2020-12-12 00:00:00','../imgact2/sdc.jpg',56,233,'dcds',0,'123456781234',12,2,'1999-05-05 00:00:00','1999-05-05 00:00:00',NULL,'../imgact2/sdc.jpg'),(19,'2','1970-01-01 00:00:02','../imgact2/2.jpg',73,NULL,'edwsdcd',1,'123456781234',12,2,'2011-01-01 00:00:00','2011-01-01 00:00:00',NULL,'../imgact2/2.jpg'),(20,'sdfg','2021-11-11 00:00:00','../imgact2/sdfg.jpg',214,112,'aaaaa',12,'123456781234',12,2,'1999-05-05 00:00:00','2011-01-01 00:00:00',NULL,'../imgact2/sdfg.jpg'),(21,'sdfg','1970-01-01 00:00:02','../imgact2/sdfg.jpg',199,112,'zzz',0,'123456781234',12,2,'1999-05-05 00:00:00','2011-01-01 00:00:00',NULL,'../imgact/sdfg.jpg'),(22,'地弟','2021-11-11 00:00:00','台北市',300,0,'演藝圈',0,'123456781234',12,2,'2011-01-01 00:00:00','2011-01-01 00:00:00',NULL,'../imgact/sdfg.jpg'),(23,'sdfg','2021-01-01 00:00:00','zzz',179,112,'zzzz',12,'123456781234',12,2,'1999-05-05 00:00:00','1999-05-05 00:00:00',NULL,'/Users/wujoe/Documents/act_spring/src/main/resources/static/imgact/sdfg.jpg'),(24,'sdfg','2021-12-12 00:00:00','dd',100,112,'ddd',12,'123456781234',12,2,'1999-05-05 00:00:00','1999-05-05 00:00:00',NULL,'/Users/wujoe/Documents/act_spring/src/main/resources/static/imgact/sdfg.jpg'),(25,'閃電邊','2020-12-12 00:00:00','台北',64,11,'雞掰',0,'123456781234',12,2,'1999-05-05 00:00:00','2011-01-01 00:00:00',NULL,'../imgact/閃電邊.jpg');
/*!40000 ALTER TABLE `act` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_chat`
--

DROP TABLE IF EXISTS `act_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `act_chat` (
  `Chat_id` int NOT NULL AUTO_INCREMENT,
  `Member_id` int NOT NULL,
  `Act_id` int NOT NULL,
  `Chat_content` text NOT NULL,
  `Chat_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Chat_id`),
  KEY `fk_act_chat_member_id` (`Member_id`),
  KEY `fk_act_chat_act_id` (`Act_id`),
  CONSTRAINT `fk_act_chat_act_id` FOREIGN KEY (`Act_id`) REFERENCES `act` (`Act_id`),
  CONSTRAINT `fk_act_chat_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_chat`
--

LOCK TABLES `act_chat` WRITE;
/*!40000 ALTER TABLE `act_chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_list`
--

DROP TABLE IF EXISTS `act_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `act_list` (
  `Act_id` int NOT NULL,
  `Member_id` int NOT NULL,
  `GiveBack_value` text,
  PRIMARY KEY (`Act_id`,`Member_id`),
  KEY `fk_act_list_member_id` (`Member_id`),
  CONSTRAINT `fk_act_list_act_id` FOREIGN KEY (`Act_id`) REFERENCES `act` (`Act_id`),
  CONSTRAINT `fk_act_list_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_list`
--

LOCK TABLES `act_list` WRITE;
/*!40000 ALTER TABLE `act_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_message`
--

DROP TABLE IF EXISTS `act_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `act_message` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `message_state` tinyint DEFAULT NULL,
  `Act_id` int DEFAULT NULL,
  `mem_id` int DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_message`
--

LOCK TABLES `act_message` WRITE;
/*!40000 ALTER TABLE `act_message` DISABLE KEYS */;
INSERT INTO `act_message` VALUES (1,0,12,0),(2,0,15,0),(3,0,13,0);
/*!40000 ALTER TABLE `act_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_msg`
--

DROP TABLE IF EXISTS `act_msg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `act_msg` (
  `Message_id` int NOT NULL AUTO_INCREMENT,
  `Member_id` int NOT NULL,
  `Act_id` int NOT NULL,
  `Msg_value` text NOT NULL,
  `Msg_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Message_id`),
  KEY `fk_act_msg_member_id` (`Member_id`),
  KEY `fk_act_msg_act_id` (`Act_id`),
  CONSTRAINT `fk_act_msg_act_id` FOREIGN KEY (`Act_id`) REFERENCES `act` (`Act_id`),
  CONSTRAINT `fk_act_msg_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_msg`
--

LOCK TABLES `act_msg` WRITE;
/*!40000 ALTER TABLE `act_msg` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_msg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_report_list`
--

DROP TABLE IF EXISTS `act_report_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `act_report_list` (
  `Report_id` int NOT NULL AUTO_INCREMENT,
  `Report_Person` int DEFAULT NULL,
  `Manager_id` int DEFAULT NULL,
  `Report_Content` text NOT NULL,
  `Report_Image` varchar(2048) DEFAULT NULL,
  `Review_State` tinyint NOT NULL,
  `Act_id` int DEFAULT NULL,
  `Message_id` int DEFAULT NULL,
  `Report_time` datetime DEFAULT NULL,
  `Reported_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_report_list`
--

LOCK TABLES `act_report_list` WRITE;
/*!40000 ALTER TABLE `act_report_list` DISABLE KEYS */;
INSERT INTO `act_report_list` VALUES (1,NULL,NULL,'','',0,NULL,NULL,NULL,''),(2,NULL,NULL,'','',0,NULL,NULL,NULL,''),(3,NULL,NULL,'rrrr','C:\\fakepath\\BEBU6346.JPG',0,NULL,NULL,'2023-06-09 00:00:00','e');
/*!40000 ALTER TABLE `act_report_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `Article_id` int NOT NULL AUTO_INCREMENT,
  `Member_id` int NOT NULL,
  `Article_class` varchar(10) NOT NULL,
  `Article_title` varchar(20) NOT NULL,
  `Article_value` text NOT NULL,
  `Post_Time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Change_Time` datetime DEFAULT NULL,
  `State` tinyint NOT NULL,
  `Manager_id` int NOT NULL,
  `Theme_id` int NOT NULL,
  PRIMARY KEY (`Article_id`),
  KEY `fk_artice_member_id` (`Member_id`),
  KEY `fk_artice_manager_id` (`Manager_id`),
  KEY `fk_artice_Theme_id` (`Theme_id`),
  CONSTRAINT `fk_artice_manager_id` FOREIGN KEY (`Manager_id`) REFERENCES `manager` (`Manager_id`),
  CONSTRAINT `fk_artice_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`),
  CONSTRAINT `fk_artice_Theme_id` FOREIGN KEY (`Theme_id`) REFERENCES `theme` (`Theme_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_image`
--

DROP TABLE IF EXISTS `article_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_image` (
  `Image_id` int NOT NULL AUTO_INCREMENT,
  `Article_id` int NOT NULL,
  `Artic_img` varchar(2048) NOT NULL,
  PRIMARY KEY (`Image_id`),
  KEY `fk_artice_image_artice_id` (`Article_id`),
  CONSTRAINT `fk_artice_image_artice_id` FOREIGN KEY (`Article_id`) REFERENCES `article` (`Article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_image`
--

LOCK TABLES `article_image` WRITE;
/*!40000 ALTER TABLE `article_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_message`
--

DROP TABLE IF EXISTS `article_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_message` (
  `Message_id` int NOT NULL AUTO_INCREMENT,
  `Member_id` int NOT NULL,
  `Artice_id` int NOT NULL,
  `Massage_content` text NOT NULL,
  `Message_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Message_id`),
  KEY `fk_artice_mseeage_member_id` (`Member_id`),
  KEY `fk_artice_mseeage_artice_id` (`Artice_id`),
  CONSTRAINT `fk_artice_mseeage_artice_id` FOREIGN KEY (`Artice_id`) REFERENCES `article` (`Article_id`),
  CONSTRAINT `fk_artice_mseeage_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_message`
--

LOCK TABLES `article_message` WRITE;
/*!40000 ALTER TABLE `article_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ban_list`
--

DROP TABLE IF EXISTS `ban_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ban_list` (
  `Ban_id` int NOT NULL AUTO_INCREMENT,
  `Member_id` int NOT NULL,
  `Manager_id` int NOT NULL,
  `Ban_reason` text NOT NULL,
  `StartTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `EndTime` datetime NOT NULL,
  PRIMARY KEY (`Ban_id`),
  KEY `fk_ban_list_member_id` (`Member_id`),
  KEY `fk_ban_list_manager_id` (`Manager_id`),
  CONSTRAINT `fk_ban_list_manager_id` FOREIGN KEY (`Manager_id`) REFERENCES `manager` (`Manager_id`),
  CONSTRAINT `fk_ban_list_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ban_list`
--

LOCK TABLES `ban_list` WRITE;
/*!40000 ALTER TABLE `ban_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `ban_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank`
--

DROP TABLE IF EXISTS `bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank` (
  `Member_id` int NOT NULL,
  `Bank_number` char(14) NOT NULL,
  `Bank_name` varchar(20) NOT NULL,
  `Bank_id` int NOT NULL,
  PRIMARY KEY (`Bank_id`),
  KEY `fk_bank_member_id_idx` (`Member_id`),
  CONSTRAINT `fk_bank_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank`
--

LOCK TABLES `bank` WRITE;
/*!40000 ALTER TABLE `bank` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `black_list`
--

DROP TABLE IF EXISTS `black_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `black_list` (
  `Member_id` int NOT NULL,
  `Black_id` int NOT NULL,
  `Black_member_id` int NOT NULL,
  PRIMARY KEY (`Black_id`),
  KEY `fk_black_list_member_id_idx` (`Member_id`),
  KEY `fk_black_list_black_member_id_idx` (`Black_member_id`),
  CONSTRAINT `fk_black_list_black_member_id` FOREIGN KEY (`Black_member_id`) REFERENCES `member` (`Member_id`),
  CONSTRAINT `fk_black_list_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `black_list`
--

LOCK TABLES `black_list` WRITE;
/*!40000 ALTER TABLE `black_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `black_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `Coupon_id` int NOT NULL AUTO_INCREMENT,
  `Discount` int NOT NULL,
  `Condition_price` int NOT NULL,
  `Deadline` datetime NOT NULL,
  `Discount_code` varchar(10) NOT NULL,
  `state` int DEFAULT '0',
  PRIMARY KEY (`Coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (1,92,961,'2023-06-09 00:00:00','DZhD1cOe',0),(2,57,853,'2023-06-09 00:00:00','dDBZEBPW',0),(3,89,740,'2023-06-09 00:00:00','XXQmGyWl',0),(4,63,553,'2023-06-09 00:00:00','WYIRHeCX',0),(5,60,556,'2023-06-09 00:00:00','K06PbjG9',0),(6,55,555,'2023-05-30 00:00:00','555',1),(7,100,1000,'2023-07-30 00:00:00','xx124588',1);
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow_list`
--

DROP TABLE IF EXISTS `follow_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow_list` (
  `Member_id` int NOT NULL,
  `Product_id` int NOT NULL,
  PRIMARY KEY (`Member_id`,`Product_id`),
  KEY `fk_follow_list_product_id` (`Product_id`),
  CONSTRAINT `fk_follow_list_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`),
  CONSTRAINT `fk_follow_list_product_id` FOREIGN KEY (`Product_id`) REFERENCES `product` (`Product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow_list`
--

LOCK TABLES `follow_list` WRITE;
/*!40000 ALTER TABLE `follow_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `follow_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forum_report_list`
--

DROP TABLE IF EXISTS `forum_report_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum_report_list` (
  `Report_id` int NOT NULL AUTO_INCREMENT,
  `Report_person` int NOT NULL,
  `Manager_id` int NOT NULL,
  `Report_content` text NOT NULL,
  `Message_id` int DEFAULT NULL,
  `Chat_id` int DEFAULT NULL,
  `Artice_id` int DEFAULT NULL,
  `Review_state` tinyint DEFAULT NULL,
  PRIMARY KEY (`Report_id`),
  KEY `fk_forum_report_list_report_person` (`Report_person`),
  KEY `fk_forum_report_list_manager_id` (`Manager_id`),
  KEY `fk_forum_report_list_message_id` (`Message_id`),
  KEY `fk_forum_report_list_chat_id` (`Chat_id`),
  KEY `fk_forum_report_list_artice_id` (`Artice_id`),
  CONSTRAINT `fk_forum_report_list_artice_id` FOREIGN KEY (`Artice_id`) REFERENCES `article` (`Article_id`),
  CONSTRAINT `fk_forum_report_list_chat_id` FOREIGN KEY (`Chat_id`) REFERENCES `theme_chat` (`Chat_id`),
  CONSTRAINT `fk_forum_report_list_manager_id` FOREIGN KEY (`Manager_id`) REFERENCES `manager` (`Manager_id`),
  CONSTRAINT `fk_forum_report_list_message_id` FOREIGN KEY (`Message_id`) REFERENCES `article_message` (`Message_id`),
  CONSTRAINT `fk_forum_report_list_report_person` FOREIGN KEY (`Report_person`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum_report_list`
--

LOCK TABLES `forum_report_list` WRITE;
/*!40000 ALTER TABLE `forum_report_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `forum_report_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend` (
  `Member_id` int NOT NULL,
  `Friend_id` int NOT NULL,
  `Is_agree` tinyint NOT NULL,
  `Friend_member_id` int NOT NULL,
  PRIMARY KEY (`Friend_id`),
  KEY `fk_friend_friend_member_id_idx` (`Friend_member_id`),
  KEY `fk_friend_member_id` (`Member_id`),
  CONSTRAINT `fk_friend_friend_member_id` FOREIGN KEY (`Friend_member_id`) REFERENCES `member` (`Member_id`),
  CONSTRAINT `fk_friend_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gaming_tag`
--

DROP TABLE IF EXISTS `gaming_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gaming_tag` (
  `Member_id` int NOT NULL,
  `Tag_id` int NOT NULL,
  PRIMARY KEY (`Member_id`,`Tag_id`),
  KEY `fk_gamimg_Tag_Tag_id` (`Tag_id`),
  CONSTRAINT `fk_gamimg_Tag_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`),
  CONSTRAINT `fk_gamimg_Tag_Tag_id` FOREIGN KEY (`Tag_id`) REFERENCES `tag` (`Tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gaming_tag`
--

LOCK TABLES `gaming_tag` WRITE;
/*!40000 ALTER TABLE `gaming_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `gaming_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_record`
--

DROP TABLE IF EXISTS `login_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_record` (
  `Login_id` int NOT NULL AUTO_INCREMENT,
  `Member_id` int NOT NULL,
  `Login_device` varchar(250) NOT NULL,
  `Host_name` varchar(45) NOT NULL,
  `Login_city` varchar(50) DEFAULT NULL,
  `Login_Time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Login_id`),
  KEY `fk_login_record_member_id` (`Member_id`),
  CONSTRAINT `fk_login_record_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_record`
--

LOCK TABLES `login_record` WRITE;
/*!40000 ALTER TABLE `login_record` DISABLE KEYS */;
INSERT INTO `login_record` VALUES (43,152,'電腦(Windows)','localhost:8080','桃園市平鎮區','2023-06-24 16:28:47'),(45,152,'電腦(Windows)','localhost:8080','桃園市平鎮區','2023-06-25 15:42:35'),(46,152,'電腦(Windows)','localhost:8080','桃園市楊梅區','2023-06-25 15:43:37'),(47,152,'電腦(Windows)','localhost:8080','桃園市平鎮區','2023-06-25 15:47:11'),(48,152,'電腦(Windows)','localhost:8080','桃園市楊梅區','2023-06-25 16:54:37'),(49,152,'電腦(Windows)','localhost:8080','桃園市楊梅區','2023-06-25 16:55:25'),(50,152,'電腦(Windows)','localhost:8080','桃園市平鎮區','2023-06-25 16:57:19'),(52,152,'電腦(Windows)','localhost:8080','桃園市中壢區','2023-06-26 15:55:50'),(53,152,'電腦(Windows)','localhost:8080','桃園市中壢區','2023-06-26 18:29:57'),(85,151,'電腦(Windows)','localhost:8080','桃園市中壢區','2023-06-28 18:22:29'),(86,151,'電腦(Windows)','localhost:8080','桃園市中壢區','2023-06-28 19:04:38'),(87,151,'電腦(Windows)','localhost:8080','桃園市楊梅區','2023-06-28 23:46:15'),(88,152,'手機(Windows)','localhost:8080','使用者未開啟定位','2023-07-04 14:24:22');
/*!40000 ALTER TABLE `login_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manager` (
  `Manager_id` int NOT NULL AUTO_INCREMENT,
  `Account` varchar(40) NOT NULL,
  `Password` varchar(40) NOT NULL,
  `Name` varchar(40) NOT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `Is_working` tinyint NOT NULL,
  PRIMARY KEY (`Manager_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (1,'admin','111111','第一管理員','admin@nbp.gg.com','0911222333','機房',1),(2,'hrManager','123456','人資組長','hr@nbp.gg.com','0977777777','新北市板橋區中山路2段456號',1),(3,'qaTester','qaTester','測試猿','qaMtester@nbp.gg.com','(02)00001111','高雄市苓雅區自強三路789號',1),(4,'shopKeeper','buybuy123','錢夫人','monopoly@nbp.gg.com','0800777947','台北市大安區敦化南路2段345號',1),(5,'FunnyVP','DirtyDeeds','瓦倫泰大總統','funny.valentine@example.com','0978901234','新北市淡水區中正路3段456號',1),(6,'StickyFinger','bossRIP','布加拉提','bruno.bucciarati@example.com','0912345678','義大利那不勒斯市',1),(7,'never444','SexPistols4','辣妹','guido.mista@example.com','0923456789','義大利那不勒斯市',1),(8,'stuckatpile','aerosmith','小飛機','narancia.ghirga@example.com','0934567890','義大利那不勒斯市',1),(9,'escaped','PurpleHaze123','不知恥的紫煙','pannacotta.fugo@example.com','0945678901','義大利那不勒斯市',1),(10,'bossDaughter','SpiceGirl456','特例休','trish.una@example.com','0956789012','義大利那不勒斯市',1);
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `Member_id` int NOT NULL AUTO_INCREMENT,
  `Account` varchar(20) NOT NULL,
  `Password` varchar(256) NOT NULL,
  `Nick` varchar(10) DEFAULT NULL,
  `Email` varchar(40) DEFAULT NULL,
  `Phone` char(10) DEFAULT NULL,
  `Birth` date DEFAULT NULL,
  `ID_number` char(10) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `Bonus` decimal(7,2) DEFAULT '0.00',
  `Member_ver_state` tinyint DEFAULT '0',
  `Suspend_deadline` date DEFAULT NULL,
  `Headshot` varchar(2048) DEFAULT NULL,
  `Ver_deadline` datetime DEFAULT NULL,
  `Violation` tinyint DEFAULT '0',
  PRIMARY KEY (`Member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (151,'Flower','b4ff8339f22ca28b7d17b3722010f752','小花喵喵','max875e6@gmail.com','031368146','2023-06-15','A123456','新北市汐止區保一街',0.00,1,NULL,'/gg4nbp/img/member/member_pic/IMG_9311.png',NULL,0),(152,'Black','fcea920f7412b5da7be0cf42b8c93759','小黑汪汪','max875e69879@gmail.com','1035406','2023-06-08','A0123456','新北市汐止區',37.00,1,NULL,'/gg4nbp/img/member/member_pic/jay.jpg',NULL,0),(153,'AAAAA','e10adc3949ba59abbe56e057f20f883e','aaaa','max875e61@gmail.com','1231415','2023-06-21','AAAAAA','AAAAA',0.00,1,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_credit`
--

DROP TABLE IF EXISTS `member_credit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_credit` (
  `Member_id` int NOT NULL,
  `Cred_number` char(16) NOT NULL,
  `bank_name` varchar(20) NOT NULL,
  `Credit_id` int NOT NULL,
  PRIMARY KEY (`Credit_id`),
  KEY `fk_member_credit_member_id_idx` (`Member_id`),
  CONSTRAINT `fk_member_credit_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_credit`
--

LOCK TABLES `member_credit` WRITE;
/*!40000 ALTER TABLE `member_credit` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_credit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `Notice_id` int NOT NULL AUTO_INCREMENT,
  `Notice_value` text NOT NULL,
  `Member_id` int NOT NULL,
  `Notice_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Is_read` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`Notice_id`),
  KEY `fk_notice_member_id_idx` (`Member_id`),
  CONSTRAINT `fk_member_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (23,'歡迎會員 小黑汪汪 加入NBPgg ',152,'2023-06-23 13:32:35',1),(25,'歡迎會員 小黑汪汪 加入NBPgg ',152,'2023-06-25 16:54:56',1),(26,'歡迎會員 小黑汪汪 加入NBPgg ',152,'2023-06-25 16:57:35',1),(27,'歡迎會員 小花喵喵 加入NBPgg ',151,'2023-06-26 21:18:14',1),(28,'測試訊息：AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA',151,'2023-06-26 21:18:40',0),(30,'歡迎會員 小花喵喵 加入NBPgg ',151,'2023-06-28 00:31:26',0),(31,'歡迎會員 aaaa 加入NBPgg ',153,'2023-06-28 18:43:19',1),(32,'歡迎會員 小花喵喵 加入NBPgg ',151,'2023-06-28 23:46:28',1),(33,'會員 小黑汪汪 您已於2023-07-04 14:33:57.811完成下單，訂單編號1',152,'2023-07-04 14:33:57',1);
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `Product_id` int NOT NULL,
  `Order_id` int NOT NULL,
  `Quantity` int NOT NULL,
  `Comment` tinyint DEFAULT NULL,
  `Comment_content` text,
  `Is_return` tinyint DEFAULT NULL,
  `Exchange_Time` tinyint DEFAULT NULL,
  `Managers_id` int DEFAULT NULL,
  `Total_price` int DEFAULT NULL,
  PRIMARY KEY (`Product_id`,`Order_id`),
  KEY `fk_order_detail_order_id` (`Order_id`),
  KEY `fk_order_detail_managers_id` (`Managers_id`),
  CONSTRAINT `fk_order_detail_managers_id` FOREIGN KEY (`Managers_id`) REFERENCES `manager` (`Manager_id`),
  CONSTRAINT `fk_order_detail_order_id` FOREIGN KEY (`Order_id`) REFERENCES `order_master` (`Order_id`),
  CONSTRAINT `fk_order_detail_product_id` FOREIGN KEY (`Product_id`) REFERENCES `product` (`Product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (22,1,1,NULL,NULL,NULL,NULL,NULL,1850);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_master`
--

DROP TABLE IF EXISTS `order_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_master` (
  `Order_id` int NOT NULL AUTO_INCREMENT,
  `Member_id` int NOT NULL,
  `Commit_date` datetime NOT NULL,
  `Total_price` int NOT NULL,
  `Bonus_use` int NOT NULL,
  `Deliver_number` varchar(20) DEFAULT NULL,
  `Commit_Type` tinyint NOT NULL,
  `Deliver_state` tinyint NOT NULL,
  `Deliver_fee` int NOT NULL,
  `Deliver_location` varchar(200) NOT NULL,
  `Pick_Type` int NOT NULL,
  `Finish_time` datetime DEFAULT NULL,
  `Coupon_id` int DEFAULT NULL,
  `Order_status` int NOT NULL,
  `Pay_status` int NOT NULL,
  PRIMARY KEY (`Order_id`),
  KEY `fk_order_master_member_id` (`Member_id`),
  KEY `fk_order_master_coupon_id` (`Coupon_id`),
  CONSTRAINT `fk_order_master_coupon_id` FOREIGN KEY (`Coupon_id`) REFERENCES `coupon` (`Coupon_id`),
  CONSTRAINT `fk_order_master_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_master`
--

LOCK TABLES `order_master` WRITE;
/*!40000 ALTER TABLE `order_master` DISABLE KEYS */;
INSERT INTO `order_master` VALUES (1,152,'2023-07-04 14:33:58',1850,0,NULL,1,0,200,'桃園市aa',2,NULL,6,1,1);
/*!40000 ALTER TABLE `order_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_app_login`
--

DROP TABLE IF EXISTS `other_app_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `other_app_login` (
  `App_id` char(1) NOT NULL,
  `Member_id` int NOT NULL,
  `Login_Token` text NOT NULL,
  PRIMARY KEY (`App_id`,`Member_id`),
  KEY `fk_other_app_login_member_id` (`Member_id`),
  CONSTRAINT `fk_other_app_login_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_app_login`
--

LOCK TABLES `other_app_login` WRITE;
/*!40000 ALTER TABLE `other_app_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_app_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `power_list`
--

DROP TABLE IF EXISTS `power_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `power_list` (
  `Power_id` int NOT NULL AUTO_INCREMENT,
  `Power_name` varchar(40) NOT NULL,
  `Power_content` text NOT NULL,
  PRIMARY KEY (`Power_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `power_list`
--

LOCK TABLES `power_list` WRITE;
/*!40000 ALTER TABLE `power_list` DISABLE KEYS */;
INSERT INTO `power_list` VALUES (1,'權限管理員','查詢、控制管理員資料，並且控制管理員擁有的權限'),(2,'會員管理員','查詢會員資料、停權紀錄，修改或中斷停權時間'),(3,'商城管理員','管理商品與訂單，對優惠券進行設定'),(4,'二手商城管理員','負責二手商品的收購、上架與管理'),(5,'檢舉單管理員','管理活動與文章申訴的檢舉單');
/*!40000 ALTER TABLE `power_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `power_of_manager`
--

DROP TABLE IF EXISTS `power_of_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `power_of_manager` (
  `Manager_id` int NOT NULL,
  `Power_id` int NOT NULL,
  PRIMARY KEY (`Manager_id`,`Power_id`),
  KEY `fk_power_of_manager_power_id` (`Power_id`),
  CONSTRAINT `fk_power_of_manager_manager_id` FOREIGN KEY (`Manager_id`) REFERENCES `manager` (`Manager_id`),
  CONSTRAINT `fk_power_of_manager_power_id` FOREIGN KEY (`Power_id`) REFERENCES `power_list` (`Power_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `power_of_manager`
--

LOCK TABLES `power_of_manager` WRITE;
/*!40000 ALTER TABLE `power_of_manager` DISABLE KEYS */;
INSERT INTO `power_of_manager` VALUES (1,1),(2,1),(1,2),(3,2),(1,3),(3,3),(4,3),(1,4),(3,4),(4,4),(1,5),(3,5);
/*!40000 ALTER TABLE `power_of_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `Product_id` int NOT NULL AUTO_INCREMENT,
  `Product_name` varchar(80) NOT NULL,
  `Type` int NOT NULL,
  `Price` int NOT NULL,
  `Amount` int NOT NULL,
  `Buy_Times` int NOT NULL,
  `Brand` varchar(45) DEFAULT NULL,
  `Rate` int NOT NULL,
  `Reviewe_count` int NOT NULL,
  `Content` text,
  `Launch_Time` datetime DEFAULT NULL,
  `state` int DEFAULT '0',
  `Takeoff_time` datetime DEFAULT NULL,
  PRIMARY KEY (`Product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (22,'任天堂 Switch 霍格華茲的傳承',2,1850,149,1,'WB',0,0,'\"《霍格華茲的傳承》故事背景設定在《哈利波特》小說中首次揭露的魔法世界。玩家將來到 19 世紀末期的霍格華茲魔法與巫術學院，扮演一名自創的魔法學徒，踏上穿越熟悉和全新地點的旅程，探索和發現奇妙的野獸，打造屬於自己的角色和製作魔藥，掌握施咒技巧，升級天賦，成為心目中所嚮往的巫師。','2020-12-03 00:00:00',0,NULL),(23,'任天堂 Switch 寶可夢 傳說 阿爾宙斯',2,1350,150,0,'任天堂',0,0,'《寶可夢傳說 阿爾宙斯》為了帶來有別於至今《寶可夢》系列的嶄新體驗，將在重視原有遊戲特色的同時，前所未有地挑戰融合了「動作」與「RPG」的展現方式。在本次作品中，將描述尚未有寶可夢訓練家或寶可夢聯盟等概念的過去時代的故事。雖然寶可夢會在嚴峻的自然環境之下自由自在地棲息在各種地方，但呈現出的是與《寶可夢 鑽石 / 珍珠》時代的神奧地區不同的生態系統。玩家將從「木木梟」、「火球鼠」與「水水獺」中選擇 1 隻寶可夢，一起踏上冒險旅程。','2021-11-04 00:00:00',0,NULL),(24,'任天堂 Nintendo Switch 瑪利歐賽車8',2,1400,150,0,'任天堂',0,0,'\"《瑪利歐賽車 8 豪華版》是在Wii U主機上推出的《瑪利歐賽車 8》的 Switch 強化移植版，將完整收錄《瑪利歐賽車 8》發售後推出的全部追加下載項目，同時追加新模式、新角色與新賽道、車種和道具，內容豐富程度為系列之最。而且，『瑪利歐賽車 8 豪華版』當中的「對戰模式」更是豐富！另外，近期備受注目的任天堂旗下新作《漆彈大作戰（ Splatoon）》的角色也收錄其中是一大亮點。','2023-02-05 00:00:00',0,NULL),(25,'PS5 快打旋風 6',22,1500,150,0,'CAPCOM',0,0,'\"對戰格鬥遊戲將於2023年迎來新時代。','2020-04-06 00:00:00',0,NULL),(26,'PS5 MLB美國職棒大聯盟22',22,1600,150,0,'SCET',0,0,'XX','2020-12-07 00:00:00',0,NULL),(27,'PS5 女神異聞錄５ 皇家版',22,1800,150,0,'SEGA',0,0,'\"偷回我們的未來吧！','2023-12-08 00:00:00',0,NULL),(28,'NS Switch ZELDA薩爾達傳說 王國之淚 中文版',2,2700,150,0,'任天堂',0,0,'\"《薩爾達傳說 王國之淚》是 2017 年廣受好評獲獎無數的《薩爾達傳說 曠野之息》的續篇作品。','2021-05-09 00:00:00',0,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `Image_id` int NOT NULL AUTO_INCREMENT,
  `Product_id` int NOT NULL,
  `Image` varchar(2048) DEFAULT NULL,
  `state` int DEFAULT '0',
  PRIMARY KEY (`Image_id`),
  KEY `fk_product_image_product_id` (`Product_id`),
  CONSTRAINT `fk_product_image_product_id` FOREIGN KEY (`Product_id`) REFERENCES `product` (`Product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (127,22,'../img/gameSoftware/test/22_index.PNG',NULL),(128,22,'../img/gameSoftware/test/22_1.PNG',NULL),(129,22,'../img/gameSoftware/test/22_2.PNG',NULL),(130,23,'../img/gameSoftware/test/23_index.PNG',NULL),(131,23,'../img/gameSoftware/test/23_1.PNG',NULL),(132,23,'../img/gameSoftware/test/23_2.PNG',NULL),(133,24,'../img/gameSoftware/test/24_index.PNG',NULL),(134,24,'../img/gameSoftware/test/24_1.PNG',NULL),(135,24,'../img/gameSoftware/test/24_2.PNG',NULL),(136,25,'../img/gameSoftware/test/25_index.PNG',NULL),(137,25,'../img/gameSoftware/test/25_1.PNG',NULL),(138,25,'../img/gameSoftware/test/25_2.PNG',NULL),(139,26,'../img/gameSoftware/test/26_index.PNG',NULL),(140,26,'../img/gameSoftware/test/26_1.PNG',NULL),(141,26,'../img/gameSoftware/test/26_2.PNG',NULL),(142,27,'../img/gameSoftware/test/27_index.PNG',NULL),(143,27,'../img/gameSoftware/test/27_1.PNG',NULL),(144,27,'../img/gameSoftware/test/27_2.PNG',NULL),(145,28,'../img/gameSoftware/test/28_index.PNG',NULL),(146,28,'../img/gameSoftware/test/28_1.PNG',NULL),(147,28,'../img/gameSoftware/test/28_2.PNG',NULL);
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secondhand_buy_picture`
--

DROP TABLE IF EXISTS `secondhand_buy_picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secondhand_buy_picture` (
  `Image_id` int NOT NULL AUTO_INCREMENT,
  `BuyList_id` int NOT NULL,
  `Image` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`Image_id`),
  KEY `fk_secondHand_buy_picture_buyList_id` (`BuyList_id`),
  CONSTRAINT `fk_secondHand_buy_picture_buyList_id` FOREIGN KEY (`BuyList_id`) REFERENCES `secondhand_buylist` (`BuyList_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secondhand_buy_picture`
--

LOCK TABLES `secondhand_buy_picture` WRITE;
/*!40000 ALTER TABLE `secondhand_buy_picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `secondhand_buy_picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secondhand_buylist`
--

DROP TABLE IF EXISTS `secondhand_buylist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secondhand_buylist` (
  `BuyList_id` int NOT NULL AUTO_INCREMENT,
  `Member_id` int NOT NULL,
  `Manager_id` int NOT NULL,
  `Product_name` varchar(30) NOT NULL,
  `Type` char(3) NOT NULL,
  `Content` text,
  `Estimate` int NOT NULL,
  `Price` int NOT NULL,
  `Confirm_Time` datetime NOT NULL,
  `Pay_state` int NOT NULL,
  `Approval_state` char(3) NOT NULL,
  `Apply_time` datetime NOT NULL,
  `Applicant_bank_number` char(14) NOT NULL,
  PRIMARY KEY (`BuyList_id`),
  KEY `fk_secondHand_buyList_member_id` (`Member_id`),
  KEY `fk_secondHand_buyList_manager_id` (`Manager_id`),
  CONSTRAINT `fk_secondHand_buyList_manager_id` FOREIGN KEY (`Manager_id`) REFERENCES `manager` (`Manager_id`),
  CONSTRAINT `fk_secondHand_buyList_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secondhand_buylist`
--

LOCK TABLES `secondhand_buylist` WRITE;
/*!40000 ALTER TABLE `secondhand_buylist` DISABLE KEYS */;
/*!40000 ALTER TABLE `secondhand_buylist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secondhand_follow_list`
--

DROP TABLE IF EXISTS `secondhand_follow_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secondhand_follow_list` (
  `Product_id` int NOT NULL,
  `Member_id` int NOT NULL,
  PRIMARY KEY (`Product_id`,`Member_id`),
  KEY `fk_secondHand_follow_list_member_id` (`Member_id`),
  CONSTRAINT `fk_secondHand_follow_list_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`),
  CONSTRAINT `fk_secondHand_follow_list_product_id` FOREIGN KEY (`Product_id`) REFERENCES `secondhand_product` (`Product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secondhand_follow_list`
--

LOCK TABLES `secondhand_follow_list` WRITE;
/*!40000 ALTER TABLE `secondhand_follow_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `secondhand_follow_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secondhand_order`
--

DROP TABLE IF EXISTS `secondhand_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secondhand_order` (
  `Order_id` int NOT NULL AUTO_INCREMENT,
  `Product_id` int NOT NULL,
  `Member_id` int NOT NULL,
  `Is_return` tinyint DEFAULT NULL,
  `Manager_id` int DEFAULT NULL,
  `Deliver_state` tinyint NOT NULL,
  `Deliver_id` varchar(30) DEFAULT NULL,
  `Deliver_location` varchar(50) NOT NULL,
  `Receive` tinyint NOT NULL,
  `Deliver_fee` int NOT NULL,
  `Total_price` int NOT NULL,
  `Deliver_name` varchar(10) NOT NULL,
  `Pay_Bank` varchar(20) DEFAULT NULL,
  `Pay_Number` varchar(20) DEFAULT NULL,
  `Pay_state` tinyint NOT NULL DEFAULT '0',
  `Order_date` date NOT NULL,
  PRIMARY KEY (`Order_id`),
  KEY `fk_secondHand_order_product_id` (`Product_id`),
  KEY `fk_secondHand_order_member_id` (`Member_id`),
  KEY `fk_secondHand_order_manager_id` (`Manager_id`),
  CONSTRAINT `fk_secondHand_order_manager_id` FOREIGN KEY (`Manager_id`) REFERENCES `manager` (`Manager_id`),
  CONSTRAINT `fk_secondHand_order_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`),
  CONSTRAINT `fk_secondHand_order_product_id` FOREIGN KEY (`Product_id`) REFERENCES `secondhand_product` (`Product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secondhand_order`
--

LOCK TABLES `secondhand_order` WRITE;
/*!40000 ALTER TABLE `secondhand_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `secondhand_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secondhand_product`
--

DROP TABLE IF EXISTS `secondhand_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secondhand_product` (
  `Product_id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Type` char(2) DEFAULT NULL,
  `Price` int DEFAULT NULL,
  `Content` text,
  `Launch_Time` datetime DEFAULT NULL,
  `Is_launch` char(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`Product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secondhand_product`
--

LOCK TABLES `secondhand_product` WRITE;
/*!40000 ALTER TABLE `secondhand_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `secondhand_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secondhand_product_image`
--

DROP TABLE IF EXISTS `secondhand_product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secondhand_product_image` (
  `Image_id` int NOT NULL AUTO_INCREMENT,
  `Product_id` int NOT NULL,
  `Image` varchar(2048) DEFAULT NULL,
  `Is_use` tinyint NOT NULL,
  PRIMARY KEY (`Image_id`),
  KEY `fk_secondHand_product_image_product_id` (`Product_id`),
  CONSTRAINT `fk_secondHand_product_image_product_id` FOREIGN KEY (`Product_id`) REFERENCES `secondhand_product` (`Product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secondhand_product_image`
--

LOCK TABLES `secondhand_product_image` WRITE;
/*!40000 ALTER TABLE `secondhand_product_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `secondhand_product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopping_list`
--

DROP TABLE IF EXISTS `shopping_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shopping_list` (
  `Memmber_id` int NOT NULL,
  `Product_id` int NOT NULL,
  `Quantity` int NOT NULL,
  PRIMARY KEY (`Memmber_id`,`Product_id`),
  KEY `fk_shopping_list_product_id` (`Product_id`),
  CONSTRAINT `fk_shopping_list_memmber_id` FOREIGN KEY (`Memmber_id`) REFERENCES `member` (`Member_id`),
  CONSTRAINT `fk_shopping_list_product_id` FOREIGN KEY (`Product_id`) REFERENCES `product` (`Product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopping_list`
--

LOCK TABLES `shopping_list` WRITE;
/*!40000 ALTER TABLE `shopping_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `shopping_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `Tag_id` int NOT NULL AUTO_INCREMENT,
  `Tag_name` varchar(25) NOT NULL,
  `Tag_picture` varchar(2048) NOT NULL,
  PRIMARY KEY (`Tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `theme`
--

DROP TABLE IF EXISTS `theme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `theme` (
  `Theme_id` int NOT NULL AUTO_INCREMENT,
  `Theme_name` varchar(20) DEFAULT NULL,
  `Theme_img` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`Theme_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `theme`
--

LOCK TABLES `theme` WRITE;
/*!40000 ALTER TABLE `theme` DISABLE KEYS */;
/*!40000 ALTER TABLE `theme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `theme_chat`
--

DROP TABLE IF EXISTS `theme_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `theme_chat` (
  `Chat_id` int NOT NULL AUTO_INCREMENT,
  `Member_id` int NOT NULL,
  `Theme_id` int NOT NULL,
  `Chat_content` text NOT NULL,
  `Chat_Time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Chat_id`),
  KEY `fk_Theme_chat_member_id` (`Member_id`),
  KEY `fk_Theme_chat_Theme_id` (`Theme_id`),
  CONSTRAINT `fk_Theme_chat_member_id` FOREIGN KEY (`Member_id`) REFERENCES `member` (`Member_id`),
  CONSTRAINT `fk_Theme_chat_Theme_id` FOREIGN KEY (`Theme_id`) REFERENCES `theme` (`Theme_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `theme_chat`
--

LOCK TABLES `theme_chat` WRITE;
/*!40000 ALTER TABLE `theme_chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `theme_chat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-04 15:13:58
