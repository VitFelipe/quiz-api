package com.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class PerguntaCreateDTO {
    
    @NotBlank(message = "A descrição da pergunta é obrigatória")
    private String descricaoPergunta;
    
    @NotNull(message = "O nível é obrigatório")
    private Integer nivelId;
    
    private String descricaoResposta;
    
    @NotNull(message = "É necessário informar pelo menos duas opções")
    @Size(min = 2, message = "É necessário informar pelo menos duas opções")
    private List<OpcaoCreateDTO> opcoes;
    
    @Data
    public static class OpcaoCreateDTO {
        @NotBlank(message = "A descrição da opção é obrigatória")
        private String descricao;
        
        @NotNull(message = "É necessário informar se a opção é correta")
        private Boolean opcaoCorreta;
    }
}
