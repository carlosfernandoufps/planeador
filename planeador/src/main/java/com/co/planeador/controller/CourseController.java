package com.co.planeador.controller;

import com.co.planeador.controller.dto.response.GetMicrocurriculumResponse;
import com.co.planeador.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("course")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseController {

    private final CourseService courseService;

    //@SessionRequired
    @GetMapping("/microcurriculum")
    public ResponseEntity<GetMicrocurriculumResponse> getMicrocurriuclum(@RequestParam("idCourse") Integer idCourse){
        GetMicrocurriculumResponse response = courseService.getMicrocurriculum(idCourse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
