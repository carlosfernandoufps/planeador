package com.co.planeador.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetVersionResponseDto {

    private Integer id;
    private String versionName;
    private boolean isDefaultVersion;
    private String versionColumns;

}
