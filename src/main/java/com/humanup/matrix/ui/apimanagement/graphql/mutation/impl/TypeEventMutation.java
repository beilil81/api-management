package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.ITypeEventMutation;
import com.humanup.matrix.ui.apimanagement.proxy.TypeEventsProxy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.humanup.matrix.ui.apimanagement.vo.TypeEventsVO;
import com.humanup.matrix.ui.apimanagement.dto.TypeEventsDTO;

@Component
public class TypeEventMutation implements GraphQLMutationResolver , ITypeEventMutation {
    @Autowired
    TypeEventsProxy typeEventProxy;

    @Override
    public TypeEventsVO createTypeEvent(@NotNull String titleEvent) throws JsonProcessingException {
        TypeEventsDTO typeEventsDTO = TypeEventsDTO.builder()
                .titleEvent(titleEvent)
                .build();
        TypeEventsDTO saveTypeEvent = ObjectBuilder.mapper.readValue(typeEventProxy.saveTypeEvent(typeEventsDTO),TypeEventsDTO.class );

        return TypeEventsVO.builder()
                .titleEvent(saveTypeEvent.getTitleEvent())
                .build();
    }
}
