package com.humanup.matrix.ui.apimanagement.vo;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PersonVO {
  String firstName;
  String lastName;
  String mailAdresses;
  String birthDate;
  ProfileVO profile;
  List<SkillVO> skillVOList;
  List<InterviewVO> interviews;
  List<ProjectVO> projects;
  List<CourseVO> courses;
  List<ReviewVO> reviews;
}
