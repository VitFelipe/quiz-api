package com.quiz.service;

import com.quiz.dto.InforJogoDTO;
import com.quiz.dto.JogoResumoDTO;
import com.quiz.form.JogoForm;
import com.quiz.model.*;
import com.quiz.repository.JogoRepository;
import com.quiz.repository.NivelRepository;
import com.quiz.repository.custom.JogoQuery;
import com.quiz.response.JogoComPerguntasResponse;
import com.quiz.response.JogosUsuarioSimplificadoResponse;
import com.quiz.response.ResumoJogoResponse;
import com.quiz.response.ResumoJogosResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JogoService {

    private final JogoRepository jogoRepository;
    private final UsuarioService usuarioService;
    private final NivelRepository nivelRepository;
    private final PerguntaJogoService perguntaJogoService;
    private final JogoQuery jogoQuery;

 
    @Transactional
    public JogoComPerguntasResponse iniciarJogo(JogoForm form) {
        Usuario usuario = usuarioService.findById(1);     /*TODO: Pegar o usuario logado   */

        Nivel nivel = nivelRepository.findById(form.getNivelId())  
            .orElseThrow(() -> new EntityNotFoundException("Nível não encontrado"));
        
        Jogo jogo = new Jogo();
        jogo.setUsuario(usuario);
        jogo.setNivel(nivel);
        jogo.setData(LocalDateTime.now());
        jogo.setPontos(0.0);
        jogo.setRanking("Em andamento");

        List<PerguntaJogo> perguntas = perguntaJogoService.gerarPerguntasPorNivel(form.getNivelId(), jogo);
        jogo.setPerguntas(perguntas);
    
        return new JogoComPerguntasResponse(jogoRepository.saveAndFlush(jogo));
    }







    @Transactional
    public ResumoJogoResponse finalizarJogo(Integer jogoId) {
        JogoResumoDTO resumo =  jogoRepository.obterResumoJogo(jogoId);
        Jogo jogo = jogoRepository.findById(jogoId).orElseThrow(() -> new EntityNotFoundException("Jogo nao encontrado"));
        jogo.finalizarJogo();
        jogo.adiconarPontos(resumo.getTotalPontos());
        jogoRepository.save(jogo);
        return new ResumoJogoResponse(resumo.getTotalPontos(), resumo.getTotalPerguntas(), resumo.getTotalAcertos(), resumo.getTotalErros());
    }


    public ResumoJogosResponse getEstatisticasJogosByUsuario(Integer usuarioId) {

     Double percentualAcertoMedio = jogoQuery.obterAcertoMedioPorUsuarioId(usuarioId);
     InforJogoDTO informacoes =  jogoRepository.obterInformacoesUsuario(usuarioId);   

       return new ResumoJogosResponse(
        informacoes.getTotalJogos(),
        informacoes.getMediaPontos(),
        percentualAcertoMedio
       ) ;
    }


    public List<JogosUsuarioSimplificadoResponse> getListResumo(Integer usuarioId) {
        return jogoQuery.obterJogosPorUsuarioId(usuarioId);
    }

    
 
    
    public List<Jogo> getRankingByNivel(Integer nivelId) {
        return jogoRepository.findByNivelNivelIdOrderByPontosDesc(nivelId);
    }
    
    public List<Jogo> getJogosByUsuario(Integer usuarioId) {
        return jogoRepository.findByUsuarioUsuarioIdOrderByPontosDesc(usuarioId);
    }
}
