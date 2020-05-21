package com.humanup.matrix.ui.apimanagement.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PersonDTO {
     String firstName;
     String lastName;
     String mailAdresses;
     String birthDate;
     String profile;
     List<SkillDTO> skillVOList;
}

