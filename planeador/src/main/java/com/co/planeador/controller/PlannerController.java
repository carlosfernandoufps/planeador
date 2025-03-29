package com.co.planeador.controller;

import com.co.planeador.controller.dto.response.GetPlannerResponseDto;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.service.PlannerService;
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
@RequestMapping("planner")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlannerController {

    private final PlannerService service;

    @SessionRequired
    @GetMapping()
    public ResponseEntity<GetPlannerResponseDto> getPlanner(@RequestParam("assignmentId") Integer assignmentId){
        GetPlannerResponseDto response = service.getPlannerByAssignmentId(assignmentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
