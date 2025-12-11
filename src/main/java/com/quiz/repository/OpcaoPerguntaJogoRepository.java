package com.quiz.repository;

import com.quiz.model.OpcaoPerguntaJogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcaoPerguntaJogoRepository extends JpaRepository<OpcaoPerguntaJogo, Integer> {
    // Métodos personalizados podem ser adicionados aqui conforme necessário
}
