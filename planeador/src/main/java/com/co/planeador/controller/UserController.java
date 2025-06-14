package com.co.planeador.controller;

import com.co.planeador.controller.dto.request.CreateUserRequestDto;
import com.co.planeador.controller.dto.request.UpdatePasswordRequestDto;
import com.co.planeador.controller.dto.request.UpdatePasswordUsingOtpRequestDto;
import com.co.planeador.controller.dto.request.UpdateProfileRequestDto;
import com.co.planeador.controller.dto.response.CreateUserResponseDto;
import com.co.planeador.controller.dto.response.ProfileResponseDto;
import com.co.planeador.repository.entities.ProfileType;
import com.co.planeador.repository.entities.User;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.security.jwt.JwtUtil;
import com.co.planeador.service.ProfileService;
import com.co.planeador.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(summary = "HU_019: Obtiene la información del usuario loggeado", description = "Requiere Sesión")
    public ResponseEntity<ProfileResponseDto> getProfileInfo(@RequestHeader("Authorization") @Parameter(hidden = true) String token){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        ProfileResponseDto dto = profileService.getProfileInfoByUserId(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @SessionRequired
    @PutMapping()
    @Operation(summary = "HU_008: Edita la información del usuario loggeado", description = "Requiere Sesión")
    public ResponseEntity<ProfileResponseDto> updateProfile(@RequestHeader("Authorization") @Parameter(hidden = true) String token,
                                                            @RequestBody UpdateProfileRequestDto requestDto){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        ProfileResponseDto responseDto = profileService.updateProfile(requestDto, userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @SessionRequired
    @PutMapping("/password")
    @Operation(summary = "HU_005: Cambia de contraseña del usuario loggeado", description = "Requiere Sesión")
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization") @Parameter(hidden = true) String token,
                                                 @RequestBody UpdatePasswordRequestDto dto){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        userService.updatePassword(dto, userId);
        return new ResponseEntity<>("Cambio de contraseña exitoso", HttpStatus.OK);
    }

    @DirectorRequired
    @GetMapping("/list")
    @Operation(summary = "HU_007: Obtiene listado de usuarios por su rol", description = "Requiere Director. " +
            "Solo admite los perfiles 'TEACHER' y 'DIRECTOR'")
    public ResponseEntity<List<ProfileResponseDto>> getProfilesByType(@RequestParam("profileType") ProfileType profileType) {
        List<ProfileResponseDto> profileResponseDtoList = profileService.getProfilesByProfileType(profileType);
        return new ResponseEntity<>(profileResponseDtoList, HttpStatus.OK);
    }

    @DirectorRequired
    @PostMapping()
    @Operation(summary = "HU_006: Crea un nuevo usuario", description = "Requiere Director. " +
            "Solo admite los perfiles 'TEACHER' y 'DIRECTOR'")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequestDto dto){
        userService.createUser(dto);
        return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.OK);
    }

    @GetMapping("/otp")
    @Operation(summary = "HU_040: Obtener código OTP", description = "Obtiene código para poder cambiar contraseña" +
            " sin estar autenticado")
    public ResponseEntity<String> getOTP(@RequestParam("institutionalEmail") String institutionalEmail){
        userService.generateOtp(institutionalEmail);
        return new ResponseEntity<>("Código único para actualizar contraseña enviado por correo de manera exitosa", HttpStatus.OK);
    }

    @PostMapping("/otp")
    @Operation(summary = "HU_041: Cambiar contraseña con OTP", description = "No requiere sesión.")
    public ResponseEntity<String> validateOtp(@RequestBody UpdatePasswordUsingOtpRequestDto dto){
        userService.updatePasswordUsingOTP(dto);
        return new ResponseEntity<>("Contraseña actualizada exitosamente", HttpStatus.OK);
    }
}
