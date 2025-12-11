package com.quiz.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaForm {
    private Integer opcaoIdEscolhida;
    private Integer tempoRestante;
}
