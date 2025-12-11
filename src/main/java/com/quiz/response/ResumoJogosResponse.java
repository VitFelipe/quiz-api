package com.quiz.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumoJogosResponse {
  private Integer quantidadeJogos;
  private Double mediaPontos;
  private Double mediaAcertos; 

  public Double getMediaPontos() {
    if(mediaPontos == null){
      return 0.0;
    }
    return new BigDecimal(mediaPontos).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public Double getMediaAcertos(){
    if(mediaAcertos == null){
      return 0.0;   
    }
    return new BigDecimal(mediaAcertos).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }
}
