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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` varchar(10) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(128) NOT NULL,
  `access_level` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Dev000','frontEnd','01cfcd4f6b8770febfb40cb906715822','1'),('Dev001','Manakal','5d41402abc4b2a76b9719d911017c592','1'),('Dev002','new User','321','2'),('Dev003','Mana','e916733534c777ebabc7c9fe0c781ad1','3'),('Dev004','test','4321','2'),('Dev005','test','321','3'),('dev01','Test Dev','Test Pw','1'),('dev03','Test Developer','Test Pw2','2'),('Dev186','mana','e916733534c777ebabc7c9fe0c781ad1','1'),('Dev19b','Buhuti Manakal','ee11cbb19052e40b07aac0ca060c23ee','1'),('Dev5d7','Isuru','05a5cf06982ba7892ed2a6d38fe832d6','1'),('Dev71b','Buhuti','02fcc64668dd1b6a85e60ac9797f2dc7','1'),('Dev742','DevOps','0d7504aa7ee1ec8e2d6b0e58f78f9535','1'),('Dev767','newUser','031366bebf535a266cf2fe84e350f420','1'),('Dev76f','newUser','02fcc64668dd1b6a85e60ac9797f2dc7','3'),('Dev9d5','User','8f9bfe9d1345237cb3b2b205864da075','1'),('Devc1e','encrypted','0d7504aa7ee1ec8e2d6b0e58f78f9535','1');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
