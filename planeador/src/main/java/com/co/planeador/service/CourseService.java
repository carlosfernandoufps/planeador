package com.co.planeador.service;

import com.co.planeador.controller.dto.request.CreateCourseRequestDto;
import com.co.planeador.controller.dto.request.UpdateCourseRequestDto;
import com.co.planeador.controller.dto.response.GetCourseResponseDto;
import com.co.planeador.controller.dto.response.GetMicrocurriculumResponse;
import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.CourseDao;
import com.co.planeador.repository.dto.CourseInfoDto;
import com.co.planeador.repository.entities.Course;
import com.co.planeador.repository.entities.DocType;
import com.co.planeador.service.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseDao courseDao;
    private static final String WORD_MIME_TYPE =  "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private static final String PDF_MIME_TYPE = "application/pdf";
    private static final String WORD_EXTENSION_SUFFIX = ".docx";
    private static final String PDF_EXTENSION_SUFFIX = ".pdf";

    public GetMicrocurriculumResponse getMicrocurriculum(Integer idCourse){
        Course course = courseDao.findById(idCourse).
                orElseThrow(() -> new CustomException("No existe curso con id: " + idCourse));
        if(null == course.getMicrocurriculum() || course.getMicrocurriculum().isBlank()){
            throw new CustomException("Curso no tiene microcurrículo asignado");
        }
        return getGetMicrocurriculumResponse(course);
    }

    public GetCourseResponseDto createCourse(CreateCourseRequestDto dto){
        Course course = new Course();
        course.setName(dto.getCourseName());
        course.setDescription(null != dto.getDescription() ? dto.getDescription(): "");
        course.setCode(null != dto.getCode() ? dto.getDescription(): "");
        if(null != dto.getFileContent() && null != dto.getFileType()){
            course.setMicrocurriculum(dto.getFileContent());
            course.setDocType(dto.getFileType().equalsIgnoreCase(DocType.WORD.name()) ? DocType.WORD : DocType.PDF);
        }
        Course courseSaved = courseDao.save(course);
        return getCourseResponseDto(courseSaved);
    }

    public List<GetCourseResponseDto> getCourses(){
        List<GetCourseResponseDto> responseList = new ArrayList<>();
        List<CourseInfoDto> courses = courseDao.getCoursesInfo();
        for(CourseInfoDto course: courses){
            responseList.add(getCourseResponseDto(course));
        }
        return responseList;
    }

    public GetCourseResponseDto updateCourse(Integer courseId, UpdateCourseRequestDto dto){
        Course course = courseDao.findById(courseId).
                orElseThrow(() -> new CustomException("No existe curso con id: " + courseId));
        if(Utilities.isNotNullOrEmptyString(dto.getCourseName())){
            course.setName(dto.getCourseName());
        }
        if(Utilities.isNotNullOrEmptyString(dto.getDescription())){
            course.setDescription(dto.getDescription());
        }
        if(Utilities.isNotNullOrEmptyString(dto.getCode())){
            course.setCode(dto.getDescription());
        }
        if(null != dto.getFileContent() && null != dto.getFileType()){
            course.setMicrocurriculum(dto.getFileContent());
            course.setDocType(dto.getFileType().equalsIgnoreCase(DocType.WORD.name()) ? DocType.WORD : DocType.PDF);
        }
        Course courseSaved = courseDao.save(course);
        return getCourseResponseDto(courseSaved);
    }

    private GetMicrocurriculumResponse getGetMicrocurriculumResponse(Course course) {
        GetMicrocurriculumResponse response = new GetMicrocurriculumResponse();
        response.setContent(course.getMicrocurriculum());
        if(DocType.WORD.equals(course.getDocType())){
            response.setFileName(course.getName() + WORD_EXTENSION_SUFFIX);
            response.setMimeType(WORD_MIME_TYPE);
        }
        if(DocType.PDF.equals(course.getDocType())){
            response.setFileName(course.getName() + PDF_EXTENSION_SUFFIX);
            response.setMimeType(PDF_MIME_TYPE);
        }
        return response;
    }

    private GetCourseResponseDto getCourseResponseDto(Course course){
        GetCourseResponseDto dto = new GetCourseResponseDto();
        dto.setId(course.getId());
        dto.setCode(course.getCode());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        return dto;
    }

    private GetCourseResponseDto getCourseResponseDto(CourseInfoDto dto){
        GetCourseResponseDto courseResponse = new GetCourseResponseDto();
        courseResponse.setName(dto.getName());
        courseResponse.setCode(dto.getCode());
        courseResponse.setId(dto.getId());
        courseResponse.setDescription(dto.getDescription());
        return courseResponse;
    }

}
