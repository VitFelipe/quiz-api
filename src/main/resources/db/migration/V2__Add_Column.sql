USE `quiz_aleitamento`;
ALTER TABLE `quiz_aleitamento`.`resposta_jogo` ADD COLUMN `resposta_correta` BOOLEAN DEFAULT FALSE NOT NULL;
ALTER TABLE `quiz_aleitamento`.`opcao` ADD COLUMN `ativo` BOOLEAN DEFAULT FALSE NOT NULL;
