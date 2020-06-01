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
import graphql.schema.DataFetchingEnvironment;
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

  @Autowired QuestionProxy questionProxy;

  @Override
  public List<QuestionVO> getListQuestion(DataFetchingEnvironment env) {
    String token = ObjectBuilder.getTokenFromGraphQL(env);
    List<QuestionDTO> questionListDTO = null;
    try {
      questionListDTO =
          ObjectBuilder.mapper.readValue(
              questionProxy.findAllQuestion(token), new TypeReference<List<QuestionDTO>>() {});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<QuestionVO> ", e);
    }
    return getQuestionVOS(questionListDTO);
  }

  @NotNull
  private List<QuestionVO> getQuestionVOS(List<QuestionDTO> questionListDTO) {
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

  @Override
  public QuestionVO getQuestionByQuestionId(@NotNull int questionId, DataFetchingEnvironment env) {
    String token = ObjectBuilder.getTokenFromGraphQL(env);
    QuestionDTO questionDTO = null;
    try {
      questionDTO =
              ObjectBuilder.mapper.readValue(
                      questionProxy.findQuestionByQuestionId(questionId, token), new TypeReference<QuestionDTO>(){});
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing QuestionVO ", e);
    }
    return ObjectBuilder.buildQuestion(questionDTO);
  }
}
