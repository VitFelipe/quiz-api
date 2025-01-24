
USE `quiz_aleitamento`;

CREATE TABLE   `configuracao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `valor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE  `configuracao_jogo` (
  `idconfiguracao_jogo` int NOT NULL AUTO_INCREMENT,
  `numero_maximo_perguntas` int DEFAULT NULL,
  PRIMARY KEY (`idconfiguracao_jogo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE  `usuario` (
  `usuario_id` int NOT NULL AUTO_INCREMENT,
  `data_atualizacao` datetime(6) NOT NULL,
  `email` varchar(85) NOT NULL,
  `nome` varchar(85) NOT NULL,
  `perfil` varchar(45) DEFAULT NULL,
  `senha` varchar(155) DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  UNIQUE KEY `UK_5171l57faosmj8myawaucatdw` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE  `nivel` (
  `nivel_id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) NOT NULL,
  `tempo_maximo_resposta` int NOT NULL,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`nivel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE  `jogo` (
  `jogo_id` int NOT NULL AUTO_INCREMENT,
  `data` datetime(6) DEFAULT NULL,
  `jogocol` datetime(6) DEFAULT NULL,
  `pontos` float NOT NULL,
  `ranking` varchar(45) NOT NULL,
  `nivel_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`jogo_id`),
  KEY `FKkv8binh44mbckalfwrh8qumgq` (`usuario_id`),
  KEY `jogo_FK` (`nivel_id`),
  CONSTRAINT `FKkv8binh44mbckalfwrh8qumgq` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`),
  CONSTRAINT `jogo_FK` FOREIGN KEY (`nivel_id`) REFERENCES `nivel` (`nivel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE  `pergunta` (
  `pergunta_id` int NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) DEFAULT NULL,
  `descricao_pergunta` longtext NOT NULL,
  `descricao_resposta` longtext,
  `nivel_id` int NOT NULL,
  `usuario_cadastro_id` int NOT NULL,
  PRIMARY KEY (`pergunta_id`),
  KEY `FK47phpi75b5ofky0wj2ayflpui` (`usuario_cadastro_id`),
  KEY `pergunta_FK` (`nivel_id`),
  CONSTRAINT `FK47phpi75b5ofky0wj2ayflpui` FOREIGN KEY (`usuario_cadastro_id`) REFERENCES `usuario` (`usuario_id`),
  CONSTRAINT `pergunta_FK` FOREIGN KEY (`nivel_id`) REFERENCES `nivel` (`nivel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE  `opcao` (
  `opcao_id` int NOT NULL AUTO_INCREMENT,
  `data_atualizacao` datetime(6) NOT NULL,
  `descricao` longtext,
  `opcao_correta` bit(1) DEFAULT NULL,
  `pergunta_id` int NOT NULL,
  PRIMARY KEY (`opcao_id`),
  KEY `FKnwgr1mlk66c82xvrnexjmr5oq` (`pergunta_id`),
  CONSTRAINT `FKnwgr1mlk66c82xvrnexjmr5oq` FOREIGN KEY (`pergunta_id`) REFERENCES `pergunta` (`pergunta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE  `resposta_jogo` (
  `resposta_jogo_id` int NOT NULL AUTO_INCREMENT,
  `data_fim` datetime(6) DEFAULT NULL,
  `data_inicio` datetime(6) DEFAULT NULL,
  `opcao_id` int NOT NULL,
  `pergunta_id` int NOT NULL,
  PRIMARY KEY (`resposta_jogo_id`),
  KEY `FKsu9vj5hxjwsm2pv1k8fn7apah` (`opcao_id`),
  KEY `FKlvi0xd6lmawq2pgfc44brnb9g` (`pergunta_id`),
  CONSTRAINT `FKlvi0xd6lmawq2pgfc44brnb9g` FOREIGN KEY (`pergunta_id`) REFERENCES `pergunta` (`pergunta_id`),
  CONSTRAINT `FKsu9vj5hxjwsm2pv1k8fn7apah` FOREIGN KEY (`opcao_id`) REFERENCES `opcao` (`opcao_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

