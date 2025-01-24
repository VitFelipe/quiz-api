package com.quiz.controller;

import com.quiz.form.NivelForm;
import com.quiz.model.Nivel;
import com.quiz.service.NivelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/niveis")
@Tag(name = "Níveis", description = "Endpoints para gerenciamento dos níveis do quiz")
public class NivelController {

    private final NivelService nivelService;

    public NivelController(NivelService nivelService) {
        this.nivelService = nivelService;
    }

    @Operation(summary = "Criar novo nível", description = "Cria um novo nível de dificuldade no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nível criado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Nivel.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Nivel> createNivel(
        @Parameter(description = "Dados do nível a ser criado") 
        @Valid @RequestBody NivelForm form) {
        return ResponseEntity.ok(nivelService.createNivel(form));
    }

    @Operation(summary = "Listar todos os níveis", description = "Retorna todos os níveis de dificuldade disponíveis")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de níveis retornada com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Nivel.class)) })
    })
    @GetMapping
    public ResponseEntity<List<Nivel>> getAllNiveis() {
        return ResponseEntity.ok(nivelService.getAllNiveis());
    }

    @Operation(summary = "Buscar nível por ID", description = "Retorna um nível específico baseado no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nível encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Nivel.class)) }),
        @ApiResponse(responseCode = "404", description = "Nível não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Nivel> getNivelById(
        @Parameter(description = "ID do nível a ser buscado") 
        @PathVariable Integer id) {
        return ResponseEntity.ok(nivelService.findById(id));
    }
}
