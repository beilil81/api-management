package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.AnswerDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.IAnswerMutation;
import com.humanup.matrix.ui.apimanagement.proxy.AnswerProxy;
import com.humanup.matrix.ui.apimanagement.vo.AnswerVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AnswerMutation implements GraphQLMutationResolver, IAnswerMutation {
    @Autowired
    AnswerProxy answerProxy;

    @Override
    public AnswerVO createAnswer(@NotNull int choiceId, @NotNull String emailPerson) throws JsonProcessingException {
        final AnswerDTO answerDto =
                AnswerDTO.builder()
                        .choiceId(choiceId)
                        .emailPerson(emailPerson)
                        .build();
        final AnswerDTO saveAnswer =
                ObjectBuilder.mapper.readValue(answerProxy.saveAnswer(answerDto), AnswerDTO.class);
        return Optional.ofNullable(saveAnswer)
                .map(
                        answer ->
                                AnswerVO.builder()
                                        .choiceId(saveAnswer.getChoiceId())
                                        .emailPerson(saveAnswer.getEmailPerson())
                                        .build())
                .orElse(null);

    }
}
