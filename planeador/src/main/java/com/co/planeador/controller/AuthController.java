package com.co.planeador.controller;

import com.co.planeador.repository.entities.ProfileType;
import com.co.planeador.repository.entities.User;
import com.co.planeador.security.jwt.JwtUtil;
import com.co.planeador.service.ProfileService;
import com.co.planeador.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;
    private final ProfileService profileService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User userRequest){
        User userValidated = userService.login(userRequest.getInstitutionalEmail(), userRequest.getPassword());
        ProfileType profileType = profileService.getProfileTypeByUserId(userValidated.getId());
        String token = JwtUtil.generateToken(userValidated.getId(), profileType.name());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
