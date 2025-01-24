package com.quiz.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "nivel")
public class Nivel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nivel_id")
    private Integer nivelId;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "descricao", nullable = false)
    private String descricao;
    
    @Column(name = "tempo_maximo_resposta", nullable = false)
    private Integer tempoMaximoResposta;
}
