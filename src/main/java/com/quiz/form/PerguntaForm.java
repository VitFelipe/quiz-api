package com.quiz.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class PerguntaForm {
    
    @NotBlank(message = "A descrição da pergunta é obrigatória")
    private String descricaoPergunta;
    
    @NotNull(message = "O assunto é obrigatório")
    private Integer assuntoId;
    
    private Boolean ativo;
    
    private String descricaoResposta;
    
    @NotNull(message = "É necessário informar pelo menos duas opções")
    @Size(min = 1, message = "É necessário informar pelo menos duas opções")
    private List<OpcaoForm> opcoes;
    
    @Data
    public static class OpcaoForm {
        @NotBlank(message = "A descrição da opção é obrigatória")
        private String descricao;
        
        @NotNull(message = "É necessário informar se a opção é correta")
        private Boolean opcaoCorreta;

        private Integer ordem;
    
    }
}
