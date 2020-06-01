package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.QuestionDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.IQuestionMutation;
import com.humanup.matrix.ui.apimanagement.proxy.QuestionProxy;
import com.humanup.matrix.ui.apimanagement.vo.QuestionVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QuestionMutation implements GraphQLMutationResolver, IQuestionMutation {

    @Autowired
    QuestionProxy questionProxy;


    @Override
    public QuestionVO createQuestion(@NotNull int questionId, @NotNull String questionText) throws JsonProcessingException {
        final QuestionDTO questionDto =
                QuestionDTO.builder()
                        .questionId(questionId)
                        .questionText(questionText)
                        .build();
        final QuestionDTO saveQuestion =
                ObjectBuilder.mapper.readValue(questionProxy.saveQuestion(questionDto), QuestionDTO.class);
        return Optional.ofNullable(saveQuestion)
                .map(
                        question ->
                                QuestionVO.builder()
                                        .questionText(questionText)
                                        .questionId(saveQuestion.getQuestionId())
                                        .build())
                .orElse(null);
    }

}
