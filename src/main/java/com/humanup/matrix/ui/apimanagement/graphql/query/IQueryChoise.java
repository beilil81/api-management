package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryChoise {
  List<ChoiceVO> getListChoice();
  List<ChoiceVO> getListChoiceByEmail(@NotNull final String email);
  List<ChoiceVO> getChoicesByQuestionId(@NotNull final Long questionId);
}
