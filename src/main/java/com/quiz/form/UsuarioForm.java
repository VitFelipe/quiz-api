package com.quiz.form;

import com.quiz.enums.PerfilEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioForm {
    
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 85, message = "O nome deve ter no máximo 85 caracteres")
    private String nome;
    
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 85, message = "O email deve ter no máximo 85 caracteres")
    private String email;
    
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;
    
    @NotNull(message = "O perfil é obrigatório")
    private PerfilEnum perfil;
}
