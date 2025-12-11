package com.quiz.service;

import com.quiz.exception.BussinessException;
import com.quiz.form.UpdateUsuarioForm;
import com.quiz.form.UsuarioForm;
import com.quiz.model.Usuario;
import com.quiz.response.UsuarioResponse;
import com.quiz.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    // private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario createUsuario(UsuarioForm form) {

        if (usuarioRepository.existsByEmail(form.getEmail())) {
            throw new BussinessException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(form.getNome());
        usuario.setEmail(form.getEmail());
        usuario.setSenha(form.getSenha());
        usuario.setPerfil(form.getPerfil());

        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUsuario(Integer id) {
        findById(id);
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void updateUsuario(Integer id, UpdateUsuarioForm form) {
        Usuario usuario = findById(id);
        usuario.setNome(form.getNome());
        usuario.setEmail(form.getEmail());
        usuario.setPerfil(form.getPerfil());
        usuarioRepository.save(usuario);
    }
}
