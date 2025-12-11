package com.quiz.service;

import com.quiz.form.PerguntaForm;
import com.quiz.model.Assunto;
import com.quiz.model.Opcao;
import com.quiz.model.Pergunta;
import com.quiz.model.Usuario;
import com.quiz.repository.AssuntoRepository;
import com.quiz.repository.OpcaoRepository;
import com.quiz.repository.PerguntaRepository;
import com.quiz.response.PerguntaComOpcaoResponse;
import com.quiz.response.PerguntaResumoResponse;
import com.quiz.response.PerguntaSemOpcaoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final AssuntoRepository assuntoRepository;
    private final OpcaoRepository opcaoRepository;
    private final UsuarioService usuarioService;
    
    @Transactional
    public void createPerguntasIA(List<PerguntaForm> forms) {
        forms.forEach(form -> createPergunta(form));
    }


    @Transactional
    public PerguntaComOpcaoResponse createPergunta(PerguntaForm form) {
        Assunto assunto = assuntoRepository.findById(form.getAssuntoId())
            .orElseThrow(() -> new EntityNotFoundException("Assunto não encontrado"));
            
        Usuario usuario = usuarioService.findById(1);
        
        Pergunta pergunta = new Pergunta();
        pergunta.setDescricaoPergunta(form.getDescricaoPergunta());
        pergunta.setDescricaoResposta(form.getDescricaoResposta());
        pergunta.setAssunto(assunto);
        pergunta.setUsuarioCadastro(usuario);
        pergunta.setAtivo(form.getAtivo());
         

        if (form.getOpcoes() == null || form.getOpcoes().isEmpty()) {
            throw new IllegalArgumentException("É necessário informar pelo menos duas opções");
        }


        List<Opcao> opcoes = new ArrayList<>();
        for (PerguntaForm.OpcaoForm opcaoForm : form.getOpcoes()) {
            Opcao opcao = new Opcao();
            opcao.setDescricao(opcaoForm.getDescricao());
            opcao.setOpcaoCorreta(opcaoForm.getOpcaoCorreta());
            opcao.setOrdem(opcaoForm.getOrdem());
            opcao.setPergunta(pergunta);
            opcoes.add(opcao);
        }
        
        pergunta.setOpcoes(opcoes);
        return PerguntaComOpcaoResponse.fromEntity(perguntaRepository.save(pergunta));
    }
    
    public List<PerguntaSemOpcaoResponse> findPerguntasByAssunto(Integer assuntoId) {
        return perguntaRepository.findByAssuntoAssuntoIdAndAtivoTrue(assuntoId)
            .stream()
            .map(PerguntaSemOpcaoResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
  
    
    public PerguntaComOpcaoResponse findById(Integer id) {
        return perguntaRepository.findById(id)
            .map(PerguntaComOpcaoResponse::fromEntity)
            .orElseThrow(() -> new EntityNotFoundException("Pergunta não encontrada"));
    }

    public Pergunta findByIdEntity(Integer id) {
        return perguntaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pergunta não encontrada"));
    }

    public Page<PerguntaResumoResponse> findByDescricaoByOrNivelId(String termo, Integer nivelId, Pageable pageable) {
        return perguntaRepository.findByDescricaoContainingIgnoreCaseAndAssuntoNivelNivelId(termo, nivelId, pageable);
    }

    @Transactional
    public void delete(Integer id) {
        Pergunta pergunta = findByIdEntity(id);
        perguntaRepository.delete(pergunta);
    }

    @Transactional
    public PerguntaComOpcaoResponse updateFromForm(Integer id, PerguntaForm form) {
        Pergunta pergunta = findByIdEntity(id);
        Assunto assunto = assuntoRepository.findById(form.getAssuntoId())
            .orElseThrow(() -> new EntityNotFoundException("Assunto não encontrado"));

        pergunta.setDescricaoPergunta(form.getDescricaoPergunta());
        pergunta.setDescricaoResposta(form.getDescricaoResposta());
        pergunta.setAssunto(assunto);
        pergunta.setAtivo(form.getAtivo());

        if (form.getOpcoes() == null || form.getOpcoes().isEmpty()) {
            throw new IllegalArgumentException("É necessário informar pelo menos duas opções");
        }

        // Clear existing options
        pergunta.getOpcoes().clear();
        opcaoRepository.deleteByPerguntaId(pergunta.getPerguntaId());

        // Add new options
        List<Opcao> opcoes = new ArrayList<>();
        for (PerguntaForm.OpcaoForm opcaoForm : form.getOpcoes()) {
            Opcao opcao = new Opcao();
            opcao.setDescricao(opcaoForm.getDescricao());
            opcao.setOpcaoCorreta(opcaoForm.getOpcaoCorreta());
            opcao.setOrdem(opcaoForm.getOrdem());
            opcao.setPergunta(pergunta);
            opcoes.add(opcao);
            opcaoRepository.save(opcao);
        }
        return PerguntaComOpcaoResponse.fromEntity(perguntaRepository.save(pergunta));
    }
}
