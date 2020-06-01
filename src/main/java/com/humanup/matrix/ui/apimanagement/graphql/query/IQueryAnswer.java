package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryAnswer {
  List<AnswerVO> getListAnswerByEmail(@NotNull final String emailPerson, DataFetchingEnvironment env);
  List<AnswerVO> getListAnswer(DataFetchingEnvironment env);
  AnswerVO getAnswerByChoiceId(@NotNull final int choiceId, DataFetchingEnvironment env);
}
