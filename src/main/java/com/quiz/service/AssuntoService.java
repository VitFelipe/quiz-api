package com.quiz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quiz.model.Assunto;
import com.quiz.model.Nivel;
import com.quiz.repository.AssuntoRepository;
import com.quiz.response.AssuntoResponse;
import com.quiz.exception.EntityNotFoundException;
import com.quiz.form.AssuntoForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssuntoService {
    private final AssuntoRepository assuntoRepository;
    private final NivelService nivelService;
    
    public List<AssuntoResponse> findAll() {
        return assuntoRepository.findAll()
            .stream()
            .map(AssuntoResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public Optional<AssuntoResponse> findById(Integer id) {
        return assuntoRepository.findById(id)
            .map(AssuntoResponse::fromEntity);
    }

      
    public List<AssuntoResponse> findByNivel(Integer id) {
        return assuntoRepository.findByNivelNivelId(id)
            .stream()
            .map(AssuntoResponse::fromEntity).toList();
    }
    
    public AssuntoResponse createFromForm(AssuntoForm form) {
        Nivel nivel = nivelService.findById(form.getNivelId());
            
        Assunto assunto = new Assunto();
        assunto.setNome(form.getNome());
        assunto.setNivel(nivel);
        return AssuntoResponse.fromEntity(assuntoRepository.save(assunto));
    }
    
    public AssuntoResponse updateFromForm(Integer id, AssuntoForm form) {
        Assunto assunto = findByIdEntity(id)
            .orElseThrow(() -> new EntityNotFoundException("Assunto não encontrado"));

        Nivel nivel = nivelService.findById(form.getNivelId());
        
        assunto.setNome(form.getNome());
        assunto.setNivel(nivel);
        return AssuntoResponse.fromEntity(assuntoRepository.save(assunto));
    }

    private Optional<Assunto> findByIdEntity(Integer id) {
        return assuntoRepository.findById(id);
    }
    
    public void delete(Integer id) {
        assuntoRepository.deleteById(id);
    }
    
    public Assunto save(Assunto assunto) {
        return assuntoRepository.save(assunto);
    }
    
    public Assunto update(Integer id, Assunto assunto) {
        if (assuntoRepository.existsById(id)) {
            assunto.setAssuntoId(id);
            return assuntoRepository.save(assunto);
        }
        return null;
    }
}
