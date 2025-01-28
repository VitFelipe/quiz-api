package com.quiz.response;

import java.util.List;
import java.util.stream.Collectors;

import com.quiz.model.Opcao;
import com.quiz.model.Pergunta;

import lombok.Getter;

@Getter
public class PerguntaComOpcaoResponse {
    private Integer perguntaId;
    private String descricaoPergunta;
    private String descricaoResposta;
    private Boolean ativo;
    private Integer assuntoId;
    private String nivelNome;
    private Integer nivelId;
    private String assuntoNome;
    private List<OpcaoResponse> opcoes;

    public PerguntaComOpcaoResponse(Pergunta pergunta) {
        this.perguntaId = pergunta.getPerguntaId();
        this.descricaoPergunta = pergunta.getDescricaoPergunta();
        this.descricaoResposta = pergunta.getDescricaoResposta();
        this.ativo = pergunta.getAtivo();
        this.nivelId = pergunta.getAssunto().getNivel().getNivelId();
        this.nivelNome = pergunta.getAssunto().getNivel().getNome();
        this.assuntoId = pergunta.getAssunto().getAssuntoId();
        this.assuntoNome = pergunta.getAssunto().getNome();
        this.opcoes = pergunta.getOpcoes().stream()
            .map(OpcaoResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public static PerguntaComOpcaoResponse fromEntity(Pergunta pergunta) {
        return new PerguntaComOpcaoResponse(pergunta);
    }

    @Getter
    public static class OpcaoResponse {
        private Integer opcaoId;
        private String descricao;
        private Boolean opcaoCorreta;
        private Integer ordem;

        private OpcaoResponse(Opcao opcao) {
            this.opcaoId = opcao.getOpcaoId();
            this.descricao = opcao.getDescricao();
            this.opcaoCorreta = opcao.getOpcaoCorreta();
            this.ordem = opcao.getOrdem();
        }

        public static OpcaoResponse fromEntity(Opcao opcao) {
            return new OpcaoResponse(opcao);
        }
    }
}
