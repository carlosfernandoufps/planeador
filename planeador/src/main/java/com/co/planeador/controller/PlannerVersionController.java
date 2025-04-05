package com.co.planeador.controller;

import com.co.planeador.controller.dto.request.CreatePlannerVersionRequestDto;
import com.co.planeador.controller.dto.response.GetVersionResponseDto;
import com.co.planeador.repository.entities.PlannerVersion;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.service.PlannerVersionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("version")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlannerVersionController {

    private final PlannerVersionService service;

    @DirectorRequired
    @GetMapping("/list")
    @Operation(summary = "HU_023_1/HU_025/HU_028: Obtiene todas las versiones de planeadores", description = "Requiere Director")
    public ResponseEntity<List<GetVersionResponseDto>> getVersionList(){
        List<GetVersionResponseDto> response = service.getAllVersions();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DirectorRequired
    @GetMapping()
    @Operation(summary = "HU_023_2: Obtiene la información detallada de un planeador", description = "Requiere Director")
    public ResponseEntity<PlannerVersion> getVersionDetail(@RequestParam("versionId") Integer versionId){
        PlannerVersion response = service.getPlannerVersionDetail(versionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DirectorRequired
    @PostMapping()
    @Operation(summary = "HU_024: Genera una nueva versión del planeador", description = "Requiere Director")
    public ResponseEntity<PlannerVersion> createPlannerVersion(@RequestBody CreatePlannerVersionRequestDto dto){
        PlannerVersion response = service.createPlannerVersion(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DirectorRequired
    @GetMapping("/default")
    @Operation(summary = "HU_026_1: Asigna una versión de planeador por defecto", description = "Requiere Director")
    public ResponseEntity<String> setVersionToDefault(@RequestParam("versionId") Integer versionId){
        service.setDefaultVersion(versionId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DirectorRequired
    @DeleteMapping()
    @Operation(summary = "HU_026_2: Elimina una versión de planeador", description = "Requiere Director")
    public ResponseEntity<String> deleteVersion(@RequestParam("versionId") Integer versionId){
        service.deleteVersion(versionId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}
