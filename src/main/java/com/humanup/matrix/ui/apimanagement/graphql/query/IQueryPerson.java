package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryPerson {
  PersonVO getPersonByEmail(@NotNull final String email);

  List<PersonVO> getListPerson();


}
