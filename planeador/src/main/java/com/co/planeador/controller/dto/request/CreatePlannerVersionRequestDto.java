package com.co.planeador.controller.dto.request;

import com.co.planeador.repository.entities.PlannerVersion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CreatePlannerVersionRequestDto {

    private String name;
    private boolean defaultVersion;
    private List<PlannerVersion.ColumnDefinition> columnDefinitions;

}
