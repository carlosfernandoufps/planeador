package com.co.planeador.service;

import com.co.planeador.controller.dto.request.SaveNewRowRequestDto;
import com.co.planeador.controller.dto.response.GetAssignmentForDirectorResponseDto;
import com.co.planeador.controller.dto.response.GetPlannerDetailResponseDto;
import com.co.planeador.controller.dto.response.GetPlannerResponseDto;
import com.co.planeador.controller.dto.response.ProfileResponseDto;
import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.AssignmentDao;
import com.co.planeador.repository.dao.CourseDao;
import com.co.planeador.repository.dao.PlannerRepository;
import com.co.planeador.repository.dao.PlannerRowDao;
import com.co.planeador.repository.dao.ProfileDao;
import com.co.planeador.repository.dto.CourseInfoDto;
import com.co.planeador.repository.entities.Assignment;
import com.co.planeador.repository.entities.Planner;
import com.co.planeador.repository.entities.PlannerRow;
import com.co.planeador.repository.entities.PlannerVersion;
import com.co.planeador.repository.entities.Semester;
import com.co.planeador.repository.entities.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlannerService {

    private final PlannerRepository repository;
    private final PlannerVersionService plannerVersionService;
    private final AssignmentDao assignmentDao;
    private final PlannerRowDao plannerRowDao;
    private final ProfileService profileService;
    private final SemesterService semesterService;
    private final ProfileDao profileDao;
    private final CourseDao courseDao;

    public Planner createNewPlanner(Integer plannerVersionId){
        PlannerVersion plannerVersion = plannerVersionService.getPlannerVersionDetail(plannerVersionId);
        Planner planner = new Planner();
        planner.setPlannerVersion(plannerVersion);
        return repository.save(planner);
    }

    public GetPlannerDetailResponseDto getPlannerByAssignmentId(Integer assignmentId){
        Assignment assignment  = assignmentDao.findById(assignmentId).orElseThrow(() ->
                new CustomException("Asignación con id: " + assignmentId + " no encontrada"));
        Planner planner = repository.findById(assignment.getPlannerId()).orElseThrow(() ->
                new CustomException("Planeador no encontrado"));
        return mapPlannerToDetailDtoResponse(planner, assignment);
    }

    public GetPlannerResponseDto saveNewRow(Integer userId, SaveNewRowRequestDto dto){
        Planner planner = repository.findById(dto.getPlannerId()).
                orElseThrow(() -> new CustomException("Planeador no encontrado"));
        validateIsPlannerEditable(userId, dto.getPlannerId());
        planner.getRows().add(createNewPlannerRow(planner, dto.getData()));
        return mapPlannerToDtoResponse(repository.save(planner));
    }

    private void validateIsPlannerEditable(Integer userId, Integer plannerId){
        ProfileResponseDto teacherProfile = profileService.getProfileInfoByUserId(userId);
        Semester activeSemester = semesterService.getActiveSemester();
        List<Assignment> teacherAssignmentsOfActiveSemester = assignmentDao.
                findBySemesterIdAndTeacherId(activeSemester.getId(), teacherProfile.getId());
        boolean plannerIsOfThisSemesterOrTeacher = teacherAssignmentsOfActiveSemester.stream().
                anyMatch(assignment -> assignment.getPlannerId().equals(plannerId));
        if(!plannerIsOfThisSemesterOrTeacher){
            throw new CustomException("No se puede editar planeador si el semestre está finalizado" +
                    " o no le corresponde al docente asignado");
        }
    }

    private PlannerRow createNewPlannerRow(Planner planner, List<String> cells){
        PlannerRow plannerRow = new PlannerRow();
        plannerRow.setPlanner(planner);
        plannerRow.setCells(cells);
        return plannerRowDao.save(plannerRow);
    }

    private GetPlannerDetailResponseDto mapPlannerToDetailDtoResponse(Planner planner, Assignment assignment){
        GetAssignmentForDirectorResponseDto assignmentDto = buildGetAssignmentForDirectorResponseDto(assignment);
        Semester semester = semesterService.getSemesterById(assignment.getSemesterId());
        GetPlannerDetailResponseDto dto = new GetPlannerDetailResponseDto();
        dto.setId(planner.getId());
        dto.setVersionName(planner.getPlannerVersion().getName());
        dto.setCourseName(assignmentDto.getCourseName());
        dto.setTeacherName(assignmentDto.getTeacherName());
        dto.setGroup(assignmentDto.getGroup());
        dto.setSemesterName(semester.getName());
        dto.setColumns(planner.getPlannerVersion().getColumnDefinitions());
        dto.setData(mapPlannerRowToDTo(planner.getRows()));
        return dto;
    }

    private GetPlannerResponseDto mapPlannerToDtoResponse(Planner planner){
        GetPlannerResponseDto dto = new GetPlannerResponseDto();
        dto.setId(planner.getId());
        dto.setColumns(planner.getPlannerVersion().getColumnDefinitions());
        dto.setData(mapPlannerRowToDTo(planner.getRows()));
        return dto;
    }

    private List<List<String>> mapPlannerRowToDTo(List<PlannerRow> plannerRows){
        List<List<String>> rows = new ArrayList<>();
        for(PlannerRow plannerRow: plannerRows){
            List<String> rowInfo = plannerRow.getCells();
            rows.add(rowInfo);
        }
        return rows;
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

}
