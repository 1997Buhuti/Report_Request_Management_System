-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: pb_app_data
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `projects_tbl`
--

DROP TABLE IF EXISTS `projects_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects_tbl` (
  `project_id` varchar(10) NOT NULL,
  `project_name` varchar(50) NOT NULL,
  `department_name` varchar(80) DEFAULT NULL,
  `branch_name` varchar(80) DEFAULT NULL,
  `regionName` varchar(80) DEFAULT NULL,
  `Remarks` varchar(80) DEFAULT NULL,
  `task_details` varchar(100) DEFAULT NULL,
  `recieved_date` date NOT NULL,
  `created_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `developer_name` varchar(50) DEFAULT NULL,
  `developer_id` varchar(10) DEFAULT NULL,
  `request_date` date DEFAULT NULL,
  `completion_date` date DEFAULT NULL,
  `CurrentStatus` varchar(50) NOT NULL,
  `Orgnization_Type` varchar(45) DEFAULT NULL,
  `Orgnization_Name` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `developer_id` (`developer_id`),
  CONSTRAINT `developer_id_fk` FOREIGN KEY (`developer_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects_tbl`
--

LOCK TABLES `projects_tbl` WRITE;
/*!40000 ALTER TABLE `projects_tbl` DISABLE KEYS */;
INSERT INTO `projects_tbl` VALUES ('Isue10','newProject',NULL,NULL,NULL,'Nope../','','2021-12-25','2021-12-27','2021-12-27','Isuru','Dev5d7','2021-12-25','2021-12-31','Ongoing','Department','lOANS'),('proj-78','Proj_Name19','Loans Dept','Kalutara Branch',NULL,'Give me a report34','','2012-12-25','2021-07-27','2021-09-12','Mana','Dev003','2021-07-27','2021-09-08','Pending','Branch','Gampaha'),('proj-87','Proj_Name7','Pawning Dept','Negombo Branch',NULL,'You have to submit this withn a week','','2012-12-25','2021-09-12','2021-07-27','Unassigned','Dev003','2021-09-12','2021-07-27','Pending','Region','Colombo'),('Proj033','Last attempt',NULL,NULL,NULL,'asdadsfwerrw','','2021-12-25','2021-12-23','2021-12-24','Unassigned','Dev001','2021-12-23','2021-12-29','Pending','Branch','Galle'),('Proj07e','project Porject',NULL,NULL,NULL,'Noooope.....','','2021-12-21','2021-12-21','2021-12-21','Unassigned','dev03','2021-12-21','2021-12-23','Pending','Region','Kandy'),('Proj141','this is testing',NULL,NULL,NULL,'zczvsfsdfsdadasdqerwrtwetw','','2021-12-21','2021-12-21','2021-12-21','Not Assigned','Dev742','2021-12-21','2021-12-25','Pending','Region','Matara'),('Proj176','Final Project',NULL,NULL,NULL,'Text','','2021-12-20','2021-12-23','2021-12-23','frontEnd','dev03','2021-12-20','2021-12-26','Ongoing','Branch','Bandarawela'),('Proj18f','Last Test',NULL,NULL,NULL,'This is only a test','','2021-12-25','2021-12-21','2021-12-26','Manakal','Dev001','2021-12-25','2021-12-27','Pending','Department','HumanResource'),('Proj2ad','Node Project',NULL,NULL,NULL,'Nothing...','','2021-12-21','2021-12-23','2021-12-24','Buhuti','Dev71b','2021-12-23',NULL,'Pending','Region','Kandy'),('Proj40d','This is dry run','Loans','Badulla',NULL,'None.. None','','2021-12-11','2021-12-16','2021-12-11','Unassigned','Dev001','2021-12-16','2021-12-12','Pending','Department','Savings'),('Proj417','complete Project',NULL,NULL,NULL,'adadnasjkdbaksjdbaksd','','2021-12-20','2021-12-23','2021-12-21','frontEnd','dev03','2021-12-20',NULL,'Dev742','Department','HumanResource'),('Proj48a','DdebuggProj',NULL,NULL,NULL,'adsdasdasdasdasd','','2021-12-21','2021-12-23','2021-12-22','frontEnd','dev03','2021-12-21',NULL,'Pending','Department','Savings'),('Proj525','Test Project 01',NULL,NULL,NULL,'These are some dummy data only.','','2021-12-21','2021-12-20','2021-12-21','DevOps','Dev742','2021-12-20','2021-12-24','Pending','Branch','Gampaha'),('Proj553','Testing again','Loans Department','Negombo',NULL,'These are the remarks','','2021-11-24','2021-11-24','2021-11-25','Unassigned','Dev71b','2021-11-24','2021-11-27','Pending','Department','lOANS'),('Proj569','porj 21201',NULL,NULL,NULL,'dadasdasdsdsdfdsf','','2021-12-11','2021-12-21','2021-12-14','DevOps','Dev742','2021-12-11','2021-12-13','Blocked','Region','Kandy'),('Proj592','Debug Project',NULL,NULL,NULL,'Testing......','','2021-12-20','2021-12-23','2021-12-22','frontEnd','dev03','2021-12-20',NULL,'Dev742','Region','Matara'),('Proj59a','Test Project','HR','Head Office',NULL,'You need to submit this quickly','','2021-11-20','2021-11-23','2021-11-22','frontEnd','dev03','2021-11-23','2021-11-22','Pending','Branch','Akkaraipattu'),('Proj5ee','debug',NULL,NULL,NULL,'dssfsdfsdfsdfsdfsd','','2021-12-23','2021-12-23','2021-12-24','Buhuti','Dev71b','2021-12-23','2021-12-28','Pending','Department','MIS'),('Proj674','Another Project',NULL,NULL,NULL,'This request is from Monargala','','2021-12-12','2021-12-17','2021-12-13','Manakal','Dev001','2021-12-17','2021-12-25','Pending','Branch','Galle'),('Proj77d','Test Project 2021',NULL,NULL,NULL,'Some words....','','2021-12-26','2021-12-23','2021-12-27','Manakal','Dev001','2021-12-26','2021-12-25','Ongoing','Branch','Galle'),('Proj837','project','Fire Department','Badulla',NULL,'Fire DepartmentFire Department','','2021-11-22','2021-11-23','2021-11-25','Manakal','Dev001','2021-11-23',NULL,'Pending','Department','HR'),('Proj8c7','LastProject',NULL,NULL,NULL,'None... Nothing to say.','','2021-12-21','2021-12-17','2021-12-21','Isuru','Dev5d7','2021-12-17','2021-12-24','Pending','Branch','Monaragala'),('Proj8eb','Manas Proj',NULL,NULL,NULL,'This is Mans Project','','2021-12-25','2021-12-21','2021-12-26','Not Assigned','Dev71b','2021-12-21','2021-12-31','Pending','Branch','Galle'),('Proj8fa','sfsdfsd',NULL,NULL,NULL,'sdfsdfsdfsd','','2021-12-20','2021-12-23','2021-12-21','Isuru','Dev5d7','2021-12-20',NULL,'Ongoing','Department','Savings'),('Proj981','Test Project','Loans','Colombo 02',NULL,'Nothing to say','','2021-11-23','2021-11-23','2021-11-25','Mana','Dev003','2021-11-23','2021-11-26','Pending','Region','Gampaha'),('Proja81','Project Almanac',NULL,NULL,NULL,'Nothing to say','','2021-12-20','2021-12-19','2021-12-20','Buhuti','Dev71b','2021-12-19','2021-12-23','Pending','Department','IT'),('Projac6','Project Debugg',NULL,NULL,NULL,'This is only a test','','2021-12-21','2021-12-23','2021-12-22','Manakal','Dev001','2021-12-21',NULL,'Ongoing','Branch','Monaragala2'),('Projb1b','TProject',NULL,NULL,NULL,'dadasdasdasd','','2021-12-22','2021-12-23','2021-12-23','Manakal','Dev001','2021-12-22',NULL,'Pending','Department','lOANS'),('Projb29','Node Project',NULL,NULL,NULL,'Name remarks','','2021-12-22','2021-12-23','2021-12-21','Manakal','Dev001','2021-12-22',NULL,'Ongoing','Region','Kandy'),('Projc3b','Final Request',NULL,NULL,NULL,'Some Words...','','2021-12-22','2021-12-23','2021-12-23','Manakal','Dev001','2021-12-23','2021-12-21','Pending','Branch','Baduraliya'),('Projccd','ProjectDebug2',NULL,NULL,NULL,'sdsfsdfsfsdf','','2021-12-20','2021-12-23','2021-12-23','frontEnd','dev03','2021-12-20',NULL,'Pending','Department','HumanResource'),('Projd2e','projah',NULL,NULL,NULL,'wewewewesdssdsddrrer3453','','2021-12-21','2021-12-21','2021-12-21','DevOps','Dev742','2021-12-21','2021-12-24','Ongoing','Branch','Bandarawela'),('Projdcd','Test Project232',NULL,NULL,NULL,'xdasdadasfdsf','','2021-12-23','2021-12-23','2021-12-22','DevOps','Dev742','2021-12-23',NULL,'Ongoing','Branch','Bandarawela'),('Projdd9','DPMan Project','Human Resource','Negombo',NULL,'Nothing to say....','','2021-12-17','2021-12-17','2021-12-18','Manakal','Dev001','2021-12-17','2021-12-19','Pending','Department','HumanResource'),('Projeeb','newProj','Savings','Negombo',NULL,'This Report is urgent deliver it withn a week','','2021-11-12','2021-11-22','2021-11-15','Not Assigned','Dev76f','2021-11-22','2021-11-15','Pending','Branch','Galle'),('Projf96','project arimac','Savings','Badulla',NULL,'Guess who\'s back again','','2021-11-22','2021-11-23','2021-11-23','Not Assigned','Dev5d7','2021-11-23','2021-12-06','Pending','Department','Savings');
/*!40000 ALTER TABLE `projects_tbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-03  9:38:08
