package com.co.planeador.service;

import com.co.planeador.controller.dto.request.CreatePlannerVersionRequestDto;
import com.co.planeador.controller.dto.response.GetVersionResponseDto;
import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.PlannerVersionRepository;
import com.co.planeador.repository.entities.PlannerVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlannerVersionService {

    private final PlannerVersionRepository repository;

    public List<GetVersionResponseDto> getAllVersions(){
        List<PlannerVersion> plannerVersionList = repository.findAll();
        return plannerVersionList.stream().map(this::mapPlannerVersionToDto).toList();
    }

    public PlannerVersion getPlannerVersionDetail(int plannerVersionId){
        return repository.findById(plannerVersionId).
                orElseThrow(() -> new CustomException("No se encontró planeador con id: " + plannerVersionId));
    }

    public PlannerVersion createPlannerVersion(CreatePlannerVersionRequestDto dto){
        validateCreatePlannerVersionInput(dto);
        checkDefaultVersions(dto);
        PlannerVersion plannerVersion = mapDtoToPlannerVersion(dto);
        return repository.save(plannerVersion);
    }

    private PlannerVersion mapDtoToPlannerVersion(CreatePlannerVersionRequestDto dto){
        PlannerVersion plannerVersion = new PlannerVersion();
        plannerVersion.setDefaultVersion(dto.isDefaultVersion());
        plannerVersion.setName(dto.getName());
        plannerVersion.setColumnDefinitions(dto.getColumnDefinitions());
        return plannerVersion;
    }

    private void validateCreatePlannerVersionInput(CreatePlannerVersionRequestDto dto){
        if(dto.getColumnDefinitions() == null || dto.getColumnDefinitions().isEmpty()){
            throw new CustomException("Columnas no pueden ser vacías");
        }
    }

    private void checkDefaultVersions(CreatePlannerVersionRequestDto dto){
        List<PlannerVersion> allPlannerVersions = repository.findAll();
        if(allPlannerVersions.isEmpty()){
            dto.setDefaultVersion(Boolean.TRUE);
        }
        else{
            boolean existsDefaultVersion = allPlannerVersions.stream().anyMatch(PlannerVersion::isDefaultVersion);
            if(!existsDefaultVersion){
                dto.setDefaultVersion(Boolean.TRUE);
            } else if(dto.isDefaultVersion()){
                allPlannerVersions.forEach(plannerVersion -> plannerVersion.setDefaultVersion(Boolean.FALSE));
                repository.saveAll(allPlannerVersions);
            }
        }
    }

    private GetVersionResponseDto mapPlannerVersionToDto(PlannerVersion plannerVersion){
        GetVersionResponseDto dto = new GetVersionResponseDto();
        dto.setDefaultVersion(plannerVersion.isDefaultVersion());
        dto.setVersionName(plannerVersion.getName());
        dto.setId(plannerVersion.getId());
        String columns = plannerVersion.getColumnDefinitions().stream().
                map(PlannerVersion.ColumnDefinition::getName).collect(Collectors.joining(", "));
        dto.setVersionColumns(columns);
        return dto;
    }



}
