package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryChoise;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryPerson;
import com.humanup.matrix.ui.apimanagement.proxy.*;
import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class  ChoiseQuery implements GraphQLQueryResolver, IQueryChoise {
  private static final Logger LOGGER = LoggerFactory.getLogger(ChoiseQuery.class);
  private static final DateTimeFormatter outputFormatter =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


  @Autowired QuestionProxy questionProxy;

  @Autowired ChoiceProxy choiceProxy;


  @Override
  public List<ChoiceVO> getListChoice() {
    List<ChoiceDTO> choiceListDTO = null;
    try {
      choiceListDTO =
          ObjectBuilder.mapper.readValue(
              choiceProxy.findAllChoice(), new TypeReference<List<ChoiceDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<ChoiceVO> ", e);
    }
    return getChoiceVOS(choiceListDTO);
  }

  @NotNull
  public List<ChoiceVO> getChoiceVOS(List<ChoiceDTO> choiceListDTO) {
    return choiceListDTO.stream()
            .map(
                    c -> {
                      QuestionDTO questionDTO = null;
                      try {
                        questionDTO =
                                ObjectBuilder.mapper.readValue(
                                        questionProxy.findQuestionByQuestionId(c.getQuestionId()),
                                        QuestionDTO.class);
                      } catch (JsonProcessingException e) {
                        LOGGER.error("Exception Parsing Question {}", c.getQuestionId(), e);
                      }
                      return ChoiceVO.builder()
                              .choiceText(c.getChoiceText())
                              .percentage(c.getPercentage())
                              .question(ObjectBuilder.buildQuestion(questionDTO))
                              .build();
                    })
            .collect(Collectors.toList());
  }

  @Override
  public List<ChoiceVO> getListChoiceByEmail(@NotNull String email) {
    //TODO
    return null;
  }

  @Override
  public List<ChoiceVO> getChoicesByQuestionId(@NotNull final Long questionId) {

    List<ChoiceDTO> choiceListDTO = null;
    try {
      choiceListDTO =
          ObjectBuilder.mapper.readValue(
              choiceProxy.findChoicesByQuestionId(questionId),
              new TypeReference<List<ChoiceDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<ChoiceVO> ", e);
    }
    return getChoiceVOS(choiceListDTO);
  }

}
