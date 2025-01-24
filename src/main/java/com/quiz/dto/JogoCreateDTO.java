package com.quiz.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JogoCreateDTO {
    
    @NotNull(message = "O nível é obrigatório")
    private Integer nivelId;
    
    @NotNull(message = "O usuário é obrigatório")
    private Integer usuarioId;
}
