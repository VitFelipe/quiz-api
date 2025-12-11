package com.quiz.config.security;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.quiz.model.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;

    @Value("${jwt.expires-in}")
    private  Integer expiresIn;



    public String generateToken(Authentication authentication) {
        String subject = authentication.getName();
        Instant now = LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        JwtClaimsSet jwtSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("authorities", roles)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtSet)).getTokenValue();
    }


    
    public String generateToken(Usuario usuario) {
        String subject = usuario.getEmail();
        Instant now = LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant();
        List<String> roles = Arrays.asList(usuario.getPerfil().name());
        JwtClaimsSet jwtSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("authorities", roles)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtSet)).getTokenValue();
    }

        


}
