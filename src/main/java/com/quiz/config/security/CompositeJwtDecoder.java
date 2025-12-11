package com.quiz.config.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.util.List;


public class CompositeJwtDecoder implements JwtDecoder {

     private final List<JwtDecoder> jwtDecoders;

    public CompositeJwtDecoder(List<JwtDecoder> jwtDecoders) {
        this.jwtDecoders = jwtDecoders;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        for (JwtDecoder jwtDecoder : jwtDecoders) {
            try {
                return jwtDecoder.decode(token);
            } catch (JwtException ignored) {

            }
        }
         throw new JwtException("Não foi possível decodificar o token");
    }
}
