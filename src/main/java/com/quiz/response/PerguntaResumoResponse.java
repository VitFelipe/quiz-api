package com.quiz.response;

public record PerguntaResumoResponse(
    Integer perguntaId,
    String descricaoPergunta,
    Boolean ativo,
    String assuntoNome,
    String nivelNome,
    String usuarioCadastro
) {}
