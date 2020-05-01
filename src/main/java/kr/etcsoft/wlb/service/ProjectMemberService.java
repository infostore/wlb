package kr.etcsoft.wlb.service;

import kr.etcsoft.wlb.domain.ProjectMember;
import kr.etcsoft.wlb.repository.ProjectMemberRepository;
import kr.etcsoft.wlb.service.dto.ProjectMemberDTO;
import kr.etcsoft.wlb.service.mapper.ProjectMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectMember}.
 */
@Service
@Transactional
public class ProjectMemberService {

    private final Logger log = LoggerFactory.getLogger(ProjectMemberService.class);

    private final ProjectMemberRepository projectMemberRepository;

    private final ProjectMemberMapper projectMemberMapper;

    public ProjectMemberService(ProjectMemberRepository projectMemberRepository, ProjectMemberMapper projectMemberMapper) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectMemberMapper = projectMemberMapper;
    }

    /**
     * Save a projectMember.
     *
     * @param projectMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectMemberDTO save(ProjectMemberDTO projectMemberDTO) {
        log.debug("Request to save ProjectMember : {}", projectMemberDTO);
        ProjectMember projectMember = projectMemberMapper.toEntity(projectMemberDTO);
        projectMember = projectMemberRepository.save(projectMember);
        return projectMemberMapper.toDto(projectMember);
    }

    /**
     * Get all the projectMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectMemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectMembers");
        return projectMemberRepository.findAll(pageable)
            .map(projectMemberMapper::toDto);
    }

    /**
     * Get one projectMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectMemberDTO> findOne(Long id) {
        log.debug("Request to get ProjectMember : {}", id);
        return projectMemberRepository.findById(id)
            .map(projectMemberMapper::toDto);
    }

    /**
     * Delete the projectMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectMember : {}", id);
        projectMemberRepository.deleteById(id);
    }
}
