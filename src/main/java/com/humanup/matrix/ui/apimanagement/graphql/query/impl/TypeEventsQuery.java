package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.TypeEventsDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryCourseType;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryTypeEvent;

import com.humanup.matrix.ui.apimanagement.proxy.TypeEventsProxy;
import com.humanup.matrix.ui.apimanagement.vo.TypeEventsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TypeEventsQuery implements GraphQLQueryResolver, IQueryTypeEvent {
  private static final Logger LOGGER = LoggerFactory.getLogger(TypeEventsQuery.class);

  @Autowired
  TypeEventsProxy typeEventProxy;


  @Override
  public List<TypeEventsVO> getListTypeEvents() {
    List<TypeEventsDTO> typeEventsDTO = null;
    try {
      typeEventsDTO =
          ObjectBuilder.mapper.readValue(
                  typeEventProxy.findAllTypeEvents(), new TypeReference<List<TypeEventsDTO>>() {});
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<TypeEventsDTO> ", e);
    }
    return Optional.ofNullable(typeEventsDTO).orElse(Collections.emptyList()).stream()
        .map(eventType -> TypeEventsVO.builder().titleEvent(eventType.getTitleEvent()).build())
        .collect(Collectors.toList());
  }

}
