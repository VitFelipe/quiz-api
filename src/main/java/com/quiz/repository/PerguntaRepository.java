package com.quiz.repository;

import com.quiz.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
    List<Pergunta> findByNivelNivelIdAndAtivoTrue(Integer nivelId);
    
    @Query(value = "SELECT p FROM Pergunta p WHERE p.nivel.nivelId = :nivelId AND p.ativo = true ORDER BY RAND() LIMIT :limit")
    List<Pergunta> findRandomPerguntasByNivel(Integer nivelId, Integer limit);
}
