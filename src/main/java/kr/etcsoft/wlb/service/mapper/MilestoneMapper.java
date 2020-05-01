package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.MilestoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Milestone} and its DTO {@link MilestoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class})
public interface MilestoneMapper extends EntityMapper<MilestoneDTO, Milestone> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    MilestoneDTO toDto(Milestone milestone);

    @Mapping(source = "projectId", target = "project")
    Milestone toEntity(MilestoneDTO milestoneDTO);

    default Milestone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Milestone milestone = new Milestone();
        milestone.setId(id);
        return milestone;
    }
}
