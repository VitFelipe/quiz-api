package com.quiz.repository;

import com.quiz.model.Opcao;
import com.quiz.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcaoRepository extends JpaRepository<Opcao, Integer> {
    List<Opcao> findByPergunta(Pergunta pergunta);
    boolean existsByPerguntaAndOpcaoCorretaTrue(Pergunta pergunta);
}
