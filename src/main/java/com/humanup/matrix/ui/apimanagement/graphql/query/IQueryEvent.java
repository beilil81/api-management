package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.EventVO;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryEvent {
  List<EventVO> getListEvent();
  List<EventVO> getEventByEmailPerson(@NotNull final String email);
  List<EventVO> getListEventByTypeTitle(@NotNull final String titleEvent);
}
