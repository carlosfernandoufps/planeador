package com.co.planeador.service;

import com.co.planeador.controller.dto.response.GetMicrocurriculumResponse;
import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.CourseDao;
import com.co.planeador.repository.entities.Course;
import com.co.planeador.repository.entities.DocType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
