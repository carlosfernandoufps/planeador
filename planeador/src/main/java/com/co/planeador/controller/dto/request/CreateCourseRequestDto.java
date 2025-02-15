package com.co.planeador.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateCourseRequestDto {

    private String courseName;
    private String description;
    private String code;
    private String fileContent;
    private String fileType;


}
