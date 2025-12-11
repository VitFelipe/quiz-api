package com.quiz.response;

import java.util.List;
import java.util.stream.Collectors;

import com.quiz.model.OpcaoPerguntaJogo;
import com.quiz.model.PerguntaJogo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PerguntaJogoComOpcaoResponse {
    private Integer id;
    private String descricaoPergunta;
    private String descricaoResposta;
    private Boolean ativo;
    private Integer assuntoId;
    private String nivelNome;
    private Integer nivelId;
    private String assuntoNome;
    private List<OpcaoPerguntaJogoResponse> opcoes;

    public PerguntaJogoComOpcaoResponse(PerguntaJogo pergunta) {
        this.id = pergunta.getId();
        this.descricaoPergunta = pergunta.getDescricaoPergunta();
        this.descricaoResposta = pergunta.getDescricaoResposta();
        this.nivelId = pergunta.getAssunto().getNivel().getNivelId();
        this.nivelNome = pergunta.getAssunto().getNivel().getNome();
        this.assuntoId = pergunta.getAssunto().getAssuntoId();
        this.assuntoNome = pergunta.getAssunto().getNome();
        this.opcoes = pergunta.getOpcoes().stream()
            .map(OpcaoPerguntaJogoResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public static PerguntaJogoComOpcaoResponse fromEntity(PerguntaJogo pergunta) {
        return new PerguntaJogoComOpcaoResponse(pergunta);
    }

    @Getter
    public static class OpcaoPerguntaJogoResponse {
        private Integer id;
        private String descricao;
        private Boolean correta;

        private OpcaoPerguntaJogoResponse(OpcaoPerguntaJogo opcao) {
            this.id = opcao.getId();
            this.descricao = opcao.getDescricao();
            this.correta = opcao.getCorreta();
        }

        public static OpcaoPerguntaJogoResponse fromEntity(OpcaoPerguntaJogo opcao) {
            return new OpcaoPerguntaJogoResponse(opcao);
        }
    }
}
