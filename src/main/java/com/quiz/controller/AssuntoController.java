package com.quiz.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.form.AssuntoForm;
import com.quiz.response.AssuntoResponse;
import com.quiz.service.AssuntoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/assuntos")
@RequiredArgsConstructor
public class AssuntoController {

    private final AssuntoService assuntoService;
    
    @GetMapping
    public ResponseEntity<List<AssuntoResponse>> findAll() {
        return ResponseEntity.ok(assuntoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AssuntoResponse> findById(@PathVariable Integer id) {
        return assuntoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

        
    @GetMapping("/nivel/{nivelId}")
    public ResponseEntity<List<AssuntoResponse>> findByNivel(@PathVariable("nivelId") Integer id) {
        return ResponseEntity.ok(assuntoService.findByNivel(id));   
    }
    
    
    @PostMapping
    public ResponseEntity<AssuntoResponse> create(@RequestBody @Valid AssuntoForm form) {
        AssuntoResponse response = assuntoService.createFromForm(form);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AssuntoResponse> update(@PathVariable Integer id, @RequestBody @Valid AssuntoForm form) {
        AssuntoResponse response = assuntoService.updateFromForm(id, form);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        assuntoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
