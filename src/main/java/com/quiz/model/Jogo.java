package com.quiz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

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
    private Float pontos;
    
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
    
    @Column(name = "jogocol")
    private LocalDateTime jogocol;
}
