package com.quiz.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "configuracao_jogo")
public class ConfiguracaoJogo {
    
    @Id
    @Column(name = "idconfiguracao_jogo")
    private Integer idConfiguracaoJogo;
    
    @Column(name = "numero_maximo_perguntas")
    private Integer numeroMaximoPerguntas;
}
