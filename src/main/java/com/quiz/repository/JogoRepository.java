package com.quiz.repository;

import com.quiz.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {
    List<Jogo> findByUsuarioUsuarioIdOrderByPontosDesc(Integer usuarioId);
    List<Jogo> findByNivelNivelIdOrderByPontosDesc(Integer nivelId);
}
