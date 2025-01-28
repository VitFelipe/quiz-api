package com.quiz.controller;

import com.quiz.form.PerguntaForm;
import com.quiz.response.PerguntaComOpcaoResponse;
import com.quiz.response.PerguntaResumoResponse;
import com.quiz.response.PerguntaSemOpcaoResponse;
import com.quiz.service.PerguntaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/perguntas")
@Tag(name = "Perguntas", description = "Endpoints para gerenciamento das perguntas do quiz")
@RequiredArgsConstructor
@Log4j2
public class PerguntaController {

    private final PerguntaService perguntaService;

    @Operation(summary = "Criar nova pergunta", description = "Cria uma nova pergunta no sistema do quiz")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pergunta criada com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PerguntaComOpcaoResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Assunto não encontrado")
    })
    @PostMapping
    public ResponseEntity<PerguntaComOpcaoResponse> createPergunta(
        @Parameter(description = "Dados da pergunta a ser criada") 
        @Valid @RequestBody PerguntaForm form) {
        PerguntaComOpcaoResponse pergunta = perguntaService.createPergunta(form);
        return ResponseEntity.ok(pergunta);
    }

    @Operation(summary = "Buscar perguntas por assunto", description = "Retorna todas as perguntas de um assunto específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perguntas encontradas",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PerguntaSemOpcaoResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "Assunto não encontrado")
    })
    @GetMapping("/assunto/{assuntoId}")
    public ResponseEntity<List<PerguntaSemOpcaoResponse>> getPerguntasByAssunto(
        @Parameter(description = "ID do assunto") 
        @PathVariable Integer assuntoId) {
        List<PerguntaSemOpcaoResponse> perguntas = perguntaService.findPerguntasByAssunto(assuntoId);
        return ResponseEntity.ok(perguntas);
    }



    @Operation(summary = "Buscar perguntas por descrição", 
              description = "Retorna perguntas que contenham o termo buscado na descrição")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perguntas encontradas",
            content = { @Content(mediaType = "application/json", 
                       schema = @Schema(implementation = PerguntaResumoResponse.class)) })
    })
    @GetMapping("/busca")
    public ResponseEntity<Page<PerguntaResumoResponse>> findByDescricao(
        @Parameter(description = "Termo a ser buscado na descrição da pergunta") 
        @RequestParam(required = false) String query,
        @RequestParam(required = false) Integer nivelId,
        @Parameter(description = "Informações de paginação (página, tamanho, ordenação)")
        @PageableDefault(size = 10) Pageable pageable) {
        log.info("Buscando perguntas com termo: {} e nivelId: {}", query, nivelId);
        return ResponseEntity.ok(perguntaService.findByDescricaoByOrNivelId(query, nivelId,  pageable));    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        perguntaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<PerguntaComOpcaoResponse> getFullPergunta(@PathVariable Integer id) {
        return ResponseEntity.ok(perguntaService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerguntaComOpcaoResponse> updatePergunta(
        @PathVariable Integer id,
        @RequestBody @Valid PerguntaForm form) {
            return ResponseEntity.ok(perguntaService.updateFromForm(id, form));
    }
 
    
    

}
