package kr.etcsoft.wlb.repository;

import kr.etcsoft.wlb.domain.IssueWatcher;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the IssueWatcher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueWatcherRepository extends JpaRepository<IssueWatcher, Long> {

    @Query("select issueWatcher from IssueWatcher issueWatcher where issueWatcher.user.login = ?#{principal.username}")
    List<IssueWatcher> findByUserIsCurrentUser();
}
