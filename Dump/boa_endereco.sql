CREATE DATABASE  IF NOT EXISTS `boa`
USE `boa`;

DROP TABLE IF EXISTS `endereco`;

CREATE TABLE `endereco` (
  `idEndereco` int(11) NOT NULL AUTO_INCREMENT,
  `logradouro` varchar(100) NOT NULL,
  `numero` int(11) NOT NULL,
  `cep` int(11) NOT NULL,
  `complemento` varchar(100) DEFAULT NULL,
  `bairro` varchar(45) NOT NULL,
  `cidade` varchar(30) NOT NULL,
  `estado` varchar(2) NOT NULL,
  PRIMARY KEY (`idEndereco`),
  UNIQUE KEY `idEndereco_UNIQUE` (`idEndereco`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

LOCK TABLES `endereco` WRITE;

INSERT INTO `endereco` VALUES (1,'Rua Santa Julieta',91,4426090,NULL,'Vila missionaria','São Paulo','SP');