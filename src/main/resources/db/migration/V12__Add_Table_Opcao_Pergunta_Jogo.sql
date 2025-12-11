CREATE TABLE quiz_aleitamento.opcao_pergunta_jogo (
	opcao_pergunta_jogo_id INT auto_increment NOT NULL,
	descricao varchar(100) NULL,
	correta BOOL NULL,
	pergunta_jogo_id INT NULL,
	CONSTRAINT opcao_pergunta_jogo_pk PRIMARY KEY (opcao_pergunta_jogo_id),
	CONSTRAINT opcao_pergunta_jogo_pergunta_jogo_FK FOREIGN KEY (pergunta_jogo_id) REFERENCES quiz_aleitamento.pergunta_jogo(pergunta_jogo_id)
)