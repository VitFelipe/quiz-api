package com.quiz.response;

import com.quiz.model.ConfiguracaoJogo;
import lombok.Data;

@Data
public class ConfiguracaoJogoResponse {
    private Integer idConfiguracaoJogo;
    private Integer numeroMaximoPerguntas;
    private String templatePrompt;
    
    public static ConfiguracaoJogoResponse fromEntity(ConfiguracaoJogo configuracaoJogo) {
        ConfiguracaoJogoResponse response = new ConfiguracaoJogoResponse();
        response.setIdConfiguracaoJogo(configuracaoJogo.getIdConfiguracaoJogo());
        response.setNumeroMaximoPerguntas(configuracaoJogo.getNumeroMaximoPerguntas());
        response.setTemplatePrompt(configuracaoJogo.getTemplatePrompt());
        return response;
    }
}
