package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.AnswerDTO;
import com.humanup.matrix.ui.apimanagement.dto.ChoiceDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryAnswer;
import com.humanup.matrix.ui.apimanagement.proxy.AnswerProxy;
import com.humanup.matrix.ui.apimanagement.proxy.ChoiceProxy;
import com.humanup.matrix.ui.apimanagement.vo.AnswerVO;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AnswerQuery implements GraphQLQueryResolver, IQueryAnswer {
  private static final Logger LOGGER = LoggerFactory.getLogger(AnswerQuery.class);

  @Autowired AnswerProxy answerProxy;
  @Autowired ChoiceProxy choiceProxy;

  @Override
  public List<AnswerVO> getListAnswerByEmail(@NotNull String email, DataFetchingEnvironment env) {
    String token = ObjectBuilder.getTokenFromGraphQL(env);
    List<AnswerDTO> answerListDTO = null;
    try {
      answerListDTO =
              ObjectBuilder.mapper.readValue(
                      answerProxy.findListAnswerByEmail(token, email), new TypeReference<List<AnswerDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<TrainerDTO> ", e);
    }
    return listAnswerVO(answerListDTO);
  }

  public List<AnswerVO> listAnswerVO(List<AnswerDTO> listAnswerDTO){
    return Optional.ofNullable(listAnswerDTO).orElse(Collections.emptyList()).stream()
          .map(
                  answer ->
                          AnswerVO.builder()
                                  .choiceId(answer.getChoiceId())
                                  .emailPerson(answer.getEmailPerson())
                                  .build())
          .collect(Collectors.toList());
}
  @Override
  public List<AnswerVO> getListAnswer(DataFetchingEnvironment env) {
    String token = ObjectBuilder.getTokenFromGraphQL(env);
    List<AnswerDTO> answerListDTO = null;
    try {
      answerListDTO =
          ObjectBuilder.mapper.readValue(
              answerProxy.findAllAnswer(token), new TypeReference<List<AnswerDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<AnswerDTO> ", e);
    }
    return answerListDTO.stream()
        .map(
            c -> {
              ChoiceDTO choiceDTO = null;
              try {
                choiceDTO =
                    ObjectBuilder.mapper.readValue(
                        choiceProxy.findChoicesByChoiceId(c.getChoiceId()), ChoiceDTO.class);
              } catch (JsonProcessingException e) {
                LOGGER.error("Exception Parsing Answer {}", e);
              }
              return AnswerVO.builder()
                      .choiceId(c.getChoiceId())
                      .emailPerson(c.getEmailPerson())
                      .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  public AnswerVO getAnswerByChoiceId(@NotNull int choiceId, DataFetchingEnvironment env) {
    String token = ObjectBuilder.getTokenFromGraphQL(env);
  AnswerDTO answerDTO = null;
    try {
      answerDTO =
          ObjectBuilder.mapper.readValue(
              answerProxy.findAnswerByChoiceId(token, choiceId), AnswerDTO.class);
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<AnswerVO> ", e);
    }
    return AnswerVO.builder()
            .emailPerson(answerDTO.getEmailPerson())
            .choiceId(answerDTO.getChoiceId())
        .build();
  }

}
