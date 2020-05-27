package com.humanup.matrix.ui.apimanagement.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventDTO implements Serializable {
	Long  idTypeEvents;
	String libelle;
	String description;
	String emailPerson;
	String typeEvents;

}
