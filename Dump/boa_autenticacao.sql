CREATE DATABASE  IF NOT EXISTS `boa`
USE `boa`;

DROP TABLE IF EXISTS `autenticacao`;

CREATE TABLE `autenticacao` (
  `idAutenticacao` int(11) NOT NULL AUTO_INCREMENT,
  `id_perfil` int(11) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  PRIMARY KEY (`idAutenticacao`),
  UNIQUE KEY `idAutenticacao_UNIQUE` (`idAutenticacao`),
  UNIQUE KEY `id_perfil_UNIQUE` (`id_perfil`),
  UNIQUE KEY `usuario_UNIQUE` (`usuario`),
  CONSTRAINT `id_perfil` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`idPerfil`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

LOCK TABLES `autenticacao` WRITE;

INSERT INTO `autenticacao` VALUES (1,1,'cesar','1234');