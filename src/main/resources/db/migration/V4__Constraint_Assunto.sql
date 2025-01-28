ALTER TABLE quiz_aleitamento.pergunta ADD assunto_id INT NOT NULL;
ALTER TABLE quiz_aleitamento.pergunta ADD CONSTRAINT pergunta_assunto_FK FOREIGN KEY (assunto_id) REFERENCES quiz_aleitamento.assunto(assunto_id);
