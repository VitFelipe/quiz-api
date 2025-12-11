package com.quiz.config.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;
    @Value("${jwt.url.public.key.google}")
    private String urlPublicKeyGoogle ;
    


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable
        );
        httpSecurity.authorizeHttpRequests(authorizeHttpRequests ->
                 authorizeHttpRequests.requestMatchers(HttpMethod.POST,"/authorization/admin/login").permitAll()
                         .requestMatchers(HttpMethod.POST,"/authorization/admin/login").permitAll()
                         .requestMatchers(HttpMethod.POST,"/authorization/quiz/login").permitAll()
                         .requestMatchers(HttpMethod.POST,"/authorization/google/login").permitAll()
                         .requestMatchers("/oauth2/**").permitAll()
                         .anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults());
        httpSecurity.oauth2ResourceServer(confi ->
                confi.jwt(Customizer.withDefaults())
        );

        httpSecurity.oauth2Login(AbstractAuthenticationFilterConfigurer::permitAll);

        return httpSecurity.build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        RSAKey jwtkey = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        ImmutableJWKSet<SecurityContext> immutableJWKSet = new ImmutableJWKSet<>(new JWKSet(jwtkey));

        return new NimbusJwtEncoder(immutableJWKSet);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        JwtDecoder jwtDecoderGoogle = NimbusJwtDecoder.withJwkSetUri(urlPublicKeyGoogle).build();
        JwtDecoder jwtDecoderQuiz = NimbusJwtDecoder.withPublicKey(publicKey).build();
        return new CompositeJwtDecoder(List.of(jwtDecoderQuiz,jwtDecoderGoogle));
    }

   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

