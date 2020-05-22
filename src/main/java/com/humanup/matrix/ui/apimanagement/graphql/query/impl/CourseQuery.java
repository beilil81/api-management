package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryCourse;
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
public class CourseQuery implements GraphQLQueryResolver, IQueryCourse {
  private static final Logger LOGGER = LoggerFactory.getLogger(CourseQuery.class);
  private static final DateTimeFormatter outputFormatter =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  @Autowired CourseProxy courseProxy;

  @Override
  public List<CourseVO> getListCourses() {
    List<CourseDTO> courseListDTO = null;
    try {
      courseListDTO =
          ObjectBuilder.mapper.readValue(
              courseProxy.findAllCourses(), new TypeReference<List<CourseDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<CourseDTO> ", e);
    }
    return getCourseVOS(courseListDTO);
  }

  @Override
  public List<CourseVO> getListCoursesByEmail(@NotNull String email) {
    List<CourseDTO> courseListDTO = null;
    try {
      courseListDTO =
              ObjectBuilder.mapper.readValue(
                      courseProxy.findCoursesByEmail(email), new TypeReference<List<CourseDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<CourseDTO> ", e);
    }
    return getCourseVOS(courseListDTO);
  }

  @NotNull
  private List<CourseVO> getCourseVOS(List<CourseDTO> courseListDTO) {
    return Optional.ofNullable(courseListDTO).orElse(Collections.emptyList()).stream()
            .map(
                    course ->
                            CourseVO.builder()
                                    .title(course.getTitle())
                                    .description(course.getDescription())
                                    .trainerEmail(course.getTrainerEmail())
                                    .courseTypeTitle(course.getTitle())
                                    .startDate(
                                            Optional.ofNullable(course.getStartDate())
                                                    .map(date -> date.format(outputFormatter))
                                                    .orElse(null))
                                    .endDate(
                                            Optional.ofNullable(course.getEndDate())
                                                    .map(date -> date.format(outputFormatter))
                                                    .orElse(null))
                                    .reviews(
                                            Optional.ofNullable(course.getReviewList()).orElse(Collections.emptyList())
                                                    .stream()
                                                    .map(
                                                            review ->
                                                                    ReviewVO.builder()
                                                                            .courseTitle(review.getCourseTitle())
                                                                            .internEmail(review.getInternEmail())
                                                                            .score(review.getScore())
                                                                            .createdOn(
                                                                                    Optional.ofNullable(review.getCreatedOn())
                                                                                            .map(date -> date.format(outputFormatter))
                                                                                            .orElse(null))
                                                                            .build())
                                                    .collect(Collectors.toList()))
                                    .build())
            .collect(Collectors.toList());
  }

}
