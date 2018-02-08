CREATE DATABASE  IF NOT EXISTS `boa`
USE `boa`;

DROP TABLE IF EXISTS `sala`;

CREATE TABLE `sala` (
  `idSala` int(11) NOT NULL AUTO_INCREMENT,
  `id_unidade` int(11) NOT NULL,
  `numero` smallint(6) NOT NULL,
  PRIMARY KEY (`idSala`),
  UNIQUE KEY `idSala_UNIQUE` (`idSala`),
  KEY `id_unidade_idx` (`id_unidade`),
  CONSTRAINT `id_unidade` FOREIGN KEY (`id_unidade`) REFERENCES `unidade` (`idUnidade`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

LOCK TABLES `sala` WRITE;

INSERT INTO `sala` VALUES (1,1,1),(2,1,2),(3,1,3);