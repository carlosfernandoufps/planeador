package com.co.planeador.service;

import com.co.planeador.repository.dao.PlannerRepository;
import com.co.planeador.repository.entities.Planner;
import com.co.planeador.repository.entities.PlannerVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final PlannerRepository repository;
    private final PlannerVersionService plannerVersionService;

    public Planner createNewPlanner(Integer plannerVersionId){
        PlannerVersion plannerVersion = plannerVersionService.getPlannerVersionDetail(plannerVersionId);
        Planner planner = new Planner();
        planner.setPlannerVersion(plannerVersion);
        return repository.save(planner);
    }

}
