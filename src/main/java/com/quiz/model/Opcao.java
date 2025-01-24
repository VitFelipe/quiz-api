package com.quiz.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "opcao")
public class Opcao {
    
    @Id
    @Column(name = "opcao_id")
    private Integer opcaoId;
    
    @Column(name = "descricao", columnDefinition = "LONGTEXT")
    private String descricao;
    
    @Column(name = "opcao_correta")
    private Boolean opcaoCorreta = false;
    
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    
    @ManyToOne
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;

    
    
    @PrePersist
    @PreUpdate
    public void prePersist() {
        dataAtualizacao = LocalDateTime.now();
    }
}
