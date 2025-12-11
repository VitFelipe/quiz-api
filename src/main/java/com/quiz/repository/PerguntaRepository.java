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

    @Query(value = """
    
select p.* from quiz_aleitamento.pergunta p
inner join quiz_aleitamento.assunto a on a.assunto_id  = p.assunto_id 
inner join quiz_aleitamento.nivel n on n.nivel_id  = a.nivel_id 
left join (select pergunta_id , count(*) as total from quiz_aleitamento.pergunta_jogo pj 
group by pergunta_id ) pg_total on pg_total.pergunta_id = p.pergunta_id 
where 
a.nivel_id = :nivelId  
order by pg_total.total asc
LIMIT :quantidadeMaxima
    """,nativeQuery = true)
List<Pergunta> findByNivelNivelIdOrderByPerguntaId(@Param("nivelId") Integer nivelId, @Param("quantidadeMaxima") Integer quantidadeMaxima);  
}
