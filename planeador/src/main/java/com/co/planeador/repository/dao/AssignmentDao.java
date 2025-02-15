package com.co.planeador.repository.dao;

import com.co.planeador.repository.entities.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentDao extends JpaRepository<Assignment, Integer> {

    Page<Assignment> findBySemesterId(Integer semesterId, Pageable pageable);
    List<Assignment> findBySemesterId(Integer semesterId);
    Page<Assignment> findBySemesterIdAndTeacherId(Integer semesterId, Integer teacherId, Pageable pageable);
    List<Assignment> findBySemesterIdAndTeacherId(Integer semesterId, Integer teacherId);


}
