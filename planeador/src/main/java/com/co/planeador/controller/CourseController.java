package com.co.planeador.controller;

import com.co.planeador.controller.dto.request.CreateCourseRequestDto;
import com.co.planeador.controller.dto.request.UpdateCourseRequestDto;
import com.co.planeador.controller.dto.response.GetCourseResponseDto;
import com.co.planeador.controller.dto.response.GetMicrocurriculumResponse;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("course")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseController {

    private final CourseService courseService;

    @SessionRequired
    @GetMapping("/microcurriculum")
    @Operation(summary = "HU_010/017: Obtiene base64 del microcurriculo", description = "Requiere Sesión")
    public ResponseEntity<GetMicrocurriculumResponse> getMicrocurriuclum(@RequestParam("idCourse") Integer idCourse){
        GetMicrocurriculumResponse response = courseService.getMicrocurriculum(idCourse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DirectorRequired
    @PostMapping()
    @Operation(summary = "HU_010/017: Crea un nuevo curso", description = "Requiere Director")
    public ResponseEntity<GetCourseResponseDto> createCourse(@RequestBody CreateCourseRequestDto dto){
        GetCourseResponseDto course = courseService.createCourse(dto);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DirectorRequired
    @GetMapping()
    @Operation(summary = "HU_010: Obtiene los cursos creados", description = "Requiere Director")
    public ResponseEntity<List<GetCourseResponseDto>> getCourses(){
        List<GetCourseResponseDto> courses = courseService.getCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @DirectorRequired
    @PutMapping()
    @Operation(summary = "HU_011: Edita un curso", description = "Requiere Director")
    public ResponseEntity<GetCourseResponseDto> updateCourse(@RequestParam("idCourse") Integer idCourse,
                                                             @RequestBody UpdateCourseRequestDto dto){
        GetCourseResponseDto response = courseService.updateCourse(idCourse, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
