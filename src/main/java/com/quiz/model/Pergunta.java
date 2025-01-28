package com.quiz.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pergunta", schema = "quiz_aleitamento")
public class Pergunta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer perguntaId;
    
    @Column(name = "descricao_pergunta")
    private String descricaoPergunta;
    
    @Column(name = "descricao_resposta", columnDefinition = "LONGTEXT")
    private String descricaoResposta;
    
    @Column(name = "ativo")
    private Boolean ativo = true;
    
    @ManyToOne
    @JoinColumn(name = "assunto_id", nullable = false)
    private Assunto assunto;
    
    @ManyToOne
    @JoinColumn(name = "usuario_cadastro_id", nullable = false)
    private Usuario usuarioCadastro;
    
    @OneToMany(mappedBy = "pergunta",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Opcao> opcoes;
}
