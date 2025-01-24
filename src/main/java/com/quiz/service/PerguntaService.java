package com.quiz.service;

import com.quiz.form.PerguntaForm;
import com.quiz.model.Nivel;
import com.quiz.model.Opcao;
import com.quiz.model.Pergunta;
import com.quiz.model.Usuario;
import com.quiz.repository.NivelRepository;
import com.quiz.repository.PerguntaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerguntaService {

    @Autowired
    private PerguntaRepository perguntaRepository;
    
    @Autowired
    private NivelRepository nivelRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Transactional
    public Pergunta createPergunta(PerguntaForm form, Integer usuarioId) {
        Nivel nivel = nivelRepository.findById(form.getNivelId())
            .orElseThrow(() -> new EntityNotFoundException("Nível não encontrado"));
            
        Usuario usuario = usuarioService.findById(usuarioId);
        
        Pergunta pergunta = new Pergunta();
        pergunta.setDescricaoPergunta(form.getDescricaoPergunta());
        pergunta.setDescricaoResposta(form.getDescricaoResposta());
        pergunta.setNivel(nivel);
        pergunta.setUsuarioCadastro(usuario);
        pergunta.setAtivo(true);
        
        List<Opcao> opcoes = new ArrayList<>();
        for (PerguntaForm.OpcaoForm opcaoForm : form.getOpcoes()) {
            Opcao opcao = new Opcao();
            opcao.setDescricao(opcaoForm.getDescricao());
            opcao.setOpcaoCorreta(opcaoForm.getOpcaoCorreta());
            opcao.setPergunta(pergunta);
            opcoes.add(opcao);
        }
        
        pergunta.setOpcoes(opcoes);
        return perguntaRepository.save(pergunta);
    }
    
    public List<Pergunta> findPerguntasByNivel(Integer nivelId) {
        return perguntaRepository.findByNivelNivelIdAndAtivoTrue(nivelId);
    }
    
    public List<Pergunta> getRandomPerguntasForJogo(Integer nivelId, Integer quantidade) {
        return perguntaRepository.findRandomPerguntasByNivel(nivelId, quantidade);
    }
    
    public Pergunta findById(Integer id) {
        return perguntaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pergunta não encontrada"));
    }
}
