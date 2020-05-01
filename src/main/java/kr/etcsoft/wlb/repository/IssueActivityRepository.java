package kr.etcsoft.wlb.repository;

import kr.etcsoft.wlb.domain.IssueActivity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IssueActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueActivityRepository extends JpaRepository<IssueActivity, Long> {
}
