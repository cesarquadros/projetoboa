CREATE DATABASE  IF NOT EXISTS `boa` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `boa`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: boa
-- ------------------------------------------------------
-- Server version	5.7.20-log
--
-- Table structure for table `agendamento`
--

DROP TABLE IF EXISTS `agendamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agendamento` (
  `idAgendamento` int(11) NOT NULL AUTO_INCREMENT,
  `id_horario` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_sala` int(11) NOT NULL,
  `dt_agendamento` date NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idAgendamento`),
  UNIQUE KEY `idAgendamento_UNIQUE` (`idAgendamento`),
  KEY `id_horario_idx` (`id_horario`),
  KEY `id_cliente_idx` (`id_cliente`),
  KEY `id_sala_idx` (`id_sala`),
  CONSTRAINT `id_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_horario` FOREIGN KEY (`id_horario`) REFERENCES `horarios` (`idHorario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_sala` FOREIGN KEY (`id_sala`) REFERENCES `sala` (`idSala`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agendamento`
--

LOCK TABLES `agendamento` WRITE;
/*!40000 ALTER TABLE `agendamento` DISABLE KEYS */;
INSERT INTO `agendamento` VALUES (3,1,1,1,'2018-02-07',1);
/*!40000 ALTER TABLE `agendamento` ENABLE KEYS */;
UNLOCK TABLES;