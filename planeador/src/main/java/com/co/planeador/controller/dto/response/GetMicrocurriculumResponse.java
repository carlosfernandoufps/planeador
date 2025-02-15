package com.co.planeador.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetMicrocurriculumResponse {

    private String content;
    private String fileName;
    private String mimeType;
}
