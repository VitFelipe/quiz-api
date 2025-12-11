package com.quiz.form;

import com.quiz.enums.PerfilEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUsuarioForm {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    @NotNull(message = "O perfil é obrigatório")
    private PerfilEnum perfil;

}
