package com.quiz.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.service.IAService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
public class IAController {

    private final IAService service;
    
    @PostMapping
    public ResponseEntity<?> getMethodName(@RequestBody @Valid IARequest request) {
        return ResponseEntity.ok(service.generateQuiz(request.getAssuntoId(), request.getQuantidadePerguntas()));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IARequest {
        @NotNull
       private Integer assuntoId;
        @NotNull
        @Min(1)
        private Integer quantidadePerguntas;
    }
}
