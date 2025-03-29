package com.co.planeador.controller.dto.response;

import com.co.planeador.repository.entities.PlannerVersion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GetPlannerResponseDto {

    private Integer id;
    private List<PlannerVersion.ColumnDefinition> columns;
    private List<List<String>> data;

}
