package com.humanup.matrix.ui.apimanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(of = {"courseId", "internId", "courseTitle", "internEmail", "createdOn", "score"})
public class ReviewDTO {
  @JsonIgnore long courseId;
  @JsonIgnore long internId;
  String courseTitle;
  String internEmail;
  LocalDateTime createdOn;
  int score;
}
