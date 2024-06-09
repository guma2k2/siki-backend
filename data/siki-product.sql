-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: siki-product
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
-- Table structure for table `base_product`
--

DROP TABLE IF EXISTS `base_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `base_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(128) NOT NULL,
  `slug` varchar(128) NOT NULL,
  `status` bit(1) NOT NULL,
  `store_id` int DEFAULT NULL,
  `brand_id` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ufnq8jkr4aovw94sebwcbm43` (`slug`),
  KEY `FK70041nkpq7e5fj7df76xpw7ty` (`brand_id`),
  KEY `FKcxgqv1ue2ysxyj2uahp5fpma3` (`category_id`),
  CONSTRAINT `FK70041nkpq7e5fj7df76xpw7ty` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FKcxgqv1ue2ysxyj2uahp5fpma3` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_product`
--

LOCK TABLES `base_product` WRITE;
/*!40000 ALTER TABLE `base_product` DISABLE KEYS */;
INSERT INTO `base_product` VALUES (2,'desc','samsung','sam-sung-123211',_binary '\0',1,1,2),(3,'desc','Áo Khoác Dù Nam chống nước ( TẶNG QUẦN LÓT HÀNG HIỆU ) Dokafashion Cao Cấp 2 Lớp Vải Dù Ấm Áp Cản Gió Đi Nắng, Chống Nước','o-kho-c-d-nam-ch-ng-n-c-t-ng-qu-n-l-t-h-ng-hi-u-dokafashion-cao-c-p-2-l-p-v-i-d-m-p-c-n-gi-i-n-ng-ch-ng-n-c',_binary '\0',1,20,3),(4,'desc','Áo thun nam polo Oscar kẻ sọc xanh, chất liệu cotton thoáng mát, hút mồ hôi tốt - OCMPKS 010','o-thun-nam-polo-oscar-k-s-c-xanh-ch-t-li-u-cotton-tho-ng-m-t-h-t-m-h-i-t-t-ocmpks-010',_binary '\0',1,19,3),(5,'desc','Áo Thun Nam Polo OSCAR - OCMPKSF 003','o-thun-nam-polo-oscar-ocmpksf-003',_binary '\0',1,17,3),(6,'desc','Viettien - Áo thun nam có cổ màu nâu sáng 6M3136 regular','viettien-o-thun-nam-c-c-m-u-n-u-s-ng-6m3136-regular',_binary '\0',1,14,3),(7,'desc','Áo thun Polo nam Novelty 200034N','o-thun-polo-nam-novelty-200034n',_binary '\0',1,13,3),(8,'desc','Áo thun Polo nam Novelty cổ bo thun họa tiết NATMINMCLR210082N','o-thun-polo-nam-novelty-c-bo-thun-h-a-ti-t-natminmclr210082n',_binary '\0',1,12,3),(9,'desc','Áo Thun Nam Ngắn Tay Thương Hiệu Chandi, Chất Thun Cotton Xịn Mẫu Mới NH20','o-thun-nam-ng-n-tay-th-ng-hi-u-chandi-ch-t-thun-cotton-x-n-m-u-m-i-nh20',_binary '\0',1,11,3),(10,'desc','Áo thun Polo nam Novelty 200029N','o-thun-polo-nam-novelty-200029n',_binary '\0',1,9,3),(11,'desc','Áo thun Nữ croptop cổ tròn tay ngắn 7 màu cotton lạnh chất đẹp siêu co giãn chống bai xù','o-thun-n-croptop-c-tr-n-tay-ng-n-7-m-u-cotton-l-nh-ch-t-p-si-u-co-gi-n-ch-ng-bai-x-',_binary '\0',1,4,4);
/*!40000 ALTER TABLE `base_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rdxh7tq2xs66r485cc8dkxt77` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'samsung.com','Samsung'),(4,'','Apple'),(5,'','VONESA'),(6,'','NIU24'),(7,'','Aligro'),(8,'','Novelty'),(9,'','GOKING'),(10,'','Titishop'),(11,'','Chandi'),(12,'','Amazing'),(13,'','Doka'),(14,'','Viettien'),(15,'','Puma'),(16,'','Vĩnh Tiến'),(17,'','LADOS'),(18,'','Atlan'),(19,'','ArcticHunter'),(20,'','dokafashion');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(60) NOT NULL,
  `status` bit(1) NOT NULL,
  `parent_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`),
  KEY `FK2y94svpmqttx80mshyny85wqr` (`parent_id`),
  CONSTRAINT `FK2y94svpmqttx80mshyny85wqr` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'desc','https://res.cloudinary.com/di6h4mtfa/image/upload/v1717756457/78213b30-7947-40dc-be1b-a2abfda0a0ec.webp','Clothing',_binary '',NULL),(2,'desc','https://res.cloudinary.com/di6h4mtfa/image/upload/v1717756457/78213b30-7947-40dc-be1b-a2abfda0a0ec.webp','Cloth',_binary '',1),(3,'desc','https://res.cloudinary.com/di6h4mtfa/image/upload/v1717756457/78213b30-7947-40dc-be1b-a2abfda0a0ec.webp','Thời trang nam',_binary '',NULL),(4,'desc','https://res.cloudinary.com/di6h4mtfa/image/upload/v1717756508/ae9f2583-6006-40af-a8b6-68e614ba583d.webp','Thời trang nữ',_binary '',NULL),(5,'desc','https://res.cloudinary.com/di6h4mtfa/image/upload/v1717756540/ecfdeedc-9b56-4651-9120-41f031a8bfef.webp','Nhà sách Tiki',_binary '',NULL),(6,'desc','https://res.cloudinary.com/di6h4mtfa/image/upload/v1717756572/648f687e-369c-41ef-9bb6-c1c35ed2c522.webp','Nhà cửa & Đời sống',_binary '',NULL),(7,'desc','https://res.cloudinary.com/di6h4mtfa/image/upload/v1717756604/faf022e4-f68a-4e07-b0ad-2f3407a6cffd.webp','Điện Thoại - Máy Tính Bảng',_binary '',NULL),(8,'desc','https://res.cloudinary.com/di6h4mtfa/image/upload/v1717756631/0a8b62b2-b811-47c1-9f9c-826e53dde62f.webp','Đồ Chơi - Mẹ & Bé',_binary '',NULL),(9,'desc','https://res.cloudinary.com/di6h4mtfa/image/upload/v1717756653/258090de-054a-4610-b521-9e1022d4196a.webp','Thiết Bị Số - Phụ Kiện Số',_binary '',NULL),(10,'desc','https://res.cloudinary.com/di6h4mtfa/image/upload/v1717756676/efdd3909-3bf3-47ea-bd46-1c947167150f.webp','Làm Đẹp - Sức Khỏe',_binary '',NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_default` bit(1) NOT NULL,
  `price` double DEFAULT NULL,
  `quantity` int NOT NULL,
  `status` bit(1) NOT NULL,
  `base_product_id` bigint DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5albbeksfq6jew5almy6c9bxj` (`base_product_id`),
  CONSTRAINT `FK5albbeksfq6jew5almy6c9bxj` FOREIGN KEY (`base_product_id`) REFERENCES `base_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (5,_binary '\0',100000,100,_binary '',2,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(6,_binary '\0',250000,100,_binary '',2,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(7,_binary '\0',190000,100,_binary '',2,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(8,_binary '\0',170000,100,_binary '',2,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(9,_binary '',147000,100,_binary '',3,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(10,_binary '\0',147000,100,_binary '',3,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(11,_binary '\0',147000,100,_binary '',3,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(12,_binary '\0',147000,100,_binary '',3,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(13,_binary '\0',147000,100,_binary '',3,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(14,_binary '\0',147000,100,_binary '',3,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(15,_binary '\0',147000,100,_binary '',3,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(16,_binary '\0',147000,100,_binary '',3,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(17,_binary '',445000,100,_binary '',4,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(18,_binary '',445000,100,_binary '',5,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(19,_binary '\0',445000,100,_binary '',5,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(20,_binary '',445000,100,_binary '',6,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(21,_binary '',239400,100,_binary '',7,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(22,_binary '',319200,100,_binary '',8,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(23,_binary '',220000,100,_binary '',9,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(24,_binary '\0',220000,100,_binary '',9,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(25,_binary '\0',220000,100,_binary '',9,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(26,_binary '',239400,100,_binary '',10,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(27,_binary '\0',238400,100,_binary '',10,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(28,_binary '\0',240400,100,_binary '',10,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(29,_binary '\0',241400,100,_binary '',10,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(30,_binary '',84000,100,_binary '',11,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1717844525/af8a00b7-d7f2-4032-974e-f128e8f86654.webp'),(31,_binary '\0',84000,100,_binary '',11,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1717844525/af8a00b7-d7f2-4032-974e-f128e8f86654.webp'),(32,_binary '\0',84000,100,_binary '',11,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1717844525/af8a00b7-d7f2-4032-974e-f128e8f86654.webp'),(33,_binary '\0',84000,100,_binary '',11,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1717844525/af8a00b7-d7f2-4032-974e-f128e8f86654.webp');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_attribute`
--

DROP TABLE IF EXISTS `product_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `base_product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKotbk51uell6s7yqrbm6uf307e` (`base_product_id`),
  CONSTRAINT `FKotbk51uell6s7yqrbm6uf307e` FOREIGN KEY (`base_product_id`) REFERENCES `base_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_attribute`
--

LOCK TABLES `product_attribute` WRITE;
/*!40000 ALTER TABLE `product_attribute` DISABLE KEYS */;
INSERT INTO `product_attribute` VALUES (1,'color',2),(2,'size',2),(3,'Màu',3),(4,'Kích cỡ',3),(5,'Kích cỡ',4),(6,'Kích cỡ',5),(7,'Nhóm màu',5),(8,'Kích cỡ',6),(9,'Màu sắc',6),(10,'Kích cỡ',7),(11,'Kích cỡ',8),(12,'Kích cỡ',9),(13,'Màu sắc',9),(14,'Kích cỡ',10),(15,'Kích cỡ',11),(16,'Màu sắc',11);
/*!40000 ALTER TABLE `product_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_attribute_value`
--

DROP TABLE IF EXISTS `product_attribute_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_attribute_value` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `product_attribute_id` bigint DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqgk2xbdl46wt0h9i5uheps5ke` (`product_attribute_id`),
  CONSTRAINT `FKqgk2xbdl46wt0h9i5uheps5ke` FOREIGN KEY (`product_attribute_id`) REFERENCES `product_attribute` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_attribute_value`
--

LOCK TABLES `product_attribute_value` WRITE;
/*!40000 ALTER TABLE `product_attribute_value` DISABLE KEYS */;
INSERT INTO `product_attribute_value` VALUES (1,'red',1,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(2,'blue',1,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(3,'s',2,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(4,'m',2,'http://res.cloudinary.com/di6h4mtfa/image/upload/v1712552138/cd4a86bd-f318-4f92-817f-341c7fbb7c71.png'),(5,'Xanh đen',3,''),(6,'Đen',3,''),(7,'L',4,''),(8,'M',4,''),(9,'XL',4,''),(10,'XXL',4,''),(11,'S/38',5,''),(12,'S',6,''),(13,'XXL',6,''),(14,'Blue',7,''),(15,'L',8,''),(16,'Nâu sáng',9,''),(17,'S',10,''),(18,'S',11,''),(19,'L',12,''),(20,'M',12,''),(21,'XL',12,''),(22,'Trắng',13,''),(23,'L',14,''),(24,'M',14,''),(25,'L',14,''),(26,'XL',14,''),(27,'L: (56kg - 62kg)',15,''),(28,'M: (49kg - 55kg)',15,''),(29,'S: (42kg - 48kg)',15,''),(30,'XL: (63kg - 69kg)',15,''),(31,'Đỏ đô',16,'');
/*!40000 ALTER TABLE `product_attribute_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_default` bit(1) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6oo0cvcdtb6qmwsga468uuukk` (`product_id`),
  CONSTRAINT `FK6oo0cvcdtb6qmwsga468uuukk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_variation`
--

DROP TABLE IF EXISTS `product_variation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_variation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `product_attribute_value_id` bigint DEFAULT NULL,
  `is_default` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpryf02se86hpv5v7xn5afye4v` (`product_id`),
  KEY `FKrlb3oiijblbat3mgiqptn6pvg` (`product_attribute_value_id`),
  CONSTRAINT `FKpryf02se86hpv5v7xn5afye4v` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKrlb3oiijblbat3mgiqptn6pvg` FOREIGN KEY (`product_attribute_value_id`) REFERENCES `product_attribute_value` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_variation`
--

LOCK TABLES `product_variation` WRITE;
/*!40000 ALTER TABLE `product_variation` DISABLE KEYS */;
INSERT INTO `product_variation` VALUES (9,5,1,_binary '\0'),(10,5,3,_binary '\0'),(11,6,2,_binary '\0'),(12,6,3,_binary '\0'),(13,7,1,_binary '\0'),(14,7,4,_binary '\0'),(15,8,2,_binary '\0'),(16,8,4,_binary '\0'),(17,9,5,_binary '\0'),(18,9,7,_binary '\0'),(19,10,5,_binary '\0'),(20,10,8,_binary '\0'),(21,11,5,_binary '\0'),(22,11,9,_binary '\0'),(23,12,5,_binary '\0'),(24,12,10,_binary '\0'),(25,13,6,_binary '\0'),(26,13,7,_binary '\0'),(27,14,6,_binary '\0'),(28,14,8,_binary '\0'),(29,15,6,_binary '\0'),(30,15,9,_binary '\0'),(31,16,6,_binary '\0'),(32,16,10,_binary '\0'),(33,17,11,_binary '\0'),(34,18,14,_binary '\0'),(35,18,12,_binary '\0'),(36,19,14,_binary '\0'),(37,19,13,_binary '\0'),(38,20,15,_binary '\0'),(39,20,16,_binary '\0'),(40,21,17,_binary '\0'),(41,22,18,_binary '\0'),(42,23,22,_binary '\0'),(43,23,19,_binary '\0'),(44,24,22,_binary '\0'),(45,24,20,_binary '\0'),(46,25,22,_binary '\0'),(47,25,21,_binary '\0'),(48,26,23,_binary '\0'),(49,27,24,_binary '\0'),(50,28,25,_binary '\0'),(51,29,26,_binary '\0'),(52,30,31,_binary '\0'),(53,30,27,_binary '\0'),(54,31,31,_binary '\0'),(55,31,28,_binary '\0'),(56,32,31,_binary '\0'),(57,32,29,_binary '\0'),(58,33,31,_binary '\0'),(59,33,30,_binary '\0');
/*!40000 ALTER TABLE `product_variation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  `rating_star` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiyof1sindb9qiqr9o8npj8klt` (`product_id`),
  CONSTRAINT `FKiyof1sindb9qiqr9o8npj8klt` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'chat luong','2024-06-03 10:39:01.909693','30517042-d493-4602-b5ca-4dd18b1faaf6',4,NULL,5),(2,'chat luong','2024-06-03 10:40:01.909693','30517042-d493-4602-b5ca-4dd18b1faaf6',5,NULL,6);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-09 10:10:58
