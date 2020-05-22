package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryPerson;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryReview;
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
public class ReviewQuery implements GraphQLQueryResolver, IQueryReview {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReviewQuery.class);
  private static final DateTimeFormatter outputFormatter =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


  @Autowired ReviewProxy reviewProxy;


  @Override
  public List<ReviewVO> getListReviews() {
    List<ReviewDTO> reviewListDTO = null;
    try {
      reviewListDTO =
          ObjectBuilder.mapper.readValue(
              reviewProxy.findAllReviews(), new TypeReference<List<ReviewDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<ReviewDTO> ", e);
    }
    return getReviewVOS(reviewListDTO);
  }

  @NotNull
  public List<ReviewVO> getReviewVOS(List<ReviewDTO> reviewListDTO) {
    return Optional.ofNullable(reviewListDTO).orElse(Collections.emptyList()).stream()
            .map(
                    review ->
                            ReviewVO.builder()
                                    .internEmail(review.getInternEmail())
                                    .courseTitle(review.getCourseTitle())
                                    .createdOn(
                                            Optional.ofNullable(review.getCreatedOn())
                                                    .map(date -> date.format(outputFormatter))
                                                    .orElse(null))
                                    .score(review.getScore())
                                    .build())
            .collect(Collectors.toList());
  }

  @Override
  public List<ReviewVO> getListReviewsByEmail(@NotNull String email) {
    List<ReviewDTO> reviewListDTO = null;
    try {
      reviewListDTO =
              ObjectBuilder.mapper.readValue(
                      reviewProxy.findReviewsByEmail(email), new TypeReference<List<ReviewDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<ReviewDTO> ", e);
    }
    return getReviewVOS(reviewListDTO);
  }

}
