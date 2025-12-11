package com.quiz.controller;

import com.quiz.form.UpdateUsuarioForm;
import com.quiz.form.UsuarioForm;
import com.quiz.model.Usuario;
import com.quiz.response.UsuarioResponse;
import com.quiz.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários do quiz")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema do quiz")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(
        @Parameter(description = "Dados do usuário a ser criado") 
        @Valid @RequestBody UsuarioForm form) {
        Usuario usuario = usuarioService.createUsuario(form);
        return ResponseEntity.ok(usuario);
    }
    
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico baseado no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuario(
        @Parameter(description = "ID do usuário a ser buscado") 
        @PathVariable Integer id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuario) );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(
        @Parameter(description = "ID do usuário a ser deletado") 
        @PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponse.class)) })
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {
        List<UsuarioResponse> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Atualizar usuário", description = "Atualiza um usuário existente no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuario(
        @Parameter(description = "ID do usuário a ser atualizado") 
        @PathVariable Integer id,
        @Parameter(description = "Dados do usuário a ser atualizado") 
        @RequestBody @Valid UpdateUsuarioForm form) {
            usuarioService.updateUsuario(id, form);
            return ResponseEntity.ok().build() ;
        }
}
