package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.CourseTypeVO;
import com.humanup.matrix.ui.apimanagement.vo.CourseVO;
import com.humanup.matrix.ui.apimanagement.vo.ReviewVO;
import com.humanup.matrix.ui.apimanagement.vo.TrainerVO;
import org.jetbrains.annotations.NotNull;

public interface ICourseMutation {


  CourseVO createCourse(
          @NotNull String idType,
          @NotNull String idTrainer,
          @NotNull String title,
          @NotNull String description,
          @NotNull String startDate,
          @NotNull String endDate)
      throws JsonProcessingException;

}
