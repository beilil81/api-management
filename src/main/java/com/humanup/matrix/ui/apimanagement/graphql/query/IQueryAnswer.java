package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryAnswer {
  List<AnswerVO> getListAnswerByEmail(@NotNull final String email);
  List<AnswerVO> getListAnswer();
  AnswerVO getAnswerByChoiceId(@NotNull final Long choiceId);
}
