package com.quiz.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JogoForm {
    
    @NotNull(message = "O nível é obrigatório")
    private Integer nivelId;
    
}
