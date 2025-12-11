package com.quiz.service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.quiz.enums.PerfilEnum;
import com.quiz.model.Usuario;
import com.quiz.repository.UsuarioRepository;
import com.quiz.response.AuthResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Log4j2
public class GoogleService {

    @Value("${google.oauth.url}")
    private String googleOauthUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    private final JwtDecoder jwtDecoder;
    private final UsuarioRepository usuarioRepository;

    public AuthResponse getAuth(String code) {
        String accessToken = getAccessToken(code);
        InforUser inforUser = extraInforUser(accessToken);

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(inforUser.email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return new AuthResponse(usuario.getNome(), usuario.getPerfil(), accessToken,inforUser.expiresIn);
        }

        Usuario usuarioNovo = Usuario.builder().nome(inforUser.nome)
                .email(inforUser.email)
                .perfil(PerfilEnum.NORMAL)
                .build();
        usuarioRepository.save(usuarioNovo);
        return new AuthResponse(usuarioNovo.getNome(), usuarioNovo.getPerfil(), accessToken,inforUser.expiresIn);

    }

    @SuppressWarnings("unchecked")
    private String getAccessToken(String code) {
        WebClient webClient = WebClient.create(googleOauthUrl);
        Map<String, Object> responseMap = webClient.post()
                .uri("/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(createBody(code))
                .retrieve()
                .onStatus(HttpStatusCode::isError, responseError -> responseError.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new RuntimeException("Erro: " + errorBody))))
                .bodyToMono(Map.class).block();

        return (String) responseMap.get("id_token");

    }

    private MultiValueMap<String, String> createBody(String code) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);      
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", redirectUri);
        return body;
    }

    private InforUser extraInforUser(String idToken) {
        log.info(idToken);
        Jwt jwt = jwtDecoder.decode(idToken);
        String nome = jwt.getClaim("name");
        String email = jwt.getClaim("email");
        Instant expiresIn = (Instant) jwt.getClaim("exp");
        long  expiresInSeconds = expiresIn.getEpochSecond() - Instant.now().getEpochSecond();
        return new InforUser(nome, email, expiresInSeconds);
    }

    public record InforUser(String nome, String email, long expiresIn) {
    }

}
