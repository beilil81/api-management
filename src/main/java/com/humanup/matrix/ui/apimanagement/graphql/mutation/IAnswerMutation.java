package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.AnswerVO;
import org.jetbrains.annotations.NotNull;


public interface IAnswerMutation {

    AnswerVO createAnswer(@NotNull int choiceId, @NotNull String emailPerson) throws JsonProcessingException;
}
