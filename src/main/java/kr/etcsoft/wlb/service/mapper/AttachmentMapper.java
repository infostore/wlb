package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.AttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {



    default Attachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attachment attachment = new Attachment();
        attachment.setId(id);
        return attachment;
    }
}
