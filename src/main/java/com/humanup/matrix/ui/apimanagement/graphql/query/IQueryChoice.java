package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryChoice {
  List<ChoiceVO> getListChoice(DataFetchingEnvironment env);
  List<ChoiceVO> getListChoiceByEmail(@NotNull final String email, DataFetchingEnvironment env);
  List<ChoiceVO> getChoicesByQuestionId(@NotNull final int questionId, DataFetchingEnvironment env);
}
