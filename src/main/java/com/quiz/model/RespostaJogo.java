package com.quiz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "resposta_jogo")
public class RespostaJogo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resposta_jogo_id")
    private Integer respostaJogoId;
    
    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;
    
    @Column(name = "data_fim")
    private LocalDateTime dataFim;
    
    @NotNull(message = "A pergunta é obrigatória")
    @ManyToOne
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;
    
    @NotNull(message = "A opção é obrigatória")
    @ManyToOne
    @JoinColumn(name = "opcao_id", nullable = false)
    private Opcao opcao;
}
