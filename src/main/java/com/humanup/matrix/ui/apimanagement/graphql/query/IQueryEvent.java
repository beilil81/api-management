package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.EventVO;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryEvent {
  List<EventVO> getListEvent(DataFetchingEnvironment env);
  List<EventVO> getEventByEmailPerson(@NotNull final String email, DataFetchingEnvironment env);
  List<EventVO> getListEventByTypeTitle(@NotNull final String titleEvent, DataFetchingEnvironment env);
}
