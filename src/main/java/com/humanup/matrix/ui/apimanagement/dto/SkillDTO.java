package com.humanup.matrix.ui.apimanagement.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SkillDTO {

	 Long  idTypeSkills;
	 String libelle;
	 String description;
	 String typeSkills;

}
