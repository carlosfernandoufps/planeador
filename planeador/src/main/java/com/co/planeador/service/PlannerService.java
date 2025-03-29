package com.co.planeador.service;

import com.co.planeador.controller.dto.response.GetPlannerResponseDto;
import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.AssignmentDao;
import com.co.planeador.repository.dao.PlannerRepository;
import com.co.planeador.repository.entities.Assignment;
import com.co.planeador.repository.entities.Planner;
import com.co.planeador.repository.entities.PlannerRow;
import com.co.planeador.repository.entities.PlannerVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final PlannerRepository repository;
    private final PlannerVersionService plannerVersionService;
    private final AssignmentDao assignmentDao;

    public Planner createNewPlanner(Integer plannerVersionId){
        PlannerVersion plannerVersion = plannerVersionService.getPlannerVersionDetail(plannerVersionId);
        Planner planner = new Planner();
        planner.setPlannerVersion(plannerVersion);
        return repository.save(planner);
    }

    public GetPlannerResponseDto getPlannerByAssignmentId(Integer assignmentId){
        Assignment assignment  = assignmentDao.findById(assignmentId).orElseThrow(() ->
                new CustomException("Asignación con id: " + assignmentId + " no encontrada"));
        Planner planner = repository.findById(assignment.getPlannerId()).orElseThrow(() ->
                new CustomException("Planeador no encontrado"));
        return mapPlannerToDtoResponse(planner);
    }

    private GetPlannerResponseDto mapPlannerToDtoResponse(Planner planner){
        GetPlannerResponseDto dto = new GetPlannerResponseDto();
        dto.setId(planner.getId());
        dto.setColumns(planner.getPlannerVersion().getColumnDefinitions());
        dto.setData(mapPlannerRowToDTo(planner.getRows()));
        return dto;
    }

    private List<List<String>> mapPlannerRowToDTo(List<PlannerRow> plannerRows){
        List<List<String>> rows = new ArrayList<>();
        for(PlannerRow plannerRow: plannerRows){
            List<String> rowInfo = plannerRow.getCells();
            rows.add(rowInfo);
        }
        return rows;
    }

}
