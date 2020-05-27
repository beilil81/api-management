package com.humanup.matrix.ui.apimanagement.graphql.mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.humanup.matrix.ui.apimanagement.vo.CourseVO;
import org.jetbrains.annotations.NotNull;

public interface IAuthenticationMutation {


  String authentication(
          @NotNull String email,
          @NotNull String password)
      throws JsonProcessingException;

}
