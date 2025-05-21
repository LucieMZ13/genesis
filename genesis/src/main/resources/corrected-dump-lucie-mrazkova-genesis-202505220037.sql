-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: genesis
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) DEFAULT NULL,
  `person_id` varchar(100) NOT NULL,
  `uuid` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_unique` (`id`),
  UNIQUE KEY `users_unique_1` (`person_id`),
  UNIQUE KEY `users_unique_2` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'aaa','aaa','mY6sT1jA3cLz','1dd53476-c2c9-4680-9b71-1b587c2bd308'),(4,'xxx','xxx','cN1vZ8pE5sYx','49399bfb-ae92-48e0-90cf-c398cd077ad0'),(5,'eee','eee','jXa4g3H7oPq2','b66753da-1353-49c7-a410-17b0d07bc772'),(6,'fff','fff','tQdG2kP3mJfB','3ce71888-0399-4992-b25b-be69709e2ef0'),(7,'ggg','ggg','iM5sO6zXcW7v','6857a88d-7983-4708-a597-598f7796dfa1'),(8,'hhh','hhh','rU8nA9eT2bYh','ff1561bc-951f-40aa-ab53-be60351f7f1e'),(9,'iii','iii','wV6eH1fK7qZj','0faa73d7-8ab4-4834-a18a-7eccfc26530e'),(10,'jjj','jjj','sL4gN9dC3bXz','0db9ec13-3e98-4f44-b4a2-5996587595c9');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'genesis'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-22  0:37:26
