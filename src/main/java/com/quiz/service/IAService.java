package com.quiz.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.exception.EntityNotFoundException;
import com.quiz.form.PerguntaForm;
import com.quiz.model.Assunto;
import com.quiz.model.ConfiguracaoJogo;
import com.quiz.repository.AssuntoRepository;
import com.quiz.repository.ConfiguracaoJogoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@lombok.extern.log4j.Log4j2
public class IAService {

    private final ChatClient chatClient;
    private final ConfiguracaoJogoRepository configuracaoJogoRepository;
    private final AssuntoRepository assuntoRepository;
    private final ObjectMapper objectMapper;


    public List<PerguntaForm> generateQuiz(Integer assuntoId, Integer quantidadePerguntas) {
      Assunto assunto =  assuntoRepository.findById(assuntoId)
        .orElseThrow(() -> new EntityNotFoundException("Assunto nao encontrado"));
    String content = chatClient.prompt().
    user(getBuildTextPrompt(assunto.getNivel().getNome(), assunto.getNome(), quantidadePerguntas)).call()
    .content();
    return  convertToResponse(extractResponse(content),assunto);
    }



    private String getBuildTextPrompt(String nomeNivel, String nomeAssunto, Integer quantidadePerguntas) {
        ConfiguracaoJogo configuracaoJogo = configuracaoJogoRepository.findLastConfig().orElseThrow(()->
            new RuntimeException("Nenhuma configuração encontrada"));
          return configuracaoJogo.getTemplatePrompt().
            replace("{quantidade}", quantidadePerguntas.toString())
            .replace("{nivel}", nomeNivel)
            .replace("{assunto}", nomeAssunto);
    }




    private List<PerguntaForm> convertToResponse(String content, Assunto assunto) {
        try{
            if(content == null || content.isBlank()){
                throw new RuntimeException("Mensagem de retorno inválida");
            }
            content.replaceAll("``", "").replaceAll("}}", "");
            return objectMapper.readValue(content, new TypeReference<List<PerguntaForm>>() {
            }).stream()
            .map(pergunta -> {
                pergunta.setAssuntoId(assunto.getAssuntoId());
                return pergunta;
            }).toList();

        }catch (Exception e){
            throw new RuntimeException("Erro ao converter resposta do Modelo");   
        }
       
    }



    private String extractResponse(String content) {
        String [] vetorHeader =  content.split("\\```json");
        return  vetorHeader[vetorHeader.length -1].trim().replaceAll("\\```", "").trim();
    }
    

}
