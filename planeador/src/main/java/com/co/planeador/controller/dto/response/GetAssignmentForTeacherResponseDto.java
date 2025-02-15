package com.co.planeador.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetAssignmentForTeacherResponseDto {

    private Integer id;
    private Integer courseId;
    private String courseName;
    private String group;

}
