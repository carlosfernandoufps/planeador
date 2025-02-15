package com.co.planeador.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetCourseResponseDto {

    private Integer id;
    private String name;
    private String description;
    private String code;

}
