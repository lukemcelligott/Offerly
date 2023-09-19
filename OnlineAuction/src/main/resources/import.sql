-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: online-auction
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
INSERT INTO `auction` VALUES (15,122.00,NULL,NULL,122.00,NULL),(27,23.00,NULL,NULL,23.00,NULL);
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `card_type`
--

LOCK TABLES `card_type` WRITE;
/*!40000 ALTER TABLE `card_type` DISABLE KEYS */;
INSERT INTO `card_type` VALUES ('American Express'),('Discover'),('Mastercard'),('Visa');
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
INSERT INTO `hibernate_sequence` VALUES (1000);
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
INSERT INTO `market_listing` VALUES (14,122.00,'2jett-muffler-1.jpg',_binary '\0',134.00,1,2000,10),(26,23.00,'191backofWheel.jpg',_binary '\0',53.00,1,19,22);
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
<<<<<<< HEAD
INSERT INTO `seller_rating` VALUES (1000,23,4.5),(6,12,2.3),(20,11,3.2);
=======
INSERT INTO `seller_rating` VALUES (3,23,4.5),(1000,12,2.3),(20,11,3.2);
>>>>>>> refs/remotes/origin/Luke-Signup
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
INSERT INTO `state_details` VALUES ('Alabama',4.00),('Alaska',0.00),('Arizona',5.60),('Arkansas',6.50),('California',7.25),('Colorado',2.90),('Connecticut',6.35),('Delaware',0.00),('Florida',6.00),('Georgia',4.00),('Hawaii',4.00),('Idaho',6.00),('Illinois',6.25),('Indiana',7.00),('Iowa',6.00),('Kansas',6.50),('Kentucky',6.00),('Louisiana',4.45),('Maine',5.50),('Maryland',6.00),('Massachusetts',6.25),('Michigan',6.00),('Minnesota',6.88),('Mississippi',7.00),('Missouri',4.23),('Montana',0.00),('Nebraska',5.50),('Nevada',6.85),('New Hampshire',0.00),('New Jersey',6.63),('New Mexico',5.13),('New York',4.00),('North Carolina',4.75),('North Dakota',5.00),('Ohio',5.75),('Oklahoma',4.50),('Oregon',0.00),('Pennsylvania',6.00),('Rhode Island',7.00),('South Carolina',6.00),('South Dakota',4.50),('Tennessee',7.00),('Texas',6.25),('Utah',5.95),('Vermont',6.00),('Virginia',5.30),('Washington',6.50),('West Virginia',6.00),('Wisconsin',5.00),('Wyoming',4.00);
/*!40000 ALTER TABLE `state_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `statistics`
--

LOCK TABLES `statistics` WRITE;
/*!40000 ALTER TABLE `statistics` DISABLE KEYS */;
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
<<<<<<< HEAD
INSERT INTO `user` VALUES (2000,200,_binary '\0',_binary '\0','US','2023-09-14',_binary '\0',_binary '\0',NULL,'dsm1015@sru.edu','rnetagzcxh',_binary '','Douglas','Maxwell','$2a$10$S7hGuNYPhsbduUL9brRjMuQ2XCFo0Mt3ZC/d1wmmCjTZ6pmvVabea','utsimjqigw','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','yo','2hack_frog.jpg','joe','rock','coffee','userName',NULL,NULL,NULL,NULL,1000,NULL),(5,200,_binary '\0',_binary '\0','US','2023-09-14',_binary '\0',_binary '\0',NULL,'dmaxwell484@gmail.com','obvvxzmscc',_binary '\0','Tim','Baggins','$2a$10$Gvs7Jsie4aQljqmaewcwWeZlJxq4.GyFsCV1KscJ6StUlcB8u8xSa','hjwcislqpm','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','',NULL,'joe','rock','coffee','sellerName',NULL,NULL,NULL,NULL,6,NULL),(19,200,_binary '\0',_binary '\0','US','2023-09-14',_binary '\0',_binary '\0',NULL,'lpm1006@sru.edu','xovxysbesc',_binary '','Douglas','Maxwell','$2a$10$k/nyrp4bhWdk5u8rj8VHOuaAg3H04kXI9Bog2ISzonRuIgyZ1.MH2','gbepmbkplx','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','','191dollvatar.png','joe','rock','coffee','sellerName1',NULL,NULL,NULL,NULL,20,NULL);
=======
INSERT INTO `user` VALUES (2000,200,_binary '\0',_binary '\0','US','2023-09-14',_binary '\0',_binary '\0',NULL,'dsm1015@sru.edu','rnetagzcxh',_binary '','Douglas','Maxwell','$2a$10$S7hGuNYPhsbduUL9brRjMuQ2XCFo0Mt3ZC/d1wmmCjTZ6pmvVabea','utsimjqigw','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','yo','2hack_frog.jpg','joe','rock','coffee','userName',NULL,NULL,NULL,NULL,1000,NULL),(4000,200,_binary '\0',_binary '\0','US','2023-09-14',_binary '\0',_binary '\0',NULL,'dmaxwell484@gmail.com','obvvxzmscc',_binary '\0','Tim','Baggins','$2a$10$Gvs7Jsie4aQljqmaewcwWeZlJxq4.GyFsCV1KscJ6StUlcB8u8xSa','hjwcislqpm','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','',NULL,'joe','rock','coffee','sellerName',NULL,NULL,NULL,NULL,3000,NULL),(19,200,_binary '\0',_binary '\0','US','2023-09-14',_binary '\0',_binary '\0',NULL,'lpm1006@sru.edu','xovxysbesc',_binary '','Douglas','Maxwell','$2a$10$k/nyrp4bhWdk5u8rj8VHOuaAg3H04kXI9Bog2ISzonRuIgyZ1.MH2','gbepmbkplx','7244754080','ROLE_USER','What is your mother\'s maiden name?','What is your favorite band?','What is your favorite beverage?','','191dollvatar.png','joe','rock','coffee','sellerName1',NULL,NULL,NULL,NULL,20,NULL);
>>>>>>> refs/remotes/origin/Luke-Signup
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
INSERT INTO `widget` VALUES (10,'A little beat up but in pretty good shape.','1.4l Rear Exhaust Muffler Fits VOLKSWAGEN JETTA 2019-2021',1),(22,'black leather steering wheel. Slightly used.','14\" Billet Black Muscle Chevy 69-94 GM Steering Wheel',1);
/*!40000 ALTER TABLE `widget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_attribute`
--

LOCK TABLES `widget_attribute` WRITE;
/*!40000 ALTER TABLE `widget_attribute` DISABLE KEYS */;
INSERT INTO `widget_attribute` VALUES (11,'Year','2012',NULL,10),(12,'Model','VW',NULL,10),(13,'Color','sliver',NULL,10),(23,'Color','black',NULL,22),(24,'Model','Chevy',NULL,22),(25,'Year','2012',NULL,22);
/*!40000 ALTER TABLE `widget_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_image`
--

LOCK TABLES `widget_image` WRITE;
/*!40000 ALTER TABLE `widget_image` DISABLE KEYS */;
INSERT INTO `widget_image` VALUES (16,'2jett-muffler-1.jpg',14),(17,'2jetta-muffler-2.jpg',14),(28,'191backofWheel.jpg',26),(29,'191frontofWheel.jpg',26);
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