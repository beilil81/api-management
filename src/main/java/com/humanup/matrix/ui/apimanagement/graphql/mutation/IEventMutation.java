package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.EventVO;
import org.jetbrains.annotations.NotNull;

public interface IEventMutation {

    EventVO createEvent(@NotNull int idTypeEvents, @NotNull  String libelle, @NotNull  String description ) throws JsonProcessingException ;

    }
