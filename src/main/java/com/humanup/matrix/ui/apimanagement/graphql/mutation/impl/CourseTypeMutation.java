package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.ICourseTypeMutation;
import com.humanup.matrix.ui.apimanagement.proxy.*;
import com.humanup.matrix.ui.apimanagement.vo.CourseTypeVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class CourseTypeMutation implements GraphQLMutationResolver, ICourseTypeMutation {

  private static final DateTimeFormatter inputFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
  private static final DateTimeFormatter outputFormatter =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  @Autowired CourseTypeProxy courseTypeProxy;


  @Override
  public CourseTypeVO createCourseType(@NotNull String typeTitle) throws JsonProcessingException {
    final CourseTypeDTO courseTypeDto = CourseTypeDTO.builder().typeTitle(typeTitle).build();
    final CourseTypeDTO saveCourseType =
        ObjectBuilder.mapper.readValue(
            courseTypeProxy.saveCourseType(courseTypeDto), CourseTypeDTO.class);
    return Optional.ofNullable(saveCourseType)
        .map(courseType -> CourseTypeVO.builder().typeTitle(courseType.getTypeTitle()).build())
        .orElse(null);
  }

}
