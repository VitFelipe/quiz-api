package com.quiz.controller;

import com.quiz.form.ConfiguracaoJogoForm;
import com.quiz.response.ConfiguracaoJogoResponse;
import com.quiz.service.ConfiguracaoJogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuracoes-jogo")
@Tag(name = "Configurações de Jogo", description = "Endpoints para gerenciamento das configurações do jogo")
@RequiredArgsConstructor
public class ConfiguracaoJogoController {

    private final ConfiguracaoJogoService configuracaoJogoService;

    @Operation(summary = "Buscar Ultima Configuração", description = "Retorna a ultima configuração criada ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuração encontrada",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ConfiguracaoJogoResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "Configuração não encontrada")
    })
    @GetMapping("/find-last")
    public ResponseEntity<ConfiguracaoJogoResponse> findLastConfig() {
        ConfiguracaoJogoResponse response = configuracaoJogoService.findLastConfig();
        return ResponseEntity.ok(response);
    }

 

    @Operation(summary = "Atualizar configuração", description = "Atualiza uma configuração de jogo existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuração atualizada com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ConfiguracaoJogoResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Configuração não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracaoJogoResponse> update(
        @Parameter(description = "ID da configuração") 
        @PathVariable Integer id,
        @Parameter(description = "Novos dados da configuração") 
        @Valid @RequestBody ConfiguracaoJogoForm form) {
        ConfiguracaoJogoResponse response = configuracaoJogoService.update(id, form);
        return ResponseEntity.ok(response);
    }


}
