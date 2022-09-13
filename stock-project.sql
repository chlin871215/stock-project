-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: localhost    Database: stock
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `hcmio`
--

DROP TABLE IF EXISTS `hcmio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hcmio` (
  `TradeDate` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `BranchNo` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CustSeq` char(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DocSeq` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Stock` char(6) DEFAULT NULL,
  `BsType` char(1) DEFAULT NULL,
  `Price` decimal(10,4) DEFAULT NULL,
  `Qty` decimal(9,0) DEFAULT NULL,
  `Amt` decimal(16,2) DEFAULT NULL,
  `Fee` decimal(8,0) DEFAULT NULL,
  `Tax` decimal(8,0) DEFAULT NULL,
  `StinTax` decimal(8,0) DEFAULT NULL,
  `NetAmt` decimal(16,2) DEFAULT NULL,
  `ModDate` char(8) DEFAULT NULL,
  `ModTime` char(6) DEFAULT NULL,
  `ModUser` char(10) DEFAULT NULL,
  PRIMARY KEY (`TradeDate`,`BranchNo`,`CustSeq`,`DocSeq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hcmio`
--

LOCK TABLES `hcmio` WRITE;
/*!40000 ALTER TABLE `hcmio` DISABLE KEYS */;
INSERT INTO `hcmio` VALUES ('20220801','F62S','4','BB001','2357','B',282.0000,2000,564000.00,804,0,0,-564804.00,'20220801','114300','Berlin'),('20220805','F62S','4','AB001','1216','B',65.0000,1000,65000.00,93,0,0,-65093.00,'22020805','104000','Berlin'),('20220810','F62S','4','AB002','1218','S',30.0000,3000,90000.00,128,270,0,89602.00,'22020810','104700','Berlin'),('20220811','F62S','4','AB003','1234','S',33.6000,2000,67200.00,96,202,0,66902.00,'22020811','104900','Berlin'),('20220819','F62S','4','CB001','1203','B',33.4500,1000,33450.00,48,0,0,-33498.00,'20220819','153200','Berlin'),('20220819','F62S','4','CB002','1229','B',57.6000,3000,172800.00,246,0,0,-173046.00,'20220819','153300','Berlin'),('20220819','F62S','4','CB003','1217','B',9.8300,7000,68810.00,98,0,0,-68908.00,'20220819','153400','Berlin'),('20220819','F62S','4','CB004','2379','B',352.0000,10000,3520000.00,5016,0,0,-3525016.00,'20220819','153500','Berlin'),('20220819','F62S','4','CB005','2609','B',90.7000,5000,453500.00,646,0,0,-454146.00,'20220819','153600','Berlin'),('20220820','F62S','4','CB001','2376','B',80.6000,4000,322400.00,459,0,0,-322859.00,'20220801','114400','Berlin'),('20220830','F62S','00','ZZ111','2222','B',10.0000,2000,20000.00,29,0,0,-20029.00,'20220912','101254','Berlin'),('20220830','F62S','00','ZZ112','2222','B',10.0000,2000,20000.00,29,0,0,-20029.00,'20220912','101258','Berlin'),('20220830','F62S','00','ZZ113','2222','B',50.0000,2000,100000.00,143,0,0,-100143.00,'20220912','114249','Berlin'),('20220908','F62S','00','ZZA46','2222','B',10.0000,2000,20000.00,29,0,0,-20029.00,'20220913','145845','Berlin');
/*!40000 ALTER TABLE `hcmio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holiday`
--

DROP TABLE IF EXISTS `holiday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holiday` (
  `holiday` varchar(255) NOT NULL,
  PRIMARY KEY (`holiday`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holiday`
--

LOCK TABLES `holiday` WRITE;
/*!40000 ALTER TABLE `holiday` DISABLE KEYS */;
INSERT INTO `holiday` VALUES ('20211231'),('20220131'),('20220201'),('20220202'),('20220203'),('20220204'),('20220228'),('20220404'),('20220405'),('20220603'),('20220909'),('20221010'),('20230102');
/*!40000 ALTER TABLE `holiday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mstmb`
--

DROP TABLE IF EXISTS `mstmb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mstmb` (
  `Stock` char(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `StockName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `MarketType` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `CurPrice` decimal(10,4) DEFAULT '0.0000',
  `RefPrice` decimal(10,4) DEFAULT '0.0000',
  `Currency` char(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT 'NTD',
  `ModDate` char(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ModTime` char(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `ModUser` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`Stock`),
  KEY `IDX_MSTMB` (`Stock`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mstmb`
--

LOCK TABLES `mstmb` WRITE;
/*!40000 ALTER TABLE `mstmb` DISABLE KEYS */;
INSERT INTO `mstmb` VALUES ('1111','特斯拉','T',10.1100,20.0000,'NTW','20220903','150400','Berlin'),('2222','大蔥鴨','T',33.0000,20.0000,'NTW','20220907','132020','Berlin');
/*!40000 ALTER TABLE `mstmb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tcnud`
--

DROP TABLE IF EXISTS `tcnud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tcnud` (
  `TradeDate` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `BranchNo` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CustSeq` char(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DocSeq` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Stock` char(6) DEFAULT NULL,
  `Price` decimal(10,4) DEFAULT NULL,
  `Qty` decimal(9,0) DEFAULT NULL,
  `RemainQty` decimal(9,0) DEFAULT NULL,
  `Fee` decimal(8,0) DEFAULT NULL,
  `Cost` decimal(16,2) DEFAULT NULL,
  `ModDate` char(8) DEFAULT NULL,
  `ModTime` char(6) DEFAULT NULL,
  `ModUser` char(10) DEFAULT NULL,
  PRIMARY KEY (`TradeDate`,`BranchNo`,`CustSeq`,`DocSeq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tcnud`
--

LOCK TABLES `tcnud` WRITE;
/*!40000 ALTER TABLE `tcnud` DISABLE KEYS */;
INSERT INTO `tcnud` VALUES ('20220801','F62S','4','BB001','2357',282.0000,2000,2000,804,564804.00,'20220801','114300','Berlin'),('20220801','F62S','4','BB002','2376',80.6000,4000,4000,459,322859.00,'20220801','114400','Berlin'),('20220819','F62S','4','CB001','1203',33.4500,1000,1000,48,33498.00,'20220819','153200','Berlin'),('20220819','F62S','4','CB002','1229',57.6000,3000,3000,246,173046.00,'20220819','153300','Berlin'),('20220819','F62S','4','CB003','1217',9.8300,7000,7000,98,68908.00,'20220819','153400','Berlin'),('20220819','F62S','4','CB004','2379',352.0000,10000,10000,5016,3525016.00,'20220819','153500','Berlin'),('20220819','F62S','4','CB005','1217',90.7000,5000,5000,646,454146.00,'20220819','153600','Berlin'),('20220830','F62S','00','ZZ111','2222',10.0000,2000,2000,29,20029.00,'20220912','101254','Berlin'),('20220830','F62S','00','ZZ112','2222',10.0000,2000,2000,29,20029.00,'20220912','101258','Berlin'),('20220830','F62S','00','ZZ113','2222',50.0000,2000,2000,143,100143.00,'20220912','114249','Berlin'),('20220908','F62S','00','ZZA46','2222',10.0000,2000,2000,29,20029.00,'20220913','145845','Berlin');
/*!40000 ALTER TABLE `tcnud` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-13 15:22:41
