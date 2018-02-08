CREATE DATABASE  IF NOT EXISTS `boa`
USE `boa`;

DROP TABLE IF EXISTS `unidade`;

CREATE TABLE `unidade` (
  `idUnidade` int(11) NOT NULL AUTO_INCREMENT,
  `id_endereco` int(11) NOT NULL,
  `nome_unidade` varchar(45) NOT NULL,
  PRIMARY KEY (`idUnidade`),
  UNIQUE KEY `idUnidade_UNIQUE` (`idUnidade`),
  UNIQUE KEY `nome_unidade_UNIQUE` (`nome_unidade`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

LOCK TABLES `unidade` WRITE;

INSERT INTO `unidade` VALUES (1,1,'Missionária');