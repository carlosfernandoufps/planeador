package com.co.planeador.controller;

import com.co.planeador.repository.entities.ProfileType;
import com.co.planeador.repository.entities.User;
import com.co.planeador.security.jwt.JwtUtil;
import com.co.planeador.service.ProfileService;
import com.co.planeador.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final UserService userService;
    private final ProfileService profileService;

    @PostMapping("/login")
    @Operation(
            summary = "HU_001: Inicia sesión",
            description = "No requiere sesión",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class),
                            examples = {
                                @ExampleObject(
                                name = "Ejemplo de Director",
                                value =
                                    """
                                    {
                                        "institutionalEmail": "director@mail.com",
                                        "password": "director123"
                                    }
                                    """
                                ),
                                @ExampleObject(
                                        name = "Ejemplo de Docente",
                                        value =
                                    """
                                    {
                                        "institutionalEmail": "teacher1@mail.com",
                                        "password": "docente123"
                                    }
                                    """
                                )
                            }
                    )
            )
    )
    public ResponseEntity<String> login(@RequestBody User userRequest){
        User userValidated = userService.login(userRequest.getInstitutionalEmail(), userRequest.getPassword());
        ProfileType profileType = profileService.getProfileTypeByUserId(userValidated.getId());
        String token = JwtUtil.generateToken(userValidated.getId(), profileType.name());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
