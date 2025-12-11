package com.quiz.service;

import com.quiz.form.ConfiguracaoJogoForm;
import com.quiz.model.ConfiguracaoJogo;
import com.quiz.repository.ConfiguracaoJogoRepository;
import com.quiz.response.ConfiguracaoJogoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfiguracaoJogoService {

    private final ConfiguracaoJogoRepository configuracaoJogoRepository;

    @Transactional(readOnly = true)
    public ConfiguracaoJogoResponse findLastConfig() {
        ConfiguracaoJogo configuracaoJogo = configuracaoJogoRepository.findLastConfig()
            .orElseThrow(() -> new EntityNotFoundException("Configuração de jogo não encontrada"));
        return ConfiguracaoJogoResponse.fromEntity(configuracaoJogo);
    }

    @Transactional
    public ConfiguracaoJogoResponse create(ConfiguracaoJogoForm form) {
        ConfiguracaoJogo configuracaoJogo = new ConfiguracaoJogo();
        configuracaoJogo.setNumeroMaximoPerguntas(form.getNumeroMaximoPerguntas());
        configuracaoJogo.setTemplatePrompt(form.getTemplatePrompt());
        
        configuracaoJogo = configuracaoJogoRepository.save(configuracaoJogo);
        return ConfiguracaoJogoResponse.fromEntity(configuracaoJogo);
    }

    @Transactional
    public ConfiguracaoJogoResponse update(Integer id, ConfiguracaoJogoForm form) {
        ConfiguracaoJogo configuracaoJogo = configuracaoJogoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Configuração de jogo não encontrada"));
            
        configuracaoJogo.setNumeroMaximoPerguntas(form.getNumeroMaximoPerguntas());
        configuracaoJogo.setTemplatePrompt(form.getTemplatePrompt());
        
        configuracaoJogo = configuracaoJogoRepository.save(configuracaoJogo);
        return ConfiguracaoJogoResponse.fromEntity(configuracaoJogo);
    }

    @Transactional
    public void delete(Integer id) {
        if (!configuracaoJogoRepository.existsById(id)) {
            throw new EntityNotFoundException("Configuração de jogo não encontrada");
        }
        configuracaoJogoRepository.deleteById(id);
    }
}
