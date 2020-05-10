package com.humanup.matrix.ui.apimanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(
    of = {
      "courseTypeTitle",
      "trainerEmail",
      "title",
      "description",
      "startDate",
      "endDate",
      "reviewList"
    })
public class CourseDTO {
  String courseTypeTitle;
  String trainerEmail;
  String title;
  String description;
  Date startDate;
  Date endDate;
  List<ReviewDTO> reviewList;
}
