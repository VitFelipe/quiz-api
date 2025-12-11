package com.quiz.response;

import com.quiz.enums.PerfilEnum;
import com.quiz.model.Usuario;
import lombok.Data;

@Data
public class UsuarioResponse {
    private Integer usuarioId;
    private String nome;
    private String email;
    private PerfilEnum perfil;
    
    public static UsuarioResponse fromEntity(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setUsuarioId(usuario.getUsuarioId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setPerfil(usuario.getPerfil());
        return response;
    }
}
