package com.co.planeador.repository.dao;

import com.co.planeador.repository.entities.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SemesterDao extends JpaRepository<Semester, Integer> {

    @Query("SELECT s FROM Semester s WHERE s.startDate <= :specificDate AND s.endDate >= :specificDate")
    Semester findOneByDateRange(@Param("specificDate") LocalDate specificDate);

    List<Semester> findAllByOrderByStartDateDesc();

}
