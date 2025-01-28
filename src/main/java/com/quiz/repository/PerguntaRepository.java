package com.quiz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quiz.model.Pergunta;
import com.quiz.response.PerguntaResumoResponse;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
    
    List<Pergunta> findByAssuntoAssuntoIdAndAtivoTrue(Integer assuntoId);
    
  

    @Query(" SELECT new com.quiz.response.PerguntaResumoResponse(" +
           " p.perguntaId, p.descricaoPergunta, p.ativo, " +
           " p.assunto.nome, p.assunto.nivel.nome, p.usuarioCadastro.nome) " +
           " FROM Pergunta p " +
           " WHERE (case when :termo is null then true else UPPER(p.descricaoPergunta) LIKE UPPER(CONCAT('%', :termo, '%')) end) "+
           " AND  (case when :nivelId is null then true else p.assunto.nivel.nivelId = :nivelId end)")
    Page<PerguntaResumoResponse> findByDescricaoContainingIgnoreCaseAndAssuntoNivelNivelId(
        @Param("termo") String termo,
        @Param("nivelId") Integer nivelId,
        Pageable pageable
    );
}
