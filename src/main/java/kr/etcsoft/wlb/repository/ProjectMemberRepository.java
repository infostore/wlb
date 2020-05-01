package kr.etcsoft.wlb.repository;

import kr.etcsoft.wlb.domain.ProjectMember;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ProjectMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    @Query("select projectMember from ProjectMember projectMember where projectMember.user.login = ?#{principal.username}")
    List<ProjectMember> findByUserIsCurrentUser();
}
