package com.co.planeador.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SaveNewRowBatchRequestDto {

    private Integer plannerId;
    List<List<String>> batchData;

}
