package com.humanup.matrix.ui.apimanagement.graphql.query;

import com.humanup.matrix.ui.apimanagement.vo.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IQueryCourse {

  List<CourseVO> getListCourses();
  List<CourseVO> getListCoursesByEmail(@NotNull final String email);
}
