package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.IssueLabelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IssueLabel} and its DTO {@link IssueLabelDTO}.
 */
@Mapper(componentModel = "spring", uses = {IssueMapper.class, LabelMapper.class})
public interface IssueLabelMapper extends EntityMapper<IssueLabelDTO, IssueLabel> {

    @Mapping(source = "issue.id", target = "issueId")
    @Mapping(source = "label.id", target = "labelId")
    IssueLabelDTO toDto(IssueLabel issueLabel);

    @Mapping(source = "issueId", target = "issue")
    @Mapping(source = "labelId", target = "label")
    IssueLabel toEntity(IssueLabelDTO issueLabelDTO);

    default IssueLabel fromId(Long id) {
        if (id == null) {
            return null;
        }
        IssueLabel issueLabel = new IssueLabel();
        issueLabel.setId(id);
        return issueLabel;
    }
}
