package kr.etcsoft.wlb.repository;

import kr.etcsoft.wlb.domain.IssueAttachment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IssueAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueAttachmentRepository extends JpaRepository<IssueAttachment, Long> {
}
