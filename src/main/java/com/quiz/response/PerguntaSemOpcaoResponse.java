package com.quiz.response;

import com.quiz.model.Pergunta;
import lombok.Getter;

@Getter
public class PerguntaSemOpcaoResponse {
    private Integer perguntaId;
    private String descricaoPergunta;
    private String descricaoResposta;
    private Boolean ativo;
    private Integer assuntoId;
    private String assuntoNome;

    public PerguntaSemOpcaoResponse(Pergunta pergunta) {
        this.perguntaId = pergunta.getPerguntaId();
        this.descricaoPergunta = pergunta.getDescricaoPergunta();
        this.descricaoResposta = pergunta.getDescricaoResposta();
        this.ativo = pergunta.getAtivo();
        this.assuntoId = pergunta.getAssunto().getAssuntoId();
        this.assuntoNome = pergunta.getAssunto().getNome();
    }

    public static PerguntaSemOpcaoResponse fromEntity(Pergunta pergunta) {
        return new PerguntaSemOpcaoResponse(pergunta);
    }
}
