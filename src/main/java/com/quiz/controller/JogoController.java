package com.quiz.controller;

import com.quiz.form.JogoForm;
import com.quiz.form.RespostaJogoForm;
import com.quiz.model.Jogo;
import com.quiz.model.RespostaJogo;
import com.quiz.service.JogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogos")
@Tag(name = "Jogos", description = "Endpoints para gerenciamento dos jogos do quiz")
public class JogoController {

    @Autowired
    private JogoService jogoService;
    
    @Operation(summary = "Iniciar novo jogo", description = "Inicia uma nova sessão de jogo para um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jogo iniciado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Jogo.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<Jogo> iniciarJogo(
        @Parameter(description = "Dados para iniciar o jogo") 
        @Valid @RequestBody JogoForm form) {
        Jogo jogo = jogoService.iniciarJogo(form);
        return ResponseEntity.ok(jogo);
    }
    
    @Operation(summary = "Responder pergunta", description = "Registra a resposta do usuário para uma pergunta do quiz")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resposta registrada com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RespostaJogo.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Jogo ou pergunta não encontrados")
    })
    @PostMapping("/resposta")
    public ResponseEntity<RespostaJogo> responderPergunta(
        @Parameter(description = "Dados da resposta do usuário") 
        @Valid @RequestBody RespostaJogoForm form) {
        RespostaJogo resposta = jogoService.responderPergunta(form);
        return ResponseEntity.ok(resposta);
    }
    
    @Operation(summary = "Listar ranking por nível", description = "Retorna o ranking dos jogos por nível")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ranking encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Jogo.class)) }),
        @ApiResponse(responseCode = "404", description = "Nível não encontrado")
    })
    @GetMapping("/ranking/nivel/{nivelId}")
    public ResponseEntity<List<Jogo>> getRankingByNivel(
        @Parameter(description = "ID do nível") 
        @PathVariable Integer nivelId) {
        List<Jogo> ranking = jogoService.getRankingByNivel(nivelId);
        return ResponseEntity.ok(ranking);
    }
    
    @Operation(summary = "Listar jogos por usuário", description = "Retorna os jogos de um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jogos encontrados",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Jogo.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Jogo>> getJogosByUsuario(
        @Parameter(description = "ID do usuário") 
        @PathVariable Integer usuarioId) {
        List<Jogo> jogos = jogoService.getJogosByUsuario(usuarioId);
        return ResponseEntity.ok(jogos);
    }
}
