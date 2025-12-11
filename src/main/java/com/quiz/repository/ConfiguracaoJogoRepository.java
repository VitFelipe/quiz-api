package com.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quiz.model.ConfiguracaoJogo;

public interface ConfiguracaoJogoRepository extends JpaRepository<ConfiguracaoJogo, Integer> {

    @Query(value = "select * from configuracao_jogo cj  order by cj.idconfiguracao_jogo desc limit 1", nativeQuery = true)
   Optional<ConfiguracaoJogo> findLastConfig();

}
