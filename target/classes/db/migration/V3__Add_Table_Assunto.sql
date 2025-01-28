CREATE TABLE quiz_aleitamento.assunto (
	assunto_id INT auto_increment NOT NULL,
	nome varchar(100) NOT NULL,
	CONSTRAINT assunto_pk PRIMARY KEY (assunto_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
