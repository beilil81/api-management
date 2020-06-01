package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.QuestionVO;
import org.jetbrains.annotations.NotNull;


public interface IQuestionMutation {

    QuestionVO createQuestion(@NotNull int questionId, @NotNull String questionText) throws JsonProcessingException;
}
