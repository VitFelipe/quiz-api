package com.quiz.response;

import com.quiz.enums.PerfilEnum;

import java.time.Instant;

public record AuthResponse(String nome, PerfilEnum perfil, String token, long expiration) {

}
