package com.co.planeador.service;

import com.co.planeador.controller.dto.response.GetVersionResponseDto;
import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.PlannerVersionRepository;
import com.co.planeador.repository.entities.PlannerVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private GetVersionResponseDto mapPlannerVersionToDto(PlannerVersion plannerVersion){
        GetVersionResponseDto dto = new GetVersionResponseDto();
        dto.setDefaultVersion(plannerVersion.isDefaultVersion());
        dto.setVersionName(plannerVersion.getName());
        dto.setId(plannerVersion.getId());
        String columns = String.join(", ", plannerVersion.getColumnAndDescriptionMap().keySet());
        dto.setVersionColumns(columns);
        return dto;
    }



}
