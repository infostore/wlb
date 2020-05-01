package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.ProjectMemberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectMember} and its DTO {@link ProjectMemberDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProjectMapper.class})
public interface ProjectMemberMapper extends EntityMapper<ProjectMemberDTO, ProjectMember> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "project.id", target = "projectId")
    ProjectMemberDTO toDto(ProjectMember projectMember);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "projectId", target = "project")
    ProjectMember toEntity(ProjectMemberDTO projectMemberDTO);

    default ProjectMember fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectMember projectMember = new ProjectMember();
        projectMember.setId(id);
        return projectMember;
    }
}
