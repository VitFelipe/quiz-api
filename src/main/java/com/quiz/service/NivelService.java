package com.quiz.service;

import com.quiz.form.NivelForm;
import com.quiz.model.Nivel;
import com.quiz.repository.NivelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NivelService {

    private final NivelRepository nivelRepository;

    public NivelService(NivelRepository nivelRepository) {
        this.nivelRepository = nivelRepository;
    }

    @Transactional
    public Nivel createNivel(NivelForm form) {
        Nivel nivel = new Nivel();
        nivel.setNome(form.getNome());
        nivel.setDescricao(form.getDescricao());
        nivel.setTempoMaximoResposta(form.getTempoMaximoResposta());
        return nivelRepository.save(nivel);
    }

    public List<Nivel> getAllNiveis() {
        return nivelRepository.findAll();
    }

    public Nivel findById(Integer id) {
        return nivelRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Nível não encontrado"));
    }
}
