package com.quiz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "jogo")
public class Jogo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jogo_id")
    private Integer jogoId;
    
    @Column(name = "data")
    private LocalDateTime data;
    
    @NotNull(message = "A pontuação é obrigatória")
    @Column(name = "pontos", nullable = false)
    private Double pontos;
    
    @NotNull(message = "O ranking é obrigatório")
    @Column(name = "ranking", length = 45, nullable = false)
    private String ranking;
    
    @NotNull(message = "O nível é obrigatório")
    @ManyToOne
    @JoinColumn(name = "nivel_id", nullable = false)
    private Nivel nivel;
    
    @NotNull(message = "O usuário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    private LocalDateTime dataFim;

    @OneToMany(mappedBy = "jogo" ,cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<PerguntaJogo> perguntas;


    public void incrementarPontos(Double pontos) {
        this.pontos += pontos;
    }

    public void finalizarJogo() {
        this.dataFim = LocalDateTime.now();
    }

    public void adiconarPontos(Double pontos) {
        this.pontos = pontos;
    }
}
