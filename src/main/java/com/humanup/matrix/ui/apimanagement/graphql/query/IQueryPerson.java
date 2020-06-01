package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryPerson {
  PersonVO getPersonByEmail(@NotNull final String email, DataFetchingEnvironment env);
  List<PersonVO> getListPerson(DataFetchingEnvironment env);
}