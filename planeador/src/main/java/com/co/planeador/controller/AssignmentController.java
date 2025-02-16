package com.co.planeador.controller;

import com.co.planeador.controller.dto.request.CreateAssignmentRequestDto;
import com.co.planeador.controller.dto.response.GetAssignmentForDirectorResponseDto;
import com.co.planeador.controller.dto.response.GetAssignmentForTeacherResponseDto;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.security.annotation.TeacherRequired;
import com.co.planeador.security.jwt.JwtUtil;
import com.co.planeador.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("assignment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @DirectorRequired
    @GetMapping("/director")
    public ResponseEntity<List<GetAssignmentForDirectorResponseDto>> getAssignment(@RequestParam("idSemester") Integer idSemester,
                                                                                   @RequestParam(value = "page", required = false) Integer page,
                                                                                   @RequestParam(value = "size", required = false) Integer size){
        List<GetAssignmentForDirectorResponseDto> response = assignmentService.getSemesterAssignments(idSemester, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @GetMapping("/teacher")
    public ResponseEntity<List<GetAssignmentForTeacherResponseDto>> getTeacherAssignment(@RequestHeader("Authorization") String token,
                                                                                         @RequestParam("idSemester") Integer idSemester,
                                                                                         @RequestParam(value = "page", required = false) Integer page,
                                                                                         @RequestParam(value = "size", required = false) Integer size){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        List<GetAssignmentForTeacherResponseDto> response = assignmentService.getSemesterAssignmentsOfTeachers(idSemester, userId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DirectorRequired
    @PostMapping()
    public ResponseEntity<GetAssignmentForDirectorResponseDto> createAssignment(@RequestBody CreateAssignmentRequestDto dto){
        GetAssignmentForDirectorResponseDto response = assignmentService.createAssignment(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
