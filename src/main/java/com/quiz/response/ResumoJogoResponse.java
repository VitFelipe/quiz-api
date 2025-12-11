package com.quiz.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResumoJogoResponse {
 private Double pontos;
 private Integer totalPerguntas;
 private Integer totalAcertos;
 private Integer totalErros;

 public Double getPercentualAcertos() {
  Double porcentagem =     (Double.valueOf(totalAcertos) * 100) / Double.valueOf(totalPerguntas);
  return new BigDecimal(porcentagem).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public double getPontos(){
        if(this.pontos == null){
            return 0.0; 
        }
        return new BigDecimal(this.pontos).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
