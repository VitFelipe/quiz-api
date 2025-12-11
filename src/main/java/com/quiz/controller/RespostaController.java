package com.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.form.RespostaForm;
import com.quiz.response.RespostaResponse;
import com.quiz.service.RespostaService;

@RestController
@RequestMapping("/respostas")
public class RespostaController {
    
    @Autowired
    private RespostaService respostaService;
    
    @PostMapping("/verificar")
    public ResponseEntity<RespostaResponse> verificarResposta(@RequestBody RespostaForm respostaForm) {
        RespostaResponse resultado = respostaService.verificarResposta(respostaForm);
        return ResponseEntity.ok(resultado);
    }
}
