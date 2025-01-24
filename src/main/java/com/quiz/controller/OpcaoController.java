package com.quiz.controller;

import com.quiz.form.OpcaoForm;
import com.quiz.model.Opcao;
import com.quiz.service.OpcaoService;
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
@RequestMapping("/api/opcoes")
@Tag(name = "Opções", description = "Endpoints para gerenciamento das opções de resposta do quiz")
public class OpcaoController {

    private final OpcaoService opcaoService;

    public OpcaoController(OpcaoService opcaoService) {
        this.opcaoService = opcaoService;
    }

    @Operation(summary = "Criar nova opção", description = "Cria uma nova opção de resposta para uma pergunta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Opção criada com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Opcao.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Pergunta não encontrada")
    })
    @PostMapping
    public ResponseEntity<Opcao> createOpcao(
        @Parameter(description = "Dados da opção a ser criada") 
        @Valid @RequestBody OpcaoForm form) {
        return ResponseEntity.ok(opcaoService.createOpcao(form));
    }

    @Operation(summary = "Listar opções por pergunta", description = "Retorna todas as opções de resposta de uma pergunta específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de opções retornada com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Opcao.class)) }),
        @ApiResponse(responseCode = "404", description = "Pergunta não encontrada")
    })
    @GetMapping("/pergunta/{perguntaId}")
    public ResponseEntity<List<Opcao>> getOpcoesByPergunta(
        @Parameter(description = "ID da pergunta") 
        @PathVariable Integer perguntaId) {
        return ResponseEntity.ok(opcaoService.findByPerguntaId(perguntaId));
    }

    @Operation(summary = "Atualizar opção", description = "Atualiza uma opção de resposta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Opção atualizada com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Opcao.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Opção não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Opcao> updateOpcao(
        @Parameter(description = "ID da opção a ser atualizada") 
        @PathVariable Integer id,
        @Parameter(description = "Novos dados da opção") 
        @Valid @RequestBody OpcaoForm form) {
        return ResponseEntity.ok(opcaoService.updateOpcao(id, form));
    }
}
