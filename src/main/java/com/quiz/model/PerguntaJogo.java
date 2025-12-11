package com.quiz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pergunta_jogo", schema = "quiz_aleitamento")
public class PerguntaJogo {

    public PerguntaJogo(Pergunta pergunta,Jogo jogo) {
        this.pergunta = pergunta;
        this.jogo = jogo;
        this.descricaoPergunta = pergunta.getDescricaoPergunta();
        this.descricaoResposta = pergunta.getDescricaoResposta();
        this.assunto = pergunta.getAssunto();
        this.opcoes = pergunta.getOpcoes().stream().
        map(opcao ->  new OpcaoPerguntaJogo(opcao, this)).toList();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pergunta_jogo_id")
    private Integer id;

    @Column(name = "descricao_pergunta", nullable = false)
    private String descricaoPergunta;

    @Column(name = "descricao_resposta", nullable = false)
    private String descricaoResposta;

    @Column(name = "resposta_correta")
    private Boolean respostaCorreta;

    @ManyToOne
    @JoinColumn(name = "assunto_id", nullable = false)
    private Assunto assunto;

    @ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @ManyToOne
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;

    @Column(name = "pontos")
    private Double pontos = 0.0;

    @OneToMany(mappedBy = "perguntaJogo", cascade = CascadeType.ALL)
    private List<OpcaoPerguntaJogo> opcoes;


    


}
