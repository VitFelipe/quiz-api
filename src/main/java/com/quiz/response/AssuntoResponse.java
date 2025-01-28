package com.quiz.response;

import com.quiz.model.Assunto;
import lombok.Getter;

@Getter
public class AssuntoResponse {
    private Integer assuntoId;
    private String nome;
    private Integer nivelId;
    private String nivelNome;

    public AssuntoResponse(Assunto assunto) {
        this.assuntoId = assunto.getAssuntoId();
        this.nome = assunto.getNome();
        this.nivelId = assunto.getNivel().getNivelId();
        this.nivelNome = assunto.getNivel().getNome();
    }

    public static AssuntoResponse fromEntity(Assunto assunto) {
        return new AssuntoResponse(assunto);
    }
}
