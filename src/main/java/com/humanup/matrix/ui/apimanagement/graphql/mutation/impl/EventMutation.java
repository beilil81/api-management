package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.EventDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.IEventMutation;
import com.humanup.matrix.ui.apimanagement.proxy.EventProxy;
import com.humanup.matrix.ui.apimanagement.vo.EventVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventMutation implements GraphQLMutationResolver , IEventMutation {
    @Autowired
    EventProxy eventProxy;


    @Override
    public EventVO createEvent(@NotNull int idTypeEvents, @NotNull String libelle, @NotNull String description) throws JsonProcessingException {
        EventDTO eventDTO = EventDTO.builder()
                .description(description)
                .libelle(libelle)
                .idTypeEvents(idTypeEvents)
                .build();
        EventDTO saveEvent = ObjectBuilder.mapper.readValue(eventProxy.saveEvent(eventDTO),EventDTO.class );
        return EventVO.builder()
                .libelle(saveEvent.getLibelle())
                .description(saveEvent.getDescription())
                .build();
    }
}
