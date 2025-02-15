package com.co.planeador.service;

import com.co.planeador.controller.dto.response.GetAssignmentForDirectorResponseDto;
import com.co.planeador.controller.dto.response.GetAssignmentForTeacherResponseDto;
import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.AssignmentDao;
import com.co.planeador.repository.dao.CourseDao;
import com.co.planeador.repository.dao.ProfileDao;
import com.co.planeador.repository.dao.SemesterDao;
import com.co.planeador.repository.dao.UserDao;
import com.co.planeador.repository.dto.CourseInfoDto;
import com.co.planeador.repository.entities.Assignment;
import com.co.planeador.repository.entities.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentDao assignmentDao;
    private final UserDao userDao;
    private final ProfileDao profileDao;
    private final CourseDao courseDao;
    private final SemesterDao semesterDao;

    public List<GetAssignmentForDirectorResponseDto> getSemesterAssignments(Integer semesterId, Integer pageNumber, Integer pageSize){
        List<GetAssignmentForDirectorResponseDto> responseDtoList = new ArrayList<>();
        List<Assignment> assignments;
        if(isValidPageableRequest(pageNumber, pageSize)){
            Page<Assignment> assignmentPage = assignmentDao.findBySemesterId(semesterId,
                    PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending()));
            assignments = assignmentPage.stream().toList();
        }else {
            assignments = assignmentDao.findBySemesterId(semesterId);
        }
        for(Assignment assignment: assignments){
            responseDtoList.add(buildGetAssignmentForDirectorResponseDto(assignment));
        }
        return responseDtoList;
    }

    public List<GetAssignmentForTeacherResponseDto> getSemesterAssignmentsOfTeachers(Integer semesterId, Integer userId,
                                                                                     Integer pageNumber, Integer pageSize){
        Teacher teacher = (Teacher) profileDao.findOneByUserId(userId);
        List<Assignment> assignments;
        List<GetAssignmentForTeacherResponseDto> response = new ArrayList<>();
        if(isValidPageableRequest(pageNumber, pageSize)){
            Page<Assignment> assignmentPage = assignmentDao.findBySemesterIdAndTeacherId(semesterId, teacher.getId(),
                    PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending()));
            assignments = assignmentPage.stream().toList();
        }else {
            assignments = assignmentDao.findBySemesterIdAndTeacherId(semesterId, teacher.getId());
        }
        for(Assignment assignment: assignments){
            response.add(buildGetAssignmentForTeacherResponseDto(assignment));
        }
        return response;
    }

    private GetAssignmentForTeacherResponseDto buildGetAssignmentForTeacherResponseDto(Assignment assignment){
        CourseInfoDto courseInfoDto = courseDao.getCourseById(assignment.getCourseId());
        GetAssignmentForTeacherResponseDto dto = new GetAssignmentForTeacherResponseDto();
        dto.setCourseId(assignment.getCourseId());
        dto.setId(assignment.getId());
        dto.setCourseName(courseInfoDto.getName());
        dto.setGroup(assignment.getGroupName());
        return dto;
    }

    private GetAssignmentForDirectorResponseDto buildGetAssignmentForDirectorResponseDto(Assignment assignment){
        Teacher teacher = (Teacher) profileDao.findById(assignment.getTeacherId())
                .orElseThrow(CustomException::new);
        CourseInfoDto courseInfoDto = courseDao.getCourseById(assignment.getCourseId());
        GetAssignmentForDirectorResponseDto dto = new GetAssignmentForDirectorResponseDto();
        dto.setId(assignment.getId());
        dto.setGroup(assignment.getGroupName());
        dto.setCourseName(courseInfoDto.getName());
        dto.setTeacherName(teacher.getName());
        return dto;
    }

    private boolean isValidPageableRequest(Integer pageNumber, Integer pageSize){
        if(null == pageNumber || null == pageSize || pageNumber < 0){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
