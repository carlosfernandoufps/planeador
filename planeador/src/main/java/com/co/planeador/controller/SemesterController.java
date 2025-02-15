package com.co.planeador.controller;

import com.co.planeador.repository.entities.Semester;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("semester")
public class SemesterController {

    private final SemesterService semesterService;

    @SessionRequired
    @GetMapping("/active")
    public ResponseEntity<Semester> getActiveSemester(){
       Semester activeSemester = semesterService.getActiveSemester();
       return new ResponseEntity<>(activeSemester, HttpStatus.OK);
    }

    @SessionRequired
    @GetMapping()
    public ResponseEntity<List<Semester>> getAllSemesters(){
        List<Semester> semesters = semesterService.getSemesters();
        return new ResponseEntity<>(semesters, HttpStatus.OK);
    }

    @DirectorRequired
    @PutMapping()
    public ResponseEntity<Semester> updateSemester(@RequestParam("idSemester") Integer idSemester,
                                                   @RequestBody Semester request){
        Semester semesterUpdated = semesterService.updateSemester(idSemester, request);
        return new ResponseEntity<>(semesterUpdated, HttpStatus.OK);
    }

    @DirectorRequired
    @PostMapping
    public ResponseEntity<Semester> createSemester(@RequestBody Semester request){
        Semester semesterUpdated = semesterService.createSemester(request);
        return new ResponseEntity<>(semesterUpdated, HttpStatus.OK);
    }

    @SessionRequired
    @GetMapping("/before")
    public ResponseEntity<List<Semester>> getOldSemesters(){
        List<Semester> semesters = semesterService.getSemestersBeforeNow();
        return new ResponseEntity<>(semesters, HttpStatus.OK);
    }

}
