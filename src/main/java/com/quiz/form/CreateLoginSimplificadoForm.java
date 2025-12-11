package com.quiz.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoginSimplificadoForm {

    @NotBlank(message = "O login é obrigatório")
    private String login;
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

}
