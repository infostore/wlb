package kr.etcsoft.wlb.repository;

import kr.etcsoft.wlb.domain.Milestone;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Milestone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    Page<Milestone> findByProjectId(Long projectId, Pageable pageable);
}
