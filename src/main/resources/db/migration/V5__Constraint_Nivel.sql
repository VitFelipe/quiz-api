ALTER TABLE quiz_aleitamento.assunto ADD nivel_id INT NOT NULL;
ALTER TABLE quiz_aleitamento.assunto ADD CONSTRAINT assunto_nivel_FK FOREIGN KEY (nivel_id) REFERENCES quiz_aleitamento.nivel(nivel_id);
