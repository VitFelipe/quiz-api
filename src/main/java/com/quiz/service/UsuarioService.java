package com.quiz.service;

import com.quiz.form.UsuarioForm;
import com.quiz.model.Usuario;
import com.quiz.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    
    
    @Transactional
    public Usuario createUsuario(UsuarioForm form) {
        if (usuarioRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNome(form.getNome());
        usuario.setEmail(form.getEmail());
      //  usuario.setSenha(passwordEncoder.encode(form.getSenha()));
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
}
