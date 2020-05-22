package com.humanup.matrix.ui.apimanagement.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
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
  LocalDateTime startDate;
  LocalDateTime endDate;
  List<ReviewDTO> reviewList;
}
