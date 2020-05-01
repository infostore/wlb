package kr.etcsoft.wlb.repository;

import kr.etcsoft.wlb.domain.IssueLabel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IssueLabel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueLabelRepository extends JpaRepository<IssueLabel, Long> {
}
