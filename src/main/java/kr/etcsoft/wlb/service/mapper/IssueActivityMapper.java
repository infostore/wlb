package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.IssueActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IssueActivity} and its DTO {@link IssueActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {IssueMapper.class})
public interface IssueActivityMapper extends EntityMapper<IssueActivityDTO, IssueActivity> {

    @Mapping(source = "issue.id", target = "issueId")
    IssueActivityDTO toDto(IssueActivity issueActivity);

    @Mapping(source = "issueId", target = "issue")
    IssueActivity toEntity(IssueActivityDTO issueActivityDTO);

    default IssueActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        IssueActivity issueActivity = new IssueActivity();
        issueActivity.setId(id);
        return issueActivity;
    }
}
