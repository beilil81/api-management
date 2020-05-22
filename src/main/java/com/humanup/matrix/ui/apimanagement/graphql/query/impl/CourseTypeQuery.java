package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.*;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryCourseType;
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
public class CourseTypeQuery implements GraphQLQueryResolver, IQueryCourseType {
  private static final Logger LOGGER = LoggerFactory.getLogger(CourseTypeQuery.class);

  @Autowired CourseTypeProxy courseTypeProxy;


  @Override
  public List<CourseTypeVO> getListCourseTypes() {
    List<CourseTypeDTO> courseTypeListDTO = null;
    try {
      courseTypeListDTO =
          ObjectBuilder.mapper.readValue(
              courseTypeProxy.findAllCourseTypes(), new TypeReference<List<CourseTypeDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<CourseTypeDTO> ", e);
    }
    return Optional.ofNullable(courseTypeListDTO).orElse(Collections.emptyList()).stream()
        .map(courseType -> CourseTypeVO.builder().typeTitle(courseType.getTypeTitle()).build())
        .collect(Collectors.toList());
  }
}
