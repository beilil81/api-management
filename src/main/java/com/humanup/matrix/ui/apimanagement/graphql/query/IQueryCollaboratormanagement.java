package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryCollaboratormanagement {
  List<ProjectVO> getListProject();
  List<ProjectVO> getListProjectByEmail(@NotNull final String email);
  List<InterviewVO> getListInterview();
  List<InterviewVO> getListInterviewByEmail(@NotNull final String email);
}
