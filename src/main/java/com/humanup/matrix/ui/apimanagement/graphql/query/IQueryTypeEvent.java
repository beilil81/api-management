package com.humanup.matrix.ui.apimanagement.graphql.query;
import com.humanup.matrix.ui.apimanagement.vo.TypeEventsVO;

import java.util.List;

public interface IQueryTypeEvent {
  List<TypeEventsVO> getListTypeEvents();
}
