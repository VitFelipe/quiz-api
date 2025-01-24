package com.quiz.controller;

import com.quiz.form.PerguntaForm;
import com.quiz.model.Pergunta;
import com.quiz.service.PerguntaService;
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
@RequestMapping("/api/perguntas")
@Tag(name = "Perguntas", description = "Endpoints para gerenciamento das perguntas do quiz")
public class PerguntaController {

    private final PerguntaService perguntaService;

    public PerguntaController(PerguntaService perguntaService) {
        this.perguntaService = perguntaService;
    }
    
    @Operation(summary = "Criar nova pergunta", description = "Cria uma nova pergunta no sistema do quiz")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pergunta criada com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Pergunta.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<Pergunta> createPergunta(
        @Parameter(description = "Dados da pergunta a ser criada") 
        @Valid @RequestBody PerguntaForm form,
        @Parameter(description = "ID do usuário que está criando a pergunta") 
        @RequestHeader("Usuario-Id") Integer usuarioId) {
        Pergunta pergunta = perguntaService.createPergunta(form, usuarioId);
        return ResponseEntity.ok(pergunta);
    }
    
    @Operation(summary = "Buscar perguntas por nível", description = "Retorna todas as perguntas de um nível específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perguntas encontradas",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Pergunta.class)) }),
        @ApiResponse(responseCode = "404", description = "Nível não encontrado")
    })
    @GetMapping("/nivel/{nivelId}")
    public ResponseEntity<List<Pergunta>> getPerguntasByNivel(
        @Parameter(description = "ID do nível") 
        @PathVariable Integer nivelId) {
        List<Pergunta> perguntas = perguntaService.findPerguntasByNivel(nivelId);
        return ResponseEntity.ok(perguntas);
    }
    
    @Operation(summary = "Buscar perguntas aleatórias por nível", description = "Retorna perguntas aleatórias de um nível específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perguntas encontradas",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Pergunta.class)) }),
        @ApiResponse(responseCode = "404", description = "Nível não encontrado")
    })
    @GetMapping("/nivel/{nivelId}/random")
    public ResponseEntity<List<Pergunta>> getRandomPerguntas(
        @Parameter(description = "ID do nível") 
        @PathVariable Integer nivelId,
        @Parameter(description = "Quantidade de perguntas a serem retornadas") 
        @RequestParam(defaultValue = "10") Integer quantidade) {
        List<Pergunta> perguntas = perguntaService.getRandomPerguntasForJogo(nivelId, quantidade);
        return ResponseEntity.ok(perguntas);
    }
}
