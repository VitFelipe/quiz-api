package com.quiz.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quiz.model.ConfiguracaoJogo;
import com.quiz.model.Jogo;
import com.quiz.model.Pergunta;
import com.quiz.model.PerguntaJogo;
import com.quiz.repository.ConfiguracaoJogoRepository;
import com.quiz.repository.PerguntaJogoRepository;
import com.quiz.repository.PerguntaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerguntaJogoService {

    private final PerguntaRepository perguntaRepository;
    private final PerguntaJogoRepository perguntaJogoRepository;
    private final ConfiguracaoJogoRepository configuracaoJogoRepository;

    public List<PerguntaJogo> gerarPerguntasPorNivel(Integer nivelId, Jogo jogo) {
       ConfiguracaoJogo configuracaoJogo =  configuracaoJogoRepository.findLastConfig().orElseThrow(()-> new RuntimeException("Nenhuma configuração encontrada"));
        List<Pergunta> listaPerguntas = perguntaRepository.findByNivelNivelIdOrderByPerguntaId(nivelId, configuracaoJogo.getNumeroMaximoPerguntas());
        return listaPerguntas.stream().map(pergunta -> new PerguntaJogo(pergunta, jogo)).toList();
    }


  

}
