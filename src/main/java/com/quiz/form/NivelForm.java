package com.quiz.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Formulário para criação/atualização de nível")
public class NivelForm {

    @NotBlank(message = "O nome é obrigatório")
    @Schema(description = "Nome do nível", example = "Iniciante")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Schema(description = "Descrição detalhada do nível", example = "Nível para usuários iniciantes com perguntas básicas")
    private String descricao;
 

    @NotNull(message = "O tempo máximo de resposta é obrigatório")
    @Min(value = 10, message = "O tempo máximo de resposta deve ser de pelo menos 10 segundos")
    @Schema(description = "Tempo máximo em segundos para responder cada pergunta", example = "60")
    private Integer tempoMaximoResposta;

    

}
