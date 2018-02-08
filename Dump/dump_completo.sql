CREATE DATABASE  IF NOT EXISTS `boa`
USE `boa`;

/*===================================================================================================*/

DROP TABLE IF EXISTS `unidade`;

CREATE TABLE `unidade` (
  `idUnidade` int(11) NOT NULL AUTO_INCREMENT,
  `id_endereco` int(11) NOT NULL,
  `nome_unidade` varchar(45) NOT NULL,
  PRIMARY KEY (`idUnidade`),
  UNIQUE KEY `idUnidade_UNIQUE` (`idUnidade`),
  UNIQUE KEY `nome_unidade_UNIQUE` (`nome_unidade`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `unidade` VALUES (1,1,'Missionária');

/*===================================================================================================*/

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

INSERT INTO `sala` VALUES (1,1,1),(2,1,2),(3,1,3);

/*===================================================================================================*/

DROP TABLE IF EXISTS `horarios`;

CREATE TABLE `horarios` (
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `horario` time NOT NULL,
  PRIMARY KEY (`idHorario`),
  UNIQUE KEY `idHorario_UNIQUE` (`idHorario`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

INSERT INTO `horarios` VALUES (1,'08:00:00'),(2,'09:00:00'),(3,'10:00:00'),(4,'11:00:00'),(5,'12:00:00'),(6,'13:00:00'),(7,'14:00:00'),(8,'15:00:00'),(9,'16:00:00'),(10,'17:00:00');

/*===================================================================================================*/

DROP TABLE IF EXISTS `perfil`;

CREATE TABLE `perfil` (
  `idPerfil` int(11) NOT NULL AUTO_INCREMENT,
  `nome_perfil` varchar(20) NOT NULL,
  PRIMARY KEY (`idPerfil`),
  UNIQUE KEY `idPerfil_UNIQUE` (`idPerfil`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `perfil` VALUES (1,'Usuario');

/*===================================================================================================*/

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

INSERT INTO `endereco` VALUES (1,'Rua Santa Julieta',91,4426090,NULL,'Vila missionaria','São Paulo','SP');

/*===================================================================================================*/

DROP TABLE IF EXISTS `autenticacao`;

CREATE TABLE `autenticacao` (
  `idAutenticacao` int(11) NOT NULL AUTO_INCREMENT,
  `id_perfil` int(11) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  PRIMARY KEY (`idAutenticacao`),
  UNIQUE KEY `idAutenticacao_UNIQUE` (`idAutenticacao`),
  UNIQUE KEY `usuario_UNIQUE` (`usuario`),
  CONSTRAINT `id_perfil` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`idPerfil`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `autenticacao` VALUES (1,1,'cesar','1234');
INSERT INTO `autenticacao` VALUES (2,1,'diego','123456');

/*===================================================================================================*/

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

INSERT INTO `cliente` VALUES (1,'Cesar','Quadros','1156214658','11980635589',37765289829,'nino@nino','m','1988-07-19',1);
INSERT INTO `cliente` VALUES (2,'Diego','Fernandes','1139264178','11962050241',36921709809,'bill@bill','m','1988-07-21',2);

/*===================================================================================================*/

DROP TABLE IF EXISTS `agendamento`;

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

INSERT INTO `agendamento` VALUES (3,1,1,1,'2018-02-07',1);
