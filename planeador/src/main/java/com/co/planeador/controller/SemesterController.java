package com.co.planeador.controller;

import com.co.planeador.repository.entities.Semester;
import com.co.planeador.repository.entities.User;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.security.annotation.TeacherRequired;
import com.co.planeador.service.SemesterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("semester")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SemesterController {

    private final SemesterService semesterService;

    @SessionRequired
    @GetMapping("/active")
    @Operation(summary = "HU_003/004: Obtiene el semestre activo", description = "Requiere Sesión")
    public ResponseEntity<Semester> getActiveSemester(){
       Semester activeSemester = semesterService.getActiveSemester();
       return new ResponseEntity<>(activeSemester, HttpStatus.OK);
    }

    @DirectorRequired
    @GetMapping()
    @Operation(summary = "HU_013: Obtiene todos los semestres", description = "Requiere Director. " +
            "Los semestres vienen ordenados descendentemente por fecha de inicio.")
    public ResponseEntity<List<Semester>> getAllSemesters(){
        List<Semester> semesters = semesterService.getSemesters();
        return new ResponseEntity<>(semesters, HttpStatus.OK);
    }

    @DirectorRequired
    @PutMapping()
    @Operation(summary = "HU_014: Edita semestre", description = "Requiere Director",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de Semestre",
                                    value = """
                                                {
                                                  "name": "2025-I",
                                                  "startDate": "2025-01-16",
                                                  "endDate": "2025-06-24"
                                                }
                                            """
                            )
                    )
            )
    )
    public ResponseEntity<Semester> updateSemester(@RequestParam("idSemester") Integer idSemester,
                                                   @RequestBody Semester request){
        Semester semesterUpdated = semesterService.updateSemester(idSemester, request);
        return new ResponseEntity<>(semesterUpdated, HttpStatus.OK);
    }

    @DirectorRequired
    @PostMapping
    @Operation(summary = "HU_012: Crea semestre", description = "Requiere Director",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de Semestre",
                                    value = """
                                                {
                                                  "name": "2025-I",
                                                  "startDate": "2025-01-16",
                                                  "endDate": "2025-06-24"
                                                }
                                            """
                            )
                    )
            )
    )
    public ResponseEntity<Semester> createSemester(@RequestBody Semester request){
        Semester semesterUpdated = semesterService.createSemester(request);
        return new ResponseEntity<>(semesterUpdated, HttpStatus.OK);
    }

    @TeacherRequired
    @GetMapping("/before")
    @Operation(summary = "HU_018: Obtiene los semestres anteriores a la fecha actual", description = "Requiere Docente")
    public ResponseEntity<List<Semester>> getOldSemesters(){
        List<Semester> semesters = semesterService.getSemestersBeforeNow();
        return new ResponseEntity<>(semesters, HttpStatus.OK);
    }

}
