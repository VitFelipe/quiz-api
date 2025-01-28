package com.quiz.response;

import java.util.List;
import java.util.stream.Collectors;

import com.quiz.model.Opcao;
import com.quiz.model.Pergunta;

import lombok.Getter;

@Getter
public class PerguntaResponse {
    private Integer perguntaId;
    private String descricaoPergunta;
    private String descricaoResposta;
    private Boolean ativo;
    private Integer assuntoId;
    private String assuntoNome;
    private List<OpcaoResponse> opcoes;

    public PerguntaResponse(Pergunta pergunta) {
        this.perguntaId = pergunta.getPerguntaId();
        this.descricaoPergunta = pergunta.getDescricaoPergunta();
        this.descricaoResposta = pergunta.getDescricaoResposta();
        this.ativo = pergunta.getAtivo();
        this.assuntoId = pergunta.getAssunto().getAssuntoId();
        this.assuntoNome = pergunta.getAssunto().getNome();
        this.opcoes = pergunta.getOpcoes().stream()
            .map(OpcaoResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public static PerguntaResponse fromEntity(Pergunta pergunta) {
        return new PerguntaResponse(pergunta);
    }

    @Getter
    public static class OpcaoResponse {
        private Integer opcaoId;
        private String descricao;
        private Boolean opcaoCorreta;

        private OpcaoResponse(Opcao opcao) {
            this.opcaoId = opcao.getOpcaoId();
            this.descricao = opcao.getDescricao();
            this.opcaoCorreta = opcao.getOpcaoCorreta();
        }

        public static OpcaoResponse fromEntity(Opcao opcao) {
            return new OpcaoResponse(opcao);
        }
    }
}
