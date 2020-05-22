package com.humanup.matrix.ui.apimanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(of = {"emailPerson", "reviewList"})
public class InternDTO {
  String emailPerson;
  @JsonIgnore List<ReviewDTO> reviewList;
}
