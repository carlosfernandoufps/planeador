package com.co.planeador.service;

import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.SemesterDao;
import com.co.planeador.repository.entities.Semester;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterDao semesterDao;

    public Semester getActiveSemester(){
        Semester activeSemester =  semesterDao.findOneByDateRange(LocalDate.now());
        if(null == activeSemester){
            throw new CustomException("No hay semestre activo");
        }
        return activeSemester;
    }

    public List<Semester> getSemesters(){
        return semesterDao.findAllByOrderByStartDateDesc();
    }

}
