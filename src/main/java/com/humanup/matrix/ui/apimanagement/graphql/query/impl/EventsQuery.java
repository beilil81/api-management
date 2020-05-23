package com.humanup.matrix.ui.apimanagement.graphql.query.impl;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.humanup.matrix.ui.apimanagement.dto.EventDTO;
import com.humanup.matrix.ui.apimanagement.dto.SkillDTO;
import com.humanup.matrix.ui.apimanagement.dto.TypeEventsDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.query.IQueryEvent;
import com.humanup.matrix.ui.apimanagement.proxy.EventProxy;
import com.humanup.matrix.ui.apimanagement.vo.EventVO;
import com.humanup.matrix.ui.apimanagement.vo.SkillVO;
import com.humanup.matrix.ui.apimanagement.vo.TypeEventsVO;
import com.humanup.matrix.ui.apimanagement.vo.TypeSkillsVO;
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
  public List<EventVO> getListEvent() {
    List<EventDTO> eventDTO = null;
    try {
      eventDTO =
              ObjectBuilder.mapper.readValue(
                      eventProxy.findAllEvent(), new TypeReference<List<EventDTO>>() {});
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
  public List<EventVO> getEventByEmailPerson(@NotNull String email) {
    return null;
  }

  @Override
  public List<EventVO> getListEventByTypeTitle(@NotNull String titleEvent) {
    return null;
  }

//    public List<EventVO> getEventByEmailPerson(@NotNull String email) {
//      List<EventDTO> eventDTO = null;
//      try {
//        eventDTO =
//                (List<EventDTO>) ObjectBuilder.mapper.readValue(
//                        eventProxy.findEventsByEmail(email), EventDTO.class);
//      } catch (final JsonProcessingException e) {
//        LOGGER.error("Exception Parsing List<EventDTO> ", e);
//      }
//      return EventVO.builder()
//              .libelle(eventDTO.getLibelle())
//              .description(eventDTO.getDescription())
//              .emailPerson(eventDTO.getEmailPerson())
//              .build();
//    }


//  @Override
//  public List<EventVO> getListEventByTypeTitle(@NotNull final String titleEvent) {
//
//    List<EventDTO> eventListDTO = null;
//    try {
//      eventListDTO =
//              ObjectBuilder.mapper.readValue(
//                      eventProxy.findListEventByType(titleEvent),
//                      new TypeReference<List<EventDTO>>() {});
//    } catch (JsonProcessingException e) {
//      LOGGER.error("Exception Parsing List<EventVO> ", e);
//    }
//    return getListEvent(eventListDTO);
//  }


//  @NotNull
//  public List<EventVO> getEventVO(List<EventDTO> eventListDTO) {
//    return eventListDTO.stream()
//            .map(
//                    c -> {
//                      TypeEventsDTO typeEventsDTO = null;
//                      try {
//                        typeEventsDTO =
//                                ObjectBuilder.mapper.readValue(
//                                        typeEventsProxy.findByTypeEventsByID(c.getIdTypeEvents()),
//                                        TypeEventsDTO.class);
//                      } catch (JsonProcessingException e) {
//                        LOGGER.error("Exception Parsing TypeEvents {}", c.getIdTypeEvents(), e);
//                      }
//                      return EventVO.builder()
//                              .libelle(c.getLibelle())
//                              .description(c.getDescription())
//                              .emailPerson(c.getEmailPerson())
//                              .build();
//                    })
//            .collect(Collectors.toList());
//  }

}