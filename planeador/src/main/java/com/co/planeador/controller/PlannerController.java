package com.co.planeador.controller;

import com.co.planeador.controller.dto.request.SaveNewRowRequestDto;
import com.co.planeador.controller.dto.request.UpdatePlannerRowRequestDto;
import com.co.planeador.controller.dto.response.GetCompatibleGroupPlanningResponseDto;
import com.co.planeador.controller.dto.response.GetCompatiblePlanningResponseDto;
import com.co.planeador.controller.dto.response.GetPlannerDetailResponseDto;
import com.co.planeador.controller.dto.response.GetPlannerResponseDto;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.security.annotation.TeacherRequired;
import com.co.planeador.security.jwt.JwtUtil;
import com.co.planeador.service.PlannerService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("planner")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlannerController {

    private final PlannerService service;

    @SessionRequired
    @GetMapping()
    public ResponseEntity<GetPlannerDetailResponseDto> getPlanner(@RequestParam("assignmentId") Integer assignmentId){
        GetPlannerDetailResponseDto response = service.getPlannerByAssignmentId(assignmentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @PostMapping()
    public ResponseEntity<GetPlannerResponseDto> saveNewRow(@RequestHeader("Authorization") @Parameter(hidden = true) String token,
                                                            @RequestBody SaveNewRowRequestDto dto){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        GetPlannerResponseDto response = service.saveNewRow(userId, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @PutMapping
    public ResponseEntity<GetPlannerResponseDto> updateRow(@RequestHeader("Authorization") @Parameter(hidden = true) String token,
                                                            @RequestBody UpdatePlannerRowRequestDto dto){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        GetPlannerResponseDto response = service.updatePlanner(userId, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @DeleteMapping
    public ResponseEntity<GetPlannerResponseDto> deleteRow(@RequestHeader("Authorization") @Parameter(hidden = true) String token,
                                                           @RequestParam("plannerId") Integer plannerId,
                                                           @RequestParam("rowPosition") Integer rowPosition){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        GetPlannerResponseDto response = service.deletePlannerRow(userId, plannerId, rowPosition);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @GetMapping("/compatible/old")
    public ResponseEntity<List<GetCompatiblePlanningResponseDto>> getCompatible(@RequestParam("plannerId") Integer plannerId){
        List<GetCompatiblePlanningResponseDto> response = service.getCompatiblePlannersOfOldSemesters(plannerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @GetMapping("/compatible/group")
    public ResponseEntity<List<GetCompatibleGroupPlanningResponseDto>> getCompatibleGroups(@RequestParam("plannerId") Integer plannerId){
        List<GetCompatibleGroupPlanningResponseDto> response = service.getCompatibleGroupOfSameSemester(plannerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
