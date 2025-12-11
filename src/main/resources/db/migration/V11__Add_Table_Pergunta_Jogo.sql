CREATE TABLE quiz_aleitamento.pergunta_jogo (
	pergunta_jogo_id INT auto_increment NOT NULL,
	descricao_pergunta LONGTEXT NOT NULL,
	descricao_resposta LONGTEXT NOT NULL,
	assunto_id INT NOT NULL,
	resposta_correta boolean NULL,
	jogo_id INT NULL,
	CONSTRAINT pergunta_jogo_pk PRIMARY KEY (pergunta_jogo_id),
	CONSTRAINT jogo_pergunta_assunto_FK FOREIGN KEY (assunto_id) REFERENCES quiz_aleitamento.assunto(assunto_id)
)
