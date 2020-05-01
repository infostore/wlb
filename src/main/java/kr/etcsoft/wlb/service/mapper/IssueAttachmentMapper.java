package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.IssueAttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IssueAttachment} and its DTO {@link IssueAttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {AttachmentMapper.class, IssueMapper.class})
public interface IssueAttachmentMapper extends EntityMapper<IssueAttachmentDTO, IssueAttachment> {

    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "issue.id", target = "issueId")
    IssueAttachmentDTO toDto(IssueAttachment issueAttachment);

    @Mapping(source = "attachmentId", target = "attachment")
    @Mapping(source = "issueId", target = "issue")
    IssueAttachment toEntity(IssueAttachmentDTO issueAttachmentDTO);

    default IssueAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        IssueAttachment issueAttachment = new IssueAttachment();
        issueAttachment.setId(id);
        return issueAttachment;
    }
}
