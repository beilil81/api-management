package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.CourseTypeVO;
import com.humanup.matrix.ui.apimanagement.vo.CourseVO;
import com.humanup.matrix.ui.apimanagement.vo.ReviewVO;
import com.humanup.matrix.ui.apimanagement.vo.TrainerVO;
import org.jetbrains.annotations.NotNull;

public interface IReviewMutation {

  ReviewVO createReview(
          @NotNull String internId,
          @NotNull String courseId,
          @NotNull String createdOn,
          @NotNull int score)
      throws JsonProcessingException;
}
