package com.co.planeador.repository.dao;

import com.co.planeador.repository.entities.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PlannerRepository extends JpaRepository<Planner, Integer> {

    @Query("SELECT p.id FROM Planner p WHERE p.plannerVersion.id = :plannerVersionId")
    Set<Integer> getCompatiblePlannersIdsByVersion(@Param("plannerVersionId") Integer plannerVersionId);

}
