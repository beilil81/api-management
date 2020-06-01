package com.humanup.matrix.ui.apimanagement.graphql.mutation.impl;


import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.dto.ChoiceDTO;
import com.humanup.matrix.ui.apimanagement.graphql.builder.ObjectBuilder;
import com.humanup.matrix.ui.apimanagement.graphql.mutation.IChoiceMutation;
import com.humanup.matrix.ui.apimanagement.proxy.ChoiceProxy;
import com.humanup.matrix.ui.apimanagement.vo.ChoiceVO;
import com.humanup.matrix.ui.apimanagement.vo.QuestionVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChoiceMutation implements GraphQLMutationResolver, IChoiceMutation {

    @Autowired
    ChoiceProxy choiceProxy;



    @Override
    public ChoiceVO createChoice(@NotNull int percentage, @NotNull String choiceText,@NotNull int questionId) throws JsonProcessingException {
        final ChoiceDTO choiceDto =
                ChoiceDTO.builder()
                        .choiceText(choiceText)
                        .questionId(questionId)
                        .percentage(percentage)
                        .build();
        final ChoiceDTO saveChoice =
                ObjectBuilder.mapper.readValue(choiceProxy.saveChoice(choiceDto), ChoiceDTO.class);
        return Optional.ofNullable(saveChoice)
                .map(
                        answer ->
                                ChoiceVO.builder()
                                        .choiceText(saveChoice.getChoiceText())
                                        .questionId(saveChoice.getQuestionId())
                                        .percentage(saveChoice.getPercentage())
                                        .build())
                .orElse(null);
    }
}
