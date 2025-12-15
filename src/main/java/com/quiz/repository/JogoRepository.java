package com.quiz.repository;

import com.quiz.dto.InforJogoDTO;
import com.quiz.dto.JogoResumoDTO;
import com.quiz.model.Jogo;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {
    List<Jogo> findByUsuarioUsuarioIdOrderByPontosDesc(Integer usuarioId);

    List<Jogo> findByNivelNivelIdOrderByPontosDesc(Integer nivelId);

    @Modifying
    @Transactional
    @Query(value = "update quiz.jogo set pontos = :pontos where jogo_id = :jogoId", nativeQuery = true)
    void atualizarPontos(@Param("pontos") Float pontos, @Param("jogoId") Integer jogoId);

    @Query(value = """
                        select
            	SUM(pontos) as totalPontos,
            	count(*) as totalPerguntas,
            	sum(case when pj.resposta_correta = true then 1 else 0 end) as totalAcertos,
            	sum(case when pj.resposta_correta = false then 1 else 0 end) as totalErros
            from
            	quiz.pergunta_jogo pj
            where
            	pj.jogo_id = :jogoId
                        """, nativeQuery = true)
    JogoResumoDTO obterResumoJogo(@Param("jogoId") Integer jogoId);

    @Query(value = """
                        select
            	count(*) as totalJogos, avg(j.pontos) as mediaPontos
            from
            	quiz.jogo j
            where
            	j.usuario_id = :usuarioId
                        """, nativeQuery = true)
    InforJogoDTO obterInformacoesUsuario(Integer usuarioId);

}
