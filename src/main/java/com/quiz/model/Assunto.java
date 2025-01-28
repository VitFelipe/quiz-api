package com.quiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "assunto", schema = "quiz_aleitamento")
public class Assunto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assuntoId;
    
    private String nome;
    
    @ManyToOne
    @JoinColumn(name = "nivel_id", nullable = false)
    private Nivel nivel;
}
