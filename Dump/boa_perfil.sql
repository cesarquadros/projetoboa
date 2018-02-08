CREATE DATABASE  IF NOT EXISTS `boa`
USE `boa`;

DROP TABLE IF EXISTS `perfil`;

CREATE TABLE `perfil` (
  `idPerfil` int(11) NOT NULL AUTO_INCREMENT,
  `nome_perfil` varchar(20) NOT NULL,
  PRIMARY KEY (`idPerfil`),
  UNIQUE KEY `idPerfil_UNIQUE` (`idPerfil`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

LOCK TABLES `perfil` WRITE;

INSERT INTO `perfil` VALUES (1,'Usuario');