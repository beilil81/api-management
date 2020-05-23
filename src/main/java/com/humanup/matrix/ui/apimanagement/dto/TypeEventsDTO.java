package com.humanup.matrix.ui.apimanagement.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(of= {"titleEvent"})
public class TypeEventsDTO {
     String titleEvent;

}
