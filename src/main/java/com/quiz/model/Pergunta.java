package com.quiz.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "pergunta")
public class Pergunta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pergunta_id")
    private Integer perguntaId;
    
    @Column(name = "descricao_pergunta", columnDefinition = "LONGTEXT", nullable = false)
    private String descricaoPergunta;
    
    @Column(name = "ativo")
    private Boolean ativo = false;
    
    @ManyToOne
    @JoinColumn(name = "nivel_id", nullable = false)
    private Nivel nivel;
    
    @Column(name = "descricao_resposta", columnDefinition = "LONGTEXT")
    private String descricaoResposta;
    
    @ManyToOne
    @JoinColumn(name = "usuario_cadastro_id", nullable = false)
    private Usuario usuarioCadastro;
    
    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Opcao> opcoes;
}
