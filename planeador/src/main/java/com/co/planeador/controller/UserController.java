package com.co.planeador.controller;

import com.co.planeador.controller.dto.request.UpdatePasswordRequestDto;
import com.co.planeador.controller.dto.response.ProfileResponseDto;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.security.jwt.JwtUtil;
import com.co.planeador.service.ProfileService;
import com.co.planeador.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Log4j2
public class UserController {

    private final ProfileService profileService;
    private final UserService userService;

    @SessionRequired
    @GetMapping()
    public ResponseEntity<ProfileResponseDto> getProfileInfo(@RequestHeader("Authorization") String token){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        ProfileResponseDto dto = profileService.getProfileInfoByUserId(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @SessionRequired
    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization") String token,
                                                 @RequestBody UpdatePasswordRequestDto dto){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        userService.updatePassword(dto, userId);
        return new ResponseEntity<>("Cambio de contraseña exitoso", HttpStatus.OK);
    }

}
