package com.humanup.matrix.ui.apimanagement.graphql.interfaces;

import com.humanup.matrix.ui.apimanagement.vo.AnswerVO;
import com.humanup.matrix.ui.apimanagement.vo.ChoiceVO;
import com.humanup.matrix.ui.apimanagement.vo.PersonVO;
import com.humanup.matrix.ui.apimanagement.vo.QuestionVO;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQuery {
    PersonVO getPersonByEmail(@NotNull final String email);

    List<PersonVO> getListPerson();

    List<QuestionVO> getListQuestion();

    List<ChoiceVO> getListChoice();

    List<ChoiceVO>  getChoicesByQuestionId(@NotNull final Long questionId);

    List<AnswerVO> getListAnswer();

    AnswerVO getAnswerByChoiceId(@NotNull final Long choiceId);
}
