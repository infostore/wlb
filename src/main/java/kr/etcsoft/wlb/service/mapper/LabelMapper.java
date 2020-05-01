package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.LabelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Label} and its DTO {@link LabelDTO}.
 */
@Mapper(componentModel = "spring", uses = {LabelGroupMapper.class})
public interface LabelMapper extends EntityMapper<LabelDTO, Label> {

    @Mapping(source = "labelGroup.id", target = "labelGroupId")
    @Mapping(source = "labelGroup.name", target = "labelGroupName")
    LabelDTO toDto(Label label);

    @Mapping(target = "issueLabels", ignore = true)
    @Mapping(target = "removeIssueLabel", ignore = true)
    @Mapping(source = "labelGroupId", target = "labelGroup")
    Label toEntity(LabelDTO labelDTO);

    default Label fromId(Long id) {
        if (id == null) {
            return null;
        }
        Label label = new Label();
        label.setId(id);
        return label;
    }
}
