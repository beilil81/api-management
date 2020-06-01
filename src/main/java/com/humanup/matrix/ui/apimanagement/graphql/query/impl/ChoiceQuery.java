package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.AnswerDTO;
import com.humanup.matrix.ui.apimanagement.dto.ChoiceDTO;
import com.humanup.matrix.ui.apimanagement.dto.QuestionDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryChoice;
import com.humanup.matrix.ui.apimanagement.proxy.ChoiceProxy;
import com.humanup.matrix.ui.apimanagement.proxy.QuestionProxy;
import com.humanup.matrix.ui.apimanagement.vo.AnswerVO;
import com.humanup.matrix.ui.apimanagement.vo.ChoiceVO;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ChoiceQuery implements GraphQLQueryResolver, IQueryChoice {
  private static final Logger LOGGER = LoggerFactory.getLogger(ChoiceQuery.class);

  @Autowired QuestionProxy questionProxy;
  @Autowired ChoiceProxy choiceProxy;

  @Override
  public List<ChoiceVO> getListChoice(DataFetchingEnvironment env) {
    String token = ObjectBuilder.getTokenFromGraphQL(env);
    List<ChoiceDTO> choiceListDTO = null;
    try {
      choiceListDTO =
          ObjectBuilder.mapper.readValue(
              choiceProxy.findAllChoice(token), new TypeReference<List<ChoiceDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<ChoiceVO> ", e);
    }
    return getChoiceVOS(choiceListDTO);
  }
  public List<ChoiceVO> getChoiceVOS(List<ChoiceDTO> choiceListDTO) {
    return choiceListDTO.stream()
            .map(
                    c -> {
                      return ChoiceVO.builder()
                              .questionId(c.getQuestionId())
                              .choiceText(c.getChoiceText())
                              .percentage(c.getPercentage())
                              .build();
                    })
            .collect(Collectors.toList());
  }

  @Override
  public List<ChoiceVO> getListChoiceByEmail(@NotNull String email, DataFetchingEnvironment env) {
    String token = ObjectBuilder.getTokenFromGraphQL(env);
    List<ChoiceDTO> choiceListDTO = null;
    try {
      choiceListDTO =
              ObjectBuilder.mapper.readValue(
                      choiceProxy.findChoiceByEmail(email, token), new TypeReference<List<ChoiceDTO>>() {});

    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<ChoiceDTO> ", e);
    }
    return getChoiceVOS(choiceListDTO);
  }

  @Override
  public List<ChoiceVO> getChoicesByQuestionId(@NotNull final int questionId, DataFetchingEnvironment env) {
    String token = ObjectBuilder.getTokenFromGraphQL(env);
    List<ChoiceDTO> choiceListDTO = null;
    try {
      choiceListDTO =
              ObjectBuilder.mapper.readValue(
                      choiceProxy.findChoicesByQuestionId(questionId, token), new TypeReference<List<ChoiceDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing  Person {}", questionId, e);
    }
    return listChoiceVO(choiceListDTO);
  }

  public List<ChoiceVO> listChoiceVO(List<ChoiceDTO> listChoiceDTO){
    return Optional.ofNullable(listChoiceDTO).orElse(Collections.emptyList()).stream()
            .map(
                    choice ->
                            ChoiceVO.builder()
                                    .questionId(choice.getQuestionId())
                                    .choiceText(choice.getChoiceText())
                                    .percentage(choice.getPercentage())
                                    .build())
            .collect(Collectors.toList());
  }
}
