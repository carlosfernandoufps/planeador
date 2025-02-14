package com.co.planeador.controller;

import com.co.planeador.controller.dto.response.ProfileResponseDto;
import com.co.planeador.repository.entities.Semester;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.security.jwt.JwtUtil;
import com.co.planeador.service.SemesterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("semester")
@Log4j2
public class SemesterController {

    private final SemesterService semesterService;

    @SessionRequired
    @GetMapping("/active")
    public ResponseEntity<Semester> getActiveSemester(){
       Semester activeSemester = semesterService.getActiveSemester();
       return new ResponseEntity<>(activeSemester, HttpStatus.OK);
    }


}
