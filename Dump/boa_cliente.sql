CREATE DATABASE  IF NOT EXISTS `boa`
USE `boa`;

DROP TABLE IF EXISTS `cliente`;

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `sobrenome` varchar(45) NOT NULL,
  `tel_fixo` varchar(10) DEFAULT NULL,
  `tel_celular` varchar(11) DEFAULT NULL,
  `cpf` bigint(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `sexo` char(1) NOT NULL,
  `dt_nasc` date NOT NULL,
  `id_autenticacao` int(11) NOT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE KEY `idCliente_UNIQUE` (`idCliente`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`),
  KEY `id_autenticacao_idx` (`id_autenticacao`),
  CONSTRAINT `id_autenticacao` FOREIGN KEY (`id_autenticacao`) REFERENCES `autenticacao` (`idAutenticacao`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

LOCK TABLES `cliente` WRITE;

INSERT INTO `cliente` VALUES (1,'Cesar','Quadros','1156214658','11980635589',37765289829,'nino@nino','m','1988-07-19',1);