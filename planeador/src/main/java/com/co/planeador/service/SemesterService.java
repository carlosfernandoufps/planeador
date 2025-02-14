package com.co.planeador.service;

import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.SemesterDao;
import com.co.planeador.repository.entities.Semester;
import com.co.planeador.service.util.Utilities;
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

    public Semester updateSemester(Integer idSemester, Semester request){
        Semester semester = semesterDao.findById(idSemester)
                .orElseThrow(() -> new CustomException("No existe semestre con id: " + idSemester));
        if(null != request.getStartDate()){
            semester.setStartDate(request.getStartDate());
        }
        if(null != request.getEndDate()){
            semester.setEndDate(request.getEndDate());
        }
        if(isNotValidGapDate(semester)){
            throw new CustomException("La fecha final del semestre no puede ser menor o igual a la fecha de inicio");
        }
        if(doesSemesterCollidesWithCreatedSemesters(semester)) {
            throw new CustomException("El rango de fechas del semestre se solapa con otro ya existente");
        }
        if(Utilities.isNotNullOrEmptyString(request.getName())){
            semester.setName(request.getName());
        }
        return semesterDao.save(semester);
    }

    private boolean doesSemesterCollidesWithCreatedSemesters(Semester semester){
        List<Semester> createdSemesters = getSemesters();
        for(Semester createdSemester: createdSemesters){
            if(null != semester.getId() && createdSemester.getId().equals(semester.getId())){
                continue;
            }
            if(doesSemesterCollide(createdSemester, semester)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private boolean doesSemesterCollide(Semester semester1, Semester semester2) {
        return semester1.getStartDate().isBefore(semester2.getEndDate())
                && semester2.getStartDate().isBefore(semester1.getEndDate());
    }

    private boolean isNotValidGapDate(Semester semester){
        return semester.getEndDate().isBefore(semester.getStartDate())
                || semester.getEndDate().isEqual(semester.getStartDate());
    }

    public Semester createSemester(Semester semester){
        if(null != semester.getId()){
            throw new CustomException("asignación de ID no es válida");
        }
        if(null == semester.getName() || null == semester.getStartDate() || null == semester.getEndDate()){
            throw new CustomException("Faltan datos requeridos");
        }
        if(isNotValidGapDate(semester)){
            throw new CustomException("La fecha final del semestre no puede ser menor o igual a la fecha de inicio");
        }
        if(doesSemesterCollidesWithCreatedSemesters(semester)) {
            throw new CustomException("El rango de fechas del semestre se solapa con otro ya existente");
        }
        return semesterDao.save(semester);
    }


}
