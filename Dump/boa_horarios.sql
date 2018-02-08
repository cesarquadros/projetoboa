CREATE DATABASE  IF NOT EXISTS `boa`
USE `boa`;

DROP TABLE IF EXISTS `horarios`;

CREATE TABLE `horarios` (
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `horario` time NOT NULL,
  PRIMARY KEY (`idHorario`),
  UNIQUE KEY `idHorario_UNIQUE` (`idHorario`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

LOCK TABLES `horarios` WRITE;

INSERT INTO `horarios` VALUES (1,'08:00:00'),(2,'09:00:00'),(3,'10:00:00'),(4,'11:00:00'),(5,'12:00:00'),(6,'13:00:00'),(7,'14:00:00'),(8,'15:00:00'),(9,'16:00:00'),(10,'17:00:00');