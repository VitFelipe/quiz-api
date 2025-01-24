package com.quiz.repository;

import com.quiz.model.RespostaJogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaJogoRepository extends JpaRepository<RespostaJogo, Integer> {
}
