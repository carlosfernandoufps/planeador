package com.co.planeador.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProfileResponseDto {

    private String name;
    private String personalEmail;
    private String phone;
    private String code;
}
