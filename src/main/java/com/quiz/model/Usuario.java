package com.quiz.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;
    
    @Column(name = "nome", length = 85, nullable = false)
    private String nome;
    
    @Column(name = "email", length = 85, nullable = false, unique = true)
    private String email;
    
    @Column(name = "perfil", length = 45)
    private String perfil;
    
    @Column(name = "senha", length = 155)
    private String senha;
    
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    
    @PrePersist
    @PreUpdate
    public void prePersist() {
        dataAtualizacao = LocalDateTime.now();
    }
}
