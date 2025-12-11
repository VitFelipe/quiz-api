package com.quiz.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PerfilEnum {
    ADMIN("Administrador"),
    NORMAL("Usuário");

    private String descricao;

     PerfilEnum(String descricao) {
        this.descricao = descricao;
    }  

    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static PerfilEnum fromString(String valor) {
        return Stream.of(PerfilEnum.values()).filter(perfil -> perfil.getDescricao().equals(valor) || perfil.name().equals(valor)).
        findFirst().orElseThrow(() -> new IllegalArgumentException("Perfil inválido"));
    }

}
