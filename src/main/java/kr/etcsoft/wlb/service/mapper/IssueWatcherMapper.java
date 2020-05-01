package kr.etcsoft.wlb.service.mapper;


import kr.etcsoft.wlb.domain.*;
import kr.etcsoft.wlb.service.dto.IssueWatcherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IssueWatcher} and its DTO {@link IssueWatcherDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, IssueMapper.class})
public interface IssueWatcherMapper extends EntityMapper<IssueWatcherDTO, IssueWatcher> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "issue.id", target = "issueId")
    IssueWatcherDTO toDto(IssueWatcher issueWatcher);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "issueId", target = "issue")
    IssueWatcher toEntity(IssueWatcherDTO issueWatcherDTO);

    default IssueWatcher fromId(Long id) {
        if (id == null) {
            return null;
        }
        IssueWatcher issueWatcher = new IssueWatcher();
        issueWatcher.setId(id);
        return issueWatcher;
    }
}
