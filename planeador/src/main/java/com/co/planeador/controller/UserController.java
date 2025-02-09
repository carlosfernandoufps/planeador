package com.co.planeador.controller;

import com.co.planeador.controller.dto.ProfileResponseDto;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.security.jwt.JwtUtil;
import com.co.planeador.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Log4j2
public class UserController {

    private final ProfileService profileService;

    @SessionRequired
    @GetMapping()
    public ResponseEntity<ProfileResponseDto> getProfileInfo(@RequestHeader("Authorization") String token){
        String noBearerToken = token.substring(7);
        Integer userId = JwtUtil.getIdFromToken(noBearerToken);
        ProfileResponseDto dto = profileService.getProfileInfoByUserId(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
