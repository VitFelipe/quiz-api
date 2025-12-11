package com.quiz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "opcao_pergunta_jogo", schema = "quiz_aleitamento")
public class OpcaoPerguntaJogo {

    public OpcaoPerguntaJogo(Opcao opcao, PerguntaJogo perguntaJogo){ 
        this.descricao = opcao.getDescricao();
        this.correta = opcao.getOpcaoCorreta();
        this.perguntaJogo = perguntaJogo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opcao_pergunta_jogo_id")
    private Integer id;

    @Column(name = "descricao", length = 100)
    private String descricao;

    @Column(name = "correta")
    private Boolean correta;

    @ManyToOne
    @JoinColumn(name = "pergunta_jogo_id")
    private PerguntaJogo perguntaJogo;
}
