package com.co.planeador.controller;

import com.co.planeador.controller.dto.response.GetVersionResponseDto;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.service.PlannerVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("version")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlannerVersionController {

    private final PlannerVersionService service;

    @DirectorRequired
    @GetMapping("/list")
    public ResponseEntity<List<GetVersionResponseDto>> getVersionList(){
        List<GetVersionResponseDto> response = service.getAllVersions();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
