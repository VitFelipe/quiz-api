package com.quiz.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ConfiguracaoJogoForm {
    
    @NotNull(message = "O número máximo de perguntas é obrigatório")
    @Positive(message = "O número máximo de perguntas deve ser maior que zero")
    private Integer numeroMaximoPerguntas;
    
    private String templatePrompt;
}
