package com.quiz.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaResponse {

    private Boolean correta;
    private String mensagem;
    private Double pontuacao;
}
