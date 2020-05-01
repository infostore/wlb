package kr.etcsoft.wlb.repository;

import kr.etcsoft.wlb.domain.IssueAssignee;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the IssueAssignee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueAssigneeRepository extends JpaRepository<IssueAssignee, Long> {

    @Query("select issueAssignee from IssueAssignee issueAssignee where issueAssignee.user.login = ?#{principal.username}")
    List<IssueAssignee> findByUserIsCurrentUser();
}
