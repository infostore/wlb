package kr.etcsoft.wlb.repository;

import kr.etcsoft.wlb.domain.ProjectActivity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectActivityRepository extends JpaRepository<ProjectActivity, Long> {
}
