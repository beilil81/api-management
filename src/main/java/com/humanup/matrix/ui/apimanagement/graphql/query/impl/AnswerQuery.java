package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryAnswer;
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
public class AnswerQuery implements GraphQLQueryResolver, IQueryAnswer {
  private static final Logger LOGGER = LoggerFactory.getLogger(AnswerQuery.class);
  private static final DateTimeFormatter outputFormatter =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  @Autowired AnswerProxy answerProxy;
  @Autowired PersonProxy personProxy;
  @Autowired ChoiceProxy choiceProxy;

  @Override
  public List<AnswerVO> getListAnswerByEmail(@NotNull String email) {
    List<AnswerVO> answerList = null;
    //TODO
    return  answerList;
  }

  @Override
  public List<AnswerVO> getListAnswer() {
    List<AnswerDTO> answerListDTO = null;
    try {
      answerListDTO =
          ObjectBuilder.mapper.readValue(
              answerProxy.findAllAnswer(), new TypeReference<List<AnswerDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<AnswerDTO> ", e);
    }
    return answerListDTO.stream()
        .map(
            c -> {
              ChoiceDTO choiceDTO = null;
              PersonDTO personDTO = null;
              try {
                choiceDTO =
                    ObjectBuilder.mapper.readValue(
                        choiceProxy.findChoicesByChoiceId(c.getChoiceId()), ChoiceDTO.class);
                personDTO =
                    ObjectBuilder.mapper.readValue(
                        personProxy.findPersonByEmail(c.getEmailPerson()), PersonDTO.class);
              } catch (JsonProcessingException e) {
                LOGGER.error("Exception Parsing Answer {}", e);
              }
              return AnswerVO.builder()
                  .choice(ObjectBuilder.buildChoice(choiceDTO))
                  .person(ObjectBuilder.buildPerson(personDTO))
                  .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  public AnswerVO getAnswerByChoiceId(@NotNull Long choiceId) {
    ChoiceDTO choiceDTO = null;
    PersonDTO personDTO = null;
    try {
      choiceDTO =
          ObjectBuilder.mapper.readValue(
              choiceProxy.findChoicesByQuestionId(choiceDTO.getQuestionId()), ChoiceDTO.class);
      personDTO =
          ObjectBuilder.mapper.readValue(
              personProxy.findPersonByEmail(personDTO.getMailAdresses()), PersonDTO.class);
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<AnswerVO> ", e);
    }
    return AnswerVO.builder()
        .choice(ObjectBuilder.buildChoice(choiceDTO))
        .person(ObjectBuilder.buildPerson(personDTO))
        .build();
  }

}
