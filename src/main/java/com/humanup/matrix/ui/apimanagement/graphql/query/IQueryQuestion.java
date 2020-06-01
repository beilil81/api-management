package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryQuestion {
  List<QuestionVO> getListQuestion(DataFetchingEnvironment env);
  QuestionVO getQuestionByQuestionId(@NotNull final int questionId, DataFetchingEnvironment env);
}
