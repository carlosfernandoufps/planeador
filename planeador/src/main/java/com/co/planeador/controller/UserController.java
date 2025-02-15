package com.co.planeador.controller;

import com.co.planeador.controller.dto.request.CreateUserRequestDto;
import com.co.planeador.controller.dto.request.UpdatePasswordRequestDto;
import com.co.planeador.controller.dto.request.UpdateProfileRequestDto;
import com.co.planeador.controller.dto.response.CreateUserResponseDto;
import com.co.planeador.controller.dto.response.ProfileResponseDto;
import com.co.planeador.repository.entities.ProfileType;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.security.jwt.JwtUtil;
import com.co.planeador.service.ProfileService;
import com.co.planeador.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    @PutMapping()
    public ResponseEntity<ProfileResponseDto> updateProfile(@RequestHeader("Authorization") String token,
                                                            @RequestBody UpdateProfileRequestDto requestDto){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        ProfileResponseDto responseDto = profileService.updateProfile(requestDto, userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @SessionRequired
    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization") String token,
                                                 @RequestBody UpdatePasswordRequestDto dto){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        userService.updatePassword(dto, userId);
        return new ResponseEntity<>("Cambio de contraseña exitoso", HttpStatus.OK);
    }

    @DirectorRequired
    @GetMapping("/list")
    public ResponseEntity<List<ProfileResponseDto>> getProfilesByType(@RequestParam("profileType") ProfileType profileType) {
        List<ProfileResponseDto> profileResponseDtoList = profileService.getProfilesByProfileType(profileType);
        return new ResponseEntity<>(profileResponseDtoList, HttpStatus.OK);
    }

    @DirectorRequired
    @PostMapping()
    public ResponseEntity<CreateUserResponseDto> createUser(@RequestBody CreateUserRequestDto dto){
        CreateUserResponseDto responseDto = userService.createUser(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
