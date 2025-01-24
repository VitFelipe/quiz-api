package com.quiz.service;

import com.quiz.form.OpcaoForm;
import com.quiz.model.Opcao;
import com.quiz.model.Pergunta;
import com.quiz.repository.OpcaoRepository;
import com.quiz.repository.PerguntaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OpcaoService {

    private final OpcaoRepository opcaoRepository;
    private final PerguntaRepository perguntaRepository;

    public OpcaoService(OpcaoRepository opcaoRepository, PerguntaRepository perguntaRepository) {
        this.opcaoRepository = opcaoRepository;
        this.perguntaRepository = perguntaRepository;
    }

    @Transactional
    public Opcao createOpcao(OpcaoForm form) {
        Pergunta pergunta = perguntaRepository.findById(form.getPerguntaId())
            .orElseThrow(() -> new EntityNotFoundException("Pergunta não encontrada"));

        // Verificar se já existe uma opção correta para esta pergunta
        if (form.getCorreta()) {
            boolean existeOpcaoCorreta = opcaoRepository.existsByPerguntaAndOpcaoCorretaTrue(pergunta);
            if (existeOpcaoCorreta) {
                throw new IllegalStateException("Já existe uma opção correta para esta pergunta");
            }
        }

        Opcao opcao = new Opcao();
        opcao.setDescricao(form.getTexto());
        opcao.setOpcaoCorreta(form.getCorreta());
        opcao.setPergunta(pergunta);

        return opcaoRepository.save(opcao);
    }

    @Transactional
    public Opcao updateOpcao(Integer id, OpcaoForm form) {
        Opcao opcao = opcaoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Opção não encontrada"));

        // Se estiver alterando a pergunta
        if (!opcao.getPergunta().getPerguntaId().equals(form.getPerguntaId())) {
            Pergunta novaPergunta = perguntaRepository.findById(form.getPerguntaId())
                .orElseThrow(() -> new EntityNotFoundException("Pergunta não encontrada"));
            opcao.setPergunta(novaPergunta);
        }

        // Se estiver marcando como correta, verificar outras opções
        if (form.getCorreta() && !opcao.getOpcaoCorreta()) {
            boolean existeOpcaoCorreta = opcaoRepository.existsByPerguntaAndOpcaoCorretaTrue(opcao.getPergunta());
            if (existeOpcaoCorreta) {
                throw new IllegalStateException("Já existe uma opção correta para esta pergunta");
            }
        }

        opcao.setDescricao(form.getTexto());
        opcao.setOpcaoCorreta(form.getCorreta());

        return opcaoRepository.save(opcao);
    }

    public List<Opcao> findByPerguntaId(Integer perguntaId) {
        Pergunta pergunta = perguntaRepository.findById(perguntaId)
            .orElseThrow(() -> new EntityNotFoundException("Pergunta não encontrada"));
        return opcaoRepository.findByPergunta(pergunta);
    }

    public void deleteOpcao(Integer id) {
        if (!opcaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Opção não encontrada");
        }
        opcaoRepository.deleteById(id);
    }
}
