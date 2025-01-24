package com.quiz.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Formulário para criação/atualização de opção de resposta")
public class OpcaoForm {

    @NotBlank(message = "O texto da opção é obrigatório")
    @Schema(description = "Texto da opção de resposta", example = "Amamentação exclusiva até os 6 meses")
    private String texto;

    @NotNull(message = "O campo 'correta' é obrigatório")
    @Schema(description = "Indica se esta é a opção correta", example = "true")
    private Boolean correta;

    @NotNull(message = "O ID da pergunta é obrigatório")
    @Schema(description = "ID da pergunta à qual esta opção pertence", example = "1")
    private Integer perguntaId;

}
