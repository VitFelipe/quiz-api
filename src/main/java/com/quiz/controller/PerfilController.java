package com.quiz.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.enums.PerfilEnum;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/perfis")
public class PerfilController {

    @GetMapping
    public ResponseEntity<List<PerfilEnum>> getAllPerfis() {
        List<PerfilEnum> perfis = Stream.of(PerfilEnum.values()).collect(Collectors.toList());
        return ResponseEntity.ok(perfis);
    }

}
