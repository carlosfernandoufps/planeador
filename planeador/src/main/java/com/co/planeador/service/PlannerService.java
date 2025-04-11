package com.co.planeador.service;

import com.co.planeador.controller.dto.request.SaveNewRowBatchRequestDto;
import com.co.planeador.controller.dto.request.SaveNewRowRequestDto;
import com.co.planeador.controller.dto.request.UpdatePlannerRowRequestDto;
import com.co.planeador.controller.dto.response.GetAssignmentForDirectorResponseDto;
import com.co.planeador.controller.dto.response.GetCompatibleGroupPlanningResponseDto;
import com.co.planeador.controller.dto.response.GetCompatiblePlanningResponseDto;
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
import com.co.planeador.repository.entities.Profile;
import com.co.planeador.repository.entities.Semester;
import com.co.planeador.repository.entities.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private static final String PLANNER_NOT_FOUND_MESSAGE = "Planeador no encontrado";

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
                new CustomException(PLANNER_NOT_FOUND_MESSAGE));
        return mapPlannerToDetailDtoResponse(planner, assignment);
    }

    public GetPlannerResponseDto saveNewRow(Integer userId, SaveNewRowRequestDto dto){
        Planner planner = repository.findById(dto.getPlannerId()).
                orElseThrow(() -> new CustomException(PLANNER_NOT_FOUND_MESSAGE));
        validateIsPlannerEditable(userId, dto.getPlannerId());
        planner.getRows().add(createNewPlannerRow(planner, dto.getData()));
        return mapPlannerToDtoResponse(repository.save(planner));
    }

    public GetPlannerResponseDto saveNewRowBatch(Integer userId, SaveNewRowBatchRequestDto dto){
        Planner planner = repository.findById(dto.getPlannerId()).
                orElseThrow(() -> new CustomException(PLANNER_NOT_FOUND_MESSAGE));
        validateIsPlannerEditable(userId, dto.getPlannerId());
        for(List<String> data: dto.getBatchData()){
            planner.getRows().add(createNewPlannerRow(planner, data));
        }
        return mapPlannerToDtoResponse(repository.save(planner));
    }

    public GetPlannerResponseDto updatePlanner(Integer userId, UpdatePlannerRowRequestDto dto){
        Planner planner = repository.findById(dto.getPlannerId()).
                orElseThrow(() -> new CustomException(PLANNER_NOT_FOUND_MESSAGE));
        validateIsPlannerEditable(userId, dto.getPlannerId());
        validateRowExists(planner, dto.getRowPosition());
        planner.getRows().get(dto.getRowPosition()).setCells(dto.getData());
        return mapPlannerToDtoResponse(repository.save(planner));
    }

    public GetPlannerResponseDto deletePlannerRow(Integer userId, Integer plannerId, int rowPosition){
        Planner planner = repository.findById(plannerId).
                orElseThrow(() -> new CustomException(PLANNER_NOT_FOUND_MESSAGE));
        validateIsPlannerEditable(userId, plannerId);
        validateRowExists(planner, rowPosition);
        planner.getRows().remove(rowPosition);
        return mapPlannerToDtoResponse(repository.save(planner));
    }

    public List<GetPlannerDetailResponseDto> getAssignmentReportOfSemester(Integer semesterId){
        List<Assignment> assignmentsOfSemester = assignmentDao.findBySemesterId(semesterId);
        return assignmentsOfSemester.stream().map(assignment -> this.getPlannerByAssignmentId(assignment.getId())).toList();
    }

    public List<GetCompatiblePlanningResponseDto> getCompatiblePlannersOfOldSemesters(Integer plannerId){
        Planner planner = repository.findById(plannerId).
                orElseThrow(() -> new CustomException(PLANNER_NOT_FOUND_MESSAGE));

        Assignment assignment = assignmentDao.findOneByPlannerId(plannerId);

        Set<Integer> plannerIdsUsingSamePlannerVersion = repository.
                getCompatiblePlannersIdsByVersion(planner.getPlannerVersion().getId());

        Semester activeSemester = semesterService.getActiveSemester();

        List<Assignment> sameCourseAssignments = assignmentDao.findByCourseId(assignment.getCourseId());

        List<Assignment> filteredBySemesterAssignments = sameCourseAssignments.stream().filter(sameCourseAssignment ->
                !sameCourseAssignment.getSemesterId().equals(activeSemester.getId())).toList();

        List<Assignment> compatibleAssignments = filteredBySemesterAssignments.stream()
                .filter(sameCourseAssignment -> plannerIdsUsingSamePlannerVersion.contains(sameCourseAssignment.getPlannerId()))
                .toList();

        return compatibleAssignments.stream().map(this::mapAssignmentToDto).toList();
    }

    public List<GetCompatibleGroupPlanningResponseDto> getCompatibleGroupOfSameSemester(Integer plannerId){
        Planner planner = repository.findById(plannerId).
                orElseThrow(() -> new CustomException(PLANNER_NOT_FOUND_MESSAGE));
        Semester activeSemester = semesterService.getActiveSemester();
        Assignment assignmentOfPlanner = assignmentDao.findOneByPlannerId(plannerId);
        List<Assignment> assignments = assignmentDao.findBySemesterIdAndTeacherId(activeSemester.getId(), assignmentOfPlanner.getTeacherId());
        Set<Integer> plannerIdsUsingSamePlannerVersion = repository.
                getCompatiblePlannersIdsByVersion(planner.getPlannerVersion().getId());
        assignments.removeIf(
                assignment -> assignment.getPlannerId().equals(plannerId) ||
                        !assignment.getCourseId().equals(assignmentOfPlanner.getCourseId()));
        List<Assignment> filteredAssignments = assignments.stream().filter(assignment ->
                plannerIdsUsingSamePlannerVersion.stream().anyMatch(plannerUsingSameVersion ->
                                !plannerUsingSameVersion.equals(assignment.getPlannerId()))).toList();
        List<Assignment> filteredByGroup = filteredAssignments.stream().filter(
                assignment -> !assignmentOfPlanner.getGroupName().equals(assignment.getGroupName())).toList();
        return filteredByGroup.stream().map(this::mapAssignmentToGroupCompatibleDto).toList();
    }

    private GetCompatibleGroupPlanningResponseDto mapAssignmentToGroupCompatibleDto(Assignment assignment){
        GetCompatibleGroupPlanningResponseDto dto = new GetCompatibleGroupPlanningResponseDto();
        dto.setGroup(assignment.getGroupName());
        dto.setPlanningId(assignment.getPlannerId());
        return dto;
    }

    private GetCompatiblePlanningResponseDto mapAssignmentToDto(Assignment assignment){
        GetCompatiblePlanningResponseDto dto = new GetCompatiblePlanningResponseDto();
        dto.setGroup(assignment.getGroupName());
        Semester semester = semesterService.getSemesterById(assignment.getSemesterId());
        Profile teacher = profileDao.findById(assignment.getTeacherId()).orElseThrow(CustomException::new);
        dto.setSemesterName(semester.getName());
        dto.setTeacherName(teacher.getName());
        dto.setPlanningId(assignment.getId());
        return dto;
    }

    private void validateRowExists(Planner planner, int rowPosition){
        if(planner.getRows().size() <= rowPosition){
            throw new CustomException("Fila solicitada no existe");
        }
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
