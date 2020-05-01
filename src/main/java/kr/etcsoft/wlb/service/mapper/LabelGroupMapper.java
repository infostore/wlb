package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.LabelGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LabelGroup} and its DTO {@link LabelGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LabelGroupMapper extends EntityMapper<LabelGroupDTO, LabelGroup> {



    default LabelGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        LabelGroup labelGroup = new LabelGroup();
        labelGroup.setId(id);
        return labelGroup;
    }
}
