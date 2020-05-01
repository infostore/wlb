package kr.etcsoft.wlb.service;

import kr.etcsoft.wlb.domain.IssueActivity;
import kr.etcsoft.wlb.repository.IssueActivityRepository;
import kr.etcsoft.wlb.service.dto.IssueActivityDTO;
import kr.etcsoft.wlb.service.mapper.IssueActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IssueActivity}.
 */
@Service
@Transactional
public class IssueActivityService {

    private final Logger log = LoggerFactory.getLogger(IssueActivityService.class);

    private final IssueActivityRepository issueActivityRepository;

    private final IssueActivityMapper issueActivityMapper;

    public IssueActivityService(IssueActivityRepository issueActivityRepository, IssueActivityMapper issueActivityMapper) {
        this.issueActivityRepository = issueActivityRepository;
        this.issueActivityMapper = issueActivityMapper;
    }

    /**
     * Save a issueActivity.
     *
     * @param issueActivityDTO the entity to save.
     * @return the persisted entity.
     */
    public IssueActivityDTO save(IssueActivityDTO issueActivityDTO) {
        log.debug("Request to save IssueActivity : {}", issueActivityDTO);
        IssueActivity issueActivity = issueActivityMapper.toEntity(issueActivityDTO);
        issueActivity = issueActivityRepository.save(issueActivity);
        return issueActivityMapper.toDto(issueActivity);
    }

    /**
     * Get all the issueActivities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IssueActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IssueActivities");
        return issueActivityRepository.findAll(pageable)
            .map(issueActivityMapper::toDto);
    }

    /**
     * Get one issueActivity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IssueActivityDTO> findOne(Long id) {
        log.debug("Request to get IssueActivity : {}", id);
        return issueActivityRepository.findById(id)
            .map(issueActivityMapper::toDto);
    }

    /**
     * Delete the issueActivity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IssueActivity : {}", id);
        issueActivityRepository.deleteById(id);
    }
}
