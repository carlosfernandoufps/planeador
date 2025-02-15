package com.co.planeador.repository.dao;

import com.co.planeador.repository.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDao extends JpaRepository<Course, Integer> {
}
