package com.co.planeador.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetCompatibleGroupPlanningResponseDto {

    private Integer planningId;
    private String group;

}
