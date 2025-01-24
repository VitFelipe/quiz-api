package com.quiz.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RespostaJogoCreateDTO {
    
    @NotNull(message = "A pergunta é obrigatória")
    private Integer perguntaId;
    
    @NotNull(message = "A opção é obrigatória")
    private Integer opcaoId;
    
    @NotNull(message = "O jogo é obrigatório")
    private Integer jogoId;
}
