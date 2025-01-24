package com.quiz.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Formulário para configurações do sistema")
public class ConfiguracaoForm {

    @NotNull(message = "O tempo limite por pergunta é obrigatório")
    @Min(value = 10, message = "O tempo limite por pergunta deve ser de pelo menos 10 segundos")
    @Schema(description = "Tempo limite em segundos para responder cada pergunta", example = "60")
    private Integer tempoLimitePorPergunta;

    @NotNull(message = "O número de tentativas por nível é obrigatório")
    @Min(value = 1, message = "O número de tentativas por nível deve ser pelo menos 1")
    @Schema(description = "Número máximo de tentativas permitidas por nível", example = "3")
    private Integer tentativasPorNivel;

    @NotNull(message = "A pontuação por resposta correta é obrigatória")
    @Min(value = 1, message = "A pontuação por resposta correta deve ser pelo menos 1")
    @Schema(description = "Pontuação concedida para cada resposta correta", example = "10")
    private Integer pontuacaoPorRespostaCorreta;

    @NotNull(message = "O campo de exibição de explicações é obrigatório")
    @Schema(description = "Define se as explicações das respostas devem ser mostradas", example = "true")
    private Boolean mostrarExplicacoes;
}
