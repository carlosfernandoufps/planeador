package com.co.planeador.controller.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordUsingOtpRequestDto {

    private String institutionalEmail;
    private String otp;
    private String newPassword;

}
