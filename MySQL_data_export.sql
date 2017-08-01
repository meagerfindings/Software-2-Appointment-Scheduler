-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: [REDACTED]    Database: U03zHj
-- ------------------------------------------------------
-- Server version	5.5.54-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `addressId` int(10) NOT NULL,
  `address` varchar(50) NOT NULL,
  `address2` varchar(50) NOT NULL,
  `cityId` int(10) NOT NULL,
  `postalCode` varchar(10) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `createDate` datetime NOT NULL,
  `createdBy` varchar(40) NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateBy` varchar(40) NOT NULL,
  PRIMARY KEY (`addressId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` (`addressId`, `address`, `address2`, `cityId`, `postalCode`, `phone`, `createDate`, `createdBy`, `lastUpdate`, `lastUpdateBy`) VALUES (1,'123 Glacier Way','Flat B5',7,'900201','123124','2017-06-18 17:38:53','Darh Maul','2017-06-18 17:38:54','Darh Maul'),(2,'Slave 1','',14,'N/A','00000000','2017-06-23 02:18:46','Darh Maul','2017-06-23 02:18:49','Darh Maul'),(3,'Captains Quarters','',18,'89012','Unlisted','2017-06-23 03:08:56','Darh Maul','2017-06-23 03:08:58','Darh Maul'),(4,'15 Muse St','23B',19,'85667','14586-54','2017-07-04 00:24:51','Darh Maul','2017-07-04 00:24:55','Darh Maul'),(5,'41 East North Shore','apt 204',1,'60626','00000000','2017-07-04 00:26:09','Darh Maul','2017-07-04 00:26:13','Darh Maul'),(6,'43 Hamburg St','Flat 8',20,'201555','255266','2017-07-04 00:27:25','Darh Maul','2017-07-04 00:27:29','Darh Maul'),(7,'123 Five Ninver St','5452 WestB',21,'00000000','564564564','2017-07-04 00:36:46','Darh Maul','2017-07-04 00:36:50','Darh Maul'),(8,'d','asd',22,' 888','123k111 3','2017-07-14 00:50:19','Darh Maul','2017-07-14 00:50:20','Darh Maul'),(9,'123','123',23,'123:','123a','2017-07-14 00:53:06','Darh Maul','2017-07-14 00:53:07','Darh Maul'),(10,'123','123 Fake Street',24,'12345','123222','2017-07-14 02:25:19','Darh Maul','2017-07-14 02:25:21','Darh Maul'),(11,'1987 You just lost the Game','',25,'19851993','6556889','2017-07-22 19:19:45','test','2017-07-22 19:19:47','test'),(12,'Granny\'s House','',26,'888324','33113142','2017-07-23 22:38:08','test','2017-07-23 22:38:10','test'),(13,'2010 14 St','Kapost',3,'80302','2010145','2017-07-27 00:15:07','test','2017-07-27 00:15:10','test'),(14,'8989 huron st','',27,'80260','720-471-7754','2017-07-28 02:19:02','test','2017-07-28 02:19:05','test'),(15,'56456 51st Street','Flat 5',28,'54564','65456465','2017-07-28 22:54:57','mat greten','2017-07-28 22:55:00','mat greten'),(16,'0000000','0000000',29,'000000','0000000','2017-07-28 22:57:22','mat greten','2017-07-28 22:57:25','mat greten'),(17,'00000000w','000000002',30,'564560','00000000','2017-07-28 23:29:16','mat greten','2017-07-28 23:29:20','mat greten'),(18,'123 Glacier Gorge','Cave 3c',7,'900222','123124','2017-07-29 15:54:11','test','2017-07-29 15:54:14','test'),(19,'65465 Fifth St','',31,'60103','00405','2017-07-29 16:09:29','test','2017-07-29 16:09:32','test'),(20,'4564 Willowbee','54564564',32,'5464564','04564','2017-07-29 16:12:59','test','2017-07-29 16:13:02','test'),(21,'1234 Main oak','44',33,'98198','5551212','2017-08-01 19:09:21','test','2017-08-01 19:09:25','test');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment` (
  `appointmentId` int(10) NOT NULL,
  `customerId` int(10) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `location` text NOT NULL,
  `contact` text NOT NULL,
  `url` varchar(255) NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `createDate` datetime NOT NULL,
  `createdBy` varchar(40) NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateBy` varchar(40) NOT NULL,
  PRIMARY KEY (`appointmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` (`appointmentId`, `customerId`, `title`, `description`, `location`, `contact`, `url`, `start`, `end`, `createDate`, `createdBy`, `lastUpdate`, `lastUpdateBy`) VALUES (1,1,'Automate this appointment','Initial Consultation','Alleyway','TaunTaun','https:/xkcd.com/1319/','2017-06-11 14:14:00','2017-06-11 23:16:00','2017-06-23 01:35:51','Randal Munroe','2017-07-13 03:02:55','Darh Maul'),(4,2,'Solo Pickup','Routine Check-In','Cloud City','Lord vader','','2017-06-27 06:00:00','2017-06-28 03:00:00','2017-06-23 02:20:24','Jaba the Hutt','2017-07-08 18:41:22','Darh Maul'),(5,3,'Discuss Surrender (of nemesis)','Troubleshooting','nondescript field','NightSwan','','2017-06-20 18:15:00','2017-06-21 00:00:00','2017-06-23 03:10:19','Mat','2017-07-12 23:08:03','Darh Maul'),(6,5,'Smashing Pumpkins Show','Customer Calibration','Red Bird Arena','Darcy, oh wait...','','2017-07-26 09:15:00','2017-07-26 20:18:00','2017-07-04 00:38:02','Jimmy Chamberlin','2017-07-08 18:41:22','Darh Maul'),(9,4,'Month test 2','Demo','Paris 2','','','2017-02-06 15:14:00','2017-02-06 21:17:00','2017-07-06 21:56:28','Darh Maul','2017-07-29 15:46:19','test'),(10,5,'Testing','Customer Calibration','Haroomp','','','2017-07-18 08:14:00','2017-07-18 07:16:00','2017-07-08 14:34:53','Darh Maul','2017-07-08 14:34:54','Darh Maul'),(11,3,'Testing','Emergency Customer Assistance','','','','2017-07-19 15:14:00','2017-07-19 18:16:00','2017-07-08 14:35:42','Darh Maul','2017-07-28 01:52:44','test'),(12,6,'Month and Appointment Type Test','Routine Check-In','asdfasdf','','','2017-06-14 21:00:00','2017-06-14 18:00:00','2017-07-08 14:36:24','Darh Maul','2017-07-08 14:36:25','Darh Maul'),(13,1,'Week of July 8','Emergency Customer Assistance','Our House','','','2017-07-08 20:15:00','2017-07-08 20:16:00','2017-07-08 19:33:18','Darh Maul','2017-07-08 19:33:18','Darh Maul'),(14,6,'Testing inactive users','Routine Check-In','Sweden','Weileong','','2017-07-11 19:14:00','2017-07-11 19:16:00','2017-07-11 02:04:50','Darh Maul','2017-07-11 02:04:50','Darh Maul'),(16,6,'kjhdfjk,asdhfklas;df','Customer Calibration','asdfasdf','','','2017-07-19 08:14:00','2017-07-19 19:17:00','2017-07-12 22:39:03','Darh Maul','2017-07-12 22:39:04','Darh Maul'),(18,5,'asga','Customer Calibration','asdg','','','2017-07-14 22:14:00','2017-07-14 23:16:00','2017-07-13 01:47:56','Darh Maul','2017-07-13 01:47:57','Darh Maul'),(19,5,'agadg','Troubleshooting','1235','1235','1234','2017-06-14 13:01:00','2017-06-14 21:17:00','2017-07-13 02:08:28','Darh Maul','2017-07-13 02:08:29','Darh Maul'),(20,6,'12512525','Troubleshooting','12351235','12351235','fasdfasd','2017-07-14 13:01:00','2017-07-14 13:02:00','2017-07-13 02:11:04','Darh Maul','2017-07-13 02:11:05','Darh Maul'),(21,1,'asdfsadf','Troubleshooting','asdf','','','2017-06-14 18:00:00','2017-06-14 19:00:00','2017-07-13 02:35:36','Darh Maul','2017-07-13 02:35:37','Darh Maul'),(22,1,'Reminder Test','Emergency Customer Assistance','','','','2017-07-17 18:00:00','2017-07-17 20:00:00','2017-07-18 03:17:56','Darh Maul','2017-07-18 03:17:58','Darh Maul'),(23,3,'Reminder 2 Test','Demo','','','','2017-07-16 18:00:00','2017-07-16 18:00:00','2017-07-18 03:18:58','Darh Maul','2017-07-18 03:18:59','Darh Maul'),(24,1,'Reminder Test 3','Customer Calibration','','','','2017-07-21 18:00:00','2017-07-21 18:00:00','2017-07-18 03:21:11','Darh Maul','2017-07-18 03:21:13','Darh Maul'),(25,1,'Reminder Test 5','Troubleshooting','','','','2017-06-25 21:12:00','2017-06-25 22:13:00','2017-07-18 03:25:00','Darh Maul','2017-07-18 03:25:01','Darh Maul'),(27,1,'Remind all the things','Routine Check-In','','','','2017-07-26 21:21:00','2017-07-26 23:14:00','2017-07-18 03:37:01','Darh Maul','2017-07-18 03:37:45','Darh Maul'),(28,8,'Remidner for today','Routine Check-In','Quik-E-Mart','','','2017-07-22 15:57:00','2017-07-22 18:00:00','2017-07-22 15:43:42','Darh Maul','2017-07-22 15:43:44','Darh Maul'),(29,10,'Test without CalendarDB in Main_Class folder','Emergency Customer Assistance','Kapost, duh','Diane','fe80::f2d8:d54b:153c:7389','2017-07-27 18:00:00','2017-07-27 20:00:00','2017-07-27 00:16:43','test','2017-07-27 00:16:45','test'),(31,2,'Interrogate','Routine Check-In','Back room','Calrissian','','2017-05-15 22:00:00','2017-05-15 23:00:00','2017-07-28 02:16:15','test','2017-07-29 15:46:46','test'),(32,8,'Testing out Empty Description','Demo','Chatanooga, Tennesee','','','2017-06-04 20:11:00','2017-06-04 21:13:00','2017-07-28 22:43:31','test','2017-07-28 22:43:33','test'),(33,13,'mewithoutyou concert','Routine Check-In','Ogden Theatre','Timothy Hay','','2017-07-02 18:13:00','2017-07-02 19:14:00','2017-07-28 23:31:19','mat greten','2017-07-28 23:31:22','mat greten'),(34,12,'DB Close Test','Demo','Outside','','','2017-07-29 18:26:00','2017-07-29 22:00:00','2017-07-29 15:20:35','test','2017-07-29 15:22:39','mat greten'),(36,14,'New Appointment Test','Initial Consultation','Anywhere, USA','','','2017-07-31 17:11:00','2017-07-31 20:14:00','2017-07-29 16:10:12','test','2017-07-29 16:10:15','test'),(37,1,'Capture Luke','Customer Calibration','Shhh it’s a secret','','','2017-07-29 22:45:00','2017-07-29 23:00:00','2017-07-30 02:36:23','test','2017-07-30 02:38:17','mat greten'),(38,16,'Plan to take over the world','Emergency Customer Assistance','Evil hide out','Dr. Evil','www.yourdoom.com','2017-08-02 17:00:00','2017-08-03 05:20:00','2017-08-01 19:10:37','test','2017-08-01 19:10:39','test');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `cityId` int(10) NOT NULL,
  `city` varchar(50) NOT NULL,
  `countryId` int(10) NOT NULL,
  `createDate` datetime NOT NULL,
  `createdBy` varchar(40) NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateBy` varchar(40) NOT NULL,
  PRIMARY KEY (`cityId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` (`cityId`, `city`, `countryId`, `createDate`, `createdBy`, `lastUpdate`, `lastUpdateBy`) VALUES (1,'Chicago',5,'2017-06-16 00:49:49','Test','2017-06-16 00:49:51','Test'),(2,'Longmont',5,'2017-06-16 00:49:59','Test','2017-06-16 00:50:01','Test'),(3,'Boulder',5,'2017-06-16 00:50:04','Test','2017-06-16 00:50:06','Test'),(4,'Denver',5,'2017-06-16 00:50:08','Test','2017-06-16 00:50:10','Test'),(5,'Courascant',10,'2017-06-16 00:50:19','Test','2017-06-16 00:50:22','Test'),(6,'Mexico City',2,'2017-06-16 00:51:20','Test','2017-06-16 00:51:22','Test'),(7,'Echo Base',6,'2017-06-16 00:51:46','Test','2017-06-16 00:51:48','Test'),(8,'Madrid',4,'2017-06-16 00:58:50','Test','2017-06-16 00:58:52','Test'),(9,'Warsaw',11,'2017-06-16 01:52:25','Test','2017-06-16 01:52:27','Test'),(10,'timbuk too',12,'2017-06-16 01:53:37','Test','2017-06-16 01:53:39','Test'),(11,'gagag',13,'2017-06-16 01:55:55','Test','2017-06-16 01:55:57','Test'),(12,'Blah Blah',14,'2017-06-16 03:09:03','Test','2017-06-16 03:09:05','Test'),(13,'Jundland Wastes',15,'2017-06-17 18:56:56','Test','2017-06-17 18:56:58','Test'),(14,'Unkown',16,'2017-06-17 19:05:04','Test','2017-06-17 19:05:06','Test'),(15,'Unknown',17,'2017-06-17 19:26:21','Test','2017-06-17 19:26:23','Test'),(16,'Milwaukee',5,'2017-06-17 22:37:07','Darh Maul','2017-06-17 22:37:10','Darh Maul'),(17,'Mos Eisley',15,'2017-06-17 23:24:41','Darh Maul','2017-06-17 23:24:44','Darh Maul'),(18,'Judicator',18,'2017-06-23 03:08:54','Darh Maul','2017-06-23 03:08:57','Darh Maul'),(19,'London',20,'2017-07-04 00:24:50','Darh Maul','2017-07-04 00:24:54','Darh Maul'),(20,'Stockholm',21,'2017-07-04 00:27:24','Darh Maul','2017-07-04 00:27:28','Darh Maul'),(21,'Thereisno',22,'2017-07-04 00:36:45','Darh Maul','2017-07-04 00:36:49','Darh Maul'),(22,'fff',23,'2017-07-14 00:50:18','Darh Maul','2017-07-14 00:50:19','Darh Maul'),(23,'qwer',24,'2017-07-14 00:53:04','Darh Maul','2017-07-14 00:53:06','Darh Maul'),(24,'Springfield',5,'2017-07-14 02:25:18','Darh Maul','2017-07-14 02:25:20','Darh Maul'),(25,'Lancashire',19,'2017-07-22 19:19:43','test','2017-07-22 19:19:46','test'),(26,'Winnipeg',5,'2017-07-23 22:38:07','test','2017-07-23 22:38:09','test'),(27,'thornton',5,'2017-07-28 02:19:01','test','2017-07-28 02:19:04','test'),(28,'Richmond',5,'2017-07-28 22:54:56','mat greten','2017-07-28 22:54:59','mat greten'),(29,'La Paz',2,'2017-07-28 22:57:20','mat greten','2017-07-28 22:57:24','mat greten'),(30,'Rochester',5,'2017-07-28 23:29:15','mat greten','2017-07-28 23:29:18','mat greten'),(31,'Hanover Park',5,'2017-07-29 16:09:28','test','2017-07-29 16:09:31','test'),(32,'Wallace',25,'2017-07-29 16:12:58','test','2017-07-29 16:13:01','test'),(33,'Seattle',5,'2017-08-01 19:09:19','test','2017-08-01 19:09:23','test');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `countryId` int(10) NOT NULL,
  `country` varchar(50) NOT NULL,
  `createDate` datetime NOT NULL,
  `createdBy` varchar(40) NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateBy` varchar(40) NOT NULL,
  PRIMARY KEY (`countryId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` (`countryId`, `country`, `createDate`, `createdBy`, `lastUpdate`, `lastUpdateBy`) VALUES (1,'Canada','2017-06-16 00:07:31','Test','2017-06-16 00:07:33','Test'),(2,'Mexico','2017-06-16 00:07:38','Test','2017-06-16 00:07:40','Test'),(3,'Japan','2017-06-16 00:07:44','Test','2017-06-16 00:07:46','Test'),(4,'Spain','2017-06-16 00:07:49','Test','2017-06-16 00:07:51','Test'),(5,'USA','2017-06-16 00:08:34','Test','2017-06-16 00:08:36','Test'),(6,'Hoth','2017-06-16 00:08:49','Test','2017-06-16 00:08:51','Test'),(7,'Arrachus','2017-06-16 00:09:09','Test','2017-06-16 00:09:11','Test'),(8,'Endor','2017-06-16 00:14:49','Test','2017-06-16 00:14:51','Test'),(9,'Rhyloth','2017-06-16 00:17:02','Test','2017-06-16 00:17:04','Test'),(10,'Courascant','2017-06-16 00:50:18','Test','2017-06-16 00:50:20','Test'),(11,'Poland','2017-06-16 01:52:24','Test','2017-06-16 01:52:26','Test'),(12,'NNNMNMN','2017-06-16 01:53:35','Test','2017-06-16 01:53:37','Test'),(13,'gagagag','2017-06-16 01:55:53','Test','2017-06-16 01:55:56','Test'),(14,'Blah Blah Blah','2017-06-16 03:09:02','Test','2017-06-16 03:09:04','Test'),(15,'Tatooine','2017-06-17 18:56:55','Test','2017-06-17 18:56:57','Test'),(16,'Outer Rim','2017-06-17 19:05:03','Test','2017-06-17 19:05:05','Test'),(17,'Unknown','2017-06-17 19:26:20','Test','2017-06-17 19:26:22','Test'),(18,'Imperial Navy','2017-06-23 03:08:53','Darh Maul','2017-06-23 03:08:56','Darh Maul'),(19,'England','2017-07-04 00:17:23','Darh Maul','2017-07-04 00:17:28','Darh Maul'),(20,'Great Britain','2017-07-04 00:24:48','Darh Maul','2017-07-04 00:24:53','Darh Maul'),(21,'Sweden','2017-07-04 00:27:23','Darh Maul','2017-07-04 00:27:27','Darh Maul'),(22,'EndGame','2017-07-04 00:36:44','Darh Maul','2017-07-04 00:36:48','Darh Maul'),(23,'f','2017-07-14 00:50:17','Darh Maul','2017-07-14 00:50:18','Darh Maul'),(24,'2','2017-07-14 00:53:03','Darh Maul','2017-07-14 00:53:05','Darh Maul'),(25,'Australia','2017-07-29 16:12:56','test','2017-07-29 16:12:59','test');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customerId` int(10) NOT NULL,
  `customerName` varchar(45) NOT NULL,
  `addressId` int(10) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `createDate` datetime NOT NULL,
  `createdBy` varchar(40) NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateBy` varchar(40) NOT NULL,
  PRIMARY KEY (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`customerId`, `customerName`, `addressId`, `active`, `createDate`, `createdBy`, `lastUpdate`, `lastUpdateBy`) VALUES (1,'Wampa',18,1,'2017-06-18 17:38:54','Darh Maul','2017-07-29 15:54:14','test'),(2,'Boba Fett',2,1,'2017-06-23 02:19:30','Darh Maul','2017-06-23 02:19:33','Darh Maul'),(3,'Mitth\'raw\'nuruodo',3,1,'2017-06-23 03:08:57','Darh Maul','2017-06-23 03:09:00','Darh Maul'),(4,'Matt Belamy',4,1,'2017-07-04 00:24:52','Darh Maul','2017-07-04 00:24:56','Darh Maul'),(5,'Billy Corgan',5,0,'2017-07-04 00:26:10','Darh Maul','2017-07-11 01:48:32','Darh Maul'),(6,'Mats Valk0',6,0,'2017-07-04 00:27:26','Darh Maul','2017-07-14 00:31:24','Darh Maul'),(7,'Silvester The Cat',12,0,'2017-07-14 00:50:20','Darh Maul','2017-07-29 15:56:39','test'),(8,'El Bartman',10,1,'2017-07-14 00:53:07','Darh Maul','2017-07-14 02:25:21','Darh Maul'),(9,'Rick Astley',11,1,'2017-07-22 19:19:46','test','2017-07-22 19:19:48','test'),(10,'Allost Macarkeys',13,1,'2017-07-27 00:15:08','test','2017-07-27 00:15:11','test'),(12,'Aaron Weiss',15,1,'2017-07-28 22:54:59','mat greten','2017-07-28 22:55:02','mat greten'),(13,'Aaron WeissToo',17,1,'2017-07-28 23:29:17','mat greten','2017-07-29 16:00:08','test'),(14,'New Customer Test',19,1,'2017-07-29 16:09:29','test','2017-07-29 16:09:33','test'),(15,'Get That Primary Key Pwease',20,1,'2017-07-29 16:12:59','test','2017-07-29 16:13:03','test'),(16,'Bill Smith',21,1,'2017-08-01 19:09:23','test','2017-08-01 19:09:27','test');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incrementtypes`
--

DROP TABLE IF EXISTS `incrementtypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incrementtypes` (
  `incrementTypeId` int(11) NOT NULL,
  `incrementTypeDescription` varchar(45) NOT NULL,
  PRIMARY KEY (`incrementTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incrementtypes`
--

LOCK TABLES `incrementtypes` WRITE;
/*!40000 ALTER TABLE `incrementtypes` DISABLE KEYS */;
/*!40000 ALTER TABLE `incrementtypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reminder`
--

DROP TABLE IF EXISTS `reminder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reminder` (
  `reminderId` int(10) NOT NULL,
  `reminderDate` datetime NOT NULL,
  `snoozeIncrement` int(11) NOT NULL,
  `snoozeIncrementTypeId` int(11) NOT NULL,
  `appointmentId` int(10) NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `createdDate` datetime NOT NULL,
  `remindercol` varchar(45) NOT NULL,
  PRIMARY KEY (`reminderId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reminder`
--

LOCK TABLES `reminder` WRITE;
/*!40000 ALTER TABLE `reminder` DISABLE KEYS */;
INSERT INTO `reminder` (`reminderId`, `reminderDate`, `snoozeIncrement`, `snoozeIncrementTypeId`, `appointmentId`, `createdBy`, `createdDate`, `remindercol`) VALUES (1,'2017-07-01 17:45:00',0,0,26,'Darh Maul','2017-07-18 03:26:14','0'),(2,'2017-07-26 21:06:00',0,0,27,'Darh Maul','2017-07-18 03:37:02','0'),(3,'2017-07-22 15:42:00',0,0,28,'Darh Maul','2017-07-22 15:43:43','0'),(4,'2017-07-27 17:45:00',0,0,29,'test','2017-07-27 00:16:44','0'),(5,'2023-07-06 14:57:00',0,0,30,'test','2017-07-28 01:23:46','0'),(6,'2017-05-15 21:45:00',0,0,31,'test','2017-07-28 02:16:16','0'),(7,'2017-07-28 17:45:00',0,0,-1,'test','2017-07-28 22:36:33','0'),(8,'2017-06-04 19:56:00',0,0,32,'test','2017-07-28 22:43:32','0'),(9,'2017-07-02 17:58:00',0,0,33,'mat greten','2017-07-28 23:31:21','0'),(10,'2017-07-29 18:11:00',0,0,34,'test','2017-07-29 15:20:36','0'),(11,'2017-07-29 17:45:00',0,0,35,'mat greten','2017-07-29 15:51:31','0'),(12,'2017-07-31 16:56:00',0,0,36,'test','2017-07-29 16:10:14','0'),(13,'2017-07-29 22:30:00',0,0,37,'test','2017-07-30 02:36:24','0'),(14,'2017-08-02 16:45:00',0,0,38,'test','2017-08-01 19:10:39','0');
/*!40000 ALTER TABLE `reminder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `active` tinyint(4) NOT NULL,
  `createBy` varchar(40) NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdatedBy` varchar(50) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`userId`, `userName`, `password`, `active`, `createBy`, `createDate`, `lastUpdate`, `lastUpdatedBy`) VALUES (1,'test','test',1,'','0000-00-00 00:00:00','2017-06-02 02:22:09',''),(2,'Mat Greten','endgame',1,'test','0000-00-00 00:00:00','2017-06-06 00:56:10','');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-01 16:43:50
