package kr.etcsoft.wlb.service;

import kr.etcsoft.wlb.domain.ProjectActivity;
import kr.etcsoft.wlb.repository.ProjectActivityRepository;
import kr.etcsoft.wlb.service.dto.ProjectActivityDTO;
import kr.etcsoft.wlb.service.mapper.ProjectActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectActivity}.
 */
@Service
@Transactional
public class ProjectActivityService {

    private final Logger log = LoggerFactory.getLogger(ProjectActivityService.class);

    private final ProjectActivityRepository projectActivityRepository;

    private final ProjectActivityMapper projectActivityMapper;

    public ProjectActivityService(ProjectActivityRepository projectActivityRepository, ProjectActivityMapper projectActivityMapper) {
        this.projectActivityRepository = projectActivityRepository;
        this.projectActivityMapper = projectActivityMapper;
    }

    /**
     * Save a projectActivity.
     *
     * @param projectActivityDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectActivityDTO save(ProjectActivityDTO projectActivityDTO) {
        log.debug("Request to save ProjectActivity : {}", projectActivityDTO);
        ProjectActivity projectActivity = projectActivityMapper.toEntity(projectActivityDTO);
        projectActivity = projectActivityRepository.save(projectActivity);
        return projectActivityMapper.toDto(projectActivity);
    }

    /**
     * Get all the projectActivities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectActivities");
        return projectActivityRepository.findAll(pageable)
            .map(projectActivityMapper::toDto);
    }

    /**
     * Get one projectActivity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectActivityDTO> findOne(Long id) {
        log.debug("Request to get ProjectActivity : {}", id);
        return projectActivityRepository.findById(id)
            .map(projectActivityMapper::toDto);
    }

    /**
     * Delete the projectActivity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectActivity : {}", id);
        projectActivityRepository.deleteById(id);
    }
}
