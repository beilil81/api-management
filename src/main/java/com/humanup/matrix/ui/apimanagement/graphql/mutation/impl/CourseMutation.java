package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.ICourseMutation;
import com.humanup.matrix.ui.apimanagement.proxy.*;
import com.humanup.matrix.ui.apimanagement.vo.CourseVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class CourseMutation implements GraphQLMutationResolver, ICourseMutation {

  private static final DateTimeFormatter inputFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
  private static final DateTimeFormatter outputFormatter =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  @Autowired CourseProxy courseProxy;

  @Override
  public CourseVO createCourse(
      @NotNull String typeTitle,
      @NotNull String trainerEmail,
      @NotNull String title,
      @NotNull String description,
      @NotNull String startDate,
      @NotNull String endDate)
      throws JsonProcessingException {
    final CourseDTO courseDto =
        CourseDTO.builder()
            .courseTypeTitle(typeTitle)
            .trainerEmail(trainerEmail)
            .title(title)
            .description(description)
            .startDate(
                Optional.ofNullable(startDate)
                    .map(date -> LocalDateTime.parse(date, inputFormatter))
                    .orElse(null))
            .endDate(
                Optional.ofNullable(endDate)
                    .map(date -> LocalDateTime.parse(date, inputFormatter))
                    .orElse(null))
            .build();
    final CourseDTO saveCourse =
        ObjectBuilder.mapper.readValue(courseProxy.saveCourse(courseDto), CourseDTO.class);
    return Optional.ofNullable(saveCourse)
        .map(
            course ->
                CourseVO.builder()
                    .courseTypeTitle(course.getCourseTypeTitle())
                    .trainerEmail(course.getTrainerEmail())
                    .title(course.getTitle())
                    .description(course.getDescription())
                    .startDate(
                        Optional.ofNullable(course.getStartDate())
                            .map(date -> date.format(outputFormatter))
                            .orElse(null))
                    .endDate(
                        Optional.ofNullable(course.getEndDate())
                            .map(date -> date.format(outputFormatter))
                            .orElse(null))
                    .build())
        .orElse(null);
  }

}
