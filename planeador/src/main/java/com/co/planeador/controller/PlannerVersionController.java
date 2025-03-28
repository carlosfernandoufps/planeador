package com.co.planeador.controller;

import com.co.planeador.controller.dto.request.CreatePlannerVersionRequestDto;
import com.co.planeador.controller.dto.response.GetVersionResponseDto;
import com.co.planeador.repository.entities.PlannerVersion;
import com.co.planeador.security.annotation.DirectorRequired;
import com.co.planeador.service.PlannerVersionService;
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
    public ResponseEntity<List<GetVersionResponseDto>> getVersionList(){
        List<GetVersionResponseDto> response = service.getAllVersions();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DirectorRequired
    @GetMapping()
    public ResponseEntity<PlannerVersion> getVersionDetail(@RequestParam("versionId") Integer versionId){
        PlannerVersion response = service.getPlannerVersionDetail(versionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DirectorRequired
    @PostMapping()
    public ResponseEntity<PlannerVersion> createPlannerVersion(@RequestBody CreatePlannerVersionRequestDto dto){
        PlannerVersion response = service.createPlannerVersion(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DirectorRequired
    @GetMapping("/default")
    public ResponseEntity<String> setVersionToDefault(@RequestParam("versionId") Integer versionId){
        service.setDefaultVersion(versionId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DirectorRequired
    @DeleteMapping()
    public ResponseEntity<String> deleteVersion(@RequestParam("versionId") Integer versionId){
        service.deleteVersion(versionId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}
