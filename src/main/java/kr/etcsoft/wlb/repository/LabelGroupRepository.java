package kr.etcsoft.wlb.repository;

import kr.etcsoft.wlb.domain.LabelGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LabelGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LabelGroupRepository extends JpaRepository<LabelGroup, Long> {
}
