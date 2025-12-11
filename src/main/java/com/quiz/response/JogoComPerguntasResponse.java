package com.quiz.response;

import java.util.List;
import com.quiz.model.Jogo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogoComPerguntasResponse {

    private Integer jogoId;
    private List<PerguntaJogoComOpcaoResponse> perguntas;

    public JogoComPerguntasResponse(Jogo jogo) {
        this.jogoId = jogo.getJogoId();
        this.perguntas = jogo.getPerguntas().stream()
            .map(PerguntaJogoComOpcaoResponse::new)
            .toList();
    }
}
