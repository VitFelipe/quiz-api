package com.quiz.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.form.CreateLoginSimplificadoForm;
import com.quiz.form.LoginSimplificado;
import com.quiz.response.AuthResponse;
import com.quiz.service.AuthService;
import com.quiz.service.GoogleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/authorization")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;
    private final GoogleService googleService;


    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponse> loginAdmin(Authentication authentication){
        return  ResponseEntity.ok(authService.loginAdmin(authentication));
    }

    @PostMapping("/login-simplificado")
    public ResponseEntity<AuthResponse> loginUsuario(@RequestBody @Valid LoginSimplificado login){
       return  ResponseEntity.ok(authService.loginSimplificado(login.getLogin()));
    }

    @PostMapping("/create-user/login")
    public ResponseEntity<AuthResponse> createLoginSimplificado(@RequestBody @Valid CreateLoginSimplificadoForm loginSimplificadoForm){
       return  ResponseEntity.ok(authService.criarLoginSimplificado(loginSimplificadoForm));
    }

    @PostMapping("/google/login")
    public ResponseEntity<AuthResponse> loginGoogle(@RequestParam("code") String code){
        return  ResponseEntity.ok(googleService.getAuth(code));
    }

}
