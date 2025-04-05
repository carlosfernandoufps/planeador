package com.co.planeador.controller;

import com.co.planeador.controller.dto.request.SaveNewRowRequestDto;
import com.co.planeador.controller.dto.request.UpdatePlannerRowRequestDto;
import com.co.planeador.controller.dto.response.GetCompatibleGroupPlanningResponseDto;
import com.co.planeador.controller.dto.response.GetCompatiblePlanningResponseDto;
import com.co.planeador.controller.dto.response.GetPlannerDetailResponseDto;
import com.co.planeador.controller.dto.response.GetPlannerResponseDto;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.security.annotation.TeacherRequired;
import com.co.planeador.security.jwt.JwtUtil;
import com.co.planeador.service.PlannerService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "HU_029/HU_030/HU_031/HU_032: Obtiene la información detallada de un planeador", description = "Requiere Sesión")
    public ResponseEntity<GetPlannerDetailResponseDto> getPlanner(@RequestParam("assignmentId") Integer assignmentId){
        GetPlannerDetailResponseDto response = service.getPlannerByAssignmentId(assignmentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @PostMapping()
    @Operation(summary = "HU_033: Crea un nuevo registro de planeación", description = "Requiere Docente")
    public ResponseEntity<GetPlannerResponseDto> saveNewRow(@RequestHeader("Authorization") @Parameter(hidden = true) String token,
                                                            @RequestBody SaveNewRowRequestDto dto){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        GetPlannerResponseDto response = service.saveNewRow(userId, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @PutMapping
    @Operation(summary = "HU_034: Edita registro de planeación", description = "Requiere Docente")
    public ResponseEntity<GetPlannerResponseDto> updateRow(@RequestHeader("Authorization") @Parameter(hidden = true) String token,
                                                            @RequestBody UpdatePlannerRowRequestDto dto){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        GetPlannerResponseDto response = service.updatePlanner(userId, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @DeleteMapping
    @Operation(summary = "HU_035: Elimina registro de planeación", description = "Requiere Docente")
    public ResponseEntity<GetPlannerResponseDto> deleteRow(@RequestHeader("Authorization") @Parameter(hidden = true) String token,
                                                           @RequestParam("plannerId") Integer plannerId,
                                                           @RequestParam("rowPosition") Integer rowPosition){
        Integer userId = JwtUtil.getIdFromToken( token.substring(7));
        GetPlannerResponseDto response = service.deletePlannerRow(userId, plannerId, rowPosition);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @GetMapping("/compatible/old")
    @Operation(summary = "HU_036: Carga listado de planeadores de semestres anteriores que son compatibles con la versión",
            description = "Requiere Docente")
    public ResponseEntity<List<GetCompatiblePlanningResponseDto>> getCompatible(@RequestParam("plannerId") Integer plannerId){
        List<GetCompatiblePlanningResponseDto> response = service.getCompatiblePlannersOfOldSemesters(plannerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @TeacherRequired
    @GetMapping("/compatible/group")
    @Operation(summary = "HU_037: Carga listado de planeadores que son del mismo docente, misma materia, en diferente grupo, " +
            "del semestre activo.", description = "Requiere Docente")
    public ResponseEntity<List<GetCompatibleGroupPlanningResponseDto>> getCompatibleGroups(@RequestParam("plannerId") Integer plannerId){
        List<GetCompatibleGroupPlanningResponseDto> response = service.getCompatibleGroupOfSameSemester(plannerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DirectorRequired
    @GetMapping("/report")
    @Operation(summary = "HU_038: Obtiene listado de los detalles de los planeadores por semestre " +
            "para reporte", description = "Requiere Docente")
    public ResponseEntity<List<GetPlannerDetailResponseDto>> getReport(@RequestParam("semesterId") Integer semesterId){
        List<GetPlannerDetailResponseDto> response = service.getAssignmentReportOfSemester(semesterId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
