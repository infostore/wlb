package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.IssueAssigneeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IssueAssignee} and its DTO {@link IssueAssigneeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, IssueMapper.class})
public interface IssueAssigneeMapper extends EntityMapper<IssueAssigneeDTO, IssueAssignee> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "issue.id", target = "issueId")
    IssueAssigneeDTO toDto(IssueAssignee issueAssignee);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "issueId", target = "issue")
    IssueAssignee toEntity(IssueAssigneeDTO issueAssigneeDTO);

    default IssueAssignee fromId(Long id) {
        if (id == null) {
            return null;
        }
        IssueAssignee issueAssignee = new IssueAssignee();
        issueAssignee.setId(id);
        return issueAssignee;
    }
}
