package com.quiz.service;

import com.quiz.exception.ForbiddenException;
import com.quiz.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.quiz.config.security.TokenService;
import com.quiz.config.security.UserDetailsCustom;
import com.quiz.enums.PerfilEnum;
import com.quiz.exception.BussinessException;
import com.quiz.form.CreateLoginSimplificadoForm;
import com.quiz.model.Usuario;
import com.quiz.repository.UsuarioRepository;
import com.quiz.response.AuthResponse;

import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    @Value("${jwt.expires-in}")
    private  Integer expiresIn;
    private String SUFIX_EMAIL = "@quiz.com.br";
    

    public AuthResponse loginAdmin(Authentication authentication) {
        UserDetailsCustom userAuth = (UserDetailsCustom) authentication.getPrincipal();

        if (!userAuth.isAdmin()) {
            throw new ForbiddenException("Você não tem permissão para acessar esse recurso");
        }

        String token = tokenService.generateToken(authentication);
        return new AuthResponse(
                userAuth.getNome(),
                PerfilEnum.valueOf(authentication.getAuthorities().stream().findAny().get().getAuthority()),
                token,
                expiresIn
                );
    }






    public AuthResponse loginSimplificado(String login) {
        Usuario usuario = usuarioRepository.findByEmail(loginComSufixo(login))
                .orElseThrow(() -> new BussinessException("Usuário com login '%s' não encontrado".formatted(login)));
        return fromResponse(usuario);
    }



    public AuthResponse criarLoginSimplificado(CreateLoginSimplificadoForm loginSimplificadoForm) {
        if (usuarioRepository.existsByEmail(loginComSufixo(loginSimplificadoForm.getLogin()))) {
            throw new BussinessException(
                    "Já existe um usuário com login '%s'".formatted(loginSimplificadoForm.getLogin()));
        }
        Usuario usuario = salvandoUsuario(loginSimplificadoForm);
        return fromResponse(usuario);
    }





    private AuthResponse fromResponse(Usuario usuario) {
        String token = tokenService.generateToken(usuario);
        return new AuthResponse(
                usuario.getNome(),
                usuario.getPerfil(),
                token,
                expiresIn
                );
    }


    private Usuario salvandoUsuario(CreateLoginSimplificadoForm loginSimplificadoForm) {
        Usuario usuario = new Usuario();
        usuario.setNome(loginSimplificadoForm.getNome());
        usuario.setEmail(loginSimplificadoForm.getLogin() + SUFIX_EMAIL);
        usuario.setPerfil(PerfilEnum.NORMAL);
        return usuarioRepository.save(usuario);
    }



    private String loginComSufixo(String login) {
        return login + SUFIX_EMAIL;
    }




}
