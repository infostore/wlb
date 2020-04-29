package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.IssueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Issue} and its DTO {@link IssueDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class, MilestoneMapper.class})
public interface IssueMapper extends EntityMapper<IssueDTO, Issue> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(source = "milestone.id", target = "milestoneId")
    @Mapping(source = "milestone.name", target = "milestoneName")
    IssueDTO toDto(Issue issue);

    @Mapping(target = "assignees", ignore = true)
    @Mapping(target = "removeAssignee", ignore = true)
    @Mapping(target = "watchers", ignore = true)
    @Mapping(target = "removeWatcher", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComment", ignore = true)
    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "milestoneId", target = "milestone")
    Issue toEntity(IssueDTO issueDTO);

    default Issue fromId(Long id) {
        if (id == null) {
            return null;
        }
        Issue issue = new Issue();
        issue.setId(id);
        return issue;
    }
}
