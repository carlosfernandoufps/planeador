package com.co.planeador.service;

import com.co.planeador.controller.dto.request.CreatePlannerVersionRequestDto;
import com.co.planeador.controller.dto.response.GetVersionResponseDto;
import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.AssignmentDao;
import com.co.planeador.repository.dao.PlannerRepository;
import com.co.planeador.repository.dao.PlannerVersionRepository;
import com.co.planeador.repository.entities.Assignment;
import com.co.planeador.repository.entities.Planner;
import com.co.planeador.repository.entities.PlannerVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlannerVersionService {

    private final PlannerVersionRepository repository;
    private final AssignmentDao assignmentDao;
    private final PlannerRepository plannerRepository;

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
        if(dto.isDefaultVersion()){
            setAllDefaultVersionsToFalse();
        }
        PlannerVersion plannerVersion = mapDtoToPlannerVersion(dto);
        return repository.save(plannerVersion);
    }

    public void setDefaultVersion(Integer versionId){
        PlannerVersion plannerVersion = this.getPlannerVersionDetail(versionId);
        setAllDefaultVersionsToFalse();
        plannerVersion.setDefaultVersion(Boolean.TRUE);
        repository.save(plannerVersion);
    }

    public void deleteVersion(Integer versionId){
        validateNoneOfAssignmentsUseThePlannerVersion(versionId);
        repository.deleteById(versionId);
    }

    private void validateNoneOfAssignmentsUseThePlannerVersion(Integer versionId){
        List<Assignment> allAssignments = assignmentDao.findAll();
        Set<Integer> plannerIds = allAssignments.stream().map(Assignment::getPlannerId).collect(Collectors.toSet());
        List<Planner> planners = plannerRepository.findAllById(plannerIds);
        boolean existsPlannerThatUseThisVersion = planners.stream().map(planner -> planner.getPlannerVersion().getId()).
                anyMatch(plannerVersionId -> plannerVersionId.equals(versionId));
        if(existsPlannerThatUseThisVersion){
            throw new CustomException("Existen asignaciones que utilizan esta versión de planeador");
        }
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

    private void setAllDefaultVersionsToFalse(){
        List<PlannerVersion> allPlannerVersions = repository.findAll();
        boolean existsDefaultVersion = allPlannerVersions.stream().anyMatch(PlannerVersion::isDefaultVersion);
        if(existsDefaultVersion){
            allPlannerVersions.forEach(plannerVersion -> plannerVersion.setDefaultVersion(Boolean.FALSE));
            repository.saveAll(allPlannerVersions);
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
