package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.ChoiceVO;

import javax.validation.constraints.NotNull;


public interface IChoiceMutation {

    ChoiceVO createChoice(@NotNull int percentage, @NotNull String choiceText, @NotNull int questionId ) throws JsonProcessingException;
}