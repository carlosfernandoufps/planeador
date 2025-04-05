package com.co.planeador.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetCompatiblePlanningResponseDto {

    private Integer planningId;
    private String semesterName;
    private String teacherName;
    private String group;


}
