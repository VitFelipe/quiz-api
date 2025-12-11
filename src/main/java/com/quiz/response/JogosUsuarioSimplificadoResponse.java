package com.quiz.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JogosUsuarioSimplificadoResponse {

    private Integer jogoId;
    private Double pontos;
    private Date data;
    private String nivel;
    private Integer totalPerguntas;
    private Integer totalAcertos;

    public double getTaxaAcerto(){

        if(totalAcertos == null || totalPerguntas == null){ 
            return 0.0;
        }
       double taxa =  (Double.valueOf(totalAcertos) * 100) / Double.valueOf(totalPerguntas);
        return new BigDecimal(taxa).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public double getPontos() {
        if(pontos == null){
            return 0.0;
        }
        return new BigDecimal(pontos).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
