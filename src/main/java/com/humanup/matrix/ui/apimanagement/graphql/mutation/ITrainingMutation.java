package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.CourseTypeVO;
import com.humanup.matrix.ui.apimanagement.vo.CourseVO;
import com.humanup.matrix.ui.apimanagement.vo.ReviewVO;
import com.humanup.matrix.ui.apimanagement.vo.TrainerVO;
import org.jetbrains.annotations.NotNull;

public interface ITrainingMutation {


  TrainerVO createTrainer(
      @NotNull String name, @NotNull String address, @NotNull String phone, @NotNull String email)
      throws JsonProcessingException;
}
