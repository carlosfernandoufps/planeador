package com.co.planeador.controller;

import com.co.planeador.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("test")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {

    private final EmailService emailService;

    @GetMapping()
    public ResponseEntity<String> sendEmail(){
        emailService.sendEmail("cafcalderonri@gmail.com", "prueba", "sus credenciales son:");
        return new ResponseEntity<>("Mensaje enviado", HttpStatus.OK);
    }

}
