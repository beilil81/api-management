package com.humanup.matrix.ui.apimanagement.vo;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(of= {"titleEvent"})
public class TypeEventsVO {
     String titleEvent;


}
