-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: sellingwidgets
-- ------------------------------------------------------
-- Server version	8.0.34

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

SET foreign_key_checks = 0;
--
-- Dumping data for table `applicant`
--

LOCK TABLES `applicant` WRITE;
/*!40000 ALTER TABLE `applicant` DISABLE KEYS */;
/*!40000 ALTER TABLE `applicant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES (1,'Year',1),(2,'Model',1),(3,'Color',1);
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
INSERT INTO `auction` VALUES (8,12.00,NULL,NULL,12.00,NULL),(20,49.00,NULL,NULL,49.00,NULL);
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `card_type`
--

LOCK TABLES `card_type` WRITE;
/*!40000 ALTER TABLE `card_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `card_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Vehicle Parts',1,NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `direct_deposit_details`
--

LOCK TABLES `direct_deposit_details` WRITE;
/*!40000 ALTER TABLE `direct_deposit_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `direct_deposit_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `fields`
--

LOCK TABLES `fields` WRITE;
/*!40000 ALTER TABLE `fields` DISABLE KEYS */;
/*!40000 ALTER TABLE `fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `friend_request`
--

LOCK TABLES `friend_request` WRITE;
/*!40000 ALTER TABLE `friend_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (23),(23);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `image_packet`
--

LOCK TABLES `image_packet` WRITE;
/*!40000 ALTER TABLE `image_packet` DISABLE KEYS */;
/*!40000 ALTER TABLE `image_packet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `item_condition`
--

LOCK TABLES `item_condition` WRITE;
/*!40000 ALTER TABLE `item_condition` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_condition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `market_listing`
--

LOCK TABLES `market_listing` WRITE;
/*!40000 ALTER TABLE `market_listing` DISABLE KEYS */;
INSERT INTO `market_listing` VALUES (7,12.00,'2hack_frog.jpg',_binary '\0',123.05,2,2,3),(19,49.00,'12jett-muffler-1.jpg',_binary '\0',123.23,1,12,15);
/*!40000 ALTER TABLE `market_listing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment_details`
--

LOCK TABLES `payment_details` WRITE;
/*!40000 ALTER TABLE `payment_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `paypal`
--

LOCK TABLES `paypal` WRITE;
/*!40000 ALTER TABLE `paypal` DISABLE KEYS */;
/*!40000 ALTER TABLE `paypal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `seller_rating`
--

LOCK TABLES `seller_rating` WRITE;
/*!40000 ALTER TABLE `seller_rating` DISABLE KEYS */;
INSERT INTO `seller_rating` VALUES (3,23,4.2),(13,12,3.1);
/*!40000 ALTER TABLE `seller_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shipping`
--

LOCK TABLES `shipping` WRITE;
/*!40000 ALTER TABLE `shipping` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shipping_address`
--

LOCK TABLES `shipping_address` WRITE;
/*!40000 ALTER TABLE `shipping_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipping_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sidebar`
--

LOCK TABLES `sidebar` WRITE;
/*!40000 ALTER TABLE `sidebar` DISABLE KEYS */;
/*!40000 ALTER TABLE `sidebar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `state_details`
--

LOCK TABLES `state_details` WRITE;
/*!40000 ALTER TABLE `state_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `state_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `statistics`
--

LOCK TABLES `statistics` WRITE;
/*!40000 ALTER TABLE `statistics` DISABLE KEYS */;
INSERT INTO `statistics` VALUES (1,2,'2023-09-12 15:34:27.347283','Account with username: userName was created',15,1),(2,3,'2023-09-12 17:32:21.003150','userName logged in',17,1),(11,2,'2023-09-12 17:36:05.198570','Account with username: sellerName was created',17,1),(14,3,'2023-09-12 17:36:44.043714','sellerName logged in',17,1);
/*!40000 ALTER TABLE `statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ticket_message`
--

LOCK TABLES `ticket_message` WRITE;
/*!40000 ALTER TABLE `ticket_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,200,_binary '\0',_binary '\0','US','2023-09-12',_binary '\0',_binary '\0',NULL,'dsm1015@sru.edu','zdtgdqdmyg',_binary '','Douglas','Maxwell','$2a$10$2q7Xu9R0xJ4KhEjpyrn2POWtmqeoZWNpcv80DeTSOW8aiCtXcx6vO','bpbeszzejr','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','blah','22hack_frog.jpg','joe','rock','beer','userName',NULL,NULL,NULL,NULL,3,NULL),(12,200,_binary '\0',_binary '\0','US','2023-09-12',_binary '\0',_binary '\0',NULL,'dmaxwell484@gmail.com','nfqxqinosm',_binary '','Joe','Mama','$2a$10$kW7AoKXVjUKH0sAlJwPkNOZr926oAkA5uNN9P.Za3xwombjydXAMG','dbmnnelmpm','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','seller','12planet_space_outer_space_144566_3840x2160.jpg','joe','rock','coffee','sellerName',NULL,NULL,NULL,NULL,13,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_list`
--

LOCK TABLES `user_list` WRITE;
/*!40000 ALTER TABLE `user_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget`
--

LOCK TABLES `widget` WRITE;
/*!40000 ALTER TABLE `widget` DISABLE KEYS */;
INSERT INTO `widget` VALUES (3,'dafad','1.4l Rear Exhaust Muffler 5qm253611 OE Fits VOLKSWAGEN JETTA 2019-2021',1),(15,'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don\'t look even slightly believable.','1.4l Rear Exhaust Muffler 5qm253611 OE Fits VOLKSWAGEN JETTA 2019-2021',1);
/*!40000 ALTER TABLE `widget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_attribute`
--

LOCK TABLES `widget_attribute` WRITE;
/*!40000 ALTER TABLE `widget_attribute` DISABLE KEYS */;
INSERT INTO `widget_attribute` VALUES (4,'Color','asdf',NULL,3),(5,'Year','sdf',NULL,3),(6,'Model','asdf',NULL,3),(16,'Year','2012',NULL,15),(17,'Color','yeller',NULL,15),(18,'Model','VW',NULL,15);
/*!40000 ALTER TABLE `widget_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_image`
--

LOCK TABLES `widget_image` WRITE;
/*!40000 ALTER TABLE `widget_image` DISABLE KEYS */;
INSERT INTO `widget_image` VALUES (9,'2hack_frog.jpg',7),(10,'2hack_frog.jpg',7),(21,'12jett-muffler-1.jpg',19),(22,'12jetta-muffler-2.jpg',19);
/*!40000 ALTER TABLE `widget_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_wishlist_entry`
--

LOCK TABLES `widget_wishlist_entry` WRITE;
/*!40000 ALTER TABLE `widget_wishlist_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `widget_wishlist_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widgets_info`
--

LOCK TABLES `widgets_info` WRITE;
/*!40000 ALTER TABLE `widgets_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `widgets_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-12 17:40:05

SET foreign_key_checks = 1;