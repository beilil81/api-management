package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.TypeEventsVO;
import org.jetbrains.annotations.NotNull;

public interface ITypeEventMutation {

    TypeEventsVO createTypeEvent(@NotNull String titleEvent) throws JsonProcessingException ;

    }
