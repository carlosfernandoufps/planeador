package com.co.planeador.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class GetAssignmentForDirectorResponseDto {

    private Integer id;
    private String courseName;
    private String teacherName;
    private String group;

}
