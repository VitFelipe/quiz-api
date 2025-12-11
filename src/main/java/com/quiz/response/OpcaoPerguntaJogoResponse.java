package com.quiz.response;

import com.quiz.model.OpcaoPerguntaJogo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpcaoPerguntaJogoResponse {
    private Integer id;
    private String descricao;
    private Boolean correta;

    public OpcaoPerguntaJogoResponse(OpcaoPerguntaJogo opcao) {
        this.id = opcao.getId();
        this.descricao = opcao.getDescricao();
        this.correta = opcao.getCorreta();
    }
}
