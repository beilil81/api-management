package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.IReviewMutation;
import com.humanup.matrix.ui.apimanagement.proxy.*;
import com.humanup.matrix.ui.apimanagement.vo.ReviewVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class ReviewMutation implements GraphQLMutationResolver, IReviewMutation {

  private static final DateTimeFormatter inputFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
  private static final DateTimeFormatter outputFormatter =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  @Autowired CourseProxy courseProxy;
  @Autowired InternProxy internProxy;
  @Autowired ReviewProxy reviewProxy;

  @Override
  public ReviewVO createReview(
      @NotNull String internId,
      @NotNull String courseId,
      @NotNull String createdOn,
      @NotNull int score)
      throws JsonProcessingException {
    final CourseDTO course =
        ObjectBuilder.mapper.readValue(courseProxy.findCourseById(courseId), CourseDTO.class);
    if (null == course) {
      return null;
    }
    final InternDTO intern =
        ObjectBuilder.mapper.readValue(internProxy.findInternById(internId), InternDTO.class);
    if (null == intern) {
      return null;
    }
    final ReviewDTO reviewDto =
        ReviewDTO.builder()
            .courseTitle(course.getTitle())
            .internEmail(intern.getEmailPerson())
            .createdOn(
                Optional.ofNullable(createdOn)
                    .map(date -> LocalDateTime.parse(date, inputFormatter))
                    .orElse(null))
            .score(score)
            .build();
    final ReviewDTO saveReview =
        ObjectBuilder.mapper.readValue(reviewProxy.saveReview(reviewDto), ReviewDTO.class);
    return Optional.ofNullable(saveReview)
        .map(
            review ->
                ReviewVO.builder()
                    .courseTitle(review.getCourseTitle())
                    .internEmail(review.getInternEmail())
                    .createdOn(
                        Optional.ofNullable(review.getCreatedOn())
                            .map(date -> date.format(outputFormatter))
                            .orElse(null))
                    .score(review.getScore())
                    .build())
        .orElse(null);
  }
}
