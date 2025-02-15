package com.co.planeador.repository.dao;

import com.co.planeador.repository.dto.CourseInfoDto;
import com.co.planeador.repository.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseDao extends JpaRepository<Course, Integer> {

    @Query("SELECT c.id as id, c.name as name, c.description as description, c.code as code FROM Course c")
    List<CourseInfoDto> getCoursesInfo();

}
