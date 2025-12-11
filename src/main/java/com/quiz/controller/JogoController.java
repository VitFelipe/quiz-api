package com.quiz.controller;

import com.quiz.form.JogoForm;
import com.quiz.model.Jogo;
import com.quiz.response.JogoComPerguntasResponse;
import com.quiz.response.JogosUsuarioSimplificadoResponse;
import com.quiz.response.ResumoJogoResponse;
import com.quiz.response.ResumoJogosResponse;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/jogos")
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
    public ResponseEntity<JogoComPerguntasResponse> iniciarJogo(
        @Parameter(description = "Dados para iniciar o jogo") 
        @Valid @RequestBody JogoForm form) {
        JogoComPerguntasResponse jogoResponse = jogoService.iniciarJogo(form);
        return ResponseEntity.ok(jogoResponse);
    }

    @Operation(summary = "Finalizar jogo", description = "Finaliza uma sessão de jogo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jogo finalizado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Jogo.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Jogo não encontrado")
    })
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<ResumoJogoResponse> finalizar(@Parameter(description = "ID do jogo a ser finalizado") @PathVariable Integer id) {
         ResumoJogoResponse resumo =  jogoService.finalizarJogo(id);
        return ResponseEntity.ok(resumo);
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


    @Operation(summary = "Estatísticas de jogos", description = "Retorna Estatísticas de jogos de um usuário")
    @GetMapping("/estatisticas/{usuarioId}")
    public ResponseEntity<ResumoJogosResponse> getResumoJogosByUsuario(
        @Parameter(description = "ID do usuário") 
        @PathVariable Integer usuarioId) {
        ResumoJogosResponse resumo = jogoService.getEstatisticasJogosByUsuario(usuarioId);
        return ResponseEntity.ok(resumo);
    }

    @Operation(summary = "Listar resumos de jogos", description = "Retorna uma lista com os resumos de jogos de um usuário")
    @GetMapping("/lista-resumos/{usuarioId}")
    public ResponseEntity<List<JogosUsuarioSimplificadoResponse>> getListResumo(
        @Parameter(description = "ID do usuário") 
        @PathVariable Integer usuarioId) {
        return ResponseEntity.ok(jogoService.getListResumo(usuarioId));
    }
}
