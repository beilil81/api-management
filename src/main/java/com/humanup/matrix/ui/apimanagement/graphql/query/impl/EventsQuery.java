package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.EventDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryEvent;
import com.humanup.matrix.ui.apimanagement.proxy.EventProxy;
import com.humanup.matrix.ui.apimanagement.vo.EventVO;
import com.humanup.matrix.ui.apimanagement.vo.TypeEventsVO;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EventsQuery implements GraphQLQueryResolver, IQueryEvent {
  private static final Logger LOGGER = LoggerFactory.getLogger(EventsQuery.class);

  @Autowired
  EventProxy eventProxy;

  @Override
  public List<EventVO> getListEvent(DataFetchingEnvironment env) {
    String token = ObjectBuilder.getTokenFromGraphQL(env);
    List<EventDTO> eventDTO = null;
    try {
      eventDTO =
              ObjectBuilder.mapper.readValue(
                      eventProxy.findAllEvent(token), new TypeReference<List<EventDTO>>() {
                      });
    } catch (final JsonProcessingException e) {
      LOGGER.error("Exception Parsing List<TypeEventsDTO> ", e);
    }
    return Optional.ofNullable(eventDTO).orElse(Collections.emptyList()).stream()
            .map(event -> EventVO.builder()
                    .libelle(event.getLibelle())
                    .description(event.getDescription())
                    .typeEvents(
                            TypeEventsVO.builder()
                                    .titleEvent(null != event.getTypeEvents() ? event.getTypeEvents() : "")
                                    .build()).build())
            .collect(Collectors.toList());
  }

  @Override
  public List<EventVO> getEventByEmailPerson(DataFetchingEnvironment env, @NotNull String email) {
    String token = ObjectBuilder.getTokenFromGraphQL(env);
    List<EventDTO> eventDTO = null;
    try {
      eventDTO =
              (List<EventDTO>) ObjectBuilder.mapper.readValue(
                      eventProxy.findEventsByEmail(email,token), EventDTO.class);
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception Parsing Event ", e);
    }
    return Optional.ofNullable(eventDTO).orElse(Collections.emptyList()).stream()
            .map(event -> EventVO.builder()
                    .libelle(event.getLibelle())
                    .description(event.getDescription())
                    .typeEvents(
                            TypeEventsVO.builder()
                                    .titleEvent(null != event.getTypeEvents() ? event.getTypeEvents() : "")
                                    .build()).build())
            .collect(Collectors.toList());
    }



  @Override
  public List<EventVO> getListEventByTypeTitle(@NotNull String titleEvent) {
    return null;
  }

}
