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
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute_recommendation` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
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
-- Dumping data for table `market_listing`
--

LOCK TABLES `market_listing` WRITE;
/*!40000 ALTER TABLE `market_listing` DISABLE KEYS */;
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
-- Dumping data for table `social_friend_request`
--

LOCK TABLES `social_friend_request` WRITE;
/*!40000 ALTER TABLE `social_friend_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_friend_request` ENABLE KEYS */;
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
/*!40000 ALTER TABLE `widget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_attribute`
--

LOCK TABLES `widget_attribute` WRITE;
/*!40000 ALTER TABLE `widget_attribute` DISABLE KEYS */;
 /*!40000 ALTER TABLE `widget_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_image`
--

LOCK TABLES `widget_image` WRITE;
/*!40000 ALTER TABLE `widget_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `widget_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `widget_wishlist_entry`
--

LOCK TABLES `widget_wishlist_entry` WRITE;
/*!40000 ALTER TABLE `widget_wishlist_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `widget_wishlist_entry` ENABLE KEYS */;
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