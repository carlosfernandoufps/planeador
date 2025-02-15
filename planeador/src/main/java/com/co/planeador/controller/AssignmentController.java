package com.co.planeador.controller;

import com.co.planeador.controller.dto.response.GetAssignmentForDirectorResponseDto;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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


}
