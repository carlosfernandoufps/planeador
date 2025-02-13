package com.co.planeador.controller.dto.request;

import com.co.planeador.repository.entities.ProfileType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequestDto {

    private String institutionalEmail;
    private String name;
    private String personalEmail;
    private String phone;
    private String code;
    private ProfileType profileType;

}
