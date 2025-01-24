package com.quiz.service;

import com.quiz.form.JogoForm;
import com.quiz.form.RespostaJogoForm;
import com.quiz.model.*;
import com.quiz.repository.JogoRepository;
import com.quiz.repository.RespostaJogoRepository;
import com.quiz.repository.NivelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JogoService {

    private final JogoRepository jogoRepository;
    private final RespostaJogoRepository respostaJogoRepository;
    private final UsuarioService usuarioService;
    private final PerguntaService perguntaService;
    private final NivelRepository nivelRepository;

    public JogoService(
            JogoRepository jogoRepository,
            RespostaJogoRepository respostaJogoRepository,
            UsuarioService usuarioService,
            PerguntaService perguntaService,
            NivelRepository nivelRepository) {
        this.jogoRepository = jogoRepository;
        this.respostaJogoRepository = respostaJogoRepository;
        this.usuarioService = usuarioService;
        this.perguntaService = perguntaService;
        this.nivelRepository = nivelRepository;
    }

    @Transactional
    public Jogo iniciarJogo(JogoForm form) {
        Usuario usuario = usuarioService.findById(form.getUsuarioId());
        Nivel nivel = nivelRepository.findById(form.getNivelId())
            .orElseThrow(() -> new EntityNotFoundException("Nível não encontrado"));
        
        Jogo jogo = new Jogo();
        jogo.setUsuario(usuario);
        jogo.setNivel(nivel);
        jogo.setData(LocalDateTime.now());
        jogo.setPontos(0.0f);
        jogo.setRanking("Em andamento");
        
        return jogoRepository.save(jogo);
    }
    
    @Transactional
    public RespostaJogo responderPergunta(RespostaJogoForm form) {
        Jogo jogo = jogoRepository.findById(form.getJogoId())
            .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado"));
            
        Pergunta pergunta = perguntaService.findById(form.getPerguntaId());
        
        RespostaJogo resposta = new RespostaJogo();
        resposta.setPergunta(pergunta);
        resposta.setDataInicio(LocalDateTime.now());
        
        Opcao opcaoEscolhida = pergunta.getOpcoes().stream()
            .filter(o -> o.getOpcaoId().equals(form.getOpcaoId()))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Opção não encontrada"));
            
        resposta.setOpcao(opcaoEscolhida);
        resposta.setDataFim(LocalDateTime.now());
        
        if (opcaoEscolhida.getOpcaoCorreta()) {
            float pontuacao = jogo.getPontos() + 10.0f;
            jogo.setPontos(pontuacao);
            jogoRepository.save(jogo);
        }
        
        return respostaJogoRepository.save(resposta);
    }
    
    public List<Jogo> getRankingByNivel(Integer nivelId) {
        return jogoRepository.findByNivelNivelIdOrderByPontosDesc(nivelId);
    }
    
    public List<Jogo> getJogosByUsuario(Integer usuarioId) {
        return jogoRepository.findByUsuarioUsuarioIdOrderByPontosDesc(usuarioId);
    }
}
