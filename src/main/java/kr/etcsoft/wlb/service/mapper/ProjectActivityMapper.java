package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.ProjectActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectActivity} and its DTO {@link ProjectActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class})
public interface ProjectActivityMapper extends EntityMapper<ProjectActivityDTO, ProjectActivity> {

    @Mapping(source = "project.id", target = "projectId")
    ProjectActivityDTO toDto(ProjectActivity projectActivity);

    @Mapping(source = "projectId", target = "project")
    ProjectActivity toEntity(ProjectActivityDTO projectActivityDTO);

    default ProjectActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectActivity projectActivity = new ProjectActivity();
        projectActivity.setId(id);
        return projectActivity;
    }
}
