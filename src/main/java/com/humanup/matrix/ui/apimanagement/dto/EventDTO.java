package com.humanup.matrix.ui.apimanagement.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventDTO {

	 Long  idTypeEvents;
	 String libelle;
	 String description;
	 String typeEvents;

}
