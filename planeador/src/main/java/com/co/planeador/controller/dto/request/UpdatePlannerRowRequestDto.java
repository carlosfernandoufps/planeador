package com.co.planeador.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UpdatePlannerRowRequestDto {

    private Integer plannerId;
    private Integer rowPosition;
    private List<String> data;



}
