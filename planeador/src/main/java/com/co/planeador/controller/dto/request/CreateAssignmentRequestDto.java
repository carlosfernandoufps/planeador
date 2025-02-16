package com.co.planeador.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateAssignmentRequestDto {

    private Integer teacherId;
    private Integer courseId;
    private Integer semesterId;
    private String group;

    public boolean noneOfTheirAttributesAreNull(){
        return null != teacherId && null != courseId && null != semesterId && null != group && !group.isBlank();
    }

}
