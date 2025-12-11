package com.quiz.service;


import org.springframework.stereotype.Service;

import com.quiz.form.RespostaForm;
import com.quiz.model.Jogo;
import com.quiz.model.OpcaoPerguntaJogo;
import com.quiz.repository.JogoRepository;
import com.quiz.repository.OpcaoPerguntaJogoRepository;
import com.quiz.repository.PerguntaJogoRepository;
import com.quiz.response.RespostaResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RespostaService {
    
    
    private final OpcaoPerguntaJogoRepository opcaoRepository;
    private final Double PONTUACAO_BASE = 100.0;
    private final PerguntaJogoRepository perguntaJogoRepository;
    private final JogoRepository jogoRepository;
    
    public RespostaResponse verificarResposta(RespostaForm respostaForm) {
        // Busca a opção escolhida pelo usuário
        OpcaoPerguntaJogo opcaoEscolhida = opcaoRepository.findById(respostaForm.getOpcaoIdEscolhida())
            .orElseThrow(() -> new RuntimeException("Opção não encontrada"));
        
        // Verifica se a opção está correta
        boolean respostaCorreta = opcaoEscolhida.getCorreta();

        Integer tempoMaximo = opcaoEscolhida.getPerguntaJogo().getAssunto().getNivel().getTempoMaximoResposta();
        
        // Calcula a pontuação baseada no tempo restante como peso
        Double pontuacao = respostaCorreta ? 
            calcularPontuacao(respostaForm.getTempoRestante(), tempoMaximo) : 0.0;

          perguntaJogoRepository.atualizarPerguntaComResposta(respostaCorreta, pontuacao, opcaoEscolhida.getPerguntaJogo().getId());
        
           Jogo jogo = jogoRepository.findById(opcaoEscolhida.getPerguntaJogo().getJogo().getJogoId())
               .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
            
            jogo.incrementarPontos(pontuacao);   
            jogoRepository.save(jogo);   

        
        // Cria a mensagem apropriada
        String mensagem = opcaoEscolhida.getPerguntaJogo().getDescricaoResposta();
            
        return new RespostaResponse(respostaCorreta, mensagem, pontuacao);
    }
    
    private Double calcularPontuacao(int tempoRestante, int maxTempoQuestao) {
        // Pontuação base por acertar
       
        
        // Calcula o peso baseado no tempo restante (quanto mais tempo sobrar, maior o peso)
        // O peso será um valor entre 0.5 e 1.5, onde:
        // - Se usar todo o tempo (tempoRestante = 0), o peso será 0.5
        // - Se usar metade do tempo, o peso será 1.0
        // - Se responder instantaneamente (tempoRestante = maxTempoQuestao), o peso será 1.5
        double peso = 0.5 + ((double) tempoRestante / maxTempoQuestao);
        
        // Aplica o peso à pontuação base
        return  PONTUACAO_BASE * peso;
    }
}
