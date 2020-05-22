package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryPerson;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryQuestion;
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
public class QuestionQuery implements GraphQLQueryResolver, IQueryQuestion {
  private static final Logger LOGGER = LoggerFactory.getLogger(QuestionQuery.class);
  private static final DateTimeFormatter outputFormatter =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  @Autowired QuestionProxy questionProxy;

  @Override
  public List<QuestionVO> getListQuestion() {
    List<QuestionDTO> questionListDTO = null;

    try {
      questionListDTO =
          ObjectBuilder.mapper.readValue(
              questionProxy.findAllQuestion(), new TypeReference<List<QuestionDTO>>() {});

    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<QuestionVO> ", e);
    }

    return questionListDTO.stream()
        .map(
            q -> {
              return QuestionVO.builder()
                  .questionId(q.getQuestionId())
                  .questionText(q.getQuestionText())
                  .build();
            })
        .collect(Collectors.toList());
  }


}
