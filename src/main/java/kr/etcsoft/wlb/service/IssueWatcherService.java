package kr.etcsoft.wlb.service;

import kr.etcsoft.wlb.domain.IssueWatcher;
import kr.etcsoft.wlb.repository.IssueWatcherRepository;
import kr.etcsoft.wlb.service.dto.IssueWatcherDTO;
import kr.etcsoft.wlb.service.mapper.IssueWatcherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IssueWatcher}.
 */
@Service
@Transactional
public class IssueWatcherService {

    private final Logger log = LoggerFactory.getLogger(IssueWatcherService.class);

    private final IssueWatcherRepository issueWatcherRepository;

    private final IssueWatcherMapper issueWatcherMapper;

    public IssueWatcherService(IssueWatcherRepository issueWatcherRepository, IssueWatcherMapper issueWatcherMapper) {
        this.issueWatcherRepository = issueWatcherRepository;
        this.issueWatcherMapper = issueWatcherMapper;
    }

    /**
     * Save a issueWatcher.
     *
     * @param issueWatcherDTO the entity to save.
     * @return the persisted entity.
     */
    public IssueWatcherDTO save(IssueWatcherDTO issueWatcherDTO) {
        log.debug("Request to save IssueWatcher : {}", issueWatcherDTO);
        IssueWatcher issueWatcher = issueWatcherMapper.toEntity(issueWatcherDTO);
        issueWatcher = issueWatcherRepository.save(issueWatcher);
        return issueWatcherMapper.toDto(issueWatcher);
    }

    /**
     * Get all the issueWatchers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IssueWatcherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IssueWatchers");
        return issueWatcherRepository.findAll(pageable)
            .map(issueWatcherMapper::toDto);
    }

    /**
     * Get one issueWatcher by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IssueWatcherDTO> findOne(Long id) {
        log.debug("Request to get IssueWatcher : {}", id);
        return issueWatcherRepository.findById(id)
            .map(issueWatcherMapper::toDto);
    }

    /**
     * Delete the issueWatcher by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IssueWatcher : {}", id);
        issueWatcherRepository.deleteById(id);
    }
}
