package com.quiz.repository.custom;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.quiz.response.JogosUsuarioSimplificadoResponse;

@Component
@Mapper
public interface JogoQuery {


     @Select("""
             select  j.jogo_id as jogoId , j.pontos,
j.`data` , 
n.nome as nivel , 
count(*) totalPerguntas, 
sum(case when pj.resposta_correta = true then 1 else 0 end ) as totalAcertos
from quiz.jogo j 
inner join quiz.pergunta_jogo pj on j.jogo_id  = pj.jogo_id 
inner join quiz.nivel n on n.nivel_id  = j.nivel_id 
where 
j.usuario_id  = #{usuarioId}
group by j.jogo_id  , j.`data` , n.nome order by j.`data` desc
             """)
     List<JogosUsuarioSimplificadoResponse> obterJogosPorUsuarioId(Integer usuarioId);
     
     


     
  @Select(value = """
          select 
	avg(acerto_medio.taxa_acerto_medio) * 100 as taxa_media
	from (
	select 
	( sum(case when resposta_correta = true then 1 else 0 end ) / count(*))  as taxa_acerto_medio
	from
	quiz.jogo j 
	inner join quiz.pergunta_jogo pj 
	on j.jogo_id  = pj.jogo_id 
	where 
	j.usuario_id  = #{usuarioId} 
	group by j.jogo_id ) acerto_medio
          """)
     Double obterAcertoMedioPorUsuarioId(Integer usuarioId);


	 


}
