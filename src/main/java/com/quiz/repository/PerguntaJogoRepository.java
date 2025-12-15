package com.quiz.repository;

import com.quiz.model.PerguntaJogo;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerguntaJogoRepository extends JpaRepository<PerguntaJogo, Integer> {

    @Modifying
    @Transactional
    @Query(value="update quiz.pergunta_jogo set resposta_correta = :resposta, pontos = :pontos where pergunta_jogo_id = :perguntaJogoId", nativeQuery = true)
     void atualizarPerguntaComResposta(@Param("resposta") Boolean resposta, @Param("pontos") Double pontos, @Param("perguntaJogoId") Integer perguntaJogoId);
}
